package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.History;
import com.e_votie.Party_ms.Repository.HistoryRepository;
import com.e_votie.Party_ms.Repository.PartyRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PartyRepository partyRepository;

    // Create
    public History createHistory(History history) {
        return historyRepository.save(history);
    }

    // Read All
    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    // Read By ID
    public Optional<History> getHistoryById(int id) {
        return historyRepository.findById(id);
    }

    // Update
    public History updateHistory(int id, History updatedHistory) throws Exception {
        History history = historyRepository.findById(id).orElseThrow(() -> new Exception("History not found"));
        history.setOldValue(updatedHistory.getOldValue());
        history.setNewValue(updatedHistory.getNewValue());
        history.setDateTime(updatedHistory.getDateTime());
        history.setParty(updatedHistory.getParty());
        return historyRepository.save(history);
    }

    // Delete
    public void deleteHistory(int id) {
        historyRepository.deleteById(id);
    }
}
