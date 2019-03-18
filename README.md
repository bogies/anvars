# AVars 微服务API权限管理

## 背景
### 引言
  现在很多项目都是用 Spring Cloud 框架开发的微服务，这些微服务API都需要权限限制，此项目使用RBAC来实现微服务API的权限管理。
  
### 需求
* 生成API接口列表
* 生成前台页面列表
* 实现RBAC 基于角色的访问控制

## 实现
  基本思路是，实现rbac服务，并在gateway服务中加入拦截，在请求真实服务API前验证用户权限。


## 开发环境

​	JDK1.8

​	Spring Boot 2.0.4.Release

​	Spring Cloud Finchley.RELEASE

​	AngularJS 1.2.30

##模块说明

### Consul 1.4

​	注册与服务发现，所有微服务都依赖此模块。开源产品，具体文档和使用说明请参阅[官网](https://www.consul.io/)。

### Gateway

​	网关路由服务，服务对所有请求进行权限验证后路由到相应的微服务。可设置白名单，使其无需进行权限验证，直接进行路由。

​	使用zuul进行路由，feign进行服务间通讯，hystrix熔断机制。

### rbacs

​	实现基于角色的权限控制(RBAC)，用户通过角色与权限进行关联，进行权限管理和验证。可对所有注册到Consul的服务，读取swagger2文档，并保存进数据库，做为可配置的资源。

​	使用swagger2编写Controller接口文档，~~只有在开发模式下才开放swagger2文档接口~~。`应修改为：只能超级管理员角色有权限访问`。

### RbacView

​	rbacs服务的web管理端，只是WEB页面的展示实现，逻辑功能通过调用rbacs服务模块进行操作。

##约束及说明

### 微服务约束

​	服务所有@RestController中，所有@RequestMapping不得使用@PathVariable，会导致权限验证无法通过，可替换成@QueryParam。

​	服务中的url在访问时不区分大小写，注意不要导致重复。

## xxxView说明和约束

所有访问必须由.jsd 和 .html结尾。其中.jsd 为返回json字符串的服务接口，主要接口如下：

​	/s/do.jsd 为服务中转实现，通过POST方式中转服务请求。

​	/s/login.jsd为登录请求，登录成功后将用户数据保存到session 

.html为页面显示接口，主要接口如下：

​	/v/{xxx-xxx-xxx}.html 和/i/{xxx-xxx-xxx}.html 为显示页面跳转接口，其中xxx-xxx-xxx为路径，在后台会转换成xxx/xxx/xxx与jsp文件的目录结构相对应。其中以/v开头的页面需要进行权限验证，以/i开头的页面不做权限验证。

​	/login.html 为登录页面

​	/logout.html为登出页面

​	webapps为web页面根目录，需要权限验证的放在/pages目录（将使用工具扫描此目录并生成页面资源列表，可由后台配置访问权限），其他文件可根据需要自由放置。每个模块建目录，在目录下创建jsp和对应的 Angularjs controller。 `所有jsp文件名必须使用英文小写，不得包含符号：- `



  

tmsp.js实现了服务请求方法，具体使用请查看相关实例。

## 作者

贾晓峰

任宝坤

王辉
