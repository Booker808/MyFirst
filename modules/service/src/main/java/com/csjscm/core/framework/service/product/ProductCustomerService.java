package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuCustomerEx;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductCustomerService {
    QueryResult<SkuCustomerEx> queryCustomerProduct(int page, int rpp, SkuCustomerExample example);

    /**
     * 导入客户商品
     * @param file
     * @param customerNo
     * @return
     * @throws BussinessException
     */
    Map<String,Object> importCustomerExcel(MultipartFile file,String customerNo)throws BussinessException;

    void save(SkuCustomer skuCustomer);
}
