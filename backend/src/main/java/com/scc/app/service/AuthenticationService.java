package com.scc.app.service;

import com.scc.app.authentication.TokenGenerator;
import com.scc.app.encryption.PasswordEncrypt;
import com.scc.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordEncrypt passwordEncrypt;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenGenerator tokenGenerator;

    public String authenticateUser(String userName, String password) throws NoSuchAlgorithmException {

        String encryptedPassword = passwordEncrypt.encryptPassword(password);
        Optional<User> userByName = userService.findUserByName(userName);

        if (userByName.isPresent()) {
            User user = userByName.get();
            if (Objects.equals(user.getPassword(), encryptedPassword)) {
                return tokenGenerator.authenticateUser(user.getUserName(), user.getUserType());
            }
        }

        return null;
    }
}
