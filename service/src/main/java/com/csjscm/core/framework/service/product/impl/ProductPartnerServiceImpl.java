package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.dao.SkuPartnerMapper;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPartnerServiceImpl implements ProductPartnerService {
    @Autowired
    private SkuPartnerMapper skuPartnerMapper;

    @Override
    public QueryResult<SkuPartner> queryPartnerProduct(int page, int rpp, SkuPartnerExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuPartner> skuPartner=skuPartnerMapper.selectByExample(example);
        PageInfo<SkuPartner> pageInfo=new PageInfo<>(skuPartner);
        QueryResult<SkuPartner> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }
}
