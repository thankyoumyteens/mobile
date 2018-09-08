/*
 Navicat Premium Data Transfer

 Source Server         : ali
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 112.74.181.125:3306
 Source Schema         : mobile

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 23/08/2018 21:43:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mobile_cart
-- ----------------------------
DROP TABLE IF EXISTS `mobile_cart`;
CREATE TABLE `mobile_cart`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `quantity` bigint(20) NULL DEFAULT NULL COMMENT '数量',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品参数 json',
  `checked` bigint(20) NULL DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_index`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_cart
-- ----------------------------
INSERT INTO `mobile_cart` VALUES (22, 27, 1, 1, '1', 0, '2018-04-20 19:18:48', '2018-04-20 19:18:48');
INSERT INTO `mobile_cart` VALUES (23, 27, 1, 1, '7', 0, '2018-04-20 19:18:56', '2018-04-20 19:18:56');
INSERT INTO `mobile_cart` VALUES (24, 27, 1, 1, '2', 0, '2018-04-21 09:11:01', '2018-04-21 13:23:43');
INSERT INTO `mobile_cart` VALUES (26, 27, 2, 2, '14', 0, '2018-04-21 16:54:27', '2018-04-21 16:59:13');
INSERT INTO `mobile_cart` VALUES (27, 27, 1, 1, '8', 0, '2018-04-21 16:59:24', '2018-06-03 20:36:47');
INSERT INTO `mobile_cart` VALUES (28, 37, 1, 1, '4', 0, '2018-05-09 11:17:42', '2018-05-09 11:17:42');
INSERT INTO `mobile_cart` VALUES (29, 38, 1, 1, '4', 1, '2018-07-10 16:53:10', '2018-07-10 16:53:58');
INSERT INTO `mobile_cart` VALUES (30, 38, 1, 1, '1', 1, '2018-07-10 16:53:21', '2018-07-10 16:53:59');
INSERT INTO `mobile_cart` VALUES (31, 38, 2, 1, '23', 1, '2018-07-10 16:53:30', '2018-07-10 16:54:00');
INSERT INTO `mobile_cart` VALUES (32, 39, 1, 1, '9', 0, '2018-07-12 14:31:07', '2018-07-12 14:31:07');
INSERT INTO `mobile_cart` VALUES (33, 39, 3, 1, '15', 0, '2018-07-12 14:32:20', '2018-07-12 14:32:20');

-- ----------------------------
-- Table structure for mobile_category
-- ----------------------------
DROP TABLE IF EXISTS `mobile_category`;
CREATE TABLE `mobile_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别名称',
  `img` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别图片',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '类别状态1-正常,2-已废弃',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100042 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_category
-- ----------------------------
INSERT INTO `mobile_category` VALUES (100032, 0, '360', '360.jpg', 1, '2018-03-21 15:37:30', '2018-03-21 15:37:33');
INSERT INTO `mobile_category` VALUES (100033, 0, 'honor', 'honor.jpg', 1, '2018-03-21 15:54:43', '2018-03-21 15:54:48');
INSERT INTO `mobile_category` VALUES (100034, 0, 'oneplus', 'oneplus.jpg', 1, '2018-03-21 15:58:09', '2018-03-21 15:58:15');
INSERT INTO `mobile_category` VALUES (100035, 0, 'samsung', 'samsung.jpg', 1, '2018-03-21 15:59:03', '2018-03-21 15:59:07');
INSERT INTO `mobile_category` VALUES (100036, 0, 'vivo', 'vivo.jpg', 1, '2018-03-21 15:59:44', '2018-03-21 15:59:48');
INSERT INTO `mobile_category` VALUES (100037, 0, 'xiaomi', 'xiaomi.jpg', 1, '2018-03-21 16:00:13', '2018-03-21 16:00:18');
INSERT INTO `mobile_category` VALUES (100038, 0, '测试', '1.jpg', 0, '2018-03-21 20:04:30', '2018-03-21 20:15:13');
INSERT INTO `mobile_category` VALUES (100039, 0, '测试', '1.jpg', 1, '2018-03-21 20:10:48', '2018-03-21 20:10:48');
INSERT INTO `mobile_category` VALUES (100040, 0, '啊啊啊', '76ed25c3-c616-44a3-ba4a-36fdff79a1c8.jpg', 0, '2018-03-22 11:47:47', '2018-03-22 11:55:44');
INSERT INTO `mobile_category` VALUES (100041, 0, '踩踩踩', '3073bee9-2672-4ad2-9bf3-4613062a1780.jpg', 1, '2018-03-22 12:16:29', '2018-03-22 12:16:29');

-- ----------------------------
-- Table structure for mobile_favorite
-- ----------------------------
DROP TABLE IF EXISTS `mobile_favorite`;
CREATE TABLE `mobile_favorite`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品Id',
  `seller_id` bigint(20) NULL DEFAULT NULL COMMENT '卖家Id',
  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺Id',
  `type` int(5) NOT NULL COMMENT '收藏类型, 0-商品, 1-店铺',
  `status` int(5) NULL DEFAULT NULL COMMENT '处理状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_favorite
-- ----------------------------
INSERT INTO `mobile_favorite` VALUES (1, 27, 1, NULL, NULL, 0, NULL, '2018-06-03 18:47:12', '2018-06-03 18:47:12');
INSERT INTO `mobile_favorite` VALUES (2, 39, 3, NULL, NULL, 0, NULL, '2018-07-12 14:32:06', '2018-07-12 14:32:06');

-- ----------------------------
-- Table structure for mobile_goods
-- ----------------------------
DROP TABLE IF EXISTS `mobile_goods`;
CREATE TABLE `mobile_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `seller_id` bigint(20) NOT NULL COMMENT '卖家id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片地址,json格式,扩展用',
  `status` int(6) NULL DEFAULT 1 COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_goods
-- ----------------------------
INSERT INTO `mobile_goods` VALUES (1, 100032, 31, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', '4050mAh大电池/1600万后置双摄/5.99英寸全面屏/骁龙660', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', 1, '2018-04-17 16:24:52', '2018-05-01 17:14:32');
INSERT INTO `mobile_goods` VALUES (2, 100032, 31, 'zzz', 'asdwqwqw', '3fdb0bb3-8b2f-43a7-8775-fea8fe0eda1c.jpg', '394c5ba2-659a-4831-b479-81dd5cabe0f5.jpg,020081c1-d4b8-4828-bb8c-44240cfe66b2.jpg', 1, NULL, '2018-05-07 11:26:35');
INSERT INTO `mobile_goods` VALUES (3, 100032, 31, '360手机 N6 Lite 全网通 4GB+32GB 璀璨金 移动联通电信4G手机 双卡双待', '4020mAh大电池/1300万摄像头/5.5英寸全高清屏幕/骁龙630', '7332a564-f0d0-4b7f-8365-1ea7004c13fb.jpg', '507b9fef-5904-4f3e-87bf-ec2ce77a8df5.jpg,7b84b14f-f230-4c01-aa51-dff2219ed1a6.jpg,bc0f11ed-5327-4ecc-983e-88b72cae782d.jpg,5551bc14-8c02-4158-9032-940ab8db0f5a.jpg,9910122e-3346-44a9-b0f3-7f810dc60d75.jpg', 1, NULL, NULL);
INSERT INTO `mobile_goods` VALUES (4, 100032, 31, '360手机 F5 移动版 2GB+16GB 流光金 移动联通4G手机 双卡双待', '2G+16G内存/5英寸屏幕/2.5D弧形玻璃/前置指纹识别', 'e0dbafa4-37f9-4bc8-9212-84a3fee6c307.jpg', '14530f90-b8fb-4e14-84d1-87adb09c3b0f.jpg,6755d213-076f-4427-a848-c0891d427673.jpg,2d491eb7-d7b9-4ecd-a343-a4b36e5e4577.jpg,1c1265c2-e42e-434d-a394-d191623ff946.jpg', 1, NULL, '2018-04-28 12:42:14');
INSERT INTO `mobile_goods` VALUES (5, 100032, 31, 'adaas', 'xczxczc', '034eed88-33f0-4914-99b7-3ee9ab785e12.jpg', '4bc30514-c2f8-4f8e-a97b-f55b839d239a.jpg,35d20114-4300-4741-94c2-d27c656abf3f.jpg,', 1, NULL, '2018-04-29 15:44:40');
INSERT INTO `mobile_goods` VALUES (6, 100033, 31, 'aaaaaaa', 'xczxczxczxczxczzxczxzxc', 'f5ae4ca5-2e73-4182-9589-623dff11062c.jpg', '019e5758-9a56-411f-ac57-72eb3cd45f23.jpg,4ef88f55-3b25-405d-a4a4-4f1cbe44a91b.jpg,b5904d73-bd57-45f2-a567-7e9b69383668.jpg,', 1, '2018-04-29 15:59:59', '2018-05-07 14:27:06');
INSERT INTO `mobile_goods` VALUES (7, 100032, 31, 'aasasda', 'aASASASDASAS', '83613089-aa3f-469a-a83f-ff7ea6c57ce3.jpg', 'b04a2fbd-9d36-41ff-8b5c-d44d2eef390b.jpg,46fd6c6f-f6bf-47c0-94e6-f29b3d9049b6.jpg,54314492-9793-44af-801f-e2b92cd2a396.jpg,', 1, '2018-05-02 12:23:05', '2018-05-02 12:40:17');
INSERT INTO `mobile_goods` VALUES (8, 100032, 31, 'zzxzxxccv', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 1, '2018-05-02 12:23:49', '2018-05-02 12:41:37');
INSERT INTO `mobile_goods` VALUES (9, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 1, '2018-05-02 12:24:06', '2018-05-02 12:57:27');
INSERT INTO `mobile_goods` VALUES (10, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 1, '2018-05-02 12:24:22', '2018-05-02 12:59:21');
INSERT INTO `mobile_goods` VALUES (11, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:22', '2018-05-02 12:24:22');
INSERT INTO `mobile_goods` VALUES (12, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:22', '2018-05-02 12:24:22');
INSERT INTO `mobile_goods` VALUES (13, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:24', '2018-05-02 12:24:24');
INSERT INTO `mobile_goods` VALUES (14, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:24', '2018-05-02 12:24:24');
INSERT INTO `mobile_goods` VALUES (15, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:24', '2018-05-02 12:24:24');
INSERT INTO `mobile_goods` VALUES (16, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 1, '2018-05-02 12:24:24', '2018-05-02 13:02:14');
INSERT INTO `mobile_goods` VALUES (17, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:24', '2018-05-02 12:24:24');
INSERT INTO `mobile_goods` VALUES (18, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:25', '2018-05-02 12:24:25');
INSERT INTO `mobile_goods` VALUES (19, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:25', '2018-05-02 12:24:25');
INSERT INTO `mobile_goods` VALUES (20, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:25', '2018-05-02 12:24:25');
INSERT INTO `mobile_goods` VALUES (21, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 1, '2018-05-02 12:24:25', '2018-05-02 13:00:06');
INSERT INTO `mobile_goods` VALUES (22, 100032, 31, 'qeerterretr', 'rrtfgfgghghfgjj', 'd4021518-2517-41d8-aeca-0a9a7a508bbf.jpg', 'eee78bf2-0f27-4f32-8ef8-a655ea30e1ce.jpg,c38b89ad-6e95-4948-951e-28dc50f03bdf.jpg,', 2, '2018-05-02 12:24:25', '2018-05-02 12:24:25');

-- ----------------------------
-- Table structure for mobile_goods_comment
-- ----------------------------
DROP TABLE IF EXISTS `mobile_goods_comment`;
CREATE TABLE `mobile_goods_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '买家id',
  `order_item_id` bigint(20) NOT NULL COMMENT '商品id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品规格id',
  `images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片地址,json格式,扩展用',
  `text` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论',
  `star` int(10) NOT NULL COMMENT '评星',
  `status` int(6) NULL DEFAULT 1 COMMENT '评论状态.1-公开 2-匿名 3-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_goods_comment
-- ----------------------------
INSERT INTO `mobile_goods_comment` VALUES (1, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 5, 1, '2018-04-18 12:14:17', '2018-04-18 12:14:17');
INSERT INTO `mobile_goods_comment` VALUES (3, 27, 1, 2, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 5, 1, '2018-04-19 09:33:50', '2018-04-19 09:33:50');
INSERT INTO `mobile_goods_comment` VALUES (4, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 4, 1, '2018-04-19 10:02:20', '2018-04-19 10:02:20');
INSERT INTO `mobile_goods_comment` VALUES (5, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 3, 1, '2018-04-19 10:02:25', '2018-04-19 10:02:25');
INSERT INTO `mobile_goods_comment` VALUES (6, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 3, 1, '2018-04-19 10:02:26', '2018-04-19 10:02:26');
INSERT INTO `mobile_goods_comment` VALUES (7, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 2, 1, '2018-04-19 10:02:30', '2018-04-19 10:02:30');
INSERT INTO `mobile_goods_comment` VALUES (8, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 1, 1, '2018-04-19 10:02:35', '2018-04-19 10:02:35');
INSERT INTO `mobile_goods_comment` VALUES (9, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 1, 1, '2018-04-19 10:02:35', '2018-04-19 10:02:35');
INSERT INTO `mobile_goods_comment` VALUES (11, 27, 1, 1, '', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 5, 1, '2018-04-19 10:06:33', '2018-04-19 10:06:33');
INSERT INTO `mobile_goods_comment` VALUES (12, 27, 1, 1, '', '力挺这款手机，配置很高，玩游戏很实用，里面的有些使用我说不出来，但是我有些东西，是以前没遇到过的，360手机用良心说话，用品质给消费者或游戏党一些更高的体验，发货也很快，今天收到货后就迫不及待的打开了，比同款其他牌子手机实用，，360手机我这是买的第二部，n5s也不多，全面屏，大容量，这些都不说了，这款手机设计也比n5s新颖，后壳也特别漂亮，给人感觉很舒适，手感也不多，自认为比r11好很多，有同感的顶一下，', 5, 1, '2018-04-19 10:06:34', '2018-04-19 10:06:34');
INSERT INTO `mobile_goods_comment` VALUES (13, 27, 1, 1, '76550176-f604-4a24-b480-77d1fccf639c.jpg,79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg,673711b3-6a90-4b17-8603-726ae3c8d085.jpg,bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg,265a97c5-ed11-4d13-9304-c0f56578002e.jpg,49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', '', 5, 1, '2018-04-19 10:06:45', '2018-04-19 10:06:45');
INSERT INTO `mobile_goods_comment` VALUES (15, 27, 1, 1, '', '', 5, 1, '2018-04-19 10:06:50', '2018-04-19 10:06:50');
INSERT INTO `mobile_goods_comment` VALUES (16, 37, 1, 1, '', 'aaaxxx', 3, 1, '2018-05-01 22:00:37', '2018-05-01 22:00:37');
INSERT INTO `mobile_goods_comment` VALUES (17, 37, 3, 1, '848fdb76-11fc-41ef-a88e-6f901a882ab8.jpg,ff025c5d-0ffe-4d00-8889-809b93bd1031.jpg', 'asdasa', 5, 1, '2018-05-05 10:27:49', '2018-05-05 10:27:49');
INSERT INTO `mobile_goods_comment` VALUES (18, 37, 13, 1, '', 'aasdasda', 5, 1, '2018-05-09 11:18:53', '2018-05-09 11:18:53');
INSERT INTO `mobile_goods_comment` VALUES (19, 37, 14, 2, '', 'xzxzz', 5, 1, '2018-05-09 11:18:53', '2018-05-09 11:18:53');
INSERT INTO `mobile_goods_comment` VALUES (20, 37, 15, 3, 'd85600c0-dae9-4efc-b44f-a23064231608.jpg,62b269a8-5425-4536-b7c9-9ce762608575.jpg', '好好好', 5, 1, '2018-06-01 13:49:47', '2018-06-01 13:49:47');
INSERT INTO `mobile_goods_comment` VALUES (21, 37, 16, 4, '0ee76c15-ac2d-43aa-820d-992bbad784cb.jpg', '撒大苏打大大', 5, 1, '2018-06-01 13:49:47', '2018-06-01 13:49:47');
INSERT INTO `mobile_goods_comment` VALUES (22, 37, 13, 1, '', '', 5, 1, '2018-06-01 13:57:47', '2018-06-01 13:57:47');
INSERT INTO `mobile_goods_comment` VALUES (23, 37, 14, 2, '', '', 5, 1, '2018-06-01 13:57:47', '2018-06-01 13:57:47');

-- ----------------------------
-- Table structure for mobile_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `mobile_goods_detail`;
CREATE TABLE `mobile_goods_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品详情id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片地址,json格式,扩展用',
  `text` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详情 html格式',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_goods_detail
-- ----------------------------
INSERT INTO `mobile_goods_detail` VALUES (1, 1, NULL, '<p>详情1</p><p><img src=\"http://work2743.oss-cn-qingdao.aliyuncs.com/mobile/29e0fda6-78ae-4392-afd9-92370fa1dae3.jpg\"></p><p>aaa</p><p><img src=\"http://work2743.oss-cn-qingdao.aliyuncs.com/mobile/ce4bcc8b-a25f-48d3-ae7a-1df989905558.jpg\"></p><p>bbbc</p>', '2018-04-17 16:36:50', '2018-05-07 11:17:47');
INSERT INTO `mobile_goods_detail` VALUES (2, 2, NULL, '<p>aaa</p>', NULL, NULL);

-- ----------------------------
-- Table structure for mobile_goods_properties
-- ----------------------------
DROP TABLE IF EXISTS `mobile_goods_properties`;
CREATE TABLE `mobile_goods_properties`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品规格id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格名称',
  `text` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `main_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片地址,json格式,扩展用',
  `price` decimal(20, 2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `stock` bigint(20) NULL DEFAULT 0 COMMENT '库存数量',
  `status` int(6) NULL DEFAULT 1 COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_goods_properties
-- ----------------------------
INSERT INTO `mobile_goods_properties` VALUES (1, 1, '手机', '{\"颜色\":\"极夜黑\",\"机型\":\"裸机\",\"版本\":\"4GB+64GB\"}', '76550176-f604-4a24-b480-77d1fccf639c.jpg', NULL, 1900.00, 999, 1, '2018-04-17 16:28:32', '2018-05-05 09:31:36');
INSERT INTO `mobile_goods_properties` VALUES (2, 1, '手机', '{\"颜色\":\"极夜黑\",\"机型\":\"裸机\",\"版本\":\"6GB+64GB\"}', '79933b4d-ce31-443b-8f5e-f2475bd7c091.jpg', NULL, 2099.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-20 12:36:30');
INSERT INTO `mobile_goods_properties` VALUES (3, 1, '手机', '{\"颜色\":\"极夜黑\",\"机型\":\"裸机\",\"版本\":\"6GB+128GB\"}', '673711b3-6a90-4b17-8603-726ae3c8d085.jpg', NULL, 2599.00, 1000, 1, '2018-04-17 16:28:32', '2018-05-09 11:18:14');
INSERT INTO `mobile_goods_properties` VALUES (4, 1, '手机', '{\"颜色\":\"极夜黑\",\"机型\":\"音乐套装\",\"版本\":\"4GB+64GB\"}', 'bef0d377-5ea6-478e-a1fd-d7ab2f598f88.jpg', NULL, 1999.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-17 16:28:35');
INSERT INTO `mobile_goods_properties` VALUES (5, 1, '手机', '{\"颜色\":\"极夜黑\",\"机型\":\"音乐套装\",\"版本\":\"6GB+64GB\"}', '265a97c5-ed11-4d13-9304-c0f56578002e.jpg', NULL, 2199.00, 999, 1, '2018-04-17 16:28:32', '2018-05-02 14:11:29');
INSERT INTO `mobile_goods_properties` VALUES (6, 1, '手机', '{\"颜色\":\"极夜黑\",\"机型\":\"音乐套装\",\"版本\":\"6GB+128GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 2699.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-17 16:28:35');
INSERT INTO `mobile_goods_properties` VALUES (7, 1, '手机', '{\"颜色\":\"深海蓝\",\"机型\":\"裸机\",\"版本\":\"4GB+64GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 1899.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-17 16:28:35');
INSERT INTO `mobile_goods_properties` VALUES (8, 1, '手机', '{\"颜色\":\"深海蓝\",\"机型\":\"裸机\",\"版本\":\"6GB+64GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 2099.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-17 16:28:35');
INSERT INTO `mobile_goods_properties` VALUES (9, 1, '手机', '{\"颜色\":\"深海蓝\",\"机型\":\"裸机\",\"版本\":\"6GB+128GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 2599.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-17 16:28:35');
INSERT INTO `mobile_goods_properties` VALUES (10, 1, '手机', '{\"颜色\":\"深海蓝\",\"机型\":\"音乐套装\",\"版本\":\"4GB+64GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 1999.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-22 20:06:05');
INSERT INTO `mobile_goods_properties` VALUES (11, 1, '手机', '{\"颜色\":\"深海蓝\",\"机型\":\"音乐套装\",\"版本\":\"6GB+64GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 2199.00, 999, 1, '2018-04-17 16:28:32', '2018-04-27 08:51:08');
INSERT INTO `mobile_goods_properties` VALUES (12, 1, '手机', '{\"颜色\":\"深海蓝\",\"机型\":\"音乐套装\",\"版本\":\"6GB+128GB\"}', '49cb51e2-70e5-4272-8d11-9f7aaf28a3cc.jpg', NULL, 2699.00, 1000, 1, '2018-04-17 16:28:32', '2018-04-17 16:28:35');
INSERT INTO `mobile_goods_properties` VALUES (15, 3, NULL, '{\"颜色\":\"璀璨金\",\"版本\":\"裸机\"}', '7332a564-f0d0-4b7f-8365-1ea7004c13fb.jpg', NULL, 899.00, 999, 1, NULL, '2018-05-04 10:38:10');
INSERT INTO `mobile_goods_properties` VALUES (16, 3, NULL, '{\"颜色\":\"星空黑\",\"版本\":\"裸机\"}', '7248efb0-f5ad-4a88-861b-0c1602240399.jpg', NULL, 799.00, 100, 1, NULL, NULL);
INSERT INTO `mobile_goods_properties` VALUES (17, 4, NULL, '{\"颜色\":\"流光金\",\"版本\":\"F5移动低配版\"}', 'e0dbafa4-37f9-4bc8-9212-84a3fee6c307.jpg', NULL, 379.00, 499, 1, NULL, '2018-05-04 10:38:10');
INSERT INTO `mobile_goods_properties` VALUES (18, 4, NULL, '{\"颜色\":\"星空灰\",\"版本\":\"F4S\"}', '22beda91-a7ab-49e5-bdf0-621432afc768.jpg', NULL, 799.00, 199, 1, NULL, '2018-04-30 15:27:54');
INSERT INTO `mobile_goods_properties` VALUES (21, 5, '手机', '{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"}', '412a570b-75a5-4524-a2a3-099c55f9ca11.jpg', NULL, 999.00, 111, 1, NULL, NULL);
INSERT INTO `mobile_goods_properties` VALUES (22, 6, '手机', '{\"版本\":\"4GB+64GB\"}', 'b298dc4e-bc43-48ca-8c4d-77a219a9ea31.jpg', NULL, 9999.00, 1000, 1, NULL, NULL);
INSERT INTO `mobile_goods_properties` VALUES (23, 2, '手机', '{\"机型\":\"裸机\"}', '8b4da2e7-17fe-43d6-b654-36d9565dab16.jpg', NULL, 999.00, 998, 1, '2018-05-02 12:25:05', '2018-05-02 14:11:29');
INSERT INTO `mobile_goods_properties` VALUES (24, 2, '手机', '{\"机型\":\"套餐1\"}', 'd16c82b6-45ef-4f3e-a2a8-9b8acbd4ff02.jpg', NULL, 1000.00, 111, 1, '2018-05-02 12:25:27', '2018-05-02 12:25:27');
INSERT INTO `mobile_goods_properties` VALUES (25, 7, '手机', '{\"版本\":\"4GB\"}', '19416973-75f1-4834-b573-f8f10c86110f.jpg', NULL, 111.00, 111, 1, '2018-05-02 12:40:10', '2018-05-02 12:40:10');
INSERT INTO `mobile_goods_properties` VALUES (26, 8, '手机', '{\"颜色\":\"土豪金\"}', '54b591c0-e308-47da-9926-c40f556561be.jpg', NULL, 999.00, 111, 1, '2018-05-02 12:41:30', '2018-05-02 12:41:30');
INSERT INTO `mobile_goods_properties` VALUES (27, 9, '手机', '{\"版本\":\"裸机\"}', '6cc7ba83-6fa9-4468-9270-b9e2f21c52bf.jpg', NULL, 111.00, 11, 1, '2018-05-02 12:57:19', '2018-05-02 12:57:19');
INSERT INTO `mobile_goods_properties` VALUES (28, 10, '手机', '{\"版本\":\"套餐2\"}', '6e1c2d02-8a8f-4666-936f-232d7ab5e3e9.jpg', NULL, 1111.00, 111, 1, '2018-05-02 12:59:15', '2018-05-02 12:59:15');
INSERT INTO `mobile_goods_properties` VALUES (29, 21, '手机', '{\"啦啦啦\":\"啊啊啊\"}', '0025fb49-deb0-42f7-863b-8b9bf6001d89.jpg', NULL, 111.00, 1, 1, '2018-05-02 12:59:54', '2018-05-02 12:59:54');
INSERT INTO `mobile_goods_properties` VALUES (30, 16, '手机', '{\"zzz\":\"qqq\"}', 'd56a9c55-990d-49c5-aa85-bb092c22d2af.jpg', NULL, 111.00, 11, 1, '2018-05-02 13:02:06', '2018-05-02 13:02:06');
INSERT INTO `mobile_goods_properties` VALUES (31, 2, '手机', '{\"机型\":\"xxx\"}', '4e8369dc-c9af-4a7b-a6f5-4cf04570ad38.jpg', NULL, 111.00, 11, 1, '2018-05-07 11:26:13', '2018-05-07 11:26:13');

-- ----------------------------
-- Table structure for mobile_message
-- ----------------------------
DROP TABLE IF EXISTS `mobile_message`;
CREATE TABLE `mobile_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_id` bigint(20) NOT NULL COMMENT '发送方Id',
  `to_id` bigint(20) NULL DEFAULT NULL COMMENT '接收方Id',
  `type` int(5) NULL DEFAULT NULL COMMENT '消息类型',
  `status` int(5) NULL DEFAULT NULL COMMENT '处理状态',
  `message` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息正文',
  `data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '附加数据',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '消息发送时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '消息接收时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_message
-- ----------------------------
INSERT INTO `mobile_message` VALUES (1, 27, 31, 1, 1, '提醒发货', '1524020811456', '2018-04-25 10:32:12', '2018-04-25 10:32:12');
INSERT INTO `mobile_message` VALUES (2, 37, 1, 1, 1, '提醒发货', '1524019304866', '2018-04-27 08:19:55', '2018-04-27 08:19:55');
INSERT INTO `mobile_message` VALUES (3, 37, 1, 1, 1, '提醒发货', '1524019304866', '2018-04-27 08:22:19', '2018-04-27 08:22:19');

-- ----------------------------
-- Table structure for mobile_order
-- ----------------------------
DROP TABLE IF EXISTS `mobile_order`;
CREATE TABLE `mobile_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `shipping_id` bigint(20) NULL DEFAULT NULL COMMENT '收货地址id',
  `payment` decimal(20, 2) NULL DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) NULL DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `postage` int(10) NULL DEFAULT NULL COMMENT '运费,单位是元',
  `status` int(10) NULL DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime(0) NULL DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no_index`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_order
-- ----------------------------
INSERT INTO `mobile_order` VALUES (1, 1524019304866, 37, 18, 7596.00, 1, 0, 0, '2018-04-20 18:21:14', NULL, '2018-05-01 18:48:56', '2018-05-14 10:44:02', '2018-04-18 10:41:44', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (3, 1524188997156, 37, 18, 259900.00, 1, 0, 0, NULL, NULL, '2018-04-27 08:44:31', '2018-05-09 11:18:14', '2018-04-20 09:49:57', '2018-05-09 11:18:14');
INSERT INTO `mobile_order` VALUES (4, 1524198990091, 27, 18, 4198.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-20 12:36:30', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (5, 1524223165653, 27, 18, 1999.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-20 19:19:25', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (6, 1524301362476, 27, 18, 111.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-21 17:02:42', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (7, 1524390136824, 29, 21, 899.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-22 17:42:16', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (8, 1524390355997, 29, 21, 1899.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-22 17:45:55', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (9, 1524398765060, 29, 21, 1999.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-22 20:06:04', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (11, 1524833548356, 27, 18, 10396.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-14 10:44:02', '2018-04-27 20:52:33', '2018-05-14 10:44:02');
INSERT INTO `mobile_order` VALUES (12, 1525073274611, 37, 22, 799.00, 1, 0, 40, NULL, NULL, NULL, '2018-05-06 15:55:53', '2018-04-30 15:27:54', '2018-05-06 15:55:53');
INSERT INTO `mobile_order` VALUES (13, 1525241488531, 37, 22, 3198.00, 1, 0, 60, NULL, NULL, '2018-05-07 14:42:45', '2018-06-01 13:57:47', '2018-05-02 14:11:28', '2018-06-01 13:57:47');
INSERT INTO `mobile_order` VALUES (14, 1525401490218, 37, 22, 1278.00, 1, 0, 50, '2018-05-04 10:39:07', NULL, '2018-05-07 14:40:17', '2018-06-01 13:49:47', '2018-05-04 10:38:10', '2018-06-01 13:49:47');
INSERT INTO `mobile_order` VALUES (15, 1525483878287, 37, 22, 5700.00, 1, 0, 0, NULL, NULL, NULL, '2018-05-05 09:31:36', '2018-05-05 09:31:18', '2018-05-05 09:31:36');

-- ----------------------------
-- Table structure for mobile_order_item
-- ----------------------------
DROP TABLE IF EXISTS `mobile_order_item`;
CREATE TABLE `mobile_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `properties_id` bigint(20) NULL DEFAULT NULL COMMENT '规格id',
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_no_index`(`order_no`) USING BTREE,
  INDEX `order_no_user_id_index`(`user_id`, `order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_order_item
-- ----------------------------
INSERT INTO `mobile_order_item` VALUES (1, 27, 1524019304866, 1, 1, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 1899.00, 4, 7596.00, '2018-04-18 10:41:44', '2018-04-18 10:41:44');
INSERT INTO `mobile_order_item` VALUES (2, 27, 1524020811456, 1, 1, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 1899.00, 1, 1899.00, '2018-04-18 11:06:51', '2018-04-18 11:06:51');
INSERT INTO `mobile_order_item` VALUES (3, 27, 1524188997156, 1, 3, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 2599.00, 100, 259900.00, '2018-04-20 09:49:57', '2018-04-20 09:49:57');
INSERT INTO `mobile_order_item` VALUES (4, 27, 1524198990091, 1, 2, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 2099.00, 2, 4198.00, '2018-04-20 12:36:30', '2018-04-20 12:36:30');
INSERT INTO `mobile_order_item` VALUES (5, 27, 1524223165653, 1, 10, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 1999.00, 1, 1999.00, '2018-04-20 19:19:25', '2018-04-20 19:19:25');
INSERT INTO `mobile_order_item` VALUES (6, 27, 1524301362476, 2, 13, 'zzz', '3fdb0bb3-8b2f-43a7-8775-fea8fe0eda1c.jpg', 111.00, 1, 111.00, '2018-04-21 17:02:43', '2018-04-21 17:02:43');
INSERT INTO `mobile_order_item` VALUES (7, 29, 1524390136824, 3, 15, '360手机 N6 Lite 全网通 4GB+32GB 璀璨金 移动联通电信4G手机 双卡双待', '7332a564-f0d0-4b7f-8365-1ea7004c13fb.jpg', 899.00, 1, 899.00, '2018-04-22 17:42:16', '2018-04-22 17:42:16');
INSERT INTO `mobile_order_item` VALUES (8, 29, 1524390355997, 1, 1, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 1899.00, 1, 1899.00, '2018-04-22 17:45:55', '2018-04-22 17:45:55');
INSERT INTO `mobile_order_item` VALUES (9, 29, 1524398765060, 1, 10, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 1999.00, 1, 1999.00, '2018-04-22 20:06:05', '2018-04-22 20:06:05');
INSERT INTO `mobile_order_item` VALUES (10, 37, 1524790268809, 1, 11, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 2199.00, 1, 2199.00, '2018-04-27 08:51:08', '2018-04-27 08:51:08');
INSERT INTO `mobile_order_item` VALUES (11, 27, 1524833548356, 1, 3, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 2599.00, 4, 10396.00, '2018-04-27 20:52:33', '2018-04-27 20:52:33');
INSERT INTO `mobile_order_item` VALUES (12, 37, 1525073274611, 4, 18, '360手机 F5 移动版 2GB+16GB 流光金 移动联通4G手机 双卡双待', 'e0dbafa4-37f9-4bc8-9212-84a3fee6c307.jpg', 799.00, 1, 799.00, '2018-04-30 15:27:54', '2018-04-30 15:27:54');
INSERT INTO `mobile_order_item` VALUES (13, 37, 1525241488531, 1, 5, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 2199.00, 1, 2199.00, '2018-05-02 14:11:28', '2018-05-02 14:11:28');
INSERT INTO `mobile_order_item` VALUES (14, 37, 1525241488531, 2, 23, 'zzz', '3fdb0bb3-8b2f-43a7-8775-fea8fe0eda1c.jpg', 999.00, 1, 999.00, '2018-05-02 14:11:28', '2018-05-02 14:11:28');
INSERT INTO `mobile_order_item` VALUES (15, 37, 1525401490218, 3, 15, '360手机 N6 Lite 全网通 4GB+32GB 璀璨金 移动联通电信4G手机 双卡双待', '7332a564-f0d0-4b7f-8365-1ea7004c13fb.jpg', 899.00, 1, 899.00, '2018-05-04 10:38:10', '2018-05-04 10:38:10');
INSERT INTO `mobile_order_item` VALUES (16, 37, 1525401490218, 4, 17, '360手机 F5 移动版 2GB+16GB 流光金 移动联通4G手机 双卡双待', 'e0dbafa4-37f9-4bc8-9212-84a3fee6c307.jpg', 379.00, 1, 379.00, '2018-05-04 10:38:10', '2018-05-04 10:38:10');
INSERT INTO `mobile_order_item` VALUES (17, 37, 1525483878287, 1, 1, '360手机 N6 Pro 全网通 6GB+64GB 极夜黑 移动联通电信4G手机 双卡双待', 'eab1094e-c8f7-473f-ac0e-eda5deaffd7e.jpg', 1900.00, 3, 5700.00, '2018-05-05 09:31:18', '2018-05-05 09:31:18');

-- ----------------------------
-- Table structure for mobile_order_seller
-- ----------------------------
DROP TABLE IF EXISTS `mobile_order_seller`;
CREATE TABLE `mobile_order_seller`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `seller_id` bigint(20) NULL DEFAULT NULL COMMENT '卖家id',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_order_seller
-- ----------------------------
INSERT INTO `mobile_order_seller` VALUES (1, 37, 31, 1525073274611, '2018-04-30 15:27:54', '2018-04-30 15:27:54');
INSERT INTO `mobile_order_seller` VALUES (2, 37, 31, 1524019304866, '2018-04-30 15:32:22', '2018-04-30 15:32:27');
INSERT INTO `mobile_order_seller` VALUES (3, 37, 31, 1525241488531, '2018-05-02 14:11:28', '2018-05-02 14:11:28');
INSERT INTO `mobile_order_seller` VALUES (4, 37, 31, 1525241488531, '2018-05-02 14:11:28', '2018-05-02 14:11:28');
INSERT INTO `mobile_order_seller` VALUES (5, 37, 31, 1525401490218, '2018-05-04 10:38:10', '2018-05-04 10:38:10');
INSERT INTO `mobile_order_seller` VALUES (6, 37, 31, 1525401490218, '2018-05-04 10:38:10', '2018-05-04 10:38:10');
INSERT INTO `mobile_order_seller` VALUES (7, 37, 31, 1525483878287, '2018-05-05 09:31:18', '2018-05-05 09:31:18');
INSERT INTO `mobile_order_seller` VALUES (8, 37, 31, 1524390355997, NULL, NULL);
INSERT INTO `mobile_order_seller` VALUES (9, 37, 31, 1524398765060, NULL, NULL);
INSERT INTO `mobile_order_seller` VALUES (10, 37, 31, 1524833548356, NULL, NULL);
INSERT INTO `mobile_order_seller` VALUES (11, 37, 31, 1524301362476, NULL, NULL);
INSERT INTO `mobile_order_seller` VALUES (12, 37, 31, 1524223165653, NULL, NULL);
INSERT INTO `mobile_order_seller` VALUES (13, 37, 31, 1524198990091, NULL, NULL);
INSERT INTO `mobile_order_seller` VALUES (14, 37, 31, 1524188997156, NULL, NULL);

-- ----------------------------
-- Table structure for mobile_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `mobile_pay_info`;
CREATE TABLE `mobile_pay_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) NULL DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_pay_info
-- ----------------------------
INSERT INTO `mobile_pay_info` VALUES (1, 27, 1524019304866, 1, '2018042021001004210200253278', NULL, '2018-04-20 18:21:14', '2018-04-20 18:21:14');
INSERT INTO `mobile_pay_info` VALUES (2, NULL, 1524020811456, 1, '2018042221001004210200253768', NULL, '2018-04-22 14:24:07', '2018-04-22 14:24:07');
INSERT INTO `mobile_pay_info` VALUES (7, 27, 1524376999965, 1, '2018042221001004210200253768', NULL, '2018-04-22 15:33:53', '2018-04-22 15:33:53');
INSERT INTO `mobile_pay_info` VALUES (8, 37, 1524790268809, 1, NULL, NULL, '2018-04-27 09:22:42', '2018-04-27 09:30:40');
INSERT INTO `mobile_pay_info` VALUES (9, 37, 1525073274611, 1, '2018050421001004210200257906', 'TRADE_SUCCESS', '2018-05-04 10:29:46', '2018-05-04 10:29:46');
INSERT INTO `mobile_pay_info` VALUES (10, 37, 1525241488531, 1, '2018050421001004210200258816', 'TRADE_SUCCESS', '2018-05-04 10:32:38', '2018-05-04 10:32:38');
INSERT INTO `mobile_pay_info` VALUES (11, 37, 1525401490218, 1, '2018050421001004210200257907', 'TRADE_SUCCESS', '2018-05-04 10:39:07', '2018-05-04 10:39:07');

-- ----------------------------
-- Table structure for mobile_shipping
-- ----------------------------
DROP TABLE IF EXISTS `mobile_shipping`;
CREATE TABLE `mobile_shipping`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_shipping
-- ----------------------------
INSERT INTO `mobile_shipping` VALUES (18, 27, '张', '13111212312312', '13912345678', '黑龙江省', '哈尔滨市', '道里区', '某个街110号', '150000', '2018-03-26 10:42:43', '2018-03-26 10:42:43');
INSERT INTO `mobile_shipping` VALUES (20, 27, 'aaa', NULL, '111', '山西省', '太原市', '杏花岭区', 'aaaa', '11111', '2018-04-06 10:26:34', '2018-04-06 10:26:34');
INSERT INTO `mobile_shipping` VALUES (21, 29, 'sasas', NULL, '121231212', '湖北省', '宜昌市', '长阳土家族自治县', 'AAAASD', '11311', '2018-04-22 17:41:39', '2018-04-22 17:41:39');
INSERT INTO `mobile_shipping` VALUES (22, 37, 'sadasdada', NULL, '1231231', '湖北省', '宜昌市', '长阳土家族自治县', 'zxczxczxc', '1313', '2018-04-27 08:50:55', '2018-04-27 08:50:55');
INSERT INTO `mobile_shipping` VALUES (23, 37, 'aaa', NULL, '121212', '湖北省', '宜昌市', '长阳土家族自治县', 'SAASAS', '1112', '2018-05-05 09:42:43', '2018-05-05 09:42:43');
INSERT INTO `mobile_shipping` VALUES (24, 37, 'asasdsa', NULL, '232112', '湖北省', '宜昌市', '长阳土家族自治县', 'sadasa', '1212', '2018-05-05 09:43:11', '2018-05-05 09:43:11');
INSERT INTO `mobile_shipping` VALUES (25, 37, 'adad', NULL, '12312312', '湖北省', '宜昌市', '长阳土家族自治县', 'asda', '11311', '2018-05-05 09:43:25', '2018-05-05 09:43:25');
INSERT INTO `mobile_shipping` VALUES (26, 37, 'wqqw', NULL, '1123123', '湖北省', '宜昌市', '长阳土家族自治县', 'wefesf', '1212', '2018-05-05 09:43:44', '2018-05-05 09:43:44');
INSERT INTO `mobile_shipping` VALUES (28, 37, 'aaa1', NULL, '111', '湖北省', '宜昌市', '长阳土家族自治县', 'xzxczxc', '121', '2018-05-09 10:38:34', '2018-05-09 10:38:34');

-- ----------------------------
-- Table structure for mobile_user
-- ----------------------------
DROP TABLE IF EXISTS `mobile_user`;
CREATE TABLE `mobile_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `question` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name_unique`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mobile_user
-- ----------------------------
INSERT INTO `mobile_user` VALUES (1, 'admin', '427338237BD929443EC5D48E24FD2B1A', 'admin@admin.com', '12345677901', '管理员', '1.jpg', '问题', '答案', 0, '2018-03-12 20:06:41', '2018-03-12 20:06:41');
INSERT INTO `mobile_user` VALUES (22, 'user01', 'D8F80B67499E434EA61ADAF6E6219BF2', 'aaa111@test.com', '1312312311', '用户01', '1.jpg', '新问题', '新答案', 1, '2018-03-12 16:44:29', '2018-03-12 19:30:26');
INSERT INTO `mobile_user` VALUES (25, 'user02', 'D8F80B67499E434EA61ADAF6E6219BF2', 'aaa112@test.com', '1312312311', '用户002', '2.jpg', '新问题', '新答案', 1, '2018-03-13 09:23:52', '2018-03-13 09:25:07');
INSERT INTO `mobile_user` VALUES (26, 'user03', 'D8F80B67499E434EA61ADAF6E6219BF2', 'test@test.com', '12345678901', '用户02', '1.jpg', NULL, NULL, 1, '2018-03-13 09:54:50', '2018-03-13 09:54:50');
INSERT INTO `mobile_user` VALUES (27, 'aaaa', 'EF8C030DC3F55FE602083B06CA8EDF55', 'aaaa@126.com', '13912345678', '阿拉a', 'e3edd625-9966-40e0-a8d3-0f7c781abd47.png', '问题', '答案', 1, '2018-03-13 09:56:42', '2018-04-21 12:01:45');
INSERT INTO `mobile_user` VALUES (28, 'zzzz', 'E32F3E69683CB0530C8222032BBB4F04', 'asaad@123.com', '13912341234', 'zzzz', '208155a2-0974-49c5-b986-ab7e73307aca.jpg', '问题', '答案', 1, '2018-03-21 14:00:54', '2018-03-21 14:57:54');
INSERT INTO `mobile_user` VALUES (29, 'tmp111', 'D8F80B67499E434EA61ADAF6E6219BF2', 'tmp111@163.com', '13912345678', '临时', '1.jpg', NULL, NULL, 1, '2018-04-22 17:20:55', '2018-04-22 17:20:55');
INSERT INTO `mobile_user` VALUES (30, 'asdsadasd', 'D8F80B67499E434EA61ADAF6E6219BF2', 'wqqw@123.com', '13912345678', 'wwqwae', '1.jpg', NULL, NULL, 1, '2018-04-22 17:23:17', '2018-04-22 17:23:17');
INSERT INTO `mobile_user` VALUES (31, 'seller01', 'D8F80B67499E434EA61ADAF6E6219BF2', 'seller@test.com', '12345678901', '卖家01', '1.jpg', 'q', 'a', 2, '2018-04-24 13:38:25', '2018-04-24 13:38:25');
INSERT INTO `mobile_user` VALUES (32, 'xxx', '12AEAC4D5C99B893C9CE08C55877ACEA', 'xxx', '111', 'xxx', '1.jpg', NULL, NULL, 2, '2018-04-24 15:45:44', '2018-04-24 15:45:44');
INSERT INTO `mobile_user` VALUES (33, 'aaa', 'EF8C030DC3F55FE602083B06CA8EDF55', 'aaaa', '111', 'aaaa', '1.jpg', NULL, NULL, 2, '2018-04-24 15:49:10', '2018-04-24 15:49:10');
INSERT INTO `mobile_user` VALUES (34, 'qqq', 'EB641B065EE510D8DDAE66C6E222D699', 'qqq', '111', 'qqq', '1.jpg', NULL, NULL, 2, '2018-04-24 15:51:05', '2018-04-24 15:51:05');
INSERT INTO `mobile_user` VALUES (35, 'ccc', 'EAF4CBDDB2797201221DE1C5E5D05E27', 'ccc', '111', 'ccc', '1.jpg', NULL, NULL, 2, '2018-04-24 15:54:28', '2018-04-24 15:54:28');
INSERT INTO `mobile_user` VALUES (36, 'xxxc', '12AEAC4D5C99B893C9CE08C55877ACEA', 'xxxvvv', '111', 'xxx', '1.jpg', NULL, NULL, 2, '2018-04-24 15:56:58', '2018-04-24 15:56:58');
INSERT INTO `mobile_user` VALUES (37, 'tmp01', 'D8F80B67499E434EA61ADAF6E6219BF2', 'tmp@163.com', '13912345678', '啦啦啦', '1.jpg', 'qqq', 'aaa', 1, '2018-04-24 19:05:37', '2018-05-09 10:30:15');
INSERT INTO `mobile_user` VALUES (38, 'hello', '8D4235228498C361E53FAD467C58BE2D', '15789165276@qq.com', '13956872135', 'h', 'avatar.jpg', NULL, NULL, 1, '2018-07-10 16:52:18', '2018-07-10 16:52:18');
INSERT INTO `mobile_user` VALUES (39, 'aaaaa', '449B6FAFDC05B1245E315BF834628105', '3@qq.com', '18642977877', 'aaaaa', 'avatar.jpg', NULL, NULL, 1, '2018-07-12 14:30:24', '2018-07-12 14:30:24');
INSERT INTO `mobile_user` VALUES (40, 'asdf', '3273C3AF1F259E5E19464976B3FBEE58', '123@qw.co', '17602479612', '12312', 'avatar.jpg', NULL, NULL, 1, '2018-07-16 15:27:47', '2018-07-16 15:27:47');
INSERT INTO `mobile_user` VALUES (41, 'asdffdas', '38B372A899F42895398CC90E8D05464C', 'asdf@asdf.com', '13011011010', 'asdf', 'avatar.jpg', NULL, NULL, 1, '2018-07-17 11:42:33', '2018-07-17 11:42:33');
INSERT INTO `mobile_user` VALUES (42, 'dddddd', 'EB641B065EE510D8DDAE66C6E222D699', 'aaa@outlook.com', '13945894911', 'dddddd', 'avatar.jpg', NULL, NULL, 1, '2018-07-18 10:41:47', '2018-07-18 10:41:47');

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for student_score
-- ----------------------------
DROP TABLE IF EXISTS `student_score`;
CREATE TABLE `student_score`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `subject_id` bigint(20) NOT NULL,
  `subject_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `score` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(6) NULL DEFAULT 1 COMMENT '状态.1-允许教师修改 2-不允许教师修改',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for student_subject
-- ----------------------------
DROP TABLE IF EXISTS `student_subject`;
CREATE TABLE `student_subject`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` int(10) NOT NULL COMMENT '人数上限',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for student_subject_student
-- ----------------------------
DROP TABLE IF EXISTS `student_subject_student`;
CREATE TABLE `student_subject_student`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for student_user
-- ----------------------------
DROP TABLE IF EXISTS `student_user`;
CREATE TABLE `student_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码，MD5加密',
  `user_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-教师,2-学生',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_no_unique`(`user_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student_user
-- ----------------------------
INSERT INTO `student_user` VALUES (1, 'admin', '4521590F535876863E95BC3AD3058EB3', '1234567890', 0, '2018-03-16 20:46:50', '2018-03-16 20:46:50');

SET FOREIGN_KEY_CHECKS = 1;
