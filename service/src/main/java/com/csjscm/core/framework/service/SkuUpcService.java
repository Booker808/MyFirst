package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.SkuUpc;

/**
 * Created by zjc on 2018/9/4.
 */

public interface SkuUpcService {
    /**
     * 保存商品识别码
     * @param skuUpcs
     */
    void insertSelective(SkuUpc skuUpcs);
}
