package calculator;

import calculator.operations.Operation;
import calculator.operations.OperationFactory;

import java.util.*;

public class Calculator {

    private Parser parser;
    private OperationFactory operationFactory;

    public Calculator(Parser parser, OperationFactory operationFactory) {
        this.parser = parser;
        this.operationFactory=operationFactory;
    }

    public double compute(String expression) {
        Queue<String> expressionQueue = parser.convertInfixToRPN(expression);
        if(expressionQueue.size()<3){
            throw new IllegalArgumentException("There should be at least 2 operands and 1 operator");
        }
        return compute(expressionQueue);

    }

    private double compute(Queue<String> expr) throws ArithmeticException, EmptyStackException {
        Stack<Double> stack = new Stack<>();

        for (String token : expr) {
            Operation operation = operationFactory.getOperation(token);
            operation.process(stack);
        }

        return stack.pop();
    }


}
















