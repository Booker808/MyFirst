package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.SysDictDetail;
import java.util.List;
import java.util.Map;

/**
 * 字典表数据明细Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:57:31
 */


public interface SysDictDetailMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SysDictDetail t);

    int insertSelective(SysDictDetail t);

    int updateSelective(SysDictDetail t);

    int update(SysDictDetail t);

    SysDictDetail findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     SysDictDetail findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<SysDictDetail> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
