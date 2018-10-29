package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

public class BrandMasterModel {
    @NotBlank(message = "品牌名称不能为空")
    private String brandName;
    @NotBlank(message = "品牌分类信息不能为空")
    private String brandCategory;
    /**
     * 品牌图片
     */
    private String brandPic;
    /**
     * 相关信息
     */
    private String brandInfo;
    /**
     * 品牌授权书
     */
    private String brandCa;

    @ApiModelProperty("是否授权：0未授权，1已授权")
    private Integer isAuthorize;
}
