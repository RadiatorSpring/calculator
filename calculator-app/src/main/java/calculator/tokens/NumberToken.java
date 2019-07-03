package calculator.tokens;

import java.util.List;

public class NumberToken implements Token {

    private char symbolToAdd;

    public NumberToken(char symbolToAdd) {
        this.symbolToAdd = symbolToAdd;
    }

    @Override
    public void process(StringBuilder numberBuilder, List<String> list) {
        numberBuilder.append(symbolToAdd);
    }
}
