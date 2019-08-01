package calculator.operations;

/**
 * Used for operation creation.
 */
public class OperationFactory {


    public Operation getOperation(String inputOperation) {
        Operation operation = getBasicOperation(inputOperation);
        if (isBasicOperation(operation)) {
            return operation;
        } else {
            return createOperand(inputOperation);
        }

    }

    public int getOperationPriority(String inputOperation) {
        Operation operation = getBasicOperation(inputOperation);
        if (isBasicOperation(operation)) {
            return ((PriorityValue) operation).getValue();
        }
        else{
            return new Bracket().getValue();
        }
    }

    private Operation getBasicOperation(String s) {
        switch (s) {
            case "*":
                return new Multiplication();
            case "-":
                return new Subtraction();
            case "+":
                return new Addition();
            case "/":
                return new Division();
            default:
                return null;
        }
    }

    private boolean isBasicOperation(Operation operation) {
        return operation != null;
    }

    private Operation createOperand(String inputOperation) {
        double input = Double.parseDouble(inputOperation);
        return new Operand(input);
    }
}
