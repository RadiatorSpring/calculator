module.exports = function (config) {
    config.set({

        frameworks: ["ui5"],

        ui5: {
            type: "application",
            paths: {
                webapp: "src/main/webapp"
            },
            url: "https://localhost:8080",
            testpage: "src/main/webapp/test/integration/opa.html"
        
        
        },
        browsers: ["Chrome"]

    });
};
