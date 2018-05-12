var app = angular.module("configure.controller", ["ngTable", "ui.bootstrap"]);

app.controller("configureCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {

    $scope.vouchers = [
        {"desc": "纸币100元", "amount": 100, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币50元", "amount": 50, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币20元", "amount": 20, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币10元", "amount": 10, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币5元", "amount": 50, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币2元", "amount": 2, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币1元", "amount": 1, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币5角", "amount": 0.5, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币1角", "amount": 0.1, "type": 1, "typeDesc": "纸币"},
        {"desc": "硬币1元", "amount": 1, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币5角", "amount": 0.5, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币2角", "amount": 0.2, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币1角", "amount": 0.1, "type": 2, "typeDesc": "硬币"}]

    $scope.addVoucher = function (voucherName, voucher) {
        if (voucherName == null || voucherName == undefined || voucherName.trim().length == 0) {
            swal({
                title: "卷别不能为空",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
            return;
        }
        if (voucher == null || voucher == undefined) {
            swal({
                title: "请选择金额",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
            return;
        }
        else {
            var deferred = $q.defer();
            $http.get("../api/add-voucher?desc=" + voucher.desc + "&amount=" + voucher.amount + "&type=" + voucher.type+"&name=" + voucherName)
                .success(function (data) {
                    if(data.code == 1){
                        swal({
                            title: "已存在该卷别",
                            text: "",
                            type: "warning",
                            showCancelButton: false,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "关闭",
                            closeOnConfirm: false
                        });
                    }
                    else {
                        swal("添加成功！", "卷别【" + voucherName + "】已经添加", "success");
                    }
                    $scope.voucherList = data.voucherPos;
                    deferred.resolve(data);
                })
                .error(function (data) {
                    deferred.reject(data);
                });
            return deferred.promise;
        }
    };

    $scope.removeVoucher = function (voucher) {
        swal({
                title: "确定删除吗？",
                text: "你将无法恢复该卷别！",
                type: "warning",
                cancelButtonText: '取消',
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                var deferred = $q.defer();
                $http.get("../api/remove-voucher?uId=" + voucher.uId)
                    .success(function (data) {
                        $scope.voucherList = data;
                        deferred.resolve(data);
                        swal("删除！", "卷别【" + voucher.name + "】已经被删除", "success");
                    })
                    .error(function (data) {
                        deferred.reject(data);
                    });
                return deferred.promise;
            });
    };

    $scope.getVoucherList = function () {
        var deferred = $q.defer();
        $http.get("../api/voucher-list")
            .success(function (data) {
                $scope.voucherList = data;
                deferred.resolve(data);
            })
            .error(function (data) {
                deferred.reject(data);
            });
        return deferred.promise;
    };

    $scope.getVoucherList();

}]);


