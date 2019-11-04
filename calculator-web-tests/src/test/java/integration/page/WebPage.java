package integration.page;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WebPage {
    private final static String URL = "http://localhost:9090/calculator-web/api/v1/calculate";
    private static final String URL_FOR_EXPRESSIONS = "http://localhost:9090/calculator-web/api/v1/expressions/";
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "admin";


    public HttpResponse executePostRequest(String expression) throws IOException {
        return executePostRequest(URL, expression, VALID_USERNAME, VALID_PASSWORD);
    }

    public int executeURLWithCredentials(String url, String expression, String username, String password) throws IOException {
        HttpResponse response = executePostRequest(url, expression, username, password);

        return response.getStatusLine().getStatusCode();
    }

    private HttpResponse executePostRequest(String url, String expression, String username, String password) throws IOException {
        String json = "{\"expression\":" + "\"" + expression + "\"" + "}";
        String contentType = "Content-Type";
        String accept = "Accept";
        String mediaTypeApplicationJSON = "application/json";
        StringEntity entity = new StringEntity(json);

        HttpPost httpPost = createHttpPostWithCredentials(url, username, password);
        httpPost.addHeader(contentType, mediaTypeApplicationJSON);
        httpPost.setHeader(accept, mediaTypeApplicationJSON);
        httpPost.setEntity(entity);

        HttpClient client = HttpClientBuilder.create().build();
        return client.execute(httpPost);
    }


    public HttpResponse executeGetRequest(String url, String username, String password) throws IOException {
        HttpGet httpGet = createHttpGetWithCredentials(url, username, password);
        HttpClient client = HttpClientBuilder.create().build();
        return client.execute(httpGet);
    }

    private HttpPost createHttpPostWithCredentials(String url, String username, String password) {
        String authHeader = getAuthorizationHeader(username, password);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        return httpPost;
    }

    private HttpGet createHttpGetWithCredentials(String url, String username, String password) {
        String authHeader = getAuthorizationHeader(username, password);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        return httpGet;
    }

    private String getAuthorizationHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    HttpResponse executeGetRequest(String id ) throws IOException {
        return executeGetRequest(URL_FOR_EXPRESSIONS + id, VALID_USERNAME, VALID_PASSWORD);
    }
}
