package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.WebException;
import models.CalculatorResult;
import models.errors.ErrorCodeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;
import services.CalculatorService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static models.errors.ExceptionMessages.GENERAL_EXCEPTION_MESSAGE;


@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorWebService {

    private CalculatorService calculatorService;
    private ObjectMapper mapper;
    private ExpressionResultDAO expressionResultDAO;
    private Response response;
    private final Logger logger = LoggerFactory.getLogger(CalculatorWebService.class);

    @Inject
    public CalculatorWebService(CalculatorService calculatorService, ObjectMapper mapper, ExpressionResultDAO expressionResultDAO) {
        this.calculatorService = calculatorService;
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;
    }

    @GET
    @Path("/calculate")
    public Response calculate(@Valid @NotNull @QueryParam("expression") String expression) throws IOException {
        setResponse(expression);

        saveResponseToDb(expression);

        return response;
    }

    private void setResponse(String expression) throws JsonProcessingException {
        try {
            this.response = calculationResultAsJSON(expression);
        } catch (WebException e) {
            if (e.getMessage().equals(GENERAL_EXCEPTION_MESSAGE)) {
                logger.error("{} {}", e.getCause(), e.getMessage());
                this.response = errorResponseAsJSON(e.getMessage(), SC_INTERNAL_SERVER_ERROR);
            } else {
                logger.error("{} {}", e.getCause(), e.getMessage());
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
