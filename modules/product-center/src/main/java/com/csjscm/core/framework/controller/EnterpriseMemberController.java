package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseMember;
import com.csjscm.core.framework.model.EnterpriseSettlementInfo;
import com.csjscm.core.framework.model.EnterpriseTicketInfo;
import com.csjscm.core.framework.model.SysDict;
import com.csjscm.core.framework.service.*;
import com.csjscm.core.framework.vo.EnterpriseMemberModel;
import com.csjscm.core.framework.vo.EnterpriseUpdateModel;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Api("企业会员")
@Controller
@RequestMapping("/enterprise/member")
@ResponseBody
public class EnterpriseMemberController {
    @Autowired
    private EnterpriseMemberService enterpriseMemberService;
    @Autowired
    private EnterpriseSettlementInfoService enterpriseSettlementInfoService;
    @Autowired
    private EnterpriseTicketInfoService enterpriseTicketInfoService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysDictDetailService sysDictDetailService;



    /**
     * 查询分类接口
     *
     * @return
     */
    @ApiOperation("企业会员分页接口")
    @RequestMapping(value = "enterpriseMemberPage",method = RequestMethod.GET)
    public APIResponse<QueryResult<EnterpriseMember>> queryCategoryList(@ApiParam(name="entName",value="企业名称",required=false) @RequestParam(value = "entName",required = false) String entName,
                                                                        @ApiParam(name="current",value="当前页",required=true) @RequestParam(value = "current") int current,
                                                                        @ApiParam(name="pageSize",value="页面大小",required=true) @RequestParam(value = "pageSize") int pageSize,
                                                                        @ApiParam(name="tradeType",value=" 1供应商  2采购商 3供应商&采购商",required=false) @RequestParam(value = "tradeType",required = false) Integer tradeType,
                                                                        @ApiParam(name="entNumber",value="企业编号",required=false) @RequestParam(value = "entNumber",required = false) String entNumber){

        Map<String,Object> map=new HashMap<>();
        map.put("entNameLike",entName);
        map.put("tradeType",tradeType);
        map.put("entNumber",entNumber);
        QueryResult<EnterpriseMember> page = enterpriseMemberService.findPage(map, current, pageSize);
        return APIResponse.success(page);
    }

    /**
     * 创建企业会员
     *
     * @param enterpriseMemberModel
     * @return
     */
    @ApiOperation("新增企业会员")
    @RequestMapping(value = "saveEnterpriseMember",method = RequestMethod.POST)
    public APIResponse createCategory(@RequestBody @Valid EnterpriseMemberModel enterpriseMemberModel){
        enterpriseMemberService.saveEnterpriseMember(enterpriseMemberModel);
        return APIResponse.success();
    }
    @ApiOperation("获取银行集合")
    @RequestMapping(value = "getBankList",method = RequestMethod.GET)
    public APIResponse getBankList(@ApiParam(name="bankName",value="筛选的银行名称，不传查询全部银行",required=false)String bankName){
        String  entNumber= Constant.ENTNUMBER_INDEX+System.currentTimeMillis()+(int)((Math.random()*9+1)*100000);
        SysDict byCode = sysDictService.findByCode(Constant.DICT_CODE_BANK);
        if(byCode==null){
            return APIResponse.success();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("parentId",byCode.getId());
        if(StringUtils.isNotBlank(bankName)){
            map.put("itemValueLike",bankName);
        }
        return APIResponse.success(sysDictDetailService.findListByMap(map));
    }
    @ApiOperation("获取企业会员信息")
    @RequestMapping(value = "getEnterpriseMember",method = RequestMethod.GET)
    public APIResponse getEnterpriseMember(@ApiParam(name="id",value="列表的主键id",required=true)Integer id){
        EnterpriseMember enterpriseMember = enterpriseMemberService.findByPrimary(id);
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",enterpriseMember.getEntNumber());
        EnterpriseSettlementInfo enterpriseSettlementInfo = enterpriseSettlementInfoService.findSelective(map);
        EnterpriseTicketInfo enterpriseTicketInfo = enterpriseTicketInfoService.findSelective(map);
        Map<String,Object> result=new HashMap<>();
        result.put("enterpriseMember",enterpriseMember);
        result.put("enterpriseSettlementInfo",enterpriseSettlementInfo);
        result.put("enterpriseTicketInfo",enterpriseTicketInfo);
        return APIResponse.success(result);
    }
    /**
     * 更新企业会员
     *
     * @param enterpriseUpdateModel
     * @return
     */
    @ApiOperation("更新企业会员")
    @RequestMapping(value = "updateEnterpriseMember",method = RequestMethod.POST)
    public APIResponse updateEnterpriseMember(@RequestBody @Valid EnterpriseUpdateModel enterpriseUpdateModel){
        enterpriseMemberService.updateEnterpriseModel(enterpriseUpdateModel);
        return APIResponse.success();
    }


    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }

}
