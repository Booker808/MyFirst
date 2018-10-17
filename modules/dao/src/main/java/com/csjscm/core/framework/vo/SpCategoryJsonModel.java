package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sp 1-3级分类 json model
 */
public class SpCategoryJsonModel implements Comparable<SpCategoryJsonModel>, Serializable {

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
    private String simpleName;

    /**
     * 上层分类ID
     */
    @NotNull(message = "上层分类ID不能为空")
    private Integer parentClass;

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

    private String bigImg;

    private String midImg;



    private List<SpCategoryJsonModel> children;

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getMidImg() {
        return midImg;
    }

    public void setMidImg(String midImg) {
        this.midImg = midImg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @NotNull
    public Integer getParentClass() {
        return parentClass;
    }

    public void setParentClass(@NotNull Integer parentClass) {
        this.parentClass = parentClass;
    }

    @NotNull
    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(@NotNull Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getMemos() {
        return memos;
    }

    public void setMemos(String memos) {
        this.memos = memos;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(String editUserId) {
        this.editUserId = editUserId;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    public String getUdf6() {
        return udf6;
    }

    public void setUdf6(String udf6) {
        this.udf6 = udf6;
    }

    public String getUdf7() {
        return udf7;
    }

    public void setUdf7(String udf7) {
        this.udf7 = udf7;
    }

    public String getUdf8() {
        return udf8;
    }

    public void setUdf8(String udf8) {
        this.udf8 = udf8;
    }

    public String getUdf9() {
        return udf9;
    }

    public void setUdf9(String udf9) {
        this.udf9 = udf9;
    }

    public String getUdf10() {
        return udf10;
    }

    public void setUdf10(String udf10) {
        this.udf10 = udf10;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
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

    public List<SpCategoryJsonModel> getChildren() {
        return children;
    }

    public void setChildren(List<SpCategoryJsonModel> children) {
        this.children = children;
    }

    @Override
    public int compareTo(SpCategoryJsonModel o) {
        if (this.sort == null || o.sort == null) {
            return 0;
        }
        return this.sort - o.sort;
    }
}
