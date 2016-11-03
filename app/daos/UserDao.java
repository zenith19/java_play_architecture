package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.User;

import javax.persistence.EntityManager;

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
}
