package config.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CalculatorService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class CalculatorListener implements ServletContextListener {

    private Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorListener.class);
    private static final String TRIGGER_FOR_CALCULATION = "CalculationTrigger";
    private static final String CALCULATION_GROUP = "calculations";
    private static final String CALCULATOR_EVALUATOR = "evaluator";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        startScheduler();

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void startScheduler() {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();

            JobDetail job = JobBuilder.newJob(CalculatorService.class)
                    .withIdentity(CALCULATOR_EVALUATOR, CALCULATION_GROUP)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TRIGGER_FOR_CALCULATION, CALCULATION_GROUP)
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);
            logger.error(String.valueOf(scheduler));
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }


    public Scheduler getScheduler() {
        return scheduler;
    }
}
