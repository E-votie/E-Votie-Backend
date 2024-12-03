package com.e_votie.Party_ms.Controller;

import com.e_votie.Party_ms.Model.History;
import com.e_votie.Party_ms.Service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // Create
    @PostMapping
    public ResponseEntity<History> createHistory(@RequestBody History history) {
        try {
            History createdHistory = historyService.createHistory(history);
            return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<History>> getAllHistory() {
        try {
            List<History> histories = historyService.getAllHistory();
            return new ResponseEntity<>(histories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read By ID
    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable int id) {
        Optional<History> history = historyService.getHistoryById(id);
        return history.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<History> updateHistory(@PathVariable int id, @RequestBody History updatedHistory) {
        try {
            History updated = historyService.updateHistory(id, updatedHistory);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHistory(@PathVariable int id) {
        try {
            historyService.deleteHistory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
