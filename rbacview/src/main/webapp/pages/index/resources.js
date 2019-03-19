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
				reqMethod: '',
				summary: '',
				servicesName: '',
				description: ''
			}
		};
	},
	methods: {
		refreshPage: function (page) {
			var self = this;
			Tmsp('/rbacs/resources?page=' + page + '&pageSize=10&path=' + self.searchParam.path +
				"&reqMethod=" + self.searchParam.reqMethod + "&summary=" + self.searchParam.summary +
				"&servicesName=" + self.searchParam.servicesName, 'get').then(function (result) {

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
				this.editResInfo.reqMethod = resInfo.reqMethod;
				this.editResInfo.summary = resInfo.summary;
				this.editResInfo.servicesName = resInfo.servicesName;
				this.editResInfo.description = resInfo.description;
			} else {
				dlgTitle = "添加";
				tmspMethod = 'post';
				this.editResInfo.id = '';
				this.editResInfo.type = '';
				this.editResInfo.path = '';
				this.editResInfo.reqMethod = '';
				this.editResInfo.summary = '';
				this.editResInfo.servicesName = '';
				this.editResInfo.description = '';
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
					Tmsp('/rbacs/resources', tmspMethod, self.editResInfo).then(function (result) {
						if (result.code == 200) {
							self.refreshPage(resourcesList.pageNum);
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
					Tmsp('/rbacs/resources?id=' + resId, 'delete').then(function (result) {
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
			Tmsp('/rbacs/role/incres?page='+page+'&pageSize=10&resId='+this.curResId, 'GET').then(function (result) {
				if (result.code == 200) {
					self.resInRoles = result.data;
					console.log(self.resInRoles);
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
		}
	},
	mounted: function () {
		this.refreshPage(1)
	}
};