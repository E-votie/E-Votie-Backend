package com.e_votie.Party_ms.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// Create a separate class for the composite key
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NominationId implements Serializable {
    private Integer partyRegistrationId;
    private Integer electionId;
}
