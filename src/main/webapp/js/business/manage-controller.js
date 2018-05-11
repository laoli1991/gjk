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
        else if (k == 2) {
            $scope.tab1 = false;
            $scope.tab2 = true;
            $scope.tab3 = false;
        }
        else if (k == 3) {
            $scope.tab1 = false;
            $scope.tab2 = false;
            $scope.tab3 = true;
        }
    };

    $scope.getVoucher1List = function () {
        var deferred = $q.defer();
        $http.get("../api/voucher-list?type=1")
            .success(function (data) {
                $scope.voucherZlist = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };
    $scope.getVoucher2List = function () {
        var deferred = $q.defer();
        $http.get("../api/voucher-list?type=2")
            .success(function (data) {
                $scope.voucherYlist = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    $scope.getVoucher1List();
    $scope.getVoucher2List();


    $scope.types = [
        {"desc": "原封劵", idx: 1},
        {"desc": "已清分", idx: 2},
        {"desc": "未清分", idx: 3},
        {"desc": "已复点", idx: 4},
        {"desc": "未复点", idx: 5}];

    $scope.zwTypes = [
        {"desc": "原封劵", idx: 1},
        {"desc": "已清分", idx: 2},
        {"desc": "未清分", idx: 3}];


    $scope.chgZw = function () {
        console.log($scope.zw);
        $scope.zw.allCount = Number(0);
        if ($scope.zw.xiangCount != undefined && $scope.zw.xiangCount != null) {
            $scope.zw.allCount += Number($scope.zw.xiangCount) * 20 * 100000;
        }
        if ($scope.zw.kunCount != undefined && $scope.zw.kunCount != null) {
            $scope.zw.allCount += Number($scope.zw.kunCount) * 100000;
        }
        if ($scope.zw.baCount != undefined && $scope.zw.baCount != null) {
            $scope.zw.allCount += Number($scope.zw.baCount);
        }

        $scope.zw.amount = Number(0);
        if ($scope.zw.voucher != undefined && $scope.zw.voucher != null) {
            $scope.zw.amount += Number($scope.zw.voucher.amount) * $scope.zw.allCount;
        }

    };


    $scope.addZwStock = function (zw) {
        console.log(zw);
        // $http({
        //     method: 'post',
        //     url: '../api/insertGjkCurrency',
        //     data: {
        //         "operator": "liyang",
        //         "version": currency.version,
        //         "unit": currency.unit.idx,
        //         "type": currency.currencyType.idx,
        //         "count": currency.count,
        //         "busi": currency.busi,
        //         "attribute": currency.attribute.idx
        //     }
        // }).success(function (req) {
        // })
    };


    $scope.mustDouble = function clearNoNum(obj , attr) {
        obj[attr] = obj[attr].replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
        obj[attr] = obj[attr].replace(/^\./g, "");  //验证第一个字符是数字而不是.
        obj[attr] = obj[attr].replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
        obj[attr] = obj[attr].replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj[attr] = obj[attr].replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
    }

    $scope.mustInt = function clearNoNum(obj , attr) {
        obj[attr] = obj[attr].replace(/[^\d]/g, "");  //清除“数字”和“.”以外的字符
    }


}]);


