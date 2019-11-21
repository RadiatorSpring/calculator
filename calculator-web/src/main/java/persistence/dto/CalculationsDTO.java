package persistence.dto;

import javax.persistence.*;

@Entity
@Table(name = "calculations")

public class CalculationsDTO {
    @Id
    @Column
    private String expression;

    @Column
    private double evaluation;

    @Column
    private String error;

    public CalculationsDTO(String expression) {
        this.expression = expression;
        this.evaluation = 0;
        this.error = "";
    }

    public CalculationsDTO(String expression, double evaluation, String error) {
        this.expression = expression;
        this.evaluation = evaluation;
        this.error = error;
    }

    public CalculationsDTO() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
