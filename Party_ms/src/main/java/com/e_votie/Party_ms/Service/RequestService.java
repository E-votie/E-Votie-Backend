package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Model.Request;
import com.e_votie.Party_ms.Repository.PartyRepository;
import com.e_votie.Party_ms.Repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PartyRepository partyRepository;

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
}