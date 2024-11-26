package com.evotie.hyperledgerfabricclient_ms.Model;

import lombok.Data;

@Data
public class HyperlegerFabric_Election_Model {
    private String contractAddress;
    private String electionID;
    private String endTime;
    private String startTime;
}
