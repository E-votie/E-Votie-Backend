package com.e_votie.Announcement_ms.Repository;

import com.e_votie.Announcement_ms.Model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
