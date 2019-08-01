package calculator.parsers;

import calculator.operations.OperationFactory;
import calculator.validators.Checker;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Orders the given expression into a RPN Queue
 */
public class ReversePolishNotationParser {

    private Checker checker;
    private OperationFactory operationFactory;

    /**
     * @param checker used to check for certain types
     */
    public ReversePolishNotationParser(Checker checker, OperationFactory operationFactory) {
        this.checker = checker;
        this.operationFactory = operationFactory;
    }

    ReversePolishNotationParser() {
        this.checker = new Checker();
        this.operationFactory = new OperationFactory();
    }

    /**
     * Converts the expression into Queue Ordered in RPN
     *
     * @param infixNotation the expression separated in logical elements in infix notation
     * @return Queue ordered in Reverse Polish Notation
     */
    Queue<String> buildRPNfromElementsOfExpression(List<String> infixNotation) {

        Queue<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        for (String token : infixNotation) {
            if (isOpeningBracket(token)) {
                stack.push(token);
            } else if (isClosingBracket(token)) {
                addExpressionFromBrackets(queue, stack);
            } else if (checker.isOperation(token)) {
                addOperationsAccordingToPriority(queue, stack, token);
            } else if (checker.isNumber(token)) {
                queue.add(token);
            }
        }
        addRemainingTokens(queue, stack);
        return queue;
    }

    private boolean isClosingBracket(String input) {
        return ")".equals(input);
    }

    private boolean isOpeningBracket(String input) {
        return "(".equals(input);
    }

    private void addExpressionFromBrackets(Queue<String> queue, Stack<String> stack) {
        while (!"(".equals(stack.peek())) {
            queue.add(stack.pop());
        }
        stack.pop();
    }

    private void addOperationsAccordingToPriority(Queue<String> queue, Stack<String> stack, String token) {
        while (!stack.empty()) {
            int topOfStackPriorityValue = operationFactory.getOperationPriority(stack.peek());
            int currTokenPriorityValue = operationFactory.getOperationPriority(token);

            if (currTokenPriorityValue <= topOfStackPriorityValue) {
                queue.add(stack.pop());
            } else {
                break;
            }
        }
        stack.push(token);
    }

    private void addRemainingTokens(Queue<String> queue, Stack<String> stack) {
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
    }


}
