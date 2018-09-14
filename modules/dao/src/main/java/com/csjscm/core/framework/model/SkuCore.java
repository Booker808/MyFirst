package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkuCore {
    @ApiModelProperty("商品编码")
    private String productNo;

    @ApiModelProperty("标准商品编码")
    private String stdProductNo;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("分类ID")
    private Integer categoryId;

    @ApiModelProperty("分类编码")
    private String categoryNo;
    @ApiModelProperty("分类ID")
    private Integer categorySpId;

    @ApiModelProperty("分类编码")
    private String categorySpNo;

    @ApiModelProperty("是否有效0：有效，1无效")
    private Integer isvalidate;

    @ApiModelProperty("核准状况(1:已核准;2:待核准;3:不许交易)")
    private Integer approvalStatus;

    @ApiModelProperty("来源渠道（1：手动新增；2：导入；3：来自商城）")
    private Integer channel;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("品牌ID")
    private Integer brandId;

    @ApiModelProperty("包装规格ID")
    private Integer unitId;

    @ApiModelProperty("最小库存单位")
    private String minUint;

    @ApiModelProperty("商品图片")
    private String productPic;

    @ApiModelProperty("也称69码")
    private String ean13Code;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty("开票类型")
    private String invoiceType;

    @ApiModelProperty("税则码")
    private String txtCode;

    @ApiModelProperty("规格")
    private String rule;

    @ApiModelProperty("型号")
    private String size;

    @ApiModelProperty("创建用户ID")
    private String createUserId;

    @ApiModelProperty("创建时间戳")
    private Date createTime;

    @ApiModelProperty("编辑用户ID")
    private String editUserId;

    @ApiModelProperty("编辑时间戳")
    private Date editTime;

    private String udf1;

    private String udf2;

    private String udf3;

    private String udf4;

    private String udf5;

    private String udf6;

    private String udf7;

    private String udf8;

    private String udf9;

    private String udf10;

    private String suf1;

    private String suf2;

    private String suf3;

    private String suf4;

    private String suf5;

    private String suf6;

    private String suf7;

    private String suf8;

    private String suf9;

    private String suf10;
}