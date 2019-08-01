package calculator.operations;

import java.util.Stack;
/**
 * Used to add the top 2 elements of stack
 */
public class Addition implements Operation, PriorityValue {
    private static final int value = 1;

    @Override
    public void process(Stack<Double> stack) {
        stack.push(stack.pop() + stack.pop());
    }


    @Override
    public int getValue() {
        return value;
    }
}
