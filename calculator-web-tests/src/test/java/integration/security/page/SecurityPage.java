package integration.security.page;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class SecurityPage {

    public int executeURLWithCredentials(String url) throws IOException {
        return executeURLWithCredentials(url, "admin", "admin");
    }

    public int executeURLWithCredentials(String url, String username, String password) throws IOException {
        HttpResponse response = createHttpResponseWithCredentials(url, username, password);

        return response.getStatusLine().getStatusCode();
    }

    private HttpResponse createHttpResponseWithCredentials(String url, String username, String password) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        return client.execute(new HttpGet(url));
    }

    public HttpResponse createHttpResponseWithCredentials(String url) throws IOException {
        return createHttpResponseWithCredentials(url,"admin","admin");
    }

}
