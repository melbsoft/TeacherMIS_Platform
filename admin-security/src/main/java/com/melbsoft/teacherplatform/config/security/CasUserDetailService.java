package com.melbsoft.teacherplatform.config.security;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CasUserDetailService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        return User.builder().username("leo").build();
    }
}
