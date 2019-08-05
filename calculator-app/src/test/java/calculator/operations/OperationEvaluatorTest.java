package calculator.operations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationEvaluatorTest {


    private OperationEvaluator operationEvaluator;

    @Before
    public void setup() {
        this.operationEvaluator = new OperationEvaluator();

    }

    @Test
    public void testBasicOperation() {
        Assert.assertEquals(1, operationEvaluator.getOperationPriority("+"));
    }

    @Test
    public void testBracketsPriority() {
        Assert.assertEquals(0, operationEvaluator.getOperationPriority("("));
    }
}
