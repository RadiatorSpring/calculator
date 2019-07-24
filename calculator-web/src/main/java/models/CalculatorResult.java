package models;

public class CalculatorResult {
    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    private double result ;

    public CalculatorResult(double result) {
        this.result = result;
    }

}
