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
    static EntityManagerFactory entityManagerFactory;

    public JsonNode create(Product product){
        EntityManager entityManager = getEmf().createEntityManager();
        entityManager.persist(product);
        entityManager.close();

        return Json.toJson(product);
    }

    public Product getProductById(String productId) {
        EntityManager entityManager = getEmf().createEntityManager();
        Product product = entityManager.find(Product.class, productId);
        entityManager.close();

        return product;
    }

    public JsonNode update(Product product) {
        EntityManager entityManager = getEmf().createEntityManager();
        Product updatedProduct = entityManager.merge(product);
        entityManager.close();

        return Json.toJson(updatedProduct);
    }

    public void delete(Product product) {
        EntityManager entityManager = getEmf().createEntityManager();
        entityManager.remove(product);
        entityManager.close();
    }

    private static EntityManagerFactory getEmf() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu");
        }

        return entityManagerFactory;
    }
}
