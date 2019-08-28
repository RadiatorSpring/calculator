package persistence.dao;

import persistence.dto.ExpressionDTO;

import javax.persistence.*;

public class ExpressionDAO {


    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("expression");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void save(ExpressionDTO expressionDTO) {
        entityManager.getTransaction().begin();
        entityManager.persist(expressionDTO);
        entityManager.getTransaction().commit();
    }

    public void delete(ExpressionDTO expressionDTO) {
        entityManager.getTransaction().begin();
        entityManager.remove(expressionDTO);
        entityManager.getTransaction().commit();
    }

    public ExpressionDTO getExpression(int id) {
        return entityManager.find(ExpressionDTO.class, id);
    }


}
