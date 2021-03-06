package com.melbsoft.teacherplatform.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.service.admin.RBACUserDetailsService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {

    public static final int TOKEN_VALIDITY_SECONDS = 3600 * 24 * 15;
    @Resource
    BaseLdapPathContextSource contextSource;
    @Resource
    DataSource datasourceAdmin;
    private String INVALID_RESP = "";
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        INVALID_RESP = objectMapper.writeValueAsString(ResultMessage.INVALID);
    }

    private void InvalidResponse(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.getWriter().write(INVALID_RESP);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf(csrf -> {
                            csrf
                                    .csrfTokenRepository(
                                            CookieCsrfTokenRepository.withHttpOnlyFalse()
                                    );

                        }
                )
                .formLogin(form -> {
                    form
                            .loginPage("/login")
                            .successHandler((req, resp, auth) -> {
                                log.info("登录成功");
                                resp.setContentType("application/json;charset=UTF-8");
                                resp.getWriter().write(objectMapper.writeValueAsString(ResultMessage.success(auth)));
                            })
                            .failureHandler((req, resp, auth) -> {
                                log.info("登录验证失败");
                                InvalidResponse(resp);
                            })
                            .permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessHandler((req, resp, auth) -> {
                                log.info("退出成功");
                                resp.setContentType("application/json;charset=UTF-8");
                                resp.getWriter().write(objectMapper.writeValueAsString(auth));
                            })
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .permitAll();
                })
                .sessionManagement()
                .invalidSessionStrategy(new InvalidSessionStrategy() {
                    @Override
                    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
                        request.getSession();
                        InvalidResponse(resp);
                    }
                })
                .sessionConcurrency(c -> {
            c.maximumSessions(1)
                    .expiredSessionStrategy(expire -> {
                        HttpServletResponse resp = expire.getResponse();
                        InvalidResponse(resp);
                    });
        })
                .and()
                .rememberMe(rememberMe -> {
                    rememberMe
                            .userDetailsService(rbacUserDetailsService())
                            .tokenRepository(persistentTokenRepository())
                            .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS);
                })
                .authorizeRequests(authorize -> {
                            authorize
                                    .antMatchers("/swagger-ui/**").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .exceptionHandling(handler -> {
                    handler.accessDeniedHandler((req, resp, e) -> {
                        log.error("拒绝访问",e);
                        InvalidResponse(resp);
                    });
                })
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(rbacUserDetailsService())
                .passwordEncoder(securityPasswordEncoder());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(datasourceAdmin);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RBACUserDetailsService rbacUserDetailsService() {
        return new RBACUserDetailsService();
    }

    @Bean
    Argon2PasswordEncoder securityPasswordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
