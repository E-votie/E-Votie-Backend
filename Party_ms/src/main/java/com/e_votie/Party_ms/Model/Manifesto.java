package com.e_votie.Party_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manifestos")
public class Manifesto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manifesto_id")
    private Integer manifestoId;

    private String manifestoName;
    private String election;
    private LocalDate createdDate;
    private Float progress; // Store progress as a percentage

    @ManyToOne
    @JoinColumn(name = "partyMemberId", nullable = false)
    @JsonIgnoreProperties("manifestos") // Ignore the 'manifestos' field in PartyMember during serialization
    private PartyMember partyMember;

    @OneToMany
    private List<Promise> promises;
}
