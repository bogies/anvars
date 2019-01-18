var rbacViewApp = angular.module('rbacViewApp', []);
rbacViewApp.controller('loginCtrl', function ($scope, $compile) {
    $scope.loginParam = {username:'', password:''};
    $scope.reqLogin = function() {
        $.ajax({
            url: URI_DOMAIN+'/s/login.jsd',
            type: 'POST',
            data: {username: $scope.loginParam.username, password: $scope.loginParam.password},
            dataType: 'JSON',
            beforeSend:function(req) {
                req.setRequestHeader('V-Req-Method', 'post');
                req.setRequestHeader('V-Req-Uri', '/rbacs/members/login');
            },
            success: function(result) {
                console.log(result);
                if (200 == result.code) {
                    window.location.href = URI_DOMAIN+'/v/index.html';
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(textStatus);
            }
        });
    }
});
