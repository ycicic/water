package com.ycicic.framework.config;

import com.ycicic.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ycicic.framework.security.handle.AuthenticationEntryPointImpl;
import com.ycicic.framework.security.handle.LogoutSuccessHandlerImpl;
import com.ycicic.framework.security.provider.SysPasswordLoginProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * 认证配置
 *
 * @author ycicic
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CorsFilter corsFilter;

    private AuthenticationEntryPointImpl unauthorizedHandler;

    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    private SysPasswordLoginProvider sysUserPasswordLoginProvider;

    @Autowired
    public void setUnauthorizedHandler(AuthenticationEntryPointImpl unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Autowired
    public void setLogoutSuccessHandler(LogoutSuccessHandlerImpl logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Autowired
    public void setAuthenticationTokenFilter(JwtAuthenticationTokenFilter authenticationTokenFilter) {
        this.authenticationTokenFilter = authenticationTokenFilter;
    }

    @Autowired
    public void setSysUserPasswordLoginProvider(SysPasswordLoginProvider sysUserPasswordLoginProvider) {
        this.sysUserPasswordLoginProvider = sysUserPasswordLoginProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers("/system/login", "/common/validate/captchaImage").anonymous()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/", "/favicon.ico", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll().antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                .antMatchers("/system").access("hasRole('SYSTEM')")
                .antMatchers("/common").access("hasRole('COMMON')")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().and().headers().frameOptions().disable();
        // 添加Logout filter
        httpSecurity.logout().logoutUrl("/system/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(sysUserPasswordLoginProvider);
    }

}
