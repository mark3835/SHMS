//需要使用到的JS位置設定
requirejs.config({
    baseUrl: '',
    paths: {
        jquery: '../resources/js/jquery-3.4.1.min',
        popper: '../resources/js/popper.min',
        bootstrap: '../resources/js/bootstrap.min',
        jquery_validate: '../resources/js/jquery.validate.min'
    },
    map: {
        '*': {
            'popper.js': 'popper'
        }
    }
});

var CONFIG = {
				"SYSTEM_NAME":"SHMS"
			};