package com.melbsoft.teacherplatform.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.service.admin.RBACUserDetailsService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

//@EnableWebSecurity
//@Slf4j
//@Configuration
//@Order(1)
public class LDAPSecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private RoleMapper roleMapper;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .contextSource(contextSource())
                .userDnPatterns("uid={0},ou=people")
                .passwordCompare()
                .passwordAttribute("userPassword").and()
                .ldapAuthoritiesPopulator(new LdapAuthoritiesPopulator() {
                    @Override
                    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
                        return roleMapper.listRolesByUserName(username);
                    }
                });

    }

    @Bean
    UnboundIdContainer ldapContainer() {
        return new UnboundIdContainer("dc=springframework,dc=org",
                "classpath:users.ldif");
    }


    @Bean
    BaseLdapPathContextSource contextSource() {
        return new
                DefaultSpringSecurityContextSource("ldap://localhost:53389/dc=springframework,dc=org");
    }
}
