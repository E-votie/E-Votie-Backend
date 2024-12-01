package com.e_votie.Party_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(NominationId.class)
public class Nomination {
    @Id
    private Integer partyRegistrationId;

    @Id
    private Integer electionId;

    private String partyMemberNIC;

}

