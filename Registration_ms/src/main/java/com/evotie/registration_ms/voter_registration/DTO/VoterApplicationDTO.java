package com.evotie.registration_ms.voter_registration.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class VoterApplicationDTO {
    @JsonProperty("ApplicationID")
    private String ApplicationID;
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
    @JsonProperty("GramaNiladhariDivision")
    private String GramaNiladhariDivision;
    @JsonProperty("Photo")
    private String Photo;
    @JsonProperty("NICBack")
    private String NICBack;
    @JsonProperty("NICFront")
    private String NICFront;
}
