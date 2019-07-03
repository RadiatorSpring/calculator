package calculator.operations;

import java.util.Stack;

public class Division implements Operation {
    @Override
    public void process(Stack<Double> stack) {
        double divisor = stack.pop();
        stack.push(stack.pop() / divisor);
    }
}
