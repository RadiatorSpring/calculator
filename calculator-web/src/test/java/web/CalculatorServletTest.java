//package web;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameter;
//import org.junit.runners.Parameterized.Parameters;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.Arrays;
//import java.util.Collection;
//
//import static errors.ExceptionMessages.*;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.when;
//
//@RunWith(Parameterized.class)
//public class CalculatorServletTest {
//
//    private CalculatorJerseyServlet calculatorJerseyServlet;
//
//    @Before
//    public void setup() {
//        calculatorJerseyServlet = new CalculatorJerseyServlet();
//    }
//
//    @Parameters
//    public static Collection<Object> data() {
//        return Arrays.asList(new Object[][]{
//                {"1-1-(-2)","\"result\":2.0}"},{"1*/2",EMPTY_STACK_EXCEPTION_MESSAGE},
//                {"asd", ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE},{"",EMPTY_PARAMETER_EXCEPTION},
//                {"1*0+1/0",CANNOT_DIVIDE_BY_ZERO},{"1*-1",EMPTY_STACK_EXCEPTION_MESSAGE},
//                {"1111111111111111111111111111111-22222222222222222222222222222","{\"result\":1.0888888888888888E30}"},
//                {"1()+1",ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE},{"1+(2)","{\"result\":3.0}"}
//        });
//    }
//    @Parameter
//    public String expression;
//
//    @Parameter(1)
//    public String result;
//
//    @Test
//    public void testExpressionAndResult() throws IOException {
//
//        when(request.getParameter("expression")).thenReturn(expression);
//           StringWriter stringWriter = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(printWriter);
//        calculatorServlet.doGet(request, response);
//        assertTrue( stringWriter.toString().contains(result));
//    }
//
//
//}
