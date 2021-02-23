package com.melbsoft.teacherplatform.config.security;

import com.google.common.collect.Lists;
import com.melbsoft.teacherplatform.dao.admin.PermissionMapper;
import com.melbsoft.teacherplatform.dao.admin.ResourceMapper;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Slf4j
@Configuration
public class RBACPermissionEvaluator implements PermissionEvaluator {


    @Resource
    PermissionMapper permissionMapper;

    @Resource
    ResourceMapper resourceMapper;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("{} {} {}", authentication, targetDomainObject, permission);
        String[] targetInfo = targetDomainObject.toString().split(":");
        List<Integer> resourceIDs = getResourceIDs(targetInfo[0], targetInfo[1]);
        if (targetInfo.length == 2) {
            return authentication.getAuthorities().stream().filter(
                    auth -> {
                        return hasPermission(auth, resourceIDs, permission.toString());
                    }
            ).findFirst().isPresent();

        }

        return false;
    }

    private List<Integer> getResourceIDs(String resourceName, String resourceType) {
        List<Integer> l = Lists.newArrayList();
        Integer id = resourceMapper.findResourceID(resourceName, resourceType);
        l.add(id);
        Integer pid = resourceMapper.findParentId(id);
        while (pid != null) {
            l.add(pid);
            pid = resourceMapper.findParentId(pid);
        }
        return l;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("{} {} {} {}", authentication, targetId, targetType, permission);
        List<Integer> resourceIDs = getResourceIDs(targetId.toString(), targetType);
        return authentication.getAuthorities().stream().filter(
                auth -> {
                    return hasPermission(auth, resourceIDs, permission.toString());
                }
        ).findFirst().isPresent();
    }

    public Boolean hasPermission(GrantedAuthority auth, List<Integer> ids, String permission) {
        log.info("{} {} {} {}", auth, ids, permission);
        SysRole r = (SysRole) auth;
        return (permissionMapper.findPermissions(r.getRoleId(), ids, permission) > 0L);
    }
}
