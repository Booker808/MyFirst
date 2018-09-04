package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.SkuUom;

/**
 * Created by zjc on 2018/9/4.
 */

public interface SkuUomService {
    /**
     * 保存商品包装规格对象
     * @param skuUoms
     */
    void insertSelective(SkuUom skuUoms);
}
