package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/enterprise")
@RestController
@ResponseBody
@Api("企业中心接口")
public class EnterpriseController {
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @ApiOperation("生成企业编码")
    @RequestMapping(value = "/enterpriseNo",method = RequestMethod.GET)
    public APIResponse<String> createEpNo(){
        return APIResponse.success(enterpriseInfoService.createEnterpriseNo());
    }

}
