package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.EnterpriseTicketInfo;
import java.util.List;
import java.util.Map;

/**
 * 开票信息表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:03:11
 */


public interface EnterpriseTicketInfoMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseTicketInfo t);

    int insertSelective(EnterpriseTicketInfo t);

    int updateSelective(EnterpriseTicketInfo t);

    int update(EnterpriseTicketInfo t);

    EnterpriseTicketInfo findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     EnterpriseTicketInfo findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<EnterpriseTicketInfo> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
