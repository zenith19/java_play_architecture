package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zenith on 10/26/16.
 */
@Entity
@Table(name = "sessions", schema = "java_play@cassandra_pu")
public class Session {
    @Id @Column(name = "auth_token")
    private String authToken;
    private String email;

    public String getAuthToken() { return authToken; }

    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
