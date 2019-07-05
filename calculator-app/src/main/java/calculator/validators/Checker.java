package calculator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {


    public boolean isNumber(String str) {
        return isNumber(str.toCharArray()[0]);
    }

    private boolean isNumber(char c) {
        try {
            Double.valueOf(Character.toString(c));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateExpression(String text) {
        Pattern pattern = Pattern.compile(".*[^\\d()+\\-*/^ ].*");
        Matcher matcher = pattern.matcher(text);
        Pattern spacesPattern = Pattern.compile(".*\\d+ +\\d+.*");
        Matcher spaceMatcher = spacesPattern.matcher(text);
        return matcher.matches() || spaceMatcher.matches();

    }
    boolean isOperationOrBracket(char c) {
        return Character.toString(c).matches("[-+/*)(]");
    }

    public boolean isDigit(char c){
        return isNumber(c);
    }

    public boolean isOperation(String token) {
        return token.matches("[-+*/]");
    }
}
