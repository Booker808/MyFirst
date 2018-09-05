package com.csjscm.core.framework.service;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.SkuCore;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 商品核心表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 15:43:10
 */

public interface SkuCoreService {
    /**
     * 导入商品
     * @param file
     * @throws Exception
     */
    Map<String,Object> importSkuCoreExcel(MultipartFile file)throws BussinessException;

    /**
     * 保存导入的商品信息
     * @param jsonData
     */
    void saveImportSkuCore(String jsonData);

    /**
     * 查询商品最大分类
     * @return
     */
    List<SkuCore> selectByProductNoList();


    /**
     * 创建商品对象
     * @param map
     */
    boolean insertSelective(Map<String, Object> map);
}
