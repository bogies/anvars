var rbacViewApp = angular.module("rbacViewApp", ['ui.router', 'ngAntsModules']);  
  
rbacViewApp.config(function($stateProvider, $urlRouterProvider) {  
  $urlRouterProvider.when("", "/resources");

  $stateProvider.state("resources", {
      url : "/resources",  
      templateUrl : URI_DOMAIN+'/v/index-resources.html',
      controller: 'resourcesCtrl'
  }).state("role", {  
      url : "/role",  
      templateUrl : URI_DOMAIN+"/v/index-role.html",
      controller: 'roleCtrl'
  }).state("user", {  
      url : "/user",
      templateUrl: URI_DOMAIN+'/v/index-member.html',
      controller: 'userCtrl'
  });
});
/*
rbacViewApp.config(function ($routeProvider) {
  $routeProvider.when('/', {
    templateUrl: '/view/index-resources.html',
    controller: 'resourcesCtrl'
  }).
  when('/login', {
    templateUrl: '/view/login/index.html',
    controller: 'loginCtrl',
    allowAnonymous: true
  }).
  when('/role', {
    templateUrl: '/view/index-role.html',
    controller: 'roleCtrl'
  }).
  when('/user', {
    templateUrl: '/view/index-user.jsp',
    controller: 'userCtrl'
  })
  .otherwise({
    redirectTo: '/'
  });
});

rbacViewApp.run(function ($location, $rootScope, $log, $route) {
  // $rootScope 的 $routeChangeStart 事件
  function onRouteChangeStart(event, next, current) {
    if (!$rootScope.currentUser) {
      //var username = {loginName: $("#loginName").val()};
      //_userInfo.username = username;
      //$rootScope.currentUser = username;
    }
    // 如果下一个路由不允许匿名， 并且没有认证， 则重定向到 login 页面
    if (!next.allowAnonymous && !$rootScope.currentUser.loginName) {
      //$log.log('Authentication required, redirect to login.');
      var returnUrl = '/#'+$location.url();
      //$log.log('return url is ' + returnUrl);
        //
        event.preventDefault();
        $location.path('/login').search({ returnUrl: returnUrl });
      }
    }
  // 监听 $rootScope 的 $routeChangeStart 事件
  // $rootScope.$on('$routeChangeStart', onRouteChangeStart);
});*/