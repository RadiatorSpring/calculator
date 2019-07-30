package calculator;

import calculator.operations.OperationFactory;
import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.Parser;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;

/**
 * Used for creating calculator and not caring for its dependencies
 */
public class CalculatorFactory {

    public Calculator createCalculator(){
        return new Calculator(new Parser(new Checker(), new ExpressionParser(new Checker()), new ReversePolishNotationParser(new Checker()), new NegativeNumbersBuilder()), new OperationFactory());
    }

}
