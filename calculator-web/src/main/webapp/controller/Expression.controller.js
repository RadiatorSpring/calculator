sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageToast"
], function (Controller, MessageToast) {
	"use strict";

	return Controller.extend("sap.ui.demo.walkthrough.controller.Expression", {

		onCalculate: function () {
			var sExpression = "1-1";
			var oModel = new sap.ui.model.json.JSONModel();
			oModel.loadData("http://localhost:8080/calculator/calculate?expression=" + sExpression);
			this.getView().setModel(oModel);
		}


		//  onCalculate : function () {

		//  	// read msg from i18n model
		//  	var oBundle = this.getView().getModel("i18n").getResourceBundle();
		//  	var sRecipient = this.getView().getModel().getProperty("/recipient/name");
		//  	var sMsg = oBundle.getText("helloMsg", [sRecipient]);
		//  	// show message
		//  	MessageToast.show(sMsg);
		//  }


	});

});