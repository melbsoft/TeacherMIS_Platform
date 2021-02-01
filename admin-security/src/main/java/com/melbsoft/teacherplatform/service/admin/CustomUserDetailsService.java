package com.melbsoft.teacherplatform.service.admin;

import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.dao.admin.UserMapper;
import com.melbsoft.teacherplatform.model.admin.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser detail = userMapper.findUser(username);
        log.info("username {} detail {}",username,detail);
        if (detail != null) {
            User.UserBuilder builder = User.builder()
                    .username(detail.getLoginName())
                    .password(detail.getPassword())
                    .roles(roleMapper.listRolesByUserID(detail.getUserID())
                            .toArray(new String[0]));
            return builder.build();
        }
        throw new UsernameNotFoundException(String.format("username not found %s",username));
    }
}
