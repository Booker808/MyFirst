package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.EnterpriseSettlementInfo;

import java.util.List;
import java.util.Map;

/**
 * 结算信息表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:00:44
 */


public interface EnterpriseSettlementInfoMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseSettlementInfo t);

    int insertSelective(EnterpriseSettlementInfo t);

    int updateSelective(EnterpriseSettlementInfo t);

    int update(EnterpriseSettlementInfo t);

    EnterpriseSettlementInfo findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     EnterpriseSettlementInfo findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<EnterpriseSettlementInfo> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
