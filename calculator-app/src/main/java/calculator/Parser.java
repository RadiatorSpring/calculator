package calculator;

import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

/**
 * The Parser exists solely to convert the expression that is given
 * to Calculator to something that is computable with Reverse polish notation
 */
public class Parser {
    private Checker checker;
    private ExpressionParser expressionParser;
    private ReversePolishNotationParser parserRPN;
    private NegativeNumbersBuilder negativeNumbersBuilder;

    /**
     * creates the Parser
     *
     * @param checker                checks for validity of expression
     * @param expressionParser       makes the initial separation of elements
     * @param parserRPN              creates Queue in RPN
     * @param negativeNumbersBuilder decides which minuses are part of expression and which are part of a number
     */
    public Parser(Checker checker, ExpressionParser expressionParser,
                  ReversePolishNotationParser parserRPN, NegativeNumbersBuilder negativeNumbersBuilder) {
        this.checker = checker;
        this.expressionParser = expressionParser;
        this.parserRPN = parserRPN;
        this.negativeNumbersBuilder = negativeNumbersBuilder;
    }

    /**
     * @param expression the mathematical expression
     * @return the expression that is ready to be computed
     * @throws IllegalArgumentException if the validation fails
     */
    Queue<String> convertExpressionToRPN(String expression) throws IllegalArgumentException {
        if (checker.validateExpression(expression) || expression.isEmpty()) {
            throw new IllegalArgumentException("There cannot be spaces between digits, there cannot be letters");
        }
        List<String> elementsOfExpression = expressionParser.expressionToNumbersAndOperations(expression);
        List<String> elementsOfExpressionWithNegativeNumbers = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(elementsOfExpression);
        return parserRPN.buildRPNfromElementsOfExpression(elementsOfExpressionWithNegativeNumbers);
    }


}
