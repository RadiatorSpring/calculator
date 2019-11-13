sap.ui.define([
    "sap/ui/test/Opa5",
    "sap/ui/test/actions/Press",
    "sap/ui/test/actions/EnterText",
    'sap/ui/test/matchers/AggregationLengthEquals'
], function (Opa5, Press, EnterText, AggregationLengthEquals) {

    const viewName = "Expression";
    Opa5.extendConfig({
        viewNamespace: "calculator.ui.view"
    });

    function iFillInputField(page, text) {
        return page.waitFor({
            id: "expression",
            viewName: viewName,
            actions: new EnterText({
                text: text
            }),
            errorMessage: "Did not find input field for expression"
        });
    }

    function iShouldSeeAnError(page, text) {
        return page.waitFor({
            id: "errorText",
            viewName: viewName,
            matchers: new sap.ui.test.matchers.Properties({
                text: text
            }),
            success: function () {
                Opa5.assert.ok(true, "The error text is placed properly");
            },
            errorMessage: "Did not change the error text"
        });
    }

    Opa5.createPageObjects({
        onTheAppPage: {

            actions: {
                iClearSessionStorage: function () {
                    sessionStorage.clear();
                },
                iPressCalculateButton: function () {
                    return this.waitFor({
                        id: "calculateBtn",
                        viewName: viewName,
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
                iShouldSeeResolvedExpression: function () {
                    return this.waitFor({
                        id: "table",
                        viewName: viewName,
                        check: function (oTable) {
                            let items = oTable.getModel("history").getJSON();
                            let oItems  = JSON.parse(items);
                            console.log(oItems);

                            if (oItems[4].expression ==="1-1*(1+1)/2" && oItems[4].result == 0) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        success: function (isEvaluated) {
                            Opa5.assert.ok(isEvaluated, "The entity is set correctly");
                        }
                    });
                },
                iShouldSeeTableWithOneEntry: function () {
                    return this.waitFor({
                        id: "table",
                        viewName: viewName,
                        matchers: new AggregationLengthEquals({
                            name: "items",
                            length: 1
                        }),
                        success: function () {
                            Opa5.assert.ok(true, "The row is set correctly");
                        },
                        errorMessage: "The row is not correct"
                    });
                },
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
                        viewName: viewName,
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