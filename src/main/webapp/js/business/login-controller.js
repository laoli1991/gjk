var app = angular.module("login.controller", []);

app.controller("loginCtrl", ["$scope", function ($scope) {

    $scope.login = function (user) {
        console.log($scope.user.username);
        console.log($scope.user.password);
    }

}]);
