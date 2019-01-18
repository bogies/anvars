<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" >
<head>
<title>${webConfig.TITLE}</title>
<%@ include file="../../webkits.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layui/layui/css/layui.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1">
</head>
<body ng-app="rbacViewApp">

<div style="height: 50px;width: 100%;background: #1E9FFF">
    <div style="line-height: 50px;font-size: 24px;color: #fff;padding-left: 30px">权限管理系统</div>
</div>
<%@ include file="../../webkits.jsp" %>
</body>

<!-- 左菜单栏 -->
<div id="bd">
  <div id="mainside" style="background:#f0f9fd;height: 100%">
    <ul class="leftmenu layui-nav layui-nav-tree" lay-filter="test">
<!-- 侧边导航: <ul class="layui-nav layui-nav-tree layui-nav-side"> -->
      <li class="layui-nav-item layui-nav-itemed">
        <a href="javascript:;">管理信息</a>
        <dl class="layui-nav-child">
          <li ng-class="{'resourcesMgr': 'active'}[activeTable]"><a href="javascript:void(0)" ui-sref="resources">资源管理</a></li>
          <li ng-class="{'roleMgr': 'active'}[activeTable]"><a href="javascript:void(0)" ui-sref="role">角色管理</a></li>
          <li ng-class="{'userMgr': 'active'}[activeTable]"><a href="javascript:void(0)" ui-sref="user">用户管理</a></li>
        </dl>
      </li>
    </ul>
  </div>

<div ui-view></div>
</div>
</body>
<%@ include file="../webkits.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/webkits/zTree/css/zTreeStyle/zTreeStyle.css">
<script src="${pageContext.request.contextPath}/webkits/zTree/js/jquery.ztree.all.min.js"></script>

<script src="${pageContext.request.contextPath}/pages/index/route.js?v=9"></script>
<script src="${pageContext.request.contextPath}/pages/index/resources.js?v=9"></script>
<script src="${pageContext.request.contextPath}/pages/index/role.js?v=5"></script>
<script src="${pageContext.request.contextPath}/pages/index/member.js?v=5"></script>
</html>