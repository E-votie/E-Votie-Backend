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
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registrationId;

    private String partyName;
    private String abbreviation;
    private LocalDate foundedDate;

    @ElementCollection
    private List<String> partyColors = new ArrayList<>();

    private Integer districtBasisSeats;
    private Integer nationalBasisSeats;
    private Integer totalSeats;

    @OneToOne
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
    private List<Document> documents = new ArrayList<>();

    private  String leaderId;
    private String secretaryId;

    @ManyToOne
    @JoinColumn(name = "verificationOfficerId")
    private VerificationOfficer verificationOfficer;

    public void addDocument(Document document) {
        this.documents.add(document);
        document.setParty(this);
    }

    public void removeDocument(Document document) {
        this.documents.remove(document);
        document.setParty(null);
    }
}
