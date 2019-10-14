package integration.db;

import integration.db.page.BaseDBTest;
import integration.security.page.SecurityPage;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ExpressionWebServiceIT extends BaseDBTest {
    private static final String LOCAL_URL = "http://localhost:9090/calculator-web/api/v1/calculate?expression=";
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
    public void testGetAll() throws IOException {
        String expression1 = "1-1";
        String expression2 = "1-2";
        securityPage.executeURLWithCredentials(LOCAL_URL + expression1);
        securityPage.executeURLWithCredentials(LOCAL_URL + expression2);

        List<ExpressionResultDTO> expressionResultDTOS = expressionResultDAO.getAll();

        assertEquals(expressionResultDTOS.get(0).getExpression(), expression1);
        assertEquals(expressionResultDTOS.get(1).getExpression(), expression2);
    }

}
