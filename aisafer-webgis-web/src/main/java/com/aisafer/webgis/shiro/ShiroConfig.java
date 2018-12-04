package com.aisafer.webgis.shiro;

import com.aisafer.sso.config.AbstractShiroConfiguration;
import io.buji.pac4j.realm.Pac4jRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.pac4j.core.matching.PathMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Configuration
public class ShiroConfig extends AbstractShiroConfiguration {


    @Resource(name = "redisTemplate")
    protected RedisTemplate<String, Object> redisTemplate;


    @Override
    public RedisTemplate<String, Object> redisTemplate() {
        return redisTemplate;
    }
    

	 @Bean(name = "pac4jRealm")
	 public Pac4jRealm pac4jRealm() {
			return new ShiroRealm();
	 }

    /**
	 *
	 *
	 * 过滤请求拦截
	 **/
	@Override
	public void loadFilterConfig(DefaultShiroFilterChainDefinition definition) {
		//主页
		definition.addPathDefinition("/authc/main", "authc,perms[webgis:sys:main]");
		//大屏
		definition.addPathDefinition("/authc/index", "authc,perms[webgis:sys:index]");
	}
	
	/**
	 * 过滤页面
	 **/
	@Override
	public void loadPathMatcher(PathMatcher pathMatcher) {
		pathMatcher.excludePath("/res/*");
		pathMatcher.excludePath("/login");
		pathMatcher.excludePath("/logout");
		pathMatcher.excludePath("/error");
	}

}
