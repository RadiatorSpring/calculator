package services;

import calculator.Calculator;
import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import persistence.dao.CalculationsDAO;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.CalculationsDTO;
import persistence.dto.ExpressionResultDTO;

import java.util.EmptyStackException;
import java.util.List;

import static models.errors.ExceptionMessages.*;

public class CalculatorService implements Job {
    private Computable computable;
    private CalculationsDAO calculationsDAO;
    private ExpressionResultDAO expressionResultDAO;

    public CalculatorService() {
        this.computable = new Calculator();
        this.calculationsDAO = new CalculationsDAO();
        this.expressionResultDAO = new ExpressionResultDAO();
    }

    public CalculatorService(Computable computable) {
        this.computable = computable;
    }

    double compute(String expression) throws WebException {
        if (!isEmptyExpression(expression)) {
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
        throw new WebException(EMPTY_PARAMETER_EXCEPTION);
    }

    private boolean isEmptyExpression(String expression) {
        if (expression == null) return true;
        else return expression.isEmpty();
    }

    @SuppressWarnings("RedundantThrows")
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
