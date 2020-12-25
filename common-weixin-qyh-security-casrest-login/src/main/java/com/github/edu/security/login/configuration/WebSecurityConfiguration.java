package com.github.edu.security.login.configuration;

import com.github.edu.security.login.filter.UserLoginFilter;
import com.github.edu.security.login.service.impl.UserServiceImpl;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-24
 */
@Configuration
@EnableWebSecurity
//开启Security注解支持
@EnableGlobalMethodSecurity(jsr250Enabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${server.custom.security.matcher}")
    private String[] matcher;

    @Value("${server.custom.security.csrf}")
    private String[] csrf;


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
    public UserServiceImpl getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    public CustomUserAuthenticationManager getCustomUserAuthenticationManager(){
        return new CustomUserAuthenticationManager();
    }

    @Bean
    public UserLoginFilter userLoginFilter() throws Exception {
        UserLoginFilter filer = new UserLoginFilter();
        filer.setAuthenticationManager(authenticationManager());
        return filer;
    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    /**
     * 认证方式
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getCustomUserAuthenticationManager());
        auth.eraseCredentials(false);
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
                .and().addFilterBefore(userLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                .failureUrl("/error")
                .defaultSuccessUrl("/uc/index")//登录成功后默认跳转到"/index"
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/loginout")
                .logoutSuccessUrl("/login").invalidateHttpSession(true)//退出登录后的默认url是"/login"
                .permitAll();
        //设置rest api不启用csrf拦截
        http.csrf().ignoringAntMatchers(csrf);
    }

    @Override
    public void configure(WebSecurity web) {
        // 设置不拦截规则
        web.ignoring().antMatchers(matcher);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

}
