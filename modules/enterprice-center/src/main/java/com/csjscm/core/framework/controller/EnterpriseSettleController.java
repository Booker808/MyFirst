package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseAccount;
import com.csjscm.core.framework.model.EnterpriseSettle;
import com.csjscm.core.framework.service.enterprise.EnterpriseAccountService;
import com.csjscm.core.framework.service.enterprise.EnterpriseSettleService;
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


@Api("结算信息")
@Controller
@RequestMapping("/enterprise/settle")
@ResponseBody
public class EnterpriseSettleController {
    @Autowired
    private EnterpriseSettleService enterpriseSettleService;
    /**
     * 查询企结算信息列表
     *
     * @param
     * @return
     */
    @ApiOperation("查询结算信息列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseSettle> enterpriseSettles = enterpriseSettleService.listSelective(map);
        return APIResponse.success(enterpriseSettles);
    }

    /**
     * 新建结算信息
     *
     * @param
     * @return
     */
    @ApiOperation("新建结算信息信息")
    @RequestMapping(value = "saveEnterpriseSettle",method = RequestMethod.POST)
    public APIResponse saveEnterpriseAttachment(@RequestBody @Valid EnterpriseSettle enterpriseSettle){
        enterpriseSettleService.save(enterpriseSettle);
        return APIResponse.success();
    }

    /**
     * 更新结算信息
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新结算信息信息")
    @RequestMapping(value = "editEnterpriseSettle",method = RequestMethod.POST)
    public APIResponse editEnterpriseAttachment(
            @RequestBody  @Valid   EnterpriseSettle enterpriseSettle){
        if(enterpriseSettle.getId()!=null){
            enterpriseSettleService.update(enterpriseSettle);
        }
        return APIResponse.success();
    }



    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
