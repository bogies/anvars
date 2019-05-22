var memberTpl = {
    template: '#members-template',
    data: function () {
        return {
            // 当前操作的用户id
            curUserId: '', 
            // 查找参数
            searchParam: {
                username: '',
                xm: '', 
                dwmc: '',
                bmmc: ''
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
            }
        };
    },
    methods: {
        refreshPage: function (page) {
            var self = this;
            var p = new tmspParams();
            p.setService(SERVICE.RBACS);
            p.setUrl('/member?page=' + page + '&pageSize=10&username=' + this.searchParam.username +
            "&nickname=" + this.searchParam.nickname);
            p.setLoading({el: $("body")});
            Tmsp(p).then(function (result) {
                console.log(result);
                self.memberList = result.data;
            }, function (error) {
                console.log(error);
            });
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
            var p = new tmspParams();
            p.setService(SERVICE.RBACS);
            p.setUrl('/role/byuserid?page='+page+'&pageSize=10&userId='+this.curUserId);
            p.setLoading({el: $("#userRolesDlg")});
			Tmsp(p).then(function (result) {
				if (result.code == 200) {
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
		}, 
		deleteUserFromRole: function(roleId) {
            var self = this;
            var p = new tmspParams();
            p.setService(SERVICE.RBACS);
            p.setUrl('/member?roleId='+roleId+'&userIds='+this.curUserId);
            p.setMethod('DELETE');
            p.setLoading({el: $("body")});

			Tmsp(p).then(function (result) {
				if (result.code == 200) {
                    ListUtils.remove(self.userInRoles.list, 'id', roleId);
                    if (self.userInRoles.list.length==0) {
                        self.userInRoles.total = 0;
                    }
					layer.msg("删除成功", {
						icon: 1,
						time: 1000
					});
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
    },
    mounted: function () {
        this.refreshPage(1)
    }
};