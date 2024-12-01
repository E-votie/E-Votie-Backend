package com.e_votie.Announcement_ms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer announcementId;

    private String announcement;
    private LocalDateTime publishedDate;

    @ElementCollection
    private List<String> attachments;

}
