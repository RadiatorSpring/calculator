package integration.db;

import integration.page.WebPage;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import java.io.IOException;

import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;
import static org.junit.Assert.assertEquals;

public class CalculatorDBServiceIT extends BaseDBTest {
    private ExpressionResultDAO expressionResultDAO;
    private WebPage webPage;
    private static final long testId = 1;

    @Before
    public void createDBConfiguration() {
        super.createDBConfiguration();
        expressionResultDAO = new ExpressionResultDAO(getEntityManager());
        webPage = new WebPage();
        clearTable();
    }


    @Test
    public void testSimpleRequest() throws IOException {
        String expression = "11+1*(1-1)/2";

        webPage.executePostRequest(expression);
        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(testId);

        assertEquals(testId, foundDTO.getId());
        assertEquals(expression, foundDTO.getExpression());
        assertEquals(0d, foundDTO.getEvaluation(), 0.01);
        assertEquals(IS_NOT_EVALUATED, foundDTO.getError());
    }

    @Test
    public void testWithWrongExpression() throws IOException {
        String expression = "1--1";
        webPage.executePostRequest(expression);
        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(testId);

        assertEquals(testId, foundDTO.getId());
        assertEquals(expression, foundDTO.getExpression());
        assertEquals(0d, foundDTO.getEvaluation(), 0.01);
        assertEquals(IS_NOT_EVALUATED, foundDTO.getError());
    }


}
