package com.csjscm.core.framework.vo;

import com.csjscm.core.framework.model.SkuCustomer;
import lombok.Data;

@Data
public class SkuCustomerPageVo extends SkuCustomer {
    /**
     * 细分类编码
     */
    private String categoryNo;

    /**
     * 业务一级分类编码
     */
    private String lv1CategorySpNo;
    /**
     * 业务二级分类编码
     */
    private String lv2CategorySpNo;
    /**
     * 业务三级分类编码
     */
    private String categorySpNo;
    /**
     * 底层二级分类编码
     */
    private String lv2CategoryNo;
    /**
     * 底层一级分类编码
     */
    private String lv1CategoryNo;
    /**
     * 来源渠道（1：平台手动新增；2：平台导入；3：来自西域； 4：来自商城； 5：来自scm）
     */
    private Integer channel;

    private String entName;
    private String mnemonicCode;
    private String ean13Code;



}
