<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <html lang="en">

    <head>
      <title>${webConfig.TITLE}</title>
      <%@ include file="../../webkits.jsp" %>
    </head>

    <body>
      <div class="layui-container" style="margin-top:100px" id="rbac_login">
        <div class="layui-row">
          <div class="layui-col-xs4 layui-col-xs-offset4">
            <div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="">

              <div class="layadmin-user-login-main">
                <div class="layadmin-user-login-box layadmin-user-login-header">
                  <h2 style="text-align: center">AVars后台管理系统</h2>
                </div>
                <div class="layadmin-user-login-box layadmin-user-login-body layui-form" style="margin:10px 0 10px 0">
                  <div class="layui-form-item">

                    <input type="text" v-model="loginParam.username" lay-verify="required" placeholder="用户名" class="layui-input">
                  </div>
                  <div class="layui-form-item">

                    <input type="password" v-model="loginParam.password" lay-verify="required" placeholder="密码" class="layui-input">
                  </div>


                  <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit="" lay-filter="LAY-user-login-submit" v-on:click="reqLogin">登 入</button>
                  </div>

                </div>
              </div>

              <div class="layui-trans layadmin-user-login-footer">
                <p>© 2018
                  <a href="javascript:void(0)">AVars</a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--<div class="ladmin-user-login-theme">
    <script type="text/html" template>
      <ul>
        <li data-theme=""><img src="{{ layui.setter.base }}style/res/bg-none.jpg"></li>
        <li data-theme="#03152A" style="background-color: #03152A;"></li>
        <li data-theme="#2E241B" style="background-color: #2E241B;"></li>
        <li data-theme="#50314F" style="background-color: #50314F;"></li>
        <li data-theme="#344058" style="background-color: #344058;"></li>
        <li data-theme="#20222A" style="background-color: #20222A;"></li>
      </ul>
    </script>
  </div>-->






      <!-- <h1 class="text-center">账户登录1</h1>
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <div class="form-group">
        <label for="account">登录账号</label>
        <input type="text" class="form-control" v-model="loginParam.username" placeholder="输入登录账号">
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input type="password" class="form-control" v-model="loginParam.password" placeholder="输入密码">
      </div>
      <button type="button" v-on:click="reqLogin();" class="btn btn-default">登录</button>
    </div>
  </div> -->
      <script>
        window.returnUrl = "${returnUrl}";
      </script>
      <script src="${pageContext.request.contextPath}/login/login.js?v=4"></script>
    </body>

    </html>