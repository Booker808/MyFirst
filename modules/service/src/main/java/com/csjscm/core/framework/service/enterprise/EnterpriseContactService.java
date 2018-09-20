package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseContact;

import java.util.List;
import java.util.Map;

/**
 * 联系人信息Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 09:24:25
 */

public interface EnterpriseContactService {
    /**
     * 按条件查询单个
     *
     */
    EnterpriseContact findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseContact> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    int  save(EnterpriseContact enterpriseContact);

    int update(EnterpriseContact enterpriseContact);

    int updateIsdelete(Integer id);

}
