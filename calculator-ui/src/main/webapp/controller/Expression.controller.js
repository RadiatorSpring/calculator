sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/ui/thirdparty/jquery"

], function (Controller, JSONModel) {

    return Controller.extend("calculator.ui.controller.Expression", {
        mapName: "map",

        onInit: function () {
            let map = this.getMapCalculations();
            if (this.isEmpty(map)) {
                this.updateHistoryModel();
            }
        },
        isEmpty:function(map){
           return  Object.entries(map).length !== 0;
        },

        onPost: function () {
            var expression = this.getView().byId("expression").getValue();
            var оBody = {
                expression: expression
            }
            var sBody = JSON.stringify(оBody);
            this.doPostXHR(sBody, function (xhr) {
                var oResponse = JSON.parse(xhr.responseText);
                var responseId = oResponse.id;
                this.addAsPendingToHistory(responseId, expression);

                var getCall = setInterval(() => {
                    this.onGet(responseId, getCall)
                }, 1600);

            }.bind(this))

        },
        doPostXHR: function (body, done) {
            let xhr = new XMLHttpRequest();
            let manifest =  this.getOwnerComponent().getMetadata().getManifestEntry("sap.app");
            let url = manifest.dataSources.calculator.restApiPost;
            xhr.open("POST", url);
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
            this.updateHistoryModel();
        },
        updateSessionStorage: function (id, historyTableItem) {
            let updatedSessionStorage = this.getMapCalculations();
            updatedSessionStorage[id] = historyTableItem;
            var sUpdatedSessionStorage = JSON.stringify(updatedSessionStorage);
            sessionStorage.setItem(this.mapName, sUpdatedSessionStorage);
        },
        onGet: async function (id, intervalCallback) {
            this.doGetXHR(id, function (xhr) {
                var evaluation = xhr.responseText;
                var oModel = new JSONModel();
                oModel.setData(JSON.parse(evaluation));

                if(this.isNotStatusAccepted(xhr)){
                    if(!this.isStatusOK(xhr)){
                        this.setErrorModel(xhr);
                    }
                    this.updateHistory(evaluation, id)
                    clearInterval(intervalCallback);
                }


            }.bind(this))
        },
        isStatusOK: function (xhr) {
            return xhr.status === 200;
        },
        isNotStatusAccepted: function (xhr) {
            return xhr.status !== 202;
        },
        doGetXHR: function (id, done) {
            let xhr = new XMLHttpRequest();
            let manifest =  this.getOwnerComponent().getMetadata().getManifestEntry("sap.app");
            let url = manifest.dataSources.calculator.restApiGet;

            xhr.open("GET", url + id);
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
            let historyTableItem = this.createHistoryTableItem(evaluation, id);
            this.updateSessionStorage(id, historyTableItem);
            this.updateHistoryModel();
        },
        createHistoryTableItem: function (evaluation, id) {
            let map = this.getMapCalculations();
            let expression = map[id].expression;
            let parsedEvaluation = JSON.parse(evaluation);

            if (this.isCorrectResult(parsedEvaluation)) {
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
            return historyTableItem;
        },
        isCorrectResult:function(parsedEvaluation){
            return parsedEvaluation.result !== undefined;
        },

        updateHistoryModel: function () {
            let historyMap = this.getMapCalculations();
            let reversedHistory = this.reverseHistory(historyMap);
            let modelBindingName = "history";
            let oModel = new JSONModel(reversedHistory);
            let view = this.getView();

            view.setModel(oModel, modelBindingName);
        },
        getMapCalculations: function () {
            let map = sessionStorage.getItem(this.mapName);
            if (map !== null) {
                return JSON.parse(map);
            } else {
                sessionStorage.setItem(this.mapName, "{}");
                return JSON.parse(sessionStorage.getItem(this.mapName));
            }
        },
        reverseHistory:function(history){
               let historyAsArray =  Object.values(history);
               return Array.prototype.reverse.call(historyAsArray);
        },

        setErrorModel: function (xhr) {
            let sErrorTextID = "errorText";
            var oResponse = JSON.parse(xhr.responseText);
            let sError = oResponse.message;
            this.getView().byId(sErrorTextID).setText(sError)
        },

    })
});
