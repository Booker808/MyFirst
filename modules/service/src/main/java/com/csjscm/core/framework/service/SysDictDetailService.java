package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.SysDictDetail;

import java.util.List;
import java.util.Map;

/**
 * 字典表数据明细Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:33:38
 */

public interface SysDictDetailService {
    /**
     * 按照条件查询
     * @param map
     * @return
     */
    List<SysDictDetail> findListByMap(Map<String,Object> map);
}
