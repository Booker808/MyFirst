package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

public interface ProductCoreService {
    QueryResult<SkuCore> queryCoreProduct(int page, int rpp, SkuCoreExample example);
}
