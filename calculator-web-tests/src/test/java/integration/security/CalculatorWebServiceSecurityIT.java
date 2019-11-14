package integration.security;

import integration.page.WebPage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorWebServiceSecurityIT {
    private WebPage security;
    private static final String REST_URL = "http://localhost:9090/calculator-web/api/v1/calculate";
    private static final String SIMPLE_EXPRESSION = "1-1";
    private static final String CORRECT_USERNAME = "admin";
    private static final String CORRECT_PASSWORD = "admin";
    private static final String WRONG_USERNAME = "wrongUsername";
    private static final String WRONG_PASSWORD = "wrongPassword";

    @Before
    public void setup() {
        security = new WebPage();
    }

    @Test
    public void testSuccessfulLoginToRestApi() throws IOException {

        int statusCode = security.executeURLWithCredentials(REST_URL, SIMPLE_EXPRESSION, CORRECT_USERNAME, CORRECT_PASSWORD);

        assertThat(statusCode, equalTo(HttpStatus.SC_ACCEPTED));
    }

    @Test
    public void testWrongUsernameToRestApi() throws IOException {
        int statusCode = security.executeURLWithCredentials(REST_URL, SIMPLE_EXPRESSION, WRONG_USERNAME, CORRECT_PASSWORD);

        assertThat(statusCode, equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void testWrongCredentialsOnGetRequest() throws IOException {
        HttpResponse response = security.executeGetRequest(REST_URL, CORRECT_USERNAME, WRONG_PASSWORD);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }
}
