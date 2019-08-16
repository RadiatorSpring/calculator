sap.ui.require([
	"sap/ui/test/Opa5",
	"sap/ui/test/opaQunit",
	"sap/ui/test/actions/Press",
	"sap/ui/test/matchers/Properties",
	"sap/ui/demo/walkthrough/test/integration/PageObjects"
], function (Opa5, opaTest, Press, Properties) {
	"use strict";

	QUnit.module("Simple action");

	opaTest("Should change button text", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough",
				async: true
			}
		});

		When.onTheAppPage.iPressChangeTextButton();

		Then.onTheAppPage.iShouldSeeTheChangedText();
	});

});