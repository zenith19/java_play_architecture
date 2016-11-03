package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.SessionDao;
import helpers.NoTxJPA;
import models.Session;
import models.User;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Http;
import utility.Utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionService {
    private final SessionDao sessionDao;
    private final MessagesApi messagesApi;
    private final NoTxJPA jpa;

    @Inject
    public SessionService(SessionDao sessionDao, MessagesApi messagesApi, NoTxJPA jpa) {
        this.sessionDao = sessionDao;
        this.messagesApi = messagesApi;
        this.jpa = jpa;
    }

    private String decryptPassword(String password) {
        byte[] decoded = Base64.getDecoder().decode(password);

        return new String(decoded, StandardCharsets.UTF_8);
    }

    public JsonNode login(User user) {
        return jpa.withDefaultEm(() -> {
            User tempUser = sessionDao.getUser(user.getEmail());
            String password = decryptPassword(tempUser.getPassword());
            if ((tempUser == null) || !password.equals(user.getPassword())) {
                return null;
            }
            // TODO It has security risk. don't save raw password in Database. Please use random string.
            String authToken = Utility.randomString();
            Session session = new Session();
            session.setEmail(user.getEmail());
            session.setAuthToken(authToken);
            sessionDao.login(session);

            return Json.toJson(session.getAuthToken());
        });
    }

    public void logout(String authToken) throws Exception {
        play.i18n.Lang lang = Http.Context.current().lang();
        jpa.withDefaultEm(() -> {
            Session session = sessionDao.getSession(authToken);
            if (session == null) {
                throw new IllegalStateException(messagesApi.get(lang, "userNotFound"));
            }
            sessionDao.logout(session);
        });
    }

    public Session authenticate(String authToken) {

        return sessionDao.getSession(authToken);
    }
}
