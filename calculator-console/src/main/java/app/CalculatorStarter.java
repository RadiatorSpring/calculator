package app;

import calculator.Calculator;
import calculator.Parser;
import calculator.operations.OperationFactory;

public class CalculatorStarter {

    public static void main(String[] args) {
        Calculator calculator = new Calculator(new Parser(),new OperationFactory());
        double result = calculator.compute(args[0]);
        System.out.print(result);

    }


}
