package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;

/**
 * Created by rownak on 11/4/16.
 */
@Entity
@Table(name = "user_branches", schema = "java_play@cassandra_pu")
public class UserBranch {
    @Id
    private String branch;
    @Column(name = "no_of_user")
    @DefaultValue("0")
    private Integer noOfUser;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getNoOfUser() {
        return noOfUser;
    }

    public void setNoOfUser(Integer noOfUser) {
        this.noOfUser = noOfUser;
    }
}
