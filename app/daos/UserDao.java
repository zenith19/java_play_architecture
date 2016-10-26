package daos;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.libs.Json;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by zenith on 10/25/16.
 */
public class UserDao {
    static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory getEmf() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu");
        }

        return entityManagerFactory;
    }

    public JsonNode registration(User user){
        EntityManager em = getEmf().createEntityManager();
        em.persist(user);
        em.close();

        return Json.toJson(user);
    }

    public String login(String email, String password){
        EntityManager em = getEmf().createEntityManager();
        User user = em.find(User.class, email);
        em.close();

        return user.getAuthToken();
    }
}
