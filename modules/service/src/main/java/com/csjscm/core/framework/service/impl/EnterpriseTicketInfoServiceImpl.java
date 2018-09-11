package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.EnterpriseTicketInfoMapper;
import com.csjscm.core.framework.model.EnterpriseTicketInfo;
import com.csjscm.core.framework.service.EnterpriseTicketInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 开票信息ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:29:40
 */
 
@Service
public class EnterpriseTicketInfoServiceImpl implements EnterpriseTicketInfoService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseTicketInfoServiceImpl.class);
   
    @Autowired
    private EnterpriseTicketInfoMapper enterpriseTicketInfoMapper;


    @Override
    public EnterpriseTicketInfo findSelective(Map<String, Object> map) {
        return enterpriseTicketInfoMapper.findSelective(map);
    }
}