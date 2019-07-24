package web;

import calculator.Calculator;
import calculator.parsers.Parser;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.OperationFactory;
import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import errors.ErrorCodeMessage;
import models.CalculatorResult;
import services.CalculatorService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EmptyStackException;

import static errors.ExceptionMessages.*;

//todo read about rest in neo

@WebServlet(urlPatterns = {"/calculate"})
public class CalculatorServlet extends HttpServlet {

    private CalculatorService calculatorService = new CalculatorService(new Calculator(new Parser(new Checker(), new ExpressionParser(new Checker()), new ReversePolishNotationParser(new Checker()), new NegativeNumbersBuilder()), new OperationFactory()));
    private ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String expression = request.getParameter("expression");

        if (expression != null && !expression.isEmpty()) {
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            String jsonString;
            try {
                CalculatorResult calculatorResult = new CalculatorResult(calculatorService.compute(expression));
                jsonString = mapper.writeValueAsString(calculatorResult);
                printWriter.println(jsonString);
            } catch (CannotDivideByZeroException e) {
                processMessageAndStatusCode(cannotDivideByZero, 400, response);
            } catch (IllegalArgumentException e) {
                processMessageAndStatusCode(illegalArgumentExceptionMessage, 400, response);
            } catch (EmptyStackException e) {
                processMessageAndStatusCode(emptyStackExceptionMessage, 400, response);
            } catch (Exception e) {
                processMessageAndStatusCode(generalExceptionMessage, 500, response);
            }
        } else {
            processMessageAndStatusCode(emptyParameterException, 400, response);
        }
    }

    private void processMessageAndStatusCode(String message, int code, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = new PrintWriter(response.getWriter());
        response.setStatus(code);
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(message, code);
        String jsonString = mapper.writeValueAsString(errorCodeMessage);
        printWriter.println(jsonString);
    }

}

