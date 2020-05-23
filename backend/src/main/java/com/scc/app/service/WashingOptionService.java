package com.scc.app.service;

import com.scc.app.model.Vehicle;
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

    @Autowired
    private WashingOptionRepository washingOptionRepository;

    private ConcurrentMap<Long, WashingOption> idToWashingOption = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, WashingOption> idToWashingOptionTemporary = new ConcurrentHashMap<>();

        washingOptionRepository.findAll().forEach(washingOption -> idToWashingOptionTemporary.put(washingOption.getId(), washingOption.clone()));

        idToWashingOption = idToWashingOptionTemporary;
    }

    public WashingOption saveWashingOption(WashingOption washingOption) {

        return washingOptionRepository.save(washingOption);
    }

    public WashingOption getWashindOptionById(Long id) {
        return idToWashingOption.get(id);
    }

    public Collection<WashingOption> getAllWashingOptions() {
        return idToWashingOption.values();
    }

    public WashingOption update(WashingOption washingOptionUpdated) {

        WashingOption washingOption = idToWashingOption.get(washingOptionUpdated.getId());
        if (washingOption != null) {

            if (washingOptionUpdated.getName() != null) {
                washingOption.setName(washingOptionUpdated.getName());
            }
            if (washingOptionUpdated.getDescription() != null) {
                washingOption.setDescription(washingOptionUpdated.getDescription());
            }
            if (washingOptionUpdated.getRequiredResourcesIdToQuantity() != null) {
                washingOption.setRequiredResourcesIdToQuantity(washingOptionUpdated.getRequiredResourcesIdToQuantity());
            }

            if (washingOptionUpdated.getPrice() != null) {
                washingOption.setPrice(washingOptionUpdated.getPrice());
            }

            if (washingOptionUpdated.getDuration() != null) {
                washingOption.setDuration(washingOptionUpdated.getDuration());
            }
        }
        return null;
    }
}
