package com.csjscm.core.framework.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkuCore {
    /**
     * 商品编码
     */
    private String productNo;

    /**
     * spu编码
     */
    private String stdProductNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 基准价
     */
    private BigDecimal productPrice;

    /**
     * 底层一级分类
     */
    private Integer lv1CategoryId;

    /**
     * 底层一级分类编码
     */
    private String lv1CategoryNo;

    /**
     * 底层二级分类
     */
    private Integer lv2CategoryId;

    /**
     * 底层二级分类编码
     */
    private String lv2CategoryNo;

    /**
     * 底层商品细分类ID
     */
    private Integer categoryId;

    /**
     * 细分类编码
     */
    private String categoryNo;

    /**
     * 业务一级分类
     */
    private Integer lv1CategorySpId;

    /**
     * 业务一级分类编码
     */
    private String lv1CategorySpNo;

    /**
     * 业务二级分类
     */
    private Integer lv2CategorySpId;

    /**
     * 业务二级分类编码
     */
    private String lv2CategorySpNo;

    /**
     * 业务三级分类
     */
    private Integer categorySpId;

    /**
     * 业务三级分类编码
     */
    private String categorySpNo;

    /**
     * 是否有效(0:有效,1:无效)
     */
    private Integer isvalidate;

    /**
     * 核准状况(1:已核准;2:待核准;3:不许交易)
     */
    private Integer approvalStatus;

    /**
     * 来源渠道（1：平台手动新增；2：平台导入；3：来自西域； 4：来自商城； 5：来自scm）
     */
    private Integer channel;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 包装规格id(弃用)
     */
    private Integer unitId;

    /**
     * 最小库存单位
     */
    private String minUint;

    /**
     * 商品图片
     */
    private String productPic;

    /**
     * 69码（EAN13码）条形码
     */
    private String ean13Code;

    /**
     * 助记码 商品简码
     */
    private String mnemonicCode;

    /**
     * 开票类型
     */
    private String invoiceType;

    /**
     * 近期询价
     */
    private BigDecimal recentEnquiry;

    /**
     * 描述
     */
    private String description;

    /**
     * 参考进价
     */
    private BigDecimal refrencePrice;

    /**
     * 税则码 （弃用）
     */
    private String txtCode;

    /**
     * 规格
     */
    private String rule;

    /**
     * 型号
     */
    private String size;

    /**
     *
     */
    private String createUserId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String editUserId;

    /**
     *
     */
    private Date editTime;

    /**
     *
     */
    private String udf1;

    /**
     *
     */
    private String udf2;

    /**
     *
     */
    private String udf3;

    /**
     *
     */
    private String udf4;

    /**
     *
     */
    private String udf5;

    /**
     *
     */
    private String udf6;

    /**
     *
     */
    private String udf7;

    /**
     *
     */
    private String udf8;

    /**
     *
     */
    private String udf9;

    /**
     *
     */
    private String udf10;

    /**
     *
     */
    private String suf1;

    /**
     *
     */
    private String suf2;

    /**
     *
     */
    private String suf3;

    /**
     *
     */
    private String suf4;

    /**
     *
     */
    private String suf5;

    /**
     *
     */
    private String suf6;

    /**
     *
     */
    private String suf7;

    /**
     *
     */
    private String suf8;

    /**
     *
     */
    private String suf9;

    /**
     *
     */
    private String suf10;

    /**
     * 迁移数据标识
     */
    private String requestId;

    /**
     * 迁移数据标识
     */
    private String categoryIdOld;

    /**
     * 迁移数据标识
     */
    private String createUserIdOld;

    /**
     * 迁移数据标识
     */
    private String editUserIdOld;

    /**
     * 迁移数据标识
     */
    private String approvalStatusOld;

    /**
     * 迁移数据标识
     */
    private String brandIdOld;

    /**
     * 迁移数据标识
     */
    private String minUintOld;
}