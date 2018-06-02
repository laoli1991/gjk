var app = angular.module("manage.controller", ["ngTable", "ui.bootstrap"]);

app.controller("manageCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {

    $scope.tab1 = true;
    $scope.tab2 = false;

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
            $scope.getstockList();
        }
        else if (k == 2) {
            $scope.tab1 = false;
            $scope.tab2 = true;
        }
    };

    $scope.getVoucherList = function () {
        var deferred = $q.defer();
        $http.get("../api/voucher-list")
            .success(function (data) {
                $scope.voucherLis = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    // $scope.getVoucher1List = function () {
    //     var deferred = $q.defer();
    //     $http.get("../api/voucher-list?type=1")
    //         .success(function (data) {
    //             $scope.voucherZlist = data;
    //             deferred.resolve(data);
    //         })
    //         .error(function (data) {
    //             deferred.reject(data);
    //         });
    //     return deferred.promise;
    // };
    // $scope.getVoucher2List = function () {
    //     var deferred = $q.defer();
    //     $http.get("../api/voucher-list?type=2")
    //         .success(function (data) {
    //             $scope.voucherYlist = data;
    //             deferred.resolve(data);
    //         })
    //         .error(function (data) {
    //             deferred.reject(data);
    //         });
    //     return deferred.promise;
    // };


    // $scope.getVoucher1List();
    // $scope.getVoucher2List();
    $scope.getstockList();
    $scope.getVoucherList();

    $scope.operatType = [
        {"desc": "入库", idx: 1},
        {"desc": "出库", idx: 2}];


    $scope.ybTypes = [
        {"desc": "原封劵", idx: 6},
        {"desc": "已清分", idx: 7},
        {"desc": "未清分", idx: 8},
        {"desc": "已复点", idx: 9},
        {"desc": "未复点", idx: 10}];

    $scope.voucherTypes = [
        {"desc": "原封劵", idx: 1, "typeDesc": "完整卷"},
        {"desc": "已清分", idx: 2, "typeDesc": "完整卷"},
        {"desc": "未清分", idx: 3, "typeDesc": "完整卷"},
        {"desc": "已复点", idx: 4, "typeDesc": "残损卷"},
        {"desc": "未复点", idx: 5, "typeDesc": "残损卷"}
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

    $scope.xiangShow = false;
    $scope.daiShow = false;
    $scope.kunShow = false;
    $scope.baShow = false;
    $scope.heShow = false;


    $scope.chgVoucher = function () {
        console.log("sdds");
        console.log($scope.curStock);
        if ($scope.curStock != undefined && $scope.curStock.type != undefined && $scope.curStock.voucher != undefined) {
            $scope.xiangShow = false;
            $scope.daiShow = false;
            $scope.kunShow = false;
            $scope.baShow = false;
            $scope.heShow = false;
            if($scope.curStock.voucher.type == 1){//纸币
                if($scope.curStock.type.idx <= 3){//完整卷
                    $scope.xiangShow = true;
                    $scope.kunShow = true;
                    $scope.baShow = true;
                }
                else{//残损卷
                    $scope.daiShow = true;
                    $scope.kunShow = true;
                    $scope.baShow = true;
                }
            }
            else {//硬币
                $scope.xiangShow = true;
                $scope.heShow = true;
            }
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


    $scope.addStock = function (curStock) {
        console.log(curStock);
        if (curStock == null || curStock.type == undefined || curStock.voucher == undefined) {
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
                "allCount": curStock.allCount,
                "amount": curStock.amount,
                "baCount": curStock.baCount,
                "kunCount": curStock.kunCount,
                "xiangCount": curStock.xiangCount,
                "voucherUid": curStock.voucher.uId,
                "voucherName": curStock.voucher.name,
                "type": curStock.type.idx,
                "typeDesc": curStock.type.desc,
                "voucherAmount": curStock.voucher.amount
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
                "voucherAmount": yb.voucher.amount
            }
        }).success(function (req) {
            console.log(req);
            $scope.stockList = req;
            swal("录入成功！", "", "success");
        })
    };


    $scope.removeStock = function (stockUid) {
        swal({
                title: "确定删除吗？",
                text: "你将无法恢复该记录！",
                type: "warning",
                cancelButtonText: '取消',
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                var deferred = $q.defer();
                $http.get("../api/remove-stock?stockUid=" + stockUid)
                    .success(function (data) {
                        $scope.stockList = data;
                        deferred.resolve(data);
                        swal("删除！", "该记录已经被删除", "success");
                    })
                    .error(function (data) {
                        deferred.reject(data);
                    });
                return deferred.promise;
            });
    };


    $scope.sendMsg = function (commonInfo) {
        console.log(commonInfo);
        if (commonInfo == null || commonInfo == undefined) {
            commonInfo = "";
        }
        var deferred = $q.defer();
        $http.get("../api/send-msg?commonInfo=" + commonInfo)
            .success(function (data) {
                swal("同步成功", "", "success");
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;

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


