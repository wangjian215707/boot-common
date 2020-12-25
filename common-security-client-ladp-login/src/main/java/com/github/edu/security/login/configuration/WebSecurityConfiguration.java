package com.github.edu.security.login.configuration;

import com.github.edu.security.login.component.CustomUserAuthenticationManager;
import com.github.edu.security.login.component.MyAuthenticationSuccessHandler;
import com.github.edu.security.login.component.MySimpleUrlAuthenticationFailureHandler;
import com.github.edu.security.login.filter.UserLoginFilter;
import com.github.edu.security.login.impl.TSysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
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

    @Value("${server.custom.verification-code}")
    public boolean verificationCode;

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
     * 设置认证拦截，指定登录页面
     *
     * @param http
     * @throws Exception
     */
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        //允许所有用户访问"/"和"/home",/static下面的默认不需要登录验证
        http.authorizeRequests()
                .antMatchers(matcher).permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and().addFilterBefore(userLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/uc/user/index")//登录成功后默认跳转到"/index"
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login").
                invalidateHttpSession(true).
                permitAll();//退出登录后的默认url是"/login";
        http.csrf().ignoringAntMatchers(csrf);
    }

    /**
     * 自定义
     * 登录拦截
     *
     * @return
     * @throws Exception
     */
    @Bean
    public UserLoginFilter userLoginFilter() throws Exception {
        UserLoginFilter filer = new UserLoginFilter();
        filer.setAuthenticationManager(authenticationManager());//设置验证
        filer.setAuthenticationFailureHandler(getMySimpleUrlAuthenticationFailureHandler());
        filer.setAuthenticationSuccessHandler(getMyAuthenticationSuccessHandler());
        return filer;
    }

    /**
     * 登录错误处理方法
     *
     * @return
     */
    @Bean
    public MySimpleUrlAuthenticationFailureHandler getMySimpleUrlAuthenticationFailureHandler() {
        return new MySimpleUrlAuthenticationFailureHandler("/login?error=true");
    }

    /**
     * 认证方式
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(customUserAuthenticationManager());
        /**设置不清除登录密码，此操作只限于开启金智ldap认证**/
        auth.eraseCredentials(false);

    }

    /**
     * 自定义登录验证
     * @return
     */
    @Bean
    public CustomUserAuthenticationManager customUserAuthenticationManager() {
        return new CustomUserAuthenticationManager();
    }

    /**
     * 登录成功处理方法
     *
     * @return
     */
    @Bean
    public MyAuthenticationSuccessHandler getMyAuthenticationSuccessHandler() {
        MyAuthenticationSuccessHandler myAuthenticationSuccessHandler = new MyAuthenticationSuccessHandler();
        return myAuthenticationSuccessHandler;
    }

    @Override
    public void configure(WebSecurity web)  {
        // 设置不拦截规则
        web.ignoring().antMatchers("/static/**");
    }

}
