package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.DTO.Voter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@PrimaryKeyJoinColumn(name = "VoterID")
public class VerificationOfficer {

    @Id
    private String verificationOfficerId; //save voter id

    @OneToMany(mappedBy = "verificationOfficer")
    private List<Party> parties;

}
