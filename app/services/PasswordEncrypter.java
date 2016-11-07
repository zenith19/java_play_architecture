package services;

/**
 * Password encrypt inferface
 */
public interface PasswordEncrypter {
    /** password enccrypt */
    public String encrypt( String rawPassword);

    /** check inputPassword and encrptedPassword(This is saved DB). */
    public boolean checkPassword(String inputPassword, String ecryptedPassword);

}
