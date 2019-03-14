var memberTpl = {
    template: '#member-template',
    data: function() {
      return {
        searchParam: { username: '', nickname: '' }, 
        memberList: { total: 0, list:[] }, 
        editMemberInfo: { id: '', username: '', nickname: '', sort:'', createTime: '', updateTime: '', isAdmin: 0, status: '1',  }
      };
    }, 
    methods: {
      refreshPage: function(page) {
        var self = this;
        Tmsp('/rbacs/members?page='+page+'&pageSize=10&username='+this.searchParam.username+
        "&nickname="+this.searchParam.nickname, 'get').then(function(result) {
          console.log(result);
          self.memberList = result.data;
          }, function(error) {
            console.log(error);
          });
      }, 
      openEditDlg: function(isAdd) {
        var dlgTitle  = isAdd ? "添加" : '编辑';
        var self = this;
        var dlgIndex = layer.open({
          type: 1,
          scrollbar: false,
          skin: "layer-ext-moon",
          area: ["500px", "450px"],
          title: dlgTitle,
          shadeClose: false,
          btn: ["确认", "取消"],
          content: layui.jquery("#editResources"),
          yes: function() {
            Tmsp('/rbacs/resources', 'post',addX).then(function(result){
              if (result.code==200) {
                self.refreshPage(resourcesList.pageNum);
                layer.close(dlgIndex);
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
      }, 
      clearSearch: function() {
        
      }
    }, 
    mounted: function (){
      this.refreshPage(1)
    }
  };
/*rbacViewApp.controller('userCtrl', function ($scope, $compile) {
    isAdmin = {};
    isAdmin = sessionUserInfo.admin;
    // 新增接口信息
    openAdd = function() {
        var du = layer.open({
            type: 1,
            scrollbar: false,
            skin: "layer-ext-moon",
            area: ["500px", "450px"],
            title: "添加新的用户",
            shadeClose: true,
            btn: ["确认", "取消"],
            content: layui.jquery("#insertUser"),
            yes: function() {
                Tmsp('/rbacs/members', 'post',addX).then(function(result) {
                if (result.code==200) {
                    refreshPage(memberList.pageNum);
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
  openEdit = function(curX) {
    editX = {};
    editX.id = curX.id;
    editX.username = curX.username;
    editX.nickname = curX.nickname;
    editX.sort = curX.sort;
    editX.create_time = curX.createTime;
    editX.update_time = curX.updateTime;
    editX.isAdmin = curX.isAdmin;
    editX.status = curX.status;
    var du = layer.open({
      type: 1,
      scrollbar: false,
      skin: "layer-ext-moon",
      area: ["500px", "450px"],
      title: "修改当前记录信息",
      shadeClose: false,
      btn: ["确认", "取消"],
      content: layui.jquery("#updateRecord"),
      yes: function() {
        Tmsp('/rbacs/members', 'put',editX).then(function(result){

          if (result.code==200) {
            refreshPage(memberList.pageNum);
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
    deleteRecord = function(x) {
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
                    refreshPage(memberList.pageNum);
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
    clearSearch = function() {
        searchParam.username='';
        searchParam.nickname='';
    }
    searchParam = {};
    /// 当前查找的过滤条件
    refreshPage = function(page) {
        Tmsp('/rbacs/members?page='+page+'&pageSize=10&username='+searchParam.username+
        "&nickname="+searchParam.nickname, 'get').then(function(result){

        console.log(result);
        memberList = result.data;
        $apply();
        }, function(error) {
            console.log(error);
        });
    }
    clearSearch();
    refreshPage(1);
  });
*/