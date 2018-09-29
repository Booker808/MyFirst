package com.csjscm.core.framework.service.enterprise.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CheckStateEnum;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.HttpClientUtil;
import com.csjscm.core.framework.dao.EnterpriseCategoryMapper;
import com.csjscm.core.framework.dao.EnterpriseFlowMapper;
import com.csjscm.core.framework.dao.EnterpriseInfoMapper;
import com.csjscm.core.framework.dao.EnterpriseProtocolMapper;
import com.csjscm.core.framework.model.EnterpriseCategory;
import com.csjscm.core.framework.model.EnterpriseFlow;
import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.model.EnterpriseProtocol;
import com.csjscm.core.framework.service.enterprise.EnterpriseFlowService;
import com.csjscm.core.framework.vo.EnterpriseFlowModel;
import com.csjscm.sweet.framework.auth.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 供应商审批流程记录ServiceImpl
 *
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-28 09:06:02
 */

@Service
public class EnterpriseFlowServiceImpl implements EnterpriseFlowService {

    private static final Logger logger = LoggerFactory.getLogger(EnterpriseFlowServiceImpl.class);

    @Autowired
    private EnterpriseFlowMapper enterpriseFlowMapper;
    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;
    @Autowired
    private EnterpriseCategoryMapper enterpriseCategoryMapper;
    @Autowired
    private EnterpriseProtocolMapper enterpriseProtocolMapper;


