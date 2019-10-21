package web;
import calculator.exceptions.CannotDivideByZeroException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.WebException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.dao.ExpressionResultDAO;
import services.CalculatorService;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.EmptyStackException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static models.errors.ExceptionMessages.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorWebServiceTest {

    @InjectMocks
    private CalculatorWebService calculatorWebService;
    @Mock
    private CalculatorService calculatorService;
    @Mock
    private ObjectMapper objectMapper;


    @Test
    public void testExpressionAndResult() throws IOException {
        String expression = "1-1";
        String result = "{\"result\":0.0}";

        when(objectMapper.writeValueAsString(any())).thenReturn(result);
        Response response = setup(expression,result);
        String actual = (String) response.getEntity();

        assertEquals(result, actual);
    }

    @Test
    public void testCalculatorServletEmptyStackException() throws IOException, WebException {
        errorCasesRunner(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST, "1*-1", EmptyStackException.class);
    }

    @Test
    public void testCalculatorServletCannotDivideByZero() throws CannotDivideByZeroException, IOException, WebException {
        errorCasesRunner(CANNOT_DIVIDE_BY_ZERO, SC_BAD_REQUEST, "1/0", CannotDivideByZeroException.class);
    }

    @Test
    public void testCalculatorServletIllegalArgumentException() throws CannotDivideByZeroException, IOException, WebException {
        errorCasesRunner(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, SC_BAD_REQUEST, "aqefdgb", IllegalArgumentException.class);
    }

    @Test
    public void testCalculatorServletEmptyParameterException() throws CannotDivideByZeroException, IOException, WebException {
        errorCasesRunner(EMPTY_PARAMETER_EXCEPTION, SC_BAD_REQUEST, "", EmptyStackException.class);
    }

    private void errorCasesRunner(String message, int code, String expression, Class<? extends Exception> e) throws IOException, WebException {
        String result = messageAsJSON(message, code);

        when(calculatorService.compute(expression)).thenThrow(new WebException(message));
        Response response = setup(expression,result);

        assertEquals(result, response.getEntity().toString());
    }


    private String messageAsJSON(String message, int code) {
        return "{\"message\":\"" + message + "\"," + "\"code\":" + code + "}";
    }

    private Response setup(String expression ,String result) throws IOException {
        CalculatorWebService spyService = spy(calculatorWebService);

        doNothing().when(spyService).saveResponseToDb(anyString());
        when(objectMapper.writeValueAsString(any())).thenReturn(result);

        return spyService.saveExpression(expression);
    }

}
