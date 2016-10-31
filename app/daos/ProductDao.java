package daos;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.Product;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductDao{

    private final NoTxJPA jpa;

    @Inject
    public ProductDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    // TODO; Json lib dosen't use Dao and service. Please see ProductService#create todo comment.
    public Product create(Product product){

        //TODO: Please doesn't call createEntityManger and close in every Dao method. There should call in service method.
        //      Because createEntityManger is heavy. And transaction is not available.


        EntityManager entityManager = jpa.currentEm();
        entityManager.persist(product);

        //entityManager.close();

        return product;
    }

    public Product getProductById(String productId) {
        EntityManager entityManager = jpa.currentEm();
        Product product = entityManager.find(Product.class, productId);
        //entityManager.close();

        return product;
    }

    public List<Product> getAllProduct() {
        EntityManager entityManager = jpa.currentEm();
        List<Product> products = entityManager.createQuery("SELECT p from Product p").getResultList();
       // entityManager.close();

        return products;
    }

    public JsonNode update(Product product) {
        EntityManager entityManager = jpa.currentEm();
        Product updatedProduct = entityManager.merge(product);
        //entityManager.close();

        return Json.toJson(updatedProduct);
    }

    public void delete(Product product) {
        EntityManager entityManager = jpa.currentEm();
        entityManager.remove(product);
       // entityManager.close();
    }
}
