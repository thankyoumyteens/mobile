DROP DATABASE IF EXISTS `mobile`;
CREATE DATABASE `mobile` DEFAULT CHARACTER SET utf8;

use `mobile`;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;



-- ----------------------------
--  收藏
-- ----------------------------
DROP TABLE IF EXISTS `mobile_favorite`;
CREATE TABLE `mobile_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `seller_id` bigint(20) DEFAULT NULL COMMENT '卖家Id',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺Id',
  `type` int(5) NOT NULL COMMENT '收藏类型, 0-商品, 1-店铺',
  `status` int(5) DEFAULT NULL COMMENT '处理状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  消息
-- ----------------------------
DROP TABLE IF EXISTS `mobile_message`;
CREATE TABLE `mobile_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_id` bigint(20) NOT NULL COMMENT '发送方Id',
  `to_id` bigint(20) DEFAULT NULL COMMENT '接收方Id',
  `type` int(5) DEFAULT NULL COMMENT '消息类型',
  `status` int(5) DEFAULT NULL COMMENT '处理状态',
  `message` varchar(100) COMMENT '消息正文',
  `data` text DEFAULT NULL COMMENT '附加数据',
  `send_time` datetime DEFAULT NULL COMMENT '消息发送时间',
  `receive_time` datetime DEFAULT NULL COMMENT '消息接收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  购物车
-- ----------------------------
DROP TABLE IF EXISTS `mobile_cart`;
CREATE TABLE `mobile_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `quantity` bigint(20) DEFAULT NULL COMMENT '数量',
  `detail` text COMMENT '商品参数 json',
  `checked` bigint(20) DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  商品分类
-- ----------------------------
DROP TABLE IF EXISTS `mobile_category`;
CREATE TABLE `mobile_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `img` varchar(500) DEFAULT NULL COMMENT '类别图片',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常,0-已废弃',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  订单
-- ----------------------------
DROP TABLE IF EXISTS `mobile_order`;
CREATE TABLE `mobile_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `shipping_id` bigint(20) DEFAULT NULL COMMENT '收货地址id',
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `postage` int(10) DEFAULT NULL COMMENT '运费,单位是元',
  `status` int(10) DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  订单项
-- ----------------------------
DROP TABLE IF EXISTS `mobile_order_item`;
CREATE TABLE `mobile_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `properties_id` bigint(20) DEFAULT NULL COMMENT '规格id',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  记录订单对应的卖家
-- ----------------------------
DROP TABLE IF EXISTS `mobile_order_seller`;
CREATE TABLE `mobile_order_seller` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `seller_id` bigint(20) DEFAULT NULL COMMENT '卖家id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  支付信息
-- ----------------------------
DROP TABLE IF EXISTS `mobile_pay_info`;
CREATE TABLE `mobile_pay_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  商品
-- ----------------------------

DROP TABLE IF EXISTS `mobile_goods`;
CREATE TABLE `mobile_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `seller_id` bigint(20) NOT NULL COMMENT '卖家id',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text DEFAULT NULL COMMENT '图片地址,json格式,扩展用',
  `status` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 规格
DROP TABLE IF EXISTS `mobile_goods_properties`;
CREATE TABLE `mobile_goods_properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品规格id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `name` varchar(100) DEFAULT NULL COMMENT '规格名称',
  `text` varchar(200) DEFAULT NULL COMMENT '规格',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text DEFAULT NULL COMMENT '图片地址,json格式,扩展用',
  `price` decimal(20,2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `stock` bigint(20) DEFAULT '0' COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 详情
DROP TABLE IF EXISTS `mobile_goods_detail`;
CREATE TABLE `mobile_goods_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品详情id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `images` text DEFAULT NULL COMMENT '图片地址,json格式,扩展用',
  `text` text NOT NULL COMMENT '详情 html格式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 评论
DROP TABLE IF EXISTS `mobile_goods_comment`;
CREATE TABLE `mobile_goods_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '买家id',
  `order_item_id` bigint(20) NOT NULL COMMENT '商品id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品规格id',
  `images` text DEFAULT NULL COMMENT '图片地址',
  `text` text DEFAULT NULL COMMENT '评论',
  `star` int(10) NOT NULL COMMENT '评星',
  `status` int(6) DEFAULT '1' COMMENT '评论状态.1-公开 2-匿名 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  收货地址
-- ----------------------------
DROP TABLE IF EXISTS `mobile_shipping`;
CREATE TABLE `mobile_shipping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  用户
-- ----------------------------
DROP TABLE IF EXISTS `mobile_user`;
CREATE TABLE `mobile_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户,2-商家',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 管理员账号
INSERT INTO `mobile`.`mobile_user`(`id`, `username`, `password`, `email`, `phone`, `nickname`, `avatar`, `question`, `answer`, `role`, `create_time`, `update_time`) VALUES (1, 'admin', '427338237BD929443EC5D48E24FD2B1A', 'admin@admin.com', '12345677901', '管理员', '1.jpg', '问题', '答案', 0, '2018-03-12 20:06:41', '2018-03-12 20:06:41');


SET FOREIGN_KEY_CHECKS = 1;
