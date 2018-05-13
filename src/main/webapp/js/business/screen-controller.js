var app = angular.module("screen.controller", ["ngTable", "ui.bootstrap"]);

app.controller("screenCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {


    $scope.getScreenList = function () {
        var deferred = $q.defer();
        $http.get("../api/screen-list")
            .success(function (data) {
                $scope.screenList = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    $scope.getScreenList();


}]);


