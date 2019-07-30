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

    public boolean validateExpression(String text) {
        Pattern noLettersPattern = Pattern.compile(".*[^\\d().+\\-*/^ ].*");
        Matcher noLettersMatcher = noLettersPattern.matcher(text);

        Pattern spacesPattern = Pattern.compile(".*\\d+ +\\d+.*");
        Matcher spaceMatcher = spacesPattern.matcher(text);

        Pattern invalidBracketsPattern = Pattern.compile("(.*\\d+\\(.*)|(.*\\) \\d+)|(\\( *\\).*)");
        Matcher invalidBracketsMatcher = invalidBracketsPattern.matcher(text);

        return noLettersMatcher.matches() || spaceMatcher.matches() || invalidBracketsMatcher.matches();

    }


    public boolean isDigit(char c) {
        return isNumber(c);
    }

    public boolean isOperation(String token) {
        return token.matches("[-+*/]");
    }
}
