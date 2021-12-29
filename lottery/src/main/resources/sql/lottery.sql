/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : localhost:3306
 Source Schema         : lottery

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : 65001

 Date: 29/12/2021 22:09:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lottery
-- ----------------------------
DROP TABLE IF EXISTS `lottery`;
CREATE TABLE `lottery`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `topic` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `state` int(11) NOT NULL DEFAULT 1 COMMENT '活动状态，1-上线，2-下线',
  `link` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `images` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lottery
-- ----------------------------
INSERT INTO `lottery` VALUES (1, '幸运大抽奖', 1, 'localhost:8080/lottery', NULL, '2021-12-01 22:06:23', '2021-12-31 22:06:28', '2021-12-01 22:06:32');

-- ----------------------------
-- Table structure for lottery_item
-- ----------------------------
DROP TABLE IF EXISTS `lottery_item`;
CREATE TABLE `lottery_item`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) NULL DEFAULT NULL COMMENT '活动id',
  `item_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖项名称',
  `level` int(11) NOT NULL COMMENT '奖项等级',
  `percent` decimal(2, 2) NOT NULL COMMENT '奖项概率',
  `prize_id` int(11) NOT NULL COMMENT '奖品id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `default_item` int(11) NULL DEFAULT 0 COMMENT '是否是默认的奖项, 0-不是 ， 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lottery_item
-- ----------------------------
INSERT INTO `lottery_item` VALUES (1, 1, '一等奖', 1, 0.02, 1, '2021-12-29 22:10:00', 0);
INSERT INTO `lottery_item` VALUES (2, 1, '二等奖', 2, 0.09, 2, '2021-12-29 22:10:00', 0);
INSERT INTO `lottery_item` VALUES (3, 1, '三等奖', 3, 0.20, 3, '2021-12-29 22:10:00', 0);
INSERT INTO `lottery_item` VALUES (4, 1, '四等奖', 4, 0.30, 4, '2021-12-29 22:10:00', 0);
INSERT INTO `lottery_item` VALUES (5, 1, '五等奖', 5, 0.40, 5, '2021-12-29 22:10:00', 0);
INSERT INTO `lottery_item` VALUES (6, 1, '六等奖', 6, 0.80, 6, '2021-12-29 22:10:00', 1);

-- ----------------------------
-- Table structure for lottery_prize
-- ----------------------------
DROP TABLE IF EXISTS `lottery_prize`;
CREATE TABLE `lottery_prize`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) NULL DEFAULT NULL COMMENT '活动ID',
  `prize_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品名称',
  `prize_type` int(11) NOT NULL COMMENT '奖品类型， -1-谢谢参与、1-普通奖品、2-唯一性奖品',
  `total_stock` int(11) NULL DEFAULT NULL COMMENT '总库存',
  `valid_stock` int(11) NULL DEFAULT NULL COMMENT '可用库存',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lottery_prize
-- ----------------------------
INSERT INTO `lottery_prize` VALUES (1, 1, '55寸小米电视', 1, 1, 1, NULL);
INSERT INTO `lottery_prize` VALUES (2, 1, 'AirPods', 1, 5, 3, NULL);
INSERT INTO `lottery_prize` VALUES (3, 1, '摄影背包', 1, 10, 9, NULL);
INSERT INTO `lottery_prize` VALUES (4, 1, '三脚架套餐', 1, 15, 15, NULL);
INSERT INTO `lottery_prize` VALUES (5, 1, '移动电源', 1, 40, 35, NULL);
INSERT INTO `lottery_prize` VALUES (6, 1, '记事本', -1, 1000, 1000, NULL);

-- ----------------------------
-- Table structure for lottery_record
-- ----------------------------
DROP TABLE IF EXISTS `lottery_record`;
CREATE TABLE `lottery_record`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `item_id` int(11) NOT NULL,
  `prize_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lottery_record
-- ----------------------------
INSERT INTO `lottery_record` VALUES (1, '192.168.1.102', 5, '移动电源', '2021-12-29 21:26:11');
INSERT INTO `lottery_record` VALUES (2, '192.168.1.102', 5, '移动电源', '2021-12-29 21:26:11');
INSERT INTO `lottery_record` VALUES (3, '192.168.1.102', 6, '记事本', '2021-12-29 21:26:11');
INSERT INTO `lottery_record` VALUES (4, '192.168.1.102', 5, '移动电源', '2021-12-29 21:26:11');
INSERT INTO `lottery_record` VALUES (5, '192.168.1.102', 6, '记事本', '2021-12-29 21:26:11');
INSERT INTO `lottery_record` VALUES (6, '0:0:0:0:0:0:0:1', 5, '移动电源', '2021-12-29 21:26:11');

SET FOREIGN_KEY_CHECKS = 1;
