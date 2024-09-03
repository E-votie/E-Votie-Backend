package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.DTO.Voter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@PrimaryKeyJoinColumn(name = "VoterID")
public class PartyMember{

    @Id
    String partyMemberId; //save voter id here. is a relationship is removed since the relationship can not be created with a dto.

    private String NIC;
    private String role;

    @ManyToOne
    @JoinColumn(name = "partyId", nullable = false)
    private Party party;

    @OneToMany(mappedBy = "partyMember")
    private List<Manifesto> manifestos = new ArrayList<>();

    @ElementCollection
    private List<String> topics = new ArrayList<>();
}
