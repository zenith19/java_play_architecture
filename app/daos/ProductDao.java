package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.Product;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
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

        return product;
    }

    @Override
    public Class<Product> getEntityType() {
        ParameterizedType entityType = Arrays.stream(ProductDao.class.getGenericInterfaces())
                .map(t -> ((ParameterizedType)t))
                .filter(t -> t.getRawType().equals(GenericDao.class)).findFirst().get();
        Type t = entityType.getActualTypeArguments()[0];
        return (Class<Product>) t;
    }

    public List<Product> getAllProduct() {

        return selectAll();
    }

}
