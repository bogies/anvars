# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: code.ku8.info (MySQL 5.5.52-MariaDB)
# Database: jrbac
# Generation Time: 2019-03-29 02:23:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table apps
# ------------------------------------------------------------

DROP TABLE IF EXISTS `apps`;

CREATE TABLE `apps` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '应用id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名称',
  `key` varchar(32) NOT NULL DEFAULT '',
  `detail` varchar(255) DEFAULT NULL COMMENT '详细说明',
  `order` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table members
# ------------------------------------------------------------

DROP TABLE IF EXISTS `members`;

CREATE TABLE `members` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id,uuid32位',
  `username` varchar(64) NOT NULL COMMENT '登录用户名',
  `password` varchar(64) DEFAULT '' COMMENT '登录密码,生成的password也是32位',
  `nickname` varchar(64) DEFAULT NULL COMMENT '姓名',
  `sort` int(11) unsigned DEFAULT '0' COMMENT '排序值',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户修改时间,要用程序控制,ubuntu上不能设置为now()',
  `is_admin` tinyint(3) DEFAULT '0' COMMENT '账户状态,默认为0,管理员1',
  `status` tinyint(3) DEFAULT '1' COMMENT '1=正常,2=锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `jrbac_login_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户表';

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;

INSERT INTO `members` (`id`, `username`, `password`, `nickname`, `sort`, `create_time`, `update_time`, `is_admin`, `status`)
VALUES
	('fa2c960b3d1046f0b82341a0a690ce11','test','de34d5e3d85e728193db65739d82db3c','测试',NULL,'2016-10-26 13:57:04','2017-07-26 13:57:04',0,1);

/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table resource_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `resource_type`;

CREATE TABLE `resource_type` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name` varchar(32) NOT NULL DEFAULT '',
  `detail` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `resource_type` WRITE;
/*!40000 ALTER TABLE `resource_type` DISABLE KEYS */;

INSERT INTO `resource_type` (`id`, `name`, `detail`)
VALUES
	('menu','菜单','');

/*!40000 ALTER TABLE `resource_type` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table resources
# ------------------------------------------------------------

DROP TABLE IF EXISTS `resources`;

CREATE TABLE `resources` (
  `id` varchar(255) NOT NULL DEFAULT '' COMMENT 'md5(path+req_method+service_name)',
  `type` varchar(255) DEFAULT NULL COMMENT '接口类型',
  `path` varchar(255) DEFAULT NULL COMMENT '接口地址',
  `req_method` varchar(255) DEFAULT NULL COMMENT '请求方式',
  `summary` varchar(255) DEFAULT NULL COMMENT '接口概要',
  `services_name` varchar(255) DEFAULT NULL COMMENT '所在服务器',
  `description` varchar(255) DEFAULT NULL COMMENT '接口详细说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;

INSERT INTO `resources` (`id`, `type`, `path`, `req_method`, `summary`, `services_name`, `description`)
VALUES
	('1e4fe530b8ce4079274bc9f4794b2186','api','/resources','post','add','rbac-service',NULL),
	('22bdc905890b7c0d42f5625587464c55','api','/resources','delete','删除 resources Table 接口信息','rbac-service','删除 resources Table 接口信息，根据ID'),
	('23bb17507ae9ce594253d8005169f6c6','api','/resources/getresources','get','获取 resources Table 接口信息','rbac-service','获取 resources Table 接口信息，分页'),
	('2819c572adcb78183fa2dac5e21262c0','api','/user/count/username/{username}','get','countByUsername','rbac-service',NULL),
	('2898d0efbee5ad48fd9693dc86192631','api','/resources/swaggerv2apipaths','get','Add Swagger v2-api Paths','rbac-service','将Swagger v2-api Paths 添加至 resources 表中'),
	('295a9d4e14b5c5c46355532f09c9a649','api','/role/{page}/{pagesize}','post','getRoles','getway-service',NULL),
	('2db652865d7189847f8b983b94951fd6','api','/resources','get','获取 resources Table 接口信息','rbac-service','获取 resources Table 接口信息，分页'),
	('383041d7ea1e5793e22dd384096db3ea','api','/role','post','添加角色','rbac-service',NULL),
	('387533300dd9d054073ab7b235bc0349',NULL,'1','adsf','sadf','asdf','asdf'),
	('4026d5828f7ee67b4258de0e4bfeb206','api','/user/login','post','reqLogin','rbac-service',NULL),
	('46f1d5b2eaf627dfde06175bfefd3223','api','/role/{page}/{pagesize}','get','getRoles','getway-service',NULL),
	('4b1484912d07c7f28eb7bc7fcab61da2','api','/role','put','update','rbac-service',NULL),
	('57e08000af1ee92ac6078f7f11905bda',NULL,'12341324132','asdf','asdf','asdf','asdasdfasdf'),
	('59e794d45697b360e18ba972bada0123',NULL,'aaaaaaaaa','aaaaaaaaaaaaaaaa','aaaaaaaaaaaaa','aaaaa','aaaaaaaaaaaaaaaaaaaaaa'),
	('61c7285517351d2d2833e31b87d35a42','api','/resources/deleteresources','get','删除 resources Table 接口信息','rbac-service','删除 resources Table 接口信息，根据ID'),
	('6cc6b6d5b88cb97eb808c3ca92ba1c77','api','/role/{page}/{pagesize}','delete','getRoles','getway-service',NULL),
	('768e3cad9afe86ed0e9dd677e15443c6','api','/resources/ztree','get','获取资源ztree数据','rbac-service','根据过滤条件获取想要得到的资源列表'),
	('7cb1dc6c0ee74159bea1d457b077732d','api','/role/{page}/{pagesize}','patch','getRoles','getway-service',NULL),
	('82b20f6acfa7933c312807c7cc83ae62','api','/resources','put','修改 resources Table 接口信息','rbac-service','修改 resources Table 接口信息（概要和详细说明）'),
	('93f9d18df5a79662a93609c0654eda7e','api','/resources/detial/{id}','get','detial','rbac-service',NULL),
	('94cef1f79bbbc51ad6d0792d8d2e2545','api','/role/resources','post','addResource','rbac-service',NULL),
	('9e25903ad7fe315dacbf6f76784e9eb1','pages','index-role','get',NULL,'RbacView',NULL),
	('a34f38c6f0dc255ed0fe0ef6ec14d0a0','api','/resources/role/{roleid}','get','getResourcesByRoleId','rbac-service',NULL),
	('a67509239cc9930a434c11a53ac18a68','api','/resources/{id}','delete','delete','rbac-service',NULL),
	('a92a1d4b51f84554c574a15717a0ee42','api','/user/role/{userid}','get','getUserRoles','rbac-service',NULL),
	('a95c530a7af5f492a74499e70578d150',NULL,'asdf','asdf','asdf','asdf','asd'),
	('ca427102cf6307bccb335998d4be0c2e','api','/role/user','post','updateUsers','rbac-service',NULL),
	('caf18e246adbb92ea01d9045bab8d130','api','/role/{page}/{pagesize}','options','getRoles','getway-service',NULL),
	('d3f677c7691181dc3d1e737a9e6750c8','pages','index','get',NULL,'RbacView',NULL),
	('dfa9a76703bf29afc3b2e900803b0808','api','/role','get','获取角色列表','rbac-service','根据过滤条件获取角色列表'),
	('dfb06def573685022e64507eb3d5875f','pages','index-user','get',NULL,'RbacView',NULL),
	('e2e65760e4054401f556e6626f10400e','api','/resources/auth','get','权限验证','rbac-service','检查用户是否拥有指定资源的权限'),
	('e9401e416810bc6f1b57061a6473597b','api','/resources/auth/{resid}','get','checkPermit','rbac-service',NULL),
	('fc08162a45ee018d0a4a8b6517a95809','api','/role/{page}/{pagesize}','head','getRoles','getway-service',NULL);

/*!40000 ALTER TABLE `resources` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_resources
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_resources`;

