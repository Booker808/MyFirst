package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.dao.EnterpriseContractMapper;
import com.csjscm.core.framework.model.EnterpriseContract;
import com.csjscm.core.framework.service.enterprise.EnterpriseContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 合同模板ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 11:25:02
 */
 
@Service
public class EnterpriseContractServiceImpl implements EnterpriseContractService {

    private static final Logger logger = LoggerFactory.getLogger(EnterpriseContractServiceImpl.class);
   
    @Autowired
    private EnterpriseContractMapper enterpriseContractMapper;


    @Override
    public EnterpriseContract findSelective(Map<String, Object> map) {
        return enterpriseContractMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseContract> listSelective(Map<String, Object> map) {
        return enterpriseContractMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return enterpriseContractMapper.findCount(map);
    }

    @Override
    public int save(EnterpriseContract enterpriseContract) {
        return enterpriseContractMapper.insertSelective(enterpriseContract);
    }

    @Override
    public int update(EnterpriseContract enterpriseContract) {
        return enterpriseContractMapper.updateByPrimaryKeySelective(enterpriseContract);
    }
}