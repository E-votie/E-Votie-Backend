package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Document;
import com.e_votie.Party_ms.Model.Party;
import feign.Param;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Modifying
    @Query(value = "DELETE FROM document WHERE party_registration_id = :partyId AND document_type = :fileType", nativeQuery = true)
    void deleteByPartyAndDocumentType(@Param("partyId") Integer partyId, @Param("fileType") String fileType);

}