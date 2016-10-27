package daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by zenith on 10/26/16.
 */
public class BaseDao {
    static EntityManagerFactory entityManagerFactory;

    protected static EntityManagerFactory getEmf() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu");
        }

        return entityManagerFactory;
    }
}
