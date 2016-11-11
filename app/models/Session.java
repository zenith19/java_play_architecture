package models;

import org.hibernate.validator.constraints.NotBlank;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Encoded;

/**
 * Created by zenith on 10/26/16.
 */

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "auth_token")
    @Constraints.Required(message = "input.authToken")
    private String authToken;
    @NotBlank
    @Constraints.Required(message = "input.email")
    private String email;

    public String getAuthToken() { return authToken; }

    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
