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

    @ApiModelProperty("获取企业准入详情信息")
    @RequestMapping(value = "enterpriseInfoAccess/{entNumber}",method = RequestMethod.GET)
    public APIResponse<EnterpriseInfoAccessDto> queryEnterpriseInfoAccess(@PathVariable String entNumber){
        return APIResponse.success(enterpriseInfoService.queryEnterpriseInfoAccess(entNumber));
    }


    @ApiModelProperty("新增基本信息并准入")
    @RequestMapping(value = "enterpriseInfoAccess",method = RequestMethod.POST)
    public APIResponse<String> insertEnterpriseInfoAccess(@RequestBody EnterpriseInfoAccessDto enterpriseInfoAccessDto){
        String result=checkAccessEmpty(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return APIResponse.fail(result);
        }
        enterpriseInfoAccessDto.getEnterpriseProtocol().setNumber(enterpriseProtocolService.createProtocolNo());
        enterpriseInfoAccessDto.getEnterpriseInfo().setIsvalid(0);
        result=enterpriseInfoService.insertEnterpriseInfo(enterpriseInfoAccessDto);
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

        String result=checkAccessEmpty(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return APIResponse.fail(result);
        }
        enterpriseInfoAccessDto.getEnterpriseProtocol().setNumber(enterpriseProtocolService.createProtocolNo());
        enterpriseInfoAccessDto.getEnterpriseInfo().setIsvalid(0);
        result=enterpriseInfoService.updateEnterpriseDetail(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return APIResponse.fail(result);
        }

        if(enterpriseInfoAccessDto.getEnterpriseCategory().getId()==null){
            enterpriseInfoAccessDto.getEnterpriseCategory().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
            enterpriseCategoryService.save(enterpriseInfoAccessDto.getEnterpriseCategory());
        }else{
            enterpriseCategoryService.update(enterpriseInfoAccessDto.getEnterpriseCategory());
        }
        if(enterpriseInfoAccessDto.getEnterpriseProtocol().getId()==null){
            enterpriseInfoAccessDto.getEnterpriseProtocol().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
            enterpriseProtocolService.save(enterpriseInfoAccessDto.getEnterpriseProtocol());
        }else{
            enterpriseProtocolService.update(enterpriseInfoAccessDto.getEnterpriseProtocol());
        }
        return APIResponse.success("修改成功");
    }

    private String checkAccessEmpty(EnterpriseInfoAccessDto enterpriseInfoAccessDto){

        if(enterpriseInfoAccessDto.getEnterpriseInfo()==null||StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber())){
            return "企业编码不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseInfo().getEntName())){
            return "企业名称不能为空";
        }
        if(enterpriseInfoAccessDto.getLegalPerson()==null||StringUtils.isEmpty(enterpriseInfoAccessDto.getLegalPerson().getName())){
            return "法人不能为空";
        }
        if(enterpriseInfoAccessDto.getEnterpriseContact()==null||StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseContact().getName())){
            return "联系人不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseContact().getPhone())){
            return "联系人电话不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseInfo().getRegisterAddress())){
            return "注册地址不能为空";
        }
        if(enterpriseInfoAccessDto.getEnterpriseAttachment()==null||StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseAttachment().getAttachmentUrl())){
            return "营业执照不能为空";
        }

        if(StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseInfo().getTaxpayerId())){
            return "纳税人识别号不能为空";
        }
        if(enterpriseInfoAccessDto.getEnterpriseAccount()==null||StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseAccount().getBankName())){
            return "基本开户银行不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseAccount().getBankNo())){
            return "基本开户账号不能为空";
        }


        if(enterpriseInfoAccessDto.getEnterpriseCategory()==null
                ||StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseCategory().getBrandName())
                ||enterpriseInfoAccessDto.getEnterpriseCategory().getBrandLevel()==null
                ||StringUtils.isEmpty(enterpriseInfoAccessDto.getEnterpriseCategory().getMainProducts())
                ||enterpriseInfoAccessDto.getEnterpriseCategory().getSupplyState()==null
                ||enterpriseInfoAccessDto.getEnterpriseCategory().getSupplyStartTime()==null
                ||enterpriseInfoAccessDto.getEnterpriseCategory().getSupplyEndTime()==null){
            return "其他信息不能为空";
        }
        if(enterpriseInfoAccessDto.getEnterpriseProtocol()==null){
            return "框架协议信息不能为空";
        }
        return "";
    }
}
