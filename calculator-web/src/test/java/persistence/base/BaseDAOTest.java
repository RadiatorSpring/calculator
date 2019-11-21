package persistence.base;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import persistence.dao.ExpressionResultDAO;

import java.io.FileInputStream;

public class BaseDAOTest {
    private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String CONNECTION_URL = "jdbc:derby:memory://127.0.0.1:1527/calculator;create=true";
    private static JdbcDatabaseTester databaseTester;

    protected void populateDB(String fileLocation) throws Exception {
        IDatabaseConnection databaseConnection = databaseTester.getConnection();
        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(fileLocation));

        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
    }

    protected void createTestDBClasses() throws ClassNotFoundException {
        databaseTester = new JdbcDatabaseTester(
                DERBY_DRIVER,
                CONNECTION_URL);
    }
}
