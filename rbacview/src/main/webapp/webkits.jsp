<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--[if lt IE 10]>
<script src="${pageContext.request.contextPath}/webkits/ielt10/json2.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/html5.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/respond.js.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/es5-shim.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/ielt10/es5-sham.min.js"></script>
<![endif]-->
<script>window.URI_DOMAIN = "${pageContext.request.contextPath}"</script>
<script>
    window.sessionUserInfo = {
        id:"${userInfo.id}",
        nickname:"${userInfo.xm}"
    };
    window.SERVICE = {
        RBACS: '/rbacs'
    };
</script>

<script src="${pageContext.request.contextPath}/webkits/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/webkits/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/jquery/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/vue/vue.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/vue/vue-router.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/vue/vue-bogies.js"></script>
<script src="${pageContext.request.contextPath}/webkits/moment-with-locales.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/promise-6.1.0.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/bluebird.core.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/base64.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/md5.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/string_utils.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/list_utils.min.js"></script>
<script src="${pageContext.request.contextPath}/webkits/tmsp.js?v=8"></script>
<script src="${pageContext.request.contextPath}/webkits/loading/jquery.loading.min.js"></script>

<link href="${pageContext.request.contextPath}/webkits/loading/jquery.loading.min.css?v=2" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/webkits/layui/css/layui.css">
