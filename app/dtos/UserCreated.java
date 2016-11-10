package dtos;

import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;

import javax.validation.constraints.NotNull;

/**
 * example of User Output Dto. It's able to convert json.
 * And this has limited field.
 */
public class UserCreated {
    // This is not Entity, so that this has only validation.
    private String email;
    private String name;
    private String branch;
    private String message; // Todo: It's example. you have custom filed that does't define at model.

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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
