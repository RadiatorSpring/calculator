package services;

import calculator.Calculator;
import calculator.exceptions.CannotDivideByZeroException;

public class CalculatorService {
    private Calculator calculator;

    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public double compute(String expression) throws CannotDivideByZeroException {
        return calculator.compute(expression);
    }
}
