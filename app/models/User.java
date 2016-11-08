package models;

import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;
import play.data.validation.Constraints.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by rownak on 10/25/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Email
    @Required(message = "Invalid Email format")
    private String email;
    @NotNull
    @Required(message = "Name can't be blank")
    private String name;
    @NotNull
    @Length(min = 6)
    @Required(message = "Password minimum length 6")
    private String password;
    private String branch;

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
}
