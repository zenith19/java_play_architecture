package models;

import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;
import play.data.validation.Constraints.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

/**
 * Created by rownak on 10/25/16.
 */
@Entity
@Table(name = "users")
public class User {
    // TODO : if model is not every controller input/output, valiation annotation doesn't need.

    @Id
    @Email
    private String email;

    private String name;

    private String password;
    private String branch;

    @DefaultValue("false")
    @Column(name = "is_admin")
    private Boolean isAdmin;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranch() {return branch;}

    public void setBranch(String branch) {this.branch = branch;}

    public Boolean getAdmin() {return isAdmin;}

    public void setAdmin(Boolean admin) {isAdmin = admin;}
}
