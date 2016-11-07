package utility;

import org.mindrot.jbcrypt.BCrypt;
import play.api.mvc.Cookie;
import play.api.mvc.Session;
import play.mvc.Security;

import java.util.UUID;

/**
 * Created by zenith on 11/3/16.
 */
public class Utility {

    public static String publichAuthToken() {
        return Session.cookieSigner().generateToken();
    }
}
