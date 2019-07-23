package integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CalculationServletCorrectJsonIT {
    @Test
    public void testForCorrectInput() throws IOException {
        String localHostAppURL = "http://localhost:8080/calculator/api/v1/calculate?expression=";
        String expression = "1-1";
        HttpUriRequest request = new HttpGet(localHostAppURL + expression);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Scanner scanner = new Scanner(response.getEntity().getContent());
        String json = "{\"result\":" + 0.0 + "}";
        String contentTypeOfHttpResponse = response.getHeaders("Content-Type")[0].getElements()[0].getName();

        assertEquals("application/json", contentTypeOfHttpResponse);
        assertEquals("expected 0.0 as json", json, scanner.nextLine());
        assertEquals("status code for OK request", 200, response.getStatusLine().getStatusCode());
    }

}
