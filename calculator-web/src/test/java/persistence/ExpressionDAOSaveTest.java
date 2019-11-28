package persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JpaUnitRunner.class)
public class ExpressionDAOSaveTest {
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

        ExpressionResultDTO savedDTO = new ExpressionResultDTO(expression, true);
        expressionResultDAO.save(savedDTO);

        ExpressionResultDTO foundDTO = expressionResultDAO.getEntity(testingId);

        assertEquals(foundDTO.getId(), savedDTO.getId());
        assertEquals(foundDTO.getExpression(),savedDTO.getExpression());
    }


}
