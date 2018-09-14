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
     * redis中最大商城商品编码的key的前缀 后面拼接最小商品分类编码
     */
    public static final String REDIS_KEY_PRODUCT_SPNO = "spCategory_";
    /**
     * redis中最小单位的key
     */
    public static final String REDIS_KEY_UNIT = "unitList";
    /**
     * redis中 商品分类json数据的 key
     */
    public static final String REDIS_KEY_JSON_CATEGORY = "categoryJsonStr";
    /**
     * redis中 SP商品分类json数据的 key
     */
    public static final String REDIS_KEY_JSON_SP_CATEGORY = "spCategoryJsonStr";
    /**
     * 企业编码前缀
     */
    public static final String  ENTNUMBER_INDEX = "EP";
    /**
     * 字典表中银行的code
     */
    public static final String  DICT_CODE_BANK = "bankName";
    /**
     * 品牌数据 redis 中的key
     */
    public static final String  REDIS_KEY_JSONSTR_BRAND = "brandMasterJsonStr";
}
