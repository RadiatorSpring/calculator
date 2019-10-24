package services;

import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import java.io.IOException;
import java.util.Collections;
import java.util.EmptyStackException;

import static models.errors.ExceptionMessages.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalculatorServiceTest {

    @InjectMocks
    private CalculatorService calculatorService;
    @Mock
    private Computable computable;
    @Mock
    private ExpressionResultDAO expressionResultDAO;
    @Mock
    JobExecutionContext jobExecutionContext;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testQuartz() throws CannotDivideByZeroException {

        ArgumentCaptor<Double> doubleArgumentCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> longCapture = ArgumentCaptor.forClass(Long.class);

        when(expressionResultDAO.getAllNotEvaluated())
                .thenReturn(Collections.singletonList(new ExpressionResultDTO("1-2", -1)
                ));
        when(computable.compute("1-2")).thenReturn(-1.0);

        try {
            calculatorService.execute(jobExecutionContext);
        } catch (JobExecutionException e) {
            assertEquals(e.getMessage(),EMPTY_STACK_EXCEPTION_MESSAGE);
        }

        verify(expressionResultDAO).update(longCapture.capture(),doubleArgumentCaptor.capture(),stringArgumentCaptor.capture());

        assertEquals(doubleArgumentCaptor.getValue(),-1,0.01);
        assertNull(stringArgumentCaptor.getValue());
    }

    @Test
    public void testExpressionAndResult() throws CannotDivideByZeroException, WebException {
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
