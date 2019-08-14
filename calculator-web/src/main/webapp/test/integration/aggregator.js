sap.ui.define([
    "sap/ui/test/Opa5",
    "sap/ui/demo/walkthrough/test/integration/opaTests"
], function(Opa5){
    Opa5("Should press a Button", function (Given, When, Then) {
        "use strict";
        console.log("asd")
        // Arrangements
        Given.iStartMyApp();
    
        //Actions
        When.iPressOnTheButton();
    
        // Assertions
        Then.theButtonShouldHaveADifferentText();
    
    });
})

