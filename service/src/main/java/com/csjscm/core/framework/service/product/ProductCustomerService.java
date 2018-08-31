package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

public interface ProductCustomerService {
    QueryResult<SkuCustomer> queryCustomerProduct(int page, int rpp, SkuCustomerExample example);
}
