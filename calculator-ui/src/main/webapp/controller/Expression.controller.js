sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/ui/thirdparty/jquery"

], function (Controller, JSONModel) {

    return Controller.extend("calculator.ui.controller.Expression", {
        mapName: "map",
        mapCalculations: sessionStorage.getItem(this.mapName),

        onInit: function () {
            let map = this.getMapCalculations();
            if (Object.entries(map).length !== 0) {
                this.updateModel(this.getMapCalculations());

            }
        },
        onPost: function () {
            var expression = this.getView().byId("expression").getValue();

            var оBody = {
                expression: expression
            }
            var sBody = JSON.stringify(оBody);
            this.doPostXHR(sBody, function (xhr) {
                var oResponse = JSON.parse(xhr.response);
                var responseId = oResponse.id;
                this.addAsPendingToHistory(responseId, expression);

                var getCall = setInterval(() => {
                    this.onGet(responseId, getCall)
                }, 1600);

            }.bind(this))

        },
        doPostXHR: function (body, done) {
            let xhr = new XMLHttpRequest();
            xhr.open("POST", "../calculator-web-1.0-SNAPSHOT/api/v1/calculate/");
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onload = function () {
                done(xhr);
            };
            xhr.onerror = function () {
                done(xhr)
            }
            xhr.send(body)
        },
        addAsPendingToHistory: function (id, expression) {
            let sIsNotEvaluated = "Is not evaluated";
            var historyTableItem = {
                expression: expression,
                message: sIsNotEvaluated
            };
            this.updateSessionStorage(id, historyTableItem);
            this.updateModel(this.getMapCalculations);
        },
        updateSessionStorage: function (id, historyTableItem) {
            let updatedSessionStorage = this.getMapCalculations();
            updatedSessionStorage[id] = historyTableItem;
            var sUpdatedSessionStorage = JSON.stringify(updatedSessionStorage);
            sessionStorage.setItem(this.mapName, sUpdatedSessionStorage);
        },
        onGet: async function (id, intervalCallback) {
            let statusCodeOk = 200;

            this.doGetXHR(id, function (xhr) {
                var evaluation = xhr.response;
                var oModel = new JSONModel();
                oModel.setData(JSON.parse(evaluation));
                if (xhr.status == statusCodeOk) {
                    this.getView().setModel(oModel);
                    this.updateHistory(evaluation, id)
                    clearInterval(intervalCallback);
                } else if (xhr.status != 202) {
                    this.setError(xhr)
                    this.updateHistory(evaluation, id);
                    clearInterval(intervalCallback);
                }
            }.bind(this))
        },
        doGetXHR: function (id, done) {
            let xhr = new XMLHttpRequest();
            xhr.open("GET", "../calculator-web-1.0-SNAPSHOT/api/v1/expressions/" + id);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onload = function () {
                done(xhr);
            };
            xhr.onerror = function () {
                done(xhr)
            }
            xhr.send()
        },

        updateHistory: function (evaluation, id) {
            let map = this.getMapCalculations();
            let expression = map[id].expression;
            let parsedEvaluation = JSON.parse(evaluation);

            if (parsedEvaluation.result !== undefined) {
                var historyTableItem = {
                    expression: expression,
                    result: parsedEvaluation.result
                };
            } else {
                var historyTableItem = {
                    expression: expression,
                    message: parsedEvaluation.message
                };
            }
            this.updateSessionStorage(id, historyTableItem);
            this.updateModel(this.getMapCalculations());
        },
        updateModel: function (mapValues) {
            let modelBindingName = "history";
            let oModel = new JSONModel(mapValues);
            this.getView().setModel(oModel, modelBindingName);
        },
        getMapCalculations: function () {
            let map = sessionStorage.getItem(this.mapName)
            if (map !== null) {
                return JSON.parse(map);
            } else {
                sessionStorage.setItem(this.mapName, "{}");
                return JSON.parse(sessionStorage.getItem(this.mapName));
            }

        },
        setError: function (xhr) {
            let sErrorTextID = "errorText";
            var oResponse = JSON.parse(xhr.response);
            let sError = oResponse.message;
            this.getView().byId(sErrorTextID).setText(sError)
        }
    })
});
