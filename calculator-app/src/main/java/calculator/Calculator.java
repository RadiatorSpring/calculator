
package calculator;

import calculator.exceptions.CannotDivideByZeroException;
import calculator.operations.Operation;
import calculator.operations.OperationFactory;
import calculator.parsers.ParserOrchestrator;

import java.util.*;


/**
 * Represents a simple calculator.
 * A compute method is provided
 * which in turn returns the result.
 */
public class Calculator {
    private ParserOrchestrator parserOrchestrator;
    private OperationFactory operationFactory;


    public Calculator(ParserOrchestrator parserOrchestrator, OperationFactory operationFactory) {
        this.parserOrchestrator = parserOrchestrator;
        this.operationFactory = operationFactory;
    }


    public Calculator() {
        this.parserOrchestrator = new ParserOrchestrator();
        this.operationFactory = new OperationFactory();
    }


    /**
     * Passes the initial String to the parserOrchestrator in order to get RPN and calculate it
     *
     * @param expression the string representation of mathematical expression
     * @return the resulting number
     * @throws CannotDivideByZeroException if the expression tries to divide by zero
     * @throws EmptyStackException         if there are too many operators and too little operands in the expression
     * @throws IllegalArgumentException    if the passed expression has less than 3 arguments
     */
    public double compute(String expression) throws CannotDivideByZeroException, EmptyStackException, IllegalArgumentException {
        Queue<String> expressionQueue = parserOrchestrator.convertExpressionToRPN(expression);
        if (expressionQueue.size() < 3) {
            throw new IllegalArgumentException("There should be at least 2 operands and 1 operator in infix order");
        }
        return compute(expressionQueue);

    }


    private double compute(Queue<String> expr) throws EmptyStackException, CannotDivideByZeroException {
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
















