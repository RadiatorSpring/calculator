package services;

import calculator.Calculator;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.OperationFactory;
import calculator.parsers.Parser;

public class CalculatorService {
    private Calculator calculator;

    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }



    public double compute(String expression) throws CannotDivideByZeroException {
        return calculator.compute(expression);
    }
}
