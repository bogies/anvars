<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

<!--
	***********用户管理************
					Author:W.H.Media
-->
<div id="mainright">
<div style="margin:20px">
		<ul>
			<li class="click"><a class="layui-btn layui-btn-warm layui-btn-sm" href="javascript:void(0)" ng-click="addUser()">添加用户</a></li>
		</ul>
			<input type="text" class="input-sm" style="width:220px;margin-top:2px" ng-model="filter">
			<a href="javascript:void(0)" ng-click="getUsers(1)" class="btn btn-info" style="height:26px">搜索</a>
	<table class="layui-table">
		<thead>
			<tr >
			<th width="150">姓名</th>
			<th width="150">用户名</th>
			<th width="300">角色</th>
			<th width="150">是否管理员</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<tr ng-repeat="x in userList.list">
				<td>{{x.nickname}}</td>
				<td>{{getUsername(x)}}</td>
				<td>{{x.roleName}}</td>
				<td>{{getAdminText(x.admin)}}</td>
				<td>
					<a href="javascript:void(0)" ng-click="editUser(x)" class="tablelink"> 修改账号</a>
					<a href="javascript:void(0)" ng-click="initPassword(x.id)" class="tablelink"> 初始化密码</a>
				</td>
			</tr>

		</tbody>
	</table>
	<div class="pagin" id="usersPageBar" style="margin:15px"></div>
</div>

<!-- S弹出新增或修改账号信息 -->
<div id="editUser" style="display: none">
	<div style="margin:20px">
		<table class="table">
			<tr>
				<td width="100">姓名</td>
				<td>
	                <span ng-if="!isAdd()">
	                    {{editUserInfo.nickname}}
	                </span>
	                <input ng-if="isAdd()" type="text" style="width:200px" class="input-sm" ng-model="editUserInfo.nickname" />
	            </td>
			</tr>
			<tr>
				<td>账号</td>
				<td>
	                <span ng-if="!isAdd()">
	                    {{editUserInfo.username}}
	                </span>
	                <input ng-if="isAdd()" type="text" style="width:200px" class="input-sm" ng-model="editUserInfo.username" />
	            </td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" style="width:200px" class="input-sm" ng-model="editUserInfo.password" /></td>
			</tr>
			<tr ng-if="isEdit">
				<td>确认密码</td>
				<td><input type="password" style="width:200px" class="input-sm" ng-model="editUserInfo.confimPassword" /></td>
			</tr>
			<tr>
				<td>是否管理员</td>
				<td><input type="checkbox" ng-model="editUserInfo.admin" ng-checked="isCheckedAdmin()" /></td>
			</tr>
		</table>
		<div class="pull-right">
			<a href="javascript:void(0)" ng-click="saveUserInfo()" class="btn btn-info" style="width:80px;height:30px">保存</a>
			<a href="javascript:void(0)" ng-click="closeAdd()" class="btn btn-default" style="width:80px;height:30px">取消</a>
		</div>
	</div>
</div>
  <!-- E 弹出新增或修改账号信息 -->
</div>

<script>
  $('.tablelist tbody tr:odd').addClass('odd');
</script>