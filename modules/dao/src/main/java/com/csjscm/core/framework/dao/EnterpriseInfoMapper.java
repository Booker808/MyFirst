package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.EnterpriseInfoExample;
import com.csjscm.core.framework.model.EnterpriseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;
import java.util.Map;

public interface EnterpriseInfoMapper {
    int deleteByPrimaryKey(String entNumber);

    int insert(EnterpriseInfo record);

    int insertSelective(EnterpriseInfo record);

    EnterpriseInfo selectByPrimaryKey(String entNumber);

    List<EnterpriseInfo> selectByExample(EnterpriseInfoExample example);

    int updateByPrimaryKeySelective(EnterpriseInfo record);

    int updateByPrimaryKey(EnterpriseInfo record);

    String findMaxEntNumber();

    /**
     * 按条件查询单个
     *
     */
    EnterpriseInfo findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseInfo> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    /**
     * 根据企业名称精确搜索企业ID
     *
     * @param enterpriseName
     * @return
     */
    String selectEpNoByEpName(String enterpriseName);

    List<String> selectNameByFuzzyName(@Param("entName") String entName);
}