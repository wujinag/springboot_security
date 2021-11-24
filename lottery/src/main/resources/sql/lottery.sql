/*
Navicat MySQL Data Transfer

Source Server         : 192.168.221.128
Source Server Version : 80025
Source Host           : 192.168.221.128:3306
Source Database       : world

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2021-07-02 22:28:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lottery
-- ----------------------------
DROP TABLE IF EXISTS `lottery`;
CREATE TABLE `lottery` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `topic` varchar(30) NOT NULL,
  `state` int NOT NULL DEFAULT '1' COMMENT '活动状态，1-上线，2-下线',
  `link` varchar(100) DEFAULT NULL,
  `images` varchar(200) DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of lottery
-- ----------------------------
INSERT INTO `lottery` VALUES ('1', '幸运大抽奖', '1', 'localhost:8080/lottery', null, '2021-07-01 22:06:23', '2021-07-31 22:06:28', '2021-07-01 22:06:32');

-- ----------------------------
-- Table structure for lottery_item
-- ----------------------------
DROP TABLE IF EXISTS `lottery_item`;
CREATE TABLE `lottery_item` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `lottery_id` int DEFAULT NULL COMMENT '活动id',
  `item_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖项名称',
  `level` int NOT NULL COMMENT '奖项等级',
  `percent` decimal(2,2) NOT NULL COMMENT '奖项概率',
  `prize_id` int NOT NULL COMMENT '奖品id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `default_item` int DEFAULT '0' COMMENT '是否是默认的奖项, 0-不是 ， 1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of lottery_item
-- ----------------------------
INSERT INTO `lottery_item` VALUES ('1', '1', '一等奖', '1', '0.02', '1', '2021-07-01 22:10:00', '0');
INSERT INTO `lottery_item` VALUES ('2', '1', '二等奖', '2', '0.09', '2', '2021-07-01 22:11:10', '0');
INSERT INTO `lottery_item` VALUES ('3', '1', '三等奖', '3', '0.20', '3', '2021-07-01 22:11:37', '0');
INSERT INTO `lottery_item` VALUES ('4', '1', '四等奖', '4', '0.30', '4', '2021-07-01 22:12:25', '0');
INSERT INTO `lottery_item` VALUES ('5', '1', '五等奖', '5', '0.40', '5', '2021-07-01 22:12:48', '0');
INSERT INTO `lottery_item` VALUES ('6', '1', '六等奖', '6', '0.80', '6', '2021-07-01 22:13:04', '1');

-- ----------------------------
-- Table structure for lottery_prize
-- ----------------------------
DROP TABLE IF EXISTS `lottery_prize`;
CREATE TABLE `lottery_prize` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `lottery_id` int DEFAULT NULL COMMENT '活动ID',
  `prize_name` varchar(30) NOT NULL COMMENT '奖品名称',
  `prize_type` int NOT NULL COMMENT '奖品类型， -1-谢谢参与、1-普通奖品、2-唯一性奖品',
  `total_stock` int DEFAULT NULL COMMENT '总库存',
  `valid_stock` int DEFAULT NULL COMMENT '可用库存',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of lottery_prize
-- ----------------------------
INSERT INTO `lottery_prize` VALUES ('1', '1', '55寸小米电视', '1', '1', '1', null);
INSERT INTO `lottery_prize` VALUES ('2', '1', 'AirPods', '1', '5', '3', null);
INSERT INTO `lottery_prize` VALUES ('3', '1', '摄影背包', '1', '10', '9', null);
INSERT INTO `lottery_prize` VALUES ('4', '1', '三脚架套餐', '1', '15', '15', null);
INSERT INTO `lottery_prize` VALUES ('5', '1', '移动电源', '1', '40', '36', null);
INSERT INTO `lottery_prize` VALUES ('6', '1', '记事本', '-1', '1000', '1000', null);

-- ----------------------------
-- Table structure for lottery_record
-- ----------------------------
DROP TABLE IF EXISTS `lottery_record`;
CREATE TABLE `lottery_record` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `account_ip` varchar(15) NOT NULL,
  `item_id` int NOT NULL,
  `prize_name` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of lottery_record
-- ----------------------------
INSERT INTO `lottery_record` VALUES ('1', '192.168.1.102', '5', '移动电源', '2021-07-02 22:21:09');
INSERT INTO `lottery_record` VALUES ('2', '192.168.1.102', '5', '移动电源', '2021-07-02 22:22:03');
INSERT INTO `lottery_record` VALUES ('3', '192.168.1.102', '6', '记事本', '2021-07-02 22:22:34');
INSERT INTO `lottery_record` VALUES ('4', '192.168.1.102', '5', '移动电源', '2021-07-02 22:22:45');
INSERT INTO `lottery_record` VALUES ('5', '192.168.1.102', '6', '记事本', '2021-07-02 22:23:00');
