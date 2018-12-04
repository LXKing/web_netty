package com.aisafer.webgis.shiro;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:Bill
 * @Description:FreeMarker 集成shiro
 * @Date:2018/5/28 16:56
 * @version:1.0
 **/
@Component
public class ShiroFreeMarkerConfig implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
