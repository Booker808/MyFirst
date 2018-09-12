package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.model.SkuPartnerEx;
import com.csjscm.core.framework.vo.SkuPartnerModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductPartnerService {
    QueryResult<SkuPartnerEx> queryPartnerProduct(int page, int rpp, SkuPartnerExample example);
    /**
     * 导入供应商商品
     * @param file
     * @param supplyNo
     * @return
     * @throws BussinessException
     */
    Map<String,Object> importPartnerExcel(MultipartFile file, String supplyNo)throws BussinessException;

    /**
     * 保存
     * @param skuPartner
     */
    void  save(SkuPartner skuPartner);
    /**
     * 按条件查询list
     *
     */
    List<SkuPartner> listSelective(Map<String,Object> map);

    /**
     * 对 对外接口 新增
     * @param skuPartnerModel
     * @return
     */
    int savePartner(SkuPartnerModel skuPartnerModel);
}
