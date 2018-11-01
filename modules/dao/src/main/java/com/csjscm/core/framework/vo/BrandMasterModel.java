package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

public class BrandMasterModel {
    private Integer id;

    @NotBlank(message = "品牌名称不能为空")
    private String brandName;
    private String brandCategory;
    private String brandSpCategory;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(String brandCategory) {
        this.brandCategory = brandCategory;
    }

    public String getBrandPic() {
        return brandPic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic;
    }

    public String getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(String brandInfo) {
        this.brandInfo = brandInfo;
    }

    public String getBrandCa() {
        return brandCa;
    }

    public void setBrandCa(String brandCa) {
        this.brandCa = brandCa;
    }

    public Integer getIsAuthorize() {
        return isAuthorize;
    }

    public void setIsAuthorize(Integer isAuthorize) {
        this.isAuthorize = isAuthorize;
    }

    public String getBrandSpCategory() {
        return brandSpCategory;
    }

    public void setBrandSpCategory(String brandSpCategory) {
        this.brandSpCategory = brandSpCategory;
    }
}
