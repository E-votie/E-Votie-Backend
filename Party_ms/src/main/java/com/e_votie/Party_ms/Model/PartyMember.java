package com.e_votie.Party_ms.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class PartyMember{

    @Id
    String partyMemberId; //save voter id here. is a relationship is removed since the relationship can not be created with a dto.

    private String NIC;
    private String role; //leader, secretory, MP,

    private String partyMemberName;
    private String partyMemberDescription;
    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @OneToMany(mappedBy = "partyMember")
    @JsonIgnoreProperties("partyMembers")
    private List<Manifesto> manifestos = new ArrayList<>();

    @ElementCollection
    @JsonIgnoreProperties("partyMembers")
    private List<String> topics = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "partyId", nullable = false)
    @JsonIgnoreProperties("partyMembers")
    private Party party;

}
