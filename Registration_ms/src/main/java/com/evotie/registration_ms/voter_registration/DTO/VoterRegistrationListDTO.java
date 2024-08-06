package com.evotie.registration_ms.voter_registration.DTO;

import lombok.Data;

@Data
public class VoterRegistrationListDTO {
    private String ApplicationID;
    private String Name;
    private String Address;
    private String HouseNo;
    private String NIC;

    public VoterRegistrationListDTO(String applicationID, String name, String address, String houseNo, String NIC) {
        ApplicationID = applicationID;
        Name = name;
        Address = address;
        HouseNo = houseNo;
        this.NIC = NIC;
    }
}
