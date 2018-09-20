package com.csjscm.core.framework.elasticsearch.utils;

import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.io.IOException;
import java.util.Map;

public interface EsUtil {
    /**
     * 插入数据（不指定ID）
     *
     * @param index
     * @param type
     * @param object
     * @return
     * @throws IOException
     */
    String insert(String index,String type,Object object) throws IOException;

    /**
     * 插入数据（指定ID）
     *
     * @param index
     * @param type
     * @param id
     * @param object
     * @return
     * @throws IOException
     */
    String insert(String index,String type,String id,Object object) throws IOException;

    /**
     * 根据ID更新数据
     *
     * @param index
     * @param type
     * @param id
     * @param object
     * @return
     * @throws IOException
     */
    String updateById(String index, String type, String id, Object object) throws IOException;

    /**
     * 根据id删除数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    String deleteById(String index, String type, String id) throws IOException;

    /**
     * 根据ID查找数据
     *
     * @param index
     * @param type
     * @param id
     * @param clazz
     * @return
     */
    <T> T selectById(String index, String type, String id, Class<T> clazz) throws IOException;

    /**
     * 根据and条件查找数据
     *
     * @param index
     * @param type
     * @param condition
     * @param clazz
     * @return
     */
    <T> QueryResult<T> selectByAndCondition(String index, String type, Map<String,String> condition, Class<T> clazz) throws IOException;


    /**
     * 根据and条件查找ID集合
     *
     * @param index
     * @param type
     * @param condition
     * @return
     */
     QueryResult<String> selectIdsByAndCondition(String index, String type, Map<String,String> condition) throws IOException;


}
