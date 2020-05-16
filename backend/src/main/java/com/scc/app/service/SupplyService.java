package com.scc.app.service;

import com.scc.app.model.Resource;
import com.scc.app.model.Supply;
import com.scc.app.model.SupplyStatus;
import com.scc.app.mysql.repository.SupplyRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class SupplyService {

    //TODO add firebase

    @Autowired
    private SupplyRepository supplyRepository;

    private ConcurrentMap<Long, Supply> idToSupply = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, Supply> idToSupplyTemporary = new ConcurrentHashMap<>();

        if (Utils.isFirebaseDatabase()) {
            //TODO get all firebase
        } else {
            supplyRepository.findAll().forEach(supply -> idToSupplyTemporary.put(supply.getId(), supply.clone()));
        }

        idToSupply = idToSupplyTemporary;
    }

    public Supply saveSupply(Supply supply) {

        if (Utils.isFirebaseDatabase()) {
            // TODO add to firebase db
        } else {
            return supplyRepository.save(supply);
        }
        return null;
    }

    public Supply getResourceById(Long id) {
        return idToSupply.get(id);
    }

    public Collection<Supply> getAllSupplies() {
        return idToSupply.values();
    }

    public void update(Supply supplyUpdated) {

        Supply supply = idToSupply.get(supplyUpdated.getId());
        if (supply != null) {
            if (supplyUpdated.getResourceIdToQuantity() != null) {
                supply.setResourceIdToQuantity(supplyUpdated.getResourceIdToQuantity());
            }

            if (supplyUpdated.getDate() != null) {
                supply.setDate(supplyUpdated.getDate());
            }

            if (supplyUpdated.getSupplyStatus() != null) {
                supply.setSupplyStatus(supplyUpdated.getSupplyStatus());
            }

            if (supplyUpdated.getWashingStationId() != null) {
                supply.setWashingStationId(supplyUpdated.getWashingStationId());
            }

            if (supplyUpdated.getPrice() != null) {
                supply.setPrice(supplyUpdated.getPrice());
            }
            supplyRepository.save(supply);
        }
    }
}
