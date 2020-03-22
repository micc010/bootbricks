package com.github.rogerli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.DispatcherServlet;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

/**
 * @author roger.li
 */
//@Configuration
//@ComponentScan(basePackages = "com.github.rogerli")
//@EnableWebMvc
//@SpringBootApplication(scanBasePackages = "com.github.rogerli",
//        exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = "com.github.rogerli")
//@WebAppConfiguration
@EnableSwagger2
@EnableAspectJAutoProxy
public class ApiApplication {

    /**
     * jar包
     *
     * @param args
     */
    public static void main(String[] args) {
//        SpringApplication.run(ApplicationTest.class, args);
        new SpringApplicationBuilder(ApiApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    /**
     * @return
     */
    @Bean
    DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

//    /**
//     * 普通war包
//     * @param builder
//     * @return
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ApplicationTest.class);
//    }

    /**
     * @return
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    /**
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    /**
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

    /**
     * @return
     */
    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "0,0,0");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "45");
        properties.put("kaptcha.session.key", "validCode");
        properties.put("kaptcha.textproducer.font.color", "120,100,80");
        properties.put("kaptcha.textproducer.font.size", "35");
        properties.put("kaptcha.textproducer.char.length", "5");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,黑体");
        captchaProducer.setConfig(new Config(properties));
        return captchaProducer;
    }

    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }

//    /**
//     * 文件上传配置
//     *
//     * @return
//     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement(@Value("spring.servlet.multipart.max-file-size") Integer maxSize) {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 单个文件最大
//        factory.setMaxFileSize(DataSize.of(maxSize, DataUnit.MEGABYTES));
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize(DataSize.of(maxSize, DataUnit.MEGABYTES));
//        /// 设置总上传数据总大小
//        factory.setLocation();
//        return factory.createMultipartConfig();
//    }

}
