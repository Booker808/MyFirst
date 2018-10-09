package com.csjscm.core.framework.service.template.impl;

import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.dao.EnterpriseStandardTemplateMapper;
import com.csjscm.core.framework.model.EnterpriseStandardTemplate;
import com.csjscm.core.framework.service.template.EnterpriseTemplateService;
import com.csjscm.sweet.framework.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
@Slf4j
public class EnterpriseTemplateServiceImpl implements EnterpriseTemplateService{
    @Autowired
    private EnterpriseStandardTemplateMapper standardTemplateMapper;

    @Override
    public void addStandardTemplate(EnterpriseStandardTemplate template) {
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        if(sessionUser!=null){
            template.setCreateUser(sessionUser.getString("name"));
            template.setEditUser(sessionUser.getString("name"));
        }
        standardTemplateMapper.insertSelective(template);
    }

    @Override
    public void updateStandardTemplate(EnterpriseStandardTemplate template) {
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        if(sessionUser!=null){
            template.setEditUser(sessionUser.getString("name"));
        }
        standardTemplateMapper.updateByPrimaryKeySelective(template);
    }

    @Override
    public List<EnterpriseStandardTemplate> queryStandardTemplate() {
        return standardTemplateMapper.selectAllList();
    }

    @Override
    public EnterpriseStandardTemplate queryStandardTemplateById(Integer id) {
        return standardTemplateMapper.selectByPrimaryKey(id);
    }
}
