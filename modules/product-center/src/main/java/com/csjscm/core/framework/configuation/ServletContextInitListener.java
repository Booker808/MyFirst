package com.csjscm.core.framework.configuation;


import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SpSkuCore;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.service.spu.SpuService;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

import static com.csjscm.core.framework.common.constant.Constant.REDIS_KEY_PRODUCT_NO;
import static com.csjscm.core.framework.common.constant.Constant.REDIS_KEY_SP_PRODUCT_NO;

@WebListener
@Configuration
public class ServletContextInitListener implements ServletContextListener {

    @Autowired
    private RedisServiceFacade redisServiceFacade;

    @Autowired
    private SkuCoreService skuCoreService;

    @Autowired
    private SpuService spuService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("==================MyServletContextListener Start=============");
        //商品编码—redis初始化
        List<SkuCore> coreList = skuCoreService.selectByProductNoList();
        for (SkuCore skuCore : coreList) {
            String categoryNo = skuCore.getCategoryNo();
            String productNo = skuCore.getProductNo();
            String redisCategoryNo = redisServiceFacade.get(REDIS_KEY_PRODUCT_NO + categoryNo);
            if (StringUtils.isEmpty(productNo)) {
                redisServiceFacade.set(REDIS_KEY_PRODUCT_NO + categoryNo, 0);
            } else if (StringUtils.isEmpty(redisCategoryNo)) {
                redisServiceFacade.set(REDIS_KEY_PRODUCT_NO + categoryNo, Integer.parseInt(productNo.substring(3)));
            } else {
                int pId = Integer.parseInt(productNo.substring(3));
                int rID = Integer.parseInt(redisCategoryNo);
                redisServiceFacade.set(REDIS_KEY_PRODUCT_NO + categoryNo, pId > rID ? pId : rID);
            }
        }

        sce.getServletContext().log("==================商品编码—redis初始化成功=============");

        //商城商品编码—redis初始化
        List<SpSkuCore> spCoreList=spuService.selectByProductNoList();
        for(SpSkuCore spSkuCore:spCoreList){
            String categorySpNo = spSkuCore.getCategorySpNo();
            String productNo = spSkuCore.getProductNo();
            String redisCategorySpNo = redisServiceFacade.get(REDIS_KEY_SP_PRODUCT_NO + categorySpNo);
            if (StringUtils.isEmpty(productNo)) {
                redisServiceFacade.set(REDIS_KEY_SP_PRODUCT_NO + categorySpNo, 0);
            } else if (StringUtils.isEmpty(redisCategorySpNo)) {
                redisServiceFacade.set(REDIS_KEY_SP_PRODUCT_NO + categorySpNo, Integer.parseInt(productNo.substring(3)));
            } else {
                int pId = Integer.parseInt(productNo.substring(3));
                int rID = Integer.parseInt(redisCategorySpNo);
                redisServiceFacade.set(REDIS_KEY_SP_PRODUCT_NO + categorySpNo, pId > rID ? pId : rID);
            }
        }

        sce.getServletContext().log("==================商城商品编码—redis初始化成功=============");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("==================MyServletContextListener end=============");
    }

}
