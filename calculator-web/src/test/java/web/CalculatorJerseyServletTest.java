package web;

import calculator.exceptions.CannotDivideByZeroException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import services.CalculatorService;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.EmptyStackException;

import static errors.ExceptionMessages.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class CalculatorJerseyServletTest {


    @InjectMocks
    private CalculatorJerseyServlet calculatorJerseyServlet;
    @Mock
    private CalculatorService calculatorService;
    @Mock
    private ObjectMapper objectMapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testExpressionAndResult() throws IOException, CannotDivideByZeroException {
        String expression = "1-1";
        String result = "{\"result\":0.0}";
        when(calculatorService.compute(expression)).thenReturn(0.0);
        when(objectMapper.writeValueAsString(any())).thenReturn(result);
        Response response = calculatorJerseyServlet.calculate(expression);
        String actual = (String) response.getEntity();
        assertEquals(result, actual);


    }

    @Test
    public void testCalculatorServletEmptyStackException() throws CannotDivideByZeroException, JsonProcessingException {
        errorCasesRunner(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST, "1*-1", EmptyStackException.class);
    }

    @Test
    public void testCalculatorServletCannotDivideByZero() throws CannotDivideByZeroException, JsonProcessingException {
        errorCasesRunner(CANNOT_DIVIDE_BY_ZERO, SC_BAD_REQUEST, "1/0", CannotDivideByZeroException.class);
    }

    @Test
    public void testCalculatorServletIllegalArgumentException() throws CannotDivideByZeroException, JsonProcessingException {
        errorCasesRunner(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, SC_BAD_REQUEST, "aqefdgb", IllegalArgumentException.class);
    }

    @Test
    public void testCalculatorServletEmptyParameterException() throws CannotDivideByZeroException, JsonProcessingException {
        errorCasesRunner(EMPTY_PARAMETER_EXCEPTION, SC_BAD_REQUEST, "", EmptyStackException.class);
    }

    private void errorCasesRunner(String message, int code, String expression, Class<? extends Exception> e) throws CannotDivideByZeroException, JsonProcessingException {

        String result = messageAsJSON(message, code);
        when(calculatorService.compute(expression)).thenThrow(e);
        when(objectMapper.writeValueAsString(any())).thenReturn(result);
        Response response = calculatorJerseyServlet.calculate(expression);
        assertEquals(result, response.getEntity().toString());
    }


    private String messageAsJSON(String message, int code) {
        return "{\"message\":\"" + message + "\"," + "\"code\":" + code + "}";
    }
}
