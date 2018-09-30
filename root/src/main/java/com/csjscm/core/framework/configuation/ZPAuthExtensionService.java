package com.csjscm.core.framework.configuation;

import com.csjscm.sweet.framework.cas.AbstractCasShiroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
@Slf4j
public class ZPAuthExtensionService extends AbstractCasShiroAuthService implements EnvironmentAware {

    private boolean mock = false;

    /**
     * 配置扩展访问白名单
     **/
    @Value("${whitelist.resource:}")
    private String[] whiteListResource;


    /**
     * 配置扩展登录后访问白名单
     **/
    @Value("${login.WhiteList.resource:}")
    private String[] loginWhiteListResource;


    @Override
    public void setEnvironment(Environment environment) {

        if (environment.acceptsProfiles("mock")) {
            this.mock = true;
            log.warn("当前应用运行[mock]模式, 模拟用户登陆");
        }
    }

    @Override
    public Set<String> getWhiteListResourcesOnLogin() {
        if (loginWhiteListResource != null && loginWhiteListResource.length > 0) {
            return new HashSet<>(Arrays.asList(loginWhiteListResource));
        }
        return new HashSet();
    }


    @Override
    public String getSuccessUrl() {
        return "/#/dashboard";
    }


    @Override
    /**
     * 配置项目白名单资源
     */
    public String[] getWhiteListResources() {

        Set<String> whiteList = new HashSet<>();
        String[] normalWhiteList = whiteList.toArray(new String[]{});

        if (whiteListResource.length == 0) {
            return normalWhiteList;
        }

        String[] resources = new String[normalWhiteList.length + whiteListResource.length];

        System.arraycopy(normalWhiteList, 0, resources, 0, normalWhiteList.length);
        System.arraycopy(whiteListResource, 0, resources, normalWhiteList.length, whiteListResource.length);

        return resources;
    }

}

