package persistence.dao;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dto.CalculationsDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CalculationsDAO {
    private static final String FIND_ALL = "CalculationsDTO_findAll";
    private static final String FIND_ALL_NOT_EVALUATED = "CalculationsDTO_findAllNotEvaluated";
    private static final String FIND_HISTORY_WITH_SESSION_ID = "ExpressionDTO_findHistoryWithSessionId";
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(CalculationsDAO.class);

    public CalculationsDAO(String persistenceUnitName) {
        setEntityManager(persistenceUnitName);
    }

    public CalculationsDAO() {
        setEntityManager("production");
    }

    public CalculationsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CalculationsDTO> getAll() {
        Query<CalculationsDTO> query = (Query<CalculationsDTO>) entityManager
                .createNamedQuery(FIND_ALL, CalculationsDTO.class);
        return query.getResultList();
    }

    public List<CalculationsDTO> getAllNotEvaluated() {
        Query<CalculationsDTO> query = (Query<CalculationsDTO>) entityManager
                .createNamedQuery(FIND_ALL_NOT_EVALUATED, CalculationsDTO.class);
        return query.getResultList();
    }

    public void save(CalculationsDTO calculationsDTO) {
        CalculationsDTO exists = this.getEntity(calculationsDTO.getExpression());

        if (exists == null) {
            entityManager.getTransaction().begin();
            entityManager.persist(calculationsDTO);
            entityManager.getTransaction().commit();
        }
        else{
            logger.info("An attempt to store already existing expression");
        }
    }

    public CalculationsDTO getEntity(String id) {
        return entityManager.find(CalculationsDTO.class, id);
    }

    private void setEntityManager(String unitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void update(String id, double evaluation) {
        entityManager.getTransaction().begin();
        CalculationsDTO calculationsDTO = getEntity(id);

        calculationsDTO.setEvaluation(evaluation);
        calculationsDTO.setError(null);

        entityManager.getTransaction().commit();
        entityManager.clear();
    }
    public void update(String id, double evaluation,String error) {
        entityManager.getTransaction().begin();
        CalculationsDTO calculationsDTO = getEntity(id);

        calculationsDTO.setEvaluation(evaluation);
        calculationsDTO.setError(error);

        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
