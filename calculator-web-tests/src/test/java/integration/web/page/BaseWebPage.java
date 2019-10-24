package integration.web.page;

import integration.security.page.SecurityPage;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;

import java.io.IOException;
import java.util.Scanner;

public class BaseWebPage {
    private ObjectMapper objectMapper;
    private SecurityPage securityPage;
    private HttpResponse response;

    BaseWebPage(String url, String expression) throws IOException {
        objectMapper = new ObjectMapper();
        securityPage = new SecurityPage();
        this.response = createHttpResponse(url, expression);
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

    private HttpResponse createHttpResponse(String url, String expression) throws IOException {
        return securityPage.createHttpResponseWithCredentials(url, expression);
    }
}
