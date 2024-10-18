package com.e_votie.Party_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private LocalDate createdDate;
    private Float progress; //thinking of storing progress as a percentage

    @OneToOne
    private PartyMember partyMember;

    @OneToMany
    private List<Promise> promises;

}
