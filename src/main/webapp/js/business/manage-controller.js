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

    $scope.getstockList();
    $scope.getVoucherList();

    $scope.voucherTypes = [
        {"desc": "完整卷（原封劵）", idx: 1, "typeDesc": "完整卷"},
        {"desc": "完整卷（已清分）", idx: 2, "typeDesc": "完整卷"},
        {"desc": "完整卷（未清分）", idx: 3, "typeDesc": "完整卷"},
        {"desc": "残损卷（已复点）", idx: 4, "typeDesc": "残损卷"},
        {"desc": "残损卷（未复点）", idx: 5, "typeDesc": "残损卷"}
    ];

    $scope.xiangShow = false;
    $scope.daiShow = false;
    $scope.kunShow = false;
    $scope.baShow = false;
    $scope.heShow = false;


    $scope.chgEnterVoucher = function () {
        if ($scope.enterStock != undefined && $scope.enterStock.type != undefined && $scope.enterStock.voucher != undefined) {
            $scope.xiangShow = false;
            $scope.daiShow = false;
            $scope.kunShow = false;
            $scope.baShow = false;
            $scope.heShow = false;
            $scope.enterStock.allCount = undefined;
            $scope.enterStock.amount = undefined;
            $scope.enterStock.xiangCount = undefined;
            $scope.enterStock.daiCount = undefined;
            $scope.enterStock.kunCount = undefined;
            $scope.enterStock.baCount = undefined;
            $scope.enterStock.heCount = undefined;

            if($scope.enterStock.voucher.type == 1){//纸币
                if($scope.enterStock.type.idx <= 3){//完整卷
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


    $scope.chgEnterAmount = function () {
        console.log($scope.enterStock);
        $scope.enterStock.allCount = Number(0);
        if($scope.enterStock.voucher.type == 1){//纸币
            //      5角/1角	    1箱/袋 = 25捆	1捆 = 10把	1把 = 100张
            //      其他	        1箱/袋 = 20捆	1捆 = 10把	1把 = 100张
            if ($scope.enterStock.xiangCount != undefined && $scope.enterStock.xiangCount != null) {
                if ($scope.enterStock.voucher.amount == 0.5 || $scope.enterStock.voucher.amount == 0.1) {
                    $scope.enterStock.allCount += Number($scope.enterStock.xiangCount) * 25 * 10 * 100;//箱->张
                }
                else {
                    $scope.enterStock.allCount += Number($scope.enterStock.xiangCount) * 20 * 10 * 100;//箱->张
                }
            }
            if ($scope.enterStock.daiCount != undefined && $scope.enterStock.daiCount != null) {
                if ($scope.enterStock.voucher.amount == 0.5 || $scope.enterStock.voucher.amount == 0.1) {
                    $scope.enterStock.allCount += Number($scope.enterStock.daiCount) * 25 * 10 * 100;//袋->张
                }
                else {
                    $scope.enterStock.allCount += Number($scope.enterStock.daiCount) * 20 * 10 * 100;//袋->张
                }
            }
            if ($scope.enterStock.kunCount != undefined && $scope.enterStock.kunCount != null) {//捆->张
                $scope.enterStock.allCount += Number($scope.enterStock.kunCount) * 10 * 100;
            }
            if ($scope.enterStock.baCount != undefined && $scope.enterStock.baCount != null) {//把->张
                $scope.enterStock.allCount += Number($scope.enterStock.baCount) * 100;
            }

            $scope.enterStock.amount = Number(0);
            if ($scope.enterStock.voucher != undefined && $scope.enterStock.voucher != null) {
                $scope.enterStock.amount += Number($scope.enterStock.voucher.amount) * $scope.enterStock.allCount;
            }
        }
        else {//硬币
            //	    1元	1箱 = 10盒	1盒 = 400枚
            //      5角	1箱 = 10盒	1盒 = 400枚
            //      1角	1箱 = 10盒	1盒 = 500枚
            if ($scope.enterStock.xiangCount != undefined && $scope.enterStock.xiangCount != null) {
                if ($scope.enterStock.voucher.amount == 0.1) {
                    $scope.enterStock.allCount += Number($scope.enterStock.xiangCount) * 10 * 500;//箱->枚
                }
                else {
                    $scope.enterStock.allCount += Number($scope.enterStock.xiangCount) * 10 * 400;//箱->枚
                }
            }
            if ($scope.enterStock.heCount != undefined && $scope.enterStock.heCount != null) {
                if ($scope.enterStock.voucher.amount == 0.1) {
                    $scope.enterStock.allCount += Number($scope.enterStock.heCount) * 500;//盒->枚
                }
                else {
                    $scope.enterStock.allCount += Number($scope.enterStock.heCount) * 400;//盒->枚
                }
            }

            $scope.enterStock.amount = Number(0);
            if ($scope.enterStock.voucher != undefined && $scope.enterStock.voucher != null) {
                $scope.enterStock.amount += Number($scope.enterStock.voucher.amount) * $scope.enterStock.allCount;
            }
        }

    };

    $scope.addStock = function (enterStock) {
        console.log(enterStock);
        if (enterStock == undefined || enterStock.type == undefined || enterStock.voucher == undefined || enterStock.amount == undefined || enterStock.amount == 0) {
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
        if($scope.enterStock.voucher.type == 1){//纸币
            if($scope.enterStock.kunCount == undefined){
                $scope.enterStock.kunCount = 0;
            }
            if($scope.enterStock.baCount == undefined){
                $scope.enterStock.baCount = 0;
            }
            if($scope.enterStock.type.idx <= 3){//完整卷
                if($scope.enterStock.xiangCount == undefined){
                    $scope.enterStock.xiangCount = 0;
                }
            }
            else{//残损卷
                if($scope.enterStock.daiCount == undefined){
                    $scope.enterStock.daiCount = 0;
                }
            }
        }
        else {//硬币
            if($scope.enterStock.xiangCount == undefined){
                $scope.enterStock.xiangCount = 0;
            }
            if($scope.enterStock.heCount == undefined){
                $scope.enterStock.heCount = 0;
            }
        }
        $http({
            method: 'post',
            url: '../api/add-stock',
            data: {
                "allCount": enterStock.allCount,
                "amount": enterStock.amount,
                "baCount": enterStock.baCount,
                "kunCount": enterStock.kunCount,
                "xiangCount": enterStock.xiangCount,
                "daiCount": enterStock.daiCount,
                "heCount": enterStock.heCount,
                "voucherUid": enterStock.voucher.uId,
                "voucherName": enterStock.voucher.name,
                "type": enterStock.type.idx,
                "typeDesc": enterStock.type.desc,
                "voucherAmount": enterStock.voucher.amount
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
    };

    $scope.mustInt = function clearNoNum(obj, attr) {
        if(obj[attr] != undefined){
            obj[attr] = obj[attr].replace(/[^\d]/g, "");  //清除“数字”和“.”以外的字符
        }
    };


}]);


