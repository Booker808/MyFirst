package com.csjscm.core.framework.service;

import com.csjscm.core.framework.common.util.BussinessException;
import org.springframework.web.multipart.MultipartFile;

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
     * @param data
     */
    void saveImportSkuCore(String data);

}
