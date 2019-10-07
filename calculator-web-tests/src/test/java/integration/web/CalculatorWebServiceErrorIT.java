package integration;

import integration.page.ErrorResponsePage;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static models.errors.ExceptionMessages.*;

@RunWith(Parameterized.class)
public class CalculatorWebServiceErrorIT {

    private static final String LocalURL = "http://localhost:9090/calculator-web/api/v1/";

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {LocalURL + "calculate?expression=50--4", HttpStatus.SC_BAD_REQUEST, EMPTY_STACK_EXCEPTION_MESSAGE}, {LocalURL + "calculate", HttpStatus.SC_BAD_REQUEST, EMPTY_PARAMETER_EXCEPTION},
                {LocalURL + "calculate?expression", HttpStatus.SC_BAD_REQUEST, EMPTY_PARAMETER_EXCEPTION}, {LocalURL + "calculate?expression=5%2012", HttpStatus.SC_BAD_REQUEST, ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE},
                {LocalURL + "calculate?expression=cvbjk33nbjlkb22", HttpStatus.SC_BAD_REQUEST, ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE}
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
        ErrorResponsePage errorResponsePage = new ErrorResponsePage(requestURL);
        errorResponsePage.verifyStatusCode(code);
        errorResponsePage.verifyMessage(message);

    }


}