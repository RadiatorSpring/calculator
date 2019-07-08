package calculator;

import calculator.parsers.ExpressionParser;
import calculator.parsers.NegativeNumbersBuilder;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;

import java.util.List;
import java.util.Queue;

class Parser {
    private Checker checker;
    private ExpressionParser expressionParser;
    private ReversePolishNotationParser parserRPN;
    private NegativeNumbersBuilder negativeNumbersBuilder;

    private Parser(Checker checker, ExpressionParser expressionParser,
                   ReversePolishNotationParser parserRPN, NegativeNumbersBuilder negativeNumbersBuilder) {
        this.checker = checker;
        this.expressionParser = expressionParser;
        this.parserRPN = parserRPN;
        this.negativeNumbersBuilder = negativeNumbersBuilder;
    }

    Parser() {
        this(new Checker(), new ExpressionParser(), new ReversePolishNotationParser(), new NegativeNumbersBuilder());
    }

    Queue<String> convertExpressionToRPN(String expression) throws IllegalArgumentException {
        if (checker.validateExpression(expression) || expression.isEmpty()) {
            throw new IllegalArgumentException("There cannot be spaces between digits, there cannot be letters");
        }
        List<String> elementsOfExpression = expressionParser.expressionToNumbersAndOperations(expression);
        List<String> elementsOfExpressionWithNegativeNumbers = negativeNumbersBuilder.buildListWithOperatorsAndNegativeNumbers(elementsOfExpression);
        return parserRPN.buildRPNfromElementsOfExpression(elementsOfExpressionWithNegativeNumbers);
    }


}