    @Override
    @Transactional
    public void saveFirstCheck(EnterpriseFlow enterpriseFlow) {
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        EnterpriseInfo enterpriseInfo = enterpriseInfoMapper.selectByPrimaryKey(enterpriseFlow.getEntNumber());
        if (!checkFirstState(Integer.parseInt(enterpriseInfo.getCheckState()))) {
            throw new BussinessException("该状态不能提交初始化审核接口");
        }
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) + Constant.ENTERPRISE_CHECK_OA_START_URL;
        Map<String, String> map = new HashMap<>();
        map.put("userId", sessionUser.getString("name"));
        map.put("businessKey", enterpriseInfo.getEntNumber());
        String post = "";
        try {
            post = HttpClientUtil.post(url, map);
            logger.info(post);
        } catch (IOException e) {
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }
        //更新状态
        enterpriseInfo.setCheckState(CheckStateEnum.待内部审核.getState().toString());
        //保存记录
        Map<String, Object> map1 = new HashMap<>();
        map1.put("entNumber", enterpriseInfo.getEntNumber());
        EnterpriseCategory enterpriseCategory = enterpriseCategoryMapper.findSelective(map1);
        if (enterpriseCategory.getSupplyBusiness().intValue() == 4) {
            enterpriseFlow.setFlowType(2);
        }
        enterpriseFlow.setCustom(3);
        enterpriseFlow.setFlowNode(CheckStateEnum.待内部审核.getState());
        enterpriseFlow.setInstanceId(jsonObject.getJSONObject("data").getString("processInstanceId"));
        enterpriseFlow.setUserName(sessionUser.getString("name"));
        enterpriseFlowMapper.insertSelective(enterpriseFlow);
        enterpriseInfoMapper.updateByPrimaryKeySelective(enterpriseInfo);
    }

    @Override
    @Transactional
    public void saveNormal(EnterpriseFlowModel enterpriseFlowModel) {
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        EnterpriseInfo enterpriseInfo = enterpriseInfoMapper.selectByPrimaryKey(enterpriseFlowModel.getEntNumber());
        if (!checkNormalState(Integer.parseInt(enterpriseInfo.getCheckState()))) {
            throw new BussinessException("该状态不能提交审核接口");
        }
        Map<String, Object> map2 = new HashMap<>();
        map2.put("entNumber", enterpriseFlowModel.getEntNumber());
        map2.put("custom", 3);
        EnterpriseFlow one = enterpriseFlowMapper.findOne(map2);

        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) + Constant.ENTERPRISE_CHECK_OA_COMPLETE_URL;
        Map<String, String> map = new HashMap<>();
        //是否通过
        if (enterpriseFlowModel.getCustom().intValue() == 1) {
            map.put("suggestionStatus", "true");
        } else {
            map.put("suggestionStatus", "false");
        }
        if (CheckStateEnum.待准入确认.getState().toString().equals(enterpriseInfo.getCheckState())) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("entNumber", enterpriseInfo.getEntNumber());
            EnterpriseCategory enterpriseCategory = enterpriseCategoryMapper.findSelective(map1);
            //是否特批
            if (enterpriseCategory.getSupplyBusiness().intValue() == 4) {
                map.put("TPFlag", "false");
            } else {
                map.put("TPFlag", "true");
            }
            EnterpriseProtocol selective = enterpriseProtocolMapper.findSelective(map1);
            if (selective.getType().intValue() == 1) {
                map.put("XYFlag", "true");
            } else {
                map.put("XYFlag", "false");
            }

        }
        map.put("userId", sessionUser.getString("name"));
        map.put("taskId", enterpriseFlowModel.getTaskId());
        String post = "";
        try {
            post = HttpClientUtil.post(url, map);
            logger.info(post);
        } catch (IOException e) {
            throw new BussinessException("提交请求oa接口异常");
        }
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }

        Integer oaCheckNode = findOACheckNode(one.getInstanceId());
        EnterpriseFlow enterpriseFlow = new EnterpriseFlow();
        BeanutilsCopy.copyProperties(enterpriseFlowModel, enterpriseFlow);
        enterpriseFlow.setFlowNode(oaCheckNode);
        enterpriseFlow.setUserName(sessionUser.getString("name"));
        enterpriseFlow.setFlowType(one.getFlowType());
        enterpriseFlowMapper.insertSelective(enterpriseFlow);
        if (enterpriseFlowModel.getCustom().intValue() == 1) {
            enterpriseInfo.setCheckState(oaCheckNode.toString());
        } else {
            if (enterpriseInfo.getCheckState().equals(CheckStateEnum.已驳回申请人.getState().toString())) {
                enterpriseInfo.setCheckState(CheckStateEnum.已作废.getState().toString());
            } else {
                enterpriseInfo.setCheckState(CheckStateEnum.已驳回申请人.getState().toString());
            }
        }
        if (enterpriseInfo.getCheckState().equals(CheckStateEnum.归档.getState().toString())) {
            enterpriseInfo.setIsvalid(1);
        }
        enterpriseInfoMapper.updateByPrimaryKeySelective(enterpriseInfo);
    }

    @Override
    public EnterpriseFlow findByPrimary(Integer id) {
        return enterpriseFlowMapper.findByPrimary(id);
    }

    @Override
    public EnterpriseFlow findSelective(Map<String, Object> map) {
        return enterpriseFlowMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseFlow> listSelective(Map<String, Object> map) {
        return enterpriseFlowMapper.listSelective(map);
    }


    public boolean checkFirstState(Integer state) {
        if (CheckStateEnum.保存.getState().intValue() == state.intValue() ||
                CheckStateEnum.待申请人提交.getState().intValue() == state.intValue()) {
            return true;
        }
        return false;
    }

    public boolean checkNormalState(Integer state) {
        if (CheckStateEnum.待内部审核.getState().intValue() == state.intValue() || CheckStateEnum.待执行总经理审核.getState().intValue() == state.intValue() ||
                CheckStateEnum.待准入确认.getState().intValue() == state.intValue() || CheckStateEnum.待打印盖章.getState().intValue() == state.intValue()
                || CheckStateEnum.待合同预警.getState().intValue() == state.intValue() || CheckStateEnum.已驳回申请人.getState().intValue() == state.intValue()
        ||CheckStateEnum.待准入审核.getState().intValue() == state.intValue()) {
            return true;
        }
        return false;
    }

    public Integer findOACheckNode(String instanceId) {
        String url = System.getProperty(Constant.RNTERPRISE_CHECK_OA_DOMAIN) + Constant.ENTERPRISE_CHECK_OA_FLOW_INFO_URL;
        Map<String, String> map = new HashMap<>();
        map.put("instanceId", instanceId);
        map.put("processDefinitionKey", Constant.ENTERPRISE_CHECK_OA_PROCESS);
        String post = "";
        try {
            post = HttpClientUtil.post(url, map);
            logger.info(post);
        } catch (IOException e) {
            throw new BussinessException("提交请求oa接口地址异常,地址：" + url);
        }
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (!jsonObject.getString("code").equals("200")) {
            throw new BussinessException("提交请求oa接口返回失败：" + jsonObject.getString("message"));
        }
        JSONArray data = jsonObject.getJSONArray("data");
        String activityId = data.getJSONObject(data.size() - 1).getString("activityId");
        Integer state = matchingCheckState(activityId);
        return state;
    }

    public Integer matchingCheckState(String state) {
        Integer checkState = 0;
        switch (state) {
            case "userTask1":
                checkState = CheckStateEnum.待内部审核.getState();
                break;
            case "userTask2":
                checkState = CheckStateEnum.待准入审核.getState();
                break;
            case "userTask3":
                checkState = CheckStateEnum.待执行总经理审核.getState();
                break;
            case "userTask4":
                checkState = CheckStateEnum.待准入确认.getState();
                break;
            case "userTask5":
                checkState = CheckStateEnum.已驳回申请人.getState();
                break;
            case "userTask8":
                checkState = CheckStateEnum.待打印盖章.getState();
                break;
            case "userTask10":
                checkState = CheckStateEnum.待合同预警.getState();
                break;
            case "endEvent1":
                checkState = CheckStateEnum.归档.getState();
                break;
            default:
                checkState = 0;
                break;
        }
        if (checkState.intValue() == 0) {
            throw new BussinessException("未匹配到流程节点");
        }
        return checkState;
    }

}