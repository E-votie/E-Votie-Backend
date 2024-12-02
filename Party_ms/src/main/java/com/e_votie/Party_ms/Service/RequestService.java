package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Document;
import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Model.Request;
import com.e_votie.Party_ms.Repository.PartyMemberRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import com.e_votie.Party_ms.Repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyMemberRepository partyMemberRepository;

    @Autowired
    private FileMsClient fileMsClient;

    @Autowired
    private PartyMemberService partyMemberService;

    @Autowired
    private DocumentService documentService;

    public Request createNewRequest(Jwt jwt, Request request) throws Exception {

        Optional<Party> party = partyRepository.findById(request.getParty().getRegistrationId());
        if(party.isPresent()){
            Party exisitingParty = party.get();
            request.setParty(exisitingParty);
        }else{
            throw new Exception("Party not found");
        }

        request.setRequestInitiatorNIC(jwt.getClaimAsString("preferred_username"));
        request.setCreatedAt(String.valueOf(LocalDateTime.now()));
        return requestRepository.save(request);
    }

    public List<Request> getRequestsByPartyId(Jwt jwt, Integer partyRegistrationId) {
        return requestRepository.findByPartyRegistrationId(partyRegistrationId);
    }

    public List<Request> getRequestsByReceiverNic(Jwt jwt, String receiverNic) {
        return requestRepository.findByReceiverNIC(receiverNic);
    }

    public Request updateRequestStatus(Jwt jwt, Integer requestId, String status) {
        Request existingRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        existingRequest.setRequestState(status);

        return requestRepository.save(existingRequest);
    }

    public Request getRequestsByPartyAndReceiverNic(Jwt jwt, String partyRegistrationId, String receiverNic) {
        return requestRepository.findByPartyAndReceiverNIC(Integer.valueOf(partyRegistrationId),receiverNic);
    }

    public void deleteRequestById(Jwt jwt, Integer requestId) throws Exception {
        if (!requestRepository.existsById(requestId)) {
            throw new Exception("Request not found.");
        }
        requestRepository.deleteById(requestId);
    }

    public void deleteRequestsByPartyId(Jwt jwt, Integer partyRegistrationId) throws Exception {
        List<Request> requests = requestRepository.findByPartyRegistrationId(partyRegistrationId);
        if (requests.isEmpty()) {
            throw new Exception("No requests found for the given party ID.");
        }
        requestRepository.deleteAll(requests);
    }

//    public ResponseEntity<String> acceptRequest(Jwt jwt, String requestId, PartyMember partyMember, List<MultipartFile> files) {
//        try {
//            Request existingRequest = requestRepository.findById(Integer.valueOf(requestId))
//                    .orElseThrow(() -> new RuntimeException("Request not found"));
//            existingRequest.setRequestState("accepted");
//            requestRepository.save(existingRequest);
//
//            String profilePictureURL = "";
//            //update profile picture
//            if (files != null && !files.isEmpty()) {
//                try {
//                    MultipartFile profilePicture = files.get(0);
//                    String originalFileName = profilePicture.getOriginalFilename();
//                    String fileExtension = "";
//                    String baseFileName = "";
//                    if (originalFileName != null) {
//                        int dotIndex = originalFileName.lastIndexOf(".");
//                        if (dotIndex != -1) {
//                            fileExtension = originalFileName.substring(dotIndex);
//                            baseFileName = originalFileName.substring(0, dotIndex);
//                        } else {
//                            baseFileName = originalFileName;
//                        }
//                    }
//
//                    String uniqueFileName = baseFileName + "_" + UUID.randomUUID() + fileExtension;
//                    // Split the base name by underscore (_)
//                    String[] parts = baseFileName.split("_");
//                    // Rename the file (create a new MultipartFile with the updated name)
//                    MultipartFile renamedFile = new MockMultipartFile(
//                            uniqueFileName,
//                            uniqueFileName,
//                            profilePicture.getContentType(),
//                            profilePicture.getInputStream()
//                    );
//                    // Upload the file using Feign client
//                    ResponseEntity<String> response = fileMsClient.uploadFile(renamedFile);
//                    if (response.getStatusCode().is2xxSuccessful()) {
//                        profilePictureURL = fileMsClient.getFileUrl(uniqueFileName);
//                    }
//
//                } catch (Exception e) {
//                    System.err.println("Error uploading profile picture: " + e.getMessage());
//                    throw new Exception("Error uploading profile picture: " + e.getMessage());
//                }
//            }
//
//            Optional<PartyMember> existingPartyMemberOptional = partyMemberRepository.findByNIC(existingRequest.getReceiverNIC());
//
//            if (existingPartyMemberOptional.isPresent()) {
//                PartyMember existingPartyMember = existingPartyMemberOptional.get();
//
//                if (existingPartyMember.getParty().getRegistrationId() == 2000) {
//                    existingPartyMember.setPartyMemberName(partyMember.getPartyMemberName());
//                    existingPartyMember.setRole(partyMember.getRole());
//                    if(!Objects.equals(profilePictureURL, "")){
//                        existingPartyMember.setProfilePicture(profilePictureURL);
//                    }
//                    existingPartyMember.setPartyMemberDescription(partyMember.getPartyMemberDescription());
//                    existingPartyMember.setParty(existingRequest.getParty());
//
//                    partyMemberRepository.save(existingPartyMember);
//                } else {
//                    return ResponseEntity.badRequest().body("Member is already associated with another party.");
//                }
//            } else {
//                PartyMember newPartyMember = new PartyMember();
//                newPartyMember.setPartyMemberId(existingRequest.getReceiverNIC());
//                newPartyMember.setRole(partyMember.getRole());
//                newPartyMember.setPartyMemberName(partyMember.getPartyMemberName());
//                newPartyMember.setPartyMemberDescription(partyMember.getPartyMemberDescription());
//                if(!Objects.equals(profilePictureURL, "")){
//                    newPartyMember.setProfilePicture(profilePictureURL);
//                }
//                newPartyMember.setManifestos(new ArrayList<>());
//                newPartyMember.setTopics(new ArrayList<>());
//                newPartyMember.setParty(existingRequest.getParty());
//
//                partyMemberRepository.save(partyMember);
//            }
//
//            return ResponseEntity.ok("Request accepted and party member processed successfully.");
//        } catch (Exception e) {
//            log.error("Error accepting request: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
//        }
//    }

    @Transactional
    public ResponseEntity<String> acceptRequest(Jwt jwt, String requestId, PartyMember partyMember, List<MultipartFile> files) {
        try {
            // Fetch the existing request
            Request existingRequest = requestRepository.findById(Integer.valueOf(requestId))
                    .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

            // Update request state
            existingRequest.setRequestState("accepted");
            requestRepository.save(existingRequest);

            // Upload profile picture if provided
            String profilePictureURL = documentService.uploadProfilePicture(files);

            // Process party member
            partyMemberService.processPartyMember(existingRequest, partyMember, profilePictureURL);

            return ResponseEntity.ok("Request accepted and party member processed successfully.");
        } catch (Exception e) {
            log.error("Error accepting request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }


}