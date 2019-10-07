package services;

import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import persistence.dao.ExpressionResultDAO;

import java.io.IOException;
import java.util.EmptyStackException;

import static models.errors.ExceptionMessages.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CalculatorServiceTest {

    @InjectMocks
    private CalculatorService calculatorService;
    @Mock
    private Computable computable;
    @Mock
    private ExpressionResultDAO ExpressionResultDAO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testExpressionAndResult() throws IOException, CannotDivideByZeroException, WebException {
        String expression = "1-1";
        double result = 0.0;
        when(computable.compute(expression)).thenReturn(0.0);
        double actual = calculatorService.compute(expression);
        assertEquals(result, actual,0.01);
    }

    @Test(expected = WebException.class)
    public void testCalculatorServletEmptyStackException() throws CannotDivideByZeroException, IOException, WebException {
        errorCasesRunner(EMPTY_STACK_EXCEPTION_MESSAGE, "1*-1", new EmptyStackException());
    }

    @Test(expected = WebException.class)
    public void testCalculatorServletCannotDivideByZero() throws CannotDivideByZeroException, IOException, WebException {
        errorCasesRunner(CANNOT_DIVIDE_BY_ZERO, "1/0", new CannotDivideByZeroException(CANNOT_DIVIDE_BY_ZERO));
    }

    @Test(expected = WebException.class)
    public void testCalculatorServletIllegalArgumentException() throws CannotDivideByZeroException, IOException, WebException {
        errorCasesRunner(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, "aqefdgb", new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE));
    }



    private void errorCasesRunner(String message, String expression, Exception e) throws CannotDivideByZeroException, IOException, WebException {

        when(computable.compute(expression)).thenThrow(e);
        calculatorService.compute(expression);
        assertEquals(e.getMessage(),message);

    }
}
