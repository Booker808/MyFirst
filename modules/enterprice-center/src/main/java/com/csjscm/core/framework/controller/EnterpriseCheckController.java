package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.HttpClientUtil;
import com.csjscm.core.framework.model.EnterpriseFlow;
import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.service.enterprise.EnterpriseFlowService;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.vo.EnterpriseFlowModel;
import com.csjscm.core.framework.vo.MyTodoDealWithVo;
import com.csjscm.sweet.framework.auth.AuthUtils;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/enterprise/check")
@RestController
@ResponseBody
@Api("供应商审核相关接口")
public class EnterpriseCheckController {

    private static final Logger logger = LoggerFactory.getLogger(EnterpriseCheckController.class);
    @Autowired
    private EnterpriseFlowService enterpriseFlowService;
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    /**
     * 获取代办信息
     *
     * @param
     * @return
     */
    @ApiOperation("获取代办信息")
    @RequestMapping(value = "myTodoDealWith", method = RequestMethod.GET)
    public APIResponse myTodoDealWith() {
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) + Constant.ENTERPRISE_CHECK_OA_MYTODODEALWITH_URL;
        Map<String, String> map = new HashMap<>();
        map.put("userId", sessionUser.getString("name"));
        String post = "";
        try {
            post = HttpClientUtil.post(url, map);
            logger.info(post);
        } catch (IOException e) {
            throw new BussinessException("提交请求oa接口地址异常,地址:" + url);
        }
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败:" + jsonObject.getString("message"));
        }
        JSONArray data = jsonObject.getJSONObject("data").getJSONArray("TodoWorkFlowInfoList");
        List<MyTodoDealWithVo> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject jsonObject1 = data.getJSONObject(i);
            EnterpriseInfo bussinessKey = enterpriseInfoService.selectByPrimaryKey(jsonObject1.getString("bussinessKey"));
            if (bussinessKey != null) {
                MyTodoDealWithVo vo = new MyTodoDealWithVo();
                vo.setApplyer(jsonObject1.getString("applyer"));
                vo.setApplyTime(jsonObject1.getString("applyTime"));
                vo.setEntName(bussinessKey.getEntName());
                vo.setEntNumber(bussinessKey.getEntNumber());
                vo.setTaskId(jsonObject1.getString("taskId"));
                vo.setTaskName(jsonObject1.getString("taskName"));
                vo.setCheckState(bussinessKey.getCheckState());
                list.add(vo);
            }
        }
        return APIResponse.success(list);
    }


    @ApiOperation("开始审批，提交审核")
    @RequestMapping(value = "startSupplierAdmittanceWorkFlow", method = RequestMethod.POST)
    public APIResponse startSupplierAdmittanceWorkFlow(@RequestBody EnterpriseFlow enterpriseFlow) {
        BeanValidator.validate(enterpriseFlow);
        enterpriseFlowService.saveFirstCheck(enterpriseFlow);
        return APIResponse.success();
    }

    @ApiOperation("正常审批")
    @RequestMapping(value = "myTodoComplete", method = RequestMethod.POST)
    public APIResponse myTodoComplete(@RequestBody EnterpriseFlowModel enterpriseFlowModel) {
        BeanValidator.validate(enterpriseFlowModel);
        enterpriseFlowService.saveNormal(enterpriseFlowModel);
        return APIResponse.success();
    }
    @ApiOperation("获取流程列表")
    @RequestMapping(value = "checkFlowList", method = RequestMethod.GET)
    public APIResponse checkFlowList(@ApiParam(name = "entNumber",value = "企业编码" ,required = true) String entNumber) {
       Map<String,Object> map=new HashMap<>();
       map.put("entNumber",entNumber);
        List<EnterpriseFlow> enterpriseFlows = enterpriseFlowService.listSelective(map);
        return APIResponse.success(enterpriseFlows);
    }
    @ApiOperation("获取流程节点详情")
    @RequestMapping(value = "checkFlowInfo", method = RequestMethod.GET)
    public APIResponse checkFlowInfo(@ApiParam(name = "id",value = "流程列表id" ,required = true) Integer id) {
        EnterpriseFlow byPrimary = enterpriseFlowService.findByPrimary(id);
        return APIResponse.success(byPrimary);
    }

    /**
     * 自定义异常捕获
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public APIResponse validationExp(ValidationException e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
