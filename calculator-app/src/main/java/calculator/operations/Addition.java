package calculator.operations;

import java.util.Stack;

public class Addition implements Operation {
    @Override
    public void process(Stack<Double> stack) {
        stack.push(stack.pop() + stack.pop());
    }
}
