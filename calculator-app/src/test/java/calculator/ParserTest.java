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

    @Test(expected = IllegalArgumentException.class)
    public void testWrongInputWithSpaces() {
        String expression = "q213 12 + 3     7";
        when(checker.validateExpression(expression)).thenReturn(true);
        parser.convertExpressionToRPN(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException() {
        String expression = "asvcx1232+12ws";
        when(checker.validateExpression(expression)).thenReturn(true);
        parser.convertExpressionToRPN(expression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumbersWithSpacesBetweenThem() {
        String expression = "1   1+ 1 ";
        when(checker.validateExpression(expression)).thenReturn(true);
        parser.convertExpressionToRPN(expression);

    }
}
