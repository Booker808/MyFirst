package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.EnterpriseProtocol;

import java.util.List;
import java.util.Map;

/**
 * 框架协议信息Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-26 08:40:38
 */


public interface EnterpriseProtocolMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseProtocol t);

    int insertSelective(EnterpriseProtocol t);

    int updateSelective(EnterpriseProtocol t);

    int update(EnterpriseProtocol t);

    EnterpriseProtocol findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     EnterpriseProtocol findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<EnterpriseProtocol> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


    String findMaxProtocolNumber();
}
