package com.melbsoft.teacherplatform.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.melbsoft.teacherplatform.service.admin.CustomUserDetailsService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.annotation.Resource;

@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    BaseLdapPathContextSource contextSource;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> {
                    authorize
                            .anyRequest()
                            .authenticated();
                }
        ).formLogin(form -> {
            form
                    .loginPage("/login")
                    .successHandler((req, resp, auth) -> {
                        log.info("登录成功");
                        resp.setContentType("application/json;charset=UTF-8");
                        resp.getWriter().write(objectMapper.writeValueAsString(ResultMessage.success(auth)));
                    })
                    .failureHandler((req, resp, auth) -> {
                        log.info("登录验证失败");
                        resp.setContentType("application/json;charset=UTF-8");
                        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                        resp.getWriter().write(objectMapper.writeValueAsString(ResultMessage.INVALID));
                    })
                    .permitAll();
        }).csrf(csrf -> {
                    csrf
                            .csrfTokenRepository(
                                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                            );
                }
        ).logout(logout -> {
            logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler((req, resp, auth) -> {
                        log.info("退出成功");
                        resp.setContentType("application/json;charset=UTF-8");
                        resp.getWriter().write(objectMapper.writeValueAsString(auth));
                    })
                    .invalidateHttpSession(true)
                    .clearAuthentication(true);

        }).httpBasic()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .contextSource(contextSource)
                .userDnPatterns("uid={0},ou=people")
                .passwordCompare().passwordAttribute("userPassword").and()
                .and()
                .userDetailsService(customUserDetailsService());
    }

    @Bean
    CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

}
