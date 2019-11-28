package aspects.logging;

import calculator.Calculator;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;
import models.errors.ExceptionMessages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.CalculatorService;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class LoggingOnServiceTest {

    private CalculatorService calculatorService;
    private PrintStream actualStream;

    @Mock
    private PrintStream mockStream;

    @Mock
    private Calculator calculator;

    @Before
    public void setup() {
        calculatorService = new CalculatorService(calculator);
        actualStream = System.out;
        System.setOut(mockStream);
    }

    @After
    public void clearUp() {
        System.setOut(actualStream);
    }

    @Test
    public void test() throws  CannotDivideByZeroException, IOException {
        String expression = "1-asd";
        String pattern = asRegexPattern(expression);

        when(calculator.compute(expression)).thenThrow(new IllegalArgumentException(ExceptionMessages.ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE));
        try {
            calculatorService.compute(expression);
        } catch (WebException e) {
            byte[] bytes = getAllArguments().get(0);
            String argument = new String(bytes);

            assertTrue(argument.matches(pattern));
        }
    }


    private String asRegexPattern(String argument) {
        return ".*" + argument + ".*\\r*\\n*";
    }

    private List<byte[]> getAllArguments() throws IOException {
        ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
        verify(System.out,times(1)).write(captor.capture());
        return captor.getAllValues();
    }
}
