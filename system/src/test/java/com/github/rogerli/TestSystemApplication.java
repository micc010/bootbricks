package com.github.rogerli;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author roger.li
 */
//@Configuration
//@ComponentScan(basePackages = "com.github.rogerli")
//@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.github.rogerli")
//@WebAppConfiguration
public class TestSystemApplication {

    /**
     * jar包
     *
     * @param args
     */
    public static void main(String[] args) {
//        SpringApplication.run(ApplicationTest.class, args);
        new SpringApplicationBuilder(TestSystemApplication.class).bannerMode(Banner.Mode.OFF).run(args);
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

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

//    /**
//     * 文件上传配置
//     * @return
//     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 单个文件最大
//        factory.setMaxFileSize(DataSize.of(maxSize, DataUnit.MEGABYTES));
//        /// 设置总上传数据总大小
//        factory.setMaxFileSize(DataSize.of(maxSize, DataUnit.MEGABYTES));
//        return factory.createMultipartConfig();
//    }

}
