
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"calculator/ui/test/unit/controller/ExpressionTest.controller"
	], function () {
		QUnit.start();
	});
});
