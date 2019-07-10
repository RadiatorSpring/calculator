package calculator.operations;

import java.util.Stack;

/**
 * Used to subtract the top 2 elements of stack
 */
public class Subtraction implements Operation {

    @Override
    public void process(Stack<Double> stack) {
        stack.push(-stack.pop() + stack.pop());
    }
}
