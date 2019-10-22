package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.errors.ErrorCodeMessage;
import models.errors.ExceptionMessages;
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
    public Response getAllExpressions() throws IOException {
        List<ExpressionResultDTO> listOfResults = expressionResultDAO.getAll();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonList = mapper.writeValueAsString(listOfResults);

        return Response.ok().entity(jsonList).build();
    }

    @GET
    @Path("/expression/{id}")
    public Response getExpression(@PathParam(value = "id") long id) throws JsonProcessingException {
        ExpressionResultDTO expressionResultDTO = expressionResultDAO.getExpression(id);
        String expressionJSON;

        if (expressionResultDTO == null) {
            String errorMessageJSON = createErrorMessageJSON();
            return Response.status(400)
                    .entity(errorMessageJSON)
                    .build();
        } else if (isNotEvaluated(expressionResultDTO)) {
            expressionJSON = createExpressionJSON(expressionResultDTO);
            return Response.accepted()
                    .entity(expressionJSON)
                    .build();
        } else {
            expressionJSON = createExpressionJSON(expressionResultDTO);
            return Response.status(200)
                    .entity(expressionJSON)
                    .build();
        }
    }

    private boolean isNotEvaluated(ExpressionResultDTO expressionResultDTO) {
        if (expressionResultDTO.getError() == null) {
            return false;
        }
        return expressionResultDTO
                .getError()
                .equals(ExceptionMessages.IS_NOT_EVALUATED);
    }

    private String createErrorMessageJSON() throws JsonProcessingException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(ExceptionMessages.DOES_NOT_EXIST, 400);
        return mapper.writeValueAsString(errorCodeMessage);
    }

    private String createExpressionJSON(ExpressionResultDTO expressionResultDTO) throws JsonProcessingException {
        return mapper.writeValueAsString(expressionResultDTO);
    }
}
