package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.dao.EnterpriseCategoryMapper;
import com.csjscm.core.framework.model.EnterpriseCategory;
import com.csjscm.core.framework.service.enterprise.EnterpriseCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 供应商分类ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 11:16:27
 */
 
@Service
public class EnterpriseCategoryServiceImpl implements EnterpriseCategoryService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseCategoryServiceImpl.class);
   
    @Autowired
    private EnterpriseCategoryMapper enterpriseCategoryMapper;


    @Override
    public EnterpriseCategory findSelective(Map<String, Object> map) {
        return enterpriseCategoryMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseCategory> listSelective(Map<String, Object> map) {
        return enterpriseCategoryMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return enterpriseCategoryMapper.findCount(map);
    }

    @Override
    public int save(EnterpriseCategory enterpriseCategory) {
        return enterpriseCategoryMapper.insertSelective(enterpriseCategory);
    }

    @Override
    public int update(EnterpriseCategory enterpriseCategory) {
        return enterpriseCategoryMapper.updateByPrimaryKeySelective(enterpriseCategory);
    }
}