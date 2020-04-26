package com.scc.app.controller;

import com.scc.app.model.Appointment;
import com.scc.app.service.AppointmentService;
import com.scc.app.service.AuthenticationService;
import org.jsondoc.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(description = "The appointments controller", name = "Appointment service")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

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

        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
        if (savedAppointment == null) {
            //TODO user already exists
        }
        return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
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

        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @ApiMethod(description = "Method that return all appointments for a user")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/appointments/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<Appointment>> getByName(
            @RequestHeader("authorization") String authorization,
            @ApiPathParam(name = "userId") @PathVariable(value = "userid") Long userId
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(appointmentService.getAppointmentsForUser(userId), HttpStatus.OK);
    }
}
