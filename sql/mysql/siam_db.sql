SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(50) DEFAULT NULL COMMENT '管理员用户名',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `password_salt` varchar(100) DEFAULT NULL COMMENT '密码加盐',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `roles` varchar(128) DEFAULT NULL COMMENT '权限',
  `is_disabled` int(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 0=启用 1=禁用',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=已删除',
  `disabled` int(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 0=启用 1=禁用',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='后台管理员表';

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES ('1', 'admin', '13121860001', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', 'admin', '0', '0', '0', '0', '2019-10-21 15:57:57', '2019-10-21 15:58:00', '2023-11-24 14:39:05');
INSERT INTO `tb_admin` VALUES ('2', 'siam', '13121865386', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', 'siam', '0', '0', '0', '0', '2019-10-21 15:57:57', '2019-10-21 15:58:00', '2023-11-24 15:08:38');

-- ----------------------------
-- Table structure for tb_admin_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_token`;
CREATE TABLE `tb_admin_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `username` varchar(50) DEFAULT NULL COMMENT '管理员用户名',
  `token` varchar(128) NOT NULL COMMENT 'token',
  `type` varchar(5) DEFAULT NULL COMMENT '登陆方式 wap',
  `login_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1226 DEFAULT CHARSET=utf8 COMMENT='管理员登陆token表';

-- ----------------------------
-- Records of tb_admin_token
-- ----------------------------

-- ----------------------------
-- Table structure for tb_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `tb_advertisement`;
CREATE TABLE `tb_advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `image_name` varchar(50) DEFAULT NULL COMMENT '轮播图名称',
  `image_path` varchar(256) DEFAULT NULL COMMENT '轮播图路径',
  `description` varchar(50) DEFAULT NULL COMMENT '说明',
  `type` int(2) DEFAULT '1' COMMENT '轮播图类型 1=首页轮播图 2=菜单页轮播图 3=积分商城页面轮播图 4=分享页面生成美图',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `image_link_url` varchar(256) DEFAULT NULL COMMENT '点击轮播图跳转的链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='广告轮播图表';

-- ----------------------------
-- Records of tb_advertisement
-- ----------------------------
INSERT INTO `tb_advertisement` VALUES ('16', '新用户全场首单品3折', 'data/images/admin/1/deerspot_202010141035.jpg', '新店入驻，新用户全场首单品3折', '1', '1', '', '2020-04-05 06:58:40', '2020-09-22 23:29:31');
INSERT INTO `tb_advertisement` VALUES ('19', '邀请瓜分红包', 'data/images/admin/1/deerspot_1616945420417.png', '', '1', null, '/pages/mine/share/index/index', '2021-03-28 23:31:25', '2021-04-19 00:34:48');
INSERT INTO `tb_advertisement` VALUES ('20', '商城邀请海报', 'data/images/admin/1/deerspot_1616945568711.png', '', '3', null, '/pages/mine/share/index/index', '2021-03-28 23:32:55', '2021-04-19 00:34:43');
INSERT INTO `tb_advertisement` VALUES ('23', '朋友圈转发', 'data/images/admin/1/deerspot_1618144948605.png', '', '4', null, '', '2021-04-11 20:42:29', '2021-04-11 20:42:29');
INSERT INTO `tb_advertisement` VALUES ('24', '宣传海报', 'data/images/admin/1/deerspot_1619338886758.png', '', '3', null, '', '2021-04-25 16:21:29', '2021-04-25 16:21:29');

-- ----------------------------
-- Table structure for tb_appraise
-- ----------------------------
DROP TABLE IF EXISTS `tb_appraise`;
CREATE TABLE `tb_appraise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `appraise_type` int(1) NOT NULL DEFAULT '1' COMMENT '评价类型 1-订单评价，2-店铺评价',
  `content` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '评价内容',
  `images_url` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '图url，用逗号分隔',
  `level` int(1) DEFAULT NULL COMMENT '评价等级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户评价表';

-- ----------------------------
-- Records of tb_appraise
-- ----------------------------

-- ----------------------------
-- Table structure for tb_coupons
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupons`;
CREATE TABLE `tb_coupons` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) DEFAULT NULL COMMENT '优惠卷名称',
  `preferential_type` int(11) DEFAULT NULL COMMENT '优惠类型，1=折扣，2=满减',
  `discount_amount` decimal(10,2) DEFAULT '1.00' COMMENT '折扣额度',
  `limited_price` decimal(10,2) DEFAULT '0.00' COMMENT '满足价格（元，满足该价格才能使用）',
  `reduced_price` decimal(10,2) DEFAULT '0.00' COMMENT '减价额度(元)',
  `description` varchar(500) DEFAULT NULL COMMENT '使用规则描述',
  `valid_type` int(1) NOT NULL DEFAULT '2' COMMENT '时效:1绝对时效（领取后XXX-XXX时间段有效）  2相对时效（领取后N天有效）',
  `valid_start_time` datetime DEFAULT NULL COMMENT '使用开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '使用结束时间',
  `valid_days` int(3) NOT NULL DEFAULT '0' COMMENT '自领取之日起有效天数',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否被删除，0-否，1-是',
  `source` int(2) DEFAULT NULL COMMENT '优惠券发放来源 1=商家中心 2=调度中心',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='优惠卷表';

-- ----------------------------
-- Records of tb_coupons
-- ----------------------------
INSERT INTO `tb_coupons` VALUES ('1', '新人3折卷', '1', '0.30', '0.00', '0.00', '全场饮品一律3折', '2', null, null, '7', '0', '2', '2020-04-27 17:39:40', '2023-11-10 15:26:53');
INSERT INTO `tb_coupons` VALUES ('2', '推荐新人3折卷', '1', '0.30', '0.00', '0.00', '全场饮品一律3折', '2', null, null, '7', '0', '2', '2020-04-27 13:38:48', null);
INSERT INTO `tb_coupons` VALUES ('28', '3.8折单品券', '1', '0.38', '0.00', '0.00', '本优惠券有效期内仅限单品使用', '1', '2020-05-11 00:00:00', '2020-05-31 00:00:00', '0', '1', '1', '2020-05-11 01:26:00', null);
INSERT INTO `tb_coupons` VALUES ('29', '3折单品券', '1', '0.30', '0.00', '0.00', '全场单品可以使用', '1', '2020-05-12 00:00:00', '2020-05-31 00:00:00', '0', '1', '1', '2020-05-12 16:15:22', null);
INSERT INTO `tb_coupons` VALUES ('30', '3折单品券', '1', '0.30', '0.00', '0.00', '仅限全场单品使用', '2', null, null, '7', '1', '1', '2020-05-13 12:29:30', '2020-05-13 12:30:40');
INSERT INTO `tb_coupons` VALUES ('31', '3折单品券', '1', '0.30', '0.00', '0.00', '全场单品使用', '1', '2020-05-12 00:00:00', '2020-05-30 00:00:00', '0', '1', '1', '2020-05-13 12:31:33', null);
INSERT INTO `tb_coupons` VALUES ('32', '3折单品券', '1', '0.30', '0.00', '0.00', '全场单品使用', '1', '2020-05-12 00:00:00', '2020-06-01 00:00:00', '0', '1', '1', '2020-05-13 14:07:43', null);
INSERT INTO `tb_coupons` VALUES ('33', '3.8折单品券', '1', '0.38', '0.00', '0.00', '全场单品使用', '1', '2020-05-13 00:00:00', '2020-05-31 00:00:00', '0', '1', '1', '2020-05-14 09:07:44', null);
INSERT INTO `tb_coupons` VALUES ('34', '3.8折单品券', '1', '0.38', '0.00', '0.00', '全场单品使用', '1', '2020-05-16 00:00:00', '2020-05-31 00:00:00', '0', '1', '1', '2020-05-16 12:00:52', '2020-05-22 11:02:03');
INSERT INTO `tb_coupons` VALUES ('35', '520告白券', '1', '0.35', '0.00', '0.00', '全场单品使用', '1', '2020-05-20 00:00:00', '2020-05-21 00:00:00', '0', '1', '1', '2020-05-20 08:50:31', '2020-05-21 09:27:46');
INSERT INTO `tb_coupons` VALUES ('36', '3.8折单品券', '1', '0.38', '0.00', '0.00', '全场单品使用', '1', '2020-05-22 00:00:00', '2020-05-31 00:00:00', '0', '1', '1', '2020-05-22 11:02:55', '2020-05-30 14:43:02');
INSERT INTO `tb_coupons` VALUES ('37', '4折单品券', '1', '0.40', '0.00', '0.00', '全场单品使用', '1', '2020-05-30 00:00:00', '2020-06-05 00:00:00', '0', '1', '1', '2020-05-30 14:43:59', '2020-06-03 11:31:08');
INSERT INTO `tb_coupons` VALUES ('38', '5.5折单品券', '1', '0.55', '0.00', '0.00', '全场单品使用，邀请好友一起喝奖励3折优惠券哦', '1', '2020-06-03 00:00:00', '2020-06-30 00:00:00', '0', '1', '1', '2020-06-03 11:32:28', '2020-06-15 09:42:24');
INSERT INTO `tb_coupons` VALUES ('39', '5.8折单品券【邀请好友奖励3折券】', '1', '0.58', '0.00', '0.00', '仅限制饮品使用，邀请好友奖励3折优惠券', '1', '2020-06-15 00:00:00', '2020-08-31 00:00:00', '0', '1', '1', '2020-06-15 09:44:00', '2020-06-22 11:04:31');
INSERT INTO `tb_coupons` VALUES ('40', '3.9折节日特惠券（邀请好友获3折优惠券）', '1', '0.39', '0.00', '0.00', '全场单品使用，有效期3天，邀请好友获3折优惠券', '1', '2020-06-22 00:00:00', '2020-06-25 00:00:00', '0', '1', '1', '2020-06-22 11:06:54', '2020-06-27 11:48:02');
INSERT INTO `tb_coupons` VALUES ('41', '4.8折单品券（邀请好友奖励3折优惠券）', '1', '0.48', '0.00', '0.00', '全场单品使用，邀请好友奖励3折优惠券', '1', '2020-06-27 00:00:00', '2020-07-03 00:00:00', '0', '1', '1', '2020-06-27 11:49:31', '2020-07-03 18:40:47');
INSERT INTO `tb_coupons` VALUES ('42', '7.7专场券(邀请好友奖励3折优惠券)', '1', '0.68', '0.00', '0.00', '全场饮品使用(邀请好友奖励3折优惠券)', '1', '2020-07-03 00:00:00', '2020-09-30 00:00:00', '0', '1', '1', '2020-07-03 18:45:03', '2020-08-11 11:41:17');
INSERT INTO `tb_coupons` VALUES ('43', '5.8折商品通用券（邀请好友奖励3折优惠券）', '1', '0.58', '0.00', '0.00', '5.8折商品通用券（邀请好友奖励3折优惠券）', '1', '2020-07-08 00:00:00', '2020-07-31 00:00:00', '0', '1', '1', '2020-07-08 11:34:23', '2020-08-11 11:41:19');
INSERT INTO `tb_coupons` VALUES ('44', '夏日6折饮品畅饮券（邀请好友奖励3折优惠券）', '1', '0.60', '0.00', '0.00', '全场饮品专用（邀请好友奖励3折优惠券）', '1', '2020-07-17 00:00:00', '2020-07-31 00:00:00', '0', '1', '1', '2020-07-17 11:18:13', '2020-08-11 11:41:24');
INSERT INTO `tb_coupons` VALUES ('45', '3.8折单品餐饮券（邀请好友奖励3折优惠券）', '1', '0.38', '0.00', '0.00', '全场单品使用，邀请好友奖励3折优惠券哟', '2', null, null, '7', '1', '1', '2020-08-11 11:42:56', '2020-08-11 12:19:50');
INSERT INTO `tb_coupons` VALUES ('46', '4.8折畅饮券（邀请好友奖励3折优惠券）', '1', '0.48', '0.00', '0.00', '全场饮品4.8折（邀请好友奖励3折优惠券）', '2', null, null, '7', '0', '1', '2020-08-14 11:17:03', null);
INSERT INTO `tb_coupons` VALUES ('47', '5.5折畅饮券', '1', '0.55', '0.00', '0.00', '全场饮品通用（邀请好友奖励3折优惠券）', '2', null, null, '7', '0', '1', '2020-08-17 11:52:33', null);
INSERT INTO `tb_coupons` VALUES ('48', '6.8折畅饮券（邀请好友奖励3折优惠券）', '1', '0.68', '0.00', '0.00', '全场饮品使用（邀请好友奖励3折优惠券）', '2', null, null, '7', '0', '1', '2020-08-18 11:48:10', null);
INSERT INTO `tb_coupons` VALUES ('49', '新用户7折', '1', '0.70', '0.00', '0.00', '1', '1', '2020-10-07 00:00:00', '2020-10-31 00:00:00', '0', '1', '1', '2020-10-07 17:44:56', '2020-10-07 17:48:03');
INSERT INTO `tb_coupons` VALUES ('50', '新店入驻8折', '1', '0.80', '0.00', '0.00', '1', '1', '2020-10-14 00:00:00', '2020-10-31 00:00:00', '0', '1', '1', '2020-10-14 12:52:29', '2020-10-14 12:53:54');
INSERT INTO `tb_coupons` VALUES ('52', '白桃拿铁7折', '1', '0.70', '0.00', '0.00', '1', '1', '2020-10-14 00:00:00', '2020-10-31 00:00:00', '0', '0', '1', '2020-10-14 17:22:54', null);
INSERT INTO `tb_coupons` VALUES ('53', '会员专享折扣-8折', '1', '0.80', '0.00', '0.00', '会员专享折扣券，有效期1年内有效，仅外卖订单使用', '2', null, null, '365', '0', '2', '2021-02-22 10:23:32', '2021-02-22 10:25:31');
INSERT INTO `tb_coupons` VALUES ('54', '会员专享折扣-7折', '1', '0.70', '0.00', '0.00', '会员专享折扣券，有效期1年内有效，仅外卖订单使用', '2', null, null, '365', '0', '2', '2021-02-22 22:15:19', '2021-02-22 22:15:53');
INSERT INTO `tb_coupons` VALUES ('55', '会员专享折扣-9折', '1', '0.90', '0.00', '0.00', '会员专享折扣券，有效期1年内有效，仅外卖订单使用', '2', null, null, '365', '0', '2', '2021-02-22 22:16:27', null);
INSERT INTO `tb_coupons` VALUES ('56', '会员专享折扣-6折', '1', '0.60', '0.00', '0.00', '会员专享折扣券，有效期1年内有效，仅外卖订单使用', '2', null, null, '365', '0', '2', '2021-02-22 22:17:43', null);
INSERT INTO `tb_coupons` VALUES ('57', '会员专享折扣-5折', '1', '0.50', '0.00', '0.00', '会员专享折扣券，有效期1年内有效，仅外卖订单使用', '2', null, null, '365', '0', '2', '2021-02-22 22:22:25', null);
INSERT INTO `tb_coupons` VALUES ('58', '会员专享折扣-4折', '1', '0.40', '0.00', '0.00', '会员专享折扣券，有效期1年内有效，仅外卖订单使用', '2', null, null, '365', '1', '2', '2021-02-22 22:25:01', '2023-11-10 15:28:17');
INSERT INTO `tb_coupons` VALUES ('59', '奶茶季专享券', '1', '0.50', '0.00', '0.00', '外卖专用', '2', null, null, '7', '0', '2', '2021-07-02 12:06:57', null);

-- ----------------------------
-- Table structure for tb_coupons_goods_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupons_goods_relation`;
CREATE TABLE `tb_coupons_goods_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupons_id` int(11) DEFAULT NULL COMMENT '优惠卷id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1458 DEFAULT CHARSET=utf8 COMMENT='优惠卷使用规则对应的固定商品，如优惠卷用于‘美国拿铁’';

-- ----------------------------
-- Records of tb_coupons_goods_relation
-- ----------------------------
INSERT INTO `tb_coupons_goods_relation` VALUES ('206', '1', '28', '2020-05-05 17:30:04');
INSERT INTO `tb_coupons_goods_relation` VALUES ('207', '2', '28', '2020-05-05 17:30:04');
INSERT INTO `tb_coupons_goods_relation` VALUES ('210', '1', '33', '2020-05-05 17:30:04');
INSERT INTO `tb_coupons_goods_relation` VALUES ('211', '2', '33', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('212', '1', '38', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('213', '2', '38', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('214', '1', '39', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('215', '2', '39', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('216', '1', '41', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('217', '2', '41', '2020-05-05 17:30:05');
INSERT INTO `tb_coupons_goods_relation` VALUES ('222', '1', '45', '2020-05-10 17:50:44');
INSERT INTO `tb_coupons_goods_relation` VALUES ('223', '2', '45', '2020-05-10 17:50:44');
INSERT INTO `tb_coupons_goods_relation` VALUES ('224', '1', '46', '2020-05-10 17:58:42');
INSERT INTO `tb_coupons_goods_relation` VALUES ('225', '2', '46', '2020-05-10 17:58:42');
INSERT INTO `tb_coupons_goods_relation` VALUES ('226', '1', '47', '2020-05-10 18:23:24');
INSERT INTO `tb_coupons_goods_relation` VALUES ('227', '2', '47', '2020-05-10 18:23:24');
INSERT INTO `tb_coupons_goods_relation` VALUES ('228', '1', '48', '2020-05-10 18:40:11');
INSERT INTO `tb_coupons_goods_relation` VALUES ('229', '2', '48', '2020-05-10 18:40:11');
INSERT INTO `tb_coupons_goods_relation` VALUES ('230', '1', '49', '2020-05-10 23:53:19');
INSERT INTO `tb_coupons_goods_relation` VALUES ('231', '2', '49', '2020-05-10 23:53:19');
INSERT INTO `tb_coupons_goods_relation` VALUES ('234', '1', '51', '2020-05-11 00:13:56');
INSERT INTO `tb_coupons_goods_relation` VALUES ('235', '2', '51', '2020-05-11 00:13:56');
INSERT INTO `tb_coupons_goods_relation` VALUES ('236', '1', '52', '2020-05-11 12:22:42');
INSERT INTO `tb_coupons_goods_relation` VALUES ('237', '2', '52', '2020-05-11 12:22:42');
INSERT INTO `tb_coupons_goods_relation` VALUES ('238', '1', '53', '2020-05-11 12:39:35');
INSERT INTO `tb_coupons_goods_relation` VALUES ('239', '2', '53', '2020-05-11 12:39:35');
INSERT INTO `tb_coupons_goods_relation` VALUES ('244', '1', '56', '2020-05-12 11:13:42');
INSERT INTO `tb_coupons_goods_relation` VALUES ('245', '2', '56', '2020-05-12 11:13:42');
INSERT INTO `tb_coupons_goods_relation` VALUES ('246', '1', '57', '2020-05-12 17:52:48');
INSERT INTO `tb_coupons_goods_relation` VALUES ('247', '2', '57', '2020-05-12 17:52:48');
INSERT INTO `tb_coupons_goods_relation` VALUES ('248', '1', '58', '2020-05-13 10:23:23');
INSERT INTO `tb_coupons_goods_relation` VALUES ('249', '2', '58', '2020-05-13 10:23:23');
INSERT INTO `tb_coupons_goods_relation` VALUES ('250', '1', '59', '2020-05-13 10:40:30');
INSERT INTO `tb_coupons_goods_relation` VALUES ('251', '2', '59', '2020-05-13 10:40:30');
INSERT INTO `tb_coupons_goods_relation` VALUES ('252', '1', '60', '2020-05-13 11:58:38');
INSERT INTO `tb_coupons_goods_relation` VALUES ('253', '2', '60', '2020-05-13 11:58:38');
INSERT INTO `tb_coupons_goods_relation` VALUES ('317', '1', '61', '2020-05-13 13:07:36');
INSERT INTO `tb_coupons_goods_relation` VALUES ('318', '2', '61', '2020-05-13 13:07:36');
INSERT INTO `tb_coupons_goods_relation` VALUES ('363', '1', '62', '2020-05-15 14:38:01');
INSERT INTO `tb_coupons_goods_relation` VALUES ('364', '2', '62', '2020-05-15 14:38:01');
INSERT INTO `tb_coupons_goods_relation` VALUES ('365', '1', '63', '2020-05-15 14:51:29');
INSERT INTO `tb_coupons_goods_relation` VALUES ('366', '2', '63', '2020-05-15 14:51:29');
INSERT INTO `tb_coupons_goods_relation` VALUES ('367', '1', '64', '2020-05-15 14:55:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('368', '2', '64', '2020-05-15 14:55:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('369', '1', '65', '2020-05-15 14:58:19');
INSERT INTO `tb_coupons_goods_relation` VALUES ('370', '2', '65', '2020-05-15 14:58:19');
INSERT INTO `tb_coupons_goods_relation` VALUES ('396', '1', '66', '2020-05-17 11:36:34');
INSERT INTO `tb_coupons_goods_relation` VALUES ('397', '2', '66', '2020-05-17 11:36:34');
INSERT INTO `tb_coupons_goods_relation` VALUES ('450', '1', '67', '2020-05-20 14:39:21');
INSERT INTO `tb_coupons_goods_relation` VALUES ('451', '2', '67', '2020-05-20 14:39:21');
INSERT INTO `tb_coupons_goods_relation` VALUES ('452', '1', '68', '2020-05-20 14:46:25');
INSERT INTO `tb_coupons_goods_relation` VALUES ('453', '2', '68', '2020-05-20 14:46:25');
INSERT INTO `tb_coupons_goods_relation` VALUES ('510', '1', '69', '2020-05-23 14:31:48');
INSERT INTO `tb_coupons_goods_relation` VALUES ('511', '2', '69', '2020-05-23 14:31:48');
INSERT INTO `tb_coupons_goods_relation` VALUES ('541', '1', '70', '2020-05-25 16:40:04');
INSERT INTO `tb_coupons_goods_relation` VALUES ('542', '2', '70', '2020-05-25 16:40:04');
INSERT INTO `tb_coupons_goods_relation` VALUES ('573', '1', '71', '2020-05-28 21:42:50');
INSERT INTO `tb_coupons_goods_relation` VALUES ('574', '2', '71', '2020-05-28 21:42:50');
INSERT INTO `tb_coupons_goods_relation` VALUES ('575', '1', '72', '2020-05-29 12:15:52');
INSERT INTO `tb_coupons_goods_relation` VALUES ('576', '2', '72', '2020-05-29 12:15:52');
INSERT INTO `tb_coupons_goods_relation` VALUES ('607', '1', '73', '2020-06-01 15:16:31');
INSERT INTO `tb_coupons_goods_relation` VALUES ('608', '2', '73', '2020-06-01 15:16:31');
INSERT INTO `tb_coupons_goods_relation` VALUES ('640', '1', '74', '2020-06-05 10:15:19');
INSERT INTO `tb_coupons_goods_relation` VALUES ('641', '2', '74', '2020-06-05 10:15:19');
INSERT INTO `tb_coupons_goods_relation` VALUES ('706', '1', '75', '2020-06-15 14:33:36');
INSERT INTO `tb_coupons_goods_relation` VALUES ('707', '2', '75', '2020-06-15 14:33:36');
INSERT INTO `tb_coupons_goods_relation` VALUES ('741', '1', '76', '2020-06-17 13:44:00');
INSERT INTO `tb_coupons_goods_relation` VALUES ('742', '2', '76', '2020-06-17 13:44:00');
INSERT INTO `tb_coupons_goods_relation` VALUES ('743', '1', '77', '2020-06-17 14:21:44');
INSERT INTO `tb_coupons_goods_relation` VALUES ('744', '2', '77', '2020-06-17 14:21:44');
INSERT INTO `tb_coupons_goods_relation` VALUES ('780', '1', '78', '2020-06-17 19:19:51');
INSERT INTO `tb_coupons_goods_relation` VALUES ('781', '2', '78', '2020-06-17 19:19:51');
INSERT INTO `tb_coupons_goods_relation` VALUES ('856', '1', '80', '2020-06-22 13:29:57');
INSERT INTO `tb_coupons_goods_relation` VALUES ('857', '2', '80', '2020-06-22 13:29:57');
INSERT INTO `tb_coupons_goods_relation` VALUES ('895', '1', '81', '2020-07-01 14:01:44');
INSERT INTO `tb_coupons_goods_relation` VALUES ('896', '2', '81', '2020-07-01 14:01:44');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1011', '1', '82', '2020-08-04 09:02:54');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1012', '2', '82', '2020-08-04 09:02:54');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1130', '46', '82', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1131', '46', '81', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1132', '46', '80', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1133', '46', '78', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1134', '46', '77', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1135', '46', '76', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1136', '46', '75', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1137', '46', '74', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1138', '46', '73', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1139', '46', '72', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1140', '46', '71', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1141', '46', '70', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1142', '46', '69', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1143', '46', '68', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1144', '46', '67', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1145', '46', '66', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1146', '46', '65', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1147', '46', '64', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1148', '46', '63', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1149', '46', '62', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1150', '46', '61', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1151', '46', '60', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1152', '46', '59', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1153', '46', '58', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1154', '46', '57', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1155', '46', '56', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1156', '46', '53', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1157', '46', '52', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1158', '46', '51', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1159', '46', '49', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1160', '46', '48', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1161', '46', '47', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1162', '46', '46', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1163', '46', '45', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1164', '46', '41', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1165', '46', '39', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1166', '46', '38', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1167', '46', '33', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1168', '46', '28', '2020-08-16 15:10:06');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1169', '47', '82', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1170', '47', '81', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1171', '47', '80', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1172', '47', '78', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1173', '47', '77', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1174', '47', '76', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1175', '47', '75', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1176', '47', '74', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1177', '47', '73', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1178', '47', '72', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1179', '47', '71', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1180', '47', '70', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1181', '47', '69', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1182', '47', '68', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1183', '47', '67', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1184', '47', '66', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1185', '47', '65', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1186', '47', '64', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1187', '47', '63', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1188', '47', '62', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1189', '47', '61', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1190', '47', '60', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1191', '47', '59', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1192', '47', '58', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1193', '47', '57', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1194', '47', '56', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1195', '47', '53', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1196', '47', '52', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1197', '47', '51', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1198', '47', '49', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1199', '47', '48', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1200', '47', '47', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1201', '47', '46', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1202', '47', '45', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1203', '47', '41', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1204', '47', '39', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1205', '47', '38', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1206', '47', '33', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1207', '47', '28', '2020-08-17 11:52:41');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1208', '48', '82', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1209', '48', '81', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1210', '48', '80', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1211', '48', '78', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1212', '48', '77', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1213', '48', '76', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1214', '48', '75', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1215', '48', '74', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1216', '48', '73', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1217', '48', '72', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1218', '48', '71', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1219', '48', '70', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1220', '48', '69', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1221', '48', '68', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1222', '48', '67', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1223', '48', '66', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1224', '48', '65', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1225', '48', '64', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1226', '48', '63', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1227', '48', '62', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1228', '48', '61', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1229', '48', '60', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1230', '48', '59', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1231', '48', '58', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1232', '48', '57', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1233', '48', '56', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1234', '48', '53', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1235', '48', '52', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1236', '48', '51', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1237', '48', '49', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1238', '48', '48', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1239', '48', '47', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1240', '48', '46', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1241', '48', '45', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1242', '48', '41', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1243', '48', '39', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1244', '48', '38', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1245', '48', '33', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1246', '48', '28', '2020-08-18 11:48:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1409', '52', '185', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1410', '52', '184', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1411', '52', '183', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1412', '52', '182', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1413', '52', '181', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1414', '52', '180', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1415', '52', '179', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1416', '52', '178', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1417', '52', '177', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1418', '52', '176', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1419', '52', '175', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1420', '52', '174', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1421', '52', '173', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1422', '52', '172', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1423', '52', '171', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1424', '52', '170', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1425', '52', '169', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1426', '52', '168', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1427', '52', '167', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1428', '52', '166', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1429', '52', '165', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1430', '52', '164', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1431', '52', '163', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1432', '52', '162', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1433', '52', '161', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1434', '52', '160', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1435', '52', '159', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1436', '52', '158', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1437', '52', '157', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1438', '52', '156', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1439', '52', '155', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1440', '52', '154', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1441', '52', '153', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1442', '52', '152', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1443', '52', '151', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1444', '52', '150', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1445', '52', '149', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1446', '52', '148', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1447', '52', '147', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1448', '52', '146', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1449', '52', '145', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1450', '52', '144', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1451', '52', '143', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1452', '52', '142', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1453', '52', '141', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1454', '52', '140', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1455', '52', '139', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1456', '52', '138', '2020-10-14 17:23:16');
INSERT INTO `tb_coupons_goods_relation` VALUES ('1457', '52', '137', '2020-10-14 17:23:16');

-- ----------------------------
-- Table structure for tb_coupons_member_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupons_member_relation`;
CREATE TABLE `tb_coupons_member_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupons_id` int(11) DEFAULT NULL COMMENT '优惠卷id',
  `coupons_name` varchar(100) DEFAULT NULL COMMENT '优惠卷名称',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `start_time` datetime DEFAULT NULL COMMENT '生效时间',
  `end_time` datetime DEFAULT NULL COMMENT '过期时间',
  `is_used` tinyint(1) DEFAULT '0' COMMENT '是否已经使用，0=未使用，1=已使用',
  `is_expired` tinyint(1) DEFAULT '0' COMMENT '是否过期，0=未过期，1=已过期',
  `is_valid` tinyint(1) DEFAULT '1' COMMENT '是否有效，0-否，1-是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19598 DEFAULT CHARSET=utf8 COMMENT='优惠卷用户关联表';

-- ----------------------------
-- Records of tb_coupons_member_relation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_coupons_shop_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_coupons_shop_relation`;
CREATE TABLE `tb_coupons_shop_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupons_id` int(11) DEFAULT NULL COMMENT '优惠卷id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COMMENT='优惠卷使用店铺范围，优惠卷和店铺关系表';

-- ----------------------------
-- Records of tb_coupons_shop_relation
-- ----------------------------
INSERT INTO `tb_coupons_shop_relation` VALUES ('50', '1', '13', '2020-09-15 17:05:56');
INSERT INTO `tb_coupons_shop_relation` VALUES ('51', '2', '13', '2020-09-15 17:05:56');
INSERT INTO `tb_coupons_shop_relation` VALUES ('52', '28', '13', '2020-09-16 00:06:22');
INSERT INTO `tb_coupons_shop_relation` VALUES ('53', '29', '13', '2020-09-16 00:06:22');
INSERT INTO `tb_coupons_shop_relation` VALUES ('54', '30', '13', '2020-09-16 00:06:23');
INSERT INTO `tb_coupons_shop_relation` VALUES ('55', '31', '13', '2020-09-16 00:06:23');
INSERT INTO `tb_coupons_shop_relation` VALUES ('56', '32', '13', '2020-09-16 00:06:23');
INSERT INTO `tb_coupons_shop_relation` VALUES ('57', '33', '13', '2020-09-16 00:06:23');
INSERT INTO `tb_coupons_shop_relation` VALUES ('58', '34', '13', '2020-09-16 00:06:23');
INSERT INTO `tb_coupons_shop_relation` VALUES ('59', '35', '13', '2020-09-16 00:06:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('60', '36', '13', '2020-09-16 00:06:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('61', '37', '13', '2020-09-16 00:06:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('62', '38', '13', '2020-09-16 00:06:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('63', '39', '13', '2020-09-16 00:06:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('64', '40', '13', '2020-09-16 00:06:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('65', '41', '13', '2020-09-16 00:06:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('66', '42', '13', '2020-09-16 00:06:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('67', '43', '13', '2020-09-16 00:06:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('68', '44', '13', '2020-09-16 00:06:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('69', '45', '13', '2020-09-16 00:06:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('70', '46', '13', '2020-09-16 00:06:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('71', '47', '13', '2020-09-16 00:06:26');
INSERT INTO `tb_coupons_shop_relation` VALUES ('72', '48', '13', '2020-09-16 00:06:26');
INSERT INTO `tb_coupons_shop_relation` VALUES ('73', '1', '14', '2020-09-16 16:57:03');
INSERT INTO `tb_coupons_shop_relation` VALUES ('74', '2', '14', '2020-09-16 16:57:04');
INSERT INTO `tb_coupons_shop_relation` VALUES ('75', '1', '15', '2020-09-22 22:11:51');
INSERT INTO `tb_coupons_shop_relation` VALUES ('76', '2', '15', '2020-09-22 22:11:51');
INSERT INTO `tb_coupons_shop_relation` VALUES ('77', '49', '15', '2020-10-07 17:44:56');
INSERT INTO `tb_coupons_shop_relation` VALUES ('78', '1', '16', '2020-10-08 16:19:37');
INSERT INTO `tb_coupons_shop_relation` VALUES ('79', '2', '16', '2020-10-08 16:19:37');
INSERT INTO `tb_coupons_shop_relation` VALUES ('80', '1', '17', '2020-10-09 10:02:53');
INSERT INTO `tb_coupons_shop_relation` VALUES ('81', '2', '17', '2020-10-09 10:02:53');
INSERT INTO `tb_coupons_shop_relation` VALUES ('82', '1', '18', '2020-10-11 19:04:31');
INSERT INTO `tb_coupons_shop_relation` VALUES ('83', '2', '18', '2020-10-11 19:04:31');
INSERT INTO `tb_coupons_shop_relation` VALUES ('84', '50', '15', '2020-10-14 12:52:29');
INSERT INTO `tb_coupons_shop_relation` VALUES ('85', '51', '18', '2020-10-14 16:44:49');
INSERT INTO `tb_coupons_shop_relation` VALUES ('86', '52', '16', '2020-10-14 17:22:54');
INSERT INTO `tb_coupons_shop_relation` VALUES ('87', '1', '19', '2021-02-21 14:57:02');
INSERT INTO `tb_coupons_shop_relation` VALUES ('88', '2', '19', '2021-02-21 14:57:02');
INSERT INTO `tb_coupons_shop_relation` VALUES ('96', '54', '13', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('97', '54', '14', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('98', '54', '15', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('99', '54', '16', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('100', '54', '17', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('101', '54', '18', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('102', '54', '19', '2021-02-22 22:15:19');
INSERT INTO `tb_coupons_shop_relation` VALUES ('103', '55', '13', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('104', '55', '14', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('105', '55', '15', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('106', '55', '16', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('107', '55', '17', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('108', '55', '18', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('109', '55', '19', '2021-02-22 22:16:27');
INSERT INTO `tb_coupons_shop_relation` VALUES ('110', '56', '13', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('111', '56', '14', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('112', '56', '15', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('113', '56', '16', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('114', '56', '17', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('115', '56', '18', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('116', '56', '19', '2021-02-22 22:17:43');
INSERT INTO `tb_coupons_shop_relation` VALUES ('117', '57', '13', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('118', '57', '14', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('119', '57', '15', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('120', '57', '16', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('121', '57', '17', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('122', '57', '18', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('123', '57', '19', '2021-02-22 22:22:25');
INSERT INTO `tb_coupons_shop_relation` VALUES ('124', '58', '13', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('125', '58', '14', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('126', '58', '15', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('127', '58', '16', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('128', '58', '17', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('129', '58', '18', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('130', '58', '19', '2021-02-22 22:25:01');
INSERT INTO `tb_coupons_shop_relation` VALUES ('138', '59', '13', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('139', '59', '14', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('140', '59', '15', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('141', '59', '16', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('142', '59', '17', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('143', '59', '18', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('144', '59', '19', '2021-07-02 12:07:07');
INSERT INTO `tb_coupons_shop_relation` VALUES ('145', '1', '34', '2021-10-21 14:52:18');
INSERT INTO `tb_coupons_shop_relation` VALUES ('146', '2', '34', '2021-10-21 14:52:36');
INSERT INTO `tb_coupons_shop_relation` VALUES ('147', '1', '35', '2021-10-21 16:06:41');
INSERT INTO `tb_coupons_shop_relation` VALUES ('148', '2', '35', '2021-10-21 16:06:44');
INSERT INTO `tb_coupons_shop_relation` VALUES ('149', '1', '36', '2021-10-21 16:44:23');
INSERT INTO `tb_coupons_shop_relation` VALUES ('150', '2', '36', '2021-10-21 16:44:24');
INSERT INTO `tb_coupons_shop_relation` VALUES ('151', '60', '13', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('152', '60', '14', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('153', '60', '15', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('154', '60', '16', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('155', '60', '17', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('156', '60', '18', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('157', '60', '19', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('158', '60', '34', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('159', '60', '35', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('160', '60', '36', '2023-11-10 15:28:09');
INSERT INTO `tb_coupons_shop_relation` VALUES ('161', '53', '13', '2023-11-10 17:13:08');
INSERT INTO `tb_coupons_shop_relation` VALUES ('162', '53', '14', '2023-11-10 17:13:08');
INSERT INTO `tb_coupons_shop_relation` VALUES ('163', '53', '15', '2023-11-10 17:13:08');
INSERT INTO `tb_coupons_shop_relation` VALUES ('164', '53', '16', '2023-11-10 17:13:08');
INSERT INTO `tb_coupons_shop_relation` VALUES ('165', '53', '17', '2023-11-10 17:13:08');
INSERT INTO `tb_coupons_shop_relation` VALUES ('166', '53', '18', '2023-11-10 17:13:08');
INSERT INTO `tb_coupons_shop_relation` VALUES ('167', '53', '19', '2023-11-10 17:13:08');

-- ----------------------------
-- Table structure for tb_courier
-- ----------------------------
DROP TABLE IF EXISTS `tb_courier`;
CREATE TABLE `tb_courier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `realname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `sex` int(1) DEFAULT '0' COMMENT '性别 0=无 1=男 2=女',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_courier
-- ----------------------------

-- ----------------------------
-- Table structure for tb_delivery_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_delivery_address`;
CREATE TABLE `tb_delivery_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `realname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '联系人姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(100) DEFAULT NULL COMMENT '区/县',
  `street` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `is_default` int(1) NOT NULL DEFAULT '0' COMMENT '是否默认收获地址 0=否 1=是',
  `sex` int(1) DEFAULT '0' COMMENT '联系人性别 0=无 1=先生 2=女士',
  `house_number` varchar(100) DEFAULT NULL COMMENT '门牌号',
  `longitude` decimal(18,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,6) DEFAULT NULL COMMENT '纬度',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=612 DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- ----------------------------
-- Records of tb_delivery_address
-- ----------------------------
INSERT INTO `tb_delivery_address` VALUES ('603', '2', '暹罗', '13121865386', '湖南省', '长沙市', '岳麓区', '麓谷小镇', '1', '0', '', '112.885538', '28.232363', '2023-11-19 16:59:03', '2023-11-19 16:59:03');

-- ----------------------------
-- Table structure for tb_full_reduction_rule
-- ----------------------------
DROP TABLE IF EXISTS `tb_full_reduction_rule`;
CREATE TABLE `tb_full_reduction_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(100) DEFAULT NULL COMMENT '规则名称',
  `status` int(11) DEFAULT NULL COMMENT '活动状态，1=开启，2=关闭',
  `limited_price` decimal(10,2) DEFAULT '0.00' COMMENT '满足价格（元，满足该价格才能使用）',
  `reduced_price` decimal(10,2) DEFAULT '0.00' COMMENT '减价额度(元)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='满减规则表';

-- ----------------------------
-- Records of tb_full_reduction_rule
-- ----------------------------
INSERT INTO `tb_full_reduction_rule` VALUES ('11', '13', '满19减5', '1', '19.00', '5.00', '2020-05-12 16:14:25', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('12', '13', '满39减14', '1', '39.00', '14.00', '2020-05-12 16:14:41', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('13', '13', '满9减2', '1', '9.00', '2.00', '2020-05-14 10:14:48', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('29', '13', '满88减30', '1', '88.00', '30.00', '2020-07-06 13:48:12', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('30', '13', '满168减66', '1', '168.00', '66.00', '2020-07-06 13:49:05', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('33', '16', '25减5', '1', '25.00', '5.00', '2020-10-10 13:54:06', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('34', '16', '38减10', '1', '38.00', '10.00', '2020-10-10 13:55:12', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('35', '16', '69减20', '1', '69.00', '20.00', '2020-10-10 13:55:33', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('36', '16', '128减38', '1', '128.00', '38.00', '2020-10-10 13:56:04', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('37', '15', '25减4', '1', '25.00', '4.00', '2020-10-10 13:57:00', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('38', '15', '49减9', '1', '49.00', '9.00', '2020-10-10 13:57:20', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('39', '15', '88减20', '1', '88.00', '20.00', '2020-10-10 13:57:36', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('40', '15', '168减39', '1', '168.00', '39.00', '2020-10-10 13:57:59', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('51', '18', '满25减3', '1', '25.00', '3.00', '2021-07-01 22:00:10', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('52', '18', '满38减8', '1', '38.00', '8.00', '2021-07-01 22:00:24', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('53', '18', '满72减15', '1', '72.00', '15.00', '2021-07-01 22:01:04', null);
INSERT INTO `tb_full_reduction_rule` VALUES ('54', '18', '满109减25', '1', '109.00', '25.00', '2021-07-01 22:01:30', null);

-- ----------------------------
-- Table structure for tb_give_like
-- ----------------------------
DROP TABLE IF EXISTS `tb_give_like`;
CREATE TABLE `tb_give_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `appraise_id` int(11) DEFAULT NULL COMMENT '评价id',
  `reply_id` int(11) DEFAULT NULL COMMENT '回复id',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '点赞类型 1-评价点赞，2-回复点赞',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户点赞表';

-- ----------------------------
-- Records of tb_give_like
-- ----------------------------

-- ----------------------------
-- Table structure for tb_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods`;
CREATE TABLE `tb_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(50) NOT NULL COMMENT '商品名称',
  `main_image` varchar(128) DEFAULT NULL COMMENT '商品主图',
  `sub_images` varchar(1024) DEFAULT NULL COMMENT '商品子图',
  `detail` varchar(1024) DEFAULT NULL COMMENT '商品详情',
  `detail_images` varchar(1024) DEFAULT NULL COMMENT '详情图片',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '一口价',
  `stock` int(11) DEFAULT '0' COMMENT '库存',
  `is_hot` tinyint(1) DEFAULT '0' COMMENT '是否热门',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否新品',
  `status` int(2) DEFAULT '1' COMMENT '状态 1=待上架 2=已上架 3=已下架 4=售罄',
  `is_sale` tinyint(1) unsigned DEFAULT '0' COMMENT '是否开启促销 0-否 1-是',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '折扣价',
  `monthly_sales` int(11) DEFAULT '0' COMMENT '月销量',
  `total_sales` int(11) DEFAULT '0' COMMENT '累计销量',
  `total_comments` int(11) DEFAULT '0' COMMENT '累计评价',
  `preferential_name` varchar(20) DEFAULT NULL COMMENT '优惠名称',
  `packing_charges` decimal(10,2) DEFAULT '0.00' COMMENT '包装费',
  `product_time` decimal(10,2) DEFAULT '0.00' COMMENT '制作时长(分钟)',
  `exchange_points` int(10) DEFAULT NULL COMMENT '兑换商品所需积分数量',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of tb_goods
-- ----------------------------
INSERT INTO `tb_goods` VALUES ('28', '13', '布丁仙草', 'data/images/admin/1/deerspot_1591847965973.jpg', 'data/images/admin/1/deerspot_1591847965973.jpg', '一半都是料', 'data/images/admin/1/deerspot_1591847969280.jpg', '10.00', '-290', '0', '0', '2', '0', '10.00', '0', '637', '0', null, '1.00', '0.00', '6', null, '2020-03-23 11:04:57', '2020-06-17 20:37:32');
INSERT INTO `tb_goods` VALUES ('33', '13', '姜汁撞奶', 'data/images/admin/1/deerspot_1589253795582.jpg', 'data/images/admin/1/deerspot_1589253795582.jpg', '当生姜汁遇见牛奶会发生什么呢', 'data/images/admin/1/deerspot_1589253800285.jpg', '12.00', '-2159', '0', '0', '2', '0', '12.00', '0', '62', '0', null, '1.00', '0.00', '1', null, '2020-03-23 12:41:37', '2020-07-13 15:11:00');
INSERT INTO `tb_goods` VALUES ('38', '13', '柠檬汁', 'data/images/admin/1/deerspot_1590674515926.jpg', 'data/images/admin/1/deerspot_1590674515926.jpg', '口感酸甜，解渴必备', 'data/images/admin/1/deerspot_1590674522457.jpg', '5.00', '227', '0', '0', '2', '0', '5.00', '0', '96', '0', null, '1.00', '0.00', '1', null, '2020-03-24 12:31:28', '2020-05-29 00:20:22');
INSERT INTO `tb_goods` VALUES ('39', '13', '椰果奶茶', 'data/images/admin/1/deerspot_1591616215790.jpg', 'data/images/admin/1/deerspot_1591616215790.jpg', '不详', 'data/images/admin/1/deerspot_1591616221489.jpg', '7.00', '5', '0', '0', '2', '0', '7.00', '0', '217', '0', null, '1.00', '3.00', '12', null, '2020-03-24 12:42:12', '2020-06-08 19:37:03');
INSERT INTO `tb_goods` VALUES ('41', '13', '卡布基诺', 'data/images/admin/1/deerspot_1590563935696.jpg', 'data/images/admin/1/deerspot_1590563935696.jpg', '选用上等咖啡粉和牛奶调制，味香醇厚，', 'data/images/admin/1/deerspot_1590564060451.jpg', '10.00', '2', '0', '0', '2', '0', '10.00', '0', '81', '0', null, '1.00', '0.00', '10', null, '2020-04-05 00:15:13', '2020-05-27 15:21:03');
INSERT INTO `tb_goods` VALUES ('45', '13', '抹茶拿铁', 'data/images/admin/1/deerspot_1591705887087.jpg', 'data/images/admin/1/deerspot_1591705887087.jpg', '抹茶拿铁', 'data/images/admin/1/deerspot_1591705892837.jpg', '8.00', '6', '0', '0', '2', '0', '8.00', '0', '111', '0', null, '1.00', '3.00', '8', null, '2020-05-10 17:50:44', '2020-06-09 20:31:35');
INSERT INTO `tb_goods` VALUES ('46', '13', '金桔柠檬', 'data/images/admin/1/deerspot_1591604902009.jpg', 'data/images/admin/1/deerspot_1591604902009.jpg', '1', 'data/images/admin/1/deerspot_1591604912267.jpg', '9.00', '1', '0', '0', '2', '0', '9.00', '0', '101', '0', null, '1.00', '3.00', '9', null, '2020-05-10 17:58:42', '2020-06-08 16:28:36');
INSERT INTO `tb_goods` VALUES ('47', '13', '蜂蜜柚子', 'data/images/admin/1/deerspot_1591603513492.jpg', 'data/images/admin/1/deerspot_1591603513492.jpg', '蜂蜜柚子', 'data/images/admin/1/deerspot_1591603517951.jpg', '9.00', '2', '0', '0', '2', '0', '9.00', '0', '82', '0', null, '1.00', '3.00', '9', null, '2020-05-10 18:23:23', '2020-06-08 16:05:20');
INSERT INTO `tb_goods` VALUES ('48', '13', '芝士绿颜', 'data/images/admin/1/deerspot_1592548306115.jpg', 'data/images/admin/1/deerspot_1592548306115.jpg', '1', 'data/images/admin/1/deerspot_1592548310080.jpg', '12.00', '1', '0', '0', '2', '0', '12.00', '0', '33', '0', null, '1.00', '5.00', '12', null, '2020-05-10 18:40:11', '2020-06-21 19:24:12');
INSERT INTO `tb_goods` VALUES ('49', '13', '红豆奶茶', 'data/images/admin/1/deerspot_1591616292814.jpg', 'data/images/admin/1/deerspot_1591616292814.jpg', '1', 'data/images/admin/1/deerspot_1591616296803.jpg', '7.00', '3', '0', '0', '2', '0', '7.00', '0', '147', '0', null, '1.00', '3.00', '7', null, '2020-05-10 23:53:19', '2020-06-08 19:38:18');
INSERT INTO `tb_goods` VALUES ('51', '13', '黄金珍奶', 'data/images/admin/3/deerspot_1591719311481.jpg', 'data/images/admin/3/deerspot_1591719311481.jpg', 'Q弹软糯的口感', 'data/images/admin/3/deerspot_1591719314034.jpg', '8.00', '4', '0', '0', '2', '0', '8.00', '0', '246', '0', null, '1.00', '3.00', '8', null, '2020-05-11 00:13:55', '2020-06-10 00:15:16');
INSERT INTO `tb_goods` VALUES ('52', '13', '蓝色海洋', 'data/images/admin/1/deerspot_1591264167859.jpg', 'data/images/admin/1/deerspot_1591264167859.jpg', '1', 'data/images/admin/1/deerspot_1591264191442.jpg', '9.00', '1', '0', '0', '2', '0', '9.00', '0', '83', '0', null, '1.00', '3.00', '9', null, '2020-05-11 12:22:42', '2020-06-04 18:11:13');
INSERT INTO `tb_goods` VALUES ('53', '13', '薄荷苏打', 'data/images/admin/1/deerspot_1591264346926.jpg', 'data/images/admin/1/deerspot_1591264346926.jpg', '口感清新', 'data/images/admin/1/deerspot_1591264350213.jpg', '9.00', '1', '0', '0', '2', '0', '9.00', '0', '39', '0', null, '1.00', '3.00', '9', null, '2020-05-11 12:39:35', '2020-06-04 17:53:16');
INSERT INTO `tb_goods` VALUES ('56', '13', '布丁果冻', 'data/images/admin/3/deerspot_1591720158652.jpg', 'data/images/admin/3/deerspot_1591720158652.jpg', '1', 'data/images/admin/3/deerspot_1591720160926.jpg', '6.00', '0', '0', '0', '4', '0', '6.00', '0', '10', '0', null, '1.00', '3.00', '6', null, '2020-05-12 11:13:42', '2020-07-03 11:01:20');
INSERT INTO `tb_goods` VALUES ('57', '13', '芝士仙草', 'data/images/admin/1/deerspot_1590216480659.jpg', 'data/images/admin/1/deerspot_1590216480659.jpg', '芝士布丁仙草', 'data/images/admin/1/deerspot_1590216544440.jpg', '15.00', '3', '0', '0', '2', '0', '15.00', '0', '259', '0', null, '1.00', '2.00', '15', null, '2020-05-12 17:52:48', '2020-06-04 17:40:09');
INSERT INTO `tb_goods` VALUES ('58', '13', '黑糖小芋圆', 'data/images/admin/1/deerspot_1591622581910.jpg', 'data/images/admin/1/deerspot_1591622581910.jpg', '黑糖小芋圆', 'data/images/admin/1/deerspot_1591622587499.jpg', '10.00', '8', '0', '0', '2', '0', '10.00', '0', '89', '0', null, '1.00', '4.00', '8', null, '2020-05-13 10:23:23', '2020-07-18 14:39:50');
INSERT INTO `tb_goods` VALUES ('59', '13', '椰奶半杯料', 'data/images/admin/3/deerspot_1592749335669.jpg', 'data/images/admin/3/deerspot_1592749335669.jpg', '满满的椰奶香味，喝上半杯满足的味道，真正的零脂肪饮品', 'data/images/admin/3/deerspot_1592749340566.jpg', '10.00', '10', '0', '0', '2', '0', '10.00', '0', '53', '0', null, '1.00', '5.00', '12', null, '2020-05-13 10:40:30', '2020-07-03 18:35:38');
INSERT INTO `tb_goods` VALUES ('60', '13', '鲜果草莓汁', 'data/images/admin/1/deerspot_1590565521257.jpg', 'data/images/admin/1/deerspot_1590565521257.jpg', '浓浓的草莓味道', 'data/images/admin/1/deerspot_1590565528729.jpg', '10.00', '0', '0', '0', '2', '0', '10.00', '0', '44', '0', null, '1.00', '3.00', '10', null, '2020-05-13 11:58:38', '2020-05-27 15:45:49');
INSERT INTO `tb_goods` VALUES ('61', '13', '芝士很草', 'data/images/admin/3/deerspot_1590777231649.jpg', 'data/images/admin/3/deerspot_1590777231649.jpg', '芝士奶茶加上鲜果草莓的味道', 'data/images/admin/3/deerspot_1590777238880.jpg', '15.00', '4', '0', '0', '2', '0', '15.00', '0', '86', '0', null, '1.00', '3.00', '15', null, '2020-05-13 13:07:36', '2020-05-30 02:34:19');
INSERT INTO `tb_goods` VALUES ('62', '13', '鲜果芒果冰冰冻冻', 'data/images/admin/1/deerspot_1591623477034.jpg', 'data/images/admin/1/deerspot_1591623477034.jpg', '浓浓的芒果味道', 'data/images/admin/1/deerspot_1591623480295.jpg', '10.00', '3', '0', '0', '2', '0', '10.00', '0', '74', '0', null, '1.00', '3.00', '10', null, '2020-05-15 14:38:01', '2020-06-08 21:38:02');
INSERT INTO `tb_goods` VALUES ('63', '13', '满杯西瓜', 'data/images/admin/1/deerspot_1592726120158.jpg', 'data/images/admin/1/deerspot_1592726120158.jpg', '选择多汁甘甜西瓜600，杯底含有200g西瓜丁，满满的西瓜味道', 'data/images/admin/1/deerspot_1592726123613.jpg', '12.00', '0', '0', '0', '2', '0', '12.00', '0', '71', '0', null, '1.00', '1.00', '12', null, '2020-05-15 14:51:29', '2020-06-21 15:56:17');
INSERT INTO `tb_goods` VALUES ('64', '13', '鲜果草莓益菌多', 'data/images/admin/1/deerspot_1592548035563.jpg', 'data/images/admin/1/deerspot_1592548035563.jpg', '鲜果草莓益菌多', 'data/images/admin/1/deerspot_1592548073376.jpg', '13.00', '3', '0', '0', '2', '0', '13.00', '0', '138', '0', null, '1.00', '3.00', '10', null, '2020-05-15 14:55:06', '2020-07-15 17:33:09');
INSERT INTO `tb_goods` VALUES ('65', '13', '鲜果彩虹冰冰冻冻', 'data/images/admin/1/deerspot_1589525885573.jpg', 'data/images/admin/1/deerspot_1589525885573.jpg', '芒果  草莓', 'data/images/admin/1/deerspot_1589525896188.jpg', '12.00', '1', '0', '0', '3', '0', '12.00', '0', '16', '0', null, '1.00', '5.00', '12', null, '2020-05-15 14:58:18', '2020-06-21 14:50:06');
INSERT INTO `tb_goods` VALUES ('66', '13', '鲜果芒果益菌多', 'data/images/admin/1/deerspot_1591613166071.jpg', 'data/images/admin/1/deerspot_1591613166071.jpg', '鲜果芒果益菌多', 'data/images/admin/1/deerspot_1591613171654.jpg', '13.00', '3', '0', '0', '2', '0', '13.00', '0', '77', '0', null, '1.00', '3.00', '12', null, '2020-05-17 11:36:34', '2020-07-15 17:32:53');
INSERT INTO `tb_goods` VALUES ('67', '13', '鲜果草莓橙茉波波', 'data/images/admin/1/deerspot_1591605925025.jpg', 'data/images/admin/1/deerspot_1591605925025.jpg,data/images/admin/1/deerspot_1591605945344.jpg', '鲜果草莓橙茉波波', 'data/images/admin/1/deerspot_1591605929805.jpg', '14.00', '4', '0', '0', '2', '0', '14.00', '0', '288', '0', null, '1.00', '5.00', '14', null, '2020-05-20 14:39:21', '2020-06-21 11:32:32');
INSERT INTO `tb_goods` VALUES ('68', '13', '鲜果橙意满满', 'data/images/admin/1/deerspot_1591012263741.jpg', 'data/images/admin/1/deerspot_1591012263741.jpg', '一整颗橙子榨出来的灵魂', 'data/images/admin/1/deerspot_1591012270172.jpg', '12.00', '0', '0', '0', '3', '0', '12.00', '0', '38', '0', null, '1.00', '5.00', '12', null, '2020-05-20 14:46:25', '2020-07-06 13:56:51');
INSERT INTO `tb_goods` VALUES ('69', '13', '芝士很芒', 'data/images/admin/1/deerspot_1592547883421.jpg', 'data/images/admin/1/deerspot_1592547883421.jpg', '鲜芒果汁加上芝士奶盖', 'data/images/admin/1/deerspot_1592547886805.jpg', '15.00', '4', '0', '0', '2', '0', '15.00', '0', '246', '0', null, '1.00', '5.00', '15', null, '2020-05-23 14:31:48', '2020-06-19 14:24:48');
INSERT INTO `tb_goods` VALUES ('70', '13', '百香茉金水果茶', 'data/images/admin/1/deerspot_1591622107370.jpg', 'data/images/admin/1/deerspot_1591622107370.jpg', '西瓜 芒果  百香果 柠檬 金桔 香橙 茉莉绿茶', 'data/images/admin/1/deerspot_1591622110926.jpg', '18.00', '1', '0', '0', '2', '0', '18.00', '0', '56', '0', null, '1.00', '5.00', '18', null, '2020-05-25 16:40:04', '2020-07-02 11:24:16');
INSERT INTO `tb_goods` VALUES ('71', '13', '爆橙百香果', 'data/images/admin/1/deerspot_1590673364361.jpg', 'data/images/admin/1/deerspot_1590673364361.jpg', '口感酸甜，浓缩百香果精华', 'data/images/admin/1/deerspot_1590673367816.jpg', '13.00', '0', '0', '0', '3', '0', '13.00', '0', '10', '0', null, '1.00', '5.00', '13', null, '2020-05-28 21:42:50', '2020-07-03 16:07:53');
INSERT INTO `tb_goods` VALUES ('72', '13', '绿野仙踪', 'data/images/admin/1/deerspot_1591264022633.jpg', 'data/images/admin/1/deerspot_1591264022633.jpg', '青苹果的酸甜口感', 'data/images/admin/1/deerspot_1591264026456.jpg', '9.00', '0', '0', '0', '2', '0', '9.00', '0', '11', '0', null, '1.00', '3.00', '9', null, '2020-05-29 12:15:52', '2020-06-21 19:23:26');
INSERT INTO `tb_goods` VALUES ('73', '13', '芝士桃桃', 'data/images/admin/1/deerspot_1590995783575.jpg', 'data/images/admin/1/deerspot_1590995783575.jpg', '鲜果水蜜桃用茉莉花香绿茶榨汁萃取后加上一点奶盖，口感酸甜', 'data/images/admin/1/deerspot_1590995788964.jpg', '16.00', '90', '0', '0', '2', '0', '16.00', '0', '353', '0', null, '1.00', '5.00', '16', null, '2020-06-01 15:16:30', '2020-06-29 14:16:49');
INSERT INTO `tb_goods` VALUES ('74', '13', '西瓜椰椰', 'data/images/admin/1/deerspot_1591861930820.jpg', 'data/images/admin/1/deerspot_1591861930820.jpg', '鲜果西瓜与椰奶的融合，口感清甜，加上椰果的嚼劲，解暑必备', 'data/images/admin/1/deerspot_1591861934566.jpg', '12.00', '6', '0', '0', '2', '0', '12.00', '0', '519', '0', null, '1.00', '3.00', '12', null, '2020-06-05 10:15:19', '2020-06-17 10:59:57');
INSERT INTO `tb_goods` VALUES ('75', '13', '⭐ ⭐ ⭐ ⭐', 'data/images/admin/1/deerspot_1592202810151.jpg', 'data/images/admin/1/deerspot_1592202810151.jpg', '一颗橙子的霸气', 'data/images/admin/1/deerspot_1592202814877.jpg', '14.00', '2', '0', '0', '3', '0', '14.00', '0', '20', '0', null, '1.00', '4.00', '14', null, '2020-06-15 14:33:36', '2020-09-09 23:32:14');
INSERT INTO `tb_goods` VALUES ('76', '13', '百香果芒果', 'data/images/admin/1/deerspot_1592372635018.jpg', 'data/images/admin/1/deerspot_1592372635018.jpg', '鲜芒果和百香果的交合', 'data/images/admin/1/deerspot_1592372638898.jpg', '14.00', '2', '0', '0', '2', '0', '14.00', '0', '37', '0', null, '1.00', '5.00', '14', null, '2020-06-17 13:44:00', '2020-06-17 13:44:00');
INSERT INTO `tb_goods` VALUES ('77', '13', '柠檬益菌多', 'data/images/admin/1/deerspot_1592374898933.jpg', 'data/images/admin/1/deerspot_1592374898933.jpg', '柠檬的酸的口感', 'data/images/admin/1/deerspot_1592374902583.jpg', '12.00', '1', '0', '0', '2', '0', '12.00', '0', '30', '0', null, '1.00', '5.00', '13', null, '2020-06-17 14:21:44', '2020-07-15 17:32:38');
INSERT INTO `tb_goods` VALUES ('78', '13', '牛芒', 'data/images/admin/1/deerspot_1592392781920.jpg', 'data/images/admin/1/deerspot_1592392781920.jpg', '醇香芒果肉加上脱脂纯牛奶', 'data/images/admin/1/deerspot_1592392785923.jpg', '15.00', '122', '0', '0', '2', '0', '15.00', '0', '888', '0', null, '1.00', '5.00', '15', null, '2020-06-17 19:19:51', '2020-08-04 09:03:54');
INSERT INTO `tb_goods` VALUES ('80', '13', '多肉葡萄', 'data/images/admin/1/deerspot_1592803789993.jpg', 'data/images/admin/1/deerspot_1592803789993.jpg', '选用优质的葡萄原料榨汁，不含任何果酱，鲜果鲜汁', 'data/images/admin/1/deerspot_1592803793702.jpg', '14.00', '0', '0', '0', '3', '0', '14.00', '0', '3', '0', null, '1.00', '5.00', '14', null, '2020-06-22 13:29:57', '2020-07-07 00:13:28');
INSERT INTO `tb_goods` VALUES ('81', '13', '百香果双响炮', 'data/images/admin/1/deerspot_1593583283106.jpg', 'data/images/admin/1/deerspot_1593583283106.jpg,data/images/admin/1/deerspot_1593583302764.jpg', '珍珠和椰果做底料，百香果和绿茶酸甜的口感', 'data/images/admin/1/deerspot_1593583288044.jpg', '14.00', '5', '0', '0', '2', '0', '14.00', '0', '37', '0', null, '1.00', '5.00', '14', null, '2020-07-01 14:01:44', '2020-08-04 09:03:35');
INSERT INTO `tb_goods` VALUES ('82', '13', '奥利奥芝士草莓牛奶', 'data/images/admin/1/deerspot_1596502968273.jpg', 'data/images/admin/1/deerspot_1596502968273.jpg', '牛奶与草莓融合而成的碎冰，盖上一层芝士味道奶盖，撒上奥利奥饼干碎', 'data/images/admin/1/deerspot_1596502971936.jpg', '16.00', '7', '0', '0', '2', '0', '16.00', '0', '204', '0', null, '1.00', '5.00', '16', null, '2020-08-04 09:02:54', '2020-08-04 09:02:54');
INSERT INTO `tb_goods` VALUES ('83', '15', '招牌杨枝甘露（喝前均匀摇一摇）', 'data/images/merchant/20/deerspot_1600790057478.jpg', 'data/images/merchant/20/deerspot_1600790057478.jpg', '当热带芒果混入美味西米喝清新野果\n色彩如织布明霞\n亮澄露中分雪白\n闻之醉而不迷\n饮之纯而不腻', null, '18.90', '0', '0', '0', '2', '0', '0.00', '0', '14', '0', null, '1.00', '0.00', '20', null, '2020-09-22 23:54:20', '2020-10-14 14:37:23');
INSERT INTO `tb_goods` VALUES ('84', '15', '蓝色妖姬（喝前摇一摇）', 'data/images/merchant/20/deerspot_1600790697172.jpg', 'data/images/merchant/20/deerspot_1600790697172.jpg', '爆款蓝色妖姬，清凉又解渴，0糖分添加，美丽容颜纤身佳品，非碳酸饮料哦，怎么喝都不会打隔，到店点的客户非常多', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '12', '0', null, '1.00', '0.00', '15', null, '2020-09-23 00:05:00', '2020-09-23 00:05:00');
INSERT INTO `tb_goods` VALUES ('85', '15', '泰式奥利奥蛋糕奶茶', 'data/images/merchant/20/deerspot_1600791458616.jpg', 'data/images/merchant/20/deerspot_1600791458616.jpg', '41%精选新西兰进口脱脂淡奶\n59%斯里兰卡阿萨姆\n浓情鲜奶\n分享给最亲爱的人\n展现自己的小仙女娇气', null, '17.00', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '17', null, '2020-09-23 00:17:40', '2020-09-23 00:17:40');
INSERT INTO `tb_goods` VALUES ('86', '15', '金桔柠檬', 'data/images/merchant/20/deerspot_1600792148443.jpg', 'data/images/merchant/20/deerspot_1600792148443.jpg', '金桔是刚刚好的甜度\n柠檬是调试好的酸度\n加冰凝固是这一刻的口感\n在冰块还没融化时品尝最佳', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-09-23 00:29:18', '2020-09-23 00:29:31');
INSERT INTO `tb_goods` VALUES ('87', '15', '鲜果芒果奶昔', 'data/images/merchant/20/deerspot_1600792655135.jpg', 'data/images/merchant/20/deerspot_1600792655135.jpg', '果酱酿玉露，只需轻轻一口，\n便被深深的迷住~', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '3', '0', null, '1.00', '0.00', '16', null, '2020-09-23 00:37:37', '2020-09-23 00:37:37');
INSERT INTO `tb_goods` VALUES ('88', '15', '粉红佳人（喝前摇一摇）', 'data/images/merchant/20/deerspot_1600792955123.jpg', 'data/images/merchant/20/deerspot_1600792955123.jpg', '超高的颜值和口感', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '15', null, '2020-09-23 00:42:36', '2020-09-23 00:43:45');
INSERT INTO `tb_goods` VALUES ('89', '15', '草莓奶昔', 'data/images/merchant/20/deerspot_1600819761094.jpg', 'data/images/merchant/20/deerspot_1600819761094.jpg', '果酱琼浆玉露，只需轻轻一口，就会被深深的爱上~', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '3', '0', null, '1.00', '0.00', '15', null, '2020-09-23 08:09:23', '2020-09-23 08:09:23');
INSERT INTO `tb_goods` VALUES ('90', '15', '可可奶昔', 'data/images/merchant/20/deerspot_1600916461441.jpg', 'data/images/merchant/20/deerspot_1600916461441.jpg', '果酱酿玉露，只需轻轻一口，便被深深迷住~', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-09-24 11:01:03', '2020-09-24 11:01:03');
INSERT INTO `tb_goods` VALUES ('91', '15', '妃子笑芝芝', 'data/images/merchant/20/deerspot_1600917065360.jpg', 'data/images/merchant/20/deerspot_1600917065360.jpg', '满满的红心火龙果肉，与奶盖的融合，颜值超级棒！', null, '18.90', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '20', null, '2020-09-24 11:11:08', '2020-10-14 14:38:36');
INSERT INTO `tb_goods` VALUES ('92', '15', '草莓芝芝', 'data/images/merchant/20/deerspot_1601048139177.jpg', 'data/images/merchant/20/deerspot_1601048139177.jpg', '草莓', null, '18.90', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '20', null, '2020-09-25 23:35:40', '2020-10-14 14:38:02');
INSERT INTO `tb_goods` VALUES ('93', '15', '小淘气', 'data/images/merchant/20/deerspot_1602223917034.jpg', 'data/images/merchant/20/deerspot_1602223917034.jpg', '小桃气选用优质水蜜桃，浓浓的水蜜桃味道，搭配奶盖一起喝味道更好哟', null, '18.90', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '20', null, '2020-09-25 23:45:54', '2020-10-14 13:24:07');
INSERT INTO `tb_goods` VALUES ('94', '15', '芒芒芝士', 'data/images/merchant/20/deerspot_1601049012745.jpg', 'data/images/merchant/20/deerspot_1601049012745.jpg', '选用优质小台芒制成沙冰，口感细腻，芒果控的福音！', null, '18.90', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '20', null, '2020-09-25 23:50:14', '2020-10-23 18:39:01');
INSERT INTO `tb_goods` VALUES ('95', '15', '凤梨泡泡柚(喝前摇一摇)', 'data/images/merchant/20/deerspot_1601050220627.jpg', 'data/images/merchant/20/deerspot_1601050220627.jpg', '盛锦的重妍\n华丽的收稍\n凤梨和红柚\n最终粉碎在明媚的通透里\n捧在手心\n凝为香甜', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '15.00', '0.00', '15', null, '2020-09-26 00:10:22', '2020-09-26 00:10:22');
INSERT INTO `tb_goods` VALUES ('96', '15', '小气红西柚', 'data/images/merchant/20/deerspot_1601050533950.jpg', 'data/images/merchant/20/deerspot_1601050533950.jpg', '偶尔也需要一杯小气茶来降温\n晶莹剔透的碎冰里，加满橘色的红西柚\n清新的色调，让人想起记忆中蝉鸣不止的夏天\n一秒平静的内心，带来丝丝的凉意', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-09-26 00:15:35', '2020-10-07 16:57:30');
INSERT INTO `tb_goods` VALUES ('97', '15', '鸡柳', 'data/images/merchant/20/deerspot_1601050850619.jpg', 'data/images/merchant/20/deerspot_1601050850619.jpg', '口感酥脆，好吃到停不下来', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-09-26 00:20:52', '2020-10-14 14:28:39');
INSERT INTO `tb_goods` VALUES ('98', '15', '鸡米花', 'data/images/merchant/20/deerspot_1601051047475.jpg', 'data/images/merchant/20/deerspot_1601051047475.jpg,data/images/merchant/20/deerspot_1601051054900.jpg', '口感酥脆，好吃到停不下来', null, '10.00', '0', '0', '0', '4', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-09-26 00:24:20', '2020-10-14 14:28:30');
INSERT INTO `tb_goods` VALUES ('99', '15', '鱿米花', 'data/images/merchant/20/deerspot_1601051217303.jpg', 'data/images/merchant/20/deerspot_1601051217303.jpg', '口感酥脆，好吃到停不下来', null, '10.00', '0', '0', '0', '4', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-09-26 00:27:19', '2020-10-14 14:28:19');
INSERT INTO `tb_goods` VALUES ('100', '15', '蓝颜知己', 'data/images/merchant/20/deerspot_1602063645167.png', 'data/images/merchant/20/deerspot_1602063645167.png', '1', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '9', '0', null, '1.00', '0.00', '16', null, '2020-10-07 17:40:56', '2020-10-07 17:40:56');
INSERT INTO `tb_goods` VALUES ('101', '15', '茉莉奶绿', 'data/images/merchant/20/deerspot_1602122388382.jpg', 'data/images/merchant/20/deerspot_1602122388382.jpg', '牛奶、红茶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 09:59:50', '2020-10-08 09:59:50');
INSERT INTO `tb_goods` VALUES ('102', '15', '奶霜治愈抹茶', 'data/images/merchant/20/deerspot_1602122561273.jpg', 'data/images/merchant/20/deerspot_1602122561273.jpg', '芝士、抹茶', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-08 10:02:43', '2020-10-08 10:02:43');
INSERT INTO `tb_goods` VALUES ('103', '15', '治愈奶霜可可', 'data/images/merchant/20/deerspot_1602122673636.jpg', 'data/images/merchant/20/deerspot_1602122673636.jpg', '可可、芝士', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-08 10:04:35', '2020-10-08 10:04:35');
INSERT INTO `tb_goods` VALUES ('104', '15', '抹茶三兄弟', 'data/images/merchant/20/deerspot_1602122787090.jpg', 'data/images/merchant/20/deerspot_1602122787090.jpg', '芋圆、红豆', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-08 10:06:28', '2020-10-08 10:06:28');
INSERT INTO `tb_goods` VALUES ('105', '15', '治愈抹茶', 'data/images/merchant/20/deerspot_1602122901246.jpg', 'data/images/merchant/20/deerspot_1602122901246.jpg', '简约的几何，原始的色调', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 10:08:22', '2020-10-08 10:08:22');
INSERT INTO `tb_goods` VALUES ('106', '15', '蜜桃乌龙', 'data/images/merchant/20/deerspot_1602123012605.jpg', 'data/images/merchant/20/deerspot_1602123012605.jpg', '独特的茶香气味', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 10:10:14', '2020-10-08 10:10:14');
INSERT INTO `tb_goods` VALUES ('107', '15', '玫瑰乌龙', 'data/images/merchant/20/deerspot_1602123129857.jpg', 'data/images/merchant/20/deerspot_1602123129857.jpg', '纯粹茶香', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '10.00', '0.00', '10', null, '2020-10-08 10:12:11', '2020-10-08 10:12:11');
INSERT INTO `tb_goods` VALUES ('108', '15', '桂花乌龙', 'data/images/merchant/20/deerspot_1602123180115.jpg', 'data/images/merchant/20/deerspot_1602123180115.jpg', '纯粹茶香', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 10:13:13', '2020-10-08 10:13:13');
INSERT INTO `tb_goods` VALUES ('109', '15', '阿里山四季春', 'data/images/merchant/20/deerspot_1602123305716.jpg', 'data/images/merchant/20/deerspot_1602123305716.jpg', '纯粹茶香', null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2020-10-08 10:15:07', '2020-10-08 10:15:07');
INSERT INTO `tb_goods` VALUES ('110', '15', '九印茉莉', 'data/images/merchant/20/deerspot_1602123467126.jpg', 'data/images/merchant/20/deerspot_1602123467126.jpg', '特选九印茉莉使用100°纯净开水冲泡', null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2020-10-08 10:17:48', '2020-10-08 10:18:01');
INSERT INTO `tb_goods` VALUES ('111', '15', '蜜桃芝士奶霜', 'data/images/merchant/20/deerspot_1602123633454.jpg', 'data/images/merchant/20/deerspot_1602123633454.jpg', '蜜桃乌龙茶汤、芝士', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 10:20:55', '2020-10-08 10:20:55');
INSERT INTO `tb_goods` VALUES ('112', '15', '玫瑰芝士奶霜', 'data/images/merchant/20/deerspot_1602123913432.jpg', 'data/images/merchant/20/deerspot_1602123913432.jpg', '芝士、玫瑰茶汤', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 10:25:15', '2020-10-08 10:25:15');
INSERT INTO `tb_goods` VALUES ('113', '15', '四季春芝士奶霜', 'data/images/merchant/20/deerspot_1602124041203.jpg', 'data/images/merchant/20/deerspot_1602124041203.jpg', '四季春茶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 10:27:22', '2020-10-08 10:27:22');
INSERT INTO `tb_goods` VALUES ('114', '15', '桂花芝士奶霜', 'data/images/merchant/20/deerspot_1602124196624.jpg', 'data/images/merchant/20/deerspot_1602124196624.jpg', '桂花茶汤', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 10:29:58', '2020-10-08 10:29:58');
INSERT INTO `tb_goods` VALUES ('115', '15', '抹茶奶昔', 'data/images/merchant/20/deerspot_1602124429775.jpg', 'data/images/merchant/20/deerspot_1602124429775.jpg', '牛奶', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-08 10:33:51', '2020-10-08 10:33:51');
INSERT INTO `tb_goods` VALUES ('116', '15', '可可奶昔', 'data/images/merchant/20/deerspot_1602124532669.jpg', 'data/images/merchant/20/deerspot_1602124532669.jpg', '芝士、、可可', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-08 10:35:34', '2020-10-08 10:35:34');
INSERT INTO `tb_goods` VALUES ('117', '15', '红柚奶昔', 'data/images/merchant/20/deerspot_1602124673923.jpg', 'data/images/merchant/20/deerspot_1602124673923.jpg', '红柚、芝士', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '16', null, '2020-10-08 10:37:55', '2020-10-08 10:37:55');
INSERT INTO `tb_goods` VALUES ('118', '15', '凤梨奶昔', 'data/images/merchant/20/deerspot_1602124830567.jpg', 'data/images/merchant/20/deerspot_1602124830567.jpg', '凤梨、芝士', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-08 10:40:32', '2020-10-08 10:40:32');
INSERT INTO `tb_goods` VALUES ('119', '15', '双拼奶茶', 'data/images/merchant/20/deerspot_1602125224526.jpg', 'data/images/merchant/20/deerspot_1602125224526.jpg', '红豆', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '12', null, '2020-10-08 10:47:10', '2020-10-08 10:47:10');
INSERT INTO `tb_goods` VALUES ('120', '15', '烧仙草奶茶', 'data/images/merchant/20/deerspot_1602125416032.jpg', 'data/images/merchant/20/deerspot_1602125416032.jpg', '仙草冻、花生碎、葡萄、红豆、野果', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '12', null, '2020-10-08 10:50:17', '2020-10-08 10:50:17');
INSERT INTO `tb_goods` VALUES ('121', '15', '黑糖珍珠奶茶', 'data/images/merchant/20/deerspot_1602125650972.jpg', 'data/images/merchant/20/deerspot_1602125650972.jpg', '红茶、黑糖、珍珠', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '3', '0', null, '1.00', '0.00', '10', null, '2020-10-08 10:54:13', '2020-10-08 10:54:13');
INSERT INTO `tb_goods` VALUES ('122', '15', '红豆奶茶', 'data/images/merchant/20/deerspot_1602125764570.jpg', 'data/images/merchant/20/deerspot_1602125764570.jpg', '红豆、牛奶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 10:56:06', '2020-10-08 10:56:06');
INSERT INTO `tb_goods` VALUES ('123', '15', '布丁奶茶', 'data/images/merchant/20/deerspot_1602125891667.jpg', 'data/images/merchant/20/deerspot_1602125891667.jpg', '布丁、红茶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 10:58:13', '2020-10-08 10:58:13');
INSERT INTO `tb_goods` VALUES ('124', '15', '椰果奶茶', 'data/images/merchant/20/deerspot_1602126013964.jpg', 'data/images/merchant/20/deerspot_1602126013964.jpg', '野果、奶茶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '10', null, '2020-10-08 11:00:16', '2020-10-09 15:47:04');
INSERT INTO `tb_goods` VALUES ('125', '15', '原味奶茶', 'data/images/merchant/20/deerspot_1602126072764.jpg', 'data/images/merchant/20/deerspot_1602126072764.jpg', '红茶', null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '4', '0', null, '1.00', '0.00', '8', null, '2020-10-08 11:01:14', '2020-10-08 11:01:14');
INSERT INTO `tb_goods` VALUES ('126', '15', '四季奶青', 'data/images/merchant/20/deerspot_1602126212777.jpg', 'data/images/merchant/20/deerspot_1602126212777.jpg', '牛奶、红茶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 11:03:34', '2020-10-08 11:03:41');
INSERT INTO `tb_goods` VALUES ('127', '15', '泰式椰子水', 'data/images/merchant/20/deerspot_1602126749503.jpg', 'data/images/merchant/20/deerspot_1602126749503.jpg', '牛奶', null, '17.00', '0', '0', '0', '2', '0', '0.00', '0', '12', '0', null, '1.00', '0.00', '17', null, '2020-10-08 11:12:31', '2020-10-08 11:12:31');
INSERT INTO `tb_goods` VALUES ('128', '15', '百香果双响炮', 'data/images/merchant/20/deerspot_1602126856452.jpg', 'data/images/merchant/20/deerspot_1602126856452.jpg', '百香果、芋圆', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 11:14:22', '2020-10-08 11:14:22');
INSERT INTO `tb_goods` VALUES ('129', '15', '葡萄柚绿茶', 'data/images/merchant/20/deerspot_1602127095763.jpg', 'data/images/merchant/20/deerspot_1602127095763.jpg', '绿茶、西柚', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 11:18:18', '2020-10-08 11:18:18');
INSERT INTO `tb_goods` VALUES ('130', '15', '凤梨小清新', 'data/images/merchant/20/deerspot_1602127224855.jpg', 'data/images/merchant/20/deerspot_1602127224855.jpg', '凤梨、茉莉茶汤', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 11:20:27', '2020-10-08 11:20:27');
INSERT INTO `tb_goods` VALUES ('131', '15', '小气百香果', 'data/images/merchant/20/deerspot_1602127589572.jpg', 'data/images/merchant/20/deerspot_1602127589572.jpg', '百香果、青柠檬', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 11:26:32', '2020-10-08 11:26:32');
INSERT INTO `tb_goods` VALUES ('132', '15', '女朋友益菌多', 'data/images/merchant/20/deerspot_1602128147756.jpg', 'data/images/merchant/20/deerspot_1602128147756.jpg', '西柚', null, '17.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '17', null, '2020-10-08 11:32:38', '2020-10-08 11:35:52');
INSERT INTO `tb_goods` VALUES ('133', '15', '芒果益菌多', 'data/images/merchant/20/deerspot_1602128045830.jpg', 'data/images/merchant/20/deerspot_1602128045830.jpg', '芒果', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-08 11:34:08', '2020-10-08 11:34:08');
INSERT INTO `tb_goods` VALUES ('134', '15', '草莓果啤', 'data/images/merchant/20/deerspot_1602128458739.jpg', 'data/images/merchant/20/deerspot_1602128458739.jpg', '草莓，啤酒', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 11:41:05', '2020-10-08 11:41:05');
INSERT INTO `tb_goods` VALUES ('135', '15', '小气红枣茶', 'data/images/merchant/20/deerspot_1602130161424.jpg', 'data/images/merchant/20/deerspot_1602130161424.jpg', '红枣、全脂牛奶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 12:09:24', '2020-10-08 12:09:24');
INSERT INTO `tb_goods` VALUES ('136', '15', '小气槟榔奶芋', 'data/images/merchant/20/deerspot_1602130280074.jpg', 'data/images/merchant/20/deerspot_1602130280074.jpg', '全脂牛奶、芋头', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 12:11:21', '2020-10-08 12:11:21');
INSERT INTO `tb_goods` VALUES ('137', '16', '奥利奥可可', 'data/images/merchant/21/deerspot_1602150961490.jpg', 'data/images/merchant/21/deerspot_1602150961490.jpg', '奥利奥、芝士、可可', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-08 17:56:03', '2020-10-14 18:00:50');
INSERT INTO `tb_goods` VALUES ('138', '16', '奥利奥芝士奶茶', 'data/images/merchant/21/deerspot_1602151105703.jpg', 'data/images/merchant/21/deerspot_1602151105703.jpg', '奥利奥、芝士、红茶、牛奶、珍珠', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '15', null, '2020-10-08 17:58:27', '2020-10-14 18:00:37');
INSERT INTO `tb_goods` VALUES ('139', '16', '白桃乌龙拿铁', 'data/images/merchant/21/deerspot_1602151262380.jpg', 'data/images/merchant/21/deerspot_1602151262380.jpg', '奶油、白桃茶汤、全脂牛奶', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '4', '0', null, '1.00', '0.00', '15', null, '2020-10-08 18:01:11', '2020-10-14 18:01:20');
INSERT INTO `tb_goods` VALUES ('140', '16', '黑芝麻烧仙草', 'data/images/merchant/21/deerspot_1602151558112.jpg', 'data/images/merchant/21/deerspot_1602151558112.jpg', '黑芝麻', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '15.00', '0.00', '15', null, '2020-10-08 18:05:59', '2020-10-14 18:00:17');
INSERT INTO `tb_goods` VALUES ('141', '16', '经典咖啡', 'data/images/merchant/21/deerspot_1602151634081.jpg', 'data/images/merchant/21/deerspot_1602151634081.jpg', '全脂牛奶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '11', null, '2020-10-08 18:07:15', '2020-10-14 17:59:59');
INSERT INTO `tb_goods` VALUES ('142', '16', '荔枝乌龙拿铁', 'data/images/merchant/21/deerspot_1602151787976.jpg', 'data/images/merchant/21/deerspot_1602151787976.jpg', '奶油核桃碎让人欲罢不能的味道哟!', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '15', null, '2020-10-08 18:09:49', '2020-10-14 18:01:07');
INSERT INTO `tb_goods` VALUES ('143', '16', '黑芝麻芋圆奶茶', 'data/images/merchant/21/deerspot_1602151910531.jpg', 'data/images/merchant/21/deerspot_1602151910531.jpg', '红茶、牛奶、小芋圆', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 18:11:54', '2020-10-14 17:59:44');
INSERT INTO `tb_goods` VALUES ('144', '16', '抹茶玛奇朵', 'data/images/merchant/21/deerspot_1602152084981.jpg', 'data/images/merchant/21/deerspot_1602152084981.jpg', '抹茶、牛奶、芝士', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-08 18:14:51', '2020-10-14 17:59:16');
INSERT INTO `tb_goods` VALUES ('145', '16', '芋泥燕麦奶茶', 'data/images/merchant/21/deerspot_1602152312428.jpg', 'data/images/merchant/21/deerspot_1602152312428.jpg', '芋泥、燕麦', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-08 18:18:33', '2020-10-14 17:59:03');
INSERT INTO `tb_goods` VALUES ('146', '16', '百香果益菌多', 'data/images/merchant/21/deerspot_1602152466696.jpg', 'data/images/merchant/21/deerspot_1602152466696.jpg', '百香果、益菌多、绿茶', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-08 18:21:08', '2020-10-08 18:22:18');
INSERT INTO `tb_goods` VALUES ('147', '16', '百香芒果', 'data/images/merchant/21/deerspot_1602152582424.jpg', 'data/images/merchant/21/deerspot_1602152582424.jpg', '百香果、芒果', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '13', null, '2020-10-08 18:23:04', '2020-10-14 17:58:19');
INSERT INTO `tb_goods` VALUES ('148', '16', '百香果芒果益菌多', 'data/images/merchant/21/deerspot_1602152649293.jpg', 'data/images/merchant/21/deerspot_1602152649293.jpg', '百香果、芒果、益菌多', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-08 18:24:11', '2020-10-08 18:24:11');
INSERT INTO `tb_goods` VALUES ('149', '16', '百香果双响炮', 'data/images/merchant/21/deerspot_1602152830758.jpg', 'data/images/merchant/21/deerspot_1602152830758.jpg', '百香果、珍珠、野果', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 18:27:12', '2020-10-08 18:27:12');
INSERT INTO `tb_goods` VALUES ('150', '16', '布丁奶茶', 'data/images/merchant/21/deerspot_1602152993838.jpg', 'data/images/merchant/21/deerspot_1602152993838.jpg', '布丁、红茶、牛奶', null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '7', null, '2020-10-08 18:29:55', '2020-10-08 18:29:55');
INSERT INTO `tb_goods` VALUES ('151', '16', '奶绿三兄弟', 'data/images/merchant/21/deerspot_1602153131363.jpg', 'data/images/merchant/21/deerspot_1602153131363.jpg', '布丁、西米', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-08 18:32:13', '2020-10-14 17:57:48');
INSERT INTO `tb_goods` VALUES ('152', '16', '草莓波波奶茶', 'data/images/merchant/21/deerspot_1602153275555.jpg', 'data/images/merchant/21/deerspot_1602153275555.jpg', '全职牛奶、西米、草莓果酱', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 18:34:37', '2020-10-14 17:56:04');
INSERT INTO `tb_goods` VALUES ('153', '16', '草莓波波酸奶', 'data/images/merchant/21/deerspot_1602153399584.jpg', 'data/images/merchant/21/deerspot_1602153399584.jpg', '酸奶、草莓酱', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-08 18:36:43', '2020-10-14 17:55:09');
INSERT INTO `tb_goods` VALUES ('154', '16', '草莓波波益菌多', 'data/images/merchant/21/deerspot_1602153528014.jpg', 'data/images/merchant/21/deerspot_1602153528014.jpg', '草莓酱、茉莉绿茶、脆波波', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 18:38:50', '2020-10-14 17:54:39');
INSERT INTO `tb_goods` VALUES ('155', '16', '草莓芋圆冻冻', 'data/images/merchant/21/deerspot_1602153698235.jpg', 'data/images/merchant/21/deerspot_1602153698235.jpg', '草莓、芋圆、茉莉绿茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 18:41:40', '2020-10-14 17:54:14');
INSERT INTO `tb_goods` VALUES ('156', '16', '多肉桃桃', 'data/images/merchant/21/deerspot_1602153915131.jpg', 'data/images/merchant/21/deerspot_1602153915131.jpg', '满杯水蜜桃、口感甜蜜', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 18:45:16', '2020-10-08 18:45:16');
INSERT INTO `tb_goods` VALUES ('157', '16', '粉桃波波酸奶', 'data/images/merchant/21/deerspot_1602154054986.jpg', 'data/images/merchant/21/deerspot_1602154054986.jpg', '水蜜桃、酸奶', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-08 18:47:36', '2020-10-08 18:47:36');
INSERT INTO `tb_goods` VALUES ('158', '16', '蜂蜜柚子茶', 'data/images/merchant/21/deerspot_1602154218902.jpg', 'data/images/merchant/21/deerspot_1602154218902.jpg', '柚子酱、茉莉绿茶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 18:50:20', '2020-10-08 18:50:20');
INSERT INTO `tb_goods` VALUES ('159', '16', '黑糖芋圆奶茶', 'data/images/merchant/21/deerspot_1602154346707.jpg', 'data/images/merchant/21/deerspot_1602154346707.jpg', '黑糖、芋圆、红茶、牛奶', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 18:52:28', '2020-10-08 18:52:28');
INSERT INTO `tb_goods` VALUES ('160', '16', '红豆奶茶', 'data/images/merchant/21/deerspot_1602154480821.jpg', 'data/images/merchant/21/deerspot_1602154480821.jpg', '红豆、红茶、牛奶', null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '7', null, '2020-10-08 18:54:43', '2020-10-08 18:54:43');
INSERT INTO `tb_goods` VALUES ('161', '16', '黄金烤奶', 'data/images/merchant/21/deerspot_1602154597573.jpg', 'data/images/merchant/21/deerspot_1602154597573.jpg', '牛奶、红茶、珍珠', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '7', null, '2020-10-08 18:56:39', '2020-10-14 17:53:30');
INSERT INTO `tb_goods` VALUES ('162', '16', '金观音奶茶', 'data/images/merchant/21/deerspot_1602154779774.jpg', 'data/images/merchant/21/deerspot_1602154779774.jpg', '金观音茶汤、牛奶、珍珠', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-08 18:59:50', '2020-10-14 17:51:50');
INSERT INTO `tb_goods` VALUES ('163', '16', '金桔柠檬', 'data/images/merchant/21/deerspot_1602158264036.jpg', 'data/images/merchant/21/deerspot_1602158264036.jpg', '酸甜的口感清热解暑', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '9', null, '2020-10-08 19:57:46', '2020-10-08 19:57:46');
INSERT INTO `tb_goods` VALUES ('164', '16', '蓝莓波波酸奶', 'data/images/merchant/21/deerspot_1602158370935.jpg', 'data/images/merchant/21/deerspot_1602158370935.jpg', '酸奶、蓝莓酱', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-08 19:59:52', '2020-10-08 19:59:52');
INSERT INTO `tb_goods` VALUES ('165', '16', '蓝莓芋圆冻冻', 'data/images/merchant/21/deerspot_1602158517220.jpg', 'data/images/merchant/21/deerspot_1602158517220.jpg', '蓝莓、芋圆', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:01:59', '2020-10-14 17:50:52');
INSERT INTO `tb_goods` VALUES ('166', '16', '荔枝波波芋圆', 'data/images/merchant/21/deerspot_1602158671268.jpg', 'data/images/merchant/21/deerspot_1602158671268.jpg', '荔枝、芋圆', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:04:33', '2020-10-14 17:49:54');
INSERT INTO `tb_goods` VALUES ('167', '16', '满杯橙子', 'data/images/merchant/21/deerspot_1602158816193.jpg', 'data/images/merchant/21/deerspot_1602158816193.jpg', '橙子、蜂蜜、绿茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 20:06:57', '2020-10-08 20:06:57');
INSERT INTO `tb_goods` VALUES ('168', '16', '满杯烧仙草', 'data/images/merchant/21/deerspot_1602158952994.jpg', 'data/images/merchant/21/deerspot_1602158952994.jpg', '仙草冻、花生碎、葡萄干、珍珠、野果', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-08 20:09:15', '2020-10-08 20:09:15');
INSERT INTO `tb_goods` VALUES ('169', '16', '芒果椰汁西米露', 'data/images/merchant/21/deerspot_1602159276866.jpg', 'data/images/merchant/21/deerspot_1602159276866.jpg', '芒果、椰汁、西米露', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:14:38', '2020-10-14 17:48:33');
INSERT INTO `tb_goods` VALUES ('170', '16', '蜜桃波波益菌多', 'data/images/merchant/21/deerspot_1602159815191.jpg', 'data/images/merchant/21/deerspot_1602159815191.jpg', '水蜜桃、益菌多、茉莉绿茶', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:23:37', '2020-10-08 20:23:37');
INSERT INTO `tb_goods` VALUES ('171', '16', '奶茶三兄弟', 'data/images/merchant/21/deerspot_1602159885270.jpg', 'data/images/merchant/21/deerspot_1602159885270.jpg', '布丁、西米、野果', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-08 20:25:41', '2020-10-14 17:47:49');
INSERT INTO `tb_goods` VALUES ('172', '16', '柠檬水', 'data/images/merchant/21/deerspot_1602160083679.jpg', 'data/images/merchant/21/deerspot_1602160083679.jpg', '柠檬、蜂蜜', null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '6', null, '2020-10-08 20:28:05', '2020-10-14 17:21:41');
INSERT INTO `tb_goods` VALUES ('173', '16', '柠檬益菌多', 'data/images/merchant/21/deerspot_1602160238500.jpg', 'data/images/merchant/21/deerspot_1602160238500.jpg', '柠檬、茉莉绿茶、益菌多', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:30:40', '2020-10-08 20:30:40');
INSERT INTO `tb_goods` VALUES ('174', '16', '葡萄柚益菌多', 'data/images/merchant/21/deerspot_1602160355537.jpg', 'data/images/merchant/21/deerspot_1602160355537.jpg', '葡萄、西柚、益菌多、茉莉绿茶', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:32:37', '2020-10-14 17:47:29');
INSERT INTO `tb_goods` VALUES ('175', '16', '葡萄芋圆冻冻', 'data/images/merchant/21/deerspot_1602160614758.jpg', 'data/images/merchant/21/deerspot_1602160614758.jpg', '葡萄、芋圆、茉莉绿茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:36:56', '2020-10-14 17:46:55');
INSERT INTO `tb_goods` VALUES ('176', '16', '奇异果波波芋圆', 'data/images/merchant/21/deerspot_1602160729047.jpg', 'data/images/merchant/21/deerspot_1602160729047.jpg', '奇异果、芋圆、脆波波', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:38:51', '2020-10-14 17:46:37');
INSERT INTO `tb_goods` VALUES ('177', '16', '酸奶烧仙草', 'data/images/merchant/21/deerspot_1602160851277.jpg', 'data/images/merchant/21/deerspot_1602160851277.jpg', '西米、仙草冻', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-08 20:40:54', '2020-10-14 17:52:10');
INSERT INTO `tb_goods` VALUES ('178', '16', '鲜橙葡萄柚', 'data/images/merchant/21/deerspot_1602161045589.jpg', 'data/images/merchant/21/deerspot_1602161045589.jpg', '红西柚诱人香气，配合橙子香甜', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:44:09', '2020-10-08 20:44:09');
INSERT INTO `tb_goods` VALUES ('179', '16', '鲜橙益菌多', 'data/images/merchant/21/deerspot_1602161200771.jpg', 'data/images/merchant/21/deerspot_1602161200771.jpg', '真果粒，看的见', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:46:42', '2020-10-08 20:46:42');
INSERT INTO `tb_goods` VALUES ('180', '16', '杨枝甘露烧仙草', 'data/images/merchant/21/deerspot_1602161463510.jpg', 'data/images/merchant/21/deerspot_1602161463510.jpg', '西米、杨枝甘露、椰浆、芒果、仙草冻冻', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '13', null, '2020-10-08 20:51:06', '2020-10-14 17:46:02');
INSERT INTO `tb_goods` VALUES ('181', '16', '杨枝甘露酸奶', 'data/images/merchant/21/deerspot_1602161604335.jpg', 'data/images/merchant/21/deerspot_1602161604335.jpg', '西米、西柚、酸奶、芒果、茉莉绿茶', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-08 20:53:26', '2020-10-14 17:45:09');
INSERT INTO `tb_goods` VALUES ('182', '16', '椰果奶茶', 'data/images/merchant/21/deerspot_1602161741171.jpg', 'data/images/merchant/21/deerspot_1602161741171.jpg', '野果、红茶、牛奶', null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '7', null, '2020-10-08 20:55:43', '2020-10-14 17:44:11');
INSERT INTO `tb_goods` VALUES ('183', '16', '小芋圆奶茶', 'data/images/merchant/21/deerspot_1602161866341.jpg', 'data/images/merchant/21/deerspot_1602161866341.jpg', '芋圆', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '9', null, '2020-10-08 20:57:48', '2020-10-10 18:21:06');
INSERT INTO `tb_goods` VALUES ('184', '16', '小芋圆烧仙草', 'data/images/merchant/21/deerspot_1602162092691.jpg', 'data/images/merchant/21/deerspot_1602162092691.jpg', '芋圆、仙草冻、珍珠、花生碎', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-08 21:01:55', '2020-10-08 21:01:55');
INSERT INTO `tb_goods` VALUES ('185', '16', '黄金珍珠奶茶', 'data/images/merchant/21/deerspot_1602162204376.jpg', 'data/images/merchant/21/deerspot_1602162204376.jpg', '牛奶、红茶、珍珠', null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '7', null, '2020-10-08 21:03:34', '2020-10-08 21:03:34');
INSERT INTO `tb_goods` VALUES ('186', '15', '西瓜椰奶', 'data/images/merchant/20/deerspot_1602223067045.jpg', 'data/images/merchant/20/deerspot_1602223067045.jpg', '西瓜、椰奶', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '13', '0', null, '1.00', '0.00', '16', null, '2020-10-09 13:57:49', '2020-10-09 13:57:49');
INSERT INTO `tb_goods` VALUES ('187', '15', '西米西瓜汁', 'data/images/merchant/20/deerspot_1602223302458.jpg', 'data/images/merchant/20/deerspot_1602223302458.jpg', '西瓜、西米', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-09 14:01:44', '2020-10-09 14:01:44');
INSERT INTO `tb_goods` VALUES ('188', '15', '蜜桃桂花酒酿', 'data/images/merchant/20/deerspot_1602223662936.jpg', 'data/images/merchant/20/deerspot_1602223662936.jpg', '水蜜桃、桂花茶', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '6', '0', null, '1.00', '0.00', '16', null, '2020-10-09 14:07:46', '2020-10-09 14:07:46');
INSERT INTO `tb_goods` VALUES ('189', '15', '冰荔沉香', 'data/images/merchant/20/deerspot_1602224085614.jpg', 'data/images/merchant/20/deerspot_1602224085614.jpg', '荔枝', null, '18.90', '0', '0', '0', '2', '0', '0.00', '0', '6', '0', null, '1.00', '0.00', '20', null, '2020-10-09 14:14:47', '2020-10-14 14:37:38');
INSERT INTO `tb_goods` VALUES ('190', '15', '小气阳光橙', 'data/images/merchant/20/deerspot_1602224270930.jpg', 'data/images/merchant/20/deerspot_1602224270930.jpg', '橙子、茉香绿茶', null, '18.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '18', null, '2020-10-09 14:17:53', '2020-10-09 14:17:53');
INSERT INTO `tb_goods` VALUES ('191', '15', '多肉莓莓', 'data/images/merchant/20/deerspot_1602224512630.jpg', 'data/images/merchant/20/deerspot_1602224512630.jpg', '草莓', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-09 14:21:54', '2020-10-09 14:21:54');
INSERT INTO `tb_goods` VALUES ('192', '15', '百香啤酒', 'data/images/merchant/20/deerspot_1602224991782.jpg', 'data/images/merchant/20/deerspot_1602224991782.jpg', '啤酒、百香果', null, '14.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '14', null, '2020-10-09 14:29:53', '2020-10-09 14:29:53');
INSERT INTO `tb_goods` VALUES ('193', '15', '小桃微醺', 'data/images/merchant/20/deerspot_1602225403176.jpg', 'data/images/merchant/20/deerspot_1602225403176.jpg', '水蜜桃', null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2020-10-09 14:36:45', '2020-10-09 14:36:45');
INSERT INTO `tb_goods` VALUES ('194', '15', '金桂飘香', 'data/images/merchant/20/deerspot_1602225775350.jpg', 'data/images/merchant/20/deerspot_1602225775350.jpg', '桂花', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-09 14:42:57', '2020-10-09 14:42:57');
INSERT INTO `tb_goods` VALUES ('195', '15', '老虎斑奶茶', 'data/images/merchant/20/deerspot_1602225931463.jpg', 'data/images/merchant/20/deerspot_1602225931463.jpg', '牛奶、红茶、黑糖、珍珠', null, '17.00', '0', '0', '0', '2', '0', '0.00', '0', '58', '0', null, '1.00', '0.00', '17', null, '2020-10-09 14:45:33', '2020-10-09 14:45:33');
INSERT INTO `tb_goods` VALUES ('196', '15', '男朋友益菌多', 'data/images/merchant/20/deerspot_1602226090625.jpg', 'data/images/merchant/20/deerspot_1602226090625.jpg', '青柠檬、蓝柑', null, '17.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '17', null, '2020-10-09 14:48:12', '2020-10-09 14:48:12');
INSERT INTO `tb_goods` VALUES ('197', '15', '小归薯', 'data/images/merchant/20/deerspot_1602226274881.jpg', 'data/images/merchant/20/deerspot_1602226274881.jpg', '紫薯', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '9', '0', null, '1.00', '0.00', '15', null, '2020-10-09 14:51:17', '2020-10-09 14:51:17');
INSERT INTO `tb_goods` VALUES ('198', '18', '黑糖波波茶', 'data/images/merchant/23/deerspot_1602472553211.jpg', 'data/images/merchant/23/deerspot_1602472553211.jpg', '黑糖、波波、红茶、牛奶', null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '39', '0', null, '1.00', '0.00', '8', null, '2020-10-12 11:15:55', '2020-10-12 11:15:55');
INSERT INTO `tb_goods` VALUES ('199', '18', '姜汁柚子蜜', 'data/images/merchant/23/deerspot_1602472993855.jpg', 'data/images/merchant/23/deerspot_1602472993855.jpg', '小芋圆、绿茶、柚子酱、蜂蜜', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-12 11:23:15', '2021-07-01 18:30:08');
INSERT INTO `tb_goods` VALUES ('200', '18', '脏脏草莓', 'data/images/merchant/23/deerspot_1602777061891.jpg', 'data/images/merchant/23/deerspot_1602777061891.jpg', '草莓', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '7', '0', null, '1.00', '0.00', '10', null, '2020-10-12 11:32:29', '2020-10-16 00:25:20');
INSERT INTO `tb_goods` VALUES ('201', '18', '芝士奶茶', 'data/images/merchant/23/deerspot_1603216451624.jpg', 'data/images/merchant/23/deerspot_1603216451624.jpg', '芝士奶盖、牛奶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-12 12:02:03', '2020-10-21 12:59:48');
INSERT INTO `tb_goods` VALUES ('202', '18', '幽兰拿铁', 'data/images/merchant/23/deerspot_1602475560912.jpg', 'data/images/merchant/23/deerspot_1602475560912.jpg', '奶油、红茶、牛奶', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '5', '0', null, '1.00', '0.00', '15', null, '2020-10-12 12:06:02', '2020-10-12 12:06:02');
INSERT INTO `tb_goods` VALUES ('203', '18', '黑钻奶茶', 'data/images/merchant/23/deerspot_1602475746841.jpg', 'data/images/merchant/23/deerspot_1602475746841.jpg', '红茶、牛奶、黑糖', null, '9.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-12 12:09:09', '2021-07-01 18:30:43');
INSERT INTO `tb_goods` VALUES ('204', '18', '草莓芦荟爽', 'data/images/merchant/23/deerspot_1602475955501.jpg', 'data/images/merchant/23/deerspot_1602475955501.jpg', '草莓、芦荟，绿茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-12 12:12:37', '2020-10-12 12:12:37');
INSERT INTO `tb_goods` VALUES ('205', '18', '冰摇绿妍', 'data/images/merchant/23/deerspot_1602476146225.jpg', 'data/images/merchant/23/deerspot_1602476146225.jpg', '绿茶', null, '6.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '6', null, '2020-10-12 12:15:48', '2021-07-01 18:30:55');
INSERT INTO `tb_goods` VALUES ('206', '18', '金桔柠檬', 'data/images/merchant/23/deerspot_1625112206648.jpg', 'data/images/merchant/23/deerspot_1625112206648.jpg', '金桔、柠檬、绿茶', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '9', null, '2020-10-13 15:04:45', '2021-07-01 18:31:22');
INSERT INTO `tb_goods` VALUES ('207', '18', '百香果益菌多', 'data/images/merchant/23/deerspot_1602572832496.jpg', 'data/images/merchant/23/deerspot_1602572832496.jpg', '百香果、绿茶、益菌多', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-13 15:07:14', '2020-10-13 15:07:14');
INSERT INTO `tb_goods` VALUES ('208', '18', '葡萄柚益菌多', 'data/images/merchant/23/deerspot_1603216077549.jpg', 'data/images/merchant/23/deerspot_1603216077549.jpg', '西柚、益菌多、绿茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '11', '0', null, '1.00', '0.00', '12', null, '2020-10-13 15:11:09', '2020-10-21 01:48:00');
INSERT INTO `tb_goods` VALUES ('209', '18', '水蜜桃益菌多', 'data/images/merchant/23/deerspot_1603215883701.jpg', 'data/images/merchant/23/deerspot_1603215883701.jpg', '水蜜桃、益菌多、绿茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '9', '0', null, '1.00', '0.00', '12', null, '2020-10-13 15:19:48', '2020-10-21 01:44:45');
INSERT INTO `tb_goods` VALUES ('210', '18', '杨枝甘露', 'data/images/merchant/23/deerspot_1603215528332.jpg', 'data/images/merchant/23/deerspot_1603215528332.jpg', '芒果、西米、椰浆', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '3', '0', null, '1.00', '0.00', '15', null, '2020-10-13 15:23:42', '2020-10-21 01:38:52');
INSERT INTO `tb_goods` VALUES ('211', '18', '百香果双响炮', 'data/images/merchant/23/deerspot_1603215184233.jpg', 'data/images/merchant/23/deerspot_1603215184233.jpg', '百香果、珍珠、椰果、绿茶', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '12', null, '2020-10-13 15:26:16', '2021-07-01 18:27:16');
INSERT INTO `tb_goods` VALUES ('212', '18', '宜兰青桔茉莉', 'data/images/merchant/23/deerspot_1602777706179.jpg', 'data/images/merchant/23/deerspot_1602777706179.jpg', '金桔、青柠', null, '9.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-13 15:29:04', '2021-07-01 18:27:28');
INSERT INTO `tb_goods` VALUES ('213', '18', '柠檬汁', 'data/images/merchant/23/deerspot_1602574285494.jpg', 'data/images/merchant/23/deerspot_1602574285494.jpg', '柠檬', null, '5.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '5', null, '2020-10-13 15:31:29', '2021-07-01 18:27:45');
INSERT INTO `tb_goods` VALUES ('214', '18', '芒果西米露', 'data/images/merchant/23/deerspot_1603215738039.jpg', 'data/images/merchant/23/deerspot_1603215738039.jpg', '芒果、西米', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-13 15:35:08', '2020-10-21 01:42:19');
INSERT INTO `tb_goods` VALUES ('215', '18', '芋圆葡萄冻冻', 'data/images/merchant/23/deerspot_1602574686668.jpg', 'data/images/merchant/23/deerspot_1602574686668.jpg', '芋圆、葡萄', null, '13.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2020-10-13 15:38:08', '2021-07-01 18:28:08');
INSERT INTO `tb_goods` VALUES ('216', '18', '豆乳波波', 'data/images/merchant/23/deerspot_1602574873506.jpg', 'data/images/merchant/23/deerspot_1602574873506.jpg', '豆乳', null, '11.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '11', null, '2020-10-13 15:41:15', '2021-07-01 18:28:42');
INSERT INTO `tb_goods` VALUES ('217', '18', '元气烤奶', 'data/images/merchant/23/deerspot_1602575085184.jpg', 'data/images/merchant/23/deerspot_1602575085184.jpg', '烤奶、黑糖', null, '9.00', '0', '0', '0', '3', '0', '0.00', '0', '6', '0', null, '1.00', '0.00', '9', null, '2020-10-13 15:44:47', '2021-07-01 18:28:33');
INSERT INTO `tb_goods` VALUES ('218', '18', '海洋之心', 'data/images/merchant/23/deerspot_1602575450732.jpg', 'data/images/merchant/23/deerspot_1602575450732.jpg', '蓝柑、青柠', null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2020-10-13 15:51:47', '2020-10-13 15:51:47');
INSERT INTO `tb_goods` VALUES ('219', '18', '芝芝桃桃', 'data/images/merchant/23/deerspot_1602775589231.jpg', 'data/images/merchant/23/deerspot_1602775589231.jpg', '水蜜桃', null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '11', '0', null, '1.00', '0.00', '13', null, '2020-10-13 16:02:15', '2020-10-15 23:26:31');
INSERT INTO `tb_goods` VALUES ('220', '18', '满杯橙橙', 'data/images/merchant/23/deerspot_1602778137566.jpg', 'data/images/merchant/23/deerspot_1602778137566.jpg', '橙子', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-13 16:04:51', '2021-07-01 18:28:59');
INSERT INTO `tb_goods` VALUES ('221', '18', '红豆奶茶', 'data/images/merchant/23/deerspot_1602576964675.jpg', 'data/images/merchant/23/deerspot_1602576964675.jpg', '红豆', null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '7', null, '2020-10-13 16:16:09', '2020-10-13 16:16:09');
INSERT INTO `tb_goods` VALUES ('222', '18', '红颜佳荔', 'data/images/merchant/23/deerspot_1602577219121.jpg', 'data/images/merchant/23/deerspot_1602577219121.jpg', '绿茶', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '12', null, '2020-10-13 16:20:20', '2021-07-01 18:29:09');
INSERT INTO `tb_goods` VALUES ('223', '18', '柠檬益菌多', 'data/images/merchant/23/deerspot_1602577403784.jpg', 'data/images/merchant/23/deerspot_1602577403784.jpg', '柠檬、益菌多', null, '10.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-13 16:23:25', '2021-07-01 18:29:44');
INSERT INTO `tb_goods` VALUES ('224', '18', '鲜奶可可', 'data/images/merchant/23/deerspot_1602776161295.jpg', 'data/images/merchant/23/deerspot_1602776161295.jpg', '可可、奶盖', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2020-10-15 23:36:02', '2021-07-01 18:29:27');
INSERT INTO `tb_goods` VALUES ('225', '18', '红豆抹茶', 'data/images/merchant/23/deerspot_1602776495792.jpg', 'data/images/merchant/23/deerspot_1602776495792.jpg', '抹茶、红豆', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-15 23:41:37', '2020-10-15 23:41:37');
INSERT INTO `tb_goods` VALUES ('226', '18', '脏脏芒果', 'data/images/merchant/23/deerspot_1602779000259.jpg', 'data/images/merchant/23/deerspot_1602779000259.jpg', '芒果', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '1', '0', null, '1.00', '0.00', '10', null, '2020-10-16 00:23:21', '2020-10-16 00:23:21');
INSERT INTO `tb_goods` VALUES ('227', '18', '招牌烧仙草', 'data/images/merchant/23/deerspot_1603216685230.jpg', 'data/images/merchant/23/deerspot_1603216685230.jpg', '仙草冻、椰果、红豆、花生、珍珠、葡萄', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-21 01:58:07', '2020-10-21 01:58:07');
INSERT INTO `tb_goods` VALUES ('228', '18', '双拼奶茶', 'data/images/merchant/23/deerspot_1603216842236.jpg', 'data/images/merchant/23/deerspot_1603216842236.jpg', '两种底料任选哦', null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2020-10-21 02:00:43', '2020-10-21 02:00:43');
INSERT INTO `tb_goods` VALUES ('229', '18', '桂圆红枣奶茶', 'data/images/merchant/23/deerspot_1603255419132.jpg', 'data/images/merchant/23/deerspot_1603255419132.jpg', '桂圆、红枣、奶茶', null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '5', '0', null, '1.00', '0.00', '12', null, '2020-10-21 12:43:40', '2020-10-21 12:43:40');
INSERT INTO `tb_goods` VALUES ('230', '18', '元气燕麦烤玉米', 'data/images/merchant/23/deerspot_1603255609232.jpg', 'data/images/merchant/23/deerspot_1603255609232.jpg', '燕麦、奶茶', null, '12.00', '0', '0', '0', '3', '0', '0.00', '0', '18', '0', null, '1.00', '0.00', '12', null, '2020-10-21 12:46:51', '2021-07-01 18:26:57');
INSERT INTO `tb_goods` VALUES ('231', '18', '芝士莓莓', 'data/images/merchant/23/deerspot_1603256188313.jpg', 'data/images/merchant/23/deerspot_1603256188313.jpg', '水蜜桃、草莓', null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2020-10-21 12:56:30', '2021-06-28 22:32:25');
INSERT INTO `tb_goods` VALUES ('232', '18', '经典醇香咖啡', 'data/images/merchant/23/deerspot_1603256771145.jpg', 'data/images/merchant/23/deerspot_1603256771145.jpg', '咖啡豆', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-21 13:06:13', '2020-10-21 13:06:13');
INSERT INTO `tb_goods` VALUES ('233', '18', '卡布基诺', 'data/images/merchant/23/deerspot_1603256951398.jpg', 'data/images/merchant/23/deerspot_1603256951398.jpg', '咖啡豆', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-21 13:09:13', '2020-10-21 13:09:13');
INSERT INTO `tb_goods` VALUES ('234', '18', '拿铁', 'data/images/merchant/23/deerspot_1603257063587.jpg', 'data/images/merchant/23/deerspot_1603257063587.jpg', '咖啡豆', null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2020-10-21 13:11:05', '2020-10-21 13:11:05');
INSERT INTO `tb_goods` VALUES ('235', '18', '芒果双皮奶', 'data/images/merchant/23/deerspot_1603257583612.jpg', 'data/images/merchant/23/deerspot_1603257583612.jpg', '芒果双皮奶', null, '5.00', '0', '0', '0', '2', '0', '0.00', '0', '2', '0', null, '1.00', '0.00', '6', null, '2020-10-21 13:19:46', '2021-07-01 20:50:47');
INSERT INTO `tb_goods` VALUES ('237', '19', '原味奶茶', 'data/images/merchant/24/deerspot_1615789977715.png', 'data/images/merchant/24/deerspot_1615789977715.png', null, null, '6.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '6', null, '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods` VALUES ('239', '19', '琥珀珍珠奶茶', 'data/images/merchant/24/deerspot_1619353829385.png', 'data/images/merchant/24/deerspot_1619353829385.png', null, null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2021-04-12 21:53:15', '2021-04-25 20:30:31');
INSERT INTO `tb_goods` VALUES ('240', '19', '椰果奶茶', 'data/images/merchant/24/deerspot_1619353804464.png', 'data/images/merchant/24/deerspot_1619353804464.png', null, null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2021-04-12 22:01:33', '2021-04-25 20:30:06');
INSERT INTO `tb_goods` VALUES ('241', '19', '抹茶奶绿', 'data/images/merchant/24/deerspot_1619353761590.png', 'data/images/merchant/24/deerspot_1619353761590.png', null, null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '7', null, '2021-04-25 19:02:49', '2021-04-25 20:29:23');
INSERT INTO `tb_goods` VALUES ('242', '19', '抹茶珍珠', 'data/images/merchant/24/deerspot_1619353743852.png', 'data/images/merchant/24/deerspot_1619353743852.png', null, null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2021-04-25 19:08:10', '2021-04-25 20:29:06');
INSERT INTO `tb_goods` VALUES ('243', '19', '红豆抹抹茶', 'data/images/merchant/24/deerspot_1619353723356.png', 'data/images/merchant/24/deerspot_1619353723356.png', null, null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2021-04-25 19:10:48', '2021-04-25 20:28:45');
INSERT INTO `tb_goods` VALUES ('244', '19', '满杯烧仙草', 'data/images/merchant/24/deerspot_1619353687255.png', 'data/images/merchant/24/deerspot_1619353687255.png', null, null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2021-04-25 19:16:20', '2021-04-25 20:28:09');
INSERT INTO `tb_goods` VALUES ('245', '19', '奶茶三兄弟', 'data/images/merchant/24/deerspot_1619353669403.png', 'data/images/merchant/24/deerspot_1619353669403.png', null, null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2021-04-25 19:36:30', '2021-04-25 20:27:51');
INSERT INTO `tb_goods` VALUES ('246', '19', '红豆双皮奶', 'data/images/merchant/24/deerspot_1619353634474.png', 'data/images/merchant/24/deerspot_1619353634474.png', null, null, '6.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '6', null, '2021-04-25 19:40:48', '2021-04-25 20:27:16');
INSERT INTO `tb_goods` VALUES ('247', '19', '椰果双皮奶', 'data/images/merchant/24/deerspot_1619353619521.png', 'data/images/merchant/24/deerspot_1619353619521.png', null, null, '6.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '6', null, '2021-04-25 19:54:34', '2021-04-25 20:27:01');
INSERT INTO `tb_goods` VALUES ('248', '19', '茉莉奶绿', 'data/images/merchant/24/deerspot_1619353587300.png', 'data/images/merchant/24/deerspot_1619353587300.png', null, null, '6.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '6', null, '2021-04-25 19:57:54', '2021-04-25 20:26:29');
INSERT INTO `tb_goods` VALUES ('249', '19', '柠檬红茶', 'data/images/merchant/24/deerspot_1619353564899.png', 'data/images/merchant/24/deerspot_1619353564899.png', null, null, '7.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '7', null, '2021-04-25 19:59:49', '2021-04-25 20:26:06');
INSERT INTO `tb_goods` VALUES ('250', '19', '百香四季', 'data/images/merchant/24/deerspot_1619353454367.png', 'data/images/merchant/24/deerspot_1619353454367.png', null, null, '11.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '11', null, '2021-04-25 20:04:49', '2021-04-25 20:24:16');
INSERT INTO `tb_goods` VALUES ('251', '19', '茉莉橙子', 'data/images/merchant/24/deerspot_1619353524338.png', 'data/images/merchant/24/deerspot_1619353524338.png', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-04-25 20:20:22', '2021-04-25 20:25:26');
INSERT INTO `tb_goods` VALUES ('252', '19', '金桔柠檬绿茶', 'data/images/merchant/24/deerspot_1619354142615.png', 'data/images/merchant/24/deerspot_1619354142615.png', null, null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-04-25 20:35:44', '2021-04-25 20:37:07');
INSERT INTO `tb_goods` VALUES ('253', '19', '百香果双响炮', 'data/images/merchant/24/deerspot_1619354304921.png', 'data/images/merchant/24/deerspot_1619354304921.png', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-04-25 20:38:26', '2021-04-25 20:38:26');
INSERT INTO `tb_goods` VALUES ('254', '19', '百香金桔柠檬', 'data/images/merchant/24/deerspot_1619354480442.png', 'data/images/merchant/24/deerspot_1619354480442.png', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods` VALUES ('255', '19', '芒果牛奶', 'data/images/merchant/24/deerspot_1619355534032.png', 'data/images/merchant/24/deerspot_1619355534032.png', null, null, '8.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '8', null, '2021-04-25 20:58:56', '2021-04-25 23:28:45');
INSERT INTO `tb_goods` VALUES ('256', '19', '椰浆西米露', 'data/images/merchant/24/deerspot_1619358453180.png', 'data/images/merchant/24/deerspot_1619358453180.png', null, null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2021-04-25 21:47:35', '2021-04-25 23:28:38');
INSERT INTO `tb_goods` VALUES ('257', '19', '芒果益菌多', 'data/images/merchant/24/deerspot_1619359158859.png', 'data/images/merchant/24/deerspot_1619359158859.png', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2021-04-25 21:59:20', '2021-04-25 22:33:10');
INSERT INTO `tb_goods` VALUES ('258', '19', '柠檬益菌多', 'data/images/merchant/24/deerspot_1619359264893.png', 'data/images/merchant/24/deerspot_1619359264893.png', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2021-04-25 22:01:06', '2021-04-25 22:32:49');
INSERT INTO `tb_goods` VALUES ('259', '19', '百香果益菌多', 'data/images/merchant/24/deerspot_1619361128763.png', 'data/images/merchant/24/deerspot_1619361128763.png', null, null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods` VALUES ('260', '19', '熊猫奶盖', 'data/images/merchant/24/deerspot_1622733955759.png', 'data/images/merchant/24/deerspot_1622733955759.png', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-04-25 23:25:51', '2021-06-03 23:25:58');
INSERT INTO `tb_goods` VALUES ('261', '13', 'test-a', 'data/images/merchant/18/deerspot_1622733699122.png', 'data/images/merchant/18/deerspot_1622733699122.png', '1', null, '10000.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10000', null, '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods` VALUES ('262', '18', '脏脏蓝莓', 'data/images/merchant/23/deerspot_1624764648593.jpg', 'data/images/merchant/23/deerspot_1624764648593.jpg', null, null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2021-06-27 11:30:50', '2021-06-27 11:31:02');
INSERT INTO `tb_goods` VALUES ('263', '18', '原味奶茶', 'data/images/merchant/23/deerspot_1624889025764.jpg', 'data/images/merchant/23/deerspot_1624889025764.jpg', null, null, '6.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '6', null, '2021-06-28 22:03:47', '2021-06-28 22:11:41');
INSERT INTO `tb_goods` VALUES ('264', '18', '芋泥椰奶波波', 'data/images/merchant/23/deerspot_1624889229079.jpg', 'data/images/merchant/23/deerspot_1624889229079.jpg', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-06-28 22:07:11', '2021-06-28 22:11:34');
INSERT INTO `tb_goods` VALUES ('265', '18', '奥利奥奶茶', 'data/images/merchant/23/deerspot_1624889444597.jpg', 'data/images/merchant/23/deerspot_1624889444597.jpg', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-06-28 22:10:46', '2021-06-28 22:11:29');
INSERT INTO `tb_goods` VALUES ('266', '18', '花香阿华田', 'data/images/merchant/23/deerspot_1624889720668.jpg', 'data/images/merchant/23/deerspot_1624889720668.jpg', null, null, '16.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '16', null, '2021-06-28 22:15:22', '2021-06-28 22:15:22');
INSERT INTO `tb_goods` VALUES ('267', '18', '芝士芒芒', 'data/images/merchant/23/deerspot_1624890682710.jpg', 'data/images/merchant/23/deerspot_1624890682710.jpg', null, null, '15.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '15', null, '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods` VALUES ('268', '18', '芝士多肉葡萄', 'data/images/merchant/23/deerspot_1624890875564.jpg', 'data/images/merchant/23/deerspot_1624890875564.jpg', null, null, '18.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '18', null, '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods` VALUES ('269', '18', '脏脏牛乳茶', 'data/images/merchant/23/deerspot_1625111261841.jpg', 'data/images/merchant/23/deerspot_1625111261841.jpg', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-07-01 11:47:43', '2021-07-01 11:47:43');
INSERT INTO `tb_goods` VALUES ('270', '18', '超级柠檬水', 'data/images/merchant/23/deerspot_1625111730453.jpg', 'data/images/merchant/23/deerspot_1625111730453.jpg', null, null, '5.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '5', null, '2021-07-01 11:55:32', '2021-07-01 11:55:32');
INSERT INTO `tb_goods` VALUES ('271', '18', '百香果三重奏', 'data/images/merchant/23/deerspot_1625115665092.jpg', 'data/images/merchant/23/deerspot_1625115665092.jpg', null, null, '12.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '12', null, '2021-07-01 13:01:06', '2021-07-01 13:01:06');
INSERT INTO `tb_goods` VALUES ('272', '18', '芋圆葡萄冻冻', 'data/images/merchant/23/deerspot_1625134448911.jpg', 'data/images/merchant/23/deerspot_1625134448911.jpg', null, null, '13.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '13', null, '2021-07-01 18:14:10', '2021-07-01 18:14:10');
INSERT INTO `tb_goods` VALUES ('273', '18', '美式咖啡', 'data/images/merchant/23/deerspot_1625134931730.jpg', 'data/images/merchant/23/deerspot_1625134931730.jpg', null, null, '10.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '10', null, '2021-07-01 18:22:13', '2021-07-01 18:22:13');
INSERT INTO `tb_goods` VALUES ('274', '18', '原味鸡蛋仔（多口味）', 'data/images/merchant/23/deerspot_1625135121727.jpg', 'data/images/merchant/23/deerspot_1625135121727.jpg', null, null, '9.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '1.00', '0.00', '9', null, '2021-07-01 18:25:29', '2021-07-01 18:25:29');
INSERT INTO `tb_goods` VALUES ('275', '18', '珍珠', 'data/images/merchant/23/deerspot_1625146462382.jpg', 'data/images/merchant/23/deerspot_1625146462382.jpg', null, null, '2.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '2', null, '2021-07-01 21:34:24', '2021-07-01 21:34:24');
INSERT INTO `tb_goods` VALUES ('276', '18', '椰果', 'data/images/merchant/23/deerspot_1625146549591.jpg', 'data/images/merchant/23/deerspot_1625146549591.jpg', null, null, '2.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '2', null, '2021-07-01 21:35:51', '2021-07-01 21:35:51');
INSERT INTO `tb_goods` VALUES ('277', '18', '红豆', 'data/images/merchant/23/deerspot_1625147026980.jpg', 'data/images/merchant/23/deerspot_1625147026980.jpg', null, null, '2.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '2', null, '2021-07-01 21:43:49', '2021-07-01 21:43:49');
INSERT INTO `tb_goods` VALUES ('278', '18', '布丁', 'data/images/merchant/23/deerspot_1625147158381.jpg', 'data/images/merchant/23/deerspot_1625147158381.jpg', '', null, '2.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '2', null, '2021-07-01 21:46:00', '2021-07-01 21:46:00');
INSERT INTO `tb_goods` VALUES ('279', '18', '仙草', 'data/images/merchant/23/deerspot_1625147259171.jpg', 'data/images/merchant/23/deerspot_1625147259171.jpg', null, null, '2.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '2', null, '2021-07-01 21:47:41', '2021-07-01 21:47:41');
INSERT INTO `tb_goods` VALUES ('280', '18', '芝士奶油', 'data/images/merchant/23/deerspot_1625147419502.jpg', 'data/images/merchant/23/deerspot_1625147419502.jpg', null, null, '4.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '4', null, '2021-07-01 21:50:21', '2021-07-01 21:50:21');

-- ----------------------------
-- Table structure for tb_goods_specification
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_specification`;
CREATE TABLE `tb_goods_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `name` varchar(10) NOT NULL COMMENT '商品规格名称',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1678 DEFAULT CHARSET=utf8 COMMENT='商品规格表';

-- ----------------------------
-- Records of tb_goods_specification
-- ----------------------------
INSERT INTO `tb_goods_specification` VALUES ('141', '28', '规格', '1', '2020-03-23 11:07:24', '2020-03-23 11:07:24');
INSERT INTO `tb_goods_specification` VALUES ('180', '28', '甜度', '2', '2020-03-23 17:26:12', '2020-03-23 17:26:12');
INSERT INTO `tb_goods_specification` VALUES ('181', '33', '规格', '1', '2020-03-24 11:52:53', '2020-03-24 11:52:53');
INSERT INTO `tb_goods_specification` VALUES ('190', '38', '规格', '1', '2020-03-24 12:43:24', '2020-03-24 12:43:24');
INSERT INTO `tb_goods_specification` VALUES ('193', '39', '添加', '2', '2020-03-24 12:46:12', '2020-03-24 12:46:12');
INSERT INTO `tb_goods_specification` VALUES ('194', '39', '温度', '3', '2020-03-24 18:11:09', '2020-03-24 18:11:09');
INSERT INTO `tb_goods_specification` VALUES ('204', '41', '温度', '1', '2020-04-05 00:15:47', '2020-04-05 00:15:47');
INSERT INTO `tb_goods_specification` VALUES ('205', '41', '甜度', '2', '2020-04-05 00:16:55', '2020-04-05 00:16:55');
INSERT INTO `tb_goods_specification` VALUES ('262', '53', '规格', '1', '2020-05-11 13:02:35', '2020-05-11 13:02:35');
INSERT INTO `tb_goods_specification` VALUES ('263', '53', '温度', '2', '2020-05-11 13:02:54', '2020-05-11 13:02:54');
INSERT INTO `tb_goods_specification` VALUES ('264', '53', '甜度', '3', '2020-05-11 13:03:19', '2020-05-11 13:03:19');
INSERT INTO `tb_goods_specification` VALUES ('265', '52', '规格', '1', '2020-05-11 13:03:38', '2020-05-11 13:03:38');
INSERT INTO `tb_goods_specification` VALUES ('266', '52', '温度', '2', '2020-05-11 13:03:49', '2020-05-11 13:03:49');
INSERT INTO `tb_goods_specification` VALUES ('267', '52', '甜度', '3', '2020-05-11 13:03:58', '2020-05-11 13:03:58');
INSERT INTO `tb_goods_specification` VALUES ('268', '51', '温度', '1', '2020-05-11 13:04:23', '2020-05-11 13:04:23');
INSERT INTO `tb_goods_specification` VALUES ('269', '51', '加料', '2', '2020-05-11 13:04:48', '2020-05-11 13:04:48');
INSERT INTO `tb_goods_specification` VALUES ('270', '51', '甜度', '3', '2020-05-11 13:07:02', '2020-05-11 13:07:02');
INSERT INTO `tb_goods_specification` VALUES ('273', '49', '温度', '1', '2020-05-11 13:22:49', '2020-05-11 13:22:49');
INSERT INTO `tb_goods_specification` VALUES ('274', '49', '加料', '2', '2020-05-11 13:23:07', '2020-05-11 13:23:07');
INSERT INTO `tb_goods_specification` VALUES ('275', '49', '甜度', '3', '2020-05-11 13:24:25', '2020-05-11 13:24:25');
INSERT INTO `tb_goods_specification` VALUES ('276', '48', '规格', '1', '2020-05-11 18:05:51', '2020-05-11 18:05:51');
INSERT INTO `tb_goods_specification` VALUES ('277', '48', '温度', '2', '2020-05-11 18:06:00', '2020-05-11 18:06:00');
INSERT INTO `tb_goods_specification` VALUES ('278', '48', '甜度', '3', '2020-05-11 18:06:12', '2020-05-11 18:06:12');
INSERT INTO `tb_goods_specification` VALUES ('279', '47', '规格', '1', '2020-05-11 18:06:38', '2020-05-11 18:06:38');
INSERT INTO `tb_goods_specification` VALUES ('280', '47', '温度', '2', '2020-05-11 18:06:49', '2020-05-11 18:06:49');
INSERT INTO `tb_goods_specification` VALUES ('281', '47', '甜度', '3', '2020-05-11 18:07:09', '2020-05-11 18:07:09');
INSERT INTO `tb_goods_specification` VALUES ('282', '46', '规格', '1', '2020-05-11 18:07:55', '2020-05-11 18:07:55');
INSERT INTO `tb_goods_specification` VALUES ('283', '46', '温度', '2', '2020-05-11 18:08:03', '2020-05-11 18:08:03');
INSERT INTO `tb_goods_specification` VALUES ('284', '46', '甜度', '3', '2020-05-11 18:08:15', '2020-05-11 18:08:15');
INSERT INTO `tb_goods_specification` VALUES ('285', '45', '规格', '1', '2020-05-11 18:08:44', '2020-05-11 18:08:44');
INSERT INTO `tb_goods_specification` VALUES ('289', '39', '加料', '4', '2020-05-11 18:11:01', '2020-05-11 18:11:01');
INSERT INTO `tb_goods_specification` VALUES ('290', '39', '甜度', '5', '2020-05-11 18:11:51', '2020-05-11 18:11:51');
INSERT INTO `tb_goods_specification` VALUES ('291', '45', '加料', '2', '2020-05-11 18:12:38', '2020-05-11 18:12:38');
INSERT INTO `tb_goods_specification` VALUES ('292', '45', '温度', '3', '2020-05-11 18:12:58', '2020-05-11 18:12:58');
INSERT INTO `tb_goods_specification` VALUES ('293', '45', '甜度', '4', '2020-05-11 18:13:10', '2020-05-11 18:13:10');
INSERT INTO `tb_goods_specification` VALUES ('294', '28', '加料', '3', '2020-05-11 18:17:00', '2020-05-11 18:17:00');
INSERT INTO `tb_goods_specification` VALUES ('307', '56', '规格', '1', '2020-05-12 11:14:21', '2020-05-12 11:14:21');
INSERT INTO `tb_goods_specification` VALUES ('308', '56', '加料', '2', '2020-05-12 11:14:27', '2020-05-12 11:14:27');
INSERT INTO `tb_goods_specification` VALUES ('313', '57', '规格', '1', '2020-05-12 17:53:16', '2020-05-12 17:53:16');
INSERT INTO `tb_goods_specification` VALUES ('314', '57', '温度', '2', '2020-05-12 17:53:30', '2020-05-12 17:53:30');
INSERT INTO `tb_goods_specification` VALUES ('319', '58', '规格', '1', '2020-05-13 10:23:40', '2020-05-13 10:23:40');
INSERT INTO `tb_goods_specification` VALUES ('320', '58', '温度', '2', '2020-05-13 10:23:47', '2020-05-13 10:23:47');
INSERT INTO `tb_goods_specification` VALUES ('321', '58', '甜度', '3', '2020-05-13 10:24:01', '2020-05-13 10:24:01');
INSERT INTO `tb_goods_specification` VALUES ('333', '60', '规格', '1', '2020-05-13 11:59:04', '2020-05-13 11:59:04');
INSERT INTO `tb_goods_specification` VALUES ('334', '60', '甜度', '2', '2020-05-13 11:59:25', '2020-05-13 11:59:25');
INSERT INTO `tb_goods_specification` VALUES ('339', '61', '规格', '1', '2020-05-13 13:07:55', '2020-05-13 13:07:55');
INSERT INTO `tb_goods_specification` VALUES ('340', '61', '甜度', '2', '2020-05-13 13:08:06', '2020-05-13 13:08:06');
INSERT INTO `tb_goods_specification` VALUES ('345', '62', '规格', '1', '2020-05-15 14:38:32', '2020-05-15 14:38:32');
INSERT INTO `tb_goods_specification` VALUES ('346', '62', '温度', '2', '2020-05-15 14:38:44', '2020-05-15 14:38:44');
INSERT INTO `tb_goods_specification` VALUES ('351', '63', '规格', '1', '2020-05-15 14:51:46', '2020-05-15 14:51:46');
INSERT INTO `tb_goods_specification` VALUES ('352', '63', '温度', '2', '2020-05-15 14:51:54', '2020-05-15 14:51:54');
INSERT INTO `tb_goods_specification` VALUES ('357', '64', '规格', '1', '2020-05-15 14:55:26', '2020-05-15 14:55:26');
INSERT INTO `tb_goods_specification` VALUES ('358', '64', '温度', '2', '2020-05-15 14:55:33', '2020-05-15 14:55:33');
INSERT INTO `tb_goods_specification` VALUES ('363', '65', '规格', '1', '2020-05-15 14:59:04', '2020-05-15 14:59:04');
INSERT INTO `tb_goods_specification` VALUES ('364', '65', '温度', '2', '2020-05-15 14:59:13', '2020-05-15 14:59:13');
INSERT INTO `tb_goods_specification` VALUES ('369', '66', '规格', '1', '2020-05-17 11:36:53', '2020-05-17 11:36:53');
INSERT INTO `tb_goods_specification` VALUES ('370', '66', '温度', '2', '2020-05-17 11:37:01', '2020-05-17 11:37:01');
INSERT INTO `tb_goods_specification` VALUES ('371', '28', '温度', '4', '2020-05-20 12:30:19', '2020-05-20 12:30:19');
INSERT INTO `tb_goods_specification` VALUES ('376', '67', '规格', '1', '2020-05-20 14:39:43', '2020-05-20 14:39:43');
INSERT INTO `tb_goods_specification` VALUES ('377', '67', '温度', '2', '2020-05-20 14:39:50', '2020-05-20 14:39:50');
INSERT INTO `tb_goods_specification` VALUES ('382', '68', '规格', '1', '2020-05-20 14:46:42', '2020-05-20 14:46:42');
INSERT INTO `tb_goods_specification` VALUES ('383', '68', '温度', '2', '2020-05-20 14:46:49', '2020-05-20 14:46:49');
INSERT INTO `tb_goods_specification` VALUES ('388', '69', '规格', '1', '2020-05-23 14:32:06', '2020-05-23 14:32:06');
INSERT INTO `tb_goods_specification` VALUES ('389', '69', '温度', '2', '2020-05-23 14:32:11', '2020-05-23 14:32:11');
INSERT INTO `tb_goods_specification` VALUES ('390', '69', '甜度', '3', '2020-05-23 14:32:17', '2020-05-23 14:32:17');
INSERT INTO `tb_goods_specification` VALUES ('395', '70', '规格', '1', '2020-05-25 16:40:25', '2020-05-25 16:40:25');
INSERT INTO `tb_goods_specification` VALUES ('396', '70', '温度', '2', '2020-05-25 16:40:32', '2020-05-25 16:40:32');
INSERT INTO `tb_goods_specification` VALUES ('401', '71', '规格', '1', '2020-05-28 21:43:13', '2020-05-28 21:43:13');
INSERT INTO `tb_goods_specification` VALUES ('402', '71', '温度', '2', '2020-05-28 21:43:24', '2020-05-28 21:43:24');
INSERT INTO `tb_goods_specification` VALUES ('407', '72', '规格', '1', '2020-05-29 12:16:21', '2020-05-29 12:16:21');
INSERT INTO `tb_goods_specification` VALUES ('408', '72', '温度', '2', '2020-05-29 12:16:28', '2020-05-29 12:16:28');
INSERT INTO `tb_goods_specification` VALUES ('413', '73', '规格', '1', '2020-06-01 15:18:05', '2020-06-01 15:18:05');
INSERT INTO `tb_goods_specification` VALUES ('414', '73', '温度', '2', '2020-06-01 15:18:16', '2020-06-01 15:18:16');
INSERT INTO `tb_goods_specification` VALUES ('415', '64', '添加', '3', '2020-06-01 15:18:49', '2020-06-01 15:18:49');
INSERT INTO `tb_goods_specification` VALUES ('416', '66', '添加', '3', '2020-06-01 15:21:58', '2020-06-01 15:21:58');
INSERT INTO `tb_goods_specification` VALUES ('421', '74', '规格', '1', '2020-06-05 10:15:53', '2020-06-05 10:15:53');
INSERT INTO `tb_goods_specification` VALUES ('422', '74', '温度', '2', '2020-06-05 10:17:12', '2020-06-05 10:17:12');
INSERT INTO `tb_goods_specification` VALUES ('423', '63', '添加', '3', '2020-06-07 14:56:14', '2020-06-07 14:56:14');
INSERT INTO `tb_goods_specification` VALUES ('424', '62', '添加', '3', '2020-06-07 14:56:34', '2020-06-07 14:56:34');
INSERT INTO `tb_goods_specification` VALUES ('425', '61', '添加', '3', '2020-06-07 14:56:55', '2020-06-07 14:56:55');
INSERT INTO `tb_goods_specification` VALUES ('426', '60', '添加', '3', '2020-06-07 14:57:11', '2020-06-07 14:57:11');
INSERT INTO `tb_goods_specification` VALUES ('431', '75', '规格', '1', '2020-06-15 14:33:57', '2020-06-15 14:33:57');
INSERT INTO `tb_goods_specification` VALUES ('432', '75', '温度', '2', '2020-06-15 14:34:06', '2020-06-15 14:34:06');
INSERT INTO `tb_goods_specification` VALUES ('437', '76', '规格', '1', '2020-06-17 13:44:14', '2020-06-17 13:44:14');
INSERT INTO `tb_goods_specification` VALUES ('438', '76', '温度', '2', '2020-06-17 13:44:21', '2020-06-17 13:44:21');
INSERT INTO `tb_goods_specification` VALUES ('443', '77', '规格', '1', '2020-06-17 14:21:58', '2020-06-17 14:21:58');
INSERT INTO `tb_goods_specification` VALUES ('444', '77', '温度', '2', '2020-06-17 14:22:04', '2020-06-17 14:22:04');
INSERT INTO `tb_goods_specification` VALUES ('445', '77', '添加', '3', '2020-06-17 14:22:12', '2020-06-17 14:22:12');
INSERT INTO `tb_goods_specification` VALUES ('450', '78', '规格', '1', '2020-06-17 19:20:08', '2020-06-17 19:20:08');
INSERT INTO `tb_goods_specification` VALUES ('451', '78', '温度', '2', '2020-06-17 19:20:14', '2020-06-17 19:20:14');
INSERT INTO `tb_goods_specification` VALUES ('458', '59', '规格', '1', '2020-06-21 22:22:45', '2020-06-21 22:22:45');
INSERT INTO `tb_goods_specification` VALUES ('459', '59', '温度', '2', '2020-06-21 22:23:05', '2020-06-21 22:23:05');
INSERT INTO `tb_goods_specification` VALUES ('464', '80', '规格', '1', '2020-06-22 13:30:24', '2020-06-22 13:30:24');
INSERT INTO `tb_goods_specification` VALUES ('465', '80', '温度', '2', '2020-06-22 13:30:30', '2020-06-22 13:30:30');
INSERT INTO `tb_goods_specification` VALUES ('466', '78', '甜度', '3', '2020-06-22 14:36:38', '2020-06-22 14:36:38');
INSERT INTO `tb_goods_specification` VALUES ('471', '81', '规格', '1', '2020-07-01 14:02:02', '2020-07-01 14:02:02');
INSERT INTO `tb_goods_specification` VALUES ('472', '81', '温度', '2', '2020-07-01 14:02:11', '2020-07-01 14:02:11');
INSERT INTO `tb_goods_specification` VALUES ('473', '81', '甜度', '3', '2020-07-01 14:02:29', '2020-07-01 14:02:29');
INSERT INTO `tb_goods_specification` VALUES ('474', '78', '添加', '4', '2020-07-08 21:07:30', '2020-07-08 21:07:30');
INSERT INTO `tb_goods_specification` VALUES ('480', '82', '规格', '1', '2020-08-04 09:03:16', '2020-08-04 09:03:16');
INSERT INTO `tb_goods_specification` VALUES ('481', '82', '温度', '2', '2020-08-04 09:03:22', '2020-08-04 09:03:22');
INSERT INTO `tb_goods_specification` VALUES ('482', '82', '甜度', '3', '2020-08-05 17:34:22', '2020-08-05 17:34:22');
INSERT INTO `tb_goods_specification` VALUES ('487', '83', '规格', '1', '2020-09-22 23:55:15', '2020-09-22 23:55:15');
INSERT INTO `tb_goods_specification` VALUES ('492', '84', '规格', '1', '2020-09-23 00:05:15', '2020-09-23 00:05:15');
INSERT INTO `tb_goods_specification` VALUES ('557', '100', '规格', '1', '2020-10-07 17:41:34', '2020-10-07 17:41:34');
INSERT INTO `tb_goods_specification` VALUES ('558', '100', '温度', '2', '2020-10-07 17:41:57', '2020-10-07 17:41:57');
INSERT INTO `tb_goods_specification` VALUES ('853', '174', '温度', '2', '2020-10-08 20:32:37', '2020-10-08 20:32:37');
INSERT INTO `tb_goods_specification` VALUES ('948', '83', '温度', '2', '2020-10-09 14:52:06', '2020-10-09 14:52:06');
INSERT INTO `tb_goods_specification` VALUES ('949', '84', '温度', '2', '2020-10-09 14:54:32', '2020-10-09 14:54:32');
INSERT INTO `tb_goods_specification` VALUES ('950', '85', '规格', '1', '2020-10-09 14:54:58', '2020-10-09 14:54:58');
INSERT INTO `tb_goods_specification` VALUES ('951', '85', '温度', '2', '2020-10-09 14:56:14', '2020-10-09 14:56:14');
INSERT INTO `tb_goods_specification` VALUES ('953', '86', '规格', '1', '2020-10-09 15:03:24', '2020-10-09 15:03:24');
INSERT INTO `tb_goods_specification` VALUES ('954', '86', '温度', '2', '2020-10-09 15:03:30', '2020-10-09 15:03:30');
INSERT INTO `tb_goods_specification` VALUES ('955', '86', '添加', '3', '2020-10-09 15:03:36', '2020-10-09 15:03:36');
INSERT INTO `tb_goods_specification` VALUES ('956', '87', '规格', '1', '2020-10-09 15:05:23', '2020-10-09 15:05:23');
INSERT INTO `tb_goods_specification` VALUES ('957', '87', '温度', '2', '2020-10-09 15:05:27', '2020-10-09 15:05:27');
INSERT INTO `tb_goods_specification` VALUES ('958', '88', '规格', '1', '2020-10-09 15:05:50', '2020-10-09 15:05:50');
INSERT INTO `tb_goods_specification` VALUES ('959', '88', '温度', '2', '2020-10-09 15:05:59', '2020-10-09 15:05:59');
INSERT INTO `tb_goods_specification` VALUES ('960', '89', '规格', '1', '2020-10-09 15:06:18', '2020-10-09 15:06:18');
INSERT INTO `tb_goods_specification` VALUES ('961', '89', '温度', '2', '2020-10-09 15:06:23', '2020-10-09 15:06:23');
INSERT INTO `tb_goods_specification` VALUES ('962', '90', '规格', '1', '2020-10-09 15:06:42', '2020-10-09 15:06:42');
INSERT INTO `tb_goods_specification` VALUES ('963', '90', '温度', '2', '2020-10-09 15:06:54', '2020-10-09 15:06:54');
INSERT INTO `tb_goods_specification` VALUES ('964', '91', '规格', '1', '2020-10-09 15:07:18', '2020-10-09 15:07:18');
INSERT INTO `tb_goods_specification` VALUES ('965', '91', '温度', '2', '2020-10-09 15:07:25', '2020-10-09 15:07:25');
INSERT INTO `tb_goods_specification` VALUES ('966', '92', '规格', '1', '2020-10-09 15:07:44', '2020-10-09 15:07:44');
INSERT INTO `tb_goods_specification` VALUES ('967', '92', '温度', '2', '2020-10-09 15:07:52', '2020-10-09 15:07:52');
INSERT INTO `tb_goods_specification` VALUES ('968', '93', '规格', '1', '2020-10-09 15:08:07', '2020-10-09 15:08:07');
INSERT INTO `tb_goods_specification` VALUES ('969', '93', '温度', '2', '2020-10-09 15:08:13', '2020-10-09 15:08:13');
INSERT INTO `tb_goods_specification` VALUES ('970', '94', '规格', '1', '2020-10-09 15:08:25', '2020-10-09 15:08:25');
INSERT INTO `tb_goods_specification` VALUES ('971', '94', '温度', '2', '2020-10-09 15:08:33', '2020-10-09 15:08:33');
INSERT INTO `tb_goods_specification` VALUES ('972', '95', '规格', '1', '2020-10-09 15:08:44', '2020-10-09 15:08:44');
INSERT INTO `tb_goods_specification` VALUES ('973', '95', '温度', '2', '2020-10-09 15:08:52', '2020-10-09 15:08:52');
INSERT INTO `tb_goods_specification` VALUES ('974', '96', '规格', '1', '2020-10-09 15:09:08', '2020-10-09 15:09:08');
INSERT INTO `tb_goods_specification` VALUES ('975', '96', '温度', '2', '2020-10-09 15:09:17', '2020-10-09 15:09:17');
INSERT INTO `tb_goods_specification` VALUES ('976', '101', '规格', '1', '2020-10-09 15:11:21', '2020-10-09 15:11:21');
INSERT INTO `tb_goods_specification` VALUES ('977', '101', '温度', '2', '2020-10-09 15:15:54', '2020-10-09 15:15:54');
INSERT INTO `tb_goods_specification` VALUES ('979', '102', '规格', '1', '2020-10-09 15:17:15', '2020-10-09 15:17:15');
INSERT INTO `tb_goods_specification` VALUES ('980', '102', '温度', '2', '2020-10-09 15:17:35', '2020-10-09 15:17:35');
INSERT INTO `tb_goods_specification` VALUES ('981', '102', '添加', '3', '2020-10-09 15:18:18', '2020-10-09 15:18:18');
INSERT INTO `tb_goods_specification` VALUES ('982', '103', '规格', '1', '2020-10-09 15:18:50', '2020-10-09 15:18:50');
INSERT INTO `tb_goods_specification` VALUES ('983', '103', '温度', '2', '2020-10-09 15:18:57', '2020-10-09 15:18:57');
INSERT INTO `tb_goods_specification` VALUES ('984', '104', '规格', '1', '2020-10-09 15:19:51', '2020-10-09 15:19:51');
INSERT INTO `tb_goods_specification` VALUES ('985', '104', '温度', '2', '2020-10-09 15:20:12', '2020-10-09 15:20:12');
INSERT INTO `tb_goods_specification` VALUES ('986', '104', '添加', '3', '2020-10-09 15:20:49', '2020-10-09 15:20:49');
INSERT INTO `tb_goods_specification` VALUES ('987', '105', '规格', '1', '2020-10-09 15:21:45', '2020-10-09 15:21:45');
INSERT INTO `tb_goods_specification` VALUES ('988', '105', '温度', '2', '2020-10-09 15:22:05', '2020-10-09 15:22:05');
INSERT INTO `tb_goods_specification` VALUES ('989', '105', '添加', '3', '2020-10-09 15:22:45', '2020-10-09 15:22:45');
INSERT INTO `tb_goods_specification` VALUES ('990', '106', '规格', '1', '2020-10-09 15:23:30', '2020-10-09 15:23:30');
INSERT INTO `tb_goods_specification` VALUES ('991', '106', '温度', '2', '2020-10-09 15:23:44', '2020-10-09 15:23:44');
INSERT INTO `tb_goods_specification` VALUES ('992', '107', '规格', '1', '2020-10-09 15:24:25', '2020-10-09 15:24:25');
INSERT INTO `tb_goods_specification` VALUES ('993', '107', '温度', '2', '2020-10-09 15:24:41', '2020-10-09 15:24:41');
INSERT INTO `tb_goods_specification` VALUES ('994', '108', '规格', '1', '2020-10-09 15:25:18', '2020-10-09 15:25:18');
INSERT INTO `tb_goods_specification` VALUES ('995', '108', '温度', '2', '2020-10-09 15:25:30', '2020-10-09 15:25:30');
INSERT INTO `tb_goods_specification` VALUES ('996', '109', '规格', '1', '2020-10-09 15:26:20', '2020-10-09 15:26:20');
INSERT INTO `tb_goods_specification` VALUES ('997', '109', '温度', '2', '2020-10-09 15:26:35', '2020-10-09 15:26:35');
INSERT INTO `tb_goods_specification` VALUES ('998', '110', '规格', '1', '2020-10-09 15:27:14', '2020-10-09 15:27:14');
INSERT INTO `tb_goods_specification` VALUES ('999', '110', '温度', '2', '2020-10-09 15:27:25', '2020-10-09 15:27:25');
INSERT INTO `tb_goods_specification` VALUES ('1000', '111', '规格', '1', '2020-10-09 15:28:03', '2020-10-09 15:28:03');
INSERT INTO `tb_goods_specification` VALUES ('1004', '113', '规格', '1', '2020-10-09 15:29:54', '2020-10-09 15:29:54');
INSERT INTO `tb_goods_specification` VALUES ('1006', '114', '规格', '1', '2020-10-09 15:30:25', '2020-10-09 15:30:25');
INSERT INTO `tb_goods_specification` VALUES ('1008', '115', '规格', '1', '2020-10-09 15:30:53', '2020-10-09 15:30:53');
INSERT INTO `tb_goods_specification` VALUES ('1009', '115', '温度', '2', '2020-10-09 15:31:42', '2020-10-09 15:31:42');
INSERT INTO `tb_goods_specification` VALUES ('1010', '116', '规格', '1', '2020-10-09 15:31:51', '2020-10-09 15:31:51');
INSERT INTO `tb_goods_specification` VALUES ('1011', '116', '温度', '2', '2020-10-09 15:31:59', '2020-10-09 15:31:59');
INSERT INTO `tb_goods_specification` VALUES ('1012', '117', '规格', '1', '2020-10-09 15:33:33', '2020-10-09 15:33:33');
INSERT INTO `tb_goods_specification` VALUES ('1013', '117', '温度', '2', '2020-10-09 15:33:40', '2020-10-09 15:33:40');
INSERT INTO `tb_goods_specification` VALUES ('1014', '118', '规格', '1', '2020-10-09 15:34:04', '2020-10-09 15:34:04');
INSERT INTO `tb_goods_specification` VALUES ('1015', '118', '温度', '2', '2020-10-09 15:34:11', '2020-10-09 15:34:11');
INSERT INTO `tb_goods_specification` VALUES ('1016', '119', '规格', '1', '2020-10-09 15:34:34', '2020-10-09 15:34:34');
INSERT INTO `tb_goods_specification` VALUES ('1017', '119', '温度', '2', '2020-10-09 15:34:48', '2020-10-09 15:34:48');
INSERT INTO `tb_goods_specification` VALUES ('1018', '119', '添加', '3', '2020-10-09 15:35:48', '2020-10-09 15:35:48');
INSERT INTO `tb_goods_specification` VALUES ('1019', '120', '规格', '1', '2020-10-09 15:36:18', '2020-10-09 15:36:18');
INSERT INTO `tb_goods_specification` VALUES ('1020', '120', '温度', '2', '2020-10-09 15:36:30', '2020-10-09 15:36:30');
INSERT INTO `tb_goods_specification` VALUES ('1021', '120', '添加', '3', '2020-10-09 15:37:39', '2020-10-09 15:37:39');
INSERT INTO `tb_goods_specification` VALUES ('1022', '121', '规格', '1', '2020-10-09 15:38:28', '2020-10-09 15:38:28');
INSERT INTO `tb_goods_specification` VALUES ('1023', '121', '温度', '2', '2020-10-09 15:42:46', '2020-10-09 15:42:46');
INSERT INTO `tb_goods_specification` VALUES ('1024', '121', '添加', '3', '2020-10-09 15:43:44', '2020-10-09 15:43:44');
INSERT INTO `tb_goods_specification` VALUES ('1025', '122', '规格', '1', '2020-10-09 15:44:27', '2020-10-09 15:44:27');
INSERT INTO `tb_goods_specification` VALUES ('1026', '122', '温度', '2', '2020-10-09 15:44:40', '2020-10-09 15:44:40');
INSERT INTO `tb_goods_specification` VALUES ('1027', '122', '添加', '3', '2020-10-09 15:45:03', '2020-10-09 15:45:03');
INSERT INTO `tb_goods_specification` VALUES ('1028', '123', '规格', '1', '2020-10-09 15:45:54', '2020-10-09 15:45:54');
INSERT INTO `tb_goods_specification` VALUES ('1029', '123', '温度', '2', '2020-10-09 15:46:04', '2020-10-09 15:46:04');
INSERT INTO `tb_goods_specification` VALUES ('1030', '123', '添加', '3', '2020-10-09 15:46:27', '2020-10-09 15:46:27');
INSERT INTO `tb_goods_specification` VALUES ('1031', '124', '规格', '1', '2020-10-09 15:48:22', '2020-10-09 15:48:22');
INSERT INTO `tb_goods_specification` VALUES ('1032', '124', '温度', '2', '2020-10-09 15:48:35', '2020-10-09 15:48:35');
INSERT INTO `tb_goods_specification` VALUES ('1033', '124', '添加', '3', '2020-10-09 15:49:07', '2020-10-09 15:49:07');
INSERT INTO `tb_goods_specification` VALUES ('1034', '125', '规格', '1', '2020-10-09 15:50:34', '2020-10-09 15:50:34');
INSERT INTO `tb_goods_specification` VALUES ('1035', '125', '温度', '2', '2020-10-09 15:50:47', '2020-10-09 15:50:47');
INSERT INTO `tb_goods_specification` VALUES ('1036', '125', '添加', '3', '2020-10-09 15:51:14', '2020-10-09 15:51:14');
INSERT INTO `tb_goods_specification` VALUES ('1037', '111', '温度', '2', '2020-10-09 15:54:51', '2020-10-09 15:54:51');
INSERT INTO `tb_goods_specification` VALUES ('1038', '114', '温度', '2', '2020-10-09 15:56:32', '2020-10-09 15:56:32');
INSERT INTO `tb_goods_specification` VALUES ('1039', '113', '温度', '2', '2020-10-09 15:57:14', '2020-10-09 15:57:14');
INSERT INTO `tb_goods_specification` VALUES ('1040', '112', '规格', '1', '2020-10-09 15:57:47', '2020-10-09 15:57:47');
INSERT INTO `tb_goods_specification` VALUES ('1041', '112', '温度', '2', '2020-10-09 15:58:06', '2020-10-09 15:58:06');
INSERT INTO `tb_goods_specification` VALUES ('1042', '129', '规格', '1', '2020-10-10 09:04:05', '2020-10-10 09:04:05');
INSERT INTO `tb_goods_specification` VALUES ('1043', '129', '温度', '2', '2020-10-10 09:04:15', '2020-10-10 09:04:15');
INSERT INTO `tb_goods_specification` VALUES ('1044', '130', '规格', '1', '2020-10-10 09:04:44', '2020-10-10 09:04:44');
INSERT INTO `tb_goods_specification` VALUES ('1045', '131', '规格', '1', '2020-10-10 09:06:40', '2020-10-10 09:06:40');
INSERT INTO `tb_goods_specification` VALUES ('1046', '131', '温度', '2', '2020-10-10 09:06:53', '2020-10-10 09:06:53');
INSERT INTO `tb_goods_specification` VALUES ('1048', '132', '规格', '1', '2020-10-10 09:09:43', '2020-10-10 09:09:43');
INSERT INTO `tb_goods_specification` VALUES ('1049', '132', '温度', '2', '2020-10-10 09:09:48', '2020-10-10 09:09:48');
INSERT INTO `tb_goods_specification` VALUES ('1050', '133', '规格', '1', '2020-10-10 09:10:31', '2020-10-10 09:10:31');
INSERT INTO `tb_goods_specification` VALUES ('1051', '133', '温度', '2', '2020-10-10 09:10:36', '2020-10-10 09:10:36');
INSERT INTO `tb_goods_specification` VALUES ('1052', '134', '规格', '1', '2020-10-10 09:11:24', '2020-10-10 09:11:24');
INSERT INTO `tb_goods_specification` VALUES ('1053', '134', '温度', '2', '2020-10-10 09:11:36', '2020-10-10 09:11:36');
INSERT INTO `tb_goods_specification` VALUES ('1054', '135', '规格', '1', '2020-10-10 09:12:05', '2020-10-10 09:12:05');
INSERT INTO `tb_goods_specification` VALUES ('1055', '135', '温度', '2', '2020-10-10 09:22:45', '2020-10-10 09:22:45');
INSERT INTO `tb_goods_specification` VALUES ('1056', '136', '规格', '1', '2020-10-10 09:23:33', '2020-10-10 09:23:33');
INSERT INTO `tb_goods_specification` VALUES ('1057', '136', '温度', '2', '2020-10-10 09:25:49', '2020-10-10 09:25:49');
INSERT INTO `tb_goods_specification` VALUES ('1058', '186', '规格', '1', '2020-10-10 09:26:59', '2020-10-10 09:26:59');
INSERT INTO `tb_goods_specification` VALUES ('1059', '186', '温度', '2', '2020-10-10 09:27:07', '2020-10-10 09:27:07');
INSERT INTO `tb_goods_specification` VALUES ('1060', '187', '规格', '1', '2020-10-10 09:27:22', '2020-10-10 09:27:22');
INSERT INTO `tb_goods_specification` VALUES ('1061', '187', '温度', '2', '2020-10-10 09:27:33', '2020-10-10 09:27:33');
INSERT INTO `tb_goods_specification` VALUES ('1062', '188', '规格', '1', '2020-10-10 09:27:51', '2020-10-10 09:27:51');
INSERT INTO `tb_goods_specification` VALUES ('1063', '188', '温度', '2', '2020-10-10 09:27:57', '2020-10-10 09:27:57');
INSERT INTO `tb_goods_specification` VALUES ('1064', '189', '规格', '1', '2020-10-10 09:28:16', '2020-10-10 09:28:16');
INSERT INTO `tb_goods_specification` VALUES ('1065', '189', '温度', '2', '2020-10-10 09:28:34', '2020-10-10 09:28:34');
INSERT INTO `tb_goods_specification` VALUES ('1066', '190', '规格', '1', '2020-10-10 09:29:23', '2020-10-10 09:29:23');
INSERT INTO `tb_goods_specification` VALUES ('1067', '190', '温度', '2', '2020-10-10 09:29:33', '2020-10-10 09:29:33');
INSERT INTO `tb_goods_specification` VALUES ('1068', '191', '规格', '1', '2020-10-10 09:29:51', '2020-10-10 09:29:51');
INSERT INTO `tb_goods_specification` VALUES ('1069', '191', '温度', '2', '2020-10-10 09:29:58', '2020-10-10 09:29:58');
INSERT INTO `tb_goods_specification` VALUES ('1070', '192', '规格', '1', '2020-10-10 09:30:08', '2020-10-10 09:30:08');
INSERT INTO `tb_goods_specification` VALUES ('1071', '192', '温度', '2', '2020-10-10 09:30:18', '2020-10-10 09:30:18');
INSERT INTO `tb_goods_specification` VALUES ('1072', '193', '规格', '1', '2020-10-10 09:32:19', '2020-10-10 09:32:19');
INSERT INTO `tb_goods_specification` VALUES ('1073', '193', '温度', '2', '2020-10-10 09:32:27', '2020-10-10 09:32:27');
INSERT INTO `tb_goods_specification` VALUES ('1074', '194', '规格', '1', '2020-10-10 10:38:13', '2020-10-10 10:38:13');
INSERT INTO `tb_goods_specification` VALUES ('1075', '194', '温度', '2', '2020-10-10 10:38:20', '2020-10-10 10:38:20');
INSERT INTO `tb_goods_specification` VALUES ('1076', '195', '规格', '1', '2020-10-10 10:38:36', '2020-10-10 10:38:36');
INSERT INTO `tb_goods_specification` VALUES ('1077', '195', '温度', '2', '2020-10-10 10:38:42', '2020-10-10 10:38:42');
INSERT INTO `tb_goods_specification` VALUES ('1078', '196', '规格', '1', '2020-10-10 10:39:21', '2020-10-10 10:39:21');
INSERT INTO `tb_goods_specification` VALUES ('1079', '196', '温度', '2', '2020-10-10 10:39:28', '2020-10-10 10:39:28');
INSERT INTO `tb_goods_specification` VALUES ('1080', '197', '规格', '1', '2020-10-10 10:40:11', '2020-10-10 10:40:11');
INSERT INTO `tb_goods_specification` VALUES ('1081', '197', '温度', '2', '2020-10-10 10:40:21', '2020-10-10 10:40:21');
INSERT INTO `tb_goods_specification` VALUES ('1082', '185', '规格', '1', '2020-10-10 14:06:20', '2020-10-10 14:06:20');
INSERT INTO `tb_goods_specification` VALUES ('1083', '185', '温度', '2', '2020-10-10 14:06:49', '2020-10-10 14:06:49');
INSERT INTO `tb_goods_specification` VALUES ('1085', '184', '规格', '1', '2020-10-10 14:35:20', '2020-10-10 14:35:20');
INSERT INTO `tb_goods_specification` VALUES ('1086', '184', '温度', '2', '2020-10-10 14:35:24', '2020-10-10 14:35:24');
INSERT INTO `tb_goods_specification` VALUES ('1087', '184', '添加', '3', '2020-10-10 14:38:27', '2020-10-10 14:38:27');
INSERT INTO `tb_goods_specification` VALUES ('1088', '183', '规格', '1', '2020-10-10 15:06:34', '2020-10-10 15:06:34');
INSERT INTO `tb_goods_specification` VALUES ('1089', '183', '温度', '2', '2020-10-10 15:07:20', '2020-10-10 15:07:20');
INSERT INTO `tb_goods_specification` VALUES ('1091', '182', '规格', '1', '2020-10-10 18:16:30', '2020-10-10 18:16:30');
INSERT INTO `tb_goods_specification` VALUES ('1092', '185', '口味', '4', '2020-10-10 18:22:25', '2020-10-10 18:22:25');
INSERT INTO `tb_goods_specification` VALUES ('1094', '182', '口味', '2', '2020-10-10 18:24:19', '2020-10-10 18:24:19');
INSERT INTO `tb_goods_specification` VALUES ('1095', '182', '温度', '3', '2020-10-10 18:24:33', '2020-10-10 18:24:33');
INSERT INTO `tb_goods_specification` VALUES ('1097', '181', '规格', '1', '2020-10-10 18:27:54', '2020-10-10 18:27:54');
INSERT INTO `tb_goods_specification` VALUES ('1098', '181', '温度', '2', '2020-10-10 18:28:01', '2020-10-10 18:28:01');
INSERT INTO `tb_goods_specification` VALUES ('1100', '180', '规格', '1', '2020-10-10 18:30:28', '2020-10-10 18:30:28');
INSERT INTO `tb_goods_specification` VALUES ('1101', '180', '温度', '2', '2020-10-10 18:30:33', '2020-10-10 18:30:33');
INSERT INTO `tb_goods_specification` VALUES ('1103', '179', '规格', '1', '2020-10-10 18:39:29', '2020-10-10 18:39:29');
INSERT INTO `tb_goods_specification` VALUES ('1104', '179', '温度', '2', '2020-10-10 18:39:33', '2020-10-10 18:39:33');
INSERT INTO `tb_goods_specification` VALUES ('1105', '178', '规格', '1', '2020-10-10 18:39:45', '2020-10-10 18:39:45');
INSERT INTO `tb_goods_specification` VALUES ('1106', '178', '温度', '2', '2020-10-10 18:39:51', '2020-10-10 18:39:51');
INSERT INTO `tb_goods_specification` VALUES ('1107', '177', '规格', '1', '2020-10-10 18:40:17', '2020-10-10 18:40:17');
INSERT INTO `tb_goods_specification` VALUES ('1108', '177', '温度', '2', '2020-10-10 18:40:59', '2020-10-10 18:40:59');
INSERT INTO `tb_goods_specification` VALUES ('1110', '176', '规格', '1', '2020-10-10 18:43:57', '2020-10-10 18:43:57');
INSERT INTO `tb_goods_specification` VALUES ('1111', '176', '温度', '2', '2020-10-10 18:44:04', '2020-10-10 18:44:04');
INSERT INTO `tb_goods_specification` VALUES ('1112', '175', '规格', '1', '2020-10-10 18:44:23', '2020-10-10 18:44:23');
INSERT INTO `tb_goods_specification` VALUES ('1113', '175', '温度', '2', '2020-10-10 18:44:38', '2020-10-10 18:44:38');
INSERT INTO `tb_goods_specification` VALUES ('1114', '174', '规格', '3', '2020-10-10 18:45:17', '2020-10-10 18:45:17');
INSERT INTO `tb_goods_specification` VALUES ('1115', '173', '规格', '1', '2020-10-10 18:46:05', '2020-10-10 18:46:05');
INSERT INTO `tb_goods_specification` VALUES ('1116', '173', '温度', '2', '2020-10-10 18:46:10', '2020-10-10 18:46:10');
INSERT INTO `tb_goods_specification` VALUES ('1117', '172', '规格', '1', '2020-10-10 18:46:20', '2020-10-10 18:46:20');
INSERT INTO `tb_goods_specification` VALUES ('1118', '172', '温度', '2', '2020-10-10 18:46:26', '2020-10-10 18:46:26');
INSERT INTO `tb_goods_specification` VALUES ('1119', '171', '规格', '1', '2020-10-10 18:46:42', '2020-10-10 18:46:42');
INSERT INTO `tb_goods_specification` VALUES ('1120', '171', '口味', '2', '2020-10-10 18:46:59', '2020-10-10 18:46:59');
INSERT INTO `tb_goods_specification` VALUES ('1121', '171', '温度', '3', '2020-10-10 18:47:20', '2020-10-10 18:47:20');
INSERT INTO `tb_goods_specification` VALUES ('1123', '170', '规格', '1', '2020-10-10 18:48:49', '2020-10-10 18:48:49');
INSERT INTO `tb_goods_specification` VALUES ('1124', '170', '温度', '2', '2020-10-10 18:48:59', '2020-10-10 18:48:59');
INSERT INTO `tb_goods_specification` VALUES ('1125', '169', '规格', '1', '2020-10-10 18:51:19', '2020-10-10 18:51:19');
INSERT INTO `tb_goods_specification` VALUES ('1126', '169', '温度', '2', '2020-10-10 18:51:30', '2020-10-10 18:51:30');
INSERT INTO `tb_goods_specification` VALUES ('1127', '168', '规格', '1', '2020-10-10 18:52:49', '2020-10-10 18:52:49');
INSERT INTO `tb_goods_specification` VALUES ('1128', '168', '温度', '2', '2020-10-10 18:53:14', '2020-10-10 18:53:14');
INSERT INTO `tb_goods_specification` VALUES ('1130', '167', '规格', '1', '2020-10-10 19:00:39', '2020-10-10 19:00:39');
INSERT INTO `tb_goods_specification` VALUES ('1131', '167', '温度', '2', '2020-10-10 19:00:48', '2020-10-10 19:00:48');
INSERT INTO `tb_goods_specification` VALUES ('1132', '166', '规格', '1', '2020-10-10 19:02:32', '2020-10-10 19:02:32');
INSERT INTO `tb_goods_specification` VALUES ('1133', '166', '温度', '2', '2020-10-10 19:02:41', '2020-10-10 19:02:41');
INSERT INTO `tb_goods_specification` VALUES ('1134', '165', '规格', '1', '2020-10-10 19:03:49', '2020-10-10 19:03:49');
INSERT INTO `tb_goods_specification` VALUES ('1135', '165', '温度', '2', '2020-10-10 19:03:57', '2020-10-10 19:03:57');
INSERT INTO `tb_goods_specification` VALUES ('1136', '164', '规格', '1', '2020-10-10 19:04:42', '2020-10-10 19:04:42');
INSERT INTO `tb_goods_specification` VALUES ('1137', '164', '温度', '2', '2020-10-10 19:04:46', '2020-10-10 19:04:46');
INSERT INTO `tb_goods_specification` VALUES ('1138', '163', '规格', '1', '2020-10-10 19:05:07', '2020-10-10 19:05:07');
INSERT INTO `tb_goods_specification` VALUES ('1139', '163', '温度', '2', '2020-10-10 19:05:12', '2020-10-10 19:05:12');
INSERT INTO `tb_goods_specification` VALUES ('1140', '162', '规格', '1', '2020-10-10 19:05:31', '2020-10-10 19:05:31');
INSERT INTO `tb_goods_specification` VALUES ('1141', '162', '温度', '2', '2020-10-10 19:05:39', '2020-10-10 19:05:39');
INSERT INTO `tb_goods_specification` VALUES ('1143', '161', '规格', '1', '2020-10-10 19:06:55', '2020-10-10 19:06:55');
INSERT INTO `tb_goods_specification` VALUES ('1144', '161', '口味', '2', '2020-10-10 19:07:09', '2020-10-10 19:07:09');
INSERT INTO `tb_goods_specification` VALUES ('1145', '161', '温度', '3', '2020-10-10 19:07:27', '2020-10-10 19:07:27');
INSERT INTO `tb_goods_specification` VALUES ('1147', '160', '规格', '1', '2020-10-10 20:48:52', '2020-10-10 20:48:52');
INSERT INTO `tb_goods_specification` VALUES ('1148', '160', '口味', '2', '2020-10-10 20:49:03', '2020-10-10 20:49:03');
INSERT INTO `tb_goods_specification` VALUES ('1149', '160', '温度', '3', '2020-10-10 20:49:18', '2020-10-10 20:49:18');
INSERT INTO `tb_goods_specification` VALUES ('1151', '159', '规格', '1', '2020-10-10 20:52:19', '2020-10-10 20:52:19');
INSERT INTO `tb_goods_specification` VALUES ('1152', '159', '温度', '2', '2020-10-10 20:52:27', '2020-10-10 20:52:27');
INSERT INTO `tb_goods_specification` VALUES ('1153', '158', '规格', '1', '2020-10-10 20:54:03', '2020-10-10 20:54:03');
INSERT INTO `tb_goods_specification` VALUES ('1154', '158', '温度', '2', '2020-10-10 20:54:08', '2020-10-10 20:54:08');
INSERT INTO `tb_goods_specification` VALUES ('1155', '157', '规格', '1', '2020-10-10 20:54:26', '2020-10-10 20:54:26');
INSERT INTO `tb_goods_specification` VALUES ('1156', '157', '温度', '2', '2020-10-10 20:54:34', '2020-10-10 20:54:34');
INSERT INTO `tb_goods_specification` VALUES ('1157', '156', '规格', '1', '2020-10-10 20:55:08', '2020-10-10 20:55:08');
INSERT INTO `tb_goods_specification` VALUES ('1158', '156', '温度', '2', '2020-10-10 20:55:12', '2020-10-10 20:55:12');
INSERT INTO `tb_goods_specification` VALUES ('1159', '155', '规格', '1', '2020-10-10 20:55:27', '2020-10-10 20:55:27');
INSERT INTO `tb_goods_specification` VALUES ('1160', '155', '温度', '2', '2020-10-10 20:55:31', '2020-10-10 20:55:31');
INSERT INTO `tb_goods_specification` VALUES ('1161', '154', '规格', '1', '2020-10-10 20:55:46', '2020-10-10 20:55:46');
INSERT INTO `tb_goods_specification` VALUES ('1162', '154', '温度', '2', '2020-10-10 20:55:51', '2020-10-10 20:55:51');
INSERT INTO `tb_goods_specification` VALUES ('1163', '153', '规格', '1', '2020-10-10 21:03:20', '2020-10-10 21:03:20');
INSERT INTO `tb_goods_specification` VALUES ('1164', '153', '温度', '2', '2020-10-10 21:03:24', '2020-10-10 21:03:24');
INSERT INTO `tb_goods_specification` VALUES ('1165', '152', '规格', '1', '2020-10-10 21:03:35', '2020-10-10 21:03:35');
INSERT INTO `tb_goods_specification` VALUES ('1166', '152', '温度', '2', '2020-10-10 21:03:40', '2020-10-10 21:03:40');
INSERT INTO `tb_goods_specification` VALUES ('1171', '151', '规格', '1', '2020-10-10 21:21:50', '2020-10-10 21:21:50');
INSERT INTO `tb_goods_specification` VALUES ('1172', '151', '温度', '2', '2020-10-10 21:21:59', '2020-10-10 21:21:59');
INSERT INTO `tb_goods_specification` VALUES ('1174', '151', '口味', '4', '2020-10-10 21:25:53', '2020-10-10 21:25:53');
INSERT INTO `tb_goods_specification` VALUES ('1175', '150', '规格', '1', '2020-10-10 21:26:45', '2020-10-10 21:26:45');
INSERT INTO `tb_goods_specification` VALUES ('1176', '150', '口味', '2', '2020-10-10 21:27:42', '2020-10-10 21:27:42');
INSERT INTO `tb_goods_specification` VALUES ('1177', '150', '温度', '3', '2020-10-10 21:27:58', '2020-10-10 21:27:58');
INSERT INTO `tb_goods_specification` VALUES ('1179', '149', '规格', '1', '2020-10-10 21:32:23', '2020-10-10 21:32:23');
INSERT INTO `tb_goods_specification` VALUES ('1180', '149', '温度', '2', '2020-10-10 21:32:30', '2020-10-10 21:32:30');
INSERT INTO `tb_goods_specification` VALUES ('1181', '148', '规格', '1', '2020-10-10 21:32:47', '2020-10-10 21:32:47');
INSERT INTO `tb_goods_specification` VALUES ('1182', '148', '温度', '2', '2020-10-10 21:32:51', '2020-10-10 21:32:51');
INSERT INTO `tb_goods_specification` VALUES ('1183', '147', '规格', '1', '2020-10-10 21:33:09', '2020-10-10 21:33:09');
INSERT INTO `tb_goods_specification` VALUES ('1184', '147', '温度', '2', '2020-10-10 21:33:19', '2020-10-10 21:33:19');
INSERT INTO `tb_goods_specification` VALUES ('1185', '146', '规格', '1', '2020-10-10 21:33:46', '2020-10-10 21:33:46');
INSERT INTO `tb_goods_specification` VALUES ('1186', '146', '温度', '2', '2020-10-10 21:33:59', '2020-10-10 21:33:59');
INSERT INTO `tb_goods_specification` VALUES ('1187', '145', '规格', '1', '2020-10-10 21:42:36', '2020-10-10 21:42:36');
INSERT INTO `tb_goods_specification` VALUES ('1188', '145', '温度', '2', '2020-10-10 21:42:42', '2020-10-10 21:42:42');
INSERT INTO `tb_goods_specification` VALUES ('1189', '144', '规格', '1', '2020-10-10 21:54:53', '2020-10-10 21:54:53');
INSERT INTO `tb_goods_specification` VALUES ('1190', '144', '温度', '2', '2020-10-10 21:55:03', '2020-10-10 21:55:03');
INSERT INTO `tb_goods_specification` VALUES ('1191', '128', '规格', '1', '2020-10-10 21:57:52', '2020-10-10 21:57:52');
INSERT INTO `tb_goods_specification` VALUES ('1192', '128', '温度', '2', '2020-10-10 21:58:04', '2020-10-10 21:58:04');
INSERT INTO `tb_goods_specification` VALUES ('1193', '127', '规格', '1', '2020-10-10 21:58:25', '2020-10-10 21:58:25');
INSERT INTO `tb_goods_specification` VALUES ('1194', '127', '温度', '2', '2020-10-10 21:58:30', '2020-10-10 21:58:30');
INSERT INTO `tb_goods_specification` VALUES ('1195', '126', '规格', '1', '2020-10-10 21:59:11', '2020-10-10 21:59:11');
INSERT INTO `tb_goods_specification` VALUES ('1196', '126', '温度', '2', '2020-10-10 21:59:32', '2020-10-10 21:59:32');
INSERT INTO `tb_goods_specification` VALUES ('1197', '197', '甜度', '3', '2020-10-11 09:32:19', '2020-10-11 09:32:19');
INSERT INTO `tb_goods_specification` VALUES ('1198', '196', '甜度', '3', '2020-10-11 09:33:07', '2020-10-11 09:33:07');
INSERT INTO `tb_goods_specification` VALUES ('1199', '195', '甜度', '3', '2020-10-11 09:33:52', '2020-10-11 09:33:52');
INSERT INTO `tb_goods_specification` VALUES ('1200', '194', '甜度', '3', '2020-10-11 09:34:23', '2020-10-11 09:34:23');
INSERT INTO `tb_goods_specification` VALUES ('1201', '193', '甜度', '3', '2020-10-11 09:34:54', '2020-10-11 09:34:54');
INSERT INTO `tb_goods_specification` VALUES ('1202', '192', '甜度', '3', '2020-10-11 09:38:10', '2020-10-11 09:38:10');
INSERT INTO `tb_goods_specification` VALUES ('1203', '191', '甜度', '3', '2020-10-11 09:38:34', '2020-10-11 09:38:34');
INSERT INTO `tb_goods_specification` VALUES ('1204', '190', '甜度', '3', '2020-10-11 09:39:04', '2020-10-11 09:39:04');
INSERT INTO `tb_goods_specification` VALUES ('1205', '189', '甜度', '3', '2020-10-11 09:39:27', '2020-10-11 09:39:27');
INSERT INTO `tb_goods_specification` VALUES ('1206', '188', '甜度', '3', '2020-10-11 09:39:51', '2020-10-11 09:39:51');
INSERT INTO `tb_goods_specification` VALUES ('1207', '187', '甜度', '3', '2020-10-11 09:40:15', '2020-10-11 09:40:15');
INSERT INTO `tb_goods_specification` VALUES ('1208', '186', '甜度', '3', '2020-10-11 09:40:36', '2020-10-11 09:40:36');
INSERT INTO `tb_goods_specification` VALUES ('1209', '136', '甜度', '3', '2020-10-11 09:41:11', '2020-10-11 09:41:11');
INSERT INTO `tb_goods_specification` VALUES ('1210', '135', '甜度', '3', '2020-10-11 09:41:44', '2020-10-11 09:41:44');
INSERT INTO `tb_goods_specification` VALUES ('1211', '134', '甜度', '3', '2020-10-11 09:42:09', '2020-10-11 09:42:09');
INSERT INTO `tb_goods_specification` VALUES ('1212', '133', '甜度', '3', '2020-10-11 09:42:51', '2020-10-11 09:42:51');
INSERT INTO `tb_goods_specification` VALUES ('1213', '132', '甜度', '3', '2020-10-11 09:43:11', '2020-10-11 09:43:11');
INSERT INTO `tb_goods_specification` VALUES ('1214', '131', '甜度', '3', '2020-10-11 09:43:41', '2020-10-11 09:43:41');
INSERT INTO `tb_goods_specification` VALUES ('1215', '130', '甜度', '2', '2020-10-11 09:44:07', '2020-10-11 09:44:07');
INSERT INTO `tb_goods_specification` VALUES ('1216', '129', '甜度', '3', '2020-10-11 09:44:31', '2020-10-11 09:44:31');
INSERT INTO `tb_goods_specification` VALUES ('1217', '128', '甜度', '3', '2020-10-11 09:45:08', '2020-10-11 09:45:08');
INSERT INTO `tb_goods_specification` VALUES ('1218', '127', '甜度', '3', '2020-10-11 09:45:39', '2020-10-11 09:45:39');
INSERT INTO `tb_goods_specification` VALUES ('1219', '126', '甜度', '3', '2020-10-11 09:46:03', '2020-10-11 09:46:03');
INSERT INTO `tb_goods_specification` VALUES ('1220', '125', '甜度', '4', '2020-10-11 09:46:27', '2020-10-11 09:46:27');
INSERT INTO `tb_goods_specification` VALUES ('1221', '124', '甜度', '4', '2020-10-11 09:46:56', '2020-10-11 09:46:56');
INSERT INTO `tb_goods_specification` VALUES ('1222', '123', '甜度', '4', '2020-10-11 09:47:21', '2020-10-11 09:47:21');
INSERT INTO `tb_goods_specification` VALUES ('1223', '122', '甜度', '4', '2020-10-11 09:48:00', '2020-10-11 09:48:00');
INSERT INTO `tb_goods_specification` VALUES ('1224', '121', '甜度', '4', '2020-10-11 09:48:33', '2020-10-11 09:48:33');
INSERT INTO `tb_goods_specification` VALUES ('1225', '120', '甜度', '4', '2020-10-11 09:49:07', '2020-10-11 09:49:07');
INSERT INTO `tb_goods_specification` VALUES ('1226', '119', '甜度', '4', '2020-10-11 09:49:29', '2020-10-11 09:49:29');
INSERT INTO `tb_goods_specification` VALUES ('1227', '118', '甜度', '3', '2020-10-11 09:49:57', '2020-10-11 09:49:57');
INSERT INTO `tb_goods_specification` VALUES ('1228', '117', '甜度', '3', '2020-10-11 09:50:26', '2020-10-11 09:50:26');
INSERT INTO `tb_goods_specification` VALUES ('1229', '116', '甜度', '3', '2020-10-11 10:21:11', '2020-10-11 10:21:11');
INSERT INTO `tb_goods_specification` VALUES ('1230', '115', '甜度', '3', '2020-10-11 10:21:42', '2020-10-11 10:21:42');
INSERT INTO `tb_goods_specification` VALUES ('1231', '114', '甜度', '3', '2020-10-11 10:23:41', '2020-10-11 10:23:41');
INSERT INTO `tb_goods_specification` VALUES ('1232', '113', '甜度', '3', '2020-10-11 10:24:21', '2020-10-11 10:24:21');
INSERT INTO `tb_goods_specification` VALUES ('1233', '112', '甜度', '3', '2020-10-11 10:24:51', '2020-10-11 10:24:51');
INSERT INTO `tb_goods_specification` VALUES ('1234', '111', '甜度', '3', '2020-10-11 10:25:19', '2020-10-11 10:25:19');
INSERT INTO `tb_goods_specification` VALUES ('1235', '110', '甜度', '3', '2020-10-11 10:26:01', '2020-10-11 10:26:01');
INSERT INTO `tb_goods_specification` VALUES ('1236', '109', '甜度', '3', '2020-10-11 10:26:31', '2020-10-11 10:26:31');
INSERT INTO `tb_goods_specification` VALUES ('1237', '108', '甜度', '3', '2020-10-11 10:26:56', '2020-10-11 10:26:56');
INSERT INTO `tb_goods_specification` VALUES ('1238', '107', '甜度', '3', '2020-10-11 10:27:16', '2020-10-11 10:27:16');
INSERT INTO `tb_goods_specification` VALUES ('1239', '106', '甜度', '3', '2020-10-11 10:28:03', '2020-10-11 10:28:03');
INSERT INTO `tb_goods_specification` VALUES ('1240', '105', '甜度', '4', '2020-10-11 10:28:28', '2020-10-11 10:28:28');
INSERT INTO `tb_goods_specification` VALUES ('1241', '104', '甜度', '4', '2020-10-11 10:28:51', '2020-10-11 10:28:51');
INSERT INTO `tb_goods_specification` VALUES ('1242', '103', '甜度', '3', '2020-10-11 10:29:20', '2020-10-11 10:29:20');
INSERT INTO `tb_goods_specification` VALUES ('1243', '102', '甜度', '4', '2020-10-11 10:29:49', '2020-10-11 10:29:49');
INSERT INTO `tb_goods_specification` VALUES ('1245', '100', '甜度', '3', '2020-10-11 10:30:58', '2020-10-11 10:30:58');
INSERT INTO `tb_goods_specification` VALUES ('1246', '96', '甜度', '3', '2020-10-11 10:31:36', '2020-10-11 10:31:36');
INSERT INTO `tb_goods_specification` VALUES ('1247', '95', '甜度', '3', '2020-10-11 10:32:23', '2020-10-11 10:32:23');
INSERT INTO `tb_goods_specification` VALUES ('1248', '94', '甜度', '3', '2020-10-11 10:33:07', '2020-10-11 10:33:07');
INSERT INTO `tb_goods_specification` VALUES ('1249', '93', '甜度', '3', '2020-10-11 10:33:29', '2020-10-11 10:33:29');
INSERT INTO `tb_goods_specification` VALUES ('1250', '92', '甜度', '3', '2020-10-11 10:34:01', '2020-10-11 10:34:01');
INSERT INTO `tb_goods_specification` VALUES ('1251', '91', '甜度', '3', '2020-10-11 10:34:32', '2020-10-11 10:34:32');
INSERT INTO `tb_goods_specification` VALUES ('1252', '90', '甜度', '3', '2020-10-11 10:41:49', '2020-10-11 10:41:49');
INSERT INTO `tb_goods_specification` VALUES ('1253', '89', '甜度', '3', '2020-10-11 10:42:14', '2020-10-11 10:42:14');
INSERT INTO `tb_goods_specification` VALUES ('1254', '88', '甜度', '3', '2020-10-11 10:42:44', '2020-10-11 10:42:44');
INSERT INTO `tb_goods_specification` VALUES ('1255', '87', '甜度', '3', '2020-10-11 10:43:06', '2020-10-11 10:43:06');
INSERT INTO `tb_goods_specification` VALUES ('1256', '86', '甜度', '4', '2020-10-11 10:43:58', '2020-10-11 10:43:58');
INSERT INTO `tb_goods_specification` VALUES ('1257', '85', '甜度', '4', '2020-10-11 10:44:37', '2020-10-11 10:44:37');
INSERT INTO `tb_goods_specification` VALUES ('1258', '84', '甜度', '3', '2020-10-11 10:45:04', '2020-10-11 10:45:04');
INSERT INTO `tb_goods_specification` VALUES ('1259', '83', '甜度', '3', '2020-10-11 10:45:30', '2020-10-11 10:45:30');
INSERT INTO `tb_goods_specification` VALUES ('1264', '198', '规格', '1', '2020-10-12 11:16:08', '2020-10-12 11:16:08');
INSERT INTO `tb_goods_specification` VALUES ('1265', '198', '温度', '2', '2020-10-12 11:16:28', '2020-10-12 11:16:28');
INSERT INTO `tb_goods_specification` VALUES ('1267', '198', '甜度', '4', '2020-10-12 11:19:20', '2020-10-12 11:19:20');
INSERT INTO `tb_goods_specification` VALUES ('1272', '199', '规格', '1', '2020-10-12 11:23:34', '2020-10-12 11:23:34');
INSERT INTO `tb_goods_specification` VALUES ('1273', '199', '温度', '2', '2020-10-12 11:23:42', '2020-10-12 11:23:42');
INSERT INTO `tb_goods_specification` VALUES ('1274', '199', '甜度', '3', '2020-10-12 11:24:19', '2020-10-12 11:24:19');
INSERT INTO `tb_goods_specification` VALUES ('1279', '200', '规格', '1', '2020-10-12 11:32:48', '2020-10-12 11:32:48');
INSERT INTO `tb_goods_specification` VALUES ('1280', '200', '温度', '2', '2020-10-12 11:32:58', '2020-10-12 11:32:58');
INSERT INTO `tb_goods_specification` VALUES ('1281', '200', '甜度', '3', '2020-10-12 11:33:11', '2020-10-12 11:33:11');
INSERT INTO `tb_goods_specification` VALUES ('1286', '201', '规格', '1', '2020-10-12 12:02:16', '2020-10-12 12:02:16');
INSERT INTO `tb_goods_specification` VALUES ('1287', '201', '温度', '2', '2020-10-12 12:02:35', '2020-10-12 12:02:35');
INSERT INTO `tb_goods_specification` VALUES ('1288', '201', '甜度', '3', '2020-10-12 12:02:58', '2020-10-12 12:02:58');
INSERT INTO `tb_goods_specification` VALUES ('1293', '202', '规格', '1', '2020-10-12 12:06:19', '2020-10-12 12:06:19');
INSERT INTO `tb_goods_specification` VALUES ('1294', '202', '温度', '2', '2020-10-12 12:06:50', '2020-10-12 12:06:50');
INSERT INTO `tb_goods_specification` VALUES ('1295', '202', '甜度', '3', '2020-10-12 12:07:05', '2020-10-12 12:07:05');
INSERT INTO `tb_goods_specification` VALUES ('1300', '203', '规格', '1', '2020-10-12 12:09:19', '2020-10-12 12:09:19');
INSERT INTO `tb_goods_specification` VALUES ('1301', '203', '温度', '2', '2020-10-12 12:09:46', '2020-10-12 12:09:46');
INSERT INTO `tb_goods_specification` VALUES ('1302', '203', '甜度', '3', '2020-10-12 12:10:15', '2020-10-12 12:10:15');
INSERT INTO `tb_goods_specification` VALUES ('1307', '204', '规格', '1', '2020-10-12 12:12:50', '2020-10-12 12:12:50');
INSERT INTO `tb_goods_specification` VALUES ('1308', '204', '温度', '2', '2020-10-12 12:12:58', '2020-10-12 12:12:58');
INSERT INTO `tb_goods_specification` VALUES ('1309', '204', '甜度', '3', '2020-10-12 12:13:07', '2020-10-12 12:13:07');
INSERT INTO `tb_goods_specification` VALUES ('1314', '205', '规格', '1', '2020-10-12 12:16:03', '2020-10-12 12:16:03');
INSERT INTO `tb_goods_specification` VALUES ('1315', '205', '温度', '2', '2020-10-12 12:16:14', '2020-10-12 12:16:14');
INSERT INTO `tb_goods_specification` VALUES ('1316', '205', '甜度', '3', '2020-10-12 12:16:25', '2020-10-12 12:16:25');
INSERT INTO `tb_goods_specification` VALUES ('1321', '206', '规格', '1', '2020-10-13 15:05:01', '2020-10-13 15:05:01');
INSERT INTO `tb_goods_specification` VALUES ('1322', '206', '温度', '2', '2020-10-13 15:05:15', '2020-10-13 15:05:15');
INSERT INTO `tb_goods_specification` VALUES ('1323', '206', '甜度', '3', '2020-10-13 15:05:22', '2020-10-13 15:05:22');
INSERT INTO `tb_goods_specification` VALUES ('1328', '207', '规格', '1', '2020-10-13 15:07:43', '2020-10-13 15:07:43');
INSERT INTO `tb_goods_specification` VALUES ('1329', '207', '温度', '2', '2020-10-13 15:07:50', '2020-10-13 15:07:50');
INSERT INTO `tb_goods_specification` VALUES ('1330', '207', '甜度', '3', '2020-10-13 15:07:55', '2020-10-13 15:07:55');
INSERT INTO `tb_goods_specification` VALUES ('1335', '208', '规格', '1', '2020-10-13 15:11:23', '2020-10-13 15:11:23');
INSERT INTO `tb_goods_specification` VALUES ('1336', '208', '温度', '2', '2020-10-13 15:11:27', '2020-10-13 15:11:27');
INSERT INTO `tb_goods_specification` VALUES ('1337', '208', '甜度', '3', '2020-10-13 15:11:32', '2020-10-13 15:11:32');
INSERT INTO `tb_goods_specification` VALUES ('1342', '209', '规格', '1', '2020-10-13 15:20:06', '2020-10-13 15:20:06');
INSERT INTO `tb_goods_specification` VALUES ('1343', '209', '温度', '2', '2020-10-13 15:20:10', '2020-10-13 15:20:10');
INSERT INTO `tb_goods_specification` VALUES ('1344', '209', '甜度', '3', '2020-10-13 15:21:38', '2020-10-13 15:21:38');
INSERT INTO `tb_goods_specification` VALUES ('1349', '210', '规格', '1', '2020-10-13 15:23:55', '2020-10-13 15:23:55');
INSERT INTO `tb_goods_specification` VALUES ('1350', '210', '温度', '2', '2020-10-13 15:24:13', '2020-10-13 15:24:13');
INSERT INTO `tb_goods_specification` VALUES ('1351', '210', '甜度', '3', '2020-10-13 15:24:29', '2020-10-13 15:24:29');
INSERT INTO `tb_goods_specification` VALUES ('1352', '211', '加料', '1', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification` VALUES ('1353', '211', '温度', '2', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification` VALUES ('1354', '211', '糖度', '3', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification` VALUES ('1355', '211', '规格', '4', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification` VALUES ('1360', '212', '规格', '1', '2020-10-13 15:29:18', '2020-10-13 15:29:18');
INSERT INTO `tb_goods_specification` VALUES ('1361', '212', '温度', '2', '2020-10-13 15:29:21', '2020-10-13 15:29:21');
INSERT INTO `tb_goods_specification` VALUES ('1362', '212', '甜度', '3', '2020-10-13 15:29:26', '2020-10-13 15:29:26');
INSERT INTO `tb_goods_specification` VALUES ('1367', '213', '规格', '1', '2020-10-13 15:31:45', '2020-10-13 15:31:45');
INSERT INTO `tb_goods_specification` VALUES ('1368', '213', '温度', '2', '2020-10-13 15:31:50', '2020-10-13 15:31:50');
INSERT INTO `tb_goods_specification` VALUES ('1369', '213', '甜度', '3', '2020-10-13 15:32:00', '2020-10-13 15:32:00');
INSERT INTO `tb_goods_specification` VALUES ('1374', '214', '规格', '1', '2020-10-13 15:35:26', '2020-10-13 15:35:26');
INSERT INTO `tb_goods_specification` VALUES ('1375', '214', '温度', '2', '2020-10-13 15:35:30', '2020-10-13 15:35:30');
INSERT INTO `tb_goods_specification` VALUES ('1376', '214', '甜度', '3', '2020-10-13 15:35:53', '2020-10-13 15:35:53');
INSERT INTO `tb_goods_specification` VALUES ('1381', '215', '规格', '1', '2020-10-13 15:38:24', '2020-10-13 15:38:24');
INSERT INTO `tb_goods_specification` VALUES ('1382', '215', '温度', '2', '2020-10-13 15:38:34', '2020-10-13 15:38:34');
INSERT INTO `tb_goods_specification` VALUES ('1383', '215', '甜度', '3', '2020-10-13 15:38:46', '2020-10-13 15:38:46');
INSERT INTO `tb_goods_specification` VALUES ('1388', '216', '规格', '1', '2020-10-13 15:41:35', '2020-10-13 15:41:35');
INSERT INTO `tb_goods_specification` VALUES ('1389', '216', '温度', '2', '2020-10-13 15:41:42', '2020-10-13 15:41:42');
INSERT INTO `tb_goods_specification` VALUES ('1394', '217', '规格', '1', '2020-10-13 15:45:00', '2020-10-13 15:45:00');
INSERT INTO `tb_goods_specification` VALUES ('1395', '217', '温度', '2', '2020-10-13 15:45:11', '2020-10-13 15:45:11');
INSERT INTO `tb_goods_specification` VALUES ('1396', '217', '添加', '3', '2020-10-13 15:45:52', '2020-10-13 15:45:52');
INSERT INTO `tb_goods_specification` VALUES ('1401', '218', '规格', '1', '2020-10-13 15:52:10', '2020-10-13 15:52:10');
INSERT INTO `tb_goods_specification` VALUES ('1402', '218', '温度', '2', '2020-10-13 15:52:15', '2020-10-13 15:52:15');
INSERT INTO `tb_goods_specification` VALUES ('1403', '218', '甜度', '3', '2020-10-13 15:53:10', '2020-10-13 15:53:10');
INSERT INTO `tb_goods_specification` VALUES ('1408', '219', '规格', '1', '2020-10-13 16:02:37', '2020-10-13 16:02:37');
INSERT INTO `tb_goods_specification` VALUES ('1409', '219', '温度', '2', '2020-10-13 16:02:45', '2020-10-13 16:02:45');
INSERT INTO `tb_goods_specification` VALUES ('1410', '219', '甜度', '3', '2020-10-13 16:02:50', '2020-10-13 16:02:50');
INSERT INTO `tb_goods_specification` VALUES ('1415', '220', '规格', '1', '2020-10-13 16:05:09', '2020-10-13 16:05:09');
INSERT INTO `tb_goods_specification` VALUES ('1416', '220', '温度', '2', '2020-10-13 16:05:18', '2020-10-13 16:05:18');
INSERT INTO `tb_goods_specification` VALUES ('1417', '220', '甜度', '3', '2020-10-13 16:05:27', '2020-10-13 16:05:27');
INSERT INTO `tb_goods_specification` VALUES ('1422', '221', '规格', '1', '2020-10-13 16:16:21', '2020-10-13 16:16:21');
INSERT INTO `tb_goods_specification` VALUES ('1423', '221', '温度', '2', '2020-10-13 16:16:34', '2020-10-13 16:16:34');
INSERT INTO `tb_goods_specification` VALUES ('1424', '221', '添加', '3', '2020-10-13 16:17:23', '2020-10-13 16:17:23');
INSERT INTO `tb_goods_specification` VALUES ('1429', '222', '规格', '1', '2020-10-13 16:20:37', '2020-10-13 16:20:37');
INSERT INTO `tb_goods_specification` VALUES ('1430', '222', '温度', '2', '2020-10-13 16:20:42', '2020-10-13 16:20:42');
INSERT INTO `tb_goods_specification` VALUES ('1431', '222', '甜度', '3', '2020-10-13 16:20:54', '2020-10-13 16:20:54');
INSERT INTO `tb_goods_specification` VALUES ('1436', '223', '规格', '1', '2020-10-13 16:23:46', '2020-10-13 16:23:46');
INSERT INTO `tb_goods_specification` VALUES ('1437', '223', '温度', '2', '2020-10-13 16:23:50', '2020-10-13 16:23:50');
INSERT INTO `tb_goods_specification` VALUES ('1438', '223', '甜度', '3', '2020-10-13 16:23:59', '2020-10-13 16:23:59');
INSERT INTO `tb_goods_specification` VALUES ('1439', '101', '主料', '3', '2020-10-14 13:10:41', '2020-10-14 13:10:41');
INSERT INTO `tb_goods_specification` VALUES ('1440', '101', '辅料', '4', '2020-10-14 13:12:40', '2020-10-14 13:12:40');
INSERT INTO `tb_goods_specification` VALUES ('1441', '101', '甜度', '5', '2020-10-14 13:13:26', '2020-10-14 13:13:26');
INSERT INTO `tb_goods_specification` VALUES ('1442', '183', '口味', '3', '2020-10-14 18:03:18', '2020-10-14 18:03:18');
INSERT INTO `tb_goods_specification` VALUES ('1443', '143', '规格', '1', '2020-10-14 18:11:27', '2020-10-14 18:11:27');
INSERT INTO `tb_goods_specification` VALUES ('1444', '143', '温度', '2', '2020-10-14 18:11:36', '2020-10-14 18:11:36');
INSERT INTO `tb_goods_specification` VALUES ('1446', '142', '规格', '1', '2020-10-14 18:13:04', '2020-10-14 18:13:04');
INSERT INTO `tb_goods_specification` VALUES ('1447', '142', '温度', '2', '2020-10-14 18:13:24', '2020-10-14 18:13:24');
INSERT INTO `tb_goods_specification` VALUES ('1448', '141', '规格', '1', '2020-10-14 18:14:01', '2020-10-14 18:14:01');
INSERT INTO `tb_goods_specification` VALUES ('1449', '141', '温度', '2', '2020-10-14 18:14:11', '2020-10-14 18:14:11');
INSERT INTO `tb_goods_specification` VALUES ('1450', '140', '规格', '1', '2020-10-14 18:14:57', '2020-10-14 18:14:57');
INSERT INTO `tb_goods_specification` VALUES ('1451', '140', '温度', '2', '2020-10-14 18:15:05', '2020-10-14 18:15:05');
INSERT INTO `tb_goods_specification` VALUES ('1452', '139', '规格', '1', '2020-10-14 18:15:38', '2020-10-14 18:15:38');
INSERT INTO `tb_goods_specification` VALUES ('1453', '139', '温度', '2', '2020-10-14 18:15:46', '2020-10-14 18:15:46');
INSERT INTO `tb_goods_specification` VALUES ('1454', '138', '规格', '1', '2020-10-14 18:16:22', '2020-10-14 18:16:22');
INSERT INTO `tb_goods_specification` VALUES ('1455', '138', '温度', '2', '2020-10-14 18:17:40', '2020-10-14 18:17:40');
INSERT INTO `tb_goods_specification` VALUES ('1456', '137', '规格', '1', '2020-10-14 18:18:25', '2020-10-14 18:18:25');
INSERT INTO `tb_goods_specification` VALUES ('1457', '137', '温度', '2', '2020-10-14 18:18:33', '2020-10-14 18:18:33');
INSERT INTO `tb_goods_specification` VALUES ('1462', '224', '规格', '1', '2020-10-15 23:36:16', '2020-10-15 23:36:16');
INSERT INTO `tb_goods_specification` VALUES ('1463', '224', '温度', '2', '2020-10-15 23:36:28', '2020-10-15 23:36:28');
INSERT INTO `tb_goods_specification` VALUES ('1468', '225', '规格', '1', '2020-10-15 23:41:55', '2020-10-15 23:41:55');
INSERT INTO `tb_goods_specification` VALUES ('1469', '225', '温度', '2', '2020-10-15 23:41:59', '2020-10-15 23:41:59');
INSERT INTO `tb_goods_specification` VALUES ('1470', '225', '甜度', '3', '2020-10-15 23:42:29', '2020-10-15 23:42:29');
INSERT INTO `tb_goods_specification` VALUES ('1471', '224', '甜度', '3', '2020-10-15 23:42:50', '2020-10-15 23:42:50');
INSERT INTO `tb_goods_specification` VALUES ('1476', '226', '规格', '1', '2020-10-16 00:23:34', '2020-10-16 00:23:34');
INSERT INTO `tb_goods_specification` VALUES ('1477', '226', '温度', '2', '2020-10-16 00:23:46', '2020-10-16 00:23:46');
INSERT INTO `tb_goods_specification` VALUES ('1478', '226', '甜度', '3', '2020-10-16 00:23:56', '2020-10-16 00:23:56');
INSERT INTO `tb_goods_specification` VALUES ('1491', '230', '加料', '1', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification` VALUES ('1492', '230', '温度', '2', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification` VALUES ('1493', '230', '糖度', '3', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification` VALUES ('1494', '230', '规格', '4', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification` VALUES ('1499', '237', '加料', '1', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification` VALUES ('1500', '237', '温度', '2', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification` VALUES ('1501', '237', '糖度', '3', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification` VALUES ('1502', '237', '规格', '4', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification` VALUES ('1507', '239', '加料', '1', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification` VALUES ('1508', '239', '温度', '2', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification` VALUES ('1509', '239', '糖度', '3', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification` VALUES ('1510', '239', '规格', '4', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification` VALUES ('1511', '240', '加料', '1', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification` VALUES ('1512', '240', '温度', '2', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification` VALUES ('1513', '240', '糖度', '3', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification` VALUES ('1514', '240', '规格', '4', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification` VALUES ('1515', '241', '加料', '1', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification` VALUES ('1516', '241', '温度', '2', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification` VALUES ('1517', '241', '糖度', '3', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification` VALUES ('1518', '241', '规格', '4', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification` VALUES ('1519', '242', '加料', '1', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification` VALUES ('1520', '242', '温度', '2', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification` VALUES ('1521', '242', '糖度', '3', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification` VALUES ('1522', '242', '规格', '4', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification` VALUES ('1523', '243', '加料', '1', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification` VALUES ('1524', '243', '温度', '2', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification` VALUES ('1525', '243', '糖度', '3', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification` VALUES ('1526', '243', '规格', '4', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification` VALUES ('1527', '244', '加料', '1', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification` VALUES ('1528', '244', '温度', '2', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification` VALUES ('1529', '244', '糖度', '3', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification` VALUES ('1530', '244', '规格', '4', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification` VALUES ('1531', '245', '加料', '1', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification` VALUES ('1532', '245', '温度', '2', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification` VALUES ('1533', '245', '糖度', '3', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification` VALUES ('1534', '245', '规格', '4', '2021-04-25 19:36:31', '2021-04-25 19:36:31');
INSERT INTO `tb_goods_specification` VALUES ('1535', '246', '加料', '1', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification` VALUES ('1536', '246', '温度', '2', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification` VALUES ('1537', '246', '糖度', '3', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification` VALUES ('1538', '246', '规格', '4', '2021-04-25 19:40:49', '2021-04-25 19:40:49');
INSERT INTO `tb_goods_specification` VALUES ('1539', '247', '加料', '1', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification` VALUES ('1540', '247', '温度', '2', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification` VALUES ('1541', '247', '糖度', '3', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification` VALUES ('1542', '247', '规格', '4', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification` VALUES ('1543', '248', '加料', '1', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification` VALUES ('1544', '248', '温度', '2', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification` VALUES ('1545', '248', '糖度', '3', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification` VALUES ('1546', '248', '规格', '4', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification` VALUES ('1547', '249', '加料', '1', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification` VALUES ('1548', '249', '温度', '2', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification` VALUES ('1549', '249', '糖度', '3', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification` VALUES ('1550', '249', '规格', '4', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification` VALUES ('1551', '250', '加料', '1', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification` VALUES ('1552', '250', '温度', '2', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification` VALUES ('1553', '250', '糖度', '3', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification` VALUES ('1554', '250', '规格', '4', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification` VALUES ('1555', '251', '加料', '1', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification` VALUES ('1556', '251', '温度', '2', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification` VALUES ('1557', '251', '糖度', '3', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification` VALUES ('1558', '251', '规格', '4', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification` VALUES ('1559', '252', '加料', '1', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification` VALUES ('1560', '252', '温度', '2', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification` VALUES ('1561', '252', '糖度', '3', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification` VALUES ('1562', '252', '规格', '4', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification` VALUES ('1563', '253', '加料', '1', '2021-04-25 20:38:26', '2021-04-25 20:38:26');
INSERT INTO `tb_goods_specification` VALUES ('1564', '253', '温度', '2', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification` VALUES ('1565', '253', '糖度', '3', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification` VALUES ('1566', '253', '规格', '4', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification` VALUES ('1567', '254', '加料', '1', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification` VALUES ('1568', '254', '温度', '2', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification` VALUES ('1569', '254', '糖度', '3', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification` VALUES ('1570', '254', '规格', '4', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification` VALUES ('1571', '255', '加料', '1', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification` VALUES ('1572', '255', '温度', '2', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification` VALUES ('1573', '255', '糖度', '3', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification` VALUES ('1574', '255', '规格', '4', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification` VALUES ('1575', '256', '加料', '1', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification` VALUES ('1576', '256', '温度', '2', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification` VALUES ('1577', '256', '糖度', '3', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification` VALUES ('1578', '256', '规格', '4', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification` VALUES ('1579', '257', '加料', '1', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification` VALUES ('1580', '257', '温度', '2', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification` VALUES ('1581', '257', '糖度', '3', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification` VALUES ('1582', '257', '规格', '4', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification` VALUES ('1583', '258', '加料', '1', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification` VALUES ('1584', '258', '温度', '2', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification` VALUES ('1585', '258', '糖度', '3', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification` VALUES ('1586', '258', '规格', '4', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification` VALUES ('1587', '259', '加料', '1', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification` VALUES ('1588', '259', '温度', '2', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification` VALUES ('1589', '259', '糖度', '3', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification` VALUES ('1590', '259', '规格', '4', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification` VALUES ('1591', '260', '加料', '1', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification` VALUES ('1592', '260', '温度', '2', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification` VALUES ('1593', '260', '糖度', '3', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification` VALUES ('1594', '260', '规格', '4', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification` VALUES ('1595', '261', '加料', '1', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification` VALUES ('1596', '261', '温度', '2', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification` VALUES ('1597', '261', '糖度', '3', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification` VALUES ('1598', '261', '规格', '4', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification` VALUES ('1600', '262', '温度', '2', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_goods_specification` VALUES ('1601', '262', '糖度', '3', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_goods_specification` VALUES ('1602', '262', '规格', '4', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_goods_specification` VALUES ('1604', '263', '温度', '2', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification` VALUES ('1605', '263', '糖度', '3', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification` VALUES ('1606', '263', '规格', '4', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification` VALUES ('1608', '264', '温度', '2', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification` VALUES ('1609', '264', '糖度', '3', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification` VALUES ('1610', '264', '规格', '4', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification` VALUES ('1612', '265', '温度', '2', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification` VALUES ('1613', '265', '糖度', '3', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification` VALUES ('1614', '265', '规格', '4', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification` VALUES ('1616', '266', '温度', '2', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification` VALUES ('1617', '266', '糖度', '3', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification` VALUES ('1618', '266', '规格', '4', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification` VALUES ('1620', '267', '温度', '2', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification` VALUES ('1621', '267', '糖度', '3', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification` VALUES ('1622', '267', '规格', '4', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification` VALUES ('1624', '268', '温度', '2', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification` VALUES ('1625', '268', '糖度', '3', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification` VALUES ('1626', '268', '规格', '4', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification` VALUES ('1628', '269', '温度', '2', '2021-07-01 11:47:43', '2021-07-01 11:47:43');
INSERT INTO `tb_goods_specification` VALUES ('1632', '270', '温度', '2', '2021-07-01 11:55:32', '2021-07-01 11:55:32');
INSERT INTO `tb_goods_specification` VALUES ('1634', '270', '规格', '4', '2021-07-01 11:55:32', '2021-07-01 11:55:32');
INSERT INTO `tb_goods_specification` VALUES ('1636', '271', '温度', '2', '2021-07-01 13:01:06', '2021-07-01 13:01:06');
INSERT INTO `tb_goods_specification` VALUES ('1638', '271', '规格', '4', '2021-07-01 13:01:07', '2021-07-01 13:01:07');
INSERT INTO `tb_goods_specification` VALUES ('1652', '274', '规格', '1', '2021-07-01 18:33:17', '2021-07-01 18:33:17');
INSERT INTO `tb_goods_specification` VALUES ('1653', '274', '口味', '2', '2021-07-01 18:33:25', '2021-07-01 18:33:25');
INSERT INTO `tb_goods_specification` VALUES ('1654', '273', '温度', '1', '2021-07-01 18:34:38', '2021-07-01 18:34:38');
INSERT INTO `tb_goods_specification` VALUES ('1655', '273', '甜度', '2', '2021-07-01 18:35:11', '2021-07-01 18:35:11');
INSERT INTO `tb_goods_specification` VALUES ('1657', '272', '温度', '2', '2021-07-01 18:37:03', '2021-07-01 18:37:03');
INSERT INTO `tb_goods_specification` VALUES ('1658', '272', '甜度', '3', '2021-07-01 18:37:40', '2021-07-01 18:37:40');
INSERT INTO `tb_goods_specification` VALUES ('1659', '271', '甜度', '5', '2021-07-01 20:42:12', '2021-07-01 20:42:12');
INSERT INTO `tb_goods_specification` VALUES ('1660', '269', '甜度', '3', '2021-07-01 20:45:17', '2021-07-01 20:45:17');
INSERT INTO `tb_goods_specification` VALUES ('1661', '235', '添加', '1', '2021-07-01 20:50:03', '2021-07-01 20:50:03');
INSERT INTO `tb_goods_specification` VALUES ('1662', '234', '温度', '1', '2021-07-01 20:51:09', '2021-07-01 20:51:09');
INSERT INTO `tb_goods_specification` VALUES ('1663', '234', '甜度', '2', '2021-07-01 20:51:26', '2021-07-01 20:51:26');
INSERT INTO `tb_goods_specification` VALUES ('1664', '233', '温度', '1', '2021-07-01 20:51:49', '2021-07-01 20:51:49');
INSERT INTO `tb_goods_specification` VALUES ('1665', '233', '甜度', '2', '2021-07-01 21:10:16', '2021-07-01 21:10:16');
INSERT INTO `tb_goods_specification` VALUES ('1666', '232', '温度', '1', '2021-07-01 21:11:16', '2021-07-01 21:11:16');
INSERT INTO `tb_goods_specification` VALUES ('1667', '232', '甜度', '2', '2021-07-01 21:11:44', '2021-07-01 21:11:44');
INSERT INTO `tb_goods_specification` VALUES ('1668', '231', '温度', '1', '2021-07-01 21:12:27', '2021-07-01 21:12:27');
INSERT INTO `tb_goods_specification` VALUES ('1669', '231', '甜度', '2', '2021-07-01 21:13:06', '2021-07-01 21:13:06');
INSERT INTO `tb_goods_specification` VALUES ('1670', '229', '温度', '1', '2021-07-01 21:14:01', '2021-07-01 21:14:01');
INSERT INTO `tb_goods_specification` VALUES ('1671', '229', '甜度', '2', '2021-07-01 21:14:23', '2021-07-01 21:14:23');
INSERT INTO `tb_goods_specification` VALUES ('1672', '228', '规格', '1', '2021-07-01 21:14:34', '2021-07-01 21:14:34');
INSERT INTO `tb_goods_specification` VALUES ('1673', '228', '温度', '2', '2021-07-01 21:14:47', '2021-07-01 21:14:47');
INSERT INTO `tb_goods_specification` VALUES ('1674', '228', '甜度', '3', '2021-07-01 21:15:02', '2021-07-01 21:15:02');
INSERT INTO `tb_goods_specification` VALUES ('1675', '227', '规格', '1', '2021-07-01 21:15:25', '2021-07-01 21:15:25');
INSERT INTO `tb_goods_specification` VALUES ('1676', '227', '温度', '2', '2021-07-01 21:15:39', '2021-07-01 21:15:39');
INSERT INTO `tb_goods_specification` VALUES ('1677', '227', '甜度', '3', '2021-07-01 21:15:53', '2021-07-01 21:15:53');

-- ----------------------------
-- Table structure for tb_goods_specification_option
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_specification_option`;
CREATE TABLE `tb_goods_specification_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_specification_id` int(11) NOT NULL COMMENT '商品规格id',
  `name` varchar(10) NOT NULL COMMENT '商品规格选项名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '单价/加价金额',
  `stock` int(11) DEFAULT '1' COMMENT '库存 1=有货 2=无货',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5274 DEFAULT CHARSET=utf8 COMMENT='商品规格选项表';

-- ----------------------------
-- Records of tb_goods_specification_option
-- ----------------------------
INSERT INTO `tb_goods_specification_option` VALUES ('587', '28', '141', '中杯', '0.00', '1', '2', '2020-03-23 17:25:02', '2020-03-23 17:25:02');
INSERT INTO `tb_goods_specification_option` VALUES ('588', '28', '141', '大杯', '2.00', '1', '3', '2020-03-23 17:25:39', '2020-03-23 17:25:39');
INSERT INTO `tb_goods_specification_option` VALUES ('589', '28', '294', '不加', '0.00', '1', '1', '2020-03-23 17:26:12', '2020-03-23 17:26:12');
INSERT INTO `tb_goods_specification_option` VALUES ('590', '28', '294', '珍珠', '1.00', '1', '2', '2020-03-23 17:26:24', '2020-03-23 17:26:24');
INSERT INTO `tb_goods_specification_option` VALUES ('591', '33', '181', '中杯/热', '0.00', '1', '1', '2020-03-24 11:52:53', '2020-03-24 11:52:53');
INSERT INTO `tb_goods_specification_option` VALUES ('620', '38', '190', '大杯', '0.00', '1', '1', '2020-03-24 12:43:24', '2020-03-24 12:43:24');
INSERT INTO `tb_goods_specification_option` VALUES ('624', '39', '194', '冰', '0.00', '1', '1', '2020-03-24 12:46:12', '2020-03-24 12:46:12');
INSERT INTO `tb_goods_specification_option` VALUES ('625', '39', '194', '热', '0.00', '1', '2', '2020-03-24 12:46:31', '2020-03-24 12:46:31');
INSERT INTO `tb_goods_specification_option` VALUES ('626', '39', '289', '珍珠', '1.00', '1', '3', '2020-03-24 12:53:14', '2020-03-24 12:53:14');
INSERT INTO `tb_goods_specification_option` VALUES ('627', '39', '289', '红豆', '1.00', '1', '4', '2020-03-24 12:53:54', '2020-03-24 12:53:54');
INSERT INTO `tb_goods_specification_option` VALUES ('628', '39', '289', '椰果', '1.00', '1', '5', '2020-03-24 18:10:28', '2020-03-24 18:10:28');
INSERT INTO `tb_goods_specification_option` VALUES ('629', '39', '289', '花生', '1.00', '1', '1', '2020-03-24 18:11:09', '2020-03-24 18:11:09');
INSERT INTO `tb_goods_specification_option` VALUES ('661', '41', '204', '冰', '0.00', '1', '1', '2020-04-05 00:15:47', '2020-04-05 00:15:47');
INSERT INTO `tb_goods_specification_option` VALUES ('664', '41', '204', '去冰', '0.00', '1', '2', '2020-04-05 00:16:32', '2020-04-05 00:16:32');
INSERT INTO `tb_goods_specification_option` VALUES ('665', '41', '204', '热', '0.00', '1', '3', '2020-04-05 00:16:41', '2020-04-05 00:16:41');
INSERT INTO `tb_goods_specification_option` VALUES ('666', '41', '205', '不加糖', '0.00', '1', '1', '2020-04-05 00:16:55', '2020-04-05 00:16:55');
INSERT INTO `tb_goods_specification_option` VALUES ('667', '41', '205', '正常糖', '0.00', '1', '2', '2020-04-05 00:17:09', '2020-04-05 00:17:09');
INSERT INTO `tb_goods_specification_option` VALUES ('850', '53', '262', '中杯', '0.00', '1', '1', '2020-05-11 13:02:35', '2020-05-11 13:02:35');
INSERT INTO `tb_goods_specification_option` VALUES ('851', '53', '263', '冰', '0.00', '1', '1', '2020-05-11 13:02:54', '2020-05-11 13:02:54');
INSERT INTO `tb_goods_specification_option` VALUES ('852', '53', '264', '正常糖', '0.00', '1', '1', '2020-05-11 13:03:19', '2020-05-11 13:03:19');
INSERT INTO `tb_goods_specification_option` VALUES ('853', '52', '265', '中杯', '0.00', '1', '1', '2020-05-11 13:03:38', '2020-05-11 13:03:38');
INSERT INTO `tb_goods_specification_option` VALUES ('854', '52', '266', '冰', '0.00', '1', '1', '2020-05-11 13:03:49', '2020-05-11 13:03:49');
INSERT INTO `tb_goods_specification_option` VALUES ('855', '52', '267', '正常糖', '0.00', '1', '1', '2020-05-11 13:03:59', '2020-05-11 13:03:59');
INSERT INTO `tb_goods_specification_option` VALUES ('856', '51', '268', '冰', '0.00', '1', '1', '2020-05-11 13:04:23', '2020-05-11 13:04:23');
INSERT INTO `tb_goods_specification_option` VALUES ('857', '51', '268', '热', '0.00', '1', '2', '2020-05-11 13:04:32', '2020-05-11 13:04:32');
INSERT INTO `tb_goods_specification_option` VALUES ('858', '51', '269', '不加', '0.00', '1', '1', '2020-05-11 13:04:48', '2020-05-11 13:04:48');
INSERT INTO `tb_goods_specification_option` VALUES ('859', '51', '269', '椰果', '1.00', '1', '2', '2020-05-11 13:04:56', '2020-05-11 13:04:56');
INSERT INTO `tb_goods_specification_option` VALUES ('860', '51', '269', '红豆', '1.00', '1', '3', '2020-05-11 13:05:03', '2020-05-11 13:05:03');
INSERT INTO `tb_goods_specification_option` VALUES ('861', '51', '269', '花生', '1.00', '1', '4', '2020-05-11 13:05:16', '2020-05-11 13:05:16');
INSERT INTO `tb_goods_specification_option` VALUES ('862', '51', '269', '葡萄', '1.00', '1', '5', '2020-05-11 13:05:29', '2020-05-11 13:05:29');
INSERT INTO `tb_goods_specification_option` VALUES ('863', '51', '269', '布丁', '2.00', '1', '6', '2020-05-11 13:05:41', '2020-05-11 13:05:41');
INSERT INTO `tb_goods_specification_option` VALUES ('864', '51', '269', '珍珠', '1.00', '1', '7', '2020-05-11 13:06:34', '2020-05-11 13:06:34');
INSERT INTO `tb_goods_specification_option` VALUES ('865', '51', '270', '正常糖', '0.00', '1', '1', '2020-05-11 13:07:02', '2020-05-11 13:07:02');
INSERT INTO `tb_goods_specification_option` VALUES ('868', '49', '273', '冰', '0.00', '1', '1', '2020-05-11 13:22:49', '2020-05-11 13:22:49');
INSERT INTO `tb_goods_specification_option` VALUES ('869', '49', '273', '热', '0.00', '1', '2', '2020-05-11 13:22:55', '2020-05-11 13:22:55');
INSERT INTO `tb_goods_specification_option` VALUES ('870', '49', '274', '不加', '0.00', '1', '1', '2020-05-11 13:23:07', '2020-05-11 13:23:07');
INSERT INTO `tb_goods_specification_option` VALUES ('871', '49', '274', '珍珠', '1.00', '1', '2', '2020-05-11 13:23:19', '2020-05-11 13:23:19');
INSERT INTO `tb_goods_specification_option` VALUES ('872', '49', '274', '红豆', '1.00', '1', '3', '2020-05-11 13:23:27', '2020-05-11 13:23:27');
INSERT INTO `tb_goods_specification_option` VALUES ('873', '49', '274', '椰果', '1.00', '1', '4', '2020-05-11 13:23:34', '2020-05-11 13:23:34');
INSERT INTO `tb_goods_specification_option` VALUES ('874', '49', '274', '葡萄', '1.00', '1', '5', '2020-05-11 13:23:48', '2020-05-11 13:23:48');
INSERT INTO `tb_goods_specification_option` VALUES ('875', '49', '274', '花生', '1.00', '1', '6', '2020-05-11 13:23:57', '2020-05-11 13:23:57');
INSERT INTO `tb_goods_specification_option` VALUES ('876', '49', '274', '布丁', '2.00', '1', '7', '2020-05-11 13:24:05', '2020-05-11 13:24:05');
INSERT INTO `tb_goods_specification_option` VALUES ('877', '49', '275', '正常糖', '0.00', '1', '1', '2020-05-11 13:24:25', '2020-05-11 13:24:25');
INSERT INTO `tb_goods_specification_option` VALUES ('878', '48', '276', '大杯', '0.00', '1', '1', '2020-05-11 18:05:51', '2020-05-11 18:05:51');
INSERT INTO `tb_goods_specification_option` VALUES ('879', '48', '277', '冰', '0.00', '1', '1', '2020-05-11 18:06:00', '2020-05-11 18:06:00');
INSERT INTO `tb_goods_specification_option` VALUES ('880', '48', '278', '正常糖', '0.00', '1', '1', '2020-05-11 18:06:12', '2020-05-11 18:06:12');
INSERT INTO `tb_goods_specification_option` VALUES ('881', '47', '279', '中杯', '0.00', '1', '1', '2020-05-11 18:06:38', '2020-05-11 18:06:38');
INSERT INTO `tb_goods_specification_option` VALUES ('882', '47', '280', '冰', '0.00', '1', '1', '2020-05-11 18:06:49', '2020-05-11 18:06:49');
INSERT INTO `tb_goods_specification_option` VALUES ('883', '47', '280', '热', '0.00', '1', '2', '2020-05-11 18:06:59', '2020-05-11 18:06:59');
INSERT INTO `tb_goods_specification_option` VALUES ('884', '47', '281', '常规糖', '0.00', '1', '1', '2020-05-11 18:07:09', '2020-05-11 18:07:09');
INSERT INTO `tb_goods_specification_option` VALUES ('885', '46', '282', '约500ML', '0.00', '1', '1', '2020-05-11 18:07:55', '2020-05-11 18:07:55');
INSERT INTO `tb_goods_specification_option` VALUES ('886', '46', '283', '冰', '0.00', '1', '1', '2020-05-11 18:08:03', '2020-05-11 18:08:03');
INSERT INTO `tb_goods_specification_option` VALUES ('887', '46', '284', '正常糖', '0.00', '1', '1', '2020-05-11 18:08:15', '2020-05-11 18:08:15');
INSERT INTO `tb_goods_specification_option` VALUES ('888', '45', '285', '中杯', '0.00', '1', '1', '2020-05-11 18:08:44', '2020-05-11 18:08:44');
INSERT INTO `tb_goods_specification_option` VALUES ('891', '39', '289', '布丁', '2.00', '1', '6', '2020-05-11 18:11:35', '2020-05-11 18:11:35');
INSERT INTO `tb_goods_specification_option` VALUES ('892', '39', '289', '葡萄', '1.00', '1', '7', '2020-05-11 18:11:44', '2020-05-11 18:11:44');
INSERT INTO `tb_goods_specification_option` VALUES ('893', '39', '289', '不加', '0.00', '1', '1', '2020-05-11 18:11:51', '2020-05-11 18:11:51');
INSERT INTO `tb_goods_specification_option` VALUES ('894', '45', '291', '红豆', '0.00', '1', '1', '2020-05-11 18:12:38', '2020-05-11 18:12:38');
INSERT INTO `tb_goods_specification_option` VALUES ('895', '45', '291', '椰果', '0.00', '1', '2', '2020-05-11 18:12:44', '2020-05-11 18:12:44');
INSERT INTO `tb_goods_specification_option` VALUES ('896', '45', '291', '珍珠', '0.00', '1', '3', '2020-05-11 18:12:52', '2020-05-11 18:12:52');
INSERT INTO `tb_goods_specification_option` VALUES ('897', '45', '292', '冰', '0.00', '1', '1', '2020-05-11 18:12:58', '2020-05-11 18:12:58');
INSERT INTO `tb_goods_specification_option` VALUES ('898', '45', '293', '正常糖', '0.00', '1', '1', '2020-05-11 18:13:10', '2020-05-11 18:13:10');
INSERT INTO `tb_goods_specification_option` VALUES ('899', '39', '290', '正常糖', '0.00', '1', '1', '2020-05-11 18:14:48', '2020-05-11 18:14:48');
INSERT INTO `tb_goods_specification_option` VALUES ('900', '28', '294', '红豆', '1.00', '1', '3', '2020-05-11 18:17:15', '2020-05-11 18:17:15');
INSERT INTO `tb_goods_specification_option` VALUES ('901', '28', '294', '椰果', '1.00', '1', '4', '2020-05-11 18:17:20', '2020-05-11 18:17:20');
INSERT INTO `tb_goods_specification_option` VALUES ('902', '28', '294', '花生', '1.00', '1', '5', '2020-05-11 18:17:28', '2020-05-11 18:17:28');
INSERT INTO `tb_goods_specification_option` VALUES ('903', '28', '294', '葡萄', '1.00', '1', '6', '2020-05-11 18:17:53', '2020-05-11 18:17:53');
INSERT INTO `tb_goods_specification_option` VALUES ('904', '28', '294', '布丁', '2.00', '1', '7', '2020-05-11 18:18:02', '2020-05-11 18:18:02');
INSERT INTO `tb_goods_specification_option` VALUES ('905', '28', '180', '正常糖', '0.00', '1', '1', '2020-05-11 18:18:20', '2020-05-11 18:18:20');
INSERT INTO `tb_goods_specification_option` VALUES ('948', '56', '307', '约250ML', '0.00', '1', '1', '2020-05-12 11:14:21', '2020-05-12 11:14:21');
INSERT INTO `tb_goods_specification_option` VALUES ('949', '56', '308', '红豆', '1.00', '1', '1', '2020-05-12 11:14:27', '2020-05-12 11:14:27');
INSERT INTO `tb_goods_specification_option` VALUES ('950', '56', '308', '椰果', '0.00', '1', '2', '2020-05-12 11:14:35', '2020-05-12 11:14:35');
INSERT INTO `tb_goods_specification_option` VALUES ('951', '56', '308', '葡萄', '1.00', '1', '3', '2020-05-12 11:14:56', '2020-05-12 11:14:56');
INSERT INTO `tb_goods_specification_option` VALUES ('952', '56', '308', '花生', '1.00', '1', '4', '2020-05-12 11:15:02', '2020-05-12 11:15:02');
INSERT INTO `tb_goods_specification_option` VALUES ('967', '57', '313', '大杯', '0.00', '1', '1', '2020-05-12 17:53:16', '2020-05-12 17:53:16');
INSERT INTO `tb_goods_specification_option` VALUES ('968', '57', '314', '冰', '0.00', '1', '1', '2020-05-12 17:53:30', '2020-05-12 17:53:30');
INSERT INTO `tb_goods_specification_option` VALUES ('983', '58', '319', '500ML', '0.00', '1', '1', '2020-05-13 10:23:40', '2020-05-13 10:23:40');
INSERT INTO `tb_goods_specification_option` VALUES ('984', '58', '320', '冰', '0.00', '1', '1', '2020-05-13 10:23:47', '2020-05-13 10:23:47');
INSERT INTO `tb_goods_specification_option` VALUES ('985', '58', '321', '正常糖', '0.00', '1', '1', '2020-05-13 10:24:01', '2020-05-13 10:24:01');
INSERT INTO `tb_goods_specification_option` VALUES ('1017', '60', '333', '中杯', '0.00', '1', '1', '2020-05-13 11:59:04', '2020-05-13 11:59:04');
INSERT INTO `tb_goods_specification_option` VALUES ('1018', '60', '334', '正常糖', '0.00', '1', '1', '2020-05-13 11:59:25', '2020-05-13 11:59:25');
INSERT INTO `tb_goods_specification_option` VALUES ('1033', '61', '339', '700ML', '0.00', '1', '1', '2020-05-13 13:07:55', '2020-05-13 13:07:55');
INSERT INTO `tb_goods_specification_option` VALUES ('1034', '61', '340', '正常糖', '0.00', '1', '1', '2020-05-13 13:08:06', '2020-05-13 13:08:06');
INSERT INTO `tb_goods_specification_option` VALUES ('1049', '62', '345', '500ML', '0.00', '1', '1', '2020-05-15 14:38:32', '2020-05-15 14:38:32');
INSERT INTO `tb_goods_specification_option` VALUES ('1050', '62', '346', '冰', '0.00', '1', '1', '2020-05-15 14:38:44', '2020-05-15 14:38:44');
INSERT INTO `tb_goods_specification_option` VALUES ('1065', '63', '351', '中杯', '0.00', '1', '1', '2020-05-15 14:51:46', '2020-05-15 14:51:46');
INSERT INTO `tb_goods_specification_option` VALUES ('1066', '63', '352', '冰', '0.00', '1', '1', '2020-05-15 14:51:54', '2020-05-15 14:51:54');
INSERT INTO `tb_goods_specification_option` VALUES ('1081', '64', '357', '大杯', '0.00', '1', '1', '2020-05-15 14:55:26', '2020-05-15 14:55:26');
INSERT INTO `tb_goods_specification_option` VALUES ('1082', '64', '358', '冰', '0.00', '1', '1', '2020-05-15 14:55:33', '2020-05-15 14:55:33');
INSERT INTO `tb_goods_specification_option` VALUES ('1097', '65', '363', '中杯', '0.00', '1', '1', '2020-05-15 14:59:04', '2020-05-15 14:59:04');
INSERT INTO `tb_goods_specification_option` VALUES ('1098', '65', '364', '冰', '0.00', '1', '1', '2020-05-15 14:59:13', '2020-05-15 14:59:13');
INSERT INTO `tb_goods_specification_option` VALUES ('1113', '66', '369', '大杯', '0.00', '1', '1', '2020-05-17 11:36:53', '2020-05-17 11:36:53');
INSERT INTO `tb_goods_specification_option` VALUES ('1114', '66', '370', '冰', '0.00', '1', '1', '2020-05-17 11:37:01', '2020-05-17 11:37:01');
INSERT INTO `tb_goods_specification_option` VALUES ('1115', '57', '314', '热', '0.00', '1', '2', '2020-05-20 12:29:47', '2020-05-20 12:29:47');
INSERT INTO `tb_goods_specification_option` VALUES ('1116', '28', '371', '冰', '0.00', '1', '1', '2020-05-20 12:30:19', '2020-05-20 12:30:19');
INSERT INTO `tb_goods_specification_option` VALUES ('1117', '28', '371', '热', '0.00', '1', '2', '2020-05-20 12:30:42', '2020-05-20 12:30:42');
INSERT INTO `tb_goods_specification_option` VALUES ('1132', '67', '376', '700ML', '0.00', '1', '1', '2020-05-20 14:39:43', '2020-05-20 14:39:43');
INSERT INTO `tb_goods_specification_option` VALUES ('1133', '67', '377', '冰', '0.00', '1', '1', '2020-05-20 14:39:50', '2020-05-20 14:39:50');
INSERT INTO `tb_goods_specification_option` VALUES ('1148', '68', '382', '大杯', '0.00', '1', '1', '2020-05-20 14:46:42', '2020-05-20 14:46:42');
INSERT INTO `tb_goods_specification_option` VALUES ('1149', '68', '383', '冰', '0.00', '1', '1', '2020-05-20 14:46:49', '2020-05-20 14:46:49');
INSERT INTO `tb_goods_specification_option` VALUES ('1164', '69', '388', '大杯', '0.00', '1', '1', '2020-05-23 14:32:06', '2020-05-23 14:32:06');
INSERT INTO `tb_goods_specification_option` VALUES ('1165', '69', '389', '冰', '0.00', '1', '1', '2020-05-23 14:32:11', '2020-05-23 14:32:11');
INSERT INTO `tb_goods_specification_option` VALUES ('1166', '69', '390', '正常糖', '0.00', '1', '1', '2020-05-23 14:32:17', '2020-05-23 14:32:17');
INSERT INTO `tb_goods_specification_option` VALUES ('1181', '70', '395', '大杯', '0.00', '1', '1', '2020-05-25 16:40:25', '2020-05-25 16:40:25');
INSERT INTO `tb_goods_specification_option` VALUES ('1182', '70', '396', '冰', '0.00', '1', '1', '2020-05-25 16:40:32', '2020-05-25 16:40:32');
INSERT INTO `tb_goods_specification_option` VALUES ('1197', '71', '401', '大杯', '0.00', '1', '1', '2020-05-28 21:43:13', '2020-05-28 21:43:13');
INSERT INTO `tb_goods_specification_option` VALUES ('1198', '71', '402', '冰', '0.00', '1', '1', '2020-05-28 21:43:24', '2020-05-28 21:43:24');
INSERT INTO `tb_goods_specification_option` VALUES ('1213', '72', '407', '中杯', '0.00', '1', '1', '2020-05-29 12:16:21', '2020-05-29 12:16:21');
INSERT INTO `tb_goods_specification_option` VALUES ('1214', '72', '408', '冰', '0.00', '1', '1', '2020-05-29 12:16:28', '2020-05-29 12:16:28');
INSERT INTO `tb_goods_specification_option` VALUES ('1229', '73', '413', '大杯', '0.00', '1', '1', '2020-06-01 15:18:05', '2020-06-01 15:18:05');
INSERT INTO `tb_goods_specification_option` VALUES ('1230', '73', '414', '冰', '0.00', '1', '1', '2020-06-01 15:18:16', '2020-06-01 15:18:16');
INSERT INTO `tb_goods_specification_option` VALUES ('1231', '64', '415', '椰果', '1.00', '1', '1', '2020-06-01 15:18:49', '2020-06-01 15:18:49');
INSERT INTO `tb_goods_specification_option` VALUES ('1232', '64', '415', '珍珠', '1.00', '1', '2', '2020-06-01 15:19:10', '2020-06-01 15:19:10');
INSERT INTO `tb_goods_specification_option` VALUES ('1233', '66', '416', '椰果', '1.00', '1', '1', '2020-06-01 15:21:58', '2020-06-01 15:21:58');
INSERT INTO `tb_goods_specification_option` VALUES ('1234', '66', '416', '珍珠', '1.00', '1', '2', '2020-06-01 15:22:08', '2020-06-01 15:22:08');
INSERT INTO `tb_goods_specification_option` VALUES ('1249', '74', '421', '大杯', '0.00', '1', '1', '2020-06-05 10:15:53', '2020-06-05 10:15:53');
INSERT INTO `tb_goods_specification_option` VALUES ('1250', '74', '422', '多冰', '0.00', '1', '1', '2020-06-05 10:17:12', '2020-06-05 10:17:12');
INSERT INTO `tb_goods_specification_option` VALUES ('1251', '63', '423', '椰果', '1.00', '1', '1', '2020-06-07 14:56:14', '2020-06-07 14:56:14');
INSERT INTO `tb_goods_specification_option` VALUES ('1252', '62', '424', '椰果', '1.00', '1', '1', '2020-06-07 14:56:34', '2020-06-07 14:56:34');
INSERT INTO `tb_goods_specification_option` VALUES ('1253', '61', '425', '椰果', '1.00', '1', '1', '2020-06-07 14:56:55', '2020-06-07 14:56:55');
INSERT INTO `tb_goods_specification_option` VALUES ('1254', '60', '426', '椰果', '1.00', '1', '1', '2020-06-07 14:57:11', '2020-06-07 14:57:11');
INSERT INTO `tb_goods_specification_option` VALUES ('1255', '56', '308', '芒果', '2.00', '1', '5', '2020-06-10 00:29:47', '2020-06-10 00:29:47');
INSERT INTO `tb_goods_specification_option` VALUES ('1270', '75', '431', '大杯', '0.00', '1', '1', '2020-06-15 14:33:57', '2020-06-15 14:33:57');
INSERT INTO `tb_goods_specification_option` VALUES ('1271', '75', '432', '冰', '0.00', '1', '1', '2020-06-15 14:34:06', '2020-06-15 14:34:06');
INSERT INTO `tb_goods_specification_option` VALUES ('1286', '76', '437', '大杯', '0.00', '1', '1', '2020-06-17 13:44:14', '2020-06-17 13:44:14');
INSERT INTO `tb_goods_specification_option` VALUES ('1287', '76', '438', '冰', '0.00', '1', '1', '2020-06-17 13:44:21', '2020-06-17 13:44:21');
INSERT INTO `tb_goods_specification_option` VALUES ('1302', '77', '443', '大杯', '0.00', '1', '1', '2020-06-17 14:21:58', '2020-06-17 14:21:58');
INSERT INTO `tb_goods_specification_option` VALUES ('1303', '77', '444', '冰', '0.00', '1', '1', '2020-06-17 14:22:04', '2020-06-17 14:22:04');
INSERT INTO `tb_goods_specification_option` VALUES ('1304', '77', '445', '椰果', '1.00', '1', '1', '2020-06-17 14:22:12', '2020-06-17 14:22:12');
INSERT INTO `tb_goods_specification_option` VALUES ('1305', '77', '445', '红豆', '1.00', '1', '2', '2020-06-17 14:22:23', '2020-06-17 14:22:23');
INSERT INTO `tb_goods_specification_option` VALUES ('1320', '78', '450', '中杯', '0.00', '1', '1', '2020-06-17 19:20:08', '2020-06-17 19:20:08');
INSERT INTO `tb_goods_specification_option` VALUES ('1321', '78', '451', '冰', '0.00', '1', '1', '2020-06-17 19:20:14', '2020-06-17 19:20:14');
INSERT INTO `tb_goods_specification_option` VALUES ('1338', '59', '458', '中杯', '0.00', '1', '1', '2020-06-21 22:22:45', '2020-06-21 22:22:45');
INSERT INTO `tb_goods_specification_option` VALUES ('1339', '59', '458', '大杯', '2.00', '1', '2', '2020-06-21 22:22:52', '2020-06-21 22:22:52');
INSERT INTO `tb_goods_specification_option` VALUES ('1340', '59', '459', '热', '0.00', '1', '1', '2020-06-21 22:23:05', '2020-06-21 22:23:05');
INSERT INTO `tb_goods_specification_option` VALUES ('1341', '59', '459', '常温', '0.00', '1', '2', '2020-06-21 22:23:12', '2020-06-21 22:23:12');
INSERT INTO `tb_goods_specification_option` VALUES ('1342', '59', '459', '冰', '0.00', '1', '3', '2020-06-21 22:23:22', '2020-06-21 22:23:22');
INSERT INTO `tb_goods_specification_option` VALUES ('1343', '59', '459', '多冰', '0.00', '1', '4', '2020-06-21 22:23:38', '2020-06-21 22:23:38');
INSERT INTO `tb_goods_specification_option` VALUES ('1358', '80', '464', '大杯', '0.00', '1', '1', '2020-06-22 13:30:24', '2020-06-22 13:30:24');
INSERT INTO `tb_goods_specification_option` VALUES ('1359', '80', '465', '冰', '0.00', '1', '1', '2020-06-22 13:30:30', '2020-06-22 13:30:30');
INSERT INTO `tb_goods_specification_option` VALUES ('1360', '78', '466', '女士专选无糖', '0.00', '1', '1', '2020-06-22 14:36:38', '2020-06-22 14:36:38');
INSERT INTO `tb_goods_specification_option` VALUES ('1361', '78', '466', '低糖', '0.00', '1', '2', '2020-06-22 14:36:46', '2020-06-22 14:36:46');
INSERT INTO `tb_goods_specification_option` VALUES ('1362', '78', '466', '中糖', '0.00', '1', '3', '2020-06-22 14:36:56', '2020-06-22 14:36:56');
INSERT INTO `tb_goods_specification_option` VALUES ('1377', '81', '471', '大杯', '0.00', '1', '1', '2020-07-01 14:02:02', '2020-07-01 14:02:02');
INSERT INTO `tb_goods_specification_option` VALUES ('1378', '81', '472', '冰', '0.00', '1', '1', '2020-07-01 14:02:11', '2020-07-01 14:02:11');
INSERT INTO `tb_goods_specification_option` VALUES ('1379', '81', '473', '正常糖', '0.00', '1', '1', '2020-07-01 14:02:29', '2020-07-01 14:02:29');
INSERT INTO `tb_goods_specification_option` VALUES ('1380', '78', '474', '不加', '0.00', '1', '1', '2020-07-08 21:07:30', '2020-07-08 21:07:30');
INSERT INTO `tb_goods_specification_option` VALUES ('1381', '78', '474', '奶盖', '3.00', '1', '2', '2020-07-08 21:07:37', '2020-07-08 21:07:37');
INSERT INTO `tb_goods_specification_option` VALUES ('1398', '82', '480', '500ML', '0.00', '1', '1', '2020-08-04 09:03:16', '2020-08-04 09:03:16');
INSERT INTO `tb_goods_specification_option` VALUES ('1399', '82', '481', '冰', '0.00', '1', '1', '2020-08-04 09:03:22', '2020-08-04 09:03:22');
INSERT INTO `tb_goods_specification_option` VALUES ('1400', '82', '482', '女士专选无糖', '0.00', '1', '1', '2020-08-05 17:34:22', '2020-08-05 17:34:22');
INSERT INTO `tb_goods_specification_option` VALUES ('1401', '82', '482', '正常糖', '0.00', '1', '2', '2020-08-05 17:34:30', '2020-08-05 17:34:30');
INSERT INTO `tb_goods_specification_option` VALUES ('1416', '83', '487', '500ML', '0.00', '1', '1', '2020-09-22 23:55:15', '2020-09-22 23:55:15');
INSERT INTO `tb_goods_specification_option` VALUES ('1431', '84', '492', '500ML', '0.00', '1', '1', '2020-09-23 00:05:15', '2020-09-23 00:05:15');
INSERT INTO `tb_goods_specification_option` VALUES ('1656', '100', '557', '500ML', '0.00', '1', '1', '2020-10-07 17:41:34', '2020-10-07 17:41:34');
INSERT INTO `tb_goods_specification_option` VALUES ('1657', '100', '558', '沙冰', '0.00', '1', '1', '2020-10-07 17:41:57', '2020-10-07 17:41:57');
INSERT INTO `tb_goods_specification_option` VALUES ('2683', '174', '1114', '700ML', '0.00', '1', '2', '2020-10-08 20:32:37', '2020-10-08 20:32:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3017', '83', '948', '沙冰', '0.00', '1', '1', '2020-10-09 14:52:06', '2020-10-09 14:52:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3018', '84', '949', '冰', '0.00', '1', '1', '2020-10-09 14:54:32', '2020-10-09 14:54:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3019', '85', '950', '500ML', '0.00', '1', '1', '2020-10-09 14:54:58', '2020-10-09 14:54:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3020', '85', '951', '热', '0.00', '1', '1', '2020-10-09 14:56:14', '2020-10-09 14:56:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3021', '85', '951', '常温', '0.00', '1', '2', '2020-10-09 14:56:21', '2020-10-09 14:56:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3022', '85', '951', '去冰', '0.00', '1', '3', '2020-10-09 14:56:41', '2020-10-09 14:56:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3023', '85', '951', '冰', '0.00', '1', '4', '2020-10-09 14:56:48', '2020-10-09 14:56:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3028', '86', '953', '700ML', '0.00', '1', '1', '2020-10-09 15:03:24', '2020-10-09 15:03:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3029', '86', '954', '冰', '0.00', '1', '1', '2020-10-09 15:03:30', '2020-10-09 15:03:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3030', '86', '955', '不加', '0.00', '1', '1', '2020-10-09 15:03:36', '2020-10-09 15:03:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3031', '86', '955', '椰果', '2.00', '1', '2', '2020-10-09 15:03:42', '2020-10-09 15:03:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3032', '86', '955', '波波', '2.00', '1', '3', '2020-10-09 15:03:54', '2020-10-09 15:03:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3033', '87', '956', '500ML0', '0.00', '1', '1', '2020-10-09 15:05:23', '2020-10-09 15:05:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3034', '87', '957', '沙冰', '0.00', '1', '1', '2020-10-09 15:05:27', '2020-10-09 15:05:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3035', '88', '958', '500ML', '0.00', '1', '1', '2020-10-09 15:05:50', '2020-10-09 15:05:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3036', '88', '959', '冰', '0.00', '1', '1', '2020-10-09 15:05:59', '2020-10-09 15:05:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3037', '89', '960', '500ML', '0.00', '1', '1', '2020-10-09 15:06:18', '2020-10-09 15:06:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3038', '89', '961', '沙冰', '0.00', '1', '1', '2020-10-09 15:06:23', '2020-10-09 15:06:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3039', '90', '962', '500ML', '0.00', '1', '1', '2020-10-09 15:06:42', '2020-10-09 15:06:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3040', '90', '963', '沙冰', '0.00', '1', '1', '2020-10-09 15:06:54', '2020-10-09 15:06:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3041', '91', '964', '500ML', '0.00', '1', '1', '2020-10-09 15:07:18', '2020-10-09 15:07:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3042', '91', '965', '沙冰', '0.00', '1', '1', '2020-10-09 15:07:25', '2020-10-09 15:07:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3043', '92', '966', '700ML', '0.00', '1', '1', '2020-10-09 15:07:44', '2020-10-09 15:07:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3044', '92', '967', '沙冰', '0.00', '1', '1', '2020-10-09 15:07:52', '2020-10-09 15:07:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3045', '93', '968', '700ML', '0.00', '1', '1', '2020-10-09 15:08:07', '2020-10-09 15:08:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3046', '93', '969', '沙冰', '0.00', '1', '1', '2020-10-09 15:08:13', '2020-10-09 15:08:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3047', '94', '970', '700ML', '0.00', '1', '1', '2020-10-09 15:08:25', '2020-10-09 15:08:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3048', '94', '971', '沙冰', '0.00', '1', '1', '2020-10-09 15:08:33', '2020-10-09 15:08:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3049', '95', '972', '500ML', '0.00', '1', '1', '2020-10-09 15:08:44', '2020-10-09 15:08:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3050', '95', '973', '冰', '0.00', '1', '1', '2020-10-09 15:08:52', '2020-10-09 15:08:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3051', '96', '974', '700ML', '0.00', '1', '1', '2020-10-09 15:09:08', '2020-10-09 15:09:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3052', '96', '975', '冰', '0.00', '1', '1', '2020-10-09 15:09:17', '2020-10-09 15:09:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3053', '101', '976', '中杯', '0.00', '1', '1', '2020-10-09 15:11:21', '2020-10-09 15:11:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3054', '101', '976', '大杯', '2.00', '1', '2', '2020-10-09 15:11:45', '2020-10-09 15:11:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3055', '101', '977', '热', '0.00', '1', '1', '2020-10-09 15:15:54', '2020-10-09 15:15:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3056', '101', '977', '常温', '0.00', '1', '2', '2020-10-09 15:15:59', '2020-10-09 15:15:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3057', '101', '977', '去冰', '0.00', '1', '3', '2020-10-09 15:16:06', '2020-10-09 15:16:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3058', '101', '977', '冰', '0.00', '1', '4', '2020-10-09 15:16:11', '2020-10-09 15:16:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3063', '102', '979', '中杯', '0.00', '1', '1', '2020-10-09 15:17:15', '2020-10-09 15:17:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3064', '102', '979', '大杯', '2.00', '1', '2', '2020-10-09 15:17:25', '2020-10-09 15:17:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3065', '102', '980', '热', '0.00', '1', '1', '2020-10-09 15:17:35', '2020-10-09 15:17:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3066', '102', '980', '常温', '0.00', '1', '2', '2020-10-09 15:17:46', '2020-10-09 15:17:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3067', '102', '980', '去冰', '0.00', '1', '3', '2020-10-09 15:17:52', '2020-10-09 15:17:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3068', '102', '980', '冰', '0.00', '1', '4', '2020-10-09 15:17:57', '2020-10-09 15:17:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3069', '102', '981', '椰果', '1.00', '1', '1', '2020-10-09 15:18:18', '2020-10-09 15:18:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3070', '102', '981', '红豆', '1.00', '1', '2', '2020-10-09 15:18:24', '2020-10-09 15:18:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3071', '102', '981', '布丁', '2.00', '1', '3', '2020-10-09 15:18:31', '2020-10-09 15:18:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3072', '102', '981', '波波', '2.00', '1', '4', '2020-10-09 15:18:37', '2020-10-09 15:18:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3073', '103', '982', '700ML', '0.00', '1', '1', '2020-10-09 15:18:50', '2020-10-09 15:18:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3074', '103', '983', '热', '0.00', '1', '1', '2020-10-09 15:18:57', '2020-10-09 15:18:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3075', '103', '983', '常温', '0.00', '1', '2', '2020-10-09 15:19:24', '2020-10-09 15:19:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3076', '103', '983', '去冰', '0.00', '1', '3', '2020-10-09 15:19:30', '2020-10-09 15:19:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3077', '103', '983', '冰', '0.00', '1', '4', '2020-10-09 15:19:34', '2020-10-09 15:19:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3078', '104', '984', '中杯', '0.00', '1', '1', '2020-10-09 15:19:51', '2020-10-09 15:19:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3079', '104', '985', '热', '0.00', '1', '1', '2020-10-09 15:20:12', '2020-10-09 15:20:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3080', '104', '985', '常温', '0.00', '1', '2', '2020-10-09 15:20:27', '2020-10-09 15:20:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3081', '104', '985', '去冰', '0.00', '1', '3', '2020-10-09 15:20:33', '2020-10-09 15:20:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3082', '104', '985', '冰', '0.00', '1', '4', '2020-10-09 15:20:37', '2020-10-09 15:20:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3083', '104', '986', '不加', '0.00', '1', '1', '2020-10-09 15:20:49', '2020-10-09 15:20:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3084', '104', '986', '椰果', '1.00', '1', '2', '2020-10-09 15:21:07', '2020-10-09 15:21:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3085', '104', '986', '红豆', '1.00', '1', '3', '2020-10-09 15:21:12', '2020-10-09 15:21:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3086', '104', '986', '布丁', '2.00', '1', '4', '2020-10-09 15:21:18', '2020-10-09 15:21:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3087', '104', '986', '波波', '2.00', '1', '5', '2020-10-09 15:21:24', '2020-10-09 15:21:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3088', '105', '987', '中杯', '0.00', '1', '1', '2020-10-09 15:21:45', '2020-10-09 15:21:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3089', '105', '987', '大杯', '2.00', '1', '2', '2020-10-09 15:21:56', '2020-10-09 15:21:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3090', '105', '988', '热', '0.00', '1', '1', '2020-10-09 15:22:05', '2020-10-09 15:22:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3091', '105', '988', '常温', '0.00', '1', '2', '2020-10-09 15:22:16', '2020-10-09 15:22:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3092', '105', '988', '去冰', '0.00', '1', '3', '2020-10-09 15:22:24', '2020-10-09 15:22:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3093', '105', '988', '冰', '0.00', '1', '4', '2020-10-09 15:22:34', '2020-10-09 15:22:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3094', '105', '989', '椰果', '1.00', '1', '1', '2020-10-09 15:22:45', '2020-10-09 15:22:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3095', '105', '989', '红豆', '1.00', '1', '2', '2020-10-09 15:22:50', '2020-10-09 15:22:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3096', '105', '989', '布丁', '2.00', '1', '3', '2020-10-09 15:23:05', '2020-10-09 15:23:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3097', '105', '989', '波波', '2.00', '1', '4', '2020-10-09 15:23:11', '2020-10-09 15:23:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3098', '106', '990', '中杯', '0.00', '1', '1', '2020-10-09 15:23:30', '2020-10-09 15:23:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3099', '106', '990', '大杯', '2.00', '1', '2', '2020-10-09 15:23:36', '2020-10-09 15:23:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3100', '106', '991', '热', '0.00', '1', '1', '2020-10-09 15:23:44', '2020-10-09 15:23:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3101', '106', '991', '常温', '0.00', '1', '2', '2020-10-09 15:23:53', '2020-10-09 15:23:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3102', '106', '991', '去冰', '0.00', '1', '3', '2020-10-09 15:24:04', '2020-10-09 15:24:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3103', '106', '991', '冰', '0.00', '1', '4', '2020-10-09 15:24:09', '2020-10-09 15:24:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3104', '107', '992', '中杯', '0.00', '1', '1', '2020-10-09 15:24:25', '2020-10-09 15:24:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3105', '107', '992', '大杯', '2.00', '1', '2', '2020-10-09 15:24:31', '2020-10-09 15:24:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3106', '107', '993', '热', '0.00', '1', '1', '2020-10-09 15:24:41', '2020-10-09 15:24:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3107', '107', '993', '常温', '0.00', '1', '2', '2020-10-09 15:24:47', '2020-10-09 15:24:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3108', '107', '993', '去冰', '0.00', '1', '3', '2020-10-09 15:24:58', '2020-10-09 15:24:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3109', '107', '993', '冰', '0.00', '1', '4', '2020-10-09 15:25:03', '2020-10-09 15:25:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3110', '108', '994', '中杯', '0.00', '1', '1', '2020-10-09 15:25:18', '2020-10-09 15:25:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3111', '108', '994', '大杯', '2.00', '1', '2', '2020-10-09 15:25:23', '2020-10-09 15:25:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3112', '108', '995', '热', '0.00', '1', '1', '2020-10-09 15:25:30', '2020-10-09 15:25:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3113', '108', '995', '常温', '0.00', '1', '2', '2020-10-09 15:25:40', '2020-10-09 15:25:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3114', '108', '995', '去冰', '0.00', '1', '3', '2020-10-09 15:25:55', '2020-10-09 15:25:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3115', '108', '995', '冰', '0.00', '1', '4', '2020-10-09 15:26:00', '2020-10-09 15:26:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3116', '109', '996', '中杯', '0.00', '1', '1', '2020-10-09 15:26:20', '2020-10-09 15:26:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3117', '109', '996', '大杯', '2.00', '1', '2', '2020-10-09 15:26:28', '2020-10-09 15:26:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3118', '109', '997', '热', '0.00', '1', '1', '2020-10-09 15:26:35', '2020-10-09 15:26:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3119', '109', '997', '常温', '0.00', '1', '2', '2020-10-09 15:26:40', '2020-10-09 15:26:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3120', '109', '997', '去冰', '0.00', '1', '3', '2020-10-09 15:26:48', '2020-10-09 15:26:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3121', '109', '997', '冰', '0.00', '1', '4', '2020-10-09 15:26:55', '2020-10-09 15:26:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3122', '110', '998', '中杯', '0.00', '1', '1', '2020-10-09 15:27:14', '2020-10-09 15:27:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3123', '110', '998', '大杯', '2.00', '1', '2', '2020-10-09 15:27:20', '2020-10-09 15:27:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3124', '110', '999', '热', '0.00', '1', '1', '2020-10-09 15:27:25', '2020-10-09 15:27:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3125', '110', '999', '常温', '0.00', '1', '2', '2020-10-09 15:27:35', '2020-10-09 15:27:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3126', '110', '999', '去冰', '0.00', '1', '3', '2020-10-09 15:27:40', '2020-10-09 15:27:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3127', '110', '999', '冰', '0.00', '1', '4', '2020-10-09 15:27:46', '2020-10-09 15:27:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3128', '111', '1000', '中杯', '0.00', '1', '1', '2020-10-09 15:28:03', '2020-10-09 15:28:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3134', '113', '1004', '中杯', '0.00', '1', '1', '2020-10-09 15:29:54', '2020-10-09 15:29:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3137', '114', '1006', '中杯', '0.00', '1', '1', '2020-10-09 15:30:25', '2020-10-09 15:30:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3140', '115', '1008', '500ML', '0.00', '1', '1', '2020-10-09 15:30:53', '2020-10-09 15:30:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3141', '115', '1009', '沙冰', '0.00', '1', '1', '2020-10-09 15:31:42', '2020-10-09 15:31:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3142', '116', '1010', '500ML', '0.00', '1', '1', '2020-10-09 15:31:51', '2020-10-09 15:31:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3143', '116', '1011', '沙冰', '0.00', '1', '1', '2020-10-09 15:31:59', '2020-10-09 15:31:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3144', '117', '1012', '500ML', '0.00', '1', '1', '2020-10-09 15:33:33', '2020-10-09 15:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3145', '117', '1013', '沙冰', '0.00', '1', '1', '2020-10-09 15:33:40', '2020-10-09 15:33:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3146', '118', '1014', '500ML', '0.00', '1', '1', '2020-10-09 15:34:04', '2020-10-09 15:34:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3147', '118', '1015', '沙冰', '0.00', '1', '1', '2020-10-09 15:34:11', '2020-10-09 15:34:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3148', '119', '1016', '中杯', '0.00', '1', '1', '2020-10-09 15:34:34', '2020-10-09 15:34:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3149', '119', '1016', '大杯', '2.00', '1', '2', '2020-10-09 15:34:41', '2020-10-09 15:34:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3150', '119', '1017', '热', '0.00', '1', '1', '2020-10-09 15:34:48', '2020-10-09 15:34:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3151', '119', '1017', '常温', '0.00', '1', '2', '2020-10-09 15:34:53', '2020-10-09 15:34:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3152', '119', '1017', '去冰', '0.00', '1', '3', '2020-10-09 15:34:59', '2020-10-09 15:34:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3153', '119', '1017', '冰', '0.00', '1', '4', '2020-10-09 15:35:05', '2020-10-09 15:35:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3154', '119', '1018', '红豆', '1.00', '1', '1', '2020-10-09 15:35:48', '2020-10-09 15:35:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3155', '119', '1018', '椰果', '1.00', '1', '2', '2020-10-09 15:35:56', '2020-10-09 15:35:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3156', '119', '1018', '布丁', '2.00', '1', '3', '2020-10-09 15:36:01', '2020-10-09 15:36:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3157', '119', '1018', '波波', '2.00', '1', '4', '2020-10-09 15:36:07', '2020-10-09 15:36:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3159', '120', '1019', '700ML', '0.00', '1', '2', '2020-10-09 15:36:24', '2020-10-09 15:36:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3160', '120', '1020', '热', '0.00', '1', '1', '2020-10-09 15:36:30', '2020-10-09 15:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3161', '120', '1020', '常温', '0.00', '1', '2', '2020-10-09 15:36:38', '2020-10-09 15:36:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3162', '120', '1020', '去冰', '0.00', '1', '3', '2020-10-09 15:36:46', '2020-10-09 15:36:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3163', '120', '1020', '冰', '0.00', '1', '4', '2020-10-09 15:36:50', '2020-10-09 15:36:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3164', '120', '1021', '红豆', '1.00', '1', '1', '2020-10-09 15:37:39', '2020-10-09 15:37:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3165', '120', '1021', '椰果', '1.00', '1', '2', '2020-10-09 15:37:45', '2020-10-09 15:37:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3166', '120', '1021', '布丁', '2.00', '1', '3', '2020-10-09 15:37:51', '2020-10-09 15:37:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3167', '120', '1021', '波波', '2.00', '1', '4', '2020-10-09 15:38:04', '2020-10-09 15:38:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3168', '121', '1022', '中杯', '0.00', '1', '1', '2020-10-09 15:38:28', '2020-10-09 15:38:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3169', '121', '1022', '大杯', '2.00', '1', '2', '2020-10-09 15:38:45', '2020-10-09 15:38:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3170', '121', '1023', '热', '0.00', '1', '1', '2020-10-09 15:42:46', '2020-10-09 15:42:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3171', '121', '1023', '常温', '0.00', '1', '2', '2020-10-09 15:43:16', '2020-10-09 15:43:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3172', '121', '1023', '去冰', '0.00', '1', '3', '2020-10-09 15:43:31', '2020-10-09 15:43:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3173', '121', '1023', '冰', '0.00', '1', '4', '2020-10-09 15:43:38', '2020-10-09 15:43:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3174', '121', '1024', '红豆', '1.00', '1', '1', '2020-10-09 15:43:44', '2020-10-09 15:43:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3175', '121', '1024', '椰果', '1.00', '1', '2', '2020-10-09 15:43:49', '2020-10-09 15:43:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3176', '121', '1024', '布丁', '2.00', '1', '3', '2020-10-09 15:44:10', '2020-10-09 15:44:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3177', '121', '1024', '波波', '2.00', '1', '4', '2020-10-09 15:44:17', '2020-10-09 15:44:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3178', '122', '1025', '中杯', '0.00', '1', '1', '2020-10-09 15:44:27', '2020-10-09 15:44:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3179', '122', '1025', '大杯', '2.00', '1', '2', '2020-10-09 15:44:34', '2020-10-09 15:44:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3180', '122', '1026', '热', '0.00', '1', '1', '2020-10-09 15:44:40', '2020-10-09 15:44:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3181', '122', '1026', '常温', '0.00', '1', '2', '2020-10-09 15:44:46', '2020-10-09 15:44:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3182', '122', '1026', '去冰', '0.00', '1', '3', '2020-10-09 15:44:51', '2020-10-09 15:44:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3183', '122', '1026', '冰', '0.00', '1', '4', '2020-10-09 15:44:55', '2020-10-09 15:44:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3184', '122', '1027', '椰果', '2.00', '1', '1', '2020-10-09 15:45:03', '2020-10-09 15:45:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3185', '122', '1027', '红豆', '2.00', '1', '2', '2020-10-09 15:45:15', '2020-10-09 15:45:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3186', '122', '1027', '布丁', '2.00', '1', '3', '2020-10-09 15:45:25', '2020-10-09 15:45:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3187', '122', '1027', '波波', '2.00', '1', '4', '2020-10-09 15:45:31', '2020-10-09 15:45:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3188', '123', '1028', '中杯', '0.00', '1', '1', '2020-10-09 15:45:54', '2020-10-09 15:45:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3189', '123', '1028', '大杯', '2.00', '1', '2', '2020-10-09 15:45:58', '2020-10-09 15:45:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3190', '123', '1029', '热', '0.00', '1', '1', '2020-10-09 15:46:04', '2020-10-09 15:46:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3191', '123', '1029', '常温', '0.00', '1', '2', '2020-10-09 15:46:08', '2020-10-09 15:46:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3192', '123', '1029', '去冰', '0.00', '1', '3', '2020-10-09 15:46:15', '2020-10-09 15:46:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3193', '123', '1029', '冰', '0.00', '1', '4', '2020-10-09 15:46:18', '2020-10-09 15:46:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3194', '123', '1030', '不加', '0.00', '1', '1', '2020-10-09 15:46:27', '2020-10-09 15:46:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3195', '123', '1030', '红豆', '2.00', '1', '2', '2020-10-09 15:46:32', '2020-10-09 15:46:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3196', '123', '1030', '椰果', '2.00', '1', '3', '2020-10-09 15:46:37', '2020-10-09 15:46:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3197', '123', '1030', '布丁', '2.00', '1', '4', '2020-10-09 15:46:47', '2020-10-09 15:46:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3198', '123', '1030', '波波', '2.00', '1', '5', '2020-10-09 15:46:52', '2020-10-09 15:46:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3199', '124', '1031', '中杯', '0.00', '1', '1', '2020-10-09 15:48:22', '2020-10-09 15:48:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3200', '124', '1031', '大杯', '2.00', '1', '2', '2020-10-09 15:48:29', '2020-10-09 15:48:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3201', '124', '1032', '热', '0.00', '1', '1', '2020-10-09 15:48:35', '2020-10-09 15:48:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3202', '124', '1032', '常温', '0.00', '1', '2', '2020-10-09 15:48:42', '2020-10-09 15:48:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3203', '124', '1032', '去冰', '0.00', '1', '3', '2020-10-09 15:48:55', '2020-10-09 15:48:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3204', '124', '1032', '冰', '0.00', '1', '4', '2020-10-09 15:49:01', '2020-10-09 15:49:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3205', '124', '1033', '不加', '0.00', '1', '1', '2020-10-09 15:49:07', '2020-10-09 15:49:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3206', '124', '1033', '红豆', '1.00', '1', '2', '2020-10-09 15:49:14', '2020-10-09 15:49:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3207', '124', '1033', '椰果', '1.00', '1', '3', '2020-10-09 15:49:27', '2020-10-09 15:49:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3208', '124', '1033', '布丁', '2.00', '1', '4', '2020-10-09 15:49:32', '2020-10-09 15:49:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3209', '124', '1033', '波波', '2.00', '1', '5', '2020-10-09 15:49:37', '2020-10-09 15:49:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3210', '125', '1034', '中杯', '0.00', '1', '1', '2020-10-09 15:50:34', '2020-10-09 15:50:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3211', '125', '1034', '大杯', '2.00', '1', '2', '2020-10-09 15:50:39', '2020-10-09 15:50:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3212', '125', '1035', '热', '0.00', '1', '1', '2020-10-09 15:50:47', '2020-10-09 15:50:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3213', '125', '1035', '常温', '0.00', '1', '2', '2020-10-09 15:50:54', '2020-10-09 15:50:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3214', '125', '1035', '去冰', '0.00', '1', '3', '2020-10-09 15:51:02', '2020-10-09 15:51:02');
INSERT INTO `tb_goods_specification_option` VALUES ('3215', '125', '1035', '冰', '0.00', '1', '4', '2020-10-09 15:51:06', '2020-10-09 15:51:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3216', '125', '1036', '不加', '0.00', '1', '1', '2020-10-09 15:51:14', '2020-10-09 15:51:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3217', '125', '1036', '红豆', '1.00', '1', '2', '2020-10-09 15:53:38', '2020-10-09 15:53:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3218', '125', '1036', '椰果', '1.00', '1', '3', '2020-10-09 15:54:00', '2020-10-09 15:54:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3219', '125', '1036', '布丁', '2.00', '1', '4', '2020-10-09 15:54:05', '2020-10-09 15:54:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3220', '125', '1036', '波波', '2.00', '1', '5', '2020-10-09 15:54:12', '2020-10-09 15:54:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3221', '111', '1000', '大杯', '2.00', '1', '2', '2020-10-09 15:54:44', '2020-10-09 15:54:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3222', '111', '1037', '热', '0.00', '1', '1', '2020-10-09 15:54:51', '2020-10-09 15:54:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3223', '111', '1037', '常温', '0.00', '1', '2', '2020-10-09 15:55:23', '2020-10-09 15:55:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3224', '111', '1037', '去冰', '0.00', '1', '3', '2020-10-09 15:55:30', '2020-10-09 15:55:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3225', '111', '1037', '冰', '0.00', '1', '4', '2020-10-09 15:55:34', '2020-10-09 15:55:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3226', '114', '1006', '大杯', '2.00', '1', '2', '2020-10-09 15:56:27', '2020-10-09 15:56:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3227', '114', '1038', '热', '0.00', '1', '1', '2020-10-09 15:56:32', '2020-10-09 15:56:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3228', '114', '1038', '常温', '0.00', '1', '2', '2020-10-09 15:56:36', '2020-10-09 15:56:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3229', '114', '1038', '去冰', '0.00', '1', '3', '2020-10-09 15:56:46', '2020-10-09 15:56:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3230', '114', '1038', '冰', '0.00', '1', '4', '2020-10-09 15:56:50', '2020-10-09 15:56:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3231', '113', '1004', '大杯', '2.00', '1', '2', '2020-10-09 15:57:06', '2020-10-09 15:57:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3232', '113', '1039', '热', '0.00', '1', '1', '2020-10-09 15:57:14', '2020-10-09 15:57:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3233', '113', '1039', '常温', '0.00', '1', '2', '2020-10-09 15:57:19', '2020-10-09 15:57:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3234', '113', '1039', '去冰', '0.00', '1', '3', '2020-10-09 15:57:25', '2020-10-09 15:57:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3235', '113', '1039', '冰', '0.00', '1', '4', '2020-10-09 15:57:29', '2020-10-09 15:57:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3236', '112', '1040', '中杯', '0.00', '1', '1', '2020-10-09 15:57:47', '2020-10-09 15:57:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3237', '112', '1040', '大杯', '2.00', '1', '2', '2020-10-09 15:57:52', '2020-10-09 15:57:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3238', '112', '1041', '热', '0.00', '1', '1', '2020-10-09 15:58:06', '2020-10-09 15:58:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3239', '112', '1041', '常温', '0.00', '1', '2', '2020-10-09 15:58:12', '2020-10-09 15:58:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3240', '112', '1041', '去冰', '0.00', '1', '3', '2020-10-09 15:58:17', '2020-10-09 15:58:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3241', '112', '1041', '冰', '0.00', '1', '4', '2020-10-09 15:58:24', '2020-10-09 15:58:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3242', '129', '1042', '中杯', '0.00', '1', '1', '2020-10-10 09:04:05', '2020-10-10 09:04:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3243', '129', '1042', '大杯', '0.00', '1', '2', '2020-10-10 09:04:09', '2020-10-10 09:04:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3244', '129', '1043', '热', '0.00', '1', '1', '2020-10-10 09:04:15', '2020-10-10 09:04:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3245', '129', '1043', '常温', '0.00', '1', '2', '2020-10-10 09:04:20', '2020-10-10 09:04:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3246', '129', '1043', '去冰', '0.00', '1', '3', '2020-10-10 09:04:27', '2020-10-10 09:04:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3247', '129', '1043', '冰', '0.00', '1', '4', '2020-10-10 09:04:32', '2020-10-10 09:04:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3248', '130', '1044', '中杯', '0.00', '1', '1', '2020-10-10 09:04:44', '2020-10-10 09:04:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3249', '130', '1044', '大杯', '0.00', '1', '2', '2020-10-10 09:04:50', '2020-10-10 09:04:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3250', '130', '1044', '热', '0.00', '1', '3', '2020-10-10 09:05:00', '2020-10-10 09:05:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3251', '130', '1044', '常温', '0.00', '1', '4', '2020-10-10 09:05:06', '2020-10-10 09:05:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3252', '130', '1044', '去冰', '0.00', '1', '5', '2020-10-10 09:05:11', '2020-10-10 09:05:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3253', '130', '1044', '冰', '0.00', '1', '6', '2020-10-10 09:05:16', '2020-10-10 09:05:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3254', '131', '1045', '中杯', '0.00', '1', '1', '2020-10-10 09:06:40', '2020-10-10 09:06:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3255', '131', '1045', '大杯', '0.00', '1', '2', '2020-10-10 09:06:44', '2020-10-10 09:06:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3256', '131', '1046', '热', '0.00', '1', '1', '2020-10-10 09:06:53', '2020-10-10 09:06:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3257', '131', '1046', '常温', '0.00', '1', '2', '2020-10-10 09:06:59', '2020-10-10 09:06:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3258', '131', '1046', '去冰', '0.00', '1', '3', '2020-10-10 09:07:04', '2020-10-10 09:07:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3259', '131', '1046', '冰', '0.00', '1', '4', '2020-10-10 09:07:11', '2020-10-10 09:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3261', '132', '1048', '700ML', '0.00', '1', '1', '2020-10-10 09:09:43', '2020-10-10 09:09:43');
INSERT INTO `tb_goods_specification_option` VALUES ('3264', '132', '1049', '去冰', '0.00', '1', '3', '2020-10-10 09:10:02', '2020-10-10 09:10:02');
INSERT INTO `tb_goods_specification_option` VALUES ('3265', '132', '1049', '冰', '0.00', '1', '4', '2020-10-10 09:10:08', '2020-10-10 09:10:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3266', '133', '1050', '700ML', '0.00', '1', '1', '2020-10-10 09:10:31', '2020-10-10 09:10:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3269', '133', '1051', '去冰', '0.00', '1', '3', '2020-10-10 09:10:50', '2020-10-10 09:10:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3270', '133', '1051', '冰', '0.00', '1', '4', '2020-10-10 09:11:09', '2020-10-10 09:11:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3271', '134', '1052', '700ML', '0.00', '1', '1', '2020-10-10 09:11:24', '2020-10-10 09:11:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3273', '134', '1053', '冰', '0.00', '1', '2', '2020-10-10 09:11:41', '2020-10-10 09:11:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3274', '135', '1054', '中杯', '0.00', '1', '1', '2020-10-10 09:12:05', '2020-10-10 09:12:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3275', '135', '1054', '大杯', '2.00', '1', '2', '2020-10-10 09:12:15', '2020-10-10 09:12:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3276', '135', '1055', '热', '0.00', '1', '1', '2020-10-10 09:22:45', '2020-10-10 09:22:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3277', '135', '1055', '常温', '0.00', '1', '2', '2020-10-10 09:22:50', '2020-10-10 09:22:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3278', '135', '1055', '去冰', '0.00', '1', '3', '2020-10-10 09:22:59', '2020-10-10 09:22:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3279', '135', '1055', '冰', '0.00', '1', '4', '2020-10-10 09:23:05', '2020-10-10 09:23:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3280', '136', '1056', '中杯', '0.00', '1', '1', '2020-10-10 09:23:33', '2020-10-10 09:23:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3281', '136', '1056', '大杯', '2.00', '1', '2', '2020-10-10 09:23:38', '2020-10-10 09:23:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3282', '136', '1057', '热', '0.00', '1', '1', '2020-10-10 09:25:49', '2020-10-10 09:25:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3283', '136', '1057', '常温', '0.00', '1', '2', '2020-10-10 09:25:55', '2020-10-10 09:25:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3284', '136', '1057', '去冰', '0.00', '1', '3', '2020-10-10 09:26:04', '2020-10-10 09:26:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3285', '136', '1057', '冰', '0.00', '1', '4', '2020-10-10 09:26:14', '2020-10-10 09:26:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3286', '186', '1058', '500ML', '0.00', '1', '1', '2020-10-10 09:26:59', '2020-10-10 09:26:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3287', '186', '1059', '冰', '0.00', '1', '1', '2020-10-10 09:27:07', '2020-10-10 09:27:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3288', '187', '1060', '700ML', '0.00', '1', '1', '2020-10-10 09:27:22', '2020-10-10 09:27:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3289', '187', '1061', '冰', '0.00', '1', '1', '2020-10-10 09:27:33', '2020-10-10 09:27:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3290', '188', '1062', '700ML', '0.00', '1', '1', '2020-10-10 09:27:51', '2020-10-10 09:27:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3291', '188', '1063', '冰', '0.00', '1', '1', '2020-10-10 09:27:57', '2020-10-10 09:27:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3292', '189', '1064', '700ML', '0.00', '1', '1', '2020-10-10 09:28:16', '2020-10-10 09:28:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3293', '189', '1065', '沙冰', '0.00', '1', '1', '2020-10-10 09:28:34', '2020-10-10 09:28:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3294', '190', '1066', '700ML', '0.00', '1', '1', '2020-10-10 09:29:23', '2020-10-10 09:29:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3295', '190', '1067', '去冰', '0.00', '1', '1', '2020-10-10 09:29:33', '2020-10-10 09:29:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3296', '190', '1067', '冰', '0.00', '1', '2', '2020-10-10 09:29:37', '2020-10-10 09:29:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3297', '191', '1068', '500ML', '0.00', '1', '1', '2020-10-10 09:29:51', '2020-10-10 09:29:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3298', '191', '1069', '冰', '0.00', '1', '1', '2020-10-10 09:29:58', '2020-10-10 09:29:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3299', '192', '1070', '700ML', '0.00', '1', '1', '2020-10-10 09:30:08', '2020-10-10 09:30:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3301', '192', '1071', '冰', '0.00', '1', '2', '2020-10-10 09:30:28', '2020-10-10 09:30:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3302', '193', '1072', '700ML', '0.00', '1', '1', '2020-10-10 09:32:19', '2020-10-10 09:32:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3303', '193', '1073', '冰', '0.00', '1', '1', '2020-10-10 09:32:27', '2020-10-10 09:32:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3304', '194', '1074', '500ML', '0.00', '1', '1', '2020-10-10 10:38:13', '2020-10-10 10:38:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3305', '194', '1075', '去冰', '0.00', '1', '1', '2020-10-10 10:38:20', '2020-10-10 10:38:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3306', '194', '1075', '冰', '0.00', '1', '2', '2020-10-10 10:38:24', '2020-10-10 10:38:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3307', '195', '1076', '500ML', '0.00', '1', '1', '2020-10-10 10:38:36', '2020-10-10 10:38:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3308', '195', '1077', '热', '0.00', '1', '1', '2020-10-10 10:38:42', '2020-10-10 10:38:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3309', '195', '1077', '常温', '0.00', '1', '2', '2020-10-10 10:38:47', '2020-10-10 10:38:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3310', '195', '1077', '去冰', '0.00', '1', '3', '2020-10-10 10:38:55', '2020-10-10 10:38:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3311', '195', '1077', '冰', '0.00', '1', '4', '2020-10-10 10:39:00', '2020-10-10 10:39:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3312', '196', '1078', '700ML', '0.00', '1', '1', '2020-10-10 10:39:21', '2020-10-10 10:39:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3313', '196', '1079', '去冰', '0.00', '1', '1', '2020-10-10 10:39:28', '2020-10-10 10:39:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3314', '196', '1079', '冰', '0.00', '1', '2', '2020-10-10 10:39:34', '2020-10-10 10:39:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3315', '197', '1080', '500ML', '0.00', '1', '1', '2020-10-10 10:40:11', '2020-10-10 10:40:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3316', '197', '1081', '热', '0.00', '1', '1', '2020-10-10 10:40:21', '2020-10-10 10:40:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3317', '197', '1081', '常温', '0.00', '1', '2', '2020-10-10 10:40:26', '2020-10-10 10:40:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3318', '197', '1081', '去冰', '0.00', '1', '3', '2020-10-10 10:40:31', '2020-10-10 10:40:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3319', '197', '1081', '冰', '0.00', '1', '4', '2020-10-10 10:40:36', '2020-10-10 10:40:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3320', '185', '1082', '中杯', '0.00', '1', '1', '2020-10-10 14:06:20', '2020-10-10 14:06:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3321', '185', '1082', '大杯', '2.00', '1', '2', '2020-10-10 14:06:27', '2020-10-10 14:06:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3322', '185', '1083', '热', '0.00', '1', '1', '2020-10-10 14:06:49', '2020-10-10 14:06:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3323', '185', '1083', '常温', '0.00', '1', '2', '2020-10-10 14:06:54', '2020-10-10 14:06:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3324', '185', '1083', '去冰', '0.00', '1', '3', '2020-10-10 14:06:59', '2020-10-10 14:06:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3325', '185', '1083', '冰', '0.00', '1', '4', '2020-10-10 14:07:03', '2020-10-10 14:07:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3334', '184', '1085', '700ML', '0.00', '1', '1', '2020-10-10 14:35:20', '2020-10-10 14:35:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3335', '184', '1086', '热', '0.00', '1', '1', '2020-10-10 14:35:24', '2020-10-10 14:35:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3336', '184', '1086', '常温', '0.00', '1', '2', '2020-10-10 14:35:30', '2020-10-10 14:35:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3337', '184', '1086', '去冰', '0.00', '1', '3', '2020-10-10 14:38:06', '2020-10-10 14:38:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3338', '184', '1086', '冰', '0.00', '1', '4', '2020-10-10 14:38:14', '2020-10-10 14:38:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3339', '184', '1087', '不加', '0.00', '1', '1', '2020-10-10 14:38:27', '2020-10-10 14:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3348', '183', '1088', '大杯', '2.00', '1', '1', '2020-10-10 15:06:34', '2020-10-10 15:06:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3349', '183', '1089', '热', '0.00', '1', '1', '2020-10-10 15:07:20', '2020-10-10 15:07:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3350', '183', '1089', '常温', '0.00', '1', '2', '2020-10-10 15:07:26', '2020-10-10 15:07:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3351', '183', '1089', '去冰', '0.00', '1', '3', '2020-10-10 15:07:31', '2020-10-10 15:07:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3352', '183', '1089', '冰', '0.00', '1', '4', '2020-10-10 15:07:34', '2020-10-10 15:07:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3361', '182', '1091', '中杯', '0.00', '1', '1', '2020-10-10 18:16:30', '2020-10-10 18:16:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3362', '182', '1091', '大杯', '2.00', '1', '2', '2020-10-10 18:17:04', '2020-10-10 18:17:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3363', '183', '1088', '中杯', '0.00', '1', '2', '2020-10-10 18:20:48', '2020-10-10 18:20:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3364', '185', '1092', '奶茶', '0.00', '1', '1', '2020-10-10 18:22:25', '2020-10-10 18:22:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3365', '185', '1092', '奶绿', '0.00', '1', '2', '2020-10-10 18:22:33', '2020-10-10 18:22:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3368', '182', '1094', '奶茶', '0.00', '1', '1', '2020-10-10 18:24:19', '2020-10-10 18:24:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3369', '182', '1094', '奶绿', '0.00', '1', '2', '2020-10-10 18:24:25', '2020-10-10 18:24:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3370', '182', '1095', '热', '0.00', '1', '1', '2020-10-10 18:24:33', '2020-10-10 18:24:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3371', '182', '1095', '常温', '0.00', '1', '2', '2020-10-10 18:24:39', '2020-10-10 18:24:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3381', '181', '1097', '700ML', '0.00', '1', '1', '2020-10-10 18:27:54', '2020-10-10 18:27:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3385', '181', '1098', '沙冰', '0.00', '1', '4', '2020-10-10 18:28:17', '2020-10-10 18:28:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3393', '180', '1100', '500ML', '0.00', '1', '1', '2020-10-10 18:30:28', '2020-10-10 18:30:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3397', '180', '1101', '沙冰', '0.00', '1', '4', '2020-10-10 18:30:51', '2020-10-10 18:30:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3405', '179', '1103', '700ML', '0.00', '1', '1', '2020-10-10 18:39:29', '2020-10-10 18:39:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3406', '179', '1104', '冰', '0.00', '1', '1', '2020-10-10 18:39:33', '2020-10-10 18:39:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3407', '178', '1105', '700ML', '0.00', '1', '1', '2020-10-10 18:39:45', '2020-10-10 18:39:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3408', '178', '1106', '冰', '0.00', '1', '1', '2020-10-10 18:39:51', '2020-10-10 18:39:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3409', '177', '1107', '500ML', '0.00', '1', '1', '2020-10-10 18:40:17', '2020-10-10 18:40:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3410', '177', '1108', '冰', '0.00', '1', '1', '2020-10-10 18:40:59', '2020-10-10 18:40:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3421', '176', '1110', '700ML', '0.00', '1', '1', '2020-10-10 18:43:57', '2020-10-10 18:43:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3422', '176', '1111', '冰', '0.00', '1', '1', '2020-10-10 18:44:04', '2020-10-10 18:44:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3423', '175', '1112', '700ML', '0.00', '1', '1', '2020-10-10 18:44:23', '2020-10-10 18:44:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3424', '175', '1113', '冰', '0.00', '1', '1', '2020-10-10 18:44:38', '2020-10-10 18:44:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3425', '174', '853', '冰', '0.00', '1', '1', '2020-10-10 18:45:41', '2020-10-10 18:45:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3426', '173', '1115', '700ML', '0.00', '1', '1', '2020-10-10 18:46:05', '2020-10-10 18:46:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3427', '173', '1116', '冰', '0.00', '1', '1', '2020-10-10 18:46:10', '2020-10-10 18:46:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3428', '172', '1117', '700ML', '0.00', '1', '1', '2020-10-10 18:46:20', '2020-10-10 18:46:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3429', '172', '1118', '冰', '0.00', '1', '1', '2020-10-10 18:46:26', '2020-10-10 18:46:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3430', '171', '1119', '中杯', '0.00', '1', '1', '2020-10-10 18:46:42', '2020-10-10 18:46:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3431', '171', '1119', '大杯', '2.00', '1', '2', '2020-10-10 18:46:51', '2020-10-10 18:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3432', '171', '1120', '奶茶', '0.00', '1', '1', '2020-10-10 18:46:59', '2020-10-10 18:46:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3433', '171', '1120', '奶绿', '0.00', '1', '2', '2020-10-10 18:47:07', '2020-10-10 18:47:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3434', '171', '1121', '热', '0.00', '1', '1', '2020-10-10 18:47:20', '2020-10-10 18:47:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3435', '171', '1121', '常温', '0.00', '1', '2', '2020-10-10 18:47:25', '2020-10-10 18:47:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3436', '171', '1121', '去冰', '0.00', '1', '3', '2020-10-10 18:47:30', '2020-10-10 18:47:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3445', '170', '1123', '700ML', '0.00', '1', '1', '2020-10-10 18:48:49', '2020-10-10 18:48:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3446', '170', '1124', '冰', '0.00', '1', '1', '2020-10-10 18:48:59', '2020-10-10 18:48:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3447', '169', '1125', '700ML', '0.00', '1', '1', '2020-10-10 18:51:19', '2020-10-10 18:51:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3448', '169', '1126', '冰', '0.00', '1', '1', '2020-10-10 18:51:30', '2020-10-10 18:51:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3449', '168', '1127', '10', '0.00', '1', '1', '2020-10-10 18:52:49', '2020-10-10 18:52:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3450', '168', '1127', '大杯', '1.00', '1', '2', '2020-10-10 18:53:05', '2020-10-10 18:53:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3451', '168', '1128', '热', '0.00', '1', '1', '2020-10-10 18:53:14', '2020-10-10 18:53:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3452', '168', '1128', '常温', '0.00', '1', '2', '2020-10-10 18:53:22', '2020-10-10 18:53:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3453', '168', '1128', '去冰', '0.00', '1', '3', '2020-10-10 18:53:29', '2020-10-10 18:53:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3454', '168', '1128', '冰', '0.00', '1', '4', '2020-10-10 18:53:38', '2020-10-10 18:53:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3462', '167', '1130', '700ML', '0.00', '1', '1', '2020-10-10 19:00:39', '2020-10-10 19:00:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3463', '167', '1131', '冰', '0.00', '1', '1', '2020-10-10 19:00:48', '2020-10-10 19:00:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3464', '166', '1132', '700ML', '0.00', '1', '1', '2020-10-10 19:02:32', '2020-10-10 19:02:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3465', '166', '1133', '冰', '0.00', '1', '1', '2020-10-10 19:02:41', '2020-10-10 19:02:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3466', '165', '1134', '700ML', '0.00', '1', '1', '2020-10-10 19:03:49', '2020-10-10 19:03:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3467', '165', '1135', '冰', '0.00', '1', '1', '2020-10-10 19:03:57', '2020-10-10 19:03:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3468', '164', '1136', '700ML', '0.00', '1', '1', '2020-10-10 19:04:42', '2020-10-10 19:04:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3469', '164', '1137', '冰', '0.00', '1', '1', '2020-10-10 19:04:46', '2020-10-10 19:04:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3470', '163', '1138', '700ML', '0.00', '1', '1', '2020-10-10 19:05:07', '2020-10-10 19:05:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3471', '163', '1139', '冰', '0.00', '1', '1', '2020-10-10 19:05:12', '2020-10-10 19:05:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3472', '162', '1140', '700ML', '0.00', '1', '1', '2020-10-10 19:05:31', '2020-10-10 19:05:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3473', '162', '1141', '热', '0.00', '1', '1', '2020-10-10 19:05:39', '2020-10-10 19:05:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3474', '162', '1141', '常温', '0.00', '1', '2', '2020-10-10 19:05:44', '2020-10-10 19:05:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3475', '162', '1141', '去冰', '0.00', '1', '3', '2020-10-10 19:05:50', '2020-10-10 19:05:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3476', '162', '1141', '冰', '0.00', '1', '4', '2020-10-10 19:05:54', '2020-10-10 19:05:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3484', '161', '1143', '中杯', '0.00', '1', '1', '2020-10-10 19:06:55', '2020-10-10 19:06:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3485', '161', '1143', '大杯', '2.00', '1', '2', '2020-10-10 19:07:01', '2020-10-10 19:07:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3486', '161', '1144', '奶茶', '0.00', '1', '1', '2020-10-10 19:07:09', '2020-10-10 19:07:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3487', '161', '1144', '奶绿', '2.00', '1', '2', '2020-10-10 19:07:18', '2020-10-10 19:07:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3488', '161', '1145', '热', '0.00', '1', '1', '2020-10-10 19:07:27', '2020-10-10 19:07:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3489', '161', '1145', '常温', '0.00', '1', '2', '2020-10-10 19:07:33', '2020-10-10 19:07:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3490', '161', '1145', '去冰', '0.00', '1', '3', '2020-10-10 19:07:39', '2020-10-10 19:07:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3491', '161', '1145', '冰', '0.00', '1', '4', '2020-10-10 19:07:47', '2020-10-10 19:07:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3500', '160', '1147', '中杯', '0.00', '1', '1', '2020-10-10 20:48:52', '2020-10-10 20:48:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3501', '160', '1147', '大杯', '2.00', '1', '2', '2020-10-10 20:48:57', '2020-10-10 20:48:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3502', '160', '1148', '奶茶', '0.00', '1', '1', '2020-10-10 20:49:03', '2020-10-10 20:49:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3503', '160', '1148', '奶绿', '0.00', '1', '2', '2020-10-10 20:49:12', '2020-10-10 20:49:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3504', '160', '1149', '热', '0.00', '1', '1', '2020-10-10 20:49:18', '2020-10-10 20:49:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3505', '160', '1149', '常温', '0.00', '1', '2', '2020-10-10 20:49:27', '2020-10-10 20:49:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3506', '160', '1149', '去冰', '0.00', '1', '3', '2020-10-10 20:49:33', '2020-10-10 20:49:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3507', '160', '1149', '冰', '0.00', '1', '4', '2020-10-10 20:49:39', '2020-10-10 20:49:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3516', '159', '1151', '700ML', '0.00', '1', '1', '2020-10-10 20:52:19', '2020-10-10 20:52:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3517', '159', '1152', '热', '0.00', '1', '1', '2020-10-10 20:52:27', '2020-10-10 20:52:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3518', '159', '1152', '常温', '0.00', '1', '2', '2020-10-10 20:52:32', '2020-10-10 20:52:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3519', '159', '1152', '去冰', '0.00', '1', '3', '2020-10-10 20:52:37', '2020-10-10 20:52:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3520', '159', '1152', '冰', '0.00', '1', '4', '2020-10-10 20:52:44', '2020-10-10 20:52:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3521', '158', '1153', '700ML', '0.00', '1', '1', '2020-10-10 20:54:03', '2020-10-10 20:54:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3522', '158', '1154', '冰', '0.00', '1', '1', '2020-10-10 20:54:08', '2020-10-10 20:54:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3523', '157', '1155', '700ML', '0.00', '1', '1', '2020-10-10 20:54:26', '2020-10-10 20:54:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3524', '157', '1156', '冰', '0.00', '1', '1', '2020-10-10 20:54:34', '2020-10-10 20:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3525', '156', '1157', '700ML', '0.00', '1', '1', '2020-10-10 20:55:08', '2020-10-10 20:55:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3526', '156', '1158', '冰', '0.00', '1', '1', '2020-10-10 20:55:12', '2020-10-10 20:55:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3527', '155', '1159', '700ML', '0.00', '1', '1', '2020-10-10 20:55:27', '2020-10-10 20:55:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3528', '155', '1160', '冰', '0.00', '1', '1', '2020-10-10 20:55:31', '2020-10-10 20:55:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3529', '154', '1161', '700ML', '0.00', '1', '1', '2020-10-10 20:55:46', '2020-10-10 20:55:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3530', '154', '1162', '冰', '0.00', '1', '1', '2020-10-10 20:55:51', '2020-10-10 20:55:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3531', '153', '1163', '700ML', '0.00', '1', '1', '2020-10-10 21:03:20', '2020-10-10 21:03:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3532', '153', '1164', '冰', '0.00', '1', '1', '2020-10-10 21:03:24', '2020-10-10 21:03:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3533', '152', '1165', '700ML', '0.00', '1', '1', '2020-10-10 21:03:35', '2020-10-10 21:03:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3534', '152', '1166', '冰', '0.00', '1', '1', '2020-10-10 21:03:40', '2020-10-10 21:03:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3539', '151', '1171', '中杯', '0.00', '1', '1', '2020-10-10 21:21:50', '2020-10-10 21:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3540', '151', '1171', '大杯', '2.00', '1', '2', '2020-10-10 21:21:54', '2020-10-10 21:21:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3541', '151', '1172', '热', '0.00', '1', '1', '2020-10-10 21:21:59', '2020-10-10 21:21:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3542', '151', '1172', '常温', '0.00', '1', '2', '2020-10-10 21:22:09', '2020-10-10 21:22:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3543', '151', '1172', '去冰', '0.00', '1', '3', '2020-10-10 21:22:37', '2020-10-10 21:22:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3544', '151', '1172', '冰', '0.00', '1', '4', '2020-10-10 21:22:49', '2020-10-10 21:22:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3552', '151', '1174', '奶茶', '0.00', '1', '1', '2020-10-10 21:25:53', '2020-10-10 21:25:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3553', '151', '1174', '奶绿', '0.00', '1', '2', '2020-10-10 21:26:05', '2020-10-10 21:26:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3554', '150', '1175', '中杯', '0.00', '1', '1', '2020-10-10 21:26:45', '2020-10-10 21:26:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3555', '150', '1175', '大杯', '2.00', '1', '2', '2020-10-10 21:26:49', '2020-10-10 21:26:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3556', '150', '1176', '奶茶', '0.00', '1', '1', '2020-10-10 21:27:42', '2020-10-10 21:27:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3557', '150', '1176', '奶绿', '0.00', '1', '2', '2020-10-10 21:27:48', '2020-10-10 21:27:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3558', '150', '1177', '热', '0.00', '1', '1', '2020-10-10 21:27:58', '2020-10-10 21:27:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3559', '150', '1177', '常温', '0.00', '1', '2', '2020-10-10 21:28:06', '2020-10-10 21:28:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3560', '150', '1177', '去冰', '0.00', '1', '3', '2020-10-10 21:28:14', '2020-10-10 21:28:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3561', '150', '1177', '冰', '0.00', '1', '4', '2020-10-10 21:28:19', '2020-10-10 21:28:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3570', '149', '1179', '700ML', '0.00', '1', '1', '2020-10-10 21:32:23', '2020-10-10 21:32:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3571', '149', '1180', '冰', '0.00', '1', '1', '2020-10-10 21:32:30', '2020-10-10 21:32:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3572', '148', '1181', '700ML', '0.00', '1', '1', '2020-10-10 21:32:47', '2020-10-10 21:32:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3573', '148', '1182', '冰', '0.00', '1', '1', '2020-10-10 21:32:51', '2020-10-10 21:32:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3574', '147', '1183', '700ML', '0.00', '1', '1', '2020-10-10 21:33:09', '2020-10-10 21:33:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3575', '147', '1184', '冰', '0.00', '1', '1', '2020-10-10 21:33:19', '2020-10-10 21:33:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3576', '146', '1185', '700ML', '0.00', '1', '1', '2020-10-10 21:33:46', '2020-10-10 21:33:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3577', '146', '1186', '冰', '0.00', '1', '1', '2020-10-10 21:33:59', '2020-10-10 21:33:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3578', '145', '1187', '大杯', '2.00', '1', '1', '2020-10-10 21:42:36', '2020-10-10 21:42:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3579', '145', '1188', '热', '0.00', '1', '1', '2020-10-10 21:42:42', '2020-10-10 21:42:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3580', '145', '1188', '常温', '0.00', '1', '2', '2020-10-10 21:42:46', '2020-10-10 21:42:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3581', '145', '1188', '去冰', '0.00', '1', '3', '2020-10-10 21:42:51', '2020-10-10 21:42:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3582', '145', '1188', '冰', '0.00', '1', '4', '2020-10-10 21:43:01', '2020-10-10 21:43:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3583', '144', '1189', '700ML', '0.00', '1', '1', '2020-10-10 21:54:53', '2020-10-10 21:54:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3584', '144', '1190', '热', '0.00', '1', '1', '2020-10-10 21:55:03', '2020-10-10 21:55:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3585', '144', '1190', '常温', '0.00', '1', '2', '2020-10-10 21:55:40', '2020-10-10 21:55:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3586', '144', '1190', '去冰', '0.00', '1', '3', '2020-10-10 21:55:45', '2020-10-10 21:55:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3587', '144', '1190', '冰', '0.00', '1', '4', '2020-10-10 21:55:49', '2020-10-10 21:55:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3588', '128', '1191', '700ML', '0.00', '1', '1', '2020-10-10 21:57:52', '2020-10-10 21:57:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3589', '128', '1192', '冰', '0.00', '1', '1', '2020-10-10 21:58:04', '2020-10-10 21:58:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3590', '127', '1193', '500ML', '0.00', '1', '1', '2020-10-10 21:58:25', '2020-10-10 21:58:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3591', '127', '1194', '热', '0.00', '1', '1', '2020-10-10 21:58:30', '2020-10-10 21:58:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3592', '127', '1194', '常温', '0.00', '1', '2', '2020-10-10 21:58:37', '2020-10-10 21:58:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3593', '127', '1194', '去冰', '0.00', '1', '3', '2020-10-10 21:58:47', '2020-10-10 21:58:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3594', '127', '1194', '冰', '0.00', '1', '4', '2020-10-10 21:58:52', '2020-10-10 21:58:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3595', '126', '1195', '中杯', '0.00', '1', '1', '2020-10-10 21:59:11', '2020-10-10 21:59:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3596', '126', '1195', '大杯', '2.00', '1', '2', '2020-10-10 21:59:17', '2020-10-10 21:59:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3597', '126', '1196', '热', '0.00', '1', '1', '2020-10-10 21:59:32', '2020-10-10 21:59:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3598', '126', '1196', '常温', '0.00', '1', '2', '2020-10-10 21:59:41', '2020-10-10 21:59:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3599', '126', '1196', '去冰', '0.00', '1', '3', '2020-10-10 21:59:50', '2020-10-10 21:59:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3600', '126', '1196', '冰', '0.00', '1', '4', '2020-10-10 21:59:55', '2020-10-10 21:59:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3601', '122', '1027', '不加', '0.00', '1', '5', '2020-10-10 22:00:17', '2020-10-10 22:00:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3602', '121', '1024', '不加', '0.00', '1', '5', '2020-10-10 22:01:00', '2020-10-10 22:01:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3603', '119', '1018', '不加', '0.00', '1', '5', '2020-10-10 22:01:10', '2020-10-10 22:01:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3605', '102', '981', '不加', '0.00', '1', '5', '2020-10-10 22:02:04', '2020-10-10 22:02:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3606', '197', '1197', '正常糖', '0.00', '1', '1', '2020-10-11 09:32:19', '2020-10-11 09:32:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3610', '196', '1198', '正常糖', '0.00', '1', '1', '2020-10-11 09:33:07', '2020-10-11 09:33:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3611', '196', '1198', '少糖', '0.00', '1', '2', '2020-10-11 09:33:27', '2020-10-11 09:33:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3612', '196', '1198', '无糖', '0.00', '1', '3', '2020-10-11 09:33:34', '2020-10-11 09:33:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3613', '196', '1198', '多糖', '0.00', '1', '4', '2020-10-11 09:33:41', '2020-10-11 09:33:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3614', '195', '1199', '正常糖', '0.00', '1', '1', '2020-10-11 09:33:52', '2020-10-11 09:33:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3615', '195', '1199', '少糖', '0.00', '1', '2', '2020-10-11 09:33:58', '2020-10-11 09:33:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3616', '195', '1199', '无糖', '0.00', '1', '3', '2020-10-11 09:34:04', '2020-10-11 09:34:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3617', '195', '1199', '多糖', '0.00', '1', '4', '2020-10-11 09:34:10', '2020-10-11 09:34:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3618', '194', '1200', '正常糖', '0.00', '1', '1', '2020-10-11 09:34:23', '2020-10-11 09:34:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3619', '194', '1200', '少糖', '0.00', '1', '2', '2020-10-11 09:34:29', '2020-10-11 09:34:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3620', '194', '1200', '无糖', '0.00', '1', '3', '2020-10-11 09:34:37', '2020-10-11 09:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3621', '194', '1200', '多糖', '0.00', '1', '4', '2020-10-11 09:34:43', '2020-10-11 09:34:43');
INSERT INTO `tb_goods_specification_option` VALUES ('3622', '193', '1201', '正常糖', '0.00', '1', '1', '2020-10-11 09:34:54', '2020-10-11 09:34:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3623', '193', '1201', '少糖', '0.00', '1', '2', '2020-10-11 09:35:01', '2020-10-11 09:35:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3624', '193', '1201', '无糖', '0.00', '1', '3', '2020-10-11 09:35:06', '2020-10-11 09:35:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3625', '193', '1201', '多糖', '0.00', '1', '4', '2020-10-11 09:35:13', '2020-10-11 09:35:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3626', '192', '1202', '正常糖', '0.00', '1', '1', '2020-10-11 09:38:10', '2020-10-11 09:38:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3627', '192', '1202', '少糖', '0.00', '1', '2', '2020-10-11 09:38:15', '2020-10-11 09:38:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3628', '192', '1202', '无糖', '0.00', '1', '3', '2020-10-11 09:38:19', '2020-10-11 09:38:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3629', '192', '1202', '多糖', '0.00', '1', '4', '2020-10-11 09:38:24', '2020-10-11 09:38:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3630', '191', '1203', '正常糖', '0.00', '1', '1', '2020-10-11 09:38:34', '2020-10-11 09:38:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3634', '190', '1204', '正常糖', '0.00', '1', '1', '2020-10-11 09:39:04', '2020-10-11 09:39:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3635', '190', '1204', '少糖', '0.00', '1', '2', '2020-10-11 09:39:08', '2020-10-11 09:39:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3636', '190', '1204', '无糖', '0.00', '1', '3', '2020-10-11 09:39:12', '2020-10-11 09:39:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3637', '190', '1204', '多糖', '0.00', '1', '4', '2020-10-11 09:39:17', '2020-10-11 09:39:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3638', '189', '1205', '正常糖', '0.00', '1', '1', '2020-10-11 09:39:27', '2020-10-11 09:39:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3639', '189', '1205', '少糖', '0.00', '1', '2', '2020-10-11 09:39:31', '2020-10-11 09:39:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3640', '189', '1205', '无糖', '0.00', '1', '3', '2020-10-11 09:39:37', '2020-10-11 09:39:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3641', '189', '1205', '多糖', '0.00', '1', '4', '2020-10-11 09:39:42', '2020-10-11 09:39:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3642', '188', '1206', '正常糖', '0.00', '1', '1', '2020-10-11 09:39:51', '2020-10-11 09:39:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3643', '188', '1206', '少糖', '0.00', '1', '2', '2020-10-11 09:39:55', '2020-10-11 09:39:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3644', '188', '1206', '无糖', '0.00', '1', '3', '2020-10-11 09:40:00', '2020-10-11 09:40:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3645', '188', '1206', '多糖', '0.00', '1', '4', '2020-10-11 09:40:05', '2020-10-11 09:40:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3646', '187', '1207', '正常糖', '0.00', '1', '1', '2020-10-11 09:40:15', '2020-10-11 09:40:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3647', '187', '1207', '少糖', '0.00', '1', '2', '2020-10-11 09:40:19', '2020-10-11 09:40:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3648', '187', '1207', '无糖', '0.00', '1', '3', '2020-10-11 09:40:23', '2020-10-11 09:40:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3649', '187', '1207', '多糖', '0.00', '1', '4', '2020-10-11 09:40:28', '2020-10-11 09:40:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3650', '186', '1208', '正常糖', '0.00', '1', '1', '2020-10-11 09:40:36', '2020-10-11 09:40:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3654', '136', '1209', '正常糖', '0.00', '1', '1', '2020-10-11 09:41:11', '2020-10-11 09:41:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3658', '135', '1210', '正常糖', '0.00', '1', '1', '2020-10-11 09:41:44', '2020-10-11 09:41:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3662', '134', '1211', '正常糖', '0.00', '1', '1', '2020-10-11 09:42:09', '2020-10-11 09:42:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3663', '134', '1211', '少糖', '0.00', '1', '2', '2020-10-11 09:42:17', '2020-10-11 09:42:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3664', '134', '1211', '无糖', '0.00', '1', '3', '2020-10-11 09:42:22', '2020-10-11 09:42:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3665', '134', '1211', '多糖', '0.00', '1', '4', '2020-10-11 09:42:27', '2020-10-11 09:42:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3666', '133', '1212', '正常糖', '0.00', '1', '1', '2020-10-11 09:42:51', '2020-10-11 09:42:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3667', '133', '1212', '少糖', '0.00', '1', '2', '2020-10-11 09:42:55', '2020-10-11 09:42:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3668', '133', '1212', '无糖', '0.00', '1', '3', '2020-10-11 09:42:59', '2020-10-11 09:42:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3669', '133', '1212', '多糖', '0.00', '1', '4', '2020-10-11 09:43:03', '2020-10-11 09:43:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3670', '132', '1213', '正常糖', '0.00', '1', '1', '2020-10-11 09:43:11', '2020-10-11 09:43:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3671', '132', '1213', '少糖', '0.00', '1', '2', '2020-10-11 09:43:15', '2020-10-11 09:43:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3672', '132', '1213', '无糖', '0.00', '1', '3', '2020-10-11 09:43:20', '2020-10-11 09:43:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3673', '132', '1213', '多糖', '0.00', '1', '4', '2020-10-11 09:43:26', '2020-10-11 09:43:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3674', '131', '1214', '正常糖', '0.00', '1', '1', '2020-10-11 09:43:41', '2020-10-11 09:43:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3675', '131', '1214', '少糖', '0.00', '1', '2', '2020-10-11 09:43:45', '2020-10-11 09:43:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3676', '131', '1214', '无糖', '0.00', '1', '3', '2020-10-11 09:43:50', '2020-10-11 09:43:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3677', '131', '1214', '多糖', '0.00', '1', '4', '2020-10-11 09:43:58', '2020-10-11 09:43:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3678', '130', '1215', '正常糖', '0.00', '1', '1', '2020-10-11 09:44:07', '2020-10-11 09:44:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3679', '130', '1215', '少糖', '0.00', '1', '2', '2020-10-11 09:44:11', '2020-10-11 09:44:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3680', '130', '1215', '无糖', '0.00', '1', '3', '2020-10-11 09:44:17', '2020-10-11 09:44:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3681', '130', '1215', '多糖', '0.00', '1', '4', '2020-10-11 09:44:22', '2020-10-11 09:44:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3682', '129', '1216', '正常糖', '0.00', '1', '1', '2020-10-11 09:44:31', '2020-10-11 09:44:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3683', '129', '1216', '少糖', '0.00', '1', '2', '2020-10-11 09:44:36', '2020-10-11 09:44:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3684', '129', '1216', '无糖', '0.00', '1', '3', '2020-10-11 09:44:41', '2020-10-11 09:44:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3685', '129', '1216', '多糖', '0.00', '1', '4', '2020-10-11 09:44:55', '2020-10-11 09:44:55');
INSERT INTO `tb_goods_specification_option` VALUES ('3686', '128', '1217', '正常糖', '0.00', '1', '1', '2020-10-11 09:45:08', '2020-10-11 09:45:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3687', '128', '1217', '少糖', '0.00', '1', '2', '2020-10-11 09:45:15', '2020-10-11 09:45:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3688', '128', '1217', '无糖', '0.00', '1', '3', '2020-10-11 09:45:22', '2020-10-11 09:45:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3689', '128', '1217', '多糖', '0.00', '1', '4', '2020-10-11 09:45:30', '2020-10-11 09:45:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3690', '127', '1218', '正常糖', '0.00', '1', '1', '2020-10-11 09:45:39', '2020-10-11 09:45:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3691', '127', '1218', '少糖', '0.00', '1', '2', '2020-10-11 09:45:44', '2020-10-11 09:45:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3692', '127', '1218', '无糖', '0.00', '1', '3', '2020-10-11 09:45:49', '2020-10-11 09:45:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3693', '127', '1218', '多糖', '0.00', '1', '4', '2020-10-11 09:45:54', '2020-10-11 09:45:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3694', '126', '1219', '正常糖', '0.00', '1', '1', '2020-10-11 09:46:03', '2020-10-11 09:46:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3695', '126', '1219', '少糖', '0.00', '1', '2', '2020-10-11 09:46:07', '2020-10-11 09:46:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3696', '126', '1219', '无糖', '0.00', '1', '3', '2020-10-11 09:46:12', '2020-10-11 09:46:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3697', '126', '1219', '多糖', '0.00', '1', '4', '2020-10-11 09:46:18', '2020-10-11 09:46:18');
INSERT INTO `tb_goods_specification_option` VALUES ('3698', '125', '1220', '正常糖', '0.00', '1', '1', '2020-10-11 09:46:27', '2020-10-11 09:46:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3699', '125', '1220', '少糖', '0.00', '1', '2', '2020-10-11 09:46:37', '2020-10-11 09:46:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3700', '125', '1220', '无糖', '0.00', '1', '3', '2020-10-11 09:46:40', '2020-10-11 09:46:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3701', '125', '1220', '多糖', '0.00', '1', '4', '2020-10-11 09:46:46', '2020-10-11 09:46:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3702', '124', '1221', '正常糖', '0.00', '1', '1', '2020-10-11 09:46:56', '2020-10-11 09:46:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3703', '124', '1221', '少糖', '0.00', '1', '2', '2020-10-11 09:47:01', '2020-10-11 09:47:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3704', '124', '1221', '无糖', '0.00', '1', '3', '2020-10-11 09:47:05', '2020-10-11 09:47:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3705', '124', '1221', '多糖', '0.00', '1', '4', '2020-10-11 09:47:09', '2020-10-11 09:47:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3706', '123', '1222', '正常糖', '0.00', '1', '1', '2020-10-11 09:47:21', '2020-10-11 09:47:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3707', '123', '1222', '少糖', '0.00', '1', '2', '2020-10-11 09:47:35', '2020-10-11 09:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3708', '123', '1222', '无糖', '0.00', '1', '3', '2020-10-11 09:47:40', '2020-10-11 09:47:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3709', '123', '1222', '多糖', '0.00', '1', '4', '2020-10-11 09:47:46', '2020-10-11 09:47:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3710', '122', '1223', '正常糖', '0.00', '1', '1', '2020-10-11 09:48:00', '2020-10-11 09:48:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3711', '122', '1223', '少糖', '0.00', '1', '2', '2020-10-11 09:48:05', '2020-10-11 09:48:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3712', '122', '1223', '无糖', '0.00', '1', '3', '2020-10-11 09:48:08', '2020-10-11 09:48:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3713', '122', '1223', '多糖', '0.00', '1', '4', '2020-10-11 09:48:20', '2020-10-11 09:48:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3714', '121', '1224', '正常糖', '0.00', '1', '1', '2020-10-11 09:48:33', '2020-10-11 09:48:33');
INSERT INTO `tb_goods_specification_option` VALUES ('3715', '121', '1224', '少糖', '0.00', '1', '2', '2020-10-11 09:48:37', '2020-10-11 09:48:37');
INSERT INTO `tb_goods_specification_option` VALUES ('3716', '121', '1224', '无糖', '0.00', '1', '3', '2020-10-11 09:48:41', '2020-10-11 09:48:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3717', '121', '1224', '多糖', '0.00', '1', '4', '2020-10-11 09:48:56', '2020-10-11 09:48:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3718', '120', '1225', '正常糖', '0.00', '1', '1', '2020-10-11 09:49:07', '2020-10-11 09:49:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3719', '120', '1225', '少糖', '0.00', '1', '2', '2020-10-11 09:49:10', '2020-10-11 09:49:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3720', '120', '1225', '无糖', '0.00', '1', '3', '2020-10-11 09:49:15', '2020-10-11 09:49:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3721', '120', '1225', '多糖', '0.00', '1', '4', '2020-10-11 09:49:19', '2020-10-11 09:49:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3722', '119', '1226', '正常糖', '0.00', '1', '1', '2020-10-11 09:49:29', '2020-10-11 09:49:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3723', '119', '1226', '少糖', '0.00', '1', '2', '2020-10-11 09:49:35', '2020-10-11 09:49:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3724', '119', '1226', '无糖', '0.00', '1', '3', '2020-10-11 09:49:42', '2020-10-11 09:49:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3725', '119', '1226', '多糖', '0.00', '1', '4', '2020-10-11 09:49:46', '2020-10-11 09:49:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3726', '118', '1227', '正常糖', '0.00', '1', '1', '2020-10-11 09:49:57', '2020-10-11 09:49:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3727', '118', '1227', '少糖', '0.00', '1', '2', '2020-10-11 09:50:04', '2020-10-11 09:50:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3729', '118', '1227', '多糖', '0.00', '1', '4', '2020-10-11 09:50:14', '2020-10-11 09:50:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3730', '117', '1228', '正常糖', '0.00', '1', '1', '2020-10-11 09:50:26', '2020-10-11 09:50:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3731', '117', '1228', '少糖', '0.00', '1', '2', '2020-10-11 09:50:31', '2020-10-11 09:50:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3732', '117', '1228', '无糖', '0.00', '1', '3', '2020-10-11 09:50:36', '2020-10-11 09:50:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3733', '117', '1228', '多糖', '0.00', '1', '4', '2020-10-11 09:50:41', '2020-10-11 09:50:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3734', '116', '1229', '正常糖', '0.00', '1', '1', '2020-10-11 10:21:11', '2020-10-11 10:21:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3735', '116', '1229', '少糖', '0.00', '1', '2', '2020-10-11 10:21:16', '2020-10-11 10:21:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3736', '116', '1229', '无糖', '0.00', '1', '3', '2020-10-11 10:21:27', '2020-10-11 10:21:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3737', '116', '1229', '多糖', '0.00', '1', '4', '2020-10-11 10:21:31', '2020-10-11 10:21:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3738', '115', '1230', '正常糖', '0.00', '1', '1', '2020-10-11 10:21:42', '2020-10-11 10:21:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3739', '115', '1230', '少糖', '0.00', '1', '2', '2020-10-11 10:21:52', '2020-10-11 10:21:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3740', '115', '1230', '无糖', '0.00', '1', '3', '2020-10-11 10:21:57', '2020-10-11 10:21:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3741', '115', '1230', '多糖', '0.00', '1', '4', '2020-10-11 10:22:02', '2020-10-11 10:22:02');
INSERT INTO `tb_goods_specification_option` VALUES ('3742', '114', '1231', '正常糖', '0.00', '1', '1', '2020-10-11 10:23:41', '2020-10-11 10:23:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3743', '114', '1231', '少糖', '0.00', '1', '2', '2020-10-11 10:23:50', '2020-10-11 10:23:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3744', '114', '1231', '无糖', '0.00', '1', '3', '2020-10-11 10:23:58', '2020-10-11 10:23:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3745', '114', '1231', '多糖', '0.00', '1', '4', '2020-10-11 10:24:02', '2020-10-11 10:24:02');
INSERT INTO `tb_goods_specification_option` VALUES ('3746', '113', '1232', '正常糖', '0.00', '1', '1', '2020-10-11 10:24:21', '2020-10-11 10:24:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3747', '113', '1232', '少糖', '0.00', '1', '2', '2020-10-11 10:24:29', '2020-10-11 10:24:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3748', '113', '1232', '无糖', '0.00', '1', '3', '2020-10-11 10:24:38', '2020-10-11 10:24:38');
INSERT INTO `tb_goods_specification_option` VALUES ('3749', '113', '1232', '多糖', '0.00', '1', '4', '2020-10-11 10:24:43', '2020-10-11 10:24:43');
INSERT INTO `tb_goods_specification_option` VALUES ('3750', '112', '1233', '正常糖', '0.00', '1', '1', '2020-10-11 10:24:51', '2020-10-11 10:24:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3751', '112', '1233', '少糖', '0.00', '1', '2', '2020-10-11 10:24:57', '2020-10-11 10:24:57');
INSERT INTO `tb_goods_specification_option` VALUES ('3752', '112', '1233', '无糖', '0.00', '1', '3', '2020-10-11 10:25:01', '2020-10-11 10:25:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3753', '112', '1233', '多糖', '0.00', '1', '4', '2020-10-11 10:25:08', '2020-10-11 10:25:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3754', '111', '1234', '正常糖', '0.00', '1', '1', '2020-10-11 10:25:19', '2020-10-11 10:25:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3755', '111', '1234', '少糖', '0.00', '1', '2', '2020-10-11 10:25:24', '2020-10-11 10:25:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3756', '111', '1234', '无糖', '0.00', '1', '3', '2020-10-11 10:25:41', '2020-10-11 10:25:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3757', '111', '1234', '多糖', '0.00', '1', '4', '2020-10-11 10:25:50', '2020-10-11 10:25:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3758', '110', '1235', '正常糖', '0.00', '1', '1', '2020-10-11 10:26:01', '2020-10-11 10:26:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3759', '110', '1235', '少糖', '0.00', '1', '2', '2020-10-11 10:26:07', '2020-10-11 10:26:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3760', '110', '1235', '无糖', '0.00', '1', '3', '2020-10-11 10:26:13', '2020-10-11 10:26:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3761', '110', '1235', '多糖', '0.00', '1', '4', '2020-10-11 10:26:21', '2020-10-11 10:26:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3762', '109', '1236', '正常糖', '0.00', '1', '1', '2020-10-11 10:26:31', '2020-10-11 10:26:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3763', '109', '1236', '少糖', '0.00', '1', '2', '2020-10-11 10:26:35', '2020-10-11 10:26:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3764', '109', '1236', '无糖', '0.00', '1', '3', '2020-10-11 10:26:40', '2020-10-11 10:26:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3765', '109', '1236', '多糖', '0.00', '1', '4', '2020-10-11 10:26:44', '2020-10-11 10:26:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3766', '108', '1237', '正常糖', '0.00', '1', '1', '2020-10-11 10:26:56', '2020-10-11 10:26:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3767', '108', '1237', '少糖', '0.00', '1', '2', '2020-10-11 10:27:00', '2020-10-11 10:27:00');
INSERT INTO `tb_goods_specification_option` VALUES ('3768', '108', '1237', '无糖', '0.00', '1', '3', '2020-10-11 10:27:05', '2020-10-11 10:27:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3769', '108', '1237', '多糖', '0.00', '1', '4', '2020-10-11 10:27:09', '2020-10-11 10:27:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3770', '107', '1238', '正常糖', '0.00', '1', '1', '2020-10-11 10:27:16', '2020-10-11 10:27:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3771', '107', '1238', '少糖', '0.00', '1', '2', '2020-10-11 10:27:21', '2020-10-11 10:27:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3772', '107', '1238', '无糖', '0.00', '1', '3', '2020-10-11 10:27:31', '2020-10-11 10:27:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3773', '107', '1238', '多糖', '0.00', '1', '4', '2020-10-11 10:27:53', '2020-10-11 10:27:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3774', '106', '1239', '正常糖', '0.00', '1', '1', '2020-10-11 10:28:03', '2020-10-11 10:28:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3775', '106', '1239', '少糖', '0.00', '1', '2', '2020-10-11 10:28:07', '2020-10-11 10:28:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3776', '106', '1239', '无糖', '0.00', '1', '3', '2020-10-11 10:28:11', '2020-10-11 10:28:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3777', '106', '1239', '多糖', '0.00', '1', '4', '2020-10-11 10:28:19', '2020-10-11 10:28:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3778', '105', '1240', '正常糖', '0.00', '1', '1', '2020-10-11 10:28:28', '2020-10-11 10:28:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3779', '105', '1240', '少糖', '0.00', '1', '2', '2020-10-11 10:28:32', '2020-10-11 10:28:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3780', '105', '1240', '无糖', '0.00', '1', '3', '2020-10-11 10:28:36', '2020-10-11 10:28:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3781', '105', '1240', '多糖', '0.00', '1', '4', '2020-10-11 10:28:42', '2020-10-11 10:28:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3782', '104', '1241', '正常糖', '0.00', '1', '1', '2020-10-11 10:28:51', '2020-10-11 10:28:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3783', '104', '1241', '少糖', '0.00', '1', '2', '2020-10-11 10:29:01', '2020-10-11 10:29:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3784', '104', '1241', '无糖', '0.00', '1', '3', '2020-10-11 10:29:05', '2020-10-11 10:29:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3785', '104', '1241', '多糖', '0.00', '1', '4', '2020-10-11 10:29:13', '2020-10-11 10:29:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3786', '103', '1242', '正常糖', '0.00', '1', '1', '2020-10-11 10:29:20', '2020-10-11 10:29:20');
INSERT INTO `tb_goods_specification_option` VALUES ('3787', '103', '1242', '少糖', '0.00', '1', '2', '2020-10-11 10:29:25', '2020-10-11 10:29:25');
INSERT INTO `tb_goods_specification_option` VALUES ('3788', '103', '1242', '无糖', '0.00', '1', '3', '2020-10-11 10:29:30', '2020-10-11 10:29:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3789', '103', '1242', '多糖', '0.00', '1', '4', '2020-10-11 10:29:41', '2020-10-11 10:29:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3790', '102', '1243', '正常糖', '0.00', '1', '1', '2020-10-11 10:29:49', '2020-10-11 10:29:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3791', '102', '1243', '少糖', '0.00', '1', '2', '2020-10-11 10:29:58', '2020-10-11 10:29:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3792', '102', '1243', '无糖', '0.00', '1', '3', '2020-10-11 10:30:03', '2020-10-11 10:30:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3793', '102', '1243', '多糖', '0.00', '1', '4', '2020-10-11 10:30:08', '2020-10-11 10:30:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3798', '100', '1245', '正常糖', '0.00', '1', '1', '2020-10-11 10:30:58', '2020-10-11 10:30:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3799', '100', '1245', '少糖', '0.00', '1', '2', '2020-10-11 10:31:04', '2020-10-11 10:31:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3800', '100', '1245', '无糖', '0.00', '1', '3', '2020-10-11 10:31:16', '2020-10-11 10:31:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3801', '100', '1245', '多糖', '0.00', '1', '4', '2020-10-11 10:31:27', '2020-10-11 10:31:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3802', '96', '1246', '正常糖', '0.00', '1', '1', '2020-10-11 10:31:36', '2020-10-11 10:31:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3803', '96', '1246', '少糖', '0.00', '1', '2', '2020-10-11 10:31:58', '2020-10-11 10:31:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3804', '96', '1246', '无糖', '0.00', '1', '3', '2020-10-11 10:32:02', '2020-10-11 10:32:02');
INSERT INTO `tb_goods_specification_option` VALUES ('3805', '96', '1246', '多糖', '0.00', '1', '4', '2020-10-11 10:32:07', '2020-10-11 10:32:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3806', '95', '1247', '正常糖', '0.00', '1', '1', '2020-10-11 10:32:23', '2020-10-11 10:32:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3807', '95', '1247', '少糖', '0.00', '1', '2', '2020-10-11 10:32:32', '2020-10-11 10:32:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3808', '95', '1247', '无糖', '0.00', '1', '3', '2020-10-11 10:32:41', '2020-10-11 10:32:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3809', '95', '1247', '多糖', '0.00', '1', '4', '2020-10-11 10:32:52', '2020-10-11 10:32:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3810', '94', '1248', '正常糖', '0.00', '1', '1', '2020-10-11 10:33:07', '2020-10-11 10:33:07');
INSERT INTO `tb_goods_specification_option` VALUES ('3811', '94', '1248', '少糖', '0.00', '1', '2', '2020-10-11 10:33:12', '2020-10-11 10:33:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3812', '94', '1248', '无糖', '0.00', '1', '3', '2020-10-11 10:33:16', '2020-10-11 10:33:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3813', '94', '1248', '多糖', '0.00', '1', '4', '2020-10-11 10:33:21', '2020-10-11 10:33:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3814', '93', '1249', '正常糖', '0.00', '1', '1', '2020-10-11 10:33:29', '2020-10-11 10:33:29');
INSERT INTO `tb_goods_specification_option` VALUES ('3815', '93', '1249', '少糖', '0.00', '1', '2', '2020-10-11 10:33:39', '2020-10-11 10:33:39');
INSERT INTO `tb_goods_specification_option` VALUES ('3816', '93', '1249', '无糖', '0.00', '1', '3', '2020-10-11 10:33:46', '2020-10-11 10:33:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3817', '93', '1249', '多糖', '0.00', '1', '4', '2020-10-11 10:33:53', '2020-10-11 10:33:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3818', '92', '1250', '正常糖', '0.00', '1', '1', '2020-10-11 10:34:01', '2020-10-11 10:34:01');
INSERT INTO `tb_goods_specification_option` VALUES ('3819', '92', '1250', '少糖', '0.00', '1', '2', '2020-10-11 10:34:10', '2020-10-11 10:34:10');
INSERT INTO `tb_goods_specification_option` VALUES ('3820', '92', '1250', '无糖', '0.00', '1', '3', '2020-10-11 10:34:17', '2020-10-11 10:34:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3821', '92', '1250', '多糖', '0.00', '1', '4', '2020-10-11 10:34:21', '2020-10-11 10:34:21');
INSERT INTO `tb_goods_specification_option` VALUES ('3822', '91', '1251', '正常糖', '0.00', '1', '1', '2020-10-11 10:34:32', '2020-10-11 10:34:32');
INSERT INTO `tb_goods_specification_option` VALUES ('3823', '91', '1251', '少糖', '0.00', '1', '2', '2020-10-11 10:34:36', '2020-10-11 10:34:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3824', '91', '1251', '无糖', '0.00', '1', '3', '2020-10-11 10:34:41', '2020-10-11 10:34:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3825', '91', '1251', '多糖', '0.00', '1', '4', '2020-10-11 10:34:47', '2020-10-11 10:34:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3826', '90', '1252', '正常糖', '0.00', '1', '1', '2020-10-11 10:41:49', '2020-10-11 10:41:49');
INSERT INTO `tb_goods_specification_option` VALUES ('3827', '90', '1252', '少糖', '0.00', '1', '2', '2020-10-11 10:41:53', '2020-10-11 10:41:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3828', '90', '1252', '无糖', '0.00', '1', '3', '2020-10-11 10:41:58', '2020-10-11 10:41:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3829', '90', '1252', '多糖', '0.00', '1', '4', '2020-10-11 10:42:03', '2020-10-11 10:42:03');
INSERT INTO `tb_goods_specification_option` VALUES ('3830', '89', '1253', '正常糖', '0.00', '1', '1', '2020-10-11 10:42:14', '2020-10-11 10:42:14');
INSERT INTO `tb_goods_specification_option` VALUES ('3831', '89', '1253', '少糖', '0.00', '1', '2', '2020-10-11 10:42:22', '2020-10-11 10:42:22');
INSERT INTO `tb_goods_specification_option` VALUES ('3833', '89', '1253', '多糖', '0.00', '1', '4', '2020-10-11 10:42:34', '2020-10-11 10:42:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3834', '88', '1254', '正常糖', '0.00', '1', '1', '2020-10-11 10:42:44', '2020-10-11 10:42:44');
INSERT INTO `tb_goods_specification_option` VALUES ('3835', '88', '1254', '少糖', '0.00', '1', '2', '2020-10-11 10:42:48', '2020-10-11 10:42:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3836', '88', '1254', '无糖', '0.00', '1', '3', '2020-10-11 10:42:52', '2020-10-11 10:42:52');
INSERT INTO `tb_goods_specification_option` VALUES ('3837', '88', '1254', '多糖', '0.00', '1', '4', '2020-10-11 10:42:56', '2020-10-11 10:42:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3838', '87', '1255', '正常糖', '0.00', '1', '1', '2020-10-11 10:43:06', '2020-10-11 10:43:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3842', '86', '1256', '正常糖', '0.00', '1', '1', '2020-10-11 10:43:58', '2020-10-11 10:43:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3843', '86', '1256', '少糖', '0.00', '1', '2', '2020-10-11 10:44:06', '2020-10-11 10:44:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3844', '86', '1256', '无糖', '0.00', '1', '3', '2020-10-11 10:44:13', '2020-10-11 10:44:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3845', '86', '1256', '多糖', '0.00', '1', '4', '2020-10-11 10:44:24', '2020-10-11 10:44:24');
INSERT INTO `tb_goods_specification_option` VALUES ('3847', '85', '1257', '少糖', '0.00', '1', '2', '2020-10-11 10:44:41', '2020-10-11 10:44:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3848', '85', '1257', '无糖', '0.00', '1', '3', '2020-10-11 10:44:47', '2020-10-11 10:44:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3849', '85', '1257', '多糖', '0.00', '1', '4', '2020-10-11 10:44:56', '2020-10-11 10:44:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3850', '84', '1258', '正常糖', '0.00', '1', '1', '2020-10-11 10:45:04', '2020-10-11 10:45:04');
INSERT INTO `tb_goods_specification_option` VALUES ('3854', '83', '1259', '正常糖', '0.00', '1', '1', '2020-10-11 10:45:30', '2020-10-11 10:45:30');
INSERT INTO `tb_goods_specification_option` VALUES ('3855', '83', '1259', '少糖', '0.00', '1', '2', '2020-10-11 10:45:35', '2020-10-11 10:45:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3856', '83', '1259', '无糖', '0.00', '1', '3', '2020-10-11 10:45:40', '2020-10-11 10:45:40');
INSERT INTO `tb_goods_specification_option` VALUES ('3857', '83', '1259', '多糖', '0.00', '1', '4', '2020-10-11 10:45:45', '2020-10-11 10:45:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3872', '198', '1264', '中杯', '0.00', '1', '1', '2020-10-12 11:16:08', '2020-10-12 11:16:08');
INSERT INTO `tb_goods_specification_option` VALUES ('3873', '198', '1264', '大杯', '2.00', '1', '2', '2020-10-12 11:16:12', '2020-10-12 11:16:12');
INSERT INTO `tb_goods_specification_option` VALUES ('3875', '198', '1265', '常温', '0.00', '1', '2', '2020-10-12 11:16:36', '2020-10-12 11:16:36');
INSERT INTO `tb_goods_specification_option` VALUES ('3877', '198', '1265', '冰', '0.00', '1', '4', '2020-10-12 11:16:47', '2020-10-12 11:16:47');
INSERT INTO `tb_goods_specification_option` VALUES ('3888', '198', '1267', '正常糖', '0.00', '1', '2', '2020-10-12 11:19:27', '2020-10-12 11:19:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3890', '198', '1267', '双份糖', '0.00', '1', '4', '2020-10-12 11:19:41', '2020-10-12 11:19:41');
INSERT INTO `tb_goods_specification_option` VALUES ('3905', '199', '1272', '500ML', '0.00', '1', '1', '2020-10-12 11:23:34', '2020-10-12 11:23:34');
INSERT INTO `tb_goods_specification_option` VALUES ('3906', '199', '1273', '热', '0.00', '1', '1', '2020-10-12 11:23:42', '2020-10-12 11:23:42');
INSERT INTO `tb_goods_specification_option` VALUES ('3907', '199', '1273', '常温', '0.00', '1', '2', '2020-10-12 11:23:45', '2020-10-12 11:23:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3908', '199', '1273', '去冰', '0.00', '1', '3', '2020-10-12 11:23:53', '2020-10-12 11:23:53');
INSERT INTO `tb_goods_specification_option` VALUES ('3909', '199', '1273', '冰', '0.00', '1', '4', '2020-10-12 11:23:56', '2020-10-12 11:23:56');
INSERT INTO `tb_goods_specification_option` VALUES ('3910', '199', '1274', '正常糖', '0.00', '1', '1', '2020-10-12 11:24:19', '2020-10-12 11:24:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3911', '199', '1274', '少糖', '0.00', '1', '2', '2020-10-12 11:24:23', '2020-10-12 11:24:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3912', '199', '1274', '无糖', '0.00', '1', '3', '2020-10-12 11:24:27', '2020-10-12 11:24:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3913', '199', '1274', '多糖', '0.00', '1', '4', '2020-10-12 11:24:31', '2020-10-12 11:24:31');
INSERT INTO `tb_goods_specification_option` VALUES ('3928', '200', '1279', '500ML', '0.00', '1', '1', '2020-10-12 11:32:48', '2020-10-12 11:32:48');
INSERT INTO `tb_goods_specification_option` VALUES ('3929', '200', '1280', '冰', '0.00', '1', '1', '2020-10-12 11:32:58', '2020-10-12 11:32:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3930', '200', '1281', '正常糖', '0.00', '1', '1', '2020-10-12 11:33:11', '2020-10-12 11:33:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3931', '200', '1281', '少糖', '0.00', '1', '2', '2020-10-12 11:33:17', '2020-10-12 11:33:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3932', '200', '1281', '无糖', '0.00', '1', '3', '2020-10-12 11:33:23', '2020-10-12 11:33:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3933', '200', '1281', '多糖', '0.00', '1', '4', '2020-10-12 11:33:27', '2020-10-12 11:33:27');
INSERT INTO `tb_goods_specification_option` VALUES ('3948', '201', '1286', '700ML', '0.00', '1', '1', '2020-10-12 12:02:16', '2020-10-12 12:02:16');
INSERT INTO `tb_goods_specification_option` VALUES ('3949', '201', '1287', '去冰', '0.00', '1', '1', '2020-10-12 12:02:35', '2020-10-12 12:02:35');
INSERT INTO `tb_goods_specification_option` VALUES ('3950', '201', '1287', '冰', '0.00', '1', '2', '2020-10-12 12:02:45', '2020-10-12 12:02:45');
INSERT INTO `tb_goods_specification_option` VALUES ('3951', '201', '1288', '正常糖', '0.00', '1', '1', '2020-10-12 12:02:58', '2020-10-12 12:02:58');
INSERT INTO `tb_goods_specification_option` VALUES ('3952', '201', '1288', '少糖', '0.00', '1', '2', '2020-10-12 12:03:02', '2020-10-12 12:03:02');
INSERT INTO `tb_goods_specification_option` VALUES ('3953', '201', '1288', '无糖', '0.00', '1', '3', '2020-10-12 12:03:06', '2020-10-12 12:03:06');
INSERT INTO `tb_goods_specification_option` VALUES ('3954', '201', '1288', '多糖', '0.00', '1', '4', '2020-10-12 12:03:11', '2020-10-12 12:03:11');
INSERT INTO `tb_goods_specification_option` VALUES ('3969', '202', '1293', '500ML', '0.00', '1', '1', '2020-10-12 12:06:19', '2020-10-12 12:06:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3970', '202', '1294', '去冰', '0.00', '1', '1', '2020-10-12 12:06:50', '2020-10-12 12:06:50');
INSERT INTO `tb_goods_specification_option` VALUES ('3971', '202', '1294', '冰', '0.00', '1', '2', '2020-10-12 12:06:54', '2020-10-12 12:06:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3972', '202', '1295', '正常糖', '0.00', '1', '1', '2020-10-12 12:07:05', '2020-10-12 12:07:05');
INSERT INTO `tb_goods_specification_option` VALUES ('3973', '202', '1295', '少糖', '0.00', '1', '2', '2020-10-12 12:07:09', '2020-10-12 12:07:09');
INSERT INTO `tb_goods_specification_option` VALUES ('3974', '202', '1295', '无糖', '0.00', '1', '3', '2020-10-12 12:07:13', '2020-10-12 12:07:13');
INSERT INTO `tb_goods_specification_option` VALUES ('3975', '202', '1295', '多糖', '0.00', '1', '4', '2020-10-12 12:07:17', '2020-10-12 12:07:17');
INSERT INTO `tb_goods_specification_option` VALUES ('3990', '203', '1300', '中杯', '0.00', '1', '1', '2020-10-12 12:09:19', '2020-10-12 12:09:19');
INSERT INTO `tb_goods_specification_option` VALUES ('3991', '203', '1300', '大杯', '0.00', '1', '2', '2020-10-12 12:09:26', '2020-10-12 12:09:26');
INSERT INTO `tb_goods_specification_option` VALUES ('3992', '203', '1301', '热', '0.00', '1', '1', '2020-10-12 12:09:46', '2020-10-12 12:09:46');
INSERT INTO `tb_goods_specification_option` VALUES ('3993', '203', '1301', '常温', '0.00', '1', '2', '2020-10-12 12:09:51', '2020-10-12 12:09:51');
INSERT INTO `tb_goods_specification_option` VALUES ('3994', '203', '1301', '去冰', '0.00', '1', '3', '2020-10-12 12:09:54', '2020-10-12 12:09:54');
INSERT INTO `tb_goods_specification_option` VALUES ('3995', '203', '1301', '冰', '0.00', '1', '4', '2020-10-12 12:09:59', '2020-10-12 12:09:59');
INSERT INTO `tb_goods_specification_option` VALUES ('3996', '203', '1302', '正常糖', '0.00', '1', '1', '2020-10-12 12:10:15', '2020-10-12 12:10:15');
INSERT INTO `tb_goods_specification_option` VALUES ('3997', '203', '1302', '少糖', '0.00', '1', '2', '2020-10-12 12:10:23', '2020-10-12 12:10:23');
INSERT INTO `tb_goods_specification_option` VALUES ('3998', '203', '1302', '无糖', '0.00', '1', '3', '2020-10-12 12:10:28', '2020-10-12 12:10:28');
INSERT INTO `tb_goods_specification_option` VALUES ('3999', '203', '1302', '多糖', '0.00', '1', '4', '2020-10-12 12:10:49', '2020-10-12 12:10:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4014', '204', '1307', '700ML', '0.00', '1', '1', '2020-10-12 12:12:50', '2020-10-12 12:12:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4015', '204', '1308', '去冰', '0.00', '1', '1', '2020-10-12 12:12:58', '2020-10-12 12:12:58');
INSERT INTO `tb_goods_specification_option` VALUES ('4016', '204', '1308', '冰', '0.00', '1', '2', '2020-10-12 12:13:01', '2020-10-12 12:13:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4017', '204', '1309', '正常糖', '0.00', '1', '1', '2020-10-12 12:13:07', '2020-10-12 12:13:07');
INSERT INTO `tb_goods_specification_option` VALUES ('4018', '204', '1309', '少糖', '0.00', '1', '2', '2020-10-12 12:13:18', '2020-10-12 12:13:18');
INSERT INTO `tb_goods_specification_option` VALUES ('4019', '204', '1309', '无糖', '0.00', '1', '3', '2020-10-12 12:13:21', '2020-10-12 12:13:21');
INSERT INTO `tb_goods_specification_option` VALUES ('4020', '204', '1309', '多糖', '0.00', '1', '4', '2020-10-12 12:13:27', '2020-10-12 12:13:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4035', '205', '1314', '700ML', '0.00', '1', '1', '2020-10-12 12:16:03', '2020-10-12 12:16:03');
INSERT INTO `tb_goods_specification_option` VALUES ('4036', '205', '1315', '去冰', '0.00', '1', '1', '2020-10-12 12:16:14', '2020-10-12 12:16:14');
INSERT INTO `tb_goods_specification_option` VALUES ('4037', '205', '1315', '冰', '0.00', '1', '2', '2020-10-12 12:16:18', '2020-10-12 12:16:18');
INSERT INTO `tb_goods_specification_option` VALUES ('4038', '205', '1316', '正常糖', '0.00', '1', '1', '2020-10-12 12:16:25', '2020-10-12 12:16:25');
INSERT INTO `tb_goods_specification_option` VALUES ('4039', '205', '1316', '少糖', '0.00', '1', '2', '2020-10-12 12:16:29', '2020-10-12 12:16:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4040', '205', '1316', '无糖', '0.00', '1', '3', '2020-10-12 12:16:33', '2020-10-12 12:16:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4041', '205', '1316', '多糖', '0.00', '1', '4', '2020-10-12 12:16:40', '2020-10-12 12:16:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4056', '206', '1321', '700ML', '0.00', '1', '1', '2020-10-13 15:05:01', '2020-10-13 15:05:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4057', '206', '1322', '冰', '0.00', '1', '1', '2020-10-13 15:05:15', '2020-10-13 15:05:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4058', '206', '1323', '正常糖', '0.00', '1', '1', '2020-10-13 15:05:22', '2020-10-13 15:05:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4059', '206', '1323', '少糖', '0.00', '1', '2', '2020-10-13 15:05:29', '2020-10-13 15:05:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4060', '206', '1323', '无糖', '0.00', '1', '3', '2020-10-13 15:05:32', '2020-10-13 15:05:32');
INSERT INTO `tb_goods_specification_option` VALUES ('4061', '206', '1323', '多糖', '0.00', '1', '4', '2020-10-13 15:05:36', '2020-10-13 15:05:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4076', '207', '1328', '700ML', '0.00', '1', '1', '2020-10-13 15:07:43', '2020-10-13 15:07:43');
INSERT INTO `tb_goods_specification_option` VALUES ('4077', '207', '1329', '冰', '0.00', '1', '1', '2020-10-13 15:07:50', '2020-10-13 15:07:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4078', '207', '1330', '正常糖', '0.00', '1', '1', '2020-10-13 15:07:55', '2020-10-13 15:07:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4079', '207', '1330', '少糖', '0.00', '1', '2', '2020-10-13 15:07:58', '2020-10-13 15:07:58');
INSERT INTO `tb_goods_specification_option` VALUES ('4080', '207', '1330', '无糖', '0.00', '1', '3', '2020-10-13 15:08:02', '2020-10-13 15:08:02');
INSERT INTO `tb_goods_specification_option` VALUES ('4081', '207', '1330', '多糖', '0.00', '1', '4', '2020-10-13 15:08:07', '2020-10-13 15:08:07');
INSERT INTO `tb_goods_specification_option` VALUES ('4096', '208', '1335', '700ML', '0.00', '1', '1', '2020-10-13 15:11:23', '2020-10-13 15:11:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4097', '208', '1336', '冰', '0.00', '1', '1', '2020-10-13 15:11:27', '2020-10-13 15:11:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4098', '208', '1337', '正常糖', '0.00', '1', '1', '2020-10-13 15:11:32', '2020-10-13 15:11:32');
INSERT INTO `tb_goods_specification_option` VALUES ('4099', '208', '1337', '少糖', '0.00', '1', '2', '2020-10-13 15:11:36', '2020-10-13 15:11:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4100', '208', '1337', '无糖', '0.00', '1', '3', '2020-10-13 15:11:40', '2020-10-13 15:11:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4101', '208', '1337', '多糖', '0.00', '1', '4', '2020-10-13 15:11:44', '2020-10-13 15:11:44');
INSERT INTO `tb_goods_specification_option` VALUES ('4116', '209', '1342', '700ML', '0.00', '1', '1', '2020-10-13 15:20:06', '2020-10-13 15:20:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4117', '209', '1343', '冰', '0.00', '1', '1', '2020-10-13 15:20:10', '2020-10-13 15:20:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4118', '209', '1344', '正常糖', '0.00', '1', '1', '2020-10-13 15:21:38', '2020-10-13 15:21:38');
INSERT INTO `tb_goods_specification_option` VALUES ('4119', '209', '1344', '少糖', '0.00', '1', '2', '2020-10-13 15:21:42', '2020-10-13 15:21:42');
INSERT INTO `tb_goods_specification_option` VALUES ('4120', '209', '1344', '无糖', '0.00', '1', '3', '2020-10-13 15:21:48', '2020-10-13 15:21:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4121', '209', '1344', '多糖', '0.00', '1', '4', '2020-10-13 15:21:53', '2020-10-13 15:21:53');
INSERT INTO `tb_goods_specification_option` VALUES ('4136', '210', '1349', '700ML', '0.00', '1', '1', '2020-10-13 15:23:55', '2020-10-13 15:23:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4137', '210', '1350', '冰', '0.00', '1', '1', '2020-10-13 15:24:13', '2020-10-13 15:24:13');
INSERT INTO `tb_goods_specification_option` VALUES ('4138', '210', '1351', '正常糖', '0.00', '1', '1', '2020-10-13 15:24:29', '2020-10-13 15:24:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4139', '210', '1351', '少糖', '0.00', '1', '2', '2020-10-13 15:24:32', '2020-10-13 15:24:32');
INSERT INTO `tb_goods_specification_option` VALUES ('4140', '210', '1351', '无糖', '0.00', '1', '3', '2020-10-13 15:24:36', '2020-10-13 15:24:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4141', '210', '1351', '多糖', '0.00', '1', '4', '2020-10-13 15:24:41', '2020-10-13 15:24:41');
INSERT INTO `tb_goods_specification_option` VALUES ('4142', '211', '1352', '常规', '0.00', '1', '1', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4143', '211', '1353', '热', '0.00', '1', '1', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4144', '211', '1353', '温', '0.00', '1', '2', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4145', '211', '1353', '常规冰', '0.00', '1', '3', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4146', '211', '1353', '多冰', '0.00', '1', '4', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4147', '211', '1353', '少冰', '0.00', '1', '5', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4148', '211', '1353', '去冰', '0.00', '1', '6', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4149', '211', '1354', '常规糖', '0.00', '1', '1', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4150', '211', '1354', '半糖', '0.00', '1', '2', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4151', '211', '1354', '微糖', '0.00', '1', '3', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4152', '211', '1354', '不另外加糖', '0.00', '1', '4', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4153', '211', '1355', '小杯', '0.00', '1', '1', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4154', '211', '1355', '中杯', '2.00', '1', '2', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4155', '211', '1355', '大杯', '4.00', '1', '3', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4170', '212', '1360', '700ML', '0.00', '1', '1', '2020-10-13 15:29:18', '2020-10-13 15:29:18');
INSERT INTO `tb_goods_specification_option` VALUES ('4171', '212', '1361', '冰', '0.00', '1', '1', '2020-10-13 15:29:21', '2020-10-13 15:29:21');
INSERT INTO `tb_goods_specification_option` VALUES ('4172', '212', '1362', '正常糖', '0.00', '1', '1', '2020-10-13 15:29:26', '2020-10-13 15:29:26');
INSERT INTO `tb_goods_specification_option` VALUES ('4173', '212', '1362', '少糖', '0.00', '1', '2', '2020-10-13 15:29:29', '2020-10-13 15:29:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4174', '212', '1362', '无糖', '0.00', '1', '3', '2020-10-13 15:29:32', '2020-10-13 15:29:32');
INSERT INTO `tb_goods_specification_option` VALUES ('4175', '212', '1362', '多糖', '0.00', '1', '4', '2020-10-13 15:29:39', '2020-10-13 15:29:39');
INSERT INTO `tb_goods_specification_option` VALUES ('4190', '213', '1367', '700ML', '0.00', '1', '1', '2020-10-13 15:31:45', '2020-10-13 15:31:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4191', '213', '1368', '冰', '0.00', '1', '1', '2020-10-13 15:31:50', '2020-10-13 15:31:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4192', '213', '1369', '正常糖', '0.00', '1', '1', '2020-10-13 15:32:00', '2020-10-13 15:32:00');
INSERT INTO `tb_goods_specification_option` VALUES ('4207', '214', '1374', '500ML', '0.00', '1', '1', '2020-10-13 15:35:26', '2020-10-13 15:35:26');
INSERT INTO `tb_goods_specification_option` VALUES ('4208', '214', '1375', '去冰', '0.00', '1', '1', '2020-10-13 15:35:30', '2020-10-13 15:35:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4209', '214', '1375', '冰', '0.00', '1', '2', '2020-10-13 15:35:46', '2020-10-13 15:35:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4210', '214', '1376', '正常糖', '0.00', '1', '1', '2020-10-13 15:35:53', '2020-10-13 15:35:53');
INSERT INTO `tb_goods_specification_option` VALUES ('4211', '214', '1376', '少糖', '0.00', '1', '2', '2020-10-13 15:35:56', '2020-10-13 15:35:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4212', '214', '1376', '无糖', '0.00', '1', '3', '2020-10-13 15:36:00', '2020-10-13 15:36:00');
INSERT INTO `tb_goods_specification_option` VALUES ('4213', '214', '1376', '多糖', '0.00', '1', '4', '2020-10-13 15:36:04', '2020-10-13 15:36:04');
INSERT INTO `tb_goods_specification_option` VALUES ('4228', '215', '1381', '700ML', '0.00', '1', '1', '2020-10-13 15:38:24', '2020-10-13 15:38:24');
INSERT INTO `tb_goods_specification_option` VALUES ('4229', '215', '1382', '去冰', '0.00', '1', '1', '2020-10-13 15:38:34', '2020-10-13 15:38:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4230', '215', '1382', '冰', '0.00', '1', '2', '2020-10-13 15:38:37', '2020-10-13 15:38:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4231', '215', '1383', '正常糖', '0.00', '1', '1', '2020-10-13 15:38:46', '2020-10-13 15:38:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4232', '215', '1383', '少糖', '0.00', '1', '2', '2020-10-13 15:38:49', '2020-10-13 15:38:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4233', '215', '1383', '无糖', '0.00', '1', '3', '2020-10-13 15:38:54', '2020-10-13 15:38:54');
INSERT INTO `tb_goods_specification_option` VALUES ('4234', '215', '1383', '多糖', '0.00', '1', '4', '2020-10-13 15:38:58', '2020-10-13 15:38:58');
INSERT INTO `tb_goods_specification_option` VALUES ('4249', '216', '1388', '500ML', '0.00', '1', '1', '2020-10-13 15:41:35', '2020-10-13 15:41:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4250', '216', '1389', '热', '0.00', '1', '1', '2020-10-13 15:41:42', '2020-10-13 15:41:42');
INSERT INTO `tb_goods_specification_option` VALUES ('4251', '216', '1389', '常温', '0.00', '1', '2', '2020-10-13 15:41:46', '2020-10-13 15:41:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4252', '216', '1389', '去冰', '0.00', '1', '3', '2020-10-13 15:41:55', '2020-10-13 15:41:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4253', '216', '1389', '冰', '0.00', '1', '4', '2020-10-13 15:42:01', '2020-10-13 15:42:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4268', '217', '1394', '中杯', '0.00', '1', '1', '2020-10-13 15:45:00', '2020-10-13 15:45:00');
INSERT INTO `tb_goods_specification_option` VALUES ('4269', '217', '1394', '大杯', '2.00', '1', '2', '2020-10-13 15:45:07', '2020-10-13 15:45:07');
INSERT INTO `tb_goods_specification_option` VALUES ('4270', '217', '1395', '热', '0.00', '1', '1', '2020-10-13 15:45:11', '2020-10-13 15:45:11');
INSERT INTO `tb_goods_specification_option` VALUES ('4271', '217', '1395', '常温', '0.00', '1', '2', '2020-10-13 15:45:15', '2020-10-13 15:45:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4272', '217', '1395', '去冰', '0.00', '1', '3', '2020-10-13 15:45:19', '2020-10-13 15:45:19');
INSERT INTO `tb_goods_specification_option` VALUES ('4273', '217', '1395', '冰', '0.00', '1', '4', '2020-10-13 15:45:22', '2020-10-13 15:45:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4274', '217', '1396', '不加', '0.00', '1', '1', '2020-10-13 15:45:52', '2020-10-13 15:45:52');
INSERT INTO `tb_goods_specification_option` VALUES ('4275', '217', '1396', '芋圆', '2.00', '1', '2', '2020-10-13 15:46:16', '2020-10-13 15:46:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4276', '217', '1396', '红豆', '1.00', '1', '3', '2020-10-13 15:46:20', '2020-10-13 15:46:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4277', '217', '1396', '椰果', '1.00', '1', '4', '2020-10-13 15:46:27', '2020-10-13 15:46:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4278', '217', '1396', '珍珠', '1.00', '1', '5', '2020-10-13 15:46:37', '2020-10-13 15:46:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4279', '217', '1396', '布丁', '2.00', '1', '6', '2020-10-13 15:46:46', '2020-10-13 15:46:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4280', '217', '1396', '仙草冻', '2.00', '1', '7', '2020-10-13 15:46:54', '2020-10-13 15:46:54');
INSERT INTO `tb_goods_specification_option` VALUES ('4281', '217', '1396', '花生碎', '2.00', '1', '8', '2020-10-13 15:47:01', '2020-10-13 15:47:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4282', '217', '1396', '葡萄干', '2.00', '1', '9', '2020-10-13 15:47:09', '2020-10-13 15:47:09');
INSERT INTO `tb_goods_specification_option` VALUES ('4297', '218', '1401', '700ML', '0.00', '1', '1', '2020-10-13 15:52:10', '2020-10-13 15:52:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4298', '218', '1402', '去冰', '0.00', '1', '1', '2020-10-13 15:52:15', '2020-10-13 15:52:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4299', '218', '1402', '冰', '0.00', '1', '2', '2020-10-13 15:52:27', '2020-10-13 15:52:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4300', '218', '1403', '正常糖', '0.00', '1', '1', '2020-10-13 15:53:10', '2020-10-13 15:53:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4301', '218', '1403', '少糖', '0.00', '1', '2', '2020-10-13 15:53:14', '2020-10-13 15:53:14');
INSERT INTO `tb_goods_specification_option` VALUES ('4302', '218', '1403', '无糖', '0.00', '1', '3', '2020-10-13 15:53:25', '2020-10-13 15:53:25');
INSERT INTO `tb_goods_specification_option` VALUES ('4303', '218', '1403', '多糖', '0.00', '1', '4', '2020-10-13 15:53:29', '2020-10-13 15:53:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4318', '219', '1408', '700ML', '0.00', '1', '1', '2020-10-13 16:02:37', '2020-10-13 16:02:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4319', '219', '1409', '常温', '0.00', '1', '1', '2020-10-13 16:02:45', '2020-10-13 16:02:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4320', '219', '1410', '正常糖', '0.00', '1', '1', '2020-10-13 16:02:50', '2020-10-13 16:02:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4321', '219', '1410', '少糖', '0.00', '1', '2', '2020-10-13 16:02:54', '2020-10-13 16:02:54');
INSERT INTO `tb_goods_specification_option` VALUES ('4322', '219', '1410', '无糖', '0.00', '1', '3', '2020-10-13 16:02:57', '2020-10-13 16:02:57');
INSERT INTO `tb_goods_specification_option` VALUES ('4323', '219', '1410', '多糖', '0.00', '1', '4', '2020-10-13 16:03:01', '2020-10-13 16:03:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4338', '220', '1415', '700ML', '0.00', '1', '1', '2020-10-13 16:05:09', '2020-10-13 16:05:09');
INSERT INTO `tb_goods_specification_option` VALUES ('4339', '220', '1416', '去冰', '0.00', '1', '1', '2020-10-13 16:05:18', '2020-10-13 16:05:18');
INSERT INTO `tb_goods_specification_option` VALUES ('4340', '220', '1416', '冰', '0.00', '1', '2', '2020-10-13 16:05:22', '2020-10-13 16:05:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4341', '220', '1417', '正常糖', '0.00', '1', '1', '2020-10-13 16:05:27', '2020-10-13 16:05:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4342', '220', '1417', '少糖', '0.00', '1', '2', '2020-10-13 16:05:33', '2020-10-13 16:05:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4343', '220', '1417', '无糖', '0.00', '1', '3', '2020-10-13 16:05:37', '2020-10-13 16:05:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4344', '220', '1417', '多糖', '0.00', '1', '4', '2020-10-13 16:05:43', '2020-10-13 16:05:43');
INSERT INTO `tb_goods_specification_option` VALUES ('4359', '221', '1422', '中杯', '0.00', '1', '1', '2020-10-13 16:16:21', '2020-10-13 16:16:21');
INSERT INTO `tb_goods_specification_option` VALUES ('4360', '221', '1422', '大杯', '2.00', '1', '2', '2020-10-13 16:16:28', '2020-10-13 16:16:28');
INSERT INTO `tb_goods_specification_option` VALUES ('4361', '221', '1423', '热', '0.00', '1', '1', '2020-10-13 16:16:34', '2020-10-13 16:16:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4362', '221', '1423', '常温', '0.00', '1', '2', '2020-10-13 16:16:39', '2020-10-13 16:16:39');
INSERT INTO `tb_goods_specification_option` VALUES ('4363', '221', '1423', '去冰', '0.00', '1', '3', '2020-10-13 16:16:44', '2020-10-13 16:16:44');
INSERT INTO `tb_goods_specification_option` VALUES ('4364', '221', '1423', '冰', '0.00', '1', '4', '2020-10-13 16:16:47', '2020-10-13 16:16:47');
INSERT INTO `tb_goods_specification_option` VALUES ('4365', '221', '1424', '不加', '0.00', '1', '1', '2020-10-13 16:17:23', '2020-10-13 16:17:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4366', '221', '1424', '芋圆', '2.00', '1', '2', '2020-10-13 16:17:28', '2020-10-13 16:17:28');
INSERT INTO `tb_goods_specification_option` VALUES ('4367', '221', '1424', '布丁', '2.00', '1', '3', '2020-10-13 16:17:31', '2020-10-13 16:17:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4368', '221', '1424', '仙草冻', '2.00', '1', '4', '2020-10-13 16:17:36', '2020-10-13 16:17:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4369', '221', '1424', '红豆', '1.00', '1', '5', '2020-10-13 16:17:41', '2020-10-13 16:17:41');
INSERT INTO `tb_goods_specification_option` VALUES ('4370', '221', '1424', '椰果', '1.00', '1', '6', '2020-10-13 16:17:44', '2020-10-13 16:17:44');
INSERT INTO `tb_goods_specification_option` VALUES ('4371', '221', '1424', '珍珠', '1.00', '1', '7', '2020-10-13 16:17:50', '2020-10-13 16:17:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4372', '221', '1424', '花生碎', '2.00', '1', '8', '2020-10-13 16:18:08', '2020-10-13 16:18:08');
INSERT INTO `tb_goods_specification_option` VALUES ('4373', '221', '1424', '葡萄干', '2.00', '1', '9', '2020-10-13 16:18:13', '2020-10-13 16:18:13');
INSERT INTO `tb_goods_specification_option` VALUES ('4388', '222', '1429', '700ML', '0.00', '1', '1', '2020-10-13 16:20:37', '2020-10-13 16:20:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4389', '222', '1430', '去冰', '0.00', '1', '1', '2020-10-13 16:20:42', '2020-10-13 16:20:42');
INSERT INTO `tb_goods_specification_option` VALUES ('4390', '222', '1430', '冰', '0.00', '1', '2', '2020-10-13 16:20:45', '2020-10-13 16:20:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4391', '222', '1431', '正常糖', '0.00', '1', '1', '2020-10-13 16:20:54', '2020-10-13 16:20:54');
INSERT INTO `tb_goods_specification_option` VALUES ('4392', '222', '1431', '少糖', '0.00', '1', '2', '2020-10-13 16:21:02', '2020-10-13 16:21:02');
INSERT INTO `tb_goods_specification_option` VALUES ('4393', '222', '1431', '无糖', '0.00', '1', '3', '2020-10-13 16:21:08', '2020-10-13 16:21:08');
INSERT INTO `tb_goods_specification_option` VALUES ('4394', '222', '1431', '多糖', '0.00', '1', '4', '2020-10-13 16:21:14', '2020-10-13 16:21:14');
INSERT INTO `tb_goods_specification_option` VALUES ('4409', '223', '1436', '500ML', '0.00', '1', '1', '2020-10-13 16:23:46', '2020-10-13 16:23:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4410', '223', '1437', '去冰', '0.00', '1', '1', '2020-10-13 16:23:50', '2020-10-13 16:23:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4411', '223', '1437', '冰', '0.00', '1', '2', '2020-10-13 16:23:54', '2020-10-13 16:23:54');
INSERT INTO `tb_goods_specification_option` VALUES ('4412', '223', '1438', '正常糖', '0.00', '1', '1', '2020-10-13 16:23:59', '2020-10-13 16:23:59');
INSERT INTO `tb_goods_specification_option` VALUES ('4414', '101', '1439', '布丁', '0.00', '1', '1', '2020-10-14 13:10:41', '2020-10-14 13:10:41');
INSERT INTO `tb_goods_specification_option` VALUES ('4415', '101', '1439', '红豆', '1.00', '1', '2', '2020-10-14 13:10:56', '2020-10-14 13:10:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4416', '101', '1439', '芋圆', '0.00', '1', '3', '2020-10-14 13:11:44', '2020-10-14 13:11:44');
INSERT INTO `tb_goods_specification_option` VALUES ('4417', '101', '1439', '椰果', '1.00', '1', '4', '2020-10-14 13:11:51', '2020-10-14 13:11:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4418', '101', '1439', '波波', '0.00', '1', '5', '2020-10-14 13:11:59', '2020-10-14 13:11:59');
INSERT INTO `tb_goods_specification_option` VALUES ('4419', '101', '1440', '波波1', '0.00', '1', '1', '2020-10-14 13:12:40', '2020-10-14 13:12:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4420', '101', '1440', '椰果1', '0.00', '1', '2', '2020-10-14 13:12:48', '2020-10-14 13:12:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4421', '101', '1440', '红豆1', '0.00', '1', '3', '2020-10-14 13:12:57', '2020-10-14 13:12:57');
INSERT INTO `tb_goods_specification_option` VALUES ('4422', '101', '1440', '布丁1', '0.00', '1', '4', '2020-10-14 13:13:07', '2020-10-14 13:13:07');
INSERT INTO `tb_goods_specification_option` VALUES ('4423', '101', '1440', '芋圆1', '0.00', '1', '5', '2020-10-14 13:13:19', '2020-10-14 13:13:19');
INSERT INTO `tb_goods_specification_option` VALUES ('4424', '101', '1441', '正常糖', '0.00', '1', '1', '2020-10-14 13:13:26', '2020-10-14 13:13:26');
INSERT INTO `tb_goods_specification_option` VALUES ('4425', '101', '1441', '少糖', '0.00', '1', '2', '2020-10-14 13:13:31', '2020-10-14 13:13:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4426', '101', '1441', '无糖', '0.00', '1', '3', '2020-10-14 13:13:36', '2020-10-14 13:13:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4427', '101', '1441', '多糖', '0.00', '1', '4', '2020-10-14 13:13:40', '2020-10-14 13:13:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4429', '183', '1442', '奶茶', '0.00', '1', '1', '2020-10-14 18:03:18', '2020-10-14 18:03:18');
INSERT INTO `tb_goods_specification_option` VALUES ('4430', '183', '1442', '奶绿', '0.00', '1', '2', '2020-10-14 18:03:30', '2020-10-14 18:03:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4431', '182', '1095', '冰', '0.00', '1', '3', '2020-10-14 18:04:16', '2020-10-14 18:04:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4432', '182', '1095', '去冰', '0.00', '1', '4', '2020-10-14 18:04:26', '2020-10-14 18:04:26');
INSERT INTO `tb_goods_specification_option` VALUES ('4433', '171', '1121', '冰', '0.00', '1', '4', '2020-10-14 18:06:04', '2020-10-14 18:06:04');
INSERT INTO `tb_goods_specification_option` VALUES ('4434', '158', '1154', '热', '0.00', '1', '2', '2020-10-14 18:08:08', '2020-10-14 18:08:08');
INSERT INTO `tb_goods_specification_option` VALUES ('4435', '152', '1166', '热', '0.00', '1', '2', '2020-10-14 18:08:36', '2020-10-14 18:08:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4436', '152', '1166', '常温', '0.00', '1', '3', '2020-10-14 18:08:47', '2020-10-14 18:08:47');
INSERT INTO `tb_goods_specification_option` VALUES ('4437', '152', '1166', '去冰', '0.00', '1', '4', '2020-10-14 18:08:54', '2020-10-14 18:08:54');
INSERT INTO `tb_goods_specification_option` VALUES ('4438', '145', '1187', '中杯', '0.00', '1', '2', '2020-10-14 18:10:37', '2020-10-14 18:10:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4439', '143', '1443', '大杯', '0.00', '1', '1', '2020-10-14 18:11:27', '2020-10-14 18:11:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4440', '143', '1444', '热', '0.00', '1', '1', '2020-10-14 18:11:36', '2020-10-14 18:11:36');
INSERT INTO `tb_goods_specification_option` VALUES ('4441', '143', '1444', '冰', '0.00', '1', '2', '2020-10-14 18:11:42', '2020-10-14 18:11:42');
INSERT INTO `tb_goods_specification_option` VALUES ('4442', '143', '1444', '去冰', '0.00', '1', '3', '2020-10-14 18:11:49', '2020-10-14 18:11:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4443', '143', '1444', '常温', '0.00', '1', '4', '2020-10-14 18:11:56', '2020-10-14 18:11:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4445', '142', '1446', '中杯', '0.00', '1', '1', '2020-10-14 18:13:04', '2020-10-14 18:13:04');
INSERT INTO `tb_goods_specification_option` VALUES ('4446', '142', '1447', '常温', '0.00', '1', '1', '2020-10-14 18:13:24', '2020-10-14 18:13:24');
INSERT INTO `tb_goods_specification_option` VALUES ('4447', '142', '1447', '冰', '0.00', '1', '2', '2020-10-14 18:13:30', '2020-10-14 18:13:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4448', '142', '1447', '去冰', '0.00', '1', '3', '2020-10-14 18:13:35', '2020-10-14 18:13:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4449', '142', '1447', '热', '0.00', '1', '4', '2020-10-14 18:13:40', '2020-10-14 18:13:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4450', '141', '1448', '中杯', '0.00', '1', '1', '2020-10-14 18:14:01', '2020-10-14 18:14:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4451', '141', '1449', '常温', '0.00', '1', '1', '2020-10-14 18:14:11', '2020-10-14 18:14:11');
INSERT INTO `tb_goods_specification_option` VALUES ('4452', '141', '1449', '冰', '0.00', '1', '2', '2020-10-14 18:14:17', '2020-10-14 18:14:17');
INSERT INTO `tb_goods_specification_option` VALUES ('4453', '141', '1449', '去冰', '0.00', '1', '3', '2020-10-14 18:14:22', '2020-10-14 18:14:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4454', '141', '1449', '热', '0.00', '1', '4', '2020-10-14 18:14:29', '2020-10-14 18:14:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4455', '140', '1450', '大杯', '0.00', '1', '1', '2020-10-14 18:14:57', '2020-10-14 18:14:57');
INSERT INTO `tb_goods_specification_option` VALUES ('4456', '140', '1451', '常温', '0.00', '1', '1', '2020-10-14 18:15:05', '2020-10-14 18:15:05');
INSERT INTO `tb_goods_specification_option` VALUES ('4457', '140', '1451', '冰', '0.00', '1', '2', '2020-10-14 18:15:10', '2020-10-14 18:15:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4458', '140', '1451', '去冰', '0.00', '1', '3', '2020-10-14 18:15:16', '2020-10-14 18:15:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4459', '140', '1451', '热', '0.00', '1', '4', '2020-10-14 18:15:23', '2020-10-14 18:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4460', '139', '1452', '中杯', '0.00', '1', '1', '2020-10-14 18:15:38', '2020-10-14 18:15:38');
INSERT INTO `tb_goods_specification_option` VALUES ('4461', '139', '1453', '常温', '0.00', '1', '1', '2020-10-14 18:15:46', '2020-10-14 18:15:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4462', '139', '1453', '冰', '0.00', '1', '2', '2020-10-14 18:15:56', '2020-10-14 18:15:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4463', '139', '1453', '去冰', '0.00', '1', '3', '2020-10-14 18:16:02', '2020-10-14 18:16:02');
INSERT INTO `tb_goods_specification_option` VALUES ('4464', '139', '1453', '热', '0.00', '1', '4', '2020-10-14 18:16:07', '2020-10-14 18:16:07');
INSERT INTO `tb_goods_specification_option` VALUES ('4465', '138', '1454', '中杯', '0.00', '1', '1', '2020-10-14 18:16:22', '2020-10-14 18:16:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4466', '138', '1455', '常温', '0.00', '1', '1', '2020-10-14 18:17:40', '2020-10-14 18:17:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4467', '138', '1455', '冰', '0.00', '1', '2', '2020-10-14 18:17:45', '2020-10-14 18:17:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4468', '138', '1455', '去冰', '0.00', '1', '3', '2020-10-14 18:17:51', '2020-10-14 18:17:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4469', '138', '1455', '热', '0.00', '1', '4', '2020-10-14 18:17:56', '2020-10-14 18:17:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4470', '137', '1456', '中杯', '0.00', '1', '1', '2020-10-14 18:18:25', '2020-10-14 18:18:25');
INSERT INTO `tb_goods_specification_option` VALUES ('4471', '137', '1457', '常温', '0.00', '1', '1', '2020-10-14 18:18:33', '2020-10-14 18:18:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4472', '137', '1457', '冰', '0.00', '1', '2', '2020-10-14 18:18:40', '2020-10-14 18:18:40');
INSERT INTO `tb_goods_specification_option` VALUES ('4473', '137', '1457', '去冰', '0.00', '1', '3', '2020-10-14 18:18:45', '2020-10-14 18:18:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4474', '137', '1457', '热', '0.00', '1', '4', '2020-10-14 18:18:51', '2020-10-14 18:18:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4489', '224', '1462', '500ML', '0.00', '1', '1', '2020-10-15 23:36:16', '2020-10-15 23:36:16');
INSERT INTO `tb_goods_specification_option` VALUES ('4490', '224', '1463', '热', '0.00', '1', '1', '2020-10-15 23:36:28', '2020-10-15 23:36:28');
INSERT INTO `tb_goods_specification_option` VALUES ('4491', '224', '1463', '常温', '0.00', '1', '2', '2020-10-15 23:36:32', '2020-10-15 23:36:32');
INSERT INTO `tb_goods_specification_option` VALUES ('4492', '224', '1463', '去冰', '0.00', '1', '3', '2020-10-15 23:36:37', '2020-10-15 23:36:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4493', '224', '1463', '冰', '0.00', '1', '4', '2020-10-15 23:36:43', '2020-10-15 23:36:43');
INSERT INTO `tb_goods_specification_option` VALUES ('4508', '225', '1468', '500ML', '0.00', '1', '1', '2020-10-15 23:41:55', '2020-10-15 23:41:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4509', '225', '1469', '热', '0.00', '1', '1', '2020-10-15 23:41:59', '2020-10-15 23:41:59');
INSERT INTO `tb_goods_specification_option` VALUES ('4510', '225', '1469', '常温', '0.00', '1', '2', '2020-10-15 23:42:03', '2020-10-15 23:42:03');
INSERT INTO `tb_goods_specification_option` VALUES ('4511', '225', '1469', '去冰', '0.00', '1', '3', '2020-10-15 23:42:08', '2020-10-15 23:42:08');
INSERT INTO `tb_goods_specification_option` VALUES ('4512', '225', '1469', '冰', '0.00', '1', '4', '2020-10-15 23:42:12', '2020-10-15 23:42:12');
INSERT INTO `tb_goods_specification_option` VALUES ('4513', '225', '1470', '正常糖', '0.00', '1', '1', '2020-10-15 23:42:29', '2020-10-15 23:42:29');
INSERT INTO `tb_goods_specification_option` VALUES ('4514', '225', '1470', '少糖', '0.00', '1', '2', '2020-10-15 23:42:33', '2020-10-15 23:42:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4515', '225', '1470', '无糖', '0.00', '1', '3', '2020-10-15 23:42:37', '2020-10-15 23:42:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4516', '225', '1470', '多糖', '0.00', '1', '4', '2020-10-15 23:42:41', '2020-10-15 23:42:41');
INSERT INTO `tb_goods_specification_option` VALUES ('4517', '224', '1471', '正常糖', '0.00', '1', '1', '2020-10-15 23:42:50', '2020-10-15 23:42:50');
INSERT INTO `tb_goods_specification_option` VALUES ('4518', '224', '1471', '少糖', '0.00', '1', '2', '2020-10-15 23:42:53', '2020-10-15 23:42:53');
INSERT INTO `tb_goods_specification_option` VALUES ('4519', '224', '1471', '无糖', '0.00', '1', '3', '2020-10-15 23:42:57', '2020-10-15 23:42:57');
INSERT INTO `tb_goods_specification_option` VALUES ('4520', '224', '1471', '多糖', '0.00', '1', '4', '2020-10-15 23:43:01', '2020-10-15 23:43:01');
INSERT INTO `tb_goods_specification_option` VALUES ('4535', '226', '1476', '500ML', '0.00', '1', '1', '2020-10-16 00:23:34', '2020-10-16 00:23:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4536', '226', '1477', '冰', '0.00', '1', '1', '2020-10-16 00:23:46', '2020-10-16 00:23:46');
INSERT INTO `tb_goods_specification_option` VALUES ('4537', '226', '1478', '正常糖', '0.00', '1', '1', '2020-10-16 00:23:56', '2020-10-16 00:23:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4538', '226', '1478', '少糖', '0.00', '1', '2', '2020-10-16 00:24:00', '2020-10-16 00:24:00');
INSERT INTO `tb_goods_specification_option` VALUES ('4539', '226', '1478', '无糖', '0.00', '1', '3', '2020-10-16 00:24:04', '2020-10-16 00:24:04');
INSERT INTO `tb_goods_specification_option` VALUES ('4540', '226', '1478', '多糖', '0.00', '1', '4', '2020-10-16 00:24:07', '2020-10-16 00:24:07');
INSERT INTO `tb_goods_specification_option` VALUES ('4583', '230', '1491', '常规', '0.00', '1', '1', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4584', '230', '1492', '热', '0.00', '1', '1', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4585', '230', '1492', '温', '0.00', '1', '2', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4586', '230', '1492', '常规冰', '0.00', '1', '3', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4587', '230', '1492', '多冰', '0.00', '1', '4', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4588', '230', '1492', '少冰', '0.00', '1', '5', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4589', '230', '1492', '去冰', '0.00', '1', '6', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4590', '230', '1493', '常规糖', '0.00', '1', '1', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4591', '230', '1493', '半糖', '0.00', '1', '2', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4592', '230', '1493', '微糖', '0.00', '1', '3', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4593', '230', '1493', '不另外加糖', '0.00', '1', '4', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4594', '230', '1494', '小杯', '0.00', '1', '1', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4595', '230', '1494', '中杯', '2.00', '1', '2', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4596', '230', '1494', '大杯', '4.00', '1', '3', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_goods_specification_option` VALUES ('4667', '85', '1257', '正常糖', '0.00', '1', '5', '2020-10-26 20:08:37', '2020-10-26 20:08:37');
INSERT INTO `tb_goods_specification_option` VALUES ('4682', '237', '1499', '常规', '0.00', '1', '1', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4683', '237', '1500', '热', '0.00', '1', '1', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4684', '237', '1500', '温', '0.00', '1', '2', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4685', '237', '1500', '常规冰', '0.00', '1', '3', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4686', '237', '1500', '多冰', '0.00', '1', '4', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4687', '237', '1500', '少冰', '0.00', '1', '5', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4688', '237', '1500', '去冰', '0.00', '1', '6', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4689', '237', '1501', '常规糖', '0.00', '1', '1', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4690', '237', '1501', '半糖', '0.00', '1', '2', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4691', '237', '1501', '微糖', '0.00', '1', '3', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4692', '237', '1501', '不另外加糖', '0.00', '1', '4', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4694', '237', '1502', '中杯', '0.00', '1', '2', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4695', '237', '1502', '大杯', '2.00', '1', '3', '2021-03-15 14:33:34', '2021-03-15 14:33:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4710', '239', '1507', '常规', '0.00', '1', '1', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4711', '239', '1508', '热', '0.00', '1', '1', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4712', '239', '1508', '温', '0.00', '1', '2', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4713', '239', '1508', '常规冰', '0.00', '1', '3', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4714', '239', '1508', '多冰', '0.00', '1', '4', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4715', '239', '1508', '少冰', '0.00', '1', '5', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4716', '239', '1508', '去冰', '0.00', '1', '6', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4717', '239', '1509', '常规糖', '0.00', '1', '1', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4718', '239', '1509', '半糖', '0.00', '1', '2', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4719', '239', '1509', '微糖', '0.00', '1', '3', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4720', '239', '1509', '不另外加糖', '0.00', '1', '4', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4722', '239', '1510', '中杯', '2.00', '1', '2', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4723', '239', '1510', '大杯', '2.00', '1', '3', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_goods_specification_option` VALUES ('4724', '240', '1511', '常规', '0.00', '1', '1', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4725', '240', '1512', '热', '0.00', '1', '1', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4726', '240', '1512', '温', '0.00', '1', '2', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4727', '240', '1512', '常规冰', '0.00', '1', '3', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4728', '240', '1512', '多冰', '0.00', '1', '4', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4729', '240', '1512', '少冰', '0.00', '1', '5', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4730', '240', '1512', '去冰', '0.00', '1', '6', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4731', '240', '1513', '常规糖', '0.00', '1', '1', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4732', '240', '1513', '半糖', '0.00', '1', '2', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4733', '240', '1513', '微糖', '0.00', '1', '3', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4734', '240', '1513', '不另外加糖', '0.00', '1', '4', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4736', '240', '1514', '中杯', '0.00', '1', '2', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4737', '240', '1514', '大杯', '2.00', '1', '3', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_goods_specification_option` VALUES ('4738', '241', '1515', '常规', '0.00', '1', '1', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4739', '241', '1516', '热', '0.00', '1', '1', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4740', '241', '1516', '温', '0.00', '1', '2', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4741', '241', '1516', '常规冰', '0.00', '1', '3', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4742', '241', '1516', '多冰', '0.00', '1', '4', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4743', '241', '1516', '少冰', '0.00', '1', '5', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4744', '241', '1516', '去冰', '0.00', '1', '6', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4745', '241', '1517', '常规糖', '0.00', '1', '1', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4746', '241', '1517', '半糖', '0.00', '1', '2', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4747', '241', '1517', '微糖', '0.00', '1', '3', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4748', '241', '1517', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4750', '241', '1518', '中杯', '0.00', '1', '2', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4751', '241', '1518', '大杯', '2.00', '1', '3', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4752', '242', '1519', '常规', '0.00', '1', '1', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4753', '242', '1520', '热', '0.00', '1', '1', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4754', '242', '1520', '温', '0.00', '1', '2', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4755', '242', '1520', '常规冰', '0.00', '1', '3', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4756', '242', '1520', '多冰', '0.00', '1', '4', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4757', '242', '1520', '少冰', '0.00', '1', '5', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4758', '242', '1520', '去冰', '0.00', '1', '6', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4759', '242', '1521', '常规糖', '0.00', '1', '1', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4760', '242', '1521', '半糖', '0.00', '1', '2', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4761', '242', '1521', '微糖', '0.00', '1', '3', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4762', '242', '1521', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4763', '242', '1522', '小杯', '0.00', '1', '1', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4764', '242', '1522', '中杯', '2.00', '1', '2', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4765', '242', '1522', '大杯', '4.00', '1', '3', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4766', '243', '1523', '常规', '0.00', '1', '1', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4767', '243', '1524', '热', '0.00', '1', '1', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4768', '243', '1524', '温', '0.00', '1', '2', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4769', '243', '1524', '常规冰', '0.00', '1', '3', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4770', '243', '1524', '多冰', '0.00', '1', '4', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4771', '243', '1524', '少冰', '0.00', '1', '5', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4772', '243', '1524', '去冰', '0.00', '1', '6', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4773', '243', '1525', '常规糖', '0.00', '1', '1', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4774', '243', '1525', '半糖', '0.00', '1', '2', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4775', '243', '1525', '微糖', '0.00', '1', '3', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4776', '243', '1525', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4777', '243', '1526', '小杯', '0.00', '1', '1', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4778', '243', '1526', '中杯', '2.00', '1', '2', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4779', '243', '1526', '大杯', '4.00', '1', '3', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4780', '244', '1527', '常规', '0.00', '1', '1', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4781', '244', '1528', '热', '0.00', '1', '1', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4782', '244', '1528', '温', '0.00', '1', '2', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4783', '244', '1528', '常规冰', '0.00', '1', '3', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4784', '244', '1528', '多冰', '0.00', '1', '4', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4785', '244', '1528', '少冰', '0.00', '1', '5', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4786', '244', '1528', '去冰', '0.00', '1', '6', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4787', '244', '1529', '常规糖', '0.00', '1', '1', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4788', '244', '1529', '半糖', '0.00', '1', '2', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4789', '244', '1529', '微糖', '0.00', '1', '3', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4790', '244', '1529', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4791', '244', '1530', '小杯', '0.00', '1', '1', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4792', '244', '1530', '中杯', '2.00', '1', '2', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4793', '244', '1530', '大杯', '4.00', '1', '3', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4794', '245', '1531', '常规', '0.00', '1', '1', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4795', '245', '1532', '热', '0.00', '1', '1', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4796', '245', '1532', '温', '0.00', '1', '2', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4797', '245', '1532', '常规冰', '0.00', '1', '3', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4798', '245', '1532', '多冰', '0.00', '1', '4', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4799', '245', '1532', '少冰', '0.00', '1', '5', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4800', '245', '1532', '去冰', '0.00', '1', '6', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4801', '245', '1533', '常规糖', '0.00', '1', '1', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4802', '245', '1533', '半糖', '0.00', '1', '2', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_goods_specification_option` VALUES ('4803', '245', '1533', '微糖', '0.00', '1', '3', '2021-04-25 19:36:31', '2021-04-25 19:36:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4804', '245', '1533', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:36:31', '2021-04-25 19:36:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4805', '245', '1534', '小杯', '0.00', '1', '1', '2021-04-25 19:36:31', '2021-04-25 19:36:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4806', '245', '1534', '中杯', '2.00', '1', '2', '2021-04-25 19:36:31', '2021-04-25 19:36:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4807', '245', '1534', '大杯', '4.00', '1', '3', '2021-04-25 19:36:31', '2021-04-25 19:36:31');
INSERT INTO `tb_goods_specification_option` VALUES ('4808', '246', '1535', '常规', '0.00', '1', '1', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4809', '246', '1536', '热', '0.00', '1', '1', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4810', '246', '1536', '温', '0.00', '1', '2', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4811', '246', '1536', '常规冰', '0.00', '1', '3', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4812', '246', '1536', '多冰', '0.00', '1', '4', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4813', '246', '1536', '少冰', '0.00', '1', '5', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4814', '246', '1536', '去冰', '0.00', '1', '6', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4815', '246', '1537', '常规糖', '0.00', '1', '1', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4816', '246', '1537', '半糖', '0.00', '1', '2', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4817', '246', '1537', '微糖', '0.00', '1', '3', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_goods_specification_option` VALUES ('4818', '246', '1537', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:40:49', '2021-04-25 19:40:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4819', '246', '1538', '小杯', '0.00', '1', '1', '2021-04-25 19:40:49', '2021-04-25 19:40:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4820', '246', '1538', '中杯', '2.00', '1', '2', '2021-04-25 19:40:49', '2021-04-25 19:40:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4821', '246', '1538', '大杯', '4.00', '1', '3', '2021-04-25 19:40:49', '2021-04-25 19:40:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4822', '247', '1539', '常规', '0.00', '1', '1', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4823', '247', '1540', '热', '0.00', '1', '1', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4824', '247', '1540', '温', '0.00', '1', '2', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4825', '247', '1540', '常规冰', '0.00', '1', '3', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4826', '247', '1540', '多冰', '0.00', '1', '4', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4827', '247', '1540', '少冰', '0.00', '1', '5', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4828', '247', '1540', '去冰', '0.00', '1', '6', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4829', '247', '1541', '常规糖', '0.00', '1', '1', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4830', '247', '1541', '半糖', '0.00', '1', '2', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4831', '247', '1541', '微糖', '0.00', '1', '3', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4832', '247', '1541', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4833', '247', '1542', '小杯', '0.00', '1', '1', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4834', '247', '1542', '中杯', '2.00', '1', '2', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4835', '247', '1542', '大杯', '4.00', '1', '3', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_goods_specification_option` VALUES ('4836', '248', '1543', '常规', '0.00', '1', '1', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4837', '248', '1544', '热', '0.00', '1', '1', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4838', '248', '1544', '温', '0.00', '1', '2', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4839', '248', '1544', '常规冰', '0.00', '1', '3', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4840', '248', '1544', '多冰', '0.00', '1', '4', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4841', '248', '1544', '少冰', '0.00', '1', '5', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4842', '248', '1544', '去冰', '0.00', '1', '6', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4843', '248', '1545', '常规糖', '0.00', '1', '1', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4844', '248', '1545', '半糖', '0.00', '1', '2', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4845', '248', '1545', '微糖', '0.00', '1', '3', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4846', '248', '1545', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4847', '248', '1546', '小杯', '0.00', '1', '1', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4848', '248', '1546', '中杯', '2.00', '1', '2', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4849', '248', '1546', '大杯', '4.00', '1', '3', '2021-04-25 19:57:55', '2021-04-25 19:57:55');
INSERT INTO `tb_goods_specification_option` VALUES ('4850', '249', '1547', '常规', '0.00', '1', '1', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4851', '249', '1548', '热', '0.00', '1', '1', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4852', '249', '1548', '温', '0.00', '1', '2', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4853', '249', '1548', '常规冰', '0.00', '1', '3', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4854', '249', '1548', '多冰', '0.00', '1', '4', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4855', '249', '1548', '少冰', '0.00', '1', '5', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4856', '249', '1548', '去冰', '0.00', '1', '6', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4857', '249', '1549', '常规糖', '0.00', '1', '1', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4858', '249', '1549', '半糖', '0.00', '1', '2', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4859', '249', '1549', '微糖', '0.00', '1', '3', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4860', '249', '1549', '不另外加糖', '0.00', '1', '4', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4861', '249', '1550', '小杯', '0.00', '1', '1', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4862', '249', '1550', '中杯', '2.00', '1', '2', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4863', '249', '1550', '大杯', '4.00', '1', '3', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4864', '250', '1551', '常规', '0.00', '1', '1', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4865', '250', '1552', '热', '0.00', '1', '1', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4866', '250', '1552', '温', '0.00', '1', '2', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4867', '250', '1552', '常规冰', '0.00', '1', '3', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4868', '250', '1552', '多冰', '0.00', '1', '4', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4869', '250', '1552', '少冰', '0.00', '1', '5', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4870', '250', '1552', '去冰', '0.00', '1', '6', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4871', '250', '1553', '常规糖', '0.00', '1', '1', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4872', '250', '1553', '半糖', '0.00', '1', '2', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4873', '250', '1553', '微糖', '0.00', '1', '3', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4874', '250', '1553', '不另外加糖', '0.00', '1', '4', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4875', '250', '1554', '小杯', '0.00', '1', '1', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4876', '250', '1554', '中杯', '2.00', '1', '2', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4877', '250', '1554', '大杯', '4.00', '1', '3', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_goods_specification_option` VALUES ('4878', '251', '1555', '常规', '0.00', '1', '1', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4879', '251', '1556', '热', '0.00', '1', '1', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4880', '251', '1556', '温', '0.00', '1', '2', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4881', '251', '1556', '常规冰', '0.00', '1', '3', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4882', '251', '1556', '多冰', '0.00', '1', '4', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4883', '251', '1556', '少冰', '0.00', '1', '5', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4884', '251', '1556', '去冰', '0.00', '1', '6', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4885', '251', '1557', '常规糖', '0.00', '1', '1', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4886', '251', '1557', '半糖', '0.00', '1', '2', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4887', '251', '1557', '微糖', '0.00', '1', '3', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4888', '251', '1557', '不另外加糖', '0.00', '1', '4', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4889', '251', '1558', '小杯', '0.00', '1', '1', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4890', '251', '1558', '中杯', '2.00', '1', '2', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4891', '251', '1558', '大杯', '4.00', '1', '3', '2021-04-25 20:20:23', '2021-04-25 20:20:23');
INSERT INTO `tb_goods_specification_option` VALUES ('4892', '252', '1559', '常规', '0.00', '1', '1', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4893', '252', '1560', '热', '0.00', '1', '1', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4894', '252', '1560', '温', '0.00', '1', '2', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4895', '252', '1560', '常规冰', '0.00', '1', '3', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4896', '252', '1560', '多冰', '0.00', '1', '4', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4897', '252', '1560', '少冰', '0.00', '1', '5', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4898', '252', '1560', '去冰', '0.00', '1', '6', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4899', '252', '1561', '常规糖', '0.00', '1', '1', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4900', '252', '1561', '半糖', '0.00', '1', '2', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4901', '252', '1561', '微糖', '0.00', '1', '3', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4902', '252', '1561', '不另外加糖', '0.00', '1', '4', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4903', '252', '1562', '小杯', '0.00', '1', '1', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4904', '252', '1562', '中杯', '2.00', '1', '2', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4905', '252', '1562', '大杯', '4.00', '1', '3', '2021-04-25 20:35:45', '2021-04-25 20:35:45');
INSERT INTO `tb_goods_specification_option` VALUES ('4906', '253', '1563', '常规', '0.00', '1', '1', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4907', '253', '1564', '热', '0.00', '1', '1', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4908', '253', '1564', '温', '0.00', '1', '2', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4909', '253', '1564', '常规冰', '0.00', '1', '3', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4910', '253', '1564', '多冰', '0.00', '1', '4', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4911', '253', '1564', '少冰', '0.00', '1', '5', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4912', '253', '1564', '去冰', '0.00', '1', '6', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4913', '253', '1565', '常规糖', '0.00', '1', '1', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4914', '253', '1565', '半糖', '0.00', '1', '2', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4915', '253', '1565', '微糖', '0.00', '1', '3', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4916', '253', '1565', '不另外加糖', '0.00', '1', '4', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4917', '253', '1566', '小杯', '0.00', '1', '1', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4918', '253', '1566', '中杯', '2.00', '1', '2', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4919', '253', '1566', '大杯', '4.00', '1', '3', '2021-04-25 20:38:27', '2021-04-25 20:38:27');
INSERT INTO `tb_goods_specification_option` VALUES ('4920', '254', '1567', '常规', '0.00', '1', '1', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4921', '254', '1568', '热', '0.00', '1', '1', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4922', '254', '1568', '温', '0.00', '1', '2', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4923', '254', '1568', '常规冰', '0.00', '1', '3', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4924', '254', '1568', '多冰', '0.00', '1', '4', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4925', '254', '1568', '少冰', '0.00', '1', '5', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4926', '254', '1568', '去冰', '0.00', '1', '6', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4927', '254', '1569', '常规糖', '0.00', '1', '1', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4928', '254', '1569', '半糖', '0.00', '1', '2', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4929', '254', '1569', '微糖', '0.00', '1', '3', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4930', '254', '1569', '不另外加糖', '0.00', '1', '4', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4931', '254', '1570', '小杯', '0.00', '1', '1', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4932', '254', '1570', '中杯', '2.00', '1', '2', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4933', '254', '1570', '大杯', '4.00', '1', '3', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_goods_specification_option` VALUES ('4934', '255', '1571', '常规', '0.00', '1', '1', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4935', '255', '1572', '热', '0.00', '1', '1', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4936', '255', '1572', '温', '0.00', '1', '2', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4937', '255', '1572', '常规冰', '0.00', '1', '3', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4938', '255', '1572', '多冰', '0.00', '1', '4', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4939', '255', '1572', '少冰', '0.00', '1', '5', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4940', '255', '1572', '去冰', '0.00', '1', '6', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4941', '255', '1573', '常规糖', '0.00', '1', '1', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4942', '255', '1573', '半糖', '0.00', '1', '2', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4943', '255', '1573', '微糖', '0.00', '1', '3', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4944', '255', '1573', '不另外加糖', '0.00', '1', '4', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4945', '255', '1574', '小杯', '0.00', '1', '1', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4946', '255', '1574', '中杯', '2.00', '1', '2', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4947', '255', '1574', '大杯', '4.00', '1', '3', '2021-04-25 20:58:56', '2021-04-25 20:58:56');
INSERT INTO `tb_goods_specification_option` VALUES ('4948', '256', '1575', '常规', '0.00', '1', '1', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4949', '256', '1576', '热', '0.00', '1', '1', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4950', '256', '1576', '温', '0.00', '1', '2', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4951', '256', '1576', '常规冰', '0.00', '1', '3', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4952', '256', '1576', '多冰', '0.00', '1', '4', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4953', '256', '1576', '少冰', '0.00', '1', '5', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4954', '256', '1576', '去冰', '0.00', '1', '6', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4955', '256', '1577', '常规糖', '0.00', '1', '1', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4956', '256', '1577', '半糖', '0.00', '1', '2', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4957', '256', '1577', '微糖', '0.00', '1', '3', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4958', '256', '1577', '不另外加糖', '0.00', '1', '4', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4959', '256', '1578', '小杯', '0.00', '1', '1', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4960', '256', '1578', '中杯', '2.00', '1', '2', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4961', '256', '1578', '大杯', '4.00', '1', '3', '2021-04-25 21:47:35', '2021-04-25 21:47:35');
INSERT INTO `tb_goods_specification_option` VALUES ('4962', '257', '1579', '常规', '0.00', '1', '1', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4963', '257', '1580', '热', '0.00', '1', '1', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4964', '257', '1580', '温', '0.00', '1', '2', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4965', '257', '1580', '常规冰', '0.00', '1', '3', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4966', '257', '1580', '多冰', '0.00', '1', '4', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4967', '257', '1580', '少冰', '0.00', '1', '5', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4968', '257', '1580', '去冰', '0.00', '1', '6', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4969', '257', '1581', '常规糖', '0.00', '1', '1', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4970', '257', '1581', '半糖', '0.00', '1', '2', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4971', '257', '1581', '微糖', '0.00', '1', '3', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4972', '257', '1581', '不另外加糖', '0.00', '1', '4', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4973', '257', '1582', '小杯', '0.00', '1', '1', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4974', '257', '1582', '中杯', '2.00', '1', '2', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4975', '257', '1582', '大杯', '4.00', '1', '3', '2021-04-25 21:59:20', '2021-04-25 21:59:20');
INSERT INTO `tb_goods_specification_option` VALUES ('4976', '258', '1583', '常规', '0.00', '1', '1', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4977', '258', '1584', '热', '0.00', '1', '1', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4978', '258', '1584', '温', '0.00', '1', '2', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4979', '258', '1584', '常规冰', '0.00', '1', '3', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4980', '258', '1584', '多冰', '0.00', '1', '4', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4981', '258', '1584', '少冰', '0.00', '1', '5', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4982', '258', '1584', '去冰', '0.00', '1', '6', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4983', '258', '1585', '常规糖', '0.00', '1', '1', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4984', '258', '1585', '半糖', '0.00', '1', '2', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4985', '258', '1585', '微糖', '0.00', '1', '3', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4986', '258', '1585', '不另外加糖', '0.00', '1', '4', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4987', '258', '1586', '小杯', '0.00', '1', '1', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4988', '258', '1586', '中杯', '2.00', '1', '2', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4989', '258', '1586', '大杯', '4.00', '1', '3', '2021-04-25 22:01:06', '2021-04-25 22:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('4990', '259', '1587', '常规', '0.00', '1', '1', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4991', '259', '1588', '热', '0.00', '1', '1', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4992', '259', '1588', '温', '0.00', '1', '2', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4993', '259', '1588', '常规冰', '0.00', '1', '3', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4994', '259', '1588', '多冰', '0.00', '1', '4', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4995', '259', '1588', '少冰', '0.00', '1', '5', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4996', '259', '1588', '去冰', '0.00', '1', '6', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4997', '259', '1589', '常规糖', '0.00', '1', '1', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4998', '259', '1589', '半糖', '0.00', '1', '2', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('4999', '259', '1589', '微糖', '0.00', '1', '3', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('5000', '259', '1589', '不另外加糖', '0.00', '1', '4', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('5001', '259', '1590', '小杯', '0.00', '1', '1', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('5002', '259', '1590', '中杯', '2.00', '1', '2', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('5003', '259', '1590', '大杯', '4.00', '1', '3', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_goods_specification_option` VALUES ('5004', '260', '1591', '常规', '0.00', '1', '1', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5005', '260', '1592', '热', '0.00', '1', '1', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5006', '260', '1592', '温', '0.00', '1', '2', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5007', '260', '1592', '常规冰', '0.00', '1', '3', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5008', '260', '1592', '多冰', '0.00', '1', '4', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5009', '260', '1592', '少冰', '0.00', '1', '5', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5010', '260', '1592', '去冰', '0.00', '1', '6', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5011', '260', '1593', '常规糖', '0.00', '1', '1', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5012', '260', '1593', '半糖', '0.00', '1', '2', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5013', '260', '1593', '微糖', '0.00', '1', '3', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5014', '260', '1593', '不另外加糖', '0.00', '1', '4', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5015', '260', '1594', '小杯', '0.00', '1', '1', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5016', '260', '1594', '中杯', '2.00', '1', '2', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5017', '260', '1594', '大杯', '4.00', '1', '3', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5018', '261', '1595', '常规', '0.00', '1', '1', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5019', '261', '1596', '热', '0.00', '1', '1', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5020', '261', '1596', '温', '0.00', '1', '2', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5021', '261', '1596', '常规冰', '0.00', '1', '3', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5022', '261', '1596', '多冰', '0.00', '1', '4', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5023', '261', '1596', '少冰', '0.00', '1', '5', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5024', '261', '1596', '去冰', '0.00', '1', '6', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5025', '261', '1597', '常规糖', '0.00', '1', '1', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5026', '261', '1597', '半糖', '0.00', '1', '2', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5027', '261', '1597', '微糖', '0.00', '1', '3', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5028', '261', '1597', '不另外加糖', '0.00', '1', '4', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5029', '261', '1598', '中杯', '0.00', '1', '1', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5030', '261', '1598', '大杯', '2.00', '1', '2', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5034', '262', '1600', '常规冰', '0.00', '1', '3', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5038', '262', '1601', '常规糖', '0.00', '1', '1', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5039', '262', '1601', '半糖', '0.00', '1', '2', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_goods_specification_option` VALUES ('5043', '262', '1602', '大杯', '0.00', '1', '2', '2021-06-27 11:30:51', '2021-06-27 11:30:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5046', '263', '1604', '温', '0.00', '1', '2', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5047', '263', '1604', '常规冰', '0.00', '1', '3', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5048', '263', '1604', '多冰', '0.00', '1', '4', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5049', '263', '1604', '少冰', '0.00', '1', '5', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5050', '263', '1604', '去冰', '0.00', '1', '6', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5051', '263', '1605', '常规糖', '0.00', '1', '1', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5052', '263', '1605', '半糖', '0.00', '1', '2', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5055', '263', '1606', '中杯', '0.00', '1', '1', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5056', '263', '1606', '大杯', '2.00', '1', '2', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5059', '264', '1608', '温', '0.00', '1', '2', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5060', '264', '1608', '常规冰', '0.00', '1', '3', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5061', '264', '1608', '多冰', '0.00', '1', '4', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5062', '264', '1608', '少冰', '0.00', '1', '5', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5063', '264', '1608', '去冰', '0.00', '1', '6', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5064', '264', '1609', '常规糖', '0.00', '1', '1', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5065', '264', '1609', '半糖', '0.00', '1', '2', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5068', '264', '1610', '中杯', '0.00', '1', '1', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5069', '264', '1610', '大杯', '2.00', '1', '2', '2021-06-28 22:07:11', '2021-06-28 22:07:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5071', '265', '1612', '热', '0.00', '1', '1', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5072', '265', '1612', '温', '0.00', '1', '2', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5073', '265', '1612', '常规冰', '0.00', '1', '3', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5074', '265', '1612', '多冰', '0.00', '1', '4', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5075', '265', '1612', '少冰', '0.00', '1', '5', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5076', '265', '1612', '去冰', '0.00', '1', '6', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5077', '265', '1613', '常规糖', '0.00', '1', '1', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5078', '265', '1613', '半糖', '0.00', '1', '2', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5081', '265', '1614', '中杯', '0.00', '1', '1', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5082', '265', '1614', '大杯', '2.00', '1', '2', '2021-06-28 22:10:46', '2021-06-28 22:10:46');
INSERT INTO `tb_goods_specification_option` VALUES ('5087', '266', '1616', '多冰', '0.00', '1', '4', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5088', '266', '1616', '少冰', '0.00', '1', '5', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5089', '266', '1616', '去冰', '0.00', '1', '6', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5090', '266', '1617', '常规糖', '0.00', '1', '1', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5091', '266', '1617', '半糖', '0.00', '1', '2', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5095', '266', '1618', '大杯', '0.00', '1', '2', '2021-06-28 22:15:23', '2021-06-28 22:15:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5100', '267', '1620', '多冰', '0.00', '1', '4', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5101', '267', '1620', '少冰', '0.00', '1', '5', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5102', '267', '1620', '去冰', '0.00', '1', '6', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5103', '267', '1621', '常规糖', '0.00', '1', '1', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5108', '267', '1622', '大杯', '0.00', '1', '2', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5112', '268', '1624', '常规冰', '0.00', '1', '3', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5113', '268', '1624', '多冰', '0.00', '1', '4', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5114', '268', '1624', '少冰', '0.00', '1', '5', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5115', '268', '1624', '去冰', '0.00', '1', '6', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5116', '268', '1625', '常规糖', '0.00', '1', '1', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5117', '268', '1625', '半糖', '0.00', '1', '2', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5121', '268', '1626', '大杯', '0.00', '1', '2', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_goods_specification_option` VALUES ('5125', '269', '1628', '常规冰', '0.00', '1', '3', '2021-07-01 11:47:43', '2021-07-01 11:47:43');
INSERT INTO `tb_goods_specification_option` VALUES ('5141', '270', '1632', '多冰', '0.00', '1', '6', '2021-07-01 11:55:32', '2021-07-01 11:55:32');
INSERT INTO `tb_goods_specification_option` VALUES ('5147', '270', '1634', '大杯', '0.00', '1', '2', '2021-07-01 11:55:32', '2021-07-01 11:55:32');
INSERT INTO `tb_goods_specification_option` VALUES ('5152', '271', '1636', '常规冰', '0.00', '1', '4', '2021-07-01 13:01:06', '2021-07-01 13:01:06');
INSERT INTO `tb_goods_specification_option` VALUES ('5160', '271', '1638', '大杯', '0.00', '1', '2', '2021-07-01 13:01:07', '2021-07-01 13:01:07');
INSERT INTO `tb_goods_specification_option` VALUES ('5201', '274', '1652', '一份', '0.00', '1', '1', '2021-07-01 18:33:17', '2021-07-01 18:33:17');
INSERT INTO `tb_goods_specification_option` VALUES ('5202', '274', '1653', '原味', '0.00', '1', '1', '2021-07-01 18:33:25', '2021-07-01 18:33:25');
INSERT INTO `tb_goods_specification_option` VALUES ('5203', '274', '1653', '巧克力', '1.00', '1', '2', '2021-07-01 18:33:38', '2021-07-01 18:33:38');
INSERT INTO `tb_goods_specification_option` VALUES ('5204', '274', '1653', '红豆', '1.00', '1', '3', '2021-07-01 18:33:49', '2021-07-01 18:33:49');
INSERT INTO `tb_goods_specification_option` VALUES ('5205', '274', '1653', '冰淇淋', '4.00', '1', '4', '2021-07-01 18:34:02', '2021-07-01 18:34:02');
INSERT INTO `tb_goods_specification_option` VALUES ('5206', '273', '1654', '热', '0.00', '1', '1', '2021-07-01 18:34:38', '2021-07-01 18:34:38');
INSERT INTO `tb_goods_specification_option` VALUES ('5207', '273', '1654', '冰', '0.00', '1', '2', '2021-07-01 18:34:53', '2021-07-01 18:34:53');
INSERT INTO `tb_goods_specification_option` VALUES ('5208', '273', '1655', '正常糖', '0.00', '1', '1', '2021-07-01 18:35:11', '2021-07-01 18:35:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5209', '273', '1655', '无糖', '0.00', '1', '2', '2021-07-01 18:35:22', '2021-07-01 18:35:22');
INSERT INTO `tb_goods_specification_option` VALUES ('5210', '273', '1655', '双份糖', '0.00', '1', '3', '2021-07-01 18:35:42', '2021-07-01 18:35:42');
INSERT INTO `tb_goods_specification_option` VALUES ('5213', '272', '1657', '常温', '0.00', '1', '1', '2021-07-01 18:37:03', '2021-07-01 18:37:03');
INSERT INTO `tb_goods_specification_option` VALUES ('5214', '272', '1657', '少冰', '0.00', '1', '2', '2021-07-01 18:37:13', '2021-07-01 18:37:13');
INSERT INTO `tb_goods_specification_option` VALUES ('5215', '272', '1657', '多冰', '0.00', '1', '3', '2021-07-01 18:37:30', '2021-07-01 18:37:30');
INSERT INTO `tb_goods_specification_option` VALUES ('5216', '272', '1658', '无糖', '0.00', '1', '1', '2021-07-01 18:37:40', '2021-07-01 18:37:40');
INSERT INTO `tb_goods_specification_option` VALUES ('5217', '272', '1658', '正常糖', '0.00', '1', '2', '2021-07-01 18:37:55', '2021-07-01 18:37:55');
INSERT INTO `tb_goods_specification_option` VALUES ('5218', '272', '1658', '少糖', '0.00', '1', '3', '2021-07-01 18:38:05', '2021-07-01 18:38:05');
INSERT INTO `tb_goods_specification_option` VALUES ('5219', '272', '1658', '多糖', '0.00', '1', '4', '2021-07-01 18:38:11', '2021-07-01 18:38:11');
INSERT INTO `tb_goods_specification_option` VALUES ('5220', '271', '1636', '少冰', '0.00', '1', '5', '2021-07-01 20:41:32', '2021-07-01 20:41:32');
INSERT INTO `tb_goods_specification_option` VALUES ('5221', '271', '1636', '多冰', '0.00', '1', '6', '2021-07-01 20:41:59', '2021-07-01 20:41:59');
INSERT INTO `tb_goods_specification_option` VALUES ('5222', '271', '1659', '正常糖', '0.00', '1', '1', '2021-07-01 20:42:12', '2021-07-01 20:42:12');
INSERT INTO `tb_goods_specification_option` VALUES ('5223', '271', '1659', '少糖', '0.00', '1', '2', '2021-07-01 20:42:22', '2021-07-01 20:42:22');
INSERT INTO `tb_goods_specification_option` VALUES ('5224', '271', '1659', '多糖', '0.00', '1', '3', '2021-07-01 20:42:34', '2021-07-01 20:42:34');
INSERT INTO `tb_goods_specification_option` VALUES ('5225', '269', '1628', '少冰', '0.00', '1', '4', '2021-07-01 20:44:54', '2021-07-01 20:44:54');
INSERT INTO `tb_goods_specification_option` VALUES ('5226', '269', '1628', '多冰', '0.00', '1', '5', '2021-07-01 20:45:02', '2021-07-01 20:45:02');
INSERT INTO `tb_goods_specification_option` VALUES ('5227', '269', '1628', '常温', '0.00', '1', '6', '2021-07-01 20:45:10', '2021-07-01 20:45:10');
INSERT INTO `tb_goods_specification_option` VALUES ('5228', '269', '1660', '少糖', '0.00', '1', '1', '2021-07-01 20:45:17', '2021-07-01 20:45:17');
INSERT INTO `tb_goods_specification_option` VALUES ('5229', '269', '1660', '正常糖', '0.00', '1', '2', '2021-07-01 20:45:24', '2021-07-01 20:45:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5230', '269', '1660', '多糖', '0.00', '1', '3', '2021-07-01 20:45:29', '2021-07-01 20:45:29');
INSERT INTO `tb_goods_specification_option` VALUES ('5231', '267', '1620', '常规冰', '0.00', '1', '7', '2021-07-01 20:46:27', '2021-07-01 20:46:27');
INSERT INTO `tb_goods_specification_option` VALUES ('5232', '266', '1616', '常规冰', '0.00', '1', '7', '2021-07-01 20:47:21', '2021-07-01 20:47:21');
INSERT INTO `tb_goods_specification_option` VALUES ('5233', '235', '1661', '红豆', '1.00', '1', '1', '2021-07-01 20:50:03', '2021-07-01 20:50:03');
INSERT INTO `tb_goods_specification_option` VALUES ('5234', '235', '1661', '椰果', '1.00', '1', '2', '2021-07-01 20:50:13', '2021-07-01 20:50:13');
INSERT INTO `tb_goods_specification_option` VALUES ('5235', '235', '1661', '葡萄', '1.00', '1', '3', '2021-07-01 20:50:22', '2021-07-01 20:50:22');
INSERT INTO `tb_goods_specification_option` VALUES ('5236', '235', '1661', '布丁', '2.00', '1', '4', '2021-07-01 20:50:33', '2021-07-01 20:50:33');
INSERT INTO `tb_goods_specification_option` VALUES ('5237', '234', '1662', '热', '0.00', '1', '1', '2021-07-01 20:51:09', '2021-07-01 20:51:09');
INSERT INTO `tb_goods_specification_option` VALUES ('5238', '234', '1662', '常温', '0.00', '1', '2', '2021-07-01 20:51:15', '2021-07-01 20:51:15');
INSERT INTO `tb_goods_specification_option` VALUES ('5239', '234', '1662', '冰', '0.00', '1', '3', '2021-07-01 20:51:19', '2021-07-01 20:51:19');
INSERT INTO `tb_goods_specification_option` VALUES ('5240', '234', '1663', '不加糖', '0.00', '1', '1', '2021-07-01 20:51:26', '2021-07-01 20:51:26');
INSERT INTO `tb_goods_specification_option` VALUES ('5241', '234', '1663', '正常糖', '0.00', '1', '2', '2021-07-01 20:51:31', '2021-07-01 20:51:31');
INSERT INTO `tb_goods_specification_option` VALUES ('5242', '234', '1663', '双份糖', '0.00', '1', '3', '2021-07-01 20:51:41', '2021-07-01 20:51:41');
INSERT INTO `tb_goods_specification_option` VALUES ('5243', '233', '1664', '热', '0.00', '1', '1', '2021-07-01 20:51:49', '2021-07-01 20:51:49');
INSERT INTO `tb_goods_specification_option` VALUES ('5244', '233', '1664', '常温', '0.00', '1', '2', '2021-07-01 21:09:52', '2021-07-01 21:09:52');
INSERT INTO `tb_goods_specification_option` VALUES ('5245', '233', '1664', '冰', '0.00', '1', '3', '2021-07-01 21:10:02', '2021-07-01 21:10:02');
INSERT INTO `tb_goods_specification_option` VALUES ('5246', '233', '1665', '无糖', '0.00', '1', '1', '2021-07-01 21:10:16', '2021-07-01 21:10:16');
INSERT INTO `tb_goods_specification_option` VALUES ('5247', '233', '1665', '正常糖', '0.00', '1', '2', '2021-07-01 21:10:25', '2021-07-01 21:10:25');
INSERT INTO `tb_goods_specification_option` VALUES ('5248', '233', '1665', '双份糖', '0.00', '1', '3', '2021-07-01 21:10:39', '2021-07-01 21:10:39');
INSERT INTO `tb_goods_specification_option` VALUES ('5249', '232', '1666', '热', '0.00', '1', '1', '2021-07-01 21:11:16', '2021-07-01 21:11:16');
INSERT INTO `tb_goods_specification_option` VALUES ('5250', '232', '1666', '常温', '0.00', '1', '2', '2021-07-01 21:11:24', '2021-07-01 21:11:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5251', '232', '1666', '冰', '0.00', '1', '3', '2021-07-01 21:11:38', '2021-07-01 21:11:38');
INSERT INTO `tb_goods_specification_option` VALUES ('5252', '232', '1667', '无糖', '0.00', '1', '1', '2021-07-01 21:11:44', '2021-07-01 21:11:44');
INSERT INTO `tb_goods_specification_option` VALUES ('5253', '232', '1667', '正常糖', '0.00', '1', '2', '2021-07-01 21:11:55', '2021-07-01 21:11:55');
INSERT INTO `tb_goods_specification_option` VALUES ('5254', '232', '1667', '双份糖', '0.00', '1', '3', '2021-07-01 21:12:00', '2021-07-01 21:12:00');
INSERT INTO `tb_goods_specification_option` VALUES ('5255', '231', '1668', '冰', '0.00', '1', '1', '2021-07-01 21:12:27', '2021-07-01 21:12:27');
INSERT INTO `tb_goods_specification_option` VALUES ('5256', '231', '1669', '5分糖', '0.00', '1', '1', '2021-07-01 21:13:06', '2021-07-01 21:13:06');
INSERT INTO `tb_goods_specification_option` VALUES ('5257', '231', '1669', '正常糖', '0.00', '1', '2', '2021-07-01 21:13:24', '2021-07-01 21:13:24');
INSERT INTO `tb_goods_specification_option` VALUES ('5258', '231', '1669', '双份糖', '0.00', '1', '3', '2021-07-01 21:13:33', '2021-07-01 21:13:33');
INSERT INTO `tb_goods_specification_option` VALUES ('5259', '229', '1670', '热', '0.00', '1', '1', '2021-07-01 21:14:01', '2021-07-01 21:14:01');
INSERT INTO `tb_goods_specification_option` VALUES ('5260', '229', '1670', '常温', '0.00', '1', '2', '2021-07-01 21:14:05', '2021-07-01 21:14:05');
INSERT INTO `tb_goods_specification_option` VALUES ('5261', '229', '1671', '正常糖', '0.00', '1', '1', '2021-07-01 21:14:23', '2021-07-01 21:14:23');
INSERT INTO `tb_goods_specification_option` VALUES ('5262', '228', '1672', '中杯', '0.00', '1', '1', '2021-07-01 21:14:34', '2021-07-01 21:14:34');
INSERT INTO `tb_goods_specification_option` VALUES ('5263', '228', '1672', '大杯', '2.00', '1', '2', '2021-07-01 21:14:41', '2021-07-01 21:14:41');
INSERT INTO `tb_goods_specification_option` VALUES ('5264', '228', '1673', '常温', '0.00', '1', '1', '2021-07-01 21:14:47', '2021-07-01 21:14:47');
INSERT INTO `tb_goods_specification_option` VALUES ('5265', '228', '1673', '冰', '0.00', '1', '2', '2021-07-01 21:14:51', '2021-07-01 21:14:51');
INSERT INTO `tb_goods_specification_option` VALUES ('5266', '228', '1674', '正常糖', '0.00', '1', '1', '2021-07-01 21:15:02', '2021-07-01 21:15:02');
INSERT INTO `tb_goods_specification_option` VALUES ('5267', '228', '1674', '双份糖', '0.00', '1', '2', '2021-07-01 21:15:14', '2021-07-01 21:15:14');
INSERT INTO `tb_goods_specification_option` VALUES ('5268', '227', '1675', '中杯', '0.00', '1', '1', '2021-07-01 21:15:25', '2021-07-01 21:15:25');
INSERT INTO `tb_goods_specification_option` VALUES ('5269', '227', '1675', '大杯', '2.00', '1', '2', '2021-07-01 21:15:33', '2021-07-01 21:15:33');
INSERT INTO `tb_goods_specification_option` VALUES ('5270', '227', '1676', '常温', '0.00', '1', '1', '2021-07-01 21:15:39', '2021-07-01 21:15:39');
INSERT INTO `tb_goods_specification_option` VALUES ('5271', '227', '1676', '冰', '0.00', '1', '2', '2021-07-01 21:15:45', '2021-07-01 21:15:45');
INSERT INTO `tb_goods_specification_option` VALUES ('5272', '227', '1677', '正常糖', '0.00', '1', '1', '2021-07-01 21:15:53', '2021-07-01 21:15:53');
INSERT INTO `tb_goods_specification_option` VALUES ('5273', '227', '1677', '双份糖', '0.00', '1', '2', '2021-07-01 21:16:05', '2021-07-01 21:16:05');

-- ----------------------------
-- Table structure for tb_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_member`;
CREATE TABLE `tb_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `password_salt` varchar(100) DEFAULT NULL COMMENT '密码盐值',
  `nickname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称(废弃字段)',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '余额',
  `login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登陆次数',
  `invite_code` varchar(10) DEFAULT NULL COMMENT '邀请码',
  `head_img` varchar(256) DEFAULT NULL COMMENT '头像',
  `roles` varchar(128) DEFAULT NULL COMMENT '权限',
  `sex` int(2) DEFAULT '0' COMMENT '性别 0=无 1=男 2=女',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 0=启用 1=禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=删除',
  `open_id` varchar(50) DEFAULT NULL COMMENT '微信小程序openId',
  `is_bind_wx` tinyint(1) DEFAULT '0' COMMENT '是否绑定微信 0=否 1=是',
  `points` decimal(10,2) DEFAULT '0.00' COMMENT '积分',
  `vip_status` int(2) DEFAULT '0' COMMENT '会员状态 0=无/非会员 1=正常 2=禁用 3=已过期(预留字段)',
  `vip_type` int(2) DEFAULT '0' COMMENT '会员类型 0=无 1=超级会员 2=黄金会员 3=钻石会员(预留字段)',
  `vip_start_time` datetime DEFAULT NULL COMMENT '会员开始时间(预留字段)',
  `vip_end_time` datetime DEFAULT NULL COMMENT '会员结束时间(预留字段)',
  `type` int(2) DEFAULT '1' COMMENT '用户类型 1=普通用户 2=VIP会员',
  `vip_no` varchar(50) DEFAULT NULL COMMENT '会员编号',
  `is_new_people` tinyint(1) DEFAULT '1' COMMENT '是否为新用户 0=否 1=是',
  `is_remind_new_people` tinyint(1) DEFAULT '1' COMMENT '是否需要弹出新人引导提示 0=否 1=是',
  `last_use_time` datetime DEFAULT NULL COMMENT '最后使用/进入小程序的时间',
  `last_use_address` varchar(200) DEFAULT NULL COMMENT '最后使用/进入小程序的定位地址',
  `register_way` int(2) DEFAULT NULL COMMENT '注册方式 1=微信一键登录 2=手机验证码 3=邀请注册',
  `wx_public_platform_open_id` varchar(50) DEFAULT NULL COMMENT '微信公众号openId',
  `is_request_wx_notify` tinyint(1) DEFAULT '1' COMMENT '是否需要请求授权服务通知 0=否 1=是',
  `last_request_wx_notify_time` datetime DEFAULT NULL COMMENT '上一次请求授权服务通知时间',
  `invite_reward_amount` decimal(10,2) DEFAULT '0.00' COMMENT '邀请新用户注册奖励金额',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `total_balance` decimal(10,2) DEFAULT '0.00' COMMENT '累计余额',
  `total_consume_balance` decimal(10,2) DEFAULT '0.00' COMMENT '累计消费余额',
  `total_points` decimal(10,2) DEFAULT '0.00' COMMENT '累计积分',
  `total_consume_points` decimal(10,2) DEFAULT '0.00' COMMENT '累计消费积分',
  `total_withdraw_invite_reward_amount` decimal(10,2) DEFAULT '0.00' COMMENT '累积已提邀请新用户注册奖励金额',
  `payment_password` varchar(100) DEFAULT NULL COMMENT '支付密码',
  `payment_password_salt` varchar(100) DEFAULT NULL COMMENT '支付密码盐值',
  `invite_suncode` varchar(256) DEFAULT NULL COMMENT '邀请分享-微信小程序太阳码',
  `unreceived_points` decimal(10,2) DEFAULT '0.00' COMMENT '未到账-积分',
  `unreceived_invite_reward_amount` decimal(10,2) DEFAULT '0.00' COMMENT '未到账-邀请新用户注册奖励金额',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_member
-- ----------------------------
INSERT INTO `tb_member` VALUES ('2', 'siam', '13121865386', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', '13121865386', '10000.00', '425', '', 'https://picture.moguit.cn//blog/admin/ico/2021/12/6/1638752954320.ico', '', '0', '', '0', '0', '', '0', '9541.00', '0', '1', '2023-11-14 16:22:10', '2024-02-14 16:22:10', '1', '', '0', '0', '2023-11-23 20:48:42', '广东省深圳市宝安区新安街道创业一路深圳市宝安区人民政府', null, '', '1', '2023-06-18 14:43:20', '8986.97', '飞机', '0.00', '0.00', '10000.00', '6451.00', '1013.03', '2ee6b71bf6fea7b0e6b3307ed48a230c', '3e615a0924384788a2fd', '', '0.00', '0.00', '2023-06-18 14:43:31', '2023-11-23 19:05:18', '2023-11-23 20:34:55');

-- ----------------------------
-- Table structure for tb_member_billing_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_billing_record`;
CREATE TABLE `tb_member_billing_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `type` int(2) NOT NULL COMMENT '账单类型 1=积分兑换商品 2=下单奖励积分 3=新用户注册赠送积分 4=会员充值奖励余额 5=邀请新用户注册获得奖励金额 6=邀请新用户注册奖励金额提现 7=邀请新用户注册奖励金额提现失败退回 8=一级邀请人佣金奖励 9=二级邀请人佣金奖励 10=下单用户佣金奖励 11=新用户注册赠送奖励金额 12=订单使用余额支付 13=一分钟内取消订单-余额退回 14=用户申请退款-余额退回 15=积分商城订单使用积分支付 16=未发货订单申请退款-积分退回 17=已发货订单申请退款-积分退回 18=会员充值原价金额同等转入余额 19=订单退款-下单奖励积分退回 20=订单退款-一级邀请人佣金奖励退回 21=订单退款-二级邀请人佣金奖励退回 22=订单退款-下单用户佣金奖励退回',
  `operate_type` int(2) NOT NULL COMMENT '操作类型 1=加 2=减',
  `coin_type` int(2) NOT NULL COMMENT '货币类型 1=积分 2=余额 3=邀请新用户注册奖励金额 4=未到账-积分 5=未到账-邀请新用户注册奖励金额',
  `number` decimal(10,2) NOT NULL COMMENT '增减的数值',
  `service_fee` decimal(10,2) DEFAULT NULL COMMENT '服务费',
  `message` varchar(200) DEFAULT NULL COMMENT '账单信息',
  `order_id` int(11) DEFAULT NULL COMMENT '外卖系统订单id',
  `points_mall_order_id` int(11) DEFAULT NULL COMMENT '积分商城订单id',
  `is_return` tinyint(1) DEFAULT '0' COMMENT '账单金额是否被退回',
  `is_settled` tinyint(1) DEFAULT '0' COMMENT '账单金额是否已发放/已结算',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8 COMMENT='用户账单记录表';

-- ----------------------------
-- Records of tb_member_billing_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_member_goods_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_goods_collect`;
CREATE TABLE `tb_member_goods_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `is_goods_exists` tinyint(1) DEFAULT '1' COMMENT '商品是否有效 0=无效 1=有效',
  `is_buy` tinyint(1) DEFAULT '0' COMMENT '商品是否购买 0=未购买 1=已购买',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_member_goods_collect
-- ----------------------------

-- ----------------------------
-- Table structure for tb_member_invite_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_invite_relation`;
CREATE TABLE `tb_member_invite_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '被邀请用户id',
  `inviter_id` int(11) DEFAULT NULL COMMENT '邀请者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=236 DEFAULT CHARSET=utf8 COMMENT='用户邀请关系表’';

-- ----------------------------
-- Records of tb_member_invite_relation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_member_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_token`;
CREATE TABLE `tb_member_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户名',
  `token` varchar(128) NOT NULL COMMENT '登录令牌',
  `type` varchar(5) DEFAULT NULL COMMENT '登录方式 wap',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2552 DEFAULT CHARSET=utf8 COMMENT='用户登录token令牌表';

-- ----------------------------
-- Records of tb_member_token
-- ----------------------------

-- ----------------------------
-- Table structure for tb_member_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_trade_record`;
CREATE TABLE `tb_member_trade_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家账号id',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '商户单号(网站平台的订单号)',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '交易单号(支付平台的订单号)',
  `type` int(2) DEFAULT '1' COMMENT '交易类型 1=用户订单付款 2=用户会员充值 3=用户自取订单改为配送 4=商家余额提现 5=邀请新用户注册奖励金额提现 6=用户积分商城订单付款',
  `payment_mode` int(2) DEFAULT NULL COMMENT '支付方式 1=微信 2=支付宝 3=平台余额 4=平台积分',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `status` int(2) DEFAULT '1' COMMENT '交易状态 1=待支付 2=支付成功 3=支付失败 4=交易超时自动关闭',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3246 DEFAULT CHARSET=utf8 COMMENT='平台交易记录表';

-- ----------------------------
-- Records of tb_member_trade_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_member_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_member_withdraw_record`;
CREATE TABLE `tb_member_withdraw_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号，供客户查询',
  `coin_type` int(2) NOT NULL COMMENT '货币类型 1=邀请新用户注册奖励金额',
  `withdraw_amount` decimal(10,2) NOT NULL COMMENT '提现金额',
  `platform_fee` decimal(10,2) NOT NULL COMMENT '平台手续费/服务费',
  `actual_amount` decimal(10,2) DEFAULT NULL COMMENT '实际到账金额',
  `audit_status` int(2) NOT NULL DEFAULT '1' COMMENT '审核状态 1=平台处理中 2=到账成功 3=审核不通过',
  `audit_reason` varchar(50) DEFAULT NULL COMMENT '审核不通过原因',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `payment_mode` int(2) DEFAULT NULL COMMENT '打款方式/到账方式 1=微信 2=支付宝 3=银行',
  `opening_bank_address` varchar(50) DEFAULT NULL COMMENT '开户行',
  `opening_bank_name` varchar(50) DEFAULT NULL COMMENT '开户银行名称',
  `bank_card` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `alipay_account` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `wechat_account` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `trade_id` int(11) DEFAULT NULL COMMENT '用户交易id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_member_withdraw_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '菜单分类名称',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单分类描述',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单分类表';

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES ('17', '13', '周边甜品', null, null, '2020-05-11 00:27:20', '2020-06-15 11:49:21');
INSERT INTO `tb_menu` VALUES ('18', '13', '甄选咖啡', null, null, '2020-05-11 00:28:01', '2020-06-15 11:49:15');
INSERT INTO `tb_menu` VALUES ('19', '13', '益菌多气泡', null, null, '2020-05-11 00:29:35', '2020-06-15 11:49:10');
INSERT INTO `tb_menu` VALUES ('20', '13', '鲜果汁', null, null, '2020-05-11 00:29:51', '2020-06-15 11:49:04');
INSERT INTO `tb_menu` VALUES ('21', '13', '鲜果益菌多', null, null, '2020-05-11 00:30:38', '2020-06-15 11:48:56');
INSERT INTO `tb_menu` VALUES ('22', '13', '鲜果芝士', null, null, '2020-05-11 00:31:40', '2020-06-15 11:48:51');
INSERT INTO `tb_menu` VALUES ('23', '13', '鲜果茶', null, null, '2020-05-11 00:33:11', '2020-06-15 11:48:45');
INSERT INTO `tb_menu` VALUES ('24', '13', '人气爆款饮品', null, null, '2020-05-11 00:34:19', '2020-06-15 11:48:39');
INSERT INTO `tb_menu` VALUES ('26', '13', '本周新品', null, null, '2020-05-11 18:23:27', '2020-06-15 11:48:30');
INSERT INTO `tb_menu` VALUES ('50', '15', '☕周边小吃☕', null, null, '2020-09-22 22:52:00', '2021-03-07 15:06:17');
INSERT INTO `tb_menu` VALUES ('51', '15', '☕小气纯茶☕', null, null, '2020-09-22 22:52:15', '2020-09-22 23:36:07');
INSERT INTO `tb_menu` VALUES ('52', '15', '☕可可抹茶☕', null, null, '2020-09-22 22:52:25', '2021-03-07 15:06:12');
INSERT INTO `tb_menu` VALUES ('53', '15', '☕恋日暖冬☕', null, null, '2020-09-22 22:53:07', '2021-03-07 15:06:07');
INSERT INTO `tb_menu` VALUES ('54', '15', '☕小气益菌多☕', null, null, '2020-09-22 22:53:49', '2021-03-07 15:06:02');
INSERT INTO `tb_menu` VALUES ('55', '15', '☕芝士奶霜☕', null, null, '2020-09-22 22:54:11', '2021-03-07 15:05:56');
INSERT INTO `tb_menu` VALUES ('56', '15', '☕小气水果茶☕', null, null, '2020-09-22 22:54:22', '2021-03-07 15:05:50');
INSERT INTO `tb_menu` VALUES ('57', '15', '☕小气奶昔果茶☕', null, null, '2020-09-22 22:55:36', '2021-03-07 15:05:43');
INSERT INTO `tb_menu` VALUES ('59', '15', '☕气泡果饮☕', null, null, '2020-09-22 22:55:48', '2021-03-07 15:05:32');
INSERT INTO `tb_menu` VALUES ('60', '15', '☕缤纷奶昔☕', null, null, '2020-09-22 22:56:02', '2021-03-07 15:05:26');
INSERT INTO `tb_menu` VALUES ('61', '15', '☕招牌奶茶☕', null, null, '2020-09-22 22:56:35', '2021-03-07 15:02:23');
INSERT INTO `tb_menu` VALUES ('65', '16', '茉莉奶绿', null, null, '2020-10-08 17:41:03', '2020-10-08 17:41:03');
INSERT INTO `tb_menu` VALUES ('66', '16', '满杯风味', null, null, '2020-10-08 17:41:12', '2020-10-08 17:41:12');
INSERT INTO `tb_menu` VALUES ('67', '16', '香浓奶茶', null, null, '2020-10-08 17:41:21', '2020-10-08 17:41:21');
INSERT INTO `tb_menu` VALUES ('68', '16', '波波酸奶', null, null, '2020-10-08 17:50:06', '2020-10-08 17:50:06');
INSERT INTO `tb_menu` VALUES ('69', '16', '满杯益菌多', null, null, '2020-10-08 17:50:17', '2020-10-08 17:50:17');
INSERT INTO `tb_menu` VALUES ('70', '16', '满杯好味道', null, null, '2020-10-08 17:50:31', '2020-10-08 17:50:31');
INSERT INTO `tb_menu` VALUES ('71', '16', '满杯鲜果茶', null, null, '2020-10-08 17:50:42', '2020-10-08 17:50:42');
INSERT INTO `tb_menu` VALUES ('72', '16', '满杯烧仙草', null, null, '2020-10-08 17:50:54', '2020-10-08 17:50:54');
INSERT INTO `tb_menu` VALUES ('73', '16', '冬季热饮', null, null, '2020-10-08 17:51:35', '2020-10-08 17:51:35');
INSERT INTO `tb_menu` VALUES ('74', '16', '进店必选', null, null, '2020-10-08 17:51:46', '2020-10-08 17:51:46');
INSERT INTO `tb_menu` VALUES ('76', '18', '醇香咖啡', null, null, '2020-10-12 11:08:27', '2021-07-01 18:26:36');
INSERT INTO `tb_menu` VALUES ('77', '18', '鲜果益菌多', null, null, '2020-10-12 11:08:41', '2021-07-01 18:26:20');
INSERT INTO `tb_menu` VALUES ('78', '18', '鲜果鲜茶', null, null, '2020-10-12 11:09:15', '2021-07-01 18:26:03');
INSERT INTO `tb_menu` VALUES ('79', '18', '芝士奶盖', null, null, '2020-10-12 11:09:50', '2021-07-01 18:25:54');
INSERT INTO `tb_menu` VALUES ('80', '18', '甜品 点心', null, null, '2020-10-12 11:10:23', '2020-10-12 11:10:23');
INSERT INTO `tb_menu` VALUES ('81', '18', '脏脏奶茶', null, null, '2020-10-12 11:11:03', '2020-10-12 11:11:03');
INSERT INTO `tb_menu` VALUES ('82', '18', '特色奶茶', null, null, '2020-10-12 11:12:08', '2020-10-12 11:12:08');
INSERT INTO `tb_menu` VALUES ('83', '18', '本周上新', null, null, '2020-10-12 11:12:28', '2020-10-12 11:12:28');
INSERT INTO `tb_menu` VALUES ('84', '18', '醇香鲜奶', null, null, '2020-10-12 11:12:47', '2021-06-28 22:11:07');
INSERT INTO `tb_menu` VALUES ('91', '19', '奶爸双皮奶', null, null, '2021-02-28 13:19:07', '2021-02-28 13:19:07');
INSERT INTO `tb_menu` VALUES ('92', '19', '奶爸益菌多', null, null, '2021-02-28 13:19:21', '2021-02-28 13:19:21');
INSERT INTO `tb_menu` VALUES ('93', '19', '奶爸鲜奶', null, null, '2021-02-28 13:19:29', '2021-02-28 13:19:29');
INSERT INTO `tb_menu` VALUES ('94', '19', '奶爸鲜果茶', null, null, '2021-02-28 13:19:37', '2021-02-28 13:19:37');
INSERT INTO `tb_menu` VALUES ('95', '19', '奶爸奶盖', null, null, '2021-02-28 13:20:00', '2021-02-28 13:20:00');
INSERT INTO `tb_menu` VALUES ('96', '19', '奶爸奶茶', null, null, '2021-02-28 13:20:11', '2021-02-28 13:20:11');
INSERT INTO `tb_menu` VALUES ('97', '18', '多份小料', null, null, '2021-07-01 21:18:01', '2021-07-01 21:18:01');
INSERT INTO `tb_menu` VALUES ('98', '19', '1', null, null, '2023-11-10 16:22:48', '2023-11-10 16:22:51');

-- ----------------------------
-- Table structure for tb_menu_goods_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu_goods_relation`;
CREATE TABLE `tb_menu_goods_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_id` int(11) NOT NULL COMMENT '菜单分类id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=288 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单分类商品关系表(多对多)';

-- ----------------------------
-- Records of tb_menu_goods_relation
-- ----------------------------
INSERT INTO `tb_menu_goods_relation` VALUES ('37', '24', '33', '2020-03-23 16:34:25', '2020-05-16 09:41:46');
INSERT INTO `tb_menu_goods_relation` VALUES ('39', '24', '28', '2020-03-23 16:34:49', '2020-06-04 17:41:09');
INSERT INTO `tb_menu_goods_relation` VALUES ('45', '23', '38', '2020-03-24 12:31:28', '2020-05-29 00:20:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('46', '24', '39', '2020-03-24 12:42:12', '2020-05-11 01:07:38');
INSERT INTO `tb_menu_goods_relation` VALUES ('48', '18', '41', '2020-04-05 00:15:13', '2020-05-11 00:36:14');
INSERT INTO `tb_menu_goods_relation` VALUES ('52', '24', '45', '2020-05-10 17:50:44', '2020-05-11 01:07:27');
INSERT INTO `tb_menu_goods_relation` VALUES ('53', '23', '46', '2020-05-10 17:58:42', '2020-05-29 00:20:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('54', '23', '47', '2020-05-10 18:23:23', '2020-05-29 00:20:58');
INSERT INTO `tb_menu_goods_relation` VALUES ('55', '22', '48', '2020-05-10 18:40:11', '2020-05-29 00:20:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('56', '24', '49', '2020-05-10 23:53:19', '2020-05-11 01:07:11');
INSERT INTO `tb_menu_goods_relation` VALUES ('58', '24', '51', '2020-05-11 00:13:55', '2020-06-04 17:40:41');
INSERT INTO `tb_menu_goods_relation` VALUES ('59', '19', '52', '2020-05-11 12:22:42', '2020-06-04 18:11:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('60', '19', '53', '2020-05-11 12:39:35', '2020-05-11 12:39:35');
INSERT INTO `tb_menu_goods_relation` VALUES ('63', '17', '56', '2020-05-12 11:13:42', '2020-05-15 09:17:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('64', '22', '57', '2020-05-12 17:52:48', '2020-06-04 17:40:09');
INSERT INTO `tb_menu_goods_relation` VALUES ('65', '24', '58', '2020-05-13 10:23:23', '2020-05-13 10:23:23');
INSERT INTO `tb_menu_goods_relation` VALUES ('66', '24', '59', '2020-05-13 10:40:30', '2020-07-03 18:35:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('67', '20', '60', '2020-05-13 11:58:38', '2020-05-13 13:50:56');
INSERT INTO `tb_menu_goods_relation` VALUES ('68', '22', '61', '2020-05-13 13:07:36', '2020-05-29 00:31:30');
INSERT INTO `tb_menu_goods_relation` VALUES ('69', '20', '62', '2020-05-15 14:38:01', '2020-05-15 14:38:01');
INSERT INTO `tb_menu_goods_relation` VALUES ('70', '20', '63', '2020-05-15 14:51:29', '2020-05-15 15:00:59');
INSERT INTO `tb_menu_goods_relation` VALUES ('71', '21', '64', '2020-05-15 14:55:06', '2020-05-29 00:18:00');
INSERT INTO `tb_menu_goods_relation` VALUES ('72', '20', '65', '2020-05-15 14:58:18', '2020-05-15 14:58:18');
INSERT INTO `tb_menu_goods_relation` VALUES ('73', '21', '66', '2020-05-17 11:36:34', '2020-06-15 14:27:06');
INSERT INTO `tb_menu_goods_relation` VALUES ('74', '24', '67', '2020-05-20 14:39:21', '2020-06-04 17:38:11');
INSERT INTO `tb_menu_goods_relation` VALUES ('75', '23', '68', '2020-05-20 14:46:25', '2020-05-29 00:17:19');
INSERT INTO `tb_menu_goods_relation` VALUES ('76', '24', '69', '2020-05-23 14:31:48', '2020-06-04 17:37:42');
INSERT INTO `tb_menu_goods_relation` VALUES ('77', '23', '70', '2020-05-25 16:40:04', '2020-05-29 00:17:00');
INSERT INTO `tb_menu_goods_relation` VALUES ('78', '23', '71', '2020-05-28 21:42:50', '2020-05-29 00:16:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('79', '19', '72', '2020-05-29 12:15:52', '2020-05-29 12:15:52');
INSERT INTO `tb_menu_goods_relation` VALUES ('80', '24', '73', '2020-06-01 15:16:30', '2020-06-21 12:58:31');
INSERT INTO `tb_menu_goods_relation` VALUES ('81', '23', '74', '2020-06-05 10:15:19', '2020-06-17 10:59:57');
INSERT INTO `tb_menu_goods_relation` VALUES ('82', '23', '75', '2020-06-15 14:33:36', '2020-07-03 18:36:38');
INSERT INTO `tb_menu_goods_relation` VALUES ('83', '26', '76', '2020-06-17 13:44:00', '2020-06-17 13:44:00');
INSERT INTO `tb_menu_goods_relation` VALUES ('84', '21', '77', '2020-06-17 14:21:44', '2020-06-21 15:14:12');
INSERT INTO `tb_menu_goods_relation` VALUES ('85', '23', '78', '2020-06-17 19:19:51', '2020-08-04 09:03:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('87', '26', '80', '2020-06-22 13:29:57', '2020-06-22 13:29:57');
INSERT INTO `tb_menu_goods_relation` VALUES ('88', '23', '81', '2020-07-01 14:01:44', '2020-08-04 09:03:35');
INSERT INTO `tb_menu_goods_relation` VALUES ('89', '26', '82', '2020-08-04 09:02:54', '2020-08-04 09:02:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('90', '57', '83', '2020-09-22 23:54:20', '2020-09-22 23:54:20');
INSERT INTO `tb_menu_goods_relation` VALUES ('91', '59', '84', '2020-09-23 00:05:00', '2020-09-23 00:05:00');
INSERT INTO `tb_menu_goods_relation` VALUES ('92', '61', '85', '2020-09-23 00:17:40', '2020-09-23 00:17:40');
INSERT INTO `tb_menu_goods_relation` VALUES ('93', '56', '86', '2020-09-23 00:29:18', '2020-09-23 00:29:18');
INSERT INTO `tb_menu_goods_relation` VALUES ('94', '60', '87', '2020-09-23 00:37:37', '2020-09-23 00:37:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('95', '59', '88', '2020-09-23 00:42:36', '2020-09-23 00:42:36');
INSERT INTO `tb_menu_goods_relation` VALUES ('96', '60', '89', '2020-09-23 08:09:23', '2020-09-23 08:09:23');
INSERT INTO `tb_menu_goods_relation` VALUES ('97', '60', '90', '2020-09-24 11:01:03', '2020-09-24 11:01:03');
INSERT INTO `tb_menu_goods_relation` VALUES ('98', '55', '91', '2020-09-24 11:11:08', '2020-09-24 11:11:08');
INSERT INTO `tb_menu_goods_relation` VALUES ('99', '57', '92', '2020-09-25 23:35:40', '2020-09-25 23:35:40');
INSERT INTO `tb_menu_goods_relation` VALUES ('100', '60', '93', '2020-09-25 23:45:54', '2020-09-25 23:45:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('101', '55', '94', '2020-09-25 23:50:14', '2020-09-25 23:50:14');
INSERT INTO `tb_menu_goods_relation` VALUES ('102', '59', '95', '2020-09-26 00:10:22', '2020-09-26 00:10:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('103', '56', '96', '2020-09-26 00:15:35', '2020-09-26 00:15:35');
INSERT INTO `tb_menu_goods_relation` VALUES ('104', '50', '97', '2020-09-26 00:20:52', '2020-09-26 00:20:52');
INSERT INTO `tb_menu_goods_relation` VALUES ('105', '50', '98', '2020-09-26 00:24:20', '2020-09-26 00:24:20');
INSERT INTO `tb_menu_goods_relation` VALUES ('106', '50', '99', '2020-09-26 00:27:19', '2020-09-26 00:27:19');
INSERT INTO `tb_menu_goods_relation` VALUES ('107', '60', '100', '2020-10-07 17:40:56', '2020-10-07 17:40:56');
INSERT INTO `tb_menu_goods_relation` VALUES ('108', '61', '101', '2020-10-08 09:59:50', '2020-10-08 09:59:50');
INSERT INTO `tb_menu_goods_relation` VALUES ('109', '52', '102', '2020-10-08 10:02:43', '2020-10-08 10:02:43');
INSERT INTO `tb_menu_goods_relation` VALUES ('110', '52', '103', '2020-10-08 10:04:35', '2020-10-08 10:04:35');
INSERT INTO `tb_menu_goods_relation` VALUES ('111', '52', '104', '2020-10-08 10:06:28', '2020-10-08 10:06:28');
INSERT INTO `tb_menu_goods_relation` VALUES ('112', '52', '105', '2020-10-08 10:08:22', '2020-10-08 10:08:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('113', '51', '106', '2020-10-08 10:10:14', '2020-10-08 10:10:14');
INSERT INTO `tb_menu_goods_relation` VALUES ('114', '51', '107', '2020-10-08 10:12:11', '2020-10-08 10:12:11');
INSERT INTO `tb_menu_goods_relation` VALUES ('115', '51', '108', '2020-10-08 10:13:13', '2020-10-08 10:13:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('116', '51', '109', '2020-10-08 10:15:07', '2020-10-08 10:15:07');
INSERT INTO `tb_menu_goods_relation` VALUES ('117', '51', '110', '2020-10-08 10:17:48', '2020-10-08 10:17:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('118', '55', '111', '2020-10-08 10:20:55', '2020-10-08 10:20:55');
INSERT INTO `tb_menu_goods_relation` VALUES ('119', '55', '112', '2020-10-08 10:25:15', '2020-10-08 10:25:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('120', '55', '113', '2020-10-08 10:27:22', '2020-10-08 10:27:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('121', '55', '114', '2020-10-08 10:29:58', '2020-10-08 10:29:58');
INSERT INTO `tb_menu_goods_relation` VALUES ('122', '60', '115', '2020-10-08 10:33:51', '2020-10-08 10:33:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('123', '60', '116', '2020-10-08 10:35:34', '2020-10-08 10:35:34');
INSERT INTO `tb_menu_goods_relation` VALUES ('124', '60', '117', '2020-10-08 10:37:55', '2020-10-08 10:37:55');
INSERT INTO `tb_menu_goods_relation` VALUES ('125', '60', '118', '2020-10-08 10:40:32', '2020-10-08 10:40:32');
INSERT INTO `tb_menu_goods_relation` VALUES ('126', '61', '119', '2020-10-08 10:47:10', '2020-10-08 10:47:10');
INSERT INTO `tb_menu_goods_relation` VALUES ('127', '61', '120', '2020-10-08 10:50:17', '2020-10-08 10:50:17');
INSERT INTO `tb_menu_goods_relation` VALUES ('128', '61', '121', '2020-10-08 10:54:13', '2020-10-08 10:54:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('129', '61', '122', '2020-10-08 10:56:06', '2020-10-08 10:56:06');
INSERT INTO `tb_menu_goods_relation` VALUES ('130', '61', '123', '2020-10-08 10:58:13', '2020-10-08 10:58:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('131', '60', '124', '2020-10-08 11:00:16', '2020-10-08 11:00:16');
INSERT INTO `tb_menu_goods_relation` VALUES ('132', '61', '125', '2020-10-08 11:01:14', '2020-10-08 11:01:14');
INSERT INTO `tb_menu_goods_relation` VALUES ('133', '61', '126', '2020-10-08 11:03:34', '2020-10-08 11:03:34');
INSERT INTO `tb_menu_goods_relation` VALUES ('134', '57', '127', '2020-10-08 11:12:31', '2020-10-08 11:12:31');
INSERT INTO `tb_menu_goods_relation` VALUES ('135', '56', '128', '2020-10-08 11:14:22', '2020-10-08 11:14:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('136', '56', '129', '2020-10-08 11:18:18', '2020-10-08 11:18:18');
INSERT INTO `tb_menu_goods_relation` VALUES ('137', '56', '130', '2020-10-08 11:20:27', '2020-10-08 11:20:27');
INSERT INTO `tb_menu_goods_relation` VALUES ('138', '56', '131', '2020-10-08 11:26:32', '2020-10-08 11:26:32');
INSERT INTO `tb_menu_goods_relation` VALUES ('139', '54', '132', '2020-10-08 11:32:38', '2020-10-08 11:32:38');
INSERT INTO `tb_menu_goods_relation` VALUES ('140', '54', '133', '2020-10-08 11:34:08', '2020-10-08 11:34:08');
INSERT INTO `tb_menu_goods_relation` VALUES ('141', '59', '134', '2020-10-08 11:41:05', '2020-10-08 11:41:05');
INSERT INTO `tb_menu_goods_relation` VALUES ('142', '53', '135', '2020-10-08 12:09:24', '2020-10-08 12:09:24');
INSERT INTO `tb_menu_goods_relation` VALUES ('143', '53', '136', '2020-10-08 12:11:21', '2020-10-08 12:11:21');
INSERT INTO `tb_menu_goods_relation` VALUES ('144', '73', '137', '2020-10-08 17:56:03', '2020-10-08 17:56:03');
INSERT INTO `tb_menu_goods_relation` VALUES ('145', '73', '138', '2020-10-08 17:58:27', '2020-10-08 17:58:27');
INSERT INTO `tb_menu_goods_relation` VALUES ('146', '73', '139', '2020-10-08 18:01:11', '2020-10-08 18:01:11');
INSERT INTO `tb_menu_goods_relation` VALUES ('147', '73', '140', '2020-10-08 18:05:59', '2020-10-08 18:05:59');
INSERT INTO `tb_menu_goods_relation` VALUES ('148', '73', '141', '2020-10-08 18:07:15', '2020-10-08 18:07:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('149', '73', '142', '2020-10-08 18:09:49', '2020-10-08 18:09:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('150', '73', '143', '2020-10-08 18:11:54', '2020-10-08 18:11:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('151', '73', '144', '2020-10-08 18:14:51', '2020-10-08 18:14:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('152', '73', '145', '2020-10-08 18:18:33', '2020-10-08 18:18:33');
INSERT INTO `tb_menu_goods_relation` VALUES ('153', '69', '146', '2020-10-08 18:21:08', '2020-10-08 18:22:18');
INSERT INTO `tb_menu_goods_relation` VALUES ('154', '71', '147', '2020-10-08 18:23:04', '2020-10-08 18:23:04');
INSERT INTO `tb_menu_goods_relation` VALUES ('155', '69', '148', '2020-10-08 18:24:11', '2020-10-08 18:24:11');
INSERT INTO `tb_menu_goods_relation` VALUES ('156', '71', '149', '2020-10-08 18:27:12', '2020-10-08 18:27:12');
INSERT INTO `tb_menu_goods_relation` VALUES ('157', '74', '150', '2020-10-08 18:29:55', '2020-10-08 18:29:55');
INSERT INTO `tb_menu_goods_relation` VALUES ('158', '74', '151', '2020-10-08 18:32:13', '2020-10-08 18:32:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('159', '70', '152', '2020-10-08 18:34:37', '2020-10-08 18:34:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('160', '68', '153', '2020-10-08 18:36:43', '2020-10-08 18:36:43');
INSERT INTO `tb_menu_goods_relation` VALUES ('161', '69', '154', '2020-10-08 18:38:50', '2020-10-08 18:38:50');
INSERT INTO `tb_menu_goods_relation` VALUES ('162', '66', '155', '2020-10-08 18:41:40', '2020-10-08 18:41:40');
INSERT INTO `tb_menu_goods_relation` VALUES ('163', '71', '156', '2020-10-08 18:45:16', '2020-10-08 18:45:16');
INSERT INTO `tb_menu_goods_relation` VALUES ('164', '68', '157', '2020-10-08 18:47:36', '2020-10-08 18:47:36');
INSERT INTO `tb_menu_goods_relation` VALUES ('165', '71', '158', '2020-10-08 18:50:20', '2020-10-08 18:50:20');
INSERT INTO `tb_menu_goods_relation` VALUES ('166', '70', '159', '2020-10-08 18:52:28', '2020-10-08 18:52:28');
INSERT INTO `tb_menu_goods_relation` VALUES ('167', '67', '160', '2020-10-08 18:54:43', '2020-10-08 18:54:43');
INSERT INTO `tb_menu_goods_relation` VALUES ('168', '67', '161', '2020-10-08 18:56:39', '2020-10-08 18:56:39');
INSERT INTO `tb_menu_goods_relation` VALUES ('169', '70', '162', '2020-10-08 18:59:50', '2020-10-08 18:59:50');
INSERT INTO `tb_menu_goods_relation` VALUES ('170', '71', '163', '2020-10-08 19:57:46', '2020-10-08 19:57:46');
INSERT INTO `tb_menu_goods_relation` VALUES ('171', '68', '164', '2020-10-08 19:59:52', '2020-10-08 19:59:52');
INSERT INTO `tb_menu_goods_relation` VALUES ('172', '66', '165', '2020-10-08 20:01:59', '2020-10-08 20:01:59');
INSERT INTO `tb_menu_goods_relation` VALUES ('173', '66', '166', '2020-10-08 20:04:33', '2020-10-08 20:04:33');
INSERT INTO `tb_menu_goods_relation` VALUES ('174', '71', '167', '2020-10-08 20:06:57', '2020-10-08 20:06:57');
INSERT INTO `tb_menu_goods_relation` VALUES ('175', '72', '168', '2020-10-08 20:09:15', '2020-10-08 20:09:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('176', '70', '169', '2020-10-08 20:14:38', '2020-10-08 20:14:38');
INSERT INTO `tb_menu_goods_relation` VALUES ('177', '69', '170', '2020-10-08 20:23:37', '2020-10-08 20:23:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('178', '67', '171', '2020-10-08 20:25:41', '2020-10-08 20:25:41');
INSERT INTO `tb_menu_goods_relation` VALUES ('179', '71', '172', '2020-10-08 20:28:05', '2020-10-08 20:28:05');
INSERT INTO `tb_menu_goods_relation` VALUES ('180', '69', '173', '2020-10-08 20:30:40', '2020-10-08 20:30:40');
INSERT INTO `tb_menu_goods_relation` VALUES ('181', '69', '174', '2020-10-08 20:32:37', '2020-10-08 20:32:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('182', '66', '175', '2020-10-08 20:36:56', '2020-10-08 20:36:56');
INSERT INTO `tb_menu_goods_relation` VALUES ('183', '66', '176', '2020-10-08 20:38:51', '2020-10-08 20:38:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('184', '72', '177', '2020-10-08 20:40:54', '2020-10-08 20:40:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('185', '71', '178', '2020-10-08 20:44:09', '2020-10-08 20:44:09');
INSERT INTO `tb_menu_goods_relation` VALUES ('186', '69', '179', '2020-10-08 20:46:42', '2020-10-08 20:46:42');
INSERT INTO `tb_menu_goods_relation` VALUES ('187', '72', '180', '2020-10-08 20:51:06', '2020-10-08 20:51:06');
INSERT INTO `tb_menu_goods_relation` VALUES ('188', '68', '181', '2020-10-08 20:53:26', '2020-10-08 20:53:26');
INSERT INTO `tb_menu_goods_relation` VALUES ('189', '67', '182', '2020-10-08 20:55:43', '2020-10-08 20:55:43');
INSERT INTO `tb_menu_goods_relation` VALUES ('190', '67', '183', '2020-10-08 20:57:48', '2020-10-08 20:57:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('191', '72', '184', '2020-10-08 21:01:55', '2020-10-08 21:01:55');
INSERT INTO `tb_menu_goods_relation` VALUES ('192', '67', '185', '2020-10-08 21:03:34', '2020-10-08 21:03:34');
INSERT INTO `tb_menu_goods_relation` VALUES ('193', '57', '186', '2020-10-09 13:57:49', '2020-10-09 13:57:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('194', '56', '187', '2020-10-09 14:01:44', '2020-10-09 14:01:44');
INSERT INTO `tb_menu_goods_relation` VALUES ('195', '56', '188', '2020-10-09 14:07:46', '2020-10-09 14:07:46');
INSERT INTO `tb_menu_goods_relation` VALUES ('196', '55', '189', '2020-10-09 14:14:47', '2020-10-09 14:14:47');
INSERT INTO `tb_menu_goods_relation` VALUES ('197', '56', '190', '2020-10-09 14:17:53', '2020-10-09 14:17:53');
INSERT INTO `tb_menu_goods_relation` VALUES ('198', '53', '191', '2020-10-09 14:21:54', '2020-10-09 14:21:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('199', '59', '192', '2020-10-09 14:29:53', '2020-10-09 14:29:53');
INSERT INTO `tb_menu_goods_relation` VALUES ('200', '59', '193', '2020-10-09 14:36:45', '2020-10-09 14:36:45');
INSERT INTO `tb_menu_goods_relation` VALUES ('201', '53', '194', '2020-10-09 14:42:57', '2020-10-09 14:42:57');
INSERT INTO `tb_menu_goods_relation` VALUES ('202', '61', '195', '2020-10-09 14:45:33', '2020-10-09 14:45:33');
INSERT INTO `tb_menu_goods_relation` VALUES ('203', '54', '196', '2020-10-09 14:48:12', '2020-10-09 14:48:12');
INSERT INTO `tb_menu_goods_relation` VALUES ('204', '53', '197', '2020-10-09 14:51:17', '2020-10-09 14:51:17');
INSERT INTO `tb_menu_goods_relation` VALUES ('205', '81', '198', '2020-10-12 11:15:55', '2020-10-12 11:15:55');
INSERT INTO `tb_menu_goods_relation` VALUES ('206', '82', '199', '2020-10-12 11:23:15', '2020-10-12 11:23:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('207', '81', '200', '2020-10-12 11:32:29', '2020-10-12 11:32:29');
INSERT INTO `tb_menu_goods_relation` VALUES ('208', '79', '201', '2020-10-12 12:02:03', '2020-10-12 12:02:03');
INSERT INTO `tb_menu_goods_relation` VALUES ('209', '84', '202', '2020-10-12 12:06:02', '2020-10-12 12:06:02');
INSERT INTO `tb_menu_goods_relation` VALUES ('210', '82', '203', '2020-10-12 12:09:09', '2020-10-12 12:09:09');
INSERT INTO `tb_menu_goods_relation` VALUES ('211', '78', '204', '2020-10-12 12:12:37', '2020-10-12 12:12:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('212', '82', '205', '2020-10-12 12:15:48', '2020-10-12 12:15:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('213', '78', '206', '2020-10-13 15:04:45', '2020-10-13 15:04:45');
INSERT INTO `tb_menu_goods_relation` VALUES ('214', '77', '207', '2020-10-13 15:07:14', '2020-10-13 15:07:14');
INSERT INTO `tb_menu_goods_relation` VALUES ('215', '77', '208', '2020-10-13 15:11:09', '2020-10-13 15:11:09');
INSERT INTO `tb_menu_goods_relation` VALUES ('216', '77', '209', '2020-10-13 15:19:48', '2020-10-13 15:19:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('217', '82', '210', '2020-10-13 15:23:42', '2020-10-13 15:23:42');
INSERT INTO `tb_menu_goods_relation` VALUES ('218', '78', '211', '2020-10-13 15:26:16', '2020-10-13 15:26:16');
INSERT INTO `tb_menu_goods_relation` VALUES ('219', '78', '212', '2020-10-13 15:29:04', '2020-10-13 15:29:04');
INSERT INTO `tb_menu_goods_relation` VALUES ('220', '78', '213', '2020-10-13 15:31:29', '2020-10-13 15:31:29');
INSERT INTO `tb_menu_goods_relation` VALUES ('221', '78', '214', '2020-10-13 15:35:08', '2020-10-13 15:35:08');
INSERT INTO `tb_menu_goods_relation` VALUES ('222', '78', '215', '2020-10-13 15:38:08', '2020-10-13 15:38:08');
INSERT INTO `tb_menu_goods_relation` VALUES ('223', '82', '216', '2020-10-13 15:41:15', '2020-10-13 15:41:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('224', '81', '217', '2020-10-13 15:44:47', '2020-10-13 15:44:47');
INSERT INTO `tb_menu_goods_relation` VALUES ('225', '78', '218', '2020-10-13 15:51:47', '2020-10-13 15:51:47');
INSERT INTO `tb_menu_goods_relation` VALUES ('226', '79', '219', '2020-10-13 16:02:15', '2020-10-13 16:02:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('227', '78', '220', '2020-10-13 16:04:51', '2020-10-13 16:04:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('228', '82', '221', '2020-10-13 16:16:09', '2020-10-13 16:16:09');
INSERT INTO `tb_menu_goods_relation` VALUES ('229', '83', '222', '2020-10-13 16:20:20', '2020-10-13 16:20:20');
INSERT INTO `tb_menu_goods_relation` VALUES ('230', '77', '223', '2020-10-13 16:23:25', '2020-10-13 16:23:25');
INSERT INTO `tb_menu_goods_relation` VALUES ('231', '79', '224', '2020-10-15 23:36:02', '2020-10-15 23:36:02');
INSERT INTO `tb_menu_goods_relation` VALUES ('232', '82', '225', '2020-10-15 23:41:37', '2020-10-15 23:41:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('233', '81', '226', '2020-10-16 00:23:21', '2020-10-16 00:23:21');
INSERT INTO `tb_menu_goods_relation` VALUES ('234', '82', '227', '2020-10-21 01:58:07', '2020-10-21 01:58:07');
INSERT INTO `tb_menu_goods_relation` VALUES ('235', '82', '228', '2020-10-21 02:00:43', '2020-10-21 02:00:43');
INSERT INTO `tb_menu_goods_relation` VALUES ('236', '84', '229', '2020-10-21 12:43:40', '2020-10-21 12:43:40');
INSERT INTO `tb_menu_goods_relation` VALUES ('237', '84', '230', '2020-10-21 12:46:51', '2020-10-21 12:46:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('238', '79', '231', '2020-10-21 12:56:30', '2021-06-28 22:32:25');
INSERT INTO `tb_menu_goods_relation` VALUES ('239', '76', '232', '2020-10-21 13:06:13', '2020-10-21 13:06:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('240', '76', '233', '2020-10-21 13:09:13', '2020-10-21 13:09:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('241', '76', '234', '2020-10-21 13:11:05', '2020-10-21 13:11:05');
INSERT INTO `tb_menu_goods_relation` VALUES ('242', '80', '235', '2020-10-21 13:19:46', '2020-10-21 13:19:46');
INSERT INTO `tb_menu_goods_relation` VALUES ('244', '96', '237', '2021-03-15 14:33:33', '2021-03-15 14:33:33');
INSERT INTO `tb_menu_goods_relation` VALUES ('246', '96', '239', '2021-04-12 21:53:15', '2021-04-12 21:53:15');
INSERT INTO `tb_menu_goods_relation` VALUES ('247', '96', '240', '2021-04-12 22:01:33', '2021-04-12 22:01:33');
INSERT INTO `tb_menu_goods_relation` VALUES ('248', '96', '241', '2021-04-25 19:02:49', '2021-04-25 19:02:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('249', '96', '242', '2021-04-25 19:08:10', '2021-04-25 19:08:10');
INSERT INTO `tb_menu_goods_relation` VALUES ('250', '96', '243', '2021-04-25 19:10:48', '2021-04-25 19:10:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('251', '96', '244', '2021-04-25 19:16:20', '2021-04-25 19:16:20');
INSERT INTO `tb_menu_goods_relation` VALUES ('252', '96', '245', '2021-04-25 19:36:30', '2021-04-25 19:36:30');
INSERT INTO `tb_menu_goods_relation` VALUES ('253', '91', '246', '2021-04-25 19:40:48', '2021-04-25 19:40:48');
INSERT INTO `tb_menu_goods_relation` VALUES ('254', '91', '247', '2021-04-25 19:54:34', '2021-04-25 19:54:34');
INSERT INTO `tb_menu_goods_relation` VALUES ('255', '94', '248', '2021-04-25 19:57:54', '2021-04-25 19:57:54');
INSERT INTO `tb_menu_goods_relation` VALUES ('256', '94', '249', '2021-04-25 19:59:49', '2021-04-25 19:59:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('257', '94', '250', '2021-04-25 20:04:49', '2021-04-25 20:04:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('258', '94', '251', '2021-04-25 20:20:22', '2021-04-25 20:20:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('259', '94', '252', '2021-04-25 20:35:44', '2021-04-25 20:35:44');
INSERT INTO `tb_menu_goods_relation` VALUES ('260', '94', '253', '2021-04-25 20:38:26', '2021-04-25 20:38:26');
INSERT INTO `tb_menu_goods_relation` VALUES ('261', '94', '254', '2021-04-25 20:41:22', '2021-04-25 20:41:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('262', '93', '255', '2021-04-25 20:58:56', '2021-04-25 23:28:45');
INSERT INTO `tb_menu_goods_relation` VALUES ('263', '93', '256', '2021-04-25 21:47:35', '2021-04-25 23:28:38');
INSERT INTO `tb_menu_goods_relation` VALUES ('264', '92', '257', '2021-04-25 21:59:20', '2021-04-25 22:32:31');
INSERT INTO `tb_menu_goods_relation` VALUES ('265', '92', '258', '2021-04-25 22:01:06', '2021-04-25 22:32:18');
INSERT INTO `tb_menu_goods_relation` VALUES ('266', '92', '259', '2021-04-25 22:32:10', '2021-04-25 22:32:10');
INSERT INTO `tb_menu_goods_relation` VALUES ('267', '95', '260', '2021-04-25 23:25:51', '2021-04-25 23:25:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('268', '26', '261', '2021-06-03 23:21:50', '2021-06-03 23:21:50');
INSERT INTO `tb_menu_goods_relation` VALUES ('269', '81', '262', '2021-06-27 11:30:50', '2021-06-27 11:30:50');
INSERT INTO `tb_menu_goods_relation` VALUES ('270', '82', '263', '2021-06-28 22:03:47', '2021-06-28 22:03:47');
INSERT INTO `tb_menu_goods_relation` VALUES ('271', '84', '264', '2021-06-28 22:07:11', '2021-06-28 22:11:34');
INSERT INTO `tb_menu_goods_relation` VALUES ('272', '84', '265', '2021-06-28 22:10:46', '2021-06-28 22:11:29');
INSERT INTO `tb_menu_goods_relation` VALUES ('273', '84', '266', '2021-06-28 22:15:22', '2021-06-28 22:15:22');
INSERT INTO `tb_menu_goods_relation` VALUES ('274', '79', '267', '2021-06-28 22:31:24', '2021-06-28 22:31:24');
INSERT INTO `tb_menu_goods_relation` VALUES ('275', '79', '268', '2021-06-28 22:34:37', '2021-06-28 22:34:37');
INSERT INTO `tb_menu_goods_relation` VALUES ('276', '81', '269', '2021-07-01 11:47:43', '2021-07-01 11:47:43');
INSERT INTO `tb_menu_goods_relation` VALUES ('277', '78', '270', '2021-07-01 11:55:32', '2021-07-01 11:55:32');
INSERT INTO `tb_menu_goods_relation` VALUES ('278', '78', '271', '2021-07-01 13:01:06', '2021-07-01 13:01:06');
INSERT INTO `tb_menu_goods_relation` VALUES ('279', '78', '272', '2021-07-01 18:14:10', '2021-07-01 18:14:10');
INSERT INTO `tb_menu_goods_relation` VALUES ('280', '76', '273', '2021-07-01 18:22:13', '2021-07-01 18:22:13');
INSERT INTO `tb_menu_goods_relation` VALUES ('281', '80', '274', '2021-07-01 18:25:29', '2021-07-01 18:25:29');
INSERT INTO `tb_menu_goods_relation` VALUES ('282', '97', '275', '2021-07-01 21:34:24', '2021-07-01 21:34:24');
INSERT INTO `tb_menu_goods_relation` VALUES ('283', '97', '276', '2021-07-01 21:35:51', '2021-07-01 21:35:51');
INSERT INTO `tb_menu_goods_relation` VALUES ('284', '97', '277', '2021-07-01 21:43:49', '2021-07-01 21:43:49');
INSERT INTO `tb_menu_goods_relation` VALUES ('285', '97', '278', '2021-07-01 21:46:00', '2021-07-01 21:46:00');
INSERT INTO `tb_menu_goods_relation` VALUES ('286', '97', '279', '2021-07-01 21:47:41', '2021-07-01 21:47:41');
INSERT INTO `tb_menu_goods_relation` VALUES ('287', '97', '280', '2021-07-01 21:50:21', '2021-07-01 21:50:21');

-- ----------------------------
-- Table structure for tb_merchant
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant`;
CREATE TABLE `tb_merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '门店信息id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `PASSWORD` varchar(100) DEFAULT NULL COMMENT '用户密码',
  `password_salt` varchar(100) DEFAULT NULL COMMENT '密码加盐',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `roles` varchar(100) DEFAULT NULL COMMENT '权限',
  `head_img` varchar(256) DEFAULT NULL COMMENT '头像',
  `sex` int(2) DEFAULT NULL COMMENT '性别',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `certificate_type` varchar(50) DEFAULT '身份证' COMMENT '证件类型',
  `certificate_img` varchar(50) DEFAULT NULL COMMENT '证件照片',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 0=启用 1=禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=删除',
  `audit_status` int(2) DEFAULT '1' COMMENT '审批状态（1=审核中，2=审核成功，3=审核失败）',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证号码',
  `opening_bank_address` varchar(50) DEFAULT NULL COMMENT '开户行',
  `opening_bank_name` varchar(50) DEFAULT NULL COMMENT '开户银行名称',
  `bank_card` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `alipay_account` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `wechat_account` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '余额',
  `withdrawable_balance` decimal(10,2) DEFAULT '0.00' COMMENT '可提现余额',
  `frozen_balance` decimal(10,2) DEFAULT '0.00' COMMENT '冻结余额',
  `order_frozen_balance` decimal(10,2) DEFAULT '0.00' COMMENT '用户下单冻结资金',
  `member_id` int(11) DEFAULT NULL COMMENT '绑定的小程序用户id(用于发送微信公众号消息、进行商家端余额提现)',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='商家账号表';

-- ----------------------------
-- Records of tb_merchant
-- ----------------------------
INSERT INTO `tb_merchant` VALUES ('18', '19', '', '13121860001', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', '', '', '', null, '', '身份证', '', '0', '0', '2', '', '', '1', '1', '', '', '', '0.00', '0.00', '0.00', '0.00', '41', '2020-09-15 17:05:56', '2021-04-20 00:15:00');
INSERT INTO `tb_merchant` VALUES ('19', '14', null, '13121860002', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '3', '', '', null, null, '', '', '', '0.00', '0.00', '0.00', '0.00', null, '2020-09-16 16:57:03', '2021-05-18 11:38:16');
INSERT INTO `tb_merchant` VALUES ('20', '15', null, '13121860003', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '2', '', '', null, null, '', '', '', '17.70', '17.70', '0.00', '0.00', '1190', '2020-09-22 22:11:51', '2020-10-28 00:15:00');
INSERT INTO `tb_merchant` VALUES ('21', '16', '', '13121860004', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', '', '', '', null, '', '身份证', '', '0', '0', '2', '', '', '', '', '', '', '', '0.93', '0.93', '0.00', '0.00', '1280', '2020-10-08 16:19:37', '2020-10-26 16:10:18');
INSERT INTO `tb_merchant` VALUES ('22', '17', null, '13121860005', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '2', '', '', null, null, '', '', '', '0.00', '0.00', '0.00', '0.00', '1287', '2020-10-09 10:02:53', '2020-10-09 10:06:53');
INSERT INTO `tb_merchant` VALUES ('23', '18', '', '13121860006', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', '', '', '', null, '', '身份证', '', '0', '0', '2', '', '', '', '', '', '', '', '0.99', '0.99', '0.00', '0.00', '1283', '2020-10-11 19:04:31', '2021-06-27 11:10:17');
INSERT INTO `tb_merchant` VALUES ('24', '13', 'siam', '13121865386', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '2', '1', '1', '1', '1', '1', '1', '1', '0.00', '0.00', '0.00', '0.00', null, '2021-02-21 14:57:02', '2021-02-24 15:38:00');
INSERT INTO `tb_merchant` VALUES ('39', '34', '1', '13121860007', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '1', '', '', null, null, '', '', '', '0.00', '0.00', '0.00', '0.00', null, '2021-10-21 14:52:10', '2021-10-21 14:52:10');
INSERT INTO `tb_merchant` VALUES ('40', '35', '1', '13121860008', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '1', '', '', null, null, '', '', '', '0.00', '0.00', '0.00', '0.00', null, '2021-10-21 16:06:14', '2021-10-21 16:06:14');
INSERT INTO `tb_merchant` VALUES ('41', '36', '1', '13121860009', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '1', '', '', null, null, '', '', '', '0.00', '0.00', '0.00', '0.00', null, '2021-10-21 16:44:12', '2021-10-21 16:44:12');
INSERT INTO `tb_merchant` VALUES ('42', '13', 'admin-ludian', '13121865386', 'e9cb2c139f656e6f7b0745dd1e4e8c84', '9f7986235b06419fbabf50d9f29fba6a', null, null, null, null, null, '身份证', '', '0', '0', '2', '1', '1', '1', '1', '1', '1', '1', '0.00', '0.00', '0.00', '0.00', null, '2021-02-21 14:57:02', '2021-02-24 15:38:00');

-- ----------------------------
-- Table structure for tb_merchant_billing_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant_billing_record`;
CREATE TABLE `tb_merchant_billing_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` int(11) NOT NULL COMMENT '商家账号id',
  `type` int(2) NOT NULL COMMENT '账单类型 1=用户下单 2=商家提现 3=商家提现失败退回 4=自取订单改为配送，收入减少 5=自取订单改为配送，收入增加 6=商家自配送-配送费收入 7=用户一分钟内取消订单 8=用户一分钟内取消订单-配送费退回 9=用户申请退款 10-用户申请退款-配送费退回',
  `operate_type` int(2) NOT NULL COMMENT '操作类型 1=加 2=减',
  `coin_type` int(2) NOT NULL COMMENT '货币类型 1=余额',
  `number` decimal(10,2) NOT NULL COMMENT '增减的数值',
  `service_fee` decimal(10,2) DEFAULT NULL COMMENT '服务费',
  `message` varchar(200) DEFAULT NULL COMMENT '账单信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8 COMMENT='商家账单记录表';

-- ----------------------------
-- Records of tb_merchant_billing_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_merchant_recommend_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant_recommend_goods`;
CREATE TABLE `tb_merchant_recommend_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='商家推荐商品表';

-- ----------------------------
-- Records of tb_merchant_recommend_goods
-- ----------------------------
INSERT INTO `tb_merchant_recommend_goods` VALUES ('23', '16', '139', '1', '2020-10-11 09:53:34', '2020-10-11 09:53:34');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('24', '16', '142', '1', '2020-10-11 09:53:34', '2020-10-11 09:53:34');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('25', '16', '145', '1', '2020-10-11 09:53:34', '2020-10-11 09:53:34');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('26', '15', '195', '1', '2020-10-14 02:36:52', '2020-10-14 02:36:52');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('27', '15', '197', '1', '2020-10-14 02:36:52', '2020-10-14 02:36:52');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('28', '15', '100', '1', '2020-10-14 02:36:52', '2020-10-14 02:36:52');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('35', '18', '272', '1', '2021-07-01 18:40:58', '2021-07-01 18:40:58');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('36', '18', '268', '1', '2021-07-01 18:40:58', '2021-07-01 18:40:58');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('37', '18', '267', '1', '2021-07-01 18:40:58', '2021-07-01 18:40:58');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('38', '18', '264', '1', '2021-07-01 18:40:58', '2021-07-01 18:40:58');
INSERT INTO `tb_merchant_recommend_goods` VALUES ('42', '19', '260', '1', '2023-11-15 13:05:43', '2023-11-15 13:05:43');

-- ----------------------------
-- Table structure for tb_merchant_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant_token`;
CREATE TABLE `tb_merchant_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` int(11) NOT NULL COMMENT '商家id',
  `username` varchar(50) DEFAULT NULL COMMENT '商家用户名',
  `token` varchar(128) NOT NULL COMMENT '登录令牌',
  `type` varchar(5) DEFAULT NULL COMMENT '登录方式 wap',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 COMMENT='商家登录token令牌表';

-- ----------------------------
-- Records of tb_merchant_token
-- ----------------------------

-- ----------------------------
-- Table structure for tb_merchant_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant_trade_record`;
CREATE TABLE `tb_merchant_trade_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` int(11) NOT NULL COMMENT '商家账号id',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '交易单号(支付平台的订单号)',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '商户单号(网站平台的订单号)',
  `type` int(2) DEFAULT '1' COMMENT '交易类型 1=商家提现',
  `payment_mode` int(2) DEFAULT NULL COMMENT '支付方式 1=微信 2=支付宝 3=平台余额 4=网银 5=银行转账',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `status` int(2) DEFAULT '1' COMMENT '交易状态 1=待支付 2=支付成功 3=支付失败 3=交易超时自动关闭',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='(废弃表)商家交易记录表';

-- ----------------------------
-- Records of tb_merchant_trade_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_merchant_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant_withdraw_record`;
CREATE TABLE `tb_merchant_withdraw_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` int(11) NOT NULL COMMENT '商家账号id',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号，供客户查询',
  `withdraw_amount` decimal(10,2) NOT NULL COMMENT '提现金额',
  `platform_fee` decimal(10,2) NOT NULL COMMENT '平台手续费/服务费',
  `actual_amount` decimal(10,2) DEFAULT NULL COMMENT '实际到账金额',
  `audit_status` int(2) DEFAULT '1' COMMENT '审核状态 1=平台处理中 2=到账成功 3=审核不通过',
  `audit_reason` varchar(50) DEFAULT NULL COMMENT '审核不通过原因',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `payment_mode` int(2) DEFAULT NULL COMMENT '打款方式/到账方式 1=微信 2=支付宝 3=银行',
  `opening_bank_address` varchar(50) DEFAULT NULL COMMENT '开户行',
  `opening_bank_name` varchar(50) DEFAULT NULL COMMENT '开户银行名称',
  `bank_card` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `alipay_account` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `wechat_account` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `merchant_trade_record_id` int(11) DEFAULT NULL COMMENT '商家交易记录id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商家提现记录表';

-- ----------------------------
-- Records of tb_merchant_withdraw_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_type` int(2) NOT NULL COMMENT '消息用户类型 1=用户 2=商家 3=管理员',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读 0=未读 1=已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3690 DEFAULT CHARSET=utf8 COMMENT='系统消息表';

-- ----------------------------
-- Records of tb_message
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号，供客户查询',
  `goods_total_quantity` int(11) DEFAULT '0' COMMENT '商品总数量',
  `goods_total_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品总金额',
  `packing_charges` decimal(10,2) DEFAULT NULL COMMENT '包装费',
  `delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '运费/配送费',
  `actual_price` decimal(10,2) DEFAULT '0.00' COMMENT '实付款',
  `shopping_way` int(2) NOT NULL COMMENT '购物方式 1=自取 2=配送',
  `delivery_address_id` int(11) DEFAULT NULL COMMENT '收货地址id',
  `contact_realname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `contact_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `contact_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `contact_area` varchar(100) DEFAULT NULL COMMENT '区/县',
  `contact_street` varchar(100) DEFAULT NULL COMMENT '详细地址(具体到街道门牌号)',
  `contact_sex` int(1) DEFAULT '0' COMMENT '联系人性别 0=无 1=先生 2=女士',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `description` varchar(2048) DEFAULT NULL COMMENT '订单描述',
  `status` int(2) DEFAULT '1' COMMENT '订单状态 1=未付款 2=待处理 3=待自取(已处理) 4=待配送(已处理) 5=已配送 6=已完成 7=售后处理中 8=已退款(废弃选项) 9=售后处理完成 10=已取消(未支付) 11=已取消(已支付)',
  `trade_id` int(11) DEFAULT NULL COMMENT '用户交易id',
  `order_logistics_id` int(11) DEFAULT NULL COMMENT '物流id',
  `is_invoice` tinyint(1) DEFAULT '0' COMMENT '是否开票',
  `invoice_id` int(11) DEFAULT NULL COMMENT '发票id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=删除',
  `shop_id` int(11) NOT NULL COMMENT '接单门店id',
  `shop_name` varchar(100) NOT NULL COMMENT '接单门店名称',
  `shop_address` varchar(200) DEFAULT NULL COMMENT '门店地址',
  `cancel_reason` int(2) DEFAULT NULL COMMENT '(未支付)订单取消原因 1=您主动取消 2=订单超时未支付 3=系统异常',
  `payment_deadline` datetime DEFAULT NULL COMMENT '支付截止时间(五分钟内未付款的订单会被自动关闭)',
  `is_printed` tinyint(1) DEFAULT '0' COMMENT '是否已打印',
  `queue_no` int(11) DEFAULT NULL COMMENT '取餐号',
  `full_reduction_rule_id` int(11) DEFAULT NULL COMMENT '使用的满减id',
  `full_reduction_rule_description` varchar(100) DEFAULT NULL COMMENT '使用的满减规则描述',
  `coupons_id` int(11) DEFAULT NULL COMMENT '使用的优惠卷id',
  `coupons_description` varchar(100) DEFAULT NULL COMMENT '使用的优惠卷描述',
  `coupons_member_relation_id` int(11) DEFAULT NULL COMMENT '优惠卷用户关系id',
  `is_change_to_delivery` tinyint(1) DEFAULT '0' COMMENT '是否由自取订单改为配送 0=否 1=是',
  `change_to_delivery_out_trade_no` varchar(50) DEFAULT NULL COMMENT '自取订单改为配送的商户单号',
  `change_to_delivery_trade_id` int(11) DEFAULT NULL COMMENT '自取订单改为配送的用户交易id',
  `platform_extract_ratio` decimal(10,2) DEFAULT '0.00' COMMENT '平台抽取比例(%)',
  `platform_extract_price` decimal(10,2) DEFAULT '0.00' COMMENT '平台抽取金额',
  `platform_delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '平台承担配送费',
  `platform_income` decimal(10,2) DEFAULT '0.00' COMMENT '平台实际收入',
  `merchant_delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '商家承担配送费',
  `courier_income` decimal(10,2) DEFAULT '0.00' COMMENT '骑手实际收入',
  `merchant_income` decimal(10,2) DEFAULT '0.00' COMMENT '商家实际收入',
  `payment_success_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `order_completion_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `paid_order_cancel_reason` int(2) DEFAULT NULL COMMENT '(废弃字段)(已支付)订单取消原因 1=信息填写错误 2=送达时间选错了 3=买错了/买少了 4=商家缺货 5=商家联系我取消 6=配送太慢 7=骑手联系我取消 8=我不想要了',
  `limited_price` decimal(10,2) DEFAULT '0.00' COMMENT '满足价格(元)',
  `reduced_price` decimal(10,2) DEFAULT '0.00' COMMENT '减价额度(元)',
  `coupons_discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券折扣金额/优惠券优惠金额',
  `delivery_way` int(2) DEFAULT NULL COMMENT '配送方式 1=商家自配送',
  `is_pay_to_merchant` tinyint(1) DEFAULT '0' COMMENT '用户下单资金是否已转入商家余额 0=否 1=是',
  `before_reduced_delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '立减之前需要支付的运费/配送费',
  `payment_mode` int(2) DEFAULT NULL COMMENT '支付方式 1=微信支付 2=平台余额',
  `contact_house_number` varchar(100) DEFAULT NULL COMMENT '门牌号',
  `contact_longitude` decimal(18,6) DEFAULT NULL COMMENT '经度',
  `contact_latitude` decimal(18,6) DEFAULT NULL COMMENT '纬度',
  `shop_logo_img` varchar(128) DEFAULT NULL COMMENT '接单门店logo',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3556 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of tb_order
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(11) NOT NULL COMMENT '订单id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `main_image` varchar(128) NOT NULL COMMENT '商品主图',
  `spec_list` varchar(1024) DEFAULT NULL COMMENT '商品规格 JSON数据',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `number` int(11) DEFAULT '0' COMMENT '购买数量',
  `subtotal` decimal(10,2) DEFAULT '0.00' COMMENT '小计',
  `packing_charges` decimal(10,2) DEFAULT '0.00' COMMENT '单件商品的包装费',
  `is_used_coupons` tinyint(1) DEFAULT '0' COMMENT '该商品是否使用了优惠券 0=否 1=是',
  `coupons_discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券折扣金额/优惠券优惠金额',
  `after_coupons_discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券折扣之后的商品价格',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4531 DEFAULT CHARSET=utf8 COMMENT='订单商品详情表';

-- ----------------------------
-- Records of tb_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_refund
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_refund`;
CREATE TABLE `tb_order_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(11) NOT NULL COMMENT '订单id',
  `type` int(2) DEFAULT '1' COMMENT '退款类型 1=已支付订单1分钟内被取消 2=已支付订单24小时内申请退款',
  `refund_way` int(2) DEFAULT '1' COMMENT '退款方式 1=全额退款 2=部分退款',
  `refund_reason` int(2) DEFAULT NULL COMMENT '退款原因 1=信息填写错误 2=送达时间选错了 3=买错了/买少了 4=商家缺货 5=商家联系我取消 6=配送太慢 7=骑手联系我取消 8=我不想要了 9=商家通知我卖完了 10=商家沟通态度差 11=骑手沟通态度差 12=送太慢了，等太久了 13=商品撒漏/包装破损 14=商家少送商品 15=商家送错商品 16=口味不佳/个人感受不好 17=餐品内有异物 18=食用后引起身体不适 19=商品变质/有异味 20=其他',
  `refund_reason_description` varchar(1024) DEFAULT NULL COMMENT '退款原因详细描述',
  `evidence_images` varchar(1024) DEFAULT NULL COMMENT '凭证图片',
  `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '退款金额',
  `refund_account` int(2) DEFAULT NULL COMMENT '退款账户 1=微信 2=支付宝 3=平台余额',
  `status` int(2) DEFAULT '1' COMMENT '退款状态 1=退款申请已提交 2=等待商家处理 3=商家拒绝退款 4=等待平台处理 5=平台拒绝退款，退款已关闭 6=退款已关闭 7=退款成功',
  `goods_total_quantity` int(11) DEFAULT '0' COMMENT '退还商品总数量',
  `goods_total_price` decimal(10,2) DEFAULT '0.00' COMMENT '退还商品总金额',
  `packing_charges` decimal(10,2) DEFAULT '0.00' COMMENT '退还包装费',
  `is_refund_delivery_fee` tinyint(1) DEFAULT '0' COMMENT '是否退还了配送费 0=否 1=是',
  `delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '退还运费/配送费',
  `is_used_coupons` tinyint(1) DEFAULT '0' COMMENT '退款商品中是否使用了优惠券 0=否 1=是',
  `is_used_full_reduction_rule` tinyint(1) DEFAULT '0' COMMENT '退款商品中是否使用了满减规则 0=否 1=是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='订单退款表';

-- ----------------------------
-- Records of tb_order_refund
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_refund_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_refund_goods`;
CREATE TABLE `tb_order_refund_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_refund_id` int(11) NOT NULL COMMENT '订单退款id',
  `order_detail_id` int(11) NOT NULL COMMENT '订单商品详情id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `main_image` varchar(128) NOT NULL COMMENT '商品主图',
  `spec_list` varchar(1024) DEFAULT NULL COMMENT '商品规格 JSON数据',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `number` int(11) DEFAULT '0' COMMENT '退款数量',
  `subtotal` decimal(10,2) DEFAULT '0.00' COMMENT '小计',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='订单退款-商品详情表';

-- ----------------------------
-- Records of tb_order_refund_goods
-- ----------------------------

-- ----------------------------
-- Table structure for tb_order_refund_process
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_refund_process`;
CREATE TABLE `tb_order_refund_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_refund_id` int(11) NOT NULL COMMENT '订单退款id',
  `name` varchar(50) NOT NULL COMMENT '流程名称',
  `description` varchar(200) DEFAULT NULL COMMENT '流程描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='订单退款流程表';

-- ----------------------------
-- Records of tb_order_refund_process
-- ----------------------------

-- ----------------------------
-- Table structure for tb_paperwork_push
-- ----------------------------
DROP TABLE IF EXISTS `tb_paperwork_push`;
CREATE TABLE `tb_paperwork_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) NOT NULL COMMENT '文案名称',
  `content` text COMMENT '文案内容',
  `pushed_number` int(11) DEFAULT '0' COMMENT '已推送次数',
  `last_pushed_time` datetime DEFAULT NULL COMMENT '上次推送时间',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_paperwork_push
-- ----------------------------

-- ----------------------------
-- Table structure for tb_picture_upload_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_picture_upload_record`;
CREATE TABLE `tb_picture_upload_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `url` varchar(128) DEFAULT NULL COMMENT '图片地址(相对路径)',
  `module` int(2) DEFAULT NULL COMMENT '所属模块 1=商品模块',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_picture_upload_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_coupons
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_coupons`;
CREATE TABLE `tb_points_mall_coupons` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) DEFAULT NULL COMMENT '优惠卷名称',
  `preferential_type` int(11) DEFAULT NULL COMMENT '优惠类型，1=折扣，2=满减',
  `discount_amount` decimal(10,2) DEFAULT '1.00' COMMENT '折扣额度',
  `limited_price` decimal(10,2) DEFAULT '0.00' COMMENT '满足价格（元，满足该价格才能使用）',
  `reduced_price` decimal(10,2) DEFAULT '0.00' COMMENT '减价额度(元)',
  `description` varchar(500) DEFAULT NULL COMMENT '使用规则描述',
  `valid_type` int(1) NOT NULL DEFAULT '2' COMMENT '时效:1绝对时效（领取后XXX-XXX时间段有效）  2相对时效（领取后N天有效）',
  `valid_start_time` datetime DEFAULT NULL COMMENT '使用开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '使用结束时间',
  `valid_days` int(3) NOT NULL DEFAULT '0' COMMENT '自领取之日起有效天数',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否被删除，0-否，1-是',
  `source` int(2) DEFAULT NULL COMMENT '优惠券发放来源 1=商家中心 2=调度中心',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_coupons
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_coupons_goods_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_coupons_goods_relation`;
CREATE TABLE `tb_points_mall_coupons_goods_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupons_id` int(11) DEFAULT NULL COMMENT '优惠卷id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_coupons_goods_relation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_coupons_member_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_coupons_member_relation`;
CREATE TABLE `tb_points_mall_coupons_member_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupons_id` int(11) DEFAULT NULL COMMENT '优惠卷id',
  `coupons_name` varchar(100) DEFAULT NULL COMMENT '优惠卷名称',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `start_time` datetime DEFAULT NULL COMMENT '生效时间',
  `end_time` datetime DEFAULT NULL COMMENT '过期时间',
  `is_used` tinyint(1) DEFAULT '0' COMMENT '是否已经使用，0=未使用，1=已使用',
  `is_expired` tinyint(1) DEFAULT '0' COMMENT '是否过期，0=未过期，1=已过期',
  `is_valid` tinyint(1) DEFAULT '1' COMMENT '是否有效，0-否，1-是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1318 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_coupons_member_relation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_full_reduction_rule
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_full_reduction_rule`;
CREATE TABLE `tb_points_mall_full_reduction_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(100) DEFAULT NULL COMMENT '规则名称',
  `status` int(11) DEFAULT NULL COMMENT '活动状态，1=开启，2=关闭',
  `limited_price` decimal(10,2) DEFAULT '0.00' COMMENT '满足价格（元，满足该价格才能使用）',
  `reduced_price` decimal(10,2) DEFAULT '0.00' COMMENT '减价额度(元)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_full_reduction_rule
-- ----------------------------
INSERT INTO `tb_points_mall_full_reduction_rule` VALUES ('2', null, '满199减99', '1', '5000.00', '4999.00', '2021-04-20 23:44:45', null);

-- ----------------------------
-- Table structure for tb_points_mall_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_goods`;
CREATE TABLE `tb_points_mall_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(50) NOT NULL COMMENT '商品名称',
  `main_image` varchar(128) DEFAULT NULL COMMENT '商品主图',
  `sub_images` varchar(1024) DEFAULT NULL COMMENT '商品子图',
  `detail` varchar(1024) DEFAULT NULL COMMENT '商品详情',
  `detail_images` varchar(1024) DEFAULT NULL COMMENT '详情图片',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '一口价',
  `stock` int(11) DEFAULT '0' COMMENT '库存',
  `is_hot` tinyint(1) DEFAULT '0' COMMENT '是否热门',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否新品',
  `status` int(2) DEFAULT '1' COMMENT '状态 1=待上架 2=已上架 3=已下架 4=售罄',
  `is_sale` tinyint(1) unsigned DEFAULT '0' COMMENT '是否开启促销 0-否 1-是',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '折扣价',
  `monthly_sales` int(11) DEFAULT '0' COMMENT '月销量',
  `total_sales` int(11) DEFAULT '0' COMMENT '累计销量',
  `total_comments` int(11) DEFAULT '0' COMMENT '累计评价',
  `preferential_name` varchar(20) DEFAULT NULL COMMENT '优惠名称',
  `packing_charges` decimal(10,2) DEFAULT '0.00' COMMENT '包装费',
  `product_time` decimal(10,2) DEFAULT '0.00' COMMENT '制作时长(分钟)',
  `exchange_points` int(10) DEFAULT NULL COMMENT '兑换商品所需积分数量',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_goods
-- ----------------------------
INSERT INTO `tb_points_mall_goods` VALUES ('1', null, '米诗萱雾面丝绒唇釉大理石哑光染唇液不易脱色持久保湿防水口红', 'data/images/admin/1/deerspot_1615385157622.png', 'data/images/admin/1/deerspot_1615385157622.png,data/images/admin/1/deerspot_1616429825944.png', null, 'data/images/admin/1/deerspot_1617105399351.png,data/images/admin/1/deerspot_1617105401513.png,data/images/admin/1/deerspot_1617105409406.png,data/images/admin/1/deerspot_1617105544308.jpg,data/images/admin/1/deerspot_1617121868742.png', '78.00', '12', '0', '0', '2', '0', '0.00', '34', '34', '0', null, '0.00', '0.00', '78', null, '2021-03-10 22:06:10', '2021-04-25 16:14:22');
INSERT INTO `tb_points_mall_goods` VALUES ('6', null, '米诗萱水漾雪肌光感素颜霜隔离滋养润泽保湿亮肤打底神器懒人霜', 'data/images/admin/1/deerspot_1618927816555.png', 'data/images/admin/1/deerspot_1618927816555.png', null, 'data/images/admin/1/deerspot_1618927673227.png,data/images/admin/1/deerspot_1618927711315.png,data/images/admin/1/deerspot_1618927714000.png,data/images/admin/1/deerspot_1618927716731.png,data/images/admin/1/deerspot_1618927721803.png', '98.00', '3', '0', '0', '2', '0', '0.00', '55', '55', '0', null, '0.00', '0.00', '98', null, '2021-04-20 22:11:00', '2021-04-25 16:14:07');
INSERT INTO `tb_points_mall_goods` VALUES ('7', null, '舒缓新肌焕颜乳', 'data/images/admin/1/deerspot_1618928297521.png', 'data/images/admin/1/deerspot_1618928297521.png', null, 'data/images/admin/1/deerspot_1623679552226.jpg,data/images/admin/1/deerspot_1623679556825.jpg,data/images/admin/1/deerspot_1623679560788.jpg,data/images/admin/1/deerspot_1623679565163.jpg,data/images/admin/1/deerspot_1623679570692.jpg,data/images/admin/1/deerspot_1623679575262.jpg', '138.00', '2', '0', '0', '2', '0', '0.00', '9', '9', '0', null, '0.00', '0.00', '138', null, '2021-04-20 22:23:17', '2021-06-14 22:07:08');
INSERT INTO `tb_points_mall_goods` VALUES ('8', null, '积雪草舒缓滋养喷雾', 'data/images/admin/1/deerspot_1623675506772.jpg', 'data/images/admin/1/deerspot_1623675506772.jpg', null, 'data/images/admin/1/deerspot_1623675611465.png,data/images/admin/1/deerspot_1623675617628.jpg,data/images/admin/1/deerspot_1623675619883.jpg,data/images/admin/1/deerspot_1623675637120.jpg,data/images/admin/1/deerspot_1623675639403.jpg,data/images/admin/1/deerspot_1623675641720.jpg', '138.00', '2', '0', '0', '2', '0', '0.00', '5', '5', '0', null, '0.00', '0.00', '138', null, '2021-04-20 22:36:05', '2021-06-14 21:00:45');
INSERT INTO `tb_points_mall_goods` VALUES ('9', null, '焕彩活肤能量水', 'data/images/admin/1/deerspot_1623679829685.jpg', 'data/images/admin/1/deerspot_1623679829685.jpg', null, 'data/images/admin/1/deerspot_1623680041668.jpg,data/images/admin/1/deerspot_1623680045897.jpg,data/images/admin/1/deerspot_1623680048606.jpg,data/images/admin/1/deerspot_1623680051338.jpg,data/images/admin/1/deerspot_1623680053889.jpg,data/images/admin/1/deerspot_1623680056132.jpg', '138.00', '1', '0', '0', '2', '0', '0.00', '3', '3', '0', null, '0.00', '0.00', '138', null, '2021-04-20 22:54:02', '2021-06-14 22:14:17');
INSERT INTO `tb_points_mall_goods` VALUES ('10', null, '米诗萱素颜柔肤洗卸凝露一瓶双用干洗卸妆湿洗深层清洁不油腻', 'data/images/admin/1/deerspot_1618931788410.png', 'data/images/admin/1/deerspot_1618931788410.png', null, 'data/images/admin/1/deerspot_1618932364284.png,data/images/admin/1/deerspot_1618932382794.png,data/images/admin/1/deerspot_1618932385852.png,data/images/admin/1/deerspot_1618932389826.png,data/images/admin/1/deerspot_1618932392795.png', '98.00', '1', '0', '0', '2', '0', '0.00', '1', '1', '0', null, '0.00', '0.00', '98', null, '2021-04-20 23:26:34', '2021-04-20 23:26:34');
INSERT INTO `tb_points_mall_goods` VALUES ('11', null, '米诗萱MISHIXUAN莹润无暇水光保湿气垫cc霜15g正装+送15g替换装', 'data/images/admin/1/deerspot_1619189960396.png', 'data/images/admin/1/deerspot_1619189960396.png', null, 'data/images/admin/1/deerspot_1618933345104.png,data/images/admin/1/deerspot_1618933348224.png,data/images/admin/1/deerspot_1618933352412.png,data/images/admin/1/deerspot_1618933356775.png,data/images/admin/1/deerspot_1618933360241.png,data/images/admin/1/deerspot_1618933364010.png,data/images/admin/1/deerspot_1618933367410.png,data/images/admin/1/deerspot_1618933371415.png', '138.00', '0', '0', '0', '2', '0', '0.00', '0', '0', '0', null, '0.00', '0.00', '138', null, '2021-04-20 23:42:53', '2021-04-23 22:59:22');
INSERT INTO `tb_points_mall_goods` VALUES ('12', null, '米诗萱纳米金滋养修护眼霜抗老抗衰去黑眼圈淡化细纹去眼袋提拉', 'data/images/admin/1/deerspot_1619186493878.png', 'data/images/admin/1/deerspot_1619186493878.png', null, 'data/images/admin/1/deerspot_1619186763035.png,data/images/admin/1/deerspot_1619186766210.png,data/images/admin/1/deerspot_1619186769873.png,data/images/admin/1/deerspot_1619186773259.png,data/images/admin/1/deerspot_1619186776134.png', '168.00', '1', '0', '0', '2', '0', '0.00', '5', '5', '0', null, '0.00', '0.00', '168', null, '2021-04-23 22:06:19', '2021-04-23 22:06:19');
INSERT INTO `tb_points_mall_goods` VALUES ('13', null, '[盒装]米诗萱补水雪肤蚕丝面膜30ml纯天然蚕丝补水嫩肤精华轻薄', 'data/images/admin/1/deerspot_1623675045984.jpg', 'data/images/admin/1/deerspot_1623675045984.jpg', null, 'data/images/admin/1/deerspot_1623675226062.jpg,data/images/admin/1/deerspot_1623675232863.jpg,data/images/admin/1/deerspot_1623675235321.jpg,data/images/admin/1/deerspot_1623675237979.jpg,data/images/admin/1/deerspot_1623675241033.jpg,data/images/admin/1/deerspot_1623675244622.jpg', '75.00', '2', '0', '0', '2', '0', '0.00', '5', '5', '0', null, '0.00', '0.00', '15', null, '2021-04-23 22:17:04', '2021-06-14 20:54:11');
INSERT INTO `tb_points_mall_goods` VALUES ('14', null, '米诗萱冰晶嫩白防晒喷雾防晒霜防紫外线网红变白美白面部隔离粉底', 'data/images/admin/1/deerspot_1619187580858.png', 'data/images/admin/1/deerspot_1619187580858.png', null, 'data/images/admin/1/deerspot_1619188232205.png,data/images/admin/1/deerspot_1619188235217.png,data/images/admin/1/deerspot_1619188238400.png,data/images/admin/1/deerspot_1619188241086.png,data/images/admin/1/deerspot_1619188244961.png,data/images/admin/1/deerspot_1619188248446.png', '98.00', '0', '0', '0', '2', '0', '0.00', '208', '208', '0', null, '0.00', '0.00', '98', null, '2021-04-23 22:30:52', '2021-04-23 22:30:52');
INSERT INTO `tb_points_mall_goods` VALUES ('15', null, '米诗萱卸妆巾女便携湿巾无刺激一次性擦脸专用卸妆棉深层清洁护肤', 'data/images/admin/1/deerspot_1619189367072.png', 'data/images/admin/1/deerspot_1619189367072.png', null, 'data/images/admin/1/deerspot_1619189667779.png,data/images/admin/1/deerspot_1619189671200.png,data/images/admin/1/deerspot_1619189674702.png', '19.00', '1', '0', '0', '2', '0', '0.00', '3', '3', '0', null, '0.00', '0.00', '19', null, '2021-04-23 22:54:37', '2021-04-23 22:54:37');
INSERT INTO `tb_points_mall_goods` VALUES ('16', null, '草本净肤祛痘膏', 'data/images/admin/1/deerspot_1623678574008.jpg', 'data/images/admin/1/deerspot_1623678574008.jpg', null, 'data/images/admin/1/deerspot_1623678600541.jpg,data/images/admin/1/deerspot_1623678604777.jpg,data/images/admin/1/deerspot_1623678607558.jpg,data/images/admin/1/deerspot_1623678610126.jpg,data/images/admin/1/deerspot_1623678612778.jpg,data/images/admin/1/deerspot_1623678615385.jpg', '168.00', '1', '0', '0', '2', '0', '0.00', '2', '2', '0', null, '0.00', '0.00', '168', null, '2021-04-23 23:49:27', '2021-06-14 21:50:17');
INSERT INTO `tb_points_mall_goods` VALUES ('17', null, '氨基酸净颜平衡洁面乳', 'data/images/admin/1/deerspot_1623678753385.jpg', 'data/images/admin/1/deerspot_1623678753385.jpg', null, 'data/images/admin/1/deerspot_1623678732930.jpg,data/images/admin/1/deerspot_1623678736713.jpg,data/images/admin/1/deerspot_1623678739167.jpg,data/images/admin/1/deerspot_1623678741661.jpg,data/images/admin/1/deerspot_1623678743736.jpg,data/images/admin/1/deerspot_1623678746638.jpg', '98.00', '1', '0', '0', '2', '0', '0.00', '1', '1', '0', null, '0.00', '0.00', '98', null, '2021-04-24 00:00:42', '2021-06-14 21:53:23');
INSERT INTO `tb_points_mall_goods` VALUES ('18', null, '米诗萱D-CUP美乳丰韵套快速增大涂抹式按摩正品美胸丰乳产后下垂', 'data/images/admin/1/deerspot_1619337503111.jpg', 'data/images/admin/1/deerspot_1619337503111.jpg', null, 'data/images/admin/1/deerspot_1619337836672.png,data/images/admin/1/deerspot_1619337839514.png,data/images/admin/1/deerspot_1619337842410.png,data/images/admin/1/deerspot_1619337844815.png,data/images/admin/1/deerspot_1619337847296.png', '298.00', '1', '0', '0', '2', '0', '0.00', '1', '1', '0', null, '0.00', '0.00', '298', null, '2021-04-25 16:04:09', '2021-04-25 16:04:09');
INSERT INTO `tb_points_mall_goods` VALUES ('19', null, '米诗萱氨基无硅油洗护套装350ml+350ml无重金属无矿物油无激素', 'data/images/admin/1/deerspot_1619337949079.png', 'data/images/admin/1/deerspot_1619337949079.png', null, 'data/images/admin/1/deerspot_1619338232529.png,data/images/admin/1/deerspot_1619338235451.png,data/images/admin/1/deerspot_1619338238508.png,data/images/admin/1/deerspot_1619338241068.png,data/images/admin/1/deerspot_1619338243589.png,data/images/admin/1/deerspot_1619338247302.png', '78.00', '2', '0', '0', '2', '0', '0.00', '1', '1', '0', null, '0.00', '0.00', '78', null, '2021-04-25 16:10:49', '2021-04-25 16:10:49');
INSERT INTO `tb_points_mall_goods` VALUES ('20', null, '米诗萱眼线笔不易脱妆不花妆', 'data/images/admin/1/deerspot_1623680156948.jpg', 'data/images/admin/1/deerspot_1623680156948.jpg', null, 'data/images/admin/1/deerspot_1619339871027.png,data/images/admin/1/deerspot_1619339873988.png,data/images/admin/1/deerspot_1619339877316.png,data/images/admin/1/deerspot_1619339880986.png,data/images/admin/1/deerspot_1619339883797.png,data/images/admin/1/deerspot_1619339887617.png', '68.00', '1', '0', '0', '2', '0', '0.00', '2', '2', '0', null, '0.00', '0.00', '68', null, '2021-04-25 16:38:09', '2021-06-14 22:15:59');
INSERT INTO `tb_points_mall_goods` VALUES ('21', null, '[盒装]炭净肤焕颜面膜', 'data/images/admin/1/deerspot_1623674885937.jpg', 'data/images/admin/1/deerspot_1623674885937.jpg', null, 'data/images/admin/1/deerspot_1623674795686.png,data/images/admin/1/deerspot_1623674799183.jpg,data/images/admin/1/deerspot_1623674802048.jpg,data/images/admin/1/deerspot_1623674805024.jpg,data/images/admin/1/deerspot_1623674807500.jpg,data/images/admin/1/deerspot_1623674809749.jpg', '78.00', '1', '0', '0', '2', '0', '0.00', '3', '3', '0', null, '0.00', '0.00', '78', null, '2021-06-14 20:46:51', '2021-06-14 20:54:33');
INSERT INTO `tb_points_mall_goods` VALUES ('22', null, '多效修复隔离妆前乳', 'data/images/admin/1/deerspot_1623675824941.jpg', 'data/images/admin/1/deerspot_1623675824941.jpg', null, 'data/images/admin/1/deerspot_1623675867202.jpg,data/images/admin/1/deerspot_1623675870158.jpg,data/images/admin/1/deerspot_1623675873484.jpg,data/images/admin/1/deerspot_1623675876451.jpg,data/images/admin/1/deerspot_1623675879115.jpg,data/images/admin/1/deerspot_1623675881413.jpg', '98.00', '1', '0', '0', '2', '0', '0.00', '1', '1', '0', null, '0.00', '0.00', '98', null, '2021-06-14 21:04:45', '2021-06-14 21:04:45');
INSERT INTO `tb_points_mall_goods` VALUES ('23', null, '烟酰胺亮肤舒润身体乳', 'data/images/admin/1/deerspot_1623677358654.jpg', 'data/images/admin/1/deerspot_1623677358654.jpg', null, 'data/images/admin/1/deerspot_1623676318802.jpg,data/images/admin/1/deerspot_1623676321602.jpg,data/images/admin/1/deerspot_1623676324461.jpg,data/images/admin/1/deerspot_1623676327228.jpg,data/images/admin/1/deerspot_1623676330226.jpg,data/images/admin/1/deerspot_1623676332381.jpg', '78.00', '1', '0', '0', '2', '0', '0.00', '4', '4', '0', null, '0.00', '0.00', '78', null, '2021-06-14 21:12:16', '2021-06-14 21:29:24');
INSERT INTO `tb_points_mall_goods` VALUES ('24', null, '胶原蛋白冻膜', 'data/images/admin/1/deerspot_1623677741442.jpg', 'data/images/admin/1/deerspot_1623677741442.jpg', null, 'data/images/admin/1/deerspot_1623677675677.jpg,data/images/admin/1/deerspot_1623677678378.jpg,data/images/admin/1/deerspot_1623677681211.jpg,data/images/admin/1/deerspot_1623677684022.jpg,data/images/admin/1/deerspot_1623677687036.jpg,data/images/admin/1/deerspot_1623677689508.jpg', '78.00', '1', '0', '0', '2', '0', '0.00', '2', '2', '0', null, '0.00', '0.00', '78', null, '2021-06-14 21:35:13', '2021-06-14 21:35:44');
INSERT INTO `tb_points_mall_goods` VALUES ('25', null, '清洁抑菌皂', 'data/images/admin/1/deerspot_1623677907505.jpg', 'data/images/admin/1/deerspot_1623677907505.jpg', null, 'data/images/admin/1/deerspot_1623677932190.jpg,data/images/admin/1/deerspot_1623677935464.jpg,data/images/admin/1/deerspot_1623677938687.jpg,data/images/admin/1/deerspot_1623677941501.jpg,data/images/admin/1/deerspot_1623677944235.jpg,data/images/admin/1/deerspot_1623677947166.jpg', '58.00', '1', '0', '0', '2', '0', '0.00', '1', '1', '0', null, '0.00', '0.00', '58', null, '2021-06-14 21:39:10', '2021-06-14 21:39:10');
INSERT INTO `tb_points_mall_goods` VALUES ('26', null, '24K黄金烟酰胺精华', 'data/images/admin/1/deerspot_1623678096696.jpg', 'data/images/admin/1/deerspot_1623678096696.jpg', null, 'data/images/admin/1/deerspot_1623678101409.jpg,data/images/admin/1/deerspot_1623678104324.jpg,data/images/admin/1/deerspot_1623678109144.jpg,data/images/admin/1/deerspot_1623678112297.jpg,data/images/admin/1/deerspot_1623678115353.jpg,data/images/admin/1/deerspot_1623678118149.jpg', '138.00', '1', '0', '0', '2', '0', '0.00', '3', '3', '0', null, '0.00', '0.00', '138', null, '2021-06-14 21:42:00', '2021-06-14 21:42:00');
INSERT INTO `tb_points_mall_goods` VALUES ('27', null, '除螨控油祛痘皂', 'data/images/admin/1/deerspot_1623678272174.jpg', 'data/images/admin/1/deerspot_1623678272174.jpg', null, 'data/images/admin/1/deerspot_1623678276796.jpg,data/images/admin/1/deerspot_1623678279690.jpg,data/images/admin/1/deerspot_1623678282435.jpg,data/images/admin/1/deerspot_1623678286221.jpg,data/images/admin/1/deerspot_1623678289293.jpg,data/images/admin/1/deerspot_1623678292096.jpg', '68.00', '1', '0', '0', '2', '0', '0.00', '3', '3', '0', null, '0.00', '0.00', '68', null, '2021-06-14 21:44:53', '2021-06-14 21:44:53');
INSERT INTO `tb_points_mall_goods` VALUES ('28', null, '多肽净透奢养焕颜霜', 'data/images/admin/1/deerspot_1623679119903.jpg', 'data/images/admin/1/deerspot_1623679119903.jpg', null, 'data/images/admin/1/deerspot_1623679137227.jpg,data/images/admin/1/deerspot_1623679140340.jpg,data/images/admin/1/deerspot_1623679143474.jpg,data/images/admin/1/deerspot_1623679146384.jpg,data/images/admin/1/deerspot_1623679149281.jpg,data/images/admin/1/deerspot_1623679152397.jpg', '158.00', '1', '0', '0', '2', '0', '0.00', '5', '5', '0', null, '0.00', '0.00', '158', null, '2021-06-14 21:59:15', '2021-06-14 21:59:15');
INSERT INTO `tb_points_mall_goods` VALUES ('29', null, '[盒装]烟酰胺原液面膜', 'data/images/admin/1/deerspot_1623685569575.jpg', 'data/images/admin/1/deerspot_1623685569575.jpg', null, 'data/images/admin/1/deerspot_1623685592807.jpg,data/images/admin/1/deerspot_1623685595771.jpg,data/images/admin/1/deerspot_1623685598715.jpg,data/images/admin/1/deerspot_1623685601052.jpg,data/images/admin/1/deerspot_1623685603584.jpg,data/images/admin/1/deerspot_1623685605954.jpg', '78.00', '1', '0', '0', '2', '0', '0.00', '5', '5', '0', null, '0.00', '0.00', '78', null, '2021-06-14 23:47:57', '2021-06-14 23:47:57');

-- ----------------------------
-- Table structure for tb_points_mall_goods_specification
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_goods_specification`;
CREATE TABLE `tb_points_mall_goods_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `name` varchar(10) NOT NULL COMMENT '商品规格名称',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_goods_specification
-- ----------------------------
INSERT INTO `tb_points_mall_goods_specification` VALUES ('1', '1', '色号', '1', '2021-03-15 13:49:19', '2021-03-15 13:49:19');
INSERT INTO `tb_points_mall_goods_specification` VALUES ('2', '19', '规格', '1', '2021-04-25 16:11:16', '2021-04-25 16:11:16');
INSERT INTO `tb_points_mall_goods_specification` VALUES ('3', '20', '颜色', '1', '2021-04-25 16:38:50', '2021-04-25 16:38:50');

-- ----------------------------
-- Table structure for tb_points_mall_goods_specification_option
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_goods_specification_option`;
CREATE TABLE `tb_points_mall_goods_specification_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_specification_id` int(11) NOT NULL COMMENT '商品规格id',
  `name` varchar(10) NOT NULL COMMENT '商品规格选项名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '单价/加价金额',
  `stock` int(11) DEFAULT '1' COMMENT '库存 1=有货 2=无货',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_goods_specification_option
-- ----------------------------
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('1', '1', '1', '#101豆沙粉', '0.00', '1', '1', '2021-03-15 13:49:19', '2021-03-15 13:49:19');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('2', '1', '1', '#102南瓜色', '0.00', '1', '2', '2021-03-15 13:49:39', '2021-03-15 13:49:39');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('3', '1', '1', '#103热恋红', '0.00', '1', '3', '2021-03-15 13:50:01', '2021-03-15 13:50:01');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('4', '1', '1', '#104甜蜜橘', '0.00', '1', '4', '2021-03-15 13:50:21', '2021-03-15 13:50:21');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('5', '1', '1', '#105中国红', '0.00', '1', '5', '2021-03-15 13:50:44', '2021-03-15 13:50:44');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('6', '1', '1', '#106姨妈红', '0.00', '1', '6', '2021-03-15 13:50:55', '2021-03-15 13:50:55');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('7', '1', '1', '#车厘子红', '0.00', '1', '7', '2021-03-15 13:51:24', '2021-03-15 13:51:24');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('8', '1', '1', '#锦鲤红', '0.00', '1', '8', '2021-03-15 13:51:45', '2021-03-15 13:51:45');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('9', '1', '1', '#番茄红', '0.00', '1', '9', '2021-03-15 13:52:09', '2021-03-15 13:52:09');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('10', '1', '1', '#烟熏玫瑰', '0.00', '1', '10', '2021-03-15 13:52:38', '2021-03-15 13:52:38');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('11', '19', '2', '洗发露', '0.00', '1', '1', '2021-04-25 16:11:16', '2021-04-25 16:11:16');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('12', '19', '2', '护发素', '0.00', '1', '2', '2021-04-25 16:11:25', '2021-04-25 16:11:25');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('13', '19', '2', '洗发露+护发素套装', '60.00', '1', '3', '2021-04-25 16:11:54', '2021-04-25 16:11:54');
INSERT INTO `tb_points_mall_goods_specification_option` VALUES ('14', '20', '3', '黑色', '0.00', '1', '1', '2021-04-25 16:38:50', '2021-04-25 16:38:50');

-- ----------------------------
-- Table structure for tb_points_mall_member_goods_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_member_goods_collect`;
CREATE TABLE `tb_points_mall_member_goods_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `is_goods_exists` tinyint(1) DEFAULT '1' COMMENT '商品是否有效 0=无效 1=有效',
  `is_buy` tinyint(1) DEFAULT '0' COMMENT '商品是否购买 0=未购买 1=已购买',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_member_goods_collect
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_menu`;
CREATE TABLE `tb_points_mall_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '菜单分类名称',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单分类描述',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `icon` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_menu
-- ----------------------------
INSERT INTO `tb_points_mall_menu` VALUES ('1', null, '美妆专区', '', null, 'data/images/admin/1/deerspot_1614583892190.png', '2021-03-01 13:24:34', '2021-03-01 15:31:35');

-- ----------------------------
-- Table structure for tb_points_mall_menu_goods_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_menu_goods_relation`;
CREATE TABLE `tb_points_mall_menu_goods_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_id` int(11) NOT NULL COMMENT '菜单分类id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_menu_goods_relation
-- ----------------------------
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('1', '1', '1', '2021-03-10 22:06:10', '2021-03-10 22:06:10');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('6', '1', '6', '2021-04-20 22:11:00', '2021-04-20 22:11:00');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('7', '1', '7', '2021-04-20 22:23:17', '2021-04-20 22:23:17');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('8', '1', '8', '2021-04-20 22:36:05', '2021-04-20 22:36:05');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('9', '1', '9', '2021-04-20 22:54:02', '2021-04-20 22:54:02');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('10', '1', '10', '2021-04-20 23:26:34', '2021-04-20 23:26:34');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('11', '1', '11', '2021-04-20 23:42:53', '2021-04-20 23:42:53');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('12', '1', '12', '2021-04-23 22:06:19', '2021-04-23 22:06:19');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('13', '1', '13', '2021-04-23 22:17:04', '2021-04-23 22:17:04');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('14', '1', '14', '2021-04-23 22:30:52', '2021-04-23 22:30:52');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('15', '1', '15', '2021-04-23 22:54:37', '2021-04-23 22:54:37');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('16', '1', '16', '2021-04-23 23:49:27', '2021-04-23 23:49:27');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('17', '1', '17', '2021-04-24 00:00:42', '2021-04-24 00:00:42');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('18', '1', '18', '2021-04-25 16:04:09', '2021-04-25 16:04:09');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('19', '1', '19', '2021-04-25 16:10:49', '2021-04-25 16:10:49');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('20', '1', '20', '2021-04-25 16:38:09', '2021-04-25 16:38:09');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('21', '1', '21', '2021-06-14 20:46:51', '2021-06-14 20:46:51');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('22', '1', '22', '2021-06-14 21:04:45', '2021-06-14 21:04:45');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('23', '1', '23', '2021-06-14 21:12:16', '2021-06-14 21:12:16');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('24', '1', '24', '2021-06-14 21:35:13', '2021-06-14 21:35:13');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('25', '1', '25', '2021-06-14 21:39:10', '2021-06-14 21:39:10');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('26', '1', '26', '2021-06-14 21:42:00', '2021-06-14 21:42:00');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('27', '1', '27', '2021-06-14 21:44:53', '2021-06-14 21:44:53');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('28', '1', '28', '2021-06-14 21:59:15', '2021-06-14 21:59:15');
INSERT INTO `tb_points_mall_menu_goods_relation` VALUES ('29', '1', '29', '2021-06-14 23:47:57', '2021-06-14 23:47:57');

-- ----------------------------
-- Table structure for tb_points_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_order`;
CREATE TABLE `tb_points_mall_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号，供客户查询',
  `goods_total_quantity` int(11) DEFAULT '0' COMMENT '商品总数量',
  `goods_total_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品总金额',
  `packing_charges` decimal(10,2) DEFAULT NULL COMMENT '包装费',
  `delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '运费/配送费',
  `actual_price` decimal(10,2) DEFAULT '0.00' COMMENT '实付款',
  `shopping_way` int(2) DEFAULT NULL COMMENT '购物方式 1=自取 2=配送',
  `delivery_address_id` int(11) DEFAULT NULL COMMENT '收货地址id',
  `contact_realname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `contact_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `contact_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `contact_area` varchar(100) DEFAULT NULL COMMENT '区/县',
  `contact_street` varchar(100) DEFAULT NULL COMMENT '详细地址(具体到街道门牌号)',
  `contact_sex` int(1) DEFAULT '0' COMMENT '联系人性别 0=无 1=先生 2=女士',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `description` varchar(2048) DEFAULT NULL COMMENT '订单描述',
  `status` int(2) DEFAULT '1' COMMENT '订单状态 1=未付款 4=待发货(已处理) 5=已发货 6=已完成 7=售后处理中 8=已退款(废弃选项) 9=售后处理完成 10=已取消(未支付)',
  `trade_id` int(11) DEFAULT NULL COMMENT '用户交易id',
  `order_logistics_id` int(11) DEFAULT NULL COMMENT '物流id',
  `is_invoice` tinyint(1) DEFAULT '0' COMMENT '是否开票',
  `invoice_id` int(11) DEFAULT NULL COMMENT '发票id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=删除',
  `shop_id` int(11) DEFAULT NULL COMMENT '接单门店id',
  `shop_name` varchar(100) DEFAULT NULL COMMENT '接单门店名称',
  `shop_address` varchar(200) DEFAULT NULL COMMENT '门店地址',
  `cancel_reason` int(2) DEFAULT NULL COMMENT '(未支付)订单取消原因 1=您主动取消 2=订单超时未支付 3=系统异常',
  `payment_deadline` datetime DEFAULT NULL COMMENT '支付截止时间(五分钟内未付款的订单会被自动关闭)',
  `is_printed` tinyint(1) DEFAULT '0' COMMENT '是否已打印',
  `queue_no` int(11) DEFAULT NULL COMMENT '取餐号',
  `full_reduction_rule_id` int(11) DEFAULT NULL COMMENT '使用的满减id',
  `full_reduction_rule_description` varchar(100) DEFAULT NULL COMMENT '使用的满减规则描述',
  `coupons_id` int(11) DEFAULT NULL COMMENT '使用的优惠卷id',
  `coupons_description` varchar(100) DEFAULT NULL COMMENT '使用的优惠卷描述',
  `coupons_member_relation_id` int(11) DEFAULT NULL COMMENT '优惠卷用户关系id',
  `is_change_to_delivery` tinyint(1) DEFAULT '0' COMMENT '是否由自取订单改为配送 0=否 1=是',
  `change_to_delivery_out_trade_no` varchar(50) DEFAULT NULL COMMENT '自取订单改为配送的商户单号',
  `change_to_delivery_trade_id` int(11) DEFAULT NULL COMMENT '自取订单改为配送的用户交易id',
  `platform_extract_ratio` decimal(10,2) DEFAULT '0.00' COMMENT '平台抽取比例(%)',
  `platform_extract_price` decimal(10,2) DEFAULT '0.00' COMMENT '平台抽取金额',
  `platform_delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '平台承担配送费',
  `platform_income` decimal(10,2) DEFAULT '0.00' COMMENT '平台实际收入',
  `merchant_delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '商家承担配送费',
  `courier_income` decimal(10,2) DEFAULT '0.00' COMMENT '骑手实际收入',
  `merchant_income` decimal(10,2) DEFAULT '0.00' COMMENT '商家实际收入',
  `payment_success_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `order_completion_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `paid_order_cancel_reason` int(2) DEFAULT NULL COMMENT '(废弃字段)(已支付)订单取消原因 1=信息填写错误 2=送达时间选错了 3=买错了/买少了 4=商家缺货 5=商家联系我取消 6=配送太慢 7=骑手联系我取消 8=我不想要了',
  `limited_price` decimal(10,2) DEFAULT '0.00' COMMENT '满足价格(元)',
  `reduced_price` decimal(10,2) DEFAULT '0.00' COMMENT '减价额度(元)',
  `coupons_discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券折扣金额/优惠券优惠金额',
  `delivery_way` int(2) DEFAULT NULL COMMENT '配送方式 1=商家自配送',
  `is_pay_to_merchant` tinyint(1) DEFAULT '0' COMMENT '用户下单资金是否已转入商家余额 0=否 1=是',
  `before_reduced_delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '立减之前需要支付的运费/配送费',
  `payment_mode` int(2) DEFAULT NULL COMMENT '支付方式 1=微信支付 (2=平台余额) 3=平台积分',
  `logistics_way` varchar(50) DEFAULT NULL COMMENT '物流方式/快递公司名称',
  `logistics_no` varchar(50) DEFAULT NULL COMMENT '物流编号/快递单号',
  `courier_name` varchar(50) DEFAULT NULL COMMENT '快递员/快递站名称',
  `courier_phone` varchar(11) DEFAULT NULL COMMENT '快递员电话',
  `delivery_status` int(2) DEFAULT NULL COMMENT '快递状态 0=快递收件(揽件) 1=在途中 2=正在派件 3=已签收 4=派送失败 5=疑难件 6=退件签收',
  `is_sign` tinyint(1) DEFAULT NULL COMMENT '是否签收',
  `delivery_last_update_time` datetime DEFAULT NULL COMMENT '快递轨迹信息最新时间',
  `take_time` varchar(50) DEFAULT NULL COMMENT '发货到收货消耗时长 (截止最新轨迹)',
  `contact_house_number` varchar(100) DEFAULT NULL COMMENT '门牌号',
  `contact_longitude` decimal(18,6) DEFAULT NULL COMMENT '经度',
  `contact_latitude` decimal(18,6) DEFAULT NULL COMMENT '纬度',
  `first_goods_main_image` varchar(128) DEFAULT NULL COMMENT '第一件商品的主图',
  `goods_source` int(2) DEFAULT NULL COMMENT '发货源 1=拼多多 2=淘宝 3=京东',
  `goods_source_order_no` varchar(50) DEFAULT NULL COMMENT '发货源订单编号',
  `platform_remark` varchar(1024) DEFAULT NULL COMMENT '平台备注',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_order
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_order_detail`;
CREATE TABLE `tb_points_mall_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` bigint(11) NOT NULL COMMENT '订单id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `main_image` varchar(128) NOT NULL COMMENT '商品主图',
  `spec_list` varchar(1024) DEFAULT NULL COMMENT '商品规格 JSON数据',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `number` int(11) DEFAULT '0' COMMENT '购买数量',
  `subtotal` decimal(10,2) DEFAULT '0.00' COMMENT '小计',
  `packing_charges` decimal(10,2) DEFAULT '0.00' COMMENT '单件商品的包装费',
  `is_used_coupons` tinyint(1) DEFAULT '0' COMMENT '该商品是否使用了优惠券 0=否 1=是',
  `coupons_discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券折扣金额/优惠券优惠金额',
  `after_coupons_discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券折扣之后的商品价格',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常 1=删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_order_logistics
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_order_logistics`;
CREATE TABLE `tb_points_mall_order_logistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` bigint(11) NOT NULL COMMENT '订单id',
  `description` varchar(200) DEFAULT NULL COMMENT '物流状态描述',
  `description_time` datetime DEFAULT NULL COMMENT '物流状态描述对应的时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_order_logistics
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_order_refund
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_order_refund`;
CREATE TABLE `tb_points_mall_order_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` bigint(11) NOT NULL COMMENT '订单id',
  `type` int(2) DEFAULT '1' COMMENT '退款类型 1=未发货订单申请退款 2=已发货订单申请售后-仅退款 3=已发货订单申请售后-退货退款',
  `refund_way` int(2) DEFAULT '1' COMMENT '退款方式 1=全额退款 2=部分退款',
  `refund_reason` int(2) DEFAULT NULL COMMENT '退款原因 1=订单信息拍错（规格/尺码/颜色等） 2=我不想要了 3=地址/电话信息填写错误 4=没用/少用优惠 5=缺货 31=缺货 32=协商一致退款 33=未按约定时间发货 34=排错/多拍/不想要 35=其他 61=效果不好/不喜欢 62=材质不符 63=尺寸不符 64=外观破损 65=颜色/款式图案与描述不符 66=物流问题 67=和预期不符 68=服务承诺/态度 69=质量问题 70=其他',
  `refund_reason_description` varchar(1024) DEFAULT NULL COMMENT '退款原因详细描述',
  `evidence_images` varchar(1024) DEFAULT NULL COMMENT '凭证图片',
  `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '退款金额',
  `refund_account` int(2) DEFAULT NULL COMMENT '退款账户 1=微信 2=支付宝 3=平台余额 4=平台积分',
  `status` int(2) DEFAULT '1' COMMENT '退款状态 1=退款申请已提交 4=等待平台处理 5=平台拒绝退款，退款已关闭 7=退款成功',
  `goods_total_quantity` int(11) DEFAULT '0' COMMENT '退还商品总数量',
  `goods_total_price` decimal(10,2) DEFAULT '0.00' COMMENT '退还商品总金额',
  `packing_charges` decimal(10,2) DEFAULT '0.00' COMMENT '退还包装费',
  `is_refund_delivery_fee` tinyint(1) DEFAULT '0' COMMENT '是否退还了配送费 0=否 1=是',
  `delivery_fee` decimal(10,2) DEFAULT '0.00' COMMENT '退还运费/配送费',
  `is_used_coupons` tinyint(1) DEFAULT '0' COMMENT '退款商品中是否使用了优惠券 0=否 1=是',
  `is_used_full_reduction_rule` tinyint(1) DEFAULT '0' COMMENT '退款商品中是否使用了满减规则 0=否 1=是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_order_refund
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_order_refund_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_order_refund_goods`;
CREATE TABLE `tb_points_mall_order_refund_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_refund_id` int(11) NOT NULL COMMENT '订单退款id',
  `order_detail_id` int(11) NOT NULL COMMENT '订单商品详情id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `main_image` varchar(128) NOT NULL COMMENT '商品主图',
  `spec_list` varchar(1024) DEFAULT NULL COMMENT '商品规格 JSON数据',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `number` int(11) DEFAULT '0' COMMENT '退款数量',
  `subtotal` decimal(10,2) DEFAULT '0.00' COMMENT '小计',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_order_refund_goods
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_order_refund_process
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_order_refund_process`;
CREATE TABLE `tb_points_mall_order_refund_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_refund_id` int(11) NOT NULL COMMENT '订单退款id',
  `name` varchar(50) NOT NULL COMMENT '流程名称',
  `description` varchar(200) DEFAULT NULL COMMENT '流程描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_order_refund_process
-- ----------------------------

-- ----------------------------
-- Table structure for tb_points_mall_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_points_mall_shopping_cart`;
CREATE TABLE `tb_points_mall_shopping_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `spec_list` varchar(1024) DEFAULT NULL COMMENT '商品规格 JSON数据',
  `number` int(11) DEFAULT '1' COMMENT '购买数量',
  `is_goods_exists` tinyint(1) DEFAULT '1' COMMENT '商品是否有效 0=无效 1=有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_points_mall_shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for tb_printer
-- ----------------------------
DROP TABLE IF EXISTS `tb_printer`;
CREATE TABLE `tb_printer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `name` varchar(50) DEFAULT NULL COMMENT '打印机名称',
  `number` varchar(50) DEFAULT NULL COMMENT '打印机编号',
  `identifying_code` varchar(50) DEFAULT NULL COMMENT '打印机密钥/识别码',
  `is_auto_print` tinyint(1) NOT NULL DEFAULT '0' COMMENT '接单后是否自动打印小票/标签 0=否 1=是',
  `mobile_card_number` varchar(50) DEFAULT NULL COMMENT '(预留字段)手机卡号',
  `cloud_registration_status` varchar(50) DEFAULT NULL COMMENT '(预留字段)云端注册状态',
  `type` int(2) DEFAULT NULL COMMENT '打印机类型 1=小票打印机 2=标签打印机',
  `brand` int(2) DEFAULT NULL COMMENT '打印机品牌 1=飞鹅打印机 2=易联云打印机',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_printer
-- ----------------------------

-- ----------------------------
-- Table structure for tb_reply
-- ----------------------------
DROP TABLE IF EXISTS `tb_reply`;
CREATE TABLE `tb_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家id',
  `appraise_id` int(11) DEFAULT NULL COMMENT '评价id（被回复对象id）',
  `reply_id` int(11) DEFAULT NULL COMMENT '回复id（被回复对象id）',
  `reply_type` int(1) NOT NULL DEFAULT '1' COMMENT '回复类型 1-评价回复，2-对回复进行再回复',
  `replier_type` int(1) NOT NULL DEFAULT '1' COMMENT '回复人类型 1-用户回复，2-商家回复',
  `content` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '回复内容',
  `images_url` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '图url，用逗号分隔',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户回复表';

-- ----------------------------
-- Records of tb_reply
-- ----------------------------

-- ----------------------------
-- Table structure for tb_scheduled_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_scheduled_task`;
CREATE TABLE `tb_scheduled_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) NOT NULL COMMENT '任务名称',
  `code` varchar(256) NOT NULL COMMENT '任务代码',
  `frequency` varchar(50) NOT NULL COMMENT '执行频率',
  `state` int(2) DEFAULT '1' COMMENT '执行状态 1=未执行 2=正在执行',
  `last_start_time` datetime DEFAULT NULL COMMENT '最后执行开始时间',
  `last_end_time` datetime DEFAULT NULL COMMENT '最后执行结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务表';

-- ----------------------------
-- Records of tb_scheduled_task
-- ----------------------------
INSERT INTO `tb_scheduled_task` VALUES ('1', '检测并关闭超时未支付的订单', 'closeOverdueOrder', '每隔1分钟', '1', '2021-09-29 16:46:00', '2021-09-29 16:46:00');
INSERT INTO `tb_scheduled_task` VALUES ('2', '检测逾期的优惠卷', 'checkOverdueCoupons', '每天凌晨0点', '1', '2021-09-29 00:00:00', '2021-09-29 00:00:00');
INSERT INTO `tb_scheduled_task` VALUES ('3', '月销量清零', 'MonthlySalesReset', '每月1号0点', '1', '2021-09-01 00:00:00', '2021-09-01 00:00:00');
INSERT INTO `tb_scheduled_task` VALUES ('4', '检测并完成超时未确认的订单', 'finishOverdueOrder', '每隔一分钟', '1', '2020-04-28 02:22:59', '2020-04-28 02:22:59');
INSERT INTO `tb_scheduled_task` VALUES ('5', '将新用户的\"是否需要弹出新人引导提示\"字段值改成是', 'updateIsRemindNewPeopleOfMember', '每天凌晨0点5分', '1', '2021-09-29 00:05:00', '2021-09-29 00:05:00');
INSERT INTO `tb_scheduled_task` VALUES ('6', '优惠卷即将过期提醒', 'couponsOverdueSMSReminder', '每周3下午3点', '1', '2021-09-29 15:00:00', '2021-09-29 15:00:00');
INSERT INTO `tb_scheduled_task` VALUES ('7', '修改用户的是否需要请求授权服务通知状态', 'updateIsRequestWxNotifyOfMember', '每天凌晨0点10分', '1', '2021-09-29 00:10:00', '2021-09-29 00:10:00');
INSERT INTO `tb_scheduled_task` VALUES ('8', '检测给商家发放用户下单资金', 'updatePayOrderFrozenBalanceOfMerchant', '每天凌晨0点15分', '1', '2021-09-29 00:15:00', '2021-09-29 00:15:00');
INSERT INTO `tb_scheduled_task` VALUES ('9', '同步积分商城-订单物流信息', 'syncPointsMallOrderLogisticsInfo', '每天凌晨0点20分', '1', '2021-09-29 00:20:00', '2021-09-29 00:20:16');

-- ----------------------------
-- Table structure for tb_scheduled_task_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_scheduled_task_log`;
CREATE TABLE `tb_scheduled_task_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheduled_task_code` varchar(256) NOT NULL COMMENT '任务代码',
  `state` int(2) NOT NULL DEFAULT '1' COMMENT '执行状态 1=执行成功 2=执行出错',
  `error` varchar(1024) DEFAULT NULL COMMENT '错误描述',
  `host_name` varchar(50) NOT NULL COMMENT '执行主机名称',
  `host_ip_address` varchar(50) NOT NULL COMMENT '执行主机ip地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1722 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务执行日志表';

-- ----------------------------
-- Records of tb_scheduled_task_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_setting
-- ----------------------------
DROP TABLE IF EXISTS `tb_setting`;
CREATE TABLE `tb_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `purchase_reward_points` decimal(10,2) DEFAULT '0.00' COMMENT '购买一杯奶茶赠送积分数量',
  `registration_reward_points` decimal(10,2) DEFAULT '0.00' COMMENT '新用户注册奖励积分数量',
  `new_member_coupons_id` int(11) DEFAULT NULL COMMENT '(废弃字段)新人优惠卷id',
  `default_shop_id` int(11) DEFAULT NULL COMMENT '(废弃字段)默认门店id',
  `merchant_withdraw_fee` decimal(10,2) NOT NULL COMMENT '商家提现手续费(%)',
  `start_delivery_price` decimal(10,2) DEFAULT '0.00' COMMENT '系统默认起送价格',
  `delivery_starting_distance` decimal(10,2) DEFAULT '0.00' COMMENT '配送起步距离(KM)',
  `delivery_starting_price` decimal(10,2) DEFAULT '0.00' COMMENT '配送起步价',
  `delivery_kilometer_price` decimal(10,2) DEFAULT '0.00' COMMENT '配送公里价(元/KM)',
  `delivery_distance_limit` decimal(10,2) DEFAULT '0.00' COMMENT '配送距离上限(KM)',
  `order_system_extraction_ratio` decimal(10,2) DEFAULT NULL COMMENT '用户下单系统抽佣比例(%)',
  `merchant_meal_preparation_time` decimal(10,2) DEFAULT NULL COMMENT '商家备餐时间(分钟)',
  `member_withdraw_fee` decimal(10,2) NOT NULL COMMENT '用户提现手续费(%)',
  `registration_reward_invite_reward_amount` decimal(10,2) NOT NULL COMMENT '新用户注册奖励-邀请新用户注册奖励金额',
  `member_withdraw_meet_amount` decimal(10,2) NOT NULL COMMENT '奖励金累计到(≥)XX元可以提现/用户的邀请新用户注册奖励金额满足多少方可提现',
  `member_withdraw_audit_threshold` decimal(10,2) NOT NULL COMMENT '用户提现金额达到(≥)XX元需要人工审核/用户提现金额需审核门槛/用户的邀请新用户注册奖励金额高于多少时需要审核(不包含该金额)',
  `customer_service_phone` varchar(11) DEFAULT NULL COMMENT '客服电话',
  `customer_service_wechat` varchar(50) DEFAULT NULL COMMENT '客服微信',
  `customer_service_wechat_qrcode` varchar(1024) DEFAULT NULL COMMENT '客服微信二维码',
  `freight_insurance_paid_amount` decimal(10,2) DEFAULT NULL COMMENT '(预留字段)运费险赔付金额',
  `invitee_consume_commission` decimal(10,2) NOT NULL COMMENT '被邀请注册的用户下单后，平台邀请佣金占订单金额的百分比(%)--该佣金发放到邀请人的邀请新用户注册奖励金额',
  `caseone_own_commission` decimal(10,2) NOT NULL COMMENT '无上级邀请人时，下单用户佣金占比(%)',
  `casetwo_own_commission` decimal(10,2) NOT NULL COMMENT '有1个上级邀请人时，下单用户佣金占比(%)',
  `casetwo_first_level_inviter_commission` decimal(10,2) NOT NULL COMMENT '有1个上级邀请人时，一级邀请人佣金占比(%)',
  `casethree_own_commission` decimal(10,2) NOT NULL COMMENT '有2个上级邀请人，下单用户佣金占比(%)',
  `casethree_first_level_inviter_commission` decimal(10,2) NOT NULL COMMENT '有2个上级邀请人，一级邀请人佣金占比(%)',
  `casethree_second_level_inviter_commission` decimal(10,2) NOT NULL COMMENT '有2个上级邀请人，二级邀请人佣金占比(%)',
  `points_mall_invitee_consume_commission` decimal(10,2) NOT NULL COMMENT '积分商城--被邀请注册的用户下单后，平台邀请佣金占订单金额的百分比(%)--该佣金发放到邀请人的邀请新用户注册奖励金额',
  `points_mall_caseone_own_commission` decimal(10,2) NOT NULL COMMENT '积分商城--无上级邀请人时，下单用户佣金占比(%)',
  `points_mall_casetwo_own_commission` decimal(10,2) NOT NULL COMMENT '积分商城--有1个上级邀请人时，下单用户佣金占比(%)',
  `points_mall_casetwo_first_level_inviter_commission` decimal(10,2) NOT NULL COMMENT '积分商城--有1个上级邀请人时，一级邀请人佣金占比(%)',
  `points_mall_casethree_own_commission` decimal(10,2) NOT NULL COMMENT '积分商城--有2个上级邀请人，下单用户佣金占比(%)',
  `points_mall_casethree_first_level_inviter_commission` decimal(10,2) NOT NULL COMMENT '积分商城--有2个上级邀请人，一级邀请人佣金占比(%)',
  `points_mall_casethree_second_level_inviter_commission` decimal(10,2) NOT NULL COMMENT '积分商城--有2个上级邀请人，二级邀请人佣金占比(%)',
  `invite_friends_activity_rule` text COMMENT '分享邀请好友-活动规则',
  `commission_rule` text COMMENT '佣金奖励规则',
  `vip_rule` text COMMENT '会员规则',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='基础数据设置表';

-- ----------------------------
-- Records of tb_setting
-- ----------------------------
INSERT INTO `tb_setting` VALUES ('1', '1.00', '0.00', '16', '1', '1.00', '20.00', '1.50', '2.00', '1.00', '10.00', '0.00', '10.00', '1.00', '5.00', '10.00', '100.00', '18073705410', 'mflele01', 'data/images/admin/1/deerspot_1619350651214.jpg', null, '3.00', '100.00', '60.00', '40.00', '50.00', '30.00', '20.00', '10.00', '100.00', '60.00', '40.00', '50.00', '30.00', '20.00', '1、活动参与者为全网用户\r\n2、邀请者成功邀请一名本平台未进行注册登录用户首次注册登录即邀请者与被邀请者各自得到一张等价面额优惠券\r\n3、邀请者优惠券叠加奖励\r\n4、优惠券不可以任何方式赠与第三方人员\r\n5、优惠券仅限外卖单品使用，无其它用途\r\n6、如发生订单退款，1分钟内退款优惠券原路退回；一分钟后根据退款原因是否退回\r\n7、邀请者成为会员后可以享受三级被邀请者下单现金返利，可提现，不可以任何方式赠送给第三方。\r\n8、以上条例最终解释权归鹿点所所有，如发生纠纷，请联系客服协商解决。', '零钱奖励规则：\r\n一、佣金结算方式和使用\r\n      1、客户在平台的每笔订单交易完成后佣金发放在零钱钱包；\r\n      2、必须是平台会员才享有订单完成返利；\r\n      3、订单如发生退款，该笔订单将不再享受返利；\r\n      4、会员账户在30天内没有在平台有任何完成消费行为，则该账户30天内所有返利佣金清零，不可提现，直到再次消费则从新开始计算返利；\r\n5、零钱不可在平台二次消费。\r\n二、零钱到账周期和提现方式\r\n      1、每月25日结算会员账户已完成订单（商城）25天/（外卖）7天以上的返利，系统将自动发放到账户可提现零钱。\r\n2、会员用户发生零钱提现系统通过审核后将直接发放在登陆账户微信，提现金额大于500元需要客服人工审核', '会员规则：\r\n1、会员开通后有效期为永久；\r\n2、会员享受邀请人、被邀请人以及以下三级完成订单返利；\r\n3、充值金额享受支付折扣全额到账，并根据充值金额不同赠送不同面额优惠券', '2020-04-05 07:05:17', '2023-11-10 15:35:55');

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` int(11) NOT NULL COMMENT '商家id',
  `name` varchar(100) DEFAULT NULL COMMENT '门店名称',
  `code` varchar(50) DEFAULT NULL COMMENT '门店编号',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(100) DEFAULT NULL COMMENT '区/县',
  `street` varchar(50) DEFAULT NULL COMMENT '详细地址',
  `is_operating` tinyint(1) DEFAULT '1' COMMENT '店铺是否营业',
  `start_time` varchar(50) DEFAULT NULL COMMENT '店铺营业开始时间',
  `end_time` varchar(50) DEFAULT NULL COMMENT '店铺营业结束时间',
  `manage_primary` varchar(50) DEFAULT NULL COMMENT '经营主要类目',
  `manage_minor` varchar(50) DEFAULT NULL COMMENT '经营次要类目',
  `shop_img` varchar(128) DEFAULT NULL COMMENT '门脸照片',
  `shop_within_img` varchar(1024) DEFAULT NULL COMMENT '店内照片',
  `shop_logo_img` varchar(128) DEFAULT NULL COMMENT '门店LOGO',
  `certificate_type1` varchar(50) DEFAULT NULL COMMENT '证件类型1（1=营业执照、2=事业单位法证书、3=民办非企业单位登记证书、4=社会团体法人登记证书、5=三小类许可证）',
  `certificate_img1` varchar(128) DEFAULT NULL COMMENT '证件照片1',
  `certificate_type2` varchar(50) DEFAULT NULL COMMENT '证件类型2(1=食品经营许可证、2=食品流通许可证、3=三小类许可证、4=药品经营许可证)',
  `certificate_img2` varchar(128) DEFAULT NULL COMMENT '证件照片2',
  `special_type` varchar(50) DEFAULT NULL COMMENT '特殊资质证件类型（1=真食品许可证、2=酒类商品许可证）',
  `special_img` varchar(128) DEFAULT NULL COMMENT '特殊资质证件照片',
  `audit_status` int(2) DEFAULT '1' COMMENT '审批状态（1=审核中，2=审核成功，3=审核失败）',
  `audit_reason` varchar(50) DEFAULT NULL COMMENT '审批失败原因',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `take_out_phone` varchar(20) DEFAULT NULL COMMENT '(废弃字段)外卖电话',
  `contact_realname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `announcement` varchar(100) DEFAULT NULL COMMENT '门店公告',
  `brief_introduction` text COMMENT '门店简介',
  `start_delivery_price` decimal(10,2) DEFAULT '0.00' COMMENT '(废弃字段)起送价格',
  `delivery_starting_price` decimal(10,2) DEFAULT '0.00' COMMENT '(废弃字段)配送起步价(0~1KM)',
  `delivery_kilometer_price` decimal(10,2) DEFAULT '0.00' COMMENT '(废弃字段)配送公里价(元/KM)',
  `delivery_distance_limit` decimal(10,2) DEFAULT '0.00' COMMENT '(废弃字段)配送距离上限',
  `service_rating` decimal(10,1) DEFAULT '0.0' COMMENT '服务评级',
  `business_license` varchar(50) DEFAULT NULL COMMENT '营业执照',
  `id_card_front_side` varchar(50) DEFAULT NULL COMMENT '身份证正面',
  `id_card_back_side` varchar(50) DEFAULT NULL COMMENT '身份证反面',
  `status` int(2) DEFAULT '1' COMMENT '状态 1=待上架 2=已上架 3=已下架',
  `reduced_delivery_price` decimal(10,2) DEFAULT '0.00' COMMENT '配送费立减金额',
  `is_open_order_audio` tinyint(1) DEFAULT '1' COMMENT '是否开启订单语音提醒',
  `is_open_local_print` tinyint(1) DEFAULT '1' COMMENT '是否开启本地自动打印',
  `is_open_cloud_print` tinyint(1) DEFAULT '1' COMMENT '是否开启云打印',
  `first_poster` varchar(128) DEFAULT NULL COMMENT '店铺详情页第一栏海报',
  `house_number` varchar(100) DEFAULT NULL COMMENT '门牌号',
  `longitude` decimal(18,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,6) DEFAULT NULL COMMENT '纬度',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='门店表';

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES ('13', '24', '鹿点(辰泰园区体验店)', '', '湖南', '长沙', '岳麓区', '长沙市岳麓区辰泰科技园-D座', '1', '0:00', '23:59', '奶茶', '', '', 'data/images/merchant/18/deerspot_1600161019310.jpg', 'data/images/merchant/18/deerspot_1600160993785.jpg', '', '', '', '', '', '', '2', '', '2020-09-15 17:11:17', '', '暹罗', '13121865386', '营业时间：0:00-23:59，欢迎各位同学进店，如有疑问，请致电联系客服哟', '1', '20.00', '0.00', '0.00', '0.00', '4.8', '', '', '', '2', '0.00', '0', '0', '0', '', '一楼', '112.883120', '28.236586', '2020-09-15 17:05:56', '2023-11-23 11:05:44');
INSERT INTO `tb_shop` VALUES ('14', '19', null, null, null, null, null, null, '1', '0:00', '23:59', null, null, null, null, null, null, '', null, '', null, '', '3', '请填写详细信息', '2021-05-18 11:38:16', null, '暹罗', '13121865386', null, null, '0.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '0.00', '1', '1', '1', null, null, null, null, '2020-09-16 16:57:03', '2020-09-16 16:57:03');
INSERT INTO `tb_shop` VALUES ('15', '20', '小气茶（奥林匹克店）', '', '湖南', '长沙', '岳麓区', '长沙市岳麓区潇湘奥林匹克花园', '1', '0:00', '23:59', '奶茶/糕点', '', '', 'data/images/merchant/20/deerspot_1602063036566.jpg', 'data/images/merchant/20/deerspot_1600788526211.jpg', '', '', '', '', '', '', '2', '', '2020-09-22 22:21:12', '', '暹罗', '13121865386', '营业时间：10:00-22:00，新推出爆款老虎班茶，如有疑问，请来电咨询哟17378131291', '“小气茶”自创立开始，始终坚持口感至上，旨在用匠心为您还原一杯好茶，以奶霜茶与水果茶为主打，通过新颖的萃取方式，打造一杯“知心”茶饮，为您的生活重新定义。在这里，我们不仅是一杯奶茶，更是一种全新的生活方式，捧上一杯符合自己心情的“小气茶”，让您“饮在此，乐在此” ！', '20.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '0.00', '0', '1', '0', 'data/images/merchant/20/deerspot_1600789239415.jpg', '13、15栋-140号', '112.876846', '28.236901', '2020-09-22 22:11:51', '2021-07-01 18:39:26');
INSERT INTO `tb_shop` VALUES ('16', '21', '满杯烧仙草（尖山小区店）', '', '湖南', '长沙', '岳麓区', '长沙市岳麓区尖山安置小区', '1', '0:00', '23:59', '奶茶', '', '', 'data/images/merchant/21/deerspot_1602146334499.jpg,data/images/merchant/21/deerspot_1602146349974.jpg', 'data/images/merchant/21/deerspot_1602146134395.jpg', '', '', '', '', '', '', '2', '', '2020-10-08 16:24:28', '', '暹罗', '13121865386', '营业时间：10:00-23:59,本店爆款-白桃乌龙拿铁，如在用餐过程中遇到任何问题，请致电13723860098咨询', '满杯烧仙草相信很多朋友都十分的熟悉，这是一个有着十多年历史的老品牌，一直以满杯用料著称的“满杯烧仙草”如今更是不断升级，料更扎实，口感更出众。满满一杯的仙草冻、蜜红豆、珍珠、椰果、花生碎和葡萄干，让消费者吃个过瘾。一根吸管搭配一个勺子，随便舀一勺都是料，喝光后再用勺子把底料收进胃囊，瞬间让人一种满满的幸福感。对于品质的要求是十分严格的。每天早上先将仙草汁熬上两个小时，再常温凝固成仙草冻，点单时才将其捣碎，每一块都细腻嫩滑。丰富的配料，现打奶盖等，都可以任意添加，更好的满足了消费者的不同口味。', '20.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '0.00', '1', '0', '0', 'data/images/merchant/21/deerspot_1602159577496.jpg', '26栋3号', '112.890548', '28.257409', '2020-10-08 16:19:37', '2021-07-01 18:39:24');
INSERT INTO `tb_shop` VALUES ('17', '22', '高杰私厨（尖山小区店）', '', '湖南', '长沙', '岳麓区', '长沙市岳麓区尖山安置小区', '1', '0:00', '23:59', '快餐', '', '', 'data/images/merchant/22/deerspot_1602220708571.jpg', 'data/images/merchant/22/deerspot_1602220393310.jpg', '', '', '', '', '', '', '2', '', '2020-10-09 10:06:53', '', '暹罗', '13121865386', '1', '1', '20.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '0.00', '1', '1', '1', '', '2栋', '112.890548', '28.257409', '2020-10-09 10:02:53', '2021-06-03 23:28:10');
INSERT INTO `tb_shop` VALUES ('18', '23', '橘小胖（商务学院店）', '', '湖南', '长沙', '岳麓区', '长沙市岳麓区天顶街道永燕新村村民委员会', '1', '0:00', '23:59', '奶茶', '', '', 'data/images/merchant/23/deerspot_1602672854049.jpg,data/images/merchant/23/deerspot_1602672882737.jpg', 'data/images/merchant/23/deerspot_1602489850491.jpg', '', '', '', '', '', '', '2', '', '2020-10-11 19:07:24', '', '暹罗', '13121865386', '用心做好每一杯奶茶', '本店新开业，如果有以为请致电18613960160咨询', '15.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '1.00', '1', '0', '0', 'data/images/merchant/23/deerspot_1625136201854.jpg', '927号', '112.900743', '28.236961', '2020-10-11 19:04:31', '2021-07-01 21:57:16');
INSERT INTO `tb_shop` VALUES ('19', '18', '超级奶爸（辰泰科技园D栋）', '', '', '', '', '长沙市岳麓区辰泰科技园-D座', '1', '0:00', '23:59', '奶茶', '', '', 'data/images/merchant/24/deerspot_1618322492097.png', 'data/images/merchant/24/deerspot_1614152249688.jpg', '', '', '', '', '', '', '2', '', '2021-02-24 15:38:00', '', '暹罗', '13121865386', '1', '新店上新，多多支持', '20.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '1.00', '1', '1', '1', '', '', '112.883120', '28.236586', '2021-02-21 14:57:02', '2023-11-10 16:24:37');
INSERT INTO `tb_shop` VALUES ('34', '39', null, null, null, null, null, null, '1', '0:00', '23:59', null, null, null, null, null, null, '', null, '', null, '', '1', null, null, null, '暹罗', '13121865386', null, null, '0.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '0.00', '1', '1', '1', null, null, null, null, '2021-10-21 14:52:10', '2021-10-21 14:52:10');
INSERT INTO `tb_shop` VALUES ('35', '40', '1111', null, null, null, null, '中山市中山市大信·新都汇118广场', '1', '0:00', '23:59', '1111', null, null, 'data/images/merchant/40/siam_1700027642922.jpg', 'data/images/merchant/40/siam_1700027639926.jpg', null, '', null, '', null, '', '3', '信息不完整', '2023-11-10 15:24:25', null, '暹罗', '13121865386', '111', null, '20.00', '0.00', '0.00', '0.00', '0.0', 'data/images/merchant/40/siam_1700027646276.png', 'data/images/merchant/40/siam_1700027649529.png', 'data/images/merchant/40/siam_1700027652711.jpg', '2', '0.00', '1', '1', '1', null, '222', '113.251926', '22.676964', '2021-10-21 16:06:14', '2023-11-15 13:54:28');
INSERT INTO `tb_shop` VALUES ('36', '41', null, null, null, null, null, null, '1', '0:00', '23:59', null, null, null, null, null, null, '', null, '', null, '', '2', null, '2023-11-10 15:24:12', null, '暹罗', '13121865386', null, null, '0.00', '0.00', '0.00', '0.00', '0.0', '', '', '', '2', '0.00', '1', '1', '1', null, null, null, null, '2021-10-21 16:44:12', '2023-11-22 17:28:26');

-- ----------------------------
-- Table structure for tb_shop_change_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_change_record`;
CREATE TABLE `tb_shop_change_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '门店信息id',
  `name` varchar(100) DEFAULT NULL COMMENT '门店名称',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(100) DEFAULT NULL COMMENT '区/县',
  `street` varchar(50) DEFAULT NULL COMMENT '详细地址(具体到街道门牌号)',
  `manage_primary` varchar(50) DEFAULT NULL COMMENT '经营主要类目',
  `manage_minor` varchar(50) DEFAULT NULL COMMENT '经营次要类目',
  `shop_img` varchar(50) DEFAULT NULL COMMENT '门脸照片',
  `shop_within_img` varchar(1024) DEFAULT NULL COMMENT '店内照片',
  `shop_logo_img` varchar(50) DEFAULT NULL COMMENT '门店LOGO',
  `certificate_type1` varchar(50) DEFAULT NULL COMMENT '证件类型1（1=营业执照、2=事业单位法证书、3=民办非企业单位登记证书、4=社会团体法人登记证书、5=三小类许可证）',
  `certificate_img1` varchar(50) DEFAULT NULL COMMENT '证件照片1',
  `certificate_type2` varchar(50) DEFAULT NULL COMMENT '证件类型2(1=食品经营许可证、2=食品流通许可证、3=三小类许可证、4=药品经营许可证)',
  `certificate_img2` varchar(50) DEFAULT NULL COMMENT '证件照片2',
  `special_type` varchar(50) DEFAULT NULL COMMENT '特殊资质证件类型（1=真食品许可证、2=酒类商品许可证）',
  `special_img` varchar(50) DEFAULT NULL COMMENT '特殊资质证件照片',
  `take_out_phone` varchar(20) DEFAULT NULL COMMENT '(废弃字段)外卖电话',
  `contact_realname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `announcement` varchar(100) DEFAULT NULL COMMENT '门店公告',
  `brief_introduction` text COMMENT '门店简介',
  `business_license` varchar(50) DEFAULT NULL COMMENT '营业执照',
  `id_card_front_side` varchar(50) DEFAULT NULL COMMENT '身份证正面',
  `id_card_back_side` varchar(50) DEFAULT NULL COMMENT '身份证反面',
  `house_number` varchar(100) DEFAULT NULL COMMENT '门牌号',
  `longitude` decimal(18,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,6) DEFAULT NULL COMMENT '纬度',
  `apply_change_content` varchar(500) DEFAULT NULL COMMENT '申请变更内容/标识哪些字段进行了变更',
  `audit_status` int(2) DEFAULT '1' COMMENT '审批状态（1=审核中，2=审核成功，3=审核失败）',
  `audit_reason` varchar(50) DEFAULT NULL COMMENT '审批失败原因',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='门店重要信息变更记录表';

-- ----------------------------
-- Records of tb_shop_change_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_shopping_cart`;
CREATE TABLE `tb_shopping_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `spec_list` varchar(1024) DEFAULT NULL COMMENT '商品规格 JSON数据',
  `number` int(11) DEFAULT '1' COMMENT '购买数量',
  `is_goods_exists` tinyint(1) DEFAULT '1' COMMENT '商品是否有效 0=无效 1=有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3777 DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- ----------------------------
-- Records of tb_shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for tb_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_sms_log`;
CREATE TABLE `tb_sms_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `type` varchar(20) DEFAULT NULL COMMENT '短信类型 注册=register 登录=login 验证手机号=verification 找回密码=findpwd 自提提醒=extractRemind',
  `verify_code` varchar(10) DEFAULT NULL COMMENT '短信验证码',
  `ip` varchar(15) NOT NULL COMMENT '请求ip',
  `state` int(2) NOT NULL DEFAULT '1' COMMENT '发送状态 1=发送成功 2=发送失败',
  `description` varchar(50) DEFAULT NULL COMMENT 'API调用返回信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='短信验证码记录表';

-- ----------------------------
-- Records of tb_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_system_usage_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_usage_record`;
CREATE TABLE `tb_system_usage_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `type` varchar(20) DEFAULT NULL COMMENT '类型 intoShop=进入店铺 intoPointsMall=进入积分商城',
  `ip` varchar(15) DEFAULT NULL COMMENT '请求ip',
  `imei` varchar(100) DEFAULT NULL COMMENT '国际移动设备标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2379 DEFAULT CHARSET=utf8 COMMENT='系统使用记录表';

-- ----------------------------
-- Records of tb_system_usage_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_vip_recharge_denomination
-- ----------------------------
DROP TABLE IF EXISTS `tb_vip_recharge_denomination`;
CREATE TABLE `tb_vip_recharge_denomination` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) DEFAULT NULL COMMENT '充值面额名称(预留字段)',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '售价',
  `is_sale` tinyint(1) unsigned DEFAULT '0' COMMENT '是否开启促销 0=否 1=是(预留字段)',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '折扣价(预留字段)',
  `brief_description` varchar(50) DEFAULT NULL COMMENT '简短展示文本/简短奖励描述(预留字段)',
  `description` varchar(500) DEFAULT NULL COMMENT '充值面额描述/充值面额优惠活动奖励描述',
  `is_give_balance` tinyint(1) unsigned DEFAULT '0' COMMENT '充值后是否赠送余额 0=否 1=是',
  `give_balance` decimal(10,2) DEFAULT '0.00' COMMENT '赠送余额数量',
  `is_give_coupons` tinyint(1) unsigned DEFAULT '0' COMMENT '充值后是否赠送优惠券 0=否 1=是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_vip_recharge_denomination
-- ----------------------------
INSERT INTO `tb_vip_recharge_denomination` VALUES ('1', '普通会员', '1.00', '1', '0.10', '热售中', '1、邀请好友享有邀请人三级内下单返利（包含邀请人）\n2、余额不扣除，下单可抵扣\n3、余额充值后不可退回', '0', '0.00', '0', '2021-02-22 10:06:33', '2021-07-10 16:43:30');
INSERT INTO `tb_vip_recharge_denomination` VALUES ('2', '黄金会员', '50.00', '1', '49.90', '最实惠', '1、邀请好友享有邀请人三级内下单返利（包含邀请人）\n2、余额不扣除，下单可抵扣\n3、余额充值后不可退回\n4、充值赠送优惠券\n    1、会员专享折扣券-5折*5张\n    2、会员专享折扣券-6折*4张\n    3、会员专享折扣券-7折*3张\n    4、会员专享折扣券-8折*2张（仅外卖使用，有效期一年）', '0', '100.00', '1', '2021-02-22 22:09:06', '2021-07-10 16:43:36');
INSERT INTO `tb_vip_recharge_denomination` VALUES ('3', '钻石会员', '100.00', '1', '99.00', '力度大', '1、邀请好友享有邀请人三级内下单返利（包含邀请人）\n2、余额不扣除，下单可抵扣\n3、余额充值后不可退回\n4、充值赠送优惠券\n    1、会员专享折扣券-4折*5张\n    2、会员专享折扣券-5折*4张\n    3、会员专享折扣券-6折*3张\n    4、会员专享折扣券-7折*2张\n    5、会员专享折扣券-8折*1张（仅外卖使用，有效期一年）', '0', '399.00', '1', '2021-03-10 21:12:10', '2021-07-01 22:15:12');

-- ----------------------------
-- Table structure for tb_vip_recharge_denomination_coupons_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_vip_recharge_denomination_coupons_relation`;
CREATE TABLE `tb_vip_recharge_denomination_coupons_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `vip_recharge_denomination_id` int(11) DEFAULT NULL COMMENT '会员充值面额id',
  `coupons_id` int(11) DEFAULT NULL COMMENT '赠送优惠卷id',
  `give_quantity` int(11) DEFAULT '1' COMMENT '赠送优惠券数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_vip_recharge_denomination_coupons_relation
-- ----------------------------
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('21', '2', '57', '5', '2021-06-25 23:43:30');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('22', '2', '56', '4', '2021-06-25 23:43:30');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('23', '2', '54', '2', '2021-06-25 23:43:30');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('24', '2', '53', '3', '2021-06-25 23:43:30');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('30', '3', '54', '1', '2023-11-10 17:59:45');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('31', '3', '55', '2', '2023-11-10 17:59:45');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('32', '3', '57', '4', '2023-11-10 17:59:45');
INSERT INTO `tb_vip_recharge_denomination_coupons_relation` VALUES ('33', '3', '56', '3', '2023-11-10 17:59:45');

-- ----------------------------
-- Table structure for tb_vip_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_vip_recharge_record`;
CREATE TABLE `tb_vip_recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` varchar(50) DEFAULT NULL COMMENT '订单编号，供客户查询',
  `channel` int(10) DEFAULT NULL COMMENT '充值渠道 1=小程序 2=商家端 3=调度后台',
  `denomination_id` int(11) DEFAULT NULL COMMENT '会员充值面额id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '充值费用',
  `denomination_name` varchar(50) DEFAULT NULL COMMENT '充值面额名称',
  `denomination_price` decimal(10,2) DEFAULT '0.00' COMMENT '售价',
  `denomination_is_sale` tinyint(1) unsigned DEFAULT '0' COMMENT '是否开启促销 0=否 1=是',
  `denomination_sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '折扣价',
  `denomination_is_give_balance` tinyint(1) unsigned DEFAULT '0' COMMENT '充值后是否赠送余额 0=否 1=是',
  `denomination_give_balance` decimal(10,2) DEFAULT '0.00' COMMENT '赠送余额数量',
  `denomination_is_give_coupons` tinyint(1) unsigned DEFAULT '0' COMMENT '充值后是否赠送优惠券 0=否 1=是',
  `denomination_give_coupons_description` varchar(500) DEFAULT NULL COMMENT '赠送的优惠券描述',
  `denomination_give_coupons_json` varchar(1024) DEFAULT NULL COMMENT '赠送的优惠券(JSON数据)',
  `trade_id` int(11) DEFAULT NULL COMMENT '用户交易id',
  `status` int(2) DEFAULT '1' COMMENT '交易状态 1=待支付 2=支付成功 3=支付失败 4=交易超时自动关闭',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='会员充值记录表';

-- ----------------------------
-- Records of tb_vip_recharge_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_wx_public_platform_subscribe_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_wx_public_platform_subscribe_user`;
CREATE TABLE `tb_wx_public_platform_subscribe_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `subscribe` varchar(100) DEFAULT NULL COMMENT '是否订阅/关注',
  `openid` varchar(100) DEFAULT NULL COMMENT 'openid',
  `nickname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `sex` varchar(100) DEFAULT NULL COMMENT '性别',
  `language` varchar(100) DEFAULT NULL COMMENT '语言',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `province` varchar(100) DEFAULT NULL COMMENT '省会',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `headimgurl` varchar(300) DEFAULT NULL COMMENT '头像',
  `subscribe_time` varchar(100) DEFAULT NULL COMMENT '订阅时间/关注时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `groupid` varchar(100) DEFAULT NULL COMMENT '组id',
  `tagid_list` varchar(100) DEFAULT NULL COMMENT '标签列表',
  `subscribe_scene` varchar(100) DEFAULT NULL COMMENT '订阅/关注的场景',
  `qr_scene` varchar(100) DEFAULT NULL COMMENT '二维码场景',
  `qr_scene_str` varchar(100) DEFAULT NULL COMMENT '二维码场景字符串',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_wx_public_platform_subscribe_user
-- ----------------------------

-- ----------------------------
-- Table structure for transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `transaction_log`;
CREATE TABLE `transaction_log` (
  `id` varchar(32) NOT NULL COMMENT '事务ID',
  `business` varchar(32) NOT NULL COMMENT '业务标识',
  `foreign_key` varchar(32) NOT NULL COMMENT '对应业务表中的主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='事务日志表';

-- ----------------------------
-- Records of transaction_log
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------




-- ----------------------------
-- Table structure for tb_rawmaterial
-- ----------------------------
DROP TABLE IF EXISTS `tb_rawmaterial`;
CREATE TABLE `tb_rawmaterial` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) NOT NULL COMMENT '原料名称',
  `main_image` varchar(128) DEFAULT NULL COMMENT '原料主图',
  `description` varchar(512) DEFAULT NULL COMMENT '原料描述',
  `unit` varchar(50) NOT NULL COMMENT '采购单位',
  `price` decimal(20,10) DEFAULT '0.0000000000' COMMENT '采购单价',
  `stock` decimal(20,10) DEFAULT '0.0000000000' COMMENT '库存',
  `stock_lower_limit` decimal(20,10) DEFAULT '0.0000000000' COMMENT '库存过低线/库存下限',
  `stock_upper_limit` decimal(20,10) DEFAULT '0.0000000000' COMMENT '库存超出线/库存上限',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='原料表';


-- ----------------------------
-- Table structure for tb_goods_rawmaterial_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_rawmaterial_relation`;
CREATE TABLE `tb_goods_rawmaterial_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `rawmaterial_id` int(11) DEFAULT NULL COMMENT '原料id',
  `consumed_quantity` decimal(10,2) DEFAULT '0.00' COMMENT '耗量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='原料配比表/商品原料关联表';


