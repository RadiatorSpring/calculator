package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Expression;
import models.Id;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;
import services.CalculatorService;

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
    private Response response;
    private final Logger logger = LoggerFactory.getLogger(CalculatorWebService.class);

    @Inject
    public CalculatorWebService(ObjectMapper mapper, ExpressionResultDAO expressionResultDAO) {
        this.mapper = mapper;
        this.expressionResultDAO = expressionResultDAO;

    }

    @POST
    @Path("/calculate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveExpression(Expression expression) throws IOException {
        startScheduler();

        long expressionId = saveResponseToDb(expression.getExpression());
        Id id = new Id(expressionId);
        String idAsJSON = mapper.writeValueAsString(id);

        return Response.status(202)
                .entity(idAsJSON)
                .build();
    }

    private long saveResponseToDb(String expression) {
        ExpressionResultDTO expressionResultDTO = new ExpressionResultDTO(expression, IS_NOT_EVALUATED);
        expressionResultDAO.save(expressionResultDTO);
        return expressionResultDTO.getId();
    }

    private void startScheduler() {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail job = JobBuilder.newJob(CalculatorService.class)
                    .withIdentity("evaluator", "calculations")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(4)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
