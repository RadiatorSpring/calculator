package integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class CalculationServletCorrectJsonIT {
    @Test
    public void testForCorrectInput() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:9090/calculator/api/v1/calculate?expression=1-1");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Scanner scanner = new Scanner(response.getEntity().getContent());
        String json = "{\"result\":" + 0.0 + "}";

        assertEquals("expected 0.0 as json", json, scanner.nextLine());
        assertEquals("status code for OK request", 200, response.getStatusLine().getStatusCode());

    }
}
