package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.EnterpriseSettlementInfoMapper;
import com.csjscm.core.framework.model.EnterpriseSettlementInfo;
import com.csjscm.core.framework.service.EnterpriseSettlementInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 结算信息表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:29:03
 */
 
@Service
public class EnterpriseSettlementInfoServiceImpl implements EnterpriseSettlementInfoService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseSettlementInfoServiceImpl.class);
   
    @Autowired
    private EnterpriseSettlementInfoMapper enterpriseSettlementInfoMapper;


    @Override
    public EnterpriseSettlementInfo findSelective(Map<String, Object> map) {
        return enterpriseSettlementInfoMapper.findSelective(map);
    }
}