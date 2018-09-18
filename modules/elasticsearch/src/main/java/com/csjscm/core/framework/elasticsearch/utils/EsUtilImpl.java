package com.csjscm.core.framework.elasticsearch.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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
    public String insert(String index, String type, T object) throws IOException {
        IndexRequest indexRequest=new IndexRequest(index,type);
        ObjectMapper mapper=new ObjectMapper();
        indexRequest.source(mapper.writeValueAsString(index), XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getId();
    }

    @Override
    public String insert(String index, String type, String id, T object) throws IOException {
        IndexRequest indexRequest=new IndexRequest(index,type,id);
        ObjectMapper mapper=new ObjectMapper();
        indexRequest.source(mapper.writeValueAsString(index), XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getId();
    }

    @Override
    public long updateById(String index, String type, String id, Map<String,Object> map) throws IOException {
        UpdateRequest request=new UpdateRequest(index,type,index).doc(map);
        UpdateResponse response=client.update(request,RequestOptions.DEFAULT);
        return response.getVersion();
    }

    @Override
    public int deleteById(String index, String type, String id) {
        return 0;
    }

    @Override
    public T selectById(String index, String type, String id, Class T) {
        return null;
    }

    @Override
    public List<T> selectByCondition(String index, String type, Map<String, Object> condition, Class T) {
        return null;
    }
}
