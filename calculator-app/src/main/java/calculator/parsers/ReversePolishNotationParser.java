package calculator.parsers;

import calculator.operations.OperationEvaluator;
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
    private OperationEvaluator evaluator;

    /**
     * @param checker used to check for certain types
     */
    public ReversePolishNotationParser(Checker checker, OperationEvaluator evaluator) {
        this.checker = checker;
        this.evaluator = evaluator;
    }

    ReversePolishNotationParser() {
        this.checker = new Checker();
        this.evaluator = new OperationEvaluator();
    }

    /**
     * Converts the expression into Queue Ordered in RPN
     *
     * @param infixNotation the expression separated in logical elements in infix notation
     * @return Queue ordered in Reverse Polish Notation
     */
    Queue<String> convertToRPN(List<String> infixNotation) {

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
            int topOfStackPriorityValue = evaluator.getOperationPriority(stack.peek());
            int currTokenPriorityValue = evaluator.getOperationPriority(token);

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
