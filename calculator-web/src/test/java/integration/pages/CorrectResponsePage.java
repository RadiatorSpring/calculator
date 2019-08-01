//package integration.pages;
//
//import org.junit.Assert;
//
//import java.io.IOException;
//
//public class CorrectResponsePage extends BasePage {
//
//    public CorrectResponsePage(String url) throws IOException {
//        super(url);
//    }
//
//    public void verifyResult(double expectedResult) {
//        try {
//            String actualMessage = getResponseBodyAsText();
//            String expectedMessageAsJson = "{\"result\":" + expectedResult + "}";
//            Assert.assertEquals(expectedMessageAsJson, actualMessage);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
//
