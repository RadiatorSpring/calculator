package calculator.tokens;

import calculator.validators.Checker;

import java.util.List;

public class OperationToken implements Token {
    private Checker checker;
    private char symbolToAdd;

    OperationToken(char symbolToAdd) {
        this.checker = new Checker();
        this.symbolToAdd = symbolToAdd;
    }

    @Override
    public void process(StringBuilder numberBuilder, List<String> list) {
        attemptAddAndClearNumberBuilder(list,numberBuilder);
        list.add(Character.toString(symbolToAdd));
    }

    private void attemptAddAndClearNumberBuilder(List<String> list,StringBuilder numberBuilder){
        String input = numberBuilder.toString();
        if (!input.isEmpty() && checker.isNumber(input)){
            list.add(input);
            numberBuilder.delete(0,numberBuilder.length());
        }
    }

}
