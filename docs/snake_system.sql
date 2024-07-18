/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-123456
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : localhost:3306
 Source Schema         : snake_system

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 15/07/2024 11:12:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号ID',
  `user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID(员工 ID/会员 ID/供应商 ID,由渠道决定)',
  `account` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `channel` smallint(3) DEFAULT NULL COMMENT '账号归属渠道(11-员工，12-会员，13-供应商）',
  `login_way` smallint(3) DEFAULT NULL COMMENT '登录方式(1-密码登录，2-手机短信验证码登录，3-扫码登录,4-微信授权登录，5-小程序授权登录)',
  `open_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '三方授权ID',
  `tenant_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户ID',
  `default_tenant_flag` smallint(3) DEFAULT NULL COMMENT '是否默认租户(0-否，1-是）',
  `disabled` smallint(3) DEFAULT '0' COMMENT '账号是否禁用(0-否，1-是）',
  `supper_admin` smallint(3) DEFAULT NULL COMMENT '是否超级管理员(0-否，1-是）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `uq_account_channel_tenant` (`account`,`channel`,`tenant_id`) USING BTREE COMMENT '账号_渠道_租户全局唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账号表';

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('1', '1', '610001', 'a434066990a9fb8a9129a7e4a46c4cb1', 11, 1, NULL, '999999', 1, 0, 1, '2024-06-26 13:01:03', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `emp_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '员工ID',
  `real_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `avatar` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `gender` smallint(3) DEFAULT NULL COMMENT '性别(0-女，1-男)',
  `tenant_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户ID',
  `supper_admin` smallint(3) DEFAULT NULL COMMENT '是否租户超级管理员(0-否，1-是）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `uq_emp_phone_tenant` (`phone`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- ----------------------------
