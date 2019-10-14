package integration.web;

import integration.web.page.CorrectResponseWebPage;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;


public class CalculatorWebServiceCorrectJsonIT {

    private final static String URL = "http://localhost:9090/calculator-web/api/v1/calculate?expression=";

    @Test
    public void testForDecimalNumbers() throws IOException {
        String expression = "1.1-1.1";
        String urlWithExpression = URL + expression;
        CorrectResponseWebPage responsePage = new CorrectResponseWebPage(urlWithExpression);



        responsePage.verifyResult(0.0);
    }
}