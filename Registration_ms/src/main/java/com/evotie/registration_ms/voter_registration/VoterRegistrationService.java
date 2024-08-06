package com.evotie.registration_ms.voter_registration;

import com.evotie.registration_ms.voter_registration.DTO.*;
import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import com.evotie.registration_ms.voter_registration.data_entity.VoterRegistration;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoterRegistrationService {
    ResponseEntity<?> VerifyContact(TempContactInfo tempContactInfo);
    VoterRegistration saveVoterRegistration(VoterRegistrationDTO voterRegistration);
    VoterApplicationDTO getVoterRegistration(String applicationID);
    boolean VerifyVoterRegistration(String applicationID, String Sign,String Status, String Reason);
    List<VoterRegistrationListDTO> getVoterRegistrationBYGramaNiladhariDivisionAndStatus(String GramaNiladhariDivision, String Status);
    List<VoterRegistrationListDTO> getVoterRegistrationByStatus(String Status);
    ResponseEntity<?> verificationOfficerSignature(SignDTO sign);
    ResponseEntity<?> VoterVerify(VoterVerify voterVerify);
    ResponseEntity<?> grameniladariSignature(SignDTO sign);
    ResponseEntity<?> sentOTP(String nic);
    ResponseEntity<?> verifyOTP(String otp);
    String AddVoter(String ID, byte[] BiometricTemplate);
}
