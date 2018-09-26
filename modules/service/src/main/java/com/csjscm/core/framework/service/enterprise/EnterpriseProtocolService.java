package com.csjscm.core.framework.service.enterprise;


import com.csjscm.core.framework.model.EnterpriseProtocol;

/**
 * 框架协议信息Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-26 08:40:38
 */

public interface EnterpriseProtocolService {
    int save(EnterpriseProtocol enterpriseProtocol);

    int update(EnterpriseProtocol enterpriseProtocol);
}
