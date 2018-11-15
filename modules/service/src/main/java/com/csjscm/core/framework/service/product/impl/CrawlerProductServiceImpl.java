package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.dao.CrawlerSkuProductDao;
import com.csjscm.core.framework.mongodb.dao.CrawlerSpuProductDao;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;
import com.csjscm.core.framework.service.product.CrawlerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class CrawlerProductServiceImpl implements CrawlerProductService {
    @Autowired
    private CrawlerSkuProductDao crawlerSkuProductDao;
    @Autowired
    private CrawlerSpuProductDao crawlerSpuProductDao;
    @Override
    public Page<CrawlerSpuProduct> findSpuPage() {
        Query query=new Query();
        Page<CrawlerSpuProduct> page = crawlerSpuProductDao.findPage(null, null, query);
        return page;
    }

    @Override
    public CrawlerSpuProduct findSpuById(String id) {
        return crawlerSpuProductDao.findById(id);
    }
}
