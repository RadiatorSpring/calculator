sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageToast",
	"sap/m/Text"
], function (Controller, Text) {
	"use strict";

	return Controller.extend("sap.ui.demo.walkthrough.controller.Expression", {

		onCalculate: function (Text) {
			var expression = this.getView().byId("expression").getValue().replace("+","%2B");
			console.log(expression);
			var oModel = new sap.ui.model.json.JSONModel();
			oModel.loadData("/calculator/api/calculate?expression=" + expression);
			this.getView().setModel(oModel);
		}

	});

});