package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商分类实体
 *
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-26 08:32:46
 */

public class EnterpriseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 企业编号
     */
    private String entNumber;

    /**
     * 供应商状态分类(1：活跃供应商，2：冻结供应商，3：黑名单供应商)
     */
    private Integer supplyState;

    /**
     * 供应商业务分类（1：合格供应商，2：备用供应商，3：独家/客户指定供应商，4：特批供应商）
     */
    private Integer supplyBusiness;

    /**
     *
     */
    private Date supplyStartTime;

    /**
     *
     */
    private Date supplyEndTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private Date editTime;

    /**
     * 修改人
     */
    @ApiModelProperty(hidden = true)
    private String editUser;

    /**
     * 删除状态（0：未删除，1：已删除）
     */
    @ApiModelProperty(hidden = true)
    private Integer isdelete;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌等级 1：国内一级 ，2国内二级，3国内三级，4进口品牌
     */
    private Integer brandLevel;

    /**
     * 产值
     */
    private String output;

    /**
     * 销售额
     */
    private String sales;

    /**
     * 主营产品大类
     */
    private String mainProducts;

    /**
     * 产品分类备注
     */
    private String productRemark;

    /**
     * 供应商简介
     */
    private String description;
    /**
     * 供应商状态变更描述
     */
    private String remark;


    /**
     * 获取主键Id
     *
     * @return id
     */
    public Integer getId(){
        return id;
    }

    /**
     * 设置主键Id
     *
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * 获取企业编号
     *
     * @return 企业编号
     */
    public String getEntNumber(){
        return entNumber;
    }

    /**
     * 设置企业编号
     *
     * @param entNumber 要设置的企业编号
     */
    public void setEntNumber(String entNumber){
        this.entNumber = entNumber;
    }

    /**
     * 获取供应商状态分类(1：活跃供应商，2：冻结供应商，3：黑名单供应商)
     *
     * @return 供应商状态分类(1：活跃供应商，2：冻结供应商，3：黑名单供应商)
     */
    public Integer getSupplyState(){
        return supplyState;
    }

    /**
     * 设置供应商状态分类(1：活跃供应商，2：冻结供应商，3：黑名单供应商)
     *
     * @param supplyState 要设置的供应商状态分类(1：活跃供应商，2：冻结供应商，3：黑名单供应商)
     */
    public void setSupplyState(Integer supplyState){
        this.supplyState = supplyState;
    }

    /**
     * 获取供应商业务分类（1：合格供应商，2：备用供应商，3：独家/客户指定供应商，4：特批供应商）
     *
     * @return 供应商业务分类（1：合格供应商，2：备用供应商，3：独家/客户指定供应商，4：特批供应商）
     */
    public Integer getSupplyBusiness(){
        return supplyBusiness;
    }

    /**
     * 设置供应商业务分类（1：合格供应商，2：备用供应商，3：独家/客户指定供应商，4：特批供应商）
     *
     * @param supplyBusiness 要设置的供应商业务分类（1：合格供应商，2：备用供应商，3：独家/客户指定供应商，4：特批供应商）
     */
    public void setSupplyBusiness(Integer supplyBusiness){
        this.supplyBusiness = supplyBusiness;
    }

    /**
     * 获取
     *
     * @return
     */
    public Date getSupplyStartTime(){
        return supplyStartTime;
    }

    /**
     * 设置
     *
     * @param supplyStartTime 要设置的
     */
    public void setSupplyStartTime(Date supplyStartTime){
        this.supplyStartTime = supplyStartTime;
    }

    /**
     * 获取
     *
     * @return
     */
    public Date getSupplyEndTime(){
        return supplyEndTime;
    }

    /**
     * 设置
     *
     * @param supplyEndTime 要设置的
     */
    public void setSupplyEndTime(Date supplyEndTime){
        this.supplyEndTime = supplyEndTime;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime(){
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public Date getEditTime(){
        return editTime;
    }

    /**
     * 设置修改时间
     *
     * @param editTime 要设置的修改时间
     */
    public void setEditTime(Date editTime){
        this.editTime = editTime;
    }

    /**
     * 获取修改人
     *
     * @return 修改人
     */
    public String getEditUser(){
        return editUser;
    }

    /**
     * 设置修改人
     *
     * @param editUser 要设置的修改人
     */
    public void setEditUser(String editUser){
        this.editUser = editUser;
    }

    /**
     * 获取删除状态（0：未删除，1：已删除）
     *
     * @return 删除状态（0：未删除，1：已删除）
     */
    public Integer getIsdelete(){
        return isdelete;
    }

    /**
     * 设置删除状态（0：未删除，1：已删除）
     *
     * @param isdelete 要设置的删除状态（0：未删除，1：已删除）
     */
    public void setIsdelete(Integer isdelete){
        this.isdelete = isdelete;
    }

    /**
     * 获取品牌名称
     *
     * @return 品牌名称
     */
    public String getBrandName(){
        return brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 要设置的品牌名称
     */
    public void setBrandName(String brandName){
        this.brandName = brandName;
    }

    /**
     * 获取品牌等级 1：国内一级 ，2国内二级，3国内三级，4进口品牌
     *
     * @return 品牌等级 1：国内一级 ，2国内二级，3国内三级，4进口品牌
     */
    public Integer getBrandLevel(){
        return brandLevel;
    }

    /**
     * 设置品牌等级 1：国内一级 ，2国内二级，3国内三级，4进口品牌
     *
     * @param brandLevel 要设置的品牌等级 1：国内一级 ，2国内二级，3国内三级，4进口品牌
     */
    public void setBrandLevel(Integer brandLevel){
        this.brandLevel = brandLevel;
    }

    /**
     * 获取产值
     *
     * @return 产值
     */
    public String getOutput(){
        return output;
    }

    /**
     * 设置产值
     *
     * @param output 要设置的产值
     */
    public void setOutput(String output){
        this.output = output;
    }

    /**
     * 获取销售额
     *
     * @return 销售额
     */
    public String getSales(){
        return sales;
    }

    /**
     * 设置销售额
     *
     * @param sales 要设置的销售额
     */
    public void setSales(String sales){
        this.sales = sales;
    }

    /**
     * 获取主营产品大类
     *
     * @return 主营产品大类
     */
    public String getMainProducts(){
        return mainProducts;
    }

    /**
     * 设置主营产品大类
     *
     * @param mainProducts 要设置的主营产品大类
     */
    public void setMainProducts(String mainProducts){
        this.mainProducts = mainProducts;
    }

    /**
     * 获取产品分类备注
     *
     * @return 产品分类备注
     */
    public String getProductRemark(){
        return productRemark;
    }

    /**
     * 设置产品分类备注
     *
     * @param productRemark 要设置的产品分类备注
     */
    public void setProductRemark(String productRemark){
        this.productRemark = productRemark;
    }

    /**
     * 获取供应商简介
     *
     * @return 供应商简介
     */
    public String getDescription(){
        return description;
    }

    /**
     * 设置供应商简介
     *
     * @param description 要设置的供应商简介
     */
    public void setDescription(String description){
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}