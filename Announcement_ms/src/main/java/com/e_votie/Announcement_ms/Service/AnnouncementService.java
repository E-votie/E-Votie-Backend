package com.e_votie.Announcement_ms.Service;

import com.e_votie.Announcement_ms.Model.Announcement;
import com.e_votie.Announcement_ms.Repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement createAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Optional<Announcement> getAnnouncementById(Integer id) {
        return announcementRepository.findById(id);
    }

    public Announcement updateAnnouncement(Integer id, Announcement announcementDetails) throws Exception {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new Exception("Announcement not found"));

        announcement.setAnnouncement(announcementDetails.getAnnouncement());
        announcement.setPublishedDate(announcementDetails.getPublishedDate());
        announcement.setAttachments(announcementDetails.getAttachments());

        return announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(Integer id) throws Exception {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new Exception("Announcement not found"));

        announcementRepository.delete(announcement);
    }
}
