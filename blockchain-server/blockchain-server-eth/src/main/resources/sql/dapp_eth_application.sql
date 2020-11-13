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

 Date: 17/06/2020 19:34:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dapp_eth_application
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_application`;
CREATE TABLE `dapp_eth_application`  (
  `app_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币币交易：CCT',
  `app_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_block_number
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_block_number`;
CREATE TABLE `dapp_eth_block_number`  (
  `block_number` int(11) NOT NULL COMMENT '区块号',
  `create_time` datetime(0) NOT NULL COMMENT '同步时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Y成功，N失败',
  PRIMARY KEY (`block_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '已同步的区块' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_clearing_corr
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_clearing_corr`;
CREATE TABLE `dapp_eth_clearing_corr`  (
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '更正记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_clearing_count_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_clearing_count_detail`;
CREATE TABLE `dapp_eth_clearing_count_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末记录id',
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动类型',
  `transfer_amount` decimal(20, 8) NOT NULL COMMENT '变动总金额,正负数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流水账记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_clearing_count_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_clearing_count_total`;
CREATE TABLE `dapp_eth_clearing_count_total`  (
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '期初期末表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_clearing_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_clearing_detail`;
CREATE TABLE `dapp_eth_clearing_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末记录id',
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动类型',
  `transfer_amount` decimal(20, 8) NOT NULL COMMENT '变动总金额,正负数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流水账记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_clearing_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_clearing_total`;
CREATE TABLE `dapp_eth_clearing_total`  (
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '期初期末表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_collection_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_collection_transfer`;
CREATE TABLE `dapp_eth_collection_transfer`  (
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ETH归集历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_gas_wallet
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_gas_wallet`;
CREATE TABLE `dapp_eth_gas_wallet`  (
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `private_key` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '托管钱包私钥',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_paradrop
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_paradrop`;
CREATE TABLE `dapp_eth_paradrop`  (
  `id` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `send_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Y -- 开启 ，N -- 关闭',
  `send_count` int(11) NOT NULL COMMENT '发送总额',
  `send_amount` int(11) NOT NULL COMMENT '单笔发送额度',
  `send_ratio` int(2) NOT NULL COMMENT '上下起伏百分比',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代币发放配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_paradrop_addr
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_paradrop_addr`;
CREATE TABLE `dapp_eth_paradrop_addr`  (
  `send_addr` char(42) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `send_pk` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包私钥',
  `status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Y--禁用，D--启用',
  `desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息描述',
  PRIMARY KEY (`send_addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代币发放配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_private_balance
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_private_balance`;
CREATE TABLE `dapp_eth_private_balance`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ETH托管钱包地址',
  `token_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种名称',
  `private_balance` decimal(36, 18) NOT NULL COMMENT '私募资金',
  `release_balance` decimal(36, 18) NOT NULL COMMENT '每次释放金额',
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包类型',
  `create_time` datetime(0) NOT NULL,
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '托管钱包表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_to_addr
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_to_addr`;
CREATE TABLE `dapp_eth_to_addr`  (
  `addr` char(42) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发币地址',
  `pk` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发币私钥',
  PRIMARY KEY (`addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代币发放配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_token
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_token`;
CREATE TABLE `dapp_eth_token`  (
  `token_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ETH为ETH，其它为代币地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ETH，BNB',
  `token_decimals` int(11) NULL DEFAULT NULL COMMENT '币种小数',
  `issue_time` datetime(0) NULL DEFAULT NULL COMMENT '发行时间',
  `total_supply` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行总量',
  `total_circulation` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流通量',
  `descr` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`token_addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '币种表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_transfer_auditing
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_transfer_auditing`;
CREATE TABLE `dapp_eth_transfer_auditing`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台操作用户id',
  `ip_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台操作用户的ip地址',
  `transfer_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的提现记录id',
  `transfer_status` tinyint(4) NOT NULL COMMENT '1成功，2待初审提币，3待复审提币，4待出币，5已出币，6出币失败(1为出币成功)，7审核不通过',
  `create_time` datetime(0) NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提币审核记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_wallet
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_wallet`;
CREATE TABLE `dapp_eth_wallet`  (
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ETH托管钱包地址',
  `token_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种地址',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_decimals` int(11) NOT NULL COMMENT '币种小数',
  `balance` decimal(36, 20) NOT NULL COMMENT '余额',
  `free_balance` decimal(36, 18) NOT NULL COMMENT '可用余额',
  `freeze_balance` decimal(36, 18) NOT NULL COMMENT '冻结余额',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'CCT:币币交易钱包',
  PRIMARY KEY (`addr`, `token_addr`) USING BTREE,
  INDEX `key2`(`addr`) USING BTREE,
  INDEX `key3`(`token_addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '托管钱包表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_wallet_blocked_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_wallet_blocked_detail`;
CREATE TABLE `dapp_eth_wallet_blocked_detail`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `wallet_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包唯一标识(地址|id)',
  `token_symbol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币名称',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `opt_total` decimal(36, 18) NOT NULL COMMENT '操作数量',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型：冻结(BLOCK)、解冻(UNBLOCK)',
  `system_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的后台管理人员id',
  `ip_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作的ip地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '钱包冻结明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_wallet_blocked_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_wallet_blocked_total`;
CREATE TABLE `dapp_eth_wallet_blocked_total`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `wallet_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包唯一标识(地址|id)',
  `token_symbol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币名称',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `blocked_total` decimal(36, 18) NOT NULL COMMENT '冻结的总额，解冻时不能超过该值',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  UNIQUE INDEX `uk_wallet_uid_token_symbol`(`wallet_uid`, `token_symbol`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '钱包冻结总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_wallet_key
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_wallet_key`;
CREATE TABLE `dapp_eth_wallet_key`  (
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `private_key` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '托管钱包私钥',
  `keystore` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'keystore',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_open_id`, `addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_wallet_out
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_wallet_out`;
CREATE TABLE `dapp_eth_wallet_out`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `addr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户地址',
  `token_addr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `token_decimals` int(11) NOT NULL,
  `keystore` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户keystore',
  `private_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户私钥',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提现审核通过需要输入的密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资金钱包备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提现资金钱包(用于用户提现)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_eth_wallet_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dapp_eth_wallet_transfer`;
CREATE TABLE `dapp_eth_wallet_transfer`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hash` varchar(66) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '充值或提现需保存的hash值',
  `from_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付人地址',
  `to_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收款人地址',
  `amount` decimal(36, 18) NOT NULL COMMENT '金额',
  `token_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gas_price` decimal(36, 18) NULL DEFAULT NULL COMMENT '收取的手续费',
  `gas_token_type` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费币种类型，BTC、ETH、EOS',
  `gas_token_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费币种名称',
  `gas_token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型：OUT提现，IN充值，CCT币币交易，GAS手续费，TXMIN内部转账（自身），TXOIN内部转账（他人）',
  `status` tinyint(4) NOT NULL COMMENT '0失败，1成功，2待初审提币，3待复审提币，4待出币，5已出币，6出币失败(1为出币成功)，7审核不通过',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_01`(`from_addr`) USING BTREE,
  INDEX `key_02`(`to_addr`) USING BTREE,
  INDEX `key_03`(`token_addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户钱包历史记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
