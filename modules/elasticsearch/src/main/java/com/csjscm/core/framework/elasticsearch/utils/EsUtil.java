package com.csjscm.core.framework.elasticsearch.utils;

import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface EsUtil {
    /**
     * 创建Index
     *
     * @param clazz
     * @return
     */
    boolean createIndex(Class clazz);

    boolean deleteIndex(Class clazz);

    /**
     * 向Es引擎插入对象
     *
     * @param object
     * @return
     * @throws IOException
     */
    String insert(Object object) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * 更新Es引擎中的对象
     *
     * @param object
     * @return
     */
    String update(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException,BusinessException;

    /**
     * 删除Es引擎中的对象
     *
     * @param object
     * @return
     */
    String delete(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, BusinessException, IOException;

    /**
     * 根据ID搜索Es中的对象
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T selectById(String id,Class<T> clazz) throws IOException;

    <T> QueryResult<T> selectByCondition(Map<String,String> condition,Class<T> clazz) throws IOException;

    QueryResult<String> selectIdsByCondition(Map<String,String> condition,Class clazz) throws IOException;

    /**
     * 插入数据（不指定ID）
     *
     * @param index
     * @param type
     * @param object
     * @return
     * @throws IOException
     */
    @Deprecated
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
    @Deprecated
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
    @Deprecated
    String updateById(String index, String type, String id, Object object) throws IOException;

    /**
     * 根据id删除数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    @Deprecated
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
    <T> QueryResult<T> selectByCondition(String index, String type, Map<String,String> condition, Class<T> clazz) throws IOException;


    /**
     * 根据and条件查找ID集合
     *
     * @param index
     * @param type
     * @param condition
     * @return
     */
    QueryResult<String> selectIdsByCondition(String index, String type, Map<String,String> condition) throws IOException;


}
