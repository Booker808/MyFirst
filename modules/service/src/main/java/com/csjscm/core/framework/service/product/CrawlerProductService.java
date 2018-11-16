package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.model.CrawlerSkuProduct;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;

import java.util.List;
import java.util.Map;

public interface CrawlerProductService {
      Page<CrawlerSpuProduct> findSpuPage(Integer pageNum, Integer pageSize, Map<String,Object> map);

      CrawlerSpuProduct findSpuById(String id);

      void updateSpu(CrawlerSpuProduct crawlerSpuProduct);

      List<CrawlerSkuProduct> findSkuList(Map<String,Object> map);

      CrawlerSkuProduct findSkuById(String id);

      void updateSku(CrawlerSkuProduct crawlerSpuProduct);
}
