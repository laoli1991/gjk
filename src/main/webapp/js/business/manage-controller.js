var app = angular.module("manage.controller", ["ngTable", "ui.bootstrap"]);

app.controller("manageCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {

    $scope.tab1 = true;
    $scope.tab2 = false;
    $scope.tab3 = false;

    $scope.getstockList = function () {
        var deferred = $q.defer();
        $http.get("../api/stock-list")
            .success(function (data) {
                $scope.stockList = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    $scope.tabShow = function (k) {
        if (k == 1) {
            $scope.tab1 = true;
            $scope.tab2 = false;
            $scope.tab3 = false;
            $scope.getstockList();
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
    $scope.getstockList();


    $scope.ybTypes = [
        {"desc": "原封劵", idx: 6},
        {"desc": "已清分", idx: 7},
        {"desc": "未清分", idx: 8},
        {"desc": "已复点", idx: 9},
        {"desc": "未复点", idx: 10}];

    $scope.zwTypes = [
        {"desc": "完整卷(原封劵)", idx: 1, "typeDesc": "完整卷"},
        {"desc": "完整卷(已清分)", idx: 2, "typeDesc": "完整卷"},
        {"desc": "完整卷(未清分)", idx: 3, "typeDesc": "完整卷"},
        {"desc": "残缺卷(已复点)", idx: 4, "typeDesc": "残缺卷"},
        {"desc": "残缺卷(未复点)", idx: 5, "typeDesc": "残缺卷"}
    ];


    $scope.chgZw = function () {
        console.log($scope.zw);
        $scope.zw.allCount = Number(0);
        if ($scope.zw.xiangCount != undefined && $scope.zw.xiangCount != null) {
            if ($scope.zw.voucher.amount == 0.5 || $scope.zw.voucher.amount == 0.1) {
                $scope.zw.allCount += Number($scope.zw.xiangCount) * 25 * 10 * 100;//箱->张
            }
            else {
                $scope.zw.allCount += Number($scope.zw.xiangCount) * 20 * 10 * 100;//箱->张
            }
        }
        if ($scope.zw.kunCount != undefined && $scope.zw.kunCount != null) {
            $scope.zw.allCount += Number($scope.zw.kunCount) * 10 * 100;
        }
        if ($scope.zw.baCount != undefined && $scope.zw.baCount != null) {
            $scope.zw.allCount += Number($scope.zw.baCount) * 100;
        }

        $scope.zw.amount = Number(0);
        if ($scope.zw.voucher != undefined && $scope.zw.voucher != null) {
            $scope.zw.amount += Number($scope.zw.voucher.amount) * $scope.zw.allCount;
        }

    };


    $scope.chgYb = function () {
        console.log($scope.yb);
        $scope.yb.allCount = Number(0);
        if ($scope.yb.xiangCount != undefined && $scope.yb.xiangCount != null) {
            if ($scope.yb.voucher.amount == 0.1) {
                $scope.yb.allCount += Number($scope.yb.xiangCount) * 10 * 500;//箱->枚
            }
            else {
                $scope.yb.allCount += Number($scope.yb.xiangCount) * 10 * 400;//箱->枚
            }
        }
        if ($scope.yb.heCount != undefined && $scope.yb.heCount != null) {
            if ($scope.yb.voucher.amount == 0.1) {
                $scope.yb.allCount += Number($scope.yb.heCount) * 500;//箱->枚
            }
            else {
                $scope.yb.allCount += Number($scope.yb.heCount) * 400;//箱->枚
            }
        }

        $scope.yb.amount = Number(0);
        if ($scope.yb.voucher != undefined && $scope.yb.voucher != null) {
            $scope.yb.amount += Number($scope.yb.voucher.amount) * $scope.yb.allCount;
        }

    };


    $scope.addZwStock = function (zw) {
        console.log(zw);
        if (zw == null || zw.type == undefined || zw.voucher == undefined) {
            swal({
                title: "请填写完整信息",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
            return;
        }
        $http({
            method: 'post',
            url: '../api/add-stock',
            data: {
                "allCount": zw.allCount,
                "amount": zw.amount,
                "baCount": zw.baCount,
                "kunCount": zw.kunCount,
                "xiangCount": zw.xiangCount,
                "voucherUid": zw.voucher.uId,
                "voucherName": zw.voucher.name,
                "type": zw.type.idx,
                "typeDesc": zw.type.desc,
                "voucherAmount":zw.voucher.amount
            }
        }).success(function (req) {
            console.log(req);
            $scope.stockList = req;
            swal("录入成功！", "", "success");
        })
    };

    $scope.addYbStock = function (yb) {
        console.log(yb);
        if (yb == null || yb.type == undefined || yb.voucher == undefined) {
            swal({
                title: "请填写完整信息",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
            return;
        }
        $http({
            method: 'post',
            url: '../api/add-stock',
            data: {
                "allCount": yb.allCount,
                "amount": yb.amount,
                "heCount": yb.heCount,
                "xiangCount": yb.xiangCount,
                "voucherUid": yb.voucher.uId,
                "voucherName": yb.voucher.name,
                "type": yb.type.idx,
                "typeDesc": yb.type.desc,
                "voucherAmount":yb.voucher.amount
            }
        }).success(function (req) {
            console.log(req);
            $scope.stockList = req;
            swal("录入成功！", "", "success");
        })
    };


    $scope.mustDouble = function clearNoNum(obj, attr) {
        obj[attr] = obj[attr].replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
        obj[attr] = obj[attr].replace(/^\./g, "");  //验证第一个字符是数字而不是.
        obj[attr] = obj[attr].replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
        obj[attr] = obj[attr].replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj[attr] = obj[attr].replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
    }

    $scope.mustInt = function clearNoNum(obj, attr) {
        obj[attr] = obj[attr].replace(/[^\d]/g, "");  //清除“数字”和“.”以外的字符
    }


}]);


