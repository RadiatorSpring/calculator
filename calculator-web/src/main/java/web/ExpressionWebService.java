package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jdk.nashorn.internal.objects.annotations.Getter;
import models.enums.Errors;
import models.wrappers.ErrorCodeMessage;
import models.wrappers.CalculationResult;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static models.errors.ExceptionMessages.DOES_NOT_EXIST;


@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ExpressionWebService {

    private ObjectMapper mapper;
    private ExpressionResultDAO expressionResultDAO;

    @Inject
    public ExpressionWebService(ObjectMapper mapper, ExpressionResultDAO expressionResultDAO) {
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;
    }

    @GET
    @Path("/expressions")
    public Response getHistory() throws IOException {
        List<ExpressionResultDTO> listOfResults = expressionResultDAO.getAll();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonList = mapper.writeValueAsString(listOfResults);

        return Response.ok().entity(jsonList).build();
    }


    @GET
    @Path("/expressions/{id}")
    public Response getExpression(@PathParam(value = "id") long id) throws JsonProcessingException {
        ExpressionResultDTO expressionResultDTO = expressionResultDAO.getExpression(id);

        if (hasError(expressionResultDTO)) {
            return createErrorResponse(expressionResultDTO);
        } else {
            return createEvaluationResponse(expressionResultDTO);
        }
    }

    private boolean hasError(ExpressionResultDTO expressionResultDTO) {
        if (isNull(expressionResultDTO)) {
            return true;
        }
        return expressionResultDTO.getError() != null;
    }


    private Response createErrorResponse(ExpressionResultDTO expressionResultDTO) throws JsonProcessingException {
        if (isNull(expressionResultDTO)) {
            return createFailResponseWithErrorMessage(DOES_NOT_EXIST);
        }
        String errorMessage = expressionResultDTO.getError();

        return createFailResponseWithErrorMessage(errorMessage);
    }

    private Response createFailResponseWithErrorMessage(String errorMessage) throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = createErrorCodeMessage(errorMessage);
        String json = mapper.writeValueAsString(errorCodeMessage);

        return Response.status(errorCodeMessage.getCode()).entity(json).build();
    }

    private Response createEvaluationResponse(ExpressionResultDTO expressionResultDTO) throws JsonProcessingException {
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
