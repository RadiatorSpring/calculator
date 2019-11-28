package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.enums.Errors;
import models.wrappers.CalculationResult;
import models.wrappers.ErrorCodeMessage;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.CalculationsDTO;
import persistence.dto.ExpressionResultDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static models.errors.ExceptionMessages.DOES_NOT_EXIST;
import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ExpressionWebService {

    private ObjectMapper mapper;
    private ExpressionResultDAO expressionResultDAO;
    private CalculationsDAO calculationsDAO;

    @Inject
    public ExpressionWebService(ObjectMapper mapper, ExpressionResultDAO expressionResultDAO, CalculationsDAO calculationsDAO) {
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;
        this.calculationsDAO = calculationsDAO;
    }

    @GET
    @Path("/expressions/{id}")
    public Response getExpression(@PathParam(value = "id") long id) throws JsonProcessingException {
        ExpressionResultDTO expressionResultDTO = expressionResultDAO.getEntity(id);
        if (isNull(expressionResultDTO)) {
            return doesNotExist();
        } else {
            CalculationsDTO calculationsDTO = calculationsDAO.getEntity(expressionResultDTO.getExpression());
            if (expressionResultDTO.isEvaluated()) {
                return evaluatedResponse(calculationsDTO);
            } else {
                return isNotEvaluatedResponse();
            }
        }
    }

    private Response evaluatedResponse(CalculationsDTO calculationsDTO) throws JsonProcessingException {
        if (hasError(calculationsDTO)) {
            return errorResponse(calculationsDTO);
        } else {
            return evaluationResponse(calculationsDTO);
        }
    }

    private Response isNotEvaluatedResponse() throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(IS_NOT_EVALUATED, 202);
        String response = mapper.writeValueAsString(errorCodeMessage);

        return Response.status(202).entity(response).build();
    }

    private Response doesNotExist() throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(DOES_NOT_EXIST, 400);
        String response = mapper.writeValueAsString(errorCodeMessage);

        return Response.status(400).entity(response).build();
    }

    private boolean hasError(CalculationsDTO calculationsDTO) {
        if (isNull(calculationsDTO)) {
            return true;
        }
        return calculationsDTO.getError() != null;
    }


    private Response errorResponse(CalculationsDTO calculationsDTO) throws JsonProcessingException {
        if (isNull(calculationsDTO)) {
            return createFailResponseWithErrorMessage(DOES_NOT_EXIST);
        }
        String errorMessage = calculationsDTO.getError();
        return createFailResponseWithErrorMessage(errorMessage);
    }

    private Response createFailResponseWithErrorMessage(String errorMessage) throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = createErrorCodeMessage(errorMessage);
        String json = mapper.writeValueAsString(errorCodeMessage);
        return Response.status(errorCodeMessage.getCode()).entity(json).build();
    }

    private Response evaluationResponse(CalculationsDTO expressionResultDTO) throws JsonProcessingException {
        int statusCodeOK = 200;
        double result = expressionResultDTO.getEvaluation();
        CalculationResult calculationResult = new CalculationResult(result);
        String json = mapper.writeValueAsString(calculationResult);

        return Response.status(statusCodeOK)
                .entity(json).build();
    }

    private ErrorCodeMessage createErrorCodeMessage(String message) {
        int errorCode = Errors.getCodeWithString(message);
        return new ErrorCodeMessage(message, errorCode);
    }


}
