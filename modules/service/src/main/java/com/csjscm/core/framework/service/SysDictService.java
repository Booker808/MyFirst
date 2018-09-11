package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.SysDict;
import com.csjscm.core.framework.model.SysDictDetail;

import java.util.List;

/**
 * 数据字典表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:33:08
 */

public interface SysDictService {
    /**
     * 根据type_code查询 SysDict
     * @param code
     * @return
     */
    SysDict findByCode(String code);

    /**
     *根据 type_code 查询 字典详情 list
     * @param code
     * @return
     */
    List<SysDictDetail> findDetailListByCode(String code);


}
