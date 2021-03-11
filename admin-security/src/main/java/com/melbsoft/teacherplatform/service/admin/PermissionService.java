package com.melbsoft.teacherplatform.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.dao.admin.PermissionMapper;
import com.melbsoft.teacherplatform.model.admin.SysPermission;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import com.melbsoft.teacherplatform.model.admin.vo.SysPermissionQuery;
import com.melbsoft.teacherplatform.model.admin.vo.UserPermissionQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Resource
    PermissionMapper permissionMapper;
    @Resource
    ResourceService resourceService;

    public PageInfo<List<SysPermission>> search(SysPermissionQuery query) {
        PageHelper.startPage(query.getPage(), DEFAULT_PAGE_SIZE);
        PageInfo<List<SysPermission>> pagedPermissions = new PageInfo(permissionMapper.search(query));
        return pagedPermissions;
    }


    public List<SysResource> listPermission(List<SysRole> roles, UserPermissionQuery query) {
        List<Long> ids = permissionMapper.listPermission(roles, query);
        List<SysResource> resources = ids.stream().map(id -> {
            SysResource resouce = resourceService.findResouce(id);
            fillSubResource(resouce);
            return resouce;
        }).collect(Collectors.toList());
        return resources;
    }

    private void fillSubResource(SysResource r) {
        if (r.getSubResource() == null) {
            List<SysResource> sublist = resourceService.listSubResource(r.getResourceId());
            if (sublist.isEmpty()) {
                return;
            } else {
                r.setSubResource(sublist);
                r.getSubResource().forEach(sub -> {
                    fillSubResource(sub);
                });
            }
            return;
        }
    }


}
