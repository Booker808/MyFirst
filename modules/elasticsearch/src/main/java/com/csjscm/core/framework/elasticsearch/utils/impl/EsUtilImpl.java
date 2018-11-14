package com.csjscm.core.framework.elasticsearch.utils.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.elasticsearch.annotation.ElasticsearchIndex;
import com.csjscm.core.framework.elasticsearch.annotation.ElasticsearchIndexField;
import com.csjscm.core.framework.elasticsearch.annotation.ElasticsearchIndexId;
import com.csjscm.core.framework.elasticsearch.utils.EsUtil;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
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
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.csjscm.core.framework.elasticsearch.constant.IndexFiledTypeEnum.KEYWORD;
import static com.csjscm.core.framework.elasticsearch.constant.IndexFiledTypeEnum.TEXT;

@Component
@Slf4j
public class EsUtilImpl implements EsUtil {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean createIndex(Class clazz) {
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return false;
        }
        String indexName=getIndexName(clazz);
        String typeName=getIndexType(clazz);
        CreateIndexRequest createIndexRequest=new CreateIndexRequest(indexName);
        Map<String,Object> jsonMap=Maps.newHashMap();
        Map<String,Object> propertiesMap=Maps.newHashMap();
        for(Field field:clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(ElasticsearchIndexField.class)){
                Map<String,Object> indexField=Maps.newHashMap();
                String fieldName= getIndexField(field);
                indexField.put("type", field.getAnnotation(ElasticsearchIndexField.class).indexType().toString().toLowerCase());
                propertiesMap.put(fieldName,indexField);
                if(field.getAnnotation(ElasticsearchIndexField.class).indexType().equals(TEXT)){
                    indexField.put("analyzer", "ik_max_word");
                }
            }
        }
        Map<String,Object> map=Maps.newHashMap();
        map.put("properties",propertiesMap);
        jsonMap.put(typeName,map);
        createIndexRequest.mapping(indexName,jsonMap);
        boolean acknowledged=false;
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            acknowledged = createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            log.error("创建搜索引擎Index"+indexName+"请求失败:"+e.getMessage(),e);
        }
        return acknowledged;
    }

    @Override
    public boolean existsIndex(Class clazz) {
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return false;
        }
        GetIndexRequest request = new GetIndexRequest();
        String indexName=getIndexName(clazz);
        request.indices(indexName);
        boolean result=false;
        try{
            result=client.indices().exists(request, RequestOptions.DEFAULT);
        }catch (IOException e){
            log.error("查询搜索引擎Index"+indexName+"请求失败:"+e.getMessage(),e);
        }
        return result;
    }

    @Override
    public boolean deleteIndex(Class clazz) {
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return false;
        }
        if(!existsIndex(clazz)){
            return true;
        }
        String indexName=getIndexName(clazz);
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        boolean acknowledged=false;
        try{
            DeleteIndexResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            acknowledged=deleteIndexResponse.isAcknowledged();
        } catch (IOException e) {
            log.error("删除搜索引擎Index"+indexName+"请求失败:"+e.getMessage(),e);
        }
        return acknowledged;
    }

    @Override
    public String insert(Object object) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(!object.getClass().isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        Map<String,Object> map=getIndexObject(object);
        IndexRequest indexRequest=new IndexRequest(getIndexName(object.getClass()),getIndexType(object.getClass()));
        String id=getId(object);
        if(StringUtils.isNotEmpty(id)){
            indexRequest.id(id);
        }
        indexRequest.source(map);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public <T> String insertList(List<T> list,Class<T> clazz) throws IOException {
        if(list==null || list.isEmpty()){
            return null;
        }
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        BulkRequest request=new BulkRequest();
        for(Object data:list){
            try{
                IndexRequest indexRequest=new IndexRequest(getIndexName(clazz),getIndexType(clazz));
                Map<String,Object> map=getIndexObject(data);
                String id=getId(data);
                if(StringUtils.isNotEmpty(id)){
                    indexRequest.id(id);
                }
                indexRequest.source(map);
                request.add(indexRequest);
            }catch (Exception e){
                log.error("Something wrong:"+ JSON.toJSONString(data),e);
            }
        }
        BulkResponse responses=client.bulk(request,RequestOptions.DEFAULT);
        if(responses.hasFailures()){
            return responses.buildFailureMessage();
        }
        return null;
    }

    @Override
    public String update(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException,BusinessException {
        if(!object.getClass().isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        Map<String,Object> map=getIndexObject(object);
        String id=getId(object);
        if(StringUtils.isEmpty(id)){
            throw new BusinessException("更新操作ID不允许为空");
        }
        UpdateRequest request=new UpdateRequest(getIndexName(object.getClass()),getIndexType(object.getClass()),id).doc(map);
        UpdateResponse response=client.update(request,RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public String delete(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, BusinessException, IOException {
        if(!object.getClass().isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        String id=getId(object);
        if(StringUtils.isEmpty(id)){
            throw new BusinessException("更新操作ID不允许为空");
        }
        DeleteRequest request=new DeleteRequest(getIndexName(object.getClass()),getIndexType(object.getClass()),id);
        DeleteResponse response=client.delete(request,RequestOptions.DEFAULT);
        return response.getResult().getLowercase();
    }

    @Override
    public <T> T selectById(String id, Class<T> clazz) throws IOException {
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        GetRequest request=new GetRequest(getIndexName(clazz),getIndexType(clazz),id);
        GetResponse response=client.get(request,RequestOptions.DEFAULT);
        T object= JSONObject.parseObject(response.getSourceAsString(),clazz);
        setId(object,response.getId());
        return object;
    }

    @Override
    public <T> QueryResult<T> selectByCondition(Map<String, String> condition, Class<T> clazz) throws IOException {
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        SearchHits hits=searchHits(getIndexName(clazz), getIndexType(clazz), condition);
        long totalHits=hits.getTotalHits();
        List<T> list= Lists.newLinkedList();
        for (SearchHit hit:hits.getHits()){
            T object=JSONObject.parseObject(hit.getSourceAsString(),clazz);
            setId(object,hit.getId());
            list.add(object);
        }
        QueryResult<T> result=new QueryResult<>();
        result.setItems(list);
        result.setTotal(totalHits);
        return result;
    }

    @Override
    public QueryResult<String> selectIdsByCondition(Map<String, String> condition, Class clazz) throws IOException {
        if(!clazz.isAnnotationPresent(ElasticsearchIndex.class)){
            return null;
        }
        SearchHits hits=searchHits(getIndexName(clazz), getIndexType(clazz), condition);
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
        T object= JSONObject.parseObject(response.getSourceAsString(),clazz);
        setId(object,response.getId());
        return object;
    }

    @Override
    public <T> QueryResult<T> selectByCondition(String index, String type, Map<String, String> condition, Class<T> clazz) throws IOException {
        SearchHits hits=searchHits(index, type, condition);
        long totalHits=hits.getTotalHits();
        List<T> list= Lists.newLinkedList();
        for (SearchHit hit:hits.getHits()){
            T object=JSONObject.parseObject(hit.getSourceAsString(),clazz);
            setId(object,hit.getId());
            list.add(object);
        }
        QueryResult<T> result=new QueryResult<>();
        result.setItems(list);
        result.setTotal(totalHits);
        return result;
    }

    @Override
    public QueryResult<String> selectIdsByCondition(String index, String type, Map<String, String> condition) throws IOException {
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
                        boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(),entry.getValue()));
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

    /**
     * 根据注解获取搜索引擎中IndexName
     *
     * @param clazz
     * @return
     */
    private String getIndexName(Class clazz){
        String indexName=((ElasticsearchIndex)clazz.getAnnotation(ElasticsearchIndex.class)).indexName();
        return StringUtils.isEmpty(indexName)?clazz.getSimpleName().toLowerCase():indexName;
    }

    /**
     * 根据注解获取搜索引擎中IndexType
     *
     * @param clazz
     * @return
     */
    private String getIndexType(Class clazz){
        String typeName=((ElasticsearchIndex)clazz.getAnnotation(ElasticsearchIndex.class)).typeName();
        return StringUtils.isEmpty(typeName)?clazz.getSimpleName().toLowerCase():typeName;
    }

    /**
     * 根据注解获取字段名
     *
     * @param field
     * @return
     */
    private String getIndexField(Field field){
        String fieldName= field.getAnnotation(ElasticsearchIndexField.class).fieldName();
        return StringUtils.isEmpty(fieldName)?field.getName():fieldName;
    }

    /**
     * 获取对象中的ID
     *
     * @param object
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private String getId(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(Field field:object.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(ElasticsearchIndexId.class)){
                char[] name;
                name=field.getName().toCharArray();
                name[0]-=32;
                Object value=object.getClass().getDeclaredMethod("get"+String.valueOf(name)).invoke(object);
                return value==null?null:value.toString();
            }
        }
        return null;
    }

    private void setId(Object object,String id){
        for(Field field:object.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(ElasticsearchIndexId.class)){
                char[] name;
                name=field.getName().toCharArray();
                name[0]-=32;
                try{
                    if(field.getType().equals(String.class)){
                        object.getClass().getDeclaredMethod("set"+String.valueOf(name),field.getType()).invoke(object,id);
                    }else if(field.getType().equals(Integer.class)){
                        object.getClass().getDeclaredMethod("set"+String.valueOf(name),field.getType()).invoke(object,Integer.parseInt(id));
                    }
                }catch (Exception e){
                    log.error("set id fail:"+id,e);
                }
                return;
//                Object value=object.getClass().getDeclaredMethod("set"+String.valueOf(name)).invoke(object);
//                return value==null?null:value.toString();
            }
        }
    }

    /**
     * 获取对象的map
     *
     * @param object
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Map<String,Object> getIndexObject(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String,Object> map=Maps.newHashMap();
        char[] name;
        Object value;
        for(Field field:object.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(ElasticsearchIndexField.class)){
                String fieldName= getIndexField(field);
                name=field.getName().toCharArray();
                name[0]-=32;
                value=object.getClass().getDeclaredMethod("get"+String.valueOf(name)).invoke(object);
                map.put(fieldName,value);
            }
        }
        return map;
    }
}
