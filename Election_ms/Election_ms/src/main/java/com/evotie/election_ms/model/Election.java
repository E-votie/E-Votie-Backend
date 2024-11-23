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
    private String sinhalaName;
    private String description;
    private String sinhalaDescription;
    private String type;
    private String status;
    private LocalDateTime electionPublishDate; //6 months before the election
    private LocalDateTime electionStartDate; //5 months before the election
    private LocalDateTime electionNominationCallingStartDate; //3 months before the election
    private LocalDateTime electionNominationCallingEndDate; //2 months before the election
    private LocalDateTime electionVoterRegistrationStartDate; //1 month before the election
    private LocalDateTime electionVoterRegistrationEndDate; //1 month before the election
    private LocalDateTime electionCampaignStartDate; //2 weeks before the election
    private LocalDateTime electionCampaignEndDate; //1 week before the election
    private LocalDateTime electionDayStartDate; //8 am on the election day
    private LocalDateTime electionDayEndDate; //5 pm on the election day
    private LocalDateTime electionEndDate; //1 day after the election
    private String contractAddress;
    @ManyToMany
    @JoinTable(
            name = "election_location",
            joinColumns = @JoinColumn(name = "election_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> locations;
}