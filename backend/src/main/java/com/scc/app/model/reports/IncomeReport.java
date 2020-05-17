package com.scc.app.model.reports;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class IncomeReport extends Report {

    private Double income;
    private Double costs;
    private Double profit;
}
