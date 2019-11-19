package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.wrappers.CalculationId;
import models.wrappers.CalculatorExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static models.errors.ExceptionMessages.IS_NOT_EVALUATED;


@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorWebService {

    private ObjectMapper mapper;
    private ExpressionResultDAO expressionResultDAO;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorWebService.class);

    @Inject
    public CalculatorWebService(ObjectMapper mapper, ExpressionResultDAO expressionResultDAO) {
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;
    }

    @POST
    @Path("/calculate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveExpression(String expression) throws IOException {
        CalculatorExpression calculatorExpression = mapper.readValue(expression, CalculatorExpression.class);

        long expressionId = saveResponseToDb(calculatorExpression.getExpression(), calculatorExpression.getHistoryId());
        CalculationId id = new CalculationId(expressionId);
        logger.error(String.valueOf(expressionId));
        String idAsJSON = mapper.writeValueAsString(id);
        logger.error(String.valueOf(idAsJSON));

        return Response.status(202)
                .entity(idAsJSON)
                .build();
    }

    private long saveResponseToDb(String expression, String historyId) {
        ExpressionResultDTO expressionResultDTO = new ExpressionResultDTO(expression, IS_NOT_EVALUATED, historyId);
        expressionResultDAO.save(expressionResultDTO);

        return expressionResultDTO.getId();
    }


}



