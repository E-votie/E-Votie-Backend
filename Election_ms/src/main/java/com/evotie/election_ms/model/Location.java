package com.evotie.election_ms.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
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