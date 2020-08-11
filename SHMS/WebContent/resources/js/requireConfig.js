//需要使用到的JS位置設定
requirejs.config({
    baseUrl: '',
    paths: {
        jquery: '../resources/js/jquery-3.4.1.min',
        popper: '../resources/js/popper.min',
        bootstrap: '../resources/js/bootstrap.min',
        jquery_validate: '../resources/js/jquery.validate.min',
        jsgrid: '../resources/js/jsgrid.min',
        jquery_ui: '../resources/js/jquery-ui.min',
        jquery_blockUI: '../resources/js/jquery.blockUI'
    },
    map: {
        '*': {
            'popper.js': 'popper'
        }
    },
    // 配置依賴性
    shim: {
        'popper' : {
            deps: ['jquery']  
        },
        'bootstrap': {
            deps: ['popper']   
        },
        'jsgrid': {
            deps: ['jquery']      
        },
        'jquery_validate': {
            deps: ['jquery']      
        },
        'jquery_ui': {
            deps: ['jquery','bootstrap']      
        },
        'jquery_blockUI': {
            deps: ['jquery','bootstrap']      
        }
    }
});

//系統參數
var CONFIG = {
				"SYSTEM_NAME":"SHMS"
			};