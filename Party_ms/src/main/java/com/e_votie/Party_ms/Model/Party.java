package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.DTO.Voter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registrationId;

    private String partyName;
    private String abbreviation;
    private String foundedDate;

    @Column(columnDefinition = "TEXT")
    private String partyLogo;

    @ElementCollection
    private List<String> partyColors = new ArrayList<>();

    private Integer districtBasisSeats;
    private Integer nationalBasisSeats;
    private Integer totalSeats;

    @OneToOne(cascade = CascadeType.ALL) // Enables persistence of the Address when Party is persisted
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;

    private String contactNumber;
    private String partyWebsite;

    @Lob
    private byte[] partySymbol;

    @OneToMany(mappedBy = "party")
    private List<PartyMember> partyMembers;

    @Column(name = "partyStatus", columnDefinition = "VARCHAR(255) DEFAULT 'pending verification'")
    private String state; //pending verification, verified, banned

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Document> documents = new ArrayList<>();

    private  String leaderId;
    private String leaderName;
    private String secretoryName;
    private String secretaryId;

    @ManyToOne
    @JoinColumn(name = "verificationOfficerId")
    private VerificationOfficer verificationOfficer;

}
