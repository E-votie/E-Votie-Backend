package com.evotie.registration_ms.voter_registration.data_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;

@Entity
@Data
public class TempContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("NIC")
    private String NIC;
    @JsonProperty("Contact")
    private String Contact;
    @JsonProperty("Email")
    private String Email;

    private String hash;

    private String OTP;

    public void generateOTP() {
        this.OTP = RandomStringUtils.randomNumeric(4); // Generates a 4-digit OTP
    }

    public void generateHash() {
        this.hash = RandomStringUtils.randomAlphanumeric(16);
    }
}
