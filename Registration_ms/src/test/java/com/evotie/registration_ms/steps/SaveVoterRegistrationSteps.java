import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.example.project.dto.VoterRegistrationDTO;
import com.example.project.model.VoterRegistration;
import com.example.project.repository.TempContactInfoRepo;
import com.evotie.registration_ms.voter_registration.repo.VoterRegistrationRepo;
import com.example.project.service.VoterRegistrationService;

@SpringBootTest
public class SaveVoterRegistrationSteps {

    @Autowired
    private VoterRegistrationService voterRegistrationService;

    @Autowired
    private VoterRegistrationRepo voterRegistrationRepo;

    @Autowired
    private TempContactInfoRepo tempContactInfoRepo;

    private VoterRegistrationDTO voterRegistrationDTO;
    private VoterRegistration voterRegistration;

    @Given("a voter registration application with Application ID {string} exists in the system")
    public void a_voter_registration_application_with_application_id_exists(String applicationID) {
        voterRegistration = new VoterRegistration();
        voterRegistration.setApplicationID(applicationID);
        voterRegistrationRepo.save(voterRegistration);
    }

    @Given("the voter registration details include:")
    public void the_voter_registration_details_include(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> details = dataTable.asMap(String.class, String.class);
        voterRegistrationDTO = new VoterRegistrationDTO();
        voterRegistrationDTO.setApplicationID(voterRegistration.getApplicationID());
        voterRegistrationDTO.setName(details.get("Name"));
        voterRegistrationDTO.setDOB(details.get("DOB"));
        voterRegistrationDTO.setCivilStatus(details.get("CivilStatus"));
        voterRegistrationDTO.setRelationshipToTheChiefOccupant(details.get("RelationshipToTheChiefOccupant"));
        voterRegistrationDTO.setChiefOccupantNIC(details.get("ChiefOccupantNIC"));
        voterRegistrationDTO.setElectionDistrict(details.get("ElectionDistrict"));
        voterRegistrationDTO.setPollingDivision(details.get("PollingDivision"));
        voterRegistrationDTO.setGramaNiladhariDivision(details.get("GramaNiladhariDivision"));
        voterRegistrationDTO.setAddress(details.get("Address"));
        voterRegistrationDTO.setAdminDistrict(details.get("AdminDistrict"));
        voterRegistrationDTO.setHouseNo(details.get("HouseNo"));
    }

    @When("the voter submits the application with email {string}")
    public void the_voter_submits_the_application_with_email(String email) {
        voterRegistrationDTO.setEmail(email);
        voterRegistration = voterRegistrationService.saveVoterRegistration(voterRegistrationDTO);
    }

    @Then("the application is saved successfully")
    public void the_application_is_saved_successfully() {
        Assertions.assertNotNull(voterRegistration);
        Assertions.assertEquals("Pending", voterRegistration.getStatus());
    }

    @Then("an email is sent to {string} with the subject {string}")
    public void an_email_is_sent_with_subject(String email, String subject) {
        // Mock or verify email sending mechanism. Use Mockito if needed.
        // Example (pseudocode):
        // Mockito.verify(sendEmail).triggerSendEmail(email, subject, ...);
    }

    @Then("the application status is updated to {string}")
    public void the_application_status_is_updated(String status) {
        Assertions.assertEquals(status, voterRegistration.getStatus());
    }

    @Then("the temporary contact information for NIC {string} is removed")
    public void the_temporary_contact_information_is_removed(String nic) {
        Assertions.assertNull(tempContactInfoRepo.findByNIC(nic));
    }
}
