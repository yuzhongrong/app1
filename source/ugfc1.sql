/*
 Navicat Premium Data Transfer

 Source Server         : 交易所成品程总136
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 127.0.0.1:3306
 Source Schema         : ugfc

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 06/08/2020 10:04:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bot_currency_config
-- ----------------------------
DROP TABLE IF EXISTS `bot_currency_config`;
CREATE TABLE `bot_currency_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `k_change_percent` float NOT NULL COMMENT 'K线配置：日涨跌幅',
  `k_max_change_percent` float NOT NULL COMMENT 'K线配置：涨跌最大幅度（偏差）',
  `k_day_total_amount` float NOT NULL COMMENT 'K线配置：日发行总量',
  `k_max_price` float NOT NULL COMMENT 'K线配置：最高单价',
  `k_min_price` float NOT NULL COMMENT 'K线配置：最低单价',
  `buy_max_price` float NOT NULL COMMENT '深度配置：买盘最高单价',
  `buy_min_price` float NOT NULL COMMENT '深度配置：买盘最低单价',
  `buy_price_percent` float NOT NULL COMMENT '深度配置：买盘单价范围',
  `buy_total_amount` float NOT NULL COMMENT '深度配置：买盘发行总量',
  `sell_max_price` float NOT NULL COMMENT '深度配置：卖盘最高单价',
  `sell_min_price` float NOT NULL COMMENT '深度配置：卖盘最低单价',
  `sell_price_percent` float NOT NULL COMMENT '深度配置：卖盘单价范围',
  `sell_total_amount` float NOT NULL COMMENT '深度配置：卖盘发行总量',
  `price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单价范围规则类型：（定价范围：PRICE，最新成交价波动范围：PERCENT）',
  `create_time` datetime(0) NOT NULL,
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bot_currency_config_copy1
-- ----------------------------
DROP TABLE IF EXISTS `bot_currency_config_copy1`;
CREATE TABLE `bot_currency_config_copy1`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `k_change_percent` float NOT NULL COMMENT 'K线配置：日涨跌幅',
  `k_max_change_percent` float NOT NULL COMMENT 'K线配置：涨跌最大幅度（偏差）',
  `k_day_total_amount` float NOT NULL COMMENT 'K线配置：日发行总量',
  `k_max_price` float NOT NULL COMMENT 'K线配置：最高单价',
  `k_min_price` float NOT NULL COMMENT 'K线配置：最低单价',
  `buy_max_price` float NOT NULL COMMENT '深度配置：买盘最高单价',
  `buy_min_price` float NOT NULL COMMENT '深度配置：买盘最低单价',
  `buy_price_percent` float NOT NULL COMMENT '深度配置：买盘单价范围',
  `buy_total_amount` float NOT NULL COMMENT '深度配置：买盘发行总量',
  `sell_max_price` float NOT NULL COMMENT '深度配置：卖盘最高单价',
  `sell_min_price` float NOT NULL COMMENT '深度配置：卖盘最低单价',
  `sell_price_percent` float NOT NULL COMMENT '深度配置：卖盘单价范围',
  `sell_total_amount` float NOT NULL COMMENT '深度配置：卖盘发行总量',
  `price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单价范围规则类型：（定价范围：PRICE，最新成交价波动范围：PERCENT）',
  `create_time` datetime(0) NOT NULL,
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bot_currency_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `bot_currency_config_handle_log`;
CREATE TABLE `bot_currency_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对',
  `handle_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型（UPDATE更新、INSERT新增）',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_k_change_percent` float NULL DEFAULT NULL COMMENT '修改前的K线配置：日涨跌幅',
  `after_k_change_percent` float NULL DEFAULT NULL COMMENT '修改后的K线配置：日涨跌幅',
  `before_k_max_change_percent` float NULL DEFAULT NULL COMMENT '修改前的K线配置：涨跌最大幅度（偏差）',
  `after_k_max_change_percent` float NULL DEFAULT NULL COMMENT '修改后的K线配置：涨跌最大幅度（偏差）',
  `before_k_day_total_amount` float NULL DEFAULT NULL COMMENT '修改前的K线配置：日发行总量',
  `after_k_day_total_amount` float NULL DEFAULT NULL COMMENT '修改后的K线配置：日发行总量',
  `before_k_max_price` float NULL DEFAULT NULL COMMENT '修改前的K线配置：最高单价',
  `after_k_max_price` float NULL DEFAULT NULL COMMENT '修改后的K线配置：最高单价',
  `before_k_min_price` float NULL DEFAULT NULL COMMENT '修改前的K线配置：最低单价',
  `after_k_min_price` float NULL DEFAULT NULL COMMENT '修改后的K线配置：最低单价',
  `before_buy_max_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘最高单价',
  `after_buy_max_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘最高单价',
  `before_buy_min_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘最低单价',
  `after_buy_min_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘最低单价',
  `before_buy_price_percent` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘单价范围',
  `after_buy_price_percent` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘单价范围',
  `before_buy_total_amount` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘发行总量',
  `after_buy_total_amount` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘发行总量',
  `before_sell_max_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘最高单价',
  `after_sell_max_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘最高单价',
  `before_sell_min_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘最低单价',
  `after_sell_min_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘最低单价',
  `before_sell_price_percent` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘单价范围',
  `after_sell_price_percent` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘单价范围',
  `before_sell_total_amount` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘发行总量',
  `after_sell_total_amount` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘发行总量',
  `before_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的单价范围规则类型',
  `after_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的单价范围规则类型',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bot_match_config
-- ----------------------------
DROP TABLE IF EXISTS `bot_match_config`;
CREATE TABLE `bot_match_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `min_price` decimal(20, 8) NOT NULL COMMENT '最低单价',
  `max_price` decimal(20, 8) NOT NULL COMMENT '最高单价',
  `min_percent` decimal(20, 8) NOT NULL COMMENT '最新成交价跌幅',
  `max_percent` decimal(20, 8) NOT NULL COMMENT '最新成交价涨幅',
  `price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单价范围规则类型：（定价范围：PRICE，最新成交价波动范围：PERCENT）',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态：（启用：Y，禁用：N）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bot_match_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `bot_match_config_handle_log`;
CREATE TABLE `bot_match_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的用户id',
  `after_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的用户id',
  `before_coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的基本货币',
  `after_coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的基本货币',
  `before_unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的二级货币',
  `after_unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的二级货币',
  `before_min_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最低单价',
  `after_min_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最低单价',
  `before_max_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最高单价',
  `after_max_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最高单价',
  `before_min_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最新成交价跌幅',
  `after_min_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最新成交价跌幅',
  `before_max_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最新成交价涨幅',
  `after_max_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最新成交价涨幅',
  `before_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的单价范围类型',
  `after_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的单价范围类型',
  `before_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `handle_type` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型（更新：UPDATE，新增：INSERT）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_about_us
-- ----------------------------
DROP TABLE IF EXISTS `conf_about_us`;
CREATE TABLE `conf_about_us`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `text_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文本内容',
  `languages` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否英文  en_US:英文 zh_CN:汉语',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '关于我们表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_contact_us
-- ----------------------------
DROP TABLE IF EXISTS `conf_contact_us`;
CREATE TABLE `conf_contact_us`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contact_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `contact_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `rank` tinyint(3) NOT NULL COMMENT '排序',
  `user_local` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语种',
  `show_status` tinyint(10) NULL DEFAULT NULL COMMENT '是否隐藏 隐藏:0，显示:1',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '联系我们表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_help_center
-- ----------------------------
DROP TABLE IF EXISTS `conf_help_center`;
CREATE TABLE `conf_help_center`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原文地址',
  `rank` int(2) NULL DEFAULT NULL COMMENT '排序',
  `user_local` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国际化标识',
  `show_status` tinyint(1) NULL DEFAULT NULL COMMENT '展示状态：0-隐藏，1-显示',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '帮助中心表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_help_center
-- ----------------------------
INSERT INTO `conf_help_center` VALUES ('102a3c15-cc86-40de-b83b-98bf1336620b', '法币交易', '<h1><strong>法币交易</strong></h1><p class=\"ql-align-justify\">1、申请成为商家</p><p class=\"ql-align-justify\">用户通过高级身份认证后，才可申请成为商家，通过后台审核成为商家后，才可发布广告。</p><p class=\"ql-align-justify\">（1）点击法币交易页面右上角的按钮，查看商家规则。</p><p class=\"ql-align-justify\">（2）勾选按钮，冻结10000个USDT后提交商家身份申请。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2、申请取消商家</p><p class=\"ql-align-justify\">（1）成为商家后，点击法币交易页面右上角的按钮，可申请取消商家。</p><p class=\"ql-align-justify\">（2）成功取消商家身份后，冻结的10000个USDT保证金将退还到法币账户的可用余额中。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3、发布广告</p><p>点击法币页面右上角按钮选择发布广告，进入法币广告页面，选择发布广告类型（购买/出售）及币种，输入广告单价、数量、订单最小限额，数量输入后自动生成最大限额。选择支付方式（支付宝、微信、银行卡），备注信息为选填，输入资金密码，点击‘立即发布’即可。</p><p><br></p><p class=\"ql-align-justify\">4、广告管理</p><p>（1）点击广告管理，查看已发布的广告信息。并可根据币种（ETH、BTC等）、广告状态（进行中、已完成、已撤销）、广告类型（购买、出售）进行筛选查询。</p><p>2）状态为挂单中的广告，左滑可操作下架。</p><p>下架中的广告左滑可重新上架。</p><p>（2）挂单中、下架中的广告左滑可进行撤销。</p><p>注：广告如有在进行中，未完成的子单不可操作撤销。</p><p><br></p><p class=\"ql-align-justify\">5、购买</p><p>（1）若用户具有商家身份，则点击法币交易页面右上角，发布购买广告。</p><p>（2）若用户没有商家身份，则在广告大厅点击购买页面，查看对应广告，点击广告的购买按钮，输入买入金额或数量（只需填写其一，系统自动转换成相同价值的金额或数量），点击立即购买，即可下单成功。</p><p>（注：订单本身有限制可用的支付方式）</p><p>（3）下单成功自动跳转支付操作页面，选择对应的支付方式，点击‘去支付’，使用微信或支付宝扫描卖家的收款二维码转账（或使用银行号卡号转账），完成支付后点击‘已完成付款’，等待卖家确认收款放币即可。</p><p>注：提交信息即生成订单，请在10分钟内完成付款。</p><p>（4）申诉：如确认付款后，卖家过久不确认收款，无法获取代币，可在订单管理--点击对应的订单进行申诉，填写申诉说明并上传凭证，点击提交申诉，等待后台审核处理即可。</p><p>（5）取消订单：未付款的订单可以点击取消订单。若是已经付款的订单取消则会损失已付款资金。</p><p><br></p><p>6、聊天：点击左下角对话按钮，买卖双方可进行聊天。</p><p><br></p><p class=\"ql-align-justify\">7、出售</p><p>①若用户具有商家身份，则点击法币交易页面右上角，发布出售广告。</p><p>②若用户没有商家身份，则在广告大厅点击出售页面，查看对应广告，点击广告的购买按钮，输入出售金额或数量（只需填写其一，系统自动转换成相同价值的金额或数量），点击立即出售，输入资金密码，确认密码即下单成功，等待买家付款。</p><p><br></p><p class=\"ql-align-justify\">8、订单管理</p><p class=\"ql-align-justify\">（1）点击法币交易页面右上角的图标，查看订单记录，选择上方的状态栏，查看不同状态的订单。</p><p>（2）点击某个订单记录，查看订单详情页。</p><p class=\"ql-align-justify\"><br></p><h1><br></h1>', '', 1, 'zh_CN', 1, '2019-11-01 10:02:45', '2019-11-01 10:02:45');
INSERT INTO `conf_help_center` VALUES ('522ad9a3-bd30-4410-bc82-aa2c45fa07d4', 'English', '<p>eeeeee</p><p><br></p>', '', 1, 'en_US', 1, '2019-10-29 10:32:48', '2019-10-29 10:32:48');
INSERT INTO `conf_help_center` VALUES ('62f5f183-a021-4cb7-986b-c1ba912ea8f3', '币币交易', '<h1><strong>币币交易</strong></h1><p>1、买入</p><p>（1）点击左上角按钮，选择币种。点击左上角买入，可选限价或市价，输入单价及数量信息，点击买入，输入密码，点击确认即发布成功，。</p><p>（2）发布的订单如未撮合成交显示在‘最近委托’，如已发布撮合成交即显示在‘历史’记录。</p><p>（注：绿色字体为买入，红色字体为卖出）</p><p>（3）在‘最近委托’里撤销已发布订单，即可取消订单，‘最近委托’仅显示三条未完成订单记录，如有多条可在‘挂单’处查看。</p><p>注：市价订单发布成功，如盘口订单符合条件即时撮合，如盘口订单无法满足条件撮合，所发布的市价订单即时撤销。</p><p><br></p><p>2、卖出</p><p>点击卖出，选择限价或市价，输入卖出单价及数量后，输入支付密码即发布成功。</p><p class=\"ql-align-center\"><br></p><p>3、挂单</p><p>点击挂单,查看已发布但未完成的订单记录。</p><p class=\"ql-align-center\"><br></p><p>4、历史</p><p>点击历史，查看已完成订单记录</p>', '', 1, 'zh_CN', 1, '2019-10-22 09:56:08', '2019-10-22 09:56:08');
INSERT INTO `conf_help_center` VALUES ('6c2aa6dc-105f-4f02-ba99-c4576f6f201f', '繁体', '<p>繁体</p>', '', 1, 'zh_HK', 1, '2019-10-26 17:51:18', '2019-10-26 17:51:18');

-- ----------------------------
-- Table structure for conf_information_data
-- ----------------------------
DROP TABLE IF EXISTS `conf_information_data`;
CREATE TABLE `conf_information_data`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `data_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '键',
  `data_tag` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '说明',
  `data_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `data_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组名',
  `rank` int(2) NOT NULL COMMENT '排序',
  `language` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文zh_CN，英文en_US',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配置说明详情' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_news
-- ----------------------------
DROP TABLE IF EXISTS `conf_news`;
CREATE TABLE `conf_news`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布人ID',
  `type` tinyint(1) NOT NULL COMMENT '文章类型',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原文地址',
  `languages` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国际化标识',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告/快讯表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_project_center_classify
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_center_classify`;
CREATE TABLE `conf_project_center_classify`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目中心类别',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `languages` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国际化标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_project_center_classify
-- ----------------------------
INSERT INTO `conf_project_center_classify` VALUES ('2a0514bc-5985-41e1-a34c-a8678ea533aa', '2', 'Y', '2019-11-09 15:10:04', '2019-11-09 15:10:04', 'en_US');
INSERT INTO `conf_project_center_classify` VALUES ('5a8c15c8-8c93-4961-bbc9-43b879c8b2cb', '1', 'Y', '2019-11-09 15:09:28', '2019-11-09 15:09:28', 'zh_CN');

-- ----------------------------
-- Table structure for conf_project_center_info
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_center_info`;
CREATE TABLE `conf_project_center_info`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数字货币类型，BTC、ETH、EOS',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `issue_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行时间',
  `total_supply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行总量',
  `total_circulation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流通量',
  `ico_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '众筹价格',
  `white_paper` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '白皮书',
  `presentation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '介绍',
  `descr` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简介',
  `coin_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'W 文件 L 链接',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
  `languages` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国际化标识',
  `classify_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类id',
  `uccn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '官网',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_project_center_report
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_center_report`;
CREATE TABLE `conf_project_center_report`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目点赞表',
  `project_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目id',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WEEK 周报 MONTH 月报 SPECIAL专题报告 FINANCIAL 财务报告',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件地址',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for conf_project_center_star
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_center_star`;
CREATE TABLE `conf_project_center_star`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目点赞表',
  `project_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目id',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点赞人id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_project_center_star
-- ----------------------------
INSERT INTO `conf_project_center_star` VALUES ('c57ba94f-241f-42b3-9969-b349ec901260', '64a8878d-c802-4666-a58f-e9cc95e0e2af', '1f004984-b694-46b9-8eb8-88f6d4bd1463', '2019-11-09 15:26:24');

-- ----------------------------
-- Table structure for conf_system_image
-- ----------------------------
DROP TABLE IF EXISTS `conf_system_image`;
CREATE TABLE `conf_system_image`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `file_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `jump_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转地址',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
  `rank` tinyint(4) NULL DEFAULT NULL COMMENT '序号',
  `type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'APP/PC',
  `group` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `language` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言zh_CN中文en_US英语',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_system_image
-- ----------------------------
INSERT INTO `conf_system_image` VALUES ('0b76f9c9-4dca-496a-b6f9-5b9873dabe20', 'sysconf/img/202007161945303448PQhWN.jpg', '', 'Y', 1, 'PC', 'INDEX', '2020-07-16 19:45:31', '2020-07-16 19:45:31', 'en_US');
INSERT INTO `conf_system_image` VALUES ('0f8c6016-ee9c-4538-af1a-a7efcb5f84a9', 'sysconf/img/20200727092141209f2TaBk.jpg', '', 'Y', 1, 'APP', 'LOTTERY', '2020-07-27 09:21:41', '2020-07-27 09:21:41', 'en_US');
INSERT INTO `conf_system_image` VALUES ('119b607b-af8e-4d56-b9db-f44f1ec63111', 'sysconf/img/20200717113412948coVNNk.jpg', '', 'Y', 1, 'APP', 'INDEX', '2020-07-17 11:34:14', '2020-07-17 11:34:14', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('19d79f63-8023-48ba-ab21-6a768e11b56e', 'sysconf/img/202007171134525384i4Lct.jpg', '', 'Y', 1, 'APP', 'LOTTERY', '2020-07-17 11:34:53', '2020-07-17 11:34:53', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('4a921158-15dd-4616-9108-04afd2b4b752', 'sysconf/img/20200717113533430Em23M3.jpg', '', 'Y', 1, 'APP', 'LOTTERY', '2020-07-17 11:35:34', '2020-07-17 11:35:34', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('4af7073a-4d62-4bbc-88f6-032cc1612f0f', 'sysconf/img/20200716203151449xY5VM0.jpg', '', 'Y', 1, 'PC', 'INDEX', '2020-07-16 20:31:52', '2020-07-16 20:31:52', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('4c6d412c-4dfc-43da-925c-388ee6f21813', 'sysconf/img/202007162029341698FFQ33.jpg', '', 'Y', 1, 'PC', 'LOTTERY', '2020-07-16 20:29:34', '2020-07-16 20:29:34', 'en_US');
INSERT INTO `conf_system_image` VALUES ('4e672642-3785-4139-a3a0-2ef2b672dca0', 'sysconf/img/20200717113523796tyM0GC.jpg', '', 'Y', 1, 'APP', 'INDEX', '2020-07-17 11:35:24', '2020-07-17 11:35:24', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('65f3a3e5-abd6-4381-8240-4b52a8daf5ab', 'sysconf/img/20200716203027656x4r2df.jpg', '', 'Y', 1, 'PC', 'LOTTERY', '2020-07-16 20:30:28', '2020-07-16 20:30:28', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('6de0caed-0506-4afc-b5a7-745a86391edf', 'sysconf/img/20200727092055286tb1ndg.jpg', '', 'Y', 1, 'APP', 'LOTTERY', '2020-07-27 09:20:56', '2020-07-27 09:20:56', 'en_US');
INSERT INTO `conf_system_image` VALUES ('85d76111-f9fb-4c2f-8361-7a188e9d489f', 'sysconf/img/20200727091749215Ed2x95.jpg', '', 'Y', 1, 'APP', 'INDEX', '2020-07-27 09:17:50', '2020-07-27 09:17:50', 'en_US');
INSERT INTO `conf_system_image` VALUES ('9ae962e7-9c60-4404-9fc1-59af9eb7e7f8', 'sysconf/img/20200716203138989wo9RG5.jpg', '', 'Y', 1, 'PC', 'INDEX', '2020-07-16 20:31:39', '2020-07-16 20:31:39', 'en_US');
INSERT INTO `conf_system_image` VALUES ('9c14ac91-18d2-458b-9a27-5ad637ff2378', 'sysconf/img/20200716203016712O6YkN4.jpg', '', 'Y', 1, 'PC', 'LOTTERY', '2020-07-16 20:30:17', '2020-07-16 20:30:17', 'en_US');
INSERT INTO `conf_system_image` VALUES ('b8461392-b7ef-4553-ab6a-efe8a9278e17', 'sysconf/img/20200727092034991Hg4zYt.jpg', '', 'Y', 1, 'APP', 'INDEX', '2020-07-27 09:20:35', '2020-07-27 09:20:35', 'en_US');
INSERT INTO `conf_system_image` VALUES ('df6f5bd3-2c29-4f4d-8c77-e7b112d7qq', 'sysconf/img/20191014165727274f6JZ8a.jpg', '', 'Y', 1, 'APP', 'POOL', '2019-10-12 16:37:56', '2019-10-14 16:57:27', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('efbbc7db-a8dd-4ed8-9b00-4b226c25cba4', 'sysconf/img/20200716202945221AR0L3X.jpg', '', 'Y', 1, 'PC', 'LOTTERY', '2020-07-16 20:29:45', '2020-07-16 20:29:45', 'zh_CN');
INSERT INTO `conf_system_image` VALUES ('feb97814-71a0-4311-acb6-054a8faf1204', 'sysconf/img/20200716202141722irzPMw.jpg', '', 'Y', 1, 'PC', 'INDEX', '2020-07-16 20:21:42', '2020-07-16 20:21:42', 'zh_CN');

-- ----------------------------
-- Table structure for conf_system_notice
-- ----------------------------
DROP TABLE IF EXISTS `conf_system_notice`;
CREATE TABLE `conf_system_notice`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '标题',
  `details` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公告内容',
  `jump_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转地址',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
  `rank` tinyint(4) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `languages` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国际化标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_system_notice
-- ----------------------------
INSERT INTO `conf_system_notice` VALUES ('47e69211-c3b9-4407-baca-1002702a7cac', 'DUOBIT正式上线', '<p>DUOBIT正式上线啦</p>', '', 'Y', 1, '2020-07-17 10:27:16', '2020-07-17 10:27:16', 'zh_CN');
INSERT INTO `conf_system_notice` VALUES ('a97e6d0b-f2c9-4c77-b205-6427090885e7', '1.1.1版本更新通信', '<p>修复了已知bug</p>', '', 'Y', 2, '2020-07-17 10:28:03', '2020-07-17 10:28:03', 'zh_CN');

-- ----------------------------
-- Table structure for conf_user_agreement
-- ----------------------------
DROP TABLE IF EXISTS `conf_user_agreement`;
CREATE TABLE `conf_user_agreement`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `text_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '协议内容',
  `languages` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否英文  en_US:英文 zh_CN:汉语',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议类型 user用户协议 privacy隐私协议',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户协议表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_user_agreement
-- ----------------------------
INSERT INTO `conf_user_agreement` VALUES ('1', '<p class=\"ql-align-center\">English</p>', 'en_US', '2019-10-26 15:14:39', '2019-12-07 11:51:07', 'privacy');
INSERT INTO `conf_user_agreement` VALUES ('11', '<p><br></p><p class=\"ql-align-center\">中文繁體</p>', 'zh_HK', '2019-10-26 15:14:39', '2019-12-07 16:46:48', 'privacy');
INSERT INTO `conf_user_agreement` VALUES ('2', '<p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">FKExAPP系为用户提供数字资产交易和相关服务的手机端信息中介服务平台（以下简称“平台”）。平台 依据本协议（定义见下文）的规定为在平台进行注册的会员（以下简称“会员”）提供服务，本协议在会 员和平台之间具有法律约束力。平台在此特别提醒用户认真阅读并充分理解本协议项下的各条款，特别 是本协议中涉及免除或限制平台责任的条款，以及排除或限制用户权利的条款。用户应当审慎阅读，并 选择接受或不接受本协议。除非用户接受本协议项下的所有条款，否则用户无权使用平台基于本协议所 提供的服务。若用户不同意本协议的内容，或拒绝承认平台随时对本协议进行单方修改的权利，则用户 应当立即停止使用并不再访问本平台。用户一经注册成为会员或使用平台服务即视为对本协议全部条款 （包括本公司对本协议随时做出的任何修改）充分理解并完全接受。</span></p><p><span style=\"color: rgb(0, 0, 0);\">为了本协议表述之方便，本平台在本协议中合称使用“我们”或其他第一人称称呼；访问平台的自然人或 其他主体均成为“您”或其他第二人称；我们和您在本协议中何成为“双方”，我们或您称为“一方”。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第一章定义和解释</span></p><p><span style=\"color: rgb(0, 0, 0);\">第一条在本协议中，除非本协议项下条款另有约定，以下词汇或者表述应当具有下述意义：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）本协议应当包括本服务协议、隐私条款、反洗钱反恐怖融资规则和法律声明以及其他在平台上已经 发布或将来可能发布的各类规则、附件、声明、说明或指引等构成。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	不可抗力：包括信息网络设备维护、信息网络连接故障、电脑、通讯或其他系统的故障、电力故 障、天气原因、意外事故、罢工、劳动争议、暴乱、起义、骚乱、生产力或生产资料不足、火灾、洪 水、风暴、爆炸、战争或其他合作方原因、数字资产市场崩溃、政府行为、司法或行政机关的命令、其 他不在平台可控范围内或平台无能力控制的情形。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	关联公司：与其他公司之间存在直接或间接控制关系或重大影响关系的公司；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	知识产权：应当具有本协议第八十六条之意义。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二条在本协议中引用的任何法典或者成文法令或者行政规章应当引用其最新的修订版本，无论该修 订是在本协议签订之前或者之后做出。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三条本协议中任何条款之标题的应用是出于简便的考量，而不应当用于解释协议条款之用途。引用 任何陈述，条款，附件，表格是指本协议项下之陈述，条款，附件，表格。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四条本协议项下，除非协议中另有要求，在任何情形下使用“包括”一词，应当具有“包括”之意义。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五条除非本协议条款另有约定，本协议项下的各个文件相互冲突或者不一致，应当以下述顺序来决 定文件的效力以解决冲突或者不一致：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）FKEx全球站用户服务协议；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	FKEx全球站隐私条款；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	反洗钱和反恐怖融资规则；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	其他协议、规则和指引。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六条用户可以选择使用平台的不同语言版本，若存在平台的不同语言版本之内容不一致或者相冲 突，或遗漏内容之情形，平台的中文文本应当优先适用。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二章平台基本条款 第七条平台系信息中介服务平台（网址：www.fkex.c。，如平台以公告等形式发布新的网址，请届时访 问新的网址），平台服务是由本公司通过平台及客户端等各种方式向会员提供的服务，具体服务内容主 要包括：数字资产交易信息发布、数字资产交易管理服务、用户服务等交易辅助服务，具体详情平台实 际提供的服务内容为准。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八条为了保障您的权益，您在自愿注册使用平台服务前，必须仔细阅读并充分理解知悉本服务协议 所有条款。一经注册或使用平台服务即视为对本协议和平台对本协议随时做出的任何修改的充分理解和 接受；如有违反而导致任何法律后果，您将自己独立承担相应的法律责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第九条在本协议履行过程中，平台可根据情况对本协议进行修改。一旦本协议的内容发生变动，平台 将公布最新的服务协议，不再向会员作个别通知。如果会员不同意平台对本协议所做的修改，会员有权 停止使用平台服务。如果会员继续使用平台服务，则视为会员接受平台对本协议所做的修改，并应遵照 修改后的协议执行。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十条平台对于会员的通知及任何其他的协议、告示或其他关于会员使用会员账户及服务的通知，会 员同意平台可通过平台公告、站内信、电子邮件、手机短信、无线通讯装置等电子方式或邮寄等物理方 式进行，该等通知于发送之日视为已送达收件人（如以邮寄方式向会员发出通知的，则在该等通知按 照会员在平台留存的联系地址交邮后的第三个自然日即视为送达）。因不可归责于平台的原因（包括电 子邮件地址、手机号码、联系地址等不准确或无效、信息传输故障等）导致会员未在前述通知视为送达 之日收到该等通知的，平台不承担任何责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三章注册会员</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十一条在使用平台服务前，用户必须先在平台上进行注册。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十二条注册平台个人会员的用户应当是年满十八岁或根据其所在国法律规定具有完全民事权利和民 事行为能力，能够独立承担民事责任的自然人</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十三条欲注册机构会员的法人、组织或其他机构，应当指定年满十八岁或根据其所在国法律规定具 有完全民事权利和民事行为能力，能够独立承担民事责任的自然人代表机构完成平台注册。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十四条您一旦点击同意注册按钮，即表示您或您有权代理的机构同意本协议的全部内容，且您本人 或您所代理的机构受到本协议之约束。若您不具备本协议第十二条或第十三条所要求之主体资格，则您 及您有权代理的机构应承担因此而导致的一切后果，且平台保留注销或永久冻结您账户，并向您及您有 权代理机构追究责任的权利。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十五条您同意根据平台用户注册页面的要求提供姓名、电子邮箱、手机号码、国籍，身份证件等信 </span><em style=\"color: rgb(0, 0, 0);\">息、。</em></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十六条在注册前或者注册后，平台有权根据用户所在国家或地区的法律法规、规则、命令等规范的 要求，向用户要求提供更多的信息或资料等。用户应当配合平台提交相关信息或资料，并采取合理的措 施以符合当地的规范之要求。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十七条会员在此承诺以下事项：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）出于合法交易自身数字资产之目的注册及使用本平台，且不具有以平台为媒介违反法律法规或破坏 数字资产交易秩序之意图； （二）会员必须依平台要求提示提供真实、最新、有效及完整的资料;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员保证并承诺通过平台进行交易的资金来源合法；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	会员有义务维持并更新会员的资料，确保其为真实、最新、有效及完整；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	个人会员不为美国之居民，机构会员不为美国注册之公司；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	除本协议以外，会员应同时遵守平台不时发布及更新的全部规则，包括公告、产品流程说明、平台 项目说明、风险提示等。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十八条除非用户提交的信息是明显虚假，错误和不完整的，平台有权信赖用户所提供的信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十九条若用户违背本协议第十七之承诺：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台有权包括停用会员平台帐户、拒绝会员使用平台服务的部份或全部功能。在此情况下，平台不 承担任何责任，并且会员同意负担因此所产生的直接或间接的任何支出或损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	未及时更新基本资料，导致平台服务无法提供或提供时发生任何错误，会员不得将此作为取消交易 或拒绝付款的理由，平台亦不承担任何责任，所有后果应由会员承担；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员应当承担因违背承诺而产生的任何直接或间接损失及不利后果，扣取不当获利，且平台保留追 究会员责任的权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十条在您合法、完整并有效提供注册所需信息并经验证后，注册流程即告结束，用户正式成为平 台会员，且可在平台进行会员登陆。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十一条平台发现用户不适合进行高风险投资情形时，有权终止或终止会员对会员账户之使用。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十二条无论本协议其他条款如何规定，平台对用户是否能够通过平台用户认证，以及是否注销已 认证用户之资格具有自由裁量权。平台有权拒绝或注销任何用户的注册，且没有义务告知用户拒绝注册 之理由，平台不承担任何因平台拒绝用户注册而导致用户所遭受的直接或间接的损失，且保留追究用户 责任的权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十三条用户系自愿注册成为平台会员，平台没有强迫、诱导、欺骗或者通过其他不公平的手段对 用户施加影响。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四章注册平台服务的内容</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十四条平台对完成注册的会员提供以下服务：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）提供数字资产各项目方所公开的相关信息；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	数字资产各项目的实时行情及交易信息；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	数字资产交易管理服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	提供客户服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	保障平台正常运营的技术和管理服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	平台公示的其他服务。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十五条平台接受数字资产项目方的委托依据本协议第二十四条进行信息发布，为数字资产之间的 交易提供撮合服务，平台仅对数字资产项目方发布的信息承担文本审查责任，不对信息的准确、完整、 合法性做出保证，也不承担相关责任，会员应依其独立判断做出决策。会员据此进行数字资产交易的， 产生的风险由会员自行承担，会员无权据此向平台提出任何法律主张。会员与数字资产项目方之间因交 易发生的或与交易有关的任何纠纷，应由纠纷各方自行解决，平台不承担任何交易风险及法律责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十六条本协议第二十四条所述之数字资产交易管理服务应当包括以下内容：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）会员账户：会员在平台进行注册时将生成会员账户，会员账户将记载会员在平台的活动，上述会员 账户是会员登陆平台的唯一账户。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（二）	数字资产交易：会员可以通过平台提交数字资产交易指令，用会员账户中的数字资产交易其他数字 资产。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	充币和提币：会员可以将数字资产从其他地址转入会员账户指定地址，或将会员账户中的数字资产 转出至其他地址。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	交易状态更新：会员确认，会员在平台上按平台服务流程所确认的数字资产交易状态，将成为平台 为会员进行相关交易或操作的不可撤销的指令。会员同意相关指令的执行时间以平台在平台系统中进行 实际操作的时间为准。会员同意平台有权依据本协议及/或平台相关纠纷处理规则等约定对相关事项进 行处理。会员未能及时对交易状态进行修改、确认或未能提交相关申请所引起的任何纠纷或损失由会员 自行负责，平台不承担任何责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（五）	交易指令传递：平台依据会员输入交易指令的时间和数据传递交易指令。会员了解平台系数字资产 交易的撮合方，并不是买家或卖家参与买卖数字资产交易行为本身，且平台不提供任何国家法定货币的 充入或提取业务。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（六）	交易查询：平台将对会员在平台的所有操作进行记录，不论该操作之目的最终是否实现。会员可以 通过会员账户实时查询会员账户名下的交易记录。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（七）	交易安全设置：平台有权基于交易安全等方面的考虑不时设定涉及交易的相关事项，包括交易限 额、交易次数等，会员了解平台的前述设定可能会对交易造成一定不便，对此没有异议。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（八）	系统故障处理：如果平台发现了因系统故障或其他任何原因导致的处理错误，无论有利于平台还是 有利于会员，平台都有权纠正该错误。如果该错误导致会员实际收到的数字资产多于应获得的数字资 产，则无论错误的性质和原因为何，平台保留纠正不当执行的交易的权利，会员应根据平台向会员发出 的有关纠正错误的通知的具体要求返还多收的数字资产或进行其他操作。会员理解并同意，平台不承担 因前述处理错误而导致的任何损失或责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十七条除了本协议第二十四条项下所列之服务，和平台公示的技术性服务，平台不能就数字资产 交易提供给会员任何投资、法律、税收或其他专业意见，且任何平台所提供之任何消息、探讨、分析、 价格等信息均为一般评论，不够成对会员进行任何数字资产交易的建议。如果会员需要专业意见，会员 应当向相关专业人事咨询数字货币交易有关的投资、法律、税收或其他专业性建议。平台不承担会员因 依赖上述一般评论而产生的任何直接或间接而产生的损失（包括任何利润损失）。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十八条平台提供的服务不应当理解为或被用于向任何认定平台所提供之服务为非法的国家或地区 的用户发出要约。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十九条会员使用本平台进行数字资产交易的过程中应当遵守以下交易规则： （-）浏览交易信息：会员在平台浏览交易信息时，应当仔细阅读交易信息中包含的全部内容，包括价 格、委托量、手续费、买入或卖出方向，会员应当在完全理解并接受交易信息中的全部内容后，再点击 按钮进行交易。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（二）	委托之提交：在理解并完全接收交易信息中的全部内容后，会员可以输入数字资产交易信息，确认 该信息无误后提交交易委托。一旦会员提交交易委托，则会员授权平台代理会员依据会员输入的数字资 产交易信息进行相应的交易撮合。会员知悉并同意，当有满足会员委托交易价格的数字资产交易时，平 台应当自动完成交易之撮合，且无需提前通知会员。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	交易明细之查询：会员可以通过个人账户直中的交易明细中查看相应的数字资产交易成交记录。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	撤销或修改委托：会员知悉，在委托之数字资产交易撮合未完成之前，会员有权随时撤销或修改委 托。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十条平台有权基于下述原因修改、暫停或永久停止对会员开放的平台之部分或全部服务：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）依据会员所属主权国家或地区的法律法规、规则、命令等规范的要求；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	平台出于保护平台或客户利益之合法利益；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	数字资产交易规则发生变更；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	其他合理理由。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十一条平台基于本协议第三十条修改、暂停或永久停止对会员开放的平台之部分或全部服务的，生 效日以平台公告为准。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五章账户安全及管理</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十二条会员了解并同意，确保会员账户及密码的机密安全是会员的责任。会员将对利用该会员账 户及密码所进行的一切行动及言论，负完全的责任，并同意以下事项：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）会员应根据平台的相关规则以及平台的相关提示创建密码（密码包括但不限于登陆密码、资金密 码、注册账户时绑定的手机号码以及手机接收的手机验证码、谷歌验证等，具体形式可能发生变化，下 同），应避免选择过于明显的单词或日期，比如会员的姓名、昵称或者生日等；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	会员不对其他任何人泄露账户或密码，亦不可使用其他任何人的账户或密码。因黑客、病毒或会员 的保管疏忽等非平台原因导致会员的会员账户遭他人非法使用的，平台不承担任何责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员禁止在未经平台同意的情形下不得将平台账号以赠与、借用、租用、转让或以其他方式处分给 </span><em style=\"color: rgb(0, 0, 0);\">第三方;</em></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	平台通过会员的会员账户及密码来识别会员的指令，会员确认，使用会员账户和密码登陆后在平台 的一切行为均代表会员本人。会员账户操作所产生的电子信息记录均为会员行为的有效凭据，并由会员 本人承担由此产生的全部责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	在平台通知会员可能预见的安全风险后，采取措施保障会员账号和密码安全；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	冒用他人账户及密码的，平台及其合法授权主体保留追究实际使用人连带责任的权利；</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十三条会员如发现有第三人冒用或盗用会员账户及密码，或其他任何未经合法授权的情形，应立 即以有效方式通知平台，要求平台暂停相关服务，否则由此产生的一切责任由会员本人承担。同时，会 员理解平台对会员的请求采取行动需要合理期限，在此之前，平台对第三人使用该服务所导致的损失不 承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十四条平台有权基于单方独立判断，在其认为可能发生危害交易安全等情形时，不经通知而先行 暂停、中断或终止向会员提供本协议项下的全部或部分会员服务（包括收费服务），移除或删除注册资 料，扣押不当获利，且无需对会员或任何第三方承担任何责任。前述情形包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台认为会员提供的资料不具有真实性、有效性或完整性，包括盗用他人证件信息注册、认证信息 不匹配等；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	平台发现异常交易或有疑义或有违法之虞时；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	平台认为会员账户涉嫌洗钱、套现、传销、被冒用或其他平台认为有风险之情形；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	平台发现会员使用非法或不正当的技术手段进行危害交易安全或影响公平交易的行为，包括篡改交 易数据、窃取客户信息、窃取交易数据、通过平台攻击其他已注册账户等；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（五）	平台认为会员已经违反本协议中规定的各类规则及精神；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（六）	会员账户已连续一年内未登录或实际使用且账户中数字资产为零；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（七）	会员违反本协议的其他情形。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（八）	平台基于交易安全等原因，根据其单独判断需先行暂停、中断或终止向会员提供本协议项下的全部 或部分会员服务（包括收费服务），并将注册资料移除或删除的其他情形。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十五条会员决定不再使用会员账户时，应首先清偿所有应付款项（包括服务费、管理费等），再将 会员账户中的可用数字资产（如有）在可提取范围全部提取，并向平台申请冻结该会员账户，经平台审 核同意后可正式注销会员账户。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十六条会员同意，如其会员账户未完成身份认证且已经连续一年未登陆，平台无需进行事先通知 即有权终止提供会员账户服务，并可能立即暂停、关闭或删除会员账户及该会员账户中所有相关资料及 档案。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十七条会员同意，会员账户的暂停、中断或终止不代表会员责任的终止，会员仍应对使用平台服 务期间的行为承担可能的违约或损害赔偿责任，同时平台仍可保有会员的相关信息。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六章会员的保证及承诺</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十八条会员承诺绝不为任何非法目的或以任何非法方式使用平台服务，并承诺遵守其所在国的相 关法律、法规及一切使用互联网之国际惯例，遵守所有与平台服务有关的网络协议、规则和程序。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十九条会员同意并保证不得利用平台服务从事侵害他人权益或违法之行为，若有违反者应负所有 法律责任。上述行为包括：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）冒用他人名义使用平台服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	从事任何不法交易行为，如贩卖枪支、毒品、禁药、盗版软件或其他违禁物；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	提供赌博资讯或以任何方式引诱他人参与赌博； （四）涉嫌洗钱、套现或进行传销活动的;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	从事任何可能含有电脑病毒或是可能侵害平台服务系統、资料等行为；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	利用平台服务系统进行可能对互联网或移动网正常运转造成不利影响之行为；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（七）	恶意干扰数字资产交易正常进行，破坏数字资产交易秩序；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（八）	利用任何技术手段或其他方式干扰平台正常运行或干扰其他用户对平台服务的使用；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（九）	以虚构、夸大事实等方式恶意诋毁平台商誉；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十）其他平台有正当理由认为不适当之行为。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十条平台保有依其单独判断删除平台内各类不符合法律政策或不真实或不适当的信息内容而无须 通知会员的权利，并无需承担任何责任。若会员未遵守以上规定的，平台有权做出独立判断并采取暂停 或关闭会员账户等措施，而无需承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十一条会员同意，由于会员违反本协议，或违反通过援引并入本协议并成为本协议一部分的文 件，或由于会员使用平台服务违反了任何法律或第三方的权利而造成任何第三方进行或发起的任何补偿 申请或要求（包括律师费用），会员会对平台及其关联方、合作伙伴、董事以及雇员给予全额补偿并使 之不受损害。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十二条会员承诺，其通过平台上传或发布的信息均真实有效，其向平台提交的任何资料均真实、 有效、完整、详细、准确。如因违背上述承诺，造成平台或平台其他使用方损失的，会员将承担相应责 任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十三条会员理解并同意，平台向符合条件的会员提供服务。平台对在平台上进行的数字资产投资 或交易行为不承担任何责任，平台无法也没有义务保证会员投资成功，会员因投资或交易数字资产原因 导致的损失由会员自行承担，平台不承担责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十四条会员同意对其平台注册账号下发生的所有活动（包括信息披露、发布信息、点击同意各类 协议、上传提交各类文件、点击同意续签各类协议或点击同意数字货币交易等）承担责任，且如在上述 活动进程中，若会员未遵从本协议条款或平台公布的交易规则中的操作指示的，平台不承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十五条会员同意，平台有权在提供平台服务过程中以各种方式投放各种商业性广告或其他任何类 型的商业信息（包括在平台的任何页面上投放广告），并且，会员同意接受平台通过电子邮件或其他方 式向会员发送商业促销或其他相关商业信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十六条会员同意，若会员因数字资产交易与项目方或其他第三方产生纠纷的，不得通过司法或行 政以外的途径要求平台提供相关资料。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七章服务中断或故障</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十七条会员同意，基于互联网的特殊性，平台不担保服务不会中断，也不担保服务的及时性和/或 安全性。系统因相关状况无法正常运作，使会员无法使用任何平台服务或使用任何平台服务时受到任何 影响时，平台对会员或第三方不负任何责任，前述状况包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台系统停机维护期间。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）电信设备出现故障不能进行数据传输的。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	由于黑客攻击、网络供应商技术调整或故障、网站升级的问题等原因而造成的平台服务中断或延 迟。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成平台系统障碍不能执 行业务的。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八章责任范围及限制</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十八条平台未对任何平台服务提供任何形式的保证，包括以下事项：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台服务将符合会员的需求。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	平台服务将不受干扰、及时提供或免于出错。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员经由平台服务购买或取得之任何产品、服务、资讯或其他资料将符合会员的期望。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	平台包含的全部信息、程序、文本、数据等完全安全，不受任何病毒、木马等恶意程序的干扰和破 坏。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	所有的交易结果计算都经过平台核实，相应的计算方法都会在平台上进行公示，但是平台不能保证 其计算没有误差或不受干扰。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十九条会员知悉并同意，在任何情形下，平台不会就下列任一事项承担责任：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）用户的收入损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	用户的交易利润或合同损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	服务中断、中止或终止而引起的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	预期可节省交易成本的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	信息传递问题而造成的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	措施投资或交易机会的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（七）	商誉或声誉的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（八）	数据的遗失或损坏而造成的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（九）	购买替代产品或服务的开销；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十）任何由于侵权（包括故意和过失）、违约、或其他任何原因导致的间接的、特殊的或附带性的损 失，不论此种损失是否为平台所能合理预见，亦不论平台是否事先被告知存在此种损失的可能性。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十条会员了解并同意，发生以下任一情形时，平台有权拒绝赔偿会员的全部或部分损失：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台有合理的理由认为会员在平台的行为系涉嫌违法或不道德行为。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）会员误以为系平台原因造成损失的情形;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）任何非因平台原因引起的其他损失。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十一条平台服务的合作单位所提供的服务品质及内容由该合作单位自行负责。平台的内容可能涉 及由第三方所有、控制或者运营的其他网站（以下简称“第三方网站”）。平台不能保证也没有义务保证 第三方网站上任何信息的真实性和有效性。会员确认按照第三方网站的服务协议使用第三方网站，而不 是按照本协议。第三方网站不是平台推荐或者介绍的，第三方网站的内容、产品、广告和其他任何信息 均由会员自行判断并承担风险，而与平台无关。会员经由平台服务的使用下载或取得任何资料，应由会 员自行考量且自负风险，因资料的下载而导致的任何损坏由会员自行承担。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十二条会员自平台及平台工作人员或经由平台服务取得的建议或资讯，无论其为书面或口头，均 不构成平台对平台服务的任何保证。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十三条平台不保证为向会员提供便利而设置的外部链接的准确性、有效性、安全性和完整性，同 时，对于该等外部链接指向的不由平台实际控制的任何网页上的内容，平台不承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十四条在法律允许的情况下，平台对于与本协议有关或由本协议引起的，或者，由于使用平台、 或由于其所包含的或以其他方式通过平台提供给会员的全部信息、内容、材料、产品（包括软件）和服 务、或购买和使用产品引起的任何间接的、惩罚性的、特殊的、派生的损失（包括业务损失、收益损 失、利润损失、使用数据或其他经济利益的损失），不论是如何产生的，也不论是由对本协议的违约</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（包括违反保证）还是由侵权造成的，均不负有任何责任，即使其事先已被告知此等损失的可能性。另 外即使本协议规定的排他性救济没有达到其基本目的，也应排除平台对上述损失的责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十五条除本协议另有规定外，在任何情况下，平台对本协议所承担的违约赔偿责任总额不超过向 会员收取的当笔数字资产交易平台服务费用总额。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十六条除本协议另有规定外，在任何情况下，因会员违反本协议或其所在国法律法规而导致平台 遭受损失的，会员应当赔偿平台所遭受的一切直接和/或间接的损失（包括诉讼费用等）。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十七条平台和会员均承认普通法对违约或可能违约情况的救济措施可能不足以弥补守约方遭受的 全部或部分损失，故双方同意，协议一方有权在协议另一方违约或可能违约情况下寻求禁令救济以及普 通法或衡平法允许的其他所有的补救措施。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十八条平台在本协议中做出的保证和承诺是平台就其依据本协议提供服务的唯一保证和陈述（以 下简称“协议保证”），取代任何其他途径和方式产生的保证和承诺（以下简称“非协议保证”），无论非 协议保证是以书面或口头，明示或暗示的形式做出。所有协议保证仅仅由平台做出，对平台自身具有约 束力，其效力不能约束任何第三方。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十九条会员知悉并同意，平台并不放弃本协议中未提及的，在法律适用的最大范围内平台所享有 的限制、免除或抵销平台损害赔偿责任的任何权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九章风险提示</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十条会员了解并认可，任何通过平台进行的交易并不能避免以下风险的产生，平台不能也没有义 务为如下风险负责：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，会员有可能遭受损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，会员有可 能遭受损失；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	违约风险：因项目方无力或无意愿进行或者继续进行项目开发，而导致的会员有可能遭受损失；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	收益风险：数字资产本身不由任何金融机构或平台发行，数字资产市场是全新的、未经确认的，且 可能不会带来实际收益增长的市场；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（五）	操盘风险：数字资产主要由投机者大量使用，零售和商业市场使用相对较少，数字资产交易存在极 高风险，其全天不间断交易，没有涨跌限制，价格容易受庄家、全球政府政策的影响而大幅波动；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（六）	不可抗力因素导致的风险；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（七）	会员过错：因会员的过错导致的任何损失，包括：决策失误、操作不当、遗忘或泄露密码、密码被 他人破解、会员使用的计算机系统被第三方侵入、会员委托第三人代理交易时第三人恶意或不当操作而 造成的损失。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六十一条数字资产交易有极高风险，并不适合绝大部分人士投资。会员知悉并了解此投资有可能导 致部分损失或全部损失，所以会员应当以能承受的损失程度来决定其投资或交易的数量。除了本协议第 六十条提示的风险外，还会有未能预测的风险存在。会员应慎重评估自己的财政状况，及各项风险，而 做出任何数字资产投资或交易的决定。会员应当承担由该决定产生的全部损失，平台对会员的投资或交 易决定不承担任何责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六十二条鉴于数字资产投资或交易所衍生的风险，若用户对该种投资或交易由任何疑问，应当在交 易或投资前事先寻求专业顾问的协助。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六十三条平台不对任何会员及/或任何交易提供任何担保或条件，无论是明示、默示或法定的。平台 不能也不试图对会员或项目方发布的信息进行控制，对该等信息，平台不承担任何形式的证明、鉴定服 务。平台不能完全保证平台内容的真实性、充分性、可靠性、准确性、完整性和有效性，并且无需承担 任何由此引起的法律责任。会员应依赖于会员的独立判断进行交易，会员应对其做出的判断承担全部责 任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十四条平台对于会员使用平台服务不做任何明示或暗示的保证，包括但不限于平台提供服务的适 用性、没有错误或疏漏、持续性、准确性、可靠性、适用于某一特定用途。同时，我们也不对平台提供 的服务所涉及的技术及信息的有效性、准确性、正确性、可靠性、质量、稳定、完整和及时性做出任何 承诺和保证。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十五条是否登陆或使用平台提供的服务是会员个人的决定且自行承担风险及可能产生的损失。平 台对于数字资产的市场、价值及价格等不做任何明示或暗示的保证，会员知悉并了解数字资产市场的不 稳定性，数字资产的价格和价值随时会大幅波动或崩盘，交易数字资产是会员个人的自由选择及决定， 且自行承担风险及可能产生的损失。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十六条以上并不能揭示会员通过平台进行交易的全部风险及市场的全部情形。会员在做出交易决 策前，应全面了解相关数字资产，根据自身的交易目标、风险承受能力和资产状况等谨慎决策，并自行 承担全部风险。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十七条在任何情况下，对于会员使用平台服务过程中涉及由第三方提供相关服务的责任由该第三 方承担，平台不承担该等责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十八条因会员自身的原因导致的任何损失或责任，由会员自行负责，平台不承担责任。平台不承 担责任的情形包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）会员未按照本协议或平台不时公布的任何规则进行操作导致的任何损失或责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	因会员向平台发送的指令信息不明确、或存在歧义、不完整等导致的任何损失或责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员账户内数字资产余额不足导致的任何损失或责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	其他因会员原因导致的任何损失或责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十章服务费用及其他费用</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十九条当会员使用平台服务时，平台向会员收取相关平台服务费用。各项平台服务费用详见会员 使用平台服务时，平台上所列之收费说明及收费标准。平台保留单方面制定及调整平台服务费用收费标 准的权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十条会员在使用平台服务过程中可能需要向第三方支付一定的第三方服务费用，具体收费标准详 见第三方网站相关页面，或平台的提示及收费标准。会员同意将根据上述收费标准自行或委托平台或平 台指定的第三方代为向第三方支付该等服务费。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十一章协议之终止</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十一条会员有权在任何时候依据本协议条款之规定向平台申请注销平台账号，依据本协议第三十 五条注销账号的，自平台批准用户注销账号之日起协议终止。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十二条依据本协议第三十五条、三十六条注销账号的，自平台注销用户账号之日起协议终止。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十三条会员死亡或被宣告死亡的，其在本协议项下的各项权利义务由其继承人承担。若会员丧失 全部或部分民事权利能力或民事行为能力，平台或其授权的主体有权根据有效法律文书（包括生效的法 院判决等）或其法定监护人的指示处置与会员账户相关的款项。若继承人或法定监护人决定继续履行本 协议的，则本协议依然有效；反之，则继承人或法定监护人需要依据本协议第三十五条向平台申请注销 账号，自平台批准用户注销账号之日起协议终止。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十四条平台有权依据本协议约定终止平台全部服务，本协议于平台全部服务终止之日终止，清退 流程根据平台公告的具体规定进行操作。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十五条本协议终止后，会员无权要求平台继续向其提供任何服务或履行任何其他义务，包括但不 限于要求平台为会员保留或向会员披露其原本网站账号中的任何信息，向会员或第三方转发任何其未 曾阅读或发送过的信息等。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十六条本协议的终止不影响守约方向违约方主张其他协议终止前违约方之违约责任，也不影响本 协议规定之后合同义务之履行。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十二章个人信息之保护和授权条款</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十七条本协议第十二章中之个人信息应当包括以下信息：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）在用户注册平台账号或者使用账户时，用户根据本平台要求提供的个人注册信息，包括但不限于电 话号码、邮箱信息、身份证件信息；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）在用户使用平台时，或访问平台时，平台自动接收并记录的用户浏览器上的服务器数值，包括但不 限于IP地址等数据及用户要求取用的网页记录； （三）平台收集到的用户在平台进行交易的有关数据，包括但不限于交易记录;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）平台通过合法途径取得的其他用户个人信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十八条不需要用户额外的同意，用户在平台注册成功即视为用户同意平台收集并使用或披露其个 人信息，且用户了解并同意，基于为用户度身订造平台服务、解决争议并有助确保在平台进行安全交易 的考量，平台可以将收集的用户个人信息用作下列用途包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）向用户提供平台服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	基于主权国家或地区相关主管部门的要求向相关部门进行报告；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	在用户使用平台服务时，平台将用户的信息用于身份验证、客户服务、安全防范、诈骗监测、市场 推广、存档和备份用途，或与第三方合作推广网站等合法用途，确保平台向用户提供的产品和服务的安 全性；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	帮助平台设计新产品及服务，改善平台现有服务目的而进行的信息收集和整理；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	为了使用户了解平台服务的具体情况，用户同意平台向其发送营销活动通知、商业性电子信息以及 提供与用户相关的广告以替代普遍投放的广告；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	平台为完成合并、分立、收购或资产转让而将用户的信息转移或披露给任何非关联的第三方；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（七）	软件认证或管理软件升级；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（八）	邀请用户参与有关平台服务的调查；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（九）	用于和政府机关、公共事务机构、协会等合作的数据分析；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十）用于解决争议或对纠纷进行调停；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十一）用作其他一切合法目的以及经用户授权的其他用途。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十九条平台按照用户在平台上的行为自动追踪关于用户的某些资料。在不透露用户的隐私资料的 前提下，平台有权对整个用户数据库进行分析并对用户数据库进行商业上的利用。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八十条用户同意，平台可在平台的某些网页上使用诸如“Cookies”的资料收集装置。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八十一条平台根据相关法律法规以保护用户的资料。用户因履行本协议提供给平台的信息，平台不 会恶意出售或免费共享给任何第三方，以下情况除外：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）提供独立服务且仅要求服务相关的必要信息的供应商；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（二）	具有合法调阅信息权限并从合法渠道调阅信息的政府部门或其他机构；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	平台的关联公司；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	经平台使用方或平台使用方授权代表同意的第三方。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八十二条授权平台，除法律另有规定之外，将用户提供给平台的信息、享受平台服务产生的信息（包 括本协议签署之前提供和产生的信息）以及平台根据本条约定查询、收集的信息，用于平台及其因服务 必要委托的合作伙伴为用户提供服务、推荐产品、开展市场调查与信息数据分析。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十三条授权平台，除法律另有规定之外，基于为用户提供更优质服务和产品的目的，向平台因服 务必要开展合作的伙伴提供、查询、收集用户的信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十四条为确保用户信息的安全，平台及其合作伙伴对上述信息负有保密义务，并采取各种措施保 证信息安全。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十五条本协议第十二章项下之条款自本协议签署时生效，具有独立法律效力，不受合同成立与否 及效力状态变化的影响。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十三章知识产权的保护</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十六条平台上所有内容，包括著作、图片、档案、资讯、资料、平台架构、平台画面的安排、平台 设计，文字和图表，软件编译，相关源代码和软件等均由平台或其他权利人依法拥有其知识产权，包括 商标权、专利权、著作权、商业秘密等。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十七条非经平台或其他权利人书面同意，任何人不得擅自使用、修改、复制、公开传播、改变、 散布、发行或公开发表平台程序或内容。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十八条用户未经平台的明确书面同意不许下载（除了页面缓存）或修改平台或其任何部分。用户 不得对平台或其内容进行转售或商业利用；不得收集和利用产品目录、说明和价格；不得对平台或其内 容进行任何衍生利用；不得为其他商业利益而下载或拷贝账户信息或使用任何数据采集、Robots或类似 的数据收集和摘录工具。未经平台的书面许可，严禁对平台的内容进行系统获取以直接或间接创建或编 辑文集、汇编、数据库或人名地址录（无论是否通过Robots、Spiders、自动仪器或手工操作）。另 外，严禁为任何未经本使用条件明确允许的目的而使用平台上的内容和材料。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十九条未经平台明确书面同意，不得以任何商业目的对平台网站或其任何部分进行复制、复印、 仿造、出售、转售、访问、或以其他方式加以利用。未经平台明确书面同意，用户不得用任何技巧把平 台或其关联公司的商标、标识或其他专有信息（包括图像、文字、网页设计或形式）据为己有。未经平 台明确书面同意，用户不得以Meta Tag s或任何其他\"隐藏文本\"方式使用平台或其关联公司的名字和商 标。任何未经授权的使用都会终止平台所授予的允许或许可。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十条用户登陆平台或使用平台提供的任何服务均不视为平台向用户转让任何知识产权。尊重知识 产权是用户应尽的义务，如有违反，用户应对平台承担损害赔偿等法律责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十四章一般条款</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十一条本协议是由用户与平台共同签订的，适用于用户在平台的全部活动。本协议内容包括协议正 文条款及已经发布的或将来可能发布的各类规则，所有条款和规则为协议不可分割的一部分，与本协议 正文具有同等法律效力。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十二条如本协议中的任何条款被任何有管辖权的机构认定为不可执行的，无效的或非法的，并不 影响本协议的其余条款的效力。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十三条本协议中约定的权利及义务同样约束通过转让从该权利义务中获取到利益的各方的受让 人，继承人，遗嘱执行人和管理员。会员不得在我们不同意的前提下将本协议项下的权利或义务转让给 任何第三方，但平台可随时将我们在本协议中的权利和义务转让给任何第三方，并于转让之日提前三十</span></p><p class=\"ql-align-justify\">	<span style=\"color: rgb(0, 0, 0);\">（30）天给与用户通知。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十四条如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，则应认为该条款 可与本协议相分割，并可被尽可能接近各方意图的、能够保留本协议要求的经济目的的、有效的新条款 所取代，而且，在此情况下，本协议的其他条款仍然完全有效并具有约束力。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十五条除非本协议中的其他条款另有约定，本协议中的任何规定均不应当被认为创造了、暗示了 或以其他方式将平台视为会员的代理人、受托人或其他代表人。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十六条本协议任何一方未能就单一事件行使与本协议有关的权利或寻求救济，并不影响该缔约方 随后就该单一事件或者在其他事件后行使该权利或者寻求救济。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十七条对违约行为的豁免，或本协议任一条款的放弃，仅在守约方或非寻求放弃方书面签字同意 豁免后方能生效。任何本协议项下的违约豁免，不能认定或解释为守约方对其后再次违约或其他违约行 为的豁免；未行使任何权利或救济不得以任何方式被解释为对该等权利或救济的放弃。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十八条本协议依据塞舌尔共和国法律订立，其成立、解释、内容及执行均适用塞舌尔共和国相关 法律规定；任何因本协议引起或与本协议相关的索赔或诉讼，都应当由塞舌尔共和国的法律进行解释和 执行。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十九条除非本协议项下其他规则另有约定，双方同意，由新加坡国际仲裁委员会对任何由本协议 引起或与本协议相关的索赔或诉讼进行仲裁。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第百条本协议自用户获得本网站账号时生效，对协议双方具有约束力。</span></p>', 'zh_CN', '2019-10-26 15:14:39', '2019-10-31 19:19:28', 'privacy');
INSERT INTO `conf_user_agreement` VALUES ('3', '<p class=\"ql-align-justify\">Tencent service agreement</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">[first and introduction]</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">Welcome to use tencent\'s service!</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">To use tencent\'s services, you should read and comply with the tencent services agreement (hereinafter referred to as \"this agreement\") and tencent privacy policy.Please read carefully and fully understand the contents of the terms, especially the terms that exempt or limit tencent\'s liability, the terms that restrict users\' rights, the terms that stipulate the way of dispute settlement and the terms of jurisdiction (such as article 18), and the separate agreements or rules for opening or using a service.Restrictions, exclusions or other terms involving your material interests may be highlighted in bold, underlined or other forms.</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">Unless you have fully read, fully understand and accept all terms of this agreement, you have no right to use tencent services.If you click \"agree\" or \"next step\", or use tencent services, or otherwise express or implied your acceptance of this agreement, you shall be deemed to have read and agreed to sign this agreement.This agreement shall have legal effect between you and tencent and become a legal document binding on both parties.</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">If you do not have full capacity for civil conduct due to age, intelligence and other factors, please read and judge whether you agree to this agreement with the legal guardian (hereinafter referred to as \"guardian\"), and pay special attention to the terms of use of minors.</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">If you are a user outside mainland China, you are required to enter into or perform this agreement in compliance with the laws of your country or territory.&nbsp;</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">&nbsp;</p><p><br></p>', 'en_US', '2019-10-26 15:14:39', '2019-10-26 15:14:39', 'business');
INSERT INTO `conf_user_agreement` VALUES ('4', '<p>商家服务准则与制度标准</p><p> 会员需通过平台认证获取商家身份及权限。</p><p> 申请商家条件如下： </p><p>1.需确保账户内USDT10000个，作为商家保证金，冻结于自身账户内。取消商家资格，将解冻此保证金。</p><p> 2.商家需遵守按时打款、快速放行、不恶意卡单、不恶意拍单、不随意取消订单等诚信交易的基本原则。优质的商家应当具备快速打款、快速放行，低申诉率、低取消率，服务态度好、服务意识强等特质。</p><p><br></p><p>&nbsp;商家基本准则：</p><p> 1、对自己的广告负责，确认好广告价格、数量再上架；当人离开或者下线时，一定先下架广告。(单价根据实时价幅度的5%) </p><p>2、熟知各个银行的转款规定，在订单有效时间内，高效完成转款。</p><p> 3、当收到买家转款，核对好金额后，及时放行。（收到到账信息后，放行时间不得超过30分钟）</p><p> 4、当有需要时，才申诉订单。不因和对手方发生纠纷，而恶意申诉。</p><p> 5、商家对普通用户应怀有服务意识，反应及时、用语礼貌。</p>', 'zh_CN', '2019-10-26 15:14:39', '2019-10-26 15:14:39', 'business');
INSERT INTO `conf_user_agreement` VALUES ('444', '<p>商家服务准则与制度标准</p><p>会员需通过平台认证获取商家身份及权限。</p><p>申请商家条件如下：</p><p>1.需确保账户内USDT5000个，作为商家保证金，冻结于自身账户内。取消商家资格，将解冻此保证金。</p><p>2.商家需遵守按时打款、快速放行、不恶意卡单、不恶意拍单、不随意取消订单等诚信交易的基本原则。优质的商家应当具备快速打款、快速放行，低申诉率、低取消率，服务态度好、服务意识强等特质。</p><p><br></p><p>&nbsp;商家基本准则：</p><p>1、对自己的广告负责，确认好广告价格、数量再上架；当人离开或者下线时，一定先下架广告。(单价根据实时价幅度的5%)</p><p>2、熟知各个银行的转款规定，在订单有效时间内，高效完成转款。</p><p>3、当收到买家转款，核对好金额后，及时放行。（收到到账信息后，放行时间不得超过30分钟）</p><p>4、当有需要时，才申诉订单。不因和对手方发生纠纷，而恶意申诉。</p><p>5、商家对普通用户应怀有服务意识，反应及时、用语礼貌。</p>', 'zh_HK', '2019-10-26 15:14:39', '2019-10-31 18:59:45', 'business');
INSERT INTO `conf_user_agreement` VALUES ('af98480b-7f6b-494d-b1b2-43ba4b2f71f3', '<p class=\"ql-align-center\">English</p>', 'en_US', '2019-10-26 15:14:39', '2019-12-07 11:50:23', 'user');
INSERT INTO `conf_user_agreement` VALUES ('e1f2ffff-3c0e-44fd-aca5-2dca9c15', '<p><span style=\"background-color: rgb(245, 247, 250);\">中文繁體中文繁體</span></p>', 'zh_HK', '2019-10-26 15:14:39', '2019-12-07 11:52:00', 'user');
INSERT INTO `conf_user_agreement` VALUES ('e1f2ffff-3c0e-44fd-aca5-2dca9c155dce', '<p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">FKExAPP系为用户提供数字资产交易和相关服务的手机端信息中介服务平台（以下简称“平台”）。平台 依据本协议（定义见下文）的规定为在平台进行注册的会员（以下简称“会员”）提供服务，本协议在会 员和平台之间具有法律约束力。平台在此特别提醒用户认真阅读并充分理解本协议项下的各条款，特别 是本协议中涉及免除或限制平台责任的条款，以及排除或限制用户权利的条款。用户应当审慎阅读，并 选择接受或不接受本协议。除非用户接受本协议项下的所有条款，否则用户无权使用平台基于本协议所 提供的服务。若用户不同意本协议的内容，或拒绝承认平台随时对本协议进行单方修改的权利，则用户 应当立即停止使用并不再访问本平台。用户一经注册成为会员或使用平台服务即视为对本协议全部条款 （包括本公司对本协议随时做出的任何修改）充分理解并完全接受。</span></p><p><span style=\"color: rgb(0, 0, 0);\">为了本协议表述之方便，本平台在本协议中合称使用“我们”或其他第一人称称呼；访问平台的自然人或 其他主体均成为“您”或其他第二人称；我们和您在本协议中何成为“双方”，我们或您称为“一方”。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第一章定义和解释</span></p><p><span style=\"color: rgb(0, 0, 0);\">第一条在本协议中，除非本协议项下条款另有约定，以下词汇或者表述应当具有下述意义：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）本协议应当包括本服务协议、隐私条款、反洗钱反恐怖融资规则和法律声明以及其他在平台上已经 发布或将来可能发布的各类规则、附件、声明、说明或指引等构成。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	不可抗力：包括信息网络设备维护、信息网络连接故障、电脑、通讯或其他系统的故障、电力故 障、天气原因、意外事故、罢工、劳动争议、暴乱、起义、骚乱、生产力或生产资料不足、火灾、洪 水、风暴、爆炸、战争或其他合作方原因、数字资产市场崩溃、政府行为、司法或行政机关的命令、其 他不在平台可控范围内或平台无能力控制的情形。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	关联公司：与其他公司之间存在直接或间接控制关系或重大影响关系的公司；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	知识产权：应当具有本协议第八十六条之意义。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二条在本协议中引用的任何法典或者成文法令或者行政规章应当引用其最新的修订版本，无论该修 订是在本协议签订之前或者之后做出。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三条本协议中任何条款之标题的应用是出于简便的考量，而不应当用于解释协议条款之用途。引用 任何陈述，条款，附件，表格是指本协议项下之陈述，条款，附件，表格。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四条本协议项下，除非协议中另有要求，在任何情形下使用“包括”一词，应当具有“包括”之意义。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五条除非本协议条款另有约定，本协议项下的各个文件相互冲突或者不一致，应当以下述顺序来决 定文件的效力以解决冲突或者不一致：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）FKEx全球站用户服务协议；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	FKEx全球站隐私条款；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	反洗钱和反恐怖融资规则；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	其他协议、规则和指引。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六条用户可以选择使用平台的不同语言版本，若存在平台的不同语言版本之内容不一致或者相冲 突，或遗漏内容之情形，平台的中文文本应当优先适用。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二章平台基本条款 第七条平台系信息中介服务平台（网址：www.fkex.c。，如平台以公告等形式发布新的网址，请届时访 问新的网址），平台服务是由本公司通过平台及客户端等各种方式向会员提供的服务，具体服务内容主 要包括：数字资产交易信息发布、数字资产交易管理服务、用户服务等交易辅助服务，具体详情平台实 际提供的服务内容为准。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八条为了保障您的权益，您在自愿注册使用平台服务前，必须仔细阅读并充分理解知悉本服务协议 所有条款。一经注册或使用平台服务即视为对本协议和平台对本协议随时做出的任何修改的充分理解和 接受；如有违反而导致任何法律后果，您将自己独立承担相应的法律责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第九条在本协议履行过程中，平台可根据情况对本协议进行修改。一旦本协议的内容发生变动，平台 将公布最新的服务协议，不再向会员作个别通知。如果会员不同意平台对本协议所做的修改，会员有权 停止使用平台服务。如果会员继续使用平台服务，则视为会员接受平台对本协议所做的修改，并应遵照 修改后的协议执行。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十条平台对于会员的通知及任何其他的协议、告示或其他关于会员使用会员账户及服务的通知，会 员同意平台可通过平台公告、站内信、电子邮件、手机短信、无线通讯装置等电子方式或邮寄等物理方 式进行，该等通知于发送之日视为已送达收件人（如以邮寄方式向会员发出通知的，则在该等通知按 照会员在平台留存的联系地址交邮后的第三个自然日即视为送达）。因不可归责于平台的原因（包括电 子邮件地址、手机号码、联系地址等不准确或无效、信息传输故障等）导致会员未在前述通知视为送达 之日收到该等通知的，平台不承担任何责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三章注册会员</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十一条在使用平台服务前，用户必须先在平台上进行注册。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十二条注册平台个人会员的用户应当是年满十八岁或根据其所在国法律规定具有完全民事权利和民 事行为能力，能够独立承担民事责任的自然人</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十三条欲注册机构会员的法人、组织或其他机构，应当指定年满十八岁或根据其所在国法律规定具 有完全民事权利和民事行为能力，能够独立承担民事责任的自然人代表机构完成平台注册。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十四条您一旦点击同意注册按钮，即表示您或您有权代理的机构同意本协议的全部内容，且您本人 或您所代理的机构受到本协议之约束。若您不具备本协议第十二条或第十三条所要求之主体资格，则您 及您有权代理的机构应承担因此而导致的一切后果，且平台保留注销或永久冻结您账户，并向您及您有 权代理机构追究责任的权利。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十五条您同意根据平台用户注册页面的要求提供姓名、电子邮箱、手机号码、国籍，身份证件等信 </span><em style=\"color: rgb(0, 0, 0);\">息、。</em></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十六条在注册前或者注册后，平台有权根据用户所在国家或地区的法律法规、规则、命令等规范的 要求，向用户要求提供更多的信息或资料等。用户应当配合平台提交相关信息或资料，并采取合理的措 施以符合当地的规范之要求。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第十七条会员在此承诺以下事项：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）出于合法交易自身数字资产之目的注册及使用本平台，且不具有以平台为媒介违反法律法规或破坏 数字资产交易秩序之意图； （二）会员必须依平台要求提示提供真实、最新、有效及完整的资料;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员保证并承诺通过平台进行交易的资金来源合法；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	会员有义务维持并更新会员的资料，确保其为真实、最新、有效及完整；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	个人会员不为美国之居民，机构会员不为美国注册之公司；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	除本协议以外，会员应同时遵守平台不时发布及更新的全部规则，包括公告、产品流程说明、平台 项目说明、风险提示等。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十八条除非用户提交的信息是明显虚假，错误和不完整的，平台有权信赖用户所提供的信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十九条若用户违背本协议第十七之承诺：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台有权包括停用会员平台帐户、拒绝会员使用平台服务的部份或全部功能。在此情况下，平台不 承担任何责任，并且会员同意负担因此所产生的直接或间接的任何支出或损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	未及时更新基本资料，导致平台服务无法提供或提供时发生任何错误，会员不得将此作为取消交易 或拒绝付款的理由，平台亦不承担任何责任，所有后果应由会员承担；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员应当承担因违背承诺而产生的任何直接或间接损失及不利后果，扣取不当获利，且平台保留追 究会员责任的权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十条在您合法、完整并有效提供注册所需信息并经验证后，注册流程即告结束，用户正式成为平 台会员，且可在平台进行会员登陆。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十一条平台发现用户不适合进行高风险投资情形时，有权终止或终止会员对会员账户之使用。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十二条无论本协议其他条款如何规定，平台对用户是否能够通过平台用户认证，以及是否注销已 认证用户之资格具有自由裁量权。平台有权拒绝或注销任何用户的注册，且没有义务告知用户拒绝注册 之理由，平台不承担任何因平台拒绝用户注册而导致用户所遭受的直接或间接的损失，且保留追究用户 责任的权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十三条用户系自愿注册成为平台会员，平台没有强迫、诱导、欺骗或者通过其他不公平的手段对 用户施加影响。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四章注册平台服务的内容</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十四条平台对完成注册的会员提供以下服务：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）提供数字资产各项目方所公开的相关信息；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	数字资产各项目的实时行情及交易信息；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	数字资产交易管理服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	提供客户服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	保障平台正常运营的技术和管理服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	平台公示的其他服务。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第二十五条平台接受数字资产项目方的委托依据本协议第二十四条进行信息发布，为数字资产之间的 交易提供撮合服务，平台仅对数字资产项目方发布的信息承担文本审查责任，不对信息的准确、完整、 合法性做出保证，也不承担相关责任，会员应依其独立判断做出决策。会员据此进行数字资产交易的， 产生的风险由会员自行承担，会员无权据此向平台提出任何法律主张。会员与数字资产项目方之间因交 易发生的或与交易有关的任何纠纷，应由纠纷各方自行解决，平台不承担任何交易风险及法律责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十六条本协议第二十四条所述之数字资产交易管理服务应当包括以下内容：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）会员账户：会员在平台进行注册时将生成会员账户，会员账户将记载会员在平台的活动，上述会员 账户是会员登陆平台的唯一账户。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（二）	数字资产交易：会员可以通过平台提交数字资产交易指令，用会员账户中的数字资产交易其他数字 资产。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	充币和提币：会员可以将数字资产从其他地址转入会员账户指定地址，或将会员账户中的数字资产 转出至其他地址。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	交易状态更新：会员确认，会员在平台上按平台服务流程所确认的数字资产交易状态，将成为平台 为会员进行相关交易或操作的不可撤销的指令。会员同意相关指令的执行时间以平台在平台系统中进行 实际操作的时间为准。会员同意平台有权依据本协议及/或平台相关纠纷处理规则等约定对相关事项进 行处理。会员未能及时对交易状态进行修改、确认或未能提交相关申请所引起的任何纠纷或损失由会员 自行负责，平台不承担任何责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（五）	交易指令传递：平台依据会员输入交易指令的时间和数据传递交易指令。会员了解平台系数字资产 交易的撮合方，并不是买家或卖家参与买卖数字资产交易行为本身，且平台不提供任何国家法定货币的 充入或提取业务。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（六）	交易查询：平台将对会员在平台的所有操作进行记录，不论该操作之目的最终是否实现。会员可以 通过会员账户实时查询会员账户名下的交易记录。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（七）	交易安全设置：平台有权基于交易安全等方面的考虑不时设定涉及交易的相关事项，包括交易限 额、交易次数等，会员了解平台的前述设定可能会对交易造成一定不便，对此没有异议。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（八）	系统故障处理：如果平台发现了因系统故障或其他任何原因导致的处理错误，无论有利于平台还是 有利于会员，平台都有权纠正该错误。如果该错误导致会员实际收到的数字资产多于应获得的数字资 产，则无论错误的性质和原因为何，平台保留纠正不当执行的交易的权利，会员应根据平台向会员发出 的有关纠正错误的通知的具体要求返还多收的数字资产或进行其他操作。会员理解并同意，平台不承担 因前述处理错误而导致的任何损失或责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十七条除了本协议第二十四条项下所列之服务，和平台公示的技术性服务，平台不能就数字资产 交易提供给会员任何投资、法律、税收或其他专业意见，且任何平台所提供之任何消息、探讨、分析、 价格等信息均为一般评论，不够成对会员进行任何数字资产交易的建议。如果会员需要专业意见，会员 应当向相关专业人事咨询数字货币交易有关的投资、法律、税收或其他专业性建议。平台不承担会员因 依赖上述一般评论而产生的任何直接或间接而产生的损失（包括任何利润损失）。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十八条平台提供的服务不应当理解为或被用于向任何认定平台所提供之服务为非法的国家或地区 的用户发出要约。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第二十九条会员使用本平台进行数字资产交易的过程中应当遵守以下交易规则： （-）浏览交易信息：会员在平台浏览交易信息时，应当仔细阅读交易信息中包含的全部内容，包括价 格、委托量、手续费、买入或卖出方向，会员应当在完全理解并接受交易信息中的全部内容后，再点击 按钮进行交易。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（二）	委托之提交：在理解并完全接收交易信息中的全部内容后，会员可以输入数字资产交易信息，确认 该信息无误后提交交易委托。一旦会员提交交易委托，则会员授权平台代理会员依据会员输入的数字资 产交易信息进行相应的交易撮合。会员知悉并同意，当有满足会员委托交易价格的数字资产交易时，平 台应当自动完成交易之撮合，且无需提前通知会员。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	交易明细之查询：会员可以通过个人账户直中的交易明细中查看相应的数字资产交易成交记录。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	撤销或修改委托：会员知悉，在委托之数字资产交易撮合未完成之前，会员有权随时撤销或修改委 托。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十条平台有权基于下述原因修改、暫停或永久停止对会员开放的平台之部分或全部服务：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）依据会员所属主权国家或地区的法律法规、规则、命令等规范的要求；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	平台出于保护平台或客户利益之合法利益；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	数字资产交易规则发生变更；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	其他合理理由。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十一条平台基于本协议第三十条修改、暂停或永久停止对会员开放的平台之部分或全部服务的，生 效日以平台公告为准。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五章账户安全及管理</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十二条会员了解并同意，确保会员账户及密码的机密安全是会员的责任。会员将对利用该会员账 户及密码所进行的一切行动及言论，负完全的责任，并同意以下事项：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）会员应根据平台的相关规则以及平台的相关提示创建密码（密码包括但不限于登陆密码、资金密 码、注册账户时绑定的手机号码以及手机接收的手机验证码、谷歌验证等，具体形式可能发生变化，下 同），应避免选择过于明显的单词或日期，比如会员的姓名、昵称或者生日等；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	会员不对其他任何人泄露账户或密码，亦不可使用其他任何人的账户或密码。因黑客、病毒或会员 的保管疏忽等非平台原因导致会员的会员账户遭他人非法使用的，平台不承担任何责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员禁止在未经平台同意的情形下不得将平台账号以赠与、借用、租用、转让或以其他方式处分给 </span><em style=\"color: rgb(0, 0, 0);\">第三方;</em></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	平台通过会员的会员账户及密码来识别会员的指令，会员确认，使用会员账户和密码登陆后在平台 的一切行为均代表会员本人。会员账户操作所产生的电子信息记录均为会员行为的有效凭据，并由会员 本人承担由此产生的全部责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	在平台通知会员可能预见的安全风险后，采取措施保障会员账号和密码安全；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	冒用他人账户及密码的，平台及其合法授权主体保留追究实际使用人连带责任的权利；</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十三条会员如发现有第三人冒用或盗用会员账户及密码，或其他任何未经合法授权的情形，应立 即以有效方式通知平台，要求平台暂停相关服务，否则由此产生的一切责任由会员本人承担。同时，会 员理解平台对会员的请求采取行动需要合理期限，在此之前，平台对第三人使用该服务所导致的损失不 承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第三十四条平台有权基于单方独立判断，在其认为可能发生危害交易安全等情形时，不经通知而先行 暂停、中断或终止向会员提供本协议项下的全部或部分会员服务（包括收费服务），移除或删除注册资 料，扣押不当获利，且无需对会员或任何第三方承担任何责任。前述情形包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台认为会员提供的资料不具有真实性、有效性或完整性，包括盗用他人证件信息注册、认证信息 不匹配等；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	平台发现异常交易或有疑义或有违法之虞时；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	平台认为会员账户涉嫌洗钱、套现、传销、被冒用或其他平台认为有风险之情形；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	平台发现会员使用非法或不正当的技术手段进行危害交易安全或影响公平交易的行为，包括篡改交 易数据、窃取客户信息、窃取交易数据、通过平台攻击其他已注册账户等；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（五）	平台认为会员已经违反本协议中规定的各类规则及精神；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（六）	会员账户已连续一年内未登录或实际使用且账户中数字资产为零；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（七）	会员违反本协议的其他情形。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（八）	平台基于交易安全等原因，根据其单独判断需先行暂停、中断或终止向会员提供本协议项下的全部 或部分会员服务（包括收费服务），并将注册资料移除或删除的其他情形。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十五条会员决定不再使用会员账户时，应首先清偿所有应付款项（包括服务费、管理费等），再将 会员账户中的可用数字资产（如有）在可提取范围全部提取，并向平台申请冻结该会员账户，经平台审 核同意后可正式注销会员账户。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十六条会员同意，如其会员账户未完成身份认证且已经连续一年未登陆，平台无需进行事先通知 即有权终止提供会员账户服务，并可能立即暂停、关闭或删除会员账户及该会员账户中所有相关资料及 档案。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十七条会员同意，会员账户的暂停、中断或终止不代表会员责任的终止，会员仍应对使用平台服 务期间的行为承担可能的违约或损害赔偿责任，同时平台仍可保有会员的相关信息。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六章会员的保证及承诺</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十八条会员承诺绝不为任何非法目的或以任何非法方式使用平台服务，并承诺遵守其所在国的相 关法律、法规及一切使用互联网之国际惯例，遵守所有与平台服务有关的网络协议、规则和程序。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第三十九条会员同意并保证不得利用平台服务从事侵害他人权益或违法之行为，若有违反者应负所有 法律责任。上述行为包括：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）冒用他人名义使用平台服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	从事任何不法交易行为，如贩卖枪支、毒品、禁药、盗版软件或其他违禁物；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	提供赌博资讯或以任何方式引诱他人参与赌博； （四）涉嫌洗钱、套现或进行传销活动的;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	从事任何可能含有电脑病毒或是可能侵害平台服务系統、资料等行为；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	利用平台服务系统进行可能对互联网或移动网正常运转造成不利影响之行为；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（七）	恶意干扰数字资产交易正常进行，破坏数字资产交易秩序；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（八）	利用任何技术手段或其他方式干扰平台正常运行或干扰其他用户对平台服务的使用；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（九）	以虚构、夸大事实等方式恶意诋毁平台商誉；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十）其他平台有正当理由认为不适当之行为。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十条平台保有依其单独判断删除平台内各类不符合法律政策或不真实或不适当的信息内容而无须 通知会员的权利，并无需承担任何责任。若会员未遵守以上规定的，平台有权做出独立判断并采取暂停 或关闭会员账户等措施，而无需承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十一条会员同意，由于会员违反本协议，或违反通过援引并入本协议并成为本协议一部分的文 件，或由于会员使用平台服务违反了任何法律或第三方的权利而造成任何第三方进行或发起的任何补偿 申请或要求（包括律师费用），会员会对平台及其关联方、合作伙伴、董事以及雇员给予全额补偿并使 之不受损害。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十二条会员承诺，其通过平台上传或发布的信息均真实有效，其向平台提交的任何资料均真实、 有效、完整、详细、准确。如因违背上述承诺，造成平台或平台其他使用方损失的，会员将承担相应责 任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十三条会员理解并同意，平台向符合条件的会员提供服务。平台对在平台上进行的数字资产投资 或交易行为不承担任何责任，平台无法也没有义务保证会员投资成功，会员因投资或交易数字资产原因 导致的损失由会员自行承担，平台不承担责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十四条会员同意对其平台注册账号下发生的所有活动（包括信息披露、发布信息、点击同意各类 协议、上传提交各类文件、点击同意续签各类协议或点击同意数字货币交易等）承担责任，且如在上述 活动进程中，若会员未遵从本协议条款或平台公布的交易规则中的操作指示的，平台不承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十五条会员同意，平台有权在提供平台服务过程中以各种方式投放各种商业性广告或其他任何类 型的商业信息（包括在平台的任何页面上投放广告），并且，会员同意接受平台通过电子邮件或其他方 式向会员发送商业促销或其他相关商业信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十六条会员同意，若会员因数字资产交易与项目方或其他第三方产生纠纷的，不得通过司法或行 政以外的途径要求平台提供相关资料。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七章服务中断或故障</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十七条会员同意，基于互联网的特殊性，平台不担保服务不会中断，也不担保服务的及时性和/或 安全性。系统因相关状况无法正常运作，使会员无法使用任何平台服务或使用任何平台服务时受到任何 影响时，平台对会员或第三方不负任何责任，前述状况包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台系统停机维护期间。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）电信设备出现故障不能进行数据传输的。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	由于黑客攻击、网络供应商技术调整或故障、网站升级的问题等原因而造成的平台服务中断或延 迟。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成平台系统障碍不能执 行业务的。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八章责任范围及限制</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十八条平台未对任何平台服务提供任何形式的保证，包括以下事项：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台服务将符合会员的需求。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	平台服务将不受干扰、及时提供或免于出错。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员经由平台服务购买或取得之任何产品、服务、资讯或其他资料将符合会员的期望。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	平台包含的全部信息、程序、文本、数据等完全安全，不受任何病毒、木马等恶意程序的干扰和破 坏。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	所有的交易结果计算都经过平台核实，相应的计算方法都会在平台上进行公示，但是平台不能保证 其计算没有误差或不受干扰。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第四十九条会员知悉并同意，在任何情形下，平台不会就下列任一事项承担责任：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）用户的收入损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	用户的交易利润或合同损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	服务中断、中止或终止而引起的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	预期可节省交易成本的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	信息传递问题而造成的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	措施投资或交易机会的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（七）	商誉或声誉的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（八）	数据的遗失或损坏而造成的损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（九）	购买替代产品或服务的开销；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十）任何由于侵权（包括故意和过失）、违约、或其他任何原因导致的间接的、特殊的或附带性的损 失，不论此种损失是否为平台所能合理预见，亦不论平台是否事先被告知存在此种损失的可能性。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十条会员了解并同意，发生以下任一情形时，平台有权拒绝赔偿会员的全部或部分损失：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）平台有合理的理由认为会员在平台的行为系涉嫌违法或不道德行为。</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）会员误以为系平台原因造成损失的情形;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）任何非因平台原因引起的其他损失。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十一条平台服务的合作单位所提供的服务品质及内容由该合作单位自行负责。平台的内容可能涉 及由第三方所有、控制或者运营的其他网站（以下简称“第三方网站”）。平台不能保证也没有义务保证 第三方网站上任何信息的真实性和有效性。会员确认按照第三方网站的服务协议使用第三方网站，而不 是按照本协议。第三方网站不是平台推荐或者介绍的，第三方网站的内容、产品、广告和其他任何信息 均由会员自行判断并承担风险，而与平台无关。会员经由平台服务的使用下载或取得任何资料，应由会 员自行考量且自负风险，因资料的下载而导致的任何损坏由会员自行承担。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十二条会员自平台及平台工作人员或经由平台服务取得的建议或资讯，无论其为书面或口头，均 不构成平台对平台服务的任何保证。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十三条平台不保证为向会员提供便利而设置的外部链接的准确性、有效性、安全性和完整性，同 时，对于该等外部链接指向的不由平台实际控制的任何网页上的内容，平台不承担任何责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十四条在法律允许的情况下，平台对于与本协议有关或由本协议引起的，或者，由于使用平台、 或由于其所包含的或以其他方式通过平台提供给会员的全部信息、内容、材料、产品（包括软件）和服 务、或购买和使用产品引起的任何间接的、惩罚性的、特殊的、派生的损失（包括业务损失、收益损 失、利润损失、使用数据或其他经济利益的损失），不论是如何产生的，也不论是由对本协议的违约</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（包括违反保证）还是由侵权造成的，均不负有任何责任，即使其事先已被告知此等损失的可能性。另 外即使本协议规定的排他性救济没有达到其基本目的，也应排除平台对上述损失的责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十五条除本协议另有规定外，在任何情况下，平台对本协议所承担的违约赔偿责任总额不超过向 会员收取的当笔数字资产交易平台服务费用总额。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十六条除本协议另有规定外，在任何情况下，因会员违反本协议或其所在国法律法规而导致平台 遭受损失的，会员应当赔偿平台所遭受的一切直接和/或间接的损失（包括诉讼费用等）。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十七条平台和会员均承认普通法对违约或可能违约情况的救济措施可能不足以弥补守约方遭受的 全部或部分损失，故双方同意，协议一方有权在协议另一方违约或可能违约情况下寻求禁令救济以及普 通法或衡平法允许的其他所有的补救措施。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十八条平台在本协议中做出的保证和承诺是平台就其依据本协议提供服务的唯一保证和陈述（以 下简称“协议保证”），取代任何其他途径和方式产生的保证和承诺（以下简称“非协议保证”），无论非 协议保证是以书面或口头，明示或暗示的形式做出。所有协议保证仅仅由平台做出，对平台自身具有约 束力，其效力不能约束任何第三方。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第五十九条会员知悉并同意，平台并不放弃本协议中未提及的，在法律适用的最大范围内平台所享有 的限制、免除或抵销平台损害赔偿责任的任何权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九章风险提示</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十条会员了解并认可，任何通过平台进行的交易并不能避免以下风险的产生，平台不能也没有义 务为如下风险负责：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，会员有可能遭受损失；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，会员有可 能遭受损失；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	违约风险：因项目方无力或无意愿进行或者继续进行项目开发，而导致的会员有可能遭受损失；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	收益风险：数字资产本身不由任何金融机构或平台发行，数字资产市场是全新的、未经确认的，且 可能不会带来实际收益增长的市场；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（五）	操盘风险：数字资产主要由投机者大量使用，零售和商业市场使用相对较少，数字资产交易存在极 高风险，其全天不间断交易，没有涨跌限制，价格容易受庄家、全球政府政策的影响而大幅波动；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（六）	不可抗力因素导致的风险；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（七）	会员过错：因会员的过错导致的任何损失，包括：决策失误、操作不当、遗忘或泄露密码、密码被 他人破解、会员使用的计算机系统被第三方侵入、会员委托第三人代理交易时第三人恶意或不当操作而 造成的损失。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六十一条数字资产交易有极高风险，并不适合绝大部分人士投资。会员知悉并了解此投资有可能导 致部分损失或全部损失，所以会员应当以能承受的损失程度来决定其投资或交易的数量。除了本协议第 六十条提示的风险外，还会有未能预测的风险存在。会员应慎重评估自己的财政状况，及各项风险，而 做出任何数字资产投资或交易的决定。会员应当承担由该决定产生的全部损失，平台对会员的投资或交 易决定不承担任何责任。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六十二条鉴于数字资产投资或交易所衍生的风险，若用户对该种投资或交易由任何疑问，应当在交 易或投资前事先寻求专业顾问的协助。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第六十三条平台不对任何会员及/或任何交易提供任何担保或条件，无论是明示、默示或法定的。平台 不能也不试图对会员或项目方发布的信息进行控制，对该等信息，平台不承担任何形式的证明、鉴定服 务。平台不能完全保证平台内容的真实性、充分性、可靠性、准确性、完整性和有效性，并且无需承担 任何由此引起的法律责任。会员应依赖于会员的独立判断进行交易，会员应对其做出的判断承担全部责 任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十四条平台对于会员使用平台服务不做任何明示或暗示的保证，包括但不限于平台提供服务的适 用性、没有错误或疏漏、持续性、准确性、可靠性、适用于某一特定用途。同时，我们也不对平台提供 的服务所涉及的技术及信息的有效性、准确性、正确性、可靠性、质量、稳定、完整和及时性做出任何 承诺和保证。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十五条是否登陆或使用平台提供的服务是会员个人的决定且自行承担风险及可能产生的损失。平 台对于数字资产的市场、价值及价格等不做任何明示或暗示的保证，会员知悉并了解数字资产市场的不 稳定性，数字资产的价格和价值随时会大幅波动或崩盘，交易数字资产是会员个人的自由选择及决定， 且自行承担风险及可能产生的损失。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十六条以上并不能揭示会员通过平台进行交易的全部风险及市场的全部情形。会员在做出交易决 策前，应全面了解相关数字资产，根据自身的交易目标、风险承受能力和资产状况等谨慎决策，并自行 承担全部风险。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十七条在任何情况下，对于会员使用平台服务过程中涉及由第三方提供相关服务的责任由该第三 方承担，平台不承担该等责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十八条因会员自身的原因导致的任何损失或责任，由会员自行负责，平台不承担责任。平台不承 担责任的情形包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）会员未按照本协议或平台不时公布的任何规则进行操作导致的任何损失或责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	因会员向平台发送的指令信息不明确、或存在歧义、不完整等导致的任何损失或责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	会员账户内数字资产余额不足导致的任何损失或责任；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	其他因会员原因导致的任何损失或责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十章服务费用及其他费用</span></p><p><span style=\"color: rgb(0, 0, 0);\">第六十九条当会员使用平台服务时，平台向会员收取相关平台服务费用。各项平台服务费用详见会员 使用平台服务时，平台上所列之收费说明及收费标准。平台保留单方面制定及调整平台服务费用收费标 准的权利。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十条会员在使用平台服务过程中可能需要向第三方支付一定的第三方服务费用，具体收费标准详 见第三方网站相关页面，或平台的提示及收费标准。会员同意将根据上述收费标准自行或委托平台或平 台指定的第三方代为向第三方支付该等服务费。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十一章协议之终止</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十一条会员有权在任何时候依据本协议条款之规定向平台申请注销平台账号，依据本协议第三十 五条注销账号的，自平台批准用户注销账号之日起协议终止。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十二条依据本协议第三十五条、三十六条注销账号的，自平台注销用户账号之日起协议终止。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十三条会员死亡或被宣告死亡的，其在本协议项下的各项权利义务由其继承人承担。若会员丧失 全部或部分民事权利能力或民事行为能力，平台或其授权的主体有权根据有效法律文书（包括生效的法 院判决等）或其法定监护人的指示处置与会员账户相关的款项。若继承人或法定监护人决定继续履行本 协议的，则本协议依然有效；反之，则继承人或法定监护人需要依据本协议第三十五条向平台申请注销 账号，自平台批准用户注销账号之日起协议终止。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十四条平台有权依据本协议约定终止平台全部服务，本协议于平台全部服务终止之日终止，清退 流程根据平台公告的具体规定进行操作。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十五条本协议终止后，会员无权要求平台继续向其提供任何服务或履行任何其他义务，包括但不 限于要求平台为会员保留或向会员披露其原本网站账号中的任何信息，向会员或第三方转发任何其未 曾阅读或发送过的信息等。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十六条本协议的终止不影响守约方向违约方主张其他协议终止前违约方之违约责任，也不影响本 协议规定之后合同义务之履行。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十二章个人信息之保护和授权条款</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十七条本协议第十二章中之个人信息应当包括以下信息：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）在用户注册平台账号或者使用账户时，用户根据本平台要求提供的个人注册信息，包括但不限于电 话号码、邮箱信息、身份证件信息；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）在用户使用平台时，或访问平台时，平台自动接收并记录的用户浏览器上的服务器数值，包括但不 限于IP地址等数据及用户要求取用的网页记录； （三）平台收集到的用户在平台进行交易的有关数据，包括但不限于交易记录;</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）平台通过合法途径取得的其他用户个人信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十八条不需要用户额外的同意，用户在平台注册成功即视为用户同意平台收集并使用或披露其个 人信息，且用户了解并同意，基于为用户度身订造平台服务、解决争议并有助确保在平台进行安全交易 的考量，平台可以将收集的用户个人信息用作下列用途包括：</span></p><p><span style=\"color: rgb(0, 0, 0);\">（-）向用户提供平台服务；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（二）	基于主权国家或地区相关主管部门的要求向相关部门进行报告；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（三）	在用户使用平台服务时，平台将用户的信息用于身份验证、客户服务、安全防范、诈骗监测、市场 推广、存档和备份用途，或与第三方合作推广网站等合法用途，确保平台向用户提供的产品和服务的安 全性；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（四）	帮助平台设计新产品及服务，改善平台现有服务目的而进行的信息收集和整理；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（五）	为了使用户了解平台服务的具体情况，用户同意平台向其发送营销活动通知、商业性电子信息以及 提供与用户相关的广告以替代普遍投放的广告；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（六）	平台为完成合并、分立、收购或资产转让而将用户的信息转移或披露给任何非关联的第三方；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（七）	软件认证或管理软件升级；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（八）	邀请用户参与有关平台服务的调查；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（九）	用于和政府机关、公共事务机构、协会等合作的数据分析；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十）用于解决争议或对纠纷进行调停；</span></p><p><span style=\"color: rgb(0, 0, 0);\">（十一）用作其他一切合法目的以及经用户授权的其他用途。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第七十九条平台按照用户在平台上的行为自动追踪关于用户的某些资料。在不透露用户的隐私资料的 前提下，平台有权对整个用户数据库进行分析并对用户数据库进行商业上的利用。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八十条用户同意，平台可在平台的某些网页上使用诸如“Cookies”的资料收集装置。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八十一条平台根据相关法律法规以保护用户的资料。用户因履行本协议提供给平台的信息，平台不 会恶意出售或免费共享给任何第三方，以下情况除外：</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（-）提供独立服务且仅要求服务相关的必要信息的供应商；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（二）	具有合法调阅信息权限并从合法渠道调阅信息的政府部门或其他机构；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（三）	平台的关联公司；</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">（四）	经平台使用方或平台使用方授权代表同意的第三方。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(0, 0, 0);\">第八十二条授权平台，除法律另有规定之外，将用户提供给平台的信息、享受平台服务产生的信息（包 括本协议签署之前提供和产生的信息）以及平台根据本条约定查询、收集的信息，用于平台及其因服务 必要委托的合作伙伴为用户提供服务、推荐产品、开展市场调查与信息数据分析。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十三条授权平台，除法律另有规定之外，基于为用户提供更优质服务和产品的目的，向平台因服 务必要开展合作的伙伴提供、查询、收集用户的信息。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十四条为确保用户信息的安全，平台及其合作伙伴对上述信息负有保密义务，并采取各种措施保 证信息安全。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十五条本协议第十二章项下之条款自本协议签署时生效，具有独立法律效力，不受合同成立与否 及效力状态变化的影响。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十三章知识产权的保护</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十六条平台上所有内容，包括著作、图片、档案、资讯、资料、平台架构、平台画面的安排、平台 设计，文字和图表，软件编译，相关源代码和软件等均由平台或其他权利人依法拥有其知识产权，包括 商标权、专利权、著作权、商业秘密等。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十七条非经平台或其他权利人书面同意，任何人不得擅自使用、修改、复制、公开传播、改变、 散布、发行或公开发表平台程序或内容。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十八条用户未经平台的明确书面同意不许下载（除了页面缓存）或修改平台或其任何部分。用户 不得对平台或其内容进行转售或商业利用；不得收集和利用产品目录、说明和价格；不得对平台或其内 容进行任何衍生利用；不得为其他商业利益而下载或拷贝账户信息或使用任何数据采集、Robots或类似 的数据收集和摘录工具。未经平台的书面许可，严禁对平台的内容进行系统获取以直接或间接创建或编 辑文集、汇编、数据库或人名地址录（无论是否通过Robots、Spiders、自动仪器或手工操作）。另 外，严禁为任何未经本使用条件明确允许的目的而使用平台上的内容和材料。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第八十九条未经平台明确书面同意，不得以任何商业目的对平台网站或其任何部分进行复制、复印、 仿造、出售、转售、访问、或以其他方式加以利用。未经平台明确书面同意，用户不得用任何技巧把平 台或其关联公司的商标、标识或其他专有信息（包括图像、文字、网页设计或形式）据为己有。未经平 台明确书面同意，用户不得以Meta Tag s或任何其他\"隐藏文本\"方式使用平台或其关联公司的名字和商 标。任何未经授权的使用都会终止平台所授予的允许或许可。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十条用户登陆平台或使用平台提供的任何服务均不视为平台向用户转让任何知识产权。尊重知识 产权是用户应尽的义务，如有违反，用户应对平台承担损害赔偿等法律责任。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第十四章一般条款</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十一条本协议是由用户与平台共同签订的，适用于用户在平台的全部活动。本协议内容包括协议正 文条款及已经发布的或将来可能发布的各类规则，所有条款和规则为协议不可分割的一部分，与本协议 正文具有同等法律效力。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十二条如本协议中的任何条款被任何有管辖权的机构认定为不可执行的，无效的或非法的，并不 影响本协议的其余条款的效力。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十三条本协议中约定的权利及义务同样约束通过转让从该权利义务中获取到利益的各方的受让 人，继承人，遗嘱执行人和管理员。会员不得在我们不同意的前提下将本协议项下的权利或义务转让给 任何第三方，但平台可随时将我们在本协议中的权利和义务转让给任何第三方，并于转让之日提前三十</span></p><p class=\"ql-align-justify\">	<span style=\"color: rgb(0, 0, 0);\">（30）天给与用户通知。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十四条如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，则应认为该条款 可与本协议相分割，并可被尽可能接近各方意图的、能够保留本协议要求的经济目的的、有效的新条款 所取代，而且，在此情况下，本协议的其他条款仍然完全有效并具有约束力。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十五条除非本协议中的其他条款另有约定，本协议中的任何规定均不应当被认为创造了、暗示了 或以其他方式将平台视为会员的代理人、受托人或其他代表人。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十六条本协议任何一方未能就单一事件行使与本协议有关的权利或寻求救济，并不影响该缔约方 随后就该单一事件或者在其他事件后行使该权利或者寻求救济。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十七条对违约行为的豁免，或本协议任一条款的放弃，仅在守约方或非寻求放弃方书面签字同意 豁免后方能生效。任何本协议项下的违约豁免，不能认定或解释为守约方对其后再次违约或其他违约行 为的豁免；未行使任何权利或救济不得以任何方式被解释为对该等权利或救济的放弃。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十八条本协议依据塞舌尔共和国法律订立，其成立、解释、内容及执行均适用塞舌尔共和国相关 法律规定；任何因本协议引起或与本协议相关的索赔或诉讼，都应当由塞舌尔共和国的法律进行解释和 执行。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第九十九条除非本协议项下其他规则另有约定，双方同意，由新加坡国际仲裁委员会对任何由本协议 引起或与本协议相关的索赔或诉讼进行仲裁。</span></p><p><span style=\"color: rgb(0, 0, 0);\">第百条本协议自用户获得本网站账号时生效，对协议双方具有约束力。</span></p>', 'zh_CN', '2019-10-26 15:14:39', '2019-10-31 19:13:12', 'user');

-- ----------------------------
-- Table structure for conf_version
-- ----------------------------
DROP TABLE IF EXISTS `conf_version`;
CREATE TABLE `conf_version`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id主键',
  `version` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `app_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'app版本路径',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `compel` tinyint(1) NULL DEFAULT NULL COMMENT '是否强制更新：0-否，1-是',
  `device` char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备 android | ios',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'app应用版本表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_version
-- ----------------------------
INSERT INTO `conf_version` VALUES ('4e95c62f-9d4a-4b6a-a1ac-6f030870c9d0', '1.1.2', 'http://18.163.227.136:80/files/download/apk/io.duobit_test0722.apk', '', 1, 'android', '2020-07-22 09:11:56', '2020-07-22 09:12:40');

-- ----------------------------
-- Table structure for conf_wgt_version
-- ----------------------------
DROP TABLE IF EXISTS `conf_wgt_version`;
CREATE TABLE `conf_wgt_version`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id主键',
  `wgt_version` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '补丁版本号',
  `wgt_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '补丁下载地址',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '补丁管理表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of conf_wgt_version
-- ----------------------------
INSERT INTO `conf_wgt_version` VALUES ('00b3dc3a-cfe5-4bd1-a69c-266f9fa96f1e', '1.1.5', 'http://18.163.227.136:80/files/download/wgt/io.duobit_test-1.1.5.wgt', '更新', '2020-07-23 16:10:15', '2020-07-23 16:10:15');
INSERT INTO `conf_wgt_version` VALUES ('17a23743-d2eb-4bf1-a9e7-071edcce777f', '1.1.6', 'http://18.163.227.136:80/files/download/wgt/io.duobit_test-1.1.6.wgt', '版本更新', '2020-07-23 19:40:35', '2020-07-23 19:40:35');
INSERT INTO `conf_wgt_version` VALUES ('79725171-e9df-43ec-a0f3-6cee25c905c8', '1.1.7', 'http://18.163.227.136:80/files/download/wgt/io.duobit_test-1.1.7.wgt', '更新', '2020-07-23 20:29:04', '2020-07-23 20:29:04');
INSERT INTO `conf_wgt_version` VALUES ('b3004226-c8bc-419e-a6a4-88e2d34ca45a', '1.1.3', 'http://18.163.227.136:80/files/download/wgt/io.duobit_test-1.1.3.wgt', '更新', '2020-07-22 16:37:36', '2020-07-22 16:37:36');
INSERT INTO `conf_wgt_version` VALUES ('fe017122-6342-4a56-aa5a-d2c96634fe57', '1.1.4', 'http://18.163.227.136:80/files/download/wgt/io.duobit_test-1.1.4.wgt', '版本更新', '2020-07-23 10:41:42', '2020-07-23 10:41:42');

-- ----------------------------
-- Table structure for conf_white_paper
-- ----------------------------
DROP TABLE IF EXISTS `conf_white_paper`;
CREATE TABLE `conf_white_paper`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数字货币类型，BTC、ETH、EOS',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `issue_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行时间',
  `total_supply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行总量',
  `total_circulation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流通量',
  `ico_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '众筹价格',
  `white_paper` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '白皮书',
  `descr` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简介',
  `coin_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'W 文件 L 链接',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_authentication_apply
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_authentication_apply`;
CREATE TABLE `dapp_u_authentication_apply`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证件类型',
  `id_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证件号',
  `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `nationality` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `file_url1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径1',
  `file_url2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径2',
  `file_url3` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径3',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'W' COMMENT '审核状态:等待审核(W)、同意(Y)、驳回(N)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户认证申请表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_config
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_config`;
CREATE TABLE `dapp_u_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `data_tag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置说明',
  `data_key` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '查询配置的key，唯一',
  `data_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置值',
  `data_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态，可用Y,不可用N',
  `create_time` datetime(0) NOT NULL,
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户模块配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_high_authentication_apply
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_high_authentication_apply`;
CREATE TABLE `dapp_u_high_authentication_apply`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `file_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'W' COMMENT '状态:等待审核(W)、同意(Y)、驳回(N)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '高级认证申请表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_sms_count
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_sms_count`;
CREATE TABLE `dapp_u_sms_count`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收短信的号码',
  `sms_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短信类型',
  `sms_count` tinyint(4) NOT NULL COMMENT '短信条数',
  `sms_date` date NOT NULL COMMENT '日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短信记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_authentication
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_authentication`;
CREATE TABLE `dapp_u_user_authentication`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `id_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号',
  `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `nationality` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `file_url1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径1',
  `file_url2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径2',
  `file_url3` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径3',
  `file_url4` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径4，用于存放高级认证文件',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id`) USING BTREE COMMENT '用户id唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户认证信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_info
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_info`;
CREATE TABLE `dapp_u_user_info`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `google_auth` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '谷歌验证器',
  `low_auth` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '初级认证：等待审核(W)、已认证(Y)、新建| 未认证(N)',
  `high_auth` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '高级认证：等待审核(W)、已认证(Y)、新建| 未认证(N)',
  `incr_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增码',
  `random_number` tinyint(4) NULL DEFAULT NULL COMMENT '随机数，和自增码组合作为邀请码',
  `grade` tinyint(4) NULL DEFAULT 0 COMMENT '用户等级',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_incr_code`(`incr_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 686050 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_list
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_list`;
CREATE TABLE `dapp_u_user_list`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `list_type` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名单类型:BLACK(黑名单)、WHITE(白名单)',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型：登录、提现等',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户黑白名单列表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_login
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_login`;
CREATE TABLE `dapp_u_user_login`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_login_log`;
CREATE TABLE `dapp_u_user_login_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `login_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陸是PC或者APP',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_main
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_main`;
CREATE TABLE `dapp_u_user_main`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `nick_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `international` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国际名称',
  `international_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机国际区号',
  `mobile_phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mobile_phone`(`mobile_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '最主要的用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_main_copy1
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_main_copy1`;
CREATE TABLE `dapp_u_user_main_copy1`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `nick_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `international` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国际名称',
  `international_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机国际区号',
  `mobile_phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mobile_phone`(`mobile_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '最主要的用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_opt_log
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_opt_log`;
CREATE TABLE `dapp_u_user_opt_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `opt_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户操作记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_relation`;
CREATE TABLE `dapp_u_user_relation`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前用户id',
  `pid` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `relation_chain` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关系链信息',
  `tree_depth` tinyint(4) NOT NULL DEFAULT 0 COMMENT '关系深度',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_reply
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_reply`;
CREATE TABLE `dapp_u_user_reply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_open_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `suggestion_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题反馈id',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回复内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_open_id`(`user_open_id`) USING BTREE,
  INDEX `idx_suggestion_id`(`suggestion_id`) USING BTREE COMMENT '反馈问题索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1038 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户建议反馈回复表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_suggestions
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_suggestions`;
CREATE TABLE `dapp_u_user_suggestions`  (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `text_suggestion` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户反馈内容',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '状态（新建:1、已读:2、忽略:3）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户建议反馈表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dapp_u_user_suggestions_copy1
-- ----------------------------
DROP TABLE IF EXISTS `dapp_u_user_suggestions_copy1`;
CREATE TABLE `dapp_u_user_suggestions_copy1`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `text_suggestion` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户反馈内容',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '状态（新建:1、已读:2、忽略:3）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户建议反馈表' ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 4691 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 4691 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for otc_ad
-- ----------------------------
DROP TABLE IF EXISTS `otc_ad`;
CREATE TABLE `otc_ad`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告id',
  `ad_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告流水号',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `price` decimal(20, 8) NOT NULL COMMENT '广告单价',
  `total_num` decimal(20, 8) NOT NULL COMMENT '发布数量',
  `last_num` decimal(20, 8) NOT NULL COMMENT '剩余数量',
  `coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币-对应数量',
  `unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币-对应单价',
  `min_limit` decimal(20, 8) NOT NULL COMMENT '最小单价限额',
  `max_limit` decimal(20, 8) NOT NULL COMMENT '最大单价限额',
  `charge_ratio` decimal(20, 8) NOT NULL COMMENT '手续费比率',
  `ad_pay` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告支付方式-多个时用字符隔开',
  `ad_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告类型-买/卖',
  `ad_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告状态',
  `ad_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '广告备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_ad_number`(`ad_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_ad_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_ad_handle_log`;
CREATE TABLE `otc_ad_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ad_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告流水号',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改前状态',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改后状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币广告操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal`;
CREATE TABLE `otc_appeal`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉记录id',
  `order_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单流水号',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal_detail
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal_detail`;
CREATE TABLE `otc_appeal_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉图片id',
  `appeal_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉记录id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `appeal_role` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉方角色-广告方申诉、订单方申诉',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉说明',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal_handle_log`;
CREATE TABLE `otc_appeal_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉日志id',
  `order_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉订单流水号',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作管理员ip地址',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理后的订单状态',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉处理日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal_img
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal_img`;
CREATE TABLE `otc_appeal_img`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `appeal_detail_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉详情记录id',
  `appeal_img_url` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉图片路径',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_bill
-- ----------------------------
DROP TABLE IF EXISTS `otc_bill`;
CREATE TABLE `otc_bill`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对账id',
  `record_number` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单或广告流水号',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `freezeBalance` decimal(20, 8) NOT NULL COMMENT '冻结余额',
  `freeBalance` decimal(20, 8) NOT NULL COMMENT '可用余额',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种',
  `bill_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金变动类型-发布、成交等等',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币资金对账表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_coin
-- ----------------------------
DROP TABLE IF EXISTS `otc_coin`;
CREATE TABLE `otc_coin`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种配置id',
  `coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `coin_net` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币主网标识',
  `coin_decimal` tinyint(4) NOT NULL COMMENT '基本货币小数长度',
  `unit_decimal` tinyint(4) NOT NULL COMMENT '二级货币小数长度',
  `coin_service_charge` decimal(20, 8) NOT NULL COMMENT '币种手续费',
  `rank` tinyint(4) NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币币种配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of otc_coin
-- ----------------------------
INSERT INTO `otc_coin` VALUES ('1', 'BTC', 'CNY', 'BTC', 8, 2, 0.00200000, 4, 'Y', '2019-04-20 09:52:24', '2019-11-03 23:03:56');
INSERT INTO `otc_coin` VALUES ('2', 'ETH', 'CNY', 'ETH', 18, 2, 0.00200000, 2, 'Y', '2019-04-20 09:52:24', '2019-10-31 17:17:13');
INSERT INTO `otc_coin` VALUES ('3', 'EOS', 'CNY', 'EOS', 6, 2, 0.00200000, 3, 'Y', '2019-04-20 09:52:24', '2019-10-31 17:16:49');
INSERT INTO `otc_coin` VALUES ('4', 'USDT', 'CNY', 'BTC', 6, 2, 0.00200000, 1, 'Y', '2019-04-20 09:52:24', '2019-10-31 17:17:40');

-- ----------------------------
-- Table structure for otc_coin_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_coin_handle_log`;
CREATE TABLE `otc_coin_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coin_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改的币种记录id',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的币种',
  `after_coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的币种',
  `before_unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的二级货币',
  `after_unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的二级货币',
  `before_coin_net` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的币种主网标识',
  `after_coin_net` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的币种主网标识',
  `before_coin_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改前的币种小数长度',
  `after_coin_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改后的币种小数长度',
  `before_unit_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改前的二级货币小数长度',
  `after_unit_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改后的二级货币小数长度',
  `before_coin_service_charge` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的手续费',
  `after_coin_service_charge` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的手续费',
  `before_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的币种状态',
  `after_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的币种状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币币种操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_config
-- ----------------------------
DROP TABLE IF EXISTS `otc_config`;
CREATE TABLE `otc_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置id',
  `data_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息key',
  `data_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息value',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of otc_config
-- ----------------------------
INSERT INTO `otc_config` VALUES ('10', 'market_freeze_amount', '0', 'Y', '2019-10-15 17:05:55', '2020-07-20 12:30:21');
INSERT INTO `otc_config` VALUES ('2', 'auto_cancel', '0.0', 'Y', '2019-10-15 10:00:00', '2020-06-28 18:06:03');
INSERT INTO `otc_config` VALUES ('3', 'auto_cancel_interval', '15', 'N', '2019-10-15 10:01:24', '2019-10-29 19:33:08');
INSERT INTO `otc_config` VALUES ('4', 'ad_service_charge', '0.1', 'Y', '2019-10-15 10:06:24', '2020-06-28 18:05:42');
INSERT INTO `otc_config` VALUES ('5', 'order_service_charge', '0.1', 'N', '2019-10-15 10:06:58', '2019-10-15 03:52:30');
INSERT INTO `otc_config` VALUES ('6', 'sell_service_charge', '0.1', 'N', '2019-10-15 09:42:59', '2019-10-15 03:52:37');
INSERT INTO `otc_config` VALUES ('9', 'market_freeze_coin', 'USDT', 'Y', '2019-10-15 17:05:18', '2019-10-15 14:53:52');

-- ----------------------------
-- Table structure for otc_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_config_handle_log`;
CREATE TABLE `otc_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `data_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改的key',
  `before_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的值',
  `after_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的值',
  `before_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币配置操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_deal_stats
-- ----------------------------
DROP TABLE IF EXISTS `otc_deal_stats`;
CREATE TABLE `otc_deal_stats`  (
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `ad_trans_num` int(11) NOT NULL DEFAULT 0 COMMENT '广告交易次数',
  `ad_mark_num` int(11) NOT NULL DEFAULT 0 COMMENT '广告成交次数',
  `order_sell_num` int(11) NOT NULL DEFAULT 0 COMMENT '卖单成交次数',
  `order_buy_num` int(11) NOT NULL DEFAULT 0 COMMENT '买单成交次数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币用户成交信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_apply
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_apply`;
CREATE TABLE `otc_market_apply`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '冻结代币',
  `amount` decimal(20, 8) NOT NULL COMMENT '冻结金额',
  `apply_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请类型(MARKET：申请市商、CANCEL：申请取消市商)',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请状态(NEW：待处理、AGREE：同意、REJECT：驳回)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_apply_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_apply_handle_log`;
CREATE TABLE `otc_market_apply_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `market_apply_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市商申请记录id',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_freeze
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_freeze`;
CREATE TABLE `otc_market_freeze`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `market_apply_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市商申请记录id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '冻结代币',
  `amount` decimal(20, 8) NOT NULL COMMENT '冻结数量',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_unique`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_user
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_user`;
CREATE TABLE `otc_market_user`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 : (NOTMARKET：未认证、MARKET：认证、CANCELING：取消中)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_unique`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_user_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_user_handle_log`;
CREATE TABLE `otc_market_user_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_order
-- ----------------------------
DROP TABLE IF EXISTS `otc_order`;
CREATE TABLE `otc_order`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `order_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单流水号',
  `ad_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告id',
  `coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `buy_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买方用户id',
  `buy_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买方操作状态',
  `sell_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卖方用户id',
  `sell_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卖方操作状态',
  `amount` decimal(20, 8) NOT NULL COMMENT '交易数量',
  `price` decimal(20, 8) NOT NULL COMMENT '交易单价',
  `turnover` decimal(20, 8) NOT NULL COMMENT '交易金额',
  `charge_ratio` decimal(20, 8) NOT NULL COMMENT '手续费比率',
  `order_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单类型-买/卖',
  `order_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态-',
  `order_pay_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_number`(`order_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_user_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_user_handle_log`;
CREATE TABLE `otc_user_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `handle_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的订单或广告流水号',
  `handle_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `handle_number_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作订单类型-广告或订单',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_user_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `otc_user_pay_info`;
CREATE TABLE `otc_user_pay_info`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pay_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付类型:----微信WX，支付宝ZFB，银行卡BANK',
  `account_info` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信昵称、支付宝昵称',
  `collection_code_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款码地址',
  `bank_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡持卡人真实姓名',
  `bank_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户交易方式信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_auth_system_menu
-- ----------------------------
DROP TABLE IF EXISTS `pc_auth_system_menu`;
CREATE TABLE `pc_auth_system_menu`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名字',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单标识,唯一',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源地址',
  `pid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级权限id',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `icon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目录图标',
  `ranking` tinyint(4) NOT NULL DEFAULT 0 COMMENT '排行',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'Y' COMMENT '显示状态： 显示(Y)，隐藏(N)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pc_auth_system_menu
-- ----------------------------
INSERT INTO `pc_auth_system_menu` VALUES ('010c1a9b-4119-466b-9391-6c4108ecaed2', '市商配置查看', 'OtcMarketConfigShow', '#', '8185a79a-6885-418e-98e5-15e699f670f4', 'F', NULL, 0, 'Y', '2019-07-22 14:58:27', '2019-07-22 14:58:29');
INSERT INTO `pc_auth_system_menu` VALUES ('0330a3fc-3dc9-4d6a-a76a-c3f5105c847d', '撮合机器人', 'MatchBot', '#', NULL, 'M', '', 13, 'Y', '2019-06-05 16:24:09', '2019-06-05 16:24:13');
INSERT INTO `pc_auth_system_menu` VALUES ('04886db5-3f72-42cd-bb3d-99f02d587b34', '待初审提币列表查看', 'WaitFirstExamShow', '#', 'bfdfffa0-88e2-4feb-8af1-37e758c19847', 'F', '', 0, 'Y', '2019-03-19 19:17:53', '2019-03-19 19:17:56');
INSERT INTO `pc_auth_system_menu` VALUES ('04d5995c-c3b4-4b31-b701-d0b0c7d66d68', '币币委托列表详情', 'BCoinListDetail', '#', 'c1b52f6d-f039-4d34-b957-fde84b3519ca', 'F', '', 1, 'Y', '2019-03-14 13:41:31', '2019-03-14 13:41:34');
INSERT INTO `pc_auth_system_menu` VALUES ('06a8b915-0f13-46e4-b512-e25629ea5b8a', '币币委托列表查看', 'BCoinListShow', '#', 'c1b52f6d-f039-4d34-b957-fde84b3519ca', 'F', '', 0, 'Y', '2019-03-19 19:13:25', '2019-03-19 19:13:27');
INSERT INTO `pc_auth_system_menu` VALUES ('06acb6e4-d7ea-468b-b1f3-82f91f7895c4', '轮播图新增', 'SystemImageAdd', '#', 'a1bd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 2, 'Y', '2019-03-25 15:17:59', '2019-03-25 15:18:02');
INSERT INTO `pc_auth_system_menu` VALUES ('076beed3-dac4-463b-ad79-cdccbca4ac15', '登录日记查看', 'LoginRecordShow', '#', '36bd2a6e-52f4-4c1e-b319-714fd09ffffc', 'F', '', 0, 'Y', '2019-03-19 19:12:44', '2019-03-19 19:12:47');
INSERT INTO `pc_auth_system_menu` VALUES ('07a630f3-b4f3-42f3-b843-0a3cc7d6f9f5', 'APP版本查看', 'VersionShow', '#', '230bce00-2998-42c4-b8a9-53dd7f7c961b', 'F', '', 0, 'Y', '2019-03-19 19:28:04', '2019-03-19 19:28:06');
INSERT INTO `pc_auth_system_menu` VALUES ('08889ddc-45ff-4279-a56e-a12f0b48e394', '资产归集', 'AssetCollection', '#', '', 'M', '', 6, 'Y', '2019-04-11 17:51:24', '2019-04-11 17:51:25');
INSERT INTO `pc_auth_system_menu` VALUES ('0980b42e-19aa-4391-95ba-eff6f62f2008', '钱包账户资金明细查看', 'WalletShow', '#', '14e39793-bb75-4b22-b6d4-12d70f58611e', 'F', '', 0, 'N', '2019-03-19 19:22:37', '2019-03-19 19:22:40');
INSERT INTO `pc_auth_system_menu` VALUES ('0a1edec9-1f39-4dfe-810f-7f8ca1298ddd', '下单异常查看', 'OrderErrorShow', '#', '3e875170-f522-417a-9168-7563b63b7e26', 'F', NULL, 0, 'Y', '2019-04-19 14:09:02', '2019-04-19 14:09:04');
INSERT INTO `pc_auth_system_menu` VALUES ('0acf54f5-c747-44fb-909e-9da5cfb22ed0', '公告管理', 'SystemNotice', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 4, 'Y', '2019-03-25 15:16:06', '2019-03-25 15:16:08');
INSERT INTO `pc_auth_system_menu` VALUES ('0ae9a204-633f-4e80-a46c-60aa27ed2cbc', '法币用户支付信息查看', 'OtcUserPayListShow', '#', '2a030ba1-6e3e-4302-b7e6-9a40bbc47c14', 'F', '', 0, 'Y', '2019-05-13 11:29:19', '2019-05-13 11:29:21');
INSERT INTO `pc_auth_system_menu` VALUES ('0b0100ed-f8df-4268-a8e2-41ebd88e36d4', '市商保证金列表查看', 'OtcMarketFreezeListShow', '#', 'd1c446a5-4178-4941-ab07-2a16d19257f1', 'F', NULL, 0, 'Y', '2019-07-16 11:16:54', '2019-07-16 11:16:57');
INSERT INTO `pc_auth_system_menu` VALUES ('0c064fc1-2fa3-4549-82a5-333fe2eacfd4', 'K线配置查看', 'BotCurrencyConfigShow', '#', '78527984-74c5-40eb-8f2f-a9e1de026524', 'F', NULL, 0, 'Y', '2019-06-05 16:28:49', '2019-06-05 16:28:52');
INSERT INTO `pc_auth_system_menu` VALUES ('0cecedb2-9da2-4861-9957-8c3368dc44d0', '联系我们查看', 'ContactUsShow', '#', '71b9b675-3def-4696-9c9e-e6bba65a4324', 'F', '', 0, 'N', '2019-04-02 14:51:37', '2019-04-02 14:51:39');
INSERT INTO `pc_auth_system_menu` VALUES ('0d8c421c-41c9-458b-af2b-dab6e8e4b17b', '行情列表', 'CurrencyPairList', '#', 'b4652551-0bec-43db-8274-3d9568f3ced0', 'C', '', 2, 'Y', '2019-03-22 11:09:02', '2019-03-22 11:09:03');
INSERT INTO `pc_auth_system_menu` VALUES ('0fb7624a-1b0d-469f-b6fe-29d8c7824183', '交易对查看', 'TradingOnShow', '#', 'f9eadd0b-555c-498d-a65c-2fbcac147cae', 'F', NULL, 0, 'Y', '2019-04-19 14:04:18', '2019-04-19 14:04:20');
INSERT INTO `pc_auth_system_menu` VALUES ('1163a4c6-d8ac-4327-8a87-1c00140ca5f2', 'TRX创建账户设置', 'CreateAccount', '#', '33f0e92c-46ea-4f1c-bfb8-89065bfea71e', 'C', '', 3, 'Y', '2019-03-27 11:02:22', '2019-03-27 11:02:23');
INSERT INTO `pc_auth_system_menu` VALUES ('1185125b-c652-474d-a19c-2485bfcb567d', '法币系统配置查看', 'OtcConfigShow', '#', 'e7605763-7988-473c-bf95-21c73e5fe12d', 'F', '', 0, 'Y', '2019-05-13 17:48:01', '2019-05-13 17:48:05');
INSERT INTO `pc_auth_system_menu` VALUES ('11fe5fa2-b0bf-4111-9e23-f70aaa7d0ab7', 'TRX创建账户设置查看', 'CreateAccountShow', '#', '1163a4c6-d8ac-4327-8a87-1c00140ca5f2', 'F', '', 0, 'Y', '2019-03-27 11:04:48', '2019-03-27 11:04:50');
INSERT INTO `pc_auth_system_menu` VALUES ('12422c84-a6d3-4445-a7fd-d73c3ebf1a42', '服务协议查看', 'AgreementShow', '#', 'def66325-7ef7-4872-8a14-328442fc2673', 'F', NULL, 0, 'Y', '2019-05-25 15:39:11', '2019-05-25 15:39:14');
INSERT INTO `pc_auth_system_menu` VALUES ('14e39793-bb75-4b22-b6d4-12d70f58611e', '钱包账户资金明细', 'Wallet', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 7, 'N', '2019-03-13 20:18:42', '2019-03-13 20:18:45');
INSERT INTO `pc_auth_system_menu` VALUES ('1555fa14-6576-47f8-a9e4-885ae909aa24', '提现手续费查看', 'WithdrawShow', '#', '99d69d0e-ed92-48be-8b8a-630c1dd6f8c7', 'F', '', 0, 'Y', '2019-03-19 19:16:58', '2019-03-19 19:17:00');
INSERT INTO `pc_auth_system_menu` VALUES ('175cd24b-93e3-46ad-a5f4-81e54032d7ea', '币种管理查看', 'CurrencyManagementShow', '#', 'afab6cce-c5d2-4fa7-b7ec-1cbba1e1edd6', 'F', '', 0, 'Y', '2019-10-08 15:28:10', '2019-10-08 15:28:12');
INSERT INTO `pc_auth_system_menu` VALUES ('19ad8999-cbcb-45e1-a8a5-e2a67061fa56', '用户资产详情与审核', 'AssetDetail', '#', 'a4bc1984-aecd-447f-9bbb-c79f1aabf630', 'F', '', 1, 'Y', '2019-03-14 13:39:11', '2019-03-14 13:39:17');
INSERT INTO `pc_auth_system_menu` VALUES ('1a7c4644-b8cb-409a-b0f7-645e7dbc4375', '币币费率标准', 'Ratio', '#', '4454e6d1-35f3-4102-85f6-a9264172171b', 'C', '', 1, 'Y', '2019-03-13 19:58:20', '2019-03-13 19:58:22');
INSERT INTO `pc_auth_system_menu` VALUES ('1fd94c16-4147-489f-ae82-63560b7e5195', '管理员管理', 'Manage', '#', '', 'M', '', 3, 'Y', '2019-03-13 19:54:47', '2019-03-13 19:54:50');
INSERT INTO `pc_auth_system_menu` VALUES ('204fedb7-e684-4c23-8fd4-098fca45481d', '币币交易对修改状态', 'UpdTradeStatus', '#', '6c820e9b-9542-4be5-b2b5-e079d7999a23', 'F', '', 3, 'Y', '2019-03-22 11:33:32', '2019-03-22 11:33:34');
INSERT INTO `pc_auth_system_menu` VALUES ('21d02ec0-5ada-4739-af80-8610839e7367', '配置操作日志查看', 'HandleConfigLogShow', '#', '2e7b781e-f941-4b5d-a8ad-bb82bcc12e6e', 'F', '', 0, 'Y', '2019-03-23 11:46:43', '2019-03-23 11:46:45');
INSERT INTO `pc_auth_system_menu` VALUES ('230bce00-2998-42c4-b8a9-53dd7f7c961b', 'APP版本', 'Version', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 1, 'Y', '2019-03-13 20:24:56', '2019-03-13 20:24:58');
INSERT INTO `pc_auth_system_menu` VALUES ('28e78de1-ad89-4148-b81b-90ab8f4358a4', '法币资金变动列表查看', 'OtcBillListShow', '#', '894f55b1-8c75-4e87-ad24-7c4247d72e30', 'F', '', 0, 'Y', '2019-05-13 17:48:42', '2019-05-13 17:48:44');
INSERT INTO `pc_auth_system_menu` VALUES ('297ee11f-0ef7-4c49-8751-94c024809ef0', '订单管理', 'OrderManage', '#', '', 'M', '', 2, 'Y', '2019-03-13 19:52:36', '2019-03-13 19:52:39');
INSERT INTO `pc_auth_system_menu` VALUES ('29fe5fa2-b0bf-4111-9e23-f70aaa7d0ab7', '发币账户查看', 'CooutAccountShow', '#', 'fd49668d-4ba2-472a-b1c4-6039d3336822', 'F', '', 0, 'Y', '2019-03-27 11:04:48', '2019-03-27 11:04:50');
INSERT INTO `pc_auth_system_menu` VALUES ('2a030ba1-6e3e-4302-b7e6-9a40bbc47c14', '法币用户支付信息', 'OtcUserPayList', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', NULL, 7, 'Y', '2019-05-13 11:29:19', '2019-05-13 11:29:21');
INSERT INTO `pc_auth_system_menu` VALUES ('2bddfdc0-4ebc-486d-a2ca-cec247383ed4', '市商申请操作日志查看', 'OtcMarketApplyHandleLogShow', '#', 'a5a48677-bdc5-46ec-8108-ce6f8ea0d9e5', 'F', NULL, 0, 'Y', '2019-07-16 11:12:05', '2019-07-16 11:12:08');
INSERT INTO `pc_auth_system_menu` VALUES ('2e9b908d-711c-48ec-806b-d102e6d895cc', '账户查看', 'QuantizedAccountShow', '#', 'f331eb5f-01b6-4187-b54e-3a8b5f6186f0', 'F', NULL, 0, 'Y', '2019-04-26 14:05:03', '2019-04-26 14:05:05');
INSERT INTO `pc_auth_system_menu` VALUES ('2f7cbc67-3624-4b87-88da-f0b385ab9a11', '私募资金列表', 'PrivateBalance', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 13, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('2f7cbc67-3624-4b87-88da-f0b385ab9a25', '加减币操作', 'Operation', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 12, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('2f7cbc67-3624-4b87-88da-f0b385ab9a91', '资金发放管理', 'WalletManagement', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 12, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('3149e502-c5d1-4ddc-81bc-81941d79a221', '删除角色', 'DelRole', '#', '58a3ac6b-2b32-4291-919a-8d7b9d20466a', 'F', '', 2, 'Y', '2019-03-18 14:23:44', '2019-03-18 14:23:46');
INSERT INTO `pc_auth_system_menu` VALUES ('32ec5a85-956b-4c5d-be05-082a40e8ce86', '法币广告操作日志', 'OtcAdHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 1, 'Y', '2019-05-10 17:44:39', '2019-05-10 17:44:42');
INSERT INTO `pc_auth_system_menu` VALUES ('33218ca7-09e5-4092-827e-9a8eef7f7800', '市商用户列表操作', 'OtcMarketUserListHandle', '#', 'b435c24c-3459-4a60-9f28-cd849c20f1b0', 'F', NULL, 1, 'Y', '2019-07-22 16:30:56', '2019-07-22 16:30:57');
INSERT INTO `pc_auth_system_menu` VALUES ('33f0e92c-46ea-4f1c-bfb8-89065bfea71e', '账户管理', 'AccountManage', '#', '', 'M', '', 10, 'Y', '2019-03-27 11:00:55', '2019-03-27 11:00:57');
INSERT INTO `pc_auth_system_menu` VALUES ('345e10d3-cc48-4799-9d91-995c2911e6f8', '待复审提币列表查看', 'WaitAgainExamShow', '#', '920e05dc-4775-4d11-8962-d19353f9a328', 'F', '', 0, 'Y', '2019-03-19 19:18:36', '2019-03-19 19:18:39');
INSERT INTO `pc_auth_system_menu` VALUES ('35fbde21-ef67-4454-84dc-c9df1bf060b1', '法币广告列表', 'OtcAdList', '#', '297ee11f-0ef7-4c49-8751-94c024809ef0', 'C', NULL, 3, 'Y', '2019-05-13 11:02:33', '2019-05-13 11:02:34');
INSERT INTO `pc_auth_system_menu` VALUES ('36bd2a6e-52f4-4c1e-b319-714fd09ffffc', '登录日记', 'LoginRecord', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 6, 'Y', '2019-03-13 19:42:49', '2019-03-13 19:42:52');
INSERT INTO `pc_auth_system_menu` VALUES ('3766cf8d-9159-4b4c-9e60-057d7d4941bd', '资讯查看', 'ConfNews', '#', 'b9727d32-7a80-4910-bae3-251565d8b301', 'F', '', 0, 'N', '2019-04-02 14:55:04', '2019-04-02 14:55:06');
INSERT INTO `pc_auth_system_menu` VALUES ('3778a69c-bf91-487f-9c06-bb3c8f74adf3', '权限', 'Authority', '#', '58a3ac6b-2b32-4291-919a-8d7b9d20466a', 'F', '', 1, 'Y', '2019-03-14 13:42:17', '2019-03-14 13:42:19');
INSERT INTO `pc_auth_system_menu` VALUES ('396598f8-b1ed-466f-a484-b9552e409f50', '币币委托列表撤销', 'BCoinListRevoke', '#', 'c1b52f6d-f039-4d34-b957-fde84b3519ca', 'F', '', 2, 'Y', '2019-03-22 09:49:21', '2019-03-22 09:49:24');
INSERT INTO `pc_auth_system_menu` VALUES ('39c4e90b-18c8-4268-bba4-9d0879b942dd', '法币广告伞下订单列表', 'OtcAdDetail', '#', '35fbde21-ef67-4454-84dc-c9df1bf060b1', 'F', '', 2, 'Y', '2019-05-13 11:02:33', '2019-05-13 11:02:34');
INSERT INTO `pc_auth_system_menu` VALUES ('3c3073ed-5caf-4d49-ac24-b25f8e631e70', '法币广告操作日志查看', 'OtcAdHandleLogShow', '#', '32ec5a85-956b-4c5d-be05-082a40e8ce86', 'F', '', 0, 'Y', '2019-05-10 17:44:39', '2019-05-10 17:44:42');
INSERT INTO `pc_auth_system_menu` VALUES ('3d01db0f-528b-42ec-96bf-b6416c15cc6e', '禁用费率标准', 'UpdRatioStatus', '#', '1a7c4644-b8cb-409a-b0f7-645e7dbc4375', 'F', '', 1, 'Y', '2019-03-22 11:24:43', '2019-03-22 11:24:45');
INSERT INTO `pc_auth_system_menu` VALUES ('3e875170-f522-417a-9168-7563b63b7e26', '下单异常', 'OrderError', '#', 'a21e4b46-02a9-4986-9370-e65e1aca04f0', 'C', NULL, 2, 'Y', '2019-04-19 14:08:19', '2019-04-19 14:08:21');
INSERT INTO `pc_auth_system_menu` VALUES ('4006a91a-8092-4ddd-a2af-7718f49f09bf', 'BTC归集查看', 'BTCAssetShow', '#', '49d478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'F', '', 0, 'Y', '2019-04-11 17:54:12', '2019-04-11 17:54:14');
INSERT INTO `pc_auth_system_menu` VALUES ('42303233-baa8-45b8-b2e5-54026af33d16', '数据统计查看', 'StatisticsShow', '#', 'c75db422-0cc9-4d06-9c24-154b3d873ff8', 'F', '', 0, 'Y', '2019-03-19 19:24:39', '2019-03-19 19:24:42');
INSERT INTO `pc_auth_system_menu` VALUES ('42bb3291-436a-4caf-a5fc-879ebde4ee64', '待复审提币审核与详情', 'AgainExamDetail', '#', '920e05dc-4775-4d11-8962-d19353f9a328', 'F', '', 1, 'Y', '2019-03-14 13:43:52', '2019-03-14 13:43:55');
INSERT INTO `pc_auth_system_menu` VALUES ('4454e6d1-35f3-4102-85f6-a9264172171b', '手续费管理', 'Cost', '#', '', 'M', '', 4, 'Y', '2019-03-13 19:57:47', '2019-03-13 19:57:50');
INSERT INTO `pc_auth_system_menu` VALUES ('4472a81e-f150-40c0-ac40-c18f1f58f752', '币币账户资金明细', 'BBAccount', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 8, 'N', '2019-03-13 20:19:12', '2019-03-13 20:19:15');
INSERT INTO `pc_auth_system_menu` VALUES ('448f470f-19bb-4317-96e6-7d113ae47b36', '修改币种状态', 'UpdCurrencyStatus', '#', '56151568-7a20-4597-af80-b8ed8059e2f8', 'F', '', 3, 'N', '2019-03-22 11:28:50', '2019-03-22 11:28:52');
INSERT INTO `pc_auth_system_menu` VALUES ('461cd87d-b114-4716-97c1-476ad3182673', '待出币提币列表', 'WaitOut', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 3, 'Y', '2019-03-13 20:16:26', '2019-03-13 20:16:31');
INSERT INTO `pc_auth_system_menu` VALUES ('46ddd508-24d2-4827-bb9e-6118a0adaa63', '项目中心查看', 'WhitePaperShow', '#', 'e63d3c82-41c1-4444-9f08-009e5b284343', 'F', NULL, 0, 'Y', '2019-06-10 18:19:43', '2019-06-10 18:19:45');
INSERT INTO `pc_auth_system_menu` VALUES ('48d5015a-798f-4e49-887c-e5aacef6a368', '禁用账号', 'UpdManagerStatus', '#', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'F', '', 2, 'Y', '2019-03-18 14:28:21', '2019-03-18 14:28:23');
INSERT INTO `pc_auth_system_menu` VALUES ('49c11490-0620-4533-8b6b-9cf8a866a8d2', '法币申诉操作日志', 'OtcAppealHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 2, 'Y', '2019-05-10 17:45:32', '2019-05-10 17:45:34');
INSERT INTO `pc_auth_system_menu` VALUES ('49d478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'BTC资产归集', 'BTCAsset', '#', '08889ddc-45ff-4279-a56e-a12f0b48e394', 'C', '', 2, 'Y', '2019-04-11 17:52:51', '2019-04-11 17:52:52');
INSERT INTO `pc_auth_system_menu` VALUES ('4b2b6f90-d748-46d9-bc07-c80955b522a1', '资金提现审核与详情', 'WithdrawDetail', '#', 'f3026d95-951f-4953-a304-a96e17cf45c3', 'F', '', 1, 'Y', '2019-03-14 13:44:36', '2019-03-14 13:44:38');
INSERT INTO `pc_auth_system_menu` VALUES ('4b947437-7daf-4b24-a467-c15e335f43a7', '法币用户操作日志查看', 'OtcUserHandleLogShow', '#', 'c746fabb-ecc7-47f5-a3d8-40734724a509', 'F', '', 0, 'Y', '2019-05-10 17:47:22', '2019-05-10 17:47:24');
INSERT INTO `pc_auth_system_menu` VALUES ('4f0c591b-5882-41e4-8131-0ced5b4b95cb', '订单操作日志查看', 'HandleOrderLogShow', '#', '24a4d447-3723-419a-bba5-3baaa7bfb244', 'F', '', 0, 'Y', '2019-03-23 11:47:15', '2019-03-23 11:47:17');
INSERT INTO `pc_auth_system_menu` VALUES ('4il6a91a-8092-4ddd-a2af-7718f49f09bf', 'TRON归集查看', 'TRONAssetShow', '#', 'uud478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'F', '', 0, 'Y', '2019-04-11 17:54:12', '2019-04-11 17:54:14');
INSERT INTO `pc_auth_system_menu` VALUES ('52027ce8-44a4-434f-af58-96d57fa04ccd', '市商用户列表查看', 'OtcMarketUserListShow', '#', 'b435c24c-3459-4a60-9f28-cd849c20f1b0', 'F', NULL, 0, 'Y', '2019-07-16 11:17:23', '2019-07-16 11:17:24');
INSERT INTO `pc_auth_system_menu` VALUES ('5253836f-ddef-491f-b8ee-0a1f6bd1a384', '法币订单申诉列表查看', 'OtcAppealShow', '#', 'c37a56e0-c4b7-47b8-9acf-02405557c846', 'F', '', 0, 'Y', '2019-05-11 10:55:23', '2019-05-11 10:55:29');
INSERT INTO `pc_auth_system_menu` VALUES ('52f3934d-526e-415d-ba5e-14bc1f2d81da', '法币订单列表', 'OtcOrderList', '#', '297ee11f-0ef7-4c49-8751-94c024809ef0', 'C', NULL, 4, 'Y', '2019-05-13 11:03:06', '2019-05-13 11:03:08');
INSERT INTO `pc_auth_system_menu` VALUES ('5381f151-794e-44fb-b5d0-248683fe9273', '撮合配置删除', 'BotMatchConfigDelete', '#', '77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', 'F', NULL, 4, 'Y', '2019-06-29 09:38:31', '2019-06-29 09:38:32');
INSERT INTO `pc_auth_system_menu` VALUES ('56151568-7a20-4597-af80-b8ed8059e2f8', '币种列表', 'CurrencyList', '#', 'b4652551-0bec-43db-8274-3d9568f3ced0', 'C', '', 1, 'Y', '2019-03-13 20:23:07', '2019-03-13 20:23:10');
INSERT INTO `pc_auth_system_menu` VALUES ('58a3ac6b-2b32-4291-919a-8d7b9d20466a', '角色管理', 'Role', '#', '1fd94c16-4147-489f-ae82-63560b7e5195', 'C', '', 1, 'Y', '2019-03-13 19:55:22', '2019-03-13 19:55:25');
INSERT INTO `pc_auth_system_menu` VALUES ('58dcfdfa-0a14-4664-b96a-be92cd1758c3', '法币配置操作日志', 'OtcConfigHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 4, 'Y', '2019-05-10 17:46:46', '2019-05-10 17:46:48');
INSERT INTO `pc_auth_system_menu` VALUES ('5911bf0e-d729-4d50-a6cc-fa25de2a8827', '帮助中心查看', 'ConfHelpShow', '#', 'd462818f-9676-4b8b-8029-6eb6db61ac65', 'F', '', 0, 'Y', '2019-04-02 14:52:13', '2019-04-02 14:52:16');
INSERT INTO `pc_auth_system_menu` VALUES ('5a549c85-03a8-4803-a70b-5e1b485a9245', '市商用户操作日志', 'OtcMarketUserHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 7, 'Y', '2019-07-16 11:10:19', '2019-07-16 11:10:23');
INSERT INTO `pc_auth_system_menu` VALUES ('5a9a55b8-3d78-49f2-9606-231ef04f262f', '用户资产查看', 'UserAssetShow', '#', 'a4bc1984-aecd-447f-9bbb-c79f1aabf630', 'F', '', 0, 'Y', '2019-03-19 19:09:48', '2019-03-19 19:09:51');
INSERT INTO `pc_auth_system_menu` VALUES ('5c38a89b-58d8-4122-a079-623ca62eff95', '发币账户删除', 'CooutAccountDel', '#', 'fd49668d-4ba2-472a-b1c4-6039d3336822', 'F', '', 2, 'Y', '2019-03-27 11:06:27', '2019-03-27 11:06:29');
INSERT INTO `pc_auth_system_menu` VALUES ('5c65eefa-0274-47e3-87ba-6f91631c7489', '币币交易对修改', 'UpdTrade', '#', '6c820e9b-9542-4be5-b2b5-e079d7999a23', 'F', NULL, 2, 'Y', '2019-10-10 17:08:39', '2019-10-10 17:08:41');
INSERT INTO `pc_auth_system_menu` VALUES ('5f15f1cb-9af6-4958-83f1-4875e0ded2df', '订单变更记录查看', 'OrderInfoShow', '#', 'ac91496f-5c75-4709-a096-d42d8ea1b51a', 'F', NULL, 0, 'Y', '2019-05-21 10:12:08', '2019-05-21 10:12:10');
INSERT INTO `pc_auth_system_menu` VALUES ('635a9d9a-a01a-4c49-b680-eda63a81c865', '资金平衡表查看', 'BalanceShow', '#', 'bf7bc537-c7aa-476b-b253-63f87e8b2a0d', 'F', '', 0, 'Y', '2019-03-19 19:25:18', '2019-03-19 19:25:20');
INSERT INTO `pc_auth_system_menu` VALUES ('6563a4c6-d8ac-4327-8a87-1c00140ca5f2', '发币账户设置', 'CooutAccount', '#', '33f0e92c-46ea-4f1c-bfb8-89065bfea71e', 'C', '', 1, 'Y', '2019-03-27 11:02:22', '2019-03-27 11:02:23');
INSERT INTO `pc_auth_system_menu` VALUES ('66fc9b4d-06fe-41da-8e21-72ea073e9a5e', '修改提现手续费', 'UpdWithdraw', '#', '99d69d0e-ed92-48be-8b8a-630c1dd6f8c7', 'F', '', 1, 'Y', '2019-03-22 17:34:37', '2019-03-22 17:34:39');
INSERT INTO `pc_auth_system_menu` VALUES ('6713bc4c-a3b9-4fb8-9f18-2d1412608264', '市商申请列表', 'OtcMarketApplyList', '#', '871eb662-5681-457c-a5c8-c00b2b79f8da', 'C', NULL, 1, 'Y', '2019-07-16 11:13:53', '2019-07-16 11:13:54');
INSERT INTO `pc_auth_system_menu` VALUES ('69039e06-e97d-11e9-b482-2c768aaecf52', '矿池管理', 'Pool', '#', '', 'M', '', 15, 'Y', '2019-06-05 16:24:09', '2019-06-05 16:24:13');
INSERT INTO `pc_auth_system_menu` VALUES ('6919a25f-7973-4150-9e2e-cd8327d0ac85', '轮播图编辑', 'SystemImageUpd', '#', 'a1bd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 3, 'Y', '2019-03-25 15:18:40', '2019-03-25 15:18:42');
INSERT INTO `pc_auth_system_menu` VALUES ('69fcfb0b-fe78-413e-ad53-1daee0abc00d', '实名审核查看', 'CertificationShow', '#', 'ab90dcdb-48a7-4444-9ddc-70cd02080d3e', 'F', '', 0, 'Y', '2019-03-19 19:10:38', '2019-03-19 19:10:40');
INSERT INTO `pc_auth_system_menu` VALUES ('6c820e9b-9542-4be5-b2b5-e079d7999a23', '币币交易对', 'CurrencyTrade', '#', 'b4652551-0bec-43db-8274-3d9568f3ced0', 'C', '', 3, 'Y', '2019-03-14 17:51:43', '2019-03-14 17:51:45');
INSERT INTO `pc_auth_system_menu` VALUES ('6d9454d8-3040-4864-b6e0-6a93fa3b220a', '法币广告列表查看', 'OtcAdListShow', '#', '35fbde21-ef67-4454-84dc-c9df1bf060b1', 'F', '', 0, 'Y', '2019-05-13 11:02:33', '2019-05-13 11:02:34');
INSERT INTO `pc_auth_system_menu` VALUES ('6e6b2588-613b-4500-85cb-3ab1dcf14aa8', '法币订单申诉完成订单', 'OtcAppealFinishOrder', '#', 'c37a56e0-c4b7-47b8-9acf-02405557c846', 'F', '', 1, 'Y', '2019-05-11 11:21:48', '2019-05-11 11:21:51');
INSERT INTO `pc_auth_system_menu` VALUES ('710bb432-41cb-468b-bef6-0a606550e8a5', '撮合配置操作', 'BotMatchConfigHandle', '#', '77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', 'F', NULL, 1, 'Y', '2019-06-27 14:22:12', '2019-06-27 14:22:14');
INSERT INTO `pc_auth_system_menu` VALUES ('71b9b675-3def-4696-9c9e-e6bba65a4324', '联系我们', 'ContactUs', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 5, 'N', '2019-04-02 10:12:52', '2019-04-02 10:12:54');
INSERT INTO `pc_auth_system_menu` VALUES ('71d56f6b-7a95-4bde-9f3f-a9dd31ac38fb', '用户权限确认按钮', 'CertifyDetailCode', '#', 'ab90dcdb-48a7-4444-9ddc-70cd02080d3e', 'F', '', 2, 'Y', '2019-03-19 19:52:52', '2019-03-19 19:52:54');
INSERT INTO `pc_auth_system_menu` VALUES ('721c46da-848d-48ca-9bcd-f7801bc011d6', '白名单删除', 'DelWhiteCode', '#', 'c2a75e88-8330-400c-b270-f1a643637373', 'F', '', 1, 'Y', '2019-03-18 17:42:08', '2019-03-18 17:42:10');
INSERT INTO `pc_auth_system_menu` VALUES ('7232ed54-d5cb-42c8-be96-390b35405354', '用户管理', 'UserManage', '#', '', 'M', '', 1, 'Y', '2019-03-05 17:40:07', '2019-03-05 17:40:07');
INSERT INTO `pc_auth_system_menu` VALUES ('733a0ab7-5f9a-4bec-b607-f42c7169add2', 'API管理查看', 'SystemApiShow', '#', '9fc89622-1323-4bab-bb64-a627fd7670f2', 'F', '', 0, 'Y', '2019-03-19 19:28:47', '2019-03-19 19:28:49');
INSERT INTO `pc_auth_system_menu` VALUES ('736205ed-7199-4639-b1cc-91724c07e184', '交易对删除', 'TradingOnDel', '#', 'f9eadd0b-555c-498d-a65c-2fbcac147cae', 'F', NULL, 1, 'Y', '2019-04-19 14:04:51', '2019-04-19 14:04:53');
INSERT INTO `pc_auth_system_menu` VALUES ('752f7c6d-f749-482b-a134-8f18cd455db3', '充币账户新增', 'CoinAccountAdd', '#', '6563a4c6-d8ac-4327-8a87-1c00140ca5f2', 'F', '', 1, 'Y', '2019-03-27 11:07:58', '2019-03-27 11:08:02');
INSERT INTO `pc_auth_system_menu` VALUES ('763baad8-3524-4ba3-806a-b6c2bcf8e111', '用户推荐关系列表', 'UserRelationList', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 1, 'Y', '2019-03-05 17:24:01', '2019-03-05 17:24:01');
INSERT INTO `pc_auth_system_menu` VALUES ('763baad8-3524-4ba3-806a-b6c2bcf8eac3', '用户列表', 'UserList', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 1, 'Y', '2019-03-05 17:24:01', '2019-03-05 17:24:01');
INSERT INTO `pc_auth_system_menu` VALUES ('76a3d54d-e509-4485-a97a-92952e078ae1', '修改费率标准', 'UpdRatio', '#', '1a7c4644-b8cb-409a-b0f7-645e7dbc4375', 'F', '', 2, 'Y', '2019-03-22 11:25:32', '2019-03-22 11:25:33');
INSERT INTO `pc_auth_system_menu` VALUES ('77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', '撮合配置', 'BotMatchConfig', '#', '0330a3fc-3dc9-4d6a-a76a-c3f5105c847d', 'C', NULL, 3, 'Y', '2019-06-27 14:19:20', '2019-06-27 14:19:22');
INSERT INTO `pc_auth_system_menu` VALUES ('78527984-74c5-40eb-8f2f-a9e1de026524', 'K线配置', 'BotCurrencyConfig', '#', 'e96a98cf-226d-48f2-8a3f-8c94b7e30305', 'C', NULL, 1, 'Y', '2019-06-05 16:26:53', '2019-06-05 16:26:55');
INSERT INTO `pc_auth_system_menu` VALUES ('797c5aa8-4383-4d64-833a-902dbd337415', '角色管理查看', 'RoleShow', '#', '58a3ac6b-2b32-4291-919a-8d7b9d20466a', 'F', '', 0, 'Y', '2019-03-19 19:14:47', '2019-03-19 19:14:50');
INSERT INTO `pc_auth_system_menu` VALUES ('7b147741-ef8b-4fde-b9fd-f82117e72fe7', '交易对操作日志查看', 'HandleCoinLogShow', '#', 'b005a154-c6fe-4cd8-8f98-860dcdc7879c', 'F', '', 0, 'Y', '2019-03-23 11:45:52', '2019-03-23 11:45:53');
INSERT INTO `pc_auth_system_menu` VALUES ('7e4e0508-aa6f-46cf-b4b9-6c0d9e7c0225', '法币订单申诉详情', 'OtcAppealDetail', '#', 'c37a56e0-c4b7-47b8-9acf-02405557c846', 'F', NULL, 1, 'Y', '2019-05-11 11:21:48', '2019-05-11 11:21:51');
INSERT INTO `pc_auth_system_menu` VALUES ('7e5f4394-1b45-4342-808c-15d6e3b90581', '行情列表修改', 'UpdPair', '#', '0d8c421c-41c9-458b-af2b-dab6e8e4b17b', 'F', NULL, 1, 'Y', '2019-10-10 17:17:40', '2019-10-10 17:17:42');
INSERT INTO `pc_auth_system_menu` VALUES ('8185a79a-6885-418e-98e5-15e699f670f4', '市商配置', 'OtcMarketConfig', '#', '871eb662-5681-457c-a5c8-c00b2b79f8da', 'C', NULL, 4, 'Y', '2019-07-22 14:57:30', '2019-07-22 14:57:32');
INSERT INTO `pc_auth_system_menu` VALUES ('8201accc-b2e4-4c58-9428-499573b9dc0d', '撮合配置操作日志查看', 'BotMatchConfigHandleLogShow', '#', 'f1a9585d-d95f-4374-a14e-1ae5614f3270', 'F', NULL, 0, 'Y', '2019-06-27 14:23:04', '2019-06-27 14:23:07');
INSERT INTO `pc_auth_system_menu` VALUES ('8202599a-a97d-4406-8e32-4a62ef947409', '币种列表查看', 'AddCurrencyShow', '#', '56151568-7a20-4597-af80-b8ed8059e2f8', 'F', '', 0, 'Y', '2019-03-19 19:26:44', '2019-03-19 19:26:46');
INSERT INTO `pc_auth_system_menu` VALUES ('86069630-f835-479f-b756-b050300a9111', '用户推荐关系列表查看', 'UserRelationListShow', '#', '763baad8-3524-4ba3-806a-b6c2bcf8e111', 'F', '', 0, 'Y', '2019-03-19 19:08:38', '2019-03-19 19:08:41');
INSERT INTO `pc_auth_system_menu` VALUES ('86069630-f835-479f-b756-b050300a99d5', '用户列表查看', 'UserListShow', '#', '763baad8-3524-4ba3-806a-b6c2bcf8eac3', 'F', '', 0, 'Y', '2019-03-19 19:08:38', '2019-03-19 19:08:41');
INSERT INTO `pc_auth_system_menu` VALUES ('867191ba-0d4b-41ce-a94b-6be352fa0eb3', '撮合配置更新', 'BotMatchConfigUpdate', '#', '77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', 'F', NULL, 2, 'Y', '2019-06-29 09:37:11', '2019-06-29 09:37:12');
INSERT INTO `pc_auth_system_menu` VALUES ('871eb662-5681-457c-a5c8-c00b2b79f8da', '市商管理', 'MarketUser', '#', NULL, 'M', NULL, 14, 'Y', '2019-07-16 11:05:14', '2019-07-16 11:05:16');
INSERT INTO `pc_auth_system_menu` VALUES ('894f55b1-8c75-4e87-ad24-7c4247d72e30', '法币资金变动列表', 'OtcBillList', '#', 'a63199c1-7905-4707-9e5a-a0ff93ba27d3', 'C', NULL, 2, 'Y', '2019-05-13 17:48:42', '2019-05-13 17:48:44');
INSERT INTO `pc_auth_system_menu` VALUES ('8a143d23-d9cd-442a-a6bb-210c75c6681d', '黑名单查看', 'BlackListShow', '#', 'f8549b42-acf1-463b-a264-96abc1b6841b', 'F', '', 0, 'Y', '2019-03-19 19:12:04', '2019-03-19 19:12:07');
INSERT INTO `pc_auth_system_menu` VALUES ('8a702f96-6379-4034-8f8e-297fe3d670bd', '市商配置操作', 'OtcMarketConfigHandle', '#', '8185a79a-6885-418e-98e5-15e699f670f4', 'F', NULL, 1, 'Y', '2019-07-22 16:29:23', '2019-07-22 16:29:25');
INSERT INTO `pc_auth_system_menu` VALUES ('8bdd6fbf-e97e-48db-bd58-a60aca7917d0', '法币配置操作日志查看', 'OtcConfigHandleLogShow', '#', '58dcfdfa-0a14-4664-b96a-be92cd1758c3', 'F', '', 0, 'Y', '2019-05-10 17:46:46', '2019-05-10 17:46:48');
INSERT INTO `pc_auth_system_menu` VALUES ('8c330bdf-1cc9-4337-a445-a970e7f3a158', '法币币种修改状态', 'OtcCoinUpdateStatus', '#', 'be4b4bb9-2aeb-4103-812b-546cc26b888d', 'F', NULL, 1, 'Y', '2019-07-10 11:45:14', '2019-07-10 11:45:16');
INSERT INTO `pc_auth_system_menu` VALUES ('8c6d7e56-5866-4790-9f9a-febe6745dab8', '新增管理员', 'AddManager', '#', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'F', '', 1, 'Y', '2019-03-18 14:25:30', '2019-03-18 14:25:32');
INSERT INTO `pc_auth_system_menu` VALUES ('8c747d18-d0fe-4b7c-baa9-1a7c030e373e', '禁用角色', 'UpdRoleStatus', '#', '58a3ac6b-2b32-4291-919a-8d7b9d20466a', 'F', '', 4, 'Y', '2019-03-18 14:27:29', '2019-03-18 14:27:31');
INSERT INTO `pc_auth_system_menu` VALUES ('8eaea6fa-7e7d-4fdb-896e-4c34ce807bea', '关于我们', 'AboutUs', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 8, 'N', '2019-04-02 10:22:51', '2019-04-02 10:22:54');
INSERT INTO `pc_auth_system_menu` VALUES ('8ff39325-6500-40ac-8add-6286fe8e6f57', '日志管理', 'Log', '#', '', 'M', '', 9, 'Y', '2019-03-23 11:41:17', '2019-03-23 11:41:19');
INSERT INTO `pc_auth_system_menu` VALUES ('90244d17-5fea-416c-8847-8380f17a4bc3', '币币历史委托列表', 'BCoinHistoryList', '#', '297ee11f-0ef7-4c49-8751-94c024809ef0', 'C', '', 2, 'Y', '2019-03-13 19:54:01', '2019-03-13 19:54:04');
INSERT INTO `pc_auth_system_menu` VALUES ('9055760a-90a9-41f9-8907-eca3dcb4ease', '私募资金列表查看', 'PrivateBalanceShow', '#', '2f7cbc67-3624-4b87-88da-f0b385ab9a11', 'F', '', 0, 'Y', '2019-03-19 19:25:57', '2019-03-19 19:25:59');
INSERT INTO `pc_auth_system_menu` VALUES ('9055760a-90a9-41f9-8907-eca3dcb4ee9e', '加减币操作查看', 'OperationShow', '#', '2f7cbc67-3624-4b87-88da-f0b385ab9a25', 'F', '', 0, 'Y', '2019-03-19 19:25:57', '2019-03-19 19:25:59');
INSERT INTO `pc_auth_system_menu` VALUES ('920e05dc-4775-4d11-8962-d19353f9a328', '待复审提币列表', 'WaitAgainExam', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 2, 'Y', '2019-03-13 20:14:22', '2019-03-13 20:14:26');
INSERT INTO `pc_auth_system_menu` VALUES ('952a5af0-f73a-49d9-b5f7-987ac4870a0e', '撤单', 'QuantizedOrderCancel', '#', 'bb3ba301-7df8-4d73-9a01-b94b7d0ae3a3', 'F', NULL, 1, 'Y', '2019-04-20 09:43:22', '2019-04-20 09:43:24');
INSERT INTO `pc_auth_system_menu` VALUES ('95b3c499-9ec6-4516-b6b6-377e7e723b64', '法币币种操作日志查看', 'OtcCoinHandleLogShow', '#', 'e24bfa20-377c-46d8-9df8-8718da8e77cf', 'F', '', 0, 'Y', '2019-05-10 17:46:17', '2019-05-10 17:46:19');
INSERT INTO `pc_auth_system_menu` VALUES ('97988360-2238-4bdd-8557-dfcaaacc8d52', 'ETH归集查看', 'ETHAssetShow', '#', 'd5af4c9c-615b-4e7e-8b6b-9e55dbe2e635', 'F', '', 0, 'Y', '2019-04-11 17:53:33', '2019-04-11 17:53:34');
INSERT INTO `pc_auth_system_menu` VALUES ('98bdbda0-e586-40a1-98c2-9684a0ac6303', '充值明细查看', 'RechargeShow', '#', 'ae5d7bde-074e-4f24-b51f-2ab77a53ef0b', 'F', '', 0, 'Y', '2019-03-19 19:22:06', '2019-03-19 19:22:09');
INSERT INTO `pc_auth_system_menu` VALUES ('98c898a2-34b7-423c-b402-77d9e4f72445', '市商用户操作日志查看', 'OtcMarketUserHandleLogShow', '#', '5a549c85-03a8-4803-a70b-5e1b485a9245', 'F', NULL, 0, 'Y', '2019-07-16 11:12:34', '2019-07-16 11:12:36');
INSERT INTO `pc_auth_system_menu` VALUES ('99d69d0e-ed92-48be-8b8a-630c1dd6f8c7', '提现手续费', 'Withdraw', '#', '4454e6d1-35f3-4102-85f6-a9264172171b', 'C', '', 2, 'Y', '2019-03-13 19:59:04', '2019-03-13 19:59:08');
INSERT INTO `pc_auth_system_menu` VALUES ('9b36d6f5-b258-4fe9-8833-1ddd31ffc16e', '法币用户交易统计查看', 'OtcDealStatsListShow', '#', 'bfbe5a49-8b23-4818-8810-0ab537838d79', 'F', '', 0, 'Y', '2019-05-14 11:13:35', '2019-05-14 11:13:37');
INSERT INTO `pc_auth_system_menu` VALUES ('9c689c1d-eeb1-4686-b2e3-18056dac7a89', '隐私协议查看', 'PrivacyAgreementShow', '#', 'cccc34a7-28be-4bc3-b682-898a6a2d331a', 'F', NULL, 0, 'Y', '2019-05-29 10:42:46', '2019-05-29 10:42:48');
INSERT INTO `pc_auth_system_menu` VALUES ('9c689c1d-eeb1-4686-b2e3-18056dac7a90', '商家协议', 'BusinessAgreement', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', NULL, 14, 'Y', '2019-07-17 16:29:30', '2019-07-17 16:29:32');
INSERT INTO `pc_auth_system_menu` VALUES ('9c689c1d-eeb1-4686-b2e3-18056dac7a91', '商家协议查看', 'BusinessAgreementShow', '#', '9c689c1d-eeb1-4686-b2e3-18056dac7a90', 'F', NULL, 0, 'Y', '2019-07-17 16:30:18', '2019-07-17 16:30:22');
INSERT INTO `pc_auth_system_menu` VALUES ('9e25d05c-4ee9-42fe-b49b-7f280ee62aef', '充币账户查看', 'CoinAccountShow', '#', '6563a4c6-d8ac-4327-8a87-1c00140ca5f2', 'F', '', 0, 'Y', '2019-03-27 11:07:23', '2019-03-27 11:07:25');
INSERT INTO `pc_auth_system_menu` VALUES ('9e3c205a-2dac-4c29-95f2-fdde89418fa4', '撮合配置新增', 'BotMatchConfigInsert', '#', '77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', 'F', NULL, 3, 'Y', '2019-06-29 09:37:49', '2019-06-29 09:37:51');
INSERT INTO `pc_auth_system_menu` VALUES ('9eea853e-eb33-4b48-8b33-d501f93ad460', '提币异常列表查看', 'ErrorOutShow', '#', 'f4d4d9b7-debc-43a9-b155-c7f1df9dafa2', 'F', '', 0, 'Y', '2019-03-19 19:20:52', '2019-03-19 19:20:55');
INSERT INTO `pc_auth_system_menu` VALUES ('9fc89622-1323-4bab-bb64-a627fd7670f2', 'API管理', 'SystemApi', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 2, 'Y', '2019-03-13 20:25:23', '2019-03-13 20:25:26');
INSERT INTO `pc_auth_system_menu` VALUES ('a1bd2274-1570-49ea-b91b-5a7c35d3643d', '轮播图管理', 'SystemImage', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 3, 'Y', '2019-03-25 15:15:25', '2019-03-25 15:15:27');
INSERT INTO `pc_auth_system_menu` VALUES ('a203da0b-a054-4900-83e1-48c328d01fe3', '系统管理', 'System', '#', '', 'M', '', 8, 'Y', '2019-03-13 20:23:50', '2019-03-13 20:23:53');
INSERT INTO `pc_auth_system_menu` VALUES ('a21e4b46-02a9-4986-9370-e65e1aca04f0', '量化交易', 'Quantized', '#', NULL, 'M', NULL, 11, 'Y', '2019-04-19 14:02:31', '2019-04-19 14:02:33');
INSERT INTO `pc_auth_system_menu` VALUES ('a3ec7fa7-5247-4d85-a81d-91addf49b92c', '公告管理修改状态', 'SystemUpdStatus', '#', '0acf54f5-c747-44fb-909e-9da5cfb22ed0', 'F', '', 4, 'Y', '2019-03-25 15:23:44', '2019-03-25 15:23:46');
INSERT INTO `pc_auth_system_menu` VALUES ('a4bc1984-aecd-447f-9bbb-c79f1aabf630', '用户资产', 'UserAsset', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 2, 'Y', '2019-03-05 17:41:10', '2019-03-05 17:41:10');
INSERT INTO `pc_auth_system_menu` VALUES ('a4ee83b9-e8e7-425d-8a28-d1a3b163d628', '删除管理员', 'DelManager', '#', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'F', '', 3, 'Y', '2019-03-18 14:29:22', '2019-03-18 14:29:23');
INSERT INTO `pc_auth_system_menu` VALUES ('a5a48677-bdc5-46ec-8108-ce6f8ea0d9e5', '市商申请操作日志', 'OtcMarketApplyHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 6, 'Y', '2019-07-16 11:09:31', '2019-07-16 11:09:33');
INSERT INTO `pc_auth_system_menu` VALUES ('a63199c1-7905-4707-9e5a-a0ff93ba27d3', '法币管理', 'OtcManage', '#', NULL, 'M', NULL, 7, 'Y', '2019-05-13 15:58:26', '2019-05-13 15:58:27');
INSERT INTO `pc_auth_system_menu` VALUES ('a685735c-a5f8-405c-a1b1-ccc952ed8138', '轮播图删除', 'SystemImageDel', '#', 'a1bd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 1, 'Y', '2019-03-25 15:17:31', '2019-03-25 15:17:32');
INSERT INTO `pc_auth_system_menu` VALUES ('a6f9b128-4fae-48fc-93d1-dd2484a4cc52', '轮播图修改状态', 'SystemImageUpdStatus', '#', 'a1bd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 4, 'Y', '2019-03-25 15:19:28', '2019-03-25 15:19:30');
INSERT INTO `pc_auth_system_menu` VALUES ('ab90dcdb-48a7-4444-9ddc-70cd02080d3e', '实名审核', 'Certification', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 3, 'Y', '2019-03-06 14:35:22', '2019-03-06 14:35:22');
INSERT INTO `pc_auth_system_menu` VALUES ('ac48d946-49c4-4c2b-82c3-c134ada1d1f2', '用户列表详情与审核', 'UserDetail', '#', '763baad8-3524-4ba3-806a-b6c2bcf8eac3', 'F', '', 1, 'Y', '2019-03-14 13:38:28', '2019-03-14 13:38:31');
INSERT INTO `pc_auth_system_menu` VALUES ('ac91496f-5c75-4709-a096-d42d8ea1b51a', '订单变更记录', 'OrderInfo', '#', 'a21e4b46-02a9-4986-9370-e65e1aca04f0', 'C', NULL, 5, 'Y', '2019-05-21 10:11:05', '2019-05-21 10:11:07');
INSERT INTO `pc_auth_system_menu` VALUES ('ae5d7bde-074e-4f24-b51f-2ab77a53ef0b', '链上充值明细', 'Recharge', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 6, 'Y', '2019-03-13 20:18:12', '2019-03-13 20:18:15');
INSERT INTO `pc_auth_system_menu` VALUES ('ae63c838-e97d-11e9-b482-2c768aaecf52', '理财管理', 'Finance', '#', '', 'M', '', 16, 'Y', '2019-06-05 16:24:09', '2019-06-05 16:24:13');
INSERT INTO `pc_auth_system_menu` VALUES ('afab6cce-c5d2-4fa7-b7ec-1cbba1e1edd6', '币种管理', 'CurrencyManagement', '#', 'b4652551-0bec-43db-8274-3d9568f3ced0', 'C', '', 5, 'Y', '2019-10-08 15:27:11', '2019-10-08 15:27:16');
INSERT INTO `pc_auth_system_menu` VALUES ('agb75fr1-e98e-11e9-baa2-2c118ddecf32', '理财方案修改', 'FinancePlanUpdate', '#', 'e9fr5541-e98e-11e9-b482-2c7d8aaecf52', 'F', '', 1, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('b28eb9a0-0f9b-4a14-9cad-3dbb963df858', '公告管理新增', 'SystemNoticeAdd', '#', '0acf54f5-c747-44fb-909e-9da5cfb22ed0', 'F', '', 2, 'Y', '2019-03-25 15:22:19', '2019-03-25 15:22:21');
INSERT INTO `pc_auth_system_menu` VALUES ('b435c24c-3459-4a60-9f28-cd849c20f1b0', '市商用户列表', 'OtcMarketUserList', '#', '871eb662-5681-457c-a5c8-c00b2b79f8da', 'C', NULL, 3, 'Y', '2019-07-16 11:15:17', '2019-07-16 11:15:19');
INSERT INTO `pc_auth_system_menu` VALUES ('b4652551-0bec-43db-8274-3d9568f3ced0', '币种管理', 'Currency', '#', '', 'M', '', 6, 'Y', '2019-03-13 20:22:34', '2019-03-13 20:22:38');
INSERT INTO `pc_auth_system_menu` VALUES ('b87aa7a3-0b39-4d32-adf3-4b286970bb08', '订单列表查看', 'QuantizedOrderShow', '#', 'bb3ba301-7df8-4d73-9a01-b94b7d0ae3a3', 'F', NULL, 0, 'Y', '2019-04-19 14:22:59', '2019-04-19 14:23:01');
INSERT INTO `pc_auth_system_menu` VALUES ('b8de41da-dfc9-4f47-8805-9cab54bfcf14', '公告管理查看', 'SystemNoticeShow', '#', '0acf54f5-c747-44fb-909e-9da5cfb22ed0', 'F', '', 0, 'Y', '2019-03-25 15:21:07', '2019-03-25 15:21:08');
INSERT INTO `pc_auth_system_menu` VALUES ('b8e807fe-5bfa-45ef-8e0d-5a03ee38b201', '提现明细查看', 'CapitalWithdrawShow', '#', 'f3026d95-951f-4953-a304-a96e17cf45c3', 'F', '', 0, 'Y', '2019-03-19 19:21:29', '2019-03-19 19:21:31');
INSERT INTO `pc_auth_system_menu` VALUES ('b9115541-e98e-11e9-b482-2c768aaecf52', '奖品设置', 'PoolPrizeList', '#', '69039e06-e97d-11e9-b482-2c768aaecf52', 'C', '', 4, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('b9727d32-7a80-4910-bae3-251565d8b301', '资讯管理', 'ConfNews', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 6, 'N', '2019-04-02 10:21:00', '2019-04-02 10:21:02');
INSERT INTO `pc_auth_system_menu` VALUES ('b9b71541-e98e-11e9-b482-2c118aaecf23', '佣金制度查看', 'PoolCommissionShow', '#', 'ceec9093-e99f-11e9-b482-2c768aa2252', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('b9b75541-e98e-11e9-b482-2c118aaecf23', '参数配置查看', 'PoolParamConfigShow', '#', 'ceec9093-e99f-11e9-b482-2c768aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('b9b75541-e98e-11e9-b482-2c118aaecf52', '矿池列表查看', 'PoolListShow', '#', 'b9b75541-e98e-11e9-b482-2c768aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('b9b75541-e98e-11e9-b482-2c118ddecf52', '奖品设置查看', 'PoolPrizeListShow', '#', 'b9115541-e98e-11e9-b482-2c768aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('b9b75541-e98e-11e9-b482-2c768aaecf52', '矿池列表', 'PoolList', '#', '69039e06-e97d-11e9-b482-2c768aaecf52', 'C', '', 1, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('bab254dc-e449-4d6d-b4c4-557baef6c029', '修改币种', 'UpdCurrency', '#', '56151568-7a20-4597-af80-b8ed8059e2f8', 'F', NULL, 1, 'N', '2019-10-12 14:38:01', '2019-10-12 14:38:03');
INSERT INTO `pc_auth_system_menu` VALUES ('bb3ba301-7df8-4d73-9a01-b94b7d0ae3a3', '订单列表', 'QuantizedOrder', '#', 'a21e4b46-02a9-4986-9370-e65e1aca04f0', 'C', NULL, 3, 'Y', '2019-04-19 14:22:22', '2019-04-19 14:22:25');
INSERT INTO `pc_auth_system_menu` VALUES ('bb52fb89-cf80-440a-9a23-f2d018891d9b', '发币账户新增', 'CooutAccountAdd', '#', 'fd49668d-4ba2-472a-b1c4-6039d3336822', 'F', '', 1, 'Y', '2019-03-27 11:05:47', '2019-03-27 11:05:50');
INSERT INTO `pc_auth_system_menu` VALUES ('be4b4bb9-2aeb-4103-812b-546cc26b888d', '法币币种', 'OtcCoin', '#', 'b4652551-0bec-43db-8274-3d9568f3ced0', 'C', '', 4, 'Y', '2019-03-28 14:32:54', '2019-03-28 14:32:56');
INSERT INTO `pc_auth_system_menu` VALUES ('bf7bc537-c7aa-476b-b253-63f87e8b2a0d', '资金平衡表', 'Balance', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 11, 'Y', '2019-03-13 20:20:46', '2019-03-13 20:20:49');
INSERT INTO `pc_auth_system_menu` VALUES ('bfbe5a49-8b23-4818-8810-0ab537838d79', '法币用户交易统计', 'OtcDealStatsList', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', NULL, 8, 'Y', '2019-05-14 11:13:35', '2019-05-14 11:13:37');
INSERT INTO `pc_auth_system_menu` VALUES ('bfd0f2d1-85e4-4bd2-b89f-c689fee0ef2b', '待出币提币列表查看', 'WaitOutShow', '#', '461cd87d-b114-4716-97c1-476ad3182673', 'F', '', 0, 'Y', '2019-03-19 19:19:12', '2019-03-19 19:19:14');
INSERT INTO `pc_auth_system_menu` VALUES ('bfdfffa0-88e2-4feb-8af1-37e758c19847', '待初审提币列表', 'WaitFirstExam', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 1, 'Y', '2019-03-13 20:13:36', '2019-03-13 20:13:39');
INSERT INTO `pc_auth_system_menu` VALUES ('bfdfffa0-88e2-4feb-ere1-37e758c19847', '内部充值明细', 'RechargeInternal', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', NULL, 14, 'Y', '2020-05-13 16:08:35', '2020-05-13 16:08:37');
INSERT INTO `pc_auth_system_menu` VALUES ('bhi75541-e98e-11e9-baa2-2c118ddecf52', '理财列表查看', 'FinanceInvestShow', '#', 'yu115541-e98e-11e9-b482-2c7d8aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('c0681e5b-b140-4a34-8ab2-71cb6659b928', '撮合配置查看', 'BotMatchConfigShow', '#', '77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', 'F', NULL, 0, 'Y', '2019-06-27 14:21:34', '2019-06-27 14:21:36');
INSERT INTO `pc_auth_system_menu` VALUES ('c1b52f6d-f039-4d34-b957-fde84b3519ca', '币币委托列表', 'BCoinList', '#', '297ee11f-0ef7-4c49-8751-94c024809ef0', 'C', '', 1, 'Y', '2019-03-13 19:53:11', '2019-03-13 19:53:14');
INSERT INTO `pc_auth_system_menu` VALUES ('c2a75e88-8330-400c-b270-f1a643637373', '白名单', 'WhiteList', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 4, 'Y', '2019-03-05 17:41:39', '2019-03-05 17:41:39');
INSERT INTO `pc_auth_system_menu` VALUES ('c37a56e0-c4b7-47b8-9acf-02405557c846', '法币订单申诉列表', 'OtcAppeal', '#', '297ee11f-0ef7-4c49-8751-94c024809ef0', 'C', NULL, 5, 'Y', '2019-05-11 10:55:23', '2019-05-11 10:55:29');
INSERT INTO `pc_auth_system_menu` VALUES ('c3a7fb6d-a7e4-4067-aac8-8bd432f4e7e7', '法币订单申诉撤销订单', 'OtcAppealCancelOrder', '#', 'c37a56e0-c4b7-47b8-9acf-02405557c846', 'F', '', 1, 'Y', '2019-05-11 11:21:48', '2019-05-11 11:21:51');
INSERT INTO `pc_auth_system_menu` VALUES ('c550c689-8d43-4063-837e-06ecd2ed0a7a', '黑名单删除', 'DelBlackCode', '#', 'f8549b42-acf1-463b-a264-96abc1b6841b', 'F', '', 1, 'Y', '2019-03-18 17:41:27', '2019-03-18 17:41:29');
INSERT INTO `pc_auth_system_menu` VALUES ('c5ce25df-d3da-4a0b-aee4-03f021e49db0', '法币申诉操作日志查看', 'OtcAppealHandleLogShow', '#', '49c11490-0620-4533-8b6b-9cf8a866a8d2', 'F', '', 0, 'Y', '2019-05-10 17:45:32', '2019-05-10 17:45:34');
INSERT INTO `pc_auth_system_menu` VALUES ('c67f5450-767f-49f5-b68b-55e9daccbb9b', '法币币种查看', 'OtcCoinShow', '#', 'be4b4bb9-2aeb-4103-812b-546cc26b888d', 'F', '', 0, 'Y', '2019-03-28 14:32:54', '2019-03-28 14:32:56');
INSERT INTO `pc_auth_system_menu` VALUES ('c746fabb-ecc7-47f5-a3d8-40734724a509', '法币用户操作日志', 'OtcUserHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 5, 'Y', '2019-05-10 17:47:22', '2019-05-10 17:47:24');
INSERT INTO `pc_auth_system_menu` VALUES ('cba32528-5ce2-4c94-83d7-5cd955aabee7', '法币广告撤单', 'OtcAdCancel', '#', '35fbde21-ef67-4454-84dc-c9df1bf060b1', 'F', '', 1, 'Y', '2019-05-13 11:02:33', '2019-05-13 11:02:34');
INSERT INTO `pc_auth_system_menu` VALUES ('cccc34a7-28be-4bc3-b682-898a6a2d331a', '隐私协议', 'PrivacyAgreement', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', NULL, 10, 'Y', '2019-05-29 10:39:54', '2019-05-29 10:39:56');
INSERT INTO `pc_auth_system_menu` VALUES ('cccc34a7-28be-4bc3-b682-898a6a2d3344', '项目中心类型', 'ProjectClassify', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', NULL, 13, 'Y', '2019-07-13 15:43:52', '2019-07-13 15:43:54');
INSERT INTO `pc_auth_system_menu` VALUES ('cccc34a7-28be-4bc3-b682-898a6a2d3345', '项目中心查看', 'ProjectClassifyShow', '#', 'cccc34a7-28be-4bc3-b682-898a6a2d3344', 'F', NULL, 0, 'Y', '2019-07-13 15:45:21', '2019-07-13 15:45:24');
INSERT INTO `pc_auth_system_menu` VALUES ('cd8931a7-d6c2-4ece-850c-fbcc5aa43765', '关于我们查看', 'AboutUsShow', '#', '8eaea6fa-7e7d-4fdb-896e-4c34ce807bea', 'F', '', 0, 'Y', '2019-04-02 14:52:52', '2019-04-02 14:52:56');
INSERT INTO `pc_auth_system_menu` VALUES ('cd947014-b461-4ad7-8e52-ee772e7a76d8', '新增角色', 'AddRole', '#', '58a3ac6b-2b32-4291-919a-8d7b9d20466a', 'F', '', 3, 'Y', '2019-03-18 14:24:21', '2019-03-18 14:24:26');
INSERT INTO `pc_auth_system_menu` VALUES ('ceec9093-e99f-11e9-b482-2c768aa2252', '佣金制度', 'PoolCommission', '#', '69039e06-e97d-11e9-b482-2c768aaecf52', 'C', '', 3, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('ceec9093-e99f-11e9-b482-2c768aaecf52', '参数配置', 'PoolParamConfig', '#', '69039e06-e97d-11e9-b482-2c768aaecf52', 'C', '', 2, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('cf3a8e91-e97d-11e9-b482-2c768aaecf52', '奖励结算', 'IncomeClear', '#', '', 'M', '', 17, 'Y', '2019-06-05 16:24:09', '2019-06-05 16:24:13');
INSERT INTO `pc_auth_system_menu` VALUES ('d1c446a5-4178-4941-ab07-2a16d19257f1', '市商保证金列表', 'OtcMarketFreezeList', '#', '871eb662-5681-457c-a5c8-c00b2b79f8da', 'C', NULL, 2, 'Y', '2019-07-16 11:14:38', '2019-07-16 11:14:40');
INSERT INTO `pc_auth_system_menu` VALUES ('d1d04bb5-331f-4cde-88cd-f802179c588b', 'K线配置操作日志', 'BotCurrencyConfigHandleLog', '#', 'e96a98cf-226d-48f2-8a3f-8c94b7e30305', 'C', NULL, 2, 'Y', '2019-06-05 16:27:29', '2019-06-05 16:27:31');
INSERT INTO `pc_auth_system_menu` VALUES ('d241f395-7572-4ac0-8690-54c1ce1de7ad', '公告管理删除', 'SystemNoticeDel', '#', '0acf54f5-c747-44fb-909e-9da5cfb22ed0', 'F', '', 1, 'Y', '2019-03-25 15:21:43', '2019-03-25 15:21:45');
INSERT INTO `pc_auth_system_menu` VALUES ('d462818f-9676-4b8b-8029-6eb6db61ac65', '帮助中心', 'ConfHelp', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 7, 'Y', '2019-04-02 10:21:55', '2019-04-02 10:21:57');
INSERT INTO `pc_auth_system_menu` VALUES ('d4d7ae85-6517-4d88-858e-bd2e9718b745', '白名单查看', 'WhiteListShow', '#', 'c2a75e88-8330-400c-b270-f1a643637373', 'F', '', 0, 'Y', '2019-03-19 19:11:17', '2019-03-19 19:11:20');
INSERT INTO `pc_auth_system_menu` VALUES ('d4eedc4d-9afb-45d4-81ac-165a7e83c5c4', '管理员管理', 'Manager', '#', '1fd94c16-4147-489f-ae82-63560b7e5195', 'C', '', 2, 'Y', '2019-03-13 19:56:00', '2019-03-13 19:56:03');
INSERT INTO `pc_auth_system_menu` VALUES ('d59d2ace-76d8-4d35-8e8d-0b68e1a3268a', '费率标准查看', 'RatioShow', '#', '1a7c4644-b8cb-409a-b0f7-645e7dbc4375', 'F', '', 0, 'Y', '2019-03-19 19:16:14', '2019-03-19 19:16:17');
INSERT INTO `pc_auth_system_menu` VALUES ('d5af4c9c-615b-4e7e-8b6b-9e55dbe2e635', 'ETH资产归集', 'ETHAsset', '#', '08889ddc-45ff-4279-a56e-a12f0b48e394', 'C', '', 1, 'Y', '2019-04-11 17:52:18', '2019-04-11 17:52:20');
INSERT INTO `pc_auth_system_menu` VALUES ('d6fa48da-2e1b-45de-a7d9-05eab03f0f16', '轮播图管理查看', 'SystemImageShow', '#', 'a1bd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 0, 'Y', '2019-03-25 15:16:48', '2019-03-25 15:16:50');
INSERT INTO `pc_auth_system_menu` VALUES ('d8cbeb30-2db8-4b9a-831b-f14efefa5f50', 'K线配置操作日志查看', 'BotCurrencyConfigHandleLogShow', '#', 'd1d04bb5-331f-4cde-88cd-f802179c588b', 'F', NULL, 0, 'Y', '2019-06-05 16:30:01', '2019-06-05 16:30:02');
INSERT INTO `pc_auth_system_menu` VALUES ('ddc25a6a-2a4c-4d8d-bc5b-e37ce7237dc5', '管理员管理查看', 'ManagerShow', '#', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'F', '', 0, 'Y', '2019-03-19 19:15:36', '2019-03-19 19:15:39');
INSERT INTO `pc_auth_system_menu` VALUES ('def66325-7ef7-4872-8a14-328442fc2673', '服务协议', 'Agreement', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 9, 'Y', '2019-04-12 14:44:04', '2019-04-12 14:44:06');
INSERT INTO `pc_auth_system_menu` VALUES ('dfdf874c-08cd-43c2-b18d-3d262c28309a', '市商申请列表查看', 'OtcMarketApplyListShow', '#', '6713bc4c-a3b9-4fb8-9f18-2d1412608264', 'F', NULL, 0, 'Y', '2019-07-16 11:16:28', '2019-07-16 11:16:29');
INSERT INTO `pc_auth_system_menu` VALUES ('dg15541-e98e-11e9-b482-2c768aaecf52', '贡献奖励', 'IcContributionIncome', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 2, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('dwwa48da-2e1b-45de-a7d9-05eab03f0f16', '抽奖轮播图管理查看', 'LotteryImgShow', '#', 'qqbd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 0, 'Y', '2019-03-25 15:16:48', '2019-03-25 15:16:50');
INSERT INTO `pc_auth_system_menu` VALUES ('e04b728e-8024-4746-8f27-3792fa5369e1', '充币账户删除', 'CoinAccountDel', '#', '6563a4c6-d8ac-4327-8a87-1c00140ca5f2', 'F', '', 2, 'Y', '2019-03-27 11:08:36', '2019-03-27 11:08:38');
INSERT INTO `pc_auth_system_menu` VALUES ('e24bfa20-377c-46d8-9df8-8718da8e77cf', '法币币种操作日志', 'OtcCoinHandleLog', '#', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'C', NULL, 3, 'Y', '2019-05-10 17:46:17', '2019-05-10 17:46:19');
INSERT INTO `pc_auth_system_menu` VALUES ('e486118b-d05a-4dac-bd67-793c8a504f9d', '行情列表查看', 'CurrencyPairListShow', '#', '0d8c421c-41c9-458b-af2b-dab6e8e4b17b', 'F', '', 0, 'Y', '2019-03-22 11:10:44', '2019-03-22 11:10:46');
INSERT INTO `pc_auth_system_menu` VALUES ('e5e6e7d4-3e35-4804-9438-f6c9ec77cf0f', '待初审提币审核与详情', 'FirstExamDetail', '#', 'bfdfffa0-88e2-4feb-8af1-37e758c19847', 'F', '', 1, 'Y', '2019-03-14 13:43:10', '2019-03-14 13:43:14');
INSERT INTO `pc_auth_system_menu` VALUES ('e63d3c82-41c1-4444-9f08-009e5b284343', '项目中心', 'WhitePaper', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', NULL, 11, 'Y', '2019-06-10 18:18:35', '2019-06-10 18:18:37');
INSERT INTO `pc_auth_system_menu` VALUES ('e7605763-7988-473c-bf95-21c73e5fe12d', '法币系统配置', 'OtcConfig', '#', 'a63199c1-7905-4707-9e5a-a0ff93ba27d3', 'C', NULL, 1, 'Y', '2019-05-13 17:48:01', '2019-05-13 17:48:05');
INSERT INTO `pc_auth_system_menu` VALUES ('e788b004-db59-42fd-90e0-134dbe081a46', '设置管理员角色', 'SetManagerRole', '#', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'F', '', 4, 'Y', '2019-03-18 14:29:53', '2019-03-18 14:29:55');
INSERT INTO `pc_auth_system_menu` VALUES ('e9115541-e98e-11e9-b482-2c7d8aaecf52', '理财币种', 'FinanceToken', '#', 'ae63c838-e97d-11e9-b482-2c768aaecf52', 'C', '', 1, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('e940f413-4b7a-4178-8de1-0798fa540559', '链上钱包资产查看', 'ChainShow', '#', '2b32aefa-892c-4d8f-a101-a86204df18d9', 'F', '', 0, 'Y', '2019-03-19 19:24:08', '2019-03-19 19:24:09');
INSERT INTO `pc_auth_system_menu` VALUES ('e96a98cf-226d-48f2-8a3f-8c94b7e30305', 'K线机器人', 'Bot', '#', NULL, 'M', NULL, 12, 'Y', '2019-06-05 16:24:09', '2019-06-05 16:24:13');
INSERT INTO `pc_auth_system_menu` VALUES ('e9ce4dec-4f33-4ee9-b15f-6d89988629c4', 'K线配置操作', 'BotCurrencyConfigHandle', '#', '78527984-74c5-40eb-8f2f-a9e1de026524', 'F', NULL, 2, 'Y', '2019-06-05 17:29:18', '2019-06-05 17:29:20');
INSERT INTO `pc_auth_system_menu` VALUES ('e9fr5541-e98e-11e9-b482-2c7d8aaecf52', '理财方案', 'FinancePlan', '#', 'ae63c838-e97d-11e9-b482-2c768aaecf52', 'C', '', 2, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('ee1rt41-e98e-11e9-b482-2c768aaecfer', '抽奖奖励', 'IcLottery', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 6, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('ee2569df-6140-4c3f-b60a-811e0447135c', '币币历史委托列表查看', 'BCoinHistoryList', '#', '90244d17-5fea-416c-8847-8380f17a4bc3', 'F', '', 0, 'Y', '2019-03-19 19:14:01', '2019-03-19 19:14:04');
INSERT INTO `pc_auth_system_menu` VALUES ('ef66d5fc-33d2-4790-8c8a-11fbd3c81421', '币币账户资金明细查看', 'BBAccountShow', '#', '4472a81e-f150-40c0-ac40-c18f1f58f752', 'F', '', 0, 'Y', '2019-03-19 19:23:26', '2019-03-19 19:23:29');
INSERT INTO `pc_auth_system_menu` VALUES ('f088a48e-f659-41e8-8f6d-19ebd45eab75', '修改密码', 'UpdPassword', '#', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'F', '', 5, 'Y', '2019-03-18 14:30:40', '2019-03-18 14:30:42');
INSERT INTO `pc_auth_system_menu` VALUES ('f1a9585d-d95f-4374-a14e-1ae5614f3270', '撮合配置操作日志', 'BotMatchConfigHandleLog', '#', '0330a3fc-3dc9-4d6a-a76a-c3f5105c847d', 'C', NULL, 4, 'Y', '2019-06-27 14:20:10', '2019-06-27 14:20:12');
INSERT INTO `pc_auth_system_menu` VALUES ('f3026d95-951f-4953-a304-a96e17cf45c3', '提现明细', 'CapitalWithdraw', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 5, 'Y', '2019-03-13 20:17:44', '2019-03-13 20:17:46');
INSERT INTO `pc_auth_system_menu` VALUES ('f331eb5f-01b6-4187-b54e-3a8b5f6186f0', '量化账户', 'QuantizedAccount', '#', 'a21e4b46-02a9-4986-9370-e65e1aca04f0', 'C', NULL, 4, 'Y', '2019-04-26 14:04:14', '2019-04-26 14:04:16');
INSERT INTO `pc_auth_system_menu` VALUES ('f4d3434b7-debc-4879-b155-3434343feff', '伞下充值提现明细', 'UnderTheUmbrella', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 4, 'Y', '2019-03-13 20:17:12', '2019-03-13 20:17:16');
INSERT INTO `pc_auth_system_menu` VALUES ('f4d4d9b7-debc-43a9-b155-c7f1df9dafa2', '提币异常列表', 'ErrorOut', '#', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'C', '', 4, 'Y', '2019-03-13 20:17:12', '2019-03-13 20:17:16');
INSERT INTO `pc_auth_system_menu` VALUES ('f6195c3d-559c-4f36-85ea-96d047ca9e2e', '实名审核详情与审核', 'CertifyDetail', '#', 'ab90dcdb-48a7-4444-9ddc-70cd02080d3e', 'F', '', 1, 'Y', '2019-03-14 13:40:36', '2019-03-14 13:40:40');
INSERT INTO `pc_auth_system_menu` VALUES ('f7322f4a-37cc-47bd-abb3-a6b25793c180', '法币订单列表查看', 'OtcOrderListShow', '#', '52f3934d-526e-415d-ba5e-14bc1f2d81da', 'F', '', 0, 'Y', '2019-05-13 11:03:06', '2019-05-13 11:03:08');
INSERT INTO `pc_auth_system_menu` VALUES ('f8549b42-acf1-463b-a264-96abc1b6841b', '黑名单', 'BlackList', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 5, 'Y', '2019-03-06 16:13:11', '2019-03-06 16:13:11');
INSERT INTO `pc_auth_system_menu` VALUES ('f9189ea0-7b39-4aff-9a18-c55defce0553', '行情列表修改交易状态', 'UpdPairCCTStatus', '#', '0d8c421c-41c9-458b-af2b-dab6e8e4b17b', 'F', NULL, 2, 'Y', '2019-10-10 17:22:54', '2019-10-10 17:22:56');
INSERT INTO `pc_auth_system_menu` VALUES ('f9eadd0b-555c-498d-a65c-2fbcac147cae', '交易对管理', 'TradingOn', '#', 'a21e4b46-02a9-4986-9370-e65e1aca04f0', 'C', NULL, 1, 'Y', '2019-04-19 14:03:37', '2019-04-19 14:03:39');
INSERT INTO `pc_auth_system_menu` VALUES ('fd49668d-4ba2-472a-b1c4-6039d3336822', '充币账户设置', 'CoinAccount', '#', '33f0e92c-46ea-4f1c-bfb8-89065bfea71e', 'C', '', 2, 'Y', '2019-03-27 11:03:50', '2019-03-27 11:03:52');
INSERT INTO `pc_auth_system_menu` VALUES ('fdd800f0-aad9-41de-a0f3-b0cd854f9733', '法币系统配置修改', 'OtcConfigUpdate', '#', 'e7605763-7988-473c-bf95-21c73e5fe12d', 'F', '', 1, 'Y', '2019-05-13 17:48:01', '2019-05-13 17:48:05');
INSERT INTO `pc_auth_system_menu` VALUES ('fdf5bd30-1f8a-4cbb-96b5-aa3bda13553a', '币币交易对查看', 'CurrencyTradeShow', '#', '6c820e9b-9542-4be5-b2b5-e079d7999a23', 'F', '', 0, 'Y', '2019-03-19 19:27:26', '2019-03-19 19:27:29');
INSERT INTO `pc_auth_system_menu` VALUES ('fe90477a-ef33-4de4-ba49-57011c2a2c95', '公告管理编辑', 'SystemNoticeUpd', '#', '0acf54f5-c747-44fb-909e-9da5cfb22ed0', 'F', '', 3, 'Y', '2019-03-25 15:22:53', '2019-03-25 15:22:55');
INSERT INTO `pc_auth_system_menu` VALUES ('ffe1a687-f55b-488c-b844-7d560d17cbd7', '资金管理', 'Capital', '#', '', 'M', '', 5, 'Y', '2019-03-13 20:12:59', '2019-03-13 20:13:02');
INSERT INTO `pc_auth_system_menu` VALUES ('fy115541-e98e-11e9-b482-2c768aaecf52', '交易奖励', 'IcTxIncome', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 1, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('fy115541-e98e-11e9-b482-2c768aaecfer', '不锁仓权益奖励', 'IcUnlockIncome', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 3, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('fy1rt41-e98e-11e9-b482-2c768aaecfer', '矿池奖励', 'IcPoolIncome', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 5, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('iob755yy-e98e-11e9-b482-2c118ddecfyu', '返佣奖励查看', 'IcCommissionShow', '#', 'qq1rt41-e98e-11e9-b482-2c768aaecfer', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('lmb75541-e98e-11e9-b482-2c118ddecf52', '交易奖励查看', 'IcTxIncomeShow', '#', 'fy115541-e98e-11e9-b482-2c768aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('lmb75541-e98e-11e9-b482-2c118ddecfyu', '不锁仓权益奖励查看', 'IcUnlockIncomeShow', '#', 'fy115541-e98e-11e9-b482-2c768aaecfer', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('lmb755yy-e98e-11e9-b482-2c118ddecfyu', '矿池奖励查看', 'IcPoolIncomeShow', '#', 'fy1rt41-e98e-11e9-b482-2c768aaecfer', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('qq115541-e98e-11e9-b482-2c768aaecfer', '锁仓权益奖励', 'IcFinanceIncome', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 4, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('qq1rt41-e98e-11e9-b482-2c768aaecfer', '返佣奖励', 'IcCommission', '#', 'cf3a8e91-e97d-11e9-b482-2c768aaecf52', 'C', '', 7, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('qqbc1984-aecd-447f-9bbb-c79f1aabert', '用户参数列表', 'UserConfigParams', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 11, 'Y', '2019-03-05 17:41:10', '2019-03-05 17:41:10');
INSERT INTO `pc_auth_system_menu` VALUES ('qqbc1984-aecd-447f-9bbb-c79f1aabf630', '用户反馈', 'UserSuggestions', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 9, 'Y', '2019-03-05 17:41:10', '2019-03-05 17:41:10');
INSERT INTO `pc_auth_system_menu` VALUES ('qqbc1984-aecd-447f-9bbb-c79f1aabf6we', '用户反馈回复', 'UserSuggestionsReply', '#', '7232ed54-d5cb-42c8-be96-390b35405354', 'C', '', 10, 'Y', '2019-03-05 17:41:10', '2019-03-05 17:41:10');
INSERT INTO `pc_auth_system_menu` VALUES ('qqbd2274-1570-49ea-b91b-5a7c35d3643d', '抽奖轮播图管理', 'LotteryImg', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 100, 'Y', '2019-03-25 15:15:25', '2019-03-25 15:15:27');
INSERT INTO `pc_auth_system_menu` VALUES ('ra9a55b8-3d78-49f2-9606-231ef04f262f', '用户反馈查看', 'UserSuggestionsShow', '#', 'qqbc1984-aecd-447f-9bbb-c79f1aabf630', 'F', '', 0, 'Y', '2019-03-19 19:09:48', '2019-03-19 19:09:51');
INSERT INTO `pc_auth_system_menu` VALUES ('ra9a55b8-3d78-49f2-9606-231ef04f26rr', '用户反馈回复查看', 'UserSuggestionsReplyShow', '#', 'qqbc1984-aecd-447f-9bbb-c79f1aabf6we', 'F', '', 0, 'Y', '2019-03-19 19:09:48', '2019-03-19 19:09:51');
INSERT INTO `pc_auth_system_menu` VALUES ('ra9a55b8-3d78-49f2-9606-231ef04fqwer', '用户参数列表查看', 'UserConfigParamsShow', '#', 'qqbc1984-aecd-447f-9bbb-c79f1aabert', 'F', '', 0, 'Y', '2019-03-19 19:09:48', '2019-03-19 19:09:51');
INSERT INTO `pc_auth_system_menu` VALUES ('rrbd2274-1570-49ea-b91b-5a7c35d3643d', '矿池顶部图片', 'SysPoolImg', '#', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'C', '', 101, 'Y', '2019-03-25 15:15:25', '2019-03-25 15:15:27');
INSERT INTO `pc_auth_system_menu` VALUES ('tgb75541-e98e-11e9-baa2-2c118ddecf52', '理财币种查看', 'FinanceTokenShow', '#', 'e9115541-e98e-11e9-b482-2c7d8aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('tgb75fr1-e98e-11e9-baa2-2c118ddecf52', '理财方案查看', 'FinancePlanShow', '#', 'e9fr5541-e98e-11e9-b482-2c7d8aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('tgd478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'LTC资产归集', 'LTCAsset', '#', '08889ddc-45ff-4279-a56e-a12f0b48e394', 'C', '', 3, 'Y', '2019-04-11 17:52:51', '2019-04-11 17:52:52');
INSERT INTO `pc_auth_system_menu` VALUES ('tns75541-e98e-11e9-b482-2c118ddecf52', '贡献奖励查看', 'IcContributionIncomeShow', '#', 'dg15541-e98e-11e9-b482-2c768aaecf52', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('trf75541-e98e-11e9-b482-2c118ddecfyu', '锁仓权益奖励查看', 'IcFinanceIncomeShow', '#', 'qq115541-e98e-11e9-b482-2c768aaecfer', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('u006a91a-8092-4ddd-a2af-7718f49f09bf', 'LTC归集查看', 'LTCAssetShow', '#', 'tgd478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'F', '', 0, 'Y', '2019-04-11 17:54:12', '2019-04-11 17:54:14');
INSERT INTO `pc_auth_system_menu` VALUES ('uub755yy-e98e-11e9-b482-2c118ddecfyu', '抽奖奖励查看', 'IcLotteryShow', '#', 'ee1rt41-e98e-11e9-b482-2c768aaecfer', 'F', '', 0, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('uud478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'TRON资产归集', 'TRONAsset', '#', '08889ddc-45ff-4279-a56e-a12f0b48e394', 'C', '', 4, 'Y', '2019-04-11 17:52:51', '2019-04-11 17:52:52');
INSERT INTO `pc_auth_system_menu` VALUES ('yu115541-e98e-11e9-b482-2c7d8aaecf52', '理财列表', 'FinanceInvest', '#', 'ae63c838-e97d-11e9-b482-2c768aaecf52', 'C', '', 3, 'Y', '2019-03-13 20:21:37', '2019-03-13 20:21:41');
INSERT INTO `pc_auth_system_menu` VALUES ('yuwa48da-2e1b-45de-a7d9-05eab03f0f16', '矿池顶部图片查看', 'SysPoolImgShow', '#', 'rrbd2274-1570-49ea-b91b-5a7c35d3643d', 'F', '', 0, 'Y', '2019-03-25 15:16:48', '2019-03-25 15:16:50');

-- ----------------------------
-- Table structure for pc_auth_system_role
-- ----------------------------
DROP TABLE IF EXISTS `pc_auth_system_role`;
CREATE TABLE `pc_auth_system_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识，唯一',
  `ranking` tinyint(4) NULL DEFAULT NULL COMMENT '排行',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态:启用(Y)、停用(N)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pc_auth_system_role
-- ----------------------------
INSERT INTO `pc_auth_system_role` VALUES ('b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '开发者', '开发者', 1, 'Y', '2020-03-17 09:44:08', '2020-03-17 09:44:08');
INSERT INTO `pc_auth_system_role` VALUES ('e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'admin', '管理员', 0, 'Y', '2019-03-15 16:13:10', '2019-03-19 16:05:01');

-- ----------------------------
-- Table structure for pc_auth_system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `pc_auth_system_role_menu`;
CREATE TABLE `pc_auth_system_role_menu`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `create_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pc_auth_system_role_menu
-- ----------------------------
INSERT INTO `pc_auth_system_role_menu` VALUES ('02357b75-bb16-4655-a9eb-9a014568a9db', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'fdf5bd30-1f8a-4cbb-96b5-aa3bda13553a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('02f97f1e-7fd3-40b3-8ba1-eb30fb019e3c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '56151568-7a20-4597-af80-b8ed8059e2f8', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('06557df9-97a9-4a90-ba02-722655d44819', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '04886db5-3f72-42cd-bb3d-99f02d587b34', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0a2458d6-6114-43f7-9adf-3d6e1378fd8e', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '33f0e92c-46ea-4f1c-bfb8-89065bfea71e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0a44d74c-f3e0-4dea-b1b3-8bdfb2ac65f1', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '2bddfdc0-4ebc-486d-a2ca-cec247383ed4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0a6ae475-a3f5-4457-b093-06651fd685f0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '71d56f6b-7a95-4bde-9f3f-a9dd31ac38fb', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0b79a711-ec45-47a5-9df4-c5a96cc98100', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '920e05dc-4775-4d11-8962-d19353f9a328', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0bebf0be-a9c8-459d-a9fc-bce392d029ce', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd4d7ae85-6517-4d88-858e-bd2e9718b745', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0c826c89-dc13-475d-bd03-ce60ad56bbd5', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '871eb662-5681-457c-a5c8-c00b2b79f8da', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0d80308f-10e2-4ec1-841d-f0b786710a4a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'b8e807fe-5bfa-45ef-8e0d-5a03ee38b201', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0effe27e-131b-48bb-b937-e044055981bf', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5381f151-794e-44fb-b5d0-248683fe9273', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('0f08b2db-3240-488b-9110-c5db389321d0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c3a7fb6d-a7e4-4067-aac8-8bd432f4e7e7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('10479792-8223-4362-bc26-29cd305d2b66', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'cccc34a7-28be-4bc3-b682-898a6a2d331a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('10ea49fb-e1ec-4d64-9242-d9fb093ac2d7', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8202599a-a97d-4406-8e32-4a62ef947409', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('11c50983-35eb-4faf-a7c5-a672a014fd6c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a1bd2274-1570-49ea-b91b-5a7c35d3643d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('14cfdb10-3193-42ba-8c33-905e4cafd0e3', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '3149e502-c5d1-4ddc-81bc-81941d79a221', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('174ea414-c31c-4fb0-9ea0-d3f4f2dabd7b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e5e6e7d4-3e35-4804-9438-f6c9ec77cf0f', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1771e563-4eb1-4d69-b069-0f1b294b585c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'qqbc1984-aecd-447f-9bbb-c79f1aabert', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('17c568fd-8f81-437a-bfa7-8b183356206c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9fc89622-1323-4bab-bb64-a627fd7670f2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('184a6715-3395-4c4e-9738-80d11464a331', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f8549b42-acf1-463b-a264-96abc1b6841b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1afe63a2-b527-4e80-85a8-e7cdc9cdc8c3', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9e25d05c-4ee9-42fe-b49b-7f280ee62aef', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1c712a9b-2164-4f78-adff-8a17eb72bca1', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '90244d17-5fea-416c-8847-8380f17a4bc3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1cd6ca0f-8e67-4c4f-b572-8bfcaa71e24f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '19ad8999-cbcb-45e1-a8a5-e2a67061fa56', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1d51e250-9f55-4e49-af47-60f073365d72', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a203da0b-a054-4900-83e1-48c328d01fe3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1e4ad1c7-b13e-4c4b-9834-10947418d20f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'u006a91a-8092-4ddd-a2af-7718f49f09bf', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1eb21732-77fd-492f-b29c-f7feb99e48d5', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '733a0ab7-5f9a-4bec-b607-f42c7169add2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('1f4bbf22-06f2-4878-b55b-93c16a16e47a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a21e4b46-02a9-4986-9370-e65e1aca04f0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('20438c74-38d8-420b-9d90-390dee20abab', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'ae5d7bde-074e-4f24-b51f-2ab77a53ef0b', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('20438c74-38d8-dfdsfdsfdsfdf9d90-3434', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'f4d3434b7-debc-4879-b155-3434343feff', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('22a2b158-ae32-495f-ae13-88a855414687', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'bfd0f2d1-85e4-4bd2-b89f-c689fee0ef2b', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('233eae4c-c46a-4fa1-bc59-69c7a033019b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ee2569df-6140-4c3f-b60a-811e0447135c', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('23dc97c4-6cad-43c1-b397-d29cedd79416', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e788b004-db59-42fd-90e0-134dbe081a46', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('242eca13-7fd7-4d43-8fc0-600efb26e83f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '6d9454d8-3040-4864-b6e0-6a93fa3b220a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2456e15f-0d02-4a5c-a2be-a92586b2b6c4', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9055760a-90a9-41f9-8907-eca3dcb4ease', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('249e160f-623b-4b84-bf03-0081996e3c58', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e9ce4dec-4f33-4ee9-b15f-6d89988629c4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2acd3454-e514-4b10-b6f7-8ceb8284b698', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '76a3d54d-e509-4485-a97a-92952e078ae1', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2bab483f-75be-4036-bbf7-3760b96e9aca', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'tgd478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2bcc7508-2a43-4e1d-86dd-fc151f0c0c7d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ra9a55b8-3d78-49f2-9606-231ef04fqwer', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2e92304f-af62-48d3-b4ba-fafa080ae1f1', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'cccc34a7-28be-4bc3-b682-898a6a2d3344', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2f215d32-b236-44cf-bc27-99931e7acf4f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f331eb5f-01b6-4187-b54e-3a8b5f6186f0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2f8473d2-018c-4e42-b458-40a8efba645b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8c6d7e56-5866-4790-9f9a-febe6745dab8', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('2f9545e6-d253-4118-9163-7b55f6fb9fd5', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9c689c1d-eeb1-4686-b2e3-18056dac7a91', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('30059221-bef5-4b75-a43a-d187a0b10b73', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '28e78de1-ad89-4148-b81b-90ab8f4358a4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('302c08d5-1478-4502-bc21-689ed5d4c6b0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'qqbc1984-aecd-447f-9bbb-c79f1aabf6we', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('32102854-916c-407e-808d-c84397209b87', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'b8de41da-dfc9-4f47-8805-9cab54bfcf14', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('3280f686-5164-4855-87ab-8094a2828b36', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '867191ba-0d4b-41ce-a94b-6be352fa0eb3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('32e68e72-9eb9-4cc7-9765-18a1dc7c0db6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'be4b4bb9-2aeb-4103-812b-546cc26b888d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('35b5fcb5-4db8-4463-9041-50129fb0d1fe', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '97988360-2238-4bdd-8557-dfcaaacc8d52', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('379485a7-d724-48f5-8f11-06c26edd7b86', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'qqbc1984-aecd-447f-9bbb-c79f1aabf630', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('379e5fa6-ac48-4764-8e50-410fb7320e48', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'bf7bc537-c7aa-476b-b253-63f87e8b2a0d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('37d24d82-61ed-4041-89ac-cba76ac711f6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'bfbe5a49-8b23-4818-8810-0ab537838d79', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('392f9142-ac88-48a4-bf97-d78240a8f3dc', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '2f7cbc67-3624-4b87-88da-f0b385ab9a91', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('3b72b4c2-39f6-4854-98c3-184a22aa3369', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'fdd800f0-aad9-41de-a0f3-b0cd854f9733', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('3c65945a-7816-4336-84b0-8f44f1775302', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '2e9b908d-711c-48ec-806b-d102e6d895cc', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('3cc887f7-8a9b-4a0f-a94b-6e7aa9076211', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'fe90477a-ef33-4de4-ba49-57011c2a2c95', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('3d5f4393-d374-4524-bda0-a9a9a85fcede', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '721c46da-848d-48ca-9bcd-f7801bc011d6', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('3f4b5c1f-1df8-46a7-bc20-f153355e6e5b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '2f7cbc67-3624-4b87-88da-f0b385ab9a25', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('41f2809d-e0a2-4259-a650-1115745668d6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '297ee11f-0ef7-4c49-8751-94c024809ef0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4324513e-1d06-4252-aa56-abcf8a802bd6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0b0100ed-f8df-4268-a8e2-41ebd88e36d4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('436cafbd-aaf4-4dc8-aaba-e23d5eee4b3b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '6563a4c6-d8ac-4327-8a87-1c00140ca5f2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4393e3fc-0201-4e67-ac27-44b4aab380df', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '4b947437-7daf-4b24-a467-c15e335f43a7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4780d200-f53c-42f9-836d-033820c7188a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0a1edec9-1f39-4dfe-810f-7f8ca1298ddd', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('49aea538-423d-4400-a62b-0f285e2e8842', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0ae9a204-633f-4e80-a46c-60aa27ed2cbc', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('49fee6db-0a80-4cdc-a86e-fa671b76ef56', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c746fabb-ecc7-47f5-a3d8-40734724a509', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4ab4f0e2-7de5-4cc5-85c2-323013ff5207', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '29fe5fa2-b0bf-4111-9e23-f70aaa7d0ab7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4bcdecc2-b784-46ca-8be5-2a3f665b1dec', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'fd49668d-4ba2-472a-b1c4-6039d3336822', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4cc970f5-8716-4758-a1a2-da795b0b60bc', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '461cd87d-b114-4716-97c1-476ad3182673', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4ccb439b-b080-4071-b79d-56ec71e46623', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '11fe5fa2-b0bf-4111-9e23-f70aaa7d0ab7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4d8f96ce-ad86-41e9-8541-c78e9666be6f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '710bb432-41cb-468b-bef6-0a606550e8a5', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4dbabef4-d5a4-4737-9702-4bedff097d37', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '461cd87d-b114-4716-97c1-476ad3182673', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4e1687e0-aab4-43a0-a096-f11cb3bb18bf', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5c38a89b-58d8-4122-a079-623ca62eff95', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('4ecca086-f236-48c6-b88b-39af1436f235', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e63d3c82-41c1-4444-9f08-009e5b284343', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('502e42bb-2eb2-48cf-9621-9bee0616996b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9c689c1d-eeb1-4686-b2e3-18056dac7a89', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('503b762a-13a9-4b20-874e-7ca5cfa0146b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd6fa48da-2e1b-45de-a7d9-05eab03f0f16', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5068ea66-9d47-442b-81e7-d350583c466d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0330a3fc-3dc9-4d6a-a76a-c3f5105c847d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('51a7c4f6-c3f2-43bb-8efb-2e47ef7b274a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e04b728e-8024-4746-8f27-3792fa5369e1', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('52541eed-9e36-4ec2-821b-ea6e16d8c46c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'b4652551-0bec-43db-8274-3d9568f3ced0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('54309a1c-6219-45a4-93fe-ada1a5f051b5', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e24bfa20-377c-46d8-9df8-8718da8e77cf', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5482b1be-013d-4f16-b7ac-79adee94ddb6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'cba32528-5ce2-4c94-83d7-5cd955aabee7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('54bcff02-8bf9-45b1-92b3-7ef3ccafc25a', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '04886db5-3f72-42cd-bb3d-99f02d587b34', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('54eef91a-9e10-4da3-a345-78c7f69c9cba', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5655c461-8922-4045-8a5a-a1f7245250e2', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5a9a55b8-3d78-49f2-9606-231ef04f262f', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('575438f5-fea0-416e-ac39-c0320a821750', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '1a7c4644-b8cb-409a-b0f7-645e7dbc4375', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('57702aff-a35f-4edd-bf86-b2307d9a0c31', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '952a5af0-f73a-49d9-b5f7-987ac4870a0e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5910afef-476a-4ad2-b7a7-85c8b2ec3126', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c0681e5b-b140-4a34-8ab2-71cb6659b928', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5959b5c4-605d-4498-8251-090ce5538034', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'b28eb9a0-0f9b-4a14-9cad-3dbb963df858', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('59e31754-a93b-44df-8a5a-e59df128ed2c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '12422c84-a6d3-4445-a7fd-d73c3ebf1a42', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5be01ca5-0724-4df6-98ee-2790df941a1c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd8cbeb30-2db8-4b9a-831b-f14efefa5f50', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5ccba163-f250-483b-82a6-d11ed3bd223e', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'cd947014-b461-4ad7-8e52-ee772e7a76d8', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5df4bde1-e0a1-4229-b63e-3d5a61ff9fa7', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'bfdfffa0-88e2-4feb-8af1-37e758c19847', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('5e2c7755-774a-4a71-851f-7e640e8640cb', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '33218ca7-09e5-4092-827e-9a8eef7f7800', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('601d9343-d90d-4f6c-bca5-b4a6277fd2e1', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8a702f96-6379-4034-8f8e-297fe3d670bd', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('60429434-e565-4527-9dce-dab17529a6d1', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'ffe1a687-f55b-488c-b844-7d560d17cbd7', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('63cb077f-6b9f-4902-8b50-466e805390d9', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd5af4c9c-615b-4e7e-8b6b-9e55dbe2e635', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('64542e32-3417-46fb-b683-9bef62b04c91', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0fb7624a-1b0d-469f-b6fe-29d8c7824183', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('645ca26f-181e-4a54-b1db-a66d4f00a294', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'dwwa48da-2e1b-45de-a7d9-05eab03f0f16', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('64c92609-271f-46d7-8745-e2f00efc23e0', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '42bb3291-436a-4caf-a5fc-879ebde4ee64', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6510d940-dbe0-4fe7-a1aa-dd6f588fffae', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8bdd6fbf-e97e-48db-bd58-a60aca7917d0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('65eda1f7-4339-4658-884d-4cadd8d90786', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8a143d23-d9cd-442a-a6bb-210c75c6681d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('67acf54f-3520-46ab-b6b6-916a729e85de', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '35fbde21-ef67-4454-84dc-c9df1bf060b1', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('689cebc6-efa4-4593-8cb0-074bea582f10', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ddc25a6a-2a4c-4d8d-bc5b-e37ce7237dc5', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('694a451b-9c86-47ec-8962-5c84a5eaabb2', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '08889ddc-45ff-4279-a56e-a12f0b48e394', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('69b5fb70-7dae-496f-810e-1198d8d4d630', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f088a48e-f659-41e8-8f6d-19ebd45eab75', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6ce16eeb-d966-46b0-8a95-d1ea766ea832', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '1163a4c6-d8ac-4327-8a87-1c00140ca5f2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6d44b1a9-b048-4b25-8d56-b87175a3989e', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f7322f4a-37cc-47bd-abb3-a6b25793c180', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6d94c327-4475-4f12-8dc4-f114ac00dddd', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0acf54f5-c747-44fb-909e-9da5cfb22ed0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6de19043-e30d-4238-9169-76f17e5a5707', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e486118b-d05a-4dac-bd67-793c8a504f9d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6ef58b95-0657-466f-a1eb-cec63f162253', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5253836f-ddef-491f-b8ee-0a1f6bd1a384', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('6f6f7361-f57e-41e7-850f-db135d985115', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd462818f-9676-4b8b-8029-6eb6db61ac65', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('70ff3057-eb58-4718-8cfa-f0f1ad192256', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '175cd24b-93e3-46ad-a5f4-81e54032d7ea', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('7195c374-f3c5-4e66-a7ef-4e0349d8150a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '752f7c6d-f749-482b-a134-8f18cd455db3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('72c53a6e-c72d-4c73-bcca-242751ade29f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '6e6b2588-613b-4500-85cb-3ab1dcf14aa8', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('73219a0d-dc8a-471b-b70c-8977f94222e9', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '2f7cbc67-3624-4b87-88da-f0b385ab9a25', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('765f94f0-422e-4e75-9a32-016e1abb5869', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '98bdbda0-e586-40a1-98c2-9684a0ac6303', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('7710a8de-bc04-4aca-a6a7-fb4d9bf74533', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0d8c421c-41c9-458b-af2b-dab6e8e4b17b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('78ce914d-b466-44a4-9102-4596d2bf90cd', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '39c4e90b-18c8-4268-bba4-9d0879b942dd', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('7976cf10-ad59-463a-ab8a-78334ebf8c8d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '763baad8-3524-4ba3-806a-b6c2bcf8eac3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('7ae8bb2f-6b76-4c97-95fc-477c758e6f4a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '635a9d9a-a01a-4c49-b680-eda63a81c865', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('7ffe5658-b427-47e7-bc7f-fc15265d79d3', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e96a98cf-226d-48f2-8a3f-8c94b7e30305', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('80c1d50b-2877-4115-b13f-b43dba6bdb7d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '6713bc4c-a3b9-4fb8-9f18-2d1412608264', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8116e684-285b-4365-bc3f-126bd517ad13', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '1fd94c16-4147-489f-ae82-63560b7e5195', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('820a79c3-d381-4ca9-9088-7221a1dd91fc', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a4bc1984-aecd-447f-9bbb-c79f1aabf630', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('83cc4860-b371-4e3e-a473-33d291a0d0d7', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'uud478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('843499d2-3a3f-45e8-9c2f-193b772916fb', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0c064fc1-2fa3-4549-82a5-333fe2eacfd4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8626eda0-3921-43d6-b6bf-ee379bf00bd4', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9b36d6f5-b258-4fe9-8833-1ddd31ffc16e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8691ca1c-e0cd-4a9e-b545-9c8665f65228', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c2a75e88-8330-400c-b270-f1a643637373', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('86d92c2e-b950-452a-a705-22d1cdeb1d92', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '58a3ac6b-2b32-4291-919a-8d7b9d20466a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('87995400-cda7-47d2-a66a-c6d83fd59d25', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '763baad8-3524-4ba3-806a-b6c2bcf8e111', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('87db518e-e9b6-435d-969a-c10e4b560b87', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '3e875170-f522-417a-9168-7563b63b7e26', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8ac2e91c-7996-4f6f-928e-185001545e31', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '1185125b-c652-474d-a19c-2485bfcb567d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8bb0cbce-9874-4fec-b3c2-ea91bdaa25ce', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'e5e6e7d4-3e35-4804-9438-f6c9ec77cf0f', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8bb850cf-c590-4b58-9b2e-9b512427b983', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '9055760a-90a9-41f9-8907-eca3dcb4ee9e', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8d6c1440-b769-4f5a-983d-61f8ef341714', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '06a8b915-0f13-46e4-b512-e25629ea5b8a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8dcef2bb-f07c-426c-a34e-baff3a435677', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9e3c205a-2dac-4c29-95f2-fdde89418fa4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8e4201d5-9bc2-429a-ae5a-65563b14c127', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '7e5f4394-1b45-4342-808c-15d6e3b90581', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8e636ae5-8e05-46d1-807b-54c403987e30', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '345e10d3-cc48-4799-9d91-995c2911e6f8', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('8e67ff3b-67f0-48ce-9a6a-517f0e9cb57a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '4b2b6f90-d748-46d9-bc07-c80955b522a1', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('9036c4ca-806c-4137-97cc-8198fedf05ec', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '2a030ba1-6e3e-4302-b7e6-9a40bbc47c14', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('91c368d6-55e4-409c-93dc-4e356ec564ec', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '6919a25f-7973-4150-9e2e-cd8327d0ac85', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('92e2ab10-c66c-43f8-8f40-e9107d38d547', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'qqbd2274-1570-49ea-b91b-5a7c35d3643d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('939ef8c1-f93c-456a-9537-d0b92467b9e2', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'dfdf874c-08cd-43c2-b18d-3d262c28309a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('95287eb2-e8db-4e2e-9c65-12cd51f6136f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '4il6a91a-8092-4ddd-a2af-7718f49f09bf', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('969e3864-18e6-4a69-9198-c7f5f6167864', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8201accc-b2e4-4c58-9428-499573b9dc0d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('977bb7a3-4f74-4122-8e31-917bd214d5df', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'f4d4d9b7-debc-43a9-b155-c7f1df9dafa2', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('979581a6-2824-4a22-9ad9-7fa424d9f5a3', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a685735c-a5f8-405c-a1b1-ccc952ed8138', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('9887c8bd-b839-4f02-b043-77d6e3172ce1', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '7e4e0508-aa6f-46cf-b4b9-6c0d9e7c0225', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('992e645e-0c50-4cb5-ac6a-9bfc8c8696fe', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '204fedb7-e684-4c23-8fd4-098fca45481d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('99a86913-47c9-4396-9de6-bb040c49443d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a4ee83b9-e8e7-425d-8a28-d1a3b163d628', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('9b3e6b75-5608-48f1-a135-97dcb7fe2e40', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ra9a55b8-3d78-49f2-9606-231ef04f26rr', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('9d0f148a-be91-42bb-a9d3-4ae73c111dcc', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '49d478ca-e4ab-45ba-8d3a-4d1c4f2dd49e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('9d8b1dd4-8512-4d6b-b20a-46979da90524', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '6c820e9b-9542-4be5-b2b5-e079d7999a23', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('9e998a50-6b67-4cb2-89b5-fdd1d01748f9', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd1d04bb5-331f-4cde-88cd-f802179c588b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('a4ef6561-9569-4033-bade-52a119824206', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f9eadd0b-555c-498d-a65c-2fbcac147cae', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('a644aa67-fb03-4565-9be7-d4211077583d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '95b3c499-9ec6-4516-b6b6-377e7e723b64', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('a6e5586e-f26b-425c-96d9-b1ba14dcc553', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '77d13cb4-8b0d-4e10-bf7d-7cf79298fe44', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('a9bfb04b-0840-41cb-8cf2-35f8c48df9ab', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c550c689-8d43-4063-837e-06ecd2ed0a7a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('abb475d9-38cb-484f-98af-e9a211677419', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f3026d95-951f-4953-a304-a96e17cf45c3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('abb66adf-f232-47b1-8f01-69f6d3ea86c9', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a6f9b128-4fae-48fc-93d1-dd2484a4cc52', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('abfb57b7-6ccb-4d99-b665-f08a43b8e935', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '3d01db0f-528b-42ec-96bf-b6416c15cc6e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('ac66b2e7-4784-4dbf-9e6d-e8e8aa94f0b0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'afab6cce-c5d2-4fa7-b7ec-1cbba1e1edd6', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('adda8718-8fc1-41fb-8e0d-1952b16738e2', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9055760a-90a9-41f9-8907-eca3dcb4ee9e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('aebb9ce8-cdca-435a-8beb-11e708f210cf', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8ff39325-6500-40ac-8add-6286fe8e6f57', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('afb66193-bbdb-4996-a941-8c2d73853253', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '230bce00-2998-42c4-b8a9-53dd7f7c961b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b10bf99a-4ad6-42d1-81a9-eae0b6cce21b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'bb3ba301-7df8-4d73-9a01-b94b7d0ae3a3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b1c4b6a6-c8de-441b-8c32-4aabe54c86b3', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'bfd0f2d1-85e4-4bd2-b89f-c689fee0ef2b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b226733a-da67-40fd-9065-d6c05a3dfaca', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '46ddd508-24d2-4827-bb9e-6118a0adaa63', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b27ec46d-c014-4ed6-9570-e0cc6c3609d4', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd59d2ace-76d8-4d35-8e8d-0b68e1a3268a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b2efa806-d083-49cc-af44-8d081d4bbc6a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '52f3934d-526e-415d-ba5e-14bc1f2d81da', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b39289ff-b914-49ec-b96e-d42c50ef7b4f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'cccc34a7-28be-4bc3-b682-898a6a2d3345', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b49d4b0a-27c1-4b19-8a3b-9648ed329208', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5a549c85-03a8-4803-a70b-5e1b485a9245', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b549bd5a-dcad-498c-b3df-9f023a42520b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8c747d18-d0fe-4b7c-baa9-1a7c030e373e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b5ac41b7-268b-4879-9e33-097947d0a828', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '894f55b1-8c75-4e87-ad24-7c4247d72e30', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b619a3d0-e6cd-4b9e-825c-9023c93befd4', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '98bdbda0-e586-40a1-98c2-9684a0ac6303', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b649c0e8-2ff3-410c-848b-e15f01d4f45c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '58dcfdfa-0a14-4664-b96a-be92cd1758c3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b65a24b8-7d0f-409e-81a5-c09b1af64e5d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ab90dcdb-48a7-4444-9ddc-70cd02080d3e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b68dda59-3b8d-4cca-8839-c92b04b868a4', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'bf7bc537-c7aa-476b-b253-63f87e8b2a0d', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b876c343-ce37-455c-ba34-cda7acce890e', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'b87aa7a3-0b39-4d32-adf3-4b286970bb08', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('b9c1ac39-761c-457a-823c-a8ad27615be4', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '345e10d3-cc48-4799-9d91-995c2911e6f8', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('bbb889b7-5dfd-41cc-9b70-8127b7f7f35b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '49c11490-0620-4533-8b6b-9cf8a866a8d2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('bd05aa65-9e09-488e-be29-877b2413c14d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '98c898a2-34b7-423c-b402-77d9e4f72445', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('bd7cff37-e034-4224-87c3-3244645922b0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '3c3073ed-5caf-4d49-ac24-b25f8e631e70', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('bdf24507-6e1b-44ce-80ce-ba8777626ecf', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '076beed3-dac4-463b-ad79-cdccbca4ac15', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('be1fcae9-1050-4490-8b76-c40bfc4da79a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a5a48677-bdc5-46ec-8108-ce6f8ea0d9e5', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('be90b272-44c9-4f4c-8693-ed74e5fc3775', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '9055760a-90a9-41f9-8907-eca3dcb4ease', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('bed8eb11-0cd1-4836-829a-494a38ff046b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '66fc9b4d-06fe-41da-8e21-72ea073e9a5e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('bfba84f2-5e53-415d-94ce-ae2d174c8879', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'f3026d95-951f-4953-a304-a96e17cf45c3', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c0a6a219-ad86-4b93-87a5-5d7b43bba433', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ac48d946-49c4-4c2b-82c3-c134ada1d1f2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c27b9baa-1a1e-459b-a15c-aaf28e8fe5a1', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c37a56e0-c4b7-47b8-9acf-02405557c846', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c359ef67-e17c-4cff-a199-cf9860088b23', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd241f395-7572-4ac0-8690-54c1ce1de7ad', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c380ebe3-1b0a-4609-a3ee-904f0f1f8c18', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '010c1a9b-4119-466b-9391-6c4108ecaed2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c3c4f38d-17eb-4a8c-90ad-9087295ac88e', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '3778a69c-bf91-487f-9c06-bb3c8f74adf3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c400068a-3f4b-422c-93fb-1b846b428a11', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f1a9585d-d95f-4374-a14e-1ae5614f3270', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c51200b2-6cb1-45e6-9f6a-aea5851a387f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a3ec7fa7-5247-4d85-a81d-91addf49b92c', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c5271f7b-cd3d-41a0-82c1-80767136caba', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c5ce25df-d3da-4a0b-aee4-03f021e49db0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c59ed047-c59a-42e2-b1fe-d4dec519d48d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '4006a91a-8092-4ddd-a2af-7718f49f09bf', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c5f0806c-e2c8-4b15-83e5-79fc5e779d74', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'e7605763-7988-473c-bf95-21c73e5fe12d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c60e6d3e-bce0-4374-bf8d-cdebb2211a8c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'a63199c1-7905-4707-9e5a-a0ff93ba27d3', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c6447d73-a2f1-4759-97f2-7fc581edaa1d', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c1b52f6d-f039-4d34-b957-fde84b3519ca', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c7ae1b2e-6447-407a-b774-77db1040dccc', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ra9a55b8-3d78-49f2-9606-231ef04f262f', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('c867d9ad-ebbe-4bf2-92e3-a213dbd820d5', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '2f7cbc67-3624-4b87-88da-f0b385ab9a11', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('ca5642ff-af29-4d9f-8452-6a59e4416fe0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ac91496f-5c75-4709-a096-d42d8ea1b51a', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('ca64b1e2-f973-43ae-af3e-9d4768d500db', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5911bf0e-d729-4d50-a6cc-fa25de2a8827', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('caf6d87e-0dd3-48cc-a091-1a26c381346e', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '9eea853e-eb33-4b48-8b33-d501f93ad460', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('cdc624ef-9849-45bc-870d-e3b1cc9d28d0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9eea853e-eb33-4b48-8b33-d501f93ad460', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('cddc6b32-96e9-44fe-9b03-3036c8374eaf', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '5f15f1cb-9af6-4958-83f1-4875e0ded2df', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('cedd6e5f-591e-4f28-abc9-40d89f8504f0', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '1555fa14-6576-47f8-a9e4-885ae909aa24', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('cf8da297-e69d-421f-8c8f-5e3a64ea9768', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8185a79a-6885-418e-98e5-15e699f670f4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('cfa4f3d9-8ce7-4b90-ab4b-6d6757589c0b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '86069630-f835-479f-b756-b050300a99d5', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('cfe60e5a-7c39-4da3-b1b6-50cb2a894b38', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'bfdfffa0-88e2-4feb-8af1-37e758c19847', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d080298c-0133-4578-a2cb-9fcc2116550f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '78527984-74c5-40eb-8f2f-a9e1de026524', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d09f7f69-2c1d-4db9-b927-6198735e58d6', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '635a9d9a-a01a-4c49-b680-eda63a81c865', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d2bb8ef3-b744-4a56-91e4-60696d4e3dc3', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '07a630f3-b4f3-42f3-b843-0a3cc7d6f9f5', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d3ecf367-b444-4812-9037-9748f37fbcab', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '04d5995c-c3b4-4b31-b701-d0b0c7d66d68', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d4cec26e-567e-49a1-9b15-b9f34ff827ae', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '4b2b6f90-d748-46d9-bc07-c80955b522a1', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d812cd75-ffb9-4882-a7a4-f7a43de80285', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '7232ed54-d5cb-42c8-be96-390b35405354', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('d9f80666-dd99-47f3-aa08-3dcc9106b743', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'def66325-7ef7-4872-8a14-328442fc2673', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('dabe9739-0beb-4939-ac91-a3acd589124c', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'b435c24c-3459-4a60-9f28-cd849c20f1b0', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('e2b578a0-12d4-4bd1-aba5-b1cfb0c58faa', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f9189ea0-7b39-4aff-9a18-c55defce0553', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('e6c54db4-eb57-415c-b9c4-9c2897dcf2a9', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '86069630-f835-479f-b756-b050300a9111', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('e80d6328-0954-41a0-a8d1-292b04cf185b', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '99d69d0e-ed92-48be-8b8a-630c1dd6f8c7', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('e812c178-9b90-4fa6-8549-95a764da5d81', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd1c446a5-4178-4941-ab07-2a16d19257f1', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('e9bd3d59-79a1-4622-8efa-f074814f81d7', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '4454e6d1-35f3-4102-85f6-a9264172171b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('eb9fdb4f-483f-473f-9348-3b98a31f829a', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'd4eedc4d-9afb-45d4-81ac-165a7e83c5c4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('ec5d0ecd-db42-44aa-ba3a-2e7f09ced7a7', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '69fcfb0b-fe78-413e-ad53-1daee0abc00d', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('ed7a5fe5-6913-4612-9433-b55174e23f01', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'bb52fb89-cf80-440a-9a23-f2d018891d9b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('edcf0efe-335c-44bc-9cf3-489ed5049d1f', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f6195c3d-559c-4f36-85ea-96d047ca9e2e', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f14b0d39-e24e-4bd4-b622-3acf5594abb0', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', 'b8e807fe-5bfa-45ef-8e0d-5a03ee38b201', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f18baf9e-b807-4c2b-90e9-3d3b5bc4fcec', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '8c330bdf-1cc9-4337-a445-a970e7f3a158', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f26febf2-8eed-46b6-a2c2-b25de44575ef', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '797c5aa8-4383-4d64-833a-902dbd337415', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f2dad397-447d-44ae-ba3a-ba2b7e45a992', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '9c689c1d-eeb1-4686-b2e3-18056dac7a90', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f3087176-f6cc-43b8-8cde-915b3d03ebbd', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '06acb6e4-d7ea-468b-b1f3-82f91f7895c4', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f4c3d7ff-f92b-4964-95a4-7f468189dfae', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'f4d4d9b7-debc-43a9-b155-c7f1df9dafa2', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f5a2f957-a5ea-496b-a89f-4bbafb77e775', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '736205ed-7199-4639-b1cc-91724c07e184', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f5b965d3-d294-404b-aa09-c7de63daa37e', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'c67f5450-767f-49f5-b68b-55e9daccbb9b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f5ca862b-1103-47aa-b706-2813e72401e9', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '42bb3291-436a-4caf-a5fc-879ebde4ee64', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f5ecd198-41c3-4f10-aa06-80b0b9f338ca', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '396598f8-b1ed-466f-a484-b9552e409f50', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f6e08bfd-2eab-40cf-8393-66cf7c510538', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '36bd2a6e-52f4-4c1e-b319-714fd09ffffc', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f865be12-6caa-45c4-ba38-40c750c64cc6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '2f7cbc67-3624-4b87-88da-f0b385ab9a11', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f86e7685-de80-453f-bf20-8236e5023634', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '52027ce8-44a4-434f-af58-96d57fa04ccd', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('f918682c-71f2-492d-a54e-3e726fc0dde7', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '920e05dc-4775-4d11-8962-d19353f9a328', 'test11', '2020-03-17 09:45:48', '2020-03-17 09:45:48');
INSERT INTO `pc_auth_system_role_menu` VALUES ('fb64a37a-fa88-4b44-8a9b-40fc695bac31', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '32ec5a85-956b-4c5d-be05-082a40e8ce86', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');
INSERT INTO `pc_auth_system_role_menu` VALUES ('fc92ecbe-ec29-4766-a8a7-ab3869c57222', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', 'ae5d7bde-074e-4f24-b51f-2ab77a53ef0b', 'admin', '2020-07-20 11:46:30', '2020-07-20 11:46:30');

-- ----------------------------
-- Table structure for pc_auth_system_user
-- ----------------------------
DROP TABLE IF EXISTS `pc_auth_system_user`;
CREATE TABLE `pc_auth_system_user`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '0女1男2保密',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态  可用(Y)、禁用(N)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pc_auth_system_user
-- ----------------------------
INSERT INTO `pc_auth_system_user` VALUES ('0be15602-9a0c-4c20-98e0-42e837ce3ace', 'admin', 'admin', 'C93CCD78B2076528346216B3B2F701E6', 1, '18807771976', '1254483440@qq.com', 'Y', '2020-07-04 10:59:23', '2020-07-04 10:59:23');
INSERT INTO `pc_auth_system_user` VALUES ('972933dd-5e41-46a9-9fb8-f9c2cbdd9e83', '开发者', 'root', '9A69E50114A30C4C5C1D455A2CFB87D1', 2, '13333333333', '12256@163.com', 'Y', '2020-03-17 09:45:01', '2020-03-17 09:45:01');
INSERT INTO `pc_auth_system_user` VALUES ('99a2a66d-57ca-4eaf-b8ca-204e09934ca9', 'test11', 'username', '5C9C7934D7779F716084C9BAD7A834DD', 1, '12345678910', '123@qq.com', 'Y', '2019-03-05 09:55:07', '2019-03-05 13:51:33');

-- ----------------------------
-- Table structure for pc_auth_system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `pc_auth_system_user_role`;
CREATE TABLE `pc_auth_system_user_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pc_auth_system_user_role
-- ----------------------------
INSERT INTO `pc_auth_system_user_role` VALUES ('02fd6cbc-8ed1-4c48-90a9-2855d09455e6', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '99a2a66d-57ca-4eaf-b8ca-204e09934ca9', '2019-03-18 13:58:43', '2019-03-18 13:58:43');
INSERT INTO `pc_auth_system_user_role` VALUES ('72cdd1bb-6014-441f-b467-3370b7d89e87', 'b9dd33b5-5dea-42ab-a332-2a4548f7cb3c', '972933dd-5e41-46a9-9fb8-f9c2cbdd9e83', '2020-03-17 09:45:07', '2020-03-17 09:45:07');
INSERT INTO `pc_auth_system_user_role` VALUES ('a8b62950-a518-404c-bc88-a66beaf1fd07', 'e71f4af4-0a61-4e99-92f1-1f8e44acb395', '0be15602-9a0c-4c20-98e0-42e837ce3ace', '2020-07-04 11:01:59', '2020-07-04 11:01:59');

-- ----------------------------
-- Table structure for pc_quantized_account
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_account`;
CREATE TABLE `pc_quantized_account`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `api_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_quantized_coin
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_coin`;
CREATE TABLE `pc_quantized_coin`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本币0,交易币1',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pc_quantized_coin
-- ----------------------------
INSERT INTO `pc_quantized_coin` VALUES ('0053b0f9-19f9-4721-b4b8-99586a78c45a', '0', 'hpt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('01e6b997-93f4-4fe8-a1be-863ed22865cd', '0', 'cvc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('01f56245-85f1-4f7e-b076-9a25cbc52f04', '0', 'usdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('022a6d8d-0ed0-43dd-8649-ef3df1a9bf35', '0', 'arpa', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('026bae80-5347-48d1-9ec0-05b541f46505', '0', 'ht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('03473693-cc70-4953-bfbd-7302ef4b9386', '0', 'ftt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('043cad88-5895-4e0b-bf69-8f204d73aef8', '0', 'bhd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('06387b2c-8365-4115-91e8-0671c2ae45f9', '0', 'phx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('07b79883-fe69-4961-a0f9-c234cc9d3f7d', '0', 'ugas', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('07bb8130-693a-456c-900a-4f4d2e9be137', '0', 'tt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('07d4f388-f114-4156-ae0d-3aa06b2a0313', '0', 'cdc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('085b28e3-8b6f-4421-b3d2-462d88c66f81', '0', 'omg', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('08759f5e-4971-4a17-9fba-067ce6cba663', '0', 'sbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('08d21281-1d84-4cc3-998c-b0e20549a270', '1', 'trx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('0b2e9900-f675-4406-87c7-500bd71a2aa7', '0', 'waves', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('1093b690-105c-4609-aef4-e2e763db510a', '0', 'mds', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('11024100-2ece-4741-b361-025e0db489f0', '0', 'ycc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('12254ce0-5472-4cc8-9c73-2f2dae53f862', '0', 'lym', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('12e7984e-588c-450a-a523-53a8bbb3c0a5', '0', 'gnx', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('13de4a55-1982-4797-b897-a91b55384376', '0', 'salt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('141c600b-1402-44aa-93dc-d23c7b36cca2', '0', 'dot', '2020-08-05 20:56:21');
INSERT INTO `pc_quantized_coin` VALUES ('17c3e730-569a-4987-b5b2-799dfd1bd19c', '0', 'zrx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('18173775-6ef9-4c9f-a277-3daef6de8d68', '0', 'top', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('18b3ca51-b63d-41bb-bf58-b5a67cb58720', '0', 'get', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('1af226ff-6d86-4061-b57f-6fbe93d91d86', '0', 'ncc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('1cdf8391-3e7f-4c38-a14f-b1f4258cea7a', '0', 'iic', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('1cfa45a3-258b-4b56-b895-82e5e9829b3f', '0', 'nano', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('1ed5acb1-cdc2-4aa3-b2d3-f70d7fefc11e', '0', 'fti', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('1ef675da-7543-4c0a-8cf1-16ffc1098544', '0', 'new', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('1f2201d2-691a-4c4a-8e74-ececffac5d46', '0', 'let', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('20beab9b-7a14-4035-8131-cd59243b81ee', '0', 'hc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('20f1deeb-8099-4f1e-bb58-e0673ba2d293', '0', 'portal', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('2123d86f-12bc-4ec6-b3d6-13fa940a45f3', '0', 'tusd', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('212c73a7-790e-4d13-9dbb-200c0af43627', '0', 'aac', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('21eaad6a-3ecd-4aa9-9a38-eb4ae583e5c3', '0', 'swftc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('22661210-ff3a-494d-a15f-4438ba7136b7', '0', 'meet', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('24cbd3bf-f897-4dda-9f0f-41840d15473b', '0', '18c', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('257d0f81-0d3f-44e0-9cff-eb46ccfc5c76', '0', 'act', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('25d065cd-28b0-42e3-b365-1ab31e5a29cc', '0', 'vidy', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('268d1472-073c-477c-ac59-faa9689c83c5', '0', 'trx', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('273d813e-f124-466b-b93c-39069a4d58c6', '0', 'gxc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('27f5aba1-74e4-4f8d-a167-a3eb126a5563', '0', 'mco', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('29ec3905-7986-42c5-8d9d-5a8e384dbdb0', '0', 'eko', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('2ad45764-80e4-4550-8c3c-0b410316513b', '0', 'srn', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3144ccdf-7f0f-4845-b3f5-0cd599dee31b', '0', 'egt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('31e9266a-7272-4747-93b1-224aa96c6008', '0', 'vet', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3296476f-6912-4fd3-8620-185e01033ca6', '0', 'soc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('32bcf3d3-23b2-4684-b478-59f51bca814a', '0', 'ksm', '2020-08-05 20:56:20');
INSERT INTO `pc_quantized_coin` VALUES ('32c7d430-fa25-4c97-8ac7-8f721b667078', '0', 'tos', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('362d7b6e-35cd-4966-8b22-787db40925be', '0', 'seele', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('36e2b284-c9fa-4d04-8d00-06771185b5fb', '0', 'mtl', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('389439e1-49dc-465a-8681-10d14fabaa2b', '0', 'iost', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('38df95ac-368c-4039-b1d8-c5d8b1c6983b', '0', 'hb10', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('39c39043-9b6c-4cf0-bf01-48e55fc9e841', '0', 'adx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3bb6c02f-2696-490a-8531-842e42a3dd42', '0', 'storj', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('3c30aa6f-af9a-41b5-b93d-a5813748a1a4', '0', 'pc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3c73ab19-5814-4cdd-8372-0df4aeccc921', '0', 'hot', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3c81a247-40f0-4248-abc6-94f020dd1046', '0', 'idt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('3d48a71f-099d-4916-9a79-2d97b66e9d7b', '0', 'nuls', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3e7ec4bf-7ae3-44f1-9e0c-5a58b283a732', '0', 'ogn', '2020-06-24 14:55:22');
INSERT INTO `pc_quantized_coin` VALUES ('3f070c1c-4904-4595-b068-4166871e07ee', '0', 'mxc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('3f4877e1-e815-4e4a-8e2d-09ec719a01b0', '0', 'mtx', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('3f88a63c-c19a-428b-8792-5f5794206497', '0', 'etn', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('4050949c-57d8-4e96-b88e-b314f8b4fd89', '0', 'elf', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('41175e6b-7de2-4d49-aa49-d51c0fd56fbf', '0', 'akro', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('4145f66b-fb31-4888-bfa0-0477f971e6f4', '0', 'vsys', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('423d52b0-d57a-4e0e-8e71-d922314a7ed8', '0', 'bkbt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('4429fde8-301f-4d20-bd72-b2dbf06c508d', '0', 'chr', '2020-08-05 20:56:21');
INSERT INTO `pc_quantized_coin` VALUES ('46cd2fdc-4221-4a30-94d7-ac545fb39889', '0', 'itc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('470ab044-2ac9-419e-8f9f-9d6fb028206b', '0', 'kan', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('47527828-926d-4826-b505-734b53615fb9', '0', 'mex', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('47950c0b-b372-49b5-bd84-2c9d947dbde1', '0', 'zen', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('49159c67-2144-404e-9111-f183fc3a44fa', '0', 'bsv', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('4946f545-dbe0-4ca3-891a-c6d6fa252eae', '0', 'iris', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('49ebe67d-8be1-402f-8526-ac645794cc8e', '0', 'chat', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('4a0ce912-0314-4a99-a6f2-ea01b1195707', '0', 'ont', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('4e13f200-ef4c-4760-9776-b66f8f3ee5aa', '0', 'appc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('4eecbfd1-4f91-4c41-9e7d-eac4fc849179', '0', 'mt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('502acb2e-1936-4558-a9e3-82d75888fe14', '0', 'snt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('51427b1c-66f8-492d-b3e1-80cf0e10ab13', '0', 'hit', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('523b61b7-1941-49dc-9da5-d19f27f8243c', '0', 'bcv', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('530b99c9-663e-4d89-a236-0991d6272e08', '0', 'bch', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('556b599d-3e91-4054-b5e2-7a197f9ac30a', '0', 'btc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('565f31d2-77d6-4965-8608-e9eb38f789fd', '0', 'ckb', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_coin` VALUES ('57582b6b-c87a-439e-b137-989051225e9e', '0', 'gnt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('578a6753-5af8-44d8-8638-08e308cc7a7c', '0', 'ncash', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('58176e57-850e-4253-8900-e1b01e8c1960', '0', 'dac', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('5b3a2896-1a35-415e-bd00-33989e98870d', '0', 'kcash', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('5b6c61d5-bdd7-4463-b051-ef92a7cc909f', '0', 'cnn', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('5c90802c-afab-4174-a3b4-f47460331511', '0', 'nexo', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('5e4c161d-1c9e-4575-93c7-5291431a5c69', '0', 'algo', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('5e81b0be-e799-4fb6-812d-6893a75e363a', '1', 'usdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('6107da14-90b3-469c-aa4c-8126d86c2de8', '0', 'stk', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('638659aa-4247-4c0e-9843-f9f9e2972b60', '0', 'man', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('63e69b74-dda7-4f3b-9ecc-9dfd59831faa', '0', 'pay', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6469d8d5-7252-441e-a670-1e7986f96b3b', '0', 'ssp', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('647506ad-9e43-4eaa-8323-971af1ae89d9', '0', 'qun', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('660d1f2d-2f94-454c-ba03-5d62a29f200f', '0', 'link', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('66c39bf8-02f4-4302-a8b6-88cf0884fe97', '0', 'rte', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6897c2ec-3d65-4e38-b04b-f13f3160da2a', '0', 'aidoc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('690c7855-f270-4178-8a86-52e9ca9b229e', '0', 'ost', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6b349626-9dfb-48a8-a157-d60a87899aef', '0', 'luna', '2020-08-05 20:56:19');
INSERT INTO `pc_quantized_coin` VALUES ('6c23fd86-5f42-41c0-aa9c-2383241e93a0', '0', 'rccc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6cd61a61-4cbe-4a3a-9f40-d31e67b90271', '1', 'ht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('6d248f0d-d526-435c-849e-1a53f9fb630d', '0', 'rcn', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('6d26453d-de7b-471e-bd0d-244cfd66767d', '0', 'lsk', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6d54e4d4-222b-496c-8ee2-93d4e5076110', '0', 'bcd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6d70c568-0471-4d96-8a30-dd45919a6115', '0', 'fsn', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('6f59339b-b53b-4373-b8ca-8a627953d4c0', '0', 'dgb', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('6f6bbd95-e794-4677-87e6-03191f438762', '0', 'em', '2019-10-30 20:28:19');
INSERT INTO `pc_quantized_coin` VALUES ('70fb6a80-de06-46d8-ba62-ea8cb40cda67', '0', 'cvcoin', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('7219c156-15df-4118-8c41-1b9779f681d7', '0', 'bix', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('738426c3-05ce-466c-b8a7-b52728836a82', '0', 'zjlt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('7541df92-7325-4be6-851f-063f5f70f9b0', '1', 'btc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('787f1bf3-8b42-47ac-9583-cd0a8d7269b3', '0', 'loom', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('7a201755-df6c-43d9-9fe0-55692a60015d', '0', 'wpr', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('7ceecdad-bf40-4353-a020-236c2eab08d2', '0', 'qash', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('7d12f564-e423-420a-8cbc-8f883435d680', '0', 'gsc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('7d8dcd3c-de33-4eaa-bf73-00d3b5c5b965', '0', 'edu', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('7dfa834f-0e2e-4eee-a855-7ff10c4f70d6', '0', 'cre', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('7f42633e-5322-474e-8cf3-9a4705fd030f', '0', 'xmr', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('80d99ef5-5e97-47f8-b461-feb9621ed2c7', '0', 'eos', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('81339c72-315e-4403-9bce-efa283bcb6db', '1', 'husd', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('83e4605a-0684-45bb-967c-f5c03e6a5788', '0', 'grs', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('843dc3f1-5a0e-4774-a29c-0534b8cc39e9', '0', 'ren', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('85496a4a-79d6-4585-8251-da499a3d087b', '0', 'bifi', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('85620792-3854-446f-a47e-ff84e89931d4', '0', 'gve', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('856847de-3eec-4257-a8e2-f6765273a3bc', '0', 'propy', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('85a4a31e-2d79-4594-ba56-5b6801fc1749', '0', 'abt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('85a88485-5bfa-4e5b-b134-24226738ee8c', '0', 'rdn', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8619823d-d056-46b6-b188-23eb71a33aa6', '0', 'uc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('87507448-66ee-4375-9219-291d0185e1f5', '0', 'wan', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('87d34185-3107-41ec-a5ac-681ebac6d0d7', '0', 'one', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('885fabe4-3730-4f19-849f-7b57fa46a14c', '0', 'zla', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8900a16d-eaf3-4e1e-a593-b650a1a9a1d1', '0', 'lxt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('89107abe-56c4-434e-bf75-29b4f0d5e986', '0', 'bat', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('8b80420f-cfdd-4558-9494-b1e8fa300d96', '0', 'dcr', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8bd3d118-b16c-4492-bad5-2b4d0dcc5821', '0', 'pnt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8cdef7a6-82c8-4134-8a86-fe2fb16e2f4d', '0', 'bft', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8d9274f4-00ad-4812-8d7a-2b98eb761cf2', '0', 'bts', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8df4c68c-a265-4b25-84a2-eb040bbe9071', '0', 'gtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8e2a4fc0-f9bc-458d-b12c-2dd57a0cb091', '0', 'mx', '2019-10-30 20:28:19');
INSERT INTO `pc_quantized_coin` VALUES ('8ee4c875-51dc-4dde-b928-875c042cea68', '0', 'lba', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8f31c7ba-3175-4c71-a167-3ce064319b79', '0', 'xtz', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('8f366e42-a8dd-4ac5-8aac-df2fb621fcad', '1', 'eth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('8f715c78-4774-4e70-a52d-f58df6665f90', '0', 'rvn', '2020-08-05 20:56:20');
INSERT INTO `pc_quantized_coin` VALUES ('915a4c9c-fe0c-4d35-8950-a8aa52892990', '0', 'bt2', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('91d06425-37ab-46f6-bbe1-acafbae78f6d', '0', 'pai', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('923381f0-cb5f-4fee-aac6-994cd5975af4', '0', 'for', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('93770b61-e05b-4627-8b0f-286afbda0fd1', '0', 'theta', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('94b7d343-93b2-4abc-8589-ad722721bbf8', '0', 'ae', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('96b7e71e-c6a7-4752-81f8-943852a23065', '0', 'eth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('9b58f7a4-6417-4d09-a182-ab2f3c7a23f6', '0', 'cvnt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('9ba0de34-49e7-4400-ae0a-7e45bc7e7ba7', '0', 'xem', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('9ea28b52-93e8-4adb-b7d4-0674f432cf37', '0', 'ast', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('9ed0ee90-2644-460c-a481-5df7d9677af2', '0', 'lol', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('a063e386-6977-4b57-a72b-98c38ab8aeec', '0', 'req', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('a0faa0e4-586d-4108-a3d0-996df0f1fd3b', '0', 'atp', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a2d2d6f9-ee50-41b0-9fc7-5b5e1bb5ef55', '0', 'ctxc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a49563e3-1b76-4130-904d-1c04f295090b', '0', 'dbc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a5168019-2ec9-4d8e-bd50-7a56ab250673', '0', 'eng', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a6dde8db-d1af-41f5-9c8f-0df4578acb45', '0', 'cmt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a77db685-271f-4a52-a40e-ab8026a6f2bb', '0', 'ekt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a7b1d82d-c710-4225-b05e-cb306b7f7de5', '0', 'lun', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('a8578a08-6aa4-4f95-bb50-efadbe33d7f1', '0', 'gt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('a8c4107b-e484-45bf-a86a-5f3baeead836', '0', 'usdc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('ad73d81c-31dd-43c7-b7cc-c51bcd6c3484', '0', 'ogo', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('aee93398-8b58-4c94-861d-a7faa4cd66a7', '0', 'ocn', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('b034347c-1a94-4989-9a8c-5ed6f39143c6', '0', 'wxt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('b0dbf5c4-6623-442a-a440-d7cb4e3672f7', '0', 'box', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('b1c3e4f7-bf63-4517-a847-ef32be4e1589', '0', 'btg', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('b2416876-32e0-433c-a727-05a73ef8c76c', '0', 'doge', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('b2e34c5b-7271-4dfe-94e0-dd30e2643b94', '0', 'steem', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('bbd96e1a-4f30-4e9f-a621-29bd7808e040', '0', 'bt1', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('bec82a6b-92c9-4427-85ad-b75206bafd39', '0', 'ltc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('bf684573-2908-434f-aa96-85de8b33cdff', '0', 'skm', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('bf780236-ad35-44a2-bd87-2b8ca66be9d9', '0', 'etc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c1d254c8-093d-4a42-b82d-fc52977104f4', '0', 'sc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c250966f-2f96-46c7-9067-28b261c9d846', '0', 'bht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c297ac03-45c4-45d1-bef3-3cb82e4ffb6a', '0', 'cro', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('c4fb9d6d-2af9-44fb-b3ba-d8716205251b', '0', 'zil', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('c650d421-eece-4b0b-857e-3f68707ec04c', '0', 'yee', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c6c3f95f-ee61-4575-b14d-9a6e3d926bb8', '0', 'cnns', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c79de003-2390-4a59-9e42-ed190d2c9989', '0', 'wax', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('c7bdf26d-af7d-4614-be8e-19e3fe338a1d', '0', 'evx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c7c42d6c-165c-414f-ba4e-c0f6720f375e', '0', 'xmx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c7e19c05-c61d-44b3-a26d-5faf8ff12984', '0', 'ruff', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('c7f1cf6d-9d1a-490c-beb5-e5657d14a937', '0', 'waxp', '2019-10-30 20:28:18');
INSERT INTO `pc_quantized_coin` VALUES ('c8a7dd33-2de4-4b30-8bb3-6adce5877e34', '0', 'utk', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('ccf9f336-0035-4b3e-b538-2e48f5a778ca', '0', 'xlm', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('cdfcff9f-94d5-47d5-a85a-e35635e93cda', '0', 'nkn', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('ce048685-9046-4a99-a25b-7f1e4ed173c9', '0', 'musk', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('ce0e6645-4356-41dd-bc26-05269c9082f5', '0', 'iota', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d0f52b95-8d08-4696-a6d1-4ee5c2896f7c', '0', 'dash', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('d142fd34-80c5-449d-8b90-5513db72dd54', '0', 'tnb', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d226e1f6-8e9e-4b35-a3ac-f7d34257361e', '0', 'she', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d3e4c933-31f4-4d8e-8522-b7dbc676b6ea', '0', 'but', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d43c7f68-05f8-40e7-aca6-6b52fcd820b6', '0', 'blz', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('d71b299d-04e4-4130-aaa6-468395f11e04', '0', 'nas', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d727e1b4-6772-4083-9ad7-9a5180dbe5fe', '0', 'mana', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d73124c9-c024-42ff-8483-c435c6ee5a3a', '0', 'btt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d8b95375-8250-41d3-8d40-92cf4988c450', '0', 'rbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('d9045db6-5cbd-44ed-ae52-731386ca2693', '0', 'poly', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('da177e4a-5bc4-44f2-9656-fdcf6a9ce64f', '0', 'powr', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('dd0a0b46-f7ca-4f71-97e8-2bd4d4ae25a1', '0', 'wtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('dd15eb67-3fb3-4b83-89d4-e65ef35de783', '0', 'cova', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('dd9a6004-3bc8-47bf-b3cf-d703750db3a5', '0', 'dta', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('de4651fa-6059-412d-a6e3-84b17715d66d', '0', 'mtn', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('de515df2-0d1d-48e5-8a0d-6ae0d678b798', '0', 'smt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('dff4bebc-b69a-4a56-b2df-850cc10221b7', '0', 'ardr', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e1f748df-51d9-44da-bd87-073277e0421f', '0', 'tnt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e3252032-96e4-4910-a67f-3daf573291b6', '0', 'dgd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e37c271a-1165-49cb-8282-60aa3e8dfad3', '0', 'icx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e4705893-a395-4fb9-b87b-92741a02f51b', '0', 'wicc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e49377b4-9102-4190-85a0-b44e4fff810b', '0', 'atom', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('e4f1d6d7-6071-47a2-8ceb-70399f82d77c', '0', 'qsp', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e58254ba-61c8-4698-99ec-e7a1a82afa66', '0', 'qtum', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e6082279-b73f-4bf8-a02b-a2c9091139d6', '0', 'dock', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e68147fc-8a22-4317-b28a-70d6a096e458', '0', 'datx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('e92e0434-7ae8-4dc5-b4f2-5323766dd6ef', '0', 'hive', '2020-05-13 10:11:01');
INSERT INTO `pc_quantized_coin` VALUES ('ea3a5576-01e3-4014-8c67-57bbccf23516', '0', 'knc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('ebe4ddf0-511d-4def-9b07-6a35460e5979', '0', 'npxs', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('ec40efcb-d4a9-4396-9087-3f2ccaf207a6', '0', 'egcc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('ec9ba687-12cc-405c-bece-00dff734f1ff', '0', 'ela', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('ed35c3f5-f20a-4f2e-954f-0c99d775b044', '0', 'zec', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('ed4f8cb5-1c90-4c9c-a9c8-0324505f0995', '0', 'btm', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('ede9839c-74ef-4db1-a234-1af2e668577e', '0', 'neo', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('eeecdd5d-0878-4e4f-88c2-4083b13e6fb8', '0', 'bcx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f08db057-b7f1-4c34-91e3-89e94786b001', '0', 'xzc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f198ca87-d576-4a21-9324-7989bc2ee5f9', '0', 'uip', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('f22209b7-4e27-4dd6-a6f2-f79da2c2436c', '0', 'lamb', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('f243d3a6-b30b-43dc-b0c4-bda7ff4a674f', '0', 'snc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('f42b38ad-81df-47fa-ae1d-595e419a8eb1', '0', 'ven', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f4e495bc-e35c-4606-b8c8-f6e68487dd2b', '0', 'dat', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f53f31af-71d0-4a82-bf3e-075c16e8b364', '0', 'ada', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f5f5a252-551e-4543-b888-88f0187473f9', '0', 'pax', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f8bfb327-b21d-4c79-9553-dbf94936781e', '0', 'node', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f8fb4231-6357-4dd4-b33a-e598c1e73c30', '0', 'fair', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('f98f0482-1926-47ec-adf9-7838cc82c58c', '0', 'gas', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('f9fe8aa3-5f33-4a4a-801a-6053b9cb2e17', '0', 'topc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('fa430ac9-69af-42e8-af1c-09f43aef5588', '0', 'xvg', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('fae6f4e1-0e4c-409e-88f8-61f8effbbb6d', '0', 'xrp', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('fb219338-2f30-4d30-970e-02ea923ed272', '0', 'rsr', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('fc3895fd-00e1-455c-8be8-f1372e947833', '0', 'trio', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('fc8ae06f-02c9-40b5-8bed-bc74e7b5f28c', '0', 'uuu', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_coin` VALUES ('ff904d07-542e-4200-ae7d-4201cc35719b', '0', 'pvt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_coin` VALUES ('fff21101-9f97-49fa-8385-02c3bc56e11c', '0', 'kmd', '2019-10-18 10:58:49');

-- ----------------------------
-- Table structure for pc_quantized_order
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_order`;
CREATE TABLE `pc_quantized_order`  (
  `id` bigint(50) NOT NULL COMMENT '订单ID',
  `amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单数量',
  `canceled_at` bigint(20) NULL DEFAULT NULL COMMENT '订单撤销时间',
  `created_at` bigint(20) NULL DEFAULT NULL COMMENT '订单创建时间',
  `field_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已成交数量',
  `field_cash_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已成交总金额',
  `field_fees` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已成交手续费（买入为币，卖出为钱）',
  `finished_at` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单变为终结态的时间，不是成交时间，包含“已撤单”状态',
  `price` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单价格',
  `source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单来源',
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `symbol` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务用户id',
  `cct_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币币id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_quantized_order_err
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_order_err`;
CREATE TABLE `pc_quantized_order_err`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `symbol` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对',
  `amount` decimal(20, 10) NULL DEFAULT NULL COMMENT '交易数量',
  `price` decimal(20, 10) NULL DEFAULT NULL COMMENT '交易价格',
  `order_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型',
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常原因',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务用户id',
  `cct_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币币id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_quantized_order_error
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_order_error`;
CREATE TABLE `pc_quantized_order_error`  (
  `id` bigint(50) NOT NULL COMMENT '订单ID',
  `amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单数量',
  `canceled_at` bigint(20) NULL DEFAULT NULL COMMENT '订单撤销时间',
  `created_at` bigint(20) NULL DEFAULT NULL COMMENT '订单创建时间',
  `field_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已成交数量',
  `field_cash_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已成交总金额',
  `field_fees` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已成交手续费（买入为币，卖出为钱）',
  `finished_at` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单变为终结态的时间，不是成交时间，包含“已撤单”状态',
  `price` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单价格',
  `source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单来源',
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `symbol` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已处理 Y是 N否',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_quantized_order_filled
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_order_filled`;
CREATE TABLE `pc_quantized_order_filled`  (
  `id` bigint(50) NOT NULL COMMENT '成交ID',
  `created_at` bigint(20) NULL DEFAULT NULL COMMENT '成交时间',
  `filled_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成交数量',
  `filled_fees` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成交手续费',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `price` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单价格',
  `source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单来源',
  `symbol` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_quantized_order_info
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_order_info`;
CREATE TABLE `pc_quantized_order_info`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `symbol` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对',
  `times` tinyint(1) NULL DEFAULT NULL COMMENT '处理次数',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `cct_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币币交易id',
  `is_die` tinyint(4) NULL DEFAULT NULL COMMENT '是否死亡',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_quantized_symbols
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_symbols`;
CREATE TABLE `pc_quantized_symbols`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `symbol` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易对',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pc_quantized_symbols
-- ----------------------------
INSERT INTO `pc_quantized_symbols` VALUES ('0094d7aa-ed01-4c4c-a019-a6986ad145a6', 'usdthusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('00e4871d-ca93-4ba6-953a-7115285d9834', 'lolusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('00fd54f2-5783-4297-94e4-476b4a5239ea', 'dgdbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('01416513-98d7-47e4-a83f-ea957a3e9676', 'kaneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('01438430-0440-44b8-844b-4d6cf9409e13', 'ostbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('01fb6f22-8607-4d1f-8f63-9289e2518893', 'aeeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0305803b-4e77-4752-b392-0b7a1b8b2c81', 'toseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('03baed99-f0c5-486c-9b3f-12dd694eadbc', 'wxtusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('046f9345-bed4-4e3d-add3-46ed3743d8ac', 'gnxbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('04d92e7f-88e5-4834-8938-f3a02d8e66fa', 'zjltbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0579f1b9-e414-4e1c-adca-c0f8cf0a55a1', 'cvntbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('05b75752-06b5-4f81-9ae2-68b602535870', 'bsvusdt', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('05b91ba1-8da0-494d-81b3-74b8b1757e9f', 'hcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('06be9b6a-b24c-43dc-a946-587f7d7909a5', 'wtceth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0784968b-3871-4295-bef1-f2b9ae03fe00', 'blzbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('088077c6-b907-45b7-8d74-3b5bcdb6ed95', 'gtht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('08e1c9bf-d239-40aa-942d-42b9a3f95913', 'egccbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('09009cee-2678-4242-8bcb-739dd2acc5f9', '18ceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0924a80a-28de-4b26-aea5-303372e227e4', 'scbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0947a152-95e2-4cf7-8d7d-d180dd01e82c', 'ognht', '2020-06-24 14:55:21');
INSERT INTO `pc_quantized_symbols` VALUES ('0aa4ca17-49df-4c13-a4d8-3f9015a3fae1', 'dashht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0ab97b07-fa1a-42df-8bdb-c94c76d5e050', 'npxseth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0be07f33-f1b5-4544-ac15-089de1b02df7', 'chrht', '2020-08-05 20:56:21');
INSERT INTO `pc_quantized_symbols` VALUES ('0bea6eb0-f931-4fd8-9716-68ab38dde350', 'faireth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0c015540-fb6b-4f33-be5e-2f9ae467f67b', 'batbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0c3e373c-5e47-46f0-98c8-7bb1d58c4fa3', 'btttrx', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0d5b5487-ecfb-4f83-a0c3-f5a14de62d0f', 'xlmhusd', '2020-05-13 10:11:01');
INSERT INTO `pc_quantized_symbols` VALUES ('0d62645e-42fb-497d-b23e-48fffa0d207e', 'cmteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0d674695-617f-412c-8615-e77fd7579044', 'ksmusdt', '2020-08-05 20:56:22');
INSERT INTO `pc_quantized_symbols` VALUES ('0d789f32-2b8d-4ccc-90fe-470a859d5394', 'akroht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0d90803b-6042-4fd7-ba4e-0e232d85059f', 'uipeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0d981e16-4981-4835-bc37-68ec9868f469', 'gsceth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0d9983e9-6ab1-487e-9de0-3523eb4ef202', 'qtumeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0db53736-059b-43f9-848e-36eb3cb4b9cf', 'vsysht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0de099ba-3de1-4952-8b79-42082f8edfa5', 'ttbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('0eaf83c1-fb9a-4811-934e-ec28f4ad5859', 'wavesusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0f74e84a-7719-41ed-a62b-9d35d109fc08', 'yccbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('0fd551e0-72f5-4f46-a87c-8f03423ea4b8', 'mtxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1001336a-0fcd-4b7d-809c-ef1f1604484e', 'blzeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('101c0005-637e-4ca6-a536-6be450bff100', 'dashusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('10bc03d2-2605-4d64-9b7a-3b7c91f3466b', 'egtbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('1152d342-ba47-4ffe-9e5f-c11330f07468', 'wiccbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('117ecc3e-8ac4-4c7e-89f4-48fe3e4d02c0', 'bcveth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('11c2abc1-2528-45b1-b6ab-acbc23ffa43e', 'triobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('12a10aee-5bb0-4b97-a4c6-dec054534572', 'eosusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('130a7407-8f50-4382-900d-bfaabf3bde67', 'qtumusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1386d086-04a9-46c7-9afb-03027e7c6c60', 'wicceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('145b504a-904f-4b5a-8aa6-1a25667e76aa', 'shebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('149e125a-1a59-4df1-95ee-d1bc3b22c909', 'skmbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('14dbdb9c-3bfa-4e10-a7da-b40342b7b4fa', 'sbtcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('14eaa41e-9406-4db6-a484-ceb63ac4bc84', 'thetaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('14fa51fd-fb7a-4fd3-a3d4-5438f3f960ca', 'ogobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('151959bd-6e42-4e37-8ef3-1fe2abe6bc57', 'cvcoineth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('16001fd0-f5f6-43af-a86b-324a9ef75385', 'lunaht', '2020-08-05 20:56:19');
INSERT INTO `pc_quantized_symbols` VALUES ('166bb979-d1f6-4d5a-9ef4-bd4fabe009de', 'lolbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('16e996a1-a7e9-49de-b83b-da654f45bd1e', 'bateth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('1762aa8a-7041-4611-b6e6-b3ccf430de75', 'nulsbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('18050e96-e5a2-4bd6-8d4b-2cc6370cab44', 'dtaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('18355894-2a05-4f46-8646-53537be3b848', 'zilbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('183dda2e-13f0-45f8-a351-43cdb92c2089', 'evxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('186aa016-1a4f-4363-a5cd-71ff651442ec', 'ethbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('19a473cd-65ea-49e5-85f0-cffbd974a983', 'bchht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1a1f0faf-11cd-4866-b042-c40b29f412ae', 'dbceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1a5da4a6-710c-4066-9cc8-cbc5bafeab50', 'btmeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1ae8aade-1ac3-437a-9ceb-e6a34746e2e9', 'embtc', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_symbols` VALUES ('1b4776cf-04f7-4cc5-8bbb-0356c3eae373', 'nexoeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1bfa4d7c-cb78-45e5-9ce7-aea05c54eb5c', 'nodebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1c209ba5-98a4-404e-b99a-0cb7ad24ec84', 'mdsbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1c2598a4-392a-4fae-bf30-f7826a419cd5', 'musketh', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1c40ae3b-5ae4-4728-9249-b03da894cfb3', 'bfteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1c91b69f-7861-46f1-8652-d0e4e6a1e128', 'datxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1cfe7114-f820-4605-aeb4-3561e5cd9e14', 'geteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1cff46ae-cb85-4511-b068-71186d3761cf', 'cvcusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('1d7d992f-9d67-48f3-b443-3866cbe6891e', 'tnteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1dd576b9-393b-4f99-8af2-fe470fa0ad45', 'etchusd', '2020-03-09 10:49:58');
INSERT INTO `pc_quantized_symbols` VALUES ('1e33796d-9f54-4866-b528-465dfe4be924', 'cvceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1e731773-63dc-4417-90fe-ee75986a95ed', 'gtceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('1f955cfa-6e07-40d8-9cb4-146e26d15c51', 'bhdht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2026e515-464c-4ca4-848b-adc0cbfd509d', 'zechusd', '2020-03-09 10:49:58');
INSERT INTO `pc_quantized_symbols` VALUES ('2036ada9-bf53-4dfd-879d-125b663cf069', 'nanoeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('20bfea2b-42a9-4a84-9228-9123aad40e48', 'rdneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('20cb15b1-3d8d-4e13-9d34-74f2849e261c', 'nanobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('21801556-b46b-4b99-b827-58b58d4fb3d9', 'srneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2189f590-4482-442d-8b60-1923a2c1b681', 'hiveusdt', '2020-05-13 10:11:01');
INSERT INTO `pc_quantized_symbols` VALUES ('21c004eb-4741-4b2b-9459-63e14669061d', 'onebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('21eb8de3-bb47-49d4-91bd-1c097e7b05f3', 'mexeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('21f41dad-8c3c-4891-a5ce-b85fd5e58f77', 'reneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('221dd1da-0284-4cfd-8762-cb0acb956d64', 'reqbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('22e306a5-30c9-4d5a-b11d-abcbaf160471', 'smteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('22e36ef3-0d19-4113-87b1-62c1fe450acb', 'swftcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('25c1f675-017a-4aff-977b-78afa603fb1c', 'omgeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('25d0f2be-ff6b-404a-aa0f-b863880ebee9', 'ckbusdt', '2019-11-18 10:07:10');
INSERT INTO `pc_quantized_symbols` VALUES ('260c83e0-8a72-48e1-8272-15b91941a7e4', 'manabtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('26b7c494-7da9-44f6-b647-9039c385002e', 'ctxcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('27151ec6-07ee-4f81-b251-b6938872c8d0', 'nexobtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('271e2a5f-8aad-4eec-b16a-9b17bf2d4c33', 'ksmht', '2020-08-05 20:56:23');
INSERT INTO `pc_quantized_symbols` VALUES ('2774cf02-253f-4dac-adc0-c35354efacad', 'zecbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('28055db2-5513-4e88-803d-2feb8497af5f', 'ctxcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2870f0ff-f595-4cde-8fec-53e6bba0d0fd', 'boxeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('28c6ae21-28bf-4b1b-a006-8284633608de', 'bchusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('29314e40-7af9-4d33-90de-f0ab58737dd3', 'topceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2937e26c-634f-4dc8-a8db-24d8e30dd29d', 'cnnsusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('295e9cf3-9a86-4616-9557-ba540e3ce505', 'vetusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('29c7daa2-5dc3-4e37-bb87-a8501952b39e', 'aeusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('29d32823-3720-46e8-ba27-4797ae675d8b', 'nulsusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2a31ee70-6d88-43f0-bf45-6610f4654271', 'storjusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2a564923-8ee0-4aa9-b9eb-5f91be712c84', 'cnnsbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2b8b0e02-32bf-4c29-a89a-e4d41e3bfb22', 'waneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2bb31f55-48be-4637-9399-3140b1de3706', 'chrbtc', '2020-08-05 20:56:22');
INSERT INTO `pc_quantized_symbols` VALUES ('2bd047ae-470a-4d45-b6ad-3930508a606a', 'yeebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2c36a445-e2d8-4b63-91b5-a8e844560c43', 'propyeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2c5090d3-9c71-43d7-94c4-37f356716d72', 'wpreth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('2c8412ca-2229-4e28-8000-5fc73258d87b', 'pceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2cc93230-1d1f-48d5-ba36-677dc67fee9f', 'idteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2d869e2c-357d-491d-9807-77c3186049c5', 'nodeht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2db28576-6cc6-42c9-b12b-b63e07e95049', 'polybtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2e1b716d-1a37-4891-a001-71945774d864', 'ogousdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2e66ed29-459f-4549-a8c7-2b42b79e41a1', 'eoshusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2e9d5069-91e3-414f-89d3-aeafe2c4281d', 'abtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2eb95916-d650-4051-bb90-8bf77796175f', 'fsnht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('2f51dda4-7ee6-4844-890a-64667e83c6c1', 'linkusdt', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('2fe02f1b-dd26-48d4-b83d-50ce62fff7e9', 'cvcbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('3030b7b1-d8cb-469e-a887-9879288bb4d3', 'aaceth', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('305f60ad-eb2f-4f16-8df7-7534022e40c2', 'qspbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('319414fd-dd8c-4cff-9110-b37cc7317acc', 'croht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('32cd3281-0823-436e-a9e9-404f53785910', 'forht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('332ba3da-d0b1-4adc-815a-fd7fa3324385', 'waxpbtc', '2019-10-30 20:28:18');
INSERT INTO `pc_quantized_symbols` VALUES ('33580034-a84b-41d5-ab80-10650aa8c808', 'edueth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('33ab7d48-3ed1-4b7b-9fc3-b39efc3ce662', 'mdsusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('35a76a0f-a719-4cb6-9578-cc4eb0ee15b5', 'vidyusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('35b2eaec-9ad1-41dc-a4c9-d031baa66411', 'reqeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3613aa93-f10d-4acb-857e-53e8b2a837a7', 'ocnusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('36830e9d-424e-4ab7-9f86-b034b9dcebf4', 'atpusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('36d9b071-636b-4ce4-8b2c-df37acce9fa2', 'renbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('36e53a22-862b-423c-a7b6-bcfcac76e7e5', 'iostht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('372f3e11-d7a7-4262-aaca-6e256f9a6b22', 'cdcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('37fb4d47-5b06-4ee0-a904-e0ded7d100b3', 'mdseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3815ec58-ed8e-48e0-9437-e8d4bcbd7523', 'adabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('396101f0-f985-4bc8-8427-3adf22c95eed', 'ardreth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('39a0f02a-76b2-416e-a168-3a3fd70624e4', 'paxhusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3a22acd9-97b5-4fa8-8a35-6963ac27480f', 'tnbbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3a3af26d-0ef0-4bd5-832e-970ba7feced2', 'pnteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3c5dbc7e-0459-4712-a7d4-03b49bc1beba', 'stkbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3c736768-308b-4de2-b668-674d69f170d0', 'uceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3ce0ddee-0c88-499c-a210-8dfaf15961a0', 'ckbht', '2019-11-18 10:07:10');
INSERT INTO `pc_quantized_symbols` VALUES ('3d34542f-1d58-45dd-bbdf-452ee4c59252', 'aacusdt', '2020-07-04 09:31:51');
INSERT INTO `pc_quantized_symbols` VALUES ('3d4714de-1aa2-4d41-8585-6b6cb3c4c3ab', 'bsvbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3dad469a-7689-49dd-bf69-283618e917a0', 'etcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3e08e049-494e-46b4-8363-1bcb3eade337', 'leteth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('3e428a09-2868-4474-89e2-2c172589c70b', 'btseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('3e787294-8dcd-4146-b0ac-0767a8ae273c', 'powrbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('400066c9-af6e-4fd1-8451-26f5bb8a2a43', 'mxbtc', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_symbols` VALUES ('409550fa-0650-430d-b813-527950716f42', 'bsvhusd', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_symbols` VALUES ('4160f340-bb5c-4a31-81b0-5082ac273d47', 'nulseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('41951993-6628-434b-86b8-ad8229a08552', 'xtzusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('422c1bc5-222d-4740-bc5c-ff8ac5b02807', 'chateth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('42bdcf88-cd94-4f02-afd7-5e21cd2cde41', 'mtht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('430949d6-7bba-47cd-b5b4-3ef87489de82', 'xmrusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('43166351-b232-4a24-bbaa-6d4e2f911319', 'eosht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('43668fdd-96ce-48b6-b09d-49ddb919f20a', 'ognbtc', '2020-06-24 14:55:22');
INSERT INTO `pc_quantized_symbols` VALUES ('43ccc077-0772-4e76-86d7-8d2968ac9c96', 'hitusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('445ba54e-091e-4357-b2ca-e31634788132', 'bhdusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4476fda7-7c10-4d43-84bb-2c3e74195cba', 'newbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('44b3674a-80bc-45db-baf5-9b1a4b1df9a3', 'vetbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('453b23ca-1d58-436a-a69d-742c946563d0', 'quneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4561c080-6617-4644-95c5-3fee86a4b1c5', 'elaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('460dd700-2cf7-4f51-b306-ecec9656df97', 'nknusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4613dec7-f4e6-4606-b1cf-626162e32e5d', 'lsketh', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('461e123c-d17f-41b4-8763-77c0e6c362c3', 'zileth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4646fd94-c28a-45c2-a267-ec66a2e40ec7', 'bchhusd', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('4732f4df-d29b-463c-a9cf-7c46a522912c', 'rvnbtc', '2020-08-05 20:56:23');
INSERT INTO `pc_quantized_symbols` VALUES ('49711516-fa7a-4a17-9ec6-d3ecb906f316', 'luneth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('49ddec29-fe65-40ae-b1b0-5e7dfa4a1707', 'mtxeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('4a3eddd6-6b9f-463e-8b86-14936aa5b094', 'ekoeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('4b5d661e-7268-45ca-81e2-4bec85f5a910', 'engbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4c3f1ccd-de7f-4f11-afaf-aa1a7431331b', 'xrphusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4c7e7e0d-c9a2-4e91-a714-521844d506cc', 'dateth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4c8b8975-9401-40c8-9f57-336743cb54d0', 'ethusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('4d1a5662-e38a-422c-83fe-3388651da379', 'uuueth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4de814d9-19bb-40a0-b6b0-aa5a122eb026', 'iotausdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4e753281-567a-4bf2-abbb-7939c4572494', 'rtebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4ec5863a-c54b-4f00-a771-f20515c6b833', 'xmrbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4f646d0e-fadb-4c70-8ed9-8deb6889bbd9', 'hotbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4f7b1042-4ed3-4581-a228-c6982f7a2f64', 'chrusdt', '2020-08-05 20:56:22');
INSERT INTO `pc_quantized_symbols` VALUES ('4f7fc19a-b9e9-42f3-a6b7-1adc68365c96', 'mexbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4fbdded3-ea89-4946-80c2-bdb383fc51b6', 'ardrbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('4fd79090-67d6-44c1-bd12-6f6e53655113', 'manbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('50293254-9be4-423d-8e35-19779f53b31b', 'oneht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('51430b31-d71e-4669-82f3-6b7d0584b123', 'dockbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5191172d-b1b6-41f1-8aef-7095ede3b808', 'bcdbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('51c8e6fd-99ec-4c64-a52b-a432987c0684', 'asteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('51f810c8-56e7-4b9d-a16a-311eb76c9fda', 'gtcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('520d1cbe-a990-4225-98ed-a99621ad60ba', 'kcashusdt', '2020-07-04 09:31:51');
INSERT INTO `pc_quantized_symbols` VALUES ('5217da74-96e6-4045-92f9-c792aa9b80f1', 'crobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5244657b-5b0d-4030-b545-9a47e09e23fe', 'vidybtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5254c397-a6d0-47c9-adce-8ada4d5c350c', 'rccceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5257e206-a5a1-4790-be8b-65176b5d5f4d', 'naseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5270b0b4-38c3-471e-8140-adce5121a201', 'hptusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('52a28a31-8b4c-4ffb-aba4-0d9f36016389', 'sncbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('53283a3d-bc8c-4c2c-aca9-dbdf89d876c1', 'tnbusdt', '2020-07-04 09:31:51');
INSERT INTO `pc_quantized_symbols` VALUES ('537a128d-117a-4917-8633-25a7bed3c489', 'ckbbtc', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_symbols` VALUES ('541c9523-7ba2-4b86-b150-b4eb1b2c3ed0', 'akrousdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5452398a-d9a2-442d-92f8-ff2c7c8f9904', 'topbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('54e02fab-8ab2-4bb8-bf81-ed977e8b9f3d', 'loombtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5566c975-8fc2-4432-bfb1-f6b69c2fc83d', 'omgusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('55ed4314-8f91-4cb6-a6b8-54bd5412e12f', 'paieth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('56969476-35ee-4321-bbc0-f822ae2453b6', 'kncbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('57c8bde3-b335-4f5b-864c-22d138f346ac', 'sntusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('59fd4083-8c98-48c0-826a-1f72b1226f79', 'phxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5a21a9cb-1311-4e4c-ad64-d8e5a5dc9c3d', 'tosbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5aa26fdc-2253-40f2-984d-51f948cc2176', 'rcnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5b22ffa1-c5b8-4414-94ee-42c4b2396748', 'ttht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5b9cb01c-e15a-46aa-bee4-faf012d74d86', '18cbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5bcc4466-167b-4169-8217-66a24e0c1daf', 'seeleusdt', '2019-10-26 09:57:08');
INSERT INTO `pc_quantized_symbols` VALUES ('5be9dd6b-4fe1-4724-a154-b7219019ba2e', 'veneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5bf207d6-3fa9-4eca-8cb7-6f2ec8f17f4d', 'stketh', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5c534204-cab8-44a0-bd62-8c373f96ed92', 'xrpht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5d387221-088a-443d-881e-6f152134848f', 'xrpbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('5d554689-2f30-4f61-a3de-00ca90af8ff1', 'bixusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5f7bd166-b0a2-4317-805c-965e13e18511', 'ugaseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5fb3af28-782f-4a67-8d01-045637686930', 'astbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('5fc2fa89-a325-487b-8322-fa85e084715b', 'kmdeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5fc40a81-7eb4-4ba7-8c1d-e5ff78a4a7c4', 'bkbtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('5fe69933-984a-46f8-8c71-aee9b55b78d7', 'mxusdt', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_symbols` VALUES ('6004491a-32bf-4ea0-a79e-eace35b96108', 'rdnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('600a6f06-fcfc-4c33-aad2-8421d805ef2c', 'waxpeth', '2019-10-30 20:28:19');
INSERT INTO `pc_quantized_symbols` VALUES ('6221394a-37dd-47c4-96c9-eea09e803f7a', 'wiccusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('62552d14-66c6-4640-9b99-495cb64d0a80', 'kanbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('626f41fa-0808-4a3e-9075-f2bbec15b208', 'bftbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6280fbff-0992-4e46-9dd5-2e661932d131', 'bcvbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('62c22661-f72c-4bac-9134-8d0c96d8cf94', 'trxusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('63279571-b3f3-49f9-aa8c-6530472ec69f', 'cvcoinbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('633204bc-ac6f-4a34-b7cd-2ad244c8ff93', 'dbcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('638d930e-0e30-4660-aa12-295d44b1a9a8', 'wxtht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('639aef04-1d0e-41b4-b3dc-549e6c737cab', 'nccbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('639b26ba-a68e-4f01-ab22-b232747e430a', 'creht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('64381dda-27cb-4ce2-a145-23c5245b1399', 'steemeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('645eca7b-4bfc-4d41-8d99-eb1e43fcbba6', 'bcxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('64652f2f-9abc-4039-af29-e46fba34a67d', 'elfbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('64a9f657-5827-4e6d-912d-8005ca722782', 'rsrusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('64f5c2ce-05e7-4f4c-8729-57297ff5f447', 'ekobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('652afd1c-c480-4d1e-a623-022c87a5c3c6', 'irisbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('653e5b47-ab5c-473a-8c20-2df9da7a1ba3', 'xtzbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6580b64a-96cb-4dc8-9671-0fd2188faa60', 'rcccbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('66b1dfc6-a95c-43ec-910a-3b486e80ac30', 'dotusdt', '2020-08-05 20:56:21');
INSERT INTO `pc_quantized_symbols` VALUES ('66c67be9-b628-4138-a723-2af04802a8ea', 'thetabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6863cb78-d4f5-4193-b05d-a9709c501b12', 'dgbeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('68c665c1-9632-484c-a194-5eadbb489e43', 'waxpusdt', '2020-08-05 20:56:22');
INSERT INTO `pc_quantized_symbols` VALUES ('68c9991b-040d-423c-af7a-531cdb87f8ec', 'pcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('69d9c7ef-5407-4c36-88f8-88c8daab3eb6', 'gscbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('69fb3528-2b70-4366-9492-3c1560dd3fc0', 'lxteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6a0ba11c-8633-4916-a943-e1d57e9da5a6', 'socusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6a980ab2-ac50-4e0a-b6f4-3acecc3cc48b', 'gvebtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('6c05ac44-afba-4b36-a123-49973fb35210', 'dogebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6c382ac5-806d-4258-854f-db1d64f2bfaf', 'tntbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6c89566b-952a-419a-bae0-2a6e4e7d9f18', 'dcrbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6cb26912-140e-435e-939a-542810ce7b68', 'ftibtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6f06822e-c9a3-422e-abbc-713fcdf15b7c', 'ruffeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6f37936c-3c57-4a6d-adc5-892385f0b492', 'zenbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('6f3d64c4-1e3d-4545-b244-9e4dfede27ad', 'ltcht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('6fa1ac69-c4f1-4c10-877b-f078dd164b79', 'tusdhusd', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('7010f8d4-8e8a-42a9-ac10-e0c9a28ea311', 'trxbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('701a6f8a-4f22-43bd-bb00-5500cbe757be', 'portaleth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('70a2eb6a-0244-4e63-b98c-62f2aa0c5e1a', 'elfeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('70eafa76-447d-4c3f-a21d-c8cfdca9c012', 'omgbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('716a973d-7059-4b0b-938c-4d77bd4d6fbb', 'zlabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('722ee746-4f87-4203-b075-0ba6d39a4d87', 'dgdeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('72603721-b169-4146-a065-bc3758cbe96f', 'chatbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('7278d10f-c7bb-4573-83bc-4636edb95ff4', 'uuubtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('73221ab1-2451-41af-acbe-5ad4777eb303', 'dtausdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('73631947-fa05-497e-8d93-8288828c8b6b', 'fsnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('74422282-c30d-48b0-8464-1f75dea2cb2c', 'ontusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('74a82ae3-e1ca-4ac9-aedf-fc783d3ebb5c', 'sspeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('74cc92a0-4808-4de8-8cfa-881d717eefd3', 'ekteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('771ca8f6-6637-4071-97d9-f7809a22c16b', 'gtusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('774ac2c1-a00b-464c-b47c-9ef8b8c91ecd', 'seeleeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('77562e1b-dba5-467b-abb8-71a493ac4219', 'ksmbtc', '2020-08-05 20:56:20');
INSERT INTO `pc_quantized_symbols` VALUES ('777231de-78b5-4e60-a32a-1449239411ae', 'steemusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('77ca0048-a1fe-4f8d-89e6-83d979954c1a', 'waxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7861c398-c907-40b7-8f81-c09e812b48ee', 'uipusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('78722982-f5c9-462e-8e45-c28f84906993', 'lbausdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('78e746f7-89ba-41e9-8add-4189e4d0403d', 'zrxusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7933d073-9629-4dc7-8c6d-96485be2d4f6', 'evxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('79f74fb8-44c7-4593-b9fd-4582184fbf13', 'elfusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7aa4aa0d-dde4-42d3-a1fe-9ba0445fb45b', 'btgbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7aeebf18-c688-48e1-99ad-98b8b6e20c0a', 'mcobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7beb16e5-6054-4989-b40b-34495096cda2', 'gnxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7ca0517f-431e-4471-99ec-7dd4ccd01f1b', 'etcht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7d6bee86-c451-461f-8654-3fe33ebfafab', 'pntbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('7dd8d25e-82e2-4ff9-916e-389afc630cff', 'eosbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7dedf191-669c-4b37-a19d-ef04dc860cd9', 'ltcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7dfbf528-d17d-486c-9b87-b7612c7c14c6', 'acteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7e2190bc-4710-443a-bee7-38d3a1496e2c', 'soceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7e48761d-d92d-4ac9-8bae-14b709851d3e', 'aidoceth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('7e711a47-9e9d-43f1-9fef-d7a6df114c5c', 'htusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7ec8d29c-2efb-413f-8cca-31b3e95227cf', 'ltcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7f6a6168-898f-46b7-add8-260bafa1a620', 'dcrusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7fd2e7b7-2f44-4104-99e6-80a63cd592cc', 'bt1btc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('7ffc2e2e-ed23-4bd2-bb94-a4f621843ded', 'hteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8036ff94-cae2-4ace-a945-069c19ee8ad6', 'mtbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('805fd496-67d7-4149-8227-29cf794dc292', 'btmbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('808845d1-38d0-45e6-8646-69f2484dc1ad', 'zjlteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('810320b2-e4be-4e05-b587-e2ad50624277', 'arpabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('81100774-f993-4210-a33c-b6dae4c8873c', 'letusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('812e854f-ead9-46b8-b7bd-31a788823bee', 'neobtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('818b8c44-a1a5-4e97-b5e9-8037977d3fd9', 'paibtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('81aaf602-7c3d-4d6a-b654-0c29d4a92fba', 'xvgbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('81dbab48-b83d-4515-9e55-c5579e857719', 'vidyht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8205bf91-b3f8-42fe-a4bc-582f9452966c', 'btsbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('823c54d7-d043-4a47-a490-9ecbd23c3b73', 'iotaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('824d424e-8d2e-43b7-b5b6-513775b955e0', 'ontbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8322a9e8-3eb9-4a9d-b9b1-b96895c8a32e', 'topusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('83340dac-ef9c-478e-8894-d2eb0560617a', 'gxceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('83861a58-ffe6-4379-add4-b5f6d21859d1', 'hceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('83e06810-478d-4d26-9fa8-e7bfb64134ca', 'tnbeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8510c471-090d-4fd6-8ba3-cde80178a365', 'topht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8518f8e3-acf2-42cf-b8b5-dda26435ffd0', 'lbabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8585f13f-f256-48e2-8be5-9b75c3ddd77a', 'vsysusdt', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('85cac865-14f0-4c6a-9524-142576ab968e', 'xzcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('869229cf-88f3-45d0-a2a3-c821e6ade035', 'rsrht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('8693e3f8-db99-48af-bcb3-f5577a254927', 'trxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('877a3604-6d49-46dc-95c5-445b8847f4fd', 'newht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('883e7c78-10a4-4e7c-90a4-85a9f429e691', 'hb10usdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('88802549-996d-4526-923a-65fa81626224', 'crebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('89b4e1f5-198a-492c-9a8c-567265afd4bc', 'dogeusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8a3c964a-51e2-48f0-9c57-c2b3c76a2dcd', 'utkbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8ac37c26-bfd2-4c66-a2df-eddf6e2c6671', 'hivebtc', '2020-05-13 10:11:01');
INSERT INTO `pc_quantized_symbols` VALUES ('8b03fe27-36b8-4089-ba9a-7de63ef9a2a8', 'meetbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('8b31bed7-b084-4e27-b310-e55c6b6bb390', 'smtusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8b5aa0fe-450a-46bf-b9c0-e047eb68b6e3', 'mxcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8b69c4f7-2b99-4115-b95a-719954b02ee9', 'zlaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8bc75a59-0eda-428c-b5dc-4a920e2f3ee2', 'fairbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8c07f6de-8b95-4b88-9d17-93cb17c16c57', 'kanusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8c5df066-e0c7-4e50-933d-93081b8cd1aa', 'grsbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8e08a0c8-ce68-4721-9e6f-e144576d03c6', 'manausdt', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('8e1039bb-a08a-4c69-8820-da1b774deb7d', 'daceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8e46cb4c-ac29-4e4b-b1e0-b0c6bb96b976', 'cmtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('8eb6cc22-209a-4936-b344-ed9a64529834', 'salteth', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('8ed89175-adbe-4258-8d6a-ea7ca914b7aa', 'lambeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('8fc89ba8-00a0-41f8-bc7a-ce0a1e833aff', 'ltchusd', '2020-03-09 10:49:58');
INSERT INTO `pc_quantized_symbols` VALUES ('903395b6-5c72-40a4-aa69-a6ef7b0fabaa', 'egtht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('903b3029-7263-4edd-8e90-1444bed751cf', 'yeeusdt', '2020-07-04 09:31:51');
INSERT INTO `pc_quantized_symbols` VALUES ('904cb198-c99e-417c-94e9-f44c53d2ca62', 'fttht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('907f69b0-5f32-4961-a94c-2d8f8ac71ed9', 'uipbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('90bef8b2-53e6-4105-8c7b-696b2d6053fd', 'topcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('90d35891-4596-41a8-97c3-efa6408770c6', 'meeteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('90d7398f-3165-43e1-83c9-5bbf5887eaf6', 'forbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('917de177-0dd3-41de-b798-de6cb14e9ec7', 'xlmbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('91ce3c12-abe8-4f47-8b6b-79b590271406', 'kcashbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('923c1950-5e14-485f-88d6-7bb3c49f6a27', 'dgbbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('92a706ea-a611-445f-bd00-80c079cb1f02', 'lolht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('92d1a1cd-b0c2-4dd6-a975-803dcefc4296', 'gaseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('93a8aef0-a8fd-4305-8fdb-b54c49224064', 'cmtusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('93b333d9-d750-4bc9-b68b-b52cab821bdc', 'rsrbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('93bf9cb4-ad7f-4911-9940-0af3157fc8f8', 'fttbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('93e6d22f-ecdf-4be5-b168-ce1adc0f9b11', 'xzceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('93eab150-53d2-48d8-a5fa-b5e648d8f0ac', 'bifibtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('93f33b47-8df7-4e16-9d13-1e9eeaf3f7ee', 'qspeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9463a9b8-edac-44e3-9b7a-d7dae538afd5', 'gxcusdt', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('9476b1f3-2850-4bac-a47a-075c695f349c', 'docketh', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('95d22aec-c1fb-40f5-956b-079504865639', 'srnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('95f6108b-6d88-4723-a07b-037afc7fd50c', 'hptht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('968e5ede-4bf8-4d60-b565-e08fd3708cee', 'pvtusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('97614553-f13d-403c-8f16-58f8a4f76dea', 'ugasbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('97a52b98-6aef-4ea2-80b3-941a304bc62a', 'lambht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('97f23600-fde8-45fb-8547-bc3f5b323768', 'iostbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('99a777cb-e4b0-4bdb-814a-060444cc2651', 'payeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('99edd105-6a18-4c7f-b3b9-2e7dc3198f75', 'datbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9a252ab8-ee13-4338-bab4-9e8cf971a8d8', 'hptbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9a5c0a1d-06b6-4c27-a177-cf3ba7d086cd', 'cnnsht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9af24c3a-192f-48b6-a0e6-69645ad25e88', 'kcasheth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9b081651-a09a-4045-b827-fd8fb0277371', 'bhdbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9bef8eda-ca3d-4740-989e-694f54ab122d', 'xvgeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('9bfa0c19-eb40-42e5-bda6-5742177323af', 'cvnteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9c1232f1-2f54-4fd6-808b-b5c6b54d5ad8', 'nknht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9d949c12-eee9-4deb-a767-930aa8904021', 'vsysbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('9e4f686c-dbf6-44a3-9ae3-4a3ddf7437d5', 'mtneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9ed87a75-4074-4697-bffa-85f8216b62c7', 'lxtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9f31348a-9a2c-45e9-aba8-cb54a1c31ad9', 'atometh', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('9f655746-f062-42be-b08e-70c409895ceb', 'ocnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('9f9a709c-ddf4-4cd9-8586-2f088efc2f6b', 'buteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a0e923eb-b6ef-4733-bf53-6941c1e77128', 'polyeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a10d0a09-378c-40ce-8694-ebc1b2ea59d1', 'btteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a1621d0e-3ba4-4180-a718-6773b7b34522', 'btchusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a2370d17-2a2f-43d0-b88c-e526467a0ecb', 'butbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a27e7a77-e31f-425d-bacf-d3774f0634fe', 'ektbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a2ef6094-0c01-4c46-815f-7fadde7ecb58', 'icxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a3c57bf7-483d-4df8-b7e3-1fac73d64b8c', 'ruffusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a3ea705d-ddd5-448d-a071-8c923f2ed4d7', 'rvnht', '2020-08-05 20:56:23');
INSERT INTO `pc_quantized_symbols` VALUES ('a4310a3e-20d1-43f8-a7f1-1ca487ad89d0', 'zecusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a44b265f-b1f9-4525-9592-1c1dd4a07a00', 'xlmusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a4cf91a5-766a-4f86-8cb5-7139867a1a5d', 'propybtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a528ae5e-e2e3-43f9-bc06-8a731334683c', 'iicbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a5ddac2d-b62a-4f6a-9d61-54668f220ef2', 'gntbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a6a6edd9-5e42-44e3-b278-2813a1cf33dd', 'boxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a6e38072-e50d-45eb-b6a2-5a2a7c3cefaf', 'eoseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a82c3920-6755-4044-a4ea-b933fbb5fe14', 'kncusdt', '2020-08-05 20:56:22');
INSERT INTO `pc_quantized_symbols` VALUES ('a89ea083-36df-4009-a87d-f018c82f7198', 'dashhusd', '2020-03-09 10:49:58');
INSERT INTO `pc_quantized_symbols` VALUES ('a8b30840-6e9f-43cd-8481-ef0f10d2cb0a', 'elabtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('a91fe242-c3e9-4843-8e71-adf959ec6869', 'pvtbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('a94d73c9-733e-4b58-8586-6ca84f62f8e8', 'mteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('a979904b-d4c9-4474-9913-98f6274b618d', 'waveseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ac2fbc88-8a1c-4d63-a325-ee84e604adfa', 'sntbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('acee64e0-35be-4be4-8760-53f2522c8b49', 'zeneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ad81d433-bc8a-4acd-a906-fc77aa0c5d60', 'maneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ae8930c8-86a3-4d1b-85e6-ded88937a582', 'xtzeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('af0a0759-e457-4087-9b88-2f6b2c30c61c', 'xmreth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('af32f607-5b60-4cee-adad-4106756bfe51', 'algobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b08246bb-3877-4f6d-a48d-8165155637e7', 'dashbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b0b276e1-ff6f-44b6-9967-1ed44106aa58', 'ncasheth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b17fbb0d-18a9-40a8-a1f1-56386739be30', 'lunausdt', '2020-08-05 20:56:20');
INSERT INTO `pc_quantized_symbols` VALUES ('b1f3cc45-dbc9-478a-bb64-6ff4e35dab77', 'adaeth', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('b26d148e-aed9-4d6e-a2e8-115707895fef', 'bttbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b26de2a3-5c6d-4943-bb16-480f93750b2c', 'smtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b3edf11a-0060-4ba2-9927-eba791f342bf', 'wanbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('b46436ee-10dd-43f3-8a19-2fed3a81f0ee', 'ogoht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b47fa16d-59f6-49eb-95bd-e9dab9df8ae3', 'iotabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b4d8066f-1c9f-4828-a1b8-f7f145a5da23', 'xlmeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b4e03c17-0f25-4558-80b5-fb8746a216c5', 'emusdt', '2019-11-18 10:07:09');
INSERT INTO `pc_quantized_symbols` VALUES ('b4e53c33-d7f7-4c2b-b84f-c0373ff266ed', 'ektusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b531c9ea-c01a-42d6-bc3e-7af7507401d6', 'trioeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b63e9b9c-c8a2-47d8-88db-30c11ea6bc28', 'oneusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('b6bebe70-7071-45fd-afe7-1399f514da97', 'rcneth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('b6d40ed0-66ae-47f2-b194-0deb58af05ba', 'emht', '2019-10-30 20:28:19');
INSERT INTO `pc_quantized_symbols` VALUES ('b7119413-c3c3-491f-8773-53dac578d291', 'skmht', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('b71aa85e-9f78-452c-b79a-6d13abcd620e', 'zrxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b7305a81-9faa-4550-93bf-2118182db288', 'bhtusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b7b409b9-76e5-41b4-a4f8-4999c2f18b20', 'bchbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('b860d1cc-2245-4671-aefe-e9d01064d94a', 'lymeth', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('b8e37918-755b-4e18-80c3-7d52809585f3', 'rvnusdt', '2020-08-05 20:56:20');
INSERT INTO `pc_quantized_symbols` VALUES ('b8ebd6d3-b802-4bf1-9c0f-061be724f8b8', 'cnneth', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('ba58ef8c-f6cb-4e85-80da-8309da8adaab', 'abteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('badc5c1d-01c4-4751-a40f-eb44e0e8b3c9', 'bixeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('badcde24-62b1-4ee1-9a4d-e001ad2baa83', 'ncashbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bb72e1c9-ea48-447c-a6f9-e8edcedfe4fe', 'yeeeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bb8556ac-d6da-4d50-a82f-e91ce37586f4', 'veteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bba13657-5b72-4ba1-bc98-e8d672c66f4d', 'hitbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('bbe68031-868f-4ebd-9b48-12dbbc281759', 'egcceth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('bce59a07-ba3a-4d87-96f2-2ed73fdb75af', 'mtnbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('bd249099-fddd-411c-bb52-dcb9efe76a40', 'mcousdt', '2020-08-05 20:56:23');
INSERT INTO `pc_quantized_symbols` VALUES ('bd9b9f3c-37ce-4574-b2ab-94ea94568f8f', 'gntusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bdfa9b92-b631-4e41-97de-ec858a63692e', 'neousdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('be5c56c2-9f7b-4f36-8683-987239609351', 'storjbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('be621a94-a8d1-4e9d-98bb-cccb62b7d1d4', 'getbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('beef8906-703a-46a8-8c2c-a7848491b7d1', 'egtusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('bf5dd17f-68b9-46e7-813a-d67514035404', 'wprbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bf6139ba-3553-4def-8bc0-67850a0875f1', 'ognusdt', '2020-06-24 14:55:22');
INSERT INTO `pc_quantized_symbols` VALUES ('bfc22879-70cf-4281-9c1e-cb1120fefd21', 'osteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bfd5413f-4622-4273-9195-d4c67bd749d3', 'xrpusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('bfd869b8-0d76-4f2a-9a85-d84e9dae15c4', 'adxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('bff5c6c9-f3ec-4cf4-86fe-77b591a42b0d', 'dcreth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c0039c19-2917-41b4-82a9-178f83192e06', 'nodeusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c0540179-bf12-4b4b-bedd-bf77ad5dad65', 'mcoeth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('c120edf7-0341-4a32-aed9-7deaeb58cb4a', 'itceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c272bde8-1c95-44ab-a37e-4c527004bd97', 'algousdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c27df35d-c0f2-4063-b1d6-6e86773c4e7e', 'iiceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c2fdb3a6-884f-4daa-8d6f-846fb9e9b075', 'cdceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c3d5b86c-4d90-4aa0-b623-6f9cbeb77943', 'aacbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c4cba43e-55ed-4a34-b549-f47d888af668', 'paiusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c4e990de-3e71-4231-aedd-ee6e01c8f32f', 'ruffbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c4f695e9-a225-4c86-8de2-1db4f9ead457', 'zilusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('c5338de1-429c-4637-8196-fb5687697c21', 'npxsbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c54bec2d-83c6-46b7-a567-c40cf56aa359', 'steembtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c5de9768-83a0-4087-8153-236e9451470f', 'bttusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c5f17ce9-105f-467b-9152-f9cfe541eb2e', 'qashbtc', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('c60335df-0ff6-4203-97d3-1595e783b86f', 'ycceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c7249f0e-7f3e-4dba-902c-902216184035', 'linketh', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c74c0aa2-7758-41bc-b5ff-2daa5398bcbc', 'grseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c94ae647-1960-4972-a094-ed8417ffdfd6', 'nasusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c96c25ff-019e-4175-beda-a225d55bd87e', 'atpbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('c9d9c3ab-f509-410f-964a-d5e9085b1d02', 'forusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cae8125a-043d-4d0a-af5e-1192a49a61e5', 'gveeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cb8b1279-6079-455c-afe1-3fa40819ea12', 'crousdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cc6948f8-7746-4770-91f8-0bdf02468b29', 'atombtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('cc6f8c2f-3ecb-49f1-9f11-fa042d39dea0', 'lymbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ccb749e9-8281-4b00-b9b6-648bd3e8a266', 'muskbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cce29609-0950-4689-b883-33ffa325d23e', 'snceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cd74c99d-fafe-4488-b831-de413c9636dc', 'dotbtc', '2020-08-05 20:56:22');
INSERT INTO `pc_quantized_symbols` VALUES ('ce1c078e-41fb-491c-a7f0-e11def0f96d4', 'gxcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ce52cfe9-9eb8-40cb-84a1-76dcef044c10', 'seelebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ceb00837-170b-456d-9895-e8827563e625', 'portalbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cebcdf95-63c3-4d97-b451-a3bd36c3a4c0', 'nknbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('cf78e0b8-fd5b-45fb-9272-32f825d2b91a', 'pvtht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cfc81775-23b2-4080-9980-83c387c54aae', 'rteeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('cfce2b4b-ca04-4083-b651-9fef4cf8ea33', 'akrobtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d0c9b9b4-6e9f-4ba9-925d-4ae293d3c20b', 'appceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d0ce1722-ffa5-4671-90d2-12afaf29d0cc', 'venbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d0d1b766-b595-4f5f-a981-1c1145b0fe09', 'datxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d103b724-df63-4731-bdc9-d684429efbc2', 'kcashht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d10be7f9-aba5-45d9-ad78-9e2bd35cbde3', 'loometh', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('d14fc3f6-898f-49a5-b855-531634fe1311', 'gtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d1d20082-62e9-479a-857c-09fcd70034be', 'iostusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d2483ca9-8e27-4dc4-8fe1-ea37d8c1cb69', 'idtbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('d28d11c7-781e-4754-ac09-5f523148fbb4', 'letbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d36fdd09-019b-4c12-8c8a-40bd9b96b005', 'socbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d381b3e2-7b31-491c-8490-facd33886779', 'fttusdt', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('d3824edc-4004-4ee4-ae61-03e27a8b5fe7', 'xzcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d3987024-342e-408b-829e-c57684031598', 'lxtusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d4833b86-3b94-49ff-8c9d-fe6df15b0d40', 'rbtcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d51b65e8-3d30-4729-8784-d3ab35f94f0b', 'etneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d5697fab-ac08-4bb8-9dfa-280e5f67d4a7', 'ftieth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('d576ddd0-c94f-4377-adf3-9e2282752295', 'iosteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d5d16df0-caa2-4719-b08d-9cec7df2ce06', 'hiteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d670a2b6-8f9b-44ee-9cbe-246b385b20c3', 'covabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d6ec266e-acfe-44d7-862e-cbfc74f9bbd1', 'qunbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d6f1ce43-0f2d-4df7-af4b-f6354a0e9784', 'gasbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d7c69356-61cd-4c80-ad9a-9aafd1894555', 'venusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d7ee40d5-bd01-400b-814c-b3c9bb17ff49', 'sheeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d8192e64-114d-4e8e-881d-13e1a2af297d', 'saltbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d878a880-cf95-4b2c-ba3a-c59747d76a52', 'kmdbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d895995a-c37a-4516-a734-a5e9cd854ef5', 'dogeeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('d9665872-4a66-4e98-9a45-f28f3b8e7983', 'iriseth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('da1fa28b-8884-41ef-bd15-da7276d6d40a', 'arpausdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('da65d622-7bd1-433a-a5a5-b306c13c8ce8', 'etcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('daa8ee6d-7d5c-4505-9397-0f79edfc9cd3', 'ctxceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dacaf4d6-f727-4012-a33c-49e904f5a6da', 'covaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('db745194-c29f-4309-8e14-168a00af7845', 'lbaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('db76f371-f4ca-4ca9-bb3a-bcf0cae77081', 'btcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dc06c98b-1b46-49de-80db-6ca39c35ba45', 'sceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dc102771-42c1-4d0d-a3f8-54927fcd387d', 'bixbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dc2e2af3-44d0-4d1a-9960-988ae8ee3751', 'creusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dc3f2a05-4502-4ca5-8e7e-36c4ac0466ba', 'xmxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dcc1d018-c386-43cf-a389-3af08cfefef8', 'lskbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dcd4c6d4-a241-4084-830d-7596bd428833', 'lunabtc', '2020-08-05 20:56:21');
INSERT INTO `pc_quantized_symbols` VALUES ('dd57a0c6-5fae-49c9-8ffc-2d3622b45bda', 'atpht', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('de0b4c56-d712-4df7-9420-ad66d65bec45', 'icxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('de223397-eca9-4084-ba1e-1a442451f009', 'irisusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('de922a0f-fbab-4164-afad-47f000a8495e', 'lambusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dece271c-c590-46dd-a9e8-c96c8c9e825a', 'bhtht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('df5a495a-310b-47b5-86d9-237b6ac0491e', 'powreth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('dfb3fbf6-7ad4-4263-848d-7020fd5f4669', 'bkbteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('dfe72d8b-7c1a-44db-b9d6-2877f9b26673', 'knceth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('dfe87d3c-daa8-44d2-95b7-40f1c2f49477', 'paybtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e0214d1a-5bd0-4b2d-b2d0-1c1c0e7be343', 'aidocbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e043158a-fb37-4096-97c8-262b0c0d7ac2', 'zrxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e0b53ada-4f8d-4a87-ae19-6f3d5b0d6f24', 'xemusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e17b2856-9703-4788-8611-3f099cc0031f', 'utketh', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('e194d95f-60c0-412f-a9c4-93236994348c', 'actbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e198bca4-cb88-4f15-81a5-2055570d1af8', 'btsusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e2a82c89-81d1-45e3-86ec-751f3cec57d2', 'hoteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e2fab1db-6585-4d93-bb4e-643badf22a22', 'itcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e37ecc60-ad33-437b-9a11-bc7bc9e6d6e1', 'dtabtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e4556521-0b9a-4861-940c-9cef66699959', 'elausdt', '2019-10-18 10:58:50');
INSERT INTO `pc_quantized_symbols` VALUES ('e4a18662-3156-40bd-ba63-f05859ab0d1c', 'itcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e51c0ee2-f2b3-4dc7-aa6e-367a03434d01', 'manaeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e54a9f62-7274-4e8d-96a8-4179ad6fec20', 'xembtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e54d7a90-1764-44df-9787-f65b6e428685', 'dockusdt', '2019-10-22 16:01:30');
INSERT INTO `pc_quantized_symbols` VALUES ('e571f938-5034-4a6b-a45f-60f6087ae345', 'ucbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e5bd65b1-00bc-4083-99d8-3c5d61089627', 'batusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e5ce17d8-0496-4497-a793-a582e913a920', 'hiveht', '2020-05-13 10:11:01');
INSERT INTO `pc_quantized_symbols` VALUES ('e6504e34-65ee-448a-a695-66bac3e1dee3', 'ncceth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e6523ccc-8b01-4fef-a387-607bb34e6665', 'linkbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e678741a-f5f7-4d00-bfcb-da3684dc84e4', 'bt2btc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e7335009-f754-405b-9435-442722809fff', 'swftceth', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('e82ed16f-e627-4e6d-aa7f-fd38dd241ae5', 'fsnusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e838f6c4-822f-467e-8da9-f69ad3a48b7a', 'skmusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e85d7841-134b-44cd-9242-778f845c07aa', 'qtumbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('e9c8e503-c7f3-4368-9b73-5e63dfa95956', 'btmusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ea4162ca-e629-441e-b317-85fbba334698', 'wtcusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ea599cc8-f2de-422f-b19f-ba3120d5473a', 'ethhusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ea67f604-8d52-443c-a6d0-1b796337a267', 'edubtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('eb19ff08-7078-4a00-96a5-a46351157c8e', 'dacusdt', '2020-07-04 09:31:51');
INSERT INTO `pc_quantized_symbols` VALUES ('ebf7fd04-de22-4b53-9625-2e3d511044cd', 'lambbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ec08647e-280b-4c13-90e5-e71f4beeecd5', 'ocneth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ecaf1d4a-8257-4290-8a69-8c3fd1a8e6dc', 'adxeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ecd6956b-6f37-44fb-ae16-10ee84b95bd5', 'cnnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ecf30dca-852b-4724-8b3f-73607b2ca863', 'ttusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('edb9a9e1-bcc7-4475-8a13-0148f64fb0a1', 'newusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('edf4ab06-ef52-4b80-8696-1270a77531f8', 'actusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ee1b425b-6a2b-43ac-8bd8-2c3e9f7feffb', 'nanousdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ee29150f-fe90-4600-85eb-054a99d59c64', 'engeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ef66ddd9-62c5-42e4-a66b-9e7f4f63e02f', 'wtcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('efcc31e4-93f2-469c-b3b0-0baa649ef447', 'hthusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f033a1fe-b44a-4aba-8e8d-b58084b9e234', 'adahusd', '2020-05-13 10:11:01');
INSERT INTO `pc_quantized_symbols` VALUES ('f0b67ee7-482f-4e9f-9ffa-0863ecc581cf', 'hcbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f0ed5b00-da79-4508-971f-387c1feba4fd', 'uuuusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f12087b1-f12b-4120-8766-7c26a2078aff', 'bhtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f1e09b43-e1c2-4031-8247-afc9a26dfe82', 'mxht', '2019-10-30 20:28:19');
INSERT INTO `pc_quantized_symbols` VALUES ('f1eeab38-35de-4bdd-ab07-13447d9b1eea', 'nasbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f249db28-9091-458e-8f76-04b07b900529', 'adausdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f2c2c11b-ac8f-4102-9f51-77beb92ee30a', 'arpaht', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f319dec3-29f8-49c9-a3d7-deab29d2224b', 'algoeth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f40d1bf9-c531-4132-bb4b-83b65ad90284', 'sspbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f46c4e4d-caf8-4148-a2ee-a3b5f0cf5bf0', 'thetausdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f4dc901a-9475-4724-957d-5be6e4dc509d', 'gnteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f64b279b-2095-4975-9840-618190d828d8', 'atomusdt', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f69697f0-4c5e-4ff0-a865-0476babbde90', 'onteth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f6d1e6df-4cfd-4157-8282-3215dbadc32e', 'xmxbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f77c9411-c8c8-428b-afbf-3aaa4727b4cc', 'htbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f7f38d24-ec47-48f1-8c35-fb0bb8499e49', 'lunbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f8afda92-41ff-4775-b1a4-c960b80540c1', 'qasheth', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('f99c3898-56a2-4f23-ad4f-82452118b07b', 'wxtbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('fa0407ae-2487-40da-a6b0-7b79e1aff03c', 'aebtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('fa35699d-fe6b-49e0-9cb3-2a798a087e8b', 'renusdt', '2019-10-26 09:57:09');
INSERT INTO `pc_quantized_symbols` VALUES ('fb3f3001-83e6-4d38-9062-756a014b3c31', 'dacbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('fb539f21-14ec-4909-9df8-819c29468449', 'mtlbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('fca29d42-e0c6-40f5-b6d0-0d17bd2b33be', 'usdchusd', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('fd6cce81-9ab0-4305-92a5-22dee8710e99', 'waxbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('fd71f2a6-e960-4a41-b595-89535b7e9952', 'wavesbtc', '2019-10-18 10:58:48');
INSERT INTO `pc_quantized_symbols` VALUES ('fdd721df-6292-4d94-8c8d-2ca235b470e0', 'etnbtc', '2019-10-18 10:58:49');
INSERT INTO `pc_quantized_symbols` VALUES ('ff486e8c-ce07-4b4b-8816-9d37e6712b09', 'appcbtc', '2019-10-18 10:58:48');

-- ----------------------------
-- Table structure for pc_quantized_trading_on
-- ----------------------------
DROP TABLE IF EXISTS `pc_quantized_trading_on`;
CREATE TABLE `pc_quantized_trading_on`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币对-基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币对-二级货币',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否开启（Y是，N否）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pc_quantized_trading_on
-- ----------------------------
INSERT INTO `pc_quantized_trading_on` VALUES ('172cde28-cbe3-45d8-8ab9-cffd1b41ea63', 'trx', 'eth', 'Y', '2019-10-28 15:58:51');
INSERT INTO `pc_quantized_trading_on` VALUES ('5bf95181-2aef-4c7d-8fe2-7c30781dbfb5', 'ltc', 'btc', 'Y', '2019-10-28 15:59:15');
INSERT INTO `pc_quantized_trading_on` VALUES ('75a151c2-b46c-4a0c-bdf1-2e3013daf45f', 'eth', 'usdt', 'Y', '2019-10-28 15:59:32');
INSERT INTO `pc_quantized_trading_on` VALUES ('7b9c2431-420d-4ff9-b3ed-498024ebb5ef', 'eth', 'btc', 'Y', '2019-10-28 15:56:35');
INSERT INTO `pc_quantized_trading_on` VALUES ('7e269357-ae6b-48cf-871a-55f164696186', 'trx', 'usdt', 'Y', '2019-10-28 16:00:49');
INSERT INTO `pc_quantized_trading_on` VALUES ('802bd11d-4fa3-49ee-b940-6369824ff5a7', 'btc', 'usdt', 'Y', '2019-10-28 10:40:00');
INSERT INTO `pc_quantized_trading_on` VALUES ('9341008a-2a75-4a66-95da-3a7b22bf286e', 'eos', 'eth', 'Y', '2019-10-18 16:54:50');
INSERT INTO `pc_quantized_trading_on` VALUES ('adbb2169-6936-452c-836d-d09c82455842', 'trx', 'btc', 'Y', '2019-10-28 15:58:13');
INSERT INTO `pc_quantized_trading_on` VALUES ('cfdeab34-8d91-4768-b012-356cbae20158', 'ltc', 'usdt', 'Y', '2019-10-31 10:49:59');
INSERT INTO `pc_quantized_trading_on` VALUES ('d6803f4a-a814-4976-bf6c-b5149bb66a41', 'eos', 'btc', 'Y', '2019-10-28 15:57:49');
INSERT INTO `pc_quantized_trading_on` VALUES ('e2b75d8b-dcab-45b2-a5a9-17ad23402311', 'eos', 'usdt', 'Y', '2019-10-28 16:00:27');

-- ----------------------------
-- Table structure for pc_u_authentication_apply_log
-- ----------------------------
DROP TABLE IF EXISTS `pc_u_authentication_apply_log`;
CREATE TABLE `pc_u_authentication_apply_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作员id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `apply_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '认证申请信息id',
  `type` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请类型:初级认证(low)，高级认证(high)',
  `apply_result` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核结果:同意(Y),驳回(N)',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '如果是驳回，填写驳回信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户申请审核记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_u_user_list_log
-- ----------------------------
DROP TABLE IF EXISTS `pc_u_user_list_log`;
CREATE TABLE `pc_u_user_list_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作员id',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户黑白名单记录表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pc_u_user_log
-- ----------------------------
DROP TABLE IF EXISTS `pc_u_user_log`;
CREATE TABLE `pc_u_user_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作员id',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容信息',
  `user_id` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户黑白名单记录表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for push_user
-- ----------------------------
DROP TABLE IF EXISTS `push_user`;
CREATE TABLE `push_user`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户Id',
  `client_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端Id',
  `language` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '语种',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_uniqe`(`user_id`) USING BTREE,
  UNIQUE INDEX `client_id_uniqe`(`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wallet_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `wallet_handle_log`;
CREATE TABLE `wallet_handle_log`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户名称',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `data_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作数据标识',
  `before_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前状态',
  `after_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后状态',
  `wallet_addr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作钱包',
  `amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '金额',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
