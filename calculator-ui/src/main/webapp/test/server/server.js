sap.ui.define([
        "sap/ui/thirdparty/sinon"
    ],
    function (sinon) {


        return {

            init: function () {
                let postURL = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/calculate/;
                let urlForMock = /.*\/calculator-web-1.0-SNAPSHOT\/.*/;
                let regexHistoryId = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/history\/id/;
                let regexHistory = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/history\/uuid/;

                this.oServer = sinon.fakeServer.create();
                this.oServer.autoRespond = true;
                this.oServer.autoRespondAfter = 100;

                sinon.fakeServer.xhr.useFilters = true;
                this.oServer.xhr.addFilter(function (method, url) {
                    return !url.match(urlForMock);
                });

                fakeRequestForHistory(this.oServer, regexHistory)
                fakeRequestsForHistoryId(this.oServer, regexHistoryId)
                fakeRequestsForEmtpyParamater(this.oServer, postURL);
                fakeRequestsForIllegalArgument(this.oServer, postURL);
                fakeRequestsForTooManyOperators(this.oServer, postURL);
                fakeRequestsForCorrectInput(this.oServer, postURL);
            }

        };

        function fakeResponse(server, url, message, code) {
            server.respondWith("GET", url, [code, {
                "Content-Type": "application/json"
            }, message]);
        }

        function fakePostResponse(server, url, id, expression, code) {
            server.respondWith("POST", url, function (xhr) {
                let oResponse = JSON.parse(xhr.requestBody);
                let oExpected = {expression: expression, historyId: "uuid"};
                if (oResponse.expression === oExpected.expression) {
                    let oId = {id: id};
                    xhr.respond(code, {"Content-Type": "application/json"}, JSON.stringify(oId));
                }
            });
        }

        function fakeRequestForHistory(server, regexHistory) {
                let historyList = JSON.stringify([
                {
                    expression: "1-1*(1+1)/2",
                    evaluation:"0",
                    error:null
                }
            ])

            fakeResponse(server, regexHistory, historyList, 200);
        }

        function fakeRequestsForHistoryId(server, regexHistoryId) {
            let uuid = "uuid";
            let historyId = JSON.stringify({historyId: uuid});

            fakeResponse(server, regexHistoryId, historyId, 200);
        }

        function fakeRequestsForEmtpyParamater(server, postURL) {
            var messageForEmptyParameterException = '{"message":"The expression parameter cannot be empty","code":400}';
            var urlForEmptyParameterException = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/1/;

            fakeResponse(server, urlForEmptyParameterException, messageForEmptyParameterException, 400);
            fakePostResponse(server, postURL, 1, "", 202);
        }

        function fakeRequestsForIllegalArgument(server, postURL) {
            var urlForIllegalArgumentException = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/2/;
            var messageForIllegalArgumentException =
                '{"message":"There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator","code":400}';
            fakeResponse(server, urlForIllegalArgumentException, messageForIllegalArgumentException, 400);
            fakePostResponse(server, postURL, 2, "1-1a", 202);
        }

        function fakeRequestsForTooManyOperators(server, postURL) {
            var urlForTooManyOperators = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/3/;
            var messageForTooManyOperators =
                '{"message": "The number of operators cannot be greater than the number of operands, using negative numbers requires brackets","code": 400}';
            fakeResponse(server, urlForTooManyOperators, messageForTooManyOperators, 400);
            fakePostResponse(server, postURL, 3, "1--1", 202);
        }

        function fakeRequestsForCorrectInput(server, postURL) {
            var urlForCorrectInput = /.*\/calculator-web-1.0-SNAPSHOT\/api\/v1\/expressions\/4/;
            let oResult = {result: 0};
            var messageForCorrectInput = JSON.stringify(oResult);
            fakeResponse(server, urlForCorrectInput, messageForCorrectInput, 200);
            fakePostResponse(server, postURL, 4, "1-1*(1+1)/2", 202);
        }


    });