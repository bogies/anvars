<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="mainright">
	<div id="resourceDetailInfo" class="pull-left" style="border-left: 2px solid #ccc;height: 100%;padding:20px;">
		<div style="text-align: left;padding-bottom: 20px;width: 100%">
			<a href="javascript:void(0)" ng-click="openAdd()" class="layui-btn layui-btn-warm layui-btn-sm" style="width: 100px;">新增</a>
		</div>
		<form>
			用户名：<input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="searchX.username" />
			姓名：<input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="searchX.nickname" />
			<a href="javascript:void(0)" ng-click="refreshPage(1)" class="tablelink">搜索</a>
			<a href="javascript:void(0)" ng-click="clearSearch()" class="tablelink">清空</a>
		</form>
		<table class="tablelist">
			<thead>
				<tr>
					<th style="display: none">用户id</th>
					<th width="150">用户名</th>
					<th width="150">姓名</th>
					<th width="150">排序值</th>
					<th width="150">创建时间</th>
					<th width="300">修改时间</th>
					<th width="150">管理员权限</th>
					<th width="150">状态</th>
					<th width="150">操作</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="x in memberList.list">
					<td style="display: none">{{x.id}}</td>
					<td>{{x.username}}</td>
					<td>{{x.nickname}}</td>
					<td>{{x.sort}}</td>
					<td>{{x.createTime}}</td>
					<td>{{x.updateTime}}</td>
					<td>
						<p ng-if=x.isAdmin=='0'>普通用户 </p>
						<p ng-if=x.isAdmin=='1'>管理员 </p>
					</td>
					<td>	
						<p ng-if=x.status=='1'>正常 </p>
						<p ng-if=x.status=='2'>锁定 </p>
					</td>
					<td>
						<a href="javascript:void(0)" ng-click="openEdit(x)" class="tablelink">修改</a>
						<a href="javascript:void(0)" ng-click="deleteRecord(x.id)" class="tablelink">删除</a>
					</td>
				</tr>

			</tbody>
		</table>
		<ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="refreshPage" page-info="memberList"></ng-pagehelper-bar>
	</div>

<!-- 弹框隐藏页 -->
	<div id="insertUser"  style="width:500px;display: none;z-index:9999999999;position: fixed">
		<div style="margin-top:10px;"></div>
		<table style="width: 100%" class="tablelist">
			<tr>
				<th style="height: 40px">用户名：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.username" /></td>
			</tr>
			<tr>
				<th style="height: 40px">密码	：(。。。。。。。。。密码不为空，长度)</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.password" /></td>
			</tr>
			<tr>
				<th style="height: 40px">姓名：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.nickname" /></td>
			</tr>
			<tr>
				<th style="height: 40px">排序：(。。。。。。。。。只允许数字)</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.sort" /></td>
			</tr>
			<tr>
				<th style="height: 40px">管理员权限：</th>
				<td>
					<p ng-if=isAdmin=='0'>普通用户 </p>
					<p ng-if=isAdmin=='1'>
						<select ng-model="addX.isAdmin">
							<option value='0'>普通用户</option>
							<option value='1'>管理员</option>
						</select>
					</p>
				</td>
			</tr>
			<tr>
				<th style="height: 40px">状态：</th>
				<td>
					<select ng-model="addX.status">
						<option value='1'>正常</option>
						<option value='2'>锁定</option>
					</select>
				</td>
			</tr>
		</table>
	</div>			
	<div id="updateRecord"  style="width:500px;display: none;z-index:9999999999;position: fixed">
		<div style="margin-top:10px;"></div>
		<table style="width: 100%" class="tablelist">
			<tr>
				<th style="height: 40px">用户名：(。。。。。。。。。不为空，长度)	</th>
				<td>{{editX.username}}</td>
			</tr>
			<tr>
				<th style="height: 40px">密码	：(。。。。。。。。。密码不为空，长度)</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="editX.password" /></td>
			</tr>
			<tr>
				<th style="height: 40px">姓名：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="editX.nickname" /></td>
			</tr>
			<tr>
				<th style="height: 40px">排序：(。。。。。。。。。只允许数字)</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="editX.sort" /></td>
			</tr>
			<tr>
				<th style="height: 40px">创建时间：</th>
				<td>{{editX.create_time}}</td>
			</tr>
			<tr>
				<th style="height: 40px">修改时间：</th>
				<td>{{editX.update_time}}</td>
			</tr>
			<tr>
				<th style="height: 40px">管理员权限：</th>
				<td>	
					<p ng-if=isAdmin=='0'>普通用户 </p>
					<p ng-if=isAdmin=='1'>
						<select ng-model="addX.isAdmin">
							<option value='0'>普通用户</option>
							<option value='1'>管理员</option>
						</select>
					</p>
				</td>
			</tr>
			<tr>
				<th style="height: 40px">状态：</th>
				<td>
					<select ng-model="editX.status">
						<option value='1'>正常</option>
						<option value='2'>锁定</option>
					</select>
				</td>
			</tr>
		</table>
	</div>								
</div>