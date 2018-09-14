package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.SkuPartnerUomMapper;
import com.csjscm.core.framework.service.SkuPartnerUomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 供应商商品包装规格ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-14 10:26:29
 */
 
@Service
public class SkuPartnerUomServiceImpl implements SkuPartnerUomService {
	
    private static final Logger logger = LoggerFactory.getLogger(SkuPartnerUomServiceImpl.class);
   
    @Autowired
    private SkuPartnerUomMapper skuPartnerUomMapper;


	
}