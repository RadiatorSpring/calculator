package integration;

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
import pages.ErrorPage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static errors.ExceptionMessages.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CalculatorServletErrorIT {

    private static final String LocalURL = "http://localhost:9090/calculator/api/v1/";

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {LocalURL + "calculate?expression=50--4", HttpStatus.SC_BAD_REQUEST, emptyStackExceptionMessage}, {LocalURL + "calculate", HttpStatus.SC_BAD_REQUEST, emptyParameterException},
                {LocalURL + "calculate?expression", HttpStatus.SC_BAD_REQUEST, emptyParameterException}, {LocalURL + "calculate?expression=5%2012", HttpStatus.SC_BAD_REQUEST, illegalArgumentExceptionMessage},
                {LocalURL + "calculate?expression=cvbjk33nbjlkb22", HttpStatus.SC_BAD_REQUEST, illegalArgumentExceptionMessage}
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
        ErrorPage errorPage = new ErrorPage(requestURL);
        errorPage.verifyMessage(message)
                .verifyStatusCode(code);
    }


}
