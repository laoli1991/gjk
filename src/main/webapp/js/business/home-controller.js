angular.module('home.controller', ['ngRoute', 'index.controller' , 'manage.controller'])
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
            .when('/bb', {templateUrl: '/view/bb.html'})
            .when('/cc', {templateUrl: '/view/cc.html'})

            .otherwise({redirectTo: '/'});
    }]);