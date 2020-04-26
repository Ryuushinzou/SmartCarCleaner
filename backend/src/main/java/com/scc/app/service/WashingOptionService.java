package com.scc.app.service;

import com.scc.app.model.WashingOption;
import com.scc.app.mysql.repository.WashingOptionRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class WashingOptionService {

    //TODO add firebase

    @Autowired
    private WashingOptionRepository washingOptionRepository;

    private ConcurrentMap<Long, WashingOption> idToWashingOption = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, WashingOption> idToWashingOptionTemporary = new ConcurrentHashMap<>();

        if (Utils.isFirebaseDatabase()) {
            //TODO get all firebase
        } else {
            washingOptionRepository.findAll().forEach(washingOption -> idToWashingOptionTemporary.put(washingOption.getId(), washingOption.clone()));
        }

        idToWashingOption = idToWashingOptionTemporary;
    }

    public WashingOption saveWashingOption(WashingOption washingOption) {

        if (Utils.isFirebaseDatabase()) {
            // TODO add to firebase db
        } else {
            return washingOptionRepository.save(washingOption);
        }
        return null;
    }

    public WashingOption getWashindOptionById(Long id) {
        return idToWashingOption.get(id);
    }

    public Collection<WashingOption> getAllWashingOptions() {
        return idToWashingOption.values();
    }
}
