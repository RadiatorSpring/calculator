sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel"

], function (Controller,JSONModel) {
	"use strict";

	return Controller.extend("sap.ui.demo.walkthrough.controller.Expression", {

		onCalculate: function (Text) {
			var expression = this.getView().byId("expression").getValue();
			var urlReadableExpression = expression.replace("+","%2B");
			var oModel = new JSONModel();

			oModel.loadData("/calculator/api/calculate?expression=" + urlReadableExpression);
			this.getView().setModel(oModel);

		}

	});

});