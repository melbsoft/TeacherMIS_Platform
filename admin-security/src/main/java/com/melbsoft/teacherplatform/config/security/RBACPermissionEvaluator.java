package com.melbsoft.teacherplatform.config.security;

import com.melbsoft.teacherplatform.dao.admin.PermissionMapper;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Resource;
import java.io.Serializable;

@Slf4j
@Configuration
public class RBACPermissionEvaluator implements PermissionEvaluator {



    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("{} {} {}", authentication, targetDomainObject, permission);

        String[] targetInfo = targetDomainObject.toString().split(":");
        if (targetInfo.length == 2) {
            return authentication.getAuthorities().stream().filter(
                    auth -> {
                        return hasPermission(auth, targetInfo[0], targetInfo[1], permission.toString());
                    }
            ).findFirst().isPresent();

        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("{} {} {} {}", authentication, targetId, targetType, permission);
        return authentication.getAuthorities().stream().filter(
                auth -> {
                    return hasPermission(auth, targetId.toString(), targetType, permission.toString());
                }
        ).findFirst().isPresent();
    }
    @Resource
    PermissionMapper permissionMapper;

    public Boolean hasPermission(GrantedAuthority auth, String targetId, String targetType, String permission) {
        log.info("{} {} {} {}", auth, targetId, targetType, permission);
        SysRole r= (SysRole) auth;
        return (permissionMapper.findPermissions(r.getRoleId(),targetId,targetType,permission)>0L);
    }
}
