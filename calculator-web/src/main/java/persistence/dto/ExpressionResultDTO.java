package persistence.dto;

import javax.persistence.*;

@Entity
@Table(name = "Expression")
@NamedQuery(name = "ExpressionResultDTO_findAll", query = "select e from ExpressionResultDTO e")
public class ExpressionResultDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private String expression;

    @Column
    private double result;

    @Column
    private String error;



    public ExpressionResultDTO(String expression, double result) {
        this.expression = expression;
        this.result = result;
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

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
