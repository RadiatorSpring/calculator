package app;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

@RunWith(Parameterized.class)
public class CalculatorIntegrationIT
{

    private static final String emptyStackExceptionMessage = "The number of operators cannot be greater than the number of operands, using negative numbers requires brackets";
    private static final String illegalArgumentExceptionMessage = "There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator";
    private static final String tooShortExpressionMessage = "There should be at least 2 operands and 1 operator";

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {"1+*1", emptyStackExceptionMessage}, {"asdsdf", illegalArgumentExceptionMessage},
                {"1/0", "You cannot divide by zero"}, {"\"+ 1 1\"", illegalArgumentExceptionMessage},
                {"-1*1", "-1.0"}, {"-1-*2", emptyStackExceptionMessage},
                {"", tooShortExpressionMessage}, {"+", tooShortExpressionMessage},
                {"---", illegalArgumentExceptionMessage}, {"111", illegalArgumentExceptionMessage},
                {"1*-(-1)", emptyStackExceptionMessage}, {"1*--1", emptyStackExceptionMessage},
                {"1*(-1)", "-1.0"}, {"\"\"", tooShortExpressionMessage}, {"2()5+22", illegalArgumentExceptionMessage},
                {"13+1.1-.1","14.0"},{"1.1-1.1","0.0"}
        });
    }
    @Parameter
    public String input;
    @Parameter(1)
    public String expected;
    @Test
    public void consoleTest() throws IOException {
        String jarPath ="../calculator-console/target/calculator-console-1.0-SNAPSHOT-jar-with-dependencies.jar";
        String path = "java -jar " + jarPath + " ";
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(path + input);
        Scanner scanner = new Scanner(process.getInputStream());
        Assert.assertEquals(expected, scanner.nextLine());
    }
}
