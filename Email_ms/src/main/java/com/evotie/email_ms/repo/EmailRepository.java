package com.evotie.email_ms.repo;

import com.evotie.email_ms.Model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}
