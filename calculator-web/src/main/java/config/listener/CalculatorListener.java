package config.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CalculatorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class CalculatorListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(CalculatorListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Starting Scheduler...\n");
        startScheduler();
        logger.info("Scheduler started\n");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
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
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
