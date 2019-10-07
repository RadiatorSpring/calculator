package integration.web;

import integration.db.page.DBPage;
import integration.web.page.CorrectResponsePage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;


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