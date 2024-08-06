package com.evotie.registration_ms.voter_registration.data_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Voter {

    @Id
    private String VoterID;
    @JsonProperty("ApplicationID")
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

    public Voter(String applicationID, String name, String address, String houseNo, String NIC, String DOB, String email, String contact, String civilStatus, String gender, String relationshipToTheChiefOccupant, String chiefOccupantNIC, String adminDistrict, String electionDistrict, String pollingDivision, String gramaNiladhariDivision, String status) {
        this.applicationID = applicationID;
        Name = name;
        Address = address;
        HouseNo = houseNo;
        this.NIC = NIC;
        this.DOB = DOB;
        Email = email;
        Contact = contact;
        CivilStatus = civilStatus;
        Gender = gender;
        RelationshipToTheChiefOccupant = relationshipToTheChiefOccupant;
        ChiefOccupantNIC = chiefOccupantNIC;
        AdminDistrict = adminDistrict;
        ElectionDistrict = electionDistrict;
        PollingDivision = pollingDivision;
        this.gramaNiladhariDivision = gramaNiladhariDivision;
        this.status = status;
    }

    public Voter() {

    }
}
