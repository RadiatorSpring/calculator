sap.ui.define([
		"sap/ui/thirdparty/sinon"
	],
	function (sinon) {

		function fakeResponse(server, url, message, code) {
			server.respondWith("GET", url, [code, {
				"Content-Type": "application/json"
			}, message]);
		}

		return {

			init: function () {
				var urlForEmptyParameterException = /\/calculator\/api\/calculate\?expression=/;
				var urlForIllegalArgumentException = /\/calculator\/api\/calculate\?expression=.*1-1a.*/;
				var urlForTooManyOperators = /\/calculator\/api\/calculate\?expression=.*1--1.*/;
				var urlForCorrectInput = /\/calculator\/api\/calculate\?expression=.*1-1\*\(1%2B1\)\/2.*/;

				var messageForEmptyParameterException = '{"message":"The expression parameter cannot be empty","code":400}';
				var messageForIllegalArgumentException =
					'{"message":"There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator","code":400}';
				var messageForTooManyOperators =
					'{"message": "The number of operators cannot be greater than the number of operands, using negative numbers requires brackets","code": 400}';
				var messageForCorrectInput = '{"result": 0.0}';
				this.oServer = sinon.fakeServer.create();
				this.oServer.autoRespond = true;
				this.oServer.autoRespondAfter = 500;

				sinon.fakeServer.xhr.useFilters = true;
				this.oServer.xhr.addFilter(function (method, url) {
					return !url.match(urlForEmptyParameterException);
				});

				fakeResponse(this.oServer, urlForEmptyParameterException, messageForEmptyParameterException, 400);
				fakeResponse(this.oServer, urlForIllegalArgumentException, messageForIllegalArgumentException, 400);
				fakeResponse(this.oServer, urlForTooManyOperators, messageForTooManyOperators, 400);
				fakeResponse(this.oServer, urlForCorrectInput, messageForCorrectInput, 200);
			}
		};

	});