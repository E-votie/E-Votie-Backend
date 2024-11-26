package com.e_votie.Announcement_ms.Controller;

import com.e_votie.Announcement_ms.Model.Announcement;
import com.e_votie.Announcement_ms.Service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    //Endpoint to add new announcement
    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
        Announcement newAnnouncement = announcementService.createAnnouncement(announcement);
        return new ResponseEntity<>(newAnnouncement, HttpStatus.CREATED);
    }

    //Endpoint to get all announcements
    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    //Endpoint to get announcement by announcement id
    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Integer id) {
        Optional<Announcement> announcement = announcementService.getAnnouncementById(id);
        if (announcement.isPresent()) {
            return new ResponseEntity<>(announcement.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Endpoint to update announcement
    @PutMapping("/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcementDetails) {
        try {
            Announcement updatedAnnouncement = announcementService.updateAnnouncement(id, announcementDetails);
            return new ResponseEntity<>(updatedAnnouncement, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete Announcement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Integer id) {
        try {
            announcementService.deleteAnnouncement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
