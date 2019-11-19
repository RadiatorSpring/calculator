package models.wrappers;

public class CalculatorExpression {
    private String expression;
    private String historyId;

    public CalculatorExpression(String expression, String historyId) {
        this.expression = expression;
        this.historyId = historyId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public CalculatorExpression() {
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public CalculatorExpression(String expression) {
        this.expression = expression;
    }
}
