package app;

import org.junit.Test;

import java.io.IOException;

public class CalculatorIntegrationTest {

    @Test
    public void integrationTestOnJar(){
        String test = "1+1";
        String path = "java -jar -cp /target/calculator-console-1.0-SNAPSHOT-jar-with-dependencies.jar";
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(path + "1+1");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
