sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"calculator/ui/models/Formatter",
	"sap/ui/thirdparty/jquery"

], function (Controller, JSONModel, Formatter) {
	"use strict";

	return Controller.extend("calculator.ui.controller.Expression", {

		onCalculate: function () {
			var expression = this.getView().byId("expression").getValue();
			var sUrlReadableExpression = Formatter.getExpression(expression);
			var oModel = new JSONModel();
			oModel.loadData("/calculator/api/calculate?expression=" + sUrlReadableExpression)
				.then(function () {
					this.getView().setModel(oModel);
				}.bind(this))
				.catch(function (error) {
					var sError = JSON.parse(error.responseText);
					this.getView().byId("errorText").setText(sError.message);
				}.bind(this));
		}
	});

});