package com.scc.app.controller;

import com.scc.app.model.Appointment;
import com.scc.app.model.AppointmentStatus;
import com.scc.app.service.AppointmentService;
import com.scc.app.service.AppointmentsPossibilitiesService;
import com.scc.app.service.AuthenticationService;
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
@Api(description = "The appointments controller", name = "Appointment service")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentsPossibilitiesService appointmentsPossibilitiesService;

    @Autowired
    private AuthenticationService authenticationService;

    @ApiMethod(description = "Method that allows to add a new appointment")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Appointment> save(
            @RequestHeader("authorization") String authorization,
            @ApiBodyObject @RequestBody Appointment appointment
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasWriteAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        appointment.setAppointmentStatus(AppointmentStatus.NOT_PAID);

        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
        if (savedAppointment == null) {
            //TODO user already exists
        }
        return ResponseEntity.ok(savedAppointment);
    }

    @ApiMethod(description = "Method that return all the appointments")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<Appointment>> getAll(
            @RequestHeader("authorization") String authorization
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasReadAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }


    @ApiMethod(description = "Method that sets an appointment to in progress status")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments/{appointmentId}/inProgress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<String> setInProgress(
            @RequestHeader("authorization") String authorization,
            @ApiPathParam(name = "appointmentId") @PathVariable(value = "appointmentId") Long appointmentId
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasReadAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        appointmentService.update(Appointment.builder().id(appointmentId).appointmentStatus(AppointmentStatus.IN_PROGRESS).build());

        return ResponseEntity.ok(Constants.DONE);
    }

    @ApiMethod(description = "Method that sets an appointment to finished status")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments/{appointmentId}/finished", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<String> setFinished(
            @RequestHeader("authorization") String authorization,
            @ApiPathParam(name = "appointmentId") @PathVariable(value = "appointmentId") Long appointmentId
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasReadAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        appointmentService.update(Appointment.builder().id(appointmentId).appointmentStatus(AppointmentStatus.FINISHED).build());

        return ResponseEntity.ok(Constants.DONE);
    }

    @ApiMethod(description = "Method that return all appointments for a user")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<Appointment>> getByName(
            @RequestHeader("authorization") String authorization,
            @ApiPathParam(name = "userId") @PathVariable(value = "userId") Long userId
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
    }

    @ApiMethod(description = "Method that returns possibilities of appointments")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments/possibilities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<Appointment>> getAppointmentPossibilities(
            @RequestHeader("authorization") String authorization,

            @ApiQueryParam(name = "latUser", description = "The latitude of the user")
            @RequestParam("latUser") Double latUser,

            @ApiQueryParam(name = "longUser", description = "The longitude of the user")
            @RequestParam("longUser") Double longUser,

            @ApiQueryParam(name = "vehicleId", description = "The id of the vehicle")
            @RequestParam("vehicleId") Long vehicleId,

            @ApiQueryParam(name = "washingOptionIds", description = "The washing options ids")
            @RequestParam("washingOptionIds") List<Long> washingOptionIds,

            @ApiQueryParam(name = "washingStationId", description = "The washing station id", required = false)
            @RequestParam(value = "washingStationId", required = false) Long washingStationId,

            @ApiQueryParam(name = "date", description = "The date", required = false)
            @RequestParam(value = "date", required = false) Date appointmentDate,

            @ApiQueryParam(name = "appointmentPossibilitiesNo", description = "The number of the required possibilities", required = false)
            @RequestParam(value = "appointmentPossibilitiesNo", required = false) Integer appointmentPossibilitiesNo
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (appointmentPossibilitiesNo == null || appointmentPossibilitiesNo < 0) {
            appointmentPossibilitiesNo = Constants.DEFAULT_APPOINTMENTS_POSSIBILITIES;
        }

        return ResponseEntity.ok(appointmentsPossibilitiesService.getAppointmentPossibilities(appointmentPossibilitiesNo, latUser, longUser, vehicleId, washingOptionIds, washingStationId, appointmentDate));
    }
}
