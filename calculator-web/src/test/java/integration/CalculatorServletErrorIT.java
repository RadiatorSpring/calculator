package integration;

import errors.ExceptionMessages;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


//todo test response code, response message and response body in API Testing.

@RunWith(Parameterized.class)
public class CalculatorServletErrorIT {

    private static final String LocalURL = "http://localhost:9090/calculator/api/v1/";

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {LocalURL + "calculate?expression=50--4", HttpStatus.SC_BAD_REQUEST, ExceptionMessages.emptyStackExceptionMessage}, {LocalURL + "calculate", HttpStatus.SC_BAD_REQUEST, ExceptionMessages.emptyParameterException},
                {LocalURL + "calculate?expression", HttpStatus.SC_BAD_REQUEST, ExceptionMessages.emptyParameterException}
        });
    }

    @Parameter
    public String requestURL;
    @Parameter(1)
    public int code;
    @Parameter(2)
    public String message;

    @Test
    public void testForStatusCode() throws IOException {
        HttpUriRequest request = new HttpGet(requestURL);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Scanner scanner = new Scanner(response.getEntity().getContent());
        String json = "{\"message\":\"" + message + "\"," + "\"code\":" + code + "}";
        assertEquals("status code expected[400,200 or 500]", code, response.getStatusLine().getStatusCode());
        assertEquals("message for status code, or result as json", json, scanner.nextLine());


    }


}
