sap.ui.define([
	"sap/ui/test/Opa5",
	"sap/ui/test/actions/Press"
], function (Opa5, Press) {
	"use strict";

	Opa5.extendConfig({
		viewNamespace: "sap.ui.demo.walkthrough.view"
	});

	Opa5.createPageObjects({
		onTheAppPage: {

			actions: {
				iPressChangeTextButton: function () {
					return this.waitFor({
						id: "calculateBtn",
						viewName: "Expression",
						actions: new Press(),
						errorMessage: "Did not find calculate button"
					});
				}
			},

			assertions: {
				iShouldSeeTheChangedText: function () {
					return this.waitFor({
						id: "result",
						viewName: "Expression",
						matchers: new sap.ui.test.matchers.Properties({
							value: "Parameter cannot be empty"
						}),
						success: function () {
							// we set the view busy, so we need to query the parent of the app
							Opa5.assert.ok(true, "The error text is placed properly");
						},
						errorMessage: "Did not find the change control"
					});
				}
			}
		}
	});
});