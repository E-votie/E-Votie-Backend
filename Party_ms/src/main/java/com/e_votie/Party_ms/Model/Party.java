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

    @Column(nullable = false)
    private String partyName;

    private String abbreviation;
    private LocalDate foundedDate;
    private String leader;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String contactNumber;

    private String symbol; // To store the path or name of the uploaded party symbol
    private String partyColors;

    @Column(columnDefinition = "TEXT")
    private String constitution; // To store the path or name of the uploaded constitution

    @Column(columnDefinition = "TEXT")
    private String financialStatements; // To store the path or name of the uploaded financial statements

    @Column(columnDefinition = "TEXT")
    private String declaration; // To store the path or name of the uploaded declaration

    @Column(name = "party_status", columnDefinition = "VARCHAR(255) DEFAULT 'pending verification'")
    private String status;
}
