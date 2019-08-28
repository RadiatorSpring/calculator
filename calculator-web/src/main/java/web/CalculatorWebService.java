package web;

import calculator.exceptions.CannotDivideByZeroException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.errors.ErrorCodeMessage;
import models.CalculatorResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.dao.ExpressionDAO;
import persistence.dto.ExpressionDTO;
import services.CalculatorService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.EmptyStackException;

import static models.errors.ExceptionMessages.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;


@Path("/calculate")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorWebService {

    private CalculatorService calculatorService;
    private ObjectMapper mapper;
    private static final Logger log = LogManager.getLogger(CalculatorWebService.class);
    private ExpressionDAO expressionDAO;
    private Response response;

    @Inject
    public CalculatorWebService(CalculatorService calculatorService, ObjectMapper mapper, ExpressionDAO expressionDAO) {
        this.calculatorService = calculatorService;
        this.mapper = mapper;
        this.expressionDAO = expressionDAO;
    }

    @GET
    public Response calculate(@QueryParam("expression") String expression) throws IOException {

        setResponse(expression);

        saveResponseToDb(expression);

        return response;
    }

    private void setResponse(String expression) throws JsonProcessingException {
        if (!isEmptyExpression(expression)) {
            try {
                response = calculationResultAsJson(expression);
            } catch (CannotDivideByZeroException e) {
                response = errorResponseAsJSON(CANNOT_DIVIDE_BY_ZERO, SC_BAD_REQUEST);
            } catch (IllegalArgumentException e) {
                response = errorResponseAsJSON(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
            } catch (EmptyStackException e) {
                response = errorResponseAsJSON(EMPTY_STACK_EXCEPTION_MESSAGE, SC_BAD_REQUEST);
            } catch (Exception e) {
                log.error("hibernate exceptions", e);
                response = errorResponseAsJSON(GENERAL_EXCEPTION_MESSAGE, SC_INTERNAL_SERVER_ERROR);
            }
        }
        response = errorResponseAsJSON(EMPTY_PARAMETER_EXCEPTION, SC_BAD_REQUEST);

    }

    private boolean isEmptyExpression(String expression) {
        return expression != null && expression.isEmpty();
    }

    private Response errorResponseAsJSON(String exceptionMessage, int statusCode) throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(exceptionMessage, statusCode);
        String json = mapper.writeValueAsString(errorCodeMessage);
        return Response.status(statusCode).entity(json).build();
    }


    private Response calculationResultAsJson(String expression) throws CannotDivideByZeroException, JsonProcessingException {
        double result = calculatorService.compute(expression);
        CalculatorResult calculatorResult = new CalculatorResult(result);
        ExpressionDTO expressionDTO = new ExpressionDTO(expression, result);

        String json = mapper.writeValueAsString(calculatorResult);
        expressionDAO.save(expressionDTO);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    private void saveResponseToDb(String expression) throws IOException {
        ExpressionDTO expressionDTO;
        if (response.getStatus() == SC_BAD_REQUEST) {
            String message = getErrorMessageFromResponse();
            expressionDTO = new ExpressionDTO(expression, message);
        } else {
            double result = getCalculationResultFromResponse();
            expressionDTO = new ExpressionDTO(expression, result);
        }
        expressionDAO.save(expressionDTO);
    }

    private String getErrorMessageFromResponse() throws IOException {
        String s = response.getEntity().toString();
        ErrorCodeMessage errorCodeMessage = mapper.readValue(s, ErrorCodeMessage.class);
        return errorCodeMessage.getMessage();
    }

    private double getCalculationResultFromResponse() throws IOException {
        String s = (String) response.getEntity();
        CalculatorResult calculatorResult = mapper.readValue(s, CalculatorResult.class);
        return calculatorResult.getResult();
    }

}
