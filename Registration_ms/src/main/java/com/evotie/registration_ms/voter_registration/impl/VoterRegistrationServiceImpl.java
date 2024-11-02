package com.evotie.registration_ms.voter_registration.impl;

import com.evotie.registration_ms.voter_registration.DTO.*;
import com.evotie.registration_ms.voter_registration.Service.FileMsClient;
import com.evotie.registration_ms.voter_registration.Service.KeycloakService;
import com.evotie.registration_ms.voter_registration.Service.S3Service;
import com.evotie.registration_ms.voter_registration.Service.VoterService_Hyperlegerfabric;
import com.evotie.registration_ms.voter_registration.VoterRegistrationService;
import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import com.evotie.registration_ms.voter_registration.data_entity.VoterRegistration;
import com.evotie.registration_ms.voter_registration.external.EmailRequest;
import com.evotie.registration_ms.voter_registration.external.Massage;
import com.evotie.registration_ms.voter_registration.helper.NICProcessor;
import com.evotie.registration_ms.voter_registration.messaging.KafkaProducer;
import com.evotie.registration_ms.voter_registration.repo.TempContactInfoRepo;
import com.evotie.registration_ms.voter_registration.repo.VoterRegistrationRepo;
import com.evotie.registration_ms.voter_registration.repo.VoterRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoterRegistrationServiceImpl implements VoterRegistrationService {

    VoterRegistrationRepo voterRegistrationRepo;
    TempContactInfoRepo tempContactInfoRepo;
    VoterRepo voterRepo;
    private final KafkaProducer kafkaProducer;
    private final S3Service s3Service;
    private final VoterService_Hyperlegerfabric voterService;
    private final ModelMapper modelMapper;
    private final KeycloakService keycloakService;
    private final FileMsClient fileMsClient;

    public VoterRegistrationServiceImpl(VoterRegistrationRepo voterRegistrationRepo, S3Service s3Service, KafkaProducer kafkaProducer, TempContactInfoRepo tempContactInfoRepo, VoterService_Hyperlegerfabric voterService, ModelMapper modelMapper, VoterRepo voterRepo, KeycloakService keycloakService, FileMsClient fileMsClient) {
        this.voterRegistrationRepo = voterRegistrationRepo;
        this.kafkaProducer = kafkaProducer;
        this.tempContactInfoRepo = tempContactInfoRepo;
        this.s3Service = s3Service;
        this.voterService = voterService;
        this.modelMapper = modelMapper;
        this.voterRepo = voterRepo;
        this.keycloakService = keycloakService;
        this.fileMsClient = fileMsClient;
    }

    @Override
    public ResponseEntity<?> VerifyContact(TempContactInfo tempContactInfo) {
        tempContactInfo.generateHash();
        tempContactInfo.generateOTP();

        VoterRegistration voter = voterRegistrationRepo.findByNIC(tempContactInfo.getNIC());
        if (voter != null) {
            return ResponseEntity.badRequest().body("NIC already registered.");
        }
        TempContactInfo tempContactInfo1 = tempContactInfoRepo.findByNIC(tempContactInfo.getNIC());
        if (tempContactInfo1 != null) {
            tempContactInfoRepo.delete(tempContactInfo1);
        }
        tempContactInfoRepo.save(tempContactInfo);
        try {
            sendEmail(tempContactInfo.getEmail(), "Verify the Email", "Your verification link is http://localhost:5173/verify/" + tempContactInfo.getHash());
            sendMessage(tempContactInfo.getContact(), "Voter Mobile number", "Your OTP is " + tempContactInfo.getOTP() + " Please verify your mobile number.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> VoterVerify(VoterVerify voterVerify) {
        TempContactInfo tempContactInfo1 = tempContactInfoRepo.findByHash(voterVerify.getHash());
        if (tempContactInfo1 != null) {
            if (tempContactInfo1.getOTP().equals(voterVerify.getOTP())) {
                VoterRegistration voter = voterRegistrationRepo.findByNIC(tempContactInfo1.getNIC());
                if (voter == null) {
                    VoterRegistration voterRegistration = new VoterRegistration();
                    voterRegistration.generateID();
                    voterRegistration.setNIC(tempContactInfo1.getNIC());
                    voterRegistration.setContact(tempContactInfo1.getContact());
                    voterRegistration.setEmail(tempContactInfo1.getEmail());
                    Map<String, String> data = NICProcessor.getBirthdayAndGender(tempContactInfo1.getNIC());
                    voterRegistration.setDOB(data.get("birthday"));
                    voterRegistration.setGender(data.get("gender"));
                    voterRegistrationRepo.save(voterRegistration);
                }
                return ResponseEntity.ok().body(voterRegistrationRepo.findByNIC(tempContactInfo1.getNIC()));
            }
        }
        return ResponseEntity.badRequest().body("OTP is incorrect or expired.");
    }

    @Override
    public ResponseEntity<?> grameniladariSignature(SignDTO sign) {
        VoterRegistration voterRegistration = voterRegistrationRepo.findByApplicationID(sign.getApplicationID());
        voterRegistration.setGramaNiladhariSignature(sign.getSign());
        voterRegistration.setStatus("Grameniladari_Signed");
        voterRegistrationRepo.save(voterRegistration);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> sentOTP(String nic) {
        VoterRegistration voter = voterRegistrationRepo.findByNIC(nic);
        if (voter == null) {
            return ResponseEntity.badRequest().body("NIC is not registered.");
        } else {
            TempContactInfo tempContactInfo = new TempContactInfo();
            tempContactInfo.setNIC(nic);
            tempContactInfo.setContact(voter.getContact());
            tempContactInfo.setEmail(voter.getEmail());
            tempContactInfo.generateOTP();
            tempContactInfo.generateHash();
            tempContactInfoRepo.save(tempContactInfo);
            log.info(tempContactInfo.getOTP());
            try {
                sendEmail(tempContactInfo.getEmail(), "Fingerprint Scan OTP", "Your OTP is " + tempContactInfo.getOTP() + "Please give this to the Officer.");
                sendMessage(voter.getContact(), "Fingerprint Scan OTP", "Your OTP is " + tempContactInfo.getOTP() + "Please give this to the Officer.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> verifyOTP(String OTP) {
        TempContactInfo temp = tempContactInfoRepo.findByOTP(OTP);
        if (temp == null) {
            return ResponseEntity.badRequest().body("OTP is Invalid or expired.");
        } else {
            VoterRegistration voter = voterRegistrationRepo.findByNIC(temp.getNIC());
            if (voter == null) {
                return ResponseEntity.badRequest().body("Something went wrong please contact your administrator");
            }
            FingerprintVerifyDTO verifyDTO = new FingerprintVerifyDTO();
            verifyDTO.setNIC(voter.getNIC());
            verifyDTO.setName(voter.getName());
            verifyDTO.setApplicationID(voter.getApplicationID());
            verifyDTO.setFace(s3Service.generatePresignedUrlServise(voter.getApplicationID() + "_Face.jpg"));
            verifyDTO.setNICBack(s3Service.generatePresignedUrlServise(voter.getApplicationID() + "_NICBack.jpg"));
            verifyDTO.setNICFront(s3Service.generatePresignedUrlServise(voter.getApplicationID() + "_NICFront.jpg"));
            return ResponseEntity.ok().body(verifyDTO);
        }
    }

    @Override
    public String AddVoter(String ID, byte[] BiometricTemplate) {
        log.info("------------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        VoterRegistration temp = voterRegistrationRepo.findByApplicationID(ID);
        temp.setStatus("Completed");
        voterRegistrationRepo.save(temp);
        Voter voter = voterRepo.findByNIC(temp.getNIC());
        log.info("------------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>++++++++++++++++++++++++++++++++");
        voterService.createVoter(voter.getNIC(), voter.getName(), voter.getVoterID(), BiometricTemplate);
        log.info("+++++++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
                sendEmail(voter.getEmail(), "Fingerprint Scan Completed", "Your fingerprint is successfully registered");
                sendMessage(voter.getContact(), "Fingerprint Scan Completed", "Your fingerprint is successfully registered");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Successfully registered the fingerprint";
    }

    @Override
    public VoterRegistration saveVoterRegistration(VoterRegistrationDTO voterRegistrationDTO) {
        VoterRegistration voterRegistration = voterRegistrationRepo.findByApplicationID(voterRegistrationDTO.getApplicationID());
        voterRegistration.setStatus("Pending");
        voterRegistration.setName(voterRegistrationDTO.getName());
        voterRegistration.setDOB(voterRegistrationDTO.getDOB());
        voterRegistration.setCivilStatus(voterRegistrationDTO.getCivilStatus());
        voterRegistration.setRelationshipToTheChiefOccupant(voterRegistrationDTO.getRelationshipToTheChiefOccupant());
        voterRegistration.setChiefOccupantNIC(voterRegistrationDTO.getChiefOccupantNIC());
        voterRegistration.setElectionDistrict(voterRegistrationDTO.getElectionDistrict());
        voterRegistration.setPollingDivision(voterRegistrationDTO.getPollingDivision());
        voterRegistration.setGramaNiladhariDivision(voterRegistrationDTO.getGramaNiladhariDivision());
        voterRegistration.setAddress(voterRegistrationDTO.getAddress());
        voterRegistration.setAdminDistrict(voterRegistrationDTO.getAdminDistrict());
        voterRegistration.setHouseNo(voterRegistrationDTO.getHouseNo());
        voterRegistrationRepo.save(voterRegistration);
        try {
            sendEmail(voterRegistration.getEmail(), "Voter Application Reserved", "Your voter registration Application has submitted. Ref-" + voterRegistration.getApplicationID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TempContactInfo tempContactInfo1 = tempContactInfoRepo.findByNIC(voterRegistration.getNIC());
        tempContactInfoRepo.delete(tempContactInfo1);
        return voterRegistration;
    }

    @Override
    public VoterApplicationDTO getVoterRegistration(String applicationID) {
        VoterRegistration voterRegistration = voterRegistrationRepo.findByApplicationID(applicationID);
        VoterApplicationDTO voterApplicationDTO = new VoterApplicationDTO();
        if (voterRegistration != null) {
            BeanUtils.copyProperties(voterRegistration, voterApplicationDTO);
            voterApplicationDTO.setPhoto(fileMsClient.getFileUrl(applicationID + "_Face.jpg"));
            voterApplicationDTO.setNICBack(fileMsClient.getFileUrl(applicationID + "_NICBack.jpg"));
            voterApplicationDTO.setNICFront(fileMsClient.getFileUrl(applicationID + "_NICFront.jpg"));
        }
        return voterApplicationDTO;
    }

    @Override
    public boolean VerifyVoterRegistration(String applicationID, String Sign, String Status, String Reason) {
        Optional<VoterRegistration> voterRegistration1 = voterRegistrationRepo.findById(applicationID);
        if (voterRegistration1.isPresent()) {
            VoterRegistration voterRegistration = voterRegistration1.get();
            if (Status.equals("Approved")) {
                if (Objects.equals(voterRegistration.getStatus(), "Pending")) {
                    voterRegistration.setStatus("Approved");
                    voterRegistration.setGramaNiladhariSignature(Sign);
                    voterRegistration.setReason(Reason);
                    voterRegistrationRepo.save(voterRegistration);
                    try {
                        sendEmail(voterRegistration.getEmail(), "Voter Registration Approved Grameniladari", "Your voter registration has been approved by Grameniladari.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return false;
                }
            } else if (Status.equals("Rejected")) {
                voterRegistration.setStatus("Approved");
                voterRegistration.setGramaNiladhariSignature(Sign);
                voterRegistration.setReason(Reason);
                voterRegistrationRepo.save(voterRegistration);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<VoterRegistrationListDTO> getVoterRegistrationBYGramaNiladhariDivisionAndStatus(String GramaNiladhariDivision, String Status) {
        List<VoterRegistration> voterRegistrationList = voterRegistrationRepo.findByGramaNiladhariDivisionAndStatus(GramaNiladhariDivision, Status);
        return voterRegistrationList.stream().map(voterRegistration -> new VoterRegistrationListDTO(voterRegistration.getApplicationID(), voterRegistration.getName(), voterRegistration.getAddress(), voterRegistration.getHouseNo(), voterRegistration.getNIC())).collect(Collectors.toList());
    }

    @Override
    public List<VoterRegistrationListDTO> getVoterRegistrationByStatus(String Status) {
        List<VoterRegistration> voterRegistrationList = voterRegistrationRepo.findByStatus(Status);
        return voterRegistrationList.stream().map(voterRegistration -> new VoterRegistrationListDTO(voterRegistration.getApplicationID(), voterRegistration.getName(), voterRegistration.getAddress(), voterRegistration.getHouseNo(), voterRegistration.getNIC())).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> verificationOfficerSignature(SignDTO sign) {
        VoterRegistration voterRegistration = voterRegistrationRepo.findByApplicationID(sign.getApplicationID());
        voterRegistration.setVerificationOfficerSignature(sign.getSign());
        voterRegistration.setStatus("Verified");
        voterRegistrationRepo.save(voterRegistration);
        String pwd = RandomStringUtils.randomAlphanumeric(5);
        String userID = keycloakService.createUser(voterRegistration.getNIC(), voterRegistration.getEmail(), voterRegistration.getName(), "null", pwd);
        keycloakService.assignRoleToUser(userID, "Voter");
        ModelMapper modelMapper = new ModelMapper();
//        voterService.createVoter(voterRegistration.getNIC(), voterRegistration.getName(), "300", "tyasgvhbbusydbbcj");
        modelMapper.typeMap(VoterRegistration.class, Voter.class).addMappings(mapper -> mapper.skip(Voter::setVoterID));
        Voter destination = modelMapper.map(voterRegistration, Voter.class);
        destination.setVoterID(userID);
        voterRepo.save(destination);
        try {
            sendEmail(voterRegistration.getEmail(), "Voter Registration Approved", "Your voter registration has been completed Username is your NIC and password is -> " + pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok().body(response);
    }

    private void sendEmail(String email, String subject, String message) throws Exception {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject(subject);
        emailRequest.setBody("<html><body><h1>Hello</h1><p>" + message + ".</p></body></html>");
        emailRequest.setHtml(true);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Priority", "1");
        emailRequest.setHeaders(headers);

        // Send email request
        kafkaProducer.sendEmailRequest(emailRequest);
    }

    private void sendMessage(String Contact, String subject, String message) throws Exception {
        Massage messageRequest = new Massage();
        messageRequest.setTo(Contact);
        messageRequest.setSubject(subject);
        messageRequest.setBody(message);

        // Send email request
        kafkaProducer.sendMassageRequest(messageRequest);
    }
}
