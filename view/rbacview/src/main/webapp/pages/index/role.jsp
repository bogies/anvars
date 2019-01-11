<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
  <div id="mainright">
    <div class="rightinfo">
      <div class="tools">
        <a class="layui-btn layui-btn-warm layui-btn-sm" href="javascript:void(0)" ng-click="editRole(true)">添加</a>

        <div class="pull-right" style="margin-top:10px;margin-right:20px">
          <input type="text" style="width:220px;margin-top:2px" ng-model="roleWhere">
          <a href="javascript:void(0)" ng-click="getRoleList(1)" style="height:26px">搜索</a>
        </div>
      </div>
      <table class="tablelist">
        <thead>
          <tr>
            <th width="150">角色名称</th>
            <th width="170">角色简述</th>
            <th width="170">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="x in rolePageData.list">
            <td>{{x.name}}</td>
            <td>{{x.description}}</td>
            <td>
              <a href="javascript:void(0)" ng-click="editRoleMembers(x.id)" class="tablelink" ng-if="x.id!='anonymous'">编辑用户</a>
              <a href="javascript:void(0)" ng-click="editRoleRes(x.id)" class="tablelink">编辑资源</a>
              <a href="javascript:void(0)" ng-click="editRole(false, x)" class="tablelink" ng-if="x.reserved!=1"> 编辑角色</a>
              <a href="javascript:void(0)" ng-click="delRole(x.id)" class="tablelink" ng-if="x.reserved!=1"> 删除角色</a>
            </td>
          </tr>
        </tbody>
      </table>
      <ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="getRoleList" page-info="rolePageData"></ng-pagehelper-bar>
    </div>

    <div id="editRole" style="display: none">
      <div style="padding:20px;">
        <table style="width: 100%">
          <tr>
            <th style="height: 40px">角色名:</th>
            <td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="editRoleInfo.name" /></td>
          </tr>
          <tr>
            <th style="height: 40px">角色描述:</th>
            <td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="editRoleInfo.description" /></td>
          </tr>
        </table>
      </div>
    </div>

    <div id="roleMembersDlg" style="display: none">
        <table class="table">
            <tr>
                <td width="340">
                    <div class="bw_title1">可选择的人员</div>
                    <div class="bw_content1" style="height: 200px;overflow-y: scroll;">
                        <table class="table" >
                            <thead>
                                <tr style="background: #d6dde4" class="text-center">
                                    <td width="50"><input type="checkbox" /></td>
                                    <td>账号</td>
                                    <td>姓名</td>
                                </tr>
                              </thead>
                              <tbody>
                                  <tr class="text-center" ng-repeat="x in unauthRoleUsers.list">
                                      <td width="50">
                                          <input type="checkbox" ng-checked="x.checked" ng-click="updateSrcUser($event,x.id)" />
                                      </td>
                                      <td>{{x.nickname}}</td>
                                      <td>{{x.username}}</td>
                                  </tr>
                              </tbody>
                        </table>
                        <ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="getUnauthUserByRoleId" page-info="unauthRoleUsers"></ng-pagehelper-bar>
                    </div>
                </td>
                <td width="80" class="text-center" style="vertical-align: middle;">
                    <a href="javascript:void(0)" ng-click="updateRoleUsers(true)" class="editbtn editbutton editmedium">>></a><br/>
                    <a href="javascript:void(0)" ng-click="updateRoleUsers(false)" class="editbtn editbutton editmedium"><<</a>
                </td>
                <td width="340">
                    <div class="bw_title1">已择人员</div>
                    <div class="bw_content1" style="height: 200px;overflow-y: scroll;">
                        <table class="table">
                          <thead>
                            <tr style="background: #d6dde4" class="text-center">
                                <td width="50"><input type="checkbox" /></td>
                                <td>账号</td>
                                <td>姓名</td>
                            </tr>
                          </thead>
                          <tbody>
                            <tr class="text-center" ng-repeat="x in roleUsers.list">
                                <td width="50">
                                    <input type="checkbox" ng-checked="x.checked" ng-click="updateRoleUser($event, x.id)" />
                                </td>
                                <td>{{x.nickname}}</td>
                                <td>{{x.username}}</td>
                            </tr>
                          </tbody>
                        </table>
                        <ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="getUserByRoleId" page-info="roleUsers"></ng-pagehelper-bar>
                    </div>
                </td>
            </tr>
        </table>

    </div>

    <div id="roleResDlg" style="display: none">
        <table class="table">
            <tr>
                <td width="340">
                    <div class="bw_title1">可选择的资源</div>
                    <div class="bw_content1" style="height: 380px;overflow-y: scroll;">
                        <table class="table" >
                            <thead>
                                <tr style="background: #d6dde4" class="text-center">
                                    <td width="50"><input type="checkbox" /></td>
                                    <td>地址</td>
                                    <td>方式</td>
                                    <td>服务</td>
                                </tr>
                              </thead>
                              <tbody>
                                  <tr class="text-center" ng-repeat="x in unauthRoleRes.list">
                                      <td width="50">
                                          <input type="checkbox" ng-checked="x.checked" ng-click="updateUnauthRes($event,x.id)" />
                                      </td>
                                      <td>{{x.path}}</td>
                                      <td>{{x.reqMethod}}</td>
                                      <td>{{x.servicesName}}</td>
                                  </tr>
                              </tbody>
                        </table>
                        <ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="getUnauthResByRoleId" page-info="unauthRoleRes"></ng-pagehelper-bar>
                    </div>
                </td>
                <td width="80" class="text-center" style="vertical-align: middle;">
                    <a href="javascript:void(0)" ng-click="updateRoleRes(true)" class="editbtn editbutton editmedium">>></a><br/>
                    <a href="javascript:void(0)" ng-click="updateRoleRes(false)" class="editbtn editbutton editmedium"><<</a>
                </td>
                <td width="340">
                    <div class="bw_title1">已选择的资源</div>
                    <div class="bw_content1" style="height: 380px;overflow-y: scroll;">
                        <table class="table" >
                            <thead>
                                <tr style="background: #d6dde4" class="text-center">
                                    <td width="50"><input type="checkbox" /></td>
                                    <td>地址</td>
                                    <td>方式</td>
                                    <td>服务</td>
                                </tr>
                              </thead>
                              <tbody>
                                  <tr class="text-center" ng-repeat="x in roleRes.list">
                                      <td width="50">
                                          <input type="checkbox" ng-checked="x.checked" ng-click="updateAuthRes($event,x.id)" />
                                      </td>
                                      <td>{{x.path}}</td>
                                      <td>{{x.reqMethod}}</td>
                                      <td>{{x.servicesName}}</td>
                                  </tr>
                              </tbody>
                        </table>
                        <ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="getResByRoleId" page-info="roleRes"></ng-pagehelper-bar>
                    </div>
                </td>
            </tr>
        </table>
    </div>
  </div>
  <script>
    $('.tablelist tbody tr:odd').addClass('odd');
  </script>