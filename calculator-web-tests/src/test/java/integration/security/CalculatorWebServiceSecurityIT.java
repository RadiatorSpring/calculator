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

    @Before
    public void setup() {
        security = new WebPage();
    }

    @Test
    public void testSuccessfulLoginToRestApi() throws IOException {

        int statusCode = security.executeURLWithCredentials(REST_URL,"1-1", "admin", "admin");

        assertThat(statusCode, equalTo(HttpStatus.SC_ACCEPTED));
    }

    @Test
    public void testWrongUsernameToRestApi() throws IOException {
        int statusCode = security.executeURLWithCredentials(REST_URL,"1-1", "root", "admin");

        assertThat(statusCode, equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void testWrongCredentialsOnGetRequest() throws IOException {
        HttpResponse response = security.executeGetRequest(REST_URL, "root", "admin");

        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }
}
