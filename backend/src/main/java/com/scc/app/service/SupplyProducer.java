package com.scc.app.service;

import com.scc.app.model.Supply;
import com.scc.app.model.SupplyStatus;
import com.scc.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class SupplyProducer {

    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private ResourceService resourceService;

    @Scheduled(cron = "0 1 1 * * *")
    public void doScheduledWork() {

        washingStationService.getAllWashingStations().forEach(ws -> {

            Map<Long, Long> availableResourcesIdToQuantity = ws.getResourcesIdToQuantity();
            Map<Long, Long> neededResourcesIdToQuantity = new HashMap<>();
            availableResourcesIdToQuantity.forEach((k, v) -> {
                if (v < Constants.RESOURCE_MIN_THRESHOLD) {
                    neededResourcesIdToQuantity.put(k, Constants.RESOURCE_MIN_THRESHOLD * Constants.RESOURCE_MULTIPLIER);
                }
            });

            if (!CollectionUtils.isEmpty(neededResourcesIdToQuantity)) {
                double totalPrice = neededResourcesIdToQuantity.keySet().stream().map(id -> resourceService.getResourceById(id)).mapToDouble(r -> neededResourcesIdToQuantity.get(r.getId()) * r.getPricePerUnit()).sum();
                Supply newSupply = Supply.builder().washingStationId(ws.getId())
                        .price(totalPrice)
                        .resourceIdToQuantity(neededResourcesIdToQuantity)
                        .date(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                        .supplyStatus(SupplyStatus.CREATED)
                        .build();

                supplyService.saveSupply(newSupply);
            }
        });
    }
}
