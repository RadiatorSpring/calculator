package calculator.parsers;

import calculator.validators.Checker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class ExpressionParserTest {

    @InjectMocks
    private ExpressionParser parser;
    @Mock
    private Checker checker;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testWithSpacesBetweenNumberAndOperator() {
        String expression = "11/ 11";

        List<String> expected = new ArrayList<>(Arrays.asList("11", "/", "", " ", "11"));
        when(checker.isDigit('1')).thenReturn(true);
        List<String> actual = parser.expressionToNumbersAndOperations(expression);

        Assert.assertEquals("expected to return list with elements [11] [+] [11], there can be empty strings and spaces", expected, actual);
    }

    @Test
    public void testWithExpressionInBrackets() {
        String expression = "2+2*(2-2)";
        List<String> expected = new ArrayList<>(Arrays.asList("2", "+", "2", "*", "", "(", "2", "-", "2", ")"));
        when(checker.isDigit('2')).thenReturn(true);
        List<String> actual = parser.expressionToNumbersAndOperations(expression);

        Assert.assertEquals("expected list with 2+2*(2-2) each element at different index, there can be empty strings and spaces", expected, actual);
    }




}
