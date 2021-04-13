package com.melbsoft.teacherplatform.config.security;

import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.dao.admin.UserMapper;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class CasUserDetailService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
    public static final long DEFAULT_ROLE_ID = 6L;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        String username = token.getPrincipal().toString();
        String cn = token.getAssertion().getPrincipal().getAttributes().get("cn").toString();
        int rows = userMapper.insertUser(username, "cas", cn);
        if (rows == 1) {
            roleMapper.insertRole(DEFAULT_ROLE_ID, username);
        }
        return User.builder().username(username).password("cas")
                .authorities(roleMapper.listRolesByUserName(username)).build();
    }
}
