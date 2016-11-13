package models;

import play.data.validation.Constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;
import java.util.Set;


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

    // @DefaultValue("false") TODO: this annotation is not JPA's annotation. cannot use.
    //@Column(name = "is_admin")
    //private Boolean isAdmin;
    // TODO : I recommend to have concrete role names as collection. Kundera can define collection as column.
    @Column(name = "roles")
    private Set<String> roles;

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
/*
    public Boolean getAdmin() {return isAdmin;}

    public void setAdmin(Boolean admin) {isAdmin = admin;}
    */

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
