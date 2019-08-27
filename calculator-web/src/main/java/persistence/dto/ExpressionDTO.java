package persistence;

import javax.persistence.*;

@Entity
public class ExpressionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private String expression;

    @Column
    private double result;

    public ExpressionDTO(long id, String expression, double result) {
        this.id = id;
        this.expression = expression;
        this.result = result;
    }

    public ExpressionDTO() {
        //for hibernate
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
