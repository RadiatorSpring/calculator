package calculator;

import calculator.parsers.ExpressionParser;
import calculator.parsers.ReversePolishNotationParser;
import calculator.validators.Checker;

import java.util.*;

public class Parser {
    private Checker checker;
    private ExpressionParser expressionParser;
    private ReversePolishNotationParser parserRPN;

    Parser(Checker checker, ExpressionParser expressionParser, ReversePolishNotationParser parserRPN) {
        this.checker = checker;
        this.expressionParser = expressionParser;
        this.parserRPN = parserRPN;
    }
    public Parser(){

    }

    Queue<String> convertExpressionToRPN(String expression) {
        if (checker.validateExpression(expression) || expression.isEmpty()) {
            throw new IllegalArgumentException("There cannot be spaces between numbers, there cannot be letters");
        }
        List<String> elementsOfExpression = expressionParser.expressionToNumbersAndOperations(expression);
        return parserRPN.buildRPNfromElementsOfExpression(elementsOfExpression);
    }



}
