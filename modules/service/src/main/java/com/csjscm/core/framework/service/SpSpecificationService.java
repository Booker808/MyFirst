package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.model.SpSpecification;
import com.csjscm.core.framework.model.SpSpecificationOptions;
import com.csjscm.core.framework.vo.SpCategoryJsonModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.cloud.context.named.NamedContextFactory;

import java.util.List;
import java.util.Map;

public interface SpSpecificationService {
    int save(SpSpecification t);


    int update(SpSpecification t);

    /**
     * 根据三级分类id，增加销售属性
     *
     * @param categoryId
     * @return
     */
    void insertSellSpecByLv3Id(SpSpecification spec, List<String> options);

    /**
     * 根据三级分类id，增加扩展属性
     *
     * @param categoryId
     * @return
     */
    void insertExtSpecByLv3Id(SpSpecification spec, List<String> options);

    /**
     * 根据分类id查询所有销售属性
     *
     * @param categoryId
     * @return
     */
    List<SpSpecification> listSelectiveBySell(Integer categoryId);

    /**
     * 根据分类id查询所有扩展属性
     *
     * @param categoryId
     * @return
     */
    List<SpSpecification> listSelectiveByExt(Integer categoryId);

    /**
     * 根据id查找分类属性
     *
     * @param id
     * @return
     */
    SpSpecification findByPrimary(Integer specId);

    /**
     * 按条件查询单个
     */
    SpSpecification findSelective(Map<String, Object> map);

    /**
     * 按条件查询list
     */
    List<SpSpecification> listSelective(Map<String, Object> map);

    /**
     * 按条件查询条数
     */
    int findCount(Map<String, Object> map);

    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    QueryResult<SpSpecification> findPage(Map<String, Object> map, int current, int pageSize);

    /**
     * 按id删除 多个id 以逗号隔开
     *
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 更新状态
     *
     * @param ids
     * @param state
     */
    void updateState(List<Integer> ids, Integer state);

    /**
     * 编辑udf
     *
     * @param
     */
    void updateUdf(SpSpecification t);
}
