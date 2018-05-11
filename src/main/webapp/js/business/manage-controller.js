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

    $scope.getVoucherList = function () {
        var deferred = $q.defer();
        $http.get("../api/getVoucherList")
            .success(function (data) {
                $scope.vouchers = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    $scope.getVoucherList();

    $scope.types = [
        {"desc": "原封劵", idx: 1},
        {"desc": "已清分", idx: 2},
        {"desc": "未清分", idx: 3},
        {"desc": "已复点", idx: 4},
        {"desc": "未复点", idx: 5}];

    $scope.chg = function () {
        console.log($scope.recoder);
        $scope.recoder.allCount = Number(0);
        if ($scope.recoder.xiangCount != undefined && $scope.recoder.xiangCount != null) {
            $scope.recoder.allCount += Number($scope.recoder.xiangCount) * 20 * 100000;
        }
        if ($scope.recoder.kunCount != undefined && $scope.recoder.kunCount != null) {
            $scope.recoder.allCount += Number($scope.recoder.kunCount) * 100000;
        }
        if ($scope.recoder.baCount != undefined && $scope.recoder.baCount != null) {
            $scope.recoder.allCount += Number($scope.recoder.baCount);
        }

        $scope.recoder.amount = Number(0);
        if ($scope.recoder.voucher != undefined && $scope.recoder.voucher != null) {
            $scope.recoder.amount += Number($scope.recoder.voucher.amount) * $scope.recoder.allCount;
        }

    };


    $scope.addRecoder = function (recoder, version) {
        console.log(recoder);
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


    $scope.clearNoNum = function () {
        //先把非数字的都替换掉，除了数字和.
        $scope.kk = $scope.kk.replace(/[^\d.]/g, "");
        //必须保证第一个为数字而不是.
        $scope.kk = $scope.kk.replace(/^\./g, "");
        //保证只有出现一个.而没有多个.
        $scope.kk = $scope.kk.replace(/\.{2,}/g, "");
        //保证.只出现一次，而不能出现两次以上
        $scope.kk = $scope.kk.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    }


}]);


