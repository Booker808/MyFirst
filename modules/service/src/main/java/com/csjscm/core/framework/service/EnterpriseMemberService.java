package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.EnterpriseMember;
import com.csjscm.core.framework.vo.EnterpriseMemberModel;
import com.csjscm.core.framework.vo.EnterpriseUpdateModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.Map;

/**
 * 企业-会员表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:28:14
 */

public interface EnterpriseMemberService {
    /**
     * 保存 企业-会员 相关信息
     * @param enterpriseMemberModel
     * @return
     */
    int saveEnterpriseMember(EnterpriseMemberModel enterpriseMemberModel);

    /**
     * 删除 企业会员
     * @param ids
     * @return
     */
    int deleteEnterpriseMember(String ids);

    /**
     * 分页查询
     * @param map
     * @return
     */
    QueryResult<EnterpriseMember> findPage(Map<String,Object> map, int current, int pageSize);

    EnterpriseMember findByPrimary(Integer id);

    /**
     * 更新 企业-会员 相关信息
     * @param enterpriseMemberModel
     * @return
     */
    int updateEnterpriseMember(EnterpriseMemberModel enterpriseMemberModel);
    /**
     * 更新 企业-会员 model信息
     * @param enterpriseUpdateModel
     * @return
     */
    int updateEnterpriseModel(EnterpriseUpdateModel enterpriseUpdateModel);

    /**
     * 校验供应商企业、客户企业是否存在的接口  true 表示存在
     * @param name  企业名称
     * @param type  type=1 校验供应
     * 商 type=2 校验客
     * 户企业
     * @return
     */
    boolean checkPartnerName(String name,Integer type);

}
