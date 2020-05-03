package com.scc.app.controller;

import com.scc.app.model.User;
import com.scc.app.model.Vehicle;
import com.scc.app.service.VehicleService;
import com.scc.app.service.AuthenticationService;
import org.jsondoc.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@Api(description = "The vehicles controller", name = "Vehicle service")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AuthenticationService authenticationService;

    @ApiMethod(description = "Method that allows to add a new vehicle")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Vehicle> save(
            @RequestHeader("authorization") String authorization,
            @ApiBodyObject @RequestBody Vehicle vehicle
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasWriteAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        if (savedVehicle == null) {
            //TODO user already exists
        }
        return new ResponseEntity<>(savedVehicle, HttpStatus.OK);
    }

    @ApiMethod(description = "Method that return all the vehicles")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/vehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<Vehicle>> getAll(
            @RequestHeader("authorization") String authorization
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasReadAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @ApiMethod(description = "Method that deletes a vehicle")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/vehicles/{vehicleId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Vehicle> deleteById(
            @RequestHeader("authorization") String authorization,
            @ApiPathParam(name = "vehicleId") @PathVariable(value = "vehicleId") Long vehicleId
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(vehicleService.deleteById(vehicleId), HttpStatus.OK);
    }
}
