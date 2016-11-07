import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import models.Product;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.Assert.assertEquals;

/**
 * Created by rownak on 10/27/16.
 */
public class ProductTest {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cassandra_test");

    private void insertOne(String name, int price) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName(name);
        product.setPrice(price);

        entityManager.persist(product);
        entityManager.close();
    }

    @Test
    public void testSelectAfterInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("product_name");
        product.setPrice(100);

        entityManager.persist(product);

        entityManager.flush();
        entityManager.clear();

        Product product1 = entityManager.find(Product.class, product.getProductId());
        assertEquals(product.getProductName(), product1.getProductName());
        entityManager.close();
    }

    @Test
    public void testInsert() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect("\"java_play\"");

        String pId = UUID.randomUUID().toString();
        String query ="INSERT INTO java_play.products (product_id, product_name, price) values('%s', '%s', %d)";
        session.execute(String.format(query, pId, "product_name_1234", 35));

        ResultSet getPrice = session.execute(
                "SELECT price FROM java_play.products WHERE product_id = ?", pId);
        assertEquals(getPrice.all().get(0).getInt("price"), 35);

        session.close();
        cluster.close();
    }

    @Test
    public void parallelInsert() {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> insertOne("product1",10), es),
                CompletableFuture.runAsync(() -> insertOne("product2",20), es),
                CompletableFuture.runAsync(() -> insertOne("product3",30), es)
        ).join();
    }

}
