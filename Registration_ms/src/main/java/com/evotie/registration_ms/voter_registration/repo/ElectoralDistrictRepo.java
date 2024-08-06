package com.evotie.registration_ms.voter_registration.repo;

import com.evotie.registration_ms.voter_registration.data_entity.ElectoralDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectoralDistrictRepo extends JpaRepository<ElectoralDistrict, Long> {
    ElectoralDistrict findById(long id);
    ElectoralDistrict findByName(String name);
}
