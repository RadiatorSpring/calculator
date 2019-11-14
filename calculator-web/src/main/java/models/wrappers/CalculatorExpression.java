package models.wrappers;

public class CalculatorExpression {
    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public CalculatorExpression() {
    }

    public CalculatorExpression(String expression) {
        this.expression = expression;
    }
}
