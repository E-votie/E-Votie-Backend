package com.e_votie.Party_ms.Model;

import com.e_votie.Party_ms.Config.LobFieldSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer documentId;
    private String documentName;

    @Column(columnDefinition = "TEXT")
     private String documentUrl;

    private String documentType;
    private String documentUploadedDate;

    @ManyToOne
    @JsonBackReference
    private Party party;
}
