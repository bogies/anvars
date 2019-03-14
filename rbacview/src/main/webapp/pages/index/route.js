var router = new VueRouter({
  routes: [
    { path: '/', redirect: 'resources' },
    { path: '/resources', component: resourcesTpl }, 
    { path: '/member', component: memberTpl }
  ]
});

// 4、创建和挂载根实例
const rbacViewApp = new Vue({
  el: '#rbacViewApp',
  router
});