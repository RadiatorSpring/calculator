package web;

import calculator.Calculator;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.OperationFactory;
import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.Parser;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import errors.ErrorCodeMessage;
import models.CalculatorResult;
import services.CalculatorService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.EmptyStackException;

import static errors.ExceptionMessages.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;


@Path("/calculate")
public class CalculatorJerseyServlet {

    private CalculatorService calculatorService;
    private ObjectMapper mapper;

    public CalculatorJerseyServlet(CalculatorService calculatorService, ObjectMapper mapper) {
        this.calculatorService = calculatorService;
        this.mapper = mapper;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(@PathParam("expression") String expression) throws JsonProcessingException {
        if (expression != null && !expression.isEmpty()) {
            try {
                double result = calculatorService.compute(expression);
                CalculatorResult calculatorResult = new CalculatorResult(result);
                String json = mapper.writeValueAsString(calculatorResult);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } catch (CannotDivideByZeroException e) {
                return responseAsJSON(CANNOT_DIVIDE_BY_ZERO, SC_BAD_REQUEST);
            } catch (IllegalArgumentException e) {
                return responseAsJSON(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
            } catch (EmptyStackException e) {
                return responseAsJSON(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
            } catch (Exception e) {
                return responseAsJSON(GENERAL_EXCEPTION_MESSAGE, SC_INTERNAL_SERVER_ERROR);
            }
        }
        return responseAsJSON(EMPTY_PARAMETER_EXCEPTION, SC_BAD_REQUEST);

    }

    private Response responseAsJSON(String exceptionMessage, int statusCode) throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(exceptionMessage, statusCode);
        String json = mapper.writeValueAsString(errorCodeMessage);
        return Response.status(statusCode).entity(json).build();
    }

}
