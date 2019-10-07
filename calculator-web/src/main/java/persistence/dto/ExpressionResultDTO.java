package persistence.dto;

import javax.persistence.*;

@Entity
@Table(name = "expression_result")
@NamedQuery(name = "ExpressionResultDTO_findAll", query = "select e from ExpressionResultDTO e")
public class ExpressionResultDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String expression;

    @Column
    private double evaluation;

    @Column
    private String error;



    public ExpressionResultDTO(String expression, double evaluation) {
        this.expression = expression;
        this.evaluation = evaluation;
    }

    public String getError() {
        return error;
    }

    public ExpressionResultDTO() {
        //for hibernate
    }

    public void setError(String error) {
        this.error = error;
    }

    public ExpressionResultDTO(String expression, String error) {
        this.expression = expression;
        this.error = error;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setevaluation(double evaluation) {
        this.evaluation = evaluation;
    }
}
