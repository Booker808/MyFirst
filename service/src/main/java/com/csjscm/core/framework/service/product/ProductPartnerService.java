package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

public interface ProductPartnerService {
    QueryResult<SkuPartner> queryPartnerProduct(int page, int rpp, SkuPartnerExample example);
}
