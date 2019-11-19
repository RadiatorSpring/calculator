package persistence.dao;

import org.hibernate.query.Query;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ExpressionResultDAO {

    private static final String FIND_ALL = "ExpressionResultDTO_findAll";
    private static final String FIND_ALL_NOT_EVALUATED = "ExpressionResultDTO_findAllNotEvaluated";
    private static final String FIND_HISTORY_WITH_SESSION_ID = "ExpressionDTO_findHistoryWithSessionId";
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
                .createNamedQuery(FIND_ALL, ExpressionResultDTO.class);
        return query.getResultList();
    }

    public List<ExpressionResultDTO> getAllNotEvaluated() {
        Query<ExpressionResultDTO> query = (Query<ExpressionResultDTO>) entityManager
                .createNamedQuery(FIND_ALL_NOT_EVALUATED, ExpressionResultDTO.class);
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
        entityManager.clear();
    }


    public List<ExpressionResultDTO> getHistory(String historyId) {
        Query<ExpressionResultDTO> query = (Query<ExpressionResultDTO>) entityManager
                .createNamedQuery(FIND_HISTORY_WITH_SESSION_ID, ExpressionResultDTO.class)
                .setParameter("historyId", historyId);

        return query.getResultList();

    }
}
