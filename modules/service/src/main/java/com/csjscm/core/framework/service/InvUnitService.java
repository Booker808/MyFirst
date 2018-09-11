package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.InvUnit;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 计量单位定义表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-03 10:01:53
 */

public interface InvUnitService {
    /**
     * 按条件查询list
     * @param map
     * @return
     */
    List<InvUnit> findListByMap(Map<String, Object> map);

    void  save(InvUnit invUnit);

    void  update(InvUnit invUnit);

    void delete(Integer id);

    void reloadRedisInvUnit();

    void  updateIsvalid(Integer id,Integer isvalid);

    /**
     * 分页查询
     * @param map
     * @return
     */
    QueryResult<InvUnit> findPage(Map<String,Object> map, int current, int pageSize);

    InvUnit findByPrimary(Integer id);

}
