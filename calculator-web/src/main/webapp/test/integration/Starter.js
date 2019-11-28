/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
    "use strict";

    sap.ui.require([
    	"calculator/ui/test/integration/TableJourney",
        "calculator/ui/test/integration/Journey"

    ], function () {
        QUnit.start();
    });
});