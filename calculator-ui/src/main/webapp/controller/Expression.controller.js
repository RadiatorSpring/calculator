sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/ui/thirdparty/jquery"

], function (Controller, JSONModel) {

    return Controller.extend("calculator.ui.controller.Expression", {
        mapName: "map",
        historyId: "historyId",
        interval: {},
        onInit: function () {
            let historyId = sessionStorage.getItem(this.historyId)
            if (historyId !== undefined) {
                this.setHistoryId();
            }
            this.interval = setInterval(() => {
                this.updateHistory()
            }, 1300);
        },
        setHistoryId: function () {
            let manifest = this.getManifest();
            let url = manifest.dataSources.calculator.restApiHistoryId;

            this.doGetXHR(url, "", function (xhr) {
                let oHistoryId = JSON.parse(xhr.responseText);
                sessionStorage.setItem(this.historyId, oHistoryId.historyId);
            }.bind(this))
        },
        updateHistory: function () {
            let manifest = this.getManifest();
            let historyId = sessionStorage.getItem(this.historyId);
            let url = manifest.dataSources.calculator.restApiHistory + historyId;

            this.doGetXHR(url, "", function (xhr) {
                let items = JSON.parse(xhr.responseText);
                this.updateHistoryModel(items);
            }.bind(this));
        },
        updateHistoryModel: function (items) {
            let modelBindingName = "history";
            let modifiedItems = this.parseExpressions(items);
            let oModel = new JSONModel(modifiedItems);
            let view = this.getView();

            view.setModel(oModel, modelBindingName);
        },
        parseExpressions: function (historyItems) {
            historyItems.forEach(element => {
                if (element.error === null) {
                    delete element.error;
                } else {
                    delete element.evaluation;
                }
            })
            return historyItems;
        },
        onPost: function () {
            var expression = this.getView().byId("expression").getValue();
            let historyId = sessionStorage.getItem(this.historyId);
            var оBody = {
                expression: expression,
                historyId: historyId
            }
            var sBody = JSON.stringify(оBody);
            this.doPostXHR(sBody, function (xhr) {
                var oResponse = JSON.parse(xhr.responseText);
                var responseId = oResponse.id;

                var getCall = setInterval(() => {
                    this.onGet(responseId, getCall)
                }, 1600);

            }.bind(this))

        },
        doPostXHR: function (body, done) {
            let xhr = new XMLHttpRequest();
            let manifest = this.getManifest();
            let url = manifest.dataSources.calculator.restApiPost;
            xhr.open("POST", url);
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onload = function () {
                done(xhr);
            };
            xhr.onerror = function () {
                done(xhr)
            };
            xhr.send(body)
        },

        onGet: async function (id, intervalCallback) {
            let manifest = this.getManifest();
            let url = manifest.dataSources.calculator.restApiGet;

            this.doGetXHR(url, id, function (xhr) {
                var evaluation = xhr.responseText;
                var oModel = new JSONModel();
                oModel.setData(JSON.parse(evaluation));
                if (this.isStatusOK(xhr)) {
                    this.getView().setModel(oModel);
                    clearInterval(intervalCallback);
                } else if (this.isNotStatusAccepted(xhr)) {
                    this.setErrorModel(xhr);
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
        doGetXHR: function (url, id, done) {
            let xhr = new XMLHttpRequest();

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
        setErrorModel: function (xhr) {
            let sErrorTextID = "errorText";
            var oResponse = JSON.parse(xhr.responseText);
            let sError = oResponse.message;
            this.getView().byId(sErrorTextID).setText(sError)
        },
        getManifest: function () {
            return this.getOwnerComponent().getMetadata().getManifestEntry("sap.app");
        },
        onExit: function () {
            clearInterval(this.interval);
        }

    })
});
