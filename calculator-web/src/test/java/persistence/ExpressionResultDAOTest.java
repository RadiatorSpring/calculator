//package persistence;
//
//import eu.drus.jpa.unit.api.JpaUnitRunner;
//import org.dbunit.JdbcDatabaseTester;
//import org.dbunit.database.IDatabaseConnection;
//import org.dbunit.dataset.xml.FlatXmlDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.dbunit.operation.DatabaseOperation;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import persistence.dao.ExpressionResultDAO;
//import persistence.dto.ExpressionResultDTO;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.io.FileInputStream;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(JpaUnitRunner.class)
//public class ExpressionResultDAOTest {
//
//    @PersistenceContext(unitName = "test")
//    private EntityManager entityManager;
//    private static final String INITIAL_STATE = "C:\\Users\\i516445\\IdeaProjects\\calculator\\calculator-web\\src\\test\\resources\\data.xml";
//    private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
//    private static final String CONNECTION_URL = "jdbc:derby:memory://127.0.0.1:1527/calculator;create=true";
//    private static JdbcDatabaseTester databaseTester;
//    private ExpressionResultDAO expressionResultDAO;
//
//    @BeforeClass
//    public static void setDBConnection() throws ClassNotFoundException {
//        databaseTester = new JdbcDatabaseTester(
//                DERBY_DRIVER,
//                CONNECTION_URL);
//    }
//
//    @Before
//    public void test() throws Exception {
//        populateDB();
//        expressionResultDAO = new ExpressionResultDAO("test");
//    }
//
//    @Test
//    public void findTest() {
//        ExpressionResultDTO foundDTO = expressionResultDAO.getExpression(1L);
//
//        assertNotNull(foundDTO);
//    }
//
//    @Test
//    public void findAllTest() {
//        List<ExpressionResultDTO> list = expressionResultDAO.getAll();
//
//        assertEquals(list.size(),2);
//    }
//
//    private void populateDB() throws Exception {
//        IDatabaseConnection databaseConnection = databaseTester.getConnection();
//        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(INITIAL_STATE));
//        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
//    }
//
//
//
//
//}
