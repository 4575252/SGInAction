/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.20.145-ruiji-master
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 192.168.20.145:3306
 Source Schema         : ssmtest

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 19/10/2022 17:58:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'UZI', 19, '上海');
INSERT INTO `user` VALUES (2, 'PDD', 25, '上海');

SET FOREIGN_KEY_CHECKS = 1;
