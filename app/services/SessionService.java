package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.SessionDao;
import models.Session;
import models.User;
import play.libs.Json;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionService {
    private SessionDao sessionDao;

    @Inject
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    private String decryptPassword(String password){
        byte[] decoded = Base64.getDecoder().decode(password);

        return new String(decoded, StandardCharsets.UTF_8);
    }

    public JsonNode login(User user){
        User tempUser = sessionDao.getUser(user.getEmail());
        String password = decryptPassword(tempUser.getPassword());

        if ((tempUser == null) || !password.equals(user.getPassword())) {
            return null;
        }
        // TODO It has security risk. don't save raw password in Database. Please use random string.
        String authToken = UUID.randomUUID().toString();
        Session session = new Session();
        session.setEmail(user.getEmail());
        session.setAuthToken(authToken);
        sessionDao.login(session);

        return Json.toJson(session.getAuthToken());
    }

    public void logout(String authToken) throws Exception{
        Session session = sessionDao.getSession(authToken);
        if (session == null) {
            throw new Exception();
        }
        sessionDao.logout(session);
    }

    public Session authenticate(String authToken) {

        return sessionDao.getSession(authToken);
    }
}
