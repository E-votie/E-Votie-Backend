package com.evotie.report_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String type;
    
    private Integer year;

    // Getters and Setters
}
