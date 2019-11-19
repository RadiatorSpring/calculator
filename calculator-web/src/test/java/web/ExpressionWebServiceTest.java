package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.errors.ExceptionMessages;
import models.wrappers.ErrorCodeMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

import static models.errors.ExceptionMessages.EMPTY_STACK_EXCEPTION_MESSAGE;
import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionWebServiceTest {
    @InjectMocks
    private ExpressionWebService expressionWebService;

    @Mock
    private ExpressionResultDAO expressionResultDAO;

    @Mock
    private ObjectMapper objectMapper;

    private static long testingId = 1;
    private final static int SC_BAD_REQUEST = 400;
    private static final int SC_ACCEPTED = 202;

    @Test
    public void testNonExistingId() throws IOException {

        String result = messageAsJSON(ExceptionMessages.DOES_NOT_EXIST, SC_BAD_REQUEST);

        when(expressionResultDAO.getExpression(testingId)).thenReturn(null);
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testNotEvaluatedExpression() throws IOException {
        String expression = "1-1";
        String result = messageAsJSON(IS_NOT_EVALUATED, SC_ACCEPTED);
        String historyId = UUID.randomUUID().toString();

        when(expressionResultDAO.getExpression(testingId)).thenReturn(new ExpressionResultDTO(expression, IS_NOT_EVALUATED,historyId));
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testExistingResultId() throws IOException {
        String result = "{\"result\":0.0}";
        String expression = "1-1";
        double evaluated = 0;
        ExpressionResultDTO mockedDTO = new ExpressionResultDTO(expression, evaluated);

        when(expressionResultDAO.getExpression(testingId))
                .thenReturn(mockedDTO);
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testExistingErrorId() throws IOException {
        String expression = "1--1";
        String result = messageAsJSON(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
        String historyId = UUID.randomUUID().toString();

        when(expressionResultDAO.getExpression(testingId)).thenReturn(new ExpressionResultDTO(expression, EMPTY_STACK_EXCEPTION_MESSAGE,historyId));
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(testingId);
        Assert.assertEquals(response.getEntity().toString(), result);
    }


    private String messageAsJSON(String message, int code) throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(message,code);
        return objectMapper.writeValueAsString(errorCodeMessage);
    }

}
