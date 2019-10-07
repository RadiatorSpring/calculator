package persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;

@RunWith(JpaUnitRunner.class)
public class ExpressionResultDAOSaveTest {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;
    private ExpressionResultDAO expressionResultDAO;

    @Before
    public void setup() {
        expressionResultDAO = new ExpressionResultDAO("test");
    }


    @Test
    public void testSave(){
        ExpressionResultDTO savedDTO = new ExpressionResultDTO("1-1",0);
        expressionResultDAO.save(savedDTO);

        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(1L);

        assertEquals(foundDTO.getExpression(), savedDTO.getExpression());
        assertEquals(foundDTO.getError(),savedDTO.getError());
        assertEquals(foundDTO.getId(),savedDTO.getId());
        assertEquals(foundDTO.getEvaluation(),savedDTO.getEvaluation(),0.01);
    }


}
