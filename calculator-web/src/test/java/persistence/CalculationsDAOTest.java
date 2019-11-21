package persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.base.BaseDAOTest;
import persistence.dao.CalculationsDAO;
import persistence.dto.CalculationsDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JpaUnitRunner.class)
public class CalculationsDAOTest extends BaseDAOTest {
    private static final String PERSISTENCE_UNIT_NAME = "test";
    private static final String INITIAL_STATE = "C:\\Users\\i516445\\IdeaProjects\\calculator\\calculator-web\\src\\test\\resources\\data.xml";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    private CalculationsDAO calculationsDAO;

    @Before
    public void setup() throws Exception {
        calculationsDAO = new CalculationsDAO(PERSISTENCE_UNIT_NAME);
        createTestDBClasses();
        populateDB(INITIAL_STATE);
    }


    @Test
    public void testUpdate() {
        String expression = "1-2";
        double evaluation = 1;
        double delta = 0.01;
        CalculationsDTO calculationsDTO = calculationsDAO.getEntity(expression);
        calculationsDAO.update(expression, evaluation);

        assertNull(calculationsDTO.getError());
        assertEquals(calculationsDTO.getEvaluation(), evaluation, delta);
    }
    @Test
    public void testSave(){
        String expression = "1-1";
        double evaluation = 0;
        double delta = 0.01;

        CalculationsDTO savedCalculationsDTO = new CalculationsDTO(expression, evaluation, null);
        calculationsDAO.save(savedCalculationsDTO);

        CalculationsDTO calculation = calculationsDAO.getEntity(expression);

        assertEquals(calculation.getExpression(), savedCalculationsDTO.getExpression());
        assertEquals(calculation.getError(), savedCalculationsDTO.getError());
        assertEquals(calculation.getEvaluation(), savedCalculationsDTO.getEvaluation(), delta);

    }


}
