var app = angular.module("index.controller", []);

app.controller("indexCtrl", ["$scope", function ($scope ) {


    $scope.msgs = [{"xx" : "入库请求" , "oo" :"2018-04-21 14:00 张三"},{"xx" : "出库请求" , "oo" :"2018-04-21 15:00 李四"} , {"xx" : "入库请求" , "oo" :"2018-04-21 15:30 王五"}] ;
    $scope.msgsLen = $scope.msgs.length ;

    $scope.hh = function (msg) {
        console.log(msg) ;
    }

    console.info("sddssdds") ;
    console.log($scope.msgs) ;

}]);
