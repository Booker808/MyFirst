package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseAttachment;

import java.util.List;
import java.util.Map;

public interface EnterpriseAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseAttachment record);

    int insertSelective(EnterpriseAttachment record);

    EnterpriseAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseAttachment record);

    int updateByPrimaryKey(EnterpriseAttachment record);

    List<EnterpriseAttachment> selectByEpNumber(String entNumber);

    /**
     * 按条件查询单个
     *
     */
    EnterpriseAttachment findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseAttachment> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);
}