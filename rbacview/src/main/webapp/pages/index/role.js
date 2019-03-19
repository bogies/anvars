var rolesTpl = {
    template: '#roles-template',
    data: function () {
        return {
            roleWhere: {
                roleName: ''
            },
            rolePageData: {
                total: 0,
                list: []
            },
            // 角色未设置的资源列表
            unauthRoleRes: {
                total: 0,
                list: []
            },
            // 角色未设置的用户列表
            unauthRoleUsers: {
                total: 0,
                list: []
            },
            roleRes: {
                total: 0,
                list: []
            },
            roleUsers: {
                total: 0,
                list: []
            },
            editRoleInfo: {
                id: '',
                username: '',
                nickname: '',
                sort: '',
                createTime: '',
                updateTime: '',
                isAdmin: 0,
                status: '1',
            },
            // 当前操作的角色id
            curRoleId: ''
        };
    },
    methods: {
        getRoleList: function (page) {
            var self = this;
            Tmsp('/rbacs/role?page=' + page + '&pageSize=10&name=' + this.roleWhere.roleName, 'get').then(function (result) {
                console.log(result);
                self.rolePageData = result.data;
            }, function (error) {
                console.log(error);
            });
        },
        openEditDlg: function (roleInfo) {
            var dlgTitle;
            var tmspMethod;
            if (roleInfo) {
                dlgTitle = '编辑';
                tmspMethod = 'put';
                this.editRoleInfo.id = roleInfo.id;
                this.editRoleInfo.name = roleInfo.name;
                this.editRoleInfo.description = roleInfo.description;
            } else {
                dlgIndex = '添加';
                tmspMethod = 'post';

                this.editRoleInfo.id = '';
                this.editRoleInfo.name = '';
                this.editRoleInfo.description = '';
            }
            var self = this;
            var dlgIndex = layer.open({
                type: 1,
                scrollbar: false,
                skin: "layer-ext-moon",
                area: ["500px", "450px"],
                title: dlgTitle,
                shadeClose: false,
                btn: ["确认", "取消"],
                content: layui.jquery("#editRoleDlg"),
                yes: function () {
                    if (self.editRoleInfo.name) {
                        layer.msg("请输入角色名称", {
                            icon: 2,
                            time: 1000
                        });
                        return false;
                    }

                    Tmsp('/rbacs/role', tmspMethod, this.editRoleInfo).then(function (result) {
                        if (result.code == 200) {
                            if (roleInfo) {
                                roleInfo.name = self.editRoleInfo.name;
                                roleInfo.description = self.editRoleInfo.description;
                            } else {
                                self.getRoleList(self.rolePageData.pageNum);
                            }
                            layer.close(dlgIndex);
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }

                    }, function (reason) {
                        layer.msg(reason.message, {
                            icon: 2,
                            time: 5000
                        });
                    });

                }
            });
        },
        deleteRole: function (resId) {
            if (StringUtils.isBlank(roleId)) {
                console.log('role\'s id is blank');
                return;
            }

            var self = this;
            var dr = layer.confirm(
                "确定要删除？", {
                    btn: ["确定", "取消"] //按钮
                },
                function () {
                    Tmsp('/rbacs/role?id=' + resId, 'delete').then(function (result) {
                        if (result.code == 200) {
                            layer.msg("删除成功", {
                                icon: 1,
                                time: 1000
                            });
                            self.getRoleList(self.rolePageData.pageNum);
                            layer.close(dr);
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }

                    }, function (error) {
                        console.log(error);
                    });
                }
            );
        },
        // 角色用户编辑
        editRoleMembers: function (roleId) {
            this.curRoleId = roleId;
            this.getUserByRoleId(1);
            this.getUnauthUserByRoleId(1);

            var layerDlg = layer.open({
                type: 1,
                scrollbar: false,
                skin: "layer-ext-moon",
                area: ["700px", "650px"],
                title: "角色用户列表",
                shadeClose: true,
                btn: ["关闭"],
                cancel: function () {
                    layer.close(layerDlg);
                },
                content: layui.jquery("#roleMembersDlg")
            });
        },
        getUserByRoleId: function (page) {
            Tmsp('/rbacs/role/members?page=' + page + '&pageSize=10&roleId=' + this.curRoleId, 'get').then(function (rlt) {
                roleUsers = rlt.data;

            }, function (reason) {
                console.log(reason);
            });
        },
        // 角色中的用户选中状态变更
        updateRoleUser: function ($event, userId) {
            var userIndex = ListUtils.getIndex(this.roleUsers.list, 'id', userId);
            if (-1 == userIndex) {
                return;
            }
            var checkbox = $event.target;
            this.roleUsers.list[userIndex].checked = checkbox.checked;
        },
        // 角色未设置的用户列表
        getUnauthUserByRoleId: function (page) {
            var self = this;
            Tmsp('/rbacs/role/unauth/members?page=' + page + '&pageSize=10&roleId=' + this.curRoleId, 'get').then(function (rlt) {
                self.unauthRoleUsers = rlt.data;

            }, function (reason) {
                console.log(reason);
            });
        },
        // 未设置的用户选中状态变更
        updateSrcUser: function ($event, userId) {
            var userIndex = ListUtils.getIndex(this.unauthRoleUsers.list, 'id', userId);
            if (-1 == userIndex) {
                return;
            }
            var checkbox = $event.target;
            this.unauthRoleUsers.list[userIndex].checked = checkbox.checked;
        },
        /// 修改角色中的用户
        updateRoleUsers: function (isAdd) {
            var updateParam = {
                userIds: '',
                roleId: this.curRoleId
            };
            var userList;
            var method = '';
            var url = '/rbacs/role/role';
            if (isAdd) {
                method = 'post';
                userList = this.unauthRoleUsers.list;
                for (var i in userList) {
                    if (userList[i].checked) {
                        updateParam.userIds += (',' + userList[i].id);
                    }
                }
            } else {
                method = 'delete';
                userList = this.roleUsers.list;
                for (var i in userList) {
                    if (userList[i].checked) {
                        updateParam.userIds += (',' + userList[i].id);
                    }
                }
            }
            if ('' != updateParam.userIds) {
                updateParam.userIds = updateParam.userIds.slice(1);
                if (!isAdd) {
                    url += '?roleId=' + updateParam.roleId + '&userIds=' + updateParam.userIds;
                    updateParam = null;
                }
                var self = this;
                Tmsp(url, method, updateParam).then(function (rlt) {
                    self.getUnauthUserByRoleId(self.unauthRoleUsers.pageNum);
                    self.getUserByRoleId(self.roleUsers.pageNum);
                }, function (reason) {
                    layer.msg(reason, {
                        icon: 2,
                        time: 1000
                    });
                });
            }
        },
        /////////// 资源相关 ///////////
        /// 角色资源编辑
        editRoleRes: function (roleId) {
            this.curRoleId = roleId;
            this.getResByRoleId(1);
            this.getUnauthResByRoleId(1);

            layer.open({
                type: 1,
                scrollbar: false,
                area: ["900px", "500px"], //宽高
                title: "角色资源列表",
                shadeClose: true, //开启遮罩关闭
                btn: ["关闭"],
                content: layui.jquery("#roleResDlg")
            });
        },
        // 角色的资源列表
        getResByRoleId: function (page) {
            var self = this;
            Tmsp('/rbacs/role/resources?page=' + page + '&pageSize=10&roleId=' + this.curRoleId, 'get').then(function (rlt) {
                self.roleRes = rlt.data;
            }, function (reason) {
                console.log(reason);
            });
        },
        // 角色中的资源选中状态变更
        updateAuthRes: function ($event, userId) {
            var resIndex = ListUtils.getIndex(this.roleRes.list, 'id', userId);
            if (-1 == resIndex) {
                return;
            }
            var checkbox = $event.target;
            this.roleRes.list[resIndex].checked = checkbox.checked;
        },
        // 角色未设置的资源列表
        getUnauthResByRoleId: function (page) {
            var self = this;
            Tmsp('/rbacs/role/unauth/resources?page=' + page + '&pageSize=10&roleId=' + this.curRoleId, 'get').then(function (rlt) {
                self.unauthRoleRes = rlt.data;
            }, function (reason) {
                console.log(reason);
            });
        },
        // 未设置的资源选中状态变更
        updateUnauthRes: function ($event, userId) {
            var resIndex = ListUtils.getIndex(this.unauthRoleRes.list, 'id', userId);
            if (-1 == resIndex) {
                return;
            }
            var checkbox = $event.target;
            this.unauthRoleRes.list[resIndex].checked = checkbox.checked;
        },
        /// 修改角色中的资源
        updateRoleRes: function (isAdd) {
            var updateParam = {
                resIds: '',
                roleId: this.curRoleId
            };
            var userList;
            var method = '';
            var url = '/rbacs/role/resources';
            if (isAdd) {
                method = 'post';
                userList = this.unauthRoleRes.list;
                for (var i in userList) {
                    if (userList[i].checked) {
                        updateParam.resIds += (',' + userList[i].id);
                    }
                }
            } else {
                method = 'delete';
                userList = this.roleRes.list;
                for (var i in userList) {
                    if (userList[i].checked) {
                        updateParam.resIds += (',' + userList[i].id);
                    }
                }
            }
            if ('' != updateParam.resIds) {
                updateParam.resIds = updateParam.resIds.slice(1);
                if (!isAdd) {
                    url += '?roleId=' + updateParam.roleId + '&resIds=' + updateParam.resIds;
                    updateParam = null;
                }
                var self = this;
                Tmsp(url, method, updateParam).then(function (rlt) {
                    if (200 == rlt.code) {
                        self.getUnauthResByRoleId(self.unauthRoleRes.pageNum);
                        self.getResByRoleId(self.roleRes.pageNum);
                    } else {
                        layer.msg(rlt.msg, {
                            icon: 1,
                            time: 1000
                        });
                    }
                }, function (reason) {
                    layer.msg(reason, {
                        icon: 2,
                        time: 1000
                    });
                });
            }
        },
        clearSearch: function () {
            this.searchParam = {
                username: '',
                nickname: ''
            };
        }
    },
    mounted: function () {
        this.getRoleList(1)
    }
};