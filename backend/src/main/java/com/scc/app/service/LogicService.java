package com.scc.app.service;


import com.scc.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogicService {


    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private WashingOptionService washingOptionService;

    @Autowired
    private ResourceService resourceService;

    public void method(Long userId, Double latUser, Double longUser, Long vehicleId, List<Long> washingOptionIds, Long washingStationId, Date specificDate) {

        if (washingStationId != null) {
            User user = userService.getUserById(userId);
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            List<WashingOption> washingOptions = washingOptionIds.stream().map(id -> washingOptionService.getWashindOptionById(id)).collect(Collectors.toList());
            WashingStation washingStation = washingStationService.getWashingStationById(washingStationId);
            boolean enoughResources = hasEnoughResources(washingStation, washingOptions);

            if (!enoughResources) {
                return; //TODO treat this
            }


            List<Resource> requiredResources = washingOptions.stream().flatMap(wo -> wo.getRequiredResourcesIdToQuantity().values().stream()).map(rid -> resourceService.getResourceById(rid)).collect(Collectors.toList());
            /*
                Steps:
                1. get user
                2. get vehicle
                3. get all washing options and calculate price
                4. check if washing station has enough resources
                5. get timeTable of washing station
                6. check in timetable if a new appointment can be created at specificDate(if exists)
                7. if no for 6 check first available possibilities for appointment

             */
        } else {

        }
    }

    private boolean hasEnoughResources(WashingStation washingStation, List<WashingOption> washingOptions) {

        Map<Long, Long> availableResourcesIdToQuantity = washingStation.getResourcesIdToQuantity();
        Map<Long, Long> requiredResourceIdToQuantity = new HashMap<>();
        for (WashingOption washingOption : washingOptions) {
            washingOption.getRequiredResourcesIdToQuantity().forEach((k, v) -> requiredResourceIdToQuantity.computeIfPresent(k, (key, val) -> val + v));
        }

        for (Map.Entry<Long, Long> entry : requiredResourceIdToQuantity.entrySet()) {
            Long resourceId = entry.getKey();
            Long requiredQuantity = entry.getValue();
            Long availableQuantity = availableResourcesIdToQuantity.get(resourceId);
            if (availableQuantity == null || availableQuantity < requiredQuantity) {
                return false;
            }
        }
        return true;
    }
}
