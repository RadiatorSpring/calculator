package calculator.parsers;

import calculator.operations.OperationFactory;
import calculator.validators.Checker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;

public class ReversePolishNotationParserTest {

    @InjectMocks
    private ReversePolishNotationParser parserRPN;
    @Mock
    private Checker checker;
    @Mock
    private OperationFactory operationFactory;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();





    @Test
    public void testInfixToRPNConversionWithTrivialInput() {
        List<String> input = new ArrayList<>(Arrays.asList("1", "+", "1", "*", "2", "+", "1"));
        when(operationFactory.getOperationPriority("+")).thenReturn(1);
        when(operationFactory.getOperationPriority("*")).thenReturn(2);
        when(checker.isOperation("+")).thenReturn(true);
        when(checker.isNumber("1")).thenReturn(true);
        when(checker.isOperation("*")).thenReturn(true);
        when(checker.isNumber("2")).thenReturn(true);
        Queue<String> actual = parserRPN.buildRPNfromElementsOfExpression(input);
        Queue<String> expected = new LinkedList<>(Arrays.asList("1", "1", "2", "*", "+", "1", "+"));
        Assert.assertEquals("expected to return 1 1 2 * +", expected, actual);
    }

    @Test
    public void testInfixToRPNConversionWithBrackets() {
        List<String> input = new ArrayList<>(Arrays.asList(" ", "2", "+", "2", "*", "(", " ", "2", "-", "2", ")"));
        when(operationFactory.getOperationPriority("+")).thenReturn(1);
        when(operationFactory.getOperationPriority("-")).thenReturn(1);
        when(operationFactory.getOperationPriority("*")).thenReturn(2);
        when(checker.isNumber("2")).thenReturn(true);
        when(checker.isOperation("-")).thenReturn(true);
        when(checker.isOperation("+")).thenReturn(true);
        when(checker.isOperation("*")).thenReturn(true);

        Queue<String> actual = parserRPN.buildRPNfromElementsOfExpression(input);
        Queue<String> expected = new LinkedList<>(Arrays.asList("2", "2", "2", "2", "-", "*", "+"));

        Assert.assertEquals("expected expression is 2 2 2 2 - * +", expected, actual);
    }


}
