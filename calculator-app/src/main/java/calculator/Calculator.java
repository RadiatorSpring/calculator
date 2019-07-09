package calculator;

import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.Operation;
import calculator.operations.OperationFactory;

import java.io.IOException;
import java.util.*;

public class Calculator {

    private Parser parser;
    private OperationFactory operationFactory;

    private Calculator(Parser parser, OperationFactory operationFactory) {
        this.parser = parser;
        this.operationFactory = operationFactory;
    }

    public Calculator() {
       this(new Parser(),new OperationFactory());
    }

    public double compute(String expression) throws CannotDivideByZeroException, EmptyStackException, IllegalArgumentException, IOException {
        Queue<String> expressionQueue = parser.convertExpressionToRPN(expression);
        if (expressionQueue.size() < 3) {
            throw new IllegalArgumentException("There should be at least 2 operands and 1 operator in infix order");
        }
        return compute(expressionQueue);

    }

    private double compute(Queue<String> expr) throws ArithmeticException, EmptyStackException, CannotDivideByZeroException {
        Stack<Double> stack = new Stack<>();

        for (String token : expr) {
            Operation operation = operationFactory.getOperation(token);
            operation.process(stack);
        }
        if (Double.isInfinite(stack.peek())) {
            throw new CannotDivideByZeroException("You cannot divide by zero");
        }
        return stack.pop();
    }


}
















