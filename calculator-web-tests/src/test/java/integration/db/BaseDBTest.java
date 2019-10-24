package integration.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDBTest {

    private EntityManager entityManager;

    public void createDBConfiguration() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("integration-test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    void clearTable() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("delete from expression_result").executeUpdate();
        entityManager.getTransaction().commit();
    }

    EntityManager getEntityManager() {
        return entityManager;
    }

}
