package com.e_votie.Party_ms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voter {

    @JsonProperty("VoterID")
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
}
