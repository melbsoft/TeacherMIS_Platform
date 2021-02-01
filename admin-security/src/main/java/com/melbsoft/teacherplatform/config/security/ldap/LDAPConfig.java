package com.melbsoft.teacherplatform.config.security.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.server.UnboundIdContainer;

@Configuration
public class LDAPConfig {
    @Bean
    UnboundIdContainer ldapContainer() {
        return new UnboundIdContainer("dc=springframework,dc=org",
                "classpath:users.ldif");
    }


    @Bean
    ContextSource contextSource(UnboundIdContainer ldapContainer) {
        return new
                DefaultSpringSecurityContextSource("ldap://localhost:53389/dc=springframework,dc=org");
    }


}
