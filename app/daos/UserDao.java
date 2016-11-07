package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import helpers.NoTxJPA;
import models.QUser;
import models.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by zenith on 10/25/16.
 */
@Singleton
// TODO: For development easyly, I write esample of GenericDao example.
public class UserDao implements GenericDao<User, String> {
    private final NoTxJPA jpa;


    @Inject
    public UserDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public EntityManager getEm() {
        return jpa.currentEm();
    }


    public List<User> getUserGroupByBranch() {
        EntityManager entityManager = jpa.currentEm();
        QUser qUser = QUser.user;
        JPQLQuery query = new JPAQuery(entityManager);
        List<User> userList = query.from(qUser).where(qUser.branch.eq("BD")).list(qUser);

        return userList;
    }
}
