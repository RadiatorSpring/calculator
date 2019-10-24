package integration.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import integration.security.page.SecurityPage;
import models.errors.ErrorCodeMessage;
import models.errors.ExceptionMessages;
import models.wrappers.CalculationId;
import models.wrappers.CalculationResult;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TemporaryIT {
    private String URL_FOR_VERIFICATION = "http://localhost:9090/calculator-web/api/v1/expressions/";

    private ObjectMapper objectMapper = new ObjectMapper();
    private SecurityPage securityPage = new SecurityPage();
    private Logger logger = LoggerFactory.getLogger(TemporaryIT.class);

    @Test
    public void simpleTest() throws IOException, InterruptedException {
        HttpResponse response = securityPage.createHttpResponseWithCredentials("1-1001");
        String json = EntityUtils.toString(response.getEntity());
        Thread.sleep(3500);
        CalculationId id = objectMapper.readValue(json, CalculationId.class);

        HttpResponse responseFromServer = securityPage.executeGetRequest(URL_FOR_VERIFICATION + id.getId());
        logger.error(String.valueOf(responseFromServer));

        String jsonDTO = EntityUtils.toString(responseFromServer.getEntity());
        logger.info(jsonDTO);

        CalculationResult calculationResult = objectMapper.readValue(jsonDTO, CalculationResult.class);

        assertEquals(calculationResult.getResult(), -1000.0, 0.01);
    }
    @Test
    public void errorTest() throws IOException, InterruptedException {
        HttpResponse response = securityPage.createHttpResponseWithCredentials("1--1");
        String json = EntityUtils.toString(response.getEntity());

        CalculationId id = objectMapper.readValue(json, CalculationId.class);
        Thread.sleep(3500);

        HttpResponse responseFromServer = securityPage.executeGetRequest(URL_FOR_VERIFICATION + id.getId());
        String jsonDTO = EntityUtils.toString(responseFromServer.getEntity());

        ErrorCodeMessage errorCodeMessage = objectMapper.readValue(jsonDTO, ErrorCodeMessage.class);

        assertEquals(errorCodeMessage.getMessage(), ExceptionMessages.EMPTY_STACK_EXCEPTION_MESSAGE);
        assertEquals(errorCodeMessage.getCode(),400);
    }
}
