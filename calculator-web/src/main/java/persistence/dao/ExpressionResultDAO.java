package persistence.dao;

import org.hibernate.query.Query;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ExpressionResultDAO {


    private EntityManager entityManager;

    public ExpressionResultDAO(String persistenceUnitName) {
        setEntityManager(persistenceUnitName);
    }

    public ExpressionResultDAO() {
        setEntityManager("production");
    }

    public ExpressionResultDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ExpressionResultDTO> getAll() {
        Query<ExpressionResultDTO> query = (Query<ExpressionResultDTO>) entityManager
                .createNamedQuery("ExpressionResultDTO_findAll", ExpressionResultDTO.class);
        return query.getResultList();
    }

    public List<ExpressionResultDTO> getAllNotEvaluated() {
        Query<ExpressionResultDTO> query = (Query<ExpressionResultDTO>) entityManager
                .createNamedQuery("ExpressionResultDTO_findAllNotEvaluated", ExpressionResultDTO.class);
        return query.getResultList();
    }

    public void save(ExpressionResultDTO ExpressionResultDTO) {
        entityManager.getTransaction().begin();
        entityManager.persist(ExpressionResultDTO);
        entityManager.getTransaction().commit();
    }

    public ExpressionResultDTO getExpression(Long id) {
        return entityManager.find(ExpressionResultDTO.class, id);
    }



    private void setEntityManager(String unitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void update(long id, double evaluation, String error) {
        entityManager.getTransaction().begin();
        ExpressionResultDTO expressionResultDTO = getExpression(id);

        expressionResultDTO.setEvaluation(evaluation);
        expressionResultDTO.setError(error);

        entityManager.getTransaction().commit();
    }


}
