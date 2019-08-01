package calculator.operations;

import java.util.Stack;

/**
 * Used to create a number
 */
public class Operand implements Operation {
    private double val;


    public Operand(double val) {
        this.val = val;
    }

    @Override
    public void process(Stack<Double> stack) {
        stack.push(val);
    }


}
