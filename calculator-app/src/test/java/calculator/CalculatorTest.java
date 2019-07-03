package calculator;

import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Mockito.*;


public class CalculatorTest {

    @InjectMocks
    private Calculator calculator;
    @Mock
    private Parser parser;
    @Mock
    private OperationFactory operationFactory;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testPlusWithSpaces() throws CannotDivideByZeroException {
        String expressionRPN = "1  + 1  + 2 / 2";
        Queue<String> mockQueue = new LinkedList<>(Arrays.asList("1", "1", "+", "2", "2", "/", "+"));
        parserSetUP(expressionRPN, mockQueue);
        factorySetUp("+",new Addition());
        factorySetUp("/",new Division());
        factorySetUp("1",new Operand(1));
        factorySetUp("2",new Operand(2));
        Assert.assertEquals(3.0, calculator.compute(expressionRPN), 0.001);
        verify(operationFactory, times(2)).getOperation("+");


    }

    @Test
    public void testMultiplyAndPlusAndMinus() throws CannotDivideByZeroException {
        Queue<String> mockQueue = new LinkedList<>(Arrays.asList("2", "2", "2", "2", "-", "*", "-"));
        String expression = "2-2*(2-2)";
        factorySetUp("2",new Operand(2));
        factorySetUp("-",new Substraction());
        factorySetUp("*",new Multiplication());
        parserSetUP(expression, mockQueue);
        Assert.assertEquals("PRN calculator : expected to return 2", 2.0, calculator.compute(expression), 0.001);
    }

    //todo empty queue

    @Test(expected = IllegalArgumentException.class)
    public void testWithOneArgument() throws CannotDivideByZeroException {
        parserSetUP("1", new LinkedList<>(Collections.singletonList("1")));
        calculator.compute("1");
        verify(operationFactory, times(0)).getOperation(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithOneOperation() throws CannotDivideByZeroException {
        parserSetUP("+",new LinkedList<>(Collections.singletonList("+")));
        Assert.assertEquals(0.0, calculator.compute("+"), 0.1);
    }

    private void parserSetUP(String expression, Queue<String> mockQueue) {
        when(parser.convertInfixToRPN(expression)).thenReturn(mockQueue);
    }

    private void factorySetUp(String element, Operation operation) {
        when(operationFactory.getOperation(element)).thenReturn(operation);

    }

}
