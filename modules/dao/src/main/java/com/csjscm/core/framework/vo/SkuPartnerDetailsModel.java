package com.csjscm.core.framework.vo;

import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.model.SkuPartnerUom;

import java.util.List;

public class SkuPartnerDetailsModel extends SkuPartner {
    private List<SkuPartnerUom>  uoms;
    private String classCode;
    private String className;
    private String minUint;

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
    }

    public List<SkuPartnerUom> getUoms() {
        return uoms;
    }

    public void setUoms(List<SkuPartnerUom> uoms) {
        this.uoms = uoms;
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
}
