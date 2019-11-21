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

    opaTest("Should change error text", function (Given, When, Then) {
        iStartMyUIComponent(Given);

        When.onTheAppPage.iFillInputFieldWithNoArguments();
        When.onTheAppPage.iPressCalculateButton();

        Then.onTheAppPage.iShouldSeeErrorMessageForEmptyParamater();

        Then.iTeardownMyApp();

    });

    opaTest("Should change error text to InvalidArgumentException message", function (Given, When, Then) {
        iStartMyUIComponent(Given);

        When.onTheAppPage.iFillInputFieldWithWrongArguments();
        When.onTheAppPage.iPressCalculateButton();

        Then.onTheAppPage.iShouldSeeErrorMessageForInvalidArgument();

        Then.iTeardownMyApp();
    });

    opaTest("Should change error text to TooManyOperatorsException message", function (Given, When, Then) {
        iStartMyUIComponent(Given);

        When.onTheAppPage.iFillInputFieldWithTooManyOperators();
        When.onTheAppPage.iPressCalculateButton();

        Then.onTheAppPage.iShouldSeeErrorMessageForTooMayOperators();

        Then.iTeardownMyApp();
    });


});