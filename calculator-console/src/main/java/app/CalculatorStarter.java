package app;

import calculator.Calculator;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.Parser;
import calculator.operations.OperationFactory;
import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;

import java.util.EmptyStackException;

public class CalculatorStarter {

    //todo add TDD rest services
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("There should be at least 2 operands and 1 operator");
            return;
        }
        if (args[0].length() < 3) {
            System.out.println("There should be at least 2 operands and 1 operator");
            return;
        }
        Parser parser = new Parser(new Checker(), new ExpressionParser(new Checker()),new ReversePolishNotationParser(new Checker()),new NegativeNumbersBuilder());

        Calculator calculator = new Calculator(parser,new OperationFactory());
        try {
            double result = calculator.compute(args[0]);
            System.out.print(result);
        } catch (CannotDivideByZeroException e) {
            System.out.println("You cannot divide by zero");
        } catch (EmptyStackException e) {
            System.out.println("The number of operators cannot be greater than the number of operands, using negative numbers requires brackets");
        } catch (IllegalArgumentException e) {
            System.out.println("There cannot be letters nor spaces between digits" +
                    " and there should be at least 2 operands and 1 operator");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }


}
