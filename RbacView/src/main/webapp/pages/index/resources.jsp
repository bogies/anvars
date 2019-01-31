<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="mainright">
	<div id="resourceDetailInfo" class="pull-left" style="border-left: 2px solid #ccc;height: 100%;padding:20px;">
		<div style="text-align: left;padding-bottom: 20px;width: 100%">
			<a href="javascript:void(0)" ng-click="openAdd()" class="layui-btn layui-btn-warm layui-btn-sm" style="width: 100px;">新增</a>
		</div>
		<form>
			接口地址：<input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="searchX.path" />
			请求方式：<input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="searchX.reqMethod" />
			接口概要：<input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="searchX.summary" />
			服务名：<input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="searchX.servicesName" />
			<a href="javascript:void(0)" ng-click="refreshPage(1)" class="tablelink">搜索</a>
			<a href="javascript:void(0)" ng-click="clearSearch()" class="tablelink">清空</a>
		</form>
		<table class="tablelist">
			<thead>
				<tr>
				<th width="150">接口类型</th>
				<th width="150">接口地址</th>
				<th width="150">请求方式</th>
				<th width="300">接口概要</th>
				<th width="150">服务名</th>
				<th width="150">操作</th>
				<th width="150" style="display: none">接口详细说明</th>
			</tr>
			</thead>
			<tbody>
				<tr ng-repeat="x in resourcesList.list">
					<td>{{x.type}}</td>
					<td>{{x.path}}</td>
					<td>{{x.reqMethod}}</td>
					<td>{{x.summary}}</td>
					<td>{{x.servicesName}}</td>
					<td>
						<a href="javascript:void(0)" ng-click="openEdit(x)" class="tablelink">修改</a>
						<a href="javascript:void(0)" ng-click="deleteRecord(x.id)" class="tablelink">删除</a>
					</td>
					<td style="display: none">{{x.description}}</td>
				</tr>

			</tbody>
		</table>
		<ng-pagehelper-bar pagebar-html="${pageContext.request.contextPath}/i/pagebar-simple.html" goto-page="refreshPage" page-info="resourcesList"></ng-pagehelper-bar>
	</div>

<!-- 弹框隐藏页 -->
	<div id="insertRecord"  style="width:500px;display: none;z-index:9999999999;position: fixed">
		<div style="margin-top:10px;"></div>
		<table style="width: 100%" class="tablelist">
			<tr>
				<th style="height: 40px">接口类型：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.type" /></td>
			</tr>
			<tr>
				<th style="height: 40px">接口地址：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.path" /></td>
			</tr>
			<tr>
				<th style="height: 40px">请求方式：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.reqMethod" /></td>
			</tr>
			<tr>
				<th style="height: 40px">接口概要：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.summary" /></td>
			</tr>
			<tr>
				<th style="height: 40px">服务名(区分大小写)：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="addX.servicesName" /></td>
			</tr>
			<tr>
				<th style="height: 40px">接口详细说明：</th><td><textarea type="text" style="width: 200px;border:1px solid #ccc;height: 60px;" ng-model="addX.description" /></td>
			</tr>
		</table>
	</div>
	<div id="updateRecord"  style="width:500px;display: none;z-index:9999999999;position: fixed">
		<div style="margin-top:10px;"></div>
		<table style="width: 100%" class="tablelist">
			<tr>
				<th style="height: 40px">接口类型：</th><td>{{editX.type}}</td>
			</tr>
			<tr>
				<th style="height: 40px">接口地址：</th><td>{{editX.path}}</td>
			</tr>
			<tr>
				<th style="height: 40px">请求方式：</th><td>{{editX.reqMethod}}</td>
			</tr>
			<tr>
				<th style="height: 40px">接口概要：</th>
				<td><input type="text" style="width: 200px;border:1px solid #ccc;height: 25px;" ng-model="editX.summary" /></td>
			</tr>
			<tr>
				<th style="height: 40px">服务名：</th><td>{{editX.servicesName}}</td>
			</tr>
			<tr>
				<th style="height: 40px">接口详细说明：</th><td><textarea type="text" style="width: 200px;border:1px solid #ccc;height: 60px;" ng-model="editX.description" /></td>
			</tr>
		</table>
	</div>
</div>