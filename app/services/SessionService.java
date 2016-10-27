package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.SessionDao;
import models.Session;
import models.User;
import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.ok;

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

    public String login(JsonNode jsonNode) throws Exception{
        User user = Json.fromJson(jsonNode, User.class);
        String authToken = user.getEmail()+user.getPassword();
        Session session = new Session();
        session.setEmail(user.getEmail());
        session.setAuthToken(authToken);
        sessionDao.login(session);

        return session.getAuthToken();
    }

    public Result logout(String authToken){
        Session session = null;
        try {
            session = sessionDao.getSession(authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionDao.logout(session);
    }
}
