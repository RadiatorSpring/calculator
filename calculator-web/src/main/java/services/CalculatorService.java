package services;

import calculator.Calculator;
import calculator.CalculatorFactory;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.OperationFactory;
import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.Parser;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;

import javax.inject.Inject;

public class CalculatorService {
    private Calculator calculator;
    private CalculatorFactory calculatorFactory = new CalculatorFactory();

    @Inject
    public CalculatorService(CalculatorFactory calculatorFactory) {
        this.calculator = calculatorFactory.createCalculator();
    }



    public double compute(String expression) throws CannotDivideByZeroException {
        return calculator.compute(expression);
    }
}
