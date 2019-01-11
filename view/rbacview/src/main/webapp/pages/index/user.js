rbacViewApp.controller('userCtrl', function ($scope, $compile) {
    $scope.pageSize = PAGE_SIZE;
    $scope.resTree = null;
    $scope.curApp = {list:[], id:'0'};
    $scope.resNodes = [];
    $scope.activeTable = 'usernmgt';

    /// 当前查找的过滤条件
    /*$scope.filter = '';

    $scope.userList = {list:[]};

    $scope.getUsers = function(page) {
      var tmx = new Tmajax();
      var url = URI_ROOT + '/admin/user/list/'+page+'/'+$scope.pageSize;
      tmx.setDataTypeJson();
      tmx.setData('filter', Base64.encodeUrl($scope.filter));
      tmx.setSuccess(function(result) {console.log(result)
        if (tmx.checkCode(result.code)) {
            $scope.userList = result.data;
            ngPageHelperBar($compile, $scope, {pageInfo:'userList', target:$("#usersPageBar"), gotoPage:'getUsers'});
            $scope.$apply();
        }
      });
      tmx.request(url);
    }
    $scope.getUsers(1);
    /// 是否为管理员显示
    $scope.getAdminText = function(isAdmin) {
        if (1 == isAdmin) {
            return '是';
        } else {
            return '否';
        }
    }
    /// 用户名显示内容
    $scope.getUsername = function(userInfo) {
        if (userInfo.id == userInfo.username) {
            return '';
        } else {
            return userInfo.username;
        }
    }
    $scope.initEditUserInfo = function() {
        $scope.editUserInfo = {admin:0, confimPassword:'', password:''};
    }
    $scope.initEditUserInfo();

    $scope.addDlgIndex = 0;
    $scope.isEdit = false;
    $scope.addUser = function() {
        $scope.isEdit = false;
        $scope.initEditUserInfo();
        $scope.addDlgIndex = layer.open({
            type: 1,
            scrollbar:false,
            // skin: 'layer-ext-moon', //加上边框
            area: ['400px', '250px'], //宽高
            title:"用户信息",
            shadeClose: true, //开启遮罩关闭
            content: $('#editUser'),
        });
    };
    $scope.editUser = function(userInfo) {
        userInfo.password = '';
        userInfo.confimPassword = '';
        $scope.isEdit = true;
        $scope.editUserInfo = userInfo;
        $scope.addDlgIndex = layer.open({
            type: 1,
            scrollbar:false,
            area: ['600px', '350px'], //宽高
            title:"请选择时间",
            shadeClose: true, //开启遮罩关闭
            content: $('#editUser'),
        });
    };
    $scope.isCheckedAdmin = function() {
        return ($scope.editUserInfo.admin == 1);
    }
    
    /// 保存用户信息
    $scope.saveUserInfo = function() {
        var userInfo = $scope.editUserInfo;
        if (userInfo.admin) {
            userInfo.admin = 1;
        } else {
            userInfo.admin = 0;
        }
        var tmx = new Tmajax();
        var url = URI_ROOT + '/admin/user/';
        var message = '';
        if (isBlank(userInfo.id)) {
            url += '/add';
            message = '添加用户成功!';
        } else {
            url += '/update';
            message = '更新用户成功!';
            console.log(userInfo);
            if (userInfo.password != userInfo.confimPassword) {
                alert('两次输入的密码不相同!');
                return;
            }
        }
        tmx.setDataTypeJson();
        tmx.setData('userParam', Base64.encodeUrl(JSON.stringify(userInfo)));
        tmx.setSuccess(function(result) {
          if (tmx.checkCode(result.code)) {
              alert(message);
          } else {
            alert('错误:'+result.message);
          }
        });
        tmx.request(url, 'post');
    }
    /// 初始化用户密码
    $scope.initPassword = function(userId) {
        var tmx = new Tmajax();
        var url = URI_ROOT + '/admin/user/update';
        tmx.setDataTypeJson();
        tmx.setData('userParam', Base64.encodeUrl(JSON.stringify({id:userId,password:'111111'})));
        tmx.setSuccess(function(result) {
          if (tmx.checkCode(result.code)) {
              alert('初始化密码成功');
          } else {
            alert('初始化密码失败!'+result.message);
          }
        });
        tmx.request(url, 'post');
    }
    $scope.isAdd = function() {
        return isBlank($scope.editUserInfo.id);
    };
    $scope.closeAdd = function() {
        layer.close($scope.addDlgIndex);
    }*/
  });
  