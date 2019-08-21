sap.ui.define([
		"sap/ui/thirdparty/sinon"
	],
	function (sinon) {
		"use strict";

		return {
			/**
			 * Initializes a fake server for testing purposes.
			 * @public
			 */
			init: function () {
				var expression = /\/calculator\/api\/calculate\?expression=/;
				// create a Sinon.JS fake server that responds automatically after 1s
				this.oServer = sinon.fakeServer.create();
				this.oServer.autoRespond = true;
				this.oServer.autoRespondAfter = 500;

				// that responds only to a specific request
				sinon.fakeServer.xhr.useFilters = true;
				this.oServer.xhr.addFilter(function (method, url) {
					// whenever this returns true the request will not faked
					return !url.match(expression);
				});

				// and sends back a title string for the page
				this.oServer.respondWith("GET", expression, [400, {
					"Content-Type": "application/json"
				}, '{"message":"The expression parameter cannot be empty","code":400}']);
			}
		};

	}
);