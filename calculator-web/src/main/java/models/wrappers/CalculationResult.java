package models.wrappers;

public class CalculationResult {
    private double result ;

    public CalculationResult(double result) {
        this.result = result;
    }

    public CalculationResult() {
    }

    public double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }

}
