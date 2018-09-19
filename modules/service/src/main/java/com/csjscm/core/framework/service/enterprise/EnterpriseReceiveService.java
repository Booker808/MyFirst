package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseReceive;

import java.util.List;
import java.util.Map;

/**
 * 收发信息Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 10:29:29
 */

public interface EnterpriseReceiveService {

    /**
     * 按条件查询单个
     *
     */
    EnterpriseReceive findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseReceive> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    int save(EnterpriseReceive enterpriseReceive);

    int update(EnterpriseReceive enterpriseReceive);

    int updateIsdelete(Integer id);

}
