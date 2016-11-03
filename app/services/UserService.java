package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.UserDao;
import models.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserService {
    private final UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    private String encryptPassword(String password){
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        String encodedPassword = Base64.getEncoder().encodeToString(passwordBytes);

        return encodedPassword;
    }

    public User create(User user){
        String password = user.getPassword();
        user.setPassword(encryptPassword(user.getPassword()));
        User user1 = userDao.create(user);
        user1.setPassword(password);
        return user1;
    }
}
