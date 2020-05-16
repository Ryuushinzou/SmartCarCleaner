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
import java.util.stream.Collectors;

@Service
public class ShipmentConsumer {

    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private SupplyService supplyService;

    @Scheduled(cron = "0 1 1 * * *")
    public void doScheduledWork() {

        final Date currentDate = new Date();
        List<Supply> suppliesToBeProcessed = supplyService.getAllSupplies()
                .stream()
                .filter(s -> Objects.equals(s.getSupplyStatus(), SupplyStatus.CREATED))
                .filter(s -> s.getDate().before(currentDate))
                .collect(Collectors.toList());

        Map<Long, Map<Long, Long>> washingStationIdToResourceIdToQuantity = new HashMap<>();

        suppliesToBeProcessed.forEach(s -> {
            Map<Long, Long> resourceIdToQuantity = washingStationIdToResourceIdToQuantity.computeIfAbsent(s.getWashingStationId(), k -> new HashMap<>());
            s.getResourceIdToQuantity().forEach((k, v) -> resourceIdToQuantity.computeIfPresent(k, (key, val) -> v + val));
            supplyService.update(Supply.builder().id(s.getId()).supplyStatus(SupplyStatus.FINISHED).build());
        });

        washingStationIdToResourceIdToQuantity.forEach((k, v) -> washingStationService.updateQuantities(k, v));
    }
}
