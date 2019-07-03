package calculator.operations;

public class OperationFactory {

    public Operation getOperation(String inputOperation) {

        switch (inputOperation) {
            case "*":
                return new Multiplication();
            case "-":
                return new Substraction();
            case "+":
                return new Addition();
            case "/":
                return new Division();

            default:
                return new Operand(Double.parseDouble(inputOperation));
        }


    }
}
