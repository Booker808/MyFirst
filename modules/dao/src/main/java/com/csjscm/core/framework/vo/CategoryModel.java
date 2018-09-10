package com.csjscm.core.framework.vo;

/**
 * 分类传入的model
 */
public class CategoryModel {
    /**
     * 要停用启用的id，多个以逗号隔开
     * 要删除的id，多个以逗号隔开
     */
    private  String ids;
    /**
     * 1启用 0停用
     */
    private  Integer state;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
