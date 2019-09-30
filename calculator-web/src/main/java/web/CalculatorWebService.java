package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.WebException;
import models.CalculatorResult;
import models.errors.ErrorCodeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;
import services.CalculatorService;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static models.errors.ExceptionMessages.GENERAL_EXCEPTION_MESSAGE;


@Path("/calculate")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorWebService {


    private CalculatorService calculatorService;
    private ObjectMapper mapper;
    private static final Logger log = LogManager.getLogger(CalculatorWebService.class);
    private ExpressionResultDAO expressionResultDAO;
    private Response response;


    @Inject
    public CalculatorWebService(CalculatorService calculatorService, ObjectMapper mapper, ExpressionResultDAO expressionResultDAO) {
        this.calculatorService = calculatorService;
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;
    }

    @GET
    public Response calculate(@QueryParam("expression") String expression) throws IOException {
        setResponse(expression);

        saveResponseToDb(expression);

        return response;
    }

    private void setResponse(String expression) throws JsonProcessingException {
        try {
            this.response = calculationResultAsJSON(expression);
        } catch (WebException e) {
            log.error(e.getMessage());
            if (e.getMessage().equals(GENERAL_EXCEPTION_MESSAGE)) {
                this.response = errorResponseAsJSON(e.getMessage(), SC_INTERNAL_SERVER_ERROR);
            } else {
                this.response = errorResponseAsJSON(e.getMessage(), SC_BAD_REQUEST);
            }
        }
    }

    private Response calculationResultAsJSON(String expression) throws WebException, JsonProcessingException {
        double result = calculatorService.compute(expression);
        CalculatorResult calculatorResult = new CalculatorResult(result);
        String json = mapper.writeValueAsString(calculatorResult);
        return Response.status(200).entity(json).build();
    }

    private Response errorResponseAsJSON(String exceptionMessage, int statusCode) throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(exceptionMessage, statusCode);
        String json = mapper.writeValueAsString(errorCodeMessage);
        return Response.status(statusCode).entity(json).build();
    }

    void saveResponseToDb(String expression) throws IOException {
        ExpressionResultDTO expressionResultDTO = createExpressionResultDTO(expression);
        expressionResultDAO.save(expressionResultDTO);
    }

    private ExpressionResultDTO createExpressionResultDTO(String expression) throws IOException {
        if (isErrorCode(response.getStatus())) {
            String message = getErrorMessageFromResponse();
            return new ExpressionResultDTO(expression, message);
        } else {
            double result = getCalculationResultFromResponse();
            return new ExpressionResultDTO(expression, result);
        }
    }

    private boolean isErrorCode(int status) {
        return status >= SC_BAD_REQUEST && status <= SC_INTERNAL_SERVER_ERROR;
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
