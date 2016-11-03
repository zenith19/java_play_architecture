package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.UserDao;
import helpers.NoTxJPA;
import models.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserService {
    private final UserDao userDao;
    private final NoTxJPA jpa;

    @Inject
    public UserService(UserDao userDao, NoTxJPA jpa) {
        this.userDao = userDao;
        this.jpa = jpa;
    }

    private String encryptPassword(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        String encodedPassword = Base64.getEncoder().encodeToString(passwordBytes);

        return encodedPassword;
    }

    public User create(User inputUser) {
        return jpa.withDefaultEm(() -> {
                    String password = inputUser.getPassword();
                    inputUser.setPassword(encryptPassword(inputUser.getPassword()));
                    User user = userDao.create(inputUser);
                    user.setPassword(password);
                    return user;
                }
        );
    }
}
