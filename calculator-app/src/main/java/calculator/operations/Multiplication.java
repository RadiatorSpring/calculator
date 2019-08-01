package calculator.operations;

import java.util.Stack;
/**
 * Used to multiply the top 2 elements of stack
 */
public class Multiplication implements Operation, PriorityValue {
    private static final int value = 2;
    @Override
    public void process(Stack<Double> stack) {
        stack.push(stack.pop() * stack.pop());
    }

    @Override
    public int getValue() {
        return value;
    }
}
