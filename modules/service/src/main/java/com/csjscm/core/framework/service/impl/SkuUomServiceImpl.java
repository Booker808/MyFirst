package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.SkuUomMapper;
import com.csjscm.core.framework.model.SkuUom;
import com.csjscm.core.framework.service.SkuUomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zjc on 2018/9/4.
 */
@Service
public class SkuUomServiceImpl implements SkuUomService {

    @Autowired
    private SkuUomMapper skuUomMapper;

    @Override
    public void insertSelective(SkuUom skuUoms) {
        skuUomMapper.insertSelective(skuUoms);
    }
}
