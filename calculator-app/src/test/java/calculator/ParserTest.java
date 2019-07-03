package calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ParserTest {

    private Parser parser;

    @Before
    public void init() {
        parser = new Parser();
    }

    @Test
    public void testInfixToRPNConversionWithTrivialInput() {
//        List<String> input = new ArrayList<>(Arrays.asList("1", "+", "1"));
        String input = "1+1";

        Queue<String> actual = parser.convertInfixToRPN(input);

        Queue<String> expected = new LinkedList<>(Arrays.asList("1", "1", "+"));

        Assert.assertEquals("expected to return 1 1 +", expected, actual);
    }

    @Test
    public void testInfixToRPNConversionWithDifficultBrackets() {
//        List<String> input = new ArrayList<>(Arrays.asList("2", "+", "2", "*", "(", "2", "-", "2", ")"));
        String input = "2+2*(2-2)";
        Queue<String> actual = parser.convertInfixToRPN(input);
        Queue<String> expected = new LinkedList<>(Arrays.asList("2", "2", "2", "2", "-", "*", "+"));

        Assert.assertEquals("expected expression is 2 2 2 2 - * +", expected, actual);
    }

    @Test
    public void testWithSimpleBrackets() {
//        List<String> input = new ArrayList<>(Arrays.asList("(", "2", "-", "2", ")"));
        String input = "(2-2)";
        Queue<String> actual = parser.convertInfixToRPN(input);
        Queue<String> expected = new LinkedList<>(Arrays.asList("2", "2", "-"));

        Assert.assertEquals("expected expression is 2 2 -", expected, actual);
    }

    @Test
    public void testTextToInfixTrivial() {
        String expression = "1+1";
        List<String> expected = new ArrayList<>(Arrays.asList("1", "+", "1"));

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
        List<String> actual = parser.textToInfix(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException() {
        parser.textToInfix("asvcx1232+12ws");
    }















}
