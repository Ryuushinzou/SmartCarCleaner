package com.scc.app.service;

import com.scc.app.encryption.PasswordEncrypt;
import com.scc.app.firebase.database.FbUsersDatabase;
import com.scc.app.model.Resource;
import com.scc.app.model.User;
import com.scc.app.mysql.repository.UserRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncrypt passwordEncrypt;

    private ConcurrentMap<Long, User> idToUser = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, User> idToUserTemporary = new ConcurrentHashMap<>();

        userRepository.findAll().forEach(user -> idToUserTemporary.put(user.getId(), user.clone()));

        idToUser = idToUserTemporary;
    }

    public User saveUser(User user) throws NoSuchAlgorithmException {

        user.setPassword(passwordEncrypt.encryptPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return idToUser.get(id);
    }

    public Optional<User> findUserByName(String userName) {

        return idToUser.values().stream()
                .filter(user -> Objects.equals(user.getUserName(), userName))
                .findFirst();
    }

    public Collection<User> getAllUsers() {
        return idToUser.values();
    }

    public User update(User userUpdated) {

        User user = idToUser.get(userUpdated.getId());
        if (user != null) {

            if (userUpdated.getUserName() != null) {
                user.setUserName(userUpdated.getUserName());
            }
            if (userUpdated.getEmail() != null) {
                user.setEmail(userUpdated.getEmail());
            }
            if (userUpdated.getPhoneNumber() != null) {
                user.setPhoneNumber(userUpdated.getPhoneNumber());
            }

            if (userUpdated.getCarIds() != null) {
                user.setCarIds(userUpdated.getCarIds());
            }

            if (userUpdated.getUserType() != null) {
                user.setUserType(userUpdated.getUserType());
            }
        }
        return null;
    }

}
