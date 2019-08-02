package calculator.operations;

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
    public void testOperand(){
        Assert.assertTrue(operationFactory.getOperation("2") instanceof Operand);
    }

}
