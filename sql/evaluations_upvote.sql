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

 Date: 20/05/2025 14:44:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evaluations_upvote
-- ----------------------------
DROP TABLE IF EXISTS `evaluations_upvote`;
CREATE TABLE `evaluations_upvote`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论点赞表ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `evaluations_id` int(11) NULL DEFAULT NULL COMMENT '点赞ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of evaluations_upvote
-- ----------------------------
INSERT INTO `evaluations_upvote` VALUES (16, 8, 2);
INSERT INTO `evaluations_upvote` VALUES (28, 8, 1);
INSERT INTO `evaluations_upvote` VALUES (36, 8, 3);
INSERT INTO `evaluations_upvote` VALUES (44, 14, 3);
INSERT INTO `evaluations_upvote` VALUES (51, 14, 1);
INSERT INTO `evaluations_upvote` VALUES (53, 14, 4);
INSERT INTO `evaluations_upvote` VALUES (54, 14, 2);
INSERT INTO `evaluations_upvote` VALUES (56, 21, 8);
INSERT INTO `evaluations_upvote` VALUES (59, 21, 7);
INSERT INTO `evaluations_upvote` VALUES (64, 21, 11);
INSERT INTO `evaluations_upvote` VALUES (69, 21, 23);
INSERT INTO `evaluations_upvote` VALUES (75, 21, 26);
INSERT INTO `evaluations_upvote` VALUES (76, 37, 27);
INSERT INTO `evaluations_upvote` VALUES (77, 37, 26);
INSERT INTO `evaluations_upvote` VALUES (80, 37, 25);
INSERT INTO `evaluations_upvote` VALUES (81, 21, 29);
INSERT INTO `evaluations_upvote` VALUES (83, 21, 25);
INSERT INTO `evaluations_upvote` VALUES (85, 21, 27);
INSERT INTO `evaluations_upvote` VALUES (88, 8, 32);

SET FOREIGN_KEY_CHECKS = 1;
