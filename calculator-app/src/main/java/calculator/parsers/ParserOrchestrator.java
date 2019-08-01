package calculator.parsers;

import calculator.validators.Checker;

import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ParserOrchestrator exists solely to convert the expression that is given
 * to Calculator to something that is computable with Reverse polish notation
 */
public class ParserOrchestrator {
    private Checker checker;
    private ExpressionParser expressionParser;
    private ReversePolishNotationParser parserRPN;
    private NegativeNumbersBuilder negativeNumbersBuilder;

    /**
     * creates the ParserOrchestrator
     *
     * @param checker                checks for validity of expression
     * @param expressionParser       makes the initial separation of elements
     * @param parserRPN              creates Queue in RPN
     * @param negativeNumbersBuilder decides which minuses are part of expression and which are part of a number
     */
    public ParserOrchestrator(Checker checker, ExpressionParser expressionParser,
                              ReversePolishNotationParser parserRPN, NegativeNumbersBuilder negativeNumbersBuilder) {
        this.checker = checker;
        this.expressionParser = expressionParser;
        this.parserRPN = parserRPN;
        this.negativeNumbersBuilder = negativeNumbersBuilder;
    }

    public ParserOrchestrator() {
        this.checker = new Checker();
        this.expressionParser = new ExpressionParser();
        this.parserRPN = new ReversePolishNotationParser();
        this.negativeNumbersBuilder = new NegativeNumbersBuilder();
    }

    /**
     * @param expression the mathematical expression
     * @return the expression that is ready to be computed
     * @throws IllegalArgumentException if the validation fails
     */
    public Queue<String> convertExpressionToRPN(String expression) throws IllegalArgumentException {
        if (validateExpression(expression) || expression.isEmpty()) {
            throw new IllegalArgumentException("There cannot be spaces between digits, there cannot be letters");
        }
        List<String> elementsOfExpression = expressionParser.expressionToNumbersAndOperations(expression);
        List<String> elementsOfExpressionWithNegativeNumbers = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(elementsOfExpression);
        return parserRPN.buildRPNfromElementsOfExpression(elementsOfExpressionWithNegativeNumbers);
    }

    boolean validateExpression(String text) {
        Pattern noLettersPattern = Pattern.compile(".*[^\\d().+\\-*/^ ].*");
        Matcher noLettersMatcher = noLettersPattern.matcher(text);

        Pattern spacesPattern = Pattern.compile(".*\\d+ +\\d+.*");
        Matcher spaceMatcher = spacesPattern.matcher(text);

        Pattern invalidBracketsPattern = Pattern.compile("(.*\\d+\\(.*)|(.*\\) \\d+)|(\\( *\\).*)");
        Matcher invalidBracketsMatcher = invalidBracketsPattern.matcher(text);

        return noLettersMatcher.matches() || spaceMatcher.matches() || invalidBracketsMatcher.matches();

    }


}
