var app = angular.module("manage.controller", ["ngTable", "ui.bootstrap"]);

app.controller("manageCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {

    $scope.tab1 = true;
    $scope.tab2 = false;
    $scope.tab3 = false;
    $scope.tab4 = false;

    $scope.tabShow = function (k) {
        if (k == 1) {
            $scope.tab1 = true;
            $scope.tab2 = false;
            $scope.tab3 = false;
            $scope.tab4 = false;
            $scope.getstockList();
        }
        else if (k == 2) {
            $scope.tab1 = false;
            $scope.tab2 = true;
            $scope.tab3 = false;
            $scope.tab4 = false;
            $scope.enterStock = undefined;
            $scope.outStock = undefined;
            $scope.xiangShow = false;
            $scope.daiShow = false;
            $scope.kunShow = false;
            $scope.baShow = false;
            $scope.heShow = false;
        }
        else if (k == 3) {
            $scope.tab1 = false;
            $scope.tab2 = false;
            $scope.tab3 = true;
            $scope.tab4 = false;
            $scope.enterStock = undefined;
            $scope.outStock = undefined;
            $scope.xiangShow = false;
            $scope.daiShow = false;
            $scope.kunShow = false;
            $scope.baShow = false;
            $scope.heShow = false;
        }
        else if (k == 4) {
            $scope.tab1 = false;
            $scope.tab2 = false;
            $scope.tab3 = false;
            $scope.tab4 = true;
            $scope.enterStock = undefined;
            $scope.outStock = undefined;
            $scope.xiangShow = false;
            $scope.daiShow = false;
            $scope.kunShow = false;
            $scope.baShow = false;
            $scope.heShow = false;
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
        {"desc": "完整券（原封劵）", idx: 1, "typeDesc": "完整券"},
        {"desc": "完整券（已清分）", idx: 2, "typeDesc": "完整券"},
        {"desc": "完整券（未清分）", idx: 3, "typeDesc": "完整券"},
        {"desc": "残损券（已复点）", idx: 4, "typeDesc": "残损券"},
        {"desc": "残损券（未复点）", idx: 5, "typeDesc": "残损券"}
    ];

    $scope.xiangShow = false;
    $scope.daiShow = false;
    $scope.kunShow = false;
    $scope.baShow = false;
    $scope.heShow = false;
    $scope.enterStock = undefined;
    $scope.outStock = undefined;


    $scope.chgCurStock = function (curStock) {
        if (curStock != undefined && curStock.type != undefined && curStock.voucher != undefined) {
            $scope.xiangShow = false;
            $scope.daiShow = false;
            $scope.kunShow = false;
            $scope.baShow = false;
            $scope.heShow = false;
            curStock.allCount = undefined;
            curStock.amount = undefined;
            curStock.xiangCount = undefined;
            curStock.daiCount = undefined;
            curStock.kunCount = undefined;
            curStock.baCount = undefined;
            curStock.heCount = undefined;

            if (curStock.voucher.type == 1) {//纸币
                if (curStock.type.idx <= 1) {//完整券 原封券
                    $scope.xiangShow = true;
                    $scope.kunShow = true;
                    $scope.baShow = true;
                }
                else {//残损券
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


    $scope.chgAmount = function (curStock) {
        console.log(curStock);
        if (curStock == null || curStock == undefined || curStock.voucher == null || curStock.voucher == undefined) {
            return;
        }
        curStock.allCount = Number(0);
        if (curStock.voucher.type == 1) {//纸币
            if (curStock.type.idx <= 1) {//完整券 原封券
                if (curStock.xiangCount != null && curStock.xiangCount != undefined && curStock.xiangCount.length != 0) {
                    curStock.allCount += Number(curStock.xiangCount) * curStock.voucher.xiang2Kun * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang;//箱->张
                }
            } else {
                if (curStock.daiCount != null && curStock.daiCount != undefined && curStock.daiCount.length != 0) {
                    curStock.allCount += Number(curStock.daiCount) * curStock.voucher.dai2Kun * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang;//袋->张
                }
            }
            if (curStock.kunCount != null && curStock.kunCount != undefined && curStock.kunCount.length != 0) {
                curStock.allCount += Number(curStock.kunCount) * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang;//捆->张
            }
            if (curStock.baCount != null && curStock.baCount != undefined && curStock.baCount.length != 0) {
                curStock.allCount += Number(curStock.baCount) * curStock.voucher.ba2Zhang;//把->张
            }
            curStock.amount = Number(0);
            curStock.amount += Number(curStock.voucher.amount) * curStock.allCount;
        }
        else if (curStock.voucher.type == 2) {
            if (curStock.xiangCount != null && curStock.xiangCount != undefined && curStock.xiangCount.length != 0) {
                curStock.allCount += Number(curStock.xiangCount) * curStock.voucher.xiang2He * curStock.voucher.he2Mei;//箱->枚
            }
            if (curStock.heCount != null && curStock.heCount != undefined && curStock.heCount.length != 0) {
                curStock.allCount += Number(curStock.heCount) * curStock.voucher.he2Mei;//箱->枚
            }
            curStock.amount = Number(0);
            curStock.amount += Number(curStock.voucher.amount) * curStock.allCount;
        }
    };


    $scope.preAddStock = function (opeStock, operation) {
        if (opeStock == undefined || opeStock.type == undefined || opeStock.voucher == undefined || opeStock.amount == undefined || opeStock.amount == 0) {
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
        curStock = JSON.parse(JSON.stringify(opeStock));
        console.log(curStock);
        info = "";
        info = info + "<font color='red'> 券别： " + curStock.voucher.name + " </font><br>";
        info = info + "<font color='red'> 类型： " + curStock.type.desc + " </font><br>";
        if (curStock.voucher.type == 1) {//纸币
            nowAllCount = curStock.allCount;
            if (curStock.type.idx <= 1) {//完整券 原封券
                if (curStock.xiangCount == null || curStock.xiangCount == undefined || curStock.xiangCount.length == 0) {
                    curStock.xiangCount = 0;
                }
                info = info + "<font color='red'> 箱数： " + curStock.xiangCount + " </font><br>";
                curStock.xiangCount = nowAllCount / (curStock.voucher.xiang2Kun * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang);//张->箱
                curStock.xiangCount = parseInt(curStock.xiangCount);
                nowAllCount = nowAllCount % (curStock.voucher.xiang2Kun * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang);
            } else {
                if (curStock.daiCount == null || curStock.daiCount == undefined || curStock.daiCount.length == 0) {
                    curStock.daiCount = 0;
                }
                info = info + "<font color='red'> 袋数： " + curStock.daiCount + " </font><br>";
                curStock.daiCount = nowAllCount / (curStock.voucher.dai2Kun * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang);//张->袋
                curStock.daiCount = parseInt(curStock.daiCount);
                nowAllCount = nowAllCount % (curStock.voucher.dai2Kun * curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang);
            }
            if (curStock.kunCount == null || curStock.kunCount == undefined || curStock.kunCount.length == 0) {
                curStock.kunCount = 0;
            }
            info = info + "<font color='red'> 捆数： " + curStock.kunCount + " </font><br>";
            // info = info + "<font color='red'> 把数： " + curStock.baCount + " </font><br>";
            info = info + "<font color='red'> 金额： " + curStock.amount + "  元</font><br>";
            curStock.kunCount = nowAllCount / (curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang);//张->捆
            nowAllCount = nowAllCount % (curStock.voucher.kun2Ba * curStock.voucher.ba2Zhang);
            curStock.kunCount = parseInt(curStock.kunCount);
            curStock.baCount = nowAllCount / (curStock.voucher.ba2Zhang);//张->把
            curStock.baCount = parseInt(curStock.baCount);
        }
        else if (curStock.voucher.type == 2) {
            if (curStock.xiangCount == null || curStock.xiangCount == undefined || curStock.xiangCount.length == 0) {
                curStock.xiangCount = 0;
            }
            if (curStock.heCount == null || curStock.heCount == undefined || curStock.heCount.length == 0) {
                curStock.heCount = 0;
            }
            info = info +  "<font color='red'> 箱数： " + curStock.xiangCount + " </font><br>";
            info = info + "<font color='red'> 盒数： " + curStock.heCount + " </font><br>";
            info = info + "<font color='red'> 金额： " + curStock.amount + "  元</font><br>";
            nowAllCount = curStock.allCount;
            curStock.xiangCount = nowAllCount / (curStock.voucher.xiang2He * curStock.voucher.he2Mei);//枚->箱
            curStock.xiangCount = parseInt(curStock.xiangCount);
            nowAllCount = nowAllCount % (curStock.voucher.xiang2He * curStock.voucher.he2Mei);
            curStock.heCount = nowAllCount / (curStock.voucher.he2Mei);//枚->箱
            curStock.heCount = parseInt(curStock.heCount);
        }
        opInfo = "";
        if(operation == 0){
            opInfo = "复核【入库】"
        }
        else if(operation == 1){
            opInfo = "复核【更正】"
        }
        else if(operation == 2){
            opInfo = "复核【出库】"
        }
        swal({
                title: opInfo,
                type: 'info',
                text: info,
                html: true,
                cancelButtonText: '取消',
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                swal.close();
                $scope.addStock(curStock, operation);
            });
    };

    $scope.addStock = function (curStock, operation) {
        console.log(curStock);
        if (curStock == undefined || curStock.type == undefined || curStock.voucher == undefined || curStock.amount == undefined || curStock.amount == 0) {
            swal({
                title: "请填写完整信息",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: true
            });
            return;
        }
        if (curStock.voucher.type == 1) {//纸币
            if (curStock.kunCount == undefined) {
                curStock.kunCount = 0;
            }
            if (curStock.baCount == undefined) {
                curStock.baCount = 0;
            }
            if (curStock.type.idx <= 1) {//完整券
                if (curStock.xiangCount == undefined) {
                    curStock.xiangCount = 0;
                }
            }
            else {//残损券
                if (curStock.daiCount == undefined) {
                    curStock.daiCount = 0;
                }
            }
        }
        else {//硬币
            if (curStock.xiangCount == undefined) {
                curStock.xiangCount = 0;
            }
            if (curStock.heCount == undefined) {
                curStock.heCount = 0;
            }
        }
        $http({
            method: 'post',
            url: '../api/add-stock',
            data: {
                "operation": operation,
                "allCount": curStock.allCount,
                "amount": curStock.amount,
                "baCount": curStock.baCount,
                "kunCount": curStock.kunCount,
                "xiangCount": curStock.xiangCount,
                "daiCount": curStock.daiCount,
                "heCount": curStock.heCount,
                "voucherUid": curStock.voucher.uId,
                "voucherName": curStock.voucher.name,
                "voucherType": curStock.voucher.type,
                "type": curStock.type.idx,
                "typeDesc": curStock.type.desc,
                "voucherAmount": curStock.voucher.amount,
                "voucherXiang2Kun": curStock.voucher.xiang2Kun,
                "voucherDai2Kun": curStock.voucher.dai2Kun,
                "voucherKun2Ba": curStock.voucher.kun2Ba,
                "voucherBa2Zhang": curStock.voucher.ba2Zhang,
                "voucherXiang2He": curStock.voucher.xiang2He,
                "voucherHe2Mei": curStock.voucher.he2Mei
            }
        }).success(function (req) {
            console.log(req);
            if (req.code == 1 && operation == 0) {//入库
                $scope.stockList = req.stockPos;
                swal({
                    title: "入库成功",
                    text: "",
                    type: "success",
                    showCancelButton: false,
                    confirmButtonColor: "#3498db",
                    confirmButtonText: "关闭",
                    closeOnConfirm: false
                });
                return;
            }
            if (req.code == 1 && operation == 1) {//入库
                $scope.stockList = req.stockPos;
                swal({
                    title: "更正成功",
                    text: "",
                    type: "success",
                    showCancelButton: false,
                    confirmButtonColor: "#3498db",
                    confirmButtonText: "关闭",
                    closeOnConfirm: false
                });
                return;
            }
            else {//出库
                if (req.code == 2) {
                    swal({
                        title: "没有对应入库记录",
                        text: "出库失败",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "关闭",
                        closeOnConfirm: false
                    });
                    return;
                }
                else if (req.code == 3) {
                    swal({
                        title: "库存不足",
                        text: "出库失败",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "关闭",
                        closeOnConfirm: false
                    });
                    return;
                }
                else if (req.code == 4) {
                    // swal({
                    //         title: "确定出库吗？",
                    //         text: " ",
                    //         type: "warning",
                    //         cancelButtonText: '取消',
                    //         showCancelButton: true,
                    //         confirmButtonColor: "#DD6B55",
                    //         confirmButtonText: "确定",
                    //         closeOnConfirm: false
                    //     },
                    //     function () {
                    //         $scope.addStock(curStock, 3);
                    //     });
                    // return;
                    $scope.addStock(curStock, 3);
                }
                else if (req.code == 5) {//出库成功
                    $scope.stockList = req.stockPos;
                    swal({
                        title: "出库成功",
                        text: "",
                        type: "success",
                        showCancelButton: false,
                        confirmButtonColor: "#3498db",
                        confirmButtonText: "关闭",
                        closeOnConfirm: false
                    });
                    return;
                }
            }


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
        if (obj[attr] != undefined) {
            obj[attr] = obj[attr].replace(/[^\d]/g, "");  //清除“数字”和“.”以外的字符
        }
    };


}]);


