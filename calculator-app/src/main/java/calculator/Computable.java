package calculator;

import calculator.exceptions.CannotDivideByZeroException;

public interface Computable {
    double compute(String expression) throws CannotDivideByZeroException;
}


