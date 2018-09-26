package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.dao.EnterpriseProtocolMapper;
import com.csjscm.core.framework.service.enterprise.EnterpriseProtocolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 框架协议信息ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-26 08:40:38
 */
 
@Service
public class EnterpriseProtocolServiceImpl implements EnterpriseProtocolService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseProtocolServiceImpl.class);
   
    @Autowired
    private EnterpriseProtocolMapper enterpriseProtocolMapper;


	
}