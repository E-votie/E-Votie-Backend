package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
