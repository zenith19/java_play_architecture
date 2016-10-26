package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import daos.UserDao;
import models.User;
import play.libs.Json;


/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserService {
    UserDao userDao = new UserDao();

    public JsonNode registration(JsonNode jsonNode){
        User user = Json.fromJson(jsonNode, User.class);

        return userDao.registration(user);
    }

    public String login(JsonNode jsonNode){
        User user = Json.fromJson(jsonNode, User.class);

        return userDao.login(user.getEmail(), user.getPassword());
    }
}
