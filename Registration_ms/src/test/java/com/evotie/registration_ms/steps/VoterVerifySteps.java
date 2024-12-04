import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import com.evotie.registration_ms.voter_registration.DTO.VoterVerify;
import com.evotie.registration_ms.voter_registration.data_entity.VoterRegistration;
import com.evotie.registration_ms.voter_registration.VoterRegistrationService;

@SpringBootTest
public class VoterVerifySteps {

    @Autowired
    private VoterRegistrationService voterRegistrationService;

    private VoterVerify voterVerify;
    private ResponseEntity<?> response;

    @Given("a voter with NIC {string}, email {string}, and contact {string} has been sent an OTP {string}")
    public void a_voter_with_details_has_been_sent_an_otp(String nic, String email, String contact, String otp) {
        // Simulate the temporary contact information and OTP setup
        TempContactInfo tempContactInfo = new TempContactInfo();
        tempContactInfo.setNIC(nic);
        tempContactInfo.setEmail(email);
        tempContactInfo.setContact(contact);
        tempContactInfo.setOTP(otp);
        tempContactInfo.setHash("validHash"); // This is the hash that is generated by the OTP
    }

    @When("the voter submits the OTP {string} and hash {string}")
    public void the_voter_submits_the_otp_and_hash(String otp, String hash) {
        voterVerify = new VoterVerify();
        voterVerify.setOTP(otp);
        voterVerify.setHash(hash);
        response = voterRegistrationService.VoterVerify(voterVerify);
    }

    @Then("the voter is registered successfully")
    public void the_voter_is_registered_successfully() {
        Assertions.assertEquals(200, response.getStatusCodeValue());
        VoterRegistration voter = (VoterRegistration) response.getBody();
        Assertions.assertNotNull(voter);
    }

    @Then("the voter details include {string}, {string}, and {string}")
    public void the_voter_details_include(String nic, String email, String contact) {
        VoterRegistration voter = (VoterRegistration) response.getBody();
        Assertions.assertEquals(nic, voter.getNIC());
        Assertions.assertEquals(email, voter.getEmail());
        Assertions.assertEquals(contact, voter.getContact());
    }

    @Then("the response is {string}")
    public void the_response_is(String expectedResponse) {
        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }
}
