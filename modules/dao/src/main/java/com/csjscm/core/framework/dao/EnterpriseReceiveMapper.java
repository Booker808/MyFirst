package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseReceive;

import java.util.List;
import java.util.Map;

public interface EnterpriseReceiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseReceive record);

    int insertSelective(EnterpriseReceive record);

    EnterpriseReceive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseReceive record);

    int updateByPrimaryKey(EnterpriseReceive record);

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
}