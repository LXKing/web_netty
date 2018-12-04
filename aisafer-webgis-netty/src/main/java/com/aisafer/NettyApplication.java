package com.aisafer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * springboot启动类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-22 15:02:46
 * @Modified By:
 */
@ImportResource(value = {"classpath:/config/*.xml"})
//@ComponentScan(basePackages={"com.aisafer.webgis"})
@PropertySource("classpath:/config/config.properties")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class NettyApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(NettyApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
