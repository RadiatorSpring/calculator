package integration.web.page;

import org.junit.Assert;

import java.io.IOException;

public class CorrectResponseWebPage extends BaseWebPage {

    public CorrectResponseWebPage(String url,String expression) throws IOException {
        super(url,expression);
    }

    public void verifyResult(double expectedResult) {
        try {
            String actualMessage = getResponseBodyAsText();
            String expectedMessageAsJson = "{\"result\":" + expectedResult + "}";
            Assert.assertEquals(expectedMessageAsJson, actualMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}