package persistence.dao;

import org.hibernate.query.Query;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ExpressionResultDAO {

    private static final String FIND_ALL_NOT_EVALUATED = "ExpressionResultDTO_findAllNotEvaluated";
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

    public void save(ExpressionResultDTO ExpressionResultDTO) {
        entityManager.getTransaction().begin();
        entityManager.persist(ExpressionResultDTO);
        entityManager.getTransaction().commit();
    }

    public ExpressionResultDTO getEntity(Long id) {
        return entityManager.find(ExpressionResultDTO.class, id);
    }

    private void setEntityManager(String unitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<ExpressionResultDTO> getAllNotEvaluated() {
        Query<ExpressionResultDTO> query = (Query<ExpressionResultDTO>) entityManager
                .createNamedQuery(FIND_ALL_NOT_EVALUATED, ExpressionResultDTO.class);
        return query.getResultList();
    }

    public void updateIsEvaluated(long id) {
        entityManager.getTransaction().begin();

        ExpressionResultDTO expressionResultDTO = getEntity(id);
        expressionResultDTO.setEvaluated(true);

        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
