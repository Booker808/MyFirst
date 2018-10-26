CREATE TABLE `sp_sku_core` (
  `product_no` varchar(20) NOT NULL COMMENT '商品编码',
  `std_product_no` varchar(20) DEFAULT NULL COMMENT 'spu编码',
  `product_name` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '基准价',
  `lv1_category_id` int(11) DEFAULT NULL COMMENT '底层一级分类',
  `lv1_category_no` varchar(255) DEFAULT NULL COMMENT '底层一级分类编码',
  `lv2_category_id` int(11) DEFAULT NULL COMMENT '底层二级分类',
  `lv2_category_no` varchar(255) DEFAULT NULL COMMENT '底层二级分类编码',
  `category_id` int(11) DEFAULT NULL COMMENT '底层商品细分类ID',
  `category_no` varchar(256) DEFAULT NULL COMMENT '细分类编码',
  `lv1_category_sp_id` int(11) DEFAULT NULL COMMENT '业务一级分类',
  `lv1_category_sp_no` varchar(255) DEFAULT NULL COMMENT '业务一级分类编码',
  `lv2_category_sp_id` int(11) DEFAULT NULL COMMENT '业务二级分类',
  `lv2_category_sp_no` varchar(255) DEFAULT NULL COMMENT '业务二级分类编码',
  `category_sp_id` int(11) DEFAULT NULL COMMENT '业务三级分类',
  `category_sp_no` varchar(256) DEFAULT NULL COMMENT '业务三级分类编码',
  `isvalidate` int(11) NOT NULL DEFAULT '0' COMMENT '是否有效(0:有效,1:无效)',
  `approval_status` int(11) DEFAULT NULL COMMENT '核准状况(1:已核准;2:待核准;3:不许交易)',
  `channel` int(11) DEFAULT NULL COMMENT '来源渠道（1：平台手动新增；2：平台导入；3：来自西域； 4：来自商城； 5：来自scm）',
  `brand_name` varchar(255) DEFAULT NULL COMMENT '品牌名称',
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌id',
  `unit_id` int(11) DEFAULT NULL COMMENT '包装规格id(弃用)',
  `min_uint` varchar(255) DEFAULT NULL COMMENT '最小库存单位',
  `product_pic` varchar(100) DEFAULT NULL COMMENT '商品图片',
  `ean13_code` varchar(45) DEFAULT NULL COMMENT '69码（EAN13码）条形码',
  `mnemonic_code` varchar(45) DEFAULT NULL COMMENT '助记码 商品简码',
  `invoice_type` varchar(45) DEFAULT NULL COMMENT '开票类型',
  `recent_enquiry` decimal(10,2) DEFAULT NULL COMMENT '近期询价',
  `description` text COMMENT '描述',
  `refrence_price` decimal(10,2) DEFAULT NULL COMMENT '参考进价',
  `txt_code` varchar(45) DEFAULT NULL COMMENT '税则码 （弃用）',
  `rule` varchar(255) DEFAULT NULL COMMENT '规格',
  `size` varchar(255) DEFAULT NULL COMMENT '型号',
  `create_user_id` varchar(256) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_user_id` varchar(256) DEFAULT NULL,
  `edit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `udf1` varchar(256) DEFAULT NULL,
  `udf2` varchar(256) DEFAULT NULL,
  `udf3` varchar(256) DEFAULT NULL,
  `udf4` varchar(256) DEFAULT NULL,
  `udf5` varchar(256) DEFAULT NULL,
  `udf6` varchar(256) DEFAULT NULL,
  `udf7` varchar(256) DEFAULT NULL,
  `udf8` varchar(256) DEFAULT NULL,
  `udf9` varchar(256) DEFAULT NULL,
  `udf10` varchar(256) DEFAULT NULL,
  `suf1` varchar(256) DEFAULT NULL,
  `suf2` varchar(256) DEFAULT NULL,
  `suf3` varchar(256) DEFAULT NULL,
  `suf4` varchar(256) DEFAULT NULL,
  `suf5` varchar(256) DEFAULT NULL,
  `suf6` varchar(256) DEFAULT NULL,
  `suf7` varchar(256) DEFAULT NULL,
  `suf8` varchar(256) DEFAULT NULL,
  `suf9` varchar(256) DEFAULT NULL,
  `suf10` varchar(256) DEFAULT NULL,
  `request_id` varchar(255) DEFAULT NULL COMMENT '迁移数据标识',
  `category_id_old` varchar(255) DEFAULT NULL COMMENT '迁移数据标识',
  `create_user_id_old` varchar(255) DEFAULT NULL COMMENT '迁移数据标识',
  `edit_user_id_old` varchar(255) DEFAULT NULL COMMENT '迁移数据标识',
  `approval_status_old` varchar(255) DEFAULT NULL COMMENT '迁移数据标识',
  `brand_id_old` varchar(255) DEFAULT NULL COMMENT '迁移数据标识',
  `min_uint_old` varchar(255) CHARACTER SET sjis DEFAULT NULL COMMENT '迁移数据标识',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `weight` double DEFAULT NULL COMMENT '重量(kg)',
  PRIMARY KEY (`product_no`) USING BTREE,
  KEY `FK_Reference_8` (`std_product_no`) USING BTREE,
  KEY `product_name` (`product_name`) USING BTREE,
  KEY `brand_name` (`product_name`) USING BTREE,
  KEY `min_uint` (`min_uint`) USING BTREE,
  KEY `rule` (`rule`) USING BTREE,
  KEY `size` (`size`) USING BTREE,
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`std_product_no`) REFERENCES `spu` (`std_product_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品核心表';
