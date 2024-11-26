package com.evotie.report_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PresidentialResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
    
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;
    
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
    
    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    // Getters and Setters
}