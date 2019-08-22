sap.ui.require([
	"sap/ui/test/Opa5",
	"sap/ui/test/opaQunit",
	"sap/ui/test/actions/Press",
	"sap/ui/test/matchers/Properties",
	"sap/ui/demo/walkthrough/test/integration/PageObjects",

], function (Opa5, opaTest, Press, Properties) {
	"use strict";

	QUnit.module("Simple action");

	opaTest("Should change error text", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough"
			}
		});

		When.onTheAppPage.iFillInputFieldWithNoArguments();
		When.onTheAppPage.iPressCalculateButton();

		Then.onTheAppPage.iShouldSeeErrorMessageForEmptyParamater();

		Then.iTeardownMyApp();

	});

	opaTest("Should change error text to InvalidArgumentException message", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough"
			}
		});

		When.onTheAppPage.iFillInputFieldWithWrongArguments();
		When.onTheAppPage.iPressCalculateButton();

		Then.onTheAppPage.iShouldSeeErrorMessageForInvalidArgument();

		Then.iTeardownMyApp();
	});

	opaTest("Should change error text to TooManyOperatorsException message", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough"
			}
		});

		When.onTheAppPage.iFillInputFieldWithTooManyOperators();
		When.onTheAppPage.iPressCalculateButton();

		Then.onTheAppPage.iShouldSeeErrorMessageForTooMayOperators();

		Then.iTeardownMyApp();
	});

	opaTest("Should change the result field to some number message", function (Given, When, Then) {
		Given.iStartMyUIComponent({
			componentConfig: {
				name: "sap.ui.demo.walkthrough"
			}
		});

		When.onTheAppPage.iFillInputWithAnExpression();
		When.onTheAppPage.iPressCalculateButton();

		Then.onTheAppPage.iShouldSeeTheResult();

		Then.iTeardownMyApp();
	});
});