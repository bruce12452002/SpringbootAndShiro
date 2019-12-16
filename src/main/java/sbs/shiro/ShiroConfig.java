package sbs.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Resource
    private MyRealm myRealm;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/loginLogout/login", "anon"); // https://shiro.apache.org/web.html#Web-defaultfilters
//        filterMap.put("/loginLogout/bbb", "authc, perms[firstFile:*]");
        filterMap.put("/loginLogout/bbb2", "authc, perms[firstFile:read]");
        filterMap.put("/loginLogout/bbb3", "authc, perms[firstFile]");
        filterMap.put("/loginLogout/**", "authc");

        var shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/error.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultSecurityManager() {
        var defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(myRealm);
        return defaultSecurityManager;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        var proxy = new DefaultAdvisorAutoProxyCreator();
        proxy.setProxyTargetClass(true);
        return proxy;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        var authorizationAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAdvisor.setSecurityManager(securityManager);
        return authorizationAdvisor;
    }

}
