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
    private static final String PERSISTENCE_UNIT_NAME = "test";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    private ExpressionResultDAO expressionResultDAO;

    @Before
    public void setup() {
        expressionResultDAO = new ExpressionResultDAO(PERSISTENCE_UNIT_NAME);
    }


    @Test
    public void testSave() {
        long testingId = 1L;
        String expression = "1-1";
        double evaluation = 0;
        double delta = 0.01;

        ExpressionResultDTO savedDTO = new ExpressionResultDTO(expression, evaluation);
        expressionResultDAO.save(savedDTO);

        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(testingId);

        assertEquals(foundDTO.getExpression(), savedDTO.getExpression());
        assertEquals(foundDTO.getError(), savedDTO.getError());
        assertEquals(foundDTO.getId(), savedDTO.getId());
        assertEquals(foundDTO.getEvaluation(), savedDTO.getEvaluation(), delta);
    }


}
