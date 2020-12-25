package com.github.edu.security.login.configuration;

import com.github.edu.security.login.filter.HttpParamsFilter;
import com.github.edu.security.login.impl.TSysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.http.HttpSessionEvent;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-24
 */
@Configuration
@EnableWebSecurity
//开启Security注解支持
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Slf4j
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${server.custom.security.matcher}")
    private String[] matcher;

    @Value("${server.custom.security.csrf}")
    private String[] csrf;

    @Value("${server.custom.cas.client.host}")
    private String host;

    @Value("${server.custom.cas.client.login}")
    private String login;

    @Value("${server.custom.cas.client.logout}")
    private String logout;

    @Value("${server.custom.cas.server.host}")
    private String serverHost;

    @Value("${server.custom.cas.server.login}")
    private String serverLogin;

    @Value("${server.custom.cas.server.logout}")
    private String serverLogout;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 获取用户身份
     *
     * @return
     */
    @Bean
    public TSysUserServiceImpl getUserService() {
        return new TSysUserServiceImpl();
    }

    /**
     * 认证方式
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.authenticationProvider(customUserAuthenticationManager());
         *//**设置不清除登录密码，此操作只限于开启金智ldap认证**//*
        auth.eraseCredentials(false);*/
        super.configure(auth);
        auth.authenticationProvider(casAuthenticationProvider());
    }

    /**
     * 设置认证拦截，指定登录页面
     *
     * @param http
     * @throws Exception
     */
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();
        //允许所有用户访问"/"和"/home",/static下面的默认不需要登录验证
        http.authorizeRequests()
                .antMatchers(matcher).permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                .failureUrl("/error")
                //办事大厅默认跳转/uc/user/index   //uc/portal/index滨职  海洋/uc/index 科艺
                .defaultSuccessUrl("/uc/index/index")//登录成功后默认跳转到"/index"无锡工艺微信默认跳转/uc/service/index
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/s/index").invalidateHttpSession(true)//退出登录后的默认url是"/login"
                .permitAll();
        //设置rest api不启用csrf拦截
        http.csrf().ignoringAntMatchers(csrf);
        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint())
                .and()
                .addFilter(casAuthenticationFilter())
                .addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web)  {
        // 设置不拦截规则
        web.ignoring().antMatchers(matcher);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    /**
     * 认证的入口
     */
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(serverHost + serverLogin);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /**
     * 指定service相关信息
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(host + login);
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }

    /**
     * CAS认证过滤器
     */
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setFilterProcessesUrl(login);
        casAuthenticationFilter.setContinueChainBeforeSuccessfulAuthentication(false);
        casAuthenticationFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/uc/index/index"));
        return casAuthenticationFilter;
    }

    /**
     * cas 认证 Provider
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setUserDetailsService(getUserService()); //这里只是接口类型，实现的接口不一样，都可以的。
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
        return casAuthenticationProvider;
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        Cas20ServiceTicketValidator ticketValidator = new Cas20ServiceTicketValidator(serverHost);
        return ticketValidator;
    }

    @Bean
    public HttpParamsFilter getHttpParamsFilter() {
        return new HttpParamsFilter();
    }

    @Bean
    public FilterRegistrationBean httpParamsFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(getHttpParamsFilter());
        filterRegistrationBean.setOrder(-999);
        filterRegistrationBean.addUrlPatterns("/uc/*");
        return filterRegistrationBean;
    }

    /**
     * 请求单点退出过滤器
     */
    @Bean
    public LogoutFilter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(serverHost + serverLogout + "?service=" + host, new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(logout);
        return logoutFilter;
    }

    /**
     * 单点登出过滤器
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(serverHost);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    //设置退出监听
    @EventListener
    public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(HttpSessionEvent event) {
        log.info("-------------------------------------退出！");
        return new SingleSignOutHttpSessionListener();
    }


}
