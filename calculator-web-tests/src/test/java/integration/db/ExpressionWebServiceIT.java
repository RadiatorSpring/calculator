package integration.db;

import integration.db.page.BaseDBTest;
import integration.db.page.CalculationPage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class ExpressionWebServiceIT extends BaseDBTest {

    private ExpressionResultDAO expressionResultDAO;
    private CalculationPage calculationPage;

    @Before
    public void setUp() {
        expressionResultDAO = new ExpressionResultDAO(getEntityManager());
        calculationPage = new CalculationPage();

        clearTable();
    }


    @Test
    public void testGetAll() throws IOException {
        String expression1 = "1-1";
        String expression2 = "1-2";
        calculationPage.calculate(expression1);
        calculationPage.calculate(expression2);

        List<ExpressionResultDTO> expressionResultDTOS = expressionResultDAO.getAll();

        assertEquals(expressionResultDTOS.get(0).getExpression(), expression1);
        assertEquals(expressionResultDTOS.get(1).getExpression(), expression2);
    }

}
