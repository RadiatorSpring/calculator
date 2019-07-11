package calculator.parsers;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks
        ;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NegativeNumbersBuilderTest {
    @InjectMocks
    private NegativeNumbersBuilder negativeNumbersBuilder;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testWithMultipleSigns() {

        List<String> input = new ArrayList<>(Arrays.asList("-", "1", "*","(", "-", "1",")", "*", "-"));
        List<String> expected = new ArrayList<>(Arrays.asList("-1", "*", "(", "-1", ")", "*", "-"));
        List<String> actual = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(input);
        Assert.assertEquals("expected list of -1, *, (, -1, (, *, -", expected, actual);
    }

    @Test
    public void testWithEmpty() {
        List<String> input = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> actual = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(input);
        Assert.assertEquals("expected empty list", expected, actual);
    }

    @Test
    public void testWithOneNegativeNumber() {
        List<String> input = new ArrayList<>(Arrays.asList("-", "1", "*", "1"));
        List<String> expected = new ArrayList<>(Arrays.asList("-1", "*", "1"));
        List<String> actual = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(input);
        Assert.assertEquals("expected -1*1", expected, actual);
    }

    @Test
    public void testWithBrackets() {
        List<String> input = new ArrayList<>(Arrays.asList("1", "*", "(", "-", "1", ")"));
        List<String> expected = new ArrayList<>(Arrays.asList("1", "*", "(", "-1", ")"));
        List<String> actual = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(input);
        Assert.assertEquals("expected 1*(-1)", expected, actual);
    }
    @Test
    public void testWithDoubleBrackets(){
        //((1+1)-1)*2
        ArrayList<String> input = new ArrayList<>(Arrays.asList("(","(","1", "+", ")", "-", "1", ")","*","2"));
        List<String> expected = new ArrayList<>(Arrays.asList("(","(","1", "+", ")", "-", "1", ")","*","2"));
        List<String> actual = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(input);
        Assert.assertEquals("expected 1*(-1)", expected, actual);
    }
}
