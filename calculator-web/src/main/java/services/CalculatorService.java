package services;

import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.CalculatorWebService;

import javax.inject.Inject;
import java.util.EmptyStackException;
import java.util.logging.SimpleFormatter;

import static models.errors.ExceptionMessages.*;

public class CalculatorService {
    private Computable computable;
    private final Logger logger = LoggerFactory.getLogger(CalculatorService.class);


    @Inject
    public CalculatorService(Computable computable) {
        this.computable = computable;
    }

    public double compute(String expression) throws WebException {

        if (!isEmptyExpression(expression))  {
            try {
                return computable.compute(expression);
            } catch (CannotDivideByZeroException e) {
                throw new WebException(CANNOT_DIVIDE_BY_ZERO);
            } catch (IllegalArgumentException e) {
                throw new WebException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
            } catch (EmptyStackException e) {
                throw new WebException(EMPTY_STACK_EXCEPTION_MESSAGE);
            } catch (Exception e) {
                throw new WebException(GENERAL_EXCEPTION_MESSAGE);
            }
        }
        logger.error("the expression passed was -> " + expression);
        throw new WebException(EMPTY_PARAMETER_EXCEPTION);
    }

    private boolean isEmptyExpression(String expression) {

        if(expression == null) return true;
        else return expression.isEmpty();
    }
}
