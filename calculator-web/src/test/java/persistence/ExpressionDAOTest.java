package persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.ExpressionDAO;
import persistence.dto.ExpressionDTO;


public class ExpressionDAOTest {

    ExpressionDAO expressionDAO;

    @Before
    public void setup() {
        expressionDAO = new ExpressionDAO("test");
    }

    @Test
    public void saveAndRetrieveTest() {

        ExpressionDTO expressionDTO = new ExpressionDTO("1-1", 0);

        expressionDAO.save(expressionDTO);
        ExpressionDTO foundDTO = expressionDAO.getExpression(1L);

        Assert.assertEquals(foundDTO, expressionDTO);
    }

//    @Test
//    public void findAllTest() {
//        expressionDAO.getAll()
//    }

}
