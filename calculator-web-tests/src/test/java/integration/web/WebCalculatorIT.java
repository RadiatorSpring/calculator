package integration.web;

import integration.page.CalculatorPage;
import integration.page.exceptions.CalculatorException;
import models.errors.ExceptionMessages;
import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;

import java.io.IOException;

import static models.errors.ExceptionMessages.*;

public class WebCalculatorIT {
    private static final int SC_BAD_REQUEST = 400;
    private CalculatorPage calculatorPage;

    @Before
    public void setup() {
        calculatorPage = new CalculatorPage();
    }

    @Test
    public void calculationTest() throws Exception {
        String correctExpression = "(1-1)+1/2*5";
        double expectedEvaluation = 2.5;

        calculatorPage.calculate(correctExpression);

        calculatorPage.verify(expectedEvaluation);
    }

    @Test
    public void tooManyArgumentsTest() throws Exception {
        String tooManyArgumentsExpression = "1--1";

        calculatorPage.calculate(tooManyArgumentsExpression);

        calculatorPage.verifyError(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
    }

    @Test
    public void illegalCharactersTest() throws Exception {
        String expressionWithIllegalCharacters = "1-asfd1";

        calculatorPage.calculate(expressionWithIllegalCharacters);

        calculatorPage.verifyError(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
    }

    @Test
    public void divisionByZeroTest() throws Exception {
        String expressionWithDivisionByZero = "100/(0)";

        calculatorPage.calculate(expressionWithDivisionByZero);

        calculatorPage.verifyError(CANNOT_DIVIDE_BY_ZERO, SC_BAD_REQUEST);
    }

}
