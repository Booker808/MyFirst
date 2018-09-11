package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.SysDict;
import java.util.List;
import java.util.Map;

/**
 * 数据字典表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:02:45
 */


public interface SysDictMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SysDict t);

    int insertSelective(SysDict t);

    int updateSelective(SysDict t);

    int update(SysDict t);

    SysDict findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     SysDict findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<SysDict> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
