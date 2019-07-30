package web;

import calculator.CalculatorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import services.CalculatorService;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        register(CalculatorWebService.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ObjectMapper.class).to(ObjectMapper.class);
                bind(CalculatorService.class).to(CalculatorService.class);
                bind(CalculatorFactory.class).to(CalculatorFactory.class);
            }
        });

    }
}
