package web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.wrappers.HistoryId;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1")
public class HistoryWebService {
    private ObjectMapper mapper;
    private ExpressionResultDAO expressionResultDAO;

    @Inject
    public HistoryWebService(ObjectMapper mapper, ExpressionResultDAO expressionResultDAO) {
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;
    }

    @GET
    @Path("/history/{historyId}")
    public Response getSessionHistory(@PathParam("historyId") String historyId) throws JsonProcessingException {
        List<ExpressionResultDTO> listOfResults = expressionResultDAO.getHistory(historyId);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonList = mapper.writeValueAsString(listOfResults);

        return Response.ok().entity(jsonList).build();
    }

    @GET
    @Path("/history/id")
    public Response getHistoryId() throws JsonProcessingException {
        UUID uuid = UUID.randomUUID();
        HistoryId historyId = new HistoryId(uuid.toString());
        String response = mapper.writeValueAsString(historyId);

        return Response.ok().entity(response).build();
    }

}
