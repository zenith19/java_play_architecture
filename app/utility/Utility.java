package utility;

import java.util.UUID;

/**
 * Created by zenith on 11/3/16.
 */
public class Utility {

    public static String randomString() {
        return UUID.randomUUID().toString();
    }
}
