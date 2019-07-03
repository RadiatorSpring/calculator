package calculator.tokens;

import calculator.validators.Checker;

public class TokenFactory {

    private Checker checker;
//todo add tests for checker
    public TokenFactory(Checker checker) {
        this.checker = checker;
    }

    public Token getTokenOfExpression(char c) {
        if (checker.isNumber(c)) {
            return new NumberToken(c);
        } else if (checker.isOperationOrBracket(c)) {
            return new OperationToken(c);
        } else {
            return null;
        }
    }


}
