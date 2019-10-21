package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.errors.ExceptionMessages;
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

    @Test
    public void testNonExistingId() throws IOException {

        String result = messageAsJSON(ExceptionMessages.DOES_NOT_EXIST, 400);

        when(expressionResultDAO.getExpression(1L)).thenReturn(null);
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(1);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testNotEvaluatedExpression() throws IOException {

        String result = messageAsJSON(ExceptionMessages.IS_NOT_EVALUATED, 202);

        when(expressionResultDAO.getExpression(1L)).thenReturn(new ExpressionResultDTO("1-1","Is not evaluated"));
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(1);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    @Test
    public void testExistingId() throws IOException {

        String result = "{\"result\":0.0}";

        when(expressionResultDAO.getExpression(1L)).thenReturn(new ExpressionResultDTO("1-1",0d));
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        Response response = expressionWebService.getExpression(1);
        Assert.assertEquals(response.getEntity().toString(), result);
    }

    private String messageAsJSON(String message, int code) {
        return "{\"message\":\"" + message + "\"," + "\"code\":" + code + "}";
    }

}
