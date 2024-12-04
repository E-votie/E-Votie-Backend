package com.evotie.registration_ms.steps;

import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import com.evotie.registration_ms.voter_registration.repo.TempContactInfoRepo;
import com.evotie.registration_ms.voter_registration.repo.VoterRegistrationRepo;
import com.evotie.registration_ms.voter_registration.Config.SendEmail;
import com.evotie.registration_ms.voter_registration.Config.SendMessage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VoterNICVerificationStepDefinitions {

    @Autowired
    private TempContactInfoRepo tempContactInfoRepo;

    @Autowired
    private VoterRegistrationRepo voterRegistrationRepo;

    @Autowired
    private SendEmail emailService;

    @Autowired
    private SendMessage smsService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private TempContactInfo tempContactInfo;
    private ResponseEntity<String> response;

    @Given("a voter with NIC {string}, Contact {string}, and Email {string}")
    public void voter_with_details(String nic, String contact, String email) {
        tempContactInfo = new TempContactInfo();
        tempContactInfo.setNIC(nic);
        tempContactInfo.setContact(contact);
        tempContactInfo.setEmail(email);
    }

    @When("the voter submits their registration information")
    public void voter_submits_registration() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Success"));
        response = restTemplate.postForEntity("/voter", tempContactInfo, String.class);
    }

    @Then("the system should save the voter's temporary contact information")
    public void system_saves_temp_info() {
        assertNotNull(tempContactInfoRepo.findByNIC(tempContactInfo.getNIC()));
    }

    @Then("send an email with a verification link to {string}")
    public void email_verification_sent(String email) throws Exception {
        verify(emailService).triggerSendEmail(eq(email), anyString(), anyString(), anyBoolean(), anyString(), anyMap());
    }

    @Then("send an SMS with an OTP to {string}")
    public void sms_otp_sent(String contact) throws Exception {
        verify(smsService).triggerSendMessage(eq(contact), anyString(), anyString());
    }

    @Then("the system should respond with {string}")
    public void system_responds(String message) {
        assertEquals(message, response.getBody());
    }
}
