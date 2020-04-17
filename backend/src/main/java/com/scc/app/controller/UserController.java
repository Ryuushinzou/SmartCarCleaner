package com.scc.app.controller;

import com.scc.app.model.User;
import com.scc.app.service.UserService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@Api(description = "The users controller", name = "User service")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiMethod(description = "Method that allows to add a new user")
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ApiResponseObject
    User save(@ApiBodyObject @RequestBody User user) {

        User saved = null;
        try {
            saved = userService.saveUser(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return saved;
    }
}
