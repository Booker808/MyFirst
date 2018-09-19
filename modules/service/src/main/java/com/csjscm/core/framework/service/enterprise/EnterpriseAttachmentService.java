package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseAttachment;

import java.util.List;
import java.util.Map;

/**
 * 企业附件Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 09:58:12
 */

public interface EnterpriseAttachmentService {
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

    int save(EnterpriseAttachment enterpriseAttachment);

    int  update(EnterpriseAttachment enterpriseAttachment);

    int updateIsdelete(Integer id);

}
