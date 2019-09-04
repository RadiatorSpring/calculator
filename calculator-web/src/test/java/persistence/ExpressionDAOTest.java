package persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.dao.ExpressionDAO;
import persistence.dto.ExpressionDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@RunWith(JpaUnitRunner.class)
public class ExpressionDAOTest {


    @PersistenceContext(type = PersistenceContextType.EXTENDED,name = "entityManager",unitName = "test")
    private EntityManager entityManager ;

    private ExpressionDAO expressionDAO = new ExpressionDAO(entityManager);

    @Test
    public void saveAndRetrieveTest(){
        ExpressionDTO expressionDTO = new ExpressionDTO("1-1",0);

        expressionDAO.save(expressionDTO);
        ExpressionDTO foundDTO = expressionDAO.getExpression(1);

        Assert.assertEquals(foundDTO,expressionDTO);
    }



}
