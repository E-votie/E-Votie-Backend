package com.evotie.hyperledgerfabricclient_ms.Model;
import lombok.Data;

@Data
public class HyperlegerFabric_Voter_Model{
    private String NIC;
    private String Name;
    private String voterID;
    private byte[] BiometricTemplate;
}
