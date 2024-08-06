package com.evotie.election_ms.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String type;
    private String status;
    private LocalDateTime electionPublishDate;
    private LocalDateTime electionNominationCallingDate;
    private LocalDateTime electionDate;
    private LocalDateTime electionEndDate;
    @ManyToMany
    @JoinTable(
            name = "election_location",
            joinColumns = @JoinColumn(name = "election_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> locations;
}