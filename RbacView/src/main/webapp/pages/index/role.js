rbacViewApp.controller("roleCtrl", function ($scope, $compile) {
    // 要查找的角色名称
    $scope.roleWhere = '';
    // 当前面角色数据
    $scope.rolePageData = {};

    // 添加或编辑角色
    $scope.editRole = function (isAdd, roleInfo) {
        var _title = "";
        var _tips = "";
        var method;
        $scope.editRoleInfo = {};
        if (isAdd) {
            $scope.editRoleInfo.id = '';
            $scope.editRoleInfo.name = '';
            $scope.editRoleInfo.description = '';

            method = 'post';
            _title = "添加角色";
            _tips = "添加角色成功";
        } else {
            $scope.editRoleInfo.id = roleInfo.id;
            $scope.editRoleInfo.name = roleInfo.name;
            $scope.editRoleInfo.description = roleInfo.description;

            method = 'put';
            _title = "编辑角色";
            _tips = "更新角色成功";
        }

        var du = layer.open({
            type: 1,
            scrollbar: false,
            skin: "layer-ext-moon",
            area: ["500px", "450px"],
            title: "修改当前记录信息",
            shadeClose: false,
            btn: ["确认", "取消"],
            content: layui.jquery("#editRole"),
            yes: function () {
                if (!$scope.editRoleInfo.name) {
                    layer.msg("请输入角色名称", {
                        icon: 2,
                        time: 1000
                    });
                    return false;
                }
                Tmsp('/rbacs/role', method, $scope.editRoleInfo).then(function (rlt) {
                    if (isAdd) {
                        $scope.getRoleList(false);
                    } else {
                        roleInfo.id = $scope.editRoleInfo.id;
                        roleInfo.name = $scope.editRoleInfo.name;
                        roleInfo.description = $scope.editRoleInfo.description;
                        $scope.$apply();
                    }
                    layer.close(du);
                    layer.msg(_tips, {
                        icon: 1,
                        time: 1000
                    });
                }, function (reason) {
                    layer.msg(reason, {
                        icon: 2,
                        time: 1000
                    });
                });
            }
        });
    };
    // 获取角色列表
    $scope.getRoleList = function (page) {
        if (!page) {
            page = $scope.rolePageData.pageNum;
        }
        Tmsp('/rbacs/role?name='+$scope.roleWhere+'&page=' + page + '&pageSize=10', 'get').then(function (rlt) {
            $scope.rolePageData = rlt.data;
            console.log($scope.rolePageData);
            $scope.$apply();
        }, function (reason) {
            console.log(reason);
        });
    }
    $scope.getRoleList(1);
    // 删除角色
    $scope.delRole = function (roleId) {
        if (StringUtils.isBlank(roleId)) {
            console.log('role id null');
            return;
        }
        var dlgIndex = layer.confirm(
            "确定要删除？", {
                btn: ["确定", "取消"] //按钮
            },
            function () {
                Tmsp('/rbacs/role?id='+roleId, 'delete', $scope.editRoleInfo).then(function (rlt) {
                    $scope.getRoleList(false);
                    layer.close(dlgIndex);
                    layer.msg('删除角色成功', {
                        icon: 1,
                        time: 1000
                    });
                }, function (reason) {
                    layer.msg(reason, {
                        icon: 2,
                        time: 1000
                    });
                });
            }
        );
    };
    /////////////////////////////////// 用户相关 ///////////////////////////////////
    // 当前操作的角色id
    $scope.curRoleId = '';
    $scope.roleUsers = null;
    // 角色用户编辑
    $scope.editRoleMembers = function (roleId) {
        $scope.curRoleId = roleId;
        $scope.getUserByRoleId(1);
        $scope.getUnauthUserByRoleId(1);

        var layerDlg = layer.open({
            type: 1,
            scrollbar: false,
            skin: "layer-ext-moon",
            area: ["900px", "750px"],
            title: "角色用户列表",
            shadeClose: true,
            btn: ["关闭"],
            cancel: function() {
                layer.close(layerDlg);
            },
            content: layui.jquery("#roleMembersDlg")
        });
    };
    $scope.getUserByRoleId = function (page) {
        Tmsp('/rbacs/role/members?page=' + page + '&pageSize=10&roleId='+$scope.curRoleId, 'get').then(function (rlt) {
            $scope.roleUsers = rlt.data;
            $scope.$apply();
        }, function (reason) {
            console.log(reason);
        });
    }
    // 角色中的用户选中状态变更
    $scope.updateRoleUser = function($event, userId) {
        var userIndex = ListUtils.getIndex($scope.roleUsers.list, 'id', userId);
        if (-1 == userIndex) {
          return;
        }
        var checkbox = $event.target;
        $scope.roleUsers.list[userIndex].checked = checkbox.checked;
    }
    // 角色未设置的用户列表
    $scope.unauthRoleUsers = {};
    // 角色未设置的用户列表
    $scope.getUnauthUserByRoleId = function (page) {
        Tmsp('/rbacs/role/unauth/members?page=' + page + '&pageSize=10&roleId='+$scope.curRoleId, 'get').then(function (rlt) {
            $scope.unauthRoleUsers = rlt.data;
            $scope.$apply();
        }, function (reason) {
            console.log(reason);
        });
    }
    // 未设置的用户选中状态变更
    $scope.updateSrcUser = function($event, userId) {
        var userIndex = ListUtils.getIndex($scope.unauthRoleUsers.list, 'id', userId);
        if (-1 == userIndex) {
          return;
        }
        var checkbox = $event.target;
        $scope.unauthRoleUsers.list[userIndex].checked = checkbox.checked;
    }
    /// 修改角色中的用户
    $scope.updateRoleUsers = function(isAdd) {
        var updateParam = {userIds:'', roleId:$scope.curRoleId};
        var userList;
        var method = '';
        var url = '/rbacs/role/members';
        if (isAdd) {
            method = 'post';
            userList = $scope.unauthRoleUsers.list;
            for(var i in userList) {
                if (userList[i].checked) {
                    updateParam.userIds += (','+userList[i].id);
                }
            }
        } else {
            method = 'delete';
            userList = $scope.roleUsers.list;
            for(var i in userList) {
                if (userList[i].checked) {
                    updateParam.userIds += (','+userList[i].id);
                }
            }
        }
        if ('' != updateParam.userIds) {
            updateParam.userIds = updateParam.userIds.slice(1);
            if (!isAdd) {
                url += '?roleId='+updateParam.roleId+'&userIds='+updateParam.userIds;
                updateParam = null;
            }
            Tmsp(url, method, updateParam).then(function (rlt) {
                $scope.getUnauthUserByRoleId($scope.unauthRoleUsers.pageNum);
                $scope.getUserByRoleId($scope.roleUsers.pageNum);
            }, function (reason) {
                layer.msg(reason, {
                    icon: 2,
                    time: 1000
                });
            });
        }
    }
    ////////////////////////////////////// 资源相关 /////////////////////////////////////////////////////////
    /// 角色资源编辑
    $scope.editRoleRes = function (roleId) {
        $scope.curRoleId = roleId;
        $scope.getResByRoleId(1);
        $scope.getUnauthResByRoleId(1);

        layer.open({
            type: 1,
            scrollbar: false,
            area: ["900px", "500px"], //宽高
            title: "角色资源列表",
            shadeClose: true, //开启遮罩关闭
            btn: ["关闭"],
            content: layui.jquery("#roleResDlg")
        });
    };
    // 角色的资源列表
    $scope.roleRes = null;
    $scope.getResByRoleId = function (page) {
        Tmsp('/rbacs/role/resources?page=' + page + '&pageSize=10&roleId='+$scope.curRoleId, 'get').then(function (rlt) {
            $scope.roleRes = rlt.data;
            $scope.$apply();
        }, function (reason) {
            console.log(reason);
        });
    }
    // 角色中的资源选中状态变更
    $scope.updateAuthRes = function($event, userId) {
        var resIndex = ListUtils.getIndex($scope.roleRes.list, 'id', userId);
        if (-1 == resIndex) {
          return;
        }
        var checkbox = $event.target;
        $scope.roleRes.list[resIndex].checked = checkbox.checked;
    }
    // 角色未设置的资源列表
    $scope.unauthRoleRes = {};
    // 角色未设置的资源列表
    $scope.getUnauthResByRoleId = function (page) {
        Tmsp('/rbacs/role/unauth/resources?page=' + page + '&pageSize=10&roleId='+$scope.curRoleId, 'get').then(function (rlt) {
            $scope.unauthRoleRes = rlt.data;
            $scope.$apply();
        }, function (reason) {
            console.log(reason);
        });
    }
    // 未设置的资源选中状态变更
    $scope.updateUnauthRes = function($event, userId) {
        var resIndex = ListUtils.getIndex($scope.unauthRoleRes.list, 'id', userId);
        if (-1 == resIndex) {
          return;
        }
        var checkbox = $event.target;
        $scope.unauthRoleRes.list[resIndex].checked = checkbox.checked;
    }
    /// 修改角色中的资源
    $scope.updateRoleRes = function(isAdd) {
        var updateParam = {resIds:'', roleId:$scope.curRoleId};
        var userList;
        var method = '';
        var url = '/rbacs/role/resources';
        if (isAdd) {
            method = 'post';
            userList = $scope.unauthRoleRes.list;
            for(var i in userList) {
                if (userList[i].checked) {
                    updateParam.resIds += (','+userList[i].id);
                }
            }
        } else {
            method = 'delete';
            userList = $scope.roleRes.list;
            for(var i in userList) {
                if (userList[i].checked) {
                    updateParam.resIds += (','+userList[i].id);
                }
            }
        }
        if ('' != updateParam.resIds) {
            updateParam.resIds = updateParam.resIds.slice(1);
            if (!isAdd) {
                url += '?roleId='+updateParam.roleId+'&resIds='+updateParam.resIds;
                updateParam = null;
            }
            Tmsp(url, method, updateParam).then(function (rlt) {
                $scope.getUnauthResByRoleId($scope.unauthRoleRes.pageNum);
                $scope.getResByRoleId($scope.roleRes.pageNum);
            }, function (reason) {
                layer.msg(reason, {
                    icon: 2,
                    time: 1000
                });
            });
        }
    }
});