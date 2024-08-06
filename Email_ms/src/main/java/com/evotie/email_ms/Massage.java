package com.evotie.email_ms;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Contact")
@Data
public class Massage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_contact_number")
    private String to;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "sent_date")
    private Date sentDate;
}
