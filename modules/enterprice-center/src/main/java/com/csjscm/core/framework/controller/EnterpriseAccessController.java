package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.model.EnterpriseCategory;
import com.csjscm.core.framework.model.EnterpriseProtocol;
import com.csjscm.core.framework.service.enterprise.EnterpriseCategoryService;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.enterprise.EnterpriseProtocolService;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoAccessDto;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/enterprise/access")
@RestController
@ResponseBody
@Api
public class EnterpriseAccessController {
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    @Autowired
    private EnterpriseCategoryService enterpriseCategoryService;
    @Autowired
    private EnterpriseProtocolService enterpriseProtocolService;

    @ApiModelProperty("新增基本信息并准入")
    @RequestMapping(value = "enterpriseInfoAccess",method = RequestMethod.POST)
    public APIResponse<String> insertEnterpriseInfoAccess(@RequestBody EnterpriseInfoAccessDto enterpriseInfoAccessDto){
        enterpriseInfoAccessDto.setCheckState("3");
        enterpriseInfoAccessDto.setIsvalid(0);
        String result=enterpriseInfoService.insertEnterpriseInfo(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return APIResponse.fail(result);
        }
        enterpriseCategoryService.save(enterpriseInfoAccessDto.getEnterpriseCategory());
        enterpriseProtocolService.save(enterpriseInfoAccessDto.getEnterpriseProtocol());
        return APIResponse.success("新建成功");
    }
}
