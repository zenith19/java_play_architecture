package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.Product;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductDao implements GenericDao<Product, String>{

    private final NoTxJPA jpa;

    @Inject
    public ProductDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public EntityManager getEm() {
        return jpa.currentEm();
    }



    public Product getProductById(String productId) {
        EntityManager entityManager = jpa.currentEm();
        Product product = entityManager.find(Product.class, productId);
        //entityManager.close();

        return product;
    }

    public List<Product> getAllProduct() {
        //EntityManager entityManager = jpa.currentEm();
       // List<Product> products = entityManager.createQuery("SELECT p from Product p").getResultList();
       // entityManager.close();

        return selectAll();
    }


}
