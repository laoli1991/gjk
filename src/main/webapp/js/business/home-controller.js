angular.module('home.controller', ['ngRoute', 'manage.controller', "configure.controller", "screen.controller"])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {
                template: '/view/manage.html',
                controller: 'manageCtrl'
            })
            .when('/manage', {
                templateUrl: '/view/manage.html',
                controller: 'manageCtrl'
            })
            .when('/configure', {
                templateUrl: '/view/configure.html',
                controller: 'configureCtrl'
            })
            .when('/screen', {
                templateUrl: '/view/screen.html',
                controller: 'screenCtrl'
            })
            .otherwise({redirectTo: '/'});
    }]);