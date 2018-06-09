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

    $scope.removeScreen = function (macAddress) {
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
                $http.get("../api/remove-screen?macAddress=" + macAddress)
                    .success(function (data) {
                        $scope.screenList = data;
                        deferred.resolve(data);
                        swal("删除！", "该记录已经被删除", "success");
                    })
                    .error(function (data) {
                        deferred.reject(data);
                    });
                return deferred.promise;
            });
    };


    $scope.toBandInfo = "";
    $scope.bandmacAddress = function (macAddress, ipAddress) {
        $scope.toBandInfo = "    MAC地址：" + macAddress + "      IP地址：" + ipAddress;
        $scope.toBandMacAddress = macAddress;
        $scope.toBandIpAddress = ipAddress;
    };

    $scope.getVoucherList = function () {
        var deferred = $q.defer();
        $http.get("../api/voucher-list")
            .success(function (data) {
                $scope.voucherlist = [];
                for (i = 0; i < data.length; i++) {
                    row = data[i];
                    if (row.type == 1) {
                        row["typeDesc"] = "纸币";
                    }
                    else {
                        row["typeDesc"] = "硬币"
                    }
                    $scope.voucherlist.push(row);
                }
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };
    $scope.getVoucherList();

    $scope.voucherTypes = [
        {"desc": "完整券（原封劵）", idx: 1, "typeDesc": "完整券"},
        {"desc": "完整券（已清分）", idx: 2, "typeDesc": "完整券"},
        {"desc": "完整券（未清分）", idx: 3, "typeDesc": "完整券"},
        {"desc": "残损券（已复点）", idx: 4, "typeDesc": "残损券"},
        {"desc": "残损券（未复点）", idx: 5, "typeDesc": "残损券"}
    ];

    $scope.screnBand = function (band) {
        if ($scope.toBandMacAddress == null || $scope.toBandMacAddress == undefined) {
            swal({
                title: "请选择要绑定的屏幕！",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
            return;
        }
        if (band == null || band.voucher == null || band.voucher == undefined) {
            swal({
                title: "请选择要绑定的券别！",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
            return;
        }
        if (band == null || band.type == null || band.type == undefined) {
            swal({
                title: "请选择要绑定的券别类型！",
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
            url: '../api/band-screen',
            data: {
                "macAddress": $scope.toBandMacAddress,
                "voucherUid": band.voucher.uId,
                "type": band.type.idx,
                "bandStockInfo": "【" + band.voucher.name + "】 " + band.type.desc,
                "voucherAmount":band.voucher.amount,
                "voucherType":band.voucher.type
            }
        }).success(function (req) {
            if (req.code == 1) {
                $scope.screenList = req.screenPos;
                swal("绑定成功！", "", "success");
            }
            else {
                swal("绑定失败！", "", "warning");
            }
        })
    };


}]);


