package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.dao.SkuCustomerMapper;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomerEx;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCustomerServiceImpl implements ProductCustomerService {
    @Autowired
    private SkuCustomerMapper skuCustomerMapper;

    @Override
    public QueryResult<SkuCustomerEx> queryCustomerProduct(int page, int rpp, SkuCustomerExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuCustomerEx> skuCoreList=skuCustomerMapper.selectExByExample(example);
        PageInfo<SkuCustomerEx> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<SkuCustomerEx> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }
}
