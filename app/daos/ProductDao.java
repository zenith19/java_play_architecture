package daos;

import com.fasterxml.jackson.databind.JsonNode;
import models.Product;
import play.libs.Json;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by rownak on 10/25/16.
 */
public class ProductDao {
    static EntityManagerFactory emf;

    public JsonNode create(Product product){
        EntityManager em = getEmf().createEntityManager();
        em.persist(product);
        em.close();

        return Json.toJson(product);
    }

    private static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("cassandra_pu");
        }

        return emf;
    }
}
