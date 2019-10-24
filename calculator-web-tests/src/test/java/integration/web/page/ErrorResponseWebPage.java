package integration.web.page;

import org.junit.Assert;

import java.io.IOException;

public class ErrorResponseWebPage extends BaseWebPage {


    public ErrorResponseWebPage(String url,String expression) throws IOException {
        super(url,expression);
    }

    public void verifyMessage(String expectedMessage) {
        try {
            String actualMessage = getResponseBodyAsText();
            String expectedMessageAsJson = "{\"message\":\"" + expectedMessage + "\"," + "\"code\":" + getResponseStatusCode() + "}";
            Assert.assertEquals(expectedMessageAsJson, actualMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
