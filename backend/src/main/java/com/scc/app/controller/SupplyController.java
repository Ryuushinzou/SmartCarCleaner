package com.scc.app.controller;

import com.scc.app.model.Supply;
import com.scc.app.service.AuthenticationService;
import com.scc.app.service.SupplyService;
import org.jsondoc.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(description = "The supplies controller", name = "Supply service")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private AuthenticationService authenticationService;

    @ApiMethod(description = "Method that allows to add a new suppply")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/supplies", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Supply> save(
            @RequestHeader("authorization") String authorization,
            @ApiBodyObject @RequestBody Supply supply
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasWriteAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Supply saved = supplyService.saveSupply(supply);
        if (saved == null) {
            //TODO user already exists
        }
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @ApiMethod(description = "Method that return all the supplies")
    @ApiHeaders(headers = {@ApiHeader(name = "authorization", allowedvalues = "", description = "")})
    @RequestMapping(value = "/supplies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    @ResponseBody
    ResponseEntity<Collection<Supply>> getAll(
            @RequestHeader("authorization") String authorization
    ) {

        if (!authenticationService.authenticatedUser(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!authenticationService.hasReadAccess(authorization)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(supplyService.getAllSupplies(), HttpStatus.OK);
    }
}
