var app = angular.module("manage.controller", ["ngTable", "ui.bootstrap"]);

app.controller("manageCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {

    $scope.tab1 = true;
    $scope.tab2 = false;
    $scope.tab3 = false;

    $scope.tabShow = function (k) {
        if (k == 1) {
            $scope.tab1 = true;
            $scope.tab2 = false;
            $scope.tab3 = false;
        }
        else if(k == 2) {
            $scope.tab1 = false;
            $scope.tab2 = true;
            $scope.tab3 = false;
        }
        else if(k == 3) {
            $scope.tab1 = false;
            $scope.tab2 = false;
            $scope.tab3 = true;
        }
    };

    $scope.currencyTypelis = [{"des": "纸币100元", "idx": 1},
        {"des": "纸币50元", "idx": 2},
        {"des": "纸币20元", "idx": 3},
        {"des": "纸币10元", "idx": 4},
        {"des": "纸币5元", "idx": 5},
        {"des": "纸币1元", "idx": 6},
        {"des": "硬币1元", "idx": 7},
        {"des": "硬币0.5元", "idx": 8}];
    $scope.currencyUnitlis = [{"des": "箱", "idx": 1},
        {"des": "贷", "idx": 2},
        {"des": "捆", "idx": 3},
        {"des": "张", "idx": 4},
        {"des": "枚", "idx": 5}];
    $scope.currencyAttributelis = [{"des": "完整卷", "idx": 1},
        {"des": "残缺卷", "idx": 2}];


    $scope.insertCurrency = function (currency) {
        console.log(currency);
        $http({
            method: 'post',
            url: '../api/insertGjkCurrency',
            data: {
                "operator": "liyang",
                "version": currency.version,
                "unit": currency.unit.idx,
                "type": currency.currencyType.idx,
                "count": currency.count,
                "busi": currency.busi,
                "attribute": currency.attribute.idx
            }
        }).success(function (req) {
        })
    };


    $scope.selectList = function (pageNum, pageSize) {
        var deferred = $q.defer();
        $http.get("../api/selectList?pageNum=" + pageNum + "&pageSize=" + pageSize)
            .success(function (data) {
                console.log(data);
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    $scope.changeDateFir = true;

    $scope.dbTables = function () {
        if ($scope.totalTables != undefined) {
            $scope.changeDateFir = !$scope.changeDateFir;
            $scope.totalTables.page(1);
            $scope.totalTables.count(10);
            $scope.totalTables.sorting({
                changeDate: $scope.changeDateFir ? 'desc' : 'asc'
            });
        } else {
            $scope.totalTables = new NgTableParams({
                    page: 1,
                    count: 10,
                },
                {
                    counts: [],
                    getData: function ($defer, params) {
                        console.log(params);
                        var page = params.page();
                        var count = params.count();
                        $scope.selectList(page, count).then(function (data) {
                            if (data && data.list.length > 0) {
                                params.total(data.total);
                                $defer.resolve(data.list);
                            } else {
                                $defer.resolve([]);
                            }
                        }, function (data) {
                            $defer.resolve([]);
                        });
                    }
                });
        }
    };
    $scope.dbTables();

    $scope.jdate = new Date();

    $scope.searchCurrency = function(jdate){
        //Date d = Date.valueOf(jdate);
        console.log(jdate) ;

    };


}]);


