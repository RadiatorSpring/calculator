package persistence.dao;

import persistence.dto.ExpressionDTO;

import javax.persistence.*;
import java.util.Collection;

public class ExpressionDAO {


    private EntityManager entityManager;

    public ExpressionDAO(String unitName) {
        setEntityManager(unitName);
    }

    public ExpressionDAO() {
        setEntityManager("expression");
    }

    public void save(ExpressionDTO expressionDTO) {

        entityManager.persist(expressionDTO);
    }

    public void delete(ExpressionDTO expressionDTO) {

        entityManager.remove(expressionDTO);
    }

    public ExpressionDTO getExpression(Long id) {
        return entityManager.find(ExpressionDTO.class, id);
    }

    private void setEntityManager(String unitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        entityManager = entityManagerFactory.createEntityManager();
    }

//    public Collection<ExpressionDTO> getAll() {
//
//    }
}
