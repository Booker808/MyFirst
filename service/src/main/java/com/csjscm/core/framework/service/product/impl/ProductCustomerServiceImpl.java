package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.dao.SkuCustomerMapper;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
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
    public QueryResult<SkuCustomer> queryCustomerProduct(int page, int rpp, SkuCustomerExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuCustomer> skuCoreList=skuCustomerMapper.selectByExample(example);
        PageInfo<SkuCustomer> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<SkuCustomer> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }
}
