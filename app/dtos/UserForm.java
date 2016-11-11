package dtos;

import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * example of User Input Dto. It's able to convert json.
 */
public class UserForm {
    // This is not Entity, so that this has only validation.

    @Constraints.Email
    @Constraints.Required(message = "Invalid Email format")
    private String email;
    @NotNull
    @Constraints.Required(message = "Name can't be blank")
    private String name;
    @NotNull
    @Length(min = 6)
    @Constraints.Required(message = "Password minimum length 6")
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
