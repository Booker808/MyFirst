package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.dao.EnterpriseProtocolMapper;
import com.csjscm.core.framework.model.EnterpriseProtocol;
import com.csjscm.core.framework.service.enterprise.EnterpriseProtocolService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EnterpriseProtocolServiceImpl implements EnterpriseProtocolService {
   
    @Autowired
    private EnterpriseProtocolMapper enterpriseProtocolMapper;


    @Override
    public int save(EnterpriseProtocol enterpriseProtocol) {
        return enterpriseProtocolMapper.insertSelective(enterpriseProtocol);
    }

    @Override
    public int update(EnterpriseProtocol enterpriseProtocol) {
        return enterpriseProtocolMapper.updateSelective(enterpriseProtocol);
    }
}