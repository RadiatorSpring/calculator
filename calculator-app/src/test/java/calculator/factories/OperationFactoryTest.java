package calculator.factories;

import calculator.operations.Operand;
import calculator.operations.Operation;
import calculator.operations.OperationFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationFactoryTest {
    private OperationFactory operationFactory;
    @Before
    public void setup(){
        this.operationFactory=new OperationFactory();
    }

    @Test
    public void testBracketsPriority(){
        Assert.assertEquals(0,operationFactory.getOperationPriority("("));
    }
    @Test
    public void testOperand(){
        Assert.assertTrue(operationFactory.getOperation("2") instanceof Operand);
    }
    @Test
    public void testBasicOperation(){
        Assert.assertEquals(1,operationFactory.getOperationPriority("+"));
    }
}
