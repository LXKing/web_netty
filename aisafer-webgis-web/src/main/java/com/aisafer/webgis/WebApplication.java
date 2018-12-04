package com.aisafer.webgis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * springboot启动类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-22 15:02:46
 * @Modified By:
 */
@ImportResource(value = {"classpath:/config/*.xml"})
@ComponentScan(basePackages={"com.aisafer.sso", "com.aisafer.webgis"})
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class WebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
