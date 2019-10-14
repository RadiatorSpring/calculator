package integration.security;

import integration.security.page.SecurityPage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorWebServiceSecurityIT {
    private SecurityPage security;
    private static final String UI_URL = "http://localhost:9090/calculator-web/";
    private static final String REST_URL = "http://localhost:9090/calculator-web/api/v1/calculate?expression=1-1";

    @Before
    public void setup(){
        security = new SecurityPage();
    }

    @Test
    public void testSuccessfulLogin() throws IOException, SAXException {

        int statusCode = security.executeURLWithCredentials(UI_URL,"admin", "admin");

        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void testWrongPassword() throws IOException, SAXException {

        int statusCode = security.executeURLWithCredentials(UI_URL,"admin", "root");

        assertThat(statusCode, equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void testWrongUsername() throws IOException, SAXException {

        int statusCode = security.executeURLWithCredentials(UI_URL,"root", "admin");


        assertThat(statusCode, equalTo(HttpStatus.SC_UNAUTHORIZED));

    }

    @Test
    public void testSuccessfulLoginToRestApi() throws IOException {

        int statusCode = security.executeURLWithCredentials(REST_URL,"admin", "admin");

        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void testWrongUsernameToRestApi() throws IOException {

        int statusCode = security.executeURLWithCredentials(REST_URL,"root", "admin");

        assertThat(statusCode, equalTo(HttpStatus.SC_UNAUTHORIZED));
    }
}
