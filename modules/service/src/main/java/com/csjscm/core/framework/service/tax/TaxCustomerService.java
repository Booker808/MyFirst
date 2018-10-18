package com.csjscm.core.framework.service.tax;

import com.csjscm.core.framework.model.TaxCustomer;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 客户商品与税收关联记录参考表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-17 16:12:45
 */

public interface TaxCustomerService {
    QueryResult<TaxCustomer> findPage(int page, int rpp, Map<String,Object>map);

    Map<String,Object> importExcel(MultipartFile file);

    void save (TaxCustomer taxCustomer);

    void update (TaxCustomer taxCustomer);
}
