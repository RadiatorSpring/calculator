package unit.services;

import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;
import exceptions.WebException;

import javax.inject.Inject;
import java.util.EmptyStackException;

import static models.errors.ExceptionMessages.*;

public class CalculatorService {
    private Computable computable;

    @Inject
    public CalculatorService(Computable computable) {
        this.computable = computable;
    }

    public double compute(String expression) throws WebException {
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
        return expression != null && expression.isEmpty();
    }
}
