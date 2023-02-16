﻿# 一个简单的便签程序
## 使用Java springboot完成，是一个连接有数据库的窗体应用程序
## 数据库配置文件在src/main/resources/db.setting
## 数据库文件内容
CREATE DATABASE notes;
USE notes;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for things
-- ----------------------------
DROP TABLE IF EXISTS `things`;
CREATE TABLE `things`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` timestamp NULL DEFAULT NULL,
  `things` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
