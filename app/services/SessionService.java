package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.SessionDao;
import exceptions.ApplicationException;
import helpers.SupplyEM;
import models.Session;
import models.User;
import play.libs.Json;
import utility.Utility;

/**
 * Created by zenith on 10/26/16.
 */
@Singleton
@SupplyEM
public class SessionService {
    private final SessionDao sessionDao;
    private final PasswordEncrypter encrypter;

    @Inject
    public SessionService(SessionDao sessionDao, PasswordEncrypter encrypter) {
        this.sessionDao = sessionDao;
        this.encrypter = encrypter;
    }
    // TODO : base64 is not encryption. Shouldn't use.

    public JsonNode login(User user) {
        User registeredUser = sessionDao.getUser(user.getEmail());
        // TODO : check password via encryptor. Note, that registeredUser.getPassword() is encrypterdPassword.
        if (registeredUser == null || !encrypter.checkPassword(user.getPassword(), registeredUser.getPassword())) {
            return null;
        }
        // TODO: It has security risk. don't save raw password in Database. Please use random string.
        //   -> TODO: I find better API for token in Play.
        String authToken = Utility.publichAuthToken();
        Session session = new Session();
        session.setEmail(user.getEmail());
        session.setAuthToken(authToken);
        sessionDao.login(session);

        return Json.toJson(session.getAuthToken());
    }

    public void logout(String authToken) throws Exception {
        Session session = sessionDao.getSession(authToken);
        if (session == null) {
            // TODO : this should common exception (e.g, ApplicationException)that has messageCode.
            // TODO ; This is better, because, service doesn't use messageApi and Lang.
            throw new ApplicationException("userNotFound");
        }
        sessionDao.logout(session);
    }

    public Session authenticate(String authToken) {
        return sessionDao.getSession(authToken);
    }
}
