package com.scc.app.controller;

import com.scc.app.model.WashingStation;
import com.scc.app.service.AuthenticationService;
import com.scc.app.service.WashingStationService;
import org.jsondoc.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(description = "The washing stations controller", name = "Washing stations service")
public class WashingStationController {

    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private AuthenticationService authenticationService;

    @ApiMethod(description = "Method that allows to add a new washing station")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/washingStations", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<WashingStation> save(
            @RequestHeader("authorization") String authorization,
            @ApiBodyObject @RequestBody WashingStation washingStation
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasWriteAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        WashingStation saved = washingStationService.saveWashingStation(washingStation);
        if (saved == null) {
            //TODO user already exists
        }
        return ResponseEntity.ok(saved);
    }

    @ApiMethod(description = "Method that return all the washing station")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/washingStaions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<WashingStation>> getAll(
            @RequestHeader("authorization") String authorization
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasReadAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(washingStationService.getAllWashingStations());
    }
}
