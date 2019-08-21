sap.ui.require([
	"sap/ui/test/Opa5",
	"sap/ui/test/opaQunit",
	"sap/ui/test/actions/Press",
	"sap/ui/test/matchers/Properties",
	"sap/ui/demo/walkthrough/test/integration/PageObjects"
], function (Opa5, opaTest, Press, Properties) {
	"use strict";

	QUnit.module("Simple action");

	opaTest("Should change error text", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough"
			}
		});

		When.onTheAppPage.iPressChangeTextButton();

		Then.onTheAppPage.iShouldSeeErrorMessageForEmptyParamater();

		Then.iTeardownMyAppFrame();
	});

	opaTest("Should change error text to InvalidArgumentException message", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough"
			}
		});

		When.onTheAppPage.iFillInputFieldWithWrongArguments();

		Then.iShouldSeeErrorMessageForInvalidArgument();
	})
});