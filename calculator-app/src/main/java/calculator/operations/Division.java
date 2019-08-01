package calculator.operations;

import java.util.Stack;
/**
 * Used to divide the top 2 elements of stack
 */
public class Division implements Operation, PriorityValue {

    private static final int value = 2;

    @Override
    public void process(Stack<Double> stack) {
        double divisor = stack.pop();
        stack.push(stack.pop() / divisor);
    }

    @Override
    public int getValue() {
        return value;
    }
}
