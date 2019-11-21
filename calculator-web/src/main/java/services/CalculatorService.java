package services;

import calculator.Calculator;
import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;
import models.errors.ExceptionMessages;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.CalculationsDTO;
import persistence.dto.ExpressionResultDTO;

import javax.inject.Inject;
import java.util.EmptyStackException;
import java.util.List;

import static models.errors.ExceptionMessages.*;

public class CalculatorService implements Job {
    private Computable computable;
    private final Logger logger = LoggerFactory.getLogger(CalculatorService.class);
    private CalculationsDAO calculationsDAO;
    private ExpressionResultDAO expressionResultDAO;

    public CalculatorService() {
        this.computable = new Calculator();
        this.calculationsDAO = new CalculationsDAO();
        this.expressionResultDAO = new ExpressionResultDAO();
    }

    public CalculatorService(Computable computable, ExpressionResultDAO CalculationsDAO) {
        this.computable = computable;
    }

    double compute(String expression) throws WebException {
        if (!isEmptyExpression(expression)) {
            try {
                return computable.compute(expression);
            } catch (CannotDivideByZeroException e) {
                logger.error(e.getMessage());
                throw new WebException(CANNOT_DIVIDE_BY_ZERO);
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
                throw new WebException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
            } catch (EmptyStackException e) {
                logger.error(e.getMessage());
                throw new WebException(EMPTY_STACK_EXCEPTION_MESSAGE);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new WebException(GENERAL_EXCEPTION_MESSAGE);
            }
        }
        throw new WebException(EMPTY_PARAMETER_EXCEPTION);
    }

    private boolean isEmptyExpression(String expression) {
        if (expression == null) return true;
        else return expression.isEmpty();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<ExpressionResultDTO> list = expressionResultDAO.getAllNotEvaluated();

        for (ExpressionResultDTO expressionResultDTO : list) {
            CalculationsDTO notEvaluatedCalculation = calculationsDAO.getEntity(expressionResultDTO.getExpression());
            updateWithEvaluations(notEvaluatedCalculation);
            updateIsEvaluated(expressionResultDTO);
        }
    }

    private void updateIsEvaluated(ExpressionResultDTO expressionResultDTO) {
        expressionResultDAO.updateIsEvaluated(expressionResultDTO.getId());
    }

    private void updateWithEvaluations(CalculationsDTO calculationsDTO) {
        double result = 0;
        String error = null;

        try {
            result = this.compute(calculationsDTO.getExpression());
        } catch (WebException e) {
            error = e.getMessage();
        }

        if (!isInternalServerError(error)) {
            calculationsDAO.update(calculationsDTO.getExpression(), result, error);
        }
    }

    private boolean isInternalServerError(String error) {
        if (error != null) {
            return error.equals(GENERAL_EXCEPTION_MESSAGE);
        }
        return false;
    }
}
