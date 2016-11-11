package dtos;

import play.data.validation.Constraints;
import javax.validation.constraints.NotNull;

/**
 * Created by zenith on 11/11/16.
 */
public class SessionForm {

    @NotNull
    @Constraints.Required(message = "auth_token can't be blank")
    private String authToken;

    @Constraints.Email
    @Constraints.Required(message = "Email can't be blank")
    private String email;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
