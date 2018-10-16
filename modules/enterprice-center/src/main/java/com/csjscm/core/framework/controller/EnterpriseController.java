package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.enums.TradeTypeEnum;
import com.csjscm.core.framework.common.util.HttpClientUtil;
import com.csjscm.core.framework.example.EnterpriseInfoExample;
import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("新建企业信息")
    @RequestMapping(value = "/enterpriseInfo",method = RequestMethod.POST)
    public APIResponse<String> saveEnterpriseInfo(@RequestBody EnterpriseInfoDto enterpriseInfoDto){
        enterpriseInfoDto.getEnterpriseInfo().setCheckState("1");
        enterpriseInfoDto.getEnterpriseInfo().setIsvalid(0);
        String result=enterpriseInfoService.insertEnterpriseInfo(enterpriseInfoDto);
        if(StringUtils.isEmpty(result)){
            return APIResponse.success("新建成功");
        }else{
            return APIResponse.fail(result);
        }
    }

    @ApiOperation("更新企业基本信息")
    @RequestMapping(value = "/enterpriseInfo",method = RequestMethod.PUT)
    public APIResponse<String> updateEnterpriseInfo(@RequestBody EnterpriseInfo enterpriseInfo){
        String result=enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo);
        if(StringUtils.isEmpty(result)){
            return APIResponse.success("更新成功");
        }else{
            return APIResponse.fail(result);
        }
    }

    @ApiOperation("模糊查询企业名称")
    @RequestMapping(value = "/enterpriseName",method = RequestMethod.GET)
    public APIResponse<List<String>> queryEnterpriseName(@RequestParam(required = false) String name){
        List<String> nameList=enterpriseInfoService.queryEnterpriseName(name);
        return APIResponse.success(nameList);
    }

    @ApiOperation("查询企业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="entNumber",value="企业编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="entName",value="企业名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "channel",value = "来源（1平台2商城）",dataType = "String",paramType = "query")
    })
    @RequestMapping(value = "/enterpriseInfo",method = RequestMethod.GET)
    public APIResponse<QueryResult<EnterpriseInfoDto>> queryEnterpriseInfo(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition,
            @ApiParam(name = "企业类型") @RequestParam(required = false) List<Integer> entType){
        EnterpriseInfoExample enterpriseInfoExample=new EnterpriseInfoExample();
        condition.put("entType",entType);
        if(condition!=null){
            enterpriseInfoExample= JSON.parseObject(JSON.toJSONString(condition),EnterpriseInfoExample.class);
        }
        QueryResult<EnterpriseInfoDto> result=enterpriseInfoService.queryEnterpriseInfo(page,rpp,enterpriseInfoExample);
        return APIResponse.success(result);
    }
    @ApiOperation("根据类型查询企业")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entType",value="企业类型 1供应商 2采购商",required=true) Integer entType){
        String entTypeIn="("+ TradeTypeEnum.供应商采购商.getState()+","+entType+")";
        Map<String,Object> map=new HashMap<>();
        map.put("entTypeIn",entTypeIn);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoService.listSelective(map);
        return APIResponse.success(enterpriseInfos);
    }
    @ApiOperation("获取我方合同主体")
    @RequestMapping(value = "getOrgByTcode",method = RequestMethod.GET)
    public APIResponse getOrgByTcode() throws IOException {
        String url = System.getProperty(Constant.SHIRO_DOMAIN) + Constant.ENTERPRISE_SHIRO_GETORG;
        String property = System.getProperty("spring.application.name");
        Map<String,String> map=new HashMap<>();
        map.put("code",property);
        String s = HttpClientUtil.get(url, map);
        JSONObject jsonObject = JSONObject.parseObject(s);
        return APIResponse.success(jsonObject.get("data"));
    }
}
