package com.scc.app.service;

import com.scc.app.model.Appointment;
import com.scc.app.model.AppointmentStatus;
import com.scc.app.model.Supply;
import com.scc.app.model.SupplyStatus;
import com.scc.app.model.reports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SupplyService supplyService;

    //1.	Income report: Cheltuieli / Venit / Profit – pentru o perioada de timp (ma gandesc ca e un business, pana la urma ii intereseaza daca fac bani sau nu, din punct de vedere al firmei)
    public IncomeReport getIncomeReport(Long days, Long washingStationId) {

        Date after = getProperPeriod(days);

        Double totalIncome = getIncome(after, washingStationId);

        Double totalCosts = getCosts(after, washingStationId);

        Double profit = totalIncome - totalCosts;

        IncomeReport report = IncomeReport.builder().costs(totalCosts).income(totalIncome).profit(profit).build();
        report.setStartDate(after);

        return report;
    }

    //3.	Popularity report: Numar de clienti / locatie
    //6.	Popularity report: Numar clienti / locatie / perioada zi ( in medie 5 clienti pentru X locatie pana in ora 12, 10 clienti dupa ora 12)
    //7.	Popularity report: Grad de ocupare (in procente) pentru locatie pe zi (daca poti spala 100 de masini, cate au venit in ziua aia)
    public PopularityReport getPopularityReport(Long days, Long washingStationId) {

        Date after = getProperPeriod(days);

        Map<Long, List<Appointment>> washingStationIdToAppointments = new HashMap<>();

        if (washingStationId != null) {
            washingStationIdToAppointments.put(washingStationId, getAppointmentsForWashingStationAfterDate(after, washingStationId));
        } else {
            washingStationService.getAllWashingStations().forEach(washingStation -> washingStationIdToAppointments.put(washingStationId, getAppointmentsForWashingStationAfterDate(after, washingStation.getId())));
        }

        Map<Long, Map<DayPeriod, Integer>> washingStationIdToDayToAppointmentsNo = new HashMap<>();
        washingStationIdToAppointments.forEach((key, value) -> {
            Map<DayPeriod, Integer> dayPeriodToAppointmentsNo = new HashMap<>();
            value.forEach(a -> {
                dayPeriodToAppointmentsNo.computeIfPresent(getDayPeriod(a), (k, v) -> v + 1);
            });
            washingStationIdToDayToAppointmentsNo.put(key, dayPeriodToAppointmentsNo);
        });

        PopularityReport report = PopularityReport.builder().washingStationIdToAppointments(washingStationIdToAppointments).washingStationIdToDayToAppointmentsNo(washingStationIdToDayToAppointmentsNo).build();
        report.setStartDate(after);
        return report;
    }

    private DayPeriod getDayPeriod(Appointment a) {

        long hour = (a.getDateStart().getTime() % 86400000) / 3600000;
        if (hour >= 0L && hour < 8) {
            return DayPeriod.PERIOD_0_8;
        } else if (hour >= 8 && hour < 16) {
            return DayPeriod.PERIOD_8_16;
        } else {
            return DayPeriod.PERIOD_16_24;
        }
    }

    private Double getCosts(Date after, Long washingStationId) {

        List<Supply> supplies = getSupplies(after, washingStationId);
        return supplies.stream().mapToDouble(Supply::getPrice).sum();
    }

    private Double getIncome(Date after, Long washingStationId) {

        List<Appointment> appointments = getAppointments(after, washingStationId);
        return appointments.stream().mapToDouble(Appointment::getPrice).sum();
    }

    private List<Supply> getSupplies(Date after, Long washingStationId) {

        List<Supply> supplies = new ArrayList<>();
        if (washingStationId != null) {
            supplies.addAll(getSuppliesForWashingStationAfterDate(after, washingStationId));
        } else {
            supplyService.getAllSupplies().forEach(washingStation -> supplies.addAll(getSuppliesForWashingStationAfterDate(after, washingStation.getId())));
        }
        return supplies;
    }

    private List<Appointment> getAppointments(Date after, Long washingStationId) {

        List<Appointment> appointments = new ArrayList<>();

        if (washingStationId != null) {
            appointments.addAll(getAppointmentsForWashingStationAfterDate(after, washingStationId));
        } else {
            washingStationService.getAllWashingStations().forEach(washingStation -> appointments.addAll(getAppointmentsForWashingStationAfterDate(after, washingStation.getId())));
        }
        return appointments;
    }

    private Date getProperPeriod(Long days) {

        if (days != null) {
            return new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(days));
        } else {
            return new Date(0);
        }
    }

    private List<Appointment> getAppointmentsForWashingStationAfterDate(Date after, Long washingStationId) {

        return appointmentService.getAllAppointments().stream()
                .filter(a -> Objects.equals(a.getWashingStationId(), washingStationId))
                .filter(a -> Objects.equals(a.getAppointmentStatus(), AppointmentStatus.FINISHED))
                .filter(a -> a.getDateStart().after(after))
                .collect(Collectors.toList());
    }

    private List<Supply> getSuppliesForWashingStationAfterDate(Date after, Long washingStationId) {

        return supplyService.getAllSupplies().stream()
                .filter(a -> Objects.equals(a.getWashingStationId(), washingStationId))
                .filter(a -> Objects.equals(a.getSupplyStatus(), SupplyStatus.FINISHED))
                .filter(a -> a.getDate().after(after))
                .collect(Collectors.toList());
    }

    public Report getReport(ReportType reportType, Long days, Long washingStationId) {

        if (Objects.equals(reportType, ReportType.INCOME_REPORT)) {
            return getIncomeReport(days, washingStationId);
        } else if (Objects.equals(reportType, ReportType.POPULARITY_REPORT)) {
            return getPopularityReport(days, washingStationId);
        }
        return null;
    }
}
