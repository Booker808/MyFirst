package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.model.SkuPartnerEx;
import com.csjscm.core.framework.vo.*;
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
     * @param skuPartnerAddModel
     */
    void  save(SkuPartnerAddModel skuPartnerAddModel);
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
    Integer savePartner(SkuPartnerModel skuPartnerModel);
    /**
     * 获取详情
     */
    SkuPartnerDetailsModel getSkuPartnerModel(Map<String,Object> map);

    /**
     * 新增 来自 scm 的供应商商品
     * @param json
     * @return
     */
    ScmPartnerVo saveSCMSkuPartner(String json);

    List<SkuPartner> listSelectiveSCM(Map<String,Object> map);

    SkuPartnerEx queryPartnerProductDetail(Integer id);

    void updateSkuPartner(SkuPartner skuPartner);
}
