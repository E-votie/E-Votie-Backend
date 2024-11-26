package com.evotie.report_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    private String imageURI;

    // Getters and Setters
}