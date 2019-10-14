package integration.web.page;

import integration.security.page.SecurityPage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.util.Scanner;

public class BaseWebPage {

    private SecurityPage securityPage;
    private HttpResponse response;

    BaseWebPage(String url) throws IOException {
        this.response = createHttpResponse(url);
    }

    String getResponseBodyAsText() throws IOException {
        try (Scanner scanner = new Scanner(response.getEntity().getContent())) {
            return scanner.nextLine();
        }
    }

    public void verifyStatusCode(int expectedCode) {
        int actual = getResponseStatusCode();
        Assert.assertEquals(expectedCode, actual);
    }

    int getResponseStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    private HttpResponse createHttpResponse(String url) throws IOException {
        return securityPage.createHttpResponseWithCredentials(url);
    }
}
