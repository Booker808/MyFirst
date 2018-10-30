package com.csjscm.core.framework.model;

import com.csjscm.core.framework.annotation.Creator;
import com.csjscm.core.framework.annotation.Editor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaxVersion {
    private Integer id;

    private String version;

    @ApiModelProperty("是否启用0未启用1已启用")
    private Integer enable;

    @Creator
    private String createUser;

    private Date createTime;

    @Editor
    private String editUser;

    private Date editTime;
}