package persistence.dto;

import javax.persistence.*;

@Entity
@Table(name = "expression_result")
@NamedQuery(name = "ExpressionResultDTO_findAllNotEvaluated",
        query = "select e from ExpressionResultDTO e where e.isEvaluated=false")
public class ExpressionResultDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String expression;

    @Column
    private boolean isEvaluated;

    public ExpressionResultDTO(String expression, boolean isEvaluated) {
        this.expression = expression;
        this.isEvaluated = isEvaluated;
    }

    public ExpressionResultDTO() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(boolean evaluated) {
        isEvaluated = evaluated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
