package com.csjscm.core.framework.elasticsearch.model;

import com.csjscm.core.framework.elasticsearch.annotation.ElasticsearchIndex;
import com.csjscm.core.framework.elasticsearch.annotation.ElasticsearchIndexField;
import com.csjscm.core.framework.elasticsearch.annotation.ElasticsearchIndexId;
import com.csjscm.core.framework.elasticsearch.constant.IndexFiledTypeEnum;
import lombok.Data;

@ElasticsearchIndex(indexName = "spu",typeName = "spu")
@Data
public class EsSpuModel {
    @ElasticsearchIndexId
    private String stdProductNo;
    @ElasticsearchIndexField(indexType = IndexFiledTypeEnum.TEXT)
    private String productName;
    @ElasticsearchIndexField(indexType = IndexFiledTypeEnum.TEXT)
    private String brandName;
    @ElasticsearchIndexField
    private String ruleSize;
    @ElasticsearchIndexField
    private String minUnit;
}
