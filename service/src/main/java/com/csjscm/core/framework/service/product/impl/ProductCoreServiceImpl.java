package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.service.product.ProductCoreService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCoreServiceImpl implements ProductCoreService {
    @Autowired
    private SkuCoreMapper skuCoreMapper;

    @Override
    public QueryResult<SkuCore> queryCoreProduct(int page, int rpp, SkuCoreExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuCore> skuCoreList=skuCoreMapper.selectByExample(example);
        PageInfo<SkuCore> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<SkuCore> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }
}
