package calculator.operations;

import calculator.validators.Checker;

/**
 * Used for operation creation.
 */
public class OperationFactory {

    private Checker checker;

    public OperationFactory(Checker checker) {
        this.checker = checker;
    }

    public OperationFactory() {
        this.checker = new Checker();
    }

    public Operation getOperation(String inputOperation) {
        BasicExpressionElement operation = getBasicOperation(inputOperation);
        return (Operation) operation;
    }

    public int getOperationPriority(String inputOperation) {
        BasicExpressionElement operation = getBasicOperation(inputOperation);
        return ((PriorityValue) operation).getValue();
    }

    private BasicExpressionElement getBasicOperation(String s) {
        switch (s) {
            case "*":
                return new Multiplication();
            case "-":
                return new Subtraction();
            case "+":
                return new Addition();
            case "/":
                return new Division();
            case "(":
                return new Bracket();
        }
        if (checker.isNumber(s)) {
            return new Operand(Double.parseDouble(s));
        }

        throw new IllegalArgumentException("You can only use basic operations");
    }

}
