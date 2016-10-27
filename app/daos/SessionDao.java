package daos;

import com.google.inject.Singleton;
import models.Session;
import models.User;

import javax.persistence.EntityManager;


/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionDao extends BaseDao{

    public String login(Session session){
        EntityManager entityManager = getEmf().createEntityManager();
        entityManager.persist(session);
        entityManager.close();

        return session.getAuthToken();
    }

    public void logout(Session session){
        EntityManager entityManager = getEmf().createEntityManager();
        entityManager.remove(session);
        entityManager.close();
    }

    public User getUser(String email){
        EntityManager entityManager = getEmf().createEntityManager();
        User user = entityManager.find(User.class, email);
        entityManager.close();

        return user;
    }

    public Session getSession(String authToken) {
        EntityManager entityManager = getEmf().createEntityManager();
        Session session = entityManager.find(Session.class, authToken);
        entityManager.close();

        return session;
    }
}
