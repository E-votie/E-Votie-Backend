package com.e_votie.Party_ms.DTO;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    // Add other location fields as needed

    @ManyToMany(mappedBy = "locations")
    private Set<Election> elections;
}