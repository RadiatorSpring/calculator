package calculator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {

    public boolean isNumber(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private boolean isNumber(char c) {
        return isNumber(Character.toString(c));
    }

    public boolean isDigit(char c) {
        return isNumber(c);
    }

    public boolean isOperation(String token) {
        return token.matches("[-+*/]");
    }
}
