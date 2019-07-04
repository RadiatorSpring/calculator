package calculator;

import calculator.validators.Checker;

import java.util.*;

public class Parser {


    private Checker checker;


    public Parser(Checker checker) {
        this.checker = checker;
    }

    Queue<String> convertInfixToRPN(String expression) {
        List<String> infixNotation = textToInfix(expression);
        return convertInfixToRPN(infixNotation);
    }


    List<String> textToInfix(String text) {
        if (checker.validateExpression(text) || text.isEmpty()) {
            throw new IllegalArgumentException("There cannot be spaces between numbers, there cannot be letters");
        }
        StringBuilder builder = new StringBuilder();
        List<String> elements = new ArrayList<>();
        char[] symbols = text.toCharArray();

        for (char symbol : symbols) {
            String symbolString = Character.toString(symbol);

            if (checker.isOperationOrBracket(symbol) && builder.length() != 0) {
                elements.add(builder.toString());
                elements.add(symbolString);
                builder.delete(0, builder.length());
            } else if (checker.isNumber(symbol)) {
                builder.append(symbol);
            } else {
                elements.add(symbolString);
            }
        }
        addRemainingNumber(elements, builder);

        return elements;
    }

    private void addRemainingNumber(List<String> elements, StringBuilder builder) {
        if (builder.length() != 0) elements.add(builder.toString());
    }


    Queue<String> convertInfixToRPN(List<String> infixNotation) {

        Map<String, Integer> operations = new HashMap<>();
        initPriorities(operations);

        Queue<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        for (String token : infixNotation) {
            if (isOpeningBracket(token)) {
                stack.push(token);
            } else if (isClosingBracket(token)) {
                addExpressionFromBrackets(queue, stack);
            } else if (checker.isOperationOrBracket(token)) {
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
        Map<String, Integer> operations = new HashMap<>();
        initPriorities(operations);

        while (!stack.empty() && operations.get(token) <= operations.get(stack.peek())) {
            queue.add(stack.pop());
        }
        stack.push(token);
    }

    private void initPriorities(Map<String, Integer> priority) {
        priority.put("/", 2);
        priority.put("*", 2);
        priority.put("+", 1);
        priority.put("-", 1);
        priority.put("(", 0);
    }

    private void addRemainingTokens(Queue<String> queue, Stack<String> stack) {

        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
    }

}
