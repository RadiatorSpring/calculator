package integration.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDBTest {

    private EntityManager entityManager;
    private static final String PERSISTENCE_UNIT_NAME = "integration-test";
    private static final String QUERY_FOR_DELETION = "delete from expression_result";

    public void createDBConfiguration() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
    }

    void clearTable() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(QUERY_FOR_DELETION).executeUpdate();
        entityManager.getTransaction().commit();
    }

    EntityManager getEntityManager() {
        return entityManager;
    }

}
