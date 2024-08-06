package com.evotie.registration_ms.voter_registration.repo;

import com.evotie.registration_ms.voter_registration.data_entity.TempContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempContactInfoRepo extends JpaRepository<TempContactInfo, Long> {
    TempContactInfo findByHash(String hash);
    TempContactInfo findByNIC(String nic);
    TempContactInfo findByOTP(String otp);
}
