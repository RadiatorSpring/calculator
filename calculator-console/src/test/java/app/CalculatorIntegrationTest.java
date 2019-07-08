package app;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.Scanner;

public class CalculatorIntegrationTest {
    //todo expand integration tests
    //todo Parameterized unit test
    private void setUpConsoleTest(String expression, String expected) throws IOException {
        String path = "java -jar C:/Users/i516445/.m2/repository/SAP/calculator-console/1.0-SNAPSHOT/calculator-console-1.0-SNAPSHOT-jar-with-dependencies.jar ";
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(path + expression);
        Scanner scanner = new Scanner(process.getInputStream());
        Assert.assertEquals(expected, scanner.nextLine());
    }
    private final String emptyStackExceptionMessage = "The number of operators cannot be greater than the number of operands, using negative numbers requires brackets";
    private final String illegalArgumentExceptionMessage ="There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator";
    private final String tooShortExpressionMessage = "There should be at least 2 operands and 1 operator";
    @Test
    public void testTwoConsecutiveOperators() throws IOException {
        setUpConsoleTest("1+*1",emptyStackExceptionMessage );
    }

    @Test
    public void testWithLetters() throws IOException {
        setUpConsoleTest("asdsdf", illegalArgumentExceptionMessage);
    }

    @Test
    public void testWithIllegalDivision() throws IOException {
        setUpConsoleTest("1/0", "You cannot divide by zero");
    }

    @Test
    public void testWithPrefix() throws IOException {
        setUpConsoleTest("\"+ 1 1\"", illegalArgumentExceptionMessage);
    }
    @Test
    public void testWithNegativeNumbers() throws IOException {
        setUpConsoleTest("-1*1","-1.0");
    }
    @Test
    public void testWithMultipleMinus() throws IOException {
        setUpConsoleTest("-1-*2",emptyStackExceptionMessage);
    }
    @Test
    public void testWithNull() throws IOException {
        setUpConsoleTest(null,illegalArgumentExceptionMessage);
    }
    @Test
    public void testWithOneArgument() throws IOException {
        setUpConsoleTest("+", tooShortExpressionMessage);
    }
    @Test
    public void testWithOnlyOperators() throws IOException {
        setUpConsoleTest("---",illegalArgumentExceptionMessage);
    }
    @Test
    public void testWithOnlyOperands() throws IOException {
        setUpConsoleTest("111",illegalArgumentExceptionMessage);
    }
    @Test
    public void testWithMinusesAndBrackets() throws IOException {
        setUpConsoleTest("1*-(-1)",emptyStackExceptionMessage);
    }
    @Test
    public void testWithManyMinuses() throws IOException {
        setUpConsoleTest("1*--1",emptyStackExceptionMessage);
    }
    @Test
    public void testWithMinusAndBrackets() throws IOException {
        setUpConsoleTest("1*(-1)","-1.0");
    }
    @Test
    public void testWithEmptyExpression() throws IOException {
        setUpConsoleTest("\"\"",tooShortExpressionMessage);
    }
}
