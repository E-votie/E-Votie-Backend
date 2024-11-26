package com.e_votie.Party_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Nominee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer nomineeId;
    private String partyMemberNIC;
    private  Integer electionId;

    @ManyToOne
    private Party party;
}
