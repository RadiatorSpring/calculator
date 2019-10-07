package integration.db;

import integration.db.page.CalculationPage;
import integration.web.page.CorrectResponsePage;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CalculatorDBServiceIT {

    private EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(CalculatorDBServiceIT.class);

    @Before
    public void setEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void testSimpleRequest() throws IOException {
        String expression = "1-11";
        CorrectResponsePage correctResponsePage = new CorrectResponsePage(CalculationPage.LOCAL_URL + expression);
//        CalculationPage calculationPage = new CalculationPage(expression);
//        calculationPage.calculate();

        ExpressionResultDTO foundDTO = entityManager.find(ExpressionResultDTO.class, 1L);
        logger.info("this is the expressionDTO -> " + foundDTO);
        logger.info("this is the entityManager -> " + entityManager);

        assertEquals(foundDTO.getId(), 1);
        assertNull(foundDTO.getError());
        assertEquals(foundDTO.getExpression(), expression);
        assertEquals(foundDTO.getEvaluation(), -10.0, 0.01);

    }
}
