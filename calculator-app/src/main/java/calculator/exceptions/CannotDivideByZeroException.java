package calculator.exceptions;

public class CannotDivideByZeroException extends Exception {
    public CannotDivideByZeroException(String string) {
        super(string);
    }

    public CannotDivideByZeroException() {
    }
}
