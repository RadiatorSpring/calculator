package integration.pages;

import org.junit.Assert;

import java.io.IOException;

public class ErrorResponsePage extends BasePage{


    public ErrorResponsePage(String url) throws IOException {
        super(url);
    }

    public void verifyMessage(String expectedMessage) {
        try{
        String actualMessage = getResponseBodyAsText();
        String expectedMessageAsJson = "{\"message\":\"" + expectedMessage + "\"," + "\"code\":" + getResponseStatusCode() + "}";
        Assert.assertEquals(expectedMessageAsJson, actualMessage);
        }catch (IOException e){
            e.printStackTrace();
        }

    }




}
