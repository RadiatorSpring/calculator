package webapp;

import calculator.Calculator;
import calculator.Parser;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.OperationFactory;
import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.CalculatorResult;
import services.CalculatorService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EmptyStackException;

@WebServlet(urlPatterns = {"/calculate"})
public class CalculatorServlet extends HttpServlet {

    private CalculatorService calculatorService = new CalculatorService(new Calculator(new Parser(new Checker(), new ExpressionParser(new Checker()), new ReversePolishNotationParser(new Checker()), new NegativeNumbersBuilder()), new OperationFactory()));

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String expression = request.getParameter("expression");
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter printWriter = response.getWriter();
        String jsonString;
        try {
            CalculatorResult calculatorResult = new CalculatorResult(calculatorService.compute(expression));
            jsonString = mapper.writeValueAsString(calculatorResult);
            printWriter.println(jsonString);
        } catch (CannotDivideByZeroException e) {
            printWriter.println("Yoy cannot divide by zero");
        } catch (IllegalArgumentException e){
            printWriter.println("You must enter at least 2 numbers and 1 operator, negative numbers should be in brackets");
        } catch (EmptyStackException e){
            printWriter.println("The number of operators cannot be grater than the number of numbers");
        }


    }
}