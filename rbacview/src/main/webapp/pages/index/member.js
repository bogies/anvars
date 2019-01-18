rbacViewApp.controller('userCtrl', function ($scope, $compile) {

    // 新增接口信息
    $scope.openAdd = function() {

        var du = layer.open({
        type: 1,
        scrollbar: false,
        skin: "layer-ext-moon",
        area: ["500px", "450px"],
        title: "添加新的用户",
        shadeClose: false,
        btn: ["确认", "取消"],
        content: $("#insertUser"),
        yes: function() {
            Tmsp('/rbacs/members', 'post',$scope.addX).then(function(result){
    
            if (result.code==200) {
                $scope.refreshPage($scope.memberList.pageNum);
                layer.close(du);
            } else {
                layer.msg(result.message, {
                icon: 2,
                time: 1000
                });
            }
    
            }, function(reason) {
                layer.msg(reason.message, {
                icon: 2,
                time: 5000
                });
            });
    
        }
        });
    };

  // 修改接口信息
  $scope.openEdit = function(curX) {
    $scope.editX = {};
    $scope.editX.id = curX.id;
    $scope.editX.username = curX.username;
    $scope.editX.nickname = curX.nickname;
    $scope.editX.sort = curX.sort;
    $scope.editX.create_time = curX.createTime;
    $scope.editX.update_time = curX.updateTime;
    $scope.editX.isAdmin = curX.isAdmin;
    $scope.editX.status = curX.status;
    var du = layer.open({
      type: 1,
      scrollbar: false,
      skin: "layer-ext-moon",
      area: ["500px", "450px"],
      title: "修改当前记录信息",
      shadeClose: false,
      btn: ["确认", "取消"],
      content: $("#updateRecord"),
      yes: function() {
        Tmsp('/rbacs/members', 'put',$scope.editX).then(function(result){
  
          if (result.code==200) {
            $scope.refreshPage($scope.memberList.pageNum);
            layer.close(du);
          } else {
            layer.msg(result.message, {
              icon: 2,
              time: 1000
            });
          }
  
        }, function(reason) {
            console.log(reason);
        });
      }
    });
  };
    /// 删除接口信息
    $scope.deleteRecord = function(x) {
        var dr = layer.confirm(
        "确定要删除？",
        {
            btn: ["确定", "取消"] //按钮
        },
        function() {
            Tmsp('/rbacs/members?id='+x, 'delete').then(function(result){
    
                if (result.code==200) {
                    layer.msg("删除成功", {
                        icon: 1,
                        time: 1000
                    });
                    $scope.refreshPage($scope.memberList.pageNum);
                    layer.close(dr);
                } else {
                    layer.msg(result.message, {
                    icon: 2,
                    time: 1000
                    });
                }
            }, function(error) {
                layer.msg(error.message, {
                    icon: 2,
                    time: 5000
                });
            });
        }
        );
    };
    $scope.clearSearch = function() {
        $scope.searchX.username='';
        $scope.searchX.nickname='';
    }
    $scope.searchX = {};
    /// 当前查找的过滤条件
    $scope.refreshPage = function(page) {
        Tmsp('/rbacs/members?page='+page+'&pageSize=10&username='+$scope.searchX.username+
        "&nickname="+$scope.searchX.nickname, 'get').then(function(result){

        console.log(result);
        $scope.memberList = result.data;
        $scope.$apply();
        }, function(error) {
            console.log(error);
        });
    }
    $scope.clearSearch();
    $scope.refreshPage(1);
  });
  