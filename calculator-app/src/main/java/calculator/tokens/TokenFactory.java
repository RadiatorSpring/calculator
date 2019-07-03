package calculator.tokens;

import calculator.validators.Checker;

public class TokenFactory {
    private Token token;
    private Checker checker;

    public TokenFactory() {
        this.checker = new Checker();
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
