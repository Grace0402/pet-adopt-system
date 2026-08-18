/*
 Navicat Premium Dump SQL

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43-log)
 Source Host           : localhost:3306
 Source Schema         : init

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43-log)
 File Encoding         : 65001

 Date: 20/05/2025 14:44:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evaluations
-- ----------------------------
DROP TABLE IF EXISTS `evaluations`;
CREATE TABLE `evaluations`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级评论ID',
  `commenter_id` int(11) NULL DEFAULT NULL COMMENT '评论者ID',
  `replier_id` int(11) NULL DEFAULT NULL COMMENT '回复者ID',
  `content_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容类型',
  `content_id` int(11) NULL DEFAULT NULL COMMENT '内容ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of evaluations
-- ----------------------------
INSERT INTO `evaluations` VALUES (1, NULL, 8, NULL, 'SCENIC', 11, '我仿佛听见了雪山的心跳，低沉而有力', '2025-04-23 13:39:05');
INSERT INTO `evaluations` VALUES (3, NULL, 14, NULL, 'SCENIC', 9, '静地凝视着这片雪白的世.我仿佛听见了雪山的心跳，低沉而有力。我仿佛听见了雪山的心跳，低沉而有力。我仿佛听见了雪山的心跳，低沉而有力。我仿佛听', '2025-04-23 14:54:26');
INSERT INTO `evaluations` VALUES (9, 7, 21, NULL, 'TEST', 1, '54534543', '2025-05-15 17:42:28');
INSERT INTO `evaluations` VALUES (17, 11, 21, 21, 'TEST', 1, '自己的样式', '2025-05-15 17:46:42');
INSERT INTO `evaluations` VALUES (18, 11, 21, 21, 'TEST', 1, '自己的样式', '2025-05-15 17:46:43');
INSERT INTO `evaluations` VALUES (20, 11, 21, 21, 'TEST', 1, '765756', '2025-05-15 17:48:17');
INSERT INTO `evaluations` VALUES (21, 11, 21, 21, 'TEST', 1, '543534543', '2025-05-15 17:55:43');
INSERT INTO `evaluations` VALUES (23, NULL, 21, NULL, 'TEST', 1, '你好', '2025-05-15 18:20:07');
INSERT INTO `evaluations` VALUES (27, 25, 37, NULL, 'TEST', 1, '真的假的？', '2025-05-15 22:20:07');
INSERT INTO `evaluations` VALUES (28, 25, 37, 21, 'TEST', 1, '当然保真', '2025-05-15 22:20:24');
INSERT INTO `evaluations` VALUES (29, NULL, 37, NULL, 'TEST', 1, '这是不是真的', '2025-05-15 22:26:26');
INSERT INTO `evaluations` VALUES (32, 31, 21, NULL, 'TEST', 1, 'huifu a', '2025-05-15 23:16:31');
INSERT INTO `evaluations` VALUES (33, NULL, 8, NULL, '', 1, '**测试', '2025-05-19 18:34:26');

SET FOREIGN_KEY_CHECKS = 1;
