import models.Session;
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
public class SessionTest {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu");

    public void insertOneSession(String authToken, String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = new Session();
        session.setEmail(email);
        session.setAuthToken(authToken);

        entityManager.persist(session);
        entityManager.close();
    }

    @Test
    public void loginTestAfterInsert(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Session session = new Session();
        session.setEmail("user9@play.com");
        session.setAuthToken("user9@play.com123456");

        entityManager.persist(session);

        entityManager.flush();
        entityManager.clear();

        Session session1 = entityManager.find(Session.class, session.getAuthToken());
        assertEquals(session.getAuthToken(), session1.getAuthToken());
        entityManager.close();
    }

    @Test
    public void parallelLoginInsert() {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> insertOneSession("user9@play.com123456", "user9@play.com"), es),
                CompletableFuture.runAsync(() -> insertOneSession("user10@play.com123456", "user10@play.com"), es),
                CompletableFuture.runAsync(() -> insertOneSession("user11@play.com123456", "user11@play.com"), es)
        ).join();
    }

    public void deleteOneSession(String authToken, String email){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = new Session();
        session.setAuthToken(authToken);
        session.setEmail(email);

        entityManager.persist(session);
        entityManager.flush();
        entityManager.clear();

        Session session1 = entityManager.find(Session.class, session.getAuthToken());
        entityManager.remove(session1);
        entityManager.close();
    }

    @Test
    public void parallelLogoutDelete() {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> deleteOneSession("user9@play.com123456", "user9@play.com"), es),
                CompletableFuture.runAsync(() -> deleteOneSession("user10@play.com123456", "user10@play.com"), es),
                CompletableFuture.runAsync(() -> deleteOneSession("user11@play.com123456", "user11@play.com"), es)
        ).join();
    }


}
