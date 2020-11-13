/*
 Navicat Premium Data Transfer

 Source Server         : fkex-高防内外
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 47.75.92.218:3306
 Source Schema         : fkex

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 17/06/2020 19:50:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for imjg_message
-- ----------------------------
DROP TABLE IF EXISTS `imjg_message`;
CREATE TABLE `imjg_message`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '极光IM消息体数据表',
  `extras` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息所带的额外参数,JSON',
  `msg_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型(1:text,2:voice,3:image,4:file, 5:video,6:location, 7:custom)',
  `text` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '(text)消息内容',
  `media_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '(voice/image/file)文件url',
  `media_crc32` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '(voice/image/file)文件的 CRC32 校验码',
  `duration` int(11) NULL DEFAULT NULL COMMENT '(voice)语音时长(秒)',
  `format` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '(voice/image/)文件后缀',
  `fsize` int(11) NULL DEFAULT NULL COMMENT '(voice/image/file)文件大小',
  `width` int(11) NULL DEFAULT NULL COMMENT '(image)文件宽度',
  `height` int(11) NULL DEFAULT NULL COMMENT '(image)文件长度',
  `fname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '(file)文件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4691 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for imjg_message_log
-- ----------------------------
DROP TABLE IF EXISTS `imjg_message_log`;
CREATE TABLE `imjg_message_log`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `data_id` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_type` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `msg_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型(1:text,2:voice,3:image,4:file, 5:video,6:location, 7:custom)',
  `target_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收方UserId',
  `from_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送方UserId',
  `node_cue` tinyint(2) NULL DEFAULT NULL COMMENT '节点提示词(0:不是节点提示词,1:两边均可见的提示词,2:发送方可见的节点提示词)',
  `status` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息状态(\nNORMAL:正常,\nCANCEL:已删除)',
  `msg_id` int(11) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4691 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for imjg_user
-- ----------------------------
DROP TABLE IF EXISTS `imjg_user`;
CREATE TABLE `imjg_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `jg_username` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '极光账号登录名',
  `jg_password` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '极光账号密码',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
