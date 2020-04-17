package com.scc.app.controller;


import com.scc.app.service.AuthenticationService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@Api(description = "The login controller", name = "Login service")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiMethod(description = "Method that allows a user to login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam("userName")
            @ApiQueryParam(name = "userName", description = "The name of the user") String userName,

            @ApiQueryParam(name = "lastName", description = "The password")
            @RequestParam("password") String password
    ) {

        String jwt = null;
        try {
            jwt = authenticationService.authenticateUser(userName, password);
        } catch (NoSuchAlgorithmException e) {
            return "401";
        }

        if (jwt == null) {
            return "401";
        }
        return jwt;
    }
}
