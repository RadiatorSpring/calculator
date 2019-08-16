sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"sap/ui/demo/walkthrough/models/Formatter"

], function (Controller, JSONModel, Formatter) {
	"use strict";

	return Controller.extend("sap.ui.demo.walkthrough.controller.Expression", {

		onCalculate: function () {
			var expression = this.getView().byId("expression").getValue();
			var sUrlReadableExpression = Formatter.getExpression(expression);
			var oModel = new JSONModel();
			oModel.loadData("/calculator/api/calculate?expression=" + sUrlReadableExpression).then(() => {
				this.getView().setModel(oModel)
			}).catch((error) => {
				console.log(error.responseText);
				var sError = JSON.parse(error.responseText);
				this.getView().byId("errorText").setText(sError.message);
			})
		}
	});



});