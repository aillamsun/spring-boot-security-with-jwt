package com.chinawiserv.admin;

import com.chinawiserv.core.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Locale;

/**
 * Created by sungang on 2017/8/19.
 */
@SpringBootApplication
@PropertySources({
        //默认配置
        @PropertySource("classpath:system.properties"),
//        tomcat目录下
//        @PropertySource(value = "file:${CATALINA_BASE}/system.properties", ignoreResourceNotFound = true),
//        @PropertySource(value = "file:${CATALINA_BASE}/wechat.properties", ignoreResourceNotFound = true),

        //${user.dir} 运行在项目同目录下
//        @PropertySource(value = "file:${user.dir}/system.properties", ignoreResourceNotFound = true),
//        @PropertySource(value = "file:${user.dir}/wechat.properties", ignoreResourceNotFound = true)
})
@Slf4j
//@EnableAspectJAutoProxy
@MapperScan("com.chinawiserv.admin.mapper")
@ComponentScan("com.chinawiserv")
//开启 缓存
@EnableCaching
public class CarAdminApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CarAdminApplication.class);
    }

    /**
     * spring boot 服务主入口
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CarAdminApplication.class, args);
        if (context instanceof EmbeddedWebApplicationContext) {
            int port = ((EmbeddedWebApplicationContext) context).getEmbeddedServletContainer().getPort();
            String contextPath = context.getApplicationName();
            String url = String.format(Locale.SIMPLIFIED_CHINESE, "http://localhost:%d%s", port, contextPath);

//            log.info(" =========== 加载阿里支付数据 ===========  ");
//            Configs.init("pay-alibaba.properties");
//            log.info(" =========== 加载阿里支付数据 成功!===========  ");

            //提示项目用到的相关配置文件
            log.info(" =========== ${user.dir}={} ===========  ", System.getProperty("user.dir"));
            log.info(" =========== ${java.io.tmpdir}={} ===========  ", System.getProperty("java.io.tmpdir"));

            String dashes = "------------------------------------------------------------------------";
            log.info("Access URLs:\n{}\n\tLocal: \t\t{}\n{}", dashes, url, dashes);

        }
    }


    @Bean
    public SpringUtils springUtilsBean() {
        return new SpringUtils();
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}