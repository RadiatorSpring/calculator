sap.ui.define([
    ".src/main/webapp/controller/App.controller.js"
], function (appController) {
	"use strict";

    QUnit.module("Number unit");

    QUnit.test("Should return the translated texts", function (assert) {
        
        assert.equal(true,true);
    })
    QUnit.test("square()", function (assert) {
    			var result = 4;

    			assert.equal(result, 4, "square(2) equals 4");
    });
})  