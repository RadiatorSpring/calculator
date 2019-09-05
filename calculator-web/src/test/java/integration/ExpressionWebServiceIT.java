package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import persistence.dto.ExpressionDTO;

import java.io.IOException;

public class ExpressionWebServiceIT {
    private static final String LocalURL = "http://localhost:8080/calculator/api/";
    private ObjectMapper objectMapper;
    @Test
    public void findElementByIdTest() throws IOException {
        HttpUriRequest request = new HttpGet(LocalURL + "expression/" + "1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        String entityString = EntityUtils.toString(httpResponse.getEntity());
        ExpressionDTO expressionDTO = objectMapper.readValue(entityString,ExpressionDTO.class);
        Assert.assertEquals(expressionDTO.getResult(),0.0,0.01);
    }
}
