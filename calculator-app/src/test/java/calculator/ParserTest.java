package calculator;

import calculator.validators.Checker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParserTest {
    @InjectMocks
    private Parser parser;
    @Mock
    private Checker checker;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testInfixToRPNConversionWithTrivialInput() {
        List<String> input = new ArrayList<>(Arrays.asList("1", "+", "1"));


        when(checker.isOperationOrBracket("+")).thenReturn(true);
        when(checker.isNumber("1")).thenReturn(true);

        Queue<String> actual = parser.convertInfixToRPN(input);
        Queue<String> expected = new LinkedList<>(Arrays.asList("1", "1", "+"));
        Assert.assertEquals("expected to return 1 1 +", expected, actual);
    }

    @Test
    public void testInfixToRPNConversionWithDifficultBrackets() {
        List<String> input = new ArrayList<>(Arrays.asList("2", "+", "2", "*", "(", "2", "-", "2", ")"));
        when(checker.isNumber("2")).thenReturn(true);
//        when(checker.isOperationOrBracket("(")).thenReturn(true);
//        when(checker.isOperationOrBracket(")")).thenReturn(true);
        when(checker.isOperationOrBracket("-")).thenReturn(true);
        when(checker.isOperationOrBracket("+")).thenReturn(true);
        when(checker.isOperationOrBracket("*")).thenReturn(true);

        Queue<String> actual = parser.convertInfixToRPN(input);
        Queue<String> expected = new LinkedList<>(Arrays.asList("2", "2", "2", "2", "-", "*", "+"));

        Assert.assertEquals("expected expression is 2 2 2 2 - * +", expected, actual);
    }


    @Test
    public void testTextToInfixTrivial() {
        String expression = "1/1";

        List<String> expected = new ArrayList<>(Arrays.asList("1", "/", "1"));

        List<String> actual = parser.textToInfix(expression);

        Assert.assertEquals("expected to return list with elements [1] [+] [1]", expected, actual);
    }

    @Test
    public void testTextToInfixLongExpression() {
        String expression = "2+2*(2-2)";
        List<String> expected = new ArrayList<>(Arrays.asList("2", "+", "2", "*", "(", "2", "-", "2", ")"));

        List<String> actual = parser.textToInfix(expression);

        Assert.assertEquals("expected list with 2+2*(2-2) each element at different index", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongInputWithSpaces() {
        String expression = "q213 12 + 3     7";
        when(checker.validateExpression("q213 12 + 3     7")).thenReturn(true);
        List<String> actual = parser.textToInfix(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException() {
        when(checker.validateExpression("asvcx1232+12ws")).thenReturn(true);
        parser.textToInfix("asvcx1232+12ws");
    }


}
