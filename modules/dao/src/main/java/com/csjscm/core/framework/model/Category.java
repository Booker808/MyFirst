package com.csjscm.core.framework.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */

 public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 分类编码
    */
    @NotBlank(message = "分类编码不能为空")
    private String classCode;

    /**
    * 分类名称
    */
    @NotBlank(message = "分类编码不能为空")
    private String className;

    /**
    * 分类简写
    */
    @NotBlank(message = "分类简写不能为空")
    private String simpleName;

    /**
    * 上层分类ID
    */
    @NotBlank(message = "上层分类ID不能为空")
    private String parentClass;

    /**
    * 分类层级
    */
    @NotNull(message = "分类层级不能为空")
    private Integer levelNum;

    /**
    * 备注
    */
    private String memos;

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
    * udf相关规则{key:key,type:type,value:{}}
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
    * 迁移数据标识
    */
    private String requestId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 1启用 0停用
     */
    private Integer state;


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
    * @param
    */
    public void setId(Integer id){
        this.id = id;
    }

    /**
    * 获取分类编码
    *
    * @return 分类编码
    */
    public String getClassCode(){
        return classCode;
    }

    /**
    * 设置分类编码
    * 
    * @param classCode 要设置的分类编码
    */
    public void setClassCode(String classCode){
        this.classCode = classCode;
    }

    /**
    * 获取分类名称
    *
    * @return 分类名称
    */
    public String getClassName(){
        return className;
    }

    /**
    * 设置分类名称
    * 
    * @param className 要设置的分类名称
    */
    public void setClassName(String className){
        this.className = className;
    }

    /**
    * 获取分类简写
    *
    * @return 分类简写
    */
    public String getSimpleName(){
        return simpleName;
    }

    /**
    * 设置分类简写
    * 
    * @param simpleName 要设置的分类简写
    */
    public void setSimpleName(String simpleName){
        this.simpleName = simpleName;
    }

    /**
    * 获取上层分类ID
    *
    * @return 上层分类ID
    */
    public String getParentClass(){
        return parentClass;
    }

    /**
    * 设置上层分类ID
    * 
    * @param parentClass 要设置的上层分类ID
    */
    public void setParentClass(String parentClass){
        this.parentClass = parentClass;
    }

    /**
    * 获取分类层级
    *
    * @return 分类层级
    */
    public Integer getLevelNum(){
        return levelNum;
    }

    /**
    * 设置分类层级
    * 
    * @param levelNum 要设置的分类层级
    */
    public void setLevelNum(Integer levelNum){
        this.levelNum = levelNum;
    }

    /**
    * 获取备注
    *
    * @return 备注
    */
    public String getMemos(){
        return memos;
    }

    /**
    * 设置备注
    * 
    * @param memos 要设置的备注
    */
    public void setMemos(String memos){
        this.memos = memos;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getCreateUserId(){
        return createUserId;
    }

    /**
    * 设置
    * 
    * @param createUserId 要设置的
    */
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getEditUserId(){
        return editUserId;
    }

    /**
    * 设置
    * 
    * @param editUserId 要设置的
    */
    public void setEditUserId(String editUserId){
        this.editUserId = editUserId;
    }

    /**
    * 获取
    *
    * @return 
    */


    /**
    * 获取udf相关规则{key:key,type:type,value:{}}
    *
    * @return udf相关规则{key:key,type:type,value:{}}
    */
    public String getUdf1(){
        return udf1;
    }

    /**
    * 设置udf相关规则{key:key,type:type,value:{}}
    * 
    * @param udf1 要设置的udf相关规则{key:key,type:type,value:{}}
    */
    public void setUdf1(String udf1){
        this.udf1 = udf1;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf2(){
        return udf2;
    }

    /**
    * 设置
    * 
    * @param udf2 要设置的
    */
    public void setUdf2(String udf2){
        this.udf2 = udf2;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf3(){
        return udf3;
    }

    /**
    * 设置
    * 
    * @param udf3 要设置的
    */
    public void setUdf3(String udf3){
        this.udf3 = udf3;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf4(){
        return udf4;
    }

    /**
    * 设置
    * 
    * @param udf4 要设置的
    */
    public void setUdf4(String udf4){
        this.udf4 = udf4;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf5(){
        return udf5;
    }

    /**
    * 设置
    * 
    * @param udf5 要设置的
    */
    public void setUdf5(String udf5){
        this.udf5 = udf5;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf6(){
        return udf6;
    }

    /**
    * 设置
    * 
    * @param udf6 要设置的
    */
    public void setUdf6(String udf6){
        this.udf6 = udf6;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf7(){
        return udf7;
    }

    /**
    * 设置
    * 
    * @param udf7 要设置的
    */
    public void setUdf7(String udf7){
        this.udf7 = udf7;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf8(){
        return udf8;
    }

    /**
    * 设置
    * 
    * @param udf8 要设置的
    */
    public void setUdf8(String udf8){
        this.udf8 = udf8;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf9(){
        return udf9;
    }

    /**
    * 设置
    * 
    * @param udf9 要设置的
    */
    public void setUdf9(String udf9){
        this.udf9 = udf9;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf10(){
        return udf10;
    }

    /**
    * 设置
    * 
    * @param udf10 要设置的
    */
    public void setUdf10(String udf10){
        this.udf10 = udf10;
    }

    /**
    * 获取迁移数据标识
    *
    * @return 迁移数据标识
    */
    public String getRequestId(){
        return requestId;
    }

    /**
    * 设置迁移数据标识
    * 
    * @param requestId 要设置的迁移数据标识
    */
    public void setRequestId(String requestId){
        this.requestId = requestId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}