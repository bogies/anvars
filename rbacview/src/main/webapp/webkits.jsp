<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Angularjs ui-route 兼容ie8,9 -->
<!--[if lt IE 10]>
<script src="${pageContext.request.contextPath}/webkits/ielt10/json2.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/html5.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/respond.js.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/es5-shim.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/es5-sham.min.js"></script>
<![endif]-->
<script>window.URI_DOMAIN = "${pageContext.request.contextPath}"</script>
<script>
    window.sessionUserInfo = {id:"${userInfo.id}",
    username:"${userInfo.username}",
    nickname:"${userInfo.nickname}",
    admin:"${userInfo.admin}"}
</script>
<script src="${pageContext.request.contextPath}/webkits/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/jquery/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/webkits/angular/angular.min.js?v=1"></script>
<script src="${pageContext.request.contextPath}/webkits/angular/angular-ui-router.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/angular/ants_directives.min.js?v=5"></script>
<script src="${pageContext.request.contextPath}/webkits/promise-6.1.0.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/bluebird.core.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/base64.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/md5.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/string_utils.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/list_utils.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/tmsp.js?v=8"></script>
<link href="${pageContext.request.contextPath}/webkits/loading/jquery.loading.min.css?v=2" rel="stylesheet">
<script src="${pageContext.request.contextPath}/webkits/loading/jquery.loading.min.js"></script>