/*
 Navicat Premium Dump SQL

 Source Server         : local_dev
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43-log)
 Source Host           : localhost:3306
 Source Schema         : pet_adopt_system

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43-log)
 File Encoding         : 65001

 Date: 28/08/2025 19:08:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for active_net
-- ----------------------------
DROP TABLE IF EXISTS `active_net`;
CREATE TABLE `active_net`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '行为信息表主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID，外键，关联的是用户表',
  `content_id` int(11) NULL DEFAULT NULL COMMENT '内容ID',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '交互行为（1：浏览；2：点赞；3：收藏）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `content_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容模块，标识的是该互动类型属于什么模块',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '行为互动信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of active_net
-- ----------------------------
INSERT INTO `active_net` VALUES (28, 61, 2, 3, '2025-06-09 14:52:29', 'PET');
INSERT INTO `active_net` VALUES (29, 61, 2, 2, '2025-06-09 14:52:32', 'PET');
INSERT INTO `active_net` VALUES (30, 1, 1, 3, '2025-06-11 15:35:32', 'PET-POST');
INSERT INTO `active_net` VALUES (32, 1, 1, 3, '2025-06-11 15:36:26', 'PET');
INSERT INTO `active_net` VALUES (33, 1, 1, 2, '2025-06-11 15:36:26', 'PET');
INSERT INTO `active_net` VALUES (34, 1, 2, 2, '2025-06-11 15:36:31', 'PET');
INSERT INTO `active_net` VALUES (36, 1, 4, 3, '2025-06-12 14:45:44', 'PET-POST');
INSERT INTO `active_net` VALUES (37, 1, 4, 2, '2025-06-12 14:45:44', 'PET-POST');
INSERT INTO `active_net` VALUES (38, 63, 1, 1, '2025-06-17 13:30:57', 'PET');
INSERT INTO `active_net` VALUES (39, 63, 2, 1, '2025-06-17 13:31:41', 'PET');
INSERT INTO `active_net` VALUES (40, 63, 1, 1, '2025-06-17 13:35:14', 'PET-POST');
INSERT INTO `active_net` VALUES (41, 63, 5, 1, '2024-06-17 13:35:26', 'PET-POST');
INSERT INTO `active_net` VALUES (42, 59, 1, 1, '2025-06-17 15:57:27', 'PET');
INSERT INTO `active_net` VALUES (45, 62, 1, 1, '2025-06-17 17:32:54', 'PET');
INSERT INTO `active_net` VALUES (46, 62, 2, 1, '2025-06-17 17:32:56', 'PET');
INSERT INTO `active_net` VALUES (47, 61, 1, 1, '2024-06-18 10:52:01', 'PET-POST');
INSERT INTO `active_net` VALUES (48, 61, 1, 1, '2025-06-18 10:52:23', 'PET');
INSERT INTO `active_net` VALUES (49, 61, 7, 1, '2025-06-18 11:08:10', 'PET');
INSERT INTO `active_net` VALUES (50, 61, 2, 1, '2025-06-18 11:08:57', 'PET');
INSERT INTO `active_net` VALUES (51, 61, 7, 2, '2025-06-18 16:59:56', 'PET');
INSERT INTO `active_net` VALUES (52, 61, 7, 3, '2025-06-18 16:59:57', 'PET');
INSERT INTO `active_net` VALUES (53, 61, 5, 1, '2025-06-18 17:00:00', 'PET');
INSERT INTO `active_net` VALUES (54, 61, 5, 2, '2025-06-18 17:00:02', 'PET');
INSERT INTO `active_net` VALUES (55, 61, 1, 3, '2025-06-18 17:00:10', 'PET');
INSERT INTO `active_net` VALUES (56, 61, 6, 1, '2025-06-18 17:00:12', 'PET');
INSERT INTO `active_net` VALUES (57, 61, 6, 3, '2025-06-18 17:00:13', 'PET');
INSERT INTO `active_net` VALUES (58, 61, 6, 2, '2025-06-18 17:00:14', 'PET');
INSERT INTO `active_net` VALUES (59, 63, 4, 1, '2025-06-18 17:00:34', 'PET');
INSERT INTO `active_net` VALUES (60, 63, 4, 3, '2025-06-18 17:00:35', 'PET');
INSERT INTO `active_net` VALUES (61, 63, 1, 2, '2025-06-18 17:00:39', 'PET');
INSERT INTO `active_net` VALUES (62, 63, 2, 3, '2025-06-18 17:00:43', 'PET');
INSERT INTO `active_net` VALUES (63, 63, 2, 2, '2025-06-18 17:00:43', 'PET');
INSERT INTO `active_net` VALUES (64, 63, 6, 1, '2025-06-18 17:00:55', 'PET');
INSERT INTO `active_net` VALUES (65, 63, 6, 2, '2025-06-18 17:00:56', 'PET');
INSERT INTO `active_net` VALUES (66, 63, 3, 1, '2025-06-18 17:55:31', 'PET');
INSERT INTO `active_net` VALUES (67, 59, 5, 1, '2025-06-19 13:21:58', 'PET-POST');
INSERT INTO `active_net` VALUES (68, 59, 5, 2, '2025-06-19 13:22:00', 'PET-POST');
INSERT INTO `active_net` VALUES (70, 59, 1, 1, '2025-06-19 13:22:07', 'PET-POST');
INSERT INTO `active_net` VALUES (72, 59, 6, 1, '2025-06-19 14:15:32', 'PET-POST');
INSERT INTO `active_net` VALUES (73, 59, 7, 1, '2025-06-19 14:17:12', 'PET-POST');
INSERT INTO `active_net` VALUES (74, 1, 6, 1, '2025-06-19 14:19:24', 'PET-POST');
INSERT INTO `active_net` VALUES (75, 1, 7, 1, '2025-06-19 14:19:30', 'PET-POST');
INSERT INTO `active_net` VALUES (76, 1, 8, 1, '2025-06-19 14:19:34', 'PET-POST');
INSERT INTO `active_net` VALUES (77, 1, 9, 1, '2025-06-19 14:19:37', 'PET-POST');
INSERT INTO `active_net` VALUES (78, 59, 8, 1, '2025-06-19 14:20:35', 'PET-POST');
INSERT INTO `active_net` VALUES (79, 59, 8, 2, '2025-06-19 14:20:37', 'PET-POST');
INSERT INTO `active_net` VALUES (80, 59, 7, 1, '2025-06-19 15:29:35', 'PET');
INSERT INTO `active_net` VALUES (81, 59, 4, 1, '2025-06-20 15:12:32', 'PET');
INSERT INTO `active_net` VALUES (86, 59, 9, 1, '2025-06-20 16:43:51', 'PET-POST');
INSERT INTO `active_net` VALUES (88, 59, 1, 3, '2025-06-20 16:48:30', 'PET');
INSERT INTO `active_net` VALUES (89, 59, 3, 1, '2025-06-20 16:48:34', 'PET');
INSERT INTO `active_net` VALUES (90, 59, 3, 3, '2025-06-20 16:48:35', 'PET');
INSERT INTO `active_net` VALUES (91, 59, 7, 3, '2025-06-20 16:48:38', 'PET');
INSERT INTO `active_net` VALUES (92, 59, 5, 1, '2025-06-20 16:48:44', 'PET');
INSERT INTO `active_net` VALUES (93, 59, 5, 3, '2025-06-20 16:48:46', 'PET');
INSERT INTO `active_net` VALUES (94, 59, 6, 1, '2025-06-20 16:48:48', 'PET');
INSERT INTO `active_net` VALUES (95, 59, 6, 3, '2025-06-20 16:48:49', 'PET');
INSERT INTO `active_net` VALUES (100, 1, 5, 1, '2025-06-21 15:20:01', 'PET');
INSERT INTO `active_net` VALUES (101, 1, 4, 1, '2025-06-21 15:25:05', 'PET');
INSERT INTO `active_net` VALUES (102, 1, 1, 1, '2025-06-21 15:25:15', 'PET');
INSERT INTO `active_net` VALUES (103, 1, 2, 1, '2025-06-21 15:25:23', 'PET');
INSERT INTO `active_net` VALUES (104, 1, 3, 1, '2025-06-21 15:25:32', 'PET');
INSERT INTO `active_net` VALUES (105, 1, 6, 1, '2025-06-21 15:25:41', 'PET');
INSERT INTO `active_net` VALUES (106, 1, 7, 1, '2025-06-21 15:25:49', 'PET');
INSERT INTO `active_net` VALUES (107, 1, 8, 1, '2025-06-21 15:55:22', 'PET');
INSERT INTO `active_net` VALUES (108, 59, 4, 3, '2025-06-21 16:00:26', 'PET');
INSERT INTO `active_net` VALUES (109, 59, 10, 1, '2025-06-21 16:05:24', 'PET-POST');
INSERT INTO `active_net` VALUES (110, 1, 10, 1, '2025-06-21 16:05:52', 'PET-POST');
INSERT INTO `active_net` VALUES (112, 59, 10, 2, '2025-06-21 16:06:16', 'PET-POST');

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID，外键，关联的是用户表，标识这条收货地址信息是谁配置的',
  `detail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '详细地址',
  `addressee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收件人',
  `concat_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收件电话',
  `is_default` tinyint(1) NULL DEFAULT NULL COMMENT '是否是默认地址（0：非默认；1：默认地址）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '收货地址信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, 63, '上海市浦东新区张江高科技园区博云路 2 号汇智国际商业中心 A 座 1503 室', '张三三', '13880901111', 0, '2025-06-16 17:50:37');
INSERT INTO `address` VALUES (2, 63, '广州市天河区天河北路 183 号芳草园小区 5 栋 3 单元 1002 室', '王建国', '13120201111', 1, '2025-06-16 17:55:42');
INSERT INTO `address` VALUES (3, 63, '北京市朝阳区建国路 88 号 SOHO 现代城 B 座 2305 室（前台代收）', '李婷', '13120909213', 0, '2025-06-16 18:11:15');
INSERT INTO `address` VALUES (5, 62, '测试地址', '张三', '13000001212', 1, '2025-06-18 09:38:48');
INSERT INTO `address` VALUES (6, 59, '测试地址', '章三', '13490786622', 0, '2025-06-19 15:30:02');
INSERT INTO `address` VALUES (7, 59, '测试', '张三', '13890908888', 1, '2025-06-21 16:02:10');

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of evaluations
-- ----------------------------
INSERT INTO `evaluations` VALUES (1, NULL, 1, NULL, 'TEST', 1, '我就是不想看了', '2025-05-29 14:43:13');
INSERT INTO `evaluations` VALUES (2, 1, 1, NULL, 'TEST', 1, '怎么说啊', '2025-05-29 14:43:21');
INSERT INTO `evaluations` VALUES (3, 1, 1, 1, 'TEST', 1, '还能怎么说哦，质量高哦', '2025-05-29 14:43:46');
INSERT INTO `evaluations` VALUES (4, NULL, 1, NULL, 'TEST', 2, '多少啊\n', '2025-05-29 14:54:50');
INSERT INTO `evaluations` VALUES (5, NULL, 1, NULL, 'TEST', 2, '423423432', '2025-05-29 14:57:47');
INSERT INTO `evaluations` VALUES (6, 5, 1, NULL, 'TEST', 2, '你说呢', '2025-05-29 14:57:55');
INSERT INTO `evaluations` VALUES (8, NULL, 61, NULL, 'PET', 1, '观察周围，偶尔还会发出软萌的\"喵', '2025-06-08 15:41:05');
INSERT INTO `evaluations` VALUES (9, 8, 61, NULL, 'PET', 1, '423423423', '2025-06-08 15:41:10');
INSERT INTO `evaluations` VALUES (10, 8, 61, 61, 'PET', 1, '偶尔还会发出软萌的\"', '2025-06-08 15:41:22');
INSERT INTO `evaluations` VALUES (11, NULL, 61, NULL, 'PET', 1, '玩逗猫棒，追逐时轻盈跳跃的样子像一朵飘', '2025-06-08 15:45:25');
INSERT INTO `evaluations` VALUES (12, NULL, 61, NULL, 'PET', 2, '还会发出软萌的\"喵呜\"声，仿佛在和人', '2025-06-08 15:47:16');
INSERT INTO `evaluations` VALUES (16, NULL, 1, NULL, 'PET-POST', 1, '会感到孤独焦虑。我每天保证至少1小时互', '2025-06-11 15:36:10');
INSERT INTO `evaluations` VALUES (17, 16, 1, NULL, 'PET-POST', 1, '这份幸福感无可替代。希望这些经验能帮', '2025-06-11 15:36:17');
INSERT INTO `evaluations` VALUES (18, NULL, 59, NULL, 'NOTICE', 2, '度高、故事感强的作品更容易获', '2025-06-20 15:34:30');
INSERT INTO `evaluations` VALUES (19, NULL, 59, NULL, 'PET', 4, '偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐', '2025-06-21 16:00:35');
INSERT INTO `evaluations` VALUES (21, NULL, 59, NULL, 'PET-POST', 10, '猫棒，追逐时轻盈跳跃的样子像一朵飘动的云，因此得名\"花花\"。花花对新鲜事物充满好奇，常常歪着头观察周围，偶尔还会发出软萌的\"喵呜\"声，仿佛在和', '2025-06-21 16:06:19');
INSERT INTO `evaluations` VALUES (22, NULL, 59, NULL, 'NOTICE', 3, '户：打开 APP→首页轮播图', '2025-06-21 16:10:59');
INSERT INTO `evaluations` VALUES (23, NULL, 59, NULL, 'PET-POST', 1, '**', '2025-06-21 16:15:56');

-- ----------------------------
-- Table structure for evaluations_upvote
-- ----------------------------
DROP TABLE IF EXISTS `evaluations_upvote`;
CREATE TABLE `evaluations_upvote`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论点赞表ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `evaluations_id` int(11) NULL DEFAULT NULL COMMENT '评论ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of evaluations_upvote
-- ----------------------------
INSERT INTO `evaluations_upvote` VALUES (2, 1, 3);
INSERT INTO `evaluations_upvote` VALUES (3, 61, 10);
INSERT INTO `evaluations_upvote` VALUES (5, 61, 12);
INSERT INTO `evaluations_upvote` VALUES (6, 59, 19);
INSERT INTO `evaluations_upvote` VALUES (7, 59, 20);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告表主键ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '公告内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (2, '【夏日萌宠摄影大赛】晒你家毛孩子的夏日，赢千元大礼包！', '<p>亲爱的铲屎官们：<br>夏天到了，毛孩子们在树荫下打盹、玩水枪的样子是不是萌翻了？快来参加「夏日萌宠摄影大赛」，用镜头记录宠物的夏日治愈瞬间，丰厚奖品等你拿！</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>📅 活动时间</strong></span></h5><p>2025 年 7 月 1 日 - 7 月 31 日（投稿截止 7 月 25 日，投票 7 月 26-31 日）</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>🎁 奖项设置</strong></span></h5><ul><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>一等奖 1 名</strong></span>：进口天然粮半年份（价值 800 元）+ 宠物摄影工作室写真套餐（价值 500 元）</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>二等奖 3 名</strong></span>：智能喂食器 + 夏季冰垫 + 驱虫药套装</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>三等奖 5 名</strong></span>：宠物夏季服饰套装 + 零食大礼包</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>参与奖</strong></span>：所有投稿用户可获「宠物美容 8 折券」一张</li></ul><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>📌 参与方式</strong></span></h5><ol><li style=\"text-align: start;\">关注公众号「萌宠之家」，回复 “摄影大赛” 获取投稿链接；</li><li style=\"text-align: start;\">上传 1-3 张宠物夏日主题照片（附 100 字以内故事）；</li><li style=\"text-align: start;\">照片需原创，需包含宠物与 “夏日元素”（如泳池、西瓜、遮阳帽等）。</li></ol><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>💡 小贴士</strong></span></h5><ul><li style=\"text-align: start;\">照片清晰度高、故事感强的作品更容易获奖哦～</li><li style=\"text-align: start;\">7 月每周五晚 8 点，公众号将推送「入围作品精选」，记得拉上亲友点赞！<br></li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>快让你家毛孩子当一次 “明星”，我们在后台等你晒照哦！</strong></span></li></ul>', '2025-06-11 20:18:40');
INSERT INTO `notice` VALUES (3, '【重要更新】平台新增 “宠物医疗地图” 功能', '<ul><li style=\"text-align: start;\"></li><li style=\"text-align: start;\">为了让铲屎官们在宠物突发不适时更快找到靠谱医疗资源，「萌宠之家」APP v3.2.0 版本新增「宠物医疗地图」功能，三大亮点抢先看：</li></ul><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>📍 功能亮点</strong></span></h5><ol><li style=\"text-align: start;\">精准定位附近机构打开 APP 首页点击 “医疗地图”，自动显示 3 公里内宠物医院、疫苗接种点、24 小时急诊，支持按 “评分”“距离” 排序。</li><li style=\"text-align: start;\">避坑指南一目了然每家机构标注 “是否支持医保”“有无夜间急诊”“用户真实评价”，避免踩雷不专业诊所。</li><li style=\"text-align: start;\">急诊一键导航遇到宠物误食、受伤等紧急情况，点击 “急诊优先” 按钮，系统自动规划最近急诊路线，并显示值班医生联系方式。</li></ol><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>📱 如何使用</strong></span></h5><ul><li style=\"text-align: start;\">已下载用户：打开 APP→首页轮播图→点击 “宠物医疗地图”</li><li style=\"text-align: start;\">新用户：扫描下方二维码下载 APP，注册即赠「宠物急诊指南手册」电子版</li></ul><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>⚠️ 温馨提示</strong></span></h5><p>目前 “医疗地图” 已覆盖全国 300 + 城市，偏远地区数据持续更新中。如有遗漏的优质机构，可在 APP 内 “反馈建议” 中提交哦～</p>', '2025-06-11 17:29:55');
INSERT INTO `notice` VALUES (4, '【社区规范更新】文明养宠，共建有爱交流环境', '<p><br></p><p>亲爱的社区用户：<br>为了维护健康的交流氛围，平台更新《社区公约》，新增 “文明养宠积分体系”，快来看看哪些行为会被奖励 / 处罚吧！</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>🚫 禁止行为（违规将被禁言 / 封号）</strong></span></h5><ol><li style=\"text-align: start;\">发布虐宠、弃养相关内容；</li><li style=\"text-align: start;\">恶意攻击其他用户或宠物；</li><li style=\"text-align: start;\">售卖无检疫证明的宠物 / 药品；</li><li style=\"text-align: start;\">发布 “不牵绳遛狗”“不清理粪便” 等不文明养宠行为的图文。</li></ol><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>🎖️ 文明养宠积分奖励</strong></span></h5><ol><li style=\"text-align: start;\">分享 “捡屎袋使用”“牵绳遛狗” 等文明行为图文 → 每次 + 10 积分</li><li style=\"text-align: start;\">科普 “科学养宠知识” 被置顶 → 每次 + 50 积分</li><li style=\"text-align: start;\">参与 “领养代替购买” 公益活动 → 每次 + 30 积分</li></ol><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>📈 积分用途</strong></span></h5><ul><li style=\"text-align: start;\">100 积分：兑换 “文明养宠” 专属头像框</li><li style=\"text-align: start;\">300 积分：兑换宠物拾便袋 1 盒</li><li style=\"text-align: start;\">500 积分：兑换社区线下活动优先参与权</li></ul><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>📢 特别提醒</strong></span></h5><p>7 月 1 日起，新用户注册需阅读并同意《文明养宠承诺书》。老用户可在 “个人中心→设置” 中查看积分明细。<br></p><p><span style=\"font-size: 16px;\"><strong>让我们一起用文明养宠的态度，给毛孩子们创造更友好的世界～</strong></span></p>', '2025-06-11 17:30:18');

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '宠物信息表主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '宠物名',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '宠物封面图',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '宠物描述',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '宠物所在地',
  `age` int(3) NULL DEFAULT NULL COMMENT '宠物年龄，以月为单位',
  `gender` int(1) NULL DEFAULT NULL COMMENT '性别（0：公；1：母）',
  `pet_type_id` int(11) NULL DEFAULT NULL COMMENT '宠物类别ID，外键，关联的是宠物类别表',
  `is_vaccine` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经接种疫苗（0：未接种；1：已接种）',
  `is_recommend` tinyint(1) NULL DEFAULT NULL COMMENT '是否推荐（0：未推荐；1：已推荐）',
  `is_adopt` tinyint(1) NULL DEFAULT NULL COMMENT '领养状态（0：待领养；1：已领养）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '宠物信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pet
-- ----------------------------
INSERT INTO `pet` VALUES (1, '宠物花花', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504907178872025062105571159.jpg', '<ul><li><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">花花是一只活泼可爱的布偶猫，拥有雪白柔软的毛发和湛蓝如宝石般的大眼睛，性格温顺亲人，喜欢撒娇和蹭主人的腿。</span></li><li><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">它最爱玩逗猫棒，追逐时轻盈跳跃的样子像一朵飘动的云，因此得名\"花花\"。花花对新鲜事物充满好奇，常常歪着头观察周围，偶尔还会发出软萌的\"喵呜\"声，仿佛在和人对话。它特别爱干净，每天都会认真梳理毛发，午后喜欢蜷在阳光下发呆，慵懒又优雅。花花不仅是家里的开心果，更是治愈心灵的温暖小天使。</span></li></ul>', '栗子街道101号仓库', 24, 1, 2, 1, 1, 0, '2025-06-05 15:10:15');
INSERT INTO `pet` VALUES (2, '忠诚田园犬', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504907279132025062105595796.jpg', '<p><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">偶尔还会发出软萌的\"喵呜\"声，仿佛在和人对话。它特别爱干净，每天都会认真梳理毛发，午后喜欢蜷在阳光下发呆，慵懒又优雅。1243324</span></p>', '栗子街道102仓库', 32, 1, 2, 0, 1, 0, '2025-06-05 15:16:38');
INSERT INTO `pet` VALUES (3, '【活泼亲人】小橘猫“橙子”等待温暖的家', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504907362372025062105582156.jpg', '<p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐾 2个月大的小橘猫，性格活泼好动，喜欢蹭人撒娇。已驱虫，健康无忧。适合喜欢互动的主人，给它一个充满爱的家吧！</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐾 2个月大的小橘猫，性格活泼好动，喜欢蹭人撒娇。已驱虫，健康无忧。适合喜欢互动的主人，给它一个充满爱的家吧！</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐾 2个月大的小橘猫，性格活泼好动，喜欢蹭人撒娇。已驱虫，健康无忧。适合喜欢互动的主人，给它一个充满爱的家吧！</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐾 2个月大的小橘猫，性格活泼好动，喜欢蹭人撒娇。已驱虫，健康无忧。适合喜欢互动的主人，给它一个充满爱的家吧！🐾 2个月大的小橘猫，性格活泼好动，喜欢蹭人撒娇。已驱虫，健康无忧。适合喜欢互动的主人，给它一个充满爱的家吧！</span></p>', '广西南宁万象城', 32, 0, 2, 1, 1, 0, '2025-06-18 11:00:06');
INSERT INTO `pet` VALUES (4, '【高颜值】布偶猫“雪球”寻找专属家人', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504907096062025062106035268.jpg', '<p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">❄️ 8个月大的布偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐全，期待成为你的小公主。</span></p><p><br></p>', '广东广州白云区', 8, NULL, 1, 1, 1, 0, '2025-06-18 11:04:23');
INSERT INTO `pet` VALUES (5, '【乖巧安静】中华田园犬“小黑”求领养', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504906949202025062106030082.jpg', '<p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐕 1岁大的小黑，聪明温顺，不吵不闹，已学会基本指令。希望找到一个有院子的家庭，陪伴你度过每一天。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐕 1岁大的小黑，聪明温顺，不吵不闹，已学会基本指令。希望找到一个有院子的家庭，陪伴你度过每一天。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐕 1岁大的小黑，聪明温顺，不吵不闹，已学会基本指令。希望找到一个有院子的家庭，陪伴你度过每一天。</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐕 1岁大的小黑，聪明温顺，不吵不闹，已学会基本指令。希望找到一个有院子的家庭，陪伴你度过每一天。</span></p><p><br></p>', '福建泉州', 12, 0, 2, 1, 0, 0, '2025-06-18 11:05:34');
INSERT INTO `pet` VALUES (6, '【流浪逆袭】三花猫“幸运”的蜕变之旅', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504907444412025062106042568.jpg', '<p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🍀 1岁三花猫，曾被救助后恢复健康，亲人又懂事。领养它，给“幸运”一个真正的幸福猫生！</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🍀 1岁三花猫，曾被救助后恢复健康，亲人又懂事。领养它，给“幸运”一个真正的幸福猫生！</span></p>', '山东枣庄', 12, 0, 2, 1, 0, 0, '2025-06-18 11:06:27');
INSERT INTO `pet` VALUES (7, '【老年陪伴】8岁泰迪“豆豆”求养老家庭', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504907556902025062105593735.jpg', '<p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐩 温顺的老年泰迪，因原主人移民急需新家。不拆家、爱撒娇，适合想省心养宠的您！</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐩 温顺的老年泰迪，因原主人移民急需新家。不拆家、爱撒娇，适合想省心养宠的您！</span></p><p><span style=\"color: rgb(64, 64, 64); background-color: rgb(255, 255, 255);\">🐩 温顺的老年泰迪，因原主人移民急需新家。不拆家、爱撒娇，适合想省心养宠的您！</span></p>', '湖北黄冈', 19, NULL, 2, 1, 1, 0, '2025-06-18 11:07:56');
INSERT INTO `pet` VALUES (8, '测试猫', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504924661092025062106031870.jpg', '<p>测试</p>', '湖北', 22, 1, 1, 1, 0, 0, '2025-06-21 15:55:16');

-- ----------------------------
-- Table structure for pet_adopt_order
-- ----------------------------
DROP TABLE IF EXISTS `pet_adopt_order`;
CREATE TABLE `pet_adopt_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '宠物领养单据主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID，外键，关联用户表，标识的是谁产生的订单数据',
  `pet_id` int(11) NULL DEFAULT NULL COMMENT '宠物ID，外键，关联的是宠物表，标识的是哪只宠物被申请领养的',
  `address_id` int(11) NULL DEFAULT NULL COMMENT '收货地址ID，外键，关联的是收货地址表',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '领养描述',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '单据状态（1：申请中；2：已审核；3：审核未通过；4：已完成）',
  `audit_error_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '审核不通过原因备注',
  `is_again_post` tinyint(255) NULL DEFAULT NULL COMMENT '是否是再次提交（0：初次提交；1：再次提交）',
  `post_number` tinyint(1) NULL DEFAULT NULL COMMENT '提交次数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '宠物领养单据信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pet_adopt_order
-- ----------------------------
INSERT INTO `pet_adopt_order` VALUES (3, 62, 1, 5, '<h4 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>领养承诺</strong></span></h4><ol><li style=\"text-align: start;\">承诺对宠物终身负责，不遗弃、不虐待，若因特殊情况无法继续饲养，将通过正规渠道送养，不私自转让。</li><li style=\"text-align: start;\">遵守国家及地方养宠规定，办理宠物登记、接种疫苗，定期驱虫，文明遛狗（如牵绳、清理粪便）。</li><li style=\"text-align: start;\">提供科学喂养，使用正规宠物粮食，定期体检，生病时及时就医。</li><li style=\"text-align: start;\">接受领养机构的定期回访，配合提供宠物近况（照片 / 视频）。</li></ol><p style=\"text-align: start;\"><img src=\"/api/v1.0/pet-adopt-api/file/getFile?fileName=1750214407488Snipaste_2025-06-05_15-08-06.png\" alt=\"/api/v1.0/pet-adopt-api/file/getFile?fileName=1750214407488Snipaste_2025-06-05_15-08-06.png\" data-href=\"/api/v1.0/pet-adopt-api/file/getFile?fileName=1750214407488Snipaste_2025-06-05_15-08-06.png\" style=\"width: 50%;\"></p>', 2, '提供科学喂养，使用正规宠物粮食，请补充哦', 1, 7, '2025-06-18 09:38:58');
INSERT INTO `pet_adopt_order` VALUES (4, 62, 2, 5, '<p style=\"text-align: start;\">尊敬的领养机构：</p><p style=\"text-align: start;\">我们家庭诚挚申请领养宠物，希望能够为一只可爱的动物提供温暖的家。以下从家庭经济情况及与动物的相处之道两方面进行说明：</p><p style=\"text-align: start;\"><strong>家庭经济情况</strong>：我们家庭收入稳定，具备足够的经济能力承担宠物的日常开支，包括食物、医疗、玩具等。家中已为宠物预留了专项预算，确保其健康成长。此外，我们居住环境宽敞，设有安全的室内外活动空间，能够为宠物提供舒适的生活条件。</p><p style=\"text-align: start;\"><strong>与动物的相处之道</strong>：我们全家都非常喜爱动物，深知宠物是家庭的重要成员，需要耐心、责任与关爱。我们承诺科学喂养，定期体检，并给予充分的陪伴与训练。家中孩子性格温和，已学习如何与动物友好相处，确保宠物在充满爱的环境中成长。</p><p style=\"text-align: start;\">我们期待通过领养，给予一只动物幸福的家庭生活，同时也丰富我们的家庭情感。望贵机构审核批准，谢谢！</p><p style=\"text-align: start;\">此致<br>敬礼</p><p style=\"text-align: start;\">申请人：B站「程序员辰星」<br>日期：2025年06月18日10：00</p>', 2, '测试', 1, 10, '2025-06-18 10:42:19');
INSERT INTO `pet_adopt_order` VALUES (5, 59, 7, 6, '<p>测试领养</p><p>11111</p>', 4, '423432', 1, 2, '2025-06-19 15:30:06');

-- ----------------------------
-- Table structure for pet_post
-- ----------------------------
DROP TABLE IF EXISTS `pet_post`;
CREATE TABLE `pet_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '宠物经验帖子主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID，外键，关联的是用户表',
  `pet_type_id` int(11) NULL DEFAULT NULL COMMENT '宠物类型ID，外键，关联的是宠物类别表',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '封面',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '摘要',
  `is_audit` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经审核（0：未审核；1：已审核）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '宠物经验帖子信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pet_post
-- ----------------------------
INSERT INTO `pet_post` VALUES (1, 61, 1, '零食奖励法，宠物能多听懂20多种指令', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1749463108000Snipaste_2025-06-05_15-08-29.png', '<h3>养宠物狗的500字经验分享</h3><p>养狗是一项充满乐趣但也需要责任心的长期承诺。通过多年的养狗经验，我总结出以下实用建议：</p><p>​<strong>​1. 品种选择要慎重​</strong>​<br><br>不同犬种性格和需求差异很大。初次养狗建议选择金毛、拉布拉多等温顺易训的品种。我家金毛\"多多\"3岁，性格稳定，对儿童友好，是理想的家庭伴侣犬。</p><p>​<strong>​2. 科学喂养是关键​</strong>​<br><br>幼犬期要少量多餐（每天3-4次），成年后改为早晚两餐。建议选择优质狗粮，搭配新鲜蔬菜（胡萝卜、西兰花等）。切记不要喂食巧克力、葡萄等对狗有毒的食物。我家多多坚持定时定量喂养，毛发光亮，体检指标一直很健康。</p><p>​<strong>​3. 日常护理不能少​</strong>​</p><ul><li>​<strong>​梳毛​</strong>​：每周至少3次，金毛这类长毛犬更要每天梳理</li><li>​<strong>​洗澡​</strong>​：夏季每月2-3次，冬季每月1次，使用宠物专用浴液</li><li>​<strong>​牙齿​</strong>​：每周刷牙2-3次，预防牙结石</li><li>​<strong>​指甲​</strong>​：每月修剪1次，避免过长影响行走</li></ul><p>​<strong>​4. 训练要从小开始​</strong>​<br><br>3-6个月是最佳训练期。基础指令如\"坐\"、\"等\"、\"来\"要反复练习。我采用零食奖励法，多多现在能听懂20多种指令。定点排便训练尤其重要，前期要耐心引导，成功后家里卫生问题就解决了。</p><p>​<strong>​5. 医疗保健要重视​</strong>​<br><br>定期驱虫（每月体外，每季度体内）、每年接种疫苗必不可少。建议购买宠物医疗保险，我去年为多多做膝关节手术，保险报销了70%费用。老年犬要增加体检频率，多多7岁后我每半年带它做一次全面检查。</p><p>​<strong>​6. 心理需求要关注​</strong>​<br><br>狗狗也会感到孤独焦虑。我每天保证至少1小时互动时间，包括散步、游戏等。疫情期间居家办公，特意给多多买了智能玩具，避免它因主人长时间工作而感到被冷落。</p><p>养狗不仅是喂养，更是建立一种互相陪伴的生命联结。通过科学养护和情感投入，看着多多从顽皮小狗成长为稳重贴心的伙伴，这份幸福感无可替代。希望这些经验能帮助新晋狗主少走弯路。</p>', '幼犬期要少量多餐（每天3-4次），成年后改为早晚两餐。建议选择优质狗粮，搭配新鲜蔬菜（胡萝卜、西兰花等）。切记不要喂食巧克力、葡萄等对狗有毒的食物。我家多多坚持定时定量喂养，毛发光亮，体检指标一直很健康。', 1, '2025-06-09 17:58:47');
INSERT INTO `pet_post` VALUES (5, 1, 1, '养猫误区大揭秘！私藏的 6 个养猫技巧，猫主子更黏人', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1749628839000Snipaste_2025-06-05_15-08-06.png', '<p>养猫后发现主子总是爱答不理？甚至躲在沙发底下？其实猫咪的很多 “高冷” 行为，都是主人无意中踩了 “雷区”。这篇文章教你用猫咪的思维相处，让它主动蹭你求抱抱。</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>1. 别用 “抱狗” 的方式抱猫</strong></span></h5><p>狗狗喜欢被搂在怀里，但猫咪的腹部和爪子非常敏感。正确抱法是：一只手托住前肢腋下，另一只手托住屁股，让它的身体紧贴你的胸口。如果猫咪挣扎，立刻松手，否则它会把 “被抱” 和痛苦关联。</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>2. 猫砂盆：比 “干净” 更重要的是 “隐私”</strong></span></h5><p>猫咪如厕时需要安全感，猫砂盆别放在阳台、厨房等嘈杂区域，建议放在卧室衣柜旁或卫生间角落。猫砂至少每天铲一次，每周全换（膨润土猫砂厚度建议 5-8cm），换砂时留少量旧砂，避免气味变化让猫咪拒用。</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>3. 零食投喂：建立 “条件反射” 的神器</strong></span></h5><p>想让猫咪听指令？用 freeze-dried 鸡肉粒做训练零食。每次喂食前，先叫它的名字，等它看你时再给零食，重复一周后，它会把 “回应名字” 和 “有好吃的” 关联起来。注意每天零食量不超过主食的 10%，避免挑食。</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>4. 梳毛：比撸猫更让猫上瘾的互动</strong></span></h5><p>猫咪每天会花 30% 的时间舔毛，但长期舔毛易导致毛球症。用针梳（短毛猫）或排梳（长毛猫）顺着毛发生长方向梳，重点梳下巴、腹部和尾巴根。梳到舒服的地方，猫咪会主动拱起身体，这时可以趁机多梳几下，增进感情。</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>5. 猫抓板：保护家具的 “必需品”</strong></span></h5><p>猫咪磨爪是天性，别因为它抓沙发就剪指甲（会损伤血线）。在沙发旁放立式猫抓板，撒少量猫薄荷粉，当它去抓时，用零食奖励。如果发现它抓家具，立刻用喷壶喷少量水（别直接喷猫脸），并引导到猫抓板旁。</p><h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>6. 读懂猫咪的 “身体语言”</strong></span></h5><ul><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>尾巴竖起且尖端弯曲</strong></span>：心情好，想让你摸；</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>耳朵后撇、瞳孔放大</strong></span>：害怕或生气，别靠近；</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>用头蹭你</strong></span>：标记领地，表达 “你是我的人”；</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>趴在地上露出肚子</strong></span>：信任你，但不一定想被摸肚子（大部分猫不喜欢）。<br></li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>结语</strong></span>：猫咪的爱很含蓄，需要主人用耐心解读它的需求。当你学会用它的方式相处，会发现高冷的猫主子其实藏着一颗黏人的心～</li></ul>', '猫咪每天会花 30% 的时间舔毛，但长期舔毛易导致毛球症。用针梳（短毛猫）或排梳（长毛猫）顺着毛发生长方向梳，重点梳下巴、腹部和尾巴根。梳到舒服的地方，猫咪会主动拱起身体，这时可以趁机多梳几下，增进感情。', 1, '2025-06-11 16:00:56');
INSERT INTO `pet_post` VALUES (6, 59, 1, '领养了一只流浪猫，准备了这些东西，让它 3 天就信任我', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1750313708324Snipaste_2025-06-18_11-02-35.png', '<h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>内容</strong></span>：</h5><p>上周从救助站领养了一只 2 岁的流浪猫 “煤球”，接它回家前我列了份清单，现在它已经敢在我腿上踩奶了！分享给新手：<br></p><ol><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>心理准备</strong></span>：流浪猫可能有应激反应，提前学习 “渐进式脱敏法”（如用零食建立信任）。</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>物品清单</strong></span>：</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>医疗预备</strong></span>：提前联系附近 24 小时宠物医院（我选了 “宠爱国际”，急诊电话 400-xxxx），购买基础体检套餐（某团 199 元含驱虫 + 疫苗）。</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>饮食过渡</strong></span>：救助站给的猫粮和新买的 “渴望” 混喂 3 天，避免腹泻（煤球换粮时没拉肚子，亲测有效！）。</li></ol>', '上周从救助站领养了一只 2 岁的流浪猫 “煤球”，接它回家前我列了份清单，现在它已经敢在我腿上踩奶了！', 1, '2025-06-19 14:15:24');
INSERT INTO `pet_post` VALUES (7, 59, 2, '金毛 “土豆” 到家第 3 天融合，我做对了这 5 件事', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1750313785167Snipaste_2025-06-18_11-01-50.png', '<h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>内容</strong></span>：</h5><p>土豆是我从领养中心接回的 2 个月大金毛，现在它已经会自己去尿垫上厕所了！关键步骤：<br></p><ol><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>圈定安全区</strong></span>：用围栏把客厅角落围起来（1.5m×1.5m），放狗窝、食盆、尿垫，土豆前 2 天只在里面活动。</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>建立规律作息</strong></span>：</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>禁止过度安抚</strong></span>：土豆第一天晚上叫时，我在围栏旁放了件带气味的 T 恤，没抱上床，第 2 天就不叫了。</li></ol>', '圈定安全区：用围栏把客厅角落围起来（1.5m×1.5m），放狗窝、食盆、尿垫，土豆前 2 天只在里面活动', 1, '2025-06-19 14:16:49');
INSERT INTO `pet_post` VALUES (8, 59, 1, '阿橘从哈气到踩奶：我用 “零食训练法” 让流浪猫信任我', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1750313882684Snipaste_2025-06-18_11-03-10.png', '<p>阿橘是只 3 岁的流浪狸花，刚到家时躲在沙发下哈气，我用这招让它 1 周就主动贴贴：<br></p><ol><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>零食诱惑</strong></span>：每天用猫条（“小李子” 鸡肝味）在沙发边蹲喂，不伸手摸，第 4 天它主动凑过来吃。</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>模拟捕猎</strong></span>：用激光笔引导它运动（流浪猫天性爱捕猎），每次玩完给零食，建立 “互动 = 快乐” 的联想。</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>尊重边界</strong></span>：阿橘躲起来时绝不强行拖拽，用 “猫隧道” 玩具引诱它自己出来（某拼夕夕 19.9 元，阿橘现在天天钻）。</li></ol>', '用激光笔引导它运动（流浪猫天性爱捕猎），每次玩完给零食，建立 “互动 = 快乐” 的联想', 1, '2025-06-19 14:18:04');
INSERT INTO `pet_post` VALUES (9, 59, 2, '边牧一日三餐：不同阶段吃什么？营养师定制食谱', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1750313948026Snipaste_2025-06-18_11-01-43.png', '<h5 style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>内容</strong></span>：</h5><p>闪电是我领养的 1 岁边牧，根据营养师建议调整饮食后，毛发亮到反光！<br></p><ol><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>1-2 岁成长期</strong></span>：</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>3 岁后维持期</strong></span>：</li><li style=\"text-align: start;\"><span style=\"font-size: 16px;\"><strong>禁忌食物</strong></span>：闪电曾偷吃葡萄后呕吐，才知道葡萄、巧克力、洋葱绝对不能吃！（附中毒症状对照表）</li></ol>', '闪电曾偷吃葡萄后呕吐，才知道葡萄、巧克力、洋葱绝对不能吃！（附中毒症状对照表）', 1, '2025-06-19 14:19:09');
INSERT INTO `pet_post` VALUES (10, 59, 2, '1偶妹妹，蓝眼睛如星空般美丽，性格温柔黏人。已绝育，疫苗齐', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17504930862852025062106001394.jpg', '<ul><li style=\"text-align: start;\"><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">花花是一只活泼可爱的布偶猫，拥有雪白柔软的毛发和湛蓝如宝石般的大眼睛，性格温顺亲人，喜欢撒娇和蹭主人的腿。</span></li><li style=\"text-align: start;\"><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">它最爱玩逗猫棒，追逐时轻盈跳跃的样子像一朵飘动的云，因此得名\"花花\"。花花对新鲜事物充满好奇，常常歪着头观察周围，偶尔还会发出软萌的\"喵呜\"声，仿佛在和人对话。它特别爱干净，每天都会认真梳理毛发，午后喜欢蜷在阳光下发呆，慵懒又优雅。花花不仅是家里的开心果，更是治愈心灵的温暖小天使。</span></li></ul><p style=\"text-align: start;\"><br></p><ul><li style=\"text-align: start;\"><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">花花是一只活泼可爱的布偶猫，拥有雪白柔软的毛发和湛蓝如宝石般的大眼睛，性格温顺亲人，喜欢撒娇和蹭主人的腿。</span></li><li style=\"text-align: start;\"><span style=\"color: rgba(0, 0, 0, 0.9); background-color: rgb(252, 252, 252); font-size: 16px;\">它最爱玩逗猫棒，追逐时轻盈跳跃的样子像一朵飘动的云，因此得名\"花花\"。花花对新鲜事物充满好奇，常常歪着头观察周围，偶尔还会发出软萌的\"喵呜\"声，仿佛在和人对话。它特别爱干净，每天都会认真梳理毛发，午后喜欢蜷在阳光下发呆，慵懒又优雅。花花不仅是家里的开心果，更是治愈心灵的温暖小天使。</span></li></ul>', '花花是一只活泼可爱的布偶猫，拥有雪白柔软的毛发和湛蓝如宝石般的大眼睛，性格温顺亲人，喜欢撒娇和蹭主人的腿。\n它最爱玩逗猫棒，追逐时轻盈跳跃的样子像一朵飘动的云，因此得名\"花花\"。花花对新鲜事物充满好奇，常常歪着头观察周围，偶尔还会发出软萌的\"喵呜\"声，仿佛在和人对话。它特别爱干净，每天都会认真梳理毛发，午后喜欢蜷在阳光下发呆，慵懒又优雅。花花不仅是家里的开心果，更是治愈心灵的温暖小天使。', 1, '2025-06-21 16:04:58');

-- ----------------------------
-- Table structure for pet_type
-- ----------------------------
DROP TABLE IF EXISTS `pet_type`;
CREATE TABLE `pet_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '宠物类别主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '宠物类别名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '宠物类别表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pet_type
-- ----------------------------
INSERT INTO `pet_type` VALUES (1, '宠物猫');
INSERT INTO `pet_type` VALUES (2, '宠物狗');
INSERT INTO `pet_type` VALUES (3, '花猫');

-- ----------------------------
-- Table structure for proposal_feedback
-- ----------------------------
DROP TABLE IF EXISTS `proposal_feedback`;
CREATE TABLE `proposal_feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '建议与反馈表主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID，外键，关联的是用户表',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `is_reply` tinyint(1) NULL DEFAULT NULL COMMENT '是否回复（0：未回复；1：已回复）',
  `reply_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '回复内容',
  `is_top` tinyint(1) NULL DEFAULT NULL COMMENT '是否是精华贴（0：否；1：是）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `reply_time` datetime NULL DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '建议与反馈信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of proposal_feedback
-- ----------------------------
INSERT INTO `proposal_feedback` VALUES (1, 1, '推荐下一个版本增加物流追踪功能', 1, '知道了！', 0, '2025-06-12 16:05:49', '2025-06-12 16:20:45');
INSERT INTO `proposal_feedback` VALUES (2, 1, '我发现评论敏感词替换有待优化，很多人擦边！！！', 1, '知道了！', 0, '2025-06-12 16:17:12', '2025-06-14 16:20:52');
INSERT INTO `proposal_feedback` VALUES (3, 59, 'wenti', 1, '4354345', 0, '2025-06-21 16:11:14', '2025-06-21 16:11:25');

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
INSERT INTO `user` VALUES (1, 'admin', 'B站「程序员辰星」原创出品', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=1756105833648Snipaste_2025-05-20_15-13-00.png', '5567643@qq.com', 1, 2, '1990-06-05', '16766666666', '2024-10-19 12:53:05');
INSERT INTO `user` VALUES (59, 'lichunran', '李春然', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17488801328625.png', '123342@qq.com', 2, 1, '1985-07-11', '17687665323', '2025-05-28 17:54:48');
INSERT INTO `user` VALUES (60, 'zhouzhiruo', '周芷若', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17488801271647.png', '23124231@qq.com', 1, 1, '1991-07-11', '16576666666', '2025-05-28 18:06:43');
INSERT INTO `user` VALUES (61, 'guihua', '桂花', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17488801204072.png', '14324@qq.com', 2, 1, NULL, NULL, '2025-05-29 14:13:04');
INSERT INTO `user` VALUES (62, 'zhangsan', '张三', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/pet-adopt-api/file/getFile?fileName=17485958396406.png', NULL, 2, NULL, NULL, NULL, '2025-05-29 15:39:59');
INSERT INTO `user` VALUES (63, 'zhangsan2', '追风筝的人', '14e1b600b1fd579f47433b88e8d85291', '/api/v1.0/house-rental-api/file/getFile?fileName=17507548294782.png', '432432@qq.com', 2, 2, '1996-07-17', '17687442010', '2025-05-29 15:41:04');

SET FOREIGN_KEY_CHECKS = 1;
