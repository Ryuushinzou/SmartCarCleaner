package com.scc.app.service;

import com.scc.app.model.WashingOption;
import com.scc.app.model.WashingStation;
import com.scc.app.mysql.repository.WashingStationRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class WashingStationService {

    //TODO add firebase

    @Autowired
    private WashingStationRepository washingStationRepository;

    private ConcurrentMap<Long, WashingStation> idToWashingStation = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, WashingStation> idToWashingStationTemporary = new ConcurrentHashMap<>();

        if (Utils.isFirebaseDatabase()) {
            //TODO get all firebase
        } else {
            washingStationRepository.findAll().forEach(washingStation -> idToWashingStationTemporary.put(washingStation.getId(), washingStation.clone()));
        }

        idToWashingStation = idToWashingStationTemporary;
    }

    public WashingStation saveWashingStation(WashingStation washingStation) {

        if (Utils.isFirebaseDatabase()) {
            // TODO add to firebase db
        } else {
            return washingStationRepository.save(washingStation);
        }
        return null;
    }

    public WashingStation getWashingStationById(Long id) {
        return idToWashingStation.get(id);
    }

    public Collection<WashingStation> getAllWashingStations() {
        return idToWashingStation.values();
    }
}
