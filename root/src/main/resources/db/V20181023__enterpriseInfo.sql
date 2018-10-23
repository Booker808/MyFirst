ALTER TABLE `shoppingcenter_new`.`enterprise_info`
MODIFY COLUMN `ent_type` int(11) NOT NULL COMMENT '企业类型(1：供应商，2：采购商，3：供应商和采购商) 4:服务商' AFTER `ent_number`