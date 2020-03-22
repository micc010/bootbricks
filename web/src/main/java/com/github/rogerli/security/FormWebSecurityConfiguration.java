package com.github.rogerli.security;

import com.github.rogerli.security.auth.BoastAccessDecisionManager;
import com.github.rogerli.security.auth.BoastFilterSecurityMetadataSource;
import com.github.rogerli.security.auth.BoastUserDetailService;
import com.github.rogerli.security.auth.BoastUsernamePasswordAuthenticationFilter;
import com.github.rogerli.security.auth.ajax.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthenticationManager 管理认证 AuthenticationProvider 改变认证方式 UserDetailsService 改变认证的用户信息来源 ProviderManager
 * 清除返回的Authentication中的凭证信息，如密码 AccessDecisionManager
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FormWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormWebSecurityConfiguration.class);

    @Autowired
    private BoastUserDetailService userDetailService;

    @Autowired
    private BoastFilterSecurityMetadataSource securityMetadataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Web层面的配置，一般用来配置无需安全检查的路径
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        LOGGER.debug("======WebSecurity configure======");
        web.ignoring().antMatchers("/**/*.json", "/**/*.js", "/js/**", "/css/**", "/images/**", "/fonts/**", "/theme/**", "/html/**", "/json/**", "/lib/**", "/widgets/**");
    }

    /**
     * Request层面的配置，对应XML Configuration中的<http>元素
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.debug("======HttpSecurity configure======");

        http.csrf().disable(); // 关闭csrf

        http.authorizeRequests()
                .mvcMatchers("/login").permitAll();// 一般请求

        // 设置头
        http.headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .addHeaderWriter(new XContentTypeOptionsHeaderWriter())
                .addHeaderWriter(new XXssProtectionHeaderWriter())
                .addHeaderWriter(new CacheControlHeadersWriter())
                .addHeaderWriter(new HstsHeaderWriter());

        // 一般请求
//        http.formLogin().loginPage("/login.html").permitAll().failureUrl("/login?error").defaultSuccessUrl("")
        http.formLogin().loginPage("/login.html").permitAll()
                .successHandler(ajaxAndRedirectAuthenticationSuccessHandler())
                .failureHandler(ajaxAndRedirectAuthenticationFailureHandler())
                .loginProcessingUrl("/login")
                .and()
                .logout()
                .logoutSuccessHandler(ajaxAndRedirectLogoutSuccessHandler()).permitAll()
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                //如果异常是 AuthenticationException，使用 AuthenticationEntryPoint 处理
                //如果异常是 AccessDeniedException 且用户是匿名用户，使用 AuthenticationEntryPoint 处理
                .authenticationEntryPoint(ajaxAndRedirectAuthenticationEntryPoint())
                //如果异常是 AccessDeniedException 且用户不是匿名用户，如果否则交给 AccessDeniedHandler 处理
                .accessDeniedHandler(ajaxAndRedirectAccessDeniedHandler())
                .and()
                .authorizeRequests().expressionHandler(webSecurityExpressionHandler())
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
                        fsi.setSecurityMetadataSource(securityMetadataSource);
                        fsi.setAccessDecisionManager(accessDecisionManager());
                        fsi.setAuthenticationManager(authenticationManagerBean());
                        return fsi;
                    }
                })
                .and()
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .invalidSessionStrategy(ajaxAndRedirectInvalidSessionStrategy()).maximumSessions(1); // session管理
    }

    /**
     * @return
     * @throws Exception
     */
    @Bean
    public BoastUsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
        BoastUsernamePasswordAuthenticationFilter filter = new BoastUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(ajaxAndRedirectAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(ajaxAndRedirectAuthenticationFailureHandler());
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * @return
     */
    @Bean
    public AjaxAndRedirectAuthenticationSuccessHandler ajaxAndRedirectAuthenticationSuccessHandler() {
        return new AjaxAndRedirectAuthenticationSuccessHandler("/index.html");
    }

    /**
     * @return
     */
    @Bean
    public AjaxAndRedirectAuthenticationFailureHandler ajaxAndRedirectAuthenticationFailureHandler() {
        return new AjaxAndRedirectAuthenticationFailureHandler("/login.html");
    }

    /**
     * @return
     */
    @Bean
    public AjaxAndRedirectLogoutSuccessHandler ajaxAndRedirectLogoutSuccessHandler() {
        return new AjaxAndRedirectLogoutSuccessHandler("/login.html");
    }

    /**
     * @return
     */
    @Bean
    public AjaxAndRedirectInvalidSessionStrategy ajaxAndRedirectInvalidSessionStrategy() {
        return new AjaxAndRedirectInvalidSessionStrategy("/login.html");
    }

    /**
     * @return
     */
    @Bean
    public AjaxAndRedirectAccessDeniedHandler ajaxAndRedirectAccessDeniedHandler() {
        return new AjaxAndRedirectAccessDeniedHandler("/login.html");
    }

    /**
     * @return
     */
    @Bean
    public AjaxAndRedirectAuthenticationEntryPoint ajaxAndRedirectAuthenticationEntryPoint() {
        return new AjaxAndRedirectAuthenticationEntryPoint("/login.html");
    }

    /**
     * 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 注入bean
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
        auth.eraseCredentials(true).userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    /**
     * 登录事件
     *
     * @return
     */
    @Bean
    public org.springframework.security.authentication.event.LoggerListener authenticationLoggerListener() {
        return new org.springframework.security.authentication.event.LoggerListener();
    }

    /**
     * 通过事件
     *
     * @return
     */
    @Bean
    public org.springframework.security.access.event.LoggerListener accessEventLoggerListener() {
        return new org.springframework.security.access.event.LoggerListener();
    }

    /**
     * 访问决策器
     *
     * @return
     */
    @Bean(name = "accessDecisionManager")
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(webExpressionVoter());// 启用表达式投票器
        BoastAccessDecisionManager accessDecisionManager = new BoastAccessDecisionManager(decisionVoters);
        return accessDecisionManager;
    }


    /**
     * 认证管理器
     *
     * @return
     */
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() {
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = super.authenticationManagerBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationManager;
    }

    /**
     * 表达式控制器
     */
    @Bean(name = "expressionHandler")
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
//        webSecurityExpressionHandler.setDefaultRolePrefix("");
        return webSecurityExpressionHandler;
    }

    /**
     * 表达式投票器
     */
    @Bean(name = "expressionVoter")
    public WebExpressionVoter webExpressionVoter() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return webExpressionVoter;
    }

}