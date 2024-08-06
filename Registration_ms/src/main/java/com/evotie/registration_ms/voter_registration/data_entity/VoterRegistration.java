package com.evotie.registration_ms.voter_registration.data_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;

@Entity
@Data
public class VoterRegistration {

    @Id
    private String applicationID;

    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Address")
    private String Address;
    @JsonProperty("HouseNo")
    private String HouseNo;
    @JsonProperty("NIC")
    private String NIC;
    @JsonProperty("DOB")
    private String DOB;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("Contact")
    private String Contact;
    @JsonProperty("CivilStatus")
    private String CivilStatus;
    @JsonProperty("Gender")
    private String Gender;
    @JsonProperty("RelationshipToTheChiefOccupant")
    private String RelationshipToTheChiefOccupant;
    @JsonProperty("ChiefOccupantNIC")
    private String ChiefOccupantNIC;
    @JsonProperty("AdminDistrict")
    private String AdminDistrict;
    @JsonProperty("ElectionDistrict")
    private String ElectionDistrict;
    @JsonProperty("PollingDivision")
    private String PollingDivision;
    @JsonProperty("GNDivision")
    private String gramaNiladhariDivision;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Reason")
    private String Reason;
    @JsonProperty("GramaNiladhariSignature")
    private String GramaNiladhariSignature;
    @JsonProperty("VerificationOfficerSignature")
    private String VerificationOfficerSignature;

    public VoterRegistration(String ApplicationID, String FirstName, String LastName, String Address,String AdminDistrict, String HouseNo, String NIC, String DOB, String Email, String Contact, String CivilStatus, String Gender, String RelationshipToTheChiefOccupant, String ChiefOccupantNIC, String ElectionDistrict, String PollingDivision, String GramaNiladhariDivision) {
        this.applicationID = ApplicationID;
        this.Name = FirstName;
        this.Address = Address;
        this.HouseNo = HouseNo;
        this.NIC = NIC;
        this.DOB = DOB;
        this.Email = Email;
        this.Contact = Contact;
        this.CivilStatus = CivilStatus;
        this.Gender = Gender;
        this.RelationshipToTheChiefOccupant = RelationshipToTheChiefOccupant;
        this.ChiefOccupantNIC = ChiefOccupantNIC;
        this.ElectionDistrict = ElectionDistrict;
        this.PollingDivision = PollingDivision;
        this.gramaNiladhariDivision = GramaNiladhariDivision;
        this.AdminDistrict = AdminDistrict;
        this.status = "Pending";
        this.Reason = "";
        this.GramaNiladhariSignature = "";
        this.VerificationOfficerSignature = "";
    }

    public VoterRegistration() {

    }

    public void generateID() {
        this.applicationID = RandomStringUtils.randomAlphanumeric(16);
    }
}

