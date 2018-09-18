package com.csjscm.core.framework.elasticsearch.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EsUtil {
    String insert(String index,String type,T object) throws IOException;
    String insert(String index,String type,String id,T object) throws IOException;
    long updateById(String index,String type,String id,Map<String,Object> map) throws IOException;
    int deleteById(String index,String type,String id);
    T selectById(String index,String type,String id,Class T);
    List<T> selectByCondition(String index, String type, Map<String,Object> condition,Class T);
}
