package com.csjscm.core.framework.configuation;


import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
@Configuration
public class ServletContextInitListener implements ServletContextListener {

    @Autowired
    private RedisServiceFacade redisServiceFacade;

    @Autowired
    private SkuCoreService skuCoreService;

    private static final String CATEGORY = "category_";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("==================MyServletContextListener Start=============");

        List<SkuCore> coreList = skuCoreService.selectByProductNoList();
        for (SkuCore skuCore : coreList) {
            String categoryNo = skuCore.getCategoryNo();
            String productNo = skuCore.getProductNo();
            String redisCategoryNo = redisServiceFacade.get(CATEGORY + categoryNo);
            if (StringUtils.isEmpty(productNo)) {
                redisServiceFacade.set(CATEGORY + categoryNo, 0);
            } else if (StringUtils.isEmpty(redisCategoryNo)) {
                redisServiceFacade.set(CATEGORY + categoryNo, Integer.parseInt(productNo.substring(3)));
            } else {
                int pId = Integer.parseInt(productNo.substring(3));
                int rID = Integer.parseInt(redisCategoryNo);
                redisServiceFacade.set(CATEGORY + categoryNo, pId > rID ? pId : rID);
            }
        }

        sce.getServletContext().log("==================商品编码—redis初始化成功=============");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("==================MyServletContextListener end=============");
    }

}
