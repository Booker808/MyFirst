package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCoreEx;
import com.csjscm.core.framework.service.product.ProductCoreService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductCoreServiceImpl implements ProductCoreService {
    @Autowired
    private SkuCoreMapper skuCoreMapper;

    @Override
    public QueryResult<SkuCoreEx> queryCoreProduct(int page, int rpp, SkuCoreExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuCoreEx> skuCoreList=skuCoreMapper.selectExByExample(example);
        PageInfo<SkuCoreEx> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<SkuCoreEx> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    @Override
    public QueryResult<SkuCore> productPage(int page, int rpp, Map<String, Object> map) {
        PageHelper.startPage(page,rpp);
        List<SkuCore> skuCores = skuCoreMapper.listSelective(map);
        PageInfo<SkuCore> pageInfo=new PageInfo<>(skuCores);
        QueryResult<SkuCore> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }
}
