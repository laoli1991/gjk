var app = angular.module("login.controller", []);

app.controller("loginCtrl", ["$scope", function ($scope ) {

    $scope.login = function (user) {
       // console.log($scope.user.username);
       // console.log($scope.user.password);
        if($scope.user.username == "a" && $scope.user.password == "a"){
            window.location="/view/home.html";
        }
    }

}]);
