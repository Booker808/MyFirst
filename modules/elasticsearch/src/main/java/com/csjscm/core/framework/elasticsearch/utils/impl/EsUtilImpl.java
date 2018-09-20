package com.csjscm.core.framework.elasticsearch.utils.impl;

import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.elasticsearch.utils.EsUtil;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EsUtilImpl implements EsUtil {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public String insert(String index, String type, Object object) throws IOException {
        IndexRequest indexRequest=new IndexRequest(index,type);
        ObjectMapper mapper=new ObjectMapper();
        indexRequest.source(mapper.writeValueAsString(object), XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public String insert(String index, String type, String id, Object object) throws IOException {
        IndexRequest indexRequest=new IndexRequest(index,type,id);
        ObjectMapper mapper=new ObjectMapper();
        indexRequest.source(mapper.writeValueAsString(object), XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public String updateById(String index, String type, String id, Object object) throws IOException {
        UpdateRequest request=new UpdateRequest(index,type,id).doc(object);
        UpdateResponse response=client.update(request,RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public String deleteById(String index, String type, String id) throws IOException {
        DeleteRequest request=new DeleteRequest(index, type, id);
        DeleteResponse response=client.delete(request,RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public <T> T selectById(String index, String type, String id, Class<T> clazz) throws IOException {
        GetRequest request=new GetRequest(index, type, id);
        GetResponse response=client.get(request,RequestOptions.DEFAULT);
        return JSONObject.parseObject(response.getSourceAsString(),clazz);
    }

    @Override
    public <T> QueryResult<T> selectByAndCondition(String index, String type, Map<String, String> condition, Class<T> clazz) throws IOException {
        SearchHits hits=searchHits(index, type, condition);
        long totalHits=hits.getTotalHits();
        List<T> list= Lists.newLinkedList();
        for (SearchHit hit:hits.getHits()){
            list.add(JSONObject.parseObject(hit.getSourceAsString(),clazz));
        }
        QueryResult<T> result=new QueryResult<>();
        result.setItems(list);
        result.setTotal(totalHits);
        return result;
    }

    @Override
    public QueryResult<String> selectIdsByAndCondition(String index, String type, Map<String, String> condition) throws IOException {
        SearchHits hits=searchHits(index, type, condition);
        long totalHits=hits.getTotalHits();
        List<String> list= Lists.newLinkedList();
        for(SearchHit hit:hits){
            list.add(hit.getId());
        }
        QueryResult<String> result=new QueryResult<>();
        result.setItems(list);
        result.setTotal(totalHits);
        return result;
    }

    private SearchHits searchHits(String index, String type, Map<String, String> condition) throws IOException {
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        Integer page=null;
        Integer size=null;
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery();
        boolean isSearchAll=true;
        for(Map.Entry<String,String> entry:condition.entrySet()){
            if(StringUtils.isEmpty(entry.getValue())){
                continue;
            }
            switch (entry.getKey()){
                case "page":
                    page=Integer.parseInt(entry.getValue());
                    continue;
                case "size":
                    size=Integer.parseInt(entry.getValue());
                    continue;
                default:
                    if(StringUtils.isNotEmpty(entry.getKey())){
                        isSearchAll=false;
                        boolQueryBuilder.must(QueryBuilders.termQuery(entry.getKey(),entry.getValue()));
                    }
            }
        }
        if(isSearchAll){
            sourceBuilder.query(QueryBuilders.matchAllQuery());
        }else{
            sourceBuilder.query(boolQueryBuilder);
        }
        if(page!=null&&size!=null){
            sourceBuilder.from((page-1)*size);
            sourceBuilder.size(size);
        }
        SearchRequest request=new SearchRequest(index).types(type).source(sourceBuilder);
        SearchResponse response=client.search(request,RequestOptions.DEFAULT);
        return response.getHits();
    }
}
