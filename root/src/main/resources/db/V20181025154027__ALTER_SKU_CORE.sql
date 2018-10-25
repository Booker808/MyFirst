ALTER TABLE `sku_core`
  ADD COLUMN `stock` INT NULL COMMENT '库存' AFTER `min_uint_old`,
  ADD COLUMN `weight` DOUBLE NULL COMMENT '重量(kg)' AFTER `stock`;
