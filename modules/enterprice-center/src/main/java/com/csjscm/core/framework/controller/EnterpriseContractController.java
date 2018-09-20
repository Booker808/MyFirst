package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseContract;
import com.csjscm.core.framework.service.enterprise.EnterpriseContractService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api("合同")
@Controller
@RequestMapping("/enterprise/contract")
@ResponseBody
public class EnterpriseContractController {
    @Autowired
    private EnterpriseContractService enterpriseContractService;
    /**
     * 查询合同列表
     *
     * @param
     * @return
     */
    @ApiOperation("查询合同列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseContract> enterpriseContracts = enterpriseContractService.listSelective(map);
        return APIResponse.success(enterpriseContracts);
    }

    /**
     * 新建联系人
     *
     * @param
     * @return
     */
    @ApiOperation("新建合同")
    @RequestMapping(value = "saveEnterpriseContract",method = RequestMethod.POST)
    public APIResponse saveEnterpriseContract(@RequestBody @Valid EnterpriseContract enterpriseContract){
        enterpriseContractService.save(enterpriseContract);
        return APIResponse.success();
    }

    /**
     * 更新合同
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新合同")
    @RequestMapping(value = "editEnterpriseContract",method = RequestMethod.POST)
    public APIResponse editEnterpriseContract(
            @RequestBody  @Valid  EnterpriseContract enterpriseContract){
        if(enterpriseContract.getId()!=null){
            enterpriseContractService.update(enterpriseContract);
        }
        return APIResponse.success();
    }



    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
