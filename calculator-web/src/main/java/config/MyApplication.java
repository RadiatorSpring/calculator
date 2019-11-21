package config;

import calculator.Calculator;
import calculator.Computable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import factories.ResponseFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.CalculationsDTO;
import services.CalculatorService;
import web.CalculatorWebService;
import web.ExpressionWebService;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class  MyApplication extends ResourceConfig {
    public MyApplication() {
        register(ExpressionWebService.class);
        register(CalculatorWebService.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ObjectMapper.class).to(ObjectMapper.class);
                bind(CalculatorService.class).to(CalculatorService.class);
                bind(Calculator.class).to(Computable.class);
                bind(ExpressionResultDAO.class).to(ExpressionResultDAO.class);
                bind(ResponseFactory.class).to(ResponseFactory.class);
                bind(CalculationsDAO.class).to(CalculationsDAO.class);
                bind(CalculationsDTO.class).to(CalculationsDTO.class);
            }
        });

    }
}
