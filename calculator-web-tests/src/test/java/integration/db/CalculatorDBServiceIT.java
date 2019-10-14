package integration.db;

import integration.db.page.BaseDBTest;
import integration.security.page.SecurityPage;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import java.io.IOException;

import static models.errors.ExceptionMessages.EMPTY_STACK_EXCEPTION_MESSAGE;
import static org.junit.Assert.assertEquals;

public class CalculatorDBServiceIT extends BaseDBTest {
    private static final String LOCAL_URL = "http://localhost:9090/calculator-web/api/v1/calculate?expression=";
    private Logger logger = LoggerFactory.getLogger(CalculatorDBServiceIT.class);
    private ExpressionResultDAO expressionResultDAO;
    private SecurityPage securityPage;

    @Before
    public void createDBConfiguration() {
        super.createDBConfiguration();
        expressionResultDAO = new ExpressionResultDAO(getEntityManager());
        securityPage = new SecurityPage();
        clearTable();
    }


    @Test
    public void testSimpleRequest() throws IOException {
        String expression = "11+1*(1-1)/2";
        securityPage.executeURLWithCredentials(LOCAL_URL +  "11%2B1*(1-1)/2");

        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(1L);

        assertEquals(1, foundDTO.getId());
        assertEquals(expression, foundDTO.getExpression());
        assertEquals(11.0, foundDTO.getEvaluation(), 0.01);
    }

    @Test
    public void testWithWrongExpression() throws IOException {
        String expression = "1--1";
        securityPage.executeURLWithCredentials(LOCAL_URL + expression);

        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(1L);

        assertEquals(1, foundDTO.getId());
        assertEquals(EMPTY_STACK_EXCEPTION_MESSAGE, foundDTO.getError());
    }


}
