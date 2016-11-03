package daos;

import com.google.inject.Singleton;
import models.User;

import javax.persistence.EntityManager;

/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserDao extends BaseDao{

    public User create(User user){
        EntityManager em = getEmf().createEntityManager();
        em.persist(user);
        em.close();

        return user;
    }
}
