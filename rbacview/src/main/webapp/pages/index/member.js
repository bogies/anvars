var memberTpl = {
    template: '#members-template',
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
      openEditDlg: function(memberInfo) {
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
          skin: "layer-ext-moon",
          area: ["500px", "450px"],
          title: dlgTitle,
          shadeClose: false,
          btn: ["确认", "取消"],
          content: layui.jquery("#editUserInfoDlg"),
          yes: function() {
            Tmsp('/rbacs/members', tmspMethod, this.editMemberInfo).then(function(result){
              if (result.code==200) {
                self.refreshPage(self.memberList.pageNum);
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
      deleteRecord: function(resId) {
        var self = this;
        var dr = layer.confirm(
          "确定要删除？",
          {
            btn: ["确定", "取消"] //按钮
          },
          function() {
            Tmsp('/rbacs/members?id='+resId, 'delete').then(function(result){
              if (result.code==200) {
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
      
            }, function(error) {
                console.log(error);
            });
          }
        );
      },
      clearSearch: function() {
        this.searchParam = { username: '', nickname: '' };
      }
    }, 
    mounted: function (){
      this.refreshPage(1)
    }
  };