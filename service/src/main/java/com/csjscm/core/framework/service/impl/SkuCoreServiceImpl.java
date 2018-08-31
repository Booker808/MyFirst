package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.service.SkuCoreService;
import org.apache.poi.ss.usermodel.Row;
import org.bouncycastle.eac.EACIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 商品核心表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 15:43:10
 */
 
@Service
public class SkuCoreServiceImpl implements SkuCoreService {
	
    private static final Logger logger = LoggerFactory.getLogger(SkuCoreServiceImpl.class);
   
    @Autowired
    private SkuCoreMapper skuCoreMapper;
    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;

    @Override
    public void importSkuCoreExcel(MultipartFile file) throws Exception {

    }
}