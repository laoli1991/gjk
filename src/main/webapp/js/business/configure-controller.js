var app = angular.module("configure.controller", ["ngTable", "ui.bootstrap"]);

app.controller("configureCtrl", ["$scope", "$http", "NgTableParams", "$q", function ($scope, $http, NgTableParams, $q) {
    $scope.tab1 = true;
    $scope.tab2 = false;

    $scope.tabShow = function (k) {
        $scope.showXiang2Kun = false;
        $scope.showDai2Kun = false;
        $scope.showKun2Ba = false;
        $scope.showBa2Zhang = false;
        $scope.showXiang2He = false;
        $scope.showHe2Mei = false;
        $scope.xiang2Kun = undefined;
        $scope.dai2Kun = undefined;
        $scope.kun2Ba = undefined;
        $scope.ba2Zhang = undefined;
        $scope.xiang2He = undefined;
        $scope.he2Mei = undefined;
        $scope.voucherPo = undefined;
        if (k == 1) {
            $scope.getVoucherList();
            $scope.tab1 = true;
            $scope.tab2 = false;
        }
        else if (k == 2) {
            $scope.tab1 = false;
            $scope.tab2 = true;
        }
    };

    $scope.vouchers = [
        {"desc": "纸币100元", "amount": 100, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币50元", "amount": 50, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币20元", "amount": 20, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币10元", "amount": 10, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币5元", "amount": 5, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币2元", "amount": 2, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币1元", "amount": 1, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币5角", "amount": 0.5, "type": 1, "typeDesc": "纸币"},
        {"desc": "纸币1角", "amount": 0.1, "type": 1, "typeDesc": "纸币"},
        {"desc": "硬币10元", "amount": 10, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币5元", "amount": 5, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币1元", "amount": 1, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币5角", "amount": 0.5, "type": 2, "typeDesc": "硬币"},
        {"desc": "硬币1角", "amount": 0.1, "type": 2, "typeDesc": "硬币"}];


    $scope.chgAddVoucher = function (voucherPo) {
        if (voucherPo.voucher.type == 1) {//纸币
            $scope.showXiang2Kun = true;
            $scope.showDai2Kun = true;
            $scope.showKun2Ba = true;
            $scope.showBa2Zhang = true;
            $scope.showXiang2He = false;
            $scope.showHe2Mei = false;

            if (voucherPo.voucher.amount == 0.1 || voucherPo.voucher.amount == 0.5) {
                voucherPo.xiang2Kun = 25;
                voucherPo.dai2Kun = 25;
            }
            else {
                voucherPo.xiang2Kun = 20;
                voucherPo.dai2Kun = 20;
            }
            voucherPo.kun2Ba = 10;
            voucherPo.ba2Zhang = 100;
            voucherPo.xiang2He = undefined;
            voucherPo.he2Mei = undefined;
        }
        else {//硬币
            $scope.showXiang2Kun = false;
            $scope.showDai2Kun = false;
            $scope.showKun2Ba = false;
            $scope.showBa2Zhang = false;
            $scope.showXiang2He = true;
            $scope.showHe2Mei = true;

            voucherPo.xiang2Kun = undefined;
            voucherPo.dai2Kun = undefined;
            voucherPo.kun2Ba = undefined;
            voucherPo.ba2Zhang = undefined;
            voucherPo.xiang2He = 10;
            if (voucherPo.voucher.amount == 0.1) {
                voucherPo.he2Mei = 500;
            }
            else {
                voucherPo.he2Mei = 400;
            }
        }
    };

    $scope.addVoucher = function (voucherPo) {
        console.info(voucherPo);
        if (voucherPo == null || voucherPo == undefined || voucherPo.voucherName == undefined) {
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
        if (voucherPo.voucher == null || voucherPo.voucher == undefined) {
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

        if (voucherPo.voucher.type == 1) {//纸币
            if (voucherPo.xiang2Kun == null || voucherPo.xiang2Kun == undefined || voucherPo.xiang2Kun.length == 0 || Number(voucherPo.xiang2Kun) < 0 ||
                voucherPo.dai2Kun == null || voucherPo.dai2Kun == undefined || voucherPo.dai2Kun.length == 0 || Number(voucherPo.dai2Kun) < 0 ||
                voucherPo.kun2Ba == null || voucherPo.kun2Ba == undefined || voucherPo.kun2Ba.length == 0 || Number(voucherPo.kun2Ba) < 0 ||
                voucherPo.ba2Zhang == null || voucherPo.ba2Zhang == undefined || voucherPo.ba2Zhang.length == 0 || Number(voucherPo.ba2Zhang) < 0) {
                swal({
                    title: "数字必须大于0",
                    text: "",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "关闭",
                    closeOnConfirm: false
                });
                return;
            }
        }
        else {//硬币
            if (voucherPo.xiang2He == null || voucherPo.xiang2He == undefined || voucherPo.xiang2He.length == 0 ||Number(voucherPo.xiang2He) < 0 ||
                voucherPo.he2Mei == null || voucherPo.he2Mei == undefined || voucherPo.he2Mei.length == 0 || Number(voucherPo.he2Mei) < 0 ) {
                swal({
                    title: "数字必须大于0",
                    text: "",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "关闭",
                    closeOnConfirm: false
                });
                return;
            }
        }
        $http({
            method: 'post',
            url: '../api/add-voucher',
            data: {
                "desc": voucherPo.desc,
                "amount": voucherPo.voucher.amount,
                "type": voucherPo.voucher.type,
                "typeDesc": voucherPo.voucher.typeDesc,
                "name": voucherPo.voucherName,
                "xiang2Kun": voucherPo.xiang2Kun,
                "dai2Kun": voucherPo.dai2Kun,
                "kun2Ba": voucherPo.kun2Ba,
                "ba2Zhang": voucherPo.ba2Zhang,
                "xiang2He": voucherPo.xiang2He,
                "he2Mei": voucherPo.he2Mei,
                "desc":voucherPo.voucher.desc
            }
        }).success(function (req) {
            if (req == 1) {
                swal("增加成功！", "", "success");
            }
            else {
                swal("增加失败！", "", "warning");
            }
        })

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


    $scope.mustInt = function clearNoNum(obj, attr) {
        if (obj[attr] != undefined) {
            obj[attr] = obj[attr].replace(/[^\d]/g, "");  //清除“数字”和“.”以外的字符
        }
    };


}]);

app.filter('to_trusted', ['$sce', function($sce){
    return function(text) {
        return $sce.trustAsHtml(text);
    };
}]);


