package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.InvUnit;

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

}
