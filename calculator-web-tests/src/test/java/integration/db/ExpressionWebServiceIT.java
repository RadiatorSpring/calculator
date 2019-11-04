package integration.db;

import integration.page.WebPage;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ExpressionWebServiceIT extends BaseDBTest {
    private ExpressionResultDAO expressionResultDAO;
    private WebPage webPage;

    @Before
    public void createDBConfiguration() {
        super.createDBConfiguration();
        expressionResultDAO = new ExpressionResultDAO(getEntityManager());
        webPage = new WebPage();

        clearTable();
    }


    @Test
    public void testGetAll() throws IOException {
        String expression1 = "1-1";
        String expression2 = "1-2";
        webPage.executePostRequest(expression1);
        webPage.executePostRequest(expression2);

        List<ExpressionResultDTO> expressionResultDTOS = expressionResultDAO.getAll();

        assertEquals(expressionResultDTOS.get(0).getExpression(), expression1);
        assertEquals(expressionResultDTOS.get(1).getExpression(), expression2);
    }

}
