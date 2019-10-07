package integration.db.page;

import javax.persistence.EntityManager;

public class DBPage {
    private EntityManager entityManager;

    public DBPage(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createTable() {
        entityManager.getTransaction().begin();

        entityManager.createNativeQuery("create table expression_result (\n" +
                "            id bigint generated by default as identity,\n" +
                "            error varchar(255),\n" +
                "            evaluation double,\n" +
                "            expression varchar(255),\n" +
                "            primary key (id)\n" +
                "            )").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void clearTable() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("delete from expression_result").executeUpdate();
        entityManager.getTransaction().commit();
    }


}
