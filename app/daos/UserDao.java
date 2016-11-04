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
public class UserDao {
    private final NoTxJPA jpa;

    @Inject
    public UserDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    public User create(User user) {
        EntityManager entityManager = jpa.currentEm();
        entityManager.persist(user);

        return user;
    }

    public User update(User user) {
        EntityManager entityManager = jpa.currentEm();
        entityManager.merge(user);

        return user;
    }

    public User getUserByEmail(String email) {
        EntityManager entityManager = jpa.currentEm();
        User user = entityManager.find(User.class, email);

        return user;
    }

    public List<User> getUserGroupByBranch() {
        EntityManager entityManager = jpa.currentEm();
        QUser qUser = QUser.user;
        JPQLQuery query = new JPAQuery(entityManager);
        List<User> userList = query.from(qUser).where(qUser.branch.eq("BD")).list(qUser);

        return userList;
    }
}
