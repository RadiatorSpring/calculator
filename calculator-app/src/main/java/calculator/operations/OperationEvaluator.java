package calculator.operations;

public class OperationEvaluator {

    private OperationFactory operationFactory;

    public OperationEvaluator(OperationFactory operationFactory) {
        this.operationFactory = operationFactory;
    }

    public OperationEvaluator() {
        this.operationFactory = new OperationFactory();
    }

    public int getOperationPriority(String inputOperation) {
        BasicExpressionElement operation = operationFactory.getBasicOperation(inputOperation);
        return ((PriorityValue) operation).getValue();
    }
}
