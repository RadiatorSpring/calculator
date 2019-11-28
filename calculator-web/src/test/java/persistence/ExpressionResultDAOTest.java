
package persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.base.BaseDAOTest;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;


import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(JpaUnitRunner.class)
public class ExpressionResultDAOTest extends BaseDAOTest {
    private static final String INITIAL_STATE = "C:\\Users\\i516445\\IdeaProjects\\calculator\\calculator-web\\src\\test\\resources\\expressionResultData.xml";


    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    private ExpressionResultDAO expressionResultDAO;
    private static final String PERSISTENCE_UNIT_NAME = "test";

    @Before
    public void setup() throws Exception {
        expressionResultDAO = new ExpressionResultDAO(PERSISTENCE_UNIT_NAME);
        createTestDBClasses();
        populateDB(INITIAL_STATE);
    }

    @Test
    public void findAllNotEvaluatedTest() {
        int expectedSize = 1;

        List<ExpressionResultDTO> list = expressionResultDAO.getAllNotEvaluated();
        assertEquals(list.size(), expectedSize);
    }

    @Test
    public void testUpdate() {
        long testId = 1L;
        expressionResultDAO.updateIsEvaluated(testId);

        ExpressionResultDTO expressionResultDTO = expressionResultDAO.getEntity(testId);
        assertTrue(expressionResultDTO.isEvaluated());
    }


}