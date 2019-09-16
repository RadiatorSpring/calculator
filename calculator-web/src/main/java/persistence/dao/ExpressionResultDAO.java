package persistence.dao;

import persistence.dto.ExpressionResultDTO;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class ExpressionResultDAO {


    private EntityManager entityManager;

    public ExpressionResultDAO(String unitName) {
        setEntityManager(unitName);
    }

    public ExpressionResultDAO() {
        setEntityManager("expression");
    }

    public void save(ExpressionResultDTO ExpressionResultDTO) {

        entityManager.persist(ExpressionResultDTO);
    }

    public void delete(ExpressionResultDTO ExpressionResultDTO) {

        entityManager.remove(ExpressionResultDTO);
    }

    public ExpressionResultDTO getExpression(Long id) {
        return entityManager.find(ExpressionResultDTO.class, id);
    }

    private void setEntityManager(String unitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        entityManager = entityManagerFactory.createEntityManager();
    }



    public List<ExpressionResultDTO> getAll() {
        Query query = entityManager.createNamedQuery("ExpressionResultDTO_findAll", ExpressionResultDTO.class);
        //noinspection unchecked
        return (List<ExpressionResultDTO>) query.getResultList();
    }
}
