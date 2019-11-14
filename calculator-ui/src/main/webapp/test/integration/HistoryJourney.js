sap.ui.require([
    "sap/ui/test/Opa5",
    "sap/ui/test/opaQunit",
    "calculator/ui/test/integration/page/PageObjects",

], function (Opa5, opaTest ) {
    "use strict";

    QUnit.module("Simple action");

    function iStartMyUIComponent(Given){
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "calculator.ui"
            }
        });
    }

    opaTest("Should see added rows to the table", function (Given, When, Then) {
        iStartMyUIComponent(Given);

        When.onTheAppPage.iClearSessionStorage();
        When.onTheAppPage.iFillInputWithAnExpression();
        When.onTheAppPage.iPressCalculateButton();

        Then.onTheAppPage.iShouldSeeResolvedExpression();
        Then.iTeardownMyApp();
    });

    opaTest("Should see resolved expression in the table", function (Given, When, Then) {
        iStartMyUIComponent(Given);

        When.onTheAppPage.iClearSessionStorage();
        When.onTheAppPage.iFillInputWithAnExpression();
        When.onTheAppPage.iPressCalculateButton();

        Then.onTheAppPage.iShouldSeeTableWithOneEntry();
        Then.iTeardownMyApp();
    });


});