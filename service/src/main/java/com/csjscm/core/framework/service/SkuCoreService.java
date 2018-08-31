package com.csjscm.core.framework.service;

import org.springframework.web.multipart.MultipartFile;

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
    void  importSkuCoreExcel(MultipartFile file)throws Exception;
}
