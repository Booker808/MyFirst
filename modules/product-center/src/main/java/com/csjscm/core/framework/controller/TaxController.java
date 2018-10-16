package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.auth.AuthUtils;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api("税务API")
@RequestMapping("/product/tax")
@RestController
@ResponseBody
public class TaxController {
    @Autowired
    private TaxService taxService;

    @ApiOperation("导入税务分类Excel")
    @RequestMapping(value = "/taxCategory/{versionId}/_import",method = RequestMethod.POST)
    public APIResponse importtaxCategoryExcel(
            @PathVariable Integer versionId,
            @ApiParam(name = "file",value = "excel文件")@RequestParam MultipartFile file){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        String userName=null;
        if(sessionUser!=null){
            userName=sessionUser.getString("name");
        }
        Map<String,Object> map=taxService.importtaxCategoryExcel(userName,versionId,file);
        return APIResponse.success(map);
    }

}
