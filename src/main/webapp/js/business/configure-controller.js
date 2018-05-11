var app = angular.module("configure.controller", ["ngTable", "ui.bootstrap"]);

app.controller("configureCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {

    $scope.amounts = [{"desc": "100元", "amount": 100},
        {"desc": "50元", "amount": 50},
        {"desc": "20元", "amount": 20},
        {"desc": "10元", "amount": 10},
        {"desc": "5元", "amount": 50},
        {"desc": "2元", "amount": 2},
        {"desc": "1元", "amount": 1},
        {"desc": "5角", "amount": 0.5},
        {"desc": "2角", "amount": 0.2},
        {"desc": "1角", "amount": 0.1}]

    $scope.addVoucher = function (voucher, amount) {
        if (voucher == null || voucher == undefined || voucher.trim().length == 0) {
            swal({
                title: "卷别不能为空",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
        }
        if (amount == null || amount == undefined) {
            swal({
                title: "请选择金额",
                text: "",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "关闭",
                closeOnConfirm: false
            });
        }
        else {
            var deferred = $q.defer();
            $http.get("../api/addVoucher?voucher=" + voucher + "&amount=" + amount.amount)
                .success(function (data) {
                    console.log(data);
                    $scope.vouchers = data;
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
                $http.get("../api/removeVoucher?uId=" + voucher.uId)
                    .success(function (data) {
                        $scope.vouchers = data;
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

}]);


