package com.evotie.registration_ms.voter_registration.impl;

import com.evotie.registration_ms.voter_registration.Config.SendEmail;
import com.evotie.registration_ms.voter_registration.Config.SendMessage;
import com.evotie.registration_ms.voter_registration.DTO.*;
import com.evotie.registration_ms.voter_registration.Service.FeignClients.HyperledgerFabricClient;
import com.evotie.registration_ms.voter_registration.Service.FileMsClient;
import com.evotie.registration_ms.voter_registration.Service.KeycloakService;
import com.evotie.registration_ms.voter_registration.Service.S3Service;
import com.evotie.registration_ms.voter_registration.Service.VoterService_Hyperlegerfabric;
import com.evotie.registration_ms.voter_registration.VoterRegistrationService;
import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import com.evotie.registration_ms.voter_registration.data_entity.Voter;
import com.evotie.registration_ms.voter_registration.data_entity.VoterRegistration;
import com.evotie.registration_ms.voter_registration.external.EmailRequest;
import com.evotie.registration_ms.voter_registration.external.Fingerprint;
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
    private final SendEmail sendEmail;
    private final SendMessage sendMessage;
    private final HyperledgerFabricClient hyperledgerFabricClient;

    public VoterRegistrationServiceImpl(VoterRegistrationRepo voterRegistrationRepo, S3Service s3Service, KafkaProducer kafkaProducer, TempContactInfoRepo tempContactInfoRepo, VoterService_Hyperlegerfabric voterService, ModelMapper modelMapper, VoterRepo voterRepo, KeycloakService keycloakService, FileMsClient fileMsClient, SendEmail sendEmail, SendMessage sendMessage, HyperledgerFabricClient hyperledgerFabricClient) {
        this.voterRegistrationRepo = voterRegistrationRepo;
        this.kafkaProducer = kafkaProducer;
        this.tempContactInfoRepo = tempContactInfoRepo;
        this.s3Service = s3Service;
        this.voterService = voterService;
        this.modelMapper = modelMapper;
        this.voterRepo = voterRepo;
        this.keycloakService = keycloakService;
        this.fileMsClient = fileMsClient;
        this.sendEmail = sendEmail;
        this.sendMessage = sendMessage;
        this.hyperledgerFabricClient = hyperledgerFabricClient;
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
        Map<String, Object> variables = new HashMap<>();
        String verificationLink = "http://localhost:5173/verify/" + tempContactInfo.getHash();
        variables.put("verification_link", verificationLink);
        try {
            sendEmail.triggerSendEmail(tempContactInfo.getEmail(), "Voter Registration", "Please verify your email address by clicking the link below. " + verificationLink, true, "email_verification", variables);
            sendMessage.triggerSendMessage(tempContactInfo.getContact(), "Voter Mobile number", "Your OTP is " + tempContactInfo.getOTP() + " Please verify your mobile number.");
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
            Map<String, Object> variables = new HashMap<>();
            variables.put("otp", tempContactInfo.getOTP());
            try {
                sendEmail.triggerSendEmail(tempContactInfo.getEmail(), "Fingerprint Scan OTP", "Finger print OTP", true, "fingerprint_otp", variables);
                sendMessage.triggerSendMessage(voter.getContact(), "Fingerprint Scan OTP", "Your OTP is " + tempContactInfo.getOTP() + "Please give this to the Officer.");
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
            verifyDTO.setFace(fileMsClient.getFileUrl(voter.getApplicationID() + "_Face.jpg"));
            verifyDTO.setNICBack(fileMsClient.getFileUrl(voter.getApplicationID() + "_NICBack.jpg"));
            verifyDTO.setNICFront(fileMsClient.getFileUrl(voter.getApplicationID() + "_NICFront.jpg"));
            return ResponseEntity.ok().body(verifyDTO);
        }
    }

    @Override
    public String addFingerprint(Fingerprint fingerprint) {
        log.info("------------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        VoterRegistration temp = voterRegistrationRepo.findByApplicationID(fingerprint.getApplication_id());
        temp.setStatus("Completed");
        voterRegistrationRepo.save(temp);
        Voter voter = voterRepo.findByNIC(temp.getNIC());
        log.info("------------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>++++++++++++++++++++++++++++++++");
        hyperledgerFabricClient.createVoter(voter.getNIC(), voter.getName(), voter.getVoterID(), fingerprint.getTemplateData());
        log.info("+++++++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>>");
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", voter.getName());
        variables.put("nic", voter.getNIC());
        try {
                sendEmail.triggerSendEmail(voter.getEmail(), "Fingerprint Scan Completed", "Your fingerprint is successfully registered", true, "fingerprint_scan_complete", variables);
                sendMessage.triggerSendMessage(voter.getContact(), "Fingerprint Scan Completed", "Your fingerprint is successfully registered");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Successfully registered the fingerprint";
    }

    @Override
    public ResponseEntity<?> getVoterDetailsPollingStationVerification(String voterID) {
        Voter voter = voterRepo.findById(voterID).orElse(null);
        if (voter != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("nic", voter.getNIC());
            response.put("name", voter.getName());
            response.put("nicback", fileMsClient.getFileUrl(voter.getApplicationID() + "_NICBack.jpg"));
            response.put("nicfront", fileMsClient.getFileUrl(voter.getApplicationID() + "_NICFront.jpg"));
            response.put("face", fileMsClient.getFileUrl(voter.getApplicationID() + "_Face.jpg"));
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body("Please contact election commission IT Department");
        }
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
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicationNumber", voterRegistration.getApplicationID());
        try {
            sendEmail.triggerSendEmail(voterRegistration.getEmail(), "Voter Application Reserved", "Your voter registration Application has submitted. Ref-" + voterRegistration.getApplicationID(), true, "voter_application_reserved", variables);
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
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("name", voterRegistration.getName());
                    variables.put("nic", voterRegistration.getNIC());
                    variables.put("by", "Grame Niladhari");
                    try {
                        sendEmail.triggerSendEmail(voterRegistration.getEmail(), "Voter Registration Approved Grameniladari", "Your voter registration has been approved by Grameniladari.", true, "voter_registration_approved", variables);
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
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", voterRegistration.getName());
        variables.put("nic", voterRegistration.getNIC());
        variables.put("pwd", pwd);
        try {
            sendEmail.triggerSendEmail(voterRegistration.getEmail(), "Voter Registration Approved", "Your voter registration has been completed ", true, "voter_registration_successful", variables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok().body(response);
    }
}
