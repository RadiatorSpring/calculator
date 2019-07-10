package calculator.operations;

import java.util.Stack;
/**
 * Used to divide the top 2 elements of stack
 */
public class Division implements Operation {
    @Override
    public void process(Stack<Double> stack) {
        double divisor = stack.pop();
        stack.push(stack.pop() / divisor);
    }
}
