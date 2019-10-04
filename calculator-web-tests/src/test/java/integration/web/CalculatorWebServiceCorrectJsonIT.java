package integration;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import integration.page.CorrectResponsePage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CalculatorWebServiceCorrectJsonIT {

    private final static String URL = "http://localhost:9090/calculator-web/api/v1/calculate?expression=";


    @Test
    public void testForDecimalNumbers() throws IOException {
        String expression = "1.1-1.1";
        String urlWithExpression = URL + expression;
        CorrectResponsePage responsePage = new CorrectResponsePage(urlWithExpression);
        responsePage.verifyResult(0.0);
    }
}