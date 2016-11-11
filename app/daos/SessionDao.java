package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.Product;
import models.Session;
import models.User;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionDao implements GenericDao<Session, String> {
    private final NoTxJPA jpa;

    @Inject
    public SessionDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public EntityManager getEm() {
        return jpa.currentEm();
    }

    @Override
    public Class<Session> getEntityType() {
        ParameterizedType entityType = Arrays.stream(SessionDao.class.getGenericInterfaces())
                .map(t -> ((ParameterizedType)t))
                .filter(t -> t.getRawType().equals(GenericDao.class)).findFirst().get();
        Type t = entityType.getActualTypeArguments()[0];
        return (Class<Session>) t;
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
