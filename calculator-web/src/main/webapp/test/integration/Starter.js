/* global QUnit */
QUnit.config.autostart = false;

<<<<<<< HEAD
sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"sap/ui/demo/walkthrough/test/integration/Journey",
		"sap/ui/demo/walkthrough/test/integration/server/server"
	], function (server) {
		server.init();
		QUnit.start();
	});
});
=======
sap.ui.getCore().attachInit(function() {
	"use strict";

	sap.ui.require([
		"sap/ui/demo/walkthrough/test/integration/Journey",
		"sap/ui/demo/walkthrough/test/integration/server/server"
	], function(server) {
		server.init();
		QUnit.start();
	});
});
>>>>>>> refs/heads/master
