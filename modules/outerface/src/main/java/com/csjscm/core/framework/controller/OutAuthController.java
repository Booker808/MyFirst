package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/outerface/auth/")
@ResponseBody
public class OutAuthController {
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    @RequestMapping(value = "/enterpriseInfo",method = RequestMethod.GET)
    public APIResponse<QueryResult<EnterpriseInfo>> queryEnterpriseInfo(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition
         ){
        if(condition==null){
            condition=new HashMap<>();
        }
        condition.put("isvalid",1);
        QueryResult<EnterpriseInfo> result=enterpriseInfoService.selectEnterpriseInfoPage(page,rpp,condition);
        return APIResponse.success(result);
    }
}
