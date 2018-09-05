package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.SkuUpcMapper;
import com.csjscm.core.framework.model.SkuUpc;
import com.csjscm.core.framework.service.SkuUpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zjc on 2018/9/4.
 */
@Service
public class SkuUpcServiceImpl implements SkuUpcService {

    @Autowired
    private SkuUpcMapper skuUpcMapper;

    @Override
    public void insertSelective(SkuUpc skuUpcs) {
        skuUpcMapper.insertSelective(skuUpcs);
    }
}
