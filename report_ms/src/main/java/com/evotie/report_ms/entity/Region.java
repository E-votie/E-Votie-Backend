package com.evotie.report_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String type;
    
    private String name;

    // Getters and Setters
}
