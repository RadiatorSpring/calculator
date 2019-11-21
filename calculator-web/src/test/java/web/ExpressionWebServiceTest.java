package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.errors.ExceptionMessages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static models.errors.ExceptionMessages.EMPTY_STACK_EXCEPTION_MESSAGE;
import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionWebServiceTest {
    @InjectMocks
    private ExpressionWebService expressionWebService;

    @Mock
    private ExpressionResultDAO expressionResultDAO;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ExpressionResultDTO expressionResultDTO;

    @Mock
    private CalculationsDAO calculationsDAO;

    private static long testingId = 1;
    private final static int SC_BAD_REQUEST = 400;
    private static final int SC_ACCEPTED = 202;

    @Test
    public void testNonExistingId() throws IOException {

        String result = messageAsJSON(ExceptionMessages.DOES_NOT_EXIST, SC_BAD_REQUEST);

        when(expressionResultDAO.getEntity(testingId)).thenReturn(null);

        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testNotEvaluatedExpression() throws IOException {
        String expression = "1-1";
        String result = messageAsJSON(IS_NOT_EVALUATED, SC_ACCEPTED);

        when(expressionResultDAO.getEntity(testingId)).thenReturn(new ExpressionResultDTO(expression, false));
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

        @Test
    public void testExistingResultId() throws IOException {
        String result = "{\"result\":0.0}";
        String expression = "1-1";
        double evaluated = 0;
        ExpressionResultDTO mockedDTO = new ExpressionResultDTO(expression, false);

        when(expressionResultDAO.getEntity(testingId))
                .thenReturn(mockedDTO);
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testExistingErrorId() throws IOException {
        String expression = "1--1";
        String result = messageAsJSON(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST);


        when(expressionResultDAO.getEntity(testingId)).thenReturn(new ExpressionResultDTO(expression, false));
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }


    private String messageAsJSON(String message, int code) {
        return "{\"message\":\"" + message + "\"," + "\"code\":" + code + "}";
    }

}
