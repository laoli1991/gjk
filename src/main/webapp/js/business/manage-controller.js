var app = angular.module("manage.controller", []);

app.controller("manageCtrl", ["$scope", function ($scope ) {

    $scope.insetTab = true ;
    $scope.operateTab = false ;

    $scope.tabShow = function (k) {
        if(k == 1){
            $scope.insetTab = true ;
            $scope.operateTab = false ;
        }
        else if(k == 2){
            $scope.insetTab = false ;
            $scope.operateTab = true ;
        }
    };

    $scope.currencyTypelis = ["纸币100元" , "纸币500元" , "纸币200元" , "纸币10元" , "纸币5元" , "纸币1元" , "硬币1元" , "硬币0.5元"] ;
    $scope.currencyUnitlis = ["箱" , "贷" , "捆" , "张" , "枚"] ;
    $scope.currencyAttributelis = ["完整卷" , "残缺卷"] ;


    $scope.insertCurrency = function(currency){
        console.log(currency) ;
    };



}]);
