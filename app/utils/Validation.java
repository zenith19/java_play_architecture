package utils;

/**
 * Created by rownak on 10/26/16.
 */
public class Validation {
    public String validateString(String input) {
        if (input != null && input.trim().length() > 0) {
            return input.trim();
        }
        else {
            return null;
        }
    }
}
