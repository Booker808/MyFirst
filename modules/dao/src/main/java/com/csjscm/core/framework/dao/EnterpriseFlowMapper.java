package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.EnterpriseFlow;
import java.util.List;
import java.util.Map;

/**
 * 供应商审批流程记录Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-28 09:06:02
 */


public interface EnterpriseFlowMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseFlow t);

    int insertSelective(EnterpriseFlow t);

    int updateSelective(EnterpriseFlow t);

    int update(EnterpriseFlow t);

    EnterpriseFlow findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     EnterpriseFlow findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<EnterpriseFlow> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);

     EnterpriseFlow findOne(Map<String, Object> map);


}
