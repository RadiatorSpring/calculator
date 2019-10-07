package integration.db.page;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.net.www.http.HttpClient;

import java.io.IOException;

public class CalculationPage {

    private static final String LOCAL_URL = "http://localhost:9090/calculator-web/api/v1/calculate?expression=";

    public void calculate(String expression) throws IOException {
        String urlReadableExpression = expression.replaceAll("\\+","%2B");
        String urlWithExpression = LOCAL_URL + urlReadableExpression;
        HttpUriRequest request = new HttpGet(urlWithExpression);
        HttpClientBuilder.create().build().execute(request);
    }

}
