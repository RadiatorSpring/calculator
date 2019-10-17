
sap.ui.define([
  "calculator/ui/models/Formatter"

], function (ExpressionModel) {

  QUnit.module("Number unit");

  QUnit.test("test formatting 1+1 to 1%2B1", function (assert) {
    assert.equal(ExpressionModel.getExpression("1+1"), "1%2B1");  
  });

});

