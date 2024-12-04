package com.e_votie.Party_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestId;
    private String receiverNIC;
    private String receiverName;
    private String requestState; //pending, accepted
    private String requestInitiatorNIC;
    private String createdAt;
    @ManyToOne
    private Party party;
}
