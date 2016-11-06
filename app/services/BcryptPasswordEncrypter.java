package services;

import com.google.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

/**
 * password enccrypter for BCrypt;
 */
@Singleton
public class BcryptPasswordEncrypter implements PasswordEncrypter {

    public String encrypt( String rawPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(rawPassword, salt);

    }

    public boolean checkPassword(String inputPassword, String encryptedPassword) {
        return BCrypt.checkpw(inputPassword, encryptedPassword);
    }

}
