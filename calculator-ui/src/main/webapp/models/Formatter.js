sap.ui.define([
    
], function () {
    
    return {
        getExpression : function (expression) {
			var urlReadableExpression = expression.replace("+", "%2B");
            return urlReadableExpression;
        }
    };
    
});