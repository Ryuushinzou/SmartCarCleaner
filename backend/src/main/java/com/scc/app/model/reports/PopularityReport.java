package com.scc.app.model.reports;

import com.scc.app.model.Appointment;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PopularityReport extends Report {

    private Map<Long, List<Appointment>> washingStationIdToAppointments;
    private Map<Long, Map<DayPeriod, Integer>> washingStationIdToDayToAppointmentsNo;
}
