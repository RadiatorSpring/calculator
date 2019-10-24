package integration.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import integration.page.exceptions.CalculatorException;
import models.errors.ErrorCodeMessage;
import models.wrappers.CalculationId;
import models.wrappers.CalculationResult;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CalculatorPage {
    private static final String URL_FOR_VERIFICATION = "http://localhost:9090/calculator-web/api/v1/expressions/";

    private ObjectMapper objectMapper;
    private WebPage webPage;
    private ErrorCodeMessage errorCodeMessage;
    private CalculationResult calculationResult;

    public CalculatorPage() {
        objectMapper = new ObjectMapper();
        webPage = new WebPage();
    }

    public void calculate(String expression) throws IOException, InterruptedException {
        String jsonDTO = getExpressionEvaluationAsJSON(expression);

        try {
            calculationResult = objectMapper.readValue(jsonDTO, CalculationResult.class);
        } catch (IOException e) {
            errorCodeMessage = objectMapper.readValue(jsonDTO, ErrorCodeMessage.class);
        }
    }

    private String getExpressionEvaluationAsJSON(String expression) throws IOException, InterruptedException {
        HttpResponse response = webPage.createHttpResponse(expression);
        String jsonId = EntityUtils.toString(response.getEntity());
        CalculationId id = objectMapper.readValue(jsonId, CalculationId.class);

        Thread.sleep(3500);

        HttpResponse responseFromServer = webPage.executeGetRequest(URL_FOR_VERIFICATION + id.getId());
        return EntityUtils.toString(responseFromServer.getEntity());
    }

    public void verify(double expectedResult) {
        double result = calculationResult.getResult();
        assertEquals(expectedResult, result, 0.0001);
    }

    public void verifyError(String expectedMessage, int expectedStatusCode) {
        String message = errorCodeMessage.getMessage();
        int statusCode = errorCodeMessage.getCode();

        assertEquals(expectedMessage, message);
        assertEquals(expectedStatusCode, statusCode);
    }

}
