package com.melbsoft.teacherplatform.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.dao.admin.UserMapper;
import com.melbsoft.teacherplatform.service.admin.SysUserService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import java.util.Collection;
import java.util.Collections;

@EnableWebSecurity
@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {

    public static final int TOKEN_VALIDITY_SECONDS = 3600 * 24 * 15;
    public static final long DEFAULT_ROLE_ID = 6L;
    @Resource
    BaseLdapPathContextSource contextSource;
    @Resource
    String dnPattern;
    @Resource
    DataSource datasourceAdmin;
    @Resource
    SysUserService sysUserService;
    @Value("${ldap.switch}")
    boolean ldapSupport;
    @Value("${cas.switch}")
    boolean casSupport;

    private String UNAUTH_RESP = "";
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    @Value("${cas.server.host.url}")
    private String casServerUrl;
    @Value("${cas.server.host.login_url}")
    private String casServerLoginUrl;
    @Value("${cas.server.host.logout_url}")
    private String casServerLogoutUrl;
    @Value("${cas.app.server.host.url}")
    private String appServerUrl;
    @Value("${cas.app.login.url}")
    private String appLoginUrl;
    @Value("${cas.app.logout.url}")
    private String appLogoutUrl;

    @Override
    public void afterPropertiesSet() throws Exception {
        UNAUTH_RESP = objectMapper.writeValueAsString(ResultMessage.UN_AUTH);
    }

    private void unAuthorizeResponse(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.getWriter().write(UNAUTH_RESP);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .cors().and()
                .csrf(csrf -> {
                            csrf.ignoringAntMatchers("/token")
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
                                unAuthorizeResponse(resp);
                            })
                            .permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessHandler((req, resp, auth) -> {
                                log.info("退出成功");
                                resp.setContentType("application/json;charset=UTF-8");
                                resp.getWriter().write(objectMapper.writeValueAsString(ResultMessage.success(auth)));
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
                        unAuthorizeResponse(resp);
                    }
                })
                .sessionConcurrency(c -> {
                    c.maximumSessions(1)
                            .expiredSessionStrategy(expire -> {
                                HttpServletResponse resp = expire.getResponse();
                                unAuthorizeResponse(resp);
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
                                    .antMatchers("/token").permitAll()
                                    .antMatchers("/favicon.ico").permitAll()
                                    .antMatchers("/v3/api-docs/**").permitAll()
                                    .antMatchers("/caslogin").permitAll()
                                    .antMatchers("/caslogout").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .exceptionHandling(handler -> {
                    handler.authenticationEntryPoint((req, resp, e) -> {
                        log.error("授权失效", e);
                        resp.addHeader("WWW-Authenticate", "Basic realm=\".\"");
                        unAuthorizeResponse(resp);
                    });
                    handler.accessDeniedHandler((req, resp, e) -> {
                        log.error("拒绝访问", e);
                        unAuthorizeResponse(resp);
                    });
                })
                .httpBasic();
        if (casSupport) {
            http
//                    .exceptionHandling()
//                    .authenticationEntryPoint(casAuthenticationEntryPoint())
//                    .and()
                    .addFilter(casAuthenticationFilter())
                    .addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                    .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
        }
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casServerLoginUrl);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setAuthenticationUserDetailsService(casUserService());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
        return casAuthenticationProvider;
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(appServerUrl + appLoginUrl);
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setFilterProcessesUrl(appLoginUrl);
        return casAuthenticationFilter;
    }

    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> casUserService() {
        return new CasUserDetailService();
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(casServerUrl);
    }


    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    @Bean
    public LogoutFilter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(casServerLogoutUrl, new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(appLogoutUrl);
        return logoutFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(rbacUserDetailsService())
                .passwordEncoder(securityPasswordEncoder());

        if (ldapSupport) {
            auth
                    .ldapAuthentication()
                    .contextSource(contextSource)
                    .userDnPatterns(dnPattern)
                    .passwordCompare()
                    .passwordEncoder(createEncoder())
                    .passwordAttribute("userPassword")
                    .and()
                    .ldapAuthoritiesPopulator(new LdapAuthoritiesPopulator() {

                        @Override
                        public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
                            int rows = userMapper.insertUser(username, "ldap", userData.getStringAttribute("cn"));
                            if (rows == 1) {
                                roleMapper.insertRole(DEFAULT_ROLE_ID, username);
                            }
                            return roleMapper.listRolesByUserName(username);
                        }
                    });
        }

        if (casSupport) {
            auth.authenticationProvider(casAuthenticationProvider());
        }
    }

    private PasswordEncoder createEncoder() {
        return new DelegatingPasswordEncoder("WMD5", Collections.singletonMap("WMD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5")));
    }


    @Bean
    String dnPattern(@Value("${ldap.dn.pattern}")
                             String dnPattern) {
        return dnPattern;
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
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:8080",
                        "http://47.92.240.50:8080"));
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.HEAD);
        configuration.addAllowedMethod(HttpMethod.OPTIONS);

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
