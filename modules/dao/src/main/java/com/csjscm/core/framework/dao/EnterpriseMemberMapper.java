package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.EnterpriseMember;
import java.util.List;
import java.util.Map;

/**
 * 企业-会员表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:01:53
 */


public interface EnterpriseMemberMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseMember t);

    int insertSelective(EnterpriseMember t);

    int updateSelective(EnterpriseMember t);

    int update(EnterpriseMember t);

    EnterpriseMember findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     EnterpriseMember findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<EnterpriseMember> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);

    /**
     * 查询最大编码
     * @return
     */
     String findMaxEntNumber();


}
