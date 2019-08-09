
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"sap/ui/demo/walkthrough/test/unit/controller/ExpressionTest.controller"
	], function () {
		QUnit.start();
	});
});
