package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseAccount;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

import java.util.List;
import java.util.Map;

/**
 * 企业账户信息Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 10:14:35
 */

public interface EnterpriseAccountService {

    /**
     * 按条件查询单个
     *
     */
    EnterpriseAccount findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseAccount> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    int  save (EnterpriseAccount enterpriseAccount);

    int  update (EnterpriseAccount enterpriseAccount);

    EnterpriseAccount selectByPrimaryKey(Integer id);

    int updateIsdelete(Integer id);
}
