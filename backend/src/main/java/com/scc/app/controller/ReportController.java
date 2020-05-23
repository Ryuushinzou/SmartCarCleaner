package com.scc.app.controller;

import com.scc.app.model.Appointment;
import com.scc.app.model.reports.Report;
import com.scc.app.model.reports.ReportType;
import com.scc.app.service.AuthenticationService;
import com.scc.app.service.ReportsService;
import com.scc.app.utils.Constants;
import org.jsondoc.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@Api(description = "The reports controller", name = "Reports service")
public class ReportController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ReportsService reportsService;

    @ApiMethod(description = "Method that returns all reports types")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/reports/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<ReportType[]> getAppointmentPossibilities(
            @RequestHeader("authorization") String authorization
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(ReportType.values());
    }

    @ApiMethod(description = "Method that retrieves the reports")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/reports/{reportType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Report> getAppointmentPossibilities(
            @RequestHeader("authorization") String authorization,

            @ApiPathParam(name = "reportType")
            @PathVariable(value = "reportType") ReportType reportType,

            @ApiQueryParam(name = "days", description = "The period of the report", required = false)
            @RequestParam("days") Long days,

            @ApiQueryParam(name = "washingStationId", description = "The id of the washing station for the report", required = false)
            @RequestParam("washingStationId") Long washingStationId
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(reportsService.getReport(reportType, days, washingStationId));
    }
}
