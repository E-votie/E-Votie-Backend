package com.evotie.officeportal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PEDutylog")

public class PEDutyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "Name")
    private String Name;

    @Column(name = "ElectionYear")
    private Date ElectionYear;

    @Column(name = "ElectionType")
    private String ElectionType;

    @Column(name = "From")
    private Date From;

    @Column(name = "To")
    private Date To;
}