CREATE TABLE `role_resources` (
  `role_id` varchar(64) NOT NULL DEFAULT '' COMMENT '角色id',
  `resource_id` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

LOCK TABLES `role_resources` WRITE;
/*!40000 ALTER TABLE `role_resources` DISABLE KEYS */;

INSERT INTO `role_resources` (`role_id`, `resource_id`)
VALUES
	('role_ secrecy','2898d0efbee5ad48fd9693dc86192631');

/*!40000 ALTER TABLE `role_resources` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_users`;

CREATE TABLE `role_users` (
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `role_id` varchar(64) NOT NULL DEFAULT '' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `members` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

LOCK TABLES `role_users` WRITE;
/*!40000 ALTER TABLE `role_users` DISABLE KEYS */;

INSERT INTO `role_users` (`user_id`, `role_id`)
VALUES
	('fa2c960b3d1046f0b82341a0a690ce11','admin');

/*!40000 ALTER TABLE `role_users` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键id,uuid32位',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `is_reserved` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否是保留角色',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '角色创建时间，主要是用来进行排序',
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;

INSERT INTO `roles` (`id`, `name`, `description`, `is_reserved`, `create_time`)
VALUES
	('admin','管理员角色','超级管理员',1,'2018-12-24 16:26:14'),
	('anonymous','匿名角色','未登录用户都属于此角色',1,'2018-12-24 16:26:14'),
	('role_audit','安全审计','安全审计员',2,'2019-01-12 21:00:25'),
	('role_ secrecy','安全保密','安全保密员',3,'2019-01-12 21:01:39');

/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table safe_logs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `safe_logs`;

CREATE TABLE `safe_logs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL DEFAULT '',
  `user_id` varchar(64) NOT NULL DEFAULT '',
  `action_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` tinyint(3) NOT NULL COMMENT '0=用户日志；1=系统管理员日志；2=安全审计日志；3=安全保密日志',
  `action_desc` varchar(512) NOT NULL DEFAULT '' COMMENT '发生的行为描述',
  `result` tinyint(1) NOT NULL COMMENT '0=失败,1=成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table unit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `unit`;

CREATE TABLE `unit` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '部门 id',
  `code` varchar(128) DEFAULT NULL COMMENT '部门编码',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '部门名称',
  `short_name` varchar(128) DEFAULT NULL COMMENT '部门简称',
  `parent_id` varchar(64) NOT NULL DEFAULT '0' COMMENT '父部门 id',
  `level` varchar(4) DEFAULT NULL COMMENT '部门级别',
  `condition` varchar(4) DEFAULT NULL COMMENT '状态',
  `order` int(10) unsigned NOT NULL COMMENT '显示排序值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;

INSERT INTO `unit` (`id`, `code`, `name`, `short_name`, `parent_id`, `level`, `condition`, `order`)
VALUES
	('1',NULL,'信息中心',NULL,'',NULL,NULL,0);

/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_unit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_unit`;

CREATE TABLE `user_unit` (
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `unit_id` varchar(64) NOT NULL DEFAULT '' COMMENT '部门id',
  PRIMARY KEY (`user_id`,`unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
