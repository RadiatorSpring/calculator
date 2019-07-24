package pages;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.util.Scanner;

//todo verifyCode verifyMessage sendRequest getResponse
public class ErrorPage {
    private String url;


    public ErrorPage(String url) {
        this.url = url;

    }

    public ErrorPage verifyMessage(String expectedMessage) throws IOException {
        String actualMessage = getResponseBodyAsText();
        String expectedMessageAsJson = "{\"message\":\"" + expectedMessage + "\"," + "\"code\":" + getResponseStatusCode() + "}";
        Assert.assertEquals(expectedMessageAsJson, actualMessage);
        return this;
    }

    private String getResponseBodyAsText() throws IOException {
        HttpResponse response = getResponse();
        try (Scanner scanner = new Scanner(response.getEntity().getContent())) {
            return scanner.nextLine();
        }
    }


    public ErrorPage verifyStatusCode(int expectedCode) throws IOException {
        int actual = getResponseStatusCode();
        Assert.assertEquals(expectedCode, actual);
        return this;
    }

    private int getResponseStatusCode() throws IOException {
        HttpResponse response = getResponse();
        return response.getStatusLine().getStatusCode();
    }

    private HttpResponse getResponse() throws IOException {
        HttpUriRequest request = new HttpGet(url);
        return HttpClientBuilder.create().build().execute(request);
    }

}
