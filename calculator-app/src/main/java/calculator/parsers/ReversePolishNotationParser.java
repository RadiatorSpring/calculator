package calculator.parsers;

import calculator.validators.Checker;

import java.util.*;

public class ReversePolishNotationParser {

    private Checker checker;

    private ReversePolishNotationParser(Checker checker) {
        this.checker = checker;
    }

    public ReversePolishNotationParser() {
        this(new Checker());
    }

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
        Map<String, Integer> operations = initPrioritiesOfOperations();


        while (!stack.empty() && operations.get(token) <= operations.get(stack.peek())) {
            queue.add(stack.pop());
        }
        stack.push(token);
    }
//todo refactor this
    private Map<String, Integer> initPrioritiesOfOperations() {
        Map<String, Integer> priority = new HashMap<>();
        priority.put("/", 2);
        priority.put("*", 2);
        priority.put("+", 1);
        priority.put("-", 1);
        priority.put("(", 0);
        return priority;
    }

    private void addRemainingTokens(Queue<String> queue, Stack<String> stack) {

        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
    }
}
