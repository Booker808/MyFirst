package com.csjscm.core.framework.elasticsearch.constant;

import com.csjscm.core.framework.elasticsearch.model.ProductCore;
import com.csjscm.core.framework.elasticsearch.model.ProductCustomer;
import com.csjscm.core.framework.elasticsearch.model.ProductPartner;

public enum EsObjEnum {
    SKU_CORE("sku_core","product", ProductCore.class),
    SKU_PARTNER("sku_partner","product", ProductPartner.class),
    SKU_CUSTOMER("sku_customer","product", ProductCustomer.class);

    private String index;
    private String type;
    private Class clazz;

    EsObjEnum(String index, String type, Class clazz) {
        this.index=index;
        this.type=type;
        this.clazz=clazz;
    }
    public static EsObjEnum getEsObjEnum(String index){
        for(EsObjEnum objEnum:EsObjEnum.values()){
            if(objEnum.getIndex().equals(index)){
                return objEnum;
            }
        }
        return null;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
