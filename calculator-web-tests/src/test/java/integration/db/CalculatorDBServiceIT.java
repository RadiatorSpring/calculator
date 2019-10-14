package integration.db;

import integration.db.page.BaseDBTest;
import integration.db.page.CalculationPage;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static models.errors.ExceptionMessages.EMPTY_STACK_EXCEPTION_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Ignore
public class CalculatorDBServiceIT extends BaseDBTest {

    private Logger logger = LoggerFactory.getLogger(CalculatorDBServiceIT.class);
    private ExpressionResultDAO expressionResultDAO;
    private CalculationPage calculationPage;

    @Before
    public void setUp() {
        expressionResultDAO = new ExpressionResultDAO(getEntityManager());
        calculationPage = new CalculationPage();

        clearTable();
    }


    @Test
    public void testSimpleRequest() throws IOException {
        String expression = "11+1*(1-1)/2";
        calculationPage.calculate(expression);

        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(1L);

        assertEquals(1, foundDTO.getId());
        assertNull(foundDTO.getError());
        assertEquals(expression, foundDTO.getExpression());
        assertEquals(11.0, foundDTO.getEvaluation(), 0.01);
    }

    @Test
    public void testWithWrongExpression() throws IOException {
        String expression = "1--1";
        calculationPage.calculate(expression);

        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(1L);

        assertEquals(1, foundDTO.getId());
        assertEquals(expression, foundDTO.getExpression());
        assertEquals(EMPTY_STACK_EXCEPTION_MESSAGE, foundDTO.getError());
    }


}
