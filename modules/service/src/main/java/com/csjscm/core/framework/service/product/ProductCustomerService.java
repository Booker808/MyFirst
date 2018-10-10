package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuCustomerEx;
import com.csjscm.core.framework.vo.SkuCustomerPageVo;
import com.csjscm.core.framework.vo.SkuCustomerSCMMolde;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductCustomerService {
    QueryResult<SkuCustomerEx> queryCustomerProduct(int page, int rpp, SkuCustomerExample example);

    QueryResult<SkuCustomerPageVo> findPage(int page, int rpp, Map<String,Object> map);

    /**
     * 导入客户商品
     * @param file
     * @param customerNo
     * @return
     * @throws BussinessException
     */
    Map<String,Object> importCustomerExcel(MultipartFile file,String customerNo)throws BussinessException;

    void save(SkuCustomer skuCustomer);
    /**
     * 按条件查询list
     *
     */
    List<SkuCustomer> listSelective(Map<String,Object> map);

    SkuCustomer saveSCMSkuCustomer(SkuCustomerSCMMolde skuCustomerSCMMolde);

    List<SkuCustomer> listSelectiveSCM(Map<String,Object> map);
}
