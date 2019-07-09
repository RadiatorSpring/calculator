package calculator.parsers;

import calculator.validators.Checker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ReversePolishNotationParser {

    private Checker checker;

    private ReversePolishNotationParser(Checker checker) {
        this.checker = checker;
    }

    public ReversePolishNotationParser() {
        this(new Checker());
    }

    public Queue<String> buildRPNfromElementsOfExpression(List<String> infixNotation) throws IOException {

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

    private void addOperationsAccordingToPriority(Queue<String> queue, Stack<String> stack, String token) throws IOException {
        Properties mapProperties = new Properties();
        InputStream mapPropertiesStream = ClassLoader.getSystemResourceAsStream("map.properties");
        mapProperties.load(Objects.requireNonNull(mapPropertiesStream));

        while (!stack.empty()) {
            int topOfStackPriorityValue = Integer.parseInt(mapProperties.getProperty(stack.peek()));
            int currTokenPriorityValue = Integer.parseInt(mapProperties.getProperty(token));
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
