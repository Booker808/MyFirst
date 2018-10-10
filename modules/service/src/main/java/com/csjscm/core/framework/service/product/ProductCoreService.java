package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCoreEx;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.Map;

public interface ProductCoreService {
    QueryResult<SkuCoreEx> queryCoreProduct(int page, int rpp, SkuCoreExample example);

    QueryResult<SkuCore> productPage(int page, int rpp, Map<String,Object> map);


}
