package com.evotie.election_ms.dto;

import com.evotie.election_ms.model.Location;

import java.util.List;

public class PollingStationRequest {
    private List<Location> pollingStations;

    public List<Location> getPollingStations() {
        return pollingStations;
    }

    public void setPollingStations(List<Location> pollingStations) {
        this.pollingStations = pollingStations;
    }
}
