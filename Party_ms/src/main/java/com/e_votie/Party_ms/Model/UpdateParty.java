package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.DTO.Voter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class UpdateParty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer updateId;

    private String oldValue;
    private String newValue;
    private LocalDateTime updateDate;
    private String updateDescription;
    private String changeRequestSubject;
    private String changeRequestDescription;


    private Voter verificationOfficerId;

}
