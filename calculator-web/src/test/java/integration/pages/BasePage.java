package integration.pages;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;

import java.util.Scanner;

public abstract class BasePage {

    private HttpResponse response;

    BasePage(String url) throws IOException {
        setResponse(url);
    }

    String getResponseBodyAsText() throws IOException {
        try (Scanner scanner = new Scanner(response.getEntity().getContent())) {
            return scanner.nextLine();
        }
    }

    private void setResponse(String url) throws IOException {
        this.response = getHttpResponse(url);
    }


    public void verifyStatusCode(int expectedCode) throws IOException {
        int actual = getResponseStatusCode();
        Assert.assertEquals(expectedCode, actual);
    }

    int getResponseStatusCode() throws IOException {
        return response.getStatusLine().getStatusCode();
    }

    private static HttpResponse getHttpResponse(String url) throws IOException {
        HttpUriRequest request = new HttpGet(url);
        return HttpClientBuilder.create().build().execute(request);
    }
}
