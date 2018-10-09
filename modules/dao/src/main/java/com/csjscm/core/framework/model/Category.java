package com.csjscm.core.framework.model;


import lombok.Data;
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

@Data
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
    private String simpleName;

    /**
    * 上层分类ID
    */
    @NotBlank(message = "上层分类ID不能为空")
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
}