//package integration;
//
//import integration.pages.CorrectResponsePage;
//import org.junit.Test;
//
//import java.io.IOException;
//
//
//public class CalculatorWebServiceCorrectJsonIT {
//    @Test
//    public void testForCorrectInput() throws IOException {
//        String url = "http://localhost:9090/calculator/api/calculate?expression=1-1";
//        CorrectResponsePage responsePage = new CorrectResponsePage(url);
//        responsePage.verifyResult(0.0);
//        responsePage.verifyStatusCode(200);
//    }
//
//}
