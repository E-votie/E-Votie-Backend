package com.evotie.registration_ms.voter_registration.data_entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GNDivision {
    @Id
    private String id;

    private String name;
    private Long pollingDivision;
}
