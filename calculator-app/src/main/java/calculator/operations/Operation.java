package calculator.operations;

import java.util.Stack;

/**
 * Common interface implemented by all Operations.
 * Used by OperationFactory for easy addition of new Operations.
 */
public interface Operation extends BasicExpressionElement{
    void process(Stack<Double> stack);

}
