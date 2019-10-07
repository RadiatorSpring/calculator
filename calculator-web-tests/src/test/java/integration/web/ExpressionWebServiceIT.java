//package integration.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.junit.Assert;
//import org.junit.Test;
//import persistence.dto.ExpressionResultDTO;
//
//import java.io.IOException;
//
//public class ExpressionWebServiceIT {
//    private static final String LocalURL = "http://localhost:9090/calculator/api/v1";
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void findElementByIdTest() throws IOException {
//        HttpUriRequest request = new HttpGet(LocalURL + "expressions");
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//        String entityString = EntityUtils.toString(httpResponse.getEntity());
//        ExpressionResultDTO ExpressionResultDTO = objectMapper.readValue(entityString,ExpressionResultDTO.class);
//        Assert.assertEquals(ExpressionResultDTO.getevaluation(),0.0,0.01);
//    }
//}
