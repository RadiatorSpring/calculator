    sap.ui.define([
	"sap/ui/thirdparty/sinon"
],
	function (sinon) {

		function fakeResponse(server, url, message, code) {
			server.respondWith("GET", url, [code, {
				"Content-Type": "application/json"
			}, message]);
		}

		function fakePostResponse(server, url, id, expression, code) {
			server.respondWith("POST", url, function (xhr) {
				console.log(xhr.requestBody);

				var jsonExpression = "{\n    \"expression\": \"" + expression + "\"\n}";
				console.log(xhr.requestBody)
				if (xhr.requestBody.localeCompare(jsonExpression) == 0) {
					console.log("the id here is " + id)
					xhr.respond(code, { "Content-Type": "application/json" }, '{ "id": ' + id + '}');
				}
			});
		}

		return {

			init: function () {
				var postURL = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/calculate/;
				var urlForMock = /.*\/calculator-web-1.0-SNAPSHOT\/.*/;
				var urlForEmptyParameterException = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/1/;
				var urlForIllegalArgumentException = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/2/;
				var urlForTooManyOperators = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/3/;
				var urlForCorrectInput = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/4/;

				var messageForEmptyParameterException = '{"message":"The expression parameter cannot be empty","code":400}';
				var messageForIllegalArgumentException =
					'{"message":"There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator","code":400}';
				var messageForTooManyOperators =
					'{"message": "The number of operators cannot be greater than the number of operands, using negative numbers requires brackets","code": 400}';
				var messageForCorrectInput = '{"result": 0.0}';

				this.oServer = sinon.fakeServer.create();
				this.oServer.autoRespond = true;
				this.oServer.autoRespondAfter = 100;

				sinon.fakeServer.xhr.useFilters = true;
				this.oServer.xhr.addFilter(function (method, url) {
					return !url.match(urlForMock);
				});

				fakeResponse(this.oServer, urlForEmptyParameterException, messageForEmptyParameterException, 400);
				fakeResponse(this.oServer, urlForIllegalArgumentException, messageForIllegalArgumentException, 400);
				fakeResponse(this.oServer, urlForTooManyOperators, messageForTooManyOperators, 400);
				fakeResponse(this.oServer, urlForCorrectInput, messageForCorrectInput, 200);

				fakePostResponse(this.oServer, postURL, 1, "", 200);
				fakePostResponse(this.oServer, postURL, 2, "1-1a", 200);
				fakePostResponse(this.oServer, postURL, 3, "1--1", 200);
				fakePostResponse(this.oServer, postURL, 4, "1-1*(1+1)/2", 200);

			}
		};

	});