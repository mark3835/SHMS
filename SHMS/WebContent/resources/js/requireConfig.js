requirejs.config({
    baseUrl: '',
    paths: {
        jquery: '/resources/js/jquery-3.4.1.min',
        popper: '/resources/js/popper.min',
        bootstrap: '/resources/js/bootstrap'
    },
    map: {
        '*': {
            'popper.js': 'popper'
        }
    }
});
require(["jquery", "popper", "bootstrap"], function (jquery, popper, bootstrap) {
});