package daos;

import com.google.inject.Singleton;
import models.Session;
import play.mvc.Result;

import javax.persistence.EntityManager;

import static play.mvc.Results.ok;


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

    public Result logout(Session session){
        EntityManager entityManager = getEmf().createEntityManager();
        entityManager.remove(session);
        entityManager.close();

        return ok("Successfully Logout");
    }

    public Session getSession(String authToken) {
        EntityManager entityManager = getEmf().createEntityManager();
        Session session = entityManager.find(Session.class, authToken);
        entityManager.close();

        return session;
    }
}
