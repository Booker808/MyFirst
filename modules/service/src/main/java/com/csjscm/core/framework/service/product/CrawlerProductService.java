package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;

public interface CrawlerProductService {
      Page<CrawlerSpuProduct> findSpuPage();

      CrawlerSpuProduct findSpuById(String id);
}
