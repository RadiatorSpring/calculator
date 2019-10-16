module.exports = function (config) {
    config.set({

        frameworks: ["ui5"],

        ui5: {
            type: "application",
            paths: {
                webapp: "src/main/webapp"
            },
            url: "http://localhost:8080",
            testpage: "src/main/webapp/test/integration/opa.html"


        },
        customLaunchers: {
                    'FirefoxHeadless': {
                        base: 'Firefox',
                        flags: [
                            '-headless',
                        ],
                        prefs: {
                            'network.proxy.type': 0
                        }
                    }
                },
	    singleRun:true,
        browsers: ["FirefoxHeadless"],
        failOnEmptyTestSuite: false
    });
};