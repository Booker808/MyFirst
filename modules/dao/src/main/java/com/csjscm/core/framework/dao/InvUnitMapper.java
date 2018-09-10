package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.InvUnit;
import java.util.List;
import java.util.Map;

/**
 * 计量单位定义表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-03 10:01:53
 */


public interface InvUnitMapper{

    int insert(InvUnit t);

    int insertSelective(InvUnit t);

    int updateSelective(InvUnit t);

    int update(InvUnit t);

    InvUnit findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     InvUnit findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<InvUnit> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);

    int deleteByPrimaryKey(Integer id);


}
