import models.User;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.Assert.assertEquals;
/**
 * Created by zenith on 10/28/16.
 */
public class UserTest {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu");

//    public void insertOne(String email, String name, String password) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        User user = new User();
//        user.setEmail(email);
//        user.setName(name);
//        user.setPassword(password);
//        entityManager.persist(user);
//        entityManager.close();
//    }

    @Test
    public void testSelectAfterInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User();
        user.setEmail("user9@play.com");
        user.setName("User9");
        user.setPassword("123456");
        user.setBranch("Bangladesh");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();
        User user1 = entityManager.find(User.class, user.getEmail());
        assertEquals(user.getName(), user1.getName());
        entityManager.close();
    }

//    @Test
//    public void parallelInsert() {
//        ExecutorService es = Executors.newFixedThreadPool(10);
//        CompletableFuture.allOf(
//                CompletableFuture.runAsync(() -> insertOne("user9@play.com","User9","123456"), es),
//                CompletableFuture.runAsync(() -> insertOne("user10@play.com","User10","123456"), es),
//                CompletableFuture.runAsync(() -> insertOne("user11@play.com","User11","123456"), es)
//        ).join();
//    }
}
