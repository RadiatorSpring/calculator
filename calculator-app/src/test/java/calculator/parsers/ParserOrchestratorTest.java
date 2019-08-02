package calculator.parsers;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParserOrchestratorTest {
    @InjectMocks
    private ParserOrchestrator parserOrchestrator;

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

        parserOrchestrator.parse(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException() {
        String expression = "asvcx1232+12ws";

        parserOrchestrator.parse(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumbersWithSpacesBetweenThem() {
        String expression = "1   1+ 1 ";

        parserOrchestrator.parse(expression);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testWIthEmptyString() {
        parserOrchestrator.parse("");
    }

    @Test
    public void testWithCorrectInput() {
        List<String> list = new ArrayList<>(Arrays.asList("1", "+", "1"));
        Queue<String> expectedQueue = new LinkedList<>(Arrays.asList("1", "+", "1"));
        when(expressionParser.expressionToNumbersAndOperations("1+1")).thenReturn(list);
        when(reversePolishNotationParser.convertToRPN(list)).thenReturn(expectedQueue);
        when(negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(list)).thenReturn(list);
        Assert.assertEquals(expectedQueue, parserOrchestrator.parse("1+1"));
    }
    @Test
    public void testWithDecimalNumbers(){

    }

    @Test
    public void testCorrectOrderOfParsers() {

        when(expressionParser.expressionToNumbersAndOperations(anyString())).thenReturn(new ArrayList<>());
        when(negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(anyList())).thenReturn(new ArrayList<>());
        when(reversePolishNotationParser.convertToRPN(anyList())).thenReturn(new LinkedList<>());

        parserOrchestrator.parse("1+1");

        InOrder inOrder = inOrder( expressionParser,negativeNumbersBuilder,reversePolishNotationParser);

        inOrder.verify(expressionParser).expressionToNumbersAndOperations(anyString());
        inOrder.verify(negativeNumbersBuilder).buildListWithOperatorsAndNegativeNumbers(anyList());
        inOrder.verify(reversePolishNotationParser).convertToRPN(anyList());
    }
    @Test
    public void testValidateExpression(){
        assertTrue(parserOrchestrator.validateExpression("q23`o1ref wmlc knlsnlrne"));
        assertFalse(parserOrchestrator.validateExpression("(1+  1)/2"));
        assertTrue(parserOrchestrator.validateExpression("1  1+1/2+(1-20)"));
        assertTrue(parserOrchestrator.validateExpression("+ 11 11"));
        assertTrue(parserOrchestrator.validateExpression("2()5+22"));
        assertTrue(parserOrchestrator.validateExpression("1(+)5"));
        assertTrue(parserOrchestrator.validateExpression("()+4+5"));
        assertFalse(parserOrchestrator.validateExpression("1.1-1.2"));
    }
}
