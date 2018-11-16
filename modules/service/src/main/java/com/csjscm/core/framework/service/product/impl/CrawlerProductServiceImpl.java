package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.dao.CrawlerSkuProductDao;
import com.csjscm.core.framework.mongodb.dao.CrawlerSpuProductDao;
import com.csjscm.core.framework.mongodb.model.CrawlerSkuProduct;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;
import com.csjscm.core.framework.service.product.CrawlerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CrawlerProductServiceImpl implements CrawlerProductService {
    @Autowired
    private CrawlerSkuProductDao crawlerSkuProductDao;
    @Autowired
    private CrawlerSpuProductDao crawlerSpuProductDao;


    @Override
    public Page<CrawlerSpuProduct> findSpuPage(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<CrawlerSpuProduct> page = crawlerSpuProductDao.findPage(pageNum, pageSize, map);
        return page;
    }

    @Override
    public CrawlerSpuProduct findSpuById(String id) {
        return crawlerSpuProductDao.findById(id);
    }

    @Override
    public void updateSpu(CrawlerSpuProduct crawlerSpuProduct) {
        crawlerSpuProductDao.updateById(crawlerSpuProduct);
    }

    @Override
    public List<CrawlerSkuProduct> findSkuList(Map<String, Object> map) {
        return crawlerSkuProductDao.find(map);
    }

    @Override
    public CrawlerSkuProduct findSkuById(String id) {
        return crawlerSkuProductDao.findById(id);
    }

    @Override
    public void updateSku(CrawlerSkuProduct crawlerSpuProduct) {
        crawlerSkuProductDao.updateById(crawlerSpuProduct);
    }
}
