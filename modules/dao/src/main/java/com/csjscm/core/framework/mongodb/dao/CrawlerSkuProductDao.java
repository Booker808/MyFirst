package com.csjscm.core.framework.mongodb.dao;

import com.csjscm.core.framework.common.util.MongoCommonDao;
import com.csjscm.core.framework.mongodb.model.CrawlerSkuProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class CrawlerSkuProductDao extends MongoCommonDao<CrawlerSkuProduct> {

    @Override
    @Autowired
    @Qualifier("mongoTemplate")
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate=mongoTemplate;
    }
}
