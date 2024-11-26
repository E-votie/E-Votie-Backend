package com.e_votie.Party_ms.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int historyId;
    private String oldValue;
    private String newValue;
    private LocalDate date;


}