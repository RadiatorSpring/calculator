
sap.ui.define([
    "sap/ui/test/Opa5",
    "sap/ui/test/opaQunit",
    "sap/ui/test/actions/Press",
    "sap/ui/test/matchers/Properties"
], function (Opa5, opaTest, Press, Properties) {
    "use strict";

    QUnit.module("Simple server action");

    Opa5.extendConfig({
        autoWait: true,
        timeout: 1  
    });
    
    opaTest("Should get a response", function (Given, When, Then) {

        
        Given.iStartMyAppInAFrame("sap/ui/demo/walkthrough/index.html",5);
        
        When.waitFor({
            
            id: "changeBtn",
            viewName:"sap.ui.demo.walkthrough.view.Expression", 
            actions: function(Btn){
                new Press();
            },
            errorMessage: "Did not find the load button"
        });
        
        Then.waitFor({
            id: "changeBtn",
            viewName:"sap.ui.demo.walkthrough.view.Expression",
            matchers: new Properties({
                value: "changed"
            }),
            success: function () {
                opaTest.assert.ok(true, "Did update the app title correctly");
            },
            errorMessage:"couldnt find button"
        }); 
        
        // Then.iTeardownMyAppFrame();
    });

});
