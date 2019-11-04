package integration.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.errors.ErrorCodeMessage;
import models.wrappers.CalculationId;
import models.wrappers.CalculationResult;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class CalculatorPage {

    private ObjectMapper objectMapper;
    private WebPage webPage;
    private ErrorCodeMessage errorCodeMessage;
    private CalculationResult calculationResult;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorPage.class);
    private static final long MAX_TIMEOUT = 10;

    public CalculatorPage() {
        objectMapper = new ObjectMapper();
        webPage = new WebPage();
    }

    public void calculate(String expression) throws Exception {
        String jsonDTO = getExpressionEvaluationAsJSON(expression);

        try {
            calculationResult = objectMapper.readValue(jsonDTO, CalculationResult.class);
        } catch (IOException e) {
            errorCodeMessage = objectMapper.readValue(jsonDTO, ErrorCodeMessage.class);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private String getExpressionEvaluationAsJSON(String expression) throws Exception {
        HttpResponse response = webPage.executePostRequest(expression);
        String jsonId = EntityUtils.toString(response.getEntity());
        CalculationId id = objectMapper.readValue(jsonId, CalculationId.class);
        String jsonResponse = IS_NOT_EVALUATED;

        for (int i = 0; i < 10 && isEvaluated(jsonResponse).call(); i++) {
            HttpResponse responseFromServer = webPage.executeGetRequest(String.valueOf(id.getId()));
            jsonResponse = EntityUtils.toString(responseFromServer.getEntity());

            try {
                await().pollInterval(Duration.ofSeconds(1))
                        .with()
                        .atMost(Duration.ofSeconds(2))
                        .until(isEvaluated(jsonResponse));
            } catch (Exception ignored) {}
        }


        return jsonResponse;
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

    private Callable<Boolean> isEvaluated(String jsonResponse) {
        return () -> jsonResponse.contains(IS_NOT_EVALUATED);
    }


}
