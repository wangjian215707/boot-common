package com.github.edu.security.login.configuration;

import com.github.edu.security.login.security.CustomAccessDecisionManager;
import com.github.edu.security.login.security.CustomSecurityMetadataSource;
import com.github.edu.security.login.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * SecurityMetadataSource :Spring Security 权限资源管理
 * AccessDecisionManager ：Spring Security 权限决策管理
 * <p>
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-8-26
 */
@Configuration
@EnableWebSecurity
//开启Security注解支持
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${server.custom.security.matcher}")
    private String[] matcher;

    @Value("${server.custom.security.csrf}")
    private String[] csrf;

    @Value("${server.custom.security.encode}")
    private Integer encode;

    @Value("${server.custom.security.session}")
    private Integer session;
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    /**
     * 使用BCrypt的强散列哈希加密实现
     * 设置加密方式
     *
     * @return
     * @strength 密码强度，值越大密码强度越大
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(encode);
    }

    /**
     * 指定密码加密方式，及userDetailsService
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * SpringSecurity资源管理器
     * @return
     */
    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource(){

        return new CustomSecurityMetadataSource();
    }

    /**
     * 资源鉴权管理
     * @return
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    @Bean
    public SysUserServiceImpl userDetailsService(){

        return new SysUserServiceImpl();
    }
/*
    *//**
     * 用户认证管理
     *
     * @param auth
     * @throws Exception
     *//*
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());
    }*/
    /**
     * 认证方式
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();
        //允许所有用户访问"/"和"/home",/static下面的默认不需要登录验证
        http.authorizeRequests()
                .antMatchers(matcher).permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
               /* .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public   <O extends FilterSecurityInterceptor> O postProcess(O object){

                        return object;
                    }
                })*/
                .and()
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/index")//登录成功后默认跳转到"/index"
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login").invalidateHttpSession(true)//退出登录后的默认url是"/login"
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().access("@authServiceImpl.isAccess(request,authentication)")
                .and().sessionManagement().maximumSessions(session);


        //设置rest api不启用csrf拦截
        http.csrf().ignoringAntMatchers(csrf);
    }

}
