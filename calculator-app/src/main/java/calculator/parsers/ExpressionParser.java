package calculator.parsers;

import calculator.validators.Checker;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes the initial separation of logical elements of the expression
 */
public class ExpressionParser {

    private Checker checker;

    /**
     * @param checker used to check for certain types
     */
    public ExpressionParser(Checker checker) {
        this.checker = checker;
    }


    /**
     * Finds and adds numbers and operations in the List
     * @param text the mathematical expression
     * @return a List of logically separated elements of expression e.g. 11+11 -> {"11", "+", "11"}
     */
    List<String> expressionToNumbersAndOperations(String text) {
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

    /**
     * Adds the remaining number from builder if there is one
     * @param elements the List of elements
     * @param numberBuilder the stringBuilder used for number creation
     */
    private void addRemainingNumber(List<String> elements, StringBuilder numberBuilder) {
        if (numberBuilder.length() != 0)
            elements.add(numberBuilder.toString());
    }
}
