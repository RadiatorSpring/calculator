sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"calculator/ui/models/Formatter",
	"sap/ui/thirdparty/jquery"

], function (Controller, JSONModel, Formatter) {
	"use strict";



	return Controller.extend("calculator.ui.controller.Expression", {

		onPost: function () {
			var expression = this.getView().byId("expression").getValue();
			var settings = {
				"async": true,
				"crossDomain": true,
				"url": "../calculator-web-1.0-SNAPSHOT/api/v1/calculate",
				"method": "POST",
				"headers": {
					"Content-Type": "application/json",
					"Accept": "*/*",
					"Cache-Control": "no-cache",
					"cache-control": "no-cache"
				},
				"data": "{\n    \"expression\": \"" + expression + "\"\n}"

			}
			$.ajax(settings)
				.done(function (response) {
					var id = response.id;
					let isEvaluated = false;

					var getCall = setInterval(() => {
						isEvaluated = this.onGet(id);
						if (isEvaluated) {
							clearInterval(getCall);
						}
					}, 1600)

				}.bind(this))
				.fail(function (response) {
					console.log(response);
				})
		},

		onGet: async function (id) {

			var statusCodeOk = 200;

			var settings = {
				"async": true,
				"crossDomain": true,
				"url": "../calculator-web-1.0-SNAPSHOT/api/v1/expressions/" + id,
				"method": "GET",
				"headers": {
					"Content-Type": "application/json",
					"cache-control": "no-cache"
				},
				"processData": false,
			}
			let isEvaluated = false;
			var thisComponent = this;

			await $.ajax(settings).done(function (response, _status, jqXHR) {
				var oModel = new JSONModel();
				oModel.setData(response);
				if (jqXHR.status == statusCodeOk) {
					this.getView().setModel(oModel);
					isEvaluated = true;
				}
			}.bind(thisComponent)).fail(function (response) {
				var sError = response.responseJSON.message;
				this.getView().byId("errorText").setText(sError)
				isEvaluated = true;
			}.bind(thisComponent))

			return isEvaluated;
		}
	})
});