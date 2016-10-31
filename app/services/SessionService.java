package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.SessionDao;
import models.Session;
import models.User;
import play.libs.Json;

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

    public JsonNode login(JsonNode jsonNode){
        User tempUserVar = Json.fromJson(jsonNode, User.class);
        User user = sessionDao.getUser(tempUserVar.getEmail());

        if (user == null || !user.getPassword().equals(tempUserVar.getPassword())) {
            return null;
        }
        // TODO It has security risk. don't save raw password in Database. Please use random string.
        String authToken = user.getEmail()+user.getPassword();
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
