package web;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import static errors.ExceptionMessages.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class CalculatorServletTest {

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

    @InjectMocks
    public CalculatorServlet calculatorServlet;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {"1-1-(-2)","\"result\":2.0}"},{"1*/2",emptyStackExceptionMessage},
                {"asd", illegalArgumentExceptionMessage},{"",emptyParameterException},
                {"1*0+1/0",cannotDivideByZero},{"1*-1",emptyStackExceptionMessage},
                {"1111111111111111111111111111111-22222222222222222222222222222","{\"result\":1.0888888888888888E30}"},
                {"1()+1",illegalArgumentExceptionMessage},{"1+(2)","{\"result\":3.0}"}
        });
    }
    @Parameter
    public String expression;

    @Parameter(1)
    public String result;

    @Test
    public void testExpressionAndResult() throws IOException {

        when(request.getParameter("expression")).thenReturn(expression);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        calculatorServlet.doGet(request, response);
        assertTrue( stringWriter.toString().contains(result));
    }



}
