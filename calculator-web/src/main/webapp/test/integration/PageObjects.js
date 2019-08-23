sap.ui.define([
	"sap/ui/test/Opa5",
	"sap/ui/test/actions/Press",
	"sap/ui/test/actions/EnterText"
], function (Opa5, Press, EnterText) {

	Opa5.extendConfig({
		viewNamespace: "calculator.ui.view"
	});

	function iFillInputField(page, text) {
		return page.waitFor({
			id: "expression",
			viewName: "Expression",
			actions: new EnterText({
				text: text
			}),
			errorMessage: "Did not find input field for expression"
		});
	}

	function iShouldSeeAnError(page, text) {
		return page.waitFor({
			id: "errorText",
			viewName: "Expression",
			matchers: new sap.ui.test.matchers.Properties({
				text: text
			}),
			success: function () {
				// we set the view busy, so we need to query the parent of the app
				Opa5.assert.ok(true, "The error text is placed properly");
			},
			errorMessage: "Did not change the error text"
		});
	}

	Opa5.createPageObjects({
		onTheAppPage: {
			actions: {
				iPressCalculateButton: function () {
					return this.waitFor({
						id: "calculateBtn",
						viewName: "Expression",
						actions: new Press(),
						errorMessage: "Did not find calculate button"
					});
				},
				iFillInputFieldWithWrongArguments: function () {
					return iFillInputField(this, "1-1a");
				},
				iFillInputFieldWithNoArguments: function () {
					return iFillInputField(this, "");
				},
				iFillInputFieldWithTooManyOperators: function () {
					return iFillInputField(this, "1--1");
				},
				iFillInputWithAnExpression: function () {
					return iFillInputField(this, "1-1*(1+1)/2");
				}

			},

			assertions: {

				iShouldSeeErrorMessageForEmptyParamater: function () {
					return iShouldSeeAnError(this, "The expression parameter cannot be empty");
				},
				iShouldSeeErrorMessageForTooMayOperators: function () {
					return iShouldSeeAnError(this,
						"The number of operators cannot be greater than the number of operands, using negative numbers requires brackets");
				},
				iShouldSeeErrorMessageForInvalidArgument: function () {
					return iShouldSeeAnError(this,
						"There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator");
				},
				iShouldSeeTheResult: function () {
					return this.waitFor({
						id: "result",
						viewName: "Expression",
						matchers: new sap.ui.test.matchers.Properties({
							value: "0"
						}),
						success: function () {
							Opa5.assert.ok(true, "The result is set correctly");
						},
						errorMessage: "The result is not correct"
					});
				}
			}
		}
	});
});