-- Records of emp
-- ----------------------------
BEGIN;
INSERT INTO `emp` VALUES ('1', '蓝东科技管理员', 'https://i1.hdslb.com/bfs/face/98a570a6c6d6a263bcb0cba9e15e492125e9d310.jpg@120w_120h_1c', 'landong@163.com', '18516908635', 1, '999999', 1, '2024-06-26 13:04:41', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for emp_org
-- ----------------------------
DROP TABLE IF EXISTS `emp_org`;
CREATE TABLE `emp_org` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `emp_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ' 员工ID',
  `org_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织ID',
  `flag_main_department` smallint(3) DEFAULT NULL COMMENT '是否主部门(1:是,0否)',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_org_tenant` (`emp_id`,`org_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工组织关联表';

-- ----------------------------
-- Records of emp_org
-- ----------------------------
BEGIN;
INSERT INTO `emp_org` VALUES ('1', '1', '1', 1, '999999');
COMMIT;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `member_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员 ID',
  `nick_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `gender` smallint(3) DEFAULT NULL COMMENT '性别(0-女，1-男，2-保密)',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `uq_member_phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员表';

-- ----------------------------
-- Records of member
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for org
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `org_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织ID',
  `parent_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上级组织ID',
  `org_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织名称',
  `org_type` smallint(3) DEFAULT NULL COMMENT '组织类型(1:公司,2:片区,3:分公司,4:部门,5:中心,6:组)',
  `org_path_ids` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织全路径ID,层级之间用 | 连接',
  `org_path_names` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织全路径名称,层级之间用 | 连接',
  `person_in_charge` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人ID',
  `person_in_charge_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人名称',
  `contact_phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人联系电话',
  `org_order` int(10) DEFAULT NULL COMMENT '排序',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除(1-删除，0-未删除）',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `uq_org_name_tenant` (`org_name`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织表';

-- ----------------------------
-- Records of org
-- ----------------------------
BEGIN;
INSERT INTO `org` VALUES ('1', '0', '陕西蓝东科技有限公司', 1, '1', '陕西蓝东科技有限公司', NULL, NULL, '18516908635', 1, 0, '999999', '2024-06-26 13:00:04', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `org` VALUES ('2', '1', '华东片区', 2, '1|2', '陕西蓝东科技有限公司|华东片区', NULL, NULL, NULL, 2, 0, '999999', '2024-07-01 18:49:27', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `org` VALUES ('21', '2', '上海分公司', 3, '1|2|21', '陕西蓝东科技有限公司|华东片区|上海分公司', NULL, NULL, NULL, 3, 0, '999999', '2024-07-01 18:51:05', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `org` VALUES ('211', '21', '研发部', 4, '1|2|21|211', '陕西蓝东科技有限公司|华东片区|上海分公司|研发部', NULL, NULL, NULL, 4, 0, '999999', '2024-07-01 18:52:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `org` VALUES ('2111', '211', '研发中心', 5, '1|2|21|2111', '陕西蓝东科技有限公司|华东片区|上海分公司|研发部|研发中心', NULL, NULL, NULL, 5, 0, '999999', '2024-07-01 18:53:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `org` VALUES ('21111', '2111', '研发一组', 6, '1|2|21|2111|21111', '陕西蓝东科技有限公司|华东片区|上海分公司|研发部|研发中心|研发一组', NULL, NULL, NULL, 6, 0, '999999', '2024-07-01 18:54:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `org` VALUES ('3', '1', '华北片区', 2, '1|3', '陕西蓝东科技有限公司|华北片区', NULL, NULL, NULL, 7, 0, '999999', '2024-07-01 18:50:12', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `position_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位ID',
  `p_position_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '平台岗位ID',
  `position_code` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `position_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上级组织ID',
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`position_id`),
  UNIQUE KEY `uq_position_role_tenant` (`position_code`,`role_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职位表';

-- ----------------------------
-- Records of position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `resource_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源ID',
  `p_resource_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '平台资源ID',
  `parent_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上级资源 ID',
  `resource_type` smallint(3) DEFAULT NULL COMMENT '资源类型(0:目录,1:菜单, 2:按钮 ,3:外链,4:接口)',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源名称',
  `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由路径(浏览器地址栏路径)',
  `component` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `perm` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '按钮权限标识',
  `redirect` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '跳转路径',
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单图标',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `disabled` smallint(3) DEFAULT NULL COMMENT '是否禁用（0-否，1-是）',
  `deleted` smallint(3) DEFAULT NULL COMMENT '是否删除（0-否，1-是）',
  `level` smallint(3) DEFAULT NULL COMMENT '层级（0～5， 最多支持 5 层）',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`resource_id`),
  UNIQUE KEY `uq_perm` (`perm`) USING BTREE,
  UNIQUE KEY `uq_component` (`component`) USING BTREE,
  UNIQUE KEY `uq_path` (`path`) USING BTREE,
  UNIQUE KEY `uq_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源表';

-- ----------------------------
-- Records of resource
-- ----------------------------
BEGIN;
INSERT INTO `resource` VALUES ('1', '1', NULL, 4, '组织机构-创建', '/snake-system-api/org/create', NULL, 'org:create', NULL, NULL, NULL, NULL, 0, 0, NULL, '999999', '2024-06-26 13:06:44', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `resource` VALUES ('2', '2', NULL, 4, '组织机构-编辑', '/snake-system-api/org/modify', NULL, 'org:modify', NULL, NULL, NULL, NULL, 0, 0, NULL, '999999', '2024-06-26 13:08:44', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `resource` VALUES ('3', '3', NULL, 4, '角色管理-创建', '/snake-system-api/role/create', NULL, 'role:create', NULL, NULL, NULL, NULL, 0, 0, NULL, '999999', '2024-06-27 08:45:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `resource` VALUES ('4', '4', NULL, 4, '组织管理-组织树查询', '/snake-system-api/org/queryOrgTree', NULL, 'org:tree:query', NULL, NULL, NULL, NULL, 0, 0, NULL, '999999', '2024-07-02 09:31:57', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色ID',
  `p_role_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '平台角色ID',
  `role_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色编码',
  `disabled` smallint(3) DEFAULT NULL COMMENT '是否禁用（0-否，1-是）',
  `deleted` smallint(3) DEFAULT NULL COMMENT '是否删除（0-否，1-是）',
  `remark` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uq_role_tenant` (`role_code`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', '1', '超级管理员', 'SUPPER_ADMIN', 0, 0, NULL, '999999', '2024-06-26 12:59:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `role` VALUES ('1806128459320147970', NULL, '管理员', 'ROLE_ADMIN', 0, 0, '管理员', '999999', NULL, '1', '张三', NULL, NULL, NULL);
INSERT INTO `role` VALUES ('1807966004232421378', NULL, '公共API', 'ROLE_COMMON_API', 0, 0, '公共API', '999999', NULL, '1', '张三', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for role_position
-- ----------------------------
DROP TABLE IF EXISTS `role_position`;
CREATE TABLE `role_position` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  `position_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位ID',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_role_position_tenant` (`role_id`,`position_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色职位关联表';

-- ----------------------------
-- Records of role_position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  `resource_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源ID',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_role_resource_tenant` (`role_id`,`resource_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源关联表';

-- ----------------------------
-- Records of role_resource
-- ----------------------------
BEGIN;
INSERT INTO `role_resource` VALUES ('1', '1', '1', '999999');
INSERT INTO `role_resource` VALUES ('2', '1', '2', '999999');
INSERT INTO `role_resource` VALUES ('3', '1', '3', '999999');
INSERT INTO `role_resource` VALUES ('4', '1', '4', '999999');
COMMIT;

-- ----------------------------
-- Table structure for sys_audit_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_audit_log`;
CREATE TABLE `sys_audit_log` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `module_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块编码(参考：me.pisces.audit.log.model.enums.LogModuleEnum)',
  `module_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块名称',
  `microservices_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微服务名称',
  `record_type` smallint(3) DEFAULT NULL COMMENT '记录类型：0-操作记录；1-异常记录',
  `operation_type` smallint(3) DEFAULT NULL COMMENT '操作类型：(1-登录，2-注销登录，3-新增，4-修改，5-删除，6-查询，7-报表导出，8-数据导入)',
  `description` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作描述',
  `request_method` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法',
  `request_params` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法参数',
  `response_data` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '响应数据',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '耗时（毫秒）',
  `exception_desc` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '异常描述',
  `user_agent` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求头特征值',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operation_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人IP',
  `operation_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人ID',
  `operation_user_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人姓名',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审计日志';

-- ----------------------------
-- Records of sys_audit_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_position`;
CREATE TABLE `sys_role_position` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键',
  `role_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '角色ID',
  `position_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '岗位ID',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '租户唯一标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_role_position` (`role_id`,`position_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色职位关联表';

-- ----------------------------
-- Records of sys_role_position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `tenant_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户唯一标识',
  `tenant_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户名称',
  `introduction` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户简称',
  `supper_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户联系人',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `phone` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `supper_account` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户超级管理员账号',
  `supper_password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户超级管理员密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`tenant_id`),
  UNIQUE KEY `uq_tennat_name` (`tenant_name`) USING BTREE,
  UNIQUE KEY `uq_introduction` (`introduction`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户表';

-- ----------------------------
-- Records of tenant
-- ----------------------------
BEGIN;
INSERT INTO `tenant` VALUES ('999999', '陕西蓝东科技有限公司', '蓝东科技', '张三', 'landong@163.com', '18516908635', '610001', '610001', '2024-06-26 12:58:24', NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID（员工ID/会员ID/供应商ID）',
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  `tenant_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户唯一标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_role_tenant` (`user_id`,`role_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1', '1', '1', '999999');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
