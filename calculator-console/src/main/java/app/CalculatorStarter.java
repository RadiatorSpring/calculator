package app;

import calculator.Calculator;
import calculator.Parser;
import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.OperationFactory;
import calculator.validators.Checker;

import java.util.EmptyStackException;

public class CalculatorStarter {

    public static void main(String[] args) {


        if (args == null || args[0].length() < 3) {
            System.out.println("There should be at least 2 operands and 1 operator");
            return;
        }
        Calculator calculator = new Calculator();

//todo make Exceptions compile time
        try {
            double result = calculator.compute(args[0]);
            System.out.print(result);
        } catch (CannotDivideByZeroException e) {
            System.out.println("You cannot divide by zero");
        } catch (EmptyStackException e) {
            System.out.println("The number of operators cannot be greater than the number of operands");
        } catch (IllegalArgumentException e) {
            System.out.println("There cannot be letters nor spaces between digits");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }


}
