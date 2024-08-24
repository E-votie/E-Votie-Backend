package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.DTO.Voter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "party members")
public class PartyMember extends Voter {

    private String firstName;
    private String lastName;
    private String email;
    private String role;

    @ElementCollection
    private List<String> phoneNumbers;

    private String pollingNumber;
    private String pollingDivision;
    private String gramaNiladhariDivision;
    private String divisionalSecretariatArea;
    private String electoralDistrict;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    @OneToMany
    @JoinColumn(name = "manifesto_id")
    private List<Manifesto> manifestos;

}
