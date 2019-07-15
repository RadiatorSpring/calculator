package calculator.enums;

import calculator.operations.Operation;

public enum OperationPriority {
    BRACKET(0), PLUS(1), MINUS(1), MULTIPLICATION(2), DIVISION(2);

    int value;


    OperationPriority(int value) {
        this.value = value;
    }

    public static int getOperationPriority(String s){
        switch (s){
            case "+":return OperationPriority.PLUS.value;
            case "-":return OperationPriority.MINUS.value;
            case "/":return OperationPriority.DIVISION.value;
            case "*":return OperationPriority.MULTIPLICATION.value;
            default: return OperationPriority.BRACKET.value;
        }
    }
}
