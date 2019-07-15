package calculator.parsers;

import calculator.enums.OperationPriority;
import calculator.validators.Checker;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Orders the given expression into a RPN Queue
 */
public class ReversePolishNotationParser {

    private Checker checker;

    /**
     * @param checker used to check for certain types
     */
    public ReversePolishNotationParser(Checker checker) {
        this.checker = checker;
    }

    /**
     * Converts the expression into Queue Ordered in RPN
     *
     * @param infixNotation the expression separated in logical elements in infix notation
     * @return Queue ordered in Reverse Polish Notation
     */
    public Queue<String> buildRPNfromElementsOfExpression(List<String> infixNotation) {

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
            int topOfStackPriorityValue = OperationPriority.getOperationPriority(stack.peek());
            int currTokenPriorityValue = OperationPriority.getOperationPriority(token);

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
