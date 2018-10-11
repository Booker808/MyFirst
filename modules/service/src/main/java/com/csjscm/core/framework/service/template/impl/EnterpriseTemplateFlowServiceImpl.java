package com.csjscm.core.framework.service.template.impl;

import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.HttpClientUtil;
import com.csjscm.core.framework.dao.EnterprisePurchaseTemplateMapper;
import com.csjscm.core.framework.model.EnterprisePurchaseTemplate;
import com.csjscm.core.framework.service.template.EnterpriseTemplateFlowService;
import com.csjscm.core.framework.service.template.model.CheckTaskVo;
import com.csjscm.core.framework.service.template.model.HisWorkFlowInfo;
import com.csjscm.core.framework.service.template.model.TodoWorkFlowInfo;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateDetailVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EnterpriseTemplateFlowServiceImpl implements EnterpriseTemplateFlowService {
    @Autowired
    private EnterprisePurchaseTemplateMapper purchaseTemplateMapper;

    private static final String BUSINESS_KEY_PREFIX ="purchaseTemplate:";

    private static final String PROCESS_DEFINITION_KEY = "templateApproval1.0";

    /**
     * 提交审批申请
     *
     * @param templateDetailVo
     */
    @Override
    @Transactional
    public void submitPurchaseTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo) {
        Integer templateId=templateDetailVo.getId();
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) +
                Constant.ENTERPRISE_CHECK_PURCHASE_TEMPLATE_START_URL;
        Map<String, String> map = new HashMap<>();
        map.put("userId", templateDetailVo.getEditUser());
        map.put("businessKey", BUSINESS_KEY_PREFIX + templateId);
        String post = "";
        try{
            post= HttpClientUtil.post(url,map);
            log.info(post);
        }catch(IOException e){
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }

        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }
        String instanceId=jsonObject.getJSONObject("data").getString("processInstanceId");

        EnterprisePurchaseTemplate purchaseTemplate=new EnterprisePurchaseTemplate();
        purchaseTemplate.setId(templateId);
        purchaseTemplate.setTemplateUrl(templateDetailVo.getTemplateUrl());
        purchaseTemplate.setInstanceId(instanceId);
        purchaseTemplate.setCheckDescription(getCurrentCheckStatus(templateId,instanceId));

        purchaseTemplateMapper.updateByPrimaryKeySelective(purchaseTemplate);
    }

    @Override
    public String getToDoTaskId(Integer templateId, String userName) {
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) +
                Constant.ENTERPRISE_CHECK_PURCHASE_TEMPLATE_MYTODODEALWITH_URL;
        Map<String, String> map = new HashMap<>();
        map.put("userId", userName);

        String post = "";
        try{
            post= HttpClientUtil.post(url,map);
            log.info(post);
        }catch(IOException e){
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }

        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }

        List<TodoWorkFlowInfo> todoWorkFlowInfoList= JSONObject.parseArray(
                jsonObject.getJSONObject("data").getString("TodoWorkFlowInfoList"),TodoWorkFlowInfo.class);
        for (TodoWorkFlowInfo info:todoWorkFlowInfoList){
            if( info.getBussinessKey().equals(BUSINESS_KEY_PREFIX + templateId)){
                return info.getTaskId();
            }
        }
        return "";
    }

    @Override
    public void checkTask(String userName, CheckTaskVo checkTask) {

        EnterprisePurchaseTemplate purchaseTemplate=purchaseTemplateMapper.selectByPrimaryKey(checkTask.getTemplateId());

        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) +
                Constant.ENTERPRISE_CHECK_PURCHASE_TEMPLATE_COMPLETE_URL;
        Map<String,String> map= Maps.newHashMap();
        map.put("userId", userName);
        map.put("taskId",checkTask.getTaskId());
        map.put("suggestionStatus",checkTask.getSuggestionStatus().toString());
        map.put("templateType",String.valueOf((char)('a'+purchaseTemplate.getTemplateType()-1)));
        map.put("suggestion",checkTask.getOpinion());
        map.put("processInstanceId",purchaseTemplate.getInstanceId());

        String post = "";
        try{
            post= HttpClientUtil.post(url,map);
            log.info(post);
        }catch(IOException e){
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }

        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }
        purchaseTemplate.setId(checkTask.getTemplateId());
        purchaseTemplate.setCheckDescription(getCurrentCheckStatus(checkTask.getTemplateId(),purchaseTemplate.getInstanceId()));
        purchaseTemplateMapper.updateByPrimaryKeySelective(purchaseTemplate);
    }

    @Override
    public List<HisWorkFlowInfo> queryHisFlow(String instanceId) {
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) +
                Constant.ENTERPRISE_CHECK_OA_FLOW_TASK_INFO_URL;
        Map<String, String> map = new HashMap<>();
        map.put("instanceId",instanceId);
        map.put("processDefinitionKey",PROCESS_DEFINITION_KEY);
        String post = "";
        try{
            post= HttpClientUtil.post(url,map);
            log.info(post);
        }catch(IOException e){
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }

        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }
        return JSONObject.parseArray(jsonObject.getString("data"),HisWorkFlowInfo.class);
    }

    /**
     * 根据业务模板ID和实例ID查询OA中当前流程状态
     *
     * @param templateId
     * @param instanceId
     * @return
     */
    private String getCurrentCheckStatus(Integer templateId,String instanceId){
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) +
                Constant.ENTERPRISE_CHECK_OA_FLOW_LAST_INFO_URL;
        Map<String, String> map = new HashMap<>();
        map.put("businessKey", BUSINESS_KEY_PREFIX + templateId);
        map.put("instanceId",instanceId);
//        map.put("processDefinitionKey",PROCESS_DEFINITION_KEY);
        String post = "";
        try{
            post= HttpClientUtil.post(url,map);
            log.info(post);
        }catch(IOException e){
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }

        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }
        HisWorkFlowInfo workFlowInfo=JSONObject.parseObject(jsonObject.getString("data"),HisWorkFlowInfo.class);
        return workFlowInfo.getTaskName();
    }
}
