package services;

import calculator.Calculator;
import calculator.CalculatorFactory;
import calculator.exceptions.CannotDivideByZeroException;

import javax.inject.Inject;

public class CalculatorService {
    private Calculator calculator;


    @Inject
    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public double compute(String expression) throws CannotDivideByZeroException {
        return calculator.compute(expression);
    }
}
