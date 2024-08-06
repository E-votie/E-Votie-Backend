package com.example.partyservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promises")
public class Promise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer promiseId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String category;
    private String status;

    @ManyToOne
    @JoinColumn(name = "manifesto_id", nullable = false)
    private Manifesto manifesto;

}