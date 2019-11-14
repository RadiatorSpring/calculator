
package persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;


import javax.persistence.EntityManager;

import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.io.FileInputStream;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(JpaUnitRunner.class)
public class ExpressionResultDAOTest {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;
    private static final String INITIAL_STATE = "C:\\Users\\i516445\\IdeaProjects\\calculator\\calculator-web\\src\\test\\resources\\data.xml";
    private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String CONNECTION_URL = "jdbc:derby:memory://127.0.0.1:1527/calculator;create=true";
    private static JdbcDatabaseTester databaseTester;
    private ExpressionResultDAO expressionResultDAO;
    private static final String PERSISTENCE_UNIT_NAME = "test";

    @Before
    public void setup() throws Exception {
        createTestDBClasses();
        populateDB();
    }

    @Test
    public void findTest() {
        long testId = 1;
        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(testId);
        assertNotNull(foundDTO);
    }

    @Test
    public void findAllTest() {
        int expectedSize = 3;

        List<ExpressionResultDTO> list = expressionResultDAO.getAll();
        assertEquals(list.size(), expectedSize);
    }

    @Test
    public void findAllNotEvaluatedTest() {
        int expectedSize = 1;

        List<ExpressionResultDTO> list = expressionResultDAO.getAllNotEvaluated();
        assertEquals(list.size(), expectedSize);
    }

    @Test
    public void testUpdate() {
        long notEvaluatedExpressionID = 3;
        double evaluation = 1;
        double delta = 0.01;

        expressionResultDAO.update(notEvaluatedExpressionID, evaluation, null);
        ExpressionResultDTO expressionResultDTO = expressionResultDAO.getExpression(notEvaluatedExpressionID);

        assertNull(expressionResultDTO.getError());
        assertEquals(expressionResultDTO.getEvaluation(), evaluation, delta);
    }

    private void populateDB() throws Exception {
        IDatabaseConnection databaseConnection = databaseTester.getConnection();
        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(INITIAL_STATE));

        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
    }

    private void createTestDBClasses() throws ClassNotFoundException {
        databaseTester = new JdbcDatabaseTester(
                DERBY_DRIVER,
                CONNECTION_URL);
        expressionResultDAO = new ExpressionResultDAO(PERSISTENCE_UNIT_NAME);
    }


}