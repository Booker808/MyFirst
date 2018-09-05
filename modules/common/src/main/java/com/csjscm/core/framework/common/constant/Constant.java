package com.csjscm.core.framework.common.constant;
/**
 * 公用常量类定义
 *
 * @author yinzy
 * @version 1.0
 */
public class Constant {
    /**
     * redis中最大商品编码的key的前缀 后面拼接最小商品分类编码
     */
    public static final String REDIS_KEY_PRODUCT_NO = "category_";
    /**
     * redis中最小单位的key
     */
    public static final String REDIS_KEY_UNIT = "unitList";
}
