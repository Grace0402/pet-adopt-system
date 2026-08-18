/*
 Navicat Premium Dump SQL

 Source Server         : localnet001
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43-log)
 Source Host           : localhost:3306
 Source Schema         : platwork

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43-log)
 File Encoding         : 65001

 Date: 03/06/2025 16:30:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户头像',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `role` int(11) NULL DEFAULT NULL COMMENT '用户角色',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别：1-女；2-男；',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '用户注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'B站「程序员辰星」原创出品', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1748877802208Snipaste_2025-05-20_15-12-41.png', '5567643@qq.com', 1, 2, '1990-06-05', '16766666666', '2024-10-19 12:53:05');
INSERT INTO `user` VALUES (59, 'lrenzhenjiuhao', '李春然', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17488801328625.png', '123342@qq.com', 2, 1, '1985-07-11', '17687665323', '2025-05-28 17:54:48');
INSERT INTO `user` VALUES (60, 'zhouzhiruo', '周芷若', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17488801271647.png', '23124231@qq.com', 1, 1, '1991-07-11', '16576666666', '2025-05-28 18:06:43');
INSERT INTO `user` VALUES (61, 'guihua', '桂花', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17488801204072.png', '14324@qq.com', 2, 1, NULL, NULL, '2025-05-29 14:13:04');
INSERT INTO `user` VALUES (62, 'zhangsan', '张三', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17485958396406.png', NULL, 2, NULL, NULL, NULL, '2025-05-29 15:39:59');
INSERT INTO `user` VALUES (63, 'zhangsan2', '追风筝的人', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17485958997558.png', '432432@qq.com', 2, 2, '1996-07-17', '17687442010', '2025-05-29 15:41:04');

SET FOREIGN_KEY_CHECKS = 1;
