DROP TABLE IF EXISTS `tb_dining_table`;
CREATE TABLE `tb_dining_table` (
  `id` bigint(11) NOT NULL COMMENT '主键id',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `table_no` varchar(50) NOT NULL COMMENT '餐桌编号',
  `table_name` varchar(50) NOT NULL COMMENT '餐桌名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

alter TABLE `tb_shop` add `checkout_mode` int(2) DEFAULT '1' COMMENT '结账模式 1=先付款后就餐 2=先就餐后付款' after latitude;

alter TABLE `tb_order` add `checkout_mode` int(2) DEFAULT '1' COMMENT '结账模式 1=先付款后就餐 2=先就餐后付款' after shop_logo_img;
alter TABLE `tb_order` add `table_no` varchar(50) NOT NULL COMMENT '餐桌编号' after checkout_mode;
alter TABLE `tb_order` add `table_name` varchar(50) NOT NULL COMMENT '餐桌名称' after table_no;

alter TABLE `tb_order` add `is_payment` tinyint(1) DEFAULT '0' COMMENT '是否已支付' after table_name;
