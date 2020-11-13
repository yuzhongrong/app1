/*
 Navicat Premium Data Transfer

 Source Server         : fkex-wallet
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 127.0.0.1:3306
 Source Schema         : fkex

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 17/06/2020 19:40:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_wallet_param
-- ----------------------------
DROP TABLE IF EXISTS `config_wallet_param`;
CREATE TABLE `config_wallet_param`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属钱包模块',
  `param_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名',
  `param_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `param_descr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数描述',
  `status` tinyint(2) NOT NULL COMMENT '0禁用，1启用',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1104 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '钱包配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_tron_application
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_application`;
CREATE TABLE `dapp_tron_application`  (
  `app_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币币交易：CCT',
  `app_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_block_number
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_block_number`;
CREATE TABLE `dapp_tron_block_number`  (
  `block_number` int(11) NOT NULL COMMENT '区块号',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'W' COMMENT '状态(W等待,Y成功,N失败)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '同步时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`block_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '已同步的区块' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_clearing_corr
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_clearing_corr`;
CREATE TABLE `dapp_tron_clearing_corr`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末表记录id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币名称',
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包类型',
  `pre_balance` decimal(20, 8) NOT NULL COMMENT '更正前资金',
  `after_balance` decimal(20, 8) NOT NULL COMMENT '更正后资金',
  `system_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人id',
  `clearing_time` datetime(0) NOT NULL COMMENT '结算日期',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '更正记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_clearing_count_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_clearing_count_detail`;
CREATE TABLE `dapp_tron_clearing_count_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末记录id',
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动类型',
  `transfer_amount` decimal(20, 8) NOT NULL COMMENT '变动总金额,正负数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流水账记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_clearing_count_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_clearing_count_total`;
CREATE TABLE `dapp_tron_clearing_count_total`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币符号',
  `balance` decimal(20, 8) NOT NULL COMMENT '当前资金',
  `real_balance` decimal(20, 8) NOT NULL COMMENT '实际资金',
  `diff_balance` decimal(20, 8) NOT NULL COMMENT '资金偏差',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,新建(NEW)、已更正(CORR)',
  `pre_time` datetime(0) NULL DEFAULT NULL COMMENT '期初时间(上一次结算时间)',
  `pre_balance` decimal(20, 8) NOT NULL COMMENT '期初金额(上一次结算金额)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '期初期末表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_clearing_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_clearing_detail`;
CREATE TABLE `dapp_tron_clearing_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末记录id',
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动类型',
  `transfer_amount` decimal(20, 8) NOT NULL COMMENT '变动总金额,正负数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流水账记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_clearing_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_clearing_total`;
CREATE TABLE `dapp_tron_clearing_total`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币符号',
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包类型',
  `balance` decimal(20, 8) NOT NULL COMMENT '当前资金',
  `real_balance` decimal(20, 8) NOT NULL COMMENT '实际资金',
  `diff_balance` decimal(20, 8) NOT NULL COMMENT '资金偏差',
  `wallet_last_time` datetime(0) NOT NULL COMMENT '最后一次资金变动时间',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,新建(NEW)、已更正(CORR)',
  `pre_time` datetime(0) NOT NULL COMMENT '期初时间(上一次结算时间)',
  `pre_balance` decimal(20, 8) NOT NULL COMMENT '期初金额(上一次结算金额)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '期初期末表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_collection_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_collection_transfer`;
CREATE TABLE `dapp_tron_collection_transfer`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
  `hash` varchar(66) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '充值或提现需保存的hash值',
  `from_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付人地址',
  `to_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收款人地址',
  `amount` decimal(36, 18) NOT NULL COMMENT '金额',
  `token_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gas_price` decimal(36, 18) NULL DEFAULT NULL COMMENT '收取的手续费',
  `status` tinyint(4) NOT NULL COMMENT '状态，0失败，1成功，5打包',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'TRON归集历史记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_token
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_token`;
CREATE TABLE `dapp_tron_token`  (
  `token_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种地址',
  `token_hex_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种16进制地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '如EOS,ZOS',
  `token_decimal` int(11) NOT NULL COMMENT '小数位',
  `issue_time` datetime(0) NULL DEFAULT NULL COMMENT '发行时间',
  `total_supply` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行总量',
  `total_circulation` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流通量',
  `descr` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`token_addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '币种表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_transfer_auditing
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_transfer_auditing`;
CREATE TABLE `dapp_tron_transfer_auditing`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台操作用户id',
  `ip_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台操作用户的ip地址',
  `transfer_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的提现记录id',
  `transfer_status` tinyint(4) NOT NULL COMMENT '1成功，2待初审提币，3待复审提币，4待出币，5已出币，6出币失败(1为出币成功)，7审核不通过',
  `create_time` datetime(0) NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提币审核记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet`;
CREATE TABLE `dapp_tron_wallet`  (
  `addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'tron托管钱包地址',
  `hex_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '16进制地址',
  `token_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '如eosio.token，zosdiscounts',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '如EOS,ZOS',
  `token_decimal` int(11) NOT NULL COMMENT '小数位',
  `balance` decimal(36, 18) NOT NULL COMMENT '余额',
  `free_balance` decimal(36, 18) NOT NULL COMMENT '可用余额',
  `freeze_balance` decimal(36, 18) NOT NULL COMMENT '冻结余额',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'CCT:币币交易钱包',
  PRIMARY KEY (`addr`, `token_addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '托管钱包表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_apply
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_apply`;
CREATE TABLE `dapp_tron_wallet_apply`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `app_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用标识',
  `app_secret` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '安全码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_create
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_create`;
CREATE TABLE `dapp_tron_wallet_create`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'base58地址',
  `hex_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '16进制地址',
  `token_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `private_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户私钥',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资金钱包备注',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '创建资金钱包(用于创建钱包)' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_inform
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_inform`;
CREATE TABLE `dapp_tron_wallet_inform`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `params_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数来源标识（支付id）',
  `params_json` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数信息',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求地址',
  `time` tinyint(1) NOT NULL DEFAULT 5 COMMENT '请求次数',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '请求状态[ 0( 未处理 ),1( 成功 ) ]',
  `inform_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知类型[ PAY(支付通知) ]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_key
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_key`;
CREATE TABLE `dapp_tron_wallet_key`  (
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `hex_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '16进制地址',
  `private_key` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '托管钱包私钥',
  `keystore` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'keystore',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_open_id`, `addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_key_init
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_key_init`;
CREATE TABLE `dapp_tron_wallet_key_init`  (
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `hex_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '16进制地址',
  `private_key` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '托管钱包私钥',
  `keystore` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'keystore',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_out
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_out`;
CREATE TABLE `dapp_tron_wallet_out`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'base58地址',
  `hex_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '16进制地址',
  `token_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `token_decimals` int(11) NOT NULL COMMENT '币种小数点',
  `private_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户私钥',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提现审核通过需要输入的密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资金钱包备注',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提现资金钱包(用于用户提现)' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_prepayment
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_prepayment`;
CREATE TABLE `dapp_tron_wallet_prepayment`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `app_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用标识',
  `app_secret` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '安全码',
  `open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `trade_no` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单唯一标识',
  `notify_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知请求',
  `coin_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数字货币名称',
  `coin_address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数字货币地址',
  `amount` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `sign` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '签名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '预支付记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_tron_wallet_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dapp_tron_wallet_transfer`;
CREATE TABLE `dapp_tron_wallet_transfer`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hash` varchar(66) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充值或提现需保存的hash值',
  `from_hex_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-1' COMMENT '支付人地址(空为提现)',
  `to_hex_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款人地址(空为充值)',
  `amount` decimal(36, 6) NOT NULL COMMENT '金额',
  `token_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gas_price` bigint(36) NULL DEFAULT NULL COMMENT '收取的手续费',
  `gas_token_type` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费币种类型，BTC、ETH、EOS',
  `gas_token_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费币种名称',
  `gas_token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型：OUT提现，IN充值，CCT币币交易，GAS手续费',
  `status` tinyint(4) NOT NULL COMMENT '0失败，1成功，2待初审提币，3待复审提币，4待出币，5已出币，6出币失败(1为出币成功)，7审核不通过',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户钱包历史记录' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
