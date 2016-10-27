package daos;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import models.Product;
import play.libs.Json;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductDao extends BaseDao{

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

    public List<Product> getAllProduct() {
        EntityManager entityManager = getEmf().createEntityManager();
        List<Product> products = entityManager.createQuery("SELECT p from Product p").getResultList();
        entityManager.close();

        return products;
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
}
