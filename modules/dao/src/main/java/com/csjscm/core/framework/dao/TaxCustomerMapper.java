package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.TaxCustomer;
import java.util.List;
import java.util.Map;

/**
 * 客户商品与税收关联记录参考表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-17 16:12:45
 */


public interface TaxCustomerMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(TaxCustomer t);

    int insertSelective(TaxCustomer t);

    int updateSelective(TaxCustomer t);

    int update(TaxCustomer t);

    TaxCustomer findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     TaxCustomer findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<TaxCustomer> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
