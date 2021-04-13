package com.melbsoft.teacherplatform.config.security;

import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.dao.admin.UserMapper;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import com.melbsoft.teacherplatform.model.admin.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

@Slf4j
public class RBACUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            SysUser sysUser = userMapper.findUser(username);
            log.info("loadUserByUsername username:{} sysUser:{}", username, sysUser);
            if (sysUser != null) {
                User.UserBuilder builder = User.builder()
                        .username(sysUser.getLoginName())
                        .password(sysUser.getPassword())
                        .authorities(roleMapper.listRolesByUserID(sysUser.getUserID()).toArray(new SysRole[0]));
                return builder.build();
            }
        } catch (Throwable e) {
            log.error("fail to load user by name!", e);
        }
        throw new UsernameNotFoundException(String.format("username not found %s", username));
    }
}
