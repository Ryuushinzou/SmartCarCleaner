package com.scc.app.service;


import com.scc.app.model.*;
import com.scc.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AppointmentsPossibilitiesService {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private WashingOptionService washingOptionService;

    public List<Appointment> getAppointmentPossibilities(Integer appointmentPossibilitiesNo, Double latUser, Double longUser, Long vehicleId, List<Long> washingOptionIds, Long washingStationId, Date appointmentDate) {

        List<Appointment> appointmentPossibilities = new ArrayList<>();
        List<WashingOption> washingOptions = washingOptionIds.stream().map(id -> washingOptionService.getWashindOptionById(id)).collect(Collectors.toList());
        long totalDuration = washingOptions.stream().mapToLong(WashingOption::getDuration).sum();
        double price = washingOptions.stream().mapToDouble(WashingOption::getPrice).sum();

        if (washingStationId != null) {
            appointmentPossibilities.addAll(makeAppointmentsPossibilitiesForWashingStation(appointmentPossibilitiesNo, latUser, longUser, vehicleId, washingOptionIds, washingOptions, washingStationId, price, totalDuration, appointmentDate));
        } else {
            Comparator<WashingStation> distanceComparator = (ws1, ws2) -> directionService.getDurationBetweenPoints(latUser, longUser, ws1.getLatPos(), ws2.getLongPos()).compareTo(directionService.getDurationBetweenPoints(latUser, longUser, ws2.getLatPos(), ws2.getLongPos()));
            List<WashingStation> availableWashingStations = washingStationService.getAllWashingStations().stream()
                    .filter(ws -> hasEnoughResources(ws, washingOptions))
                    .sorted(distanceComparator)
                    .collect(Collectors.toList());

            for (WashingStation washingStation : availableWashingStations) {
                appointmentPossibilities.addAll(makeAppointmentsPossibilitiesForWashingStation(appointmentPossibilitiesNo, latUser, longUser, vehicleId, washingOptionIds, washingOptions, washingStation.getId(), price, totalDuration, appointmentDate));
                if (appointmentPossibilities.size() >= appointmentPossibilitiesNo) {
                    appointmentPossibilities = appointmentPossibilities.subList(0, appointmentPossibilitiesNo);
                    break;
                }
            }
        }

        return appointmentPossibilities;
    }

    private Collection<? extends Appointment> makeAppointmentsPossibilitiesForWashingStation(Integer appointmentPossibilitiesNo, Double latUser, Double longUser, Long vehicleId, List<Long> washingOptionIds, List<WashingOption> washingOptions, Long washingStationId, double price, long totalDuration, Date appointmentDate) {

        List<Appointment> appointmentPossibilities = new ArrayList<>();

        WashingStation washingStation = washingStationService.getWashingStationById(washingStationId);
        boolean enoughResources = hasEnoughResources(washingStation, washingOptions);

        if (!enoughResources) {
            return Collections.emptyList();
        }

        int noTries = 0;

        while (appointmentPossibilities.size() < appointmentPossibilitiesNo && noTries < Constants.MAX_TRIES) {

            final Date finalStartAppointmentDate = adjustAppointmentDate(appointmentDate, latUser, longUser, washingStation.getLatPos(), washingStation.getLongPos());
            final Date finalEndAppointmentDate = new Date(finalStartAppointmentDate.getTime() + totalDuration);

            long concurrentAppointments = appointmentService.getAppointmentsByWashingStation(washingStationId)
                    .stream()
                    .filter(a -> !Objects.equals(a.getAppointmentStatus(), AppointmentStatus.FINISHED))
                    .filter(a -> Objects.equals(a.getDateStart(), finalStartAppointmentDate))
                    .filter(a -> a.getDateStart().before(finalStartAppointmentDate) && a.getDateEnd().after(finalStartAppointmentDate))
                    .filter(a -> a.getDateStart().after(finalStartAppointmentDate) && a.getDateStart().before(finalEndAppointmentDate))
                    .count();

            if (concurrentAppointments < washingStation.getSlots()) {
                Appointment appointmentPossibility = Appointment.builder()
                        .vehicleId(vehicleId)
                        .washingStationId(washingStationId)
                        .dateStart(finalStartAppointmentDate)
                        .dateEnd(finalEndAppointmentDate)
                        .price(price)
                        .washingOptionsIds(washingOptionIds)
                        .appointmentStatus(AppointmentStatus.CREATED)
                        .build();
                appointmentPossibilities.add(appointmentPossibility);
            }

            appointmentDate = new Date(finalStartAppointmentDate.getTime() + Constants.DELAY);
            noTries++;
        }

        return appointmentPossibilities;
    }

    private Date adjustAppointmentDate(Date appointmentDate, Double startLat, Double startLong, Double endLat, Double endLong) {

        Long durationToWashingStation = directionService.getDurationBetweenPoints(startLat, startLong, endLat, endLong);
        if (appointmentDate == null) {
            if (durationToWashingStation != null) {
                return new Date(System.currentTimeMillis() + durationToWashingStation);
            } else {
                return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
            }
        } else {
            if (durationToWashingStation != null) {
                if (appointmentDate.getTime() - System.currentTimeMillis() < durationToWashingStation) {
                    return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
                }
            }
        }
        return appointmentDate;

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
