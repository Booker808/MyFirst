package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.model.SkuPartnerEx;
import com.csjscm.core.framework.vo.SkuPartnerDetailsModel;

import java.util.List;
import java.util.Map;

public interface SkuPartnerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuPartner record);

    int insertSelective(SkuPartner record);

    SkuPartner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuPartner record);

    int updateByPrimaryKey(SkuPartner record);

    List<SkuPartner> selectByExample(SkuPartnerExample example);

    List<SkuPartnerEx> selectExByExample(SkuPartnerExample example);
    /**
     * 按条件查询单个
     *
     */
    SkuPartner findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<SkuPartner> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    /**
     * 获取详情
     */
    SkuPartnerDetailsModel getSkuPartnerModel(Map<String,Object> map);

    List<SkuPartner> listSelectiveSCM(Map<String,Object> map);
}
