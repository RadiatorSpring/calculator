package services;

import calculator.Computable;
import calculator.exceptions.CannotDivideByZeroException;

import javax.inject.Inject;

public class CalculatorService {
    private Computable computable;

    @Inject
    public CalculatorService(Computable computable) {
        this.computable = computable;
    }

    public double compute(String expression) throws CannotDivideByZeroException {
        return computable.compute(expression);
    }
}
