package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseAttachment;

import java.util.List;

public interface EnterpriseAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseAttachment record);

    int insertSelective(EnterpriseAttachment record);

    EnterpriseAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseAttachment record);

    int updateByPrimaryKey(EnterpriseAttachment record);

    List<EnterpriseAttachment> selectByEpNumber(String entNumber);
}