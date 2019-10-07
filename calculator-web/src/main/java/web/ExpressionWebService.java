package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/expressions")
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
    public Response getAllExpressions() throws IOException {
        List<ExpressionResultDTO> listOfResults = expressionResultDAO.getAll();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonList = mapper.writeValueAsString(listOfResults);

        return Response.ok().entity(jsonList).build();
    }


}
