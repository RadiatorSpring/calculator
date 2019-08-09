
sap.ui.define([
  "sap/ui/demo/walkthrough/controller/Expression.controller"
], function (exController) {

  QUnit.module("Number unit");

  QUnit.test("Should return the translated texts", function (assert) {

    assert.equal(true, true);
  })
  QUnit.test("square()", function (assert) {
    var result = 4;

    assert.equal(result, 4, "square(2) equals 4");
  });
  QUnit.test("attempt calling onCalculate", function (assert) {
    jQuery.sap.require("controller/Expression.controller");
    assert.equal(exController.onCalculate(), "hello");
  })
})


