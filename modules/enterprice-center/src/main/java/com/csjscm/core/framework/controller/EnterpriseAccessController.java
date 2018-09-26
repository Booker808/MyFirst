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
        if(enterpriseInfoAccessDto.getEnterpriseCategory()==null){
            return APIResponse.fail("其他信息不能为空");
        }
        if(enterpriseInfoAccessDto.getEnterpriseProtocol()==null){
            return APIResponse.fail("框架协议信息不能为空");
        }
        enterpriseInfoAccessDto.getEnterpriseInfo().setIsvalid(0);
        String result=enterpriseInfoService.insertEnterpriseInfo(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return APIResponse.fail(result);
        }
        enterpriseInfoAccessDto.getEnterpriseCategory().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
        enterpriseInfoAccessDto.getEnterpriseProtocol().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
        enterpriseCategoryService.save(enterpriseInfoAccessDto.getEnterpriseCategory());
        enterpriseProtocolService.save(enterpriseInfoAccessDto.getEnterpriseProtocol());
        return APIResponse.success("新建成功");
    }

    @ApiModelProperty("修改审核信息（采购商变更供应商）")
    @RequestMapping(value = "enterpriseInfoAccess",method = RequestMethod.PUT)
    public APIResponse<String> updateEnterpriseInfoAccess(@RequestBody EnterpriseInfoAccessDto enterpriseInfoAccessDto){

        if(enterpriseInfoAccessDto.getEnterpriseCategory()==null){
            return APIResponse.fail("其他信息不能为空");
        }
        if(enterpriseInfoAccessDto.getEnterpriseProtocol()==null){
            return APIResponse.fail("框架协议信息不能为空");
        }
        enterpriseInfoAccessDto.getEnterpriseInfo().setIsvalid(0);
        String result=enterpriseInfoService.updateEnterpriseDetail(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return APIResponse.fail(result);
        }

        if(enterpriseInfoAccessDto.getEnterpriseCategory().getId()==null){
            enterpriseCategoryService.save(enterpriseInfoAccessDto.getEnterpriseCategory());
        }else{
            enterpriseCategoryService.update(enterpriseInfoAccessDto.getEnterpriseCategory());
        }
        if(enterpriseInfoAccessDto.getEnterpriseProtocol().getId()==null){
            enterpriseProtocolService.save(enterpriseInfoAccessDto.getEnterpriseProtocol());
        }else{
            enterpriseProtocolService.update(enterpriseInfoAccessDto.getEnterpriseProtocol());
        }
        return APIResponse.success("修改成功");
    }
}
