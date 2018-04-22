angular.module('home.controller', ['ngRoute', 'index.controller', 'manage.controller'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {
                template: '',
                controller: 'indexCtrl'
            })
            .when('/manage', {
                templateUrl: '/view/manage.html',
                controller: 'manageCtrl'
            })
            .when('/configure', {
                templateUrl: '/view/configure.html',
                controller: 'manageCtrl'
            })
            .when('/cc', {templateUrl: '/view/cc.html'})

            .otherwise({redirectTo: '/'});
    }]);