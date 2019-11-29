package integration.db;

import integration.page.WebPage;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.CalculationsDTO;
import persistence.dto.ExpressionResultDTO;

import java.io.IOException;

import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;
import static org.junit.Assert.assertEquals;

public class CalculatorDBServiceIT extends BaseDBTest {
    private ExpressionResultDAO expressionResultDAO;
    private WebPage webPage;
    private static final long testId = 1;
    private CalculationsDAO calculationsDAO;

    @Before
    public void createDBConfiguration() {
        super.createDBConfiguration();
        expressionResultDAO = new ExpressionResultDAO(getEntityManager());
        calculationsDAO = new CalculationsDAO(getEntityManager());
        webPage = new WebPage();
        clearTable();
    }


    @Test
    public void testSimpleRequest() throws IOException {
        String expression = "11+1*(1-1)/2";

        webPage.executePostRequest(expression);

        ExpressionResultDTO expressionResultDTO = expressionResultDAO.getEntity(testId);
        CalculationsDTO calculationsDTO = calculationsDAO.getEntity(expression);

        assertEquals(testId, expressionResultDTO.getId());
        assertEquals(expression, expressionResultDTO.getExpression());
        assertEquals(0d, calculationsDTO.getEvaluation(), 0.01);
        assertEquals("", calculationsDTO.getError());
    }

    @Test
    public void testWithWrongExpression() throws IOException {
        String expression = "1--1";

        webPage.executePostRequest(expression);

        ExpressionResultDTO foundDTO = expressionResultDAO.getEntity(testId);
        CalculationsDTO calculationsDTO = calculationsDAO.getEntity(expression);

        assertEquals(testId, foundDTO.getId());
        assertEquals(expression, foundDTO.getExpression());
        assertEquals(0d, calculationsDTO.getEvaluation(), 0.01);
        assertEquals("", calculationsDTO.getError());
    }


}
