package calculator.parsers;

import calculator.validators.Checker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParserOrchestratorTest {
    @InjectMocks
    private ParserOrchestrator parserOrchestrator;
    @Mock
    private Checker checker;
    @Mock
    private ExpressionParser expressionParser;
    @Mock
    private NegativeNumbersBuilder negativeNumbersBuilder;
    @Mock
    private ReversePolishNotationParser reversePolishNotationParser;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test(expected = IllegalArgumentException.class)
    public void testWrongInputWithSpaces() {
        String expression = "q213 12 + 3     7";
        when(checker.validateExpression(expression)).thenReturn(true);
        parserOrchestrator.convertExpressionToRPN(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException() {
        String expression = "asvcx1232+12ws";
        when(checker.validateExpression(expression)).thenReturn(true);
        parserOrchestrator.convertExpressionToRPN(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumbersWithSpacesBetweenThem() {
        String expression = "1   1+ 1 ";
        when(checker.validateExpression(expression)).thenReturn(true);
        parserOrchestrator.convertExpressionToRPN(expression);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testWIthEmptyString() {
        parserOrchestrator.convertExpressionToRPN("");
    }

    @Test
    public void testWithCorrectInput() {
        List<String> list = new ArrayList<>(Arrays.asList("1", "+", "1"));
        Queue<String> expectedQueue = new LinkedList<>(Arrays.asList("1", "+", "1"));
        when(expressionParser.expressionToNumbersAndOperations("1+1")).thenReturn(list);
        when(reversePolishNotationParser.buildRPNfromElementsOfExpression(list)).thenReturn(expectedQueue);
        when(negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(list)).thenReturn(list);
        Assert.assertEquals(expectedQueue, parserOrchestrator.convertExpressionToRPN("1+1"));
    }
    @Test
    public void testWithDecimalNumbers(){

    }

    @Test
    public void testCorrectOrderOfParsers() {
        when(checker.validateExpression(anyString())).thenReturn(false);
        when(expressionParser.expressionToNumbersAndOperations(anyString())).thenReturn(new ArrayList<>());
        when(negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(anyList())).thenReturn(new ArrayList<>());
        when(reversePolishNotationParser.buildRPNfromElementsOfExpression(anyList())).thenReturn(new LinkedList<>());

        parserOrchestrator.convertExpressionToRPN("098ygvhjb");

        InOrder inOrder = inOrder(checker, expressionParser,negativeNumbersBuilder,reversePolishNotationParser);
        inOrder.verify(checker).validateExpression(anyString());
        inOrder.verify(expressionParser).expressionToNumbersAndOperations(anyString());
        inOrder.verify(negativeNumbersBuilder).buildListWithOperatorsAndNegativeNumbers(anyList());
        inOrder.verify(reversePolishNotationParser).buildRPNfromElementsOfExpression(anyList());
    }

}
