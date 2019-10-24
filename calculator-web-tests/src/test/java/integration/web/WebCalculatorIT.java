package integration.web;

import integration.page.CalculatorPage;
import integration.page.exceptions.CalculatorException;
import models.errors.ExceptionMessages;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static models.errors.ExceptionMessages.*;

public class WebCalculatorIT {
    private CalculatorPage calculatorPage;
    @Before
    public void setup(){
        calculatorPage = new CalculatorPage();
    }

    @Test
    public void calculationTest() throws IOException, InterruptedException {
        calculatorPage.calculate("(1-1)+1/2*5");

        calculatorPage.verify(2.5);
    }

    @Test
    public void tooManyArgumentsTest() throws IOException, InterruptedException {
        calculatorPage.calculate("1--1");

        calculatorPage.verifyError(EMPTY_STACK_EXCEPTION_MESSAGE,400);
    }
    @Test
    public void illegalCharactersTest() throws IOException, InterruptedException {
        calculatorPage.calculate("1-asfd1");

        calculatorPage.verifyError(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE,400);
    }
    @Test
    public void divisionByZeroTest() throws IOException, InterruptedException {
        calculatorPage.calculate("100/(0)");

        calculatorPage.verifyError(CANNOT_DIVIDE_BY_ZERO,400);
    }

}
