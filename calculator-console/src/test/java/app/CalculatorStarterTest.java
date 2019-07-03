package app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class CalculatorStarterTest {
    private OutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalStream = System.out;

    @Before
    public void setup(){
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void calculatorIntegrationTest(){
        String[] args = {"1+1"};
        CalculatorStarter.main(args);
        Assert.assertEquals("expected system output to be 2","2.0", outContent.toString());
    }

    @After
    public void restore(){
        System.setOut(originalStream);
    }

}
