var resourcesTpl = {
	template: '#resources-template',
	data: function () {
		return {
			/// 当前操作的资源id
			curResId: '', 
			/// 查找参数
			searchParam: {
				path: '',
				reqMethod: '',
				summary: '',
				servicesName: ''
			},
			/// 资源列表
			resourcesList: {
				total: 0,
				list: []
			},
			resInRoles: {
				total: 0, 
				list: []
			}, 
			/// 编辑资源参数
			editResInfo: {
				id: '',
				type: '',
				path: '',
				reqMethod: 'GET',
				summary: '',
				servicesName: '',
				pageName: '', 
				description: '', 
				extJson: ''
			}
		};
	},
	methods: {
		refreshPage: function (page) {
			var self = this;
			var p = new tmspParams();
            p.setService(SERVICE.RBACS);
            p.setUrl('/resources?page=' + page + '&pageSize=10&path=' + self.searchParam.path +
				"&reqMethod=" + self.searchParam.reqMethod + "&summary=" + self.searchParam.summary +
				"&servicesName=" + self.searchParam.servicesName);
			p.setLoading({el: $("body")});
			
			Tmsp(p).then(function (result) {
				console.log(result);
				self.resourcesList = result.data;
			}, function (error) {
				console.log(error);
			});
		},
		openEditDlg: function (resInfo) {
			var dlgTitle;
			var tmspMethod;
			if (resInfo) {
				dlgTitle = '编辑';
				tmspMethod = 'put';
				this.editResInfo.id = resInfo.id;
				this.editResInfo.type = resInfo.type;
				this.editResInfo.path = resInfo.path;
				this.editResInfo.reqMethod = resInfo.reqMethod.toUpperCase();
				this.editResInfo.summary = resInfo.summary;
				this.editResInfo.serviceName = resInfo.serviceName;
				this.editResInfo.pageName = resInfo.pageName;
				this.editResInfo.description = resInfo.description;
				this.editResInfo.extJson = resInfo.extJson;
			} else {
				dlgTitle = "添加";
				tmspMethod = 'post';
				this.editResInfo.id = '';
				this.editResInfo.type = '';
				this.editResInfo.path = '';
				this.editResInfo.reqMethod = 'GET';
				this.editResInfo.summary = '';
				this.editResInfo.serviceName = '';
				this.editResInfo.pageName = '';
				this.editResInfo.description = '';
				this.editResInfo.extJson = '';
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
				content: layui.jquery("#editResourceDlg"),
				yes: function () {
					var p = new tmspParams();
					p.setService(SERVICE.RBACS);
					p.setUrl('/resources');
					p.setMethod(tmspMethod);
					p.setData(self.editResInfo);
					p.setLoading({el: $("#editResourceDlg")});
					Tmsp(p).then(function (result) {
						if (result.code == 200) {
							self.refreshPage(self.resourcesList.pageNum);
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
		deleteRecord: function (resId) {
			var self = this;
			var dr = layer.confirm(
				"确定要删除？", {
					btn: ["确定", "取消"] //按钮
				},
				function () {
					var p = new tmspParams();
					p.setService(SERVICE.RBACS);
					p.setUrl('/resources?id=' + resId);
					p.setMethod('DELETE');
					p.setLoading({el: $("body")});

					Tmsp(p).then(function (result) {
						if (result.code == 200) {
							layer.msg("删除成功", {
								icon: 1,
								time: 1000
							});
							self.refreshPage(self.resourcesList.pageNum);
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
			this.searchParam.type = '';
			this.searchParam.path = '';
			this.searchParam.reqMethod = '';
			this.searchParam.summary = '';
			this.searchParam.servicesName = '';
			this.searchParam.description = '';
		}, 
		showRoles: function(page, resId) {
			if (resId) {
				this.curResId = resId;
			}
			var self = this;
			var p = new tmspParams();
			p.setService(SERVICE.RBACS);
			p.setUrl('/resources/roles?page='+page+'&pageSize=10&resId='+this.curResId);
			p.setLoading({el: $("#resRolesDlg")});
			Tmsp(p).then(function (result) {
				if (result.code == 200) {
					self.resInRoles = result.data;
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
				title: '资源所属角色',
				shadeClose: false,
				btn: ["关闭"],
				content: layui.jquery("#resRolesDlg")
			});
		}, 
		deleteResFromRole: function(roleId) {
			var self = this;
			var p = new tmspParams();
			p.setService(SERVICE.RBACS);
			p.setUrl('/resources?roleId='+roleId+'&resIds='+this.curResId);
			p.setMethod('DELETE');
			p.setLoading({el: $("body")});
			Tmsp(p).then(function (result) {
				if (result.code == 200) {
					ListUtils.remove(self.resInRoles.list, 'id', roleId);
					if (self.resInRoles.list.length==0) {
                        self.resInRoles.total = 0;
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