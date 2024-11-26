package com.evotie.report_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    private String abbreviation;
    
    private String color;
    
    private String imageURI;

    // Getters and Setters
}