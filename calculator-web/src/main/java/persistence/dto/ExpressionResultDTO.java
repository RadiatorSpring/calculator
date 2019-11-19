package persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "expression_result")
@NamedQuery(name = "ExpressionResultDTO_findAll", query = "select e from ExpressionResultDTO e")
@NamedQuery(name = "ExpressionResultDTO_findAllNotEvaluated",
        query = "select e from ExpressionResultDTO e where evaluation=0 and error=\'Is not evaluated\'")
@NamedQuery(name = "ExpressionDTO_findHistoryWithSessionId",
        query = "select e from ExpressionResultDTO e where e.historyId = :historyId")
public class ExpressionResultDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonIgnore
    private long id;

    @Column
    private String expression;

    @Column
    private double evaluation;

    @Column
    private String error;


    @Column
    @JsonIgnore
    private String historyId;


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

    public ExpressionResultDTO(String expression, String error, String historyId) {
        this.expression = expression;
        this.error = error;
        this.historyId = historyId;
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

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

}
