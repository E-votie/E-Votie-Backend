package com.e_votie.Party_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parties")
public class Party {

    @Id
    @Column(name = "registration_number")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registrationId;
    private String partyName;
    private String abbreviation;
    private LocalDate foundedDate;
    private String leader;
    private String partyColors;
    private String symbol;
    private String constitution;
    private String financialStatement;
    private String declaration;
    @Column(name = "party_status", columnDefinition = "VARCHAR(255) DEFAULT 'pending verification'")
    private String status;

    @OneToOne
    private PartyMember partySecretary;

}
