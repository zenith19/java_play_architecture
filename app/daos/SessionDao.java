package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.Session;
import models.User;

import javax.persistence.EntityManager;


/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionDao {
    private final NoTxJPA jpa;

    @Inject
    public SessionDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    public String login(Session session) {
        EntityManager entityManager = jpa.currentEm();
        entityManager.persist(session);

        return session.getAuthToken();
    }

    public void logout(Session session) {
        EntityManager entityManager = jpa.currentEm();
        entityManager.remove(session);
    }

    public User getUser(String email) {
        EntityManager entityManager = jpa.currentEm();
        User user = entityManager.find(User.class, email);

        return user;
    }

    public Session getSession(String authToken) {
        EntityManager entityManager = jpa.currentEm();
        Session session = entityManager.find(Session.class, authToken);

        return session;
    }
}
