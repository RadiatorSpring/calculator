package aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dto.CalculationsDTO;

@Aspect
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(void persistence.dao.CalculationsDAO.save(*)) && args(calculation)")
    public void pointcutSave(CalculationsDTO calculation) {
    }

    @Before("pointcutSave(calculation)")
    public void logSave(CalculationsDTO calculation) {
        logger.error("The dto is : " + calculation.toString());
    }

    @Pointcut("execution(double services.CalculatorService.compute(*))" + " && args(expression)")
    public void pointCutInService(String expression) {
    }

    @AfterThrowing(value = "pointCutInService(expression)", throwing = "exception")
    public void logErrorInService(String expression, Exception exception) {
        logger.error("The expression is " + expression);
        logger.error("The error is " + exception);
    }

}
