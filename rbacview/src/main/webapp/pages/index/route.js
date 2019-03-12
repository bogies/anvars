var router = new VueRouter({
  routes: [
    {
      path: '/resources',
      component: resourcesTpl
    }
  ]
});

// 4、创建和挂载根实例
const rbacViewApp = new Vue({
  el: '#rbacViewApp',
  router
});