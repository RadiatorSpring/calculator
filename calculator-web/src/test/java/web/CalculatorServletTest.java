package web;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class CalculatorServletTest {

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

    @InjectMocks
    public CalculatorServlet calculatorServlet;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testFormatAndAnswer() throws IOException {

        when(request.getParameter("expression")).thenReturn("1-1-(-2)");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        calculatorServlet.doGet(request, response);
        assertTrue("Correct format is application/json", stringWriter.toString().contains("\"result\":2.0}"));

    }

}
