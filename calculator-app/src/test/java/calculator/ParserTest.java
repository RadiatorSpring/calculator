package calculator;

import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParserTest {
    @InjectMocks
    private Parser parser;
    @Mock
    private Checker checker;
    @Mock
    private ExpressionParser expressionParser;
    @Mock
    private ReversePolishNotationParser reversePolishNotationParser;
    @Mock
    private NegativeNumbersBuilder negativeNumbersBuilder;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test(expected = IllegalArgumentException.class)
    public void testWrongInputWithSpaces() throws IOException {
        String expression = "q213 12 + 3     7";
        when(checker.validateExpression(expression)).thenReturn(true);
        parser.convertExpressionToRPN(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException() throws IOException {
        String expression = "asvcx1232+12ws";
        when(checker.validateExpression(expression)).thenReturn(true);
        parser.convertExpressionToRPN(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumbersWithSpacesBetweenThem() throws IOException {
        String expression = "1   1+ 1 ";
        when(checker.validateExpression(expression)).thenReturn(true);
        parser.convertExpressionToRPN(expression);

    }
    @Test(expected = IllegalArgumentException.class)
    public void testWIthEmptyString() throws IOException {
        parser.convertExpressionToRPN("");
    }

    @Test
    public void testWithCorrectInput() throws IOException {
        List<String> list = new ArrayList<>(Arrays.asList("1","+","1"));
        Queue<String> expectedQueue = new LinkedList<>(Arrays.asList("1","+","1"));
        when(expressionParser.expressionToNumbersAndOperations("1+1")).thenReturn(list);
        when(reversePolishNotationParser.buildRPNfromElementsOfExpression(list)).thenReturn(expectedQueue);
        when(negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(list)).thenReturn(list);
        Assert.assertEquals(expectedQueue,parser.convertExpressionToRPN("1+1"));
    }
}
