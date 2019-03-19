var memberTpl = {
    template: '#members-template',
    data: function () {
        return {
            // 当前操作的用户id
            curUserId: '', 
            // 查找参数
            searchParam: {
                username: '',
                nickname: ''
            },
            // 用户列表
            memberList: {
                total: 0,
                list: []
            },
            // 用户所属的角色列表
            userInRoles: {
                total: 0,
                list: []
            }, 
            // 当前编辑的用户信息
            editMemberInfo: {
                id: '',
                username: '',
                nickname: '',
                sort: '',
                createTime: '',
                updateTime: '',
                admin: 0,
                status: '1',
            }
        };
    },
    methods: {
        refreshPage: function (page) {
            var self = this;
            Tmsp('/rbacs/members?page=' + page + '&pageSize=10&username=' + this.searchParam.username +
                "&nickname=" + this.searchParam.nickname, 'get').then(function (result) {
                console.log(result);
                self.memberList = result.data;
            }, function (error) {
                console.log(error);
            });
        },
        openEditMemberDlg: function (memberInfo) {
            var dlgTitle;
            var tmspMethod;
            if (memberInfo) {
                dlgTitle = '编辑';
                tmspMethod = 'put';
                this.editMemberInfo.id = memberInfo.id;
                this.editMemberInfo.username = memberInfo.username;
                this.editMemberInfo.nickname = memberInfo.nickname;
                this.editMemberInfo.sort = memberInfo.sort;
                this.editMemberInfo.isAdmin = memberInfo.isAdmin;
                this.editMemberInfo.status = memberInfo.status;
            } else {
                dlgIndex = '添加';
                tmspMethod = 'post';
                this.editMemberInfo.id = '';
                this.editMemberInfo.username = '';
                this.editMemberInfo.nickname = '';
                this.editMemberInfo.sort = '';
                this.editMemberInfo.isAdmin = '0';
                this.editMemberInfo.status = '1';
            }
            var self = this;
            var dlgIndex = layer.open({
                type: 1,
                scrollbar: false,
                area: ["500px", "450px"],
                title: dlgTitle,
                shadeClose: false,
                btn: ["确认", "取消"],
                content: layui.jquery("#editUserInfoDlg"),
                yes: function () {
                    Tmsp('/rbacs/members', tmspMethod, this.editMemberInfo).then(function (result) {
                        if (result.code == 200) {
                            self.refreshPage(self.memberList.pageNum);
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
        deleteMember: function (resId) {
            var self = this;
            var dr = layer.confirm(
                "确定要删除？", {
                    btn: ["确定", "取消"] //按钮
                },
                function () {
                    Tmsp('/rbacs/members?id=' + resId, 'delete').then(function (result) {
                        if (result.code == 200) {
                            layer.msg("删除成功", {
                                icon: 1,
                                time: 1000
                            });
                            self.refreshPage(self.memberList.pageNum);
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
        clearSearch: function () {
            this.searchParam = {
                username: '',
                nickname: ''
            };
        }, 
        showRoles: function(page, userId) {
			if (userId) {
				this.curUserId = userId;
			}
			var self = this;
			Tmsp('/rbacs/members/roles?page='+page+'&pageSize=10&userId='+this.curUserId, 'GET').then(function (result) {
				if (result.code == 200) {
                    console.log(result);
					self.userInRoles = result.data;
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

			layer.open({
				type: 1,
				scrollbar: false,
				skin: "layer-ext-moon",
				area: ["500px", "450px"],
				title: '用户所属角色',
				shadeClose: false,
				btn: ["关闭"],
				content: layui.jquery("#userRolesDlg")
			});
		}
    },
    mounted: function () {
        this.refreshPage(1)
    }
};