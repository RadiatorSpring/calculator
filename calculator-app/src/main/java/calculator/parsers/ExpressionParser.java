package calculator.parsers;

import calculator.validators.Checker;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    private Checker checker;

    private ExpressionParser(Checker checker) {
        this.checker = checker;
    }

    public ExpressionParser() {
        this(new Checker());
    }

    public List<String> expressionToNumbersAndOperations(String text) {
        StringBuilder numberBuilder = new StringBuilder();
        List<String> elements = new ArrayList<>();
        char[] symbols = text.toCharArray();

        for (char symbol : symbols) {
            String symbolString = Character.toString(symbol);
            if (checker.isDigit(symbol)) {
                numberBuilder.append(symbol);
            } else {
                elements.add(numberBuilder.toString());
                elements.add(symbolString);
                numberBuilder.delete(0, numberBuilder.length());
            }
        }
        addRemainingNumber(elements, numberBuilder);

        return elements;
    }

    private void addRemainingNumber(List<String> elements, StringBuilder builder) {
        if (builder.length() != 0) elements.add(builder.toString());
    }



}
