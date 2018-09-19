package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.dao.EnterpriseSettleMapper;
import com.csjscm.core.framework.model.EnterpriseSettle;
import com.csjscm.core.framework.service.enterprise.EnterpriseSettleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 结算信息ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 11:03:25
 */
 
@Service
public class EnterpriseSettleServiceImpl implements EnterpriseSettleService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseSettleServiceImpl.class);
   
    @Autowired
    private EnterpriseSettleMapper enterpriseSettleMapper;


    @Override
    public EnterpriseSettle findSelective(Map<String, Object> map) {
        return enterpriseSettleMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseSettle> listSelective(Map<String, Object> map) {
        return enterpriseSettleMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return enterpriseSettleMapper.findCount(map);
    }

    @Override
    public int save(EnterpriseSettle enterpriseSettle) {
        return enterpriseSettleMapper.insertSelective(enterpriseSettle);
    }

    @Override
    public int update(EnterpriseSettle enterpriseSettle) {
        return enterpriseSettleMapper.updateByPrimaryKeySelective(enterpriseSettle);
    }
}