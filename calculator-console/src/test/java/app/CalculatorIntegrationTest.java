package app;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.Scanner;

public class CalculatorIntegrationTest {

    private void setUpConsoleTest(String expression, String expected) throws IOException {
        String path = "java -jar C:/Users/i516445/.m2/repository/SAP/calculator-console/1.0-SNAPSHOT/calculator-console-1.0-SNAPSHOT-jar-with-dependencies.jar ";
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(path + expression);
        Scanner scanner = new Scanner(process.getInputStream());
        Assert.assertEquals(expected, scanner.nextLine());
    }

    @Test
    public void testTwoConsecutiveOperators() throws IOException {
        setUpConsoleTest("1+*1", "The number of operators cannot be greater than the number of operands");
    }

    @Test
    public void testWithLetters() throws IOException {
        setUpConsoleTest("asdsdf", "There cannot be letters nor spaces between numbers");
    }

    @Test
    public void testWithIllegalDivision() throws IOException {
        setUpConsoleTest("1/0", "You cannot divide by zero");
    }

    @Test
    public void testWithPrefix() throws IOException {
        setUpConsoleTest("+ 1 1", "There should be at least 2 operands and 1 operator");
    }
}
