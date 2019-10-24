package integration.db.page;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDBTest {

    private EntityManager entityManager;

    public void createDBConfiguration() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("integration-test");
        entityManager = entityManagerFactory.createEntityManager();

    }

    protected void clearTable() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("delete from expression_result").executeUpdate();
        entityManager.getTransaction().commit();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
