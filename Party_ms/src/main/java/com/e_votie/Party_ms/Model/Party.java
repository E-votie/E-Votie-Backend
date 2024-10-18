package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.DTO.Voter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registrationId;
    private String partyName;
    private String abbreviation;
    private LocalDate foundedDate;

    @ElementCollection
    private List<String> partyColors = new ArrayList<>();

    @OneToMany(mappedBy = "party")
    private List<PartyMember> partyMembers;

    private String symbol;
    private String constitution;
    private String financialStatement;
    private String declaration;
    private String otherDocuments;

    @Column(name = "partyStatus", columnDefinition = "VARCHAR(255) DEFAULT 'pending verification'")
    private String status;

    @ManyToOne
    @JoinColumn(name = "verificationOfficerId")
    private VerificationOfficer verificationOfficer;

}